package com.exam.controller.useraccess;

import com.exam.Repository.NoticeRepository;
import com.exam.Repository.UserRepository;
import com.exam.model.bean.JsonRequest;
import com.exam.model.bean.JsonResult;
import com.exam.model.entity.Notice;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dell-ewtu on 2017/3/12.
 */
@RestController
@RequestMapping("/api/plan")
public class AddNoticeController {
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/addNotice")
    public String addNotice(@RequestBody String msg) {

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


        Notice newNoticeMsg = gson.fromJson(msg, Notice.class);
        noticeRepository.save(newNoticeMsg);

        jsonResult.setMsg("新增成功");
        jsonResult.setResult(1);
        return gson.toJson(jsonResult);
    }
}
