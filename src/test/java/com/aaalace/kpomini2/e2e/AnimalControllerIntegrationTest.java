package com.aaalace.kpomini2.e2e;

import com.aaalace.kpomini2.domain.model.Animal;
import com.aaalace.kpomini2.domain.repository.AnimalRepository;
import com.aaalace.kpomini2.domain.valueobject.AnimalType;
import com.aaalace.kpomini2.domain.valueobject.FoodType;
import com.aaalace.kpomini2.domain.valueobject.Gender;
import com.aaalace.kpomini2.presentation.dto.AnimalDto;
import com.aaalace.kpomini2.presentation.dto.CreateAnimalRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AnimalControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private AnimalRepository animalRepository;
    
    @Test
    void fullAnimalLifecycle() throws Exception {
        CreateAnimalRequest createRequest = new CreateAnimalRequest(
                AnimalType.HERBIVORE,
                "Жираф Мелман",
                LocalDate.of(2015, 3, 20),
                Gender.MALE,
                FoodType.GRASS
        );
        
        MvcResult createResult = mockMvc.perform(post("/api/animals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn();
        
        String animalIdStr = createResult.getResponse().getContentAsString();
        animalIdStr = animalIdStr.replace("\"", "");
        UUID animalId = UUID.fromString(animalIdStr);

        assertTrue(animalRepository.findById(animalId).isPresent());

        mockMvc.perform(get("/api/animals/{id}", animalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(animalId.toString()))
                .andExpect(jsonPath("$.name").value("Жираф Мелман"))
                .andExpect(jsonPath("$.type").value("HERBIVORE"))
                .andExpect(jsonPath("$.healthy").value(true))
                .andReturn();

        mockMvc.perform(put("/api/animals/{id}/makeIll", animalId))
                .andExpect(status().isOk());

        Animal animal = animalRepository.findById(animalId).orElseThrow();
        assertFalse(animal.isHealthy());

        mockMvc.perform(put("/api/animals/{id}/heal", animalId))
                .andExpect(status().isOk());

        animal = animalRepository.findById(animalId).orElseThrow();
        assertTrue(animal.isHealthy());

        String newName = "Жираф Маркус";
        mockMvc.perform(put("/api/animals/{id}/name", animalId)
                .param("newName", newName))
                .andExpect(status().isOk());

        animal = animalRepository.findById(animalId).orElseThrow();
        assertEquals(newName, animal.getName());
        
        MvcResult getAllResult = mockMvc.perform(get("/api/animals"))
                .andExpect(status().isOk())
                .andReturn();
        
        List<AnimalDto> animals = objectMapper.readValue(
                getAllResult.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, AnimalDto.class)
        );
        
        assertTrue(animals.stream().anyMatch(a -> a.getId().equals(animalId)));
        
        
        mockMvc.perform(delete("/api/animals/{id}", animalId))
                .andExpect(status().isNoContent());
        
        
        assertFalse(animalRepository.findById(animalId).isPresent());
    }
} 