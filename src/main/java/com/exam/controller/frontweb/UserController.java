package com.exam.controller.frontweb;

import com.exam.Repository.UserDetailRepository;
import com.exam.Repository.UserRepository;
import com.exam.common.PublicFuzzy;
import com.exam.model.bean.JsonResult;
import com.exam.model.entity.User;
import com.exam.model.entity.UserDetail;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2017/2/27.
 * 前端管理系统     各种用户的数据的增删改查
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailRepository userDetailRepository;

    /**
     * 只要求新增管理员用户信息
     *
     * 政府管理员参数用用户名及密码
     * 其他3种管理员参数用手机号以及密码
     *
     * 土地管理员(level 6)和普通用户用手机添加(level 1)
     * 项目实施者与技术员(level 3)
     * 政府管理员(level 4)
     * 宣传管理员(level 5)
     * 项目创建者拆分成四种权限：基础信息录入人员(level 21)、项目负责人、(level 22)项目技术负责人(level 23)
     * 和售前工程师(level 24)

     * @param msg 手机号以及密码或者用户名及密码
     * @return
     */
    @RequestMapping(value = "/addUserMsg")
    public String addMsg(@RequestBody String msg) {
        Gson gson = new Gson();
        User newUserMsg = gson.fromJson(msg, User.class);

        JsonResult jsonResult = new JsonResult();
        if (newUserMsg.getUserPhone()!=null) {
            if ((userRepository.findByUserPhone(newUserMsg.getUserPhone()) == null)) {

                newUserMsg.setFirst(true);//设置没有完善用户资料
                newUserMsg.setUserLevel(newUserMsg.getUserLevel());
                userRepository.save(newUserMsg);
                jsonResult.setResult(1);
                jsonResult.setMsg("保存成功");


                return gson.toJson(jsonResult);
            } else {
                jsonResult.setResult(0);
                jsonResult.setMsg("保存失败,该手机号用户已存在");
                return gson.toJson(jsonResult);

            }
        } else {
            if (newUserMsg.getUserName()!=null) {
                if ((userRepository.findByUserName(newUserMsg.getUserName()) == null)) {
                    newUserMsg.setFirst(true);//设置没有完善用户资料
                    newUserMsg.setUserLevel(newUserMsg.getUserLevel());//设置管理员权限类别
                    userRepository.save(newUserMsg);
                    jsonResult.setResult(1);
                    jsonResult.setMsg("保存政府管理员成功");
                    return gson.toJson(jsonResult);

                } else {
                    jsonResult.setResult(0);
                    jsonResult.setMsg("保存失败,该用户名已存在");
                    return gson.toJson(jsonResult);
                }
            }
        }
        jsonResult.setResult(0);
        jsonResult.setMsg("请输入用户名或者手机号");
        return gson.toJson(jsonResult);
    }

    /**
     *
     * @param msg 用户userId
     * @return
     */
    //删除管理员用户，删除普通用户
    @RequestMapping(value = "/deleteUser")
    public String deleteUser(@RequestBody String msg) {
        Gson gson = new Gson();
        User user = gson.fromJson(msg, User.class);
        JsonResult jsonResult = new JsonResult();

        User user1 = userRepository.findByUserId(user.getUserId());
        if (user1!=null) {
            UserDetail userDetail = userDetailRepository.findByUserId(user.getUserId());

            if(userDetail==null) {
                userRepository.delete(user1);
                jsonResult.setResult(1);
                jsonResult.setMsg("删除用户成功");
                return gson.toJson(jsonResult);
            }
            else {
                userDetailRepository.delete(userDetail);
                userRepository.delete(user1);
                jsonResult.setResult(1);
                jsonResult.setMsg("删除用户成功");
                return gson.toJson(jsonResult);
            }

        } else {
            jsonResult.setResult(0);
            jsonResult.setMsg("没有该用户");
            return gson.toJson(jsonResult);
        }
    }
    //编辑普通用户

    /**
     *
     * @param msg       userId
     * @return
     */
    @RequestMapping(value = "/editUser")
    public String editUser(@RequestBody String msg){

        JsonResult jsonResult = new JsonResult();
        Gson gson = new Gson();
        User user =gson.fromJson(msg,User.class);

        //UserDetail userDetail = gson.fromJson(msg,UserDetail.class);
        User user1 = userRepository.findByUserId(user.getUserId());
        if(user1!=null){
            userRepository.save(user);
            //userDetailRepository.save(userDetail);
            //System.out.println(gson.toJson(user));
            //System.out.println(gson.toJson(userDetail));
            jsonResult.setResult(1);
            jsonResult.setMsg("编辑成功");
        }
        else {
            jsonResult.setResult(0);
            jsonResult.setMsg("不存在该用户，不能编辑");

        }
        return gson.toJson(jsonResult);
    }

    /**
     * 查询用户
     * @param msg 传入userName userPhone userLevel
     * @return
     */
    @RequestMapping(value = "/fuzzy")
    public String fuzzy(@RequestBody String msg) {
        Gson gson = new Gson();
        User newUser = gson.fromJson(msg, User.class);
        JsonResult jsonResult = new JsonResult();
        List<User> userList1 = new ArrayList<User>();
        //先对所有类别用户做用户名和用户手机号的模糊搜索
        PublicFuzzy publicFuzzy = new PublicFuzzy();



        List<User> userList = publicFuzzy.fuzzyQuery(newUser, userRepository);
        //再用依据level选出具体类别的模糊搜索用户列表
        if (newUser.getUserLevel() == 1) {
            if (userList == null) {
                jsonResult.setResult(0);
                jsonResult.setMsg("获取普通用户列表为空");
                return gson.toJson(jsonResult);
            } else {

                for (User user : userList) {
                    if (user.getUserLevel() == 1 || user.getUserLevel() == 6) {
                        //设置管理员信息详细表,5个参数
                        UserDetail userDetail = userDetailRepository.findByUserId(user.getUserId());
                        if(userDetail==null){
                            user.setUserDetail(null);
                        }
                        else {
                            user.setUserDetail(userDetail);
                        }
                        userList1.add(user);
                    }
                }
                if (userList1.size() != 0) {

                    jsonResult.setResult(1);
                    jsonResult.setMsg("获取普通用户列表");
                    jsonResult.setUserList(userList1);
                    return gson.toJson(jsonResult);
                } else {
                    jsonResult.setResult(0);
                    jsonResult.setMsg("获取普通用户列表为空");
                    return gson.toJson(jsonResult);
                }
            }
        }
        if (newUser.getUserLevel() == 21) {
            if (userList == null) {
                jsonResult.setResult(0);
                jsonResult.setMsg("获取项目基础信息录入人员列表为空");
                return gson.toJson(jsonResult);
            } else {

                for (User user : userList) {
                    if (user.getUserLevel() == 21) {
                        //设置管理员信息详细表,5个参数
                        UserDetail userDetail = userDetailRepository.findByUserId(user.getUserId());
                        if(userDetail==null){
                            user.setUserDetail(null);
                        }
                        else {
                            user.setUserDetail(userDetail);
                        }
                        userList1.add(user);
                    }
                }
                if (userList1.size() != 0) {
                    jsonResult.setResult(1);
                    jsonResult.setMsg("获取基础信息录入人员列表");
                    jsonResult.setUserList(userList1);
                    return gson.toJson(jsonResult);
                } else {
                    jsonResult.setResult(0);
                    jsonResult.setMsg("获取基础信息录入人员列表为空");
                    return gson.toJson(jsonResult);
                }
            }
        }
        if (newUser.getUserLevel() == 22) {
            if (userList == null) {
                jsonResult.setResult(0);
                jsonResult.setMsg("获取项目负责人列表为空");
                return gson.toJson(jsonResult);
            } else {

                for (User user : userList) {
                    if (user.getUserLevel() == 22) {
                        //设置管理员信息详细表,5个参数
                        UserDetail userDetail = userDetailRepository.findByUserId(user.getUserId());
                        if(userDetail==null){
                            user.setUserDetail(null);
                        }
                        else {
                            user.setUserDetail(userDetail);
                        }
                        userList1.add(user);
                    }
                }
                if (userList1.size() != 0) {
                    jsonResult.setResult(1);
                    jsonResult.setMsg("获取项目负责人列表");
                    jsonResult.setUserList(userList1);
                    return gson.toJson(jsonResult);
                } else {
                    jsonResult.setResult(0);
                    jsonResult.setMsg("获取项目负责人列表为空");
                    return gson.toJson(jsonResult);
                }
            }
        }
        if (newUser.getUserLevel() == 23) {
            if (userList == null) {
                jsonResult.setResult(0);
                jsonResult.setMsg("获取项目技术负责人列表为空");
                return gson.toJson(jsonResult);
            } else {

                for (User user : userList) {
                    if (user.getUserLevel() == 23) {
                        //设置管理员信息详细表,5个参数
                        UserDetail userDetail = userDetailRepository.findByUserId(user.getUserId());
                        if(userDetail==null){
                            user.setUserDetail(null);
                        }
                        else {
                            user.setUserDetail(userDetail);
                        }
                        userList1.add(user);
                    }
                }
                if (userList1.size() != 0) {
                    jsonResult.setResult(1);
                    jsonResult.setMsg("获取项目技术负责人列表");
                    jsonResult.setUserList(userList1);
                    return gson.toJson(jsonResult);
                } else {
                    jsonResult.setResult(0);
                    jsonResult.setMsg("获取项目技术负责人列表为空");
                    return gson.toJson(jsonResult);
                }
            }
        }
        if (newUser.getUserLevel() == 24) {
            if (userList == null) {
                jsonResult.setResult(0);
                jsonResult.setMsg("获取售前工程师列表为空");
                return gson.toJson(jsonResult);
            } else {

                for (User user : userList) {
                    if (user.getUserLevel() == 24) {
                        //设置管理员信息详细表,5个参数
                        UserDetail userDetail = userDetailRepository.findByUserId(user.getUserId());
                        if(userDetail==null){
                            user.setUserDetail(null);
                        }
                        else {
                            user.setUserDetail(userDetail);
                        }
                        userList1.add(user);
                    }
                }
                if (userList1.size() != 0) {
                    jsonResult.setResult(1);
                    jsonResult.setMsg("获取售前工程师列表");
                    jsonResult.setUserList(userList1);
                    return gson.toJson(jsonResult);
                } else {
                    jsonResult.setResult(0);
                    jsonResult.setMsg("获取售前工程师列表为空");
                    return gson.toJson(jsonResult);
                }
            }
        }

        if (newUser.getUserLevel() == 3) {
            if (userList == null) {
                jsonResult.setResult(0);
                jsonResult.setMsg("获取项目实施者用户列表为空");
                return gson.toJson(jsonResult);
            } else {
                for (User user : userList) {
                    if (user.getUserLevel() == 3){
                        //设置管理员信息详细表,5个参数
                        UserDetail userDetail = userDetailRepository.findByUserId(user.getUserId());
                        if(userDetail==null){
                            user.setUserDetail(null);
                        }
                        else {
                            user.setUserDetail(userDetail);
                        }
                        userList1.add(user);
                    }

                }
                if (userList1.size() != 0) {
                    jsonResult.setResult(1);
                    jsonResult.setMsg("获取项目实施者用户列表");
                    jsonResult.setUserList(userList1);
                    return gson.toJson(jsonResult);
                }
                else {
                    jsonResult.setResult(0);
                    jsonResult.setMsg("获取项目实施者用户列表为空");
                    return gson.toJson(jsonResult);
                }
            }
        }
        if (newUser.getUserLevel() == 4) {

            if (userList == null) {
                jsonResult.setResult(0);
                jsonResult.setMsg("获取政府管理员用户列表为空");
                return gson.toJson(jsonResult);
            } else {
                for (User user : userList) {
                    if (user.getUserLevel() == 4){
                        //设置管理员信息详细表,5个参数
                        UserDetail userDetail = userDetailRepository.findByUserId(user.getUserId());
                        if(userDetail==null){
                            user.setUserDetail(null);
                        }
                        else {
                            user.setUserDetail(userDetail);
                        }
                        userList1.add(user);
                    }

                }
                if (userList1.size() != 0) {
                    jsonResult.setResult(1);
                    jsonResult.setMsg("获取政府管理员用户列表");
                    jsonResult.setUserList(userList1);
                    return gson.toJson(jsonResult);
                } else {
                    jsonResult.setResult(0);
                    jsonResult.setMsg("获取政府管理员用户列表为空");
                    return gson.toJson(jsonResult);
                }

            }


        }
        if (newUser.getUserLevel() == 5) {

            if (userList == null) {
                jsonResult.setResult(0);
                jsonResult.setMsg("获取宣传管理员用户列表为空");
                return gson.toJson(jsonResult);
            } else {
                for (User user : userList) {
                    if (user.getUserLevel() == 5){
                        //设置管理员信息详细表,5个参数
                        UserDetail userDetail = userDetailRepository.findByUserId(user.getUserId());
                        if(userDetail==null){
                            user.setUserDetail(null);
                        }
                        else {
                            user.setUserDetail(userDetail);
                        }
                        userList1.add(user);
                    }

                }
                if (userList1.size() != 0) {
                    jsonResult.setResult(1);
                    jsonResult.setMsg("获取宣传管理员用户列表");
                    jsonResult.setUserList(userList1);
                    return gson.toJson(jsonResult);
                } else {
                    jsonResult.setResult(0);
                    jsonResult.setMsg("获取宣传管理员用户列表为空");
                    return gson.toJson(jsonResult);
                }
            }
        }
        jsonResult.setResult(0);
        jsonResult.setMsg("没有该level权限");
        return gson.toJson(jsonResult);

    }
}
