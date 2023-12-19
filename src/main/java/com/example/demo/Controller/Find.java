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

    public class Response {
        public Integer magiadinh;
        public String tenchuho;
        public String diachi;

        public Response(Integer magiadinh, String tenchuho, String diachi) {
            this.diachi = diachi;
            this.magiadinh = magiadinh;
            this.tenchuho = tenchuho;
        }
    }

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

//    @GetMapping("/findAllFid")
//    public List<Response> findAllFid() {
//        List<HoGiaDinh> temp = hoGiaDinhRepository.findAllFid();
//        List<Response> ress = new ArrayList<>();
//
//        for (HoGiaDinh each : temp) {
//            Integer fid = each.getFid();
//            String cccd = each.getCccdchuho();
//            System.out.println(fid);
//            NhanKhau nhanKhau = nhanKhauRepository.findByCccd(cccd);
//            DiaChi diaChi = diaChiRepository.findByAddid(fid);
//
//            Response response = new Response(fid,nhanKhau.getHovatendem() + " " + nhanKhau.getTen(),diaChi.getSonha() + " " + diaChi.getDuong());
//            ress.add(response);
//        }
//
//        return ress;
//    }

    @GetMapping("/findAllFid")
    public List<Response> Test() {
        List<String> res = hoGiaDinhRepository.findAllFidNew();
        List<Response> ress = new ArrayList<>();
        for (String each: res) {
            JSONObject eachJ = new JSONObject(each);
            Response eachR = new Response(eachJ.getInt("fid"),eachJ.getString("hovaten"), eachJ.getString("diachi"));
            ress.add(eachR);
        }
        return ress;
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
