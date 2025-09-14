package animal.diary.controller;

import animal.diary.code.SuccessCode;
import animal.diary.code.VitalCategory;
import animal.diary.dto.RequestDateDTO;
import animal.diary.dto.ResponseDateDTO;
import animal.diary.dto.ResponseDateListDTO;
import animal.diary.dto.api.ResponseDateApi;
import animal.diary.dto.response.ErrorResponseDTO;
import animal.diary.dto.response.ResponseDTO;
import animal.diary.service.QueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/query")
@RequiredArgsConstructor
@Tag(name = "Query Controller", description = "기록 조회 관련 API")
public class QueryController {
    
    private final QueryService queryService;

    // ====================================================== 몸무게 조회
    @Operation(summary = "몸무게 날짜별 조회", description = """
            특정 날짜의 몸무게 기록을 조회합니다.
            - 필수 필드: petId, date
            - date: 조회할 날짜 (yyyy-MM-dd)
            - 해당 날짜의 모든 몸무게 기록을 시간순으로 반환합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "몸무게 날짜별 조회 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ResponseDateApi.WeightDateResponseApi.class))
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
    public ResponseEntity<ResponseDTO<ResponseDateListDTO<ResponseDateDTO.WeightResponse>>> getWeightsByDate(@Valid @ModelAttribute RequestDateDTO dto) {
        log.info("Received request to get weights by date for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());
        ResponseDateListDTO<ResponseDateDTO.WeightResponse> result = queryService.getWeightsByDate(dto);
        log.info("Successfully retrieved weights for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_RECORD_LIST.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, result));
    }

    // ====================================================== 기력 상태 조회
    @Operation(summary = "기력 상태 날짜별 조회", description = """
            특정 날짜의 기력 상태 기록을 조회합니다.
            - 필수 필드: petId, date
            - date: 조회할 날짜 (yyyy-MM-dd)
            - 해당 날짜의 모든 기력 상태 기록을 시간순으로 반환합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "기력 상태 날짜별 조회 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ResponseDateApi.EnergyAndAppetiteAndSyncopeDateResponseApi.class))
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
    public ResponseEntity<ResponseDTO<ResponseDateListDTO<ResponseDateDTO.StateResponse>>> getEnergyByDate(@Valid @ModelAttribute RequestDateDTO dto) {
        log.info("Received request to get energy by date for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());
        ResponseDateListDTO<ResponseDateDTO.StateResponse> result = queryService.getEnergyOrAppetiteByDate(dto, "energy");
        log.info("Successfully retrieved energy records for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_RECORD_LIST.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, result));
    }

    // ================================================ 식욕 상태 조회
    @Operation(summary = "식욕 상태 날짜별 조회", description = """
            특정 날짜의 식욕 상태 기록을 조회합니다.
            - 필수 필드: petId, date
            - date: 조회할 날짜 (yyyy-MM-dd)
            - 해당 날짜의 모든 식욕 상태 기록을 시간순으로 반환합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "식욕 상태 날짜별 조회 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ResponseDateApi.EnergyAndAppetiteAndSyncopeDateResponseApi.class))
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
    public ResponseEntity<ResponseDTO<ResponseDateListDTO<ResponseDateDTO.StateResponse>>> getAppetiteByDate(@Valid @ModelAttribute RequestDateDTO dto) {
        log.info("Received request to get appetite by date for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());
        ResponseDateListDTO<ResponseDateDTO.StateResponse> result = queryService.getEnergyOrAppetiteByDate(dto, "appetite");
        log.info("Successfully retrieved appetite records for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_RECORD_LIST.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, result));
    }

    // ========================================== 호흡 수 조회
    @Operation(summary = "호흡 수 날짜별 조회", description = """
            특정 날짜의 호흡 수 기록을 조회합니다.
            - 필수 필드: petId, date
            - date: 조회할 날짜 (yyyy-MM-dd)
            - 해당 날짜의 모든 호흡 수 기록을 시간순으로 반환합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "호흡 수 날짜별 조회 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ResponseDateApi.RRAndHeartRateDateResponseApi.class))
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
    public ResponseEntity<ResponseDTO<ResponseDateListDTO<ResponseDateDTO.CountResponse>>> getRRByDate(@Valid @ModelAttribute RequestDateDTO dto) {
        log.info("Received request to get respiratory rate by date for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());
        ResponseDateListDTO<ResponseDateDTO.CountResponse> result = queryService.getRROrHeartRateByDate(dto, VitalCategory.RR);
        log.info("Successfully retrieved respiratory rate records for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_RECORD_LIST.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, result));
    }

    // ===========================================  심박수 조회
    @Operation(summary = "심박수 날짜별 조회", description = """
            특정 날짜의 심박수 기록을 조회합니다.
            - 필수 필드: petId, date
            - date: 조회할 날짜 (yyyy-MM-dd)
            - 해당 날짜의 모든 심박수 기록을 시간순으로 반환합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "심박수 날짜별 조회 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ResponseDateApi.RRAndHeartRateDateResponseApi.class))
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
    public ResponseEntity<ResponseDTO<ResponseDateListDTO<ResponseDateDTO.CountResponse>>> getHeartRateByDate(@Valid @ModelAttribute RequestDateDTO dto) {
        log.info("Received request to get heart rate by date for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());
        ResponseDateListDTO<ResponseDateDTO.CountResponse> result = queryService.getRROrHeartRateByDate(dto, VitalCategory.HEART_RATE);
        log.info("Successfully retrieved heart rate records for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_RECORD_LIST.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, result));
    }

    // ============================================= 기절 상태 조회
    @Operation(summary = "기절 상태 날짜별 조회", description = """
            특정 날짜의 기절 상태 기록을 조회합니다.
            - 필수 필드: petId, date
            - date: 조회할 날짜 (yyyy-MM-dd)
            - 해당 날짜의 모든 기절 상태 기록을 시간순으로 반환합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "기절 상태 날짜별 조회 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ResponseDateApi.EnergyAndAppetiteAndSyncopeDateResponseApi.class))
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
    public ResponseEntity<ResponseDTO<ResponseDateListDTO<ResponseDateDTO.StateResponse>>> getSyncopeByDate(@Valid @ModelAttribute RequestDateDTO dto) {
        log.info("Received request to get syncope by date for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());
        ResponseDateListDTO<ResponseDateDTO.StateResponse> result = queryService.getSyncopeByDate(dto);
        log.info("Successfully retrieved syncope records for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_RECORD_LIST.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, result));
    }

    // ==========================================================  소변 상태 조회
    @Operation(summary = "소변 상태 날짜별 조회", description = """
            특정 날짜의 소변 상태 기록을 조회합니다.
            - 필수 필드: petId, date
            - date: 조회할 날짜 (yyyy-MM-DD)
            - 해당 날짜의 모든 소변 상태 기록을 시간순으로 반환합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소변 상태 날짜별 조회 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ResponseDateApi.UrinaryDateResponseApi.class))
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
    public ResponseEntity<ResponseDTO<ResponseDateListDTO<ResponseDateDTO.UrinaryResponse>>> getUrineByDate(@Valid @ModelAttribute RequestDateDTO dto) {
        log.info("Received request to get urine by date for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());
        ResponseDateListDTO<ResponseDateDTO.UrinaryResponse> result = queryService.getUrinaryByDate(dto);
        log.info("Successfully retrieved urine records for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_RECORD_LIST.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, result));
    }

    // ==================================== 특이 사항 일별 조회
    @Operation(summary = "특이 사항 날짜별 조회", description = """
            특정 날짜의 특이 사항 기록을 조회합니다.
            - 필수 필드: petId, date
            - date: 조회할 날짜 (yyyy-MM-dd)
            - 해당 날짜의 모든 특이 사항 기록을 시간순으로 반환합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "특이 사항 날짜별 조회 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ResponseDateApi.SignificantDateResponseApi.class))
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
    @GetMapping("/significant/date")
    public ResponseEntity<ResponseDTO<ResponseDateListDTO<ResponseDateDTO.SignificantResponse>>> getSignificantByDate(@Valid @ModelAttribute RequestDateDTO dto) {
        log.info("Received request to get significant records by date for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());
        ResponseDateListDTO<ResponseDateDTO.SignificantResponse> result = queryService.getSignificantByDate(dto);
        log.info("Successfully retrieved significant records for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_RECORD_LIST.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, result));
    }

    // ==================================== 경련 상태 일별 조회
    @Operation(summary = "경련 상태 날짜별 조회", description = """
            특정 날짜의 경련 상태 기록을 조회합니다.
            - 필수 필드: petId, date
            - date: 조회할 날짜 (yyyy-MM-dd)
            - 해당 날짜의 모든 경련 상태 기록을 시간순으로 반환합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "경련 상태 날짜별 조회 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ResponseDateApi.ConvulsionDateResponseApi.class))
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
    @GetMapping("/convulsion/date")
    public ResponseEntity<ResponseDTO<ResponseDateListDTO<ResponseDateDTO.ConvulsionResponse>>> getConvulsionByDate(@Valid @ModelAttribute RequestDateDTO dto) {
        log.info("Received request to get convulsion records by date for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());
        ResponseDateListDTO<ResponseDateDTO.ConvulsionResponse> result = queryService.getConvulsionByDate(dto);
        log.info("Successfully retrieved convulsion records for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_RECORD_LIST.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, result));
    }

    // ==================================== 이상한 소리 일별 조회
    @Operation(summary = "이상한 소리 날짜별 조회", description = """
            특정 날짜의 이상한 소리 기록을 조회합니다.
            - 필수 필드: petId, date
            - date: 조회할 날짜 (yyyy-MM-dd)
            - 해당 날짜의 모든 이상한 소리 기록을 시간순으로 반환합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이상한 소리 날짜별 조회 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ResponseDateApi.SoundDateResponseApi.class))
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
    @GetMapping("/sound/date")
    public ResponseEntity<ResponseDTO<ResponseDateListDTO<ResponseDateDTO.SoundResponse>>> getSoundByDate(@Valid @ModelAttribute RequestDateDTO dto) {
        log.info("Received request to get sound records by date for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());
        ResponseDateListDTO<ResponseDateDTO.SoundResponse> result = queryService.getSoundByDate(dto);
        log.info("Successfully retrieved sound records for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_RECORD_LIST.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, result));
    }


    // ==================================== 콧물 일별 조회
    @Operation(summary = "콧물 날짜별 조회", description = """
            특정 날짜의 콧물 기록을 조회합니다.
            - 필수 필드: petId, date
            - date: 조회할 날짜 (yyyy-MM-dd)
            - 해당 날짜의 모든 콧물 기록을 시간순으로 반환합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "콧물 날짜별 조회 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ResponseDateApi.SnotDateResponseApi.class))
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
    @GetMapping("/snot/date")
    public ResponseEntity<ResponseDTO<ResponseDateListDTO<ResponseDateDTO.SnotResponse>>> getSnotByDate(@Valid @ModelAttribute RequestDateDTO dto) {
        log.info("Received request to get snot records by date for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());
        ResponseDateListDTO<ResponseDateDTO.SnotResponse> result = queryService.getSnotByDate(dto);
        log.info("Successfully retrieved snot records for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_RECORD_LIST.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, result));
    }

    // ==================================== 구토 일별 조회
    @Operation(summary = "구토 날짜별 조회", description = """
            특정 날짜의 구토 기록을 조회합니다.
            - 필수 필드: petId, date
            - date: 조회할 날짜 (yyyy-MM-dd)
            - 해당 날짜의 모든 구토 기록을 시간순으로 반환합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구토 날짜별 조회 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ResponseDateApi.VomitingDateResponseApi.class))
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
    @GetMapping("/vomiting/date")
    public ResponseEntity<ResponseDTO<ResponseDateListDTO<ResponseDateDTO.VomitingResponse>>> getVomitingByDate(@Valid @ModelAttribute RequestDateDTO dto) {
        log.info("Received request to get vomiting records by date for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());
        ResponseDateListDTO<ResponseDateDTO.VomitingResponse> result = queryService.getVomitingByDate(dto);
        log.info("Successfully retrieved vomiting records for pet ID: {} on date: {}", dto.getPetId(), dto.getDate());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_RECORD_LIST.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, result));
    }
}