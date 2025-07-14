package animal.diary.service;

import animal.diary.code.VitalCategory;
import animal.diary.dto.RecordNumberDTO;
import animal.diary.dto.RequestDateDTO;
import animal.diary.dto.RecordResponseDTO;
import animal.diary.dto.ResponseDateListDTO;
import animal.diary.entity.pet.Pet;
import animal.diary.entity.pet.Type;
import animal.diary.entity.pet.Gender;
import animal.diary.entity.pet.Health;
import animal.diary.entity.pet.Neutered;
import animal.diary.entity.User;
import animal.diary.exception.EmptyListException;
import animal.diary.repository.PetRepository;
import animal.diary.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class VitalSignRecordServiceTest {

    @Autowired
    private RecordService recordService;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    private Pet testPet;

    @BeforeEach
    void setUp() {
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
    void testRecordRRWithEnum() {
        // Given
        RecordNumberDTO recordDTO = RecordNumberDTO.builder()
                .petId(testPet.getId())
                .count(25)
                .build();

        // When
        RecordResponseDTO result = recordService.recordRRAndHeartRate(recordDTO, VitalCategory.RR);

        // Then
        assertNotNull(result);
        assertEquals(testPet.getId(), result.getPetId());
        assertEquals(25, result.getCount());
    }

    @Test
    void testRecordHeartRateWithEnum() {
        // Given
        RecordNumberDTO recordDTO = RecordNumberDTO.builder()
                .petId(testPet.getId())
                .count(80)
                .build();

        // When
        RecordResponseDTO result = recordService.recordRRAndHeartRate(recordDTO, VitalCategory.HEART_RATE);

        // Then
        assertNotNull(result);
        assertEquals(testPet.getId(), result.getPetId());
        assertEquals(80, result.getCount());
    }

    @Test
    void testGetRRByDateWithEnum() {
        // Given - Record RR first
        RecordNumberDTO recordDTO = RecordNumberDTO.builder()
                .petId(testPet.getId())
                .count(30)
                .build();
        recordService.recordRRAndHeartRate(recordDTO, VitalCategory.RR);

        RequestDateDTO dateDTO = new RequestDateDTO();
        dateDTO.setPetId(testPet.getId());
        dateDTO.setDate(LocalDate.now());

        // When
        ResponseDateListDTO result = recordService.getRROrHeartRateByDate(dateDTO, VitalCategory.RR);

        // Then
        assertNotNull(result);
        assertEquals(LocalDate.now(), result.getDate());
        assertFalse(result.getDateDTOS().isEmpty());
        assertEquals(30, result.getDateDTOS().get(0).getCount());
    }

    @Test
    void testGetHeartRateByDateWithEnum() {
        // Given - Record heart rate first
        RecordNumberDTO recordDTO = RecordNumberDTO.builder()
                .petId(testPet.getId())
                .count(90)
                .build();
        recordService.recordRRAndHeartRate(recordDTO, VitalCategory.HEART_RATE);

        RequestDateDTO dateDTO = new RequestDateDTO();
        dateDTO.setPetId(testPet.getId());
        dateDTO.setDate(LocalDate.now());

        // When
        ResponseDateListDTO result = recordService.getRROrHeartRateByDate(dateDTO, VitalCategory.HEART_RATE);

        // Then
        assertNotNull(result);
        assertEquals(LocalDate.now(), result.getDate());
        assertFalse(result.getDateDTOS().isEmpty());
        assertEquals(90, result.getDateDTOS().get(0).getCount());
    }

    @Test
    void testEmptyListExceptionForRR() {
        // Given - No records for today
        RequestDateDTO dateDTO = new RequestDateDTO();
        dateDTO.setPetId(testPet.getId());
        dateDTO.setDate(LocalDate.now());

        // When & Then
        assertThrows(EmptyListException.class, () -> {
            recordService.getRROrHeartRateByDate(dateDTO, VitalCategory.RR);
        });
    }

    @Test
    void testEmptyListExceptionForHeartRate() {
        // Given - No records for today
        RequestDateDTO dateDTO = new RequestDateDTO();
        dateDTO.setPetId(testPet.getId());
        dateDTO.setDate(LocalDate.now());

        // When & Then
        assertThrows(EmptyListException.class, () -> {
            recordService.getRROrHeartRateByDate(dateDTO, VitalCategory.HEART_RATE);
        });
    }
}