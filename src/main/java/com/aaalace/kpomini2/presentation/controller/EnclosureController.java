package com.aaalace.kpomini2.presentation.controller;

import com.aaalace.kpomini2.application.port.in.EnclosureManagementUseCase;
import com.aaalace.kpomini2.domain.model.Animal;
import com.aaalace.kpomini2.domain.model.Enclosure;
import com.aaalace.kpomini2.domain.valueobject.EnclosureType;
import com.aaalace.kpomini2.presentation.dto.CreateEnclosureRequest;
import com.aaalace.kpomini2.presentation.dto.EnclosureDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/enclosures")
@RequiredArgsConstructor
@Tag(name = "Управление вольерами", description = "API для управления вольерами зоопарка")
public class EnclosureController {
    
    private final EnclosureManagementUseCase enclosureManagementUseCase;
    
    @GetMapping
    @Operation(summary = "Получить все вольеры", description = "Возвращает список всех вольеров в зоопарке")
    public ResponseEntity<List<EnclosureDto>> getAllEnclosures() {
        List<Enclosure> enclosures = enclosureManagementUseCase.getAllEnclosures();
        List<EnclosureDto> enclosureDtos = enclosures.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(enclosureDtos);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Получить вольер по ID", description = "Возвращает информацию о конкретном вольере по его ID")
    public ResponseEntity<EnclosureDto> getEnclosureById(@PathVariable UUID id) {
        Enclosure enclosure = enclosureManagementUseCase.getEnclosure(id);
        return ResponseEntity.ok(mapToDto(enclosure));
    }
    
    @GetMapping("/available")
    @Operation(summary = "Получить доступные вольеры", description = "Возвращает список вольеров, имеющих свободные места")
    public ResponseEntity<List<EnclosureDto>> getAvailableEnclosures() {
        List<Enclosure> enclosures = enclosureManagementUseCase.getAvailableEnclosures();
        List<EnclosureDto> enclosureDtos = enclosures.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(enclosureDtos);
    }
    
    @GetMapping("/byType")
    @Operation(summary = "Получить вольеры по типу", description = "Возвращает список вольеров определенного типа")
    public ResponseEntity<List<EnclosureDto>> getEnclosuresByType(@RequestParam EnclosureType type) {
        List<Enclosure> enclosures = enclosureManagementUseCase.getEnclosuresByType(type);
        List<EnclosureDto> enclosureDtos = enclosures.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(enclosureDtos);
    }
    
    @PostMapping
    @Operation(summary = "Создать новый вольер", description = "Создает новый вольер в зоопарке")
    public ResponseEntity<UUID> createEnclosure(@Valid @RequestBody CreateEnclosureRequest request) {
        UUID enclosureId = enclosureManagementUseCase.createEnclosure(
                request.getType(),
                request.getMaxCapacity()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(enclosureId);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить вольер", description = "Удаляет пустой вольер из зоопарка по его ID")
    public ResponseEntity<Void> deleteEnclosure(@PathVariable UUID id) {
        enclosureManagementUseCase.removeEnclosure(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/clean")
    @Operation(summary = "Очистить вольер", description = "Проводит уборку в вольере")
    public ResponseEntity<Void> cleanEnclosure(@PathVariable UUID id) {
        enclosureManagementUseCase.cleanEnclosure(id);
        return ResponseEntity.ok().build();
    }
    
    private EnclosureDto mapToDto(Enclosure enclosure) {
        List<UUID> animalIds = enclosure.getAnimals().stream()
                .map(Animal::getId)
                .collect(Collectors.toList());
        
        return EnclosureDto.builder()
                .id(enclosure.getId())
                .type(enclosure.getType())
                .maxCapacity(enclosure.getMaxCapacity())
                .currentOccupancy(enclosure.getCurrentOccupancy())
                .isCleaned(enclosure.isCleaned())
                .animalIds(animalIds)
                .build();
    }
} 