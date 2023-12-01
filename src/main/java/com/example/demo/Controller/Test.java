package com.example.demo.Controller;

import com.example.demo.JPARepository.DiaChiRepository;
import com.example.demo.JPARepository.HoGiaDinhRepository;
import com.example.demo.JPARepository.NhanKhauRepository;
import com.example.demo.Model.DiaChi;
import com.example.demo.Model.HoGiaDinh;
import com.example.demo.Model.NhanKhau;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Test {
    @Autowired
    private NhanKhauRepository nhanKhauRepository;

    @Autowired
    private HoGiaDinhRepository hoGiaDinhRepository;

    @Autowired
    private DiaChiRepository diaChiRepository;

    @RequestMapping("/chuyennhankhau")
    public int ChuyenNhanKhau(@RequestBody String jsonString){
        JSONObject requestObject = new JSONObject(jsonString);

        //Lấy thông tin Nhân Khẩu từ HoGiaDinh nào muốn chuyển
        JSONObject nhankhau = requestObject.getJSONObject("NhanKhau");
        String cccd = nhankhau.getString("cccd");
        Integer nkfid = nhankhau.getInt("f_id");
        // Nhập quan hệ mới đối với chủ hộ mới
        String quanhe = nhankhau.getString("quanhedoivoichuhomoi");

        //Lấy thông tin HoGiaDinh muốn chuyển đến
        JSONObject hogiadinh = requestObject.getJSONObject("HoGiaDinh");
        Integer fid = hogiadinh.getInt("f_id");
        String cccdchuho = hogiadinh.getString("cccd_chuho");


        //Kiểm tra nhân khẩu muốn chuyển có tồn tại trong table NhanKhau không
        NhanKhau existingNhanKhau = nhanKhauRepository.findByCccdAndFid(cccd, nkfid);
        if(existingNhanKhau == null){
            return 0; //Không tồn tại nhân khẩu muốn chuyển
        }
        //Tồn tại nhân khẩu muốn chuyển
        // -> Thực hiện kiểm tra HoGiaDinh muốn chuyển đến tồn tại không
        HoGiaDinh existingHoGiaDinh = hoGiaDinhRepository.findByFidAndCccdchuho(fid, cccdchuho);
        if(existingHoGiaDinh == null && nkfid.equals(fid)){
            return -1; // Không tồn tại hộ gia đình muốn chuyển đến hoặc nkfid = fid
        }

        // Thực hiện chuyển NhanKhau qua HoGiaDinh muốn chuyển đến -> lưu lại
        existingNhanKhau.setFid(fid);
        existingNhanKhau.setQuanhe(quanhe);
        nhanKhauRepository.save(existingNhanKhau);
        return 1; //Chuyển Nhân khẩu thành công
    }
}
