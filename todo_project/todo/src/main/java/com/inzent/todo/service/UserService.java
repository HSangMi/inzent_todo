package com.inzent.todo.service;

import java.util.List;

import com.inzent.todo.dto.DeptDto;
import com.inzent.todo.dto.PwdDto;
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
    public UserVo getUserToken(UserVo user) {
        UserVo userToken = userDao.selectUserIdPwd(user);

        System.out.println(userToken);

        return userToken;
    }

    public UserDto getLoginUser(UserVo user) {
        return userDao.selectLoginUser(user);
    }

    public UserVo getById(String id) {
        return userDao.selectById(id);
    }

    public boolean pwdCheck(PwdDto pwdDto) {
        String password = userDao.selectId(pwdDto);

        if (password.equals(pwdDto.getPassword()))
            return true;
        else
            return false;
    }

    public List<DeptDto> getDeptList() {
        return userDao.selectDeptList();
    }

    public List<UserDto> getUserList(String[] deptList) {
        return userDao.selectUserList(deptList);
    }
}