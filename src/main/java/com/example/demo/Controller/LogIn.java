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
public class LogIn {
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @RequestMapping("/login")
    public String logIn(@RequestBody TaiKhoan account){
        JSONObject response = new JSONObject();
        String reqUsername = account.getUser();
        String reqPassword = account.getHashkey();

        TaiKhoan dbAccount = taiKhoanRepository.findByUser(reqUsername);
        if (dbAccount != null && dbAccount.getHashkey().equals(reqPassword)) {
            response.put("username",reqUsername);
            if(dbAccount.getLvadmin().equals(0)) {
                response.put("code","LOGIN000");
                response.put("permission","0");
                //Đăng nhập là TỔ TRƯỞNG
            } else if (dbAccount.getLvadmin().equals(1)) {
                response.put("code","LOGIN001");
                response.put("permission","1");
                //Đăng nhạp là TỔ PHÓ
            } else {
                response.put("code","LOGIN002");
                //Là CÁN BỘ QUẢN LÝ
            }
            return response.toString();
        } else {
            response.put("code","LOGIN003");
            //Không tồn tại trog database
            return response.toString();
        }
    }
}
