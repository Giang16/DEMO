package com.example.demo.Controller;

import com.example.demo.JPARepository.TaiKhoanRepository;
import com.example.demo.Model.TaiKhoan;
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
    private TaiKhoanRepository taiKhoanRepository;
    @PostMapping("/signup")
    public String signUp(@RequestBody TaiKhoan taikhoan) {
        JSONObject response = new JSONObject();

        //Lấy thông tin đăng kí từ user
        String reqUsername = taikhoan.getUser();
        String reqPassword = taikhoan.getHashkey();

        Integer reqPermission = taikhoan.getLvadmin();


        if (taiKhoanRepository.findByUser(reqUsername) != null ) {
            response.put("code","SIGNUP003");
            return response.toString();
            // Đã tồn tại username hoặc cccd (Đăng ký không thành công)
        }
        //TODO: Tạo code ở API này cho giống tài liệu cũ, giảm rework cho team FE.
        //Tạo tài khoản mới
        TaiKhoan newAccount = new TaiKhoan();
        newAccount.setUser(reqUsername);
        newAccount.setHashkey(reqPassword);
        newAccount.setLvadmin(reqPermission);

        taiKhoanRepository.save(newAccount);

        if(reqPermission.equals(0)) {
            response.put("code","SIGNUP000");
            //Đăng ký là Tổ Trưởng
        } else if (reqPermission.equals(1)) {
            response.put("code","SIGNUP001");
            //Đăng ký là Tổ Phó
        } else {
            response.put("code","SIGNUP002");
            //Là Ban quản lý
        }
        return response.toString();
    }
}
