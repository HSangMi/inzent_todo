package com.inzent.todo.controller;

import java.util.HashMap;
import java.util.Map;

import com.inzent.todo.repository.UserDao;
import com.inzent.todo.service.JwtService;
import com.inzent.todo.service.UserService;
import com.inzent.todo.vo.UserVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @GetMapping("/api/hello")
    public String hello() {
        System.out.println("HELLO");
        System.out.println(userDao);
        userDao.selectDao();
        System.out.println("db 성공!");

        return "hello";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserVo user) {

        String token = null;

        // 들어온 로그인 정보(ID, PWD)로 DB에서 조회
        UserVo loginUser = userService.getUser(user);

        Map<String, String> map = new HashMap<String, String>();

        // 존재/유효한 user가 있다면 token 생성
        if (loginUser != null) {
            token = jwtService.createLoginToken(loginUser);
            map.put("accessToken", token);
        }

        return token != null ? new ResponseEntity<Object>(map, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}