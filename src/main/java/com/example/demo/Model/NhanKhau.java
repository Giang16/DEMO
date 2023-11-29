package com.example.demo.Model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

@Entity
@Table(name = "nhankhau")
public class NhanKhau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nk_id")
    private Integer nkid;

    @Column(name = "Hovatendem")
    private String hovatendem;

    @Column(name = "Ten")
    private String ten;

    @Column(name = "Gioitinh")
    private String gioitinh;

    @Column(name = "Ngaysinh")
    private String ngaysinh;

    @Column(name = "Quanhe")
    private String quanhe;
    @Column(name = "Cccd")
    private String cccd;

    @Column(name = "Sodienthoai")
    private String sodienthoai;


    @Column(name = "f_id")
    private Integer fid;


    public NhanKhau() {
    }

    public NhanKhau(String hovatendem, String ten, String gioitinh, String ngaysinh, String quanhe, String cccd, String sodienthoai, Integer fid){
        this.hovatendem = hovatendem;
        this.ten = ten;
        this.gioitinh = gioitinh;
        this.ngaysinh = ngaysinh;
        this.quanhe = quanhe;
        this.cccd = cccd;
        this.sodienthoai = sodienthoai;
        this.fid = fid;
    }

    public Integer getNkid() {
        return nkid;
    }
    public void setNkid(Integer nkid) {
        this.nkid = nkid;
    }

    public String getHovatendem() {
        return hovatendem;
    }
    public void setHovatendem(String hovatendem) {
        this.hovatendem = hovatendem;
    }

    public String getTen() {
        return ten;
    }
    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getQuanhe() {
        return quanhe;
    }

    public void setQuanhe(String quanhe) {
        this.quanhe = quanhe;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }
}
