package com.aaalace.kpomini2.domain.repository;

import com.aaalace.kpomini2.domain.model.Enclosure;
import com.aaalace.kpomini2.domain.valueobject.EnclosureType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnclosureRepository {
    Enclosure save(Enclosure enclosure);
    Optional<Enclosure> findById(UUID id);
    List<Enclosure> findAll();
    void delete(UUID id);
    List<Enclosure> findByType(EnclosureType type);
    List<Enclosure> findAvailable();
    long countByType(EnclosureType type);
} 