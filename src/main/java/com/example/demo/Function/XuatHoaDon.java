package com.example.demo.Function;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class XuatHoaDon {

    public ResponseEntity<byte[]> generateHelloWorldPdf(Integer phiId, String tenPhi, Integer soTien, Integer fid, String ngayDongPhi) throws DocumentException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);

        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, BaseColor.BLACK);

        //Lưu content lại để sau truy xuất
        String content =    "Ten loai phi: " + tenPhi + ", Ma phi: " + phiId + "\n" +
                            "Ma Ho khau nop phi: " + fid + "\n" +
                            "So tien thu: " + soTien + "\n" +
                            "Ngay dong phi: " + ngayDongPhi;

        Chunk chunk = new Chunk(content, font);
        Paragraph paragraph = new Paragraph(chunk);
        document.add(paragraph);
        document.close();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "iTextHelloWorld.pdf");

        // Return the PDF content as a byte array with the appropriate headers
        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
    }

}

