package com.hsbug.backend.app.user_register;

import com.hsbug.backend.app.refrigerator.manage_product.ManageProductEntity;
import com.hsbug.backend.app.refrigerator.manage_product.ManageProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserRegisterService implements UserDetailsService {

    private final UserRegisterRepository userRegisterRepository;      // accountRepository 가져옴
    private final ManageProductRepository manageProductRepository;
    @Transactional
    public void save(UserRegisterDto form) throws UsernameNotFoundException {       // 회원 정보 save
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        form.setPassword(encoder.encode(form.getPassword()));
        if (form.getRoles().equals("ROLE_USER")) {
            form.setRoles("ROLE_USER");
        }
        if (form.getRoles().equals("ROLE_ADMIN")) {
            form.setRoles("ROLE_ADMIN");
        }
        userRegisterRepository.save(form.toEntity());
    }

    public void userdelete(Long id) throws UsernameNotFoundException { // 회원 강퇴(delete)
            userRegisterRepository.deleteById(id);
    }

    // 회원 정보 찾기
    public boolean checkUserByUsername(String username) {//throws UsernameNotFoundException,NullPointerException {
        //Optional<UserRegisterEntity> check = userRegisterRepository.findByUsername(username);
        Optional<UserRegisterEntity> check = userRegisterRepository.findByEmail(username);
        return check.isEmpty();
    }

    @Override       // 회원 정보 불러오기
    public UserRegisterEntity loadUserByUsername(String username) throws UsernameNotFoundException, NullPointerException {
        if (!this.checkUserByNaversub(username)) {
            return loadUserByNaversub(username);
        }
        if (!this.checkUserByGooglesub(username)) {
            return loadUserByGooglesub(username);
        }
        if (!this.checkUserByKakaosub(username)) {
            return loadUserByKakaosub(username);
        } else {
            return userRegisterRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));
        }
    }
    public boolean checkUserByNaversub(String naver_sub) {//throws UsernameNotFoundException,NullPointerException {
        Optional<UserRegisterEntity> check = userRegisterRepository.findByNaversub(naver_sub);
        return check.isEmpty();
    }

    public UserRegisterEntity loadUserByNaversub(String naver_sub) throws UsernameNotFoundException,NullPointerException {
        return userRegisterRepository.findByNaversub(naver_sub)
                .orElseThrow(() -> new UsernameNotFoundException(naver_sub));
    }

    public boolean checkUserByGooglesub(String google_sub) {//throws UsernameNotFoundException,NullPointerException {
        Optional<UserRegisterEntity> check = userRegisterRepository.findByGooglesub(google_sub);
        return check.isEmpty();
    }

    public UserRegisterEntity loadUserByGooglesub(String google_sub) throws UsernameNotFoundException,NullPointerException {
        return userRegisterRepository.findByGooglesub(google_sub)
                .orElseThrow(() -> new UsernameNotFoundException(google_sub));
    }

    public boolean checkUserByKakaosub(String kakao_sub) {//throws UsernameNotFoundException,NullPointerException {
        Optional<UserRegisterEntity> check = userRegisterRepository.findByKakaosub(kakao_sub);
        return check.isEmpty();
    }

    public UserRegisterEntity loadUserByKakaosub(String kakao_sub) throws UsernameNotFoundException,NullPointerException {
        return userRegisterRepository.findByKakaosub(kakao_sub)
                .orElseThrow(() -> new UsernameNotFoundException(kakao_sub));
    }

    public List<String> findByRefrigSomething(){
        List<ManageProductEntity> manageProductEntity = manageProductRepository.findAll();
        List<String> emails = new ArrayList<>();
        for (int i = 0; i < manageProductEntity.size(); i++){
            if (manageProductEntity.get(i).getItemRemainingDate() <= 3){
                emails.add(manageProductEntity.get(i).getEmail());
            }
        }
        return emails;

    }

}
