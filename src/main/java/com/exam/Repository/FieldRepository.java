package com.exam.Repository;

import com.exam.model.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by mac on 2017/3/5.
 */
public interface FieldRepository extends JpaRepository<Field,Integer> {
    public Field findByFieldOwnerIdentification(String fieldOwnerIdentification);
    public Field findByFieldId(Integer fieldId);
    public Field findByFieldName(String fieldName);
    public List<Field> findByProjectId(Integer projectId);


}
