package com.example.demo.Controller;

import com.example.demo.JPARepository.DongPhiRepository;
import com.example.demo.JPARepository.HoGiaDinhRepository;
import com.example.demo.JPARepository.PhiRepository;
import com.example.demo.Model.DongPhi;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.KEMSpi;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DongPhiController {
    public class Response {
        public String magiadinh;
        public String tenchuho;
        public Response(String magiadinh,String tenchuho) {
            this.magiadinh = magiadinh;
            this.tenchuho = tenchuho;
        }
    }

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

    @RequestMapping("/analyzePhi")
    public String countHoGiaDinhDaDongPhi(@RequestParam(name = "phiid")Integer phiid) {
        Integer count =  dongPhiRepository.countHoGiaDinhDaDongPhi(phiid);
        Integer total = hoGiaDinhRepository.countHoGiaDinh();
        JSONObject res = new JSONObject();
        res.put("analyzePhi",count.toString() + "/" + total.toString());
        return res.toString();
    }

    @RequestMapping("/listFidOfPhi")
    public List<Response> findHoGiaDinhDaDongPhi(@RequestParam(name = "phiid")Integer phiid) {
        List<String> tmp = dongPhiRepository.findHoGiaDinhDaDongPhi(phiid);
        List<Response> res = new ArrayList<>();
        for(String each : tmp) {
            JSONObject jsonObject = new JSONObject(each);
            Response response = new Response(jsonObject.getString("fid"),jsonObject.getString("hovaten"));
            res.add(response);
        }
        return res;

    }
}
