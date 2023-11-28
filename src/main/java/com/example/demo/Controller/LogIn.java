package com.example.demo.Controller;

import com.example.demo.JPARepository.AccountRepository;
import com.example.demo.Model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LogIn {
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/login")
    public int logIn(@RequestBody Account account){
        String username = account.getUsername();
        String password = account.getPassword();

        Account newAccount = accountRepository.findByUsername(username);

        if (newAccount != null && newAccount.getPassword().equals(password)) {
            return 1;// Đăng nhập thành công
        }
        return 0; //Tài khoản không tồn tại hoặc nhập sai
    }

}
