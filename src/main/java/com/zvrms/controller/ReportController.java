package com.zvrms.controller;

import com.zvrms.service.ReportService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReportController {

    private final ReportService reportService;

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
public ResponseEntity<byte[]> excel() {

    return ResponseEntity.ok()

            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=voters.xlsx"
            )

            .contentType(
                    MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                    )
            )

            .body(reportService.excelReport());

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


}