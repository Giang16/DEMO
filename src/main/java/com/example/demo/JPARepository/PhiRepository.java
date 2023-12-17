package com.example.demo.JPARepository;

import com.example.demo.Model.Phi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface PhiRepository extends JpaRepository<Phi, Integer> {
    Phi findByTenphiAndDateendAndMoney(String tenphi, LocalDateTime dateend, Integer money);
    Phi findByPhiid(Integer phiid);
    Phi findByPhiidAndTenphi(Integer phiid, String tenphi);
}
