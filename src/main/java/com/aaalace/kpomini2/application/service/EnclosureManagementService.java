package com.aaalace.kpomini2.application.service;

import com.aaalace.kpomini2.application.port.in.EnclosureManagementUseCase;
import com.aaalace.kpomini2.domain.model.Enclosure;
import com.aaalace.kpomini2.domain.repository.EnclosureRepository;
import com.aaalace.kpomini2.domain.valueobject.EnclosureType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnclosureManagementService implements EnclosureManagementUseCase {
    
    private final EnclosureRepository enclosureRepository;

    @Override
    public UUID createEnclosure(EnclosureType type, int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Вместимость вольера должна быть положительным числом");
        }
        
        Enclosure enclosure = new Enclosure(UUID.randomUUID(), type, maxCapacity);
        Enclosure savedEnclosure = enclosureRepository.save(enclosure);
        return savedEnclosure.getId();
    }

    @Override
    public void removeEnclosure(UUID enclosureId) {
        Enclosure enclosure = getEnclosure(enclosureId);
        
        if (!enclosure.getAnimals().isEmpty()) {
            throw new IllegalStateException("Невозможно удалить вольер, содержащий животных");
        }
        
        enclosureRepository.delete(enclosureId);
    }

    @Override
    public Enclosure getEnclosure(UUID enclosureId) {
        return enclosureRepository.findById(enclosureId)
                .orElseThrow(() -> new IllegalArgumentException("Вольер с id " + enclosureId + " не найден"));
    }

    @Override
    public List<Enclosure> getAllEnclosures() {
        return enclosureRepository.findAll();
    }

    @Override
    public List<Enclosure> getAvailableEnclosures() {
        return enclosureRepository.findAvailable();
    }

    @Override
    public void cleanEnclosure(UUID enclosureId) {
        Enclosure enclosure = getEnclosure(enclosureId);
        enclosure.clean();
        enclosureRepository.save(enclosure);
    }

    @Override
    public List<Enclosure> getEnclosuresByType(EnclosureType type) {
        return enclosureRepository.findByType(type);
    }
} 