package com.example.demo.Controller;

import com.example.demo.JPARepository.*;
import com.example.demo.Model.DiaChi;
import com.example.demo.Model.HoGiaDinh;
import com.example.demo.Model.NhanKhau;
import com.example.demo.Model.TamTru;
import org.json.JSONObject;
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

    @Autowired
    private TamVangRepository tamVangRepository;


    //TODO: Trước khi đăng kí tạm trú phải thêm nhân khẩu muốn tạm trú ở chức năng addnhankhau
    @RequestMapping("/DangKiTamTru")
    public int DangKiTamTru(@RequestBody TamTru tamtru){
        String reqcccd = tamtru.getCccd();
        Date reqdatestart = tamtru.getDatestart();
        Date reqdateend = tamtru.getDateend();

        NhanKhau existingNhanKhau = nhanKhauRepository.findByCccd(reqcccd);
        if(existingNhanKhau == null){
            return 0; // Đăng kí tạm trú không thành công bắt nhập lại đúng Nhân khẩu vừa thêm (vì NhanKhau không tồn tại)
        }
        //TODO:Thêm TH đã ĐK tạm vắng -> Không đăng kí tạm trú được
        else if(tamVangRepository.findByCccd(reqcccd) != null){
            return -1; //Nhân khẩu đã được đăng kí tạm vắng
        }
        TamTru newTamTru = new TamTru(reqcccd, reqdatestart, reqdateend);
        tamTruRepository.save(newTamTru);
        return 1; // Đăng kí tạm trú thành công
    }
}

