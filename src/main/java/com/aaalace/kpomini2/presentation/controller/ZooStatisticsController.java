package com.aaalace.kpomini2.presentation.controller;

import com.aaalace.kpomini2.application.port.in.ZooStatisticsUseCase;
import com.aaalace.kpomini2.presentation.dto.ZooStatisticsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
@Tag(name = "Статистика зоопарка", description = "API для получения статистики зоопарка")
public class ZooStatisticsController {
    
    private final ZooStatisticsUseCase zooStatisticsUseCase;
    
    @GetMapping
    @Operation(summary = "Получить общую статистику", description = "Возвращает общую статистику по зоопарку")
    public ResponseEntity<ZooStatisticsDto> getZooStatistics() {
        ZooStatisticsDto statistics = ZooStatisticsDto.builder()
                .totalAnimalCount(zooStatisticsUseCase.getTotalAnimalCount())
                .healthyAnimalCount(zooStatisticsUseCase.getHealthyAnimalCount())
                .sickAnimalCount(zooStatisticsUseCase.getSickAnimalCount())
                .animalCountByType(zooStatisticsUseCase.getAnimalCountByType())
                
                .totalEnclosureCount(zooStatisticsUseCase.getTotalEnclosureCount())
                .availableEnclosureCount(zooStatisticsUseCase.getAvailableEnclosureCount())
                .occupiedEnclosureCount(zooStatisticsUseCase.getOccupiedEnclosureCount())
                .enclosureCountByType(zooStatisticsUseCase.getEnclosureCountByType())
                
                .totalScheduledFeedings(zooStatisticsUseCase.getTotalScheduledFeedings())
                .completedFeedingsCount(zooStatisticsUseCase.getCompletedFeedingsCount())
                .pendingFeedingsCount(zooStatisticsUseCase.getPendingFeedingsCount())
                .overdueFeedingsCount(zooStatisticsUseCase.getOverdueFeedingsCount())
                .build();
        
        return ResponseEntity.ok(statistics);
    }
} 