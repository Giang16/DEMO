package com.example.demo.Model;

import jakarta.persistence.*;


import java.util.Date;

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
    private Date date;

    public DongQuy(){}

    public DongQuy(Integer quyid, Integer fid, Integer money, Date date){
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
