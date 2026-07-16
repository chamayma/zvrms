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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

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
        return buildExcel(voterRepository.findAll(), "Voters");
    }

    private byte[] buildExcel(List<Voter> voters, String sheetName) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(sheetName);
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

    private VoterResponse mapToResponse(Voter voter) {
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
        return dto;
    }

    public List<Voter> getFilteredVoters(
            String search,
            Long districtId,
            Long shehiaId,
            String sex,
            LocalDate dateFrom,
            LocalDate dateTo) {

        List<Voter> voters = voterRepository.findAll();

        if (search != null && !search.isBlank()) {
            voters = voters.stream()
                    .filter(v -> v.getFullName()
                            .toLowerCase()
                            .contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (districtId != null) {
            voters = voters.stream()
                    .filter(v -> v.getDistrict().getId().equals(districtId))
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

        if (dateFrom != null) {
            voters = voters.stream()
                    .filter(v -> !v.getCreatedAt().isBefore(dateFrom.atStartOfDay()))
                    .collect(Collectors.toList());
        }

        if (dateTo != null) {
            voters = voters.stream()
                    .filter(v -> !v.getCreatedAt().isAfter(dateTo.atTime(LocalTime.MAX)))
                    .collect(Collectors.toList());
        }

        return voters;
    }

    public List<Voter> getFilteredDistrictVoters(
            Authentication authentication,
            String search,
            Long shehiaId,
            String sex,
            LocalDate dateFrom,
            LocalDate dateTo) {

        User officer = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return getFilteredVoters(search, officer.getDistrict().getId(), shehiaId, sex, dateFrom, dateTo);
    }

    public List<VoterResponse> getDistrictReport(
            Authentication authentication,
            String search,
            Long shehiaId,
            String sex,
            LocalDate dateFrom,
            LocalDate dateTo) {
        return getFilteredDistrictVoters(authentication, search, shehiaId, sex, dateFrom, dateTo)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Page<VoterResponse> getReportsPage(
            String search,
            Long districtId,
            Long shehiaId,
            String sex,
            LocalDate dateFrom,
            LocalDate dateTo,
            int page,
            int size) {

        List<Voter> filtered = getFilteredVoters(search, districtId, shehiaId, sex, dateFrom, dateTo);
        int total = filtered.size();

        int start = page * size;
        int end = Math.min(start + size, total);

        List<VoterResponse> content = new ArrayList<>();
        if (start < total) {
            for (Voter voter : filtered.subList(start, end)) {
                content.add(mapToResponse(voter));
            }
        }

        return new PageImpl<>(content, PageRequest.of(page, size), total);
    }

    public byte[] filteredPdfReport(
            String search,
            Long districtId,
            Long shehiaId,
            String sex,
            LocalDate dateFrom,
            LocalDate dateTo) {
        List<Voter> voters = getFilteredVoters(search, districtId, shehiaId, sex, dateFrom, dateTo);
        return buildPdf(voters, "VOTERS REPORT");
    }

    public byte[] filteredDistrictPdfReport(
            Authentication authentication,
            String search,
            Long shehiaId,
            String sex,
            LocalDate dateFrom,
            LocalDate dateTo) {
        List<Voter> voters = getFilteredDistrictVoters(authentication, search, shehiaId, sex, dateFrom, dateTo);
        return buildPdf(voters, "DISTRICT VOTERS REPORT");
    }

    public byte[] filteredExcelReport(
            String search,
            Long districtId,
            Long shehiaId,
            String sex,
            LocalDate dateFrom,
            LocalDate dateTo) {
        List<Voter> voters = getFilteredVoters(search, districtId, shehiaId, sex, dateFrom, dateTo);
        return buildExcel(voters, "Voters Report");
    }

    public byte[] filteredDistrictExcelReport(
            Authentication authentication,
            String search,
            Long shehiaId,
            String sex,
            LocalDate dateFrom,
            LocalDate dateTo) {
        List<Voter> voters = getFilteredDistrictVoters(authentication, search, shehiaId, sex, dateFrom, dateTo);
        return buildExcel(voters, "District Voters Report");
    }
}