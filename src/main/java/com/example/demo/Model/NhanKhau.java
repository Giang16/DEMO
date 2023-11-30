package com.example.demo.Model;

import jakarta.persistence.*;
import org.json.JSONObject;

@Entity
@Table(name = "nhankhau")
public class NhanKhau {
    @Id
    @Column(name = "cccd")
    private String cccd;

    @Column(name = "phonenumber")
    private String phonenumber;

    @Column(name = "name")
    private String name;

    @Column(name = "sex")
    private String sex;

    @Column(name = "mahokhau")
    private Integer mahokhau;


    public NhanKhau() {
    }

    public NhanKhau(String cccd, String phonenumber, String name, String sex, Integer mahokhau) {
        this.cccd = cccd;
        this.phonenumber = phonenumber;
        this.name = name;
        this.sex = sex;
        this.mahokhau = mahokhau;
    }

    public static String toJSONString(NhanKhau nhanKhau) {
        JSONObject response = new JSONObject();
        response.put("cccd",nhanKhau.getCccd());
        response.put("phonenumber",nhanKhau.getPhonenumber());
        response.put("name",nhanKhau.getName());
        response.put("sex",nhanKhau.getSex());
        response.put("magiadinh",nhanKhau.getMahokhau());
        return response.toString();
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getMahokhau() {
        return mahokhau;
    }

    public void setMahokhau(Integer mahokhau) {
        this.mahokhau = mahokhau;
    }
}
