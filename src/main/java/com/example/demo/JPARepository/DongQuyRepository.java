package com.example.demo.JPARepository;

import com.example.demo.Model.DongQuy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DongQuyRepository extends JpaRepository<DongQuy, Integer> {
    DongQuy findByFidAndQuyid(Integer fid, Integer quyid);
    List<DongQuy> findByDateBetween(LocalDateTime startdate, LocalDateTime enddate);
}
