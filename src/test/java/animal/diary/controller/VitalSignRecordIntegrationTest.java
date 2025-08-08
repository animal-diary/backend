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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class VitalSignRecordIntegrationTest {

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
    void testRecordRR() throws Exception {
        RecordNumberDTO recordDTO = RecordNumberDTO.builder()
                .petId(testPet.getId())
                .count(25)
                .build();

        mockMvc.perform(post("/record/RR")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recordDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("SUCCESS_SAVE_RR"))
                .andExpect(jsonPath("$.data.petId").value(testPet.getId()))
                .andExpect(jsonPath("$.data.count").value(25));
    }

    @Test
    void testRecordHeartRate() throws Exception {
        RecordNumberDTO recordDTO = RecordNumberDTO.builder()
                .petId(testPet.getId())
                .count(80)
                .build();

        mockMvc.perform(post("/record/heart-rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recordDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("SUCCESS_SAVE_HEART_RATE"))
                .andExpect(jsonPath("$.data.petId").value(testPet.getId()))
                .andExpect(jsonPath("$.data.count").value(80));
    }

    @Test
    void testGetRRByDate() throws Exception {
        // First record an RR entry
        RecordNumberDTO recordDTO = RecordNumberDTO.builder()
                .petId(testPet.getId())
                .count(30)
                .build();

        mockMvc.perform(post("/record/RR")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recordDTO)))
                .andExpect(status().isCreated());

        // Then retrieve by date
        mockMvc.perform(get("/record/RR/date")
                        .param("date", LocalDate.now().toString())
                        .param("petId", testPet.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS_GET_RR_BY_DATE"))
                .andExpect(jsonPath("$.data.dateDTOS[0].count").value(30));
    }

    @Test
    void testGetHeartRateByDate() throws Exception {
        // First record a heart rate entry
        RecordNumberDTO recordDTO = RecordNumberDTO.builder()
                .petId(testPet.getId())
                .count(90)
                .build();

        mockMvc.perform(post("/record/heart-rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recordDTO)))
                .andExpect(status().isCreated());

        // Then retrieve by date
        mockMvc.perform(get("/record/heart-rate/date")
                        .param("date", LocalDate.now().toString())
                        .param("petId", testPet.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS_GET_HEART_RATE_BY_DATE"))
                .andExpect(jsonPath("$.data.dateDTOS[0].count").value(90));
    }

    @Test
    void testValidationErrors() throws Exception {
        // Test with missing count for RR
        RecordNumberDTO recordDTO = RecordNumberDTO.builder()
                .petId(testPet.getId())
                .build();

        mockMvc.perform(post("/record/RR")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recordDTO)))
                .andExpect(status().isBadRequest());

        // Test with missing count for heart rate
        mockMvc.perform(post("/record/heart-rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recordDTO)))
                .andExpect(status().isBadRequest());
    }
}