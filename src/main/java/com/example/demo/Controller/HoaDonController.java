package com.example.demo.Controller;

import com.example.demo.Function.XuatHoaDon;
import com.example.demo.JPARepository.DongPhiRepository;
import com.example.demo.JPARepository.NhanKhauRepository;
import com.example.demo.JPARepository.PhiRepository;
import com.example.demo.Model.DongPhi;
import com.example.demo.Model.Phi;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/pdf")
public class HoaDonController {

    @Autowired
    private XuatHoaDon pdfGenerationService;

    @Autowired
    private PhiRepository phiRepository;

    @Autowired
    private DongPhiRepository dongPhiRepository;

    @Autowired
    private NhanKhauRepository nhanKhauRepository;

    @GetMapping("/xuatbienlai")
    public ResponseEntity<byte[]> generateHelloWorldPdf(
            @RequestParam(name = "phiid")Integer phiid,
            @RequestParam(name = "fid")Integer fid
    ) {
        Phi phi = phiRepository.findByPhiid(phiid);
        String tenPhi = phi.getTenphi();
        Integer soTien = phi.getMoney();
        DongPhi dongPhi = dongPhiRepository.findByFidAndPhiid(fid,phiid);
        LocalDateTime date = dongPhi.getDate();
        String tenchuho = nhanKhauRepository.findTenChuHo(fid);
        System.out.println(tenchuho);

        try {
            return pdfGenerationService.generateHelloWorldPdf(phiid,tenPhi,soTien,fid,date.toString().substring(0,10),tenchuho);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
