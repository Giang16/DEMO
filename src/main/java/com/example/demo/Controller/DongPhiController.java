package com.example.demo.Controller;

import com.example.demo.JPARepository.DongPhiRepository;
import com.example.demo.JPARepository.HoGiaDinhRepository;
import com.example.demo.JPARepository.PhiRepository;
import com.example.demo.Model.DongPhi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class DongPhiController {

    @Autowired
    private DongPhiRepository dongPhiRepository;

    @Autowired
    private HoGiaDinhRepository hoGiaDinhRepository;

    @Autowired
    private PhiRepository phiRepository;

    @RequestMapping("/CreateDongPhi")
    public int CreateDongPhi(@RequestBody DongPhi dongphi){
        Integer reqfid = dongphi.getFid();
        Integer reqphiid = dongphi.getPhiid();
        Integer reqmoney = dongphi.getMoney();
        Date reqdate = dongphi.getDate();

        //Kiểm tra phiid và fid tồn tại không
        if(phiRepository.findByPhiid(reqphiid) == null || hoGiaDinhRepository.findByFid(reqfid) == null){
            return 0; //Dóng phí không thành công
        }

        else if(phiRepository.findByPhiid(reqphiid) != null && hoGiaDinhRepository.findByFid(reqfid) != null){
            DongPhi newDongPhi = new DongPhi(reqphiid, reqfid, reqmoney, reqdate);
            dongPhiRepository.save(newDongPhi);
            return 1;// Dóng phí thành công
        }
        else{
            return -1; // Đóng phí không thành công (trường hợp ngoại lệ)
        }
    }
}
