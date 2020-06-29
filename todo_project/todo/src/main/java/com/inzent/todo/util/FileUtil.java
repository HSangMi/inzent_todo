package com.inzent.todo.util;

import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
    private static final String SAVE_PATH = "C:\\Users\\aa\\Desktop\\final_project_workspace\\todo_project\\todo";
    // System.getProperty("user.dir")
    public static final String BASE_URL = "/upload/image";

    public static void writeFile(MultipartFile multipartfile, String saveFileName) throws IOException {
        byte[] fileData = multipartfile.getBytes(); // 파일 데이터를 가져와서
        FileOutputStream fos = new FileOutputStream(SAVE_PATH + BASE_URL + "/" + saveFileName); // 저장할 위치에 fos스트림을 열고
        fos.write(fileData); // 쓰고
        fos.close(); // 닫기
    }
}