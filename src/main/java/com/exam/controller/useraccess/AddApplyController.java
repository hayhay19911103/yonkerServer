package com.exam.controller.useraccess;


import com.exam.Repository.ApplyRepository;
import com.exam.Repository.FieldRepository;
import com.exam.Repository.ProjectRepository;
import com.exam.Repository.UserRepository;
import com.exam.model.bean.JsonRequest;
import com.exam.model.bean.JsonResult;
import com.exam.model.entity.Apply;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dell-ewtu on 2017/3/12.
 */
//传给我applyFromId(提交申请者)，applyToId(田块创建者)，fieldId，fieldName,fieldPlanId，isPass(传0)，procedureName,projectId,projectName
@RestController
@RequestMapping("/api/plan")
public class AddApplyController {
    @Autowired
    private ApplyRepository applyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private ProjectRepository projectRepository;



    @RequestMapping(value = "/addApply")
    public String addApply(@RequestBody String msg) {
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

        Apply newApplyMsg = gson.fromJson(msg, Apply.class);
        newApplyMsg.setApplyToName(userRepository.findByUserId(newApplyMsg.getApplyToId()).getUserName());
        newApplyMsg.setApplyFromName(userRepository.findByUserId(newApplyMsg.getApplyFromId()).getUserName());
        applyRepository.save(newApplyMsg);

        jsonResult.setMsg("新增成功");
        jsonResult.setResult(1);
        return gson.toJson(jsonResult);
    }
}
