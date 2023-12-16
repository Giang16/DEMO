package com.example.demo.JPARepository;

import com.example.demo.Model.TamTru;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TamTruRepository extends JpaRepository<TamTru, String> {
    TamTru findByCccd(String cccd);
}
