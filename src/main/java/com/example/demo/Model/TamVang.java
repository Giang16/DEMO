package com.example.demo.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tamvang")
public class TamVang {
    @Id
    @Column(name = "cccd")
    private String cccd;

    @Column(name = "status")
    private Integer status;

    public TamVang(){

    }
    public TamVang(String cccd, Integer status){
        this.cccd = cccd;
        this.status = status;
    }

    public Integer getStatus(){
        return status;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public String getCccd(){
        return cccd;
    }

    public void setCccd(String cccd){
        this.cccd = cccd;
    }

}
