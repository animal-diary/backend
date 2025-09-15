package animal.diary.controller;

import animal.diary.annotation.ValidatePetId;
import animal.diary.annotation.ValidatePetWithApiResponse;
import animal.diary.dto.response.FlatStatisticsResponseDTO;
import animal.diary.service.FlatStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Frequency-monthly Controller", description = "각 필드별 독립적인 통계 API")
@RestController
@RequestMapping("/api/v1/frequency-monthly")
@RequiredArgsConstructor
@ValidatePetWithApiResponse
@Slf4j
public class FrequencyMonthlyController {

    private final FlatStatisticsService flatStatisticsService;

    // ==============================================
    // 소변 통계 - 4개 필드 독립 통계
    // ==============================================

    @Operation(
        summary = "소변 관련 필드별 독립 통계 조회",
        description = """
            소변 관련 모든 필드들의 독립적인 통계를 조회합니다.
            - 같은 날 하나의 값으로 기록된 경우 dates 에는 한 번만 포함됩니다.

            반환되는 필드들:
            - output: 소변량 (NONE, LOW, NORMAL, HIGH)
            - state: 소변 상태 (NORMAL, BLOODY, LIGHT, DARK, ETC)
            - binaryState: 악취 유무 (O, X)
            - withImageOrMemo: 이미지 및 메모 유무 (O, X)

            각 필드별로 독립적인 카운트와 날짜 정보를 제공합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "소변 통계 조회 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터")
    })
    @GetMapping("/urinary/{petId}/{year}/{month}")
    public ResponseEntity<FlatStatisticsResponseDTO> getUrinaryFlatStatistics(
            @Parameter(description = "반려동물 ID", required = true, example = "1")
            @PathVariable Long petId,
            @Parameter(description = "조회 연도 (YYYY)", required = true, example = "2025")
            @PathVariable int year,
            @Parameter(description = "조회 월 (1-12, mm)", required = true, example = "9")
            @PathVariable int month) {

        FlatStatisticsResponseDTO response = flatStatisticsService.getUrinaryFlatStatistics(petId, year, month);
        return ResponseEntity.ok(response);
    }

    // ==============================================
    // 배변 통계 - 3개 필드 독립 통계
    // ==============================================

    @Operation(
        summary = "배변 관련 필드별 독립 통계 조회",
        description = """
            배변 관련 모든 필드들의 독립적인 통계를 조회합니다.
            - 같은 날 하나의 값으로 기록된 경우 dates 에는 한 번만 포함됩니다.

            반환되는 필드들:
            - level: 배변량 (NONE, LOW, NORMAL, HIGH)
            - state: 배변 상태 (NORMAL, DIARRHEA, BLOODY, ETC)
            - withImageOrMemo: 이미지 및 메모 유무 (O, X)

            각 필드별로 독립적인 카운트와 날짜 정보를 제공합니다.
            """
    )
    @GetMapping("/defecation/{petId}/{year}/{month}")
    public ResponseEntity<FlatStatisticsResponseDTO> getDefecationFlatStatistics(
            @Parameter(description = "반려동물 ID", required = true, example = "1")
            @PathVariable Long petId,
            @Parameter(description = "조회 연도(YYYY)", required = true, example = "2025")
            @PathVariable int year,
            @Parameter(description = "조회 월(1-12, mm)", required = true, example = "9")
            @PathVariable int month) {

        FlatStatisticsResponseDTO response = flatStatisticsService.getDefecationFlatStatistics(petId, year, month);
        return ResponseEntity.ok(response);
    }

    // ==============================================
    // 피부 통계 - 2개 필드 독립 통계
    // ==============================================

    @Operation(
        summary = "피부 관련 필드별 독립 통계 조회",
        description = """
            피부 관련 모든 필드들의 독립적인 통계를 조회합니다.
            - 같은 날 하나의 값으로 기록된 경우 dates 에는 한 번만 포함됩니다.

            반환되는 필드들:
            - state: 피부 상태 (ZERO, ONE, TWO, THREE)
            - withImageOrMemo: 이미지 및 메모 유무 (O, X)

            각 필드별로 독립적인 카운트와 날짜 정보를 제공합니다.
            """
    )
    @GetMapping("/skin/{petId}/{year}/{month}")
    public ResponseEntity<FlatStatisticsResponseDTO> getSkinFlatStatistics(
            @Parameter(description = "반려동물 ID", required = true, example = "1")
            @PathVariable Long petId,
            @Parameter(description = "조회 연도(YYYY)", required = true, example = "2025")
            @PathVariable int year,
            @Parameter(description = "조회 월(1-12, mm)", required = true, example = "9")
            @PathVariable int month) {

        FlatStatisticsResponseDTO response = flatStatisticsService.getSkinFlatStatistics(petId, year, month);
        return ResponseEntity.ok(response);
    }

    // ==============================================
    // 경련 통계 - 3개 필드 독립 통계
    // ==============================================

    @Operation(
        summary = "경련 관련 필드별 독립 통계 조회",
        description = """
            경련 관련 모든 필드들의 독립적인 통계를 조회합니다.
            - 같은 날 하나의 값으로 기록된 경우 dates 에는 한 번만 포함됩니다.

            반환되는 필드들:
            - state: 경련 여부 (O, X)
            - abnormalState: 이상 증상 (INCONTINENCE, DROOLING, UNCONSCIOUS, NORMAL 등)
            - withVideo: 비디오 유무 (O, X)

            각 필드별로 독립적인 카운트와 날짜 정보를 제공합니다.
            """
    )
    @GetMapping("/convulsion/{petId}/{year}/{month}")
    public ResponseEntity<FlatStatisticsResponseDTO> getConvulsionFlatStatistics(
            @Parameter(description = "반려동물 ID", required = true, example = "1")
            @PathVariable Long petId,
            @Parameter(description = "조회 연도(YYYY)", required = true, example = "2025")
            @PathVariable int year,
            @Parameter(description = "조회 월(1-12, mm)", required = true, example = "9")
            @PathVariable int month) {

        FlatStatisticsResponseDTO response = flatStatisticsService.getConvulsionFlatStatistics(petId, year, month);
        return ResponseEntity.ok(response);
    }

    // ==============================================
    // 콧물 통계 - 2개 필드 독립 통계
    // ==============================================

    @Operation(
        summary = "콧물 관련 필드별 독립 통계 조회",
        description = """
            콧물 관련 모든 필드들의 독립적인 통계를 조회합니다.
            - 같은 날 하나의 값으로 기록된 경우 dates 에는 한 번만 포함됩니다.

            반환되는 필드들:
            - state: 콧물 상태 (CLEAR, MUCUS, BLOODY)
            - withImageOrMemo: 이미지 및 메모 유무 (O, X)

            각 필드별로 독립적인 카운트와 날짜 정보를 제공합니다.
            """
    )
    @GetMapping("/snot/{petId}/{year}/{month}")
    public ResponseEntity<FlatStatisticsResponseDTO> getSnotFlatStatistics(
            @Parameter(description = "반려동물 ID", required = true, example = "1")
            @PathVariable Long petId,
            @Parameter(description = "조회 연도(YYYY)", required = true, example = "2025")
            @PathVariable int year,
            @Parameter(description = "조회 월(1-12, mm)", required = true, example = "9")
            @PathVariable int month) {

        FlatStatisticsResponseDTO response = flatStatisticsService.getSnotFlatStatistics(petId, year, month);
        return ResponseEntity.ok(response);
    }

    // ==============================================
    // 구토 통계 - 2개 필드 독립 통계
    // ==============================================

    @Operation(
        summary = "구토 관련 필드별 독립 통계 조회",
        description = """
            구토 관련 모든 필드들의 독립적인 통계를 조회합니다.
            - 같은 날 하나의 값으로 기록된 경우 dates 에는 한 번만 포함됩니다.

            반환되는 필드들:
            - state: 구토 여부 (O, X)
            - withImages: 이미지 유무 (O, X)

            각 필드별로 독립적인 카운트와 날짜 정보를 제공합니다.
            """
    )
    @GetMapping("/vomiting/{petId}/{year}/{month}")
    public ResponseEntity<FlatStatisticsResponseDTO> getVomitingFlatStatistics(
            @Parameter(description = "반려동물 ID", required = true, example = "1")
            @PathVariable Long petId,
            @Parameter(description = "조회 연도(YYYY)", required = true, example = "2025")
            @PathVariable int year,
            @Parameter(description = "조회 월(1-12, mm)", required = true, example = "9")
            @PathVariable int month) {

        FlatStatisticsResponseDTO response = flatStatisticsService.getVomitingFlatStatistics(petId, year, month);
        return ResponseEntity.ok(response);
    }

    // ==============================================
    // 단순 상태만 있는 엔티티들 - 1개 필드
    // ==============================================

    @Operation(
        summary = "식욕 상태 통계 조회",
        description = """
            식욕 상태의 독립적인 통계를 조회합니다.
            - 같은 날 하나의 값으로 기록된 경우 dates 에는 한 번만 포함됩니다.

            반환되는 필드들:
            - state: 식욕 상태 (LOW, NORMAL, HIGH)
            """
    )
    @GetMapping("/appetite/{petId}/{year}/{month}")
    public ResponseEntity<FlatStatisticsResponseDTO> getAppetiteFlatStatistics(
            @Parameter(description = "반려동물 ID", required = true, example = "1")
            @PathVariable Long petId,
            @Parameter(description = "조회 연도(YYYY)", required = true, example = "2025")
            @PathVariable int year,
            @Parameter(description = "조회 월(1-12, mm)", required = true, example = "9")
            @PathVariable int month) {

        FlatStatisticsResponseDTO response = flatStatisticsService.getAppetiteFlatStatistics(petId, year, month);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "활력 상태 통계 조회",
        description = """
            활력 상태의 독립적인 통계를 조회합니다.
            - 같은 날 하나의 값으로 기록된 경우 dates 에는 한 번만 포함됩니다.

            반환되는 필드들:
            - state: 활력 상태 (LOW, NORMAL, HIGH)
            """
    )
    @GetMapping("/energy/{petId}/{year}/{month}")
    public ResponseEntity<FlatStatisticsResponseDTO> getEnergyFlatStatistics(
            @Parameter(description = "반려동물 ID", required = true, example = "1")
            @PathVariable Long petId,
            @Parameter(description = "조회 연도(YYYY)", required = true, example = "2025")
            @PathVariable int year,
            @Parameter(description = "조회 월(1-12, mm)", required = true, example = "9")
            @PathVariable int month) {

        FlatStatisticsResponseDTO response = flatStatisticsService.getEnergyFlatStatistics(petId, year, month);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "실신 여부 통계 조회",
        description = """
            실신 여부의 독립적인 통계를 조회합니다.
            - 같은 날 하나의 값으로 기록된 경우 dates 에는 한 번만 포함됩니다.

            반환되는 필드들:
            - state: 실신 여부 (O, X)
            """
    )
    @GetMapping("/syncope/{petId}/{year}/{month}")
    public ResponseEntity<FlatStatisticsResponseDTO> getSyncopeFlatStatistics(
            @Parameter(description = "반려동물 ID", required = true, example = "1")
            @PathVariable Long petId,
            @Parameter(description = "조회 연도(YYYY)", required = true, example = "2025")
            @PathVariable int year,
            @Parameter(description = "조회 월(1-12, mm)", required = true, example = "9")
            @PathVariable int month) {

        FlatStatisticsResponseDTO response = flatStatisticsService.getSyncopeFlatStatistics(petId, year, month);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "물 섭취량 상태 통계 조회",
        description = """
            물 섭취량 상태의 독립적인 통계를 조회합니다.
            - 같은 날 하나의 값으로 기록된 경우 dates 에는 한 번만 포함됩니다.

            반환되는 필드들:
            - state: 물 섭취량 상태 (LOW, NORMAL, HIGH)
            """
    )
    @GetMapping("/water/{petId}/{year}/{month}")
    public ResponseEntity<FlatStatisticsResponseDTO> getWaterFlatStatistics(
            @Parameter(description = "반려동물 ID", required = true, example = "1")
            @PathVariable Long petId,
            @Parameter(description = "조회 연도(YYYY)", required = true, example = "2025")
            @PathVariable int year,
            @Parameter(description = "조회 월(1-12, mm)", required = true, example = "9")
            @PathVariable int month) {

        FlatStatisticsResponseDTO response = flatStatisticsService.getWaterFlatStatistics(petId, year, month);
        return ResponseEntity.ok(response);
    }
}