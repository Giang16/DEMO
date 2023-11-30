package com.example.demo.JPARepository;

import com.example.demo.Model.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Integer> {
    TaiKhoan findByUsername(String username);
    //TaiKhoan findByCccd(String cccd);
}