package com.inzent.todo.service;

import java.util.List;

import com.inzent.todo.dto.ArchiveSubDto;
import com.inzent.todo.dto.ArchiveSuperDto;
import com.inzent.todo.repository.ArchiveDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArchiveService {

    @Autowired
    private ArchiveDao archiveDao;

    public List<ArchiveSuperDto> getArchiveSupers() {

        return archiveDao.getArchiveSupers();
    }

    public List<ArchiveSubDto> getArchiveSubs(String userId) {

        return archiveDao.getArchiveSubs(userId);
    }
}