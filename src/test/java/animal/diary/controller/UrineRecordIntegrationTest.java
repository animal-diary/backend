package animal.diary.controller;

import animal.diary.dto.RecordNumberDTO;
import animal.diary.dto.RequestDateDTO;
import animal.diary.entity.pet.Pet;
import animal.diary.entity.pet.Type;
import animal.diary.entity.pet.Gender;
import animal.diary.entity.pet.Health;
import animal.diary.entity.pet.Neutered;
import animal.diary.entity.User;
import animal.diary.repository.PetRepository;
import animal.diary.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class UrineRecordIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;
    private Pet testPet;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        // Create test user and pet
        User testUser = User.builder()
                .email("test@example.com")
                .name("Test User")
                .nickname("TestNick")
                .build();
        userRepository.save(testUser);

        testPet = Pet.builder()
                .name("TestPet")
                .type(Type.DOG)
                .gender(Gender.MALE)
                .neutered(Neutered.O)
                .species("Golden Retriever")
                .health(Health.GOOD)
                .user(testUser)
                .build();
        petRepository.save(testPet);
    }

    @Test
    void testRecordUrine() throws Exception {
        RecordNumberDTO recordDTO = RecordNumberDTO.builder()
                .petId(testPet.getId())
                .urineState("NORMAL")
                .urineAmount("NORMAL")
                .build();

        mockMvc.perform(post("/record/urine")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recordDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("SUCCESS_SAVE_URINE"))
                .andExpect(jsonPath("$.data.petId").value(testPet.getId()))
                .andExpect(jsonPath("$.data.urineState").value("NORMAL"))
                .andExpect(jsonPath("$.data.urineAmount").value("NORMAL"));
    }

    @Test
    void testGetUrineByDate() throws Exception {
        // First record a urine entry
        RecordNumberDTO recordDTO = RecordNumberDTO.builder()
                .petId(testPet.getId())
                .urineState("BLOODY")
                .urineAmount("LOW")
                .build();

        mockMvc.perform(post("/record/urine")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recordDTO)))
                .andExpect(status().isCreated());

        // Then retrieve by date
        RequestDateDTO dateDTO = new RequestDateDTO();
        dateDTO.setPetId(testPet.getId());
        dateDTO.setDate(LocalDate.now());

        mockMvc.perform(post("/record/urine/date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS_GET_URINE_BY_DATE"))
                .andExpect(jsonPath("$.data.dateDTOS[0].urineState").value("BLOODY"))
                .andExpect(jsonPath("$.data.dateDTOS[0].urineAmount").value("LOW"));
    }

    @Test
    void testValidationErrors() throws Exception {
        // Test with missing urineState
        RecordNumberDTO recordDTO = RecordNumberDTO.builder()
                .petId(testPet.getId())
                .urineAmount("NORMAL")
                .build();

        mockMvc.perform(post("/record/urine")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recordDTO)))
                .andExpect(status().isBadRequest());

        // Test with missing urineAmount
        recordDTO = RecordNumberDTO.builder()
                .petId(testPet.getId())
                .urineState("NORMAL")
                .build();

        mockMvc.perform(post("/record/urine")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recordDTO)))
                .andExpect(status().isBadRequest());
    }
}