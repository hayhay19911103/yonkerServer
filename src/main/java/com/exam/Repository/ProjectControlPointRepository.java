package com.exam.Repository;

import com.exam.model.entity.ProjectControlPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by mac on 2017/4/19.
 */
public interface ProjectControlPointRepository extends JpaRepository<ProjectControlPoint,Integer> {

    public List<ProjectControlPoint> findByProcedureIdAndProjectId(Integer procedureId,Integer projectId);
    public List<ProjectControlPoint> findByProjectId(Integer projectId);


}
