package com.exam.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by mac on 2017/4/19.
 */

@Entity
public class ProjectControlPoint {
    @Id
    @GeneratedValue

    private Integer projectControlPointId;

    private Integer projectId;

    private Integer controlPointId;

    private Integer procedureId;

    private String controlPointName;


    private int controlPointPeriod;
    private String controlPointWorkType;
    private String controlPointMinTarget;
    private String controlPointMaxTarget;
    private String controlPointRequire;
    private Boolean controlPointIsNecessary;
    private String controlPointAdvice;
    private String controlPointNotes;

    private int step;
    private int proStep;
    private int nextStep;

    public Integer getProjectControlPointId() {
        return projectControlPointId;
    }

    public void setProjectControlPointId(Integer projectControlPointId) {
        this.projectControlPointId = projectControlPointId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(Integer procedureId) {
        this.procedureId = procedureId;
    }

    public Integer getControlPointId() {
        return controlPointId;
    }

    public void setControlPointId(Integer controlPointId) {
        this.controlPointId = controlPointId;
    }

    public String getControlPointName() {
        return controlPointName;
    }

    public void setControlPointName(String controlPointName) {
        this.controlPointName = controlPointName;
    }

    public int getControlPointPeriod() {
        return controlPointPeriod;
    }

    public void setControlPointPeriod(int controlPointPeriod) {
        this.controlPointPeriod = controlPointPeriod;
    }

    public String getControlPointWorkType() {
        return controlPointWorkType;
    }

    public void setControlPointWorkType(String controlPointWorkType) {
        this.controlPointWorkType = controlPointWorkType;
    }

    public String getControlPointMinTarget() {
        return controlPointMinTarget;
    }

    public void setControlPointMinTarget(String controlPointMinTarget) {
        this.controlPointMinTarget = controlPointMinTarget;
    }

    public String getControlPointMaxTarget() {
        return controlPointMaxTarget;
    }

    public void setControlPointMaxTarget(String controlPointMaxTarget) {
        this.controlPointMaxTarget = controlPointMaxTarget;
    }

    public String getControlPointRequire() {
        return controlPointRequire;
    }

    public void setControlPointRequire(String controlPointRequire) {
        this.controlPointRequire = controlPointRequire;
    }

    public Boolean getControlPointIsNecessary() {
        return controlPointIsNecessary;
    }

    public void setControlPointIsNecessary(Boolean controlPointIsNecessary) {
        this.controlPointIsNecessary = controlPointIsNecessary;
    }

    public String getControlPointAdvice() {
        return controlPointAdvice;
    }

    public void setControlPointAdvice(String controlPointAdvice) {
        this.controlPointAdvice = controlPointAdvice;
    }

    public String getControlPointNotes() {
        return controlPointNotes;
    }

    public void setControlPointNotes(String controlPointNotes) {
        this.controlPointNotes = controlPointNotes;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getProStep() {
        return proStep;
    }

    public void setProStep(int proStep) {
        this.proStep = proStep;
    }

    public int getNextStep() {
        return nextStep;
    }

    public void setNextStep(int nextStep) {
        this.nextStep = nextStep;
    }
}
