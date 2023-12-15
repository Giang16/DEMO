package com.example.demo.JPARepository;

import com.example.demo.Model.TamVang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface TamVangRepository extends JpaRepository<TamVang, String> {
    @Query("SELECT COUNT(tv) FROM TamVang tv WHERE tv.datestart < CURRENT_DATE AND tv.dateend > CURRENT_DATE")
    int countByCurrentTime();
    //Số người đang trong thời gian tạm vắng (that shit mean outcome people ?)

    @Query("SELECT COUNT(tv) FROM TamVang tv WHERE tv.datestart <= :start AND tv.dateend > CURRENT_DATE")
    int countStart(@Param("start") Date start);

    @Query("SELECT COUNT(tv) FROM TamVang tv WHERE tv.datestart <= CURRENT_DATE AND CURRENT_DATE <= :end")
    int countEnd(@Param("end") Date end);

    @Query("SELECT COUNT(tv) FROM TamVang tv WHERE tv.datestart >= :start AND tv.dateend <= :end")
    int countFullRange(@Param("start") Date start, @Param("end") Date end);


}
