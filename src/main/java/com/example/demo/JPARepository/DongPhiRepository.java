package com.example.demo.JPARepository;

import com.example.demo.Model.DongPhi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DongPhiRepository extends JpaRepository<DongPhi, Integer> {
    DongPhi findByFidAndPhiid(Integer fid, Integer phiid);
    List<DongPhi>  findByDateBetween(LocalDateTime startdate, LocalDateTime enddate);

}
