package com.hsbug.backend.app.manage_user_info.question;

import com.hsbug.backend.admin_page.manage_question.ManageQuestionDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/question")
public class QuestionUserController {

    private final QuestionUserService questionUserService;

    @GetMapping("/read")
    public JSONObject userQuestionRead(){
        JSONObject obj = questionUserService.readUserQuestion();
        return obj;
    }

    @GetMapping("/read/detail")
    public JSONObject userQuestionReadDetail(@RequestParam Long id){
        JSONObject obj = new JSONObject();
        return questionUserService.readUserQuestionDetail(id);
    }

    @PostMapping("/add")
    public JSONObject userQuestionAdd(@RequestBody ManageQuestionDto manageQuestionDto){
        JSONObject obj = new JSONObject();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd");
        String formatedNow = now.format(formatter);

        manageQuestionDto.setDate(formatedNow);
        manageQuestionDto.setEmail(email);
        manageQuestionDto.setAnswercheck(false);
        System.out.println(manageQuestionDto);
        questionUserService.save(manageQuestionDto);

        obj.put("status",200);
        obj.put("message",manageQuestionDto.getTitle() + "  문의 작성 완료");
        return obj;
    }
}
