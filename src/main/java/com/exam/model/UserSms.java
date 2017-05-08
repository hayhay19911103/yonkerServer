package com.exam.model;

import java.util.Date;

/**
 * Created by mac on 2017/2/24.
 */
public class UserSms {
    //手机号
    private String phone;
    //验证码
    private String code;
    //时间
    private Date time;

    public String getPhone() {return phone;}

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}
