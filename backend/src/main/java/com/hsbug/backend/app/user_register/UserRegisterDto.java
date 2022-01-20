package com.hsbug.backend.app.user_register;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Setter @Getter
public class UserRegisterDto {

    private Long id;
    private String email;
    private String password;


    @Builder
    public UserRegisterDto(Long id, String email, String password, String role, String credit_check){
        this.id = id;
        this.email = email;
        this.password = "{bcrypt}"+password;
    }

    public UserRegisterEntity toEntity(){
        return UserRegisterEntity.builder()
                .id(id)
                .username(email)
                .password(password)     // BCryptPasswordEncoder  == 스프링 시큐리티에서 제공, 비밀번호 암호화
                .build();
    }

}
