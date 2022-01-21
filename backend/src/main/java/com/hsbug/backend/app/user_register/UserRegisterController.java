package com.hsbug.backend.app.user_register;

<<<<<<< HEAD
import org.json.simple.JSONObject;
=======
import org.h2.engine.User;
>>>>>>> 7952ad87a74ecf5f2f4122308156604443277373
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/*

    로그인, 회원 가입, id/pw 찾기, 회원 탈퇴

*/
@RestController
@RequestMapping("/api")        // 기본 url /api/...
public class UserRegisterController {

    private final UserRegisterService userRegisterService;
    private final HttpSession  httpSession;
    public UserRegisterController(UserRegisterService userRegisterService, HttpSession httpSession) {
        this.userRegisterService = userRegisterService;
        this.httpSession = httpSession;
    }

    @GetMapping({"/loginSuccess", "/hello"})     // 로그인 성공시 get
    public UserRegisterDto LoginsuccessPage(UserRegisterDto form) {
        //UserRegisterDto form ;
        form.setEmail(httpSession.getAttribute("username").toString());
        form.setPassword("secret");
        return form;
    }

    @GetMapping("loginFailure")
    public String LoginfailurePage(){
        return "로그인 실패";
    }

    @GetMapping("/signup")     // 회원가입 페이지 Controller
    public String SignupPage(){//@RequestBody AccountForm accountForm, HttpSession session) {
        return "Sign up page";
    }

    @PostMapping("/signup")     // 회원가입 post
<<<<<<< HEAD
    public JSONObject CreateUser(@RequestBody UserRegisterDto userRegisterDto) {
        JSONObject obj = new JSONObject();
=======
    public String CreateUser(@RequestBody UserRegisterDto userRegisterDto) {
>>>>>>> 7952ad87a74ecf5f2f4122308156604443277373
        if (!userRegisterService.checkUserByUsername(userRegisterDto.getEmail())){
            System.out.println("이미 등록된 회원입니다.");
            obj.put("message","이미 등록된 회원입니다.");
            return obj;
        }
        else {
            userRegisterService.save(userRegisterDto);           // service에 dto 저장
            System.out.println(userRegisterDto.getEmail());
            System.out.println(userRegisterDto.getPassword());
            obj.put("message","회원 가입 성공");
            return obj;
        }
    }
}

