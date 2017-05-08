package com.exam.Repository;

import com.exam.model.entity.CollectData;
import com.exam.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by mac on 2017/3/26.
 */
public interface CollectDataRepository extends JpaRepository<CollectData,Integer> {

    public List<CollectData> findByControlPointIdAndProjectId(int controlPoint,int projectId);

    //public List<CollectData> findBy




}
