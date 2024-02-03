package com.green.greengram4.security.fileupload;

import com.green.greengram4.common.fileupload.MyFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@ExtendWith(SpringExtension.class)
@Import({MyFileUtils.class})
@TestPropertySource(properties = "file.dir=D:/home/download/")
class MyFileUtilsTest {

    @Autowired
    MyFileUtils myFileUtils;

    @Test
    void makeFolderTest() {
        String path = "yyy";
        File preFolder = new File(myFileUtils.getUploadPrefixPath(), path);
        Assertions.assertEquals(false, preFolder.exists());

        String newPath = myFileUtils.makeDirs(path);
        File newFolder = new File(newPath);
        Assertions.assertEquals(preFolder.getAbsolutePath(), newFolder.getAbsolutePath());
        Assertions.assertEquals(true, newFolder.exists());
        myFileUtils.makeDirs("add");
    }

    @Test
    void getRandomFileName() {
        String result = myFileUtils.getRandomFileName();
        log.info("result = {}", result);
        String result2 = myFileUtils.getRandomFileName();
        org.assertj.core.api.Assertions.assertThat(result).isNotSameAs(result2);

    }

    @Test
    void getExtTest() {
        String fileName = "abc.efg.eee.jpg";
        String result = myFileUtils.getExt(fileName);
        log.info("result = {}", result);
        Assertions.assertTrue(".jpg".equals(result));
        String fileName2 = "abc.efg.eee.jpgf";
        String result2 = myFileUtils.getExt(fileName2);
        log.info("result = {}", result2);
        Assertions.assertTrue(".jpgf".equals(result2));

    }

    @Test
    void getRandomFileName2() {
        String fileName1 = "반갑다.친구야.jpeg";
        String rFileNm1 = myFileUtils.getRandomFileName(fileName1);
        // 랜덤문자열.jpeg
        log.info("rFileNm1 = {}", rFileNm1);

        String fileName2 = "크크크.ㅁㅈ댷젇햐.qq";
        String rFileName2 = myFileUtils.getRandomFileName(fileName2);
        log.info("rFileNm2 = {}", rFileName2);
        // 랜덤문자열.qq
    }

    @Test
    void transferToTest() throws IOException {
        String fileName = "pic.jpg";
        String filePath = "D:/ee/" + fileName;
        FileInputStream fileInputStream = new FileInputStream(filePath);
        MultipartFile multipartFile = new MockMultipartFile("pic", fileName, "png", fileInputStream);

        String storedFileName = myFileUtils.transferTo(multipartFile, "feed/10");
        log.info("storedFileName = {}", storedFileName);
    }
}