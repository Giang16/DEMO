package com.example.demo.Controller;

import com.example.demo.JPARepository.AccountRepository;
import com.example.demo.Model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SignUp {
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/signup")
    public int signUp(@RequestBody Account account) {
        String username = account.getUsername();
        String password = account.getPassword();
        String cccd = account.getCccd();


        if (accountRepository.findByUsername(username) != null || accountRepository.findByCccd(cccd) != null) {
            return 0;
        }

        Account newAccount = new Account();
        newAccount.setUsername(username);
        newAccount.setPassword(password);
        newAccount.setCccd(cccd);
        accountRepository.save(newAccount);
        return 1;
    }
}
