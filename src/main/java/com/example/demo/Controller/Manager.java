package com.example.demo.Controller;

import com.example.demo.JPARepository.HoKhauRepository;
import com.example.demo.JPARepository.NhanKhauRepository;
import com.example.demo.Model.HoKhau;
import com.example.demo.Model.NhanKhau;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Manager {
    @Autowired
    private NhanKhauRepository nhanKhauRepository;

    @Autowired
    private HoKhauRepository hoKhauRepository;

    @RequestMapping("/addnhankhau")
    public int addNhanKhau(@RequestBody NhanKhau nhankhau){
        String cccd = nhankhau.getCccd();
        String numberphone = nhankhau.getPhonenumber();
        String name = nhankhau.getName();
        String sex = nhankhau.getSex();
        Integer mahokhau = nhankhau.getMahokhau();

        //Kiểm tra cccd v sđt trong CSDL nếu không trừng thì thêm vào CSDL
        if (nhanKhauRepository.findByCccd(cccd) == null && nhanKhauRepository.findByName(name) == null) {
            // Tạo một đối tượng mới NhanKhau để lưu vào CSDL
            NhanKhau newNhanKhau = new NhanKhau();
            newNhanKhau.setCccd(cccd);
            newNhanKhau.setPhonenumber(numberphone);
            newNhanKhau.setName(name);
            newNhanKhau.setSex(sex);
            newNhanKhau.setMahokhau(mahokhau);

            //Lưu vào CSDL
            nhanKhauRepository.save(newNhanKhau);
            return 1;
        }
        else{
            return 0;
        }
    }

    @RequestMapping("/addhokhau")
    public int addHoKhau(@RequestBody HoKhau hokhau){
        Integer mahokhau = hokhau.getMahokhau();
        String chuho = hokhau.getChuho();
        String diachi = hokhau.getDiachi();

        //Kiểm tra mahokhau không có trong DL thì thêm vào DL
        if(hoKhauRepository.findByMahokhau(mahokhau) == null){
            HoKhau newhokhau = new HoKhau();
            newhokhau.setMahokhau(mahokhau);
            newhokhau.setChuho(chuho);
            newhokhau.setDiachi(diachi);

            //thêm vào DL
            hoKhauRepository.save(newhokhau);
            return 1;
        }
        else{
            return  0;
        }
    }


    @RequestMapping("/tachhokhau")
    public int tachHoKhau(@RequestBody String jsonString) {
        JSONObject requestObject = new JSONObject(jsonString);

        //Lấy thông tin hộ khẩu cũ từ user
        JSONObject hokhaucu = requestObject.getJSONObject("hokhaucu");
        Integer mahokhaucu = hokhaucu.getInt("mahokhau");
        String chuhocu = hokhaucu.getString("chuho");
        String diachicu = hokhaucu.getString("diachi");

        //Kiểm tra xem hộ khẩu cũ có tồn tại không
        HoKhau oldHoKhau = hoKhauRepository.findByMahokhau(mahokhaucu);
        if(oldHoKhau != null &&
                oldHoKhau.getChuho().equals(chuhocu) &&
                oldHoKhau.getDiachi().equals(diachicu)){
            //Hộ khẩu tồn tại và đúng các thông tin còn lại

            //Lấy thông tin hộ khẩu mới từ user
            JSONObject hokhaumoi = requestObject.getJSONObject("hokhaumoi");
            Integer mahokhaumoi = hokhaumoi.getInt("mahokhau");
            String chuhomoi = hokhaumoi.getString("chuho");
            String diachimoi = hokhaumoi.getString("diachi");

            //Kiểm tra hộ khẩu mới có tồn tại không
            HoKhau newHoKhau = hoKhauRepository.findByMahokhau(mahokhaumoi);
            if(newHoKhau == null){

                //Hộ khẩu mới không tồn tại, thêm vào dtb
                newHoKhau = new HoKhau(mahokhaumoi, chuhomoi, diachimoi);
                hoKhauRepository.save(newHoKhau);

                //Lấy thông tin cccd các Nhân Khẩu cần tách trong hộ khẩu cũ từ User
                JSONObject nhankhau = requestObject.getJSONObject("nhankhau");
                String cccd = nhankhau.getString("cccd");


                //Kiểm tra nhân khẩu có maHoKhau trùng với maHoKhau của HoKhaucu
                NhanKhau nhanKhaumoi = nhanKhauRepository.findByCccd(cccd);
                if(nhankhau != null && nhanKhaumoi.getMahokhau() != newHoKhau.getMahokhau()){
                    //Xoá nhân khẩu từ hộ khẩu cữ và thêm vào hộ khẩu mới
                    nhanKhaumoi.setMahokhau(mahokhaumoi);
                    nhanKhauRepository.save(nhanKhaumoi);
                    return 1; //Nếu set lại mahokhau thành công vào hokhaumoi trả về 1
                }

                return -1; //Nếu set lại mahokhau thành công vào hokhaumoi trả về 1


            }
            return -2;//Đã tồn tại mahokhau muốn tạo

        }
        return 0;//Hộ khẩu muốn tách không tồn tại
    }



    @RequestMapping("/deletenhankhau")
    public int deleteNhanKhau(@RequestBody NhanKhau nhankhau){
        String cccd = nhankhau.getCccd();
        String numberphone = nhankhau.getPhonenumber();
        String name = nhankhau.getName();
        String sex = nhankhau.getSex();

        //Kiểm tra nhân khẩu có tồn tại không
        NhanKhau newNhanKhau = nhanKhauRepository.findByCccd(cccd);
        if(newNhanKhau != null &&
           newNhanKhau.getPhonenumber().equals(numberphone) &&
           newNhanKhau.getName().equals(name) &&
           newNhanKhau.getSex().equals(sex)){
            nhanKhauRepository.deleteByCccd(cccd);
            return 1;
        }
        return 0;
    }

    @DeleteMapping("/deletehokhau")
    public int deleteHoKhau(@RequestBody HoKhau hokhau){
        Integer mahokhau = hokhau.getMahokhau();
        String chuho = hokhau.getChuho();
        String diachi = hokhau.getDiachi();

        //Kiểm tra thông tin nếu đúng thì xoá trả về 1
        HoKhau newHoKhau = hoKhauRepository.findByMahokhau(mahokhau);
        if(newHoKhau != null && newHoKhau.getChuho().equals(chuho) && newHoKhau.getDiachi().equals(diachi)){
            hoKhauRepository.deleteByMahokhau(mahokhau);
            return 1;
        }
        return 0;
    }
}