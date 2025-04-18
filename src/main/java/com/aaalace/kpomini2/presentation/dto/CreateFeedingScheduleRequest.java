package com.aaalace.kpomini2.presentation.dto;

import com.aaalace.kpomini2.domain.valueobject.FoodType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFeedingScheduleRequest {
    @NotNull(message = "ID животного должен быть указан")
    private UUID animalId;
    
    @NotNull(message = "Время кормления должно быть указано")
    @Future(message = "Время кормления должно быть в будущем")
    private LocalDateTime feedingTime;
    
    @NotNull(message = "Тип пищи должен быть указан")
    private FoodType foodType;
} 