package com.exam.controller.androidloginpart;

import com.exam.Repository.DisplayImageRepository;
import com.exam.Repository.UserDetailRepository;
import com.exam.Repository.UserRepository;
import com.exam.model.bean.JsonRequest;
import com.exam.model.bean.JsonResult;
import com.exam.model.entity.DisplayImage;
import com.exam.model.entity.User;
import com.exam.model.entity.UserDetail;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理员登陆与完善信息接口
 */

@RestController
@RequestMapping("/api/admin")
public class AdministratorLoginController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailRepository userDetailRepository;
    @Autowired
    public DisplayImageRepository displayImageRepository;

    /**
     * 管理员用户登陆 政府管理员使用用户名登陆，其他用手机号登陆
     * @param msg
     * @return
     */
    @RequestMapping(value = "/login")
    public String administratorLogin(@RequestBody String msg) {


        //从安卓端接收JsonRequest数据格式，并获取其中的String data 重新赋值给msg
        Gson gson = new Gson();
        JsonRequest jsonRequest = gson.fromJson(msg,JsonRequest.class);
        msg = jsonRequest.getData();
        //


        JsonResult jsonResult = new JsonResult();
        User user = gson.fromJson(msg, User.class);

        //必须传入下面中的一个值，政府管理员传用户名，其他管理员传手机号
        String userPhone = user.getUserPhone();
        String userName = user.getUserName();

        //防止空指针错误
        if (userPhone != null) {
            User user1 = userRepository.findByUserPhone(userPhone);
            //数据库查询数据操作
            if (user1 != null) {

                if (user1.getUserPwd().equals(user.getUserPwd())) {
                    jsonResult.setResult(1);
                    jsonResult.setMsg("管理员登陆成功");
                    jsonResult.setData(gson.toJson(user1));//返回管理员用户的level和是否完善用户资料
                } else {
                    jsonResult.setResult(0);
                    jsonResult.setMsg("登陆失败，密码错误");
                }
            } else {
                jsonResult.setResult(0);
                jsonResult.setMsg("登陆失败，手机号不存在");

            }
        }

        //防止空指针错误
        if (userName != null) {

            User user2 = userRepository.findByUserName(userName);
            if (user2 != null) {
                if (user2.getUserPwd().equals(user.getUserPwd())) {

                    jsonResult.setResult(1);
                    jsonResult.setMsg("政府管理员登陆成功");
                    jsonResult.setData(gson.toJson(user2));//返回管理员用户的level和是否完善用户资料


                } else {
                    jsonResult.setResult(0);
                    jsonResult.setMsg("登陆失败，密码错误");
                }

            } else {
                jsonResult.setResult(0);
                jsonResult.setMsg("登陆失败，用户名不存在");

            }
        }
        return gson.toJson(jsonResult);
    }

    /**
     * 管理员用户完善信息
     *
     * @param msg 必须传userId 以及用户信息
     * @return
     */
    //完善资料
    @RequestMapping(value = "/completeInFo", method = RequestMethod.POST)
    public String administratorInFo(@RequestBody String msg) {

        //从安卓端接收JsonRequest数据格式，并获取其中的String data 重新赋值给msg
        Gson gson = new Gson();
        JsonRequest jsonRequest = gson.fromJson(msg,JsonRequest.class);
        msg = jsonRequest.getData();
        //

        JsonResult jsonResult = new JsonResult();
        //拿到data中的数据
        User user = gson.fromJson(msg,User.class);
        //这里采用列表方式存入userDetail数据
//        UserDetail userDetail = gson.fromJson(msg, UserDetail.class);

        UserDetail userDetail = user.getUserDetail();
        User user1 = userRepository.findByUserId(user.getUserId());
        user1.setUserName(user.getUserName());
        user1.setFirst(false);
        user1.setUserPwd(user.getUserPwd());


        userRepository.save(user1);
        //如果userDetail表中存在该记录，修改，否则新增

        if(userDetailRepository.findByUserId(user.getUserId())!=null){
            UserDetail userDetail1 = userDetailRepository.findByUserId(user.getUserId());
            userDetail1.setUserNumber(userDetail.getUserNumber());
            userDetail1.setUserDepartment(userDetail.getUserDepartment());
//            userDetail1.setUserIdentification(userDetail.getUserIdentification());
//            userDetail1.setUserSex(userDetail.getUserSex());
            userDetail1.setUserStation(userDetail.getUserStation());
            userDetailRepository.save(userDetail1);
        }else{
            userDetailRepository.save(userDetail);
        }

        jsonResult.setResult(1);
        jsonResult.setMsg("完善资料成功");
        return gson.toJson(jsonResult);
    }
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public String getUserInfo(@RequestBody String msg) {
        Gson gson = new Gson();
        JsonResult jsonResult = new JsonResult();

        JsonRequest jsonRequest = gson.fromJson(msg,JsonRequest.class);
        String uuid = jsonRequest.getUserId();
        int userLevel = jsonRequest.getUserLevel();
        msg = jsonRequest.getData();

        if(userRepository.findByUserId(uuid) ==null || userRepository.findByUserId(uuid).getUserLevel()!=userLevel){
            jsonResult.setResult(0);
            jsonResult.setMsg("你没有该操作的权限");
            return gson.toJson(jsonResult);
        }

        User user = userRepository.findByUserId(uuid);
        UserDetail userDetail = userDetailRepository.findByUserId(uuid);
        user.setUserDetail(userDetail);
        jsonResult.setMsg("获取用户具体信息");
        jsonResult.setData(gson.toJson(user));
        jsonResult.setResult(1);
        return gson.toJson(jsonResult);
    }
    //管理员获取主页展示图片列表
    @RequestMapping(value = "/findImages")
    public String findImages() {
        Gson gson = new Gson();
        List<DisplayImage> displayImageList = displayImageRepository.findAll();
        List<String> imagPathList = new ArrayList<String>();
        for(DisplayImage displayImage:displayImageList){
            imagPathList.add(displayImage.getDisplayImagePath());
        }
        JsonResult jsonResult = new JsonResult();
        jsonResult.setData(gson.toJson(imagPathList));
        jsonResult.setResult(1);
        jsonResult.setMsg("获取展示图片列表");
        return gson.toJson(jsonResult);
    }
}
