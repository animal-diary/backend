package animal.diary.controller;

import animal.diary.annotation.ValidatePetWithApiResponse;
import animal.diary.dto.request.UrinaryFilterRequestDTO;
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
                    responseCode = "200",
                    description = "필터링 성공",
                    content = @Content(
                            schema = @Schema(implementation = FilterResponseDTO.class),
                            examples = @ExampleObject(
                                    name = "필터링 결과 예시",
                                    value = """
                                    {
                                      "status": 200,
                                      "code": "SUCCESS_FILTER_RECORDS",
                                      "message": "필터링된 기록을 성공적으로 조회했습니다.",
                                      "data": {
                                        "dates": [5, 12, 18, 25],
                                        "count": 4
                                      }
                                    }
                                    """
                            )
                    )
            )
    })
    @PostMapping("/urinary/{petId}/{year}/{month}")
    @ValidatePetWithApiResponse(paramName = "petId")
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
}