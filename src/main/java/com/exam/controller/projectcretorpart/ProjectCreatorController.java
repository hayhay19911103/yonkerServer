package com.exam.controller.projectcretorpart;

import com.exam.Repository.*;
import com.exam.model.bean.JsonRequest;
import com.exam.model.bean.JsonResult;
import com.exam.model.entity.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mac on 2017/4/12.
 */

@RestController
@RequestMapping("/api/creators")
public class ProjectCreatorController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectQualifiedRepository projectQualifiedRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private PlanProcedureRepository planProcedureRepository;
    @Autowired
    private ProjectPlanRepository projectPlanRepository;
    @Autowired
    private ControlPointRepository controlPointRepository;
    @Autowired
    private ProjectControlPointRepository projectControlPointRepository;
    @Autowired
    private CollectDataRepository collectDataRepository;
    @Autowired
    private CollectRepository collectRepository;
    @Autowired
    private CollectPhotoRepository collectPhotoRepository;

    //第一，基础信息录入人员(level = 21)接口，获取项目负责人员列表、获取技术人员列表、获取售前人员列表
    @RequestMapping(value = "/inputProject/21/getMemberList")
    public String getMemberList(@RequestBody String msg) {

        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);

        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();

        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != 21
                || userLevel != 21) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你不是基础信息录入人员，没有该操作的权限");
            return gson.toJson(jsonResult);
        }

        List<User> userList1 = userRepository.findByUserLevel(22);
        List<User> userList2 = userRepository.findByUserLevel(23);
        List<User> userList3 = userRepository.findByUserLevel(24);

        Map<String, List<User>> maps = new HashMap<>();
        maps.put("ManagerName", userList1);
        maps.put("SkillManagerName", userList2);
        maps.put("SaleEngineerName", userList3);

        jsonResult.setResult(1);
        jsonResult.setMsg("获取项目负责人员列表、获取技术人员列表、获取售前人员列表");
        jsonResult.setData(gson.toJson(maps));

        return gson.toJson(jsonResult);

    }



    //第二，基础信息录入人员(level = 21)接口，录入项目基础信息
    @RequestMapping(value = "/inputProject/21/addBaseIoFo")
    public String addBaseIoFo(@RequestBody String msg) {

        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);

        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();

        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != 21
                || userLevel!=21) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你不是基础信息录入人员，没有该操作的权限");
            return gson.toJson(jsonResult);
        }
        //映射实体类
        //新建工程时必须传来该工程的所有信息
        Project project = gson.fromJson(msg, Project.class);
        //数据库对象
        if (project.getProjectName() == null) {

            jsonResult.setResult(0);
            jsonResult.setMsg("请传入工程名");
            jsonResult.setData("");
            return gson.toJson(jsonResult);

        }

        Project project1 = projectRepository.findByProjectName(project.getProjectName());
        if(project1 == null) {
            projectRepository.save(project);
            jsonResult.setResult(1);
            jsonResult.setMsg("工程信息录入成功");
            jsonResult.setData("");
            return gson.toJson(jsonResult);
        }
        else {
            jsonResult.setResult(0);
            jsonResult.setMsg("该工程名已存在，录入失败");
            jsonResult.setData("");
            return gson.toJson(jsonResult);
        }
    }



    //第三，项目负责人(level = 22)接口获取实施者列表，返回列表以便项目负责人选取田块创建者，也就是实施者中的技术员

    @RequestMapping(value = "/inputProject/22/getQualifiedList")
    public String getQualifiedList(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);

        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();

        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != 22
                || userLevel!=22) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你不是项目负责人人员，没有该操作的权限");
            return gson.toJson(jsonResult);
        }

        List<User> userList = userRepository.findByUserLevel(3);

        if (userList != null && userList.size() != 0) {
            jsonResult.setResult(1);
            jsonResult.setMsg("获取项目所有实施者列表");
            jsonResult.setData(gson.toJson(userList));

        } else {
            jsonResult.setResult(0);
            jsonResult.setMsg("暂无数据");
            jsonResult.setData("");
        }
        return gson.toJson(jsonResult);
    }

    //第四，各个创建者获取尚未创建完成完成的项目列表
    @RequestMapping(value = "/inputProject/unCompletedList")
    public String getIsPersonCompletedList(@RequestBody String msg) {

        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);

        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();

        if (userRepository.findByUserId(uuid) != null && ((userRepository.findByUserId(uuid).getUserLevel() == 22
                && userLevel == 22) || (userRepository.findByUserId(uuid).getUserLevel() == 23
                && userLevel == 23) || (userRepository.findByUserId(uuid).getUserLevel() == 24
                && userLevel == 24))) {

            List<Project> projectList1 = new ArrayList<Project>();
            if (userLevel == 22) {
                List<Project> projectList = projectRepository.findByProjectManagerId(uuid);

                if (projectList != null && projectList.size() != 0) {
                    for (Project project : projectList) {

                        if (project.getPersonCompleted() == false) {
                            Project project1 = new Project();
                            project1.setProjectId(project.getProjectId());
                            project1.setProjectName(project.getProjectName());
                            projectList1.add(project1);
                        }
                    }
                    jsonResult.setResult(1);
                    jsonResult.setMsg("返回尚未操作的项目列表");
                    jsonResult.setData(gson.toJson(projectList1));
                } else {
                    jsonResult.setResult(0);
                    jsonResult.setMsg("没有未操作的项目列表");
                    jsonResult.setData("");
                    return gson.toJson(jsonResult);
                }
            }

            if (userLevel == 23) {
                List<Project> projectList = projectRepository.findByProjectSkillManagerId(uuid);
                if (projectList != null && projectList.size() != 0) {
                    for (Project project : projectList) {

                        if (project.getProcedureCompleted() == false) {
                            Project project1 = new Project();
                            project1.setProjectId(project.getProjectId());
                            project1.setProjectName(project.getProjectName());
                            projectList1.add(project1);
                        }
                    }
                    jsonResult.setResult(1);
                    jsonResult.setMsg("返回尚未操作的项目列表");
                    jsonResult.setData(gson.toJson(projectList1));
                } else {
                    jsonResult.setResult(0);
                    jsonResult.setMsg("没有未操作的项目列表");
                    jsonResult.setData("");
                    return gson.toJson(jsonResult);
                }
            }

            if (userLevel == 24) {
                List<Project> projectList = projectRepository.findByPreSaleEngineerId(uuid);
                if (projectList != null && projectList.size() != 0) {
                    for (Project project : projectList) {

                        if (project.getResearchCompleted() == false) {
                            Project project1 = new Project();
                            project1.setProjectId(project.getProjectId());
                            project1.setProjectName(project.getProjectName());
                            projectList1.add(project1);
                        }
                    }
                    jsonResult.setResult(1);
                    jsonResult.setMsg("返回尚未操作的项目列表");
                    jsonResult.setData(gson.toJson(projectList1));
                } else {
                    jsonResult.setResult(0);
                    jsonResult.setMsg("没有未操作的项目列表");
                    jsonResult.setData("");
                    return gson.toJson(jsonResult);
                }

            }
            return gson.toJson(jsonResult);
        }
        jsonResult.setResult(0);
        jsonResult.setMsg("你没有操作权限");
        jsonResult.setData("");
        return gson.toJson(jsonResult);
    }


    //第五，项目负责人(level = 22)接口，项目负责人在基础信息录入后负责进行人员分配，选择田块创建者，也就是实施者中的技术员

    /**
     *
     * @param msg  传入 projectQualifiedList
     * @return
     */
    @RequestMapping(value = "/inputProject/22/selectQualified")
    public String selectQualified(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);

        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();

        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != 22
                || userLevel!=22) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你不是项目负责人人员，没有该操作的权限");
            return gson.toJson(jsonResult);
        }

        Type listType = new TypeToken<List<ProjectQualified>>(){}.getType();

        List<ProjectQualified> projectQualifiedList = gson.fromJson(msg,listType);

        if (projectQualifiedList != null && projectQualifiedList.size()!=0) {

            for (ProjectQualified projectQualified : projectQualifiedList) {
                projectQualifiedRepository.save(projectQualified);
            }
            Integer projectId = projectQualifiedList.get(0).getProjectId();
            Project project = projectRepository.findByProjectId(projectId);
            project.setPersonCompleted(true);
            if(project.getResearchCompleted()==true && project.getProcedureCompleted()==true){

                project.setCompleted(true);

            }
            projectRepository.save(project);
            jsonResult.setResult(1);
            jsonResult.setMsg("分配田块创建者成功，写入技术员列表");
            jsonResult.setData("");
            return gson.toJson(jsonResult);

        }
        else {
            jsonResult.setResult(0);
            jsonResult.setMsg("传入的技术员列表为空，分配失败");
            jsonResult.setData("");
            return gson.toJson(jsonResult);
        }

    }

    //第六， 该项目的技术人员负责人，权限为23，获取方案工序控制点列表 以便项目的工艺路线、工序的选择

    @RequestMapping(value = "/inputProject/23/getProcedureControlPoint")
    public String getProcedureControlPoint(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);

        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();

        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != 23
                || userLevel!=23) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你不是该项目的技术负责人，没有该操作的权限");
            return gson.toJson(jsonResult);
        }

        Plan plan = gson.fromJson(msg, Plan.class);
        if (plan.getPlanName().equals("")) {
            Sort s = new Sort(Sort.Direction.DESC, "planId");
            List<Plan> planList = planRepository.findAll(s);
            if (planList == null && planList.size() == 0) {
                jsonResult.setResult(0);
                jsonResult.setMsg("返回方案列表为空");
                jsonResult.setData(gson.toJson(planList));

            } else {

                for (Plan plan1 : planList) {
                    List<PlanProcedure> planProcedureList = planProcedureRepository.findByPlanId(plan1.getPlanId());
                    for(PlanProcedure planProcedure : planProcedureList){
                        List<ControlPoint> controlPointList = controlPointRepository.findByProcedureId(planProcedure.getProcedureId());
                        planProcedure.setControlPointList(controlPointList);
                    }
                    plan1.setPlanProcedureList(planProcedureList);
                }

                jsonResult.setResult(1);
                jsonResult.setMsg("返回全部方案以及工序控制点列表");
                jsonResult.setData(gson.toJson(planList));

            }
            return gson.toJson(jsonResult);
        } else {
            Sort s = new Sort(Sort.Direction.DESC, "planId");
            List<Plan> planList = planRepository.findByPlanNameLike(plan.getPlanName() + "%", s);
            if (planList == null && planList.size() == 0) {
                jsonResult.setResult(0);
                jsonResult.setMsg("返回方案列表为空");
                jsonResult.setData(gson.toJson(planList));

            } else {
                for (Plan plan1 : planList) {
                    List<PlanProcedure> planProcedureList = planProcedureRepository.findByPlanId(plan1.getPlanId());

                    for(PlanProcedure planProcedure : planProcedureList){
                        List<ControlPoint> controlPointList = controlPointRepository.findByProcedureId(planProcedure.getProcedureId());
                        planProcedure.setControlPointList(controlPointList);

                    }
                    plan1.setPlanProcedureList(planProcedureList);
                }
                jsonResult.setResult(1);
                jsonResult.setMsg("返回模糊查询方案以及工序控制点列表");
                jsonResult.setData(gson.toJson(planList));
            }
            return gson.toJson(jsonResult);
        }
    }


    //第七，该项目的技术人员负责人，权限为23，负责该项目方案工序控制点的设定

    @RequestMapping(value = "/inputProject/23/setProcedureControlPoint")
    public String setProcedureControlPoint(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);

        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();

        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != 23
                || userLevel!=23) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你不是该项目的技术负责人，没有该操作的权限");
            return gson.toJson(jsonResult);
        }


        Project project = gson.fromJson(msg,Project.class);
        List<ProjectPlan> projectPlanList = project.getProjectPlanList();

        List<ProjectControlPoint> projectControlPointList = project.getProjectControlPointList();


        if (projectPlanList != null && projectPlanList.size()!=0) {

            for (ProjectPlan projectPlan : projectPlanList) {
                projectPlanRepository.save(projectPlan);
            }
            for (ProjectControlPoint projectControlPoint:projectControlPointList){
                projectControlPointRepository.save(projectControlPoint);

            }

            Integer projectId = project.getProjectId();
            Project project1 = projectRepository.findByProjectId(projectId);
            project1.setProcedureCompleted(true);

            if(project1.getResearchCompleted()==true && project1.getPersonCompleted()==true){

                project1.setCompleted(true);

            }
            projectRepository.save(project1);
            jsonResult.setResult(1);
            jsonResult.setMsg("该项目的技术工序控制点设定成功，写入方案工序列表");
            jsonResult.setData("");
            return gson.toJson(jsonResult);

        }

        else {
            jsonResult.setResult(0);
            jsonResult.setMsg("传入的技术工序列表为空，分配失败");
            jsonResult.setData("");
            return gson.toJson(jsonResult);
        }

    }

    //第八，该项目的售前工程师，权限为24，负责该项目前期调研,获取前期调研某个控制点数据

    @RequestMapping(value = "/inputProject/24/getCollectData")
    public String getCollectData(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);

        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();

        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != 24
                || userLevel != 24) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你不是该项目的售前工程师，没有该操作的权限");
            return gson.toJson(jsonResult);
        }

        //传过来ControlPointId和ProjectId
        CollectData collectData = gson.fromJson(msg,CollectData.class);
        //if(collectData.getControlPointId()==0 && collectData.getProjectId()==0)

        List<CollectData> collectDataList = collectDataRepository.findByControlPointIdAndProjectId(collectData.getControlPointId()
        ,collectData.getProjectId());

        if(collectDataList==null||collectDataList.size()==0){

            List<Collect> collectList = collectRepository.findByControlPointId(collectData.getControlPointId());
            for(Collect collect :collectList){
                CollectData collectData1 = new CollectData();
                collectData1.setCollectionName(collect.getCollectionName());
                collectData1.setCollectionWeight(collect.getCollectionWeight());
                collectData1.setControlPointId(collect.getControlPointId());
                collectDataList.add(collectData1);

            }
            jsonResult.setResult(1);
            jsonResult.setMsg("返回控制点采集数据的名称和单位");
            jsonResult.setData(gson.toJson(collectDataList));
            return gson.toJson(jsonResult);

        }
        else {

            jsonResult.setResult(1);
            jsonResult.setMsg("返回控制点采集数据的名称和单位和数据");
            jsonResult.setData(gson.toJson(collectDataList));
            return gson.toJson(jsonResult);
        }
    }

    //第九，该项目的售前工程师，权限为24，负责该项目前期调研,上传前期调研某个控制点图片数据
    @RequestMapping(value = "/setCollectPhoto")
    public String setCollectPhoto(@RequestParam("file") MultipartFile file, @RequestParam Map<String, String> params) {
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




    //第九，该项目的售前工程师，权限为24，负责该项目前期调研,修改前期调研某个控制点数据
    @RequestMapping(value = "/inputProject/24/setCollectData")

    public String setCollectData(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);

        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();



        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != 24
                || userLevel != 24) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你不是该项目的售前工程师，没有该操作的权限");
            return gson.toJson(jsonResult);

        }

        Type listType = new TypeToken<List<CollectData>>(){}.getType();
        List<CollectData> collectDataList = gson.fromJson(msg,listType);


        collectDataRepository.save(collectDataList);

        jsonResult.setResult(1);
        jsonResult.setMsg("保存控制点采集数据成功");
        jsonResult.setData("");
        return gson.toJson(jsonResult);

    }

    //第十，该项目的售前工程师，权限为24，负责该项目前期调研，提交该工程的前期调研

    @RequestMapping(value = "/inputProject/24/SubmitResearch")
    public String SubmitResearch(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);

        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();

        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != 24
                || userLevel != 24) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你不是该项目的售前工程师，没有该操作的权限");
            return gson.toJson(jsonResult);

        }
        Project project = gson.fromJson(msg,Project.class);

        project.setResearchCompleted(true);


        if(project.getProcedureCompleted()==true && project.getPersonCompleted()==true){

            project.setCompleted(true);

        }
        jsonResult.setResult(1);
        jsonResult.setMsg("该项目前期调研完成");
        jsonResult.setData("");
        return gson.toJson(jsonResult);

    }

    //第十一，level22，23，24管理员点项目列表中的项目,通过工程列表名找到相应project,获取项目基础信息详情
    @RequestMapping(value = "/getProjectBaseDetail")
    public String getProjectBaseDetail(@RequestBody String msg) {

        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();

        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);
        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();

        if (userRepository.findByUserId(uuid) != null && (userRepository.findByUserId(uuid).getUserLevel() == 22
        && userLevel == 22)||(userRepository.findByUserId(uuid).getUserLevel() == 23
                && userLevel == 23)||(userRepository.findByUserId(uuid).getUserLevel() == 24
                && userLevel == 24)) {

            Project project = gson.fromJson(msg, Project.class);
            Project project1 = projectRepository.findByProjectName(project.getProjectName());
            if (project1 != null) {
                String ManagerName = userRepository.findByUserId(project1.getProjectManagerId()).getUserName();

                String SkillManagerName = userRepository.findByUserId(project1.getProjectSkillManagerId()).getUserName();

                String PreSaleEngineerName = userRepository.findByUserId(project1.getPreSaleEngineerId()).getUserName();

                project1.setManagerName(ManagerName);

                project1.setSkillManagerName(SkillManagerName);

                project1.setPreSaleEngineerName(PreSaleEngineerName);

                jsonResult.setResult(1);
                jsonResult.setMsg("返回该项目基础信息");
                jsonResult.setData(gson.toJson(project1));
                return gson.toJson(jsonResult);
            } else {
                jsonResult.setData("");
                jsonResult.setResult(0);
                jsonResult.setMsg("无信息");
                return gson.toJson(jsonResult);

            }
        }
        jsonResult.setResult(0);
        jsonResult.setMsg("你没有该操作的权限");
        return gson.toJson(jsonResult);
    }

}
