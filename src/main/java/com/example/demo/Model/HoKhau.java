package com.example.demo.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "hokhau")
public class HoKhau {
    @Id
    @Column(name = "mahokhau")
    private Integer mahokhau;
    @Column(name = "chuho")
    private String chuho;

    @Column(name = "diachi")
    private  String diachi;

//    @Transient
//    private  List<NhanKhau> listnhankhau;

    public HoKhau() {
    }


    public HoKhau(Integer mahokhau, String chuho, String diachi) {
        this.mahokhau = mahokhau;
        this.chuho = chuho;
        this.diachi = diachi;
    }

    public Integer getMahokhau() {
        return mahokhau;
    }

    public void setMahokhau(Integer mahokhau) {
        this.mahokhau = mahokhau;
    }

    public String getChuho() {
        return chuho;
    }

    public void setChuho(String chuho) {
        this.chuho = chuho;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

//    public List<NhanKhau> getListnhankhau() {
//        return listnhankhau;
//    }
//
//    public void setListnhankhau(List<NhanKhau> listnhankhau) {
//        this.listnhankhau = listnhankhau;
//    }
}
