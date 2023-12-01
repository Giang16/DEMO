package com.example.demo.Controller;

import com.example.demo.JPARepository.NhanKhauRepository;
import com.example.demo.JPARepository.TaiKhoanRepository;
import com.example.demo.Model.NhanKhau;
import com.example.demo.Model.TaiKhoan;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ForgotPass {
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    @Autowired
    private NhanKhauRepository nhanKhauRepository;

    @RequestMapping("/forgotpass")
    public String forgotPass(@RequestBody String requestBody) {
        JSONObject requestJSON = new JSONObject(requestBody);
        /*
        Request:
        {
            "user":"asdad", (NOT NULL)
            "phone":"09285908204",
        }
         */
        String user = requestJSON.getString("user");
        if (user == null) return "user is null";
        else {
            TaiKhoan taiKhoan = taiKhoanRepository.findByUser(user);
            if (taiKhoan == null)
                return "User does not exist";
            else {
                String phone = requestJSON.getString("phone");
                NhanKhau nhanKhau = nhanKhauRepository.findBySodienthoai(phone);
                if (nhanKhau.getSodienthoai().equals(phone)) {
                    taiKhoan.setHashkey("@f23jfsd");
                    taiKhoanRepository.save(taiKhoan);
                    return "@f23jfsd";
                } else return "Phone number does not exist";
            }
        }
    }
}
