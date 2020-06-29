package com.inzent.todo.service;

import com.inzent.todo.dto.UserDto;
import com.inzent.todo.repository.UserDao;
import com.inzent.todo.vo.UserVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    // 토큰 발행을 위한 user
    public UserDto getUserToken(UserVo user) {
        UserDto userToken = userDao.selectUserIdPwd(user);

        System.out.println(userToken);

        return userToken;
    }

    public UserVo getLoginUser(UserVo user) {
        UserVo loginUser = userDao.selectLoginUser(user);

        System.out.println(loginUser);

        return loginUser;
    }

}