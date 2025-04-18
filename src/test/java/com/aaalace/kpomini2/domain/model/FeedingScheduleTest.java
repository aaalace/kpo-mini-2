package com.aaalace.kpomini2.domain.model;

import com.aaalace.kpomini2.domain.valueobject.AnimalType;
import com.aaalace.kpomini2.domain.valueobject.FoodType;
import com.aaalace.kpomini2.domain.valueobject.Gender;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class FeedingScheduleTest {

    @Test
    void createFeedingSchedule_ShouldCreateNotCompletedSchedule() {
        
        UUID id = UUID.randomUUID();
        Animal animal = new Animal(
                UUID.randomUUID(),
                AnimalType.HERBIVORE,
                "Жираф",
                LocalDate.now().minusYears(3),
                Gender.MALE,
                FoodType.GRASS
        );
        LocalDateTime feedingTime = LocalDateTime.now().plusHours(2);
        FoodType foodType = FoodType.GRASS;
        
        
        FeedingSchedule feedingSchedule = new FeedingSchedule(id, animal, feedingTime, foodType);
        
        
        assertEquals(id, feedingSchedule.getId());
        assertEquals(animal, feedingSchedule.getAnimal());
        assertEquals(feedingTime, feedingSchedule.getFeedingTime());
        assertEquals(foodType, feedingSchedule.getFoodType());
        assertFalse(feedingSchedule.isCompleted());
    }
    
    @Test
    void markCompleted_ShouldMarkScheduleCompletedAndFeedAnimal() {
        
        UUID id = UUID.randomUUID();
        Animal animal = Mockito.mock(Animal.class);
        LocalDateTime feedingTime = LocalDateTime.now().plusHours(2);
        FoodType foodType = FoodType.MEAT;
        
        FeedingSchedule feedingSchedule = new FeedingSchedule(id, animal, feedingTime, foodType);
        
        
        feedingSchedule.markCompleted();
        
        
        assertTrue(feedingSchedule.isCompleted());
        verify(animal, times(1)).feed();
    }
    
    @Test
    void isOverdue_WithPastUncompletedFeeding_ShouldReturnTrue() {
        
        UUID id = UUID.randomUUID();
        Animal animal = new Animal(
                UUID.randomUUID(),
                AnimalType.PREDATOR,
                "Тигр",
                LocalDate.now().minusYears(4),
                Gender.MALE,
                FoodType.MEAT
        );
        LocalDateTime feedingTime = LocalDateTime.now().minusHours(2); 
        FoodType foodType = FoodType.MEAT;
        
        FeedingSchedule feedingSchedule = new FeedingSchedule(id, animal, feedingTime, foodType);
        
        
        assertTrue(feedingSchedule.isOverdue());
    }
} 