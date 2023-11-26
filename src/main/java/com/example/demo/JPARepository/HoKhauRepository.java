package com.example.demo.JPARepository;

import com.example.demo.Model.HoKhau;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoKhauRepository extends JpaRepository<HoKhau, Integer> {
    HoKhau findByMahokhau(Integer mahokhau);
    HoKhau findByChuho(String chuho);
    HoKhau findByDiachi(String diachi);
    HoKhau deleteByMahokhau(Integer mahokhau);
}
