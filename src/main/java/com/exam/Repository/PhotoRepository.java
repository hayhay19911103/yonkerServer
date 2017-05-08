package com.exam.Repository;

import com.exam.model.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mac on 2017/3/23.
 */
public interface PhotoRepository extends JpaRepository<Photo,Integer> {


}
