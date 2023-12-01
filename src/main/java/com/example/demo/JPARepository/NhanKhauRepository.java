package com.example.demo.JPARepository;

import com.example.demo.Model.NhanKhau;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NhanKhauRepository extends JpaRepository<NhanKhau, Integer> {
    NhanKhau findByCccd(String cccd);
    NhanKhau findByCccdAndFid(String cccd, Integer fid);
    NhanKhau findByFidAndCccdAndQuanhe(Integer fid, String cccd, String quanhe);

    NhanKhau findBySodienthoai(String sodienthoai);


}
