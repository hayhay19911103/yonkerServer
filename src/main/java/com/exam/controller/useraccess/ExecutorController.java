package com.exam.controller.useraccess;

import com.exam.Repository.*;
import com.exam.model.bean.JsonRequest;
import com.exam.model.bean.JsonResult;
import com.exam.model.entity.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mac on 2017/3/10.
 */

@RestController
@RequestMapping("/api/executor")
public class ExecutorController {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectPlanRepository projectPlanRepository;
    @Autowired
    ProjectQualifiedRepository projectQualifiedRepository;
    @Autowired
    FieldExecuteRepository fieldExecuteRepository;
    @Autowired
    FieldRepository fieldRepository;
    @Autowired
    private FieldPlanRepository fieldPlanRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ControlPointRepository controlPointRepository;
    @Autowired
    private CollectPhotoRepository collectPhotoRepository;
    @Autowired
    private CollectDataRepository collectDataRepository;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private ApplyRepository applyRepository;
    @Autowired
    private PlanProcedureRepository planProcedureRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private ProjectControlPointRepository projectControlPointRepository;

    //第一，项目执行者（包括技术人员和施工人员）获取工程列表
    @RequestMapping(value = "/getExecutorProject")
    public String getExecutorProject(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();

        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);
        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();

        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != 3
                || userLevel!=3) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你没有该操作的权限");
            return gson.toJson(jsonResult);
        }

        User user = gson.fromJson(msg, User.class);
        //实际只需要项目实施者userId;
        //String userId = gson.fromJson(msg,String.class);

        List<ProjectQualified> projectQualifiedList = projectQualifiedRepository.findByQualifiedId(user.getUserId());
        List<FieldExecute> fieldExecuteList = fieldExecuteRepository.findByExecutorId(user.getUserId());

        List<Project> projectList = new ArrayList<Project>();
        if (projectQualifiedList.size() != 0 || fieldExecuteList.size() != 0) {
            //遍历fieldExecuteList
            for (FieldExecute fieldExecute : fieldExecuteList) {

                //通过田块id找到田块
                Integer fieldId = fieldExecute.getFieldId();
                Field field = fieldRepository.findOne(fieldId);
                //通过田块找到对应项目
                Integer projectId = field.getProjectId();
                Project project = projectRepository.findOne(projectId);

                //把实施人员项目加入到项目列表中
                if (!projectList.contains(project)&&project.getCreateCompleted()) {

                    Project project1 = new Project();
                    project1.setProjectId(projectId);
                    project1.setProjectName(project.getProjectName());
                    project1.setProjectStartTime(project.getProjectStartTime());
                    //根据此了解项目是否完成
                    project1.setCompleted(project.getCompleted());

                    //安卓端通过此列表来获知哪些是技术人员
                    project1.setProjectQualifiedList(projectQualifiedRepository.findByProjectId(project1.getProjectId()));
                    projectList.add(project1);

                }
            }


            for (ProjectQualified projectQualified : projectQualifiedList) {

                //通过工程Id找到对应项目
                Integer projectId = projectQualified.getProjectId();
                Project project = projectRepository.findOne(projectId);


                if (!projectList.contains(project)&&project.getCreateCompleted()) {
                    Project project2 = new Project();
                    project2.setProjectId(project.getProjectId());
                    project2.setProjectName(project.getProjectName());
                    project2.setProjectStartTime(project.getProjectStartTime());
                    project2.setCompleted(project.getCompleted());

                    project2.setProjectQualifiedList(projectQualifiedRepository.findByProjectId(project2.getProjectId()));
                    projectList.add(project2);
                }
            }
            jsonResult.setResult(1);
            jsonResult.setMsg("获取项目列表成功");
            jsonResult.setData(gson.toJson(projectList));

        } else {
            jsonResult.setResult(0);
            jsonResult.setMsg("该用户没有项目列表");
            jsonResult.setData("");

        }
        return gson.toJson(jsonResult);
    }


    //第二，由安卓端来验证是否该用户是否属于技术人员，只有技术人员可以新增田块
    @RequestMapping(value = "/newAddField")
    public String newAddField(@RequestBody String msg) {

        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);
        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();

        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != 3
                || userLevel!=3) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你没有该操作的权限");
            return gson.toJson(jsonResult);
        }
        //映射实体类
        //新建田块时必须传来田块的所有信息,projectId（除了田块id）,传入实施人员列表


        Field field = gson.fromJson(msg, Field.class);
        Field field1 = fieldRepository.findByFieldName(field.getFieldName());
        if (field1 == null) {

            fieldRepository.save(field);
            List<ProjectPlan> projectPlanList = projectPlanRepository.findByProjectId(field.getProjectId());


            //新建田块时把该田块所属项目的方案工序列表赋值给田块方案工序列表
            for (ProjectPlan projectPlan : projectPlanList) {

                FieldPlan fieldPlan = new FieldPlan();
                fieldPlan.setFieldId(field.getFieldId());
                fieldPlan.setStep(projectPlan.getStep());
                //工序第一步是否点击设为true
                if (fieldPlan.getStep() == 1) {

                    fieldPlan.setIsClick(true);
                } else {
                    fieldPlan.setIsClick(false);
                }

                fieldPlan.setIsCompleted(false);
                fieldPlan.setIsSerial(projectPlan.getIsSerial());
                fieldPlan.setNextStep(projectPlan.getNextStep());
                fieldPlan.setProStep(projectPlan.getProStep());
                fieldPlan.setProcedureName(projectPlan.getProcedureName());

                fieldPlan.setProcedureId(projectPlan.getProcedureId());
                fieldPlan.setProjectId(projectPlan.getProjectId());
                fieldPlan.setProjectPlanId(projectPlan.getProjectPlanId());
                fieldPlanRepository.save(fieldPlan);

            }


            //获取传来的实施人员列表并传入数据库
            List<FieldExecute> fieldExecuteList = field.getFieldExecuteList();
            if (fieldExecuteList != null && fieldExecuteList.size() != 0) {
                for (FieldExecute fieldExecute : fieldExecuteList) {
                    fieldExecute.setFieldId(field.getFieldId());
                    fieldExecuteRepository.save(fieldExecute);
                }
            }

            User user = new User();
            user.setUserPhone(field.getFieldOwnerPhone());
            user.setUserName(field.getFieldOwner());
            user.setUserPwd("123456");



            userRepository.save(user);



            field.getFieldOwner();
            field.getFieldOwnerIdentification();




            jsonResult.setResult(1);
            jsonResult.setMsg("创建田块成功");


        } else {
            jsonResult.setResult(0);
            jsonResult.setMsg("失败,该田块名已存在");

        }
        return gson.toJson(jsonResult);
    }


    //第三，技术人员，施工人员通过project条目获取田块列表,加田块详情
    @RequestMapping(value = "/getFieldList")
    public String getFieldList(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);
        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();

        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != 3
                || userLevel!=3) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你没有该操作的权限");
            return gson.toJson(jsonResult);
        }


        //映射实体类
        Project project = gson.fromJson(msg, Project.class);
        List<Field> fieldList = fieldRepository.findByProjectId(project.getProjectId());
        if (fieldList != null && fieldList.size() != 0) {
            for (Field field : fieldList) {
                List<FieldExecute> fieldExecuteList = fieldExecuteRepository.findByFieldId(field.getFieldId());
                field.setFieldExecuteList(fieldExecuteList);
                List<FieldPlan> fieldPlanList = fieldPlanRepository.findByFieldId(field.getFieldId());
                field.setFieldPlanList(fieldPlanList);
            }
            jsonResult.setResult(1);
            jsonResult.setMsg("返回田块列表");
            jsonResult.setData(gson.toJson(fieldList));

        } else {
            jsonResult.setResult(0);
            jsonResult.setMsg("返回田块列表为空");
        }
        return gson.toJson(jsonResult);
    }

    //第四，获取田块工序列表
    @RequestMapping(value = "/getFieldPlanList")
    public String getFieldPlanList(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);

        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();

        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != 3
                || userLevel!=3) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你没有该操作的权限");
            jsonRequest.setData("");
            return gson.toJson(jsonResult);
        }

        //通过FieldId获取田块工序列表

        Field field = gson.fromJson(msg, Field.class);
        List<FieldPlan> fieldPlanList = fieldPlanRepository.findByFieldId(field.getFieldId());
        jsonResult.setResult(1);
        jsonResult.setMsg("返回该田块工序列表");
        jsonResult.setData(gson.toJson(fieldPlanList));
        return gson.toJson(jsonResult);

    }


    //第五，通过工序id获取工序控制点列表
    @RequestMapping(value = "/getControlPoint")
    public String getControlPoint(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);

        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();


        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != 3
                || userLevel!=3) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你没有该操作的权限");
            return gson.toJson(jsonResult);
        }


        FieldPlan fieldPlan = gson.fromJson(msg, FieldPlan.class);
        List<ControlPoint> controlPointList = controlPointRepository.findByProcedureId(fieldPlan.getProcedureId());

        jsonResult.setResult(1);
        jsonResult.setMsg("返回该道工序控制点列表");
        jsonResult.setData(gson.toJson(controlPointList));
        return gson.toJson(jsonResult);

    }


    //第六，项目实施者保存上传的采集的图片
    @RequestMapping(value = "/uploadCollectPhoto")
    public String uploadCollectPhoto(@RequestParam("file") MultipartFile file, @RequestParam Map<String, String> params) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();

        String currentTime = String.valueOf(System.currentTimeMillis());
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fileName = currentTime + file.getOriginalFilename();

        //msg是json格式的字符串
        String msg = params.get("CollectPhoto");

        //传入采集图片的基本信息
        CollectPhoto collectPhoto = gson.fromJson(msg, CollectPhoto.class);
        //collectPhoto.setCollectTime(df.format(new Date()));

        collectPhoto.setPath(fileName);

        if (!file.isEmpty()) {
            try {

                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File("C:/CollectPhotos/" + fileName)));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();

                jsonResult.setResult(0);
                jsonResult.setMsg("上传失败," + e.getMessage());
                return gson.toJson(jsonResult);
            }
            collectPhotoRepository.save(collectPhoto);
            jsonResult.setResult(1);
            jsonResult.setMsg("上传采集照片成功");
            jsonResult.setData(fileName);
            return gson.toJson(jsonResult);
        } else {
            jsonResult.setResult(0);
            jsonResult.setMsg("上传失败，因为文件是空的。");
            return gson.toJson(jsonResult);
        }
    }


    //第七，保存实施者上传的采集的数据

    @RequestMapping(value = "/upLoadCollectData")
    public String upLoadCollectData(@RequestBody String msg) {

        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);

        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();

        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != 3
                || userLevel!=3) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你没有该操作的权限");
            return gson.toJson(jsonResult);
        }


        CollectData collectData = gson.fromJson(msg, CollectData.class);
        collectDataRepository.save(collectData);
        jsonResult.setResult(1);
        jsonResult.setMsg("上传采集数据到数据库成功");
        return gson.toJson(jsonResult);

    }


    //第八，获取公告
    @RequestMapping(value = "getExecutorNoticeList")
    public String getExecutorNoticeList(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);
        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();

        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != 3
                || userLevel!=3) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你没有该操作的权限");
            return gson.toJson(jsonResult);
        }

        List<ProjectQualified> projectQualifiedList = projectQualifiedRepository.findByQualifiedId(uuid);
        List<FieldExecute> fieldExecuteList = fieldExecuteRepository.findByExecutorId(uuid);
        List<Notice> noticeList = new ArrayList<Notice>();
        List<Integer> projectIdList = new ArrayList<Integer>();
        if (projectQualifiedList.size() != 0 || fieldExecuteList.size() != 0) {
            for (FieldExecute fieldExecute : fieldExecuteList) {
                Integer fieldId = fieldExecute.getFieldId();
                Field field = fieldRepository.findOne(fieldId);
                Integer projectId = field.getProjectId();
                if (!projectIdList.contains(projectId)) {
                    projectIdList.add(projectId);
                    noticeList.addAll(noticeRepository.findByProjectId(projectId));
                }
            }
            for (ProjectQualified projectQualified : projectQualifiedList) {
                Integer projectId = projectQualified.getProjectId();
                if (!projectIdList.contains(projectId)) {
                    projectIdList.add(projectId);
                    noticeList.addAll(noticeRepository.findByProjectId(projectId));
                }
            }
            jsonResult.setResult(1);
            jsonResult.setData(gson.toJson(noticeList));
            return gson.toJson(jsonResult);
        } else {
            jsonResult.setResult(0);
            jsonResult.setMsg("无相关公告信息");
            jsonResult.setData("");
            return gson.toJson(jsonResult);
        }
    }

    //第九，获取由我提出的审批列表（工程人员提出审批）
    @RequestMapping(value = "getApplyFromList")
    public String getApplyFromList(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);
        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();

        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != 3
                || userLevel!=3) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你没有该操作的权限");
            return gson.toJson(jsonResult);
        }
        List<Apply> applyList = applyRepository.findByApplyFromId(uuid);
        if (applyList.size() != 0) {
            jsonResult.setResult(1);
            jsonResult.setMsg("获取审批列表");
            jsonResult.setData(gson.toJson(applyList));
            return gson.toJson(jsonResult);
        } else {
            jsonResult.setResult(0);
            jsonResult.setMsg("无相关审批！");
            jsonResult.setData("");
            return gson.toJson(jsonResult);
        }
    }

    //第十，获取待我审批的列表
    @RequestMapping(value = "getApplyToList")
    public String getApplyToList(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);
        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != 3
                || userLevel!=3) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你没有该操作的权限");
            return gson.toJson(jsonResult);
        }
        List<Apply> applyList = applyRepository.findByApplyToId(uuid);
        //获取未审批的列表
        List<Apply> undoList = new ArrayList<Apply>();
        if (applyList.size() > 0) {
            for (Apply apply : applyList) {
                if (apply.getIsPass() == 0) {
                    undoList.add(apply);
                }
            }
        }
        if (undoList.size() != 0) {
            jsonResult.setResult(1);
            jsonResult.setMsg("获取待审批列表");
            jsonResult.setData(gson.toJson(undoList));
            return gson.toJson(jsonResult);
        } else {
            jsonResult.setResult(0);
            jsonResult.setMsg("无相关审批！");
            jsonResult.setData("");
            return gson.toJson(jsonResult);
        }
    }

    //审批通过/不通过
    @RequestMapping(value = "editApply")
    public String editApply(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);
        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != 3
                || userLevel!=3) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你没有该操作的权限");
            return gson.toJson(jsonResult);
        }
        Apply apply = gson.fromJson(jsonRequest.getData(), Apply.class);
        Apply apply1 = applyRepository.findByApplyId(apply.getApplyId());
        apply1.setIsPass(apply.getIsPass());
        applyRepository.save(apply1);
        jsonResult.setResult(1);
        jsonResult.setMsg("审批成功！");
        return gson.toJson(jsonResult);
    }

    @RequestMapping(value = "getControlPointListByProject")
    public String getControlPointListByProject(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);
        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != 3
                || userLevel!=3) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你没有该操作的权限");
            return gson.toJson(jsonResult);
        }
        ProjectControlPoint projectControlPoint = gson.fromJson(jsonRequest.getData(), ProjectControlPoint.class);
        List<ProjectControlPoint> projectControlPointList = projectControlPointRepository.findByProjectId(projectControlPoint.getProjectId());
        jsonResult.setData(gson.toJson(projectControlPointList));
        jsonResult.setResult(1);
        jsonResult.setMsg("获取项目控制点列表");
        return gson.toJson(jsonResult);
    }

}
