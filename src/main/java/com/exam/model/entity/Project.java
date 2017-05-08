package com.exam.model.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by mac on 2017/3/8.
 */
@Entity

public class Project {
    @Id
    @GeneratedValue
    private Integer projectId;
    private String projectNumber;//项目编号
    private String projectName;
    private String projectResource;//项目来源
    private String hostCompany; //业主单位

    private String projectPollutionType;//污染物类型
    private String projectPollutionTarget;//目标污染物

    private String projectPlanName;//修复技术
    private String projectLocation;//项目地点
    private Double projectArea;//项目面积
    private String projectStartTime;//项目开始时间
    private String projectEndTime;//项目结束时间

    private String projectService;//服务模式id
    private String projectExecuteCompany;//实施单位
    private String projectMonitorCompany;//监管单位

    private String baseInFoManagerId;//该项目的项目信息录入人员userId,权限为21，负责录入项目信息

    private String projectManagerId;//该项目的项目负责人userId,权限为22,负责人员任命

    private String projectSkillManagerId;//该项目的技术人员负责人userId，权限为23，负责该项目方案工序控制点的选择

    private String preSaleEngineerId;//该项目售前工程师userId，权限为24，负责该项目的前期调研

    private String projectNotes;//备注
    @Column(name = "isCompleted")
    private Boolean isCompleted = false;//项目是否完成
    @Column(name = "isCreateCompleted")
    private Boolean isCreateCompleted = false;//项目是否创建完成
    @Column(name = "isPersonCompleted")
    private Boolean isPersonCompleted = false;//人员分配是否完成
    @Column(name = "isProcedureCompleted")
    private Boolean isProcedureCompleted = false;//工艺路线选择是否完成
    @Column(name = "isResearchCompleted")
    private Boolean isResearchCompleted = false;//项目前期调研是否完成


    //额外需求
    @Transient
    private List<ProjectPlan> projectPlanList;
    @Transient
    private List<ProjectQualified> projectQualifiedList;
    @Transient
    private List<ProjectControlPoint> projectControlPointList;
    @Transient
    private String baseInFoManagerName;
    @Transient
    private String managerName;
    @Transient
    private String skillManagerName;
    @Transient
    private String preSaleEngineerName;



    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectResource() {
        return projectResource;
    }

    public void setProjectResource(String projectResource) {
        this.projectResource = projectResource;
    }

    public String getHostCompany() {
        return hostCompany;
    }

    public void setHostCompany(String hostCompany) {
        this.hostCompany = hostCompany;
    }

    public String getProjectPollutionType() {
        return projectPollutionType;
    }

    public void setProjectPollutionType(String projectPollutionType) {
        this.projectPollutionType = projectPollutionType;
    }

    public String getProjectPollutionTarget() {
        return projectPollutionTarget;
    }

    public void setProjectPollutionTarget(String projectPollutionTarget) {
        this.projectPollutionTarget = projectPollutionTarget;
    }

    public String getProjectPlanName() {
        return projectPlanName;
    }

    public void setProjectPlanName(String projectPlanName) {
        this.projectPlanName = projectPlanName;
    }

    public String getProjectLocation() {
        return projectLocation;
    }

    public void setProjectLocation(String projectLocation) {
        this.projectLocation = projectLocation;
    }

    public Double getProjectArea() {
        return projectArea;
    }

    public void setProjectArea(Double projectArea) {
        this.projectArea = projectArea;
    }

    public String getProjectStartTime() {
        return projectStartTime;
    }

    public void setProjectStartTime(String projectStartTime) {
        this.projectStartTime = projectStartTime;
    }

    public String getProjectEndTime() {
        return projectEndTime;
    }

    public void setProjectEndTime(String projectEndTime) {
        this.projectEndTime = projectEndTime;
    }

    public String getProjectService() {
        return projectService;
    }

    public void setProjectService(String projectService) {
        this.projectService = projectService;
    }

    public String getProjectExecuteCompany() {
        return projectExecuteCompany;
    }

    public void setProjectExecuteCompany(String projectExecuteCompany) {
        this.projectExecuteCompany = projectExecuteCompany;
    }

    public String getProjectMonitorCompany() {
        return projectMonitorCompany;
    }

    public void setProjectMonitorCompany(String projectMonitorCompany) {
        this.projectMonitorCompany = projectMonitorCompany;
    }

    public String getBaseInFoManagerId() {
        return baseInFoManagerId;
    }

    public void setBaseInFoManagerId(String baseInFoManagerId) {
        this.baseInFoManagerId = baseInFoManagerId;
    }

    public String getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(String projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    public String getProjectSkillManagerId() {
        return projectSkillManagerId;
    }

    public void setProjectSkillManagerId(String projectSkillManagerId) {
        this.projectSkillManagerId = projectSkillManagerId;
    }

    public String getPreSaleEngineerId() {
        return preSaleEngineerId;
    }

    public void setPreSaleEngineerId(String preSaleEngineerId) {
        this.preSaleEngineerId = preSaleEngineerId;
    }

    public String getProjectNotes() {
        return projectNotes;
    }

    public void setProjectNotes(String projectNotes) {
        this.projectNotes = projectNotes;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public Boolean getCreateCompleted() {
        return isCreateCompleted;
    }

    public void setCreateCompleted(Boolean createCompleted) {
        isCreateCompleted = createCompleted;
    }

    public Boolean getPersonCompleted() {
        return isPersonCompleted;
    }

    public void setPersonCompleted(Boolean personCompleted) {
        isPersonCompleted = personCompleted;
    }

    public Boolean getProcedureCompleted() {
        return isProcedureCompleted;
    }

    public void setProcedureCompleted(Boolean procedureCompleted) {
        isProcedureCompleted = procedureCompleted;
    }

    public Boolean getResearchCompleted() {
        return isResearchCompleted;
    }

    public void setResearchCompleted(Boolean researchCompleted) {
        isResearchCompleted = researchCompleted;
    }

    public List<ProjectPlan> getProjectPlanList() {
        return projectPlanList;
    }

    public void setProjectPlanList(List<ProjectPlan> projectPlanList) {
        this.projectPlanList = projectPlanList;
    }

    public List<ProjectQualified> getProjectQualifiedList() {
        return projectQualifiedList;
    }

    public void setProjectQualifiedList(List<ProjectQualified> projectQualifiedList) {
        this.projectQualifiedList = projectQualifiedList;
    }

    public String getBaseInFoManagerName() {
        return baseInFoManagerName;
    }

    public void setBaseInFoManagerName(String baseInFoManagerName) {
        this.baseInFoManagerName = baseInFoManagerName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getSkillManagerName() {
        return skillManagerName;
    }

    public void setSkillManagerName(String skillManagerName) {
        this.skillManagerName = skillManagerName;
    }

    public String getPreSaleEngineerName() {
        return preSaleEngineerName;
    }

    public void setPreSaleEngineerName(String preSaleEngineerName) {
        this.preSaleEngineerName = preSaleEngineerName;
    }

    public List<ProjectControlPoint> getProjectControlPointList() {
        return projectControlPointList;
    }

    public void setProjectControlPointList(List<ProjectControlPoint> projectControlPointList) {
        this.projectControlPointList = projectControlPointList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (projectId != null ? !projectId.equals(project.projectId) : project.projectId != null) return false;
        if (projectNumber != null ? !projectNumber.equals(project.projectNumber) : project.projectNumber != null)
            return false;
        if (projectName != null ? !projectName.equals(project.projectName) : project.projectName != null) return false;
        if (projectResource != null ? !projectResource.equals(project.projectResource) : project.projectResource != null)
            return false;
        if (hostCompany != null ? !hostCompany.equals(project.hostCompany) : project.hostCompany != null) return false;
        if (projectPollutionType != null ? !projectPollutionType.equals(project.projectPollutionType) : project.projectPollutionType != null)
            return false;
        if (projectPollutionTarget != null ? !projectPollutionTarget.equals(project.projectPollutionTarget) : project.projectPollutionTarget != null)
            return false;
        if (projectPlanName != null ? !projectPlanName.equals(project.projectPlanName) : project.projectPlanName != null)
            return false;
        if (projectLocation != null ? !projectLocation.equals(project.projectLocation) : project.projectLocation != null)
            return false;
        if (projectArea != null ? !projectArea.equals(project.projectArea) : project.projectArea != null) return false;
        if (projectStartTime != null ? !projectStartTime.equals(project.projectStartTime) : project.projectStartTime != null)
            return false;
        if (projectEndTime != null ? !projectEndTime.equals(project.projectEndTime) : project.projectEndTime != null)
            return false;
        if (projectService != null ? !projectService.equals(project.projectService) : project.projectService != null)
            return false;
        if (projectExecuteCompany != null ? !projectExecuteCompany.equals(project.projectExecuteCompany) : project.projectExecuteCompany != null)
            return false;
        if (projectMonitorCompany != null ? !projectMonitorCompany.equals(project.projectMonitorCompany) : project.projectMonitorCompany != null)
            return false;
        if (baseInFoManagerId != null ? !baseInFoManagerId.equals(project.baseInFoManagerId) : project.baseInFoManagerId != null)
            return false;
        if (projectManagerId != null ? !projectManagerId.equals(project.projectManagerId) : project.projectManagerId != null)
            return false;
        if (projectSkillManagerId != null ? !projectSkillManagerId.equals(project.projectSkillManagerId) : project.projectSkillManagerId != null)
            return false;
        if (preSaleEngineerId != null ? !preSaleEngineerId.equals(project.preSaleEngineerId) : project.preSaleEngineerId != null)
            return false;
        if (projectNotes != null ? !projectNotes.equals(project.projectNotes) : project.projectNotes != null)
            return false;
        if (isCompleted != null ? !isCompleted.equals(project.isCompleted) : project.isCompleted != null) return false;
        if (isCreateCompleted != null ? !isCreateCompleted.equals(project.isCreateCompleted) : project.isCreateCompleted != null)
            return false;
        if (isPersonCompleted != null ? !isPersonCompleted.equals(project.isPersonCompleted) : project.isPersonCompleted != null)
            return false;
        if (isProcedureCompleted != null ? !isProcedureCompleted.equals(project.isProcedureCompleted) : project.isProcedureCompleted != null)
            return false;
        return isResearchCompleted != null ? isResearchCompleted.equals(project.isResearchCompleted) : project.isResearchCompleted == null;
    }

    @Override
    public int hashCode() {
        int result = projectId != null ? projectId.hashCode() : 0;
        result = 31 * result + (projectNumber != null ? projectNumber.hashCode() : 0);
        result = 31 * result + (projectName != null ? projectName.hashCode() : 0);
        result = 31 * result + (projectResource != null ? projectResource.hashCode() : 0);
        result = 31 * result + (hostCompany != null ? hostCompany.hashCode() : 0);
        result = 31 * result + (projectPollutionType != null ? projectPollutionType.hashCode() : 0);
        result = 31 * result + (projectPollutionTarget != null ? projectPollutionTarget.hashCode() : 0);
        result = 31 * result + (projectPlanName != null ? projectPlanName.hashCode() : 0);
        result = 31 * result + (projectLocation != null ? projectLocation.hashCode() : 0);
        result = 31 * result + (projectArea != null ? projectArea.hashCode() : 0);
        result = 31 * result + (projectStartTime != null ? projectStartTime.hashCode() : 0);
        result = 31 * result + (projectEndTime != null ? projectEndTime.hashCode() : 0);
        result = 31 * result + (projectService != null ? projectService.hashCode() : 0);
        result = 31 * result + (projectExecuteCompany != null ? projectExecuteCompany.hashCode() : 0);
        result = 31 * result + (projectMonitorCompany != null ? projectMonitorCompany.hashCode() : 0);
        result = 31 * result + (baseInFoManagerId != null ? baseInFoManagerId.hashCode() : 0);
        result = 31 * result + (projectManagerId != null ? projectManagerId.hashCode() : 0);
        result = 31 * result + (projectSkillManagerId != null ? projectSkillManagerId.hashCode() : 0);
        result = 31 * result + (preSaleEngineerId != null ? preSaleEngineerId.hashCode() : 0);
        result = 31 * result + (projectNotes != null ? projectNotes.hashCode() : 0);
        result = 31 * result + (isCompleted != null ? isCompleted.hashCode() : 0);
        result = 31 * result + (isCreateCompleted != null ? isCreateCompleted.hashCode() : 0);
        result = 31 * result + (isPersonCompleted != null ? isPersonCompleted.hashCode() : 0);
        result = 31 * result + (isProcedureCompleted != null ? isProcedureCompleted.hashCode() : 0);
        result = 31 * result + (isResearchCompleted != null ? isResearchCompleted.hashCode() : 0);
        return result;
    }
}