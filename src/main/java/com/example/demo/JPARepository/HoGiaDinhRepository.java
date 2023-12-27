package com.example.demo.JPARepository;

import com.example.demo.Model.HoGiaDinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HoGiaDinhRepository extends JpaRepository<HoGiaDinh, Integer> {
    HoGiaDinh findByCccdchuho(String cccdchuho);
    HoGiaDinh findByFid(Integer fid);
    HoGiaDinh findByFidAndCccdchuho(Integer fid, String cccdchuho);
    List<HoGiaDinh> findAll();

    @Query("SELECT h FROM HoGiaDinh h")
    List<HoGiaDinh> findAllFid();

    @Query("SELECT COUNT(fid) FROM HoGiaDinh")
    Integer countHoGiaDinh();

//    @Query("SELECT CONCAT('{\"fid\":','\"',hgd.fid,'\"',',','\"hovaten\":','\"',CONCAT(nk.hovatendem,' ',nk.ten),'\"',',','\"',\"diachi\",'\":','\"',CONCAT(dc.sonha,' ', dc.duong),'\"}')" +
//            "FROM HoGiaDinh hgd" +
//            "JOIN NhanKhau nk ON hgd.cccdchuho = nk.cccd" +
//            "JOIN DiaChi dc ON hgd.fid = dc.addid")
    @Query("SELECT CONCAT('{\"fid\":',hgd.fid,',','\"hovaten\":','\"',CONCAT(nk.hovatendem,' ',nk.ten),'\"',',','\"diachi\":','\"',CONCAT(dc.sonha,' ',dc.duong),'\"}') FROM HoGiaDinh hgd JOIN NhanKhau nk ON hgd.cccdchuho = nk.cccd JOIN DiaChi dc ON hgd.fid = dc.addid")
    List<String> findAllFidNew();
}
