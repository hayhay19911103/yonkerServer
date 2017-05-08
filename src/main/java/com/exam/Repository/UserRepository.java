package com.exam.Repository;

import com.exam.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Created by mac on 2017/2/28.
 */
public interface UserRepository extends JpaRepository<User,Integer> {
    public User findByUserName(String userName);
    public User findByUserPhone(String userPhone);
    public User findByUserId(String userId);
    public List<User> findByUserLevel(int userLevel);
    public List<User> findByUserNameLike(String userName);
    public List<User> findByUserPhoneLike(String userPhone);
    public List<User> findByUserNameLikeAndUserPhoneLike(String userName,String userPhone);
    public List<User> findByUserLevelOrUserLevel(int num1,int num2);
}
