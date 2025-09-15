package animal.diary.controller;

import animal.diary.annotation.ValidatePetWithApiResponse;
import animal.diary.code.SuccessCode;
import animal.diary.dto.response.ResponseDTO;
import animal.diary.service.MonthlyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/monthly")
@RequiredArgsConstructor
@Tag(name = "Monthly Controller", description = "월별 기록 조회 관련 API")
public class MonthlyController {

    private final MonthlyService monthlyService;

    @GetMapping("/records/{petId}/{year}/{month}")
    @ValidatePetWithApiResponse
    @Operation(summary = "월별 기록 날짜 조회", description = """
            특정 반려동물의 특정 년/월에 기록이 있는 날짜들을 조회합니다.
            - 모든 증상 기록을 포함합니다 (몸무게, 기력, 식욕, 호흡수, 심박수, 기절, 소변, 특이사항, 경련, 이상소리, 콧물, 구토, 걷는모습, 물섭취, 피부상태, 배변)
            - 반환값: 기록이 있는 날짜들의 리스트 (예: [5, 7, 15, 23])
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 월별 기록 날짜 조회",
                    content = { @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "status": 200,
                                              "code": "SUCCESS_GET_RECORD_LIST",
                                              "message": "기록 리스트를 성공적으로 불러왔습니다.",
                                              "data": [ 6, 14, 15 ]
                                            }
                                            """
                            )
                    )})
    })
    public ResponseEntity<ResponseDTO<List<Integer>>> getMonthlyRecords(
            @Parameter(description = "반려동물 ID", example = "1")
            @PathVariable Long petId,
            @Parameter(description = "연도 (YYYY)", example = "2025")
            @PathVariable int year,
            @Parameter(description = "월 (1-12, mm)", example = "9")
            @PathVariable int month
    ) {
        log.info("Received monthly records request for pet ID: {}, year: {}, month: {}", petId, year, month);

        List<Integer> recordDates = monthlyService.getRecordDates(petId, year, month);

        log.info("Found {} record dates for pet ID: {} in {}-{}", recordDates.size(), petId, year, month);

        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, recordDates));
    }
}
