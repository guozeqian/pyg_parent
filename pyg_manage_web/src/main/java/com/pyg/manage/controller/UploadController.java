package com.pyg.manage.controller;


import com.pyg.utils.FastDFSClient;
import entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;//文件服务器地址


    @RequestMapping("/uploadFile")
    public Result uploadFile(MultipartFile file) {
        System.out.println(FILE_SERVER_URL);
        System.out.println(file);
        String originalFilename = file.getOriginalFilename();
        String extName = originalFilename.substring(originalFilename
                .lastIndexOf(".") + 1);
        try {
            FastDFSClient fastDFSClient = new FastDFSClient
                    ("classpath:config/fdfs_client.conf");
            String path = fastDFSClient.uploadFile(file.getBytes(), extName);
            String url = FILE_SERVER_URL + path;
            return new Result(true, url);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Result(false, "上传失败");
        }
    }


}
