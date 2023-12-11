package com.example.demo.Controller;

import com.example.demo.JPARepository.NhanKhauRepository;
import com.example.demo.JPARepository.TamTruRepository;
import com.example.demo.Model.NhanKhau;
import com.example.demo.Model.TamTru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class TamTruController{
    @Autowired
    private TamTruRepository tamTruRepository;

    @Autowired
    private NhanKhauRepository nhanKhauRepository;

    @RequestMapping("/DangKiTamTru")
    public int DangKiTamTru(@RequestBody TamTru tamtru){
        String reqcccd = tamtru.getCccd();
        Date reqdatestart = tamtru.getDatestart();
        Date reqdateend = tamtru.getDateend();

        NhanKhau existingNhanKhau = nhanKhauRepository.findByCccd(reqcccd);
        if(existingNhanKhau != null){
            return 0; // Đăng kí tạm trú không thành công (vì NhanKhau đã tồn tại)
        }

        //TODO: addnhankhau
        TamTru newTamTru = new TamTru(reqcccd, reqdatestart, reqdateend);
        tamTruRepository.save(newTamTru);
        return 1; // Đăng kí tạm trú thành công
    }

}
