package com.exam.common;

import com.exam.model.SaveSms;
import com.exam.model.UserSms;
import java.util.List;

/**
 * Created by mac on 2017/3/5.
 */
public class PublicCheckCode {

    /**
     * 校验手机验证码的公共方法
     * @param userSms
     * @return
     */
    public static Boolean checkCode(UserSms userSms) {

        List<UserSms> userSmsList = SaveSms.getUserSmsList();

        UserSms userSms1 = null;
        //从列表中遍历存储的手机号与验证码
        for (int i = 0; i < userSmsList.size(); i++) {
            //String a = userSmsList.get(i).getCode();
            //将用户输入的手机号和验证码和列表中的数据一一对比

            if (userSmsList.get(i).getPhone().equals(userSms.getPhone())&&userSmsList.get(i).getCode().equals(userSms.getCode())) {
                userSms1 = userSmsList.get(i);
                break;
            }
        }

        if (userSms1 != null) {
            //密码正确则移除存储的验证码
            userSmsList.remove(userSms1);//移除验证码记录
            return true;
        }
        else {

            //jsonResult.setData(null);
            return false;
        }
    }
}
