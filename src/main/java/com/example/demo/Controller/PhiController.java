package com.example.demo.Controller;

import com.example.demo.JPARepository.DongPhiRepository;
import com.example.demo.JPARepository.HoGiaDinhRepository;
import com.example.demo.JPARepository.PhiRepository;
import com.example.demo.Model.DongPhi;
import com.example.demo.Model.HoGiaDinh;
import com.example.demo.Model.Phi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PhiController {

    @Autowired
    private PhiRepository phiRepository;

    @Autowired
    private DongPhiRepository dongPhiRepository;

    @Autowired
    private HoGiaDinhRepository hoGiaDinhRepository;

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


    //trả về list các loại phí đã đóng theo fid
//    @GetMapping("/getPhis")
//    public List<Phi> getPhi() {
//        List<DongPhi> dongPhis = dongPhiRepository.findByFid(fid);
//        List<Phi> phis = new ArrayList<>(); // Khởi tạo danh sách trước khi sử dụng
//        if (dongPhis != null) {
//            for (DongPhi n : dongPhis) {
//                Phi phi = phiRepository.findByPhiid(n.getPhiid());
//                if (phi != null) {
//                    phis.add(phi);
//                }
//                // Có thể xử lý trường hợp nếu phiid không tìm thấy trong phiRepository.
//            }
//        }
//        return phis;
//    }

    @GetMapping("/AllPhi")
    public List<Phi> getPhis(){
        return phiRepository.findAll();
    }

    //Trả về các hộ gia đình theo loại phí (phiid)
    @GetMapping("/ListFidPaidByPhiid")
    public List<HoGiaDinh> getHoGiaDinh(@RequestParam(name = "phiid", required = false) Integer phiid) {
        if (phiid == null) {
            return Collections.emptyList(); // hoặc return new ArrayList<>();
        }

        List<DongPhi> dongPhis = dongPhiRepository.findByPhiid(phiid);
        List<HoGiaDinh> hoGiaDinhs = new ArrayList<>();

        if (dongPhis != null) {
            for (DongPhi n : dongPhis) {
                HoGiaDinh newHoGiaDinh = hoGiaDinhRepository.findByFid(n.getFid());
                if (newHoGiaDinh != null) {
                    hoGiaDinhs.add(newHoGiaDinh);
                }
            }
        }
        return hoGiaDinhs;
    }

}
