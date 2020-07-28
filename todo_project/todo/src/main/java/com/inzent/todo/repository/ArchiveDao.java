package com.inzent.todo.repository;

import java.util.List;

import com.inzent.todo.dto.ArchiveSubDto;
import com.inzent.todo.dto.ArchiveSuperDto;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArchiveDao {

    @Autowired
    private SqlSession sqlsession;

    public List<ArchiveSuperDto> getArchiveSupers() {

        return sqlsession.selectList("archive.getArchiveSupers");
    }

    public List<ArchiveSubDto> getArchiveSubs(String userId) {

        return sqlsession.selectList("archive.getArchiveSubs", userId);
    }
}