package com.charlie.web.fileupload;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

// 处理文件上传的handler
@Controller
public class FileUploadHandler {
    /**
     * 处理文件上传的请求
     * @param file 前端传入的参数名为file：<input type="file" name="file"/>
     *             参数MultipartFile file，即上传的文件
     */
    @RequestMapping(value = "/fileUpload")
    public String fileUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest req, String introduce) throws IOException {
        // 接收到提交的文件名
        String fileName = file.getOriginalFilename();
        System.out.println("上传的文件名：" + fileName);
        System.out.println("文件介绍：" + introduce);
        // 得到要把上传的文件保存到哪个路径，全路径：包括文件名
        String fileFullPath = req.getServletContext().getRealPath("/img/" + fileName);
        System.out.println("保存文件的路径：" + fileFullPath);
        // 创建文件
        File saveToFile = new File(fileFullPath);
        // 将上传的文件，转存到saveToFile
        file.transferTo(saveToFile);
        return "success";
    }
}
