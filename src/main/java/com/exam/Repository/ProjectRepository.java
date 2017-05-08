package com.exam.Repository;

import com.exam.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by mac on 2017/3/8.
 */
public interface ProjectRepository extends JpaRepository<Project,Integer> {

    public Project findByProjectName(String projectName);
    public Project findByProjectId(Integer projectId);

    public List<Project> findByProjectNameLike(String projectName);

    public List<Project> findByProjectManagerId(String projectManagerId);

    public List<Project> findByProjectSkillManagerId(String projectSkillManagerId);

    public List<Project> findByPreSaleEngineerId(String preSaleEngineerId);

    public List<Project> findByBaseInFoManagerId(String baseInfoManagerId);



}
