package com.inzent.todo.repository;

import java.util.List;

import com.inzent.todo.vo.UserVo;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    @Autowired
    private SqlSession sqlSession;

    public void selectDao() {
        System.out.println("Select DAO");

        List<String> list = sqlSession.selectList("user.selectTest");
        for (String s : list) {
            System.out.println(s);
        }
    }

    public UserVo selectUserOne(UserVo user) {
        return sqlSession.selectOne("user.selectByIdPwd", user);
    }
}