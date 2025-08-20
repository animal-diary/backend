package animal.diary.controller;

import animal.diary.code.SuccessCode;
import animal.diary.code.VitalCategory;
import animal.diary.dto.*;
import animal.diary.dto.record.ConvulsionRecordDTO;
import animal.diary.dto.record.RecordNumberDTO;
import animal.diary.dto.record.SignificantRecordDTO;
import animal.diary.dto.record.SnotRecordDTO;
import animal.diary.dto.response.ErrorResponseDTO;
import animal.diary.dto.response.ResponseDTO;
import animal.diary.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/record")
@RequiredArgsConstructor
@Tag(name = "Record Controller", description = "기록 생성 관련 API")
public class RecordController {
    private final RecordService recordService;

    // ====================================================== 몸무게 기록
    @Operation(summary = "몸무게 기록", description = """
            몸무게를 기록합니다.
            - 필수 필드: petId, weight
            - weight: 몸무게 (kg) 5.0
            - 몸무게는 반려동물의 체중을 나타냅니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "몸무게 기록 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RecordResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "반려동물 정보 없음", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDTO.class))
            })
    })
    @PostMapping(value = "/weight")
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordWeight(@Validated(WeightGroup.class) @RequestBody RecordNumberDTO dto) {
        log.info("Received weight record request for pet ID: {}", dto.getPetId());
        RecordResponseDTO result = recordService.recordWeight(dto);
        log.info("Weight record completed for pet ID: {}", dto.getPetId());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_RECORD.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_RECORD, result));
    }

    // ====================================================== 기력 상태
    @Operation(summary = "기력 상태 기록", description = """
            기력 상태를 기록합니다.
            - 필수 필드: petId, state
            - state 가능한 값: LOW, NORMAL, HIGH
            - 기력 상태는 반려동물의 에너지 수준을 나타냅니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "기력 상태 기록 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RecordResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "반려동물 정보 없음", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDTO.class))
            })
    })
    @PostMapping("/energy")
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordEnergy(@Validated(StateGroup.class)@RequestBody RecordNumberDTO dto) {
        log.info("Received energy record request for pet ID: {}", dto.getPetId());
        RecordResponseDTO result = recordService.recordEnergyAndAppetite(dto, "energy");
        log.info("Energy record completed for pet ID: {}", dto.getPetId());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_RECORD.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_RECORD, result));
    }

    // ================================================ 식욕 상태
    @Operation(summary = "식욕 상태 기록", description = """
            식욕 상태를 기록합니다.
            - 필수 필드: petId, state
            - state 가능한 값: LOW, NORMAL, HIGH
            - 식욕 상태는 반려동물의 식욕 수준을 나타냅니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "식욕 상태 기록 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RecordResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "반려동물 정보 없음", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDTO.class))
            })
    })
    @PostMapping("/appetite")
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordAppetite(@Validated(StateGroup.class) @RequestBody RecordNumberDTO dto) {
        log.info("Received appetite record request for pet ID: {}", dto.getPetId());
        RecordResponseDTO result = recordService.recordEnergyAndAppetite(dto, "appetite");
        log.info("Appetite record completed for pet ID: {}", dto.getPetId());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_RECORD.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_RECORD, result));
    }

    // ========================================== 호흡 수
    @Operation(summary = "호흡 수 기록", description = """
            호흡 수를 기록합니다.
            - 필수 필드: petId, count
            - count: 호흡 수 (회/분)
            - 호흡 수는 반려동물의 호흡 횟수를 나타냅니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "호흡 수 기록 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RecordResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "반려동물 정보 없음", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDTO.class))
            })
    })
    @PostMapping("/RR")
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordRR(@Validated(CountGroup.class) @RequestBody RecordNumberDTO dto) {
        log.info("Received respiratory rate record request for pet ID: {}", dto.getPetId());
        RecordResponseDTO result = recordService.recordRRAndHeartRate(dto, VitalCategory.RR);
        log.info("Respiratory rate record completed for pet ID: {}", dto.getPetId());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_RECORD.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_RECORD, result));
    }

    // ===========================================  심박수
    @Operation(summary = "심박수 기록", description = """
            심박수를 기록합니다.
            - 필수 필드: petId, count
            - count: 심박수 (회/분)
            - 심박수는 반려동물의 심장 박동 수를 나타냅니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "심박수 기록 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RecordResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "반려동물 정보 없음", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDTO.class))
            })
    })
    @PostMapping("/heart-rate")
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordHeartRate(@Validated(CountGroup.class) @RequestBody RecordNumberDTO dto) {
        log.info("Received heart rate record request for pet ID: {}", dto.getPetId());
        RecordResponseDTO result = recordService.recordRRAndHeartRate(dto, VitalCategory.HEART_RATE);
        log.info("Heart rate record completed for pet ID: {}", dto.getPetId());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_RECORD.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_RECORD, result));
    }

    // ============================================= 기절 상태
    @Operation(summary = "기절 상태 기록", description = """
            기절 상태를 기록합니다.
            - 필수 필드: petId, binaryState
            - 기절 상태(binaryState)는 O(있음), X(없음)으로 설정할 수 있습니다.
            - 기절 상태는 반려동물이 기절했는지 여부를 나타냅니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "기절 상태 기록 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RecordResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "반려동물 정보 없음", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDTO.class))
            })
    })
    @PostMapping("/syncope")
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordSyncope(@Validated(BinaryStateGroup.class) @RequestBody RecordNumberDTO dto) {
        log.info("Received syncope record request for pet ID: {}", dto.getPetId());
        RecordResponseDTO result = recordService.recordSyncope(dto);
        log.info("Syncope record completed for pet ID: {}", dto.getPetId());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_RECORD.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_RECORD, result));
    }

    // 소변 상태
    @Operation(summary = "소변 상태 기록", description = """
            특정 반려동물의 소변 상태를 기록합니다.
            - 필수 필드: petId, urineState, urineAmount
            
            - 소변 상태(urineState)는 BLOODY, LIGHT, DARK, NORMAL 중 하나로 설정할 수 있습니다.
            - 소변량(urineAmount)은 NONE, LOW, NORMAL, HIGH 중 하나로 설정할 수 있습니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소변 상태 기록 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RecordResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "반려동물 정보 없음", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDTO.class))
            })
    })
    @PostMapping("/urine")
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordUrine(@Validated(UrineGroup.class) @RequestBody RecordNumberDTO dto) {
        log.info("Received urine record request for pet ID: {}", dto.getPetId());
        RecordResponseDTO result = recordService.recordUrinary(dto);
        log.info("Urine record completed for pet ID: {}", dto.getPetId());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_RECORD.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_RECORD, result));
    }

    // ============================================================= 특이사항 기록
    @Operation(summary = "특이사항 기록", description = """
            특이사항을 기록합니다.
            - 필수 필드: petId, title, content
            - 이미지는 최대 10장까지 업로드 가능합니다.
            """)
    @PostMapping(value = "/significant", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordSignificant(
            @RequestPart SignificantRecordDTO dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        
        if (images == null) {
            images = List.of(); // 빈 리스트로 초기화
        }
        
        log.info("Received significant record request for pet ID: {} with {} images", dto.getPetId(), images.size());
        RecordResponseDTO result = recordService.recordSignificantRecord(dto, images);
        log.info("Significant record completed for pet ID: {}", dto.getPetId());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_RECORD.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_RECORD, result));
    }

    // =========================================================== 경련 기록
    @Operation(summary = "경련 상태 기록", description = """
            경련 상태를 기록합니다.
            - 필수 필드: petId, state, abnormalState
            - state: 경련 상태 (O: 있음, X: 없음)
            - abnormalState: 비정상 상태 (INCONTINENCE, DROOLING, UNCONSCIOUS, NORMAL)
            - 이미지는 단일 이미지만 업로드 가능합니다.
            """)
    @PostMapping(value = "/convulsion", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordConvulsion(
            @RequestPart ConvulsionRecordDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        
        log.info("Received convulsion record request for pet ID: {} with image: {}", dto.getPetId(), image != null);
        RecordResponseDTO result = recordService.recordConvulsionRecord(dto, image);
        log.info("Convulsion record completed for pet ID: {}", dto.getPetId());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_RECORD.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_RECORD, result));
    }

    // =========================================================== 이상 소리 기록
    @Operation(summary = "이상 소리 기록", description = """
            이상 소리를 기록합니다.
            - 필수 필드: petId
            - 이미지는 단일 이미지만 업로드 가능합니다.
            """)
    @PostMapping(value = "/abnormal-sound", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordAbnormalSound(
            @RequestPart AbnormalSoundRecordDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        log.info("Received abnormal sound record request for pet ID: {} with image: {}", dto.getPetId(), image != null);
        RecordResponseDTO result = recordService.recordSound(dto, image);
        log.info("Abnormal sound record completed for pet ID: {}", dto.getPetId());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_RECORD.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_RECORD, result));
    }

    // ============================================================ 콧물 상태 기록
    @Operation(summary = "콧물 상태 기록", description = """
            콧물 상태를 기록합니다.
            - 필수 필드: petId, state
            - state: 콧물 상태 (CLEAR, MUCUS, BLOODY)
            - 이미지는 단일 이미지만 업로드 가능합니다.
            """)
    @PostMapping(value = "/snot", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordSnot(
            @RequestPart SnotRecordDTO dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {

        log.info("Received snot record request for pet ID: {} with state: {}", dto.getPetId(), dto.getState());
        RecordResponseDTO result = recordService.recordSnot(dto, images);
        log.info("snot record completed for pet ID: {}", dto.getPetId());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_RECORD.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_RECORD, result));
    }
}