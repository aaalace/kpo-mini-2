package com.aaalace.kpomini2.application.port.in;

import java.util.UUID;

public interface AnimalTransferUseCase {
    void transferAnimal(UUID animalId, UUID targetEnclosureId);
} 