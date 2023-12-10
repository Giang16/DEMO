package com.example.demo.Controller;

import com.example.demo.JPARepository.DiaChiRepository;
import com.example.demo.JPARepository.HoGiaDinhRepository;
import com.example.demo.JPARepository.NhanKhauRepository;
import com.example.demo.Model.DiaChi;
import com.example.demo.Model.HoGiaDinh;
import com.example.demo.Model.NhanKhau;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Manager {
    @Autowired
    private NhanKhauRepository nhanKhauRepository;

    @Autowired
    private HoGiaDinhRepository hoGiaDinhRepository;

    @Autowired
    private DiaChiRepository diaChiRepository;

    @RequestMapping("/addnhankhau")
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
        if (nhanKhauRepository.findByCccd(cccd) == null && nhanKhauRepository.findBySodienthoai(sodienthoai) == null) {
            // không tồn tại trong table NhanKhau -> tạo Nhân Khẩu mới
            NhanKhau newNhanKhau = new NhanKhau(hovatendem, ten, gioitinh, ngaysinh, quanhe, cccd, sodienthoai, fid);

            //Kiểm tra f_id có tồn tại trong table HoGiaDinh
            //-> Nếu tồn tại và quanhe != chuho thì thôi
            //-> Không tồn tại và quanhe == chuho thì tạo một
            HoGiaDinh existingHoGiaDinh = hoGiaDinhRepository.findByFid(fid);
            if(existingHoGiaDinh != null && !newNhanKhau.getQuanhe().equals("Chu ho"))
            {
                //Tồn tại trong table HoGiaDinh
                //Lưu vào CSDL
                nhanKhauRepository.save(newNhanKhau);
                return 1; // Thêm Nhân Khẩu thành công, không phải là chủ hộ
            }
            //Không tồn tại trong table HoGiaDinh và quanhe== "Chuho" -> Cập nhập bản ghi <f_id, cccdchuho> bằng Nhân Khẩu mới tại
            else if (existingHoGiaDinh == null && newNhanKhau.getQuanhe().equals("Chu ho"))
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
        return 0; // Nhân khẩu đã tồn tại hoặc nhân khẩu trùng số điện thoại hoặc
                    /* Mấu thuẫn trong quan hệ (lỗi ở người nhập)
                    VD: tồn tại trong HoGiaDinh nhưng người này lại có quanhe == "Chu ho"
                    hoặc người này có quanhe != "chu ho" nhưng lại đã tồn tại trong HoGiaDinh */
    }
    //TODO: Nếu thêm Nhân Khẩu là thành viên thì không cần nhập địa chỉ
    //TODO: Nếu thêm Nhân Khẩu là chủ hộ thì bắt buộc nhập địa chỉ


    @RequestMapping("/tachhokhau")
    public int tachHoKhau(@RequestBody String jsonString){
        JSONObject requestObject = new JSONObject(jsonString);

        //Lấy thông tin hộ gia đình muốn tách từ User
        JSONObject hogiadinh = requestObject.getJSONObject("HoGiaDinhMuonTach");
        Integer fid = hogiadinh.getInt("f_id");
        String cccdchuho = hogiadinh.getString("cccd_chuho");

        //Kiểm tra hộ gia đình muốn tách tồn tại trong table HoGiaDinh không
        HoGiaDinh existingHoGiaDinh = hoGiaDinhRepository.findByFidAndCccdchuho(fid, cccdchuho);
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

        //Kiểm tra HoGiaDinh mới tồn tại không và địa chỉ bị trủng không
        HoGiaDinh newexistingHoGiaDinh = hoGiaDinhRepository.findByFid(newfid);
        NhanKhau existingNhanKhau = nhanKhauRepository.findByCccd(newcccdchuho);
        DiaChi existingDiaChi = diaChiRepository.findBySonhaAndDuongAndPhuongAndQuanAndThanhpho(sonha, duong, phuong, quan,thanhpho);
        if(newexistingHoGiaDinh == null &&
                existingNhanKhau != null &&
                existingNhanKhau.getFid().equals(fid) &&
                existingDiaChi == null){
            /*Chưa tồn tại HoGiaDinh mới &&
             tồn tại chủ hộ trong table NhanKhau &&
             chủ hộ đó có f_id = f_id muốn tách &&
             không trùng địa chỉ
             */
            HoGiaDinh newHoGiaDinh = new HoGiaDinh(newfid, newcccdchuho);
            hoGiaDinhRepository.save(newHoGiaDinh);

            //set lại f_id đối tượng chủ hộ muốn tách = newfid và quanhe == "Chu ho" lưu lại vào table NhanKhau
            existingNhanKhau.setFid(newfid);
            existingNhanKhau.setQuanhe("Chu ho");
            nhanKhauRepository.save(existingNhanKhau);

            DiaChi newDiaChi = new DiaChi(newfid, sonha, duong, phuong, quan, thanhpho);
            diaChiRepository.save(newDiaChi);

            return 1; //Tách HoGiaDinh thành công
        }
        return -1; /* Tồn tại HoGiDinh muốn tách
                nhưng (đã tồn tại HoGiaDinh mới(trùng f_id) ||
                      thông tin chủ hộ mới không nằm trong HoGiaDinh cũ ||
                      địa chủ HoGiaDinh mới bị tùng) */
    }

    @RequestMapping("/chuyennhankhau")
    public int ChuyenNhanKhau(@RequestBody String jsonString) {
        JSONObject requestObject = new JSONObject(jsonString);

        //Lấy thông tin Nhân Khẩu từ HoGiaDinh nào muốn chuyển
        JSONObject nhankhau = requestObject.getJSONObject("NhanKhauMuonChuyen");
        String cccd = nhankhau.getString("cccd");
        Integer nkfid = nhankhau.getInt("f_id");
        // Nhập quan hệ mới đối với chủ hộ mới
        String quanhe = nhankhau.getString("quanhedoivoichuhomoi");

        //Lấy thông tin HoGiaDinh muốn chuyển đến
        JSONObject hogiadinh = requestObject.getJSONObject("HoGiaDinhMuonChuyenDen");
        Integer fid = hogiadinh.getInt("f_id");
        String cccdchuho = hogiadinh.getString("cccd_chuho");


        //Kiểm tra nhân khẩu muốn chuyển có tồn tại trong table NhanKhau không
        NhanKhau existingNhanKhau = nhanKhauRepository.findByCccdAndFid(cccd, nkfid);
        if (existingNhanKhau == null) {
            return 0; //Không tồn tại nhân khẩu muốn chuyển
        }
        //Tồn tại nhân khẩu muốn chuyển
        // -> Thực hiện kiểm tra HoGiaDinh muốn chuyển đến tồn tại không
        HoGiaDinh existingHoGiaDinh = hoGiaDinhRepository.findByFidAndCccdchuho(fid, cccdchuho);
        if (existingHoGiaDinh == null && nkfid.equals(fid)) {
            return -1; // Không tồn tại hộ gia đình muốn chuyển đến hoặc nkfid = fid
        }

        // Thực hiện chuyển NhanKhau qua HoGiaDinh muốn chuyển đến -> lưu lại
        existingNhanKhau.setFid(fid);
        existingNhanKhau.setQuanhe(quanhe);
        nhanKhauRepository.save(existingNhanKhau);
        return 1; //Chuyển Nhân khẩu thành công
    }

    @Transactional
    @DeleteMapping("/deletenhankhau")
    public int deleteNhanKhau(@RequestBody String jsonString){
        //Nhập thông tin nhân khẩu muốn xoá
        JSONObject requestObject = new JSONObject(jsonString);

        JSONObject nhankhau = requestObject.getJSONObject("NhanKhauMuonXoa");
        Integer fid = nhankhau.getInt("f_id");
        String cccd = nhankhau.getString("cccd");

        //Kiểm tra nhân khẩu có tồn tại không
        NhanKhau existingNhanKhau = nhanKhauRepository.findByCccdAndFid(cccd, fid);
        if(existingNhanKhau == null){
            return 0; // Không tồn tại Nhân Khẩu
        }
        //Tồn tại

        //Kiểm tra quanhe
        // + quanhe != chuho thì xoá bth
        // + quanhe == chuho thì trong HoGiaDinh phải chuyển thành vin khác làm chủ hộ

        if(!existingNhanKhau.getQuanhe().equals("Chu ho")){
            //Quan hệ != Chủ hộ -> Xoa bình thường
            nhanKhauRepository.delete(existingNhanKhau);
            return 1; //Xoá thành công nhân khẩu không phải là chủ hộ
        }
        //Trường hợp là chủ hộ -> Cần nhập cccd của chủ hộ mới và kiểm tra chủ hộ mới phải = fid

        //Lấy thông tin chủ hộ mới
        JSONObject chuhomoi = requestObject.getJSONObject("ChuHoMoi");
        String cccdchuhomoi = chuhomoi.getString("cccd_chuho");

        //Kiểm tra chuhomoi phải tồn tại trong table NhanKhau và chfid = fid
        NhanKhau existingChuHoMoi = nhanKhauRepository.findByCccdAndFid(cccdchuhomoi, fid);
        if(existingChuHoMoi == null){
            return -1; // chuhomoi không tồn tại hoặc không cùng trong gia đình
        }
        //Tồn tại -> set lại chủ hộ trong HoGiaDinh với cccd của chủ hộ mới
        //           set lại quan hệ chuhomoi trong NhanKhau la chu ho
        HoGiaDinh hogiadinh = hoGiaDinhRepository.findByFid(fid);
        hogiadinh.setCccdchuho(cccdchuhomoi);
        hoGiaDinhRepository.save(hogiadinh);

        NhanKhau nhankhaumoi = nhanKhauRepository.findByCccd(cccdchuhomoi);
        nhankhaumoi.setQuanhe("Chu ho");
        nhanKhauRepository.save(nhankhaumoi);

        //Xoá nhân khẩu muốn xoá
        nhanKhauRepository.delete(existingNhanKhau);
        return 2; //Xoá thành công nhân khẩu là chủ hộ (đã đổi chủ hộ cho thành viên trong gia đình)
    }
    //TODO: Thiếu trường hợp xoá nhân khẩu là chủ hộ mà không có thành viên trong gia đinh
    //TODO: -> Khi xoá thì xoá cả HoGiaDinh và Diachi của nhân khẩu đó

    //TODO: Khi xoá nhân khẩu là chủ hộ -> set quanhe == chuho cho thành viên
    //TODO: Nhưng chưa set quanhe các thành viên trong gia đình đối với chủ ho mới


    @RequestMapping("/modify")
    public int modily(@RequestBody String jsonString){
        JSONObject requestObject = new JSONObject(jsonString);

        //Lấy thông tin nhân khẩu muốn set từ user
        JSONObject nhankhau = requestObject.getJSONObject("NhanKhau");
        String cccd = nhankhau.getString("cccd");
        Integer nkfid = nhankhau.getInt("f_id");


        //Kiểm tra cccd có tồn tại không
        NhanKhau existingNhanKhau = nhanKhauRepository.findByCccdAndFid(cccd, nkfid);
        if(existingNhanKhau == null ){
            return 0;//Không tồn tại cccd
        }
        //Tồn tại -> Set lại thông tin từ user nhập vào trừ f_id, cccd, quanhe; nếu muốn sửa 3 thuộc tính này thì deletenhankhau sau đó addnhankhau
        //Khi sửa sdt th phải kiểm tra sdt phải duy nhất

        JSONObject editNhanKhau = requestObject.getJSONObject("ChinhSua");
        String hovatendem = editNhanKhau.getString("hovatendem");
        String ten = editNhanKhau.getString("tem");
        String gioitinh = editNhanKhau.getString("gioitinh");
        String ngaysinh = editNhanKhau.getString("ngaysinh");
        String sodienthoai = editNhanKhau.getString("sodienthoai");


        //Kiểm tra sodienthoai trùng không
        NhanKhau NhanKhauSdt = nhanKhauRepository.findBySodienthoai(sodienthoai);
        if(NhanKhauSdt != null){
            return -1; // SoDienthoai muốn sửa bị trùng
        }
        existingNhanKhau.setHovatendem(hovatendem);
        existingNhanKhau.setTen(ten);
        existingNhanKhau.setGioitinh(gioitinh);
        existingNhanKhau.setNgaysinh(ngaysinh);
        existingNhanKhau.setSodienthoai(sodienthoai);
        nhanKhauRepository.save(existingNhanKhau);
        return 1; // Sửa thông tin thành công
    }
}
