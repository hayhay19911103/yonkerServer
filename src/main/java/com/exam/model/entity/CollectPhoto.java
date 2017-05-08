package com.exam.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.FileInputStream;


/**
 * Created by mac on 2017/3/23.
 */
@Entity
public class CollectPhoto {

    @Id
    @GeneratedValue

    private Integer collectionPhotoId;
    private int projectId;
    private int fieldId;
    private int controlPointId;
    private String collectTime;
    private String path;
    private String upLoadUserId;


    private Integer procedureId;
    private Integer step;
    private Integer proStep;
    private Integer nextStep;




    public Integer getCollectionPhotoId() {
        return collectionPhotoId;
    }

    public void setCollectionPhotoId(Integer collectionPhotoId) {
        this.collectionPhotoId = collectionPhotoId;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUploadUserId() {
        return upLoadUserId;
    }

    public void setUploadUserId(String uploadUserId) {
        this.upLoadUserId = upLoadUserId;
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
