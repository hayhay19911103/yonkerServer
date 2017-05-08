package com.exam.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by mac on 2017/3/5.
 */
@Entity
public class UserDetail {
    @Id
    @GeneratedValue

    @Column(length = 11)
    private Integer userDetailId;

    @Column(length = 50)
    private String userId;

    @Column
    private Boolean userSex;//性别 true 代表男性 false 代表女性

    @Column(length = 20)
    private String userIdentification;//身份证号

    @Column(length = 20)
    private String userStation;//岗位

    @Column(length = 20)
    private String userNumber;//工号

    @Column(length = 20)
    private String userDepartment;//部门

    public Integer getUserDetailId() {
        return userDetailId;
    }

    public void setUserDetailId(Integer userDetailId) {
        this.userDetailId = userDetailId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getUserSex() {
        return userSex;
    }

    public void setUserSex(Boolean userSex) {
        this.userSex = userSex;
    }

    public String getUserIdentification() {
        return userIdentification;
    }

    public void setUserIdentification(String userIdentification) {
        this.userIdentification = userIdentification;
    }

    public String getUserStation() {
        return userStation;
    }

    public void setUserStation(String userStation) {
        this.userStation = userStation;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserDepartment() {
        return userDepartment;
    }

    public void setUserDepartment(String userDepartment) {
        this.userDepartment = userDepartment;
    }
}
