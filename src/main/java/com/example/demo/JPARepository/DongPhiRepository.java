package com.example.demo.JPARepository;

import com.example.demo.Model.DongPhi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DongPhiRepository extends JpaRepository<DongPhi, Integer> {
    DongPhi findByFidAndPhiid(Integer fid, Integer phiid);
    List<DongPhi>  findByDateBetween(LocalDateTime startdate, LocalDateTime enddate);

    @Query("SELECT COUNT(fid) FROM DongPhi WHERE phiid = :phiid")
    Integer countHoGiaDinhDaDongPhi(@Param("phiid") Integer phiid);

    @Query("SELECT concat('{\"fid\":','\"',hgd.fid,'\"',',','\"hovaten\":\"',nk.hovatendem,' ', nk.ten,'\"}')  FROM DongPhi dp join HoGiaDinh hgd on hgd.fid = dp.fid join NhanKhau nk on nk.cccd = hgd.cccdchuho WHERE dp.phiid = :phiid")
    List<String> findHoGiaDinhDaDongPhi(@Param("phiid") Integer phiid);
}
