package com.example.demo.Controller;

import com.example.demo.JPARepository.DongQuyRepository;
import com.example.demo.JPARepository.HoGiaDinhRepository;
import com.example.demo.JPARepository.QuyRepository;
import com.example.demo.Model.DongQuy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class DongQuyController {

    @Autowired
    private DongQuyRepository dongQuyRepository;

    @Autowired
    private HoGiaDinhRepository hoGiaDinhRepository;

    @Autowired
    private QuyRepository quyRepository;

    @RequestMapping("/CreateDongQuy")
    public int CreateDongQuy(@RequestBody DongQuy dongquy){
        Integer reqfid = dongquy.getFid();
        Integer reqquyid = dongquy.getQuyid();
        Integer reqmoney = dongquy.getMoney();
        Date reqdate = dongquy.getDate();

        if(hoGiaDinhRepository.findByFid(reqfid) == null || quyRepository.findByQuyid(reqquyid) == null){
            return 0; // Đóng quỹ không thành công
        }
        else if(hoGiaDinhRepository.findByFid(reqfid) != null || quyRepository.findByQuyid(reqquyid) != null){
            DongQuy newDongQuy = new DongQuy(reqquyid, reqfid, reqmoney, reqdate);
            dongQuyRepository.save(newDongQuy);
            return 1; // Đóng quỹ thành công
        }
        else{
            return -1; // Đóng quỹ không thành công (trường hợp ngoại lệ)
        }
    }

}
