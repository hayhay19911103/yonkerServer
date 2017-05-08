package com.exam.controller.useraccess;

import com.exam.Repository.*;
import com.exam.model.bean.JsonRequest;
import com.exam.model.bean.JsonResult;
import com.exam.model.entity.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2017/3/8.
 */

@RestController
@RequestMapping("/api/creator")
public class CreatorController {
    @Autowired
    FieldRepository fieldRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectPlanRepository projectPlanRepository;
    @Autowired
    private ProjectQualifiedRepository projectQualifiedRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FieldExecuteRepository fieldExecuteRepository;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private ApplyRepository applyRepository;
    @Autowired
    private FieldPlanRepository fieldPlanRepository;
    @Autowired
    private PlanProcedureRepository planProcedureRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private ControlPointRepository controlPointRepository;


    //第二，获取项目列表
    @RequestMapping(value = "/getProjectList")
    public String managerGetProjectList(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);
        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();
        if (userRepository.findByUserId(uuid) != null && ((userRepository.findByUserId(uuid).getUserLevel() == 21
                && userLevel == 21) || (userRepository.findByUserId(uuid).getUserLevel() == 22
                && userLevel == 22) || (userRepository.findByUserId(uuid).getUserLevel() == 23
                && userLevel == 23) || (userRepository.findByUserId(uuid).getUserLevel() == 24
                && userLevel == 24))) {
            //实际只需要项目创建者userId;
            //String userId = gson.fromJson(msg,String.class);
            List<Project> projectList = new ArrayList<Project>();
            switch (userLevel) {
                case 21:
                    projectList = projectRepository.findByBaseInFoManagerId(uuid);
                case 22:
                    projectList = projectRepository.findByProjectManagerId(uuid);
                case 23:
                    projectList = projectRepository.findByProjectSkillManagerId(uuid);
                case 24:
                    projectList = projectRepository.findByPreSaleEngineerId(uuid);
            }
            if (projectList != null && projectList.size() != 0) {
                List<Project> projectList1 = new ArrayList<Project>();
                for (Project project : projectList) {
                    if (project.getCreateCompleted()) {
                        Project project1 = new Project();
                        project1.setProjectId(project.getProjectId());
                        project1.setProjectName(project.getProjectName());
                        project1.setProjectStartTime(project.getProjectStartTime());
                        project1.setCompleted(project.getCompleted());
                        projectList1.add(project1);
                    }
                }
                jsonResult.setResult(1);
                jsonResult.setMsg("获取项目列表成功");
                jsonResult.setData(gson.toJson(projectList1));
                return gson.toJson(jsonResult);
            } else {
                jsonResult.setResult(0);
                jsonResult.setMsg("该用户没有项目列表");
                jsonResult.setData("");
                return gson.toJson(jsonResult);
            }
        }
        jsonResult.setResult(0);
        jsonResult.setMsg("你没有操作权限");
        jsonResult.setData("");
        return gson.toJson(jsonResult);
    }

    //第三，点击项目列表中的项目，获取项目详情
    @RequestMapping(value = "/getProjectDetail")
    public String getProjectDetail(@RequestBody String msg) {

        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();

        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);
        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();


        if (userRepository.findByUserId(uuid) == null || ((userRepository.findByUserId(uuid).getUserLevel() != 21||userLevel
                !=21)
                && (userRepository.findByUserId(uuid).getUserLevel() != 22 ||userLevel
                !=22)&&
                (userRepository.findByUserId(uuid).getUserLevel() != 23 ||userLevel
                        !=23)&& (userRepository.findByUserId(uuid).getUserLevel() != 24||userLevel
                !=23))) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你没有该操作的权限");
            return gson.toJson(jsonResult);
        }
        Project project = gson.fromJson(msg, Project.class);

        //通过工程列表名找到相应project
        Project project1 = projectRepository.findByProjectName(project.getProjectName());

        if (project1 != null) {
            List<ProjectPlan> projectPlanList = projectPlanRepository.findByProjectId(project1.getProjectId());
            project1.setProjectPlanList(projectPlanList);
            List<ProjectQualified> projectQualifiedList = projectQualifiedRepository.findByProjectId(project1.getProjectId());
            project1.setProjectQualifiedList(projectQualifiedList);


            jsonResult.setResult(1);
            jsonResult.setMsg("返回该工程详细信息");
            jsonResult.setData(gson.toJson(project1));
            return gson.toJson(jsonResult);
        } else {
            jsonResult.setResult(0);
            jsonResult.setMsg("无信息");
            jsonResult.setData("");
            return gson.toJson(jsonResult);

        }
    }

    //第四，获取该项目的田块列表
    @RequestMapping(value = "getFieldList")
    public String getFieldList(@RequestBody String msg) {

        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();

        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);
        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();


        if (userRepository.findByUserId(uuid) == null || ((userRepository.findByUserId(uuid).getUserLevel() != 21||userLevel
                !=21)
                && (userRepository.findByUserId(uuid).getUserLevel() != 22 ||userLevel
                !=22)&&
                (userRepository.findByUserId(uuid).getUserLevel() != 23 ||userLevel
                        !=23)&& (userRepository.findByUserId(uuid).getUserLevel() != 24||userLevel
                !=23))) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你没有该操作的权限");
            return gson.toJson(jsonResult);
        }
        //映射实体类
        Project project = gson.fromJson(msg, Project.class);
        List<Field> fieldList = fieldRepository.findByProjectId(project.getProjectId());
        if (fieldList != null && fieldList.size() != 0) {
            for (Field field : fieldList) {
                List<FieldPlan> fieldPlanList = fieldPlanRepository.findByFieldIdAndIsCompleted(field.getFieldId(), false);
                if (fieldPlanList.size() == 0) {
                    field.setCompleted(true);


                } else {
                    field.setCompleted(false);
                }
            }
            fieldList = fieldRepository.findByProjectId(project.getProjectId());

            jsonResult.setResult(1);
            jsonResult.setMsg("返回田块列表");
            jsonResult.setData(gson.toJson(fieldList));
            return gson.toJson(jsonResult);
        } else {
            jsonResult.setResult(0);
            jsonResult.setMsg("返回田块列表为空");
            jsonResult.setData("");
            return gson.toJson(jsonResult);
        }
    }


    //第六，获取田块详情
    @RequestMapping(value = "getFieldDetail")
    public String getFieldDetail(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();

        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);
        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();

        if (userRepository.findByUserId(uuid) == null || ((userRepository.findByUserId(uuid).getUserLevel() != 21||userLevel
                !=21)
                && (userRepository.findByUserId(uuid).getUserLevel() != 22 ||userLevel
                !=22)&&
                (userRepository.findByUserId(uuid).getUserLevel() != 23 ||userLevel
                        !=23)&& (userRepository.findByUserId(uuid).getUserLevel() != 24||userLevel
                !=23))) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你没有该操作的权限");
            return gson.toJson(jsonResult);
        }
        //映射实体类
        Field newFieldMsg = gson.fromJson(msg, Field.class);
        Field field = fieldRepository.findByFieldId(newFieldMsg.getFieldId());
        List<FieldExecute> fieldExecuteList = fieldExecuteRepository.findByFieldId(newFieldMsg.getFieldId());
        field.setFieldExecuteList(fieldExecuteList);
        jsonResult.setData(gson.toJson(field));
        jsonResult.setResult(1);
        return gson.toJson(jsonResult);
    }

    //第七，项目创建者获取公告列表
    @RequestMapping(value = "getNoticeList")
    public String getNoticeList(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);
        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        if (userRepository.findByUserId(uuid) == null || ((userRepository.findByUserId(uuid).getUserLevel() != 21||userLevel
                !=21)
                && (userRepository.findByUserId(uuid).getUserLevel() != 22 ||userLevel
                !=22)&&
                (userRepository.findByUserId(uuid).getUserLevel() != 23 ||userLevel
                        !=23)&& (userRepository.findByUserId(uuid).getUserLevel() != 24||userLevel
                !=23))) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你没有该操作的权限");
            return gson.toJson(jsonResult);
        }

        List<Project> projectList = new ArrayList<Project>();
        switch (userLevel) {
            case 21:
                projectList = projectRepository.findByBaseInFoManagerId(uuid);
            case 22:
                projectList = projectRepository.findByProjectManagerId(uuid);
            case 23:
                projectList = projectRepository.findByProjectSkillManagerId(uuid);
            case 24:
                projectList = projectRepository.findByPreSaleEngineerId(uuid);
        }

        if (projectList != null && projectList.size() != 0) {
            for (Project project : projectList) {
                if (!project.getCreateCompleted()) {
                   projectList.remove(project);
                }
            }
        }




        List<Notice> noticeList = new ArrayList<Notice>();
        for (Project project : projectList) {
            noticeList.addAll(noticeRepository.findByProjectId(project.getProjectId()));
        }
        if (noticeList.size() != 0) {
            jsonResult.setResult(1);
            jsonResult.setData(gson.toJson(noticeList));
            return gson.toJson(jsonResult);
        } else {
            jsonResult.setResult(0);
            jsonResult.setMsg("无相关公告！");
            jsonResult.setData("");
            return gson.toJson(jsonResult);
        }
    }

    //第八，项目创建者获取审批列表
    @RequestMapping(value = "getApplyList")
    public String getApplyList(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);
        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        if (userRepository.findByUserId(uuid) == null || ((userRepository.findByUserId(uuid).getUserLevel() != 21||userLevel
                !=21)
                && (userRepository.findByUserId(uuid).getUserLevel() != 22 ||userLevel
                !=22)&&
                (userRepository.findByUserId(uuid).getUserLevel() != 23 ||userLevel
                        !=23)&& (userRepository.findByUserId(uuid).getUserLevel() != 24||userLevel
                !=23))) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你没有该操作的权限");
            return gson.toJson(jsonResult);
        }
        List<Project> projectList = projectRepository.findByProjectManagerId(uuid);
        List<Apply> applyList = new ArrayList<Apply>();
        for (Project project : projectList) {
            List<Apply> applyList1 = applyRepository.findByProjectId(project.getProjectId());
            applyList.addAll(applyList1);
        }
        if (applyList.size() != 0) {
            jsonResult.setResult(1);
            jsonResult.setMsg("获取审批列表");
            jsonResult.setData(gson.toJson(applyList));
            return gson.toJson(jsonResult);
        } else {
            jsonResult.setResult(0);
            jsonResult.setData("");
            jsonResult.setMsg("无相关审批！");
            return gson.toJson(jsonResult);
        }
    }
}
