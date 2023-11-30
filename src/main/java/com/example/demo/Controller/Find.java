package com.example.demo.Controller;


import com.example.demo.JPARepository.HoKhauRepository;
import com.example.demo.JPARepository.NhanKhauRepository;
import com.example.demo.Model.HoKhau;
import com.example.demo.Model.NhanKhau;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.demo.Model.NhanKhau.toJSONString;

@RestController
@RequestMapping("/api")
public class Find {
    @Autowired
    private NhanKhauRepository nhanKhauRepository;

    @Autowired
    private HoKhauRepository hoKhauRepository;

    @GetMapping("/findBy")
    public String findBy(
            @RequestParam(name = "idNumber", required = false) String idNumber,
            @RequestParam(name = "phoneNumber", required = false) String phoneNumber
    ) {
        if(idNumber != null) {
            NhanKhau response = nhanKhauRepository.findByCccd(idNumber);
            if(response != null) {
                return toJSONString(response);
            } else {
                return "no-person-match-cccd";
            }

        } else if (phoneNumber != null) {
            NhanKhau response = nhanKhauRepository.findByPhonenumber(phoneNumber);
            if(response != null) {
                return toJSONString(response);
            } else {
                return "no-person-match-phone-number";
            }
        } else {
            return "null-parameter";
        }
    }

    @GetMapping("/findAllFid")
    public List<HoKhau> findAllFid() {
        List<HoKhau> response = hoKhauRepository.findAll();
        return response;
    }
}
