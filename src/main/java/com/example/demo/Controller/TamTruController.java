package com.example.demo.Controller;

import com.example.demo.JPARepository.DiaChiRepository;
import com.example.demo.JPARepository.HoGiaDinhRepository;
import com.example.demo.JPARepository.NhanKhauRepository;
import com.example.demo.JPARepository.TamTruRepository;
import com.example.demo.Model.DiaChi;
import com.example.demo.Model.HoGiaDinh;
import com.example.demo.Model.NhanKhau;
import com.example.demo.Model.TamTru;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class TamTruController{
    @Autowired
    private TamTruRepository tamTruRepository;

    @Autowired
    private NhanKhauRepository nhanKhauRepository;

    @Autowired
    private HoGiaDinhRepository hoGiaDinhRepository;

    @Autowired
    private DiaChiRepository diaChiRepository;

    @RequestMapping("/addnhankhautamtru")
    public int addNhanKhau(@RequestBody String jsonString) {
        JSONObject requestObject = new JSONObject(jsonString);
        String hovatendem = requestObject.getString("hovatendem");
        String ten = requestObject.getString("ten");
        String gioitinh = requestObject.getString("gioitinh");
        String ngaysinh = requestObject.getString("ngaysinh");
        String quanhe = requestObject.getString("quanhe");
        String cccd = requestObject.getString("cccd");
        String sodienthoai = requestObject.getString("sodienthoai");
        Integer fid = requestObject.getInt("f_id");

        //Kiểm tra nhân khẩu tồn tại trong table NhanKhau
        if (nhanKhauRepository.findByCccd(cccd) == null) {
            // không tồn tại trong table NhanKhau -> tạo Nhân Khẩu mới
            NhanKhau newNhanKhau = new NhanKhau(hovatendem, ten, gioitinh, ngaysinh, quanhe, cccd, sodienthoai, fid);

            //Kiểm tra f_id có tồn tại trong table HoGiaDinh
            //-> Nếu tồn tại và quanhe != chuho thì thôi
            //-> Không tồn tại và quanhe == chuho thì tạo một
            HoGiaDinh existingHoGiaDinh = hoGiaDinhRepository.findByFid(fid);
            if(existingHoGiaDinh != null && !newNhanKhau.getQuanhe().equals("Chủ hộ"))
            {
                //Tồn tại trong table HoGiaDinh
                //Lưu vào CSDL
                nhanKhauRepository.save(newNhanKhau);
                return 1; // Thêm Nhân Khẩu thành công, không phải là chủ hộ
            }
            //Không tồn tại trong table HoGiaDinh và quanhe== "Chuho" -> Cập nhập bản ghi <f_id, cccdchuho> bằng Nhân Khẩu mới tại
            else if (existingHoGiaDinh == null && newNhanKhau.getQuanhe().equals("Chủ hộ"))
            {
                //-> Nhập thêm thông tin Địa chị
                JSONObject diachi = requestObject.getJSONObject("DiaChi");
                String sonha = diachi.getString("sonha");
                String duong = diachi.getString("duong");
                String phuong = diachi.getString("phuong");
                String quan = diachi.getString("quan");
                String thanhpho = diachi.getString("thanhPho");

                //Kiểm tra địa chỉ có bị trùng trong table DiaChi
                DiaChi existingDiaChi = diaChiRepository.findBySonhaAndDuongAndPhuongAndQuanAndThanhpho(sonha, duong, phuong, quan, thanhpho);
                if (existingDiaChi != null) {
                    return -1; // Trùng địa chỉ trong table DiaChi (nhân khẩu là chủ hộ)
                }
                //Lưu vào CSDL
                nhanKhauRepository.save(newNhanKhau);

                DiaChi newDiaChi = new DiaChi(fid, sonha, duong, phuong, quan, thanhpho);
                diaChiRepository.save(newDiaChi);

                HoGiaDinh newHoGiaDinh = new HoGiaDinh(fid,cccd);
                hoGiaDinhRepository.save(newHoGiaDinh);

                return 2; //Thêm Nhân Khẩu thành công, là chủ hộ
            }
        }
        return 0; // Nhân khẩu đã tồn tại hoặc
                    /* Mấu thuẫn trong quan hệ (lỗi ở người nhập)
                    VD: tồn tại trong HoGiaDinh nhưng người này lại có quanhe == "Chu ho"
                    hoặc người này có quanhe != "chu ho" nhưng lại đã tồn tại trong HoGiaDinh */
    }
    //TODO: Return phải trả về 2

    @RequestMapping("/DangKiTamTru")
    public int DangKiTamTru(@RequestBody TamTru tamtru){
        String reqcccd = tamtru.getCccd();
        Date reqdatestart = tamtru.getDatestart();
        Date reqdateend = tamtru.getDateend();

        NhanKhau existingNhanKhau = nhanKhauRepository.findByCccd(reqcccd);
        if(existingNhanKhau == null){
            return 0; // Đăng kí tạm trú không thành công bắt nhập lại đúng Nhân khẩu vừa thêm (vì NhanKhau không tồn tại)
        }

        //TODO: addnhankhau

        TamTru newTamTru = new TamTru(reqcccd, reqdatestart, reqdateend);
        tamTruRepository.save(newTamTru);
        return 1; // Đăng kí tạm trú thành công
    }

}

//TODO: 2
