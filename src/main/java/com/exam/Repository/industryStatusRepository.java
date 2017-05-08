package com.exam.Repository;

import com.exam.model.entity.IndustryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mac on 2017/3/22.
 */
public interface industryStatusRepository extends JpaRepository<IndustryStatus,Integer> {
}
