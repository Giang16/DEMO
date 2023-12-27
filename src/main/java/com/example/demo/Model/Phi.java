package com.example.demo.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table (name = "Phi")
public class Phi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phi_id") private Integer phiid;

    @Column(name = "tenphi") private String tenphi;

    @Column(name = "Date_start") private LocalDateTime datestart;

    @Column(name = "Date_end") private LocalDateTime dateend;

    @Column(name = "Money") private Integer money; // Số tiền theo đầu người phải đóng

    public Phi() {

    }

    public Integer getPhiid() {
        return phiid;
    }

    public void setPhiid(Integer phiid) {
        this.phiid = phiid;
    }

    public String getTenphi() {
        return tenphi;
    }

    public void setTenphi(String tenphi) {
        this.tenphi = tenphi;
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

    public Phi(String tenphi, LocalDateTime datestart, LocalDateTime dateend, Integer money) {
        this.tenphi = tenphi;
        this.datestart = datestart;
        this.dateend = dateend;
        this.money = money;
    }

}
