package com.example.demo.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
@Table(name = "TaiKhoan")

public class TaiKhoan {
    @Column(name = "User") private  String User;
    @Column(name = "Hashkey") private  String Hashkey;
    @Column(name = "lv_admin") private  Integer lvadmin;



    //User: mã căn cước công dân
    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getHashkey() {
        return Hashkey;
    }

    public void setHashkey(String password) {
        // Create MessageDigest instance for SHA-256
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Add password bytes to digest
            md.update(password.getBytes());

            // Get the hash's bytes
            byte[] bytes = md.digest();

            // Convert bytes to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            // Set the hashkey field with the hashed password
            Hashkey = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Đéo thấy có băm buồi j cả");
        }

    }

    public Integer getLvadmin() {
        return lvadmin;
    }

    public void setLvadmin(Integer lvadmin) {
        this.lvadmin = lvadmin;
    }

}
