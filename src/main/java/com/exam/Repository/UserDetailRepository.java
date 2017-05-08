package com.exam.Repository;

import com.exam.model.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mac on 2017/3/5.
 */
public interface UserDetailRepository extends JpaRepository<UserDetail,Integer> {
    public UserDetail findByUserId(String userId);
}
