package com.example.demo.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table (name = "Quy")
public class Quy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quy_id") private Integer quyid;

    @Column(name = "tenquy") private String tenquy;

    @Column(name = "Date_start") private LocalDateTime datestart;

    @Column(name = "Date_end") private LocalDateTime dateend;

    @Column(name = "Money") private Integer money; // Số tiền mong muốn vận động được

    public  Quy(){
    }

    public Quy(String tenquy, LocalDateTime datestart, LocalDateTime dateend, Integer money){
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

    public LocalDateTime getDatestart() {
        return datestart;
    }

    public void setDatestart(LocalDateTime datestart) {
        this.datestart = datestart;
    }

    public LocalDateTime getDateend() {
        return dateend;
    }

    public void setDateend(LocalDateTime dateend) {
        this.dateend = dateend;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}
