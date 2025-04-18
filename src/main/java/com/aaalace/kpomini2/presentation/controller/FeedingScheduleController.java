package com.aaalace.kpomini2.presentation.controller;

import com.aaalace.kpomini2.application.port.in.FeedingManagementUseCase;
import com.aaalace.kpomini2.domain.model.FeedingSchedule;
import com.aaalace.kpomini2.presentation.dto.CreateFeedingScheduleRequest;
import com.aaalace.kpomini2.presentation.dto.FeedingScheduleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/feeding-schedules")
@RequiredArgsConstructor
@Tag(name = "Управление расписанием кормлений", description = "API для управления расписанием кормлений в зоопарке")
public class FeedingScheduleController {
    
    private final FeedingManagementUseCase feedingManagementUseCase;
    
    @GetMapping
    @Operation(summary = "Получить все кормления", description = "Возвращает список всех кормлений в зоопарке")
    public ResponseEntity<List<FeedingScheduleDto>> getAllFeedings() {
        List<FeedingSchedule> feedings = feedingManagementUseCase.getAllFeedings();
        List<FeedingScheduleDto> feedingDtos = feedings.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(feedingDtos);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Получить кормление по ID", description = "Возвращает информацию о конкретном кормлении по его ID")
    public ResponseEntity<FeedingScheduleDto> getFeedingById(@PathVariable UUID id) {
        FeedingSchedule feeding = feedingManagementUseCase.getFeedingDetails(id);
        return ResponseEntity.ok(mapToDto(feeding));
    }
    
    @GetMapping("/animal/{animalId}")
    @Operation(summary = "Получить кормления для животного", description = "Возвращает список кормлений для конкретного животного")
    public ResponseEntity<List<FeedingScheduleDto>> getFeedingsForAnimal(@PathVariable UUID animalId) {
        List<FeedingSchedule> feedings = feedingManagementUseCase.getFeedingsForAnimal(animalId);
        List<FeedingScheduleDto> feedingDtos = feedings.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(feedingDtos);
    }
    
    @GetMapping("/timeRange")
    @Operation(summary = "Получить кормления в диапазоне времени", description = "Возвращает список кормлений, запланированных в указанном диапазоне времени")
    public ResponseEntity<List<FeedingScheduleDto>> getFeedingsInTimeRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        
        List<FeedingSchedule> feedings = feedingManagementUseCase.getFeedingsInTimeRange(start, end);
        List<FeedingScheduleDto> feedingDtos = feedings.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(feedingDtos);
    }
    
    @GetMapping("/pending")
    @Operation(summary = "Получить ожидающие кормления", description = "Возвращает список незавершенных кормлений")
    public ResponseEntity<List<FeedingScheduleDto>> getPendingFeedings() {
        List<FeedingSchedule> feedings = feedingManagementUseCase.getPendingFeedings();
        List<FeedingScheduleDto> feedingDtos = feedings.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(feedingDtos);
    }
    
    @GetMapping("/overdue")
    @Operation(summary = "Получить просроченные кормления", description = "Возвращает список просроченных кормлений")
    public ResponseEntity<List<FeedingScheduleDto>> getOverdueFeedings() {
        List<FeedingSchedule> feedings = feedingManagementUseCase.getOverdueFeedings();
        List<FeedingScheduleDto> feedingDtos = feedings.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(feedingDtos);
    }
    
    @PostMapping
    @Operation(summary = "Создать новое кормление", description = "Создает новое расписание кормления")
    public ResponseEntity<UUID> createFeedingSchedule(@Valid @RequestBody CreateFeedingScheduleRequest request) {
        UUID feedingId = feedingManagementUseCase.scheduleFeedingForAnimal(
                request.getAnimalId(),
                request.getFeedingTime(),
                request.getFoodType()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(feedingId);
    }
    
    @PutMapping("/{id}/complete")
    @Operation(summary = "Отметить кормление как завершенное", description = "Отмечает кормление как выполненное")
    public ResponseEntity<Void> markFeedingAsCompleted(@PathVariable UUID id) {
        feedingManagementUseCase.markFeedingAsCompleted(id);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить кормление", description = "Удаляет кормление из расписания")
    public ResponseEntity<Void> deleteFeedingSchedule(@PathVariable UUID id) {
        feedingManagementUseCase.removeFeedingSchedule(id);
        return ResponseEntity.noContent().build();
    }
    
    private FeedingScheduleDto mapToDto(FeedingSchedule feedingSchedule) {
        return FeedingScheduleDto.builder()
                .id(feedingSchedule.getId())
                .animalId(feedingSchedule.getAnimal().getId())
                .animalName(feedingSchedule.getAnimal().getName())
                .feedingTime(feedingSchedule.getFeedingTime())
                .foodType(feedingSchedule.getFoodType())
                .completed(feedingSchedule.isCompleted())
                .overdue(feedingSchedule.isOverdue())
                .build();
    }
} 