package com.aaalace.kpomini2.infrastructure.persistence;

import com.aaalace.kpomini2.domain.model.Enclosure;
import com.aaalace.kpomini2.domain.repository.EnclosureRepository;
import com.aaalace.kpomini2.domain.valueobject.EnclosureType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryEnclosureRepository implements EnclosureRepository {
    private final Map<UUID, Enclosure> enclosures = new ConcurrentHashMap<>();

    @Override
    public Enclosure save(Enclosure enclosure) {
        enclosures.put(enclosure.getId(), enclosure);
        return enclosure;
    }

    @Override
    public Optional<Enclosure> findById(UUID id) {
        return Optional.ofNullable(enclosures.get(id));
    }

    @Override
    public List<Enclosure> findAll() {
        return new ArrayList<>(enclosures.values());
    }

    @Override
    public void delete(UUID id) {
        enclosures.remove(id);
    }

    @Override
    public List<Enclosure> findByType(EnclosureType type) {
        return enclosures.values().stream()
                .filter(enclosure -> enclosure.getType() == type)
                .collect(Collectors.toList());
    }

    @Override
    public List<Enclosure> findAvailable() {
        return enclosures.values().stream()
                .filter(Enclosure::hasSpace)
                .collect(Collectors.toList());
    }

    @Override
    public long countByType(EnclosureType type) {
        return enclosures.values().stream()
                .filter(enclosure -> enclosure.getType() == type)
                .count();
    }
} 