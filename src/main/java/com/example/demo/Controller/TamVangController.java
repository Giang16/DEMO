package com.example.demo.Controller;

import com.example.demo.JPARepository.NhanKhauRepository;
import com.example.demo.JPARepository.TamTruRepository;
import com.example.demo.JPARepository.TamVangRepository;
import com.example.demo.Model.NhanKhau;
import com.example.demo.Model.TamTru;
import com.example.demo.Model.TamVang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class TamVangController {

    @Autowired
    private TamVangRepository tamVangRepository;

    @Autowired
    private NhanKhauRepository nhanKhauRepository;

    @Autowired
    private TamTruRepository tamTruRepository;

    @RequestMapping("/DangKiTamVang")
    public int DangKiTamVang(@RequestBody TamVang tamvang){
        String reqcccd = tamvang.getCccd();
        Date reqdatestart = tamvang.getDatestart();
        Date reqdateend = tamvang.getDateend();

        NhanKhau existingNhanKhau = nhanKhauRepository.findByCccd(reqcccd);
        if(existingNhanKhau == null){
            return 0; // Đăng kí tạm vắng không thành công (Không tồn tại nhân khẩu)
        }
        //TODO:THêm TH đã đăng kí tạm trú ko đăng kí tạm vắng được
        else if(tamTruRepository.findByCccd(reqcccd) != null){
            return -1; //Nhân khẩu đã đăng kí tặm vắng
        }
        TamVang newTamVang = new TamVang(reqcccd, reqdatestart, reqdateend);
        tamVangRepository.save(newTamVang);
        return 1; //Đăng kí tạm vắng thành công
    }
}
