package com.exam.controller.frontweb;

import com.exam.Repository.*;
import com.exam.model.bean.JsonRequest;
import com.exam.model.bean.JsonResult;
import com.exam.model.entity.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell-ewtu on 2017/3/10.
 */
@RestController
@RequestMapping("/api/find")
public class FindController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectQualifiedRepository projectQualifiedRepository;
    @Autowired
    private ProjectPlanRepository projectPlanRepository;
    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private FieldExecuteRepository fieldExecuteRepository;
    @Autowired
    private ControlPointRepository controlPointRepository;
    @Autowired
    private CollectRepository collectRepository;
    @Autowired
    private ProjectControlPointRepository projectControlPointRepository;
    @Autowired
    private PlanProcedureRepository planProcedureRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private CollectDataRepository collectDataRepository;
    @Autowired
    private CollectPhotoRepository collectPhotoRepository;



//    //第一，获取该工程所有田块列表
//    @RequestMapping(value = "/findField")
//    public String findField(@RequestBody String msg) {
//
//        Gson gson = new Gson();
//        Project newProjectMsg = gson.fromJson(msg, Project.class);
//        int projectId = newProjectMsg.getProjectId();
//
//        List<Field> fieldList = fieldRepository.findByProjectId(projectId);
//        for (Field field : fieldList) {
//            List<FieldExecute> fieldExecuteList = fieldExecuteRepository.findByFieldId(field.getFieldId());
//            field.setFieldExecuteList(fieldExecuteList);
//        }
//        if (fieldList != null && fieldList.size() != 0) {
//            JsonResult jsonResult = new JsonResult();
//            jsonResult.setMsg("获取田块列表");
//            jsonResult.setResult(1);
//            jsonResult.setFieldList(fieldList);
//            return gson.toJson(jsonResult);
//        } else {
//            JsonResult jsonResult = new JsonResult();
//            jsonResult.setMsg("无田块信息");
//            jsonResult.setResult(0);
//            jsonResult.setFieldList(null);
//            return gson.toJson(jsonResult);
//        }
//    }

    //第二，通过方案名模糊查找方案，连带方案包含的工序一起返回
    @RequestMapping(value = "/findPlan")
    public String findProcedure(@RequestBody java.lang.String msg) {
        Gson gson = new Gson();
        Plan newUserMsg = gson.fromJson(msg, Plan.class);
        JsonResult jsonResult = new JsonResult();
        if (newUserMsg.getPlanName().equals("")) {
            Sort s = new Sort(Sort.Direction.DESC, "planId");
            List<Plan> planList = planRepository.findAll(s);
            getProcedureList(planList);
            jsonResult.setResult(1);
            jsonResult.setMsg("成功返回");
            jsonResult.setPlanList(planList);
            return gson.toJson(jsonResult);

        } else {
            Sort s = new Sort(Sort.Direction.DESC, "planId");
            List<Plan> planList = planRepository.findByPlanNameLike(newUserMsg.getPlanName() + "%", s);
            getProcedureList(planList);
            jsonResult.setResult(1);
            jsonResult.setMsg("成功返回");
            jsonResult.setPlanList(planList);
            return gson.toJson(jsonResult);
        }
    }

    private List<Plan> getProcedureList(List<Plan> planList) {
        for (int i = 0; i < planList.size(); i++) {
            int planId = planList.get(i).getPlanId();
            List<PlanProcedure> procedureList = planProcedureRepository.findByPlanId(planId);
            if (procedureList != null && procedureList.size() != 0) {
                planList.get(i).setPlanProcedureList(procedureList);
            } else {
                planList.get(i).setPlanProcedureList(null);
            }
        }
        return planList;
    }

    //第二，获取某个工序控制点名称列表

    /**
     * @param msg 通过工序id找到该工序的所有控制点
     * @return 返回该工序的控制点名称列表
     */

    @RequestMapping(value = "/findControlPoint")
    public String addMsg(@RequestBody String msg) {
        Gson gson = new Gson();
        PlanProcedure planProcedure = gson.fromJson(msg, PlanProcedure.class);

        List<ControlPoint> controlPointList = controlPointRepository.findByProcedureId(planProcedure.getProcedureId());

        JsonResult jsonResult = new JsonResult();
        jsonResult.setControlPointList(controlPointList);
        jsonResult.setResult(1);
        jsonResult.setMsg("获取控制点列表包含控制点名称");
        return gson.toJson(jsonResult);

    }

    //第三，通过控制点Id获取采集数据列表

    /**
     * @param msg controlPointId
     * @return
     */
    @RequestMapping(value = "/findCollect")
    public String findCollect(@RequestBody String msg) {
        Gson gson = new Gson();
        Collect newCollectMsg = gson.fromJson(msg, Collect.class);
        List<Collect> collectList = collectRepository.findByControlPointId(newCollectMsg.getControlPointId());
        JsonResult jsonResult = new JsonResult();
        if (collectList != null && collectList.size() != 0) {
            jsonResult.setResult(1);
            jsonResult.setMsg("获取采集数据列表");
            jsonResult.setCollectList(collectList);
            return gson.toJson(jsonResult);
        } else {
            jsonResult.setResult(1);
            jsonResult.setMsg("无采集数据列表");
            jsonResult.setCollectList(collectList);
            return gson.toJson(jsonResult);
        }
    }

//    //通过项目名查找对应工序
//    @RequestMapping(value = "/findPlanProcedure")
//    public String findPlanProcedure(@RequestBody java.lang.String msg) {
//        Gson gson = new Gson();
//        PlanProcedure newPlaProcedureMsg = gson.fromJson(msg, PlanProcedure.class);
//        JsonResult jsonResult = new JsonResult();
//        Sort s = new Sort(Sort.Direction.DESC, "planId");
//        List<PlanProcedure> planProcedureList = planProcedureRepository.findByPlanId(newPlaProcedureMsg.getPlanId());
//        jsonResult.setResult(1);
//        jsonResult.setMsg("成功返回");
//        jsonResult.setPlanProcedureList(planProcedureList);
//        return gson.toJson(jsonResult);
//    }


    //1、	项目基础信息查询 ，通过工程名模糊查找已经创建完成的工程
    @RequestMapping(value = "/findProjectBaseIoFo")
    public String findProjectBaseIoFo(@RequestBody String msg) {

        JsonResult jsonResult = new JsonResult();
        Gson gson = new Gson();
        Project newProjectMsg = gson.fromJson(msg, Project.class);
        String projectName = newProjectMsg.getProjectName();
        List<Project> projectList = null;
        List<Project> projectList1 = new ArrayList<>();
        if (projectName.equals("")) {
            projectList = projectRepository.findAll();
        } else {
            projectList = projectRepository.findByProjectNameLike(projectName + "%");
        }

        for (Project project : projectList) {
            if (project.getCreateCompleted() == true) {

                String ManagerName = userRepository.findByUserId(project.getProjectManagerId()).getUserName();
                String SkillManagerName = userRepository.findByUserId(project.getProjectSkillManagerId()).getUserName();
                String PreSaleEngineerName = userRepository.findByUserId(project.getPreSaleEngineerId()).getUserName();
                project.setManagerName(ManagerName);
                project.setSkillManagerName(SkillManagerName);
                project.setPreSaleEngineerName(PreSaleEngineerName);
                projectList1.add(project);

            }
        }
        if (projectList1 != null && projectList1.size() != 0) {

            jsonResult.setMsg("获取项目基础信息列表");
            jsonResult.setResult(1);
            jsonResult.setProjectList(projectList1);
            return gson.toJson(jsonResult);
        } else {
            jsonResult.setMsg("无项目信息");
            jsonResult.setResult(0);
            jsonResult.setProjectList(null);
            return gson.toJson(jsonResult);
        }
    }
    //2、	项目工艺路线、工序、控制点信息查询

    @RequestMapping(value = "/findProjectPlanControlPoint")
    public String findProjectPlanControlPoint(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();

        Project project = gson.fromJson(msg, Project.class);

        List<ProjectPlan> projectPlanList = projectPlanRepository.findByProjectId(project.getProjectId());

        for(ProjectPlan projectPlan:projectPlanList){
            PlanProcedure planProcedure = planProcedureRepository.findByProcedureId(projectPlan.getProcedureId());
            Plan plan = planRepository.findByPlanId(planProcedure.getPlanId());
            projectPlan.setPlanName(plan.getPlanName());



            List<ProjectControlPoint> projectControlPointList = projectControlPointRepository.findByProcedureIdAndProjectId(
                    projectPlan.getProcedureId(),projectPlan.getProjectId()
            );


            List<ProjectControlPoint> projectControlPointList1 = new ArrayList<>();
            //除去一个项目中同名工序但step不同的控制点条目
            if(projectPlan.getStep()!=0){
                for(ProjectControlPoint projectControlPoint:projectControlPointList){
                    if(projectControlPoint.getStep()==projectPlan.getStep()){
                        projectControlPointList1.add(projectControlPoint);

                    }
                }
            }
            else {

                for(ProjectControlPoint projectControlPoint:projectControlPointList){
                    if(projectControlPoint.getProStep()==projectPlan.getStep()&&
                            projectControlPoint.getNextStep()==projectPlan.getNextStep()){
                        projectControlPointList1.add(projectControlPoint);

                    }
                }

            }
            projectPlan.setProjectControlPointList(projectControlPointList1);
        }

        jsonResult.setMsg("获得项目工艺工序、控制点信息");
        jsonResult.setResult(1);
        jsonResult.setProjectPlanList(projectPlanList);
        return gson.toJson(jsonResult);

    }

    //3、	项目人员查询

    @RequestMapping(value = "/findQualified")
    public String findQualified(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();

        Project project = gson.fromJson(msg, Project.class);

        List<ProjectQualified> projectQualifiedList = projectQualifiedRepository.findByProjectId(project.getProjectId());
        if (projectQualifiedList != null && projectQualifiedList.size() != 0) {
            jsonResult.setResult(1);
            jsonResult.setMsg("获取所有技术人员列表");
            jsonResult.setProjectQualifiedList(projectQualifiedList);

        } else {
            jsonResult.setResult(0);
            jsonResult.setMsg("暂无数据");

        }
        return gson.toJson(jsonResult);

    }
    //4、 售前工程师前期调研信息查询,获取前期调研控制点数据,传过来ControlPointId和ProjectId


    @RequestMapping(value = "/findCollectData")
    public String findCollectData(@RequestBody String msg) {

        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();

        //传过来ControlPointId和ProjectId
        CollectData collectData = gson.fromJson(msg, CollectData.class);
        //if(collectData.getControlPointId()==0 && collectData.getProjectId()==0)

        List<CollectData> collectDataList = collectDataRepository.findByControlPointIdAndProjectId(collectData.getControlPointId()
                , collectData.getProjectId());

        jsonResult.setResult(1);
        jsonResult.setMsg("返回前期调研该控制点采集的数据");
        jsonResult.setCollectDataList(collectDataList);

        return gson.toJson(jsonResult);
    }

    //5、售前工程师前期调研信息查询,获取前期调研控制点图片,传过来ControlPointId和ProjectId
        @RequestMapping(value = "/findCollectPhoto")
        public String findCollectPhoto(@RequestBody String msg) {
            Gson gson = new Gson();
            JsonResult jsonResult = new JsonResult();

            //传过来ControlPointId和ProjectId
            CollectPhoto collectPhoto = gson.fromJson(msg,CollectPhoto.class);
            //if(collectData.getControlPointId()==0 && collectData.getProjectId()==0)

            List<CollectPhoto> collectPhotoList = collectPhotoRepository.findByControlPointIdAndProjectId(collectPhoto.getControlPointId()
                    ,collectPhoto.getProjectId());

            jsonResult.setResult(1);
            jsonResult.setMsg("返回前期调研该控制点采集的数据");
            jsonResult.setCollectPhotoList(collectPhotoList);


            return gson.toJson(jsonResult);

        }


    //6、 根据项目名查询该项目的所有工序，还没写工序详情中展示采集的所有数据和照片

    @RequestMapping(value = "/findProjectProcedure")
    public String findProjectProcedure(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        Project project = gson.fromJson(msg, Project.class);



        Project project1 = projectRepository.findByProjectName(project.getProjectName());

        List<ProjectPlan> projectPlanList = projectPlanRepository.findByProjectId(project1.getProjectId());
        if (projectPlanList.size() != 0 ) {
            for (ProjectPlan projectPlan:projectPlanList){
                projectPlan.getProcedureId();

            }

            jsonResult.setResult(1);
            jsonResult.setMsg("返回该项目所有工序列表");
            jsonResult.setProjectPlanList(projectPlanList);
        } else {

            jsonResult.setResult(0);
            jsonResult.setMsg("该项目没有工序列表");

        }
        return gson.toJson(jsonResult);
    }

    //7、项目田块信息查询

    @RequestMapping(value = "/findField")
    public String findField(@RequestBody String msg) {

        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();

        //映射实体类
        Project project = gson.fromJson(msg, Project.class);
        List<Field> fieldList = fieldRepository.findByProjectId(project.getProjectId());
        if (fieldList != null && fieldList.size() != 0) {

            jsonResult.setResult(1);
            jsonResult.setMsg("返回田块列表");
            jsonResult.setFieldList(fieldList);

        } else {
            jsonResult.setResult(0);
            jsonResult.setMsg("返回田块列表为空");
        }
        return gson.toJson(jsonResult);
    }

}
