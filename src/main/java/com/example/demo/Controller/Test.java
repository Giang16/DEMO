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

public class Test {
    @Autowired
    private NhanKhauRepository nhanKhauRepository;

    @Autowired
    private HoGiaDinhRepository hoGiaDinhRepository;

    @Autowired
    private DiaChiRepository diaChiRepository;
    @RequestMapping("/tachhokhau")
    public int tachHoKhau(@RequestBody String jsonString){
        JSONObject requestObject = new JSONObject(jsonString);

        //Lấy thông tin hộ gia đình muốn tách từ User
        JSONObject hogiadinh = requestObject.getJSONObject("HoGiaDinhMuonTach");
        Integer fid = hogiadinh.getInt("f_id");
        String cccdchuho = hogiadinh.getString("cccd_chuho");

        //Kiểm tra hộ gia đình muốn tách tồn tại trong table HoGiaDinh không
        HoGiaDinh existingHoGiaDinh = hoGiaDinhRepository.findByFid(fid);
        if(existingHoGiaDinh == null){
            //Không tồn tại
            return 0; //Không tồn tạo HoGiaDinh muốn tách
        }
        //Tồn tại HoGiaDinh muốn tách

        //Nhập thông tin HoGiaDinh mới muốn tạo từ việc tách
        JSONObject newhogiadinh = requestObject.getJSONObject("HoGiaDinhMoi");
        Integer newfid = newhogiadinh.getInt("f_id");
        String newcccdchuho = newhogiadinh.getString("cccd_chuho");

        //Nhập thông tin Diachi của HoGiaDinh mới
        JSONObject diachi = requestObject.getJSONObject("DiaChi");
        String sonha = diachi.getString("sonha");
        String duong = diachi.getString("duong");
        String phuong = diachi.getString("phuong");
        String quan = diachi.getString("quan");
        String thanhpho = diachi.getString("thanhpho");

        //Kiểm tra HoGiaDinh mới tồn tại không
        //--> Không tồn tại: kiểm tra chủ hộ mới có trong table NhanKhau không và f_id có bằng

        HoGiaDinh newexistingHoGiaDinh = hoGiaDinhRepository.findByFid(newfid);
        NhanKhau existingNhanKhau = nhanKhauRepository.findByCccd(newcccdchuho);
        if(newexistingHoGiaDinh == null && existingNhanKhau != null && existingNhanKhau.getFid().equals(fid)){
            //Không tồn tại -> Tạo và set lại f_id trong NhanKhau của chủ hộ = newf_id
            HoGiaDinh newHoGiaDinh = new HoGiaDinh(newfid, newcccdchuho);
            hoGiaDinhRepository.save(newHoGiaDinh);




            //Kiểm tra diachi có bị trùng không
            DiaChi existingDiaChi = diaChiRepository.findBySonhaAndDuongAndPhuongAndQuanAndThanhpho(sonha, duong, phuong, quan,thanhpho);
            if(existingDiaChi == null){
                //Không ồn tại -> Tạo
                DiaChi newDiaChi = new DiaChi(newfid, sonha, duong, phuong, quan, thanhpho);
                diaChiRepository.save(newDiaChi);

            }
        }
    }
}
