package com.aaalace.kpomini2.presentation.controller;

import com.aaalace.kpomini2.application.port.in.AnimalManagementUseCase;
import com.aaalace.kpomini2.domain.model.Animal;
import com.aaalace.kpomini2.domain.valueobject.FoodType;
import com.aaalace.kpomini2.presentation.dto.AnimalDto;
import com.aaalace.kpomini2.presentation.dto.CreateAnimalRequest;
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
@RequestMapping("/api/animals")
@RequiredArgsConstructor
@Tag(name = "Управление животными", description = "API для управления животными зоопарка")
public class AnimalController {
    
    private final AnimalManagementUseCase animalManagementUseCase;
    
    @GetMapping
    @Operation(summary = "Получить всех животных", description = "Возвращает список всех животных в зоопарке")
    public ResponseEntity<List<AnimalDto>> getAllAnimals() {
        List<Animal> animals = animalManagementUseCase.getAllAnimals();
        List<AnimalDto> animalDtos = animals.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(animalDtos);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Получить животное по ID", description = "Возвращает информацию о конкретном животном по его ID")
    public ResponseEntity<AnimalDto> getAnimalById(@PathVariable UUID id) {
        Animal animal = animalManagementUseCase.getAnimal(id);
        return ResponseEntity.ok(mapToDto(animal));
    }
    
    @PostMapping
    @Operation(summary = "Создать новое животное", description = "Создает новое животное в зоопарке")
    public ResponseEntity<UUID> createAnimal(@Valid @RequestBody CreateAnimalRequest request) {
        UUID animalId = animalManagementUseCase.createAnimal(
                request.getType(),
                request.getName(),
                request.getBirthDate(),
                request.getGender(),
                request.getFavoriteFood()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(animalId);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить животное", description = "Удаляет животное из зоопарка по его ID")
    public ResponseEntity<Void> deleteAnimal(@PathVariable UUID id) {
        animalManagementUseCase.removeAnimal(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/heal")
    @Operation(summary = "Вылечить животное", description = "Меняет статус животного на 'здоров'")
    public ResponseEntity<Void> healAnimal(@PathVariable UUID id) {
        animalManagementUseCase.healAnimal(id);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{id}/makeIll")
    @Operation(summary = "Отметить животное как больное", description = "Меняет статус животного на 'болен'")
    public ResponseEntity<Void> makeAnimalIll(@PathVariable UUID id) {
        animalManagementUseCase.setAnimalIll(id);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{id}/name")
    @Operation(summary = "Изменить кличку животного", description = "Обновляет кличку животного")
    public ResponseEntity<Void> updateAnimalName(@PathVariable UUID id, @RequestParam String newName) {
        animalManagementUseCase.updateAnimalName(id, newName);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{id}/favoriteFood")
    @Operation(summary = "Изменить любимую пищу животного", description = "Обновляет информацию о любимой пище животного")
    public ResponseEntity<Void> updateAnimalFavoriteFood(@PathVariable UUID id, @RequestParam FoodType newFavoriteFood) {
        animalManagementUseCase.updateAnimalFavoriteFood(id, newFavoriteFood);
        return ResponseEntity.ok().build();
    }
    
    private AnimalDto mapToDto(Animal animal) {
        return AnimalDto.builder()
                .id(animal.getId())
                .type(animal.getType())
                .name(animal.getName())
                .birthDate(animal.getBirthDate())
                .gender(animal.getGender())
                .favoriteFood(animal.getFavoriteFood())
                .healthy(animal.isHealthy())
                .currentEnclosureId(animal.getCurrentEnclosure() != null ? animal.getCurrentEnclosure().getId() : null)
                .build();
    }
} 