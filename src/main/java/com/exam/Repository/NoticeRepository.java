package com.exam.Repository;


import com.exam.model.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by mac on 2017/2/28.
 */
public interface NoticeRepository extends JpaRepository<Notice,Integer> {
    public List<Notice> findByProjectId(int projectId);
}
