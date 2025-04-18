package com.aaalace.kpomini2.domain.model;

import com.aaalace.kpomini2.domain.valueobject.AnimalType;
import com.aaalace.kpomini2.domain.valueobject.FoodType;
import com.aaalace.kpomini2.domain.valueobject.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class Animal {
    private final UUID id;
    private final AnimalType type;
    private String name;
    private final LocalDate birthDate;
    private final Gender gender;
    @Setter
    private FoodType favoriteFood;
    private boolean healthy;
    private Enclosure currentEnclosure;

    public Animal(UUID id, AnimalType type, String name, LocalDate birthDate, Gender gender, FoodType favoriteFood) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.favoriteFood = favoriteFood;
        this.healthy = true;
    }
    
    public void feed() {
        System.out.println("Животное " + name + " накормлено");
    }
    
    public void heal() {
        if (!healthy) {
            healthy = true;
            System.out.println("Животное " + name + " вылечено");
        } else {
            System.out.println("Животное " + name + " уже здорово");
        }
    }
    
    public void makeIll() {
        this.healthy = false;
    }
    
    public void setEnclosure(Enclosure enclosure) {
        if (isCompatibleWithEnclosure(enclosure)) {
            this.currentEnclosure = enclosure;
        } else {
            throw new IllegalArgumentException("Животное не совместимо с этим типом вольера");
        }
    }
    
    private boolean isCompatibleWithEnclosure(Enclosure enclosure) {
        return switch (type) {
            case PREDATOR -> enclosure.getType().toString().contains("PREDATOR");
            case HERBIVORE -> enclosure.getType().toString().contains("HERBIVORE");
            case BIRD -> enclosure.getType().toString().contains("BIRD");
            case AQUATIC -> enclosure.getType().toString().contains("AQUARIUM");
            case REPTILE -> enclosure.getType().toString().contains("REPTILE");
            case AMPHIBIAN -> enclosure.getType().toString().contains("AMPHIBIAN");
        };
    }
    
    public void changeName(String newName) {
        if (newName != null && !newName.trim().isEmpty()) {
            this.name = newName;
        } else {
            throw new IllegalArgumentException("Новое имя не может быть пустым");
        }
    }
} 