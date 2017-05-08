package com.exam.Repository;

import com.exam.model.entity.RepairContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by mac on 2017/3/22.
 */
public interface RepairContentRepository extends JpaRepository<RepairContent,Integer>{
    List<RepairContent> findBySituationId(Integer situationId);
}
