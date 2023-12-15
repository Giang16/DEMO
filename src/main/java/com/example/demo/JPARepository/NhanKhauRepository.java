package com.example.demo.JPARepository;

import com.example.demo.Model.NhanKhau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NhanKhauRepository extends JpaRepository<NhanKhau, Integer> {
    NhanKhau findByCccd(String cccd);
    NhanKhau findByCccdAndFid(String cccd, Integer fid);
    NhanKhau findByFidAndCccdAndQuanhe(Integer fid, String cccd, String quanhe);

    NhanKhau findBySodienthoai(String sodienthoai);
    List<NhanKhau> findAllByFid(Integer fid);

    @Query("SELECT nk FROM NhanKhau nk WHERE cccd = :cccd")
    NhanKhau findNhanKhauByCccd(@Param("cccd") String cccd);

    @Query("SELECT nk FROM NhanKhau nk WHERE sodienthoai = :sdt")
    NhanKhau findNhanKhauBySdt(@Param("sdt") String sdt);

    @Query("SELECT nk FROM NhanKhau nk WHERE sodienthoai = :sdt AND cccd = :cccd")
    NhanKhau findNhanKhauBySdtVaCccd(@Param("sdt") String sdt,@Param("cccd") String cccd);

    @Query("SELECT nk FROM NhanKhau nk JOIN DiaChi dc ON nk.fid = dc.addid WHERE dc.duong = :duong")
    List<NhanKhau> findAllByDuong(@Param("duong") String duong);

    @Query("SELECT nk FROM NhanKhau nk JOIN DiaChi dc ON nk.fid = dc.addid WHERE dc.phuong = :phuong")
    List<NhanKhau> findAllByPhuong(@Param("phuong") String phuong);

    @Query("SELECT nk FROM NhanKhau nk JOIN DiaChi dc ON nk.fid = dc.addid WHERE dc.quan = :quan")
    List<NhanKhau> findAllByQuan(@Param("quan") String quan);

    @Query("SELECT nk FROM NhanKhau nk JOIN DiaChi dc ON nk.fid = dc.addid WHERE dc.thanhpho = :thanhpho")
    List<NhanKhau> findAllByThanhPho(@Param("thanhpho") String thanhpho);
    @Query("SELECT nk.gioitinh FROM NhanKhau nk " +
            "JOIN DiaChi dc ON nk.fid = dc.addid " +
            "WHERE dc.duong = :duong")
    List<String> findGioitinhByDuong(@Param("duong") String duong);

    @Query("SELECT nk.gioitinh FROM NhanKhau nk " +
            "JOIN DiaChi dc ON nk.fid = dc.addid " +
            "WHERE dc.phuong = :phuong")
    List<String> findGioitinhByPhuong(@Param("phuong") String phuong);

    @Query("SELECT nk.gioitinh FROM NhanKhau nk " +
            "JOIN DiaChi dc ON nk.fid = dc.addid " +
            "WHERE dc.quan = :quan")
    List<String> findGioitinhByQuan(@Param("quan") String quan);

    @Query("SELECT nk.gioitinh FROM NhanKhau nk " +
            "JOIN DiaChi dc ON nk.fid = dc.addid " +
            "WHERE dc.thanhpho = :thanhpho")
    List<String> findGioitinhByThanhPho(@Param("thanhpho") String thanhpho);

    @Query("SELECT nk.gioitinh FROM NhanKhau nk")
    List<String> findAllGioitinh();
}
