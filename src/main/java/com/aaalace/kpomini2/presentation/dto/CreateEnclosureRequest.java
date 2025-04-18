package com.aaalace.kpomini2.presentation.dto;

import com.aaalace.kpomini2.domain.valueobject.EnclosureType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEnclosureRequest {
    @NotNull(message = "Тип вольера должен быть указан")
    private EnclosureType type;
    
    @NotNull(message = "Максимальная вместимость должна быть указана")
    @Min(value = 1, message = "Максимальная вместимость должна быть не менее 1")
    private Integer maxCapacity;
} 