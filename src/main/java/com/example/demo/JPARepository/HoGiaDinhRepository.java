package com.example.demo.JPARepository;

import com.example.demo.Model.HoGiaDinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HoGiaDinhRepository extends JpaRepository<HoGiaDinh, Integer> {
    HoGiaDinh findByCccdchuho(String cccdchuho);
    HoGiaDinh findByFid(Integer fid);
    HoGiaDinh findByFidAndCccdchuho(Integer fid, String cccdchuho);
    List<HoGiaDinh> findAll();
    @Query("SELECT h FROM HoGiaDinh h")
    List<HoGiaDinh> findAllFid();
}
