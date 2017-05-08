package com.exam.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by mac on 2017/3/26.
 */
@Entity
public class CollectData {

    @Id
    @GeneratedValue
    private Integer dataId;
    private int projectId;
    private int fieldId;

    private int controlPointId;
    private String collectTime;
    private String dataText;
    private String upLoadUserId;

    private String collectionName;
    private String collectionWeight;




    private Integer procedureId;
    private Integer step;
    private Integer proStep;
    private Integer nextStep;


    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public int getControlPointId() {
        return controlPointId;
    }

    public void setControlPointId(int controlPointId) {
        this.controlPointId = controlPointId;
    }

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    public String getDataText() {
        return dataText;
    }

    public void setDataText(String dataText) {
        this.dataText = dataText;
    }

    public String getUpLoadUserId() {
        return upLoadUserId;
    }

    public void setUpLoadUserId(String upLoadUserId) {
        this.upLoadUserId = upLoadUserId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionWeight() {
        return collectionWeight;
    }

    public void setCollectionWeight(String collectionWeight) {
        this.collectionWeight = collectionWeight;
    }


    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getProStep() {
        return proStep;
    }

    public void setProStep(Integer proStep) {
        this.proStep = proStep;
    }

    public Integer getNextStep() {
        return nextStep;
    }

    public void setNextStep(Integer nextStep) {
        this.nextStep = nextStep;
    }

    public Integer getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(Integer procedureId) {
        this.procedureId = procedureId;
    }
}
