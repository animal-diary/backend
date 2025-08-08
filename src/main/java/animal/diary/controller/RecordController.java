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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/record")
@RequiredArgsConstructor
@Tag(name = "Record Controller", description = "기록 관련 API")
public class RecordController {
    private final RecordService recordService;

    @Operation(summary = "몸무게 기록", description = "몸무게를 기록합니다.")
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
    @PostMapping("/weight")
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordWeight(@Validated(WeightGroup.class) @RequestBody RecordNumberDTO dto) {
        RecordResponseDTO result = recordService.recordWeight(dto);

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
    public ResponseEntity<ResponseDTO<ResponseDateListDTO>> getWeightsByDate(
            @RequestParam LocalDate date, 
            @RequestParam Long petId) {
        RequestDateDTO dto = new RequestDateDTO();
        dto.setDate(date);
        dto.setPetId(petId);
        
        ResponseDateListDTO result = recordService.getWeightsByDate(dto);

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_WEIGHT_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_WEIGHT_BY_DATE, result));
    }

    
    // 기력 상태
    @Operation(summary = "기력 상태 기록", description = "기력 상태를 기록합니다.")
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
        RecordResponseDTO result = recordService.recordEnergyAndAppetite(dto, "energy");

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
    public ResponseEntity<ResponseDTO<ResponseDateListDTO>> getEnergyByDate(
            @RequestParam LocalDate date, 
            @RequestParam Long petId) {
        RequestDateDTO dto = new RequestDateDTO();
        dto.setDate(date);
        dto.setPetId(petId);
        
        ResponseDateListDTO result = recordService.getEnergyOrAppetiteByDate(dto, "energy");

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_ENERGY_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_ENERGY_BY_DATE, result));
    }

    // 식욕 상태
    @Operation(summary = "식욕 상태 기록", description = "식욕 상태를 기록합니다.")
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
        RecordResponseDTO result = recordService.recordEnergyAndAppetite(dto, "appetite");

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
    public ResponseEntity<ResponseDTO<ResponseDateListDTO>> getAppetiteByDate(
            @RequestParam LocalDate date, 
            @RequestParam Long petId) {
        RequestDateDTO dto = new RequestDateDTO();
        dto.setDate(date);
        dto.setPetId(petId);
        
        ResponseDateListDTO result = recordService.getEnergyOrAppetiteByDate(dto, "appetite");

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_APPETITE_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_APPETITE_BY_DATE, result));
    }


    // 호흡 수
    @Operation(summary = "호흡 수 기록", description = "호흡 수를 기록합니다.")
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
        RecordResponseDTO result = recordService.recordRRAndHeartRate(dto, VitalCategory.RR);

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
    public ResponseEntity<ResponseDTO<ResponseDateListDTO>> getRRByDate(
            @RequestParam LocalDate date, 
            @RequestParam Long petId) {
        RequestDateDTO dto = new RequestDateDTO();
        dto.setDate(date);
        dto.setPetId(petId);
        
        ResponseDateListDTO result = recordService.getRROrHeartRateByDate(dto, VitalCategory.RR);

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_RR_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RR_BY_DATE, result));
    }

    // 심박수
    @Operation(summary = "심박수 기록", description = "심박수를 기록합니다.")
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
        RecordResponseDTO result = recordService.recordRRAndHeartRate(dto, VitalCategory.HEART_RATE);

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
    public ResponseEntity<ResponseDTO<ResponseDateListDTO>> getHeartRateByDate(
            @RequestParam LocalDate date, 
            @RequestParam Long petId) {
        RequestDateDTO dto = new RequestDateDTO();
        dto.setDate(date);
        dto.setPetId(petId);
        
        ResponseDateListDTO result = recordService.getRROrHeartRateByDate(dto, VitalCategory.HEART_RATE);

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_HEART_RATE_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_HEART_RATE_BY_DATE, result));
    }

    // 기절 상태
    @Operation(summary = "기절 상태 기록", description = "기절 상태를 기록합니다.")
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
        RecordResponseDTO result = recordService.recordSyncope(dto);

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
    public ResponseEntity<ResponseDTO<ResponseDateListDTO>> getSyncopeByDate(
            @RequestParam LocalDate date, 
            @RequestParam Long petId) {
        RequestDateDTO dto = new RequestDateDTO();
        dto.setDate(date);
        dto.setPetId(petId);
        
        ResponseDateListDTO result = recordService.getSyncopeByDate(dto);

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_SYNCOPE_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_SYNCOPE_BY_DATE, result));
    }

    // 소변 상태
    @Operation(summary = "소변 상태 기록", description = "소변 상태를 기록합니다.")
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
        RecordResponseDTO result = recordService.recordUrinary(dto);

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
    public ResponseEntity<ResponseDTO<ResponseDateListDTO>> getUrineByDate(
            @RequestParam LocalDate date, 
            @RequestParam Long petId) {
        RequestDateDTO dto = new RequestDateDTO();
        dto.setDate(date);
        dto.setPetId(petId);
        
        ResponseDateListDTO result = recordService.getUrinaryByDate(dto);

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_URINE_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_URINE_BY_DATE, result));
    }

}
