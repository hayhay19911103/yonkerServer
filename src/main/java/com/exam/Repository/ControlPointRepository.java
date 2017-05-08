package com.exam.Repository;

import com.exam.model.entity.ControlPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by mac on 2017/2/28.
 */
public interface ControlPointRepository extends JpaRepository<ControlPoint,Integer> {
    public List<ControlPoint> findByProcedureId(int procedureId);
    public ControlPoint findByControlPointId(int controlPointId);
}
