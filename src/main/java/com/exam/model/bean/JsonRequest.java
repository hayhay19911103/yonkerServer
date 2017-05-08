package com.exam.model.bean;

/**
 * Created by mac on 2017/2/27.
 */
public class JsonRequest {

    private String data ;
    private int userLevel ;
    private String userId ;

    public JsonRequest() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
