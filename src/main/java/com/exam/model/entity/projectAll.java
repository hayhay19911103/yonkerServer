package com.exam.model.entity;

import java.util.List;

/**
 * Created by mac on 2017/5/6.
 */
public class projectAll {
    private Project project;

    private List<ProjectQualified> projectQualifiedList;

    private List<ProjectPlan> projectPlanList;
    private List<ProjectControlPoint> projectControlPointList;

    private List<CollectData> collectDataList;
    private List<CollectPhoto> collectPhotoList;

    private double schedule;


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<ProjectQualified> getProjectQualifiedList() {
        return projectQualifiedList;
    }

    public void setProjectQualifiedList(List<ProjectQualified> projectQualifiedList) {
        this.projectQualifiedList = projectQualifiedList;
    }

    public List<ProjectPlan> getProjectPlanList() {
        return projectPlanList;
    }

    public void setProjectPlanList(List<ProjectPlan> projectPlanList) {
        this.projectPlanList = projectPlanList;
    }

    public List<ProjectControlPoint> getProjectControlPointList() {
        return projectControlPointList;
    }

    public void setProjectControlPointList(List<ProjectControlPoint> projectControlPointList) {
        this.projectControlPointList = projectControlPointList;
    }

    public List<CollectData> getCollectDataList() {
        return collectDataList;
    }

    public void setCollectDataList(List<CollectData> collectDataList) {
        this.collectDataList = collectDataList;
    }

    public List<CollectPhoto> getCollectPhotoList() {
        return collectPhotoList;
    }

    public void setCollectPhotoList(List<CollectPhoto> collectPhotoList) {
        this.collectPhotoList = collectPhotoList;
    }

    public double getSchedule() {
        return schedule;
    }

    public void setSchedule(double schedule) {
        this.schedule = schedule;
    }
}
