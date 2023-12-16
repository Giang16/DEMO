package com.example.demo.Model;

import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "Dong_Quy")
public class DongQuy {
    @Id
    @Column(name = "quy_id", unique = true)
    private Integer quyid;

    @Column(name = "f_id", unique = true)
    private Integer fid;

    @Column(name = "Money")
    private Integer money;

    @Column(name = "Date")
    private LocalDateTime date;

    public DongQuy(){}

    public DongQuy(Integer quyid, Integer fid, Integer money, LocalDateTime date){
        this.quyid = quyid;
        this.fid = fid;
        this.money = money;
        this.date = date;
    }

    public Integer getQuyid() {
        return quyid;
    }

    public void setQuyid(Integer quyid) {
        this.quyid = quyid;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
