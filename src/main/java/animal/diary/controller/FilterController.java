package animal.diary.controller;

import animal.diary.annotation.ValidatePetWithApiResponse;
import animal.diary.dto.api.FilterDataApi;
import animal.diary.dto.request.ConvulsionFilterRequestDTO;
import animal.diary.dto.request.UrinaryFilterRequestDTO;
import animal.diary.dto.request.DefecationFilterRequestDTO;
import animal.diary.dto.request.SkinFilterRequestDTO;
import animal.diary.dto.request.SnotFilterRequestDTO;
import animal.diary.dto.request.VomitingFilterRequestDTO;
import animal.diary.dto.request.WaterFilterRequestDTO;
import animal.diary.dto.request.AppetiteFilterRequestDTO;
import animal.diary.dto.request.EnergyFilterRequestDTO;
import animal.diary.dto.request.SyncopeFilterRequestDTO;
import animal.diary.dto.response.FilterResponseDTO;
import animal.diary.service.FilterService;
import animal.diary.dto.response.ResponseDTO;
import animal.diary.code.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ValidatePetWithApiResponse
@RestController
@RequestMapping("/api/v1/filter")
@RequiredArgsConstructor
@Tag(name = "필터링 API", description = "특정 조건을 만족하는 기록 필터링 API")
public class FilterController {

    private final FilterService filterService;

    @Operation(
            summary = "소변 기록 필터링",
            description = """
                    특정 조건들을 모두 만족하는 소변 기록이 있는 날짜들을 반환합니다.
                    
                    - year, month: 필터링할 연도와 월
                    - output: 소변량 (NONE, LOW, NORMAL, HIGH)
                    - state: 소변 상태 (BLOODY, LIGHT, DARK, NORMAL, ETC)
                    - binaryState: 악취 유무 (O, X)
                    - withImageOrMemo: 메모/이미지 유무 (O, X 복수 선택 가능, 쉼표로 구분)
                    
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "필터링 성공",
                    content = @Content(
                            schema = @Schema(implementation = FilterDataApi.class)
                    )
            )
    })
    @PostMapping("/urinary/{petId}/{year}/{month}")
    public ResponseEntity<?> filterUrinaryRecords(
            @Parameter(description = "반려동물 ID", example = "1") @PathVariable Long petId,
            @Parameter(description = "년도", example = "2025") @PathVariable int year,
            @Parameter(description = "월", example = "9") @PathVariable int month,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "필터링 조건 (null인 필드는 조건에서 제외)",
                    content = @Content(
                            schema = @Schema(implementation = UrinaryFilterRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "필터 조건 예시",
                                    value = """
                                    {
                                      "output": "NORMAL",
                                      "state": "BLOODY",
                                      "binaryState": "O",
                                      "withImageOrMemo": "O,X"
                                    }
                                    """
                            )
                    )
            ) @RequestBody UrinaryFilterRequestDTO filter) {

        FilterResponseDTO result = filterService.filterUrinaryRecords(petId, year, month, filter);

        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, result));
    }


    // ====================================== 대변 필터링
    @Operation(
            summary = "대변 기록 필터링",
            description = """
                    특정 조건들을 모두 만족하는 대변 기록이 있는 날짜들을 반환합니다.
                    
                    - year, month: 필터링할 연도와 월
                    - level: 배변량 (NONE, LOW, NORMAL, HIGH)
                    - state: 대변 상태 (NORMAL, DIARRHEA, BLACK, BLOODY, ETC)
                    - withImageOrMemo: 메모/이미지 유무 (O, X 복수 선택 가능, 쉼표로 구분)
                    
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "필터링 성공",
                    content = @Content(
                            schema = @Schema(implementation = FilterDataApi.class)
                    )
            )
    })
    @PostMapping("/defecation/{petId}/{year}/{month}")
    public ResponseEntity<?> filterDefecationRecords(
            @Parameter(description = "반려동물 ID", example = "1") @PathVariable Long petId,
            @Parameter(description = "년도", example = "2025") @PathVariable int year,
            @Parameter(description = "월", example = "9") @PathVariable int month,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "필터링 조건 (null인 필드는 조건에서 제외)",
                    content = @Content(
                            schema = @Schema(implementation = DefecationFilterRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "필터 조건 예시",
                                    value = """
                                    {
                                      "level": "NORMAL",
                                      "state": "NORMAL",
                                      "withImageOrMemo": "O,X"
                                    }
                                    """
                            )
                    )
            ) @RequestBody DefecationFilterRequestDTO filter) {

        FilterResponseDTO result = filterService.filterDefecationRecords(petId, year, month, filter);

        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, result));
    }

    // ===================================== 경련 필터링
    @Operation(
            summary = "경련 기록 필터링",
            description = """
                    특정 조건들을 모두 만족하는 경련 기록이 있는 날짜들을 반환합니다.
                    
                    - year, month: 필터링할 연도와 월
                    - binaryState: 경련 상태 (O, X)
                    - abnormalState: 이상 행동 선택 (INCONTINENCE, DROOLING, UNCONSCIOUS, NORMAL) (복수 선택 가능, 쉼표로 구분)
                    - withVideo: 동영상 유무 (O, X 복수 선택 가능, 쉼표로 구분)
                    
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "필터링 성공",
                    content = @Content(
                            schema = @Schema(implementation = FilterDataApi.class)
                    )
            )
    })
    @PostMapping("/convulsion/{petId}/{year}/{month}")
    public ResponseEntity<?> filterConvulsionRecords(
            @Parameter(description = "반려동물 ID", example = "1") @PathVariable Long petId,
            @Parameter(description = "년도", example = "2025") @PathVariable int year,
            @Parameter(description = "월", example = "9") @PathVariable int month,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "필터링 조건 (null인 필드는 조건에서 제외)",
                    content = @Content(
                            schema = @Schema(implementation = ConvulsionFilterRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "필터 조건 예시",
                                    value = """
                                    {
                                      "binaryState": "O",
                                      "abnormalState": "INCONTINENCE,DROOLING",
                                      "withVideo": "O,X"
                                    }
                                    """
                            )
                    )
            ) @RequestBody ConvulsionFilterRequestDTO filter) {

        FilterResponseDTO result = filterService.filterConvulsionRecords(petId, year, month, filter);

        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, result));
    }

    // ===================================== 피부 필터링
    @Operation(
            summary = "피부 기록 필터링",
            description = """
                    특정 조건들을 모두 만족하는 피부 기록이 있는 날짜들을 반환합니다.

                    - year, month: 필터링할 연도와 월
                    - state: 피부 상태 (ZERO, ONE, TWO, THREE, FOUR, FIVE)
                    - withImageOrMemo: 메모/이미지 유무 (O, X 복수 선택 가능, 쉼표로 구분)

                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "필터링 성공",
                    content = @Content(
                            schema = @Schema(implementation = FilterDataApi.class)
                    )
            )
    })
    @PostMapping("/skin/{petId}/{year}/{month}")
    public ResponseEntity<?> filterSkinRecords(
            @Parameter(description = "반려동물 ID", example = "1") @PathVariable Long petId,
            @Parameter(description = "년도", example = "2025") @PathVariable int year,
            @Parameter(description = "월", example = "9") @PathVariable int month,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "필터링 조건 (null인 필드는 조건에서 제외)",
                    content = @Content(
                            schema = @Schema(implementation = SkinFilterRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "필터 조건 예시",
                                    value = """
                                    {
                                      "state": "THREE",
                                      "withImageOrMemo": "O,X"
                                    }
                                    """
                            )
                    )
            ) @RequestBody SkinFilterRequestDTO filter) {

        FilterResponseDTO result = filterService.filterSkinRecords(petId, year, month, filter);

        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, result));
    }

    // ===================================== 콧물 필터링
    @Operation(
            summary = "콧물 기록 필터링",
            description = """
                    특정 조건들을 모두 만족하는 콧물 기록이 있는 날짜들을 반환합니다.

                    - year, month: 필터링할 연도와 월
                    - state: 콧물 상태 (CLEAR, THICK, BLOODY, ETC)
                    - withImageOrMemo: 메모/이미지 유무 (O, X 복수 선택 가능, 쉼표로 구분)

                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "필터링 성공",
                    content = @Content(
                            schema = @Schema(implementation = FilterDataApi.class)
                    )
            )
    })
    @PostMapping("/snot/{petId}/{year}/{month}")
    public ResponseEntity<?> filterSnotRecords(
            @Parameter(description = "반려동물 ID", example = "1") @PathVariable Long petId,
            @Parameter(description = "년도", example = "2025") @PathVariable int year,
            @Parameter(description = "월", example = "9") @PathVariable int month,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "필터링 조건 (null인 필드는 조건에서 제외)",
                    content = @Content(
                            schema = @Schema(implementation = SnotFilterRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "필터 조건 예시",
                                    value = """
                                    {
                                      "state": "CLEAR",
                                      "withImageOrMemo": "O,X"
                                    }
                                    """
                            )
                    )
            ) @RequestBody SnotFilterRequestDTO filter) {

        FilterResponseDTO result = filterService.filterSnotRecords(petId, year, month, filter);

        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, result));
    }

    // ===================================== 구토 필터링
    @Operation(
            summary = "구토 기록 필터링",
            description = """
                    특정 조건들을 모두 만족하는 구토 기록이 있는 날짜들을 반환합니다.

                    - year, month: 필터링할 연도와 월
                    - state: 구토 상태 (O, X)
                    - withImage: 이미지 유무 (O, X 복수 선택 가능, 쉼표로 구분)

                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "필터링 성공",
                    content = @Content(
                            schema = @Schema(implementation = FilterDataApi.class)
                    )
            )
    })
    @PostMapping("/vomiting/{petId}/{year}/{month}")
    public ResponseEntity<?> filterVomitingRecords(
            @Parameter(description = "반려동물 ID", example = "1") @PathVariable Long petId,
            @Parameter(description = "년도", example = "2025") @PathVariable int year,
            @Parameter(description = "월", example = "9") @PathVariable int month,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "필터링 조건 (null인 필드는 조건에서 제외)",
                    content = @Content(
                            schema = @Schema(implementation = VomitingFilterRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "필터 조건 예시",
                                    value = """
                                    {
                                      "state": "O",
                                      "withImage": "O,X"
                                    }
                                    """
                            )
                    )
            ) @RequestBody VomitingFilterRequestDTO filter) {

        FilterResponseDTO result = filterService.filterVomitingRecords(petId, year, month, filter);

        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, result));
    }

    // ===================================== 음수량 필터링
    @Operation(
            summary = "음수량 기록 필터링",
            description = """
                    특정 조건들을 모두 만족하는 음수량 기록이 있는 날짜들을 반환합니다.

                    - year, month: 필터링할 연도와 월
                    - state: 음수량 상태 (NONE, LOW, NORMAL, HIGH)

                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "필터링 성공",
                    content = @Content(
                            schema = @Schema(implementation = FilterDataApi.class)
                    )
            )
    })
    @PostMapping("/water/{petId}/{year}/{month}")
    public ResponseEntity<?> filterWaterRecords(
            @Parameter(description = "반려동물 ID", example = "1") @PathVariable Long petId,
            @Parameter(description = "년도", example = "2025") @PathVariable int year,
            @Parameter(description = "월", example = "9") @PathVariable int month,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "필터링 조건 (null인 필드는 조건에서 제외)",
                    content = @Content(
                            schema = @Schema(implementation = WaterFilterRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "필터 조건 예시",
                                    value = """
                                    {
                                      "state": "NORMAL"
                                    }
                                    """
                            )
                    )
            ) @RequestBody WaterFilterRequestDTO filter) {

        FilterResponseDTO result = filterService.filterWaterRecords(petId, year, month, filter);

        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, result));
    }

    // ===================================== 식욕 필터링
    @Operation(
            summary = "식욕 기록 필터링",
            description = """
                    특정 조건들을 모두 만족하는 식욕 기록이 있는 날짜들을 반환합니다.

                    - year, month: 필터링할 연도와 월
                    - state: 식욕 상태 (NONE, LOW, NORMAL, HIGH)

                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "필터링 성공",
                    content = @Content(
                            schema = @Schema(implementation = FilterDataApi.class)
                    )
            )
    })
    @PostMapping("/appetite/{petId}/{year}/{month}")
    public ResponseEntity<?> filterAppetiteRecords(
            @Parameter(description = "반려동물 ID", example = "1") @PathVariable Long petId,
            @Parameter(description = "년도", example = "2025") @PathVariable int year,
            @Parameter(description = "월", example = "9") @PathVariable int month,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "필터링 조건 (null인 필드는 조건에서 제외)",
                    content = @Content(
                            schema = @Schema(implementation = AppetiteFilterRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "필터 조건 예시",
                                    value = """
                                    {
                                      "state": "NORMAL"
                                    }
                                    """
                            )
                    )
            ) @RequestBody AppetiteFilterRequestDTO filter) {

        FilterResponseDTO result = filterService.filterAppetiteRecords(petId, year, month, filter);

        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, result));
    }

    // ===================================== 활력도 필터링
    @Operation(
            summary = "활력도 기록 필터링",
            description = """
                    특정 조건들을 모두 만족하는 활력도 기록이 있는 날짜들을 반환합니다.

                    - year, month: 필터링할 연도와 월
                    - state: 활력도 상태 (NONE, LOW, NORMAL, HIGH)

                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "필터링 성공",
                    content = @Content(
                            schema = @Schema(implementation = FilterDataApi.class)
                    )
            )
    })
    @PostMapping("/energy/{petId}/{year}/{month}")
    public ResponseEntity<?> filterEnergyRecords(
            @Parameter(description = "반려동물 ID", example = "1") @PathVariable Long petId,
            @Parameter(description = "년도", example = "2025") @PathVariable int year,
            @Parameter(description = "월", example = "9") @PathVariable int month,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "필터링 조건 (null인 필드는 조건에서 제외)",
                    content = @Content(
                            schema = @Schema(implementation = EnergyFilterRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "필터 조건 예시",
                                    value = """
                                    {
                                      "state": "NORMAL"
                                    }
                                    """
                            )
                    )
            ) @RequestBody EnergyFilterRequestDTO filter) {

        FilterResponseDTO result = filterService.filterEnergyRecords(petId, year, month, filter);

        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, result));
    }

    // ===================================== 실신 필터링
    @Operation(
            summary = "실신 기록 필터링",
            description = """
                    특정 조건들을 모두 만족하는 실신 기록이 있는 날짜들을 반환합니다.

                    - year, month: 필터링할 연도와 월
                    - state: 실신 상태 (O, X)

                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "필터링 성공",
                    content = @Content(
                            schema = @Schema(implementation = FilterDataApi.class)
                    )
            )
    })
    @PostMapping("/syncope/{petId}/{year}/{month}")
    public ResponseEntity<?> filterSyncopeRecords(
            @Parameter(description = "반려동물 ID", example = "1") @PathVariable Long petId,
            @Parameter(description = "년도", example = "2025") @PathVariable int year,
            @Parameter(description = "월", example = "9") @PathVariable int month,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "필터링 조건 (null인 필드는 조건에서 제외)",
                    content = @Content(
                            schema = @Schema(implementation = SyncopeFilterRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "필터 조건 예시",
                                    value = """
                                    {
                                      "state": "O"
                                    }
                                    """
                            )
                    )
            ) @RequestBody SyncopeFilterRequestDTO filter) {

        FilterResponseDTO result = filterService.filterSyncopeRecords(petId, year, month, filter);

        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, result));
    }
}