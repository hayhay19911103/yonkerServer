package com.exam.model.entity;

import javax.persistence.*;

/**
 * Created by dell-ewtu on 2017/3/16.
 */
@Entity
public class Material {
    @Id
    @GeneratedValue
    @Basic
    @Column(name = "materialId")
    private Integer materialId;

    @Basic
    @Column(name = "projectId")
    private Integer projectId;

    @Basic
    @Column(name = "materialName")
    private String materialName;

    @Basic
    @Column(name = "size")
    private String size;

    @Basic
    @Column(name = "uploadTime")
    private String uploadTime;

    @Basic
    @Column(name = "path")
    private String path;

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
