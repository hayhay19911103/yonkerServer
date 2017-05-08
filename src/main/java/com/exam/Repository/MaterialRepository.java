package com.exam.Repository;

import com.exam.model.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by mac on 2017/2/28.
 */
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    public Material findByMaterialId(Integer materialId);
    public List<Material> findByProjectId(Integer projectId);
}
