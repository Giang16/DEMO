package com.example.demo.Controller;

import com.example.demo.JPARepository.AccountRepository;
import com.example.demo.Model.Account;
import org.json.JSONArray;
import org.json.JSONObject;
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
    public String signUp(@RequestBody Account account) {
        JSONObject response = new JSONObject();
        //Lấy thông tin đăng kí từ user
        String reqUsername = account.getUsername();
        String reqPassword = account.getPassword();
        String reqCCCD = account.getCccd();
        String reqPermission = account.getPermission();


        if (accountRepository.findByUsername(reqUsername) != null || accountRepository.findByCccd(reqCCCD) != null) {
            response.put("code","SIGNUP003");
            return response.toString();
            //return 0; // Đã tồn tại username hoặc cccd (Đăng ký không thành công)
        }
        //TODO: Tạo code ở API này cho giống tài liệu cũ, giảm rework cho team FE.
        //Tạo tài khoản mới
        Account newAccount = new Account();
        newAccount.setUsername(reqUsername);
        newAccount.setPassword(reqPassword);
        newAccount.setCccd(reqCCCD);
        newAccount.setPermission(reqPermission);
        accountRepository.save(newAccount);

        if(reqPermission.equals("admin")) {
            response.put("code","SIGNUP000");
            //Đăng ký là admin
        } else if (reqPermission.equals("user")) {
            response.put("code","SIGNUP001");
            //Đăng nhập là user
        } else {
            response.put("code","SIGNUP002");
            //Không admin hay user
        }
        return response.toString();
    }
}
