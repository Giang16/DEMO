package com.example.demo.Controller;

import com.example.demo.JPARepository.DiaChiRepository;
import com.example.demo.JPARepository.HoGiaDinhRepository;
import com.example.demo.JPARepository.NhanKhauRepository;
import com.example.demo.Model.DiaChi;
import com.example.demo.Model.HoGiaDinh;
import com.example.demo.Model.NhanKhau;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public String findAllFid() {
        List<HoGiaDinh> temp = hoGiaDinhRepository.findAllFid();
        List<String> ress = new ArrayList<>();
        for(HoGiaDinh each : temp) {
            NhanKhau nhanKhau = nhanKhauRepository.findByCccd(each.getCccdchuho());
            DiaChi diaChi = diaChiRepository.findByAddid(each.getFid());
            
            JSONObject tmp = new JSONObject();
            tmp.put("tenchuho",nhanKhau.getHovatendem() + " " + nhanKhau.getTen());
            tmp.put("diachi",diaChi.getSonha() + " " + diaChi.getDuong());
            tmp.put("fid",each.getFid());

            ress.add(tmp.toString());
        }
        return ress.toString();
    }

    @GetMapping("/findNhanKhauBy")
    public List<NhanKhau> findByFid(@RequestParam Integer fid) {
        return nhanKhauRepository.findAllByFid(fid);
    }

    @GetMapping("/findNhanKhau")
    public List<NhanKhau> findNhanKhau(@RequestParam(name = "cccd", required = false) String cccd,
                                 @RequestParam(name = "sdt", required = false) String sdt) {
        List<NhanKhau> res = null;
        if(cccd != null && sdt != null) {
            res.add(nhanKhauRepository.findNhanKhauBySdtVaCccd(sdt,cccd));
        } else if(cccd != null) {
            res.add(nhanKhauRepository.findNhanKhauByCccd(cccd));
        } else if (sdt != null) {
            res.add(nhanKhauRepository.findNhanKhauBySdt(sdt));
        } else {
            res = nhanKhauRepository.findTop100();
            if(res.size() > 30) {
                res = res.subList(0,30);
            }
        }
        return res;
    }


}
