package com.example.demo.Controller;

import com.example.demo.JPARepository.DongPhiRepository;
import com.example.demo.Model.DongPhi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TotalMoneyByPhiid {

    @Autowired
    private DongPhiRepository dongPhiRepository;

    @GetMapping("/TotalMoneyPhiByPhiid")
    public Integer TotalMoney(@RequestParam(name = "phiid", required = false) Integer phiid) {
        List<DongPhi> dongPhis = dongPhiRepository.findByPhiid(phiid);
        Integer total = 0;

        if (dongPhis != null && !dongPhis.isEmpty()) {
            for (DongPhi n : dongPhis) {
                total += (n.getMoney() != null) ? n.getMoney() : 0;
            }
        }
        return total;
    }
}
