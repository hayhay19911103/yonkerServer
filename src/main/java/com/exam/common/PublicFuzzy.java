package com.exam.common;

import com.exam.Repository.UserRepository;
import com.exam.model.entity.User;

import java.util.List;

/**
 * Created by mac on 2017/3/9.
 */
public class PublicFuzzy {

    public List<User> fuzzyQuery(User newUserMsg, UserRepository userRepository) {

        if (newUserMsg.getUserPhone().equals("") && newUserMsg.getUserName().equals("")) {
            List<User> userList = userRepository.findAll();
            if (userList != null && userList.size() != 0) {
                return userList;

            } else {

                return null;
            }
        } else if (newUserMsg.getUserPhone().equals("") && newUserMsg.getUserName() != null) {
            List<User> userList = userRepository.findByUserNameLike(newUserMsg.getUserName() + "%");
            if (userList != null && userList.size() != 0) {


                return userList;

            } else {
                return null;

            }

        } else if (newUserMsg.getUserPhone() != null && newUserMsg.getUserName().equals("")) {
            List<User> userList = userRepository.findByUserPhoneLike(newUserMsg.getUserPhone() + "%");
            if (userList != null && userList.size() != 0) {

                return userList;

            } else {

                return null;
            }
        } else {

            List<User> userList = userRepository.findByUserNameLikeAndUserPhoneLike(newUserMsg.getUserName() + "%",
                    newUserMsg.getUserPhone() + "%");
            if (userList != null && userList.size() != 0) {

                return userList;

            } else {

                return null;
            }
        }
    }
}
