package com.example.demo.Model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table (name = "Quy")
public class Quy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quy_id") private Integer quyid;

    @Column(name = "tenquy") private String tenquy;

    @Column(name = "Date_start") private Date datestart;

    @Column(name = "Date_end") private Date dateend;

    @Column(name = "Money") private Integer money; // Số tiền mong muốn vận động được

    public  Quy(){
    }

    public Quy(String tenquy, Date datestart, Date dateend, Integer money){
        this.tenquy = tenquy;
        this.datestart = datestart;
        this.dateend = dateend;
        this.money = money;
    }

    public Integer getQuyid() {
        return quyid;
    }

    public void setQuyid(Integer quyid) {
        this.quyid = quyid;
    }

    public String getTenquy() {
        return tenquy;
    }

    public void setTenquy(String tenquy) {
        this.tenquy = tenquy;
    }

    public Date getDatestart() {
        return datestart;
    }

    public void setDatestart(Date datestart) {
        this.datestart = datestart;
    }

    public Date getDateend() {
        return dateend;
    }

    public void setDateend(Date dateend) {
        this.dateend = dateend;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}
