package com.aaalace.kpomini2.e2e;

import com.aaalace.kpomini2.domain.repository.AnimalRepository;
import com.aaalace.kpomini2.domain.repository.EnclosureRepository;
import com.aaalace.kpomini2.domain.repository.FeedingScheduleRepository;
import com.aaalace.kpomini2.domain.valueobject.AnimalType;
import com.aaalace.kpomini2.domain.valueobject.EnclosureType;
import com.aaalace.kpomini2.domain.valueobject.FoodType;
import com.aaalace.kpomini2.domain.valueobject.Gender;
import com.aaalace.kpomini2.presentation.dto.AnimalTransferRequest;
import com.aaalace.kpomini2.presentation.dto.CreateAnimalRequest;
import com.aaalace.kpomini2.presentation.dto.CreateEnclosureRequest;
import com.aaalace.kpomini2.presentation.dto.CreateFeedingScheduleRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AnimalTransferAndFeedingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private AnimalRepository animalRepository;
    
    @Autowired
    private EnclosureRepository enclosureRepository;
    
    @Autowired
    private FeedingScheduleRepository feedingScheduleRepository;
    
    @Test
    void completeAnimalTransferAndFeedingProcess() throws Exception {
        
        CreateEnclosureRequest enclosureRequest = new CreateEnclosureRequest(
                EnclosureType.PREDATOR_ENCLOSURE,
                3
        );
        
        MvcResult enclosureResult = mockMvc.perform(post("/api/enclosures")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(enclosureRequest)))
                .andExpect(status().isCreated())
                .andReturn();
        
        String enclosureIdStr = enclosureResult.getResponse().getContentAsString();
        enclosureIdStr = enclosureIdStr.replace("\"", "");
        UUID enclosureId = UUID.fromString(enclosureIdStr);
        
        
        CreateAnimalRequest animalRequest = new CreateAnimalRequest(
                AnimalType.PREDATOR,
                "Лев Алекс",
                LocalDate.of(2016, 7, 15),
                Gender.MALE,
                FoodType.MEAT
        );
        
        MvcResult animalResult = mockMvc.perform(post("/api/animals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(animalRequest)))
                .andExpect(status().isCreated())
                .andReturn();
        
        String animalIdStr = animalResult.getResponse().getContentAsString();
        animalIdStr = animalIdStr.replace("\"", "");
        UUID animalId = UUID.fromString(animalIdStr);
        
        
        AnimalTransferRequest transferRequest = new AnimalTransferRequest(enclosureId);
        
        mockMvc.perform(post("/api/animals/transfer/{animalId}", animalId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferRequest)))
                .andExpect(status().isOk());
        
        
        mockMvc.perform(get("/api/animals/{id}", animalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentEnclosureId").value(enclosureId.toString()));
        
        
        LocalDateTime feedingTime = LocalDateTime.now().plusHours(1);
        CreateFeedingScheduleRequest feedingRequest = new CreateFeedingScheduleRequest(
                animalId,
                feedingTime,
                FoodType.MEAT
        );
        
        MvcResult feedingResult = mockMvc.perform(post("/api/feeding-schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(feedingRequest)))
                .andExpect(status().isCreated())
                .andReturn();
        
        String feedingIdStr = feedingResult.getResponse().getContentAsString();
        feedingIdStr = feedingIdStr.replace("\"", "");
        UUID feedingId = UUID.fromString(feedingIdStr);
        
        
        mockMvc.perform(get("/api/feeding-schedules/{id}", feedingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animalId").value(animalId.toString()))
                .andExpect(jsonPath("$.animalName").value("Лев Алекс"))
                .andExpect(jsonPath("$.foodType").value("MEAT"))
                .andExpect(jsonPath("$.completed").value(false));
        
        
        mockMvc.perform(put("/api/feeding-schedules/{id}/complete", feedingId))
                .andExpect(status().isOk());
        
        
        mockMvc.perform(get("/api/feeding-schedules/{id}", feedingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));
        
        
        mockMvc.perform(get("/api/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalAnimalCount").value(1))
                .andExpect(jsonPath("$.healthyAnimalCount").value(1))
                .andExpect(jsonPath("$.totalEnclosureCount").value(1))
                .andExpect(jsonPath("$.totalScheduledFeedings").value(1))
                .andExpect(jsonPath("$.completedFeedingsCount").value(1));
        
        
        mockMvc.perform(delete("/api/feeding-schedules/{id}", feedingId))
                .andExpect(status().isNoContent());
        
        mockMvc.perform(delete("/api/animals/{id}", animalId))
                .andExpect(status().isNoContent());
        
        mockMvc.perform(delete("/api/enclosures/{id}", enclosureId))
                .andExpect(status().isNoContent());
    }
} 