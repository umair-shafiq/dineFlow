package com.dev.dineFlow.service;

import com.dev.dineFlow.dto.response.CategoryRevenueResponseDto;
import com.dev.dineFlow.dto.response.MostOrderedItemResponseDto;
import com.dev.dineFlow.dto.response.PeakHourResponseDto;
import com.dev.dineFlow.dto.response.SalesReportResponseDto;
import com.dev.dineFlow.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService
{
    private final ReportRepository reportRepository;

    public SalesReportResponseDto getSalesReportByOrder(LocalDateTime from, LocalDateTime to)
    {
        return reportRepository.getSalesReport(from, to);
    }

    public List<MostOrderedItemResponseDto> getMostOrderedItems(int limit)
    {
        return reportRepository.getMostOrderedItems(PageRequest.of(0, limit));
    }

    public List<CategoryRevenueResponseDto> getCategoryRevenue()
    {
        return reportRepository.getCategoryRevenue();
    }

    public List<PeakHourResponseDto> getPeakHours()
    {
        List<LocalDateTime> timestamps = reportRepository.findAllCompletedOrderTimestamps();

        Map<Integer, Long> countByHour = timestamps.stream()
                .collect(Collectors.groupingBy(LocalDateTime::getHour, Collectors.counting()));

        return countByHour.entrySet().stream()
                .map(entry -> new PeakHourResponseDto(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingInt(PeakHourResponseDto::getHourOfDay))
                .toList();
    }
}
