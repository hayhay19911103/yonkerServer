package com.exam.controller.frontweb;

import com.exam.Repository.DisplayImageRepository;
import com.exam.Repository.MaterialRepository;
import com.exam.model.bean.JsonResult;
import com.exam.model.entity.DisplayImage;
import com.exam.model.entity.Material;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dell-ewtu on 2017/3/20.
 */
@RestController
@RequestMapping("/api/fileManage")
public class FileManageController {
    @Autowired
    public DisplayImageRepository displayImageRepository;
    @Autowired
    public MaterialRepository materialRepository;

    //上传图片
    @RequestMapping(value = "/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();
        String currentTime = String.valueOf(System.currentTimeMillis());

        String fileName = currentTime+file.getOriginalFilename();
        DisplayImage displayImage = new DisplayImage();
        displayImage.setDisplayImageName(file.getOriginalFilename());
        displayImage.setDisplayImagePath(fileName);
        displayImage.setDisplayImageId(0);

        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File("C:/Pics/"+fileName)));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            }
            displayImageRepository.save(displayImage);
            jsonResult.setResult(1);
            jsonResult.setData(fileName);
            return gson.toJson(jsonResult);
        }
        else {
            return "上传失败，因为文件是空的。";
        }
    }

    //上传资料
    @RequestMapping(value = "/uploadMaterial")
    public String uploadMaterial(@RequestParam("file") MultipartFile file,@RequestParam Map<String, String> params) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();

        String currentTime = String.valueOf(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fileName = currentTime+file.getOriginalFilename();

        Material material = new Material();

        material.setMaterialName(file.getOriginalFilename());
        material.setPath(fileName);
        material.setMaterialId(0);
        material.setUploadTime(df.format(new Date()));
        material.setSize(String.valueOf(file.getSize()));
        material.setProjectId(Integer.parseInt(params.get("projectId")));

        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File("C:/Files/"+fileName)));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            }
            materialRepository.save(material);
            jsonResult.setResult(1);
            jsonResult.setData(fileName);
            return gson.toJson(jsonResult);
        }
        else {
            return "上传失败，因为文件是空的。";
        }
    }

    //查找图片列表
    @RequestMapping(value = "/findDisplayImage")
    public String findDisplayImage() {
        Gson gson = new Gson();
        List<DisplayImage> displayImageList = displayImageRepository.findAll();
        JsonResult jsonResult = new JsonResult();
        jsonResult.setDisplayImageList(displayImageList);
        jsonResult.setResult(1);
        jsonResult.setMsg("获取展示图片列表");
        return gson.toJson(jsonResult);
    }

    //查找资料列表
    @RequestMapping(value = "/findMaterial")
    public String findMaterial(@RequestBody String msg) {
        Gson gson = new Gson();
        Material newMaterialMsg = gson.fromJson(msg,Material.class);
        List<Material> materialList = null;
        if(newMaterialMsg.getProjectId() == 0){
            materialList = materialRepository.findAll();
        }else{
            materialList = materialRepository.findByProjectId(newMaterialMsg.getProjectId());
        }
        JsonResult jsonResult = new JsonResult();
        jsonResult.setMaterialList(materialList);
        jsonResult.setResult(1);
        jsonResult.setMsg("获取展示培训资料列表");
        return gson.toJson(jsonResult);
    }

    //删除图片
    @RequestMapping(value = "/deleteDisplayImage")
    public String deleteDisplayImage(@RequestBody String msg) {
        Gson gson = new Gson();
        DisplayImage newDisplayImageMsg = gson.fromJson(msg,DisplayImage.class);
        displayImageRepository.delete(displayImageRepository.findByDisplayImageId(newDisplayImageMsg.getDisplayImageId()));
        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(1);
        jsonResult.setMsg("删除成功！");
        return gson.toJson(jsonResult);
    }
    //删除资料
    @RequestMapping(value = "/deleteMaterial")
    public String deleteMaterial(@RequestBody String msg) {
        Gson gson = new Gson();
        Material newMaterialMsg = gson.fromJson(msg,Material.class);
        materialRepository.delete(materialRepository.findByMaterialId(newMaterialMsg.getMaterialId()));
        JsonResult jsonResult = new JsonResult();
        jsonResult.setResult(1);
        jsonResult.setMsg("删除成功！");
        return gson.toJson(jsonResult);
    }

}
