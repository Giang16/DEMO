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

import java.time.LocalDateTime;

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

        LocalDateTime reqdate = LocalDateTime.now();

        //Kiểm tra phiid và fid tồn tại không
        if(phiRepository.findByPhiid(reqphiid) == null || hoGiaDinhRepository.findByFid(reqfid) == null){
            return 0; //Dóng phí không thành công, không tồn tại
        }
        else if(dongPhiRepository.findByFidAndPhiid(reqfid,reqphiid) != null){
            return -1; //Phí đã được đóng
        }
        //TODO: trường hợp đóng phí ngoài hạn -> Không thành công
        else if(phiRepository.findByPhiid(reqphiid).getDatestart().isAfter(reqdate) || phiRepository.findByPhiid(reqphiid).getDateend().isBefore(reqdate)){
            return -2; //Đã hết hạn đóng
        }
        else if(phiRepository.findByPhiid(reqphiid) != null && hoGiaDinhRepository.findByFid(reqfid) != null && phiRepository.findByPhiid(reqphiid).getMoney().equals(reqmoney)){
            DongPhi newDongPhi = new DongPhi(reqphiid, reqfid, reqmoney, reqdate);
            dongPhiRepository.save(newDongPhi);
            return 1;// Dóng phí thành công
        }
        else{
            return -3; // Đóng phí không thành công (nộp không đúng số tiền hoặc trường hợp ngoại lệ)
        }
    }
}
