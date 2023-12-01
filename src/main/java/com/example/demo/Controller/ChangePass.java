package com.example.demo.Controller;

import com.example.demo.JPARepository.TaiKhoanRepository;
import com.example.demo.Model.TaiKhoan;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChangePass {
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @RequestMapping("/changepass")
    public String changePass(@RequestBody TaiKhoan taikhoan){
        JSONObject response = new JSONObject();
        //Nhập username pass
        String reqUsername = taikhoan.getUser();
        String reqPassword = taikhoan.getHashkey();

        response.put("user",reqUsername);
        response.put("pass",reqPassword);
        //Kiểm tra tk có trong table account
        TaiKhoan dbAccount = taiKhoanRepository.findByUser(reqUsername);

        if(dbAccount==null){
            response.put("code","CHANGEPASS001"); // Không tồn tại tài khoản trong database
            return response.toString();
        }
        else {
            response.put("code","CHANGEPASS002"); // Tồn tại tài khoản trong database
            taiKhoanRepository.save(taikhoan);
            return response.toString();
        }

    }
}
