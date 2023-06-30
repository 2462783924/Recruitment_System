package com.kwan.controller;

import com.kwan.bean.Chat;
import com.kwan.bean.Deliver;
import com.kwan.common.Result;
import com.kwan.service.ChatService;
import com.kwan.service.DeliverService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;

@RestController
@RequestMapping("/deliver")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class DeliverController {
    @Autowired
    private DeliverService deliverService;
    @Autowired
    private ChatService chatService;

    @PostMapping("/saveDeliver")
    @Transactional
    public Result saveDeliver(Deliver deliver, String receiver) {
        if (deliver.getUsername().equals(receiver)) {
            return Result.error("无法向自己公司发布的职位投递");
        }
        int result = deliver.getUsername().compareTo(receiver);
        String bothside = null;
        if (result < 0) {
            bothside = deliver.getUsername() + "&" + receiver;
        } else if (result > 0) {
            bothside = receiver + "&" + deliver.getUsername();
        }
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Chat chat = new Chat(0, bothside, deliver.getUsername(), receiver, currentTimestamp, "# send a resume #", null, 0);
        return deliverService.saveDeliver(deliver) && chatService.insertChat(chat) ? Result.scuess("投递成功") : Result.error("投递失败");
    }

    @PostMapping("/deleteDeliver")
    public Result deleteDeliver(int id) {
        return deliverService.deleteDeliver(id) ? Result.scuess("删除成功") : Result.error("无法取消");
    }

    @SneakyThrows
    @GetMapping("/downloadResume")
    public void downloadResume(HttpServletResponse response, String resume){
        String filePath = "D:\\IDEA\\IdeaProjects\\Recruitment_System\\file\\resume\\"+resume ; // 根据实际情况进行修改
        File file = new File(filePath);
        if (file.exists()) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + resume + "\"");

            try (InputStream inputStream = new FileInputStream(file);
                 OutputStream outputStream = response.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
