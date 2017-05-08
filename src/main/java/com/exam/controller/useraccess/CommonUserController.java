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

import java.util.List;

/**
 * Created by mac on 2017/3/21.
 */

@RestController
@RequestMapping("/api/commonUser")
public class CommonUserController {

    @Autowired
    private IntroduceRepository introduceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private industryStatusRepository industryStatusRepository;
    @Autowired
    private IndustryContentRepository industryContentRepository;
    @Autowired
    private RepairSituationRepository repairSituationRepository;
    @Autowired
    private RepairContentRepository repairContentRepository;

    //第一，永清简介,返回图片路径与文本内容
    @RequestMapping(value = "/getIntroduce")
    public String getIntroduce(@RequestBody String msg){

        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);
        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();

        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != userLevel) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你没有该操作的权限");
            return gson.toJson(jsonResult);
        }

        List<Introduce> introduceList = introduceRepository.findAll();
        jsonResult.setResult(1);
        jsonResult.setMsg("返回永清简介信息列表");

        jsonResult.setData(gson.toJson(introduceList));
        return gson.toJson(jsonResult);



    }

    //第二，行业内现状

    @RequestMapping(value = "/getIndustryStatus")

    public String getIndustryStatus(@RequestBody String msg){
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);
        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();

        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != userLevel) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你没有该操作的权限");
            return gson.toJson(jsonResult);
        }


        List<IndustryStatus> industryStatusList = industryStatusRepository.findAll();

        jsonResult.setResult(1);
        jsonResult.setMsg("返回行业内现状列表");
        jsonResult.setData(gson.toJson(industryStatusList));
        return gson.toJson(jsonResult);

    }

    //第三，行业现状内容

    @RequestMapping(value = "/getIndustryContent")
    public String getIndustryContent(@RequestBody String msg){
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);
        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();

        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != userLevel) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你没有该操作的权限");
            return gson.toJson(jsonResult);
        }

        //传过来professionId;
        Integer professionId = gson.fromJson(msg,Integer.class);
        List<IndustryContent> industryContentList = industryContentRepository.findByProfessionId(professionId);
        jsonResult.setResult(1);
        jsonResult.setMsg("返回该现状内容列表");
        jsonResult.setData(gson.toJson(industryContentList));
        return gson.toJson(jsonResult);
    }

    //第四，修复案例现状

    @RequestMapping(value = "/getRepairSituation")
    public String getRepairSituation(@RequestBody String msg){
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);
        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();

        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != userLevel) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你没有该操作的权限");
            return gson.toJson(jsonResult);
        }


        List<RepairSituation> repairSituationList = repairSituationRepository.findAll();
        jsonResult.setResult(1);
        jsonResult.setMsg("返回修复案例现状列表");
        jsonResult.setData(gson.toJson(repairSituationList));
        return gson.toJson(jsonResult);

    }



    //第五，修复案例内容

    @RequestMapping(value = "/getRepairContent")
    public String getRepairContent(@RequestBody String msg){

        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);
        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();

        if (userRepository.findByUserId(uuid) == null || userRepository.findByUserId(uuid).getUserLevel() != userLevel) {
            jsonResult.setResult(0);
            jsonResult.setMsg("你没有该操作的权限");
            return gson.toJson(jsonResult);
        }

        //传过来situationId;
        Integer situationId = gson.fromJson(msg,Integer.class);
        List<RepairContent> contentList = repairContentRepository.findBySituationId(situationId);
        jsonResult.setResult(1);
        jsonResult.setMsg("返回该修复案例内容列表");
        jsonResult.setData(gson.toJson(contentList));
        return gson.toJson(jsonResult);
    }


}
