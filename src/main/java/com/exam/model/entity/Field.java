package com.exam.model.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by mac on 2017/3/10.
 */
@Entity
public class Field {
    private Integer fieldId;
    private Integer projectId;
    private String fieldQualifiedId;//技术员
    private String fieldName;//田块名
    private String fieldLocation;
    private Double fieldArea;
    private String fieldWaterCondition;

    private String fieldWaterQualified;

    private String fieldPlantType;
    private Double fieldLonEast;//经度
    private Double fieldLonWest;
    private Double fieldLatSouth;//纬度
    private Double fieldLatNorth;


    private String fieldOwner;
    private String fieldOwnerIdentification;
    private String fieldOwnerPhone;


    private String fieldPollutionLevel;
    private String fieldNotes;

    private Boolean isCompleted;

    //额外需求

    private List<FieldExecute> fieldExecuteList;
    private List<FieldPlan> fieldPlanList;

    @Id
    @GeneratedValue
    @Basic
    @Column(name = "fieldId")
    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
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
    @Column(name = "fieldQualifiedId")
    public String getFieldQualifiedId() {
        return fieldQualifiedId;
    }

    public void setFieldQualifiedId(String fieldQualifiedId) {
        this.fieldQualifiedId = fieldQualifiedId;
    }

    @Basic
    @Column(name = "fieldName")
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Basic
    @Column(name = "fieldLocation")
    public String getFieldLocation() {
        return fieldLocation;
    }

    public void setFieldLocation(String fieldLocation) {
        this.fieldLocation = fieldLocation;
    }

    @Basic
    @Column(name = "fieldArea")
    public Double getFieldArea() {
        return fieldArea;
    }

    public void setFieldArea(Double fieldArea) {
        this.fieldArea = fieldArea;
    }

    @Basic
    @Column(name = "fieldWaterCondition")
    public String getFieldWaterCondition() {
        return fieldWaterCondition;
    }

    public void setFieldWaterCondition(String fieldWaterCondition) {
        this.fieldWaterCondition = fieldWaterCondition;
    }

    @Basic
    @Column(name = "fieldWaterQualified")
    public String getFieldWaterQualified() {
        return fieldWaterQualified;
    }

    public void setFieldWaterQualified(String fieldWaterQualified) {
        this.fieldWaterQualified = fieldWaterQualified;
    }

    @Basic
    @Column(name = "fieldPlantType")
    public String getFieldPlantType() {
        return fieldPlantType;
    }

    public void setFieldPlantType(String fieldPlantType) {
        this.fieldPlantType = fieldPlantType;
    }


    public Double getFieldLonEast() {
        return fieldLonEast;
    }

    public void setFieldLonEast(Double fieldLonEast) {
        this.fieldLonEast = fieldLonEast;
    }

    public Double getFieldLonWest() {
        return fieldLonWest;
    }

    public void setFieldLonWest(Double fieldLonWest) {
        this.fieldLonWest = fieldLonWest;
    }

    public Double getFieldLatSouth() {
        return fieldLatSouth;
    }

    public void setFieldLatSouth(Double fieldLatSouth) {
        this.fieldLatSouth = fieldLatSouth;
    }

    public Double getFieldLatNorth() {
        return fieldLatNorth;
    }

    public void setFieldLatNorth(Double fieldLatNorth) {
        this.fieldLatNorth = fieldLatNorth;
    }

    @Basic
    @Column(name = "fieldOwner")
    public String getFieldOwner() {
        return fieldOwner;
    }

    public void setFieldOwner(String fieldOwner) {
        this.fieldOwner = fieldOwner;
    }

    @Basic
    @Column(name = "fieldOwnerIdentification")
    public String getFieldOwnerIdentification() {
        return fieldOwnerIdentification;
    }

    public void setFieldOwnerIdentification(String fieldOwnerIdentification) {
        this.fieldOwnerIdentification = fieldOwnerIdentification;
    }

    @Basic
    @Column(name = "fieldOwnerPhone")
    public String getFieldOwnerPhone() {
        return fieldOwnerPhone;
    }

    public void setFieldOwnerPhone(String fieldOwnerPhone) {
        this.fieldOwnerPhone = fieldOwnerPhone;
    }

    @Basic
    @Column(name = "fieldPollutionLevel")
    public String getFieldPollutionLevel() {
        return fieldPollutionLevel;
    }

    public void setFieldPollutionLevel(String fieldPollutionLevel) {
        this.fieldPollutionLevel = fieldPollutionLevel;
    }

    @Basic
    @Column(name = "fieldNotes")
    public String getFieldNotes() {
        return fieldNotes;
    }

    public void setFieldNotes(String fieldNotes) {
        this.fieldNotes = fieldNotes;
    }

    @Basic
    @Column(name = "isCompleted")
    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    @Transient
    public List<FieldExecute> getFieldExecuteList() {
        return fieldExecuteList;
    }

    public void setFieldExecuteList(List<FieldExecute> fieldExecuteList) {
        this.fieldExecuteList = fieldExecuteList;
    }
    @Transient
    public List<FieldPlan> getFieldPlanList() {
        return fieldPlanList;
    }

    public void setFieldPlanList(List<FieldPlan> fieldPlanList) {
        this.fieldPlanList = fieldPlanList;
    }

}
