package com.exam.Repository;


import com.exam.model.entity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by mac on 2017/2/28.
 */
public interface ApplyRepository extends JpaRepository<Apply, Integer> {
    public List<Apply> findByProjectId(Integer projectId);

    public List<Apply> findByApplyFromId(String userId);

    public List<Apply> findByApplyToId(String userId);

    public Apply findByApplyId(Integer applyId);
}
