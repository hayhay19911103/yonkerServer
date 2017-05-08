package com.exam.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by mac on 2017/3/23.
 */
@Entity
public class Photo {

    @Id
    @GeneratedValue

    private Integer collectionPhotoId;
    private int projectId;
    private int fieldId;
    private int controlPointId;
    private String time;
    private String path;
    private int uploadUserId;


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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getUploadUserId() {
        return uploadUserId;
    }

    public void setUploadUserId(int uploadUserId) {
        this.uploadUserId = uploadUserId;
    }
}
