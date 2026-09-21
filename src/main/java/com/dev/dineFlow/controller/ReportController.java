package com.dev.dineFlow.controller;

import com.dev.dineFlow.dto.response.CategoryRevenueResponseDto;
import com.dev.dineFlow.dto.response.MostOrderedItemResponseDto;
import com.dev.dineFlow.dto.response.PeakHourResponseDto;
import com.dev.dineFlow.dto.response.SalesReportResponseDto;
import com.dev.dineFlow.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportController
{
    private final ReportService reportService;

    @GetMapping("/sales")
    public ResponseEntity<SalesReportResponseDto> getSalesReportByOrder(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate)
    {
        if (startDate.isAfter(endDate))
        {
            throw new IllegalArgumentException("startDate must be before endDate");
        }
        return ResponseEntity.ok(reportService.getSalesReportByOrder(startDate, endDate));
    }

    @GetMapping("/most-ordered-items")
    public ResponseEntity<List<MostOrderedItemResponseDto>> getMostOrderedItems(@RequestParam(defaultValue = "10") int limit)
    {
        return ResponseEntity.ok(reportService.getMostOrderedItems(limit));
    }

    @GetMapping("/revenue-by-category")
    public ResponseEntity<List<CategoryRevenueResponseDto>> getCategoryRevenue()
    {
        return ResponseEntity.ok(reportService.getCategoryRevenue());
    }

    @GetMapping("/peak-hours")
    public ResponseEntity<List<PeakHourResponseDto>> getPeakHours()
    {
        return ResponseEntity.ok(reportService.getPeakHours());
    }

}
