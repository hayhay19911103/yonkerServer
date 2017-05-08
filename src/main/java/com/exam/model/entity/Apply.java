package com.exam.model.entity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.text.StyledEditorKit;

/**
 * Created by dell-ewtu on 2017/3/12.
 */
@Entity
public class Apply {
    private Integer applyId;
    private Integer projectId;
    private Integer fieldId;
    private Integer fieldPlanId;
    private Integer isPass;//0表示待审核，1表示未通过，2表示已通过
    private String applyTime;
    private String applyFromId;
    private String applyToId;
    private String procedureName;
    private String applyFromName;
    private String applyToName;
    @Transient
    private String projectName;
    @Transient
    private String fieldName;



    @Id
    @GeneratedValue
    @Basic
    @Column(name = "applyId")
    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    @Basic
    @Column(name = "projectId")
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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
    @Column(name = "fieldPlanId")
    public Integer getFieldPlanId() {
        return fieldPlanId;
    }

    public void setFieldPlanId(Integer fieldPlanId) {
        this.fieldPlanId = fieldPlanId;
    }

    @Basic
    @Column(name = "isPass")
    public Integer getIsPass() {
        return isPass;
    }

    public void setIsPass(Integer isPass) {
        this.isPass = isPass;
    }

    @Basic
    @Column(name = "applyTime")
    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    @Basic
    @Column(name = "applyFromId")
    public String getApplyFromId() {
        return applyFromId;
    }

    public void setApplyFromId(String applyFromId) {
        this.applyFromId = applyFromId;
    }

    @Basic
    @Column(name = "applyToId")
    public String getApplyToId() {
        return applyToId;
    }

    public void setApplyToId(String applyToId) {
        this.applyToId = applyToId;
    }

    @Basic
    @Column(name = "projectName")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Basic
    @Column(name = "fieldName")
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Basic
    @Column(name = "procedureName")
    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }
    @Basic
    @Column(name = "applyFromName")
    public String getApplyFromName() {
        return applyFromName;
    }

    public void setApplyFromName(String applyFromName) {
        this.applyFromName = applyFromName;
    }
    @Basic
    @Column(name = "applyToName")
    public String getApplyToName() {
        return applyToName;
    }

    public void setApplyToName(String applyToName) {
        this.applyToName = applyToName;
    }
}
