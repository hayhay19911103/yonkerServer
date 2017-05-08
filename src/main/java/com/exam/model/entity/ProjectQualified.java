package com.exam.model.entity;

import javax.persistence.*;

/**
 * Created by mac on 2017/3/8.
 */
@Entity
public class ProjectQualified {
    private Integer projectQualifiedId;
    private Integer projectId;
    private String qualifiedId;//技术员userId,属于项目实施者，权限为3
    private String qualifiedPhone;//技术员电话
    private String qualifiedName;//技术员名字

    @Id
    @GeneratedValue
    @Basic
    @Column(name = "projectQualifiedId")
    public Integer getProjectQualifiedId() {
        return projectQualifiedId;
    }

    public void setProjectQualifiedId(Integer projectQualifiedId) {
        this.projectQualifiedId = projectQualifiedId;
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
    @Column(name = "qualifiedId")
    public String getQualifiedId() {
        return qualifiedId;
    }

    public void setQualifiedId(String qualifiedId) {
        this.qualifiedId = qualifiedId;
    }

    @Basic
    @Column(name = "qualifiedPhone")
    public String getQualifiedPhone() {
        return qualifiedPhone;
    }

    public void setQualifiedPhone(String qualifiedPhone) {
        this.qualifiedPhone = qualifiedPhone;
    }

    @Basic
    @Column(name = "qualifiedName")
    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectQualified that = (ProjectQualified) o;

        if (projectQualifiedId != that.projectQualifiedId) return false;
        if (projectId != null ? !projectId.equals(that.projectId) : that.projectId != null) return false;
        if (qualifiedId != null ? !qualifiedId.equals(that.qualifiedId) : that.qualifiedId != null) return false;
        if (qualifiedPhone != null ? !qualifiedPhone.equals(that.qualifiedPhone) : that.qualifiedPhone != null)
            return false;
        if (qualifiedName != null ? !qualifiedName.equals(that.qualifiedName) : that.qualifiedName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = projectQualifiedId;
        result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
        result = 31 * result + (qualifiedId != null ? qualifiedId.hashCode() : 0);
        result = 31 * result + (qualifiedPhone != null ? qualifiedPhone.hashCode() : 0);
        result = 31 * result + (qualifiedName != null ? qualifiedName.hashCode() : 0);
        return result;
    }
}
