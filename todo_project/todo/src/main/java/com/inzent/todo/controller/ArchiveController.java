package com.inzent.todo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.inzent.todo.dto.ArchiveSubDto;
import com.inzent.todo.dto.ArchiveSuperDto;
import com.inzent.todo.security.Auth;
import com.inzent.todo.service.ArchiveService;
import com.inzent.todo.vo.UserVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/archive")
public class ArchiveController {

    @Autowired
    private ArchiveService archiveService;

    @Auth
    @GetMapping("/archiveSuper")
    public List<ArchiveSuperDto> getArchiveSupers(HttpServletRequest req) {
        // UserVo user = (UserVo) req.getAttribute("user");
        // String userId = user.getId();

        List<ArchiveSuperDto> list = archiveService.getArchiveSupers();
        System.out.println("아카이브브브븝" + list);
        return list;
    }

    @Auth
    @GetMapping("/archiveSub")
    public List<ArchiveSubDto> getArchiveSubs(HttpServletRequest req) {
        UserVo user = (UserVo) req.getAttribute("user");
        String userId = user.getId();

        List<ArchiveSubDto> list = archiveService.getArchiveSubs(userId);
        System.out.println("아카이브브브븝" + list);
        return list;
    }
}