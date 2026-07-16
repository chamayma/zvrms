package com.zvrms.controller;

import com.zvrms.dto.voter.VoterResponse;
import com.zvrms.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<Page<VoterResponse>> getReports(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long districtId,
            @RequestParam(required = false) Long shehiaId,
            @RequestParam(required = false) String sex,
            @RequestParam(required = false) LocalDate dateFrom,
            @RequestParam(required = false) LocalDate dateTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(
            reportService.getReportsPage(search, districtId, shehiaId, sex, dateFrom, dateTo, page, size)
        );
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> exportPdf(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long districtId,
            @RequestParam(required = false) Long shehiaId,
            @RequestParam(required = false) String sex,
            @RequestParam(required = false) LocalDate dateFrom,
            @RequestParam(required = false) LocalDate dateTo) {
        return pdf(
            reportService.filteredPdfReport(search, districtId, shehiaId, sex, dateFrom, dateTo),
            "Director_Report.pdf"
        );
    }

    @GetMapping("/all")
    public ResponseEntity<byte[]> all() {

        return pdf(reportService.allReport(), "all_voters.pdf");

    }

    @GetMapping("/district/{districtId}")
    public ResponseEntity<byte[]> district(
            @PathVariable Long districtId) {

        return pdf(
                reportService.districtReport(districtId),
                "district_report.pdf"
        );
    }

    @GetMapping("/shehia/{shehiaId}")
    public ResponseEntity<byte[]> shehia(
            @PathVariable Long shehiaId) {

        return pdf(
                reportService.shehiaReport(shehiaId),
                "shehia_report.pdf"
        );
    }

    private ResponseEntity<byte[]> pdf(byte[] file, String name) {

        return ResponseEntity.ok()

                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + name
                )

                .contentType(MediaType.APPLICATION_PDF)

                .body(file);

    }

    @GetMapping("/excel")
    public ResponseEntity<byte[]> exportExcel(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long districtId,
            @RequestParam(required = false) Long shehiaId,
            @RequestParam(required = false) String sex,
            @RequestParam(required = false) LocalDate dateFrom,
            @RequestParam(required = false) LocalDate dateTo) {

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Director_Report.xlsx"
                )
                .contentType(
                        MediaType.parseMediaType(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                        )
                )
                .body(reportService.filteredExcelReport(search, districtId, shehiaId, sex, dateFrom, dateTo));
    }

@GetMapping("/date/pdf")
public ResponseEntity<byte[]> datePdf(

        @RequestParam LocalDateTime start,

        @RequestParam LocalDateTime end) {

    return pdf(

            reportService.dateRangePdf(start, end),

            "date_report.pdf"

    );

}

@GetMapping("/date/excel")
public ResponseEntity<byte[]> dateExcel(

        @RequestParam LocalDateTime start,

        @RequestParam LocalDateTime end) {

    return ResponseEntity.ok()

            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=date_report.xlsx"
            )

            .contentType(
                    MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                    )
            )

            .body(

                    reportService.dateRangeExcel(start, end)

            );

}

@GetMapping("/district")
@PreAuthorize("hasRole('DISTRICT_OFFICER')")
public ResponseEntity<List<VoterResponse>> districtReport(
        Authentication authentication,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) Long shehiaId,
        @RequestParam(required = false) String sex,
        @RequestParam(required = false) LocalDate dateFrom,
        @RequestParam(required = false) LocalDate dateTo) {

    return ResponseEntity.ok(

        reportService.getDistrictReport(
                authentication,
                search,
                shehiaId,
                sex,
                dateFrom,
                dateTo)

    );
}

@GetMapping("/district/pdf")
@PreAuthorize("hasRole('DISTRICT_OFFICER')")
public ResponseEntity<byte[]> exportDistrictPdf(
        Authentication authentication,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) Long shehiaId,
        @RequestParam(required = false) String sex,
        @RequestParam(required = false) LocalDate dateFrom,
        @RequestParam(required = false) LocalDate dateTo) {
    return pdf(
        reportService.filteredDistrictPdfReport(authentication, search, shehiaId, sex, dateFrom, dateTo),
        "District_Report.pdf"
    );
}

@GetMapping("/district/excel")
@PreAuthorize("hasRole('DISTRICT_OFFICER')")
public ResponseEntity<byte[]> exportDistrictExcel(
        Authentication authentication,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) Long shehiaId,
        @RequestParam(required = false) String sex,
        @RequestParam(required = false) LocalDate dateFrom,
        @RequestParam(required = false) LocalDate dateTo) {
    return ResponseEntity.ok()
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=District_Report.xlsx"
            )
            .contentType(
                    MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                    )
            )
            .body(reportService.filteredDistrictExcelReport(authentication, search, shehiaId, sex, dateFrom, dateTo));
}

}