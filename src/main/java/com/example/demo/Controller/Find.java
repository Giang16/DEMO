package com.example.demo.Controller;

import com.example.demo.JPARepository.DiaChiRepository;
import com.example.demo.JPARepository.HoGiaDinhRepository;
import com.example.demo.JPARepository.NhanKhauRepository;
import com.example.demo.Model.DiaChi;
import com.example.demo.Model.HoGiaDinh;
import com.example.demo.Model.NhanKhau;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class Find {
    @Autowired
    DiaChiRepository diaChiRepository;

    @Autowired
    HoGiaDinhRepository hoGiaDinhRepository;

    @Autowired
    NhanKhauRepository nhanKhauRepository;

    @GetMapping("/findAllBy")
    public List<Integer> findAllBy(
            @RequestParam(name = "duong", required = false) String duong,
            @RequestParam(name = "phuong", required = false) String phuong,
            @RequestParam(name = "quan", required = false) String quan,
            @RequestParam(name = "thanhpho", required = false) String thanhpho)
    {
        List<Integer> res = null;
        if(duong != null) {
            List<DiaChi> diaChiList = diaChiRepository.findAllByDuong(duong);

            return diaChiList.stream()
                    .map(DiaChi::getAddid)
                    .collect(Collectors.toList());
        } else if (phuong != null) {
            List<DiaChi> diaChiList = diaChiRepository.findAllByPhuong(phuong);

            return diaChiList.stream()
                    .map(DiaChi::getAddid)
                    .collect(Collectors.toList());
        } else if (quan != null) {
            List<DiaChi> diaChiList = diaChiRepository.findAllByQuan(quan);

            return diaChiList.stream()
                    .map(DiaChi::getAddid)
                    .collect(Collectors.toList());
        } else if (thanhpho != null) {
            List<DiaChi> diaChiList = diaChiRepository.findAllByThanhpho(thanhpho);

            return diaChiList.stream()
                    .map(DiaChi::getAddid)
                    .collect(Collectors.toList());
        } else {
            return res;
        }
    }

    @GetMapping("/findAllFid")
    public List<Integer> findAllFid() {
        return hoGiaDinhRepository.findAllFid();
    }

    @GetMapping("/findNhanKhauBy")
    public List<NhanKhau> findByFid(@RequestParam Integer fid) {
        return nhanKhauRepository.findAllByFid(fid);
    }

    @GetMapping("/findNhanKhau")
    public NhanKhau findNhanKhau(@RequestParam(name = "cccd", required = false) String cccd,
                                 @RequestParam(name = "sdt", required = false) String sdt) {
        NhanKhau res = null;
        if(cccd != null && sdt != null) {
            res = nhanKhauRepository.findNhanKhauBySdtVaCccd(sdt,cccd);
        } else if(cccd != null) {
            res = nhanKhauRepository.findNhanKhauByCccd(cccd);
        } else if (sdt != null) {
            res = nhanKhauRepository.findNhanKhauBySdt(sdt);
        } else {
            res = null;
        }
        return res;
    }


}
