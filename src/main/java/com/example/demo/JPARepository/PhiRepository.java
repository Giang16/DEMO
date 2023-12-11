package com.example.demo.JPARepository;

import com.example.demo.Model.Phi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.In;

import java.util.Date;

public interface PhiRepository extends JpaRepository<Phi, Integer> {
    Phi findByTenphiAndDatestartAndDateendAndMoney(String tenphi, Date datestart, Date dateend, Integer money);
    Phi findByPhiid(Integer phiid);
    Phi findByPhiidAndTenphi(Integer phiid, String tenphi);
}
