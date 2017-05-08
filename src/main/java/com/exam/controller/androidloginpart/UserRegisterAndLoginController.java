package com.exam.controller.androidloginpart;

import com.exam.Repository.FieldRepository;
import com.exam.Repository.UserRepository;
import com.exam.common.PublicCheckCode;
import com.exam.model.SaveSms;
import com.exam.model.UserSms;
import com.exam.model.bean.JsonRequest;
import com.exam.model.bean.JsonResult;
import com.exam.model.entity.Field;
import com.exam.model.entity.User;
import com.google.gson.Gson;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.List;

/**
 * Created by mac on 2017/2/27.
 * 普通用户注册登陆
 */

@RestController
@RequestMapping("/api/users")
public class UserRegisterAndLoginController {

    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * @param msg String userPhone
     * @return
     */

    //第一，普通用户手机号注册，判断手机号是否可以用来注册
    //用手机号注册，验证该手机是否可用来注册
    @RequestMapping(value = "/registerCheckPhone", method = RequestMethod.POST)
    public String RegisterSendMsg(@RequestBody String msg) {



        //从安卓端接收JsonRequest数据格式，并获取其中的String data 重新赋值给msg
        Gson gson = new Gson();
        JsonRequest jsonRequest = gson.fromJson(msg,JsonRequest.class);
        msg = jsonRequest.getData();
        User user = gson.fromJson(msg, User.class);

        JsonResult jsonResult = new JsonResult();
        User user1 = userRepository.findByUserPhone(user.getUserPhone());
        if (user1 != null) {

            jsonResult.setResult(0);
            jsonResult.setMsg("该手机号已被注册");
            return gson.toJson(jsonResult);

        } else {

            jsonResult.setResult(1);
            jsonResult.setMsg("该手机号未被注册");
            return gson.toJson(jsonResult);

        }
    }

    /**
     * 功能：可发送手机验证码，用于手机号注册和登陆时使用
     *
     * @param msg 参数String phone;
     * @return
     */

    //第二，发送手机验证码
    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    public String sendMsg(@RequestBody String msg) {


        //从安卓端接收JsonRequest数据格式，并获取其中的String data 重新赋值给msg
        Gson gson = new Gson();
        JsonRequest jsonRequest = gson.fromJson(msg,JsonRequest.class);
        msg = jsonRequest.getData();

        //这个类用来保存发送的手机后以及验证码
        UserSms user = gson.fromJson(msg, UserSms.class);
        String phone = user.getPhone();

        //调用阿里大鱼第三方接口过程
        //阿里大鱼URL
        String url = "http://gw.api.taobao.com/router/rest";
        //我自己随机生成了六位数验证码
        int randNum = 1 + (int) (Math.random() * ((999999 - 1) + 1));
        String codes = "" + randNum;
        //以下才是重点  三个参数，一个url阿里大鱼的服务地址，其他两个去阿里大鱼后端查看自己的相应的参数

        TaobaoClient client = new DefaultTaobaoClient(url, "23650003",
                "4570e99567eb12026635da28d12bbb5e");
        // String json="{\"code\":\"1234\",\"product\":\"某某电子商务验证\"}";
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("123456");
        //必须填写normal
        req.setSmsType("normal");
        //你应用的名字
        req.setSmsFreeSignName("永清");
        //电话号码
        req.setRecNum(phone);
        //模板
        req.setSmsTemplateCode("SMS_48755072");
        //模板中的参数，按照实际情况去
        req.setSmsParamString("{code:'" + codes + "'}");
        JsonResult jsonResult = new JsonResult();
        try {
            AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
            // 这里是我设置的一个保存验证码 机制。按照实际需求，自行设计
            List<UserSms> userSmsList = SaveSms.getUserSmsList();
            //重发验证码后，清空该手机号的所有验证码
            for (int i = 0; i < userSmsList.size(); i++) {
                if (userSmsList.get(i).getPhone().equals(phone)) {
                    userSmsList.remove(userSmsList.get(i));
                }
            }

            UserSms userSms = new UserSms();
            userSms.setPhone(phone);
            userSms.setCode(codes);
            userSms.setTime(new Date());
            System.out.println(rsp.getBody());

            List<UserSms> userSmsList1 = SaveSms.getUserSmsList();
            userSmsList1.add(userSms);

            jsonResult.setResult(1);
            jsonResult.setMsg("发送成功");
            return gson.toJson(jsonResult);

        } catch (ApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            jsonResult.setResult(0);
            jsonResult.setMsg("发送失败");
            return gson.toJson(jsonResult);
        }
    }

    /**
     * @param msg 参数 String phone,String code;
     * @return
     */

    //第三，手机号注册时校对验证码，以完成注册；也可以具有手机号登陆的校对验证码的功能
    @RequestMapping(value = "/registerAndLoginCheckCode")
    public String registerAndLoginCheck(@RequestBody String msg) {


        //从安卓端接收JsonRequest数据格式，并获取其中的String data 重新赋值给msg
        Gson gson = new Gson();
        JsonRequest jsonRequest = gson.fromJson(msg,JsonRequest.class);
        msg = jsonRequest.getData();


        UserSms userSms = gson.fromJson(msg, UserSms.class);
        Boolean aBoolean = PublicCheckCode.checkCode(userSms);
        JsonResult jsonResult = new JsonResult();

        if (aBoolean) {
            User user = userRepository.findByUserPhone(userSms.getPhone());
            //如果为空则说明，此时是注册功能
            if(user == null) {
                //设置普通用户level和是否第一次登陆
                User user1 = new User();
                user1.setUserPhone(userSms.getPhone());
                user1.setUserLevel(1);//设置普通用户权限为1
                user1.setFirst(true);
                userRepository.save(user1);

                jsonResult.setResult(1);
                jsonResult.setMsg("验证码正确，手机号注册成功");
                //jsonResult.setData(gson.toJson(user1));
                return gson.toJson(jsonResult);
            }
            //不为空则说明此时是手机号登陆功能
            else {
                jsonResult.setResult(1);
                jsonResult.setMsg("验证码正确，手机登陆成功");
                //返回用户userLevel是否完善资料用来判断是否需要完善资料，以便进入完善资料界面
                //最重要的是返回了用户的userId和userLevel 以便进入普通用户的界面
                jsonResult.setData(gson.toJson(user));
                return gson.toJson(jsonResult);
            }

        } else {
            jsonResult.setResult(0);
            jsonResult.setMsg("验证码输入错误");
            return gson.toJson(jsonResult);
        }
    }




    /**
     * 传入手机号注册时的手机号，   以及要完善的用户名，密码，还可能传入土地拥有者的身份证号
     * @param msg
     * @return
     */

    //第四，完善普通用户资料信息
    @RequestMapping("/userInFo")
    public String userInFO(@RequestBody String msg){


        //从安卓端接收JsonRequest数据格式，并获取其中的String data 重新赋值给msg
        Gson gson = new Gson();
        JsonRequest jsonRequest = gson.fromJson(msg,JsonRequest.class);
        msg = jsonRequest.getData();

        JsonResult jsonResult = new JsonResult();

        //映射实体类
        User user = gson.fromJson(msg,User.class);
        //查找数据库中是否存在同名数据
        User user1 = userRepository.findByUserName(user.getUserName());
        if(user1 != null) {
            jsonResult.setResult(0);
            jsonResult.setMsg("该用户名已被使用");
            return gson.toJson(jsonResult);
        }
        else{
            if(user.getFieldOwnerIdentification()==null) {
                //数据库编辑数据操作
                //在数据库找到要完善的user条目
                user1 = userRepository.findByUserPhone(user.getUserPhone());
                user1.setUserName(user.getUserName());
                user1.setUserPwd(user.getUserPwd());
                user1.setFirst(false);//表示已完善用户资料
                userRepository.save(user1);
                jsonResult.setResult(1);
                jsonResult.setMsg("普通用户完善成功");
                return gson.toJson(jsonResult);
            }
            else {
                Field field = fieldRepository.findByFieldOwnerIdentification(user.getFieldOwnerIdentification());
                if(field!=null){
                    //数据库编辑数据操作
                    //在数据库找到要完善的user条目
                    user1 = userRepository.findByUserPhone(user.getUserPhone());
                    user1.setUserName(user.getUserName());
                    user1.setUserPwd(user.getUserPwd());
                    user1.setFirst(false);//表示已完善用户资料
                    user1.setUserLevel(6);//设置土地拥有者权限为6
                    userRepository.save(user1);
                    jsonResult.setResult(1);
                    jsonResult.setMsg("土地拥有者完善成功");
                    return gson.toJson(jsonResult);
                }
                else{
                    //数据库编辑数据操作
                    //在数据库找到要完善的user条目
                    user1 = userRepository.findByUserPhone(user.getUserPhone());
                    user1.setUserName(user.getUserName());
                    user1.setUserPwd(user.getUserPwd());
                    user1.setFirst(false);//表示已完善用户资料
                    userRepository.save(user1);
                    jsonResult.setResult(1);
                    jsonResult.setMsg("普通用户资料完善成功");
                    return gson.toJson(jsonResult);

                }
            }
        }
    }

    /**
     *
     * @param msg
     * @return
     */
    //第五，普通用户使用手机号登陆时验证用户是否注册过，未注册不可以登陆
    //用手机号注册，发送验证码；
    @RequestMapping(value = "/isRegister",method = RequestMethod.POST)
    public String checkPhoneIsRegister(@RequestBody String msg){

        //从安卓端接收JsonRequest数据格式，并获取其中的String data 重新赋值给msg
        Gson gson = new Gson();
        JsonRequest jsonRequest = gson.fromJson(msg,JsonRequest.class);
        msg = jsonRequest.getData();

        User user = gson.fromJson(msg, User.class);
        JsonResult jsonResult = new JsonResult();
        User user1 = userRepository.findByUserPhone(user.getUserPhone());
        if(user1 != null) {

            jsonResult.setResult(1);
            jsonResult.setMsg("该手机号可以登陆");
            return gson.toJson(jsonResult);
        }
        else {
            jsonResult.setResult(0);
            jsonResult.setMsg("该手机号还未注册");
            return gson.toJson(jsonResult);
        }
    }




    /**
     * 普通用户使用用户名密码登陆，此时用户信息已完成
     * @param msg
     * @return
     */
    //第六，普通用户使用用户名密码登陆
    @RequestMapping(value = "/userNameLogin")
    public String login(@RequestBody String msg) {

        //从安卓端接收JsonRequest数据格式，并获取其中的String data 重新赋值给msg
        Gson gson = new Gson();
        JsonRequest jsonRequest = gson.fromJson(msg,JsonRequest.class);
        msg = jsonRequest.getData();


        User user = gson.fromJson(msg, User.class);
        User user1 = userRepository.findByUserName(user.getUserName());
        JsonResult jsonResult = new JsonResult();

        //数据库查询数据操作
        if (user1 != null && user1.getUserPwd().equals(user.getUserPwd())) {

            jsonResult.setResult(1);
            jsonResult.setMsg("登陆成功");

            //最重要的是返回了用户的userId和userLevel以便进入普通用户的界面
            jsonResult.setData(gson.toJson(user1));
            return gson.toJson(jsonResult);

        } else {
            jsonResult.setResult(0);
            jsonResult.setMsg("登陆失败，信息有误");
            return gson.toJson(jsonResult);

        }
    }
}