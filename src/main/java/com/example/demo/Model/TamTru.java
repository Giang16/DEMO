package com.example.demo.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "tamtru")
public class TamTru {
    @Id
    @Column(name = "cccd")
    private String cccd;

    @Column(name = "status")
    private Integer status;


    public TamTru(){
    };

    public TamTru(String cccd, Integer status){
        this.status = status;
        this.cccd = cccd;
    };

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
