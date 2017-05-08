package com.exam.controller.frontweb;

import com.exam.Repository.CollectRepository;
import com.exam.Repository.ControlPointRepository;
import com.exam.Repository.PlanProcedureRepository;
import com.exam.Repository.PlanRepository;
import com.exam.model.bean.JsonResult;
import com.exam.model.entity.Collect;
import com.exam.model.entity.ControlPoint;
import com.exam.model.entity.Plan;
import com.exam.model.entity.PlanProcedure;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dell-ewtu on 2017/3/8.
 */
@RestController
@RequestMapping("/api/add")
public class AddController {
    @Autowired
    private ControlPointRepository controlPointRepository;
    @Autowired
    private CollectRepository collectRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private PlanProcedureRepository planProcedureRepository;

    //第一，新增某个控制点的采集数据的名称和单位

    /**
     * @param msg 控制点id:controlPointId
     * @return
     */
    @RequestMapping(value = "/addCollect")
    public String addCollect(@RequestBody String msg) {
        Gson gson = new Gson();
        Collect newCollectMsg = gson.fromJson(msg, Collect.class);
        JsonResult jsonResult = new JsonResult();

        List<Collect> collectList = collectRepository.findByControlPointId(newCollectMsg.getControlPointId());
        for (Collect collect : collectList) {
            if (newCollectMsg.getCollectionName().equals(collect.getCollectionName())) {
                jsonResult.setResult(0);
                jsonResult.setMsg("该采集控制点的采集数据的名称已存在！请换名");
                return gson.toJson(jsonResult);
            }
        }
        collectRepository.save(newCollectMsg);
        jsonResult.setResult(1);
        jsonResult.setMsg("新增成功");
        return gson.toJson(jsonResult);
    }

    //第二，新增方案

    /**
     * @param msg 方案名：planName
     * @return
     */

    @RequestMapping(value = "/addPlan")
    public String addPlan(@RequestBody String msg) {
        Gson gson = new Gson();
        Plan plan = gson.fromJson(msg, Plan.class);
        JsonResult jsonResult = new JsonResult();
        if (planRepository.findByPlanName(plan.getPlanName()) == null) {
            planRepository.save(plan);
            jsonResult.setResult(1);
            jsonResult.setMsg("新建方案成功");
            jsonResult.setPlan(plan);
            return gson.toJson(jsonResult);
        } else {
            jsonResult.setResult(0);
            jsonResult.setMsg("方案名已存在");
            return gson.toJson(jsonResult);
        }
    }

    //第三，新增工序

    /**
     * @param msg 必须传入 planId , isSerial , procedure 参数
     * @return
     */
    @RequestMapping(value = "/newPlanProcedure")
    public String addProcedure(@RequestBody String msg) {
        Gson gson = new Gson();
        PlanProcedure newProcedureMsg = gson.fromJson(msg, PlanProcedure.class);
        JsonResult jsonResult = new JsonResult();
        if (planProcedureRepository.findByProcedureName(newProcedureMsg.getProcedureName()) != null) {
            jsonResult.setMsg("工序名已存在，请重新输入");
            jsonResult.setResult(0);
            return gson.toJson(jsonResult);
        } else {
            planProcedureRepository.save(newProcedureMsg);
            jsonResult.setResult(1);
            jsonResult.setMsg("新增成功");
            return gson.toJson(jsonResult);
        }

    }

    //第四，新增以及编辑控制点接口，新增控制点只需新增控制点名称

    /**
     *
     * @param msg 传入procedureId与控制点名称，控制点名称可以重名
     * @return
     */

    @RequestMapping(value = "/addAndEditControlPoint")
    public String addControlPointMsg(@RequestBody String msg) {
        JsonResult jsonResult = new JsonResult();
        Gson gson = new Gson();
        ControlPoint newUserMsg = gson.fromJson(msg, ControlPoint.class);

        if (newUserMsg.getControlPointId() != null) {

            ControlPoint controlPoint = controlPointRepository.findByControlPointId(newUserMsg.getControlPointId());

            controlPoint.setControlPointName(newUserMsg.getControlPointName());

            controlPointRepository.save(controlPoint);
            jsonResult.setResult(1);
            jsonResult.setMsg("编辑控制点成功");
            return gson.toJson(jsonResult);

        } else {
            controlPointRepository.save(newUserMsg);
            jsonResult.setResult(1);
            jsonResult.setMsg("新增控制点成功");
            return gson.toJson(jsonResult);
        }
    }
}
