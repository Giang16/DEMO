package com.example.demo.Controller;

import com.example.demo.JPARepository.DiaChiRepository;
import com.example.demo.JPARepository.HoGiaDinhRepository;
import com.example.demo.Model.DiaChi;
import com.example.demo.Model.HoGiaDinh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Find {
    @Autowired
    DiaChiRepository diaChiRepository;

    @Autowired
    HoGiaDinhRepository hoGiaDinhRepository;

    @GetMapping("/findAllBy")
    public List<HoGiaDinh> findAllByDuong(
            @RequestParam(name = "duong", required = false) String duong,
            @RequestParam(name = "phuong", required = false) String phuong,
            @RequestParam(name = "quan", required = false) String quan,
            @RequestParam(name = "thanhpho", required = false) String thanhpho)
    {
        List<HoGiaDinh> response = null;
        if(duong != null) {
            List<DiaChi> diaChis = diaChiRepository.findAllByDuong(duong);
            for(DiaChi diachi : diaChis) {
                response.add(hoGiaDinhRepository.findByFid(diachi.getAddid()));
            }
            return response;
        } else if (phuong != null) {
            List<DiaChi> diaChis = diaChiRepository.findAllByPhuong(phuong);
            for(DiaChi diachi : diaChis) {
                response.add(hoGiaDinhRepository.findByFid(diachi.getAddid()));
            }
            return response;
        } else if (quan != null) {
            List<DiaChi> diaChis = diaChiRepository.findAllByQuan(quan);
            for(DiaChi diachi : diaChis) {
                response.add(hoGiaDinhRepository.findByFid(diachi.getAddid()));
            }
            return response;
        } else if (thanhpho != null) {
            List<DiaChi> diaChis = diaChiRepository.findAllByThanhpho(thanhpho);
            for(DiaChi diachi : diaChis) {
                response.add(hoGiaDinhRepository.findByFid(diachi.getAddid()));
            }
            return response;
        } else {
            return response;
        }

    }
}
