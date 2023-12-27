package com.example.demo.Controller;

import com.example.demo.JPARepository.QuyRepository;
import com.example.demo.Model.Quy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class QuyController {

    @Autowired
    private QuyRepository quyRepository;

    //Tạo loại quỹ
    @RequestMapping("/createQuy")
    public int createQuy(@RequestBody Quy quy){
        String tenquy = quy.getTenquy();
        LocalDateTime dateend = quy.getDateend();
        Integer money = quy.getMoney();

        LocalDateTime datestart = LocalDateTime.now();

        Quy existingQuy = quyRepository.findByTenquyAndDateendAndMoney(tenquy,dateend, money);
        if(existingQuy != null){
            return 0; // Đã tồn tại loại quỹ
        }

        Quy newQuy = new Quy(tenquy, datestart, dateend, money);
        quyRepository.save(newQuy);
        return 1; //Tạo loại quỹ mới thành công
    }

    @RequestMapping("/updateQuy")
    public int updateQuy(@RequestBody Quy quy) {
        Integer quyid = quy.getQuyid();
        String tenquy = quy.getTenquy();
        LocalDateTime dateend = quy.getDateend();
        Integer money = quy.getMoney();

        Quy existingQuy = quyRepository.findByQuyid(quyid);

        if (existingQuy == null) {
            return -1; // Không tìm thấy loại quỹ cần chỉnh sửa
        }

        // Kiểm tra xem thông tin chỉnh sửa có trùng với thông tin của loại quỹ khác không
        Quy duplicateQuy = quyRepository.findByTenquyAndDateendAndMoney(tenquy, dateend, money);
        if (duplicateQuy != null && !duplicateQuy.getQuyid().equals(quyid)) {
            return 0; // Thông tin chỉnh sửa đã trùng với một loại quỹ khác
        }

        // Cập nhật thông tin loại quỹ
        existingQuy.setTenquy(tenquy);
        existingQuy.setDateend(dateend);
        existingQuy.setMoney(money);

        quyRepository.save(existingQuy);
        return 1; // Chỉnh sửa loại quỹ thành công
    }

    @DeleteMapping("/deleteQuy")
    public int DeletePhi(@RequestBody Quy quy){
        Integer quyid = quy.getQuyid();
        String tenquy = quy.getTenquy();

        Quy existingQuy = quyRepository.findByQuyid(quyid);
        if(existingQuy == null){
            return 0;//Không tồn tại phí
        }

        quyRepository.delete(existingQuy);
        return 1; //Xoá thành công
    }
}
