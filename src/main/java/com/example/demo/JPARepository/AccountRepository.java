package com.example.demo.JPARepository;

import com.example.demo.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUsername(String username);
    Account findByCccd(String cccd);
}