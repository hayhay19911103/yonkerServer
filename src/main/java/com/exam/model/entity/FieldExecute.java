package com.exam.model.entity;

import javax.persistence.*;

/**
 * Created by mac on 2017/3/10.
 */
@Entity
public class FieldExecute {
    private Integer fieldExecuteId;
    private Integer fieldId;
    private String executorId;//施工人员
    private String qualifiedId;//
    private String executorPhone;
    private String executorName;

    @Id
    @GeneratedValue
    @Basic
    @Column(name = "fieldExecuteId")
    public Integer getFieldExecuteId() {
        return fieldExecuteId;
    }

    public void setFieldExecuteId(Integer fieldExecuteId) {
        this.fieldExecuteId = fieldExecuteId;
    }

    @Basic
    @Column(name = "fieldId")
    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    @Basic
    @Column(name = "executorId")
    public String getExecutorId() {
        return executorId;
    }

    public void setExecutorId(String executorId) {
        this.executorId = executorId;
    }

    @Basic
    @Column(name = "qualifiedId")
    public String getQualifiedId() {
        return qualifiedId;
    }

    public void setQualifiedId(String qualifiedId) {
        this.qualifiedId = qualifiedId;
    }

    @Basic
    @Column(name = "executorPhone")
    public String getExecutorPhone() {
        return executorPhone;
    }

    public void setExecutorPhone(String executorPhone) {
        this.executorPhone = executorPhone;
    }

    @Basic
    @Column(name = "executorName")
    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }


}
