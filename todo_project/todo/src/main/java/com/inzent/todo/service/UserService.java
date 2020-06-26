package com.inzent.todo.service;

import com.inzent.todo.repository.UserDao;
import com.inzent.todo.vo.UserVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public UserVo getUser(UserVo user) {
        UserVo loginUser = userDao.selectUserOne(user);
        if (loginUser != null) {
            loginUser.setPassword(null);
        }

        return loginUser;
    }

}