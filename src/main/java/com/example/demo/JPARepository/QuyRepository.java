package com.example.demo.JPARepository;

import com.example.demo.Model.Quy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface QuyRepository extends JpaRepository<Quy, Integer> {
    Quy findByTenquyAndDateendAndMoney(String tenquy, LocalDateTime dateend, Integer money);
    Quy findByQuyid(Integer quyid);
    Quy findByQuyidAndTenquy(Integer quyid, String tenquy);
}
