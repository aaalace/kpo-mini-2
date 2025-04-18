package com.aaalace.kpomini2.presentation.controller;

import com.aaalace.kpomini2.application.port.in.AnimalTransferUseCase;
import com.aaalace.kpomini2.presentation.dto.AnimalTransferRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/animals/transfer")
@RequiredArgsConstructor
@Tag(name = "Перемещение животных", description = "API для перемещения животных между вольерами")
public class AnimalTransferController {
    
    private final AnimalTransferUseCase animalTransferUseCase;
    
    @PostMapping("/{animalId}")
    @Operation(summary = "Переместить животное", description = "Перемещает животное в другой вольер")
    public ResponseEntity<Void> transferAnimal(
            @PathVariable UUID animalId,
            @Valid @RequestBody AnimalTransferRequest request) {
        
        animalTransferUseCase.transferAnimal(animalId, request.getTargetEnclosureId());
        return ResponseEntity.ok().build();
    }
} 