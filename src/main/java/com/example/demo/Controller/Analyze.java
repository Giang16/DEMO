package com.example.demo.Controller;

import com.example.demo.Function.AgeCalculator;
import com.example.demo.JPARepository.DiaChiRepository;
import com.example.demo.JPARepository.NhanKhauRepository;
import com.example.demo.JPARepository.TamTruRepository;
import com.example.demo.JPARepository.TamVangRepository;
import com.example.demo.Model.NhanKhau;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Analyze {
    @Autowired
    private NhanKhauRepository nhanKhauRepository;

    @Autowired
    private DiaChiRepository diaChiRepository;

    @Autowired
    private TamVangRepository tamVangRepository;

    @Autowired
    private TamTruRepository tamTruRepository;

    @GetMapping("/analyzeSex")
    public String analyzeSex(@RequestParam(name = "duong", required = false) String duong,
                               @RequestParam(name = "phuong", required = false) String phuong,
                               @RequestParam(name = "quan", required = false) String quan,
                               @RequestParam(name = "thanhpho", required = false) String thanhpho) {
        List<String> gioitinhList = null;
        if(duong != null) {
            gioitinhList = nhanKhauRepository.findGioitinhByDuong(duong);
        } else if (phuong != null) {
            gioitinhList = nhanKhauRepository.findGioitinhByPhuong(phuong);
        } else if (quan != null) {
            gioitinhList = nhanKhauRepository.findGioitinhByQuan(quan);
        } else if (thanhpho != null){
            gioitinhList = nhanKhauRepository.findGioitinhByThanhPho(thanhpho);
        } else {
            gioitinhList = nhanKhauRepository.findAllGioitinh();
        }

        long maleCount = gioitinhList.stream()
                .filter("Nam"::equalsIgnoreCase)
                .count();

        long femaleCount = gioitinhList.size() - maleCount;

        double totalResidents = gioitinhList.size();
        double malePercentage = (maleCount / totalResidents) * 100;
        double femalePercentage = (femaleCount / totalResidents) * 100;

        DecimalFormat df = new DecimalFormat("#0.00");
        String formattedMalePercentage = df.format(malePercentage);
        String formattedFemalePercentage = df.format(femalePercentage);

        // Build the JSON string
        StringBuilder result = new StringBuilder();
        result.append("{");
        result.append("\"maleCount\": ").append(maleCount).append(", ");
        result.append("\"femaleCount\": ").append(femaleCount).append(", ");
        result.append("\"malePercentage\": ").append(formattedMalePercentage).append(", ");
        result.append("\"femalePercentage\": ").append(formattedFemalePercentage);
        result.append("}");

        return result.toString();
    }

    @GetMapping("analyzeAge")
    public String analyzeAgeGroups(@RequestParam(name = "duong", required = false) String duong,
                                   @RequestParam(name = "phuong", required = false) String phuong,
                                   @RequestParam(name = "quan", required = false) String quan,
                                   @RequestParam(name = "thanhpho", required = false) String thanhpho) {

        List<NhanKhau> residents = null;
        if(duong != null) {
            residents = nhanKhauRepository.findAllByDuong(duong);
        } else if (phuong != null) {
            residents = nhanKhauRepository.findAllByPhuong(phuong);
        } else if (quan != null) {
            residents = nhanKhauRepository.findAllByQuan(quan);
        } else if (thanhpho != null){
            residents = nhanKhauRepository.findAllByThanhPho(thanhpho);
        } else {
            residents = nhanKhauRepository.findAll();
        }


        int countMamNon = 0;
        int countCap1 = 0;
        int countCap2 = 0;
        int countCap3 = 0;
        int countLaoDong = 0;
        int countNghiHuu = 0;

        for (NhanKhau resident : residents) {
            int age = AgeCalculator.calculateAge(resident.getNgaysinh());

            if (age >= 0 && age <= 5) {
                countMamNon++;
            } else if (age >= 6 && age <= 10) {
                countCap1++;
            } else if (age >= 11 && age <= 14) {
                countCap2++;
            } else if (age >= 15 && age <= 18) {
                countCap3++;
            } else if (age >= 19 && age <= 60) {
                countLaoDong++;
            } else if (age > 60) {
                countNghiHuu++;
            }
        }

        int totalResidents = residents.size();

        // Calculate percentages
        double percentageMamNon = calculatePercentage(countMamNon, totalResidents);
        double percentageCap1 = calculatePercentage(countCap1, totalResidents);
        double percentageCap2 = calculatePercentage(countCap2, totalResidents);
        double percentageCap3 = calculatePercentage(countCap3, totalResidents);
        double percentageLaoDong = calculatePercentage(countLaoDong, totalResidents);
        double percentageNghiHuu = calculatePercentage(countNghiHuu, totalResidents);

        // Build the JSON string
        StringBuilder result = new StringBuilder();
        result.append("{");
        result.append("\"mamNon\": {\"count\": ").append(countMamNon).append(", \"percentage\": ").append(percentageMamNon).append("}, ");
        result.append("\"cap1\": {\"count\": ").append(countCap1).append(", \"percentage\": ").append(percentageCap1).append("}, ");
        result.append("\"cap2\": {\"count\": ").append(countCap2).append(", \"percentage\": ").append(percentageCap2).append("}, ");
        result.append("\"cap3\": {\"count\": ").append(countCap3).append(", \"percentage\": ").append(percentageCap3).append("}, ");
        result.append("\"laoDong\": {\"count\": ").append(countLaoDong).append(", \"percentage\": ").append(percentageLaoDong).append("}, ");
        result.append("\"nghiHuu\": {\"count\": ").append(countNghiHuu).append(", \"percentage\": ").append(percentageNghiHuu).append("}");
        result.append("}");

        return result.toString();
    }
    private double calculatePercentage(int count, int total) {
        double percentage = (count / (double) total) * 100;

        // Format percentage with two decimal places
        DecimalFormat df = new DecimalFormat("#0.00");
        return Double.parseDouble(df.format(percentage));
    }

    @RequestMapping("/countTamVang")
    private int countTamVang(@RequestParam(name = "start", required = false) Date start,
                             @RequestParam(name = "end", required = false) Date end) {
        if(start != null && end != null) {
            return tamVangRepository.countFullRange(start,end);
        } else if (start != null){
            return tamVangRepository.countStart(start);
        } else if (end != null) {
            return tamVangRepository.countEnd(end);
        } else {
            return tamVangRepository.countByCurrentTime();
        }
    }

    @RequestMapping("/countTamTru")
    private int countTamTru(@RequestParam(name = "start", required = false) Date start,
                             @RequestParam(name = "end", required = false) Date end) {
        if(start != null && end != null) {
            return tamTruRepository.countFullRange(start,end);
        } else if (start != null){
            return tamTruRepository.countStart(start);
        } else if (end != null) {
            return tamTruRepository.countEnd(end);
        } else {
            return tamTruRepository.countByCurrentTime();
        }
    }


}
