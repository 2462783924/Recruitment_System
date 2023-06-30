package com.kwan.controller;

import com.kwan.bean.User;
import com.kwan.common.Result;
import com.kwan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/user")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class UserController {
    @Autowired
    private UserService userService;

    // 通过用户名查找用户
    @PostMapping("/getUserByUsername")
    public Result getUserByUsername(String username) {
        User user = userService.getByUsername(username);
        return user != null ? Result.scuess(user) : Result.error("查找不到用户");
    }

    //  检查用户是否上传过简历
    @PostMapping("/isexistResume")
    public Result isexistResume(String username) {
        User user = userService.getByUsername(username);
        return user.getResume() != null ? Result.scuess(user) : Result.error("用户未设置简历信息");
    }

    //  上传简历
    @PostMapping("/saveResume")
    public Result saveResume(@RequestParam("file") MultipartFile file, String username) {
        if (file.isEmpty()) {
            return Result.error("No file uploaded");
        }
        try {
            // 获取文件名
            String originalFileName = file.getOriginalFilename();
            // 构造新的文件名，使用用户名.后缀
            String fileExtension = "";
            int dotIndex = originalFileName.lastIndexOf('.');
            if (dotIndex > 0) {
                fileExtension = originalFileName.substring(dotIndex);
            }
            String newFileName = username + fileExtension;
            // 创建保存目录（这里将文件保存到当前目录的resume文件夹下）
            String savePath = System.getProperty("user.dir") + File.separator + "file" + File.separator + "resume";
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            // 保存文件
            File destFile = new File(saveDir, newFileName);
            file.transferTo(destFile);
            userService.updateResume(username, newFileName);
            return Result.scuess("File uploaded successfully: ");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("File upload failed");
        }
    }

    //  更新用户信息
    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody User user) {
        return userService.updateUser(user) ? Result.scuess("User update successfully") : Result.error("User update failed");
    }
}
