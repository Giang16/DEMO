package com.example.demo.JPARepository;

import com.example.demo.Model.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiaChiRepository extends JpaRepository<DiaChi, Integer > {
    DiaChi findByAddidAndSonhaAndDuongAndPhuongAndQuanAndThanhpho(Integer addid, String sonha, String duong, String phuong, String quan, String thanhpho);
    DiaChi findBySonhaAndDuongAndPhuongAndQuanAndThanhpho(String sonha, String duong, String phuong, String quan, String thanhpho);
    List<DiaChi> findAllByDuong(String duong);
    List<DiaChi> findAllByPhuong(String phuong);
    List<DiaChi> findAllByQuan(String quan);
    List<DiaChi> findAllByThanhpho(String thanhpho);


    DiaChi findByAddid(Integer addId);
}
