package com.exam.Repository;

import com.exam.model.entity.FieldExecute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by mac on 2017/3/12.
 */
public interface FieldExecuteRepository extends JpaRepository<FieldExecute,Integer> {
    public List<FieldExecute> findByExecutorId(String executorId);

    public List<FieldExecute> findByFieldId(Integer fieldId);
}
