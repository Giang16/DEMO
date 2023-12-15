package com.example.demo.Model;

import com.example.demo.JPARepository.DongPhiRepository;
import jakarta.persistence.*;


import java.util.Date;

@Entity
@Table(name = "Dong_Phi")
public class DongPhi {

    @Id
    @Column(name = "phi_id", unique = true)
    private Integer phiid;

    @Column(name = "f_id", unique = true)
    private Integer fid;

    @Column(name = "Money")
    private Integer money;

    @Column(name = "Date")
    private Date date;

    public DongPhi(){

    }

    public DongPhi(Integer phiid, Integer fid, Integer money, Date date){
        this.phiid = phiid;
        this.fid = fid;
        this.money = money;
        this.date = date;
    }

    public Integer getPhiid() {
        return phiid;
    }

    public void setPhiid(Integer phiid) {
        this.phiid = phiid;
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
