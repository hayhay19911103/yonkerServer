package com.exam.Repository;

import com.exam.model.entity.ProjectPlan;
import com.exam.model.entity.ProjectQualified;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by mac on 2017/3/8.
 */
public interface ProjectQualifiedRepository extends JpaRepository<ProjectQualified,Integer> {
    public List<ProjectQualified> findByProjectId(Integer projectId);
    public List<ProjectQualified> findByQualifiedId(String qualifiedId);
}
