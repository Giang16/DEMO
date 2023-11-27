package com.example.demo.JPARepository;

import com.example.demo.Model.NhanKhau;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NhanKhauRepository extends JpaRepository<NhanKhau, String> {
    NhanKhau findByCccd(String cccd);
    NhanKhau findByPhonenumber(String phonenumber);
    NhanKhau findByName(String name);
    NhanKhau findBySex(String sex);
    int deleteByCccd(String cccd);

    List<NhanKhau> findByMahokhau(Integer mahokhau);
}
