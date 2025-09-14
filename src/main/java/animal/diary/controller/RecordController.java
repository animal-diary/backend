package animal.diary.controller;

import animal.diary.code.ErrorCode;
import animal.diary.code.SuccessCode;
import animal.diary.code.VitalCategory;
import animal.diary.dto.*;
import animal.diary.dto.api.RecordResponseApi;
import animal.diary.dto.record.*;
import animal.diary.dto.response.ErrorResponseDTO;
import animal.diary.dto.response.ResponseDTO;
import animal.diary.exception.ImageSizeLimitException;
import animal.diary.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/record")
@RequiredArgsConstructor
@Tag(name = "Record Controller", description = "기록 생성 관련 API")
public class RecordController {
    private final RecordService recordService;
    // (2.3, 2.4, 2.5, 2.6, 2.7,2.8,2.9,2.10,2.11,2.13,2.14,2.15,2.16)
    // ====================================================== 몸무게 기록
    @Operation(summary = "몸무게 기록", description = """
            몸무게를 기록합니다.
            - 필수 필드: petId, weight
            - weight: 몸무게 (kg) 5.0
            - 몸무게는 반려동물의 체중을 나타냅니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "몸무게 기록 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RecordResponseApi.WeightResponseApi.class))
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
    public ResponseEntity<ResponseDTO<RecordResponseDTO.WeightResponseDTO>> recordWeight(@Validated(WeightGroup.class) @RequestBody RecordWithOutImageDTO.WeightRecordDTO dto) {
        log.info("Received weight record request for pet ID: {}", dto.getPetId());
        RecordResponseDTO.WeightResponseDTO result = recordService.recordWeight(dto);
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
            @ApiResponse(responseCode = "201", description = "기력 상태 기록 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RecordResponseApi.EnergyAndAppetiteResponseApi.class))
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
    public ResponseEntity<ResponseDTO<RecordResponseDTO.EnergyAndAppetiteResponseDTO>> recordEnergy(@Validated(StateGroup.class)@RequestBody RecordWithOutImageDTO.EnergyAndAppetiteRecord dto) {
        log.info("Received energy record request for pet ID: {}", dto.getPetId());
        RecordResponseDTO.EnergyAndAppetiteResponseDTO result = recordService.recordEnergyAndAppetite(dto, "energy");
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
            @ApiResponse(responseCode = "201", description = "식욕 상태 기록 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RecordResponseApi.EnergyAndAppetiteResponseApi.class))
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
    public ResponseEntity<ResponseDTO<RecordResponseDTO.EnergyAndAppetiteResponseDTO>> recordAppetite(@Validated(StateGroup.class) @RequestBody RecordWithOutImageDTO.EnergyAndAppetiteRecord dto) {
        log.info("Received appetite record request for pet ID: {}", dto.getPetId());
        RecordResponseDTO.EnergyAndAppetiteResponseDTO result = recordService.recordEnergyAndAppetite(dto, "appetite");
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
            @ApiResponse(responseCode = "201", description = "호흡 수 기록 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RecordResponseDTO.RRAndHeartRateResponseDTO.class))
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
    public ResponseEntity<ResponseDTO<RecordResponseDTO.RRAndHeartRateResponseDTO>> recordRR(@Validated(CountGroup.class) @RequestBody RecordWithOutImageDTO.RespiratoryRateAndHeartRateRecord dto) {
        log.info("Received respiratory rate record request for pet ID: {}", dto.getPetId());
        RecordResponseDTO.RRAndHeartRateResponseDTO result = recordService.recordRRAndHeartRate(dto, VitalCategory.RR);
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
            @ApiResponse(responseCode = "201", description = "심박수 기록 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RecordResponseDTO.RRAndHeartRateResponseDTO.class))
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
    public ResponseEntity<ResponseDTO<RecordResponseDTO.RRAndHeartRateResponseDTO>> recordHeartRate(@Validated(CountGroup.class) @RequestBody RecordWithOutImageDTO.RespiratoryRateAndHeartRateRecord dto) {
        log.info("Received heart rate record request for pet ID: {}", dto.getPetId());
        RecordResponseDTO.RRAndHeartRateResponseDTO result = recordService.recordRRAndHeartRate(dto, VitalCategory.HEART_RATE);
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
            @ApiResponse(responseCode = "201", description = "기절 상태 기록 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RecordResponseDTO.SyncopeResponseDTO.class))
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
    public ResponseEntity<ResponseDTO<RecordResponseDTO.SyncopeResponseDTO>> recordSyncope(@Validated(BinaryStateGroup.class) @RequestBody RecordWithOutImageDTO.SyncopeRecord dto) {
        log.info("Received syncope record request for pet ID: {}", dto.getPetId());
        RecordResponseDTO.SyncopeResponseDTO result = recordService.recordSyncope(dto);
        log.info("Syncope record completed for pet ID: {}", dto.getPetId());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_RECORD.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_RECORD, result));
    }

    // 소변 상태
    @Operation(summary = "소변 상태 기록", description = """
            특정 반려동물의 소변 상태를 기록합니다.
            - 필드: petId, urineAmount(소변량)(NONE, LOW, NORMAL, HIGH), urineState(소변 상태)(BLOODY, LIGHT, DARK, NORMAL, ETC), binaryState(소변 악취 상태)(O, X), memo(메모), images(사진)
            
            - 소변량(urineAmount)이 NONE(무뇨)가 아닌 경우:
              - 소변 상태(urineState): 필수 - BLOODY, LIGHT, DARK, NORMAL, ETC 중 하나
              - 소변 악취 상태(binaryState): 필수 - O(있음), X(없음) 중 하나
              - 메모(memo): 선택사항 - 200자 이내
              - 사진: 선택사항 - 최대 10장
            
            - 소변량(urineAmount)이 NONE(무뇨)인 경우:
              - 소변 상태, 소변 악취 상태, 메모, 사진 업로드 모두 입력 불가
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "소변 상태 기록 성공", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecordResponseApi.UrinaryRecordResponseApi.class))

            }),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (드롭다운 확인하세요)", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class),
                            examples = {
                            @ExampleObject(name = "소변량이 NONE인 경우 사진 업로드 불가"),
                            @ExampleObject(name = "소변량이 NONE인 경우 소변 상태 입력 불가"),
                            @ExampleObject(name = "소변량이 NONE인 경우 소변 악취 상태 입력 불가"),
                            @ExampleObject(name = "소변량이 NONE인 경우 메모 입력 불가"),
                            @ExampleObject(name = "사진 개수 제한 초과 (최대 10장)")
                    })
            }),
            @ApiResponse(responseCode = "404", description = "반려동물 정보 없음", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class))
            })
    })
    @PostMapping(value ="/urine", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<RecordResponseDTO.UrinaryResponseDTO>> recordUrine(@Validated(UrineGroup.class) @RequestPart UrinaryRecordDTO dto,
                                                                                         @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        log.info("Received urine record request for pet ID: {}", dto.getPetId());
        
        // 사진 업로드 검증
        if (images != null && !images.isEmpty()) {
            // 사진 개수 제한 체크 (최대 10장)
            if (images.size() > 10) {
                throw new ImageSizeLimitException(ErrorCode.IMAGE_SIZE_LIMIT_10);
            }
            
            // 소변량이 NONE인 경우 사진 업로드 불가
            if (dto.getUrineAmount() != null && "NONE".equalsIgnoreCase(dto.getUrineAmount().trim())) {
                throw new IllegalArgumentException("무뇨일 때는 사진을 업로드할 수 없습니다.");
            }
        }
        
        RecordResponseDTO.UrinaryResponseDTO result = recordService.recordUrinary(dto, images);
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "특이사항 기록 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RecordResponseDTO.SignificantResponseDTO.class))
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
    @PostMapping(value = "/significant", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<RecordResponseDTO.SignificantResponseDTO>> recordSignificant(
            @RequestPart SignificantRecordDTO dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @RequestPart(value = "video", required = false) MultipartFile video) {
        
        if (images == null) {
            images = List.of(); // 빈 리스트로 초기화
        }
        
        log.info("Received significant record request for pet ID: {} with {} images", dto.getPetId(), images.size());
        RecordResponseDTO.SignificantResponseDTO result = recordService.recordSignificantRecord(dto, images, video);
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "경련 상태 기록 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RecordResponseDTO.ConvulsionResponseDTO.class))
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
    @PostMapping(value = "/convulsion", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<RecordResponseDTO.ConvulsionResponseDTO>> recordConvulsion(
            @RequestPart ConvulsionRecordDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        
        log.info("Received convulsion record request for pet ID: {} with image: {}", dto.getPetId(), image != null);
        RecordResponseDTO.ConvulsionResponseDTO result = recordService.recordConvulsionRecord(dto, image);
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "이상 소리 기록 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RecordResponseDTO.SoundResponseDTO.class))
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
    @PostMapping(value = "/abnormal-sound", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<RecordResponseDTO.SoundResponseDTO>> recordAbnormalSound(
            @RequestPart AbnormalSoundRecordDTO dto,
            @RequestPart(value = "video", required = true) MultipartFile video) {

        // 영상 필수

        RecordResponseDTO.SoundResponseDTO result = recordService.recordSound(dto, video);
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
            - 이미지는 최대 10장까지 업로드 가능합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "콧물 상태 기록 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RecordResponseDTO.SnotResponseDTO.class))
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
    @PostMapping(value = "/snot", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<RecordResponseDTO.SnotResponseDTO>> recordSnot(
            @RequestPart SnotRecordDTO dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {

        log.info("Received snot record request for pet ID: {} with state: {}", dto.getPetId(), dto.getState());
        RecordResponseDTO.SnotResponseDTO result = recordService.recordSnot(dto, images);
        log.info("snot record completed for pet ID: {}", dto.getPetId());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_RECORD.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_RECORD, result));
    }

    // ============================================================ 구토 상태 기록
    @Operation(summary = "구토 상태 기록", description = """
            구토 상태를 기록합니다.
            - 필수 필드: petId, state
            - state: 구토 상태 (O: 있음, X: 없음)
            - 이미지는 최대 10장까지 업로드 가능합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "구토 상태 기록 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RecordResponseDTO.VomitingResponseDTO.class))
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
    @PostMapping(value = "/vomiting", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<RecordResponseDTO.VomitingResponseDTO>> recordVomiting(
            @RequestPart VomitingRecordDTO dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {

        log.info("Received vomiting record request for pet ID: {} with state: {}", dto.getPetId(), dto.getState());
        RecordResponseDTO.VomitingResponseDTO result = recordService.recordVomiting(dto, images);
        log.info("Vomiting record completed for pet ID: {}", dto.getPetId());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_RECORD.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_RECORD, result));
    }

    // ============================================================ 걷는 모습 기록
    @Operation(summary = "걷는 모습 기록", description = """
            걷는 모습을 기록합니다.
            - 필수 필드: petId
            - video: 걷는 모습 영상 (필수, 최대 50MB)
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "걷는 모습 기록 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RecordResponseDTO.WalkingResponseDTO.class))
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
    @PostMapping(value = "/walking", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<RecordResponseDTO.WalkingResponseDTO>> recordWalkingAppearance(
            @RequestPart WalkingRecordDTO dto,
            @RequestPart(value = "video", required = true) MultipartFile video) {

        log.info("Received walking appearance record request for pet ID: {}", dto.getPetId());
        RecordResponseDTO.WalkingResponseDTO result = recordService.recordWalking(dto, video);
        log.info("Walking appearance record completed for pet ID: {}", dto.getPetId());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_RECORD.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_RECORD, result));
    }
}