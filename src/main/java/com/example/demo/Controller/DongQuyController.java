package com.example.demo.Controller;

import com.example.demo.JPARepository.DongQuyRepository;
import com.example.demo.JPARepository.HoGiaDinhRepository;
import com.example.demo.JPARepository.QuyRepository;
import com.example.demo.Model.DongPhi;
import com.example.demo.Model.DongQuy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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

        LocalDateTime reqdate = LocalDateTime.now();

        if(hoGiaDinhRepository.findByFid(reqfid) == null || quyRepository.findByQuyid(reqquyid) == null){
            return 0; // Không tồn tại hogiadinh hoặc loại phí
        }
        else if(dongQuyRepository.findByFidAndQuyid(reqfid, reqquyid) != null){
            return -1; // Gia đình đã đóng
        }

        //TODO: Đóng phí ngoài hạn -> Không thành công
        else if(quyRepository.findByQuyid(reqquyid).getDatestart().isAfter(reqdate) || quyRepository.findByQuyid(reqquyid).getDateend().isBefore(reqdate)){
            return -2; //Đã hết hạn đóng
        }
        else if(hoGiaDinhRepository.findByFid(reqfid) != null || quyRepository.findByQuyid(reqquyid) != null){
            DongQuy newDongQuy = new DongQuy(reqquyid, reqfid, reqmoney, reqdate);
            dongQuyRepository.save(newDongQuy);
            return 1; // Đóng quỹ thành công
        }
        else{
            return -2; // Đóng quỹ không thành công (trường hợp ngoại lệ)
        }
    }

}
