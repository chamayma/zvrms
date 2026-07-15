package com.zvrms.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.zvrms.dto.voter.VoterResponse;
import com.zvrms.entity.District;
import com.zvrms.entity.Shehia;
import com.zvrms.entity.User;
import com.zvrms.entity.Voter;
import com.zvrms.repository.DistrictRepository;
import com.zvrms.repository.ShehiaRepository;
import com.zvrms.repository.UserRepository;
import com.zvrms.repository.VoterRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final VoterRepository voterRepository;
    private final DistrictRepository districtRepository;
    private final ShehiaRepository shehiaRepository;
    private final UserRepository userRepository;

    public byte[] allReport() {
        return buildPdf(voterRepository.findAll(), "ALL VOTERS REPORT");
    }

    public byte[] districtReport(Long districtId) {

        District district = districtRepository.findById(districtId)
                .orElseThrow(() -> new RuntimeException("District not found"));

        return buildPdf(
                voterRepository.findByDistrict(district),
                district.getName() + " DISTRICT REPORT");
    }

    public byte[] shehiaReport(Long shehiaId) {

        Shehia shehia = shehiaRepository.findById(shehiaId)
                .orElseThrow(() -> new RuntimeException("Shehia not found"));

        return buildPdf(
                voterRepository.findByShehia(shehia),
                shehia.getName() + " SHEHIA REPORT");
    }

    public byte[] excelReport() {

        try {

            List<Voter> voters = voterRepository.findAll();

            XSSFWorkbook workbook = new XSSFWorkbook();

            XSSFSheet sheet = workbook.createSheet("Voters");

            int rowNum = 0;

            Row header = sheet.createRow(rowNum++);

            header.createCell(0).setCellValue("Full Name");
            header.createCell(1).setCellValue("Voter Number");
            header.createCell(2).setCellValue("Phone");
            header.createCell(3).setCellValue("Sex");
            header.createCell(4).setCellValue("District");
            header.createCell(5).setCellValue("Shehia");

            for (Voter voter : voters) {

                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(voter.getFullName());
                row.createCell(1).setCellValue(voter.getVoterNumber());
                row.createCell(2).setCellValue(voter.getPhoneNumber());
                row.createCell(3).setCellValue(voter.getSex());
                row.createCell(4).setCellValue(voter.getDistrict().getName());
                row.createCell(5).setCellValue(voter.getShehia().getName());

            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            workbook.write(out);

            workbook.close();

            return out.toByteArray();

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

    private byte[] buildPdf(List<Voter> voters, String title) {

        try {

            Document document = new Document(PageSize.A4.rotate());

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            PdfWriter.getInstance(document, out);

            document.open();

            document.add(new Paragraph(title));

            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(6);

            table.addCell("Full Name");
            table.addCell("Voter Number");
            table.addCell("Phone");
            table.addCell("Sex");
            table.addCell("District");
            table.addCell("Shehia");

            for (Voter voter : voters) {

                table.addCell(voter.getFullName());
                table.addCell(voter.getVoterNumber());
                table.addCell(voter.getPhoneNumber());
                table.addCell(voter.getSex());
                table.addCell(voter.getDistrict().getName());
                table.addCell(voter.getShehia().getName());

            }

            document.add(table);

            document.close();

            return out.toByteArray();

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

    public byte[] dateRangePdf(LocalDateTime start,
                           LocalDateTime end) {

    return buildPdf(

            voterRepository.findByCreatedAtBetween(start, end),

            "DATE RANGE REPORT"

    );

}

public byte[] dateRangeExcel(LocalDateTime start,
                             LocalDateTime end) {

    try {

        List<Voter> voters =
                voterRepository.findByCreatedAtBetween(start, end);

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("Date Report");

        int rowNum = 0;

        Row header = sheet.createRow(rowNum++);

        header.createCell(0).setCellValue("Full Name");
        header.createCell(1).setCellValue("Voter Number");
        header.createCell(2).setCellValue("Phone");
        header.createCell(3).setCellValue("District");
        header.createCell(4).setCellValue("Shehia");

        for (Voter voter : voters) {

            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(voter.getFullName());
            row.createCell(1).setCellValue(voter.getVoterNumber());
            row.createCell(2).setCellValue(voter.getPhoneNumber());
            row.createCell(3).setCellValue(voter.getDistrict().getName());
            row.createCell(4).setCellValue(voter.getShehia().getName());

        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        workbook.write(out);

        workbook.close();

        return out.toByteArray();

    } catch (Exception e) {

        throw new RuntimeException(e);

    }

}

public List<VoterResponse> getDistrictReport(
        Authentication authentication,
        String search,
        Long shehiaId,
        String sex,
        LocalDate dateFrom,
        LocalDate dateTo) {

    User officer = userRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));

    List<Voter> voters = voterRepository.findByDistrict(officer.getDistrict());

    if (search != null && !search.isBlank()) {

        voters = voters.stream()
                .filter(v -> v.getFullName()
                        .toLowerCase()
                        .contains(search.toLowerCase()))
                .collect(Collectors.toList());

    }

    if (shehiaId != null) {

        voters = voters.stream()
                .filter(v -> v.getShehia().getId().equals(shehiaId))
                .collect(Collectors.toList());

    }

    if (sex != null && !sex.isBlank()) {

        voters = voters.stream()
                .filter(v -> v.getSex().equalsIgnoreCase(sex))
                .collect(Collectors.toList());

    }

    if (dateFrom != null && dateTo != null) {

        voters = voters.stream()
                .filter(v ->

                        !v.getCreatedAt().isBefore(dateFrom.atStartOfDay())

                        &&

                        !v.getCreatedAt().isAfter(dateTo.atTime(LocalTime.MAX))

                )
                .collect(Collectors.toList());

    }

    List<VoterResponse> response = new ArrayList<>();

for (Voter voter : voters) {

    VoterResponse dto = new VoterResponse();

    dto.setId(voter.getId());
    dto.setFullName(voter.getFullName());
    dto.setVoterNumber(voter.getVoterNumber());
    dto.setPhoneNumber(voter.getPhoneNumber());
    dto.setAddress(voter.getAddress());
    dto.setSex(voter.getSex());
    dto.setDateOfBirth(voter.getDateOfBirth());

    dto.setDistrict(voter.getDistrict().getName());
    dto.setShehia(voter.getShehia().getName());

    dto.setRegisteredBy(voter.getRegisteredBy().getFullName());
    dto.setCreatedAt(voter.getCreatedAt());

    response.add(dto);

}

return response;
        }

}