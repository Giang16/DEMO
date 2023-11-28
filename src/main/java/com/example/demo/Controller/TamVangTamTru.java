package com.example.demo.Controller;

import com.example.demo.JPARepository.NhanKhauRepository;
import com.example.demo.JPARepository.TamTruRepository;
import com.example.demo.JPARepository.TamVangRepository;
import com.example.demo.Model.NhanKhau;
import com.example.demo.Model.TamTru;
import com.example.demo.Model.TamVang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TamVangTamTru {

    @Autowired
    private TamVangRepository tamVangRepository;

    @Autowired
    private TamTruRepository tamTruRepository;

    @Autowired
    private NhanKhauRepository nhanKhauRepository;

    @RequestMapping("/tamTruTamVang")
    public int TamTruTamVang(@RequestBody NhanKhau nhanKhau){
        //Lấy thông tin muốn kiểm tra tạm vắng từ user
        String reqCCCD = nhanKhau.getCccd();

        //Kiểm tra nhân khẩu muốn kiểm tra tồn tại trong dtb khong
        NhanKhau dbNhanKhau = nhanKhauRepository.findByCccd(reqCCCD);
        if(dbNhanKhau == null){
            //Không tồn tại -> Tạm trú: thêm bản ghi vào table TamTru

            //TODO: Gud, cần thêm API để gỡ tạm trú
            TamTru tamtru = new TamTru(reqCCCD, 1);
            tamTruRepository.save(tamtru);
            return 2;// Đăng ký tạm trú thành công
        }

        //Tồn tại -> Tạm vắng: thêm bản ghi vào table TamVang
        //TODO: Gud, cần thêm API để gỡ tạm vắng
        TamVang tamvang = new TamVang(reqCCCD,1);
        tamVangRepository.save(tamvang);
        return 1; //Đăng ký tạm vắng thành công
    }

}
