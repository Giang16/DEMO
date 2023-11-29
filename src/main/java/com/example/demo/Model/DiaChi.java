package com.example.demo.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "diachi")
public class DiaChi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "add_id")
    private Integer add_id;

    @Column(name = "SoNha")
    private String sonha;

    @Column(name = "Duong")
    private String duong;

    @Column(name = "Phuong")
    private String phuong;

    @Column(name = "Quan")
    private String quan;

    @Column(name = "ThanhPho")
    private String thanhpho;

    public DiaChi(){}

    public DiaChi(String sonha, String duong, String phuong, String quan,  String thanhpho){
        this.sonha = sonha;
        this.duong = duong;
        this.phuong = phuong;
        this.quan = quan;
        this.thanhpho = thanhpho;
    }

    public String getSonha() {
        return sonha;
    }

    public void setSonha(String sonha) {
        this.sonha = sonha;
    }

    public String getDuong() {
        return duong;
    }

    public void setDuong(String duong) {
        this.duong = duong;
    }

    public String getPhuong() {
        return phuong;
    }

    public void setPhuong(String phuong) {
        this.phuong = phuong;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }

    public String getThanhpho() {
        return thanhpho;
    }

    public void setThanhpho(String thanhpho) {
        this.thanhpho = thanhpho;
    }
}
