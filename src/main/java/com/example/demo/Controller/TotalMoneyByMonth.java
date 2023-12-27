package com.example.demo.Controller;

import com.example.demo.JPARepository.DongPhiRepository;
import com.example.demo.JPARepository.DongQuyRepository;
import com.example.demo.Model.DongPhi;
import com.example.demo.Model.DongQuy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TotalMoneyByMonth {
    @Autowired
    private DongQuyRepository dongQuyRepository;

    @Autowired
    private DongPhiRepository dongPhiRepository;

    @GetMapping("/totalPhibyMonth")
    public Integer TotalPhibyMonth(@RequestParam int month,@RequestParam int year){
        LocalDateTime startdate = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime enddate = startdate.plusMonths(1).minusSeconds(1);

        List<DongPhi> dongPhis = dongPhiRepository.findByDateBetween(startdate, enddate);
        Integer total = 0;
        for(DongPhi n : dongPhis){
            total += n.getMoney();
        }
        return total;
    }

    @GetMapping("/totalQuybyMonth")
    public Integer TotalQuybyMonth(@RequestParam int month,@RequestParam int year){
        LocalDateTime startdate = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime enddate = startdate.plusMonths(1).minusSeconds(1);

        List<DongQuy> dongQuys = dongQuyRepository.findByDateBetween(startdate, enddate);
        Integer total = 0;
        for(DongQuy n : dongQuys){
            total += n.getMoney();
        }
        return total;
    }
}
