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

    @RequestMapping("/tamtrutamvang")
    public int TamTruTamVang(@RequestBody NhanKhau nhanKhau){
        //Lấy thông tin muốn kiểm tra tạm vắng ừ user
        String cccd = nhanKhau.getCccd();

        //Kiểm tra nhân khẩu muốn kiểm tra tồn tại trong dtb khong
        NhanKhau esxitNhanKhau = nhanKhauRepository.findByCccd(cccd);
        if(esxitNhanKhau == null){
            //Không tồn tại -> Tạm trú: thêm bản ghi vào table TamTru
            TamTru tamtru = new TamTru(cccd, 1);
            tamTruRepository.save(tamtru);
            return 0;// Đăng ký tạm trú thành công
        }

        //Tồn tại -> Tạm vắng: thêm bản ghi vào table TamVang
        TamVang tamvang = new TamVang(cccd,1);
        tamVangRepository.save(tamvang);
        return 1; //Đăng ký tạm vắng thành công
    }

}
