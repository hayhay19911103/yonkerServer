package com.exam.controller.useraccess;

import com.exam.Repository.MaterialRepository;
import com.exam.Repository.UserRepository;
import com.exam.model.bean.JsonRequest;
import com.exam.model.bean.JsonResult;
import com.exam.model.entity.Material;
import com.exam.model.entity.Project;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell-ewtu on 2017/4/19.
 */
@RestController
@RequestMapping("/api/download")
public class DownloadFileController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @RequestMapping(value = "/addApply", method = RequestMethod.GET)
    public void addApply(HttpServletResponse res) throws IOException{
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=1490018252324fm.docx");
        File file = new File("C://Files//1490018252324fm.docx");
        FileOutputStream fos = new FileOutputStream(file);
        res.setContentLengthLong(file.length());
        fos.close();
    }

    @RequestMapping(value = "/findFileList")
    public String addApply(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        JsonRequest jsonRequest = gson.fromJson(msg, JsonRequest.class);
        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();
        List<Material> materialList = new ArrayList<Material>();
        if (userRepository.findByUserId(uuid) != null && ((userRepository.findByUserId(uuid).getUserLevel() == 21
                && userLevel == 21) || (userRepository.findByUserId(uuid).getUserLevel() == 22
                && userLevel == 22) || (userRepository.findByUserId(uuid).getUserLevel() == 23
                && userLevel == 23) || (userRepository.findByUserId(uuid).getUserLevel() == 24
                && userLevel == 24) || (userRepository.findByUserId(uuid).getUserLevel() == 3
                && userLevel == 3))){
            Project project = gson.fromJson(msg,Project.class);
            if(project.getProjectId()!=null){
                materialList = materialRepository.findByProjectId(project.getProjectId());
            }
            if(materialList!=null && materialList.size()>0){
                jsonResult.setData(gson.toJson(materialList));
            }else {
                jsonResult.setData("");
            }
            jsonResult.setMsg("获取资料列表");
            jsonResult.setResult(1);
            return gson.toJson(jsonResult);
        }else{
            jsonResult.setMsg("您无权限查看培训资料");
            jsonResult.setResult(0);
            return gson.toJson(jsonResult);
        }
    }
}
