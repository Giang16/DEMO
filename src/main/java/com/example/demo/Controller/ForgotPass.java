package com.example.demo.Controller;

import com.example.demo.JPARepository.AccountRepository;
import com.example.demo.Model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ForgotPass {
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/forgotpass")
    public String forgotPass(@RequestBody Account account){
        //Nhập username và cccd
        String username = account.getUsername();
        String cccd = account.getCccd();

        //Kiểm tra đúng usename và cccd trong CSDL thì trả về pass
        Account newAccount = accountRepository.findByUsername(username);
        if(newAccount != null && newAccount.getCccd().equals(cccd)){
            return newAccount.getPassword();
        }
        return "Error";
    }
}
