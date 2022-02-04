package com.hsbug.backend.app.manage_user_info.delete_user;
import com.hsbug.backend.app.user_register.UserRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class DeleteUserController {     // 회원 탈퇴

    private final DeleteUserService deleteUserService;

    @PostMapping("/deleteUser")
    public String DeleteUser(@RequestBody UserRegisterDto userRegisterDto) {

        String username = userRegisterDto.getEmail();
        System.out.println(username);
        deleteUserService.deleteUser(username);
        return "delete user"+username;
    }

}
