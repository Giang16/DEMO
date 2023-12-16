package com.example.demo.Controller;

import com.example.demo.JPARepository.PhiRepository;
import com.example.demo.Model.Phi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class PhiController {

    @Autowired
    private PhiRepository phiRepository;

    //Tạo loại phí
    @RequestMapping("/createPhi")
    public int createPhi(@RequestBody Phi phi){
        String tenphi = phi.getTenphi();
        LocalDateTime datestart = phi.getDatestart();
        LocalDateTime dateend = phi.getDateend();
        Integer money = phi.getMoney();

        Phi existingPhi = phiRepository.findByTenphiAndDatestartAndDateendAndMoney(tenphi, datestart, dateend, money);
        if(existingPhi != null){
            return 0; // Đã tồn tại loại phí
        }

        Phi newPhi = new Phi(tenphi, datestart, dateend, money);
        phiRepository.save(newPhi);
        return 1; //Tạo loại phí mới thành công
    }

    @RequestMapping("/updatePhi")
    public int updatePhi(@RequestBody Phi phi){
        Integer phiid = phi.getPhiid();
        String tenphi = phi.getTenphi();
        LocalDateTime datestart = phi.getDatestart();
        LocalDateTime dateend = phi.getDateend();
        Integer money = phi.getMoney();

        Phi existingPhi = phiRepository.findByPhiid(phiid);
        if (existingPhi == null) {
            return -1; // Không tìm thấy loại phí cần chỉnh sửa
        }

        // Kiểm tra xem thông tin chỉnh sửa có trùng với thông tin của loại phí khác không
        Phi duplicatePhi = phiRepository.findByTenphiAndDatestartAndDateendAndMoney(tenphi, datestart, dateend, money);
        if (duplicatePhi != null && !duplicatePhi.getPhiid().equals(phiid)) {
            return 0; // Thông tin chỉnh sửa đã trùng với một loại phí khác
        }

        // Cập nhật thông tin loại phí
        existingPhi.setTenphi(tenphi);
        existingPhi.setDatestart(datestart);
        existingPhi.setDateend(dateend);
        existingPhi.setMoney(money);

        phiRepository.save(existingPhi);
        return 1; // Chỉnh sửa loại phí thành công
    }

    @DeleteMapping("/deletePhi")
    public int DeletePhi(@RequestBody Phi phi){
        Integer phiid = phi.getPhiid();

        Phi existingPhi = phiRepository.findByPhiid(phiid);
        if(existingPhi == null){
            return 0;//Không tồn tại phí
        }

        phiRepository.delete(existingPhi);
        return 1; //Xoá thành công
    }
}
