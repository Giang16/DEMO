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
        String reqUsername = account.getUsername();
        String reqPassword = account.getPassword();
        String reqCCCD = account.getCccd();

        //Kiểm tra tk có trong table account
        Account dbAccount = accountRepository.findByUsername(reqUsername);
        if(dbAccount != null && dbAccount.getPassword().equals(reqPassword) && dbAccount.getCccd().equals(reqCCCD)){
            //Tồn tại tk và nhập mk mới để đổi
            String newPassword = account.getNewPassword();
            String confirmNewPassword = account.getConfirmPassword();

            /*TODO: Việc nhập mật khẩu và xác nhận mật khẩu đúng hay sai sẽ do bên Dũng kiểm tra trước khi gửi request đến API
            TODO: như vậy sẽ không có confirm password được gửi đến API này -> Lỗi -1 sẽ không có.
             */

            //Nhập khớp thì thực hiện đổi mk in ra 1
            if(newPassword.equals(confirmNewPassword)){
                dbAccount.setPassword(newPassword);
                accountRepository.save(dbAccount);
                return 1;
            } else return -1; //Nhập không khớp mk mới
        }
        return 0; //Tk không tồn tại hoặc nhập sai
    }
}
