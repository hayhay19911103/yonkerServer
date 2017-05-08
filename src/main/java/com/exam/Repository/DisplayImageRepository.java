package com.exam.Repository;

import com.exam.model.entity.DisplayImage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mac on 2017/2/28.
 */
public interface DisplayImageRepository extends JpaRepository<DisplayImage, Integer> {
    public DisplayImage findByDisplayImageId(Integer displayImageId);
}
