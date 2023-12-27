package com.example.demo.Model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tamtru")
public class TamTru {
    @Id
    @Column(name = "cccd")
    private String cccd;

    @Column(name = "Date start")
    private Date datestart;

    @Column(name = "Date end")
    private Date dateend;

    public TamTru(){
    };

    public TamTru(String cccd, Date datestart, Date dateend){
        this.cccd = cccd;
        this.datestart = datestart;
        this.dateend = dateend;
    };

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
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
}
