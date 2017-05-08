package com.exam.controller.useraccess;

import com.exam.Repository.UserRepository;
import com.exam.model.bean.JsonRequest;
import com.exam.model.bean.JsonResult;
import com.exam.model.entity.User;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by mac on 2017/3/23.
 */
public class FindExecutorListController {


    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/findManager")
    public String findManager(@RequestBody String msg) {
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

        List<User> userList = userRepository.findByUserLevel(3);
        if (userList != null && userList.size() != 0) {
            jsonResult.setResult(1);
            jsonResult.setMsg("获取所有实施者列表");
            jsonResult.setData(gson.toJson(userList));

        } else {
            jsonResult.setResult(0);
            jsonResult.setMsg("暂无数据");
            jsonResult.setData("");
        }
        return gson.toJson(jsonResult);

    }
}
