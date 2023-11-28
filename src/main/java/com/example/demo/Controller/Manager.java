package com.example.demo.Controller;

import com.example.demo.JPARepository.HoKhauRepository;
import com.example.demo.JPARepository.NhanKhauRepository;
import com.example.demo.Model.HoKhau;
import com.example.demo.Model.NhanKhau;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
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

        //Kiểm tra cccd table NhanKhau
        if (nhanKhauRepository.findByCccd(cccd) == null) {
            // không tồn tại trong table NhanKhau -> tại NhanKhau mới
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
        return 0;
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
            return 1; //Thêm thành công
        }
        return  0; // Đã tồn tại hộ khẩu
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

                return -1; //Nhan khau muốn tach khog tồn tại


            }
            return -2;//Đã tồn tại mahokhau muốn tạo

        }
        return 0;//Hộ khẩu muốn tách không tồn tại
    }
    //Lú: Trong bước tạo hộ khẩu mới nếu ta nhập tê chủ hộ là nhân khẩu không có trong dtb nó vẫn nhận (-> Khi nhập phải nhập nhân khẩu có trong hộ khẩu cũ)


    @Transactional
    @DeleteMapping("/deletenhankhau")
    public int deleteNhanKhau(@RequestBody NhanKhau nhanKhau){
        String cccd = nhanKhau.getCccd();
        String name = nhanKhau.getName();
        String phonenumber = nhanKhau.getPhonenumber();
        String sex = nhanKhau.getSex();
        Integer mahokhau = nhanKhau.getMahokhau();
        System.out.println("x");
        //Kiểm tra nhân khẩu có ồn tại không
        NhanKhau newnhankhau = nhanKhauRepository.findByCccd(cccd);
        if(newnhankhau != null &&
           newnhankhau.getName().equals(name) &&
           newnhankhau.getPhonenumber().equals(phonenumber) &&
           newnhankhau.getSex().equals(sex) &&
           newnhankhau.getMahokhau().equals(mahokhau)){
            //Tồn tại nhân khẩu
            System.out.println("xx");
            //Xoá nhân khẩu khỏi dtb
            nhanKhauRepository.deleteByCccd(cccd);
            return 1; // Đã xoá thành công
        }
        return 0;
    }
    //Hơi lú: Hàm này khi mà xoá nhân khẩu là chủ hộ thì bên hộ khẩu chủ hộ vẫn l nó
    //=> Cần thêm: khi xoá nhân khẩu cần kiểm tra đó có phải chủ hộ không
    //              + Nếu không thì xoá bth
    //              + Nếu có thì cần nhập chủ hộ mới (là người cùng hộ khẩu chut hộ cũ) xong mới xoá
    @RequestMapping("/modify")
    public int modily(@RequestBody String jsonString){
        JSONObject requestObject = new JSONObject(jsonString);

        //Lấy thông tin nhân kẩu muốn edit từ user
        JSONObject nhankhau = requestObject.getJSONObject("NhanKhau");
        String cccd = nhankhau.getString("cccd");

        System.out.println("x");

        //Kiểm tra cccd có tồn tại không
        NhanKhau exsitNhanKhau = nhanKhauRepository.findByCccd(cccd);
        if(exsitNhanKhau == null ){
            return 0;//Không tồn tại cccd
        }

        System.out.println("xx");

        //Lấy các Nhân Khẩu có cùng mahokhau của Nhân Khẩu trên trong dtb
        List<NhanKhau> listNhanKhau = nhanKhauRepository.findByMahokhau(exsitNhanKhau.getMahokhau());

        //Hiện listNhanKhau có cùng mahokhau (làm sao để hiển thị ra màn hình thì chưa bt)
        for(NhanKhau n : listNhanKhau){
            System.out.println(n.getCccd());
            System.out.println(n.getName());
        }

        System.out.println("xxx");
        //Nhập cccd Nhân khẩu muốn thay đổi trong listNhanKhau
        JSONObject nhanKhauMuonDoi = requestObject.getJSONObject("NhanKhauMuonDoi");
        String cccdNhanKhauMuonDoi = nhanKhauMuonDoi.getString("cccd");

        System.out.println("xxxx");
        //Kiểm tra cccdNhanKhauMuonDoi có trong listNhanKhau
        NhanKhau exitNhanKhau1 = nhanKhauRepository.findByCccd(cccdNhanKhauMuonDoi);
        if(exitNhanKhau1 == null){
            return -2;//Nhập sai
        }
        System.out.println("xxxxx");
        for(NhanKhau n : listNhanKhau){
            if(exitNhanKhau1.equals(n)){
                //cccdNhanKhauMuonDoi tồn tại trong list

                //Nhập vào các trường muốn đổi từ User
                JSONObject changeNhanKhau = requestObject.getJSONObject("ChangeNhanKhau");
                String changename = changeNhanKhau.getString("name");
                String changephonenumber = changeNhanKhau.getString("phonenumber");
                String changesex = changeNhanKhau.getString("sex");


                //set lại thông tin muốn đổi vào dtb
                n.setName(changename);
                n.setPhonenumber(changephonenumber);
                n.setSex(changesex);
                nhanKhauRepository.save(n);
                return 1;// thay đổi thông tin nhân khẩu trong hộ khẩu thành công thông qua một thành viên trong nhân khẩu
            }
        }
        return -1;//Không tồn tại Nhân khau muon đổi trong list
    }
}