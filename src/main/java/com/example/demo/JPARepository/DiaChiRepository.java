package com.example.demo.JPARepository;

import com.example.demo.Model.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaChiRepository extends JpaRepository<DiaChi, Integer > {
    DiaChi findByAddidAndSonhaAndDuongAndPhuongAndQuanAndThanhpho(Integer addid, String sonha, String duong, String phuong, String quan, String thanhpho);
    DiaChi findBySonhaAndDuongAndPhuongAndQuanAndThanhpho(String sonha, String duong, String phuong, String quan, String thanhpho);
}
