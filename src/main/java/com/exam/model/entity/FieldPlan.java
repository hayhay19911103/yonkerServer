package com.exam.model.entity;

import javax.persistence.*;

/**
 * Created by mac on 2017/3/10.
 */
@Entity
public class FieldPlan {
    private Integer fieldPlanId;
    private Integer projectPlanId;
    private Integer projectId;
    private Integer fieldId;
    private Integer procedureId;

    private String procedureName;

    private Integer step;
    private Integer proStep;
    private Integer nextStep;
    private Boolean isSerial;
    private Boolean isCompleted;
    private Boolean isClick;



    @Id
    @GeneratedValue
    @Basic
    @Column(name = "fieldPlanId")
    public Integer getFieldPlanId() {
        return fieldPlanId;
    }

    public void setFieldPlanId(Integer fieldPlanId) {
        this.fieldPlanId = fieldPlanId;
    }

    @Basic
    @Column(name = "projectPlanId")
    public Integer getProjectPlanId() {
        return projectPlanId;
    }

    public void setProjectPlanId(Integer projectPlanId) {
        this.projectPlanId = projectPlanId;
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

    public void setFieldId(Integer fieldid) {
        this.fieldId = fieldid;
    }

    @Basic
    @Column(name = "procedureId")
    public Integer getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(Integer procedureId) {
        this.procedureId = procedureId;
    }

    @Basic
    @Column(name = "step")
    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    @Basic
    @Column(name = "proStep")
    public Integer getProStep() {
        return proStep;
    }

    public void setProStep(Integer proStep) {
        this.proStep = proStep;
    }

    @Basic
    @Column(name = "nextStep")
    public Integer getNextStep() {
        return nextStep;
    }

    public void setNextStep(Integer nextStep) {
        this.nextStep = nextStep;
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
    @Column(name = "isSerial")
    public Boolean getIsSerial() {
        return isSerial;
    }

    public void setIsSerial(Boolean isSerial) {
        this.isSerial = isSerial;
    }

    @Basic
    @Column(name = "isCompleted")
    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Basic
    @Column(name = "isClick")
    public Boolean getIsClick() {
        return isClick;
    }

    public void setIsClick(Boolean isClick) {
        this.isClick = isClick;
    }

}
