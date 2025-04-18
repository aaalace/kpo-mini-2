package com.aaalace.kpomini2.presentation.dto;

import com.aaalace.kpomini2.domain.valueobject.AnimalType;
import com.aaalace.kpomini2.domain.valueobject.FoodType;
import com.aaalace.kpomini2.domain.valueobject.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAnimalRequest {
    @NotNull(message = "Тип животного должен быть указан")
    private AnimalType type;
    
    @NotBlank(message = "Кличка животного должна быть указана")
    private String name;
    
    @NotNull(message = "Дата рождения должна быть указана")
    @Past(message = "Дата рождения должна быть в прошлом")
    private LocalDate birthDate;
    
    @NotNull(message = "Пол животного должен быть указан")
    private Gender gender;
    
    @NotNull(message = "Любимая пища должна быть указана")
    private FoodType favoriteFood;
} 