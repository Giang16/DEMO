package com.example.demo.JPARepository;

import com.example.demo.Model.NhanKhau;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NhanKhauRepository extends JpaRepository<NhanKhau, String> {
    NhanKhau findByCccd(String cccd);
    NhanKhau findByPhonenumber(String phonenumber);
    NhanKhau findByName(String name);
    NhanKhau findBySex(String sex);
    NhanKhau deleteByCccd(String cccd);

}
