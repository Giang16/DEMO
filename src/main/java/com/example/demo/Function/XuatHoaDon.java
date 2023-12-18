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

    public ResponseEntity<byte[]> generateHelloWorldPdf(Integer phiId, String tenPhi, Integer soTien, Integer fid, String ngayDongPhi, String nguoiDongTien) throws DocumentException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);

        document.open();
        Font font1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, BaseColor.BLACK);
        Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, BaseColor.BLACK);
        Font font3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, BaseColor.BLACK);
        Font font4 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK);
        Font font5 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, BaseColor.BLACK);
        Font font6 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK);

        //Lưu content lại để sau truy xuất
        String content1 ="             CONG HOA XA HOI CHU NGHIA VIET NAM"+'\n';
        String content2 ="                                  Doc Lap - Tu Do - Hanh Phuc" + '\n'+
        "                                  ___________***___________";
        String content3 = """
                                                     BIEN LAI THU TIEN PHI


                """;
        String content4 ="         Ten loai phi:         " + tenPhi;
        String content5 ="         Ma so phi:            " + phiId;
        String content6 ="         Nguoi dong tien:    " + nguoiDongTien;
        String content7 ="         So tien thu:          " + soTien;
        String content8 ="         Ngay thu:             " + ngayDongPhi;

        Chunk chunk1 = new Chunk(content1, font1);
        Paragraph paragraph1 = new Paragraph(chunk1);
        document.add(paragraph1);

        Chunk chunk2 = new Chunk(content2, font2);
        Paragraph paragraph2 = new Paragraph(chunk2);
        document.add(paragraph2);

        Chunk chunk3 = new Chunk(content3, font3);
        Paragraph paragraph3 = new Paragraph(chunk3);
        document.add(paragraph3);

        Chunk chunk4 = new Chunk(content4, font4);
        Paragraph paragraph4 = new Paragraph(chunk4);
        document.add(paragraph4);

        Chunk chunk5 = new Chunk(content5, font4);
        Paragraph paragraph5 = new Paragraph(chunk5);
        document.add(paragraph5);

        Chunk chunk6 = new Chunk(content6, font5);
        Paragraph paragraph6 = new Paragraph(chunk6);
        document.add(paragraph6);

        Chunk chunk7 = new Chunk(content7, font6);
        Paragraph paragraph7 = new Paragraph(chunk7);
        document.add(paragraph7);

        Chunk chunk8 = new Chunk(content8, font6);
        Paragraph paragraph8 = new Paragraph(chunk8);
        document.add(paragraph8);


        document.close();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "iTextHelloWorld.pdf");

        // Return the PDF content as a byte array with the appropriate headers
        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);

    }

}

