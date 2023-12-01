//package com.example.demo.Controller;
//
//import com.example.demo.JPARepository.TaiKhoanRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api")
//public class ForgotPass {
//    @Autowired
//    private TaiKhoanRepository taiKhoanRepository;
//
//    @RequestMapping("/forgotpass")
//    public String forgotPass(@RequestBody Account account){
//        //Nhập username và cccd
//        String reqUsername = account.getUsername();
//        String reqCCCD = account.getCccd();
//
//        //TODO: Chỉ cần biết mỗi CCCD của user mà lấy được tài khoản thì hơi chuối -> Cần xác thực (Có thể là TOTP nếu mình implement được)
//        //TODO: Kiểm tra đúng usename và cccd trong CSDL thì trả về pass
//
//        Account newAccount = accountRepository.findByUsername(reqUsername);
//        if(newAccount != null && newAccount.getCccd().equals(reqCCCD)){
//            return newAccount.getPassword();
//        }
//        return "Error";
//    }
//}
