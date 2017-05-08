package com.exam.Repository;

import com.exam.model.entity.CollectPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by mac on 2017/3/23.
 */
public interface CollectPhotoRepository extends JpaRepository<CollectPhoto,Integer> {

    List<CollectPhoto>findByControlPointIdAndProjectId(int controlPoint, int projectId);


}
