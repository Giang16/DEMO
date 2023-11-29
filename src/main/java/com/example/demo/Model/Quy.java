package com.example.demo.Model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table (name = "Quy")
public class Quy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quy_id") private Integer quyid;

    @Column(name = "tenquy") private String tenquy;

    @Column(name = "Date_start") private Date Datestart;

    @Column(name = "Date_end") private Date Dateend;

    @Column(name = "Money") private Integer Money; // Số tiền mong muốn vận động được

    @Column(name = "Introduction", length = 2000)  private String introduction = "Chưa có mô tả";
    public Quy() {

    }

    public Integer getQuy_id() {
        return quyid;
    }

    public void setQuy_id(Integer quy_id) {
        this.quyid = quy_id;
    }

    public String getTenquy() {
        return tenquy;
    }

    public void setTenquy(String tenquy) {
        this.tenquy = tenquy;
    }

    public Date getDates_tart() {
        return Datestart;
    }

    public void setDates_tart(Date datestart) {
        Datestart = datestart;
    }

    public Date getDate_end() {
        return Dateend;
    }

    public void setDate_end(Date dateend) {
        Dateend = dateend;
    }

    public Integer getMoney() {
        return Money;
    }

    public void setMoney(Integer money) {
        Money = money;
    }

    public Quy(Integer quy_id, String tenquy, Date dates_tart, Date date_end, Integer money) {
        this.quyid = quy_id;
        this.tenquy = tenquy;
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
