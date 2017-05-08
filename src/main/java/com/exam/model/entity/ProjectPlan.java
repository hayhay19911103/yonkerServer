package com.exam.model.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by mac on 2017/3/8.
 */
@Entity
public class ProjectPlan {
    private Integer projectPlanId;
    private Integer projectId;
    private Integer procedureId;
    private Integer step;
    private Integer proStep;
    private Integer nextStep;
    private Boolean isSerial;
    private String procedureName;


    //

    private String PlanName;

    private List<ProjectControlPoint> projectControlPointList;


    @Id
    @GeneratedValue
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
    @Column(name = "procedureName")
    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
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
    @Column(name = "isSerial")

    public Boolean getIsSerial() {
        return isSerial;
    }

    public void setIsSerial(Boolean isSerial) {
        this.isSerial = isSerial;
    }


    @Transient
    public List<ProjectControlPoint> getProjectControlPointList() {
        return projectControlPointList;
    }

    public void setProjectControlPointList(List<ProjectControlPoint> projectControlPointList) {
        this.projectControlPointList = projectControlPointList;
    }

    @Transient

    public String getPlanName() {
        return PlanName;
    }

    public void setPlanName(String planName) {
        PlanName = planName;
    }

}
