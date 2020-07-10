package com.inzent.todo.service;

<<<<<<< HEAD
import java.util.List;

import com.inzent.todo.dto.DeptDto;
import com.inzent.todo.dto.PwdDto;
=======
>>>>>>> baesohyun
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
<<<<<<< HEAD
    public UserDto getUserToken(UserDto user) {
=======
    public UserDto getUserToken(UserVo user) {
>>>>>>> baesohyun
        UserDto userToken = userDao.selectUserIdPwd(user);

        System.out.println(userToken);

        return userToken;
    }

<<<<<<< HEAD
    public UserDto getLoginUser(UserDto user) {
        return userDao.selectLoginUser(user);
    }

    public UserVo getById(String id) {
        return userDao.selectById(id);
    }

    public boolean pwdCheck(PwdDto pwdDto) {
        String password = userDao.selectId(pwdDto);
=======
    public UserVo getLoginUser(UserVo user) {
        UserVo loginUser = userDao.selectLoginUser(user);

        System.out.println(loginUser);
>>>>>>> baesohyun

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