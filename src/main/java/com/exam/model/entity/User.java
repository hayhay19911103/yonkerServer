package com.exam.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by mac on 2017/2/28.
 */
@Entity
public class User {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")

    @Column(length = 50)
    private String userId;

    @Column(length = 50)
    private String userName;

    @Column(length = 50)
    private String userPwd;

    @Column(length = 11)
    private String userPhone;

    @Column(length = 11)
    private int userLevel;

    @Column
    private Boolean isFirst;

    @Transient
    private UserDetail userDetail;

    @Transient
    private String fieldOwnerIdentification;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public Boolean getFirst() {
        return isFirst;
    }

    public void setFirst(Boolean first) {
        isFirst = first;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public String getFieldOwnerIdentification() {
        return fieldOwnerIdentification;
    }

    public void setFieldOwnerIdentification(String fieldOwnerIdentification) {
        this.fieldOwnerIdentification = fieldOwnerIdentification;
    }


}
