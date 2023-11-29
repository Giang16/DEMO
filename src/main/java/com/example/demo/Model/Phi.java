package com.example.demo.Model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table (name = "Phi")
public class Phi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phi_id") private Integer phiid;

    @Column(name = "tenphi") private String tenphi;

    @Column(name = "Date_start") private Date Datestart;

    @Column(name = "Date_end") private Date Dateend;

    @Column(name = "Money") private Integer Money; // Số tiền theo đầu người phải đóng

    @Column(name = "Introduction", length = 2000)  private String introduction = "Chưa có mô tả";
    public Phi() {

    }

    public Integer getQuy_id() {
        return phiid;
    }

    public void setQuy_id(Integer phiid) {
        this.phiid = phiid;
    }

    public String getTenphi() {
        return tenphi;
    }

    public void setTenphi(String tenphi) {
        this.tenphi = tenphi;
    }

    public Date getDates_tart() {
        return Datestart;
    }

    public void setDates_tart(Date dates_tart) {
        Datestart = dates_tart;
    }

    public Date getDate_end() {
        return Dateend;
    }

    public void setDate_end(Date date_end) {
        Dateend = date_end;
    }

    public Integer getMoney() {
        return Money;
    }

    public void setMoney(Integer money) {
        Money = money;
    }

    public Phi(Integer phi_id, String tenphi, Date dates_tart, Date date_end, Integer money) {
        this.phiid = phi_id;
        this.tenphi = tenphi;
        Datestart = dates_tart;
        Dateend = date_end;
        Money = money;
    }
    // phương thức viết mô tả cho quỹ.
    public void setDescription(String description) {
        if (description.length() > 2000) {
            this.introduction = "Chưa có mô tả";
        } else {
            this.introduction = description;
        }
    }

    // Có thể thêm một phương thức để lấy mô tả của quỹ nếu cần
    public String getDescription() {
        return introduction;
    }
}
