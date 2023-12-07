package com.example.demo.Controller;

import com.example.demo.Function.EmailSender;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.Function.EmailSender.GenOTP;

@RestController
@RequestMapping("/api")
public class ReceiveOTP {
    @Autowired
    private EmailSender emailSender;

    @GetMapping("/revOTP")
    public String revOTP(@RequestBody String requestBody) {
        JSONObject requestJSON = new JSONObject(requestBody);
        String email;
        try{
            email = requestJSON.getString("email");
            emailSender.sendOTPEmail(email,"OTP",GenOTP(email));
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    @GetMapping("/verifyOTP")
    public String verifyOTP(@RequestParam(name = "otp", required = false) String otp,
                            @RequestParam(name = "email", required = false) String email
    ) {
        if (otp != null && email != null) {
            if (GenOTP(email).equals(otp)) {
                return "1";
            } else {
                return "-1";
            }
        } else {
            return "0";
        }
    }
}
