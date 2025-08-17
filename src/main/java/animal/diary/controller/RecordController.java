package animal.diary.controller;

import animal.diary.code.SuccessCode;
import animal.diary.code.VitalCategory;
import animal.diary.dto.*;
import animal.diary.dto.response.ErrorResponseDTO;
import animal.diary.dto.response.ResponseDTO;
import animal.diary.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Record Controller", description = "기록 관련 API")
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
                .status(SuccessCode.SUCCESS_SAVE_WEIGHT.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_WEIGHT, result));
    }

    @Operation(summary = "몸무게 날짜별 조회", description = "특정 날짜의 몸무게 기록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "몸무게 날짜별 조회 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ResponseDateListDTO.class))
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
    @GetMapping("/weight/date")
    public ResponseEntity<ResponseDTO<ResponseDateListDTO>> getWeightsByDate(@Valid @ModelAttribute RequestDateDTO dto) {
        log.info("Received request to get weights by date for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());
        ResponseDateListDTO result = recordService.getWeightsByDate(dto);
        log.info("Successfully retrieved weights for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_WEIGHT_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_WEIGHT_BY_DATE, result));
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
                .status(SuccessCode.SUCCESS_SAVE_ENERGY.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_ENERGY, result));
    }

    @Operation(summary = "기력 상태 날짜별 조회", description = "특정 날짜의 기력 상태 기록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "기력 상태 날짜별 조회 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ResponseDateListDTO.class))
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
    @GetMapping("/energy/date")
    public ResponseEntity<ResponseDTO<ResponseDateListDTO>> getEnergyByDate(@Valid @ModelAttribute RequestDateDTO dto) {
        log.info("Received request to get energy by date for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());
        ResponseDateListDTO result = recordService.getEnergyOrAppetiteByDate(dto, "energy");
        log.info("Successfully retrieved energy records for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_ENERGY_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_ENERGY_BY_DATE, result));
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
                .status(SuccessCode.SUCCESS_SAVE_APPETITE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_APPETITE, result));
    }

    @Operation(summary = "식욕 상태 날짜별 조회", description = "특정 날짜의 식욕 상태 기록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "식욕 상태 날짜별 조회 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ResponseDateListDTO.class))
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
    @GetMapping("/appetite/date")
    public ResponseEntity<ResponseDTO<ResponseDateListDTO>> getAppetiteByDate(@Valid @ModelAttribute RequestDateDTO dto) {
        log.info("Received request to get appetite by date for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());
        ResponseDateListDTO result = recordService.getEnergyOrAppetiteByDate(dto, "appetite");
        log.info("Successfully retrieved appetite records for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_APPETITE_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_APPETITE_BY_DATE, result));
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
                .status(SuccessCode.SUCCESS_SAVE_RR.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_RR, result));
    }

    @Operation(summary = "호흡 수 날짜별 조회", description = "특정 날짜의 호흡 수 기록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "호흡 수 날짜별 조회 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ResponseDateListDTO.class))
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
    @GetMapping("/RR/date")
    public ResponseEntity<ResponseDTO<ResponseDateListDTO>> getRRByDate(@Valid @ModelAttribute RequestDateDTO dto) {
        log.info("Received request to get respiratory rate by date for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());
        ResponseDateListDTO result = recordService.getRROrHeartRateByDate(dto, VitalCategory.RR);
        log.info("Successfully retrieved respiratory rate records for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_RR_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RR_BY_DATE, result));
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
                .status(SuccessCode.SUCCESS_SAVE_HEART_RATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_HEART_RATE, result));
    }

    @Operation(summary = "심박수 날짜별 조회", description = "특정 날짜의 심박수 기록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "심박수 날짜별 조회 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ResponseDateListDTO.class))
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
    @GetMapping("/heart-rate/date")
    public ResponseEntity<ResponseDTO<ResponseDateListDTO>> getHeartRateByDate(@Valid @ModelAttribute RequestDateDTO dto) {
        log.info("Received request to get heart rate by date for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());
        ResponseDateListDTO result = recordService.getRROrHeartRateByDate(dto, VitalCategory.HEART_RATE);
        log.info("Successfully retrieved heart rate records for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_HEART_RATE_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_HEART_RATE_BY_DATE, result));
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
                .status(SuccessCode.SUCCESS_SAVE_SYNCOPE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_SYNCOPE, result));
    }

    @Operation(summary = "기절 상태 날짜별 조회", description = "특정 날짜의 기절 상태 기록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "기절 상태 날짜별 조회 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ResponseDateListDTO.class))
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
    @GetMapping("/syncope/date")
    public ResponseEntity<ResponseDTO<ResponseDateListDTO>> getSyncopeByDate(@Valid @ModelAttribute RequestDateDTO dto) {
        log.info("Received request to get syncope by date for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());
        ResponseDateListDTO result = recordService.getSyncopeByDate(dto);
        log.info("Successfully retrieved syncope records for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_SYNCOPE_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_SYNCOPE_BY_DATE, result));
    }

    // 소변 상태
    @Operation(summary = "소변 상태 기록", description = """
            특정 반려동물의 소변 상태를 기록합니다.
            - 필수 필드: petId, urineState, urineAmount, binaryState
            
            - 소변 상태(urineState)는 BLOODY, LIGHT, DARK, NORMAL 중 하나로 설정할 수 있습니다.
            - 소변량(urineAmount)은 NONE, LOW, NORMAL, HIGH 중 하나로 설정할 수 있습니다.
            - 소변 냄새 상태(binaryState)는 O(있음), X(없음)으로 설정할 수 있습니다.
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
                .status(SuccessCode.SUCCESS_SAVE_URINE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_URINE, result));
    }

    @Operation(summary = "소변 상태 날짜별 조회", description = "특정 날짜의 소변 상태 기록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소변 상태 날짜별 조회 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ResponseDateListDTO.class))
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
    @GetMapping("/urine/date")
    public ResponseEntity<ResponseDTO<ResponseDateListDTO>> getUrineByDate(@Valid @ModelAttribute RequestDateDTO dto) {
        log.info("Received request to get urine by date for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());
        ResponseDateListDTO result = recordService.getUrinaryByDate(dto);
        log.info("Successfully retrieved urine records for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_URINE_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_URINE_BY_DATE, result));
    }

    // 소리 기록
    @Operation(summary = "소리 기록", description = "소리를 기록합니다.")
    @PostMapping(value = "/sound", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordSound(
            @RequestPart @Valid SoundRecordDTO dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        // TODO: Implement sound recording service method
        return null;
    }

    // 대변 상태 기록
    @Operation(summary = "대변 상태 기록", description = "대변 상태를 기록합니다.")
    @PostMapping(value = "/defecation", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordDefecation(
            @RequestPart @Valid DefecationRecordDTO dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        // TODO: Implement defecation recording service method
        return null;
    }

    // 피부 상태 기록
    @Operation(summary = "피부 상태 기록", description = "피부 상태를 기록합니다.")
    @PostMapping(value = "/skin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordSkin(
            @RequestPart @Valid SkinRecordDTO dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        // TODO: Implement skin recording service method
        return null;
    }

    // 콧물 상태 기록
    @Operation(summary = "콧물 상태 기록", description = "콧물 상태를 기록합니다.")
    @PostMapping(value = "/snot", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordSnot(
            @RequestPart @Valid SnotRecordDTO dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        // TODO: Implement snot recording service method
        return null;
    }

    // 구토 상태 기록
    @Operation(summary = "구토 상태 기록", description = "구토 상태를 기록합니다.")
    @PostMapping(value = "/vomiting", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordVomiting(
            @RequestPart @Valid VomitingRecordDTO dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        // TODO: Implement vomiting recording service method
        return null;
    }

    // 걸음 상태 기록
    @Operation(summary = "걸음 상태 기록", description = "걸음 상태를 기록합니다.")
    @PostMapping(value = "/walking", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordWalking(
            @RequestPart @Valid WalkingRecordDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        // TODO: Implement walking recording service method
        return null;
    }

    // 경련 상태 기록
    @Operation(summary = "경련 상태 기록", description = "경련 상태를 기록합니다.")
    @PostMapping(value = "/convulsion", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordConvulsion(
            @RequestPart @Valid ConvulsionRecordDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        // TODO: Implement convulsion recording service method
        return null;
    }

    // 특이사항 기록
    @Operation(summary = "특이사항 기록", description = "특이사항을 기록합니다.")
    @PostMapping(value = "/significant", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordSignificant(
            @RequestPart @Valid SignificantRecordDTO dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        // TODO: Implement significant recording service method
        return null;
    }

}
