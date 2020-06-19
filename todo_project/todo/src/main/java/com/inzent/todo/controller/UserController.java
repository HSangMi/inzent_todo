package com.inzent.todo.controller;

import com.inzent.todo.repository.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    @GetMapping("/api/hello")
    public String hello() {
        System.out.println("HELLO");
        System.out.println(userDao);
        userDao.selectDao();
        System.out.println("db 성공!");

        return "hello";
    }



}