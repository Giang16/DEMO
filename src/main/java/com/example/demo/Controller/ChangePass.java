package com.example.demo.Controller;

import com.example.demo.JPARepository.AccountRepository;
import com.example.demo.Model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChangePass {
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/changepass")
    public int changePass(@RequestBody Account account){
        //Nhập username pass cddd
        String usernaem = account.getUsername();
        String pass = account.getPassword();
        String cccd = account.getCccd();

        //Kiểm tra tk có trong table account
        Account newAccount = accountRepository.findByUsername(usernaem);
        if(newAccount != null && newAccount.getPassword().equals(pass) && newAccount.getCccd().equals(cccd)){
            //Tồn tại tk và nhập mk mới để đổi
            String newpass = account.getNewPassword();
            String confirmnewpass = account.getConfirmPassword();

            //Nhập khớp thì thực hiện đổi mk in ra 1
            if(newpass.equals(confirmnewpass)){
                newAccount.setPassword(newpass);
                accountRepository.save(newAccount);
                return 1;
            }
            else return -1; //Nhập không khớp mk mới
        }
        return 0; //Tk không tồn tại hoặc nhập sai
    }
}
