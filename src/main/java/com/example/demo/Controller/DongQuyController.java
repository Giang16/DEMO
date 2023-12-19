package com.example.demo.Controller;

import com.example.demo.JPARepository.DongQuyRepository;
import com.example.demo.JPARepository.HoGiaDinhRepository;
import com.example.demo.JPARepository.QuyRepository;
import com.example.demo.Model.DongPhi;
import com.example.demo.Model.DongQuy;
import com.example.demo.Model.Quy;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.Inet4Address;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DongQuyController {
    public class Response {
        public Integer tien;
        public String ten;
        public Response(Integer tien, String ten) {
            this.ten = ten;
            this.tien = tien;
        }
    }

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

    @GetMapping("/findAllQuy")
    public List<Quy> findAllQuy() {
        return quyRepository.findAll();
    }

    @GetMapping("/findDongQuyByQuyId")
    public List<Response> findDongQuyByQuyId(
            @RequestParam(name = "quyid") Integer quyid,
            @RequestParam(name = "orderBy",required = false) String orderBy,
            //xếp theo tên hoặc theo tiền truyền orderBy
            @RequestParam(name = "order", required = false) String order) {
        List<String> res = new ArrayList<>();
        List<Response> response = new ArrayList<>();
        if(orderBy != null) {
            if(order != null) {
                if(orderBy.equals("money") && order.equals("DESC")) {
                    res = dongQuyRepository.findHoGiaDinhDaDongQuyOrderByMoneyDESC(quyid);
                } else if (orderBy.equals("name") && order.equals("DESC")) {
                    res = dongQuyRepository.findHoGiaDinhDaDongQuyOrderByNameDESC(quyid);
                } else if (orderBy.equals("money")) {
                    res = dongQuyRepository.findHoGiaDinhDaDongQuyOrderByMoneyASC(quyid);
                } else if (orderBy.equals("name")) {
                    res = dongQuyRepository.findHoGiaDinhDaDongQuyOrderByNameASC(quyid);
                }
            } else {
                if (orderBy.equals("name")) {
                    res = dongQuyRepository.findHoGiaDinhDaDongQuyOrderByNameASC(quyid);
                } else if (orderBy.equals("money")) {
                    res = dongQuyRepository.findHoGiaDinhDaDongQuyOrderByMoneyASC(quyid);
                }
            }
        } else {
            res = dongQuyRepository.findHoGiaDinhDaDongQuyOrderByNameASC(quyid);
        }
        for (String each : res) {
            JSONObject tmp = new JSONObject(each);
            Response tempresponse = new Response(tmp.getInt("money"),tmp.getString("hovaten"));
            response.add(tempresponse);
        }
        return response;
    }

    @GetMapping("/tongTienQuyBy")
    public Integer tongTienQuyBy(@RequestParam(name = "quyid",required = false) Integer quyid) {
        return dongQuyRepository.sumQuyByQuyId(quyid);
    }

    @GetMapping("/countMoney")
    public List<Response> countMoney() {
        List<Response> res = new ArrayList<>();
        for (String each : dongQuyRepository.countMoney()) {
            JSONObject tmp = new JSONObject(each);
            Response tempresponse = new Response(tmp.getInt("money"),tmp.getString("hovaten"));
            res.add(tempresponse);
        }
        return res;
    }

}
