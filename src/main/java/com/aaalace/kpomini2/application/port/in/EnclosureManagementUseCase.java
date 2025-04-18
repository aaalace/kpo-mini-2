package com.aaalace.kpomini2.application.port.in;

import com.aaalace.kpomini2.domain.model.Enclosure;
import com.aaalace.kpomini2.domain.valueobject.EnclosureType;

import java.util.List;
import java.util.UUID;

public interface EnclosureManagementUseCase {
    UUID createEnclosure(EnclosureType type, int maxCapacity);
    void removeEnclosure(UUID enclosureId);
    Enclosure getEnclosure(UUID enclosureId);
    List<Enclosure> getAllEnclosures();
    List<Enclosure> getAvailableEnclosures();
    void cleanEnclosure(UUID enclosureId);
    List<Enclosure> getEnclosuresByType(EnclosureType type);
} 