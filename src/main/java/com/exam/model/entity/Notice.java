package com.exam.model.entity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by dell-ewtu on 2017/3/12.
 */
@Entity
public class Notice {
    private Integer noticeId;
    private Integer projectId;
    private String noticeTime;
    private String noticeContent;
    private String projectName;

    @Id
    @GeneratedValue
    @Basic
    @Column(name = "noticeId")

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
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
    @Column(name = "noticeTime")
    public String getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }
    @Basic
    @Column(name = "noticeContent")
    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }
    @Basic
    @Column(name = "projectName")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
