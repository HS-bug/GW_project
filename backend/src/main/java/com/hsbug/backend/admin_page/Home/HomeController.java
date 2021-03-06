package com.hsbug.backend.admin_page.Home;

import com.hsbug.backend.admin_page.manage_question.ManageQuestionDto;
import com.hsbug.backend.admin_page.manage_question.ManageQuestionRepository;
import com.hsbug.backend.admin_page.manage_question.ManageQuestionService;
import com.hsbug.backend.app.search_recipe._refrigerator.SearchRecipeRefrigDto;
import com.hsbug.backend.app.user_register.UserRegisterDto;
import com.hsbug.backend.app.user_register.UserRegisterService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//관리자 페이지 컨트롤러
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;
    private final ManageQuestionService manageQuestionService;
    private final ManageQuestionRepository manageQuestionRepository;
    private final UserRegisterService userRegisterService;


    @RequestMapping(value = "/admin/home", method= RequestMethod.GET)
    public String goHome(HttpServletRequest request) {
        return "Home";
    }

    @GetMapping("/api/login")
    public String login(){
        return "post/login";
    }

    @GetMapping("/admin/Home")
    public String adminHome(){ return "Home";}

    @GetMapping("/admin/AdminManage")
    public String adminManage(Model model){
        List<UserRegisterDto> dtos = homeService.getAdminAll();
        System.out.println(dtos);
        model.addAttribute("admins", dtos);
        return "AdminManage";}


    @GetMapping("/admin/UserManage")
    public String UserManage(Model model){
         List<UserRegisterDto> dtos = homeService.getUserAll();
        System.out.println(dtos);
        model.addAttribute("user", dtos);
        return "UserManage";
    }


    @GetMapping("/admin/AdminRecipe")
    public String AdminRecipe(Model model){
        List<SearchRecipeRefrigDto> dtos = homeService.getRecipeAll();
        System.out.println(dtos);
        model.addAttribute("AdminRecipe",dtos);
        return "AdminRecipe";
    }
    @GetMapping("/admin/answerPage/id{id}")
    public String QuestionAnswer(Model model, @PathVariable Long id){
        System.out.println(111);
        ManageQuestionDto manageQuestionDto = manageQuestionService.readOne(id);
        System.out.println(manageQuestionDto);
        model.addAttribute("data",manageQuestionDto);

        return "answerPage";
    }

    @PostMapping("/admin/answerPage/id{id}/submit")
    public RedirectView QuestionAnswerSubmit(@PathVariable Long id, ManageQuestionDto manageQuestionDto, Model model) {
        ManageQuestionDto dto = manageQuestionService.readOne(id);
        dto.setAnswercheck(true);
        dto.setAnswer(manageQuestionDto.getAnswer());

        manageQuestionRepository.save(dto.toEntity());
        return new RedirectView("/admin/QA");
    }

    @PostMapping("/admin/UserManage/id{id}/delete")
    public RedirectView UserManageDelete(@PathVariable Long id){
        JSONObject obj = new JSONObject();
        userRegisterService.userdelete(id);
        obj.put("message",id+" 탈퇴 완료");
        obj.put("status",200);
        return new RedirectView("/admin/UserManage");
    }

    @PostMapping("/admin/QA/id{id}/delete")
    public RedirectView DeleteQA(@PathVariable Long id){
        JSONObject obj = new JSONObject();
        manageQuestionService.deleteQuestion(id);
        obj.put("message",id+" 삭제 완료");
        obj.put("status",200);
        return new RedirectView("/admin/QA");
    }

  @PostMapping("/admin/AdminManage/id{id}/delete")
    public RedirectView AdminManageDelete(@PathVariable Long id){
        JSONObject obj = new JSONObject();
        userRegisterService.userdelete(id);
        obj.put("message",id+" 탈퇴 완료");
        obj.put("status",200);
        return new RedirectView("/admin/AdminManage");
    }


}


