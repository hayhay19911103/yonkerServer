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
 * Created by dell-ewtu on 2017/3/9.
 */
@RestController
@RequestMapping("/api/delete")

public class DeleteController {
    @Autowired
    private CollectRepository collectRepository;
    @Autowired
    private ControlPointRepository controlPointRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private PlanProcedureRepository procedureRepository;


    //第一，删除收集的数据
    /**
     *
     * @param msg
     * @return
     */
    @RequestMapping(value = "/deleteCollect")
    public String deleteCollect(@RequestBody String msg) {
        Gson gson = new Gson();
        Collect newCollectMsg = gson.fromJson(msg, Collect.class);
        JsonResult jsonResult = new JsonResult();
        collectRepository.delete(newCollectMsg);
        jsonResult.setMsg("删除成功");
        jsonResult.setResult(1);
        return gson.toJson(jsonResult);
    }

    //第二，删除控制点
    /**
     *
     * @param msg
     * @return
     */

    @RequestMapping(value = "/deleteControlPoint")
    public String addMsg(@RequestBody String msg) {
        Gson gson= new Gson();
        ControlPoint newPlanMsg = gson.fromJson(msg, ControlPoint.class);
        Integer controlPointId = newPlanMsg.getControlPointId();
        ControlPoint controlPoint = controlPointRepository.findByControlPointId(controlPointId);
        controlPointRepository.delete(controlPoint);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setMsg("删除成功");
        jsonResult.setResult(1);
        return gson.toJson(jsonResult);
    }


    //第三，删除方案

    /**
     *
     * @param msg
     * @return
     */

    @RequestMapping(value = "/deletePlan")
    public String deletePlan(@RequestBody String msg) {
        Gson gson= new Gson();
        JsonResult jsonResult = new JsonResult();
        Plan newPlanMsg = gson.fromJson(msg, Plan.class);
        List<PlanProcedure> procedureList = procedureRepository.findByPlanId(newPlanMsg.getPlanId());
        for(int i = 0;i < procedureList.size();i++){
            procedureRepository.delete(procedureList.get(i));
        }
        planRepository.delete(newPlanMsg.getPlanId());
        jsonResult.setResult(1);
        jsonResult.setMsg("删除成功");
        return gson.toJson(jsonResult);
    }

    //第四，删除某个方案的工序

    /**
     *
     * @param msg
     * @return
     */

    @RequestMapping(value = "/deleteProcedure")
    public String deleteProcedure(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        PlanProcedure newCollectMsg = gson.fromJson(msg, PlanProcedure.class);
        procedureRepository.delete(newCollectMsg.getProcedureId());
        jsonResult.setMsg("删除成功");
        jsonResult.setResult(1);
        return gson.toJson(jsonResult);
    }

}
