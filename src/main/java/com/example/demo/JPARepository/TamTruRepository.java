package com.example.demo.JPARepository;

import com.example.demo.Model.TamTru;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface TamTruRepository extends JpaRepository<TamTru, String> {
    @Query("SELECT COUNT(tt) FROM TamTru tt WHERE tt.datestart < CURRENT_DATE AND tt.dateend > CURRENT_DATE")
    int countByCurrentTime();
    //Số người đang trong thời gian tạm trú (that shit mean income people ?)

    @Query("SELECT COUNT(tt) FROM TamTru tt WHERE tt.datestart <= :start AND tt.dateend > CURRENT_DATE")
    int countStart(@Param("start") Date start);
    //SỐ người tạm trú trước ngày "start" và vẫn đang tạm trú

    @Query("SELECT COUNT(tt) FROM TamTru tt WHERE tt.datestart <= CURRENT_DATE AND tt.dateend <= :end")
    int countEnd(@Param("end") Date end);
    //Số người đang tạm trú và còn hạn trước ngày "end"

    @Query("SELECT COUNT(tt) FROM TamTru tt WHERE tt.datestart >= :start AND tt.dateend <= :end")
    int countFullRange(@Param("start") Date start, @Param("end") Date end);
    //Số người tạm trú toàn thời gian trong khoảng ngày start đến ngày end

    TamTru findByCccd(String cccd);
}
