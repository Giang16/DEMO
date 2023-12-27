package com.example.demo.Controller;

import com.example.demo.Function.EmailSender;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.SocketOption;
import java.nio.charset.StandardCharsets;

import static com.example.demo.Function.EmailSender.GenOTP;

@RestController
@RequestMapping("/api")
public class ReceiveOTP {
    @Autowired
    private EmailSender emailSender;

    @RequestMapping("/revOTP")
    public String revOTP(@RequestParam(name = "email", required = false) String email) {
        if(email != null) {
            try{
                emailSender.sendOTPEmail(email,"OTP",GenOTP(email));
                return "1";
            } catch (Exception e) {
                e.printStackTrace();
                return "0";
            }
        } else {
            return "-1";
        }

    }

    @RequestMapping("/verifyOTP")
    public String verifyOTP(@RequestBody String requestBody) {
        JSONObject requestJSON = new JSONObject(requestBody);
        try {
            String otp = requestJSON.getString("otp");
            String email = requestJSON.getString("email");
            if (otp != null && email != null) {
                if (GenOTP(email).equals(otp)) {
                    return "1";
                } else {
                    return "-1";
                }
            } else {
                return "0";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }
}
