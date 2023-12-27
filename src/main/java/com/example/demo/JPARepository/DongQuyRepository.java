package com.example.demo.JPARepository;

import com.example.demo.Model.DongQuy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DongQuyRepository extends JpaRepository<DongQuy, Integer> {
    DongQuy findByFidAndQuyid(Integer fid, Integer quyid);
    List<DongQuy> findByDateBetween(LocalDateTime startdate, LocalDateTime enddate);

    @Query("SELECT dq FROM DongQuy dq WHERE quyid = :quyid")
    List<DongQuy> findByQuyId(@Param("quyid")Integer quyid);

    @Query("SELECT concat('{\"money\":','\"',dp.money,'\"',',','\"hovaten\":\"',nk.hovatendem,' ', nk.ten,'\"}') FROM DongQuy dp join HoGiaDinh hgd on hgd.fid = dp.fid join NhanKhau nk on nk.cccd = hgd.cccdchuho WHERE dp.quyid = :phiid ORDER BY nk.ten ASC")
    List<String> findHoGiaDinhDaDongQuyOrderByNameASC(@Param("phiid") Integer phiid);

    @Query("SELECT concat('{\"money\":','\"',dp.money,'\"',',','\"hovaten\":\"',nk.hovatendem,' ', nk.ten,'\"}') FROM DongQuy dp join HoGiaDinh hgd on hgd.fid = dp.fid join NhanKhau nk on nk.cccd = hgd.cccdchuho WHERE dp.quyid = :phiid ORDER BY nk.ten DESC")
    List<String> findHoGiaDinhDaDongQuyOrderByNameDESC(@Param("phiid") Integer phiid);

    @Query("SELECT concat('{\"money\":','\"',dp.money,'\"',',','\"hovaten\":\"',nk.hovatendem,' ', nk.ten,'\"}') FROM DongQuy dp join HoGiaDinh hgd on hgd.fid = dp.fid join NhanKhau nk on nk.cccd = hgd.cccdchuho WHERE dp.quyid = :phiid ORDER BY dp.money ASC")
    List<String> findHoGiaDinhDaDongQuyOrderByMoneyASC(@Param("phiid") Integer phiid);

    @Query("SELECT concat('{\"money\":','\"',dp.money,'\"',',','\"hovaten\":\"',nk.hovatendem,' ', nk.ten,'\"}') FROM DongQuy dp join HoGiaDinh hgd on hgd.fid = dp.fid join NhanKhau nk on nk.cccd = hgd.cccdchuho WHERE dp.quyid = :phiid ORDER BY dp.money DESC")
    List<String> findHoGiaDinhDaDongQuyOrderByMoneyDESC(@Param("phiid") Integer phiid);

    @Query("SELECT SUM(money) FROM DongQuy WHERE quyid = :quyid")
    Integer sumQuyByQuyId(@Param("quyid")Integer quyid);

    @Query("SELECT concat('{\"money\":','\"',SUM(dp.money),'\"',',','\"hovaten\":\"',nk.hovatendem,' ', nk.ten,'\"}') FROM DongQuy dp JOIN HoGiaDinh hgd on hgd.fid = dp.fid JOIN NhanKhau nk ON nk.cccd = hgd.cccdchuho GROUP BY nk.ten ORDER BY nk.ten DESC")
    List<String> countMoney();
}
