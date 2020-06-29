package com.inzent.todo.repository;

import com.inzent.todo.vo.MemberVo;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {
    @Autowired
    private SqlSession sqlSession;

    public int insertMember(MemberVo member) {
        System.out.println("MemberDao - insertMember");
        sqlSession.insert("member.insertMember", member);
        return member.getMemberNo();
    }

}