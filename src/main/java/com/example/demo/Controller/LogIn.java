//package com.example.demo.Controller;
//
//import com.example.demo.JPARepository.AccountRepository;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api")
//public class LogIn {
//    @Autowired
//    private AccountRepository accountRepository;
//
//    @RequestMapping("/login")
//    public String logIn(@RequestBody Account account){
//        JSONObject response = new JSONObject();
//        String reqUsername = account.getUsername();
//        String reqPassword = account.getPassword();
//
//        Account dbAccount = accountRepository.findByUsername(reqUsername);
//        //TODO: Sẽ bắt đầu sửa code trả về ở đây để xác định luồng.
//        if (dbAccount != null && dbAccount.getPassword().equals(reqPassword)) {
//            response.put("username",reqUsername);
//            if(dbAccount.getPermission().equals("admin")) {
//                response.put("code","LOGIN000");
//                response.put("permission","admin");
//                //Đăng nhập là admin
//            } else if (dbAccount.getPermission().equals("user")) {
//                response.put("code","LOGIN001");
//                response.put("permission","user");
//                //Đăng nhạp là user
//            } else {
//                response.put("code","LOGIN002");
//                //Không admin hay user
//            }
//            return response.toString();
//        } else {
//            response.put("code","LOGIN003");
//            //Không tồn tại trog database
//            return response.toString();
//        }
//    }
//
//}
