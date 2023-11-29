package com.example.demo.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "HoGiaDinh")
public class HoGiaDinh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_id")
    private Integer fid;

    @Column(name = "Cccd_Chuho")
    private  String cccdchuho;

//    @Transient
//    private  List<NhanKhau> listnhankhau;

    public HoGiaDinh() {
    }

    public HoGiaDinh(Integer fid, String cccdchuho) {
        this.fid = fid;
        this.cccdchuho = cccdchuho;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getCccdchuho() {
        return cccdchuho;
    }

    public void setCccdchuho(String cccdchuho) {
        this.cccdchuho = cccdchuho;
    }
}
