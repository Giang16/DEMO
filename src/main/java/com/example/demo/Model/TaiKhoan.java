package com.example.demo.Model;

import jakarta.persistence.*;
import org.springframework.data.relational.core.sql.In;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
@Table(name = "TaiKhoan")
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tk_id")
    private Integer tkid;
    @Column(name = "User") private  String user;
    @Column(name = "Hashkey") private  String hashkey;
    @Column(name = "lv_admin") private  Integer lvadmin;


    //User: mã căn cước công dân
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getHashkey() {
        return hashkey;
    }

    public void setHashkey(String password) {
        hashkey=password;
    }

    public Integer getLvadmin() {
        return lvadmin;
    }

    public void setLvadmin(Integer lvadmin) {
        this.lvadmin = lvadmin;
    }

}
