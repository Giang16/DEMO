package com.example.demo.Model;

import jakarta.persistence.*;


@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private  String username;

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "password")
    private String password;

    @Transient
    private String newpassword;

    @Transient
    private String confirmpassword;

    public Account() {

    }
    public Account(Integer id, String username, String cccd, String password) {
        this.id = id;
        this.username = username;
        this.cccd = cccd;
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newpassword;
    }

    public void setNewPassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getConfirmPassword() {
        return confirmpassword;
    }

    public void setConfirmPassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }
}
