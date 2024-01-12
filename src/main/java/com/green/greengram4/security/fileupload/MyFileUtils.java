package com.green.greengram4.security.fileupload;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Component
@Data
public class MyFileUtils {
    private final String uploadPrefixPath;

    public MyFileUtils(@Value("${file.dir}") String uploadPrefixPath) {
        this.uploadPrefixPath = uploadPrefixPath;
    }

    // 폴더 만들기
    // use io
    public String makeDirs(String path) {
        if(path.startsWith("/")) path = path.substring(path.indexOf("/"));
        File folder = new File(uploadPrefixPath, path);
        folder.mkdirs();
        return folder.getAbsolutePath();
    }

    public String getRandomFileName() {
        return UUID.randomUUID().toString();
    }

    // 확장자 얻어오기
    public String getExt(String fileName) {
        if(fileName == null || !fileName.contains(".")) return null;
        return fileName.substring(fileName.lastIndexOf("."));
    }

    // 랜덤 파일명 만들기 with 확장자
    public String getRandomFileName(String originalFileName) {
        return getRandomFileName() + getExt(originalFileName);
    }

    // 랜덤 파일명 만들기 with 확장자 from MultipartFile
    public String getRandomFileName(MultipartFile multipartFile) {
        return getRandomFileName(multipartFile.getOriginalFilename());
    }
}
