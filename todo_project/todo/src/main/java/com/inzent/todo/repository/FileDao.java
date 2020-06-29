package com.inzent.todo.repository;

import com.inzent.todo.vo.ImageVo;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FileDao {

    @Autowired
    private SqlSession sqlSession;

    public int insertImg(ImageVo imageVo) {
        System.out.println("FileDao.insertImg");
        sqlSession.insert("file.insertImg", imageVo);
        return imageVo.getImgNo();
    }

    public String selectImg() {
        System.out.println("FileDao.insertImg");
        return sqlSession.selectOne("file.selectImg");
    }

}