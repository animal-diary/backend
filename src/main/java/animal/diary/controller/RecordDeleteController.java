package animal.diary.controller;

import animal.diary.code.SuccessCode;
import animal.diary.dto.response.ResponseDTO;
import animal.diary.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/records")
@RequiredArgsConstructor
@Tag(name = "Record Delete", description = "기록 삭제 API")
public class RecordDeleteController {

    private final RecordService recordService;

    @DeleteMapping("/weight/{recordId}")
    @Operation(summary = "몸무게 기록 삭제", description = "특정 ID의 몸무게 기록을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "기록을 찾을 수 없음")
    })
    public ResponseEntity<ResponseDTO<String>> deleteWeight(@PathVariable Long recordId) {
        recordService.deleteWeight(recordId);
        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_DELETE_RECORD, "몸무게 기록이 성공적으로 삭제되었습니다."));
    }

    @DeleteMapping("/energy/{recordId}")
    @Operation(summary = "기력 기록 삭제", description = "특정 ID의 기력 기록을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "기록을 찾을 수 없음")
    })
    public ResponseEntity<ResponseDTO<String>> deleteEnergy(@PathVariable Long recordId) {
        recordService.deleteEnergy(recordId);
        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_DELETE_RECORD, "기력 기록이 성공적으로 삭제되었습니다."));
    }

    @DeleteMapping("/appetite/{recordId}")
    @Operation(summary = "식욕 기록 삭제", description = "특정 ID의 식욕 기록을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "기록을 찾을 수 없음")
    })
    public ResponseEntity<ResponseDTO<String>> deleteAppetite(@PathVariable Long recordId) {
        recordService.deleteAppetite(recordId);
        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_DELETE_RECORD, "식욕 기록이 성공적으로 삭제되었습니다."));
    }

    @DeleteMapping("/rr/{recordId}")
    @Operation(summary = "호흡수 기록 삭제", description = "특정 ID의 호흡수 기록을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "기록을 찾을 수 없음")
    })
    public ResponseEntity<ResponseDTO<String>> deleteRR(@PathVariable Long recordId) {
        recordService.deleteRR(recordId);
        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_DELETE_RECORD, "호흡수 기록이 성공적으로 삭제되었습니다."));
    }

    @DeleteMapping("/heart-rate/{recordId}")
    @Operation(summary = "심박수 기록 삭제", description = "특정 ID의 심박수 기록을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "기록을 찾을 수 없음")
    })
    public ResponseEntity<ResponseDTO<String>> deleteHeartRate(@PathVariable Long recordId) {
        recordService.deleteHeartRate(recordId);
        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_DELETE_RECORD, "심박수 기록이 성공적으로 삭제되었습니다."));
    }

    @DeleteMapping("/syncope/{recordId}")
    @Operation(summary = "기절 기록 삭제", description = "특정 ID의 기절 기록을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "기록을 찾을 수 없음")
    })
    public ResponseEntity<ResponseDTO<String>> deleteSyncope(@PathVariable Long recordId) {
        recordService.deleteSyncope(recordId);
        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_DELETE_RECORD, "기절 기록이 성공적으로 삭제되었습니다."));
    }

    @DeleteMapping("/urinary/{recordId}")
    @Operation(summary = "소변 기록 삭제", description = "특정 ID의 소변 기록과 관련 이미지를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "기록을 찾을 수 없음")
    })
    public ResponseEntity<ResponseDTO<String>> deleteUrinary(@PathVariable Long recordId) {
        recordService.deleteUrinary(recordId);
        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_DELETE_RECORD, "소변 기록이 성공적으로 삭제되었습니다."));
    }

    @DeleteMapping("/significant/{recordId}")
    @Operation(summary = "특이사항 기록 삭제", description = "특정 ID의 특이사항 기록과 관련 이미지/비디오를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "기록을 찾을 수 없음")
    })
    public ResponseEntity<ResponseDTO<String>> deleteSignificant(@PathVariable Long recordId) {
        recordService.deleteSignificant(recordId);
        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_DELETE_RECORD, "특이사항 기록이 성공적으로 삭제되었습니다."));
    }

    @DeleteMapping("/convulsion/{recordId}")
    @Operation(summary = "경련 기록 삭제", description = "특정 ID의 경련 기록과 관련 이미지를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "기록을 찾을 수 없음")
    })
    public ResponseEntity<ResponseDTO<String>> deleteConvulsion(@PathVariable Long recordId) {
        recordService.deleteConvulsion(recordId);
        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_DELETE_RECORD, "경련 기록이 성공적으로 삭제되었습니다."));
    }

    @DeleteMapping("/sound/{recordId}")
    @Operation(summary = "이상 소리 기록 삭제", description = "특정 ID의 이상 소리 기록과 관련 비디오를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "기록을 찾을 수 없음")
    })
    public ResponseEntity<ResponseDTO<String>> deleteSound(@PathVariable Long recordId) {
        recordService.deleteSound(recordId);
        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_DELETE_RECORD, "이상 소리 기록이 성공적으로 삭제되었습니다."));
    }

    @DeleteMapping("/snot/{recordId}")
    @Operation(summary = "콧물 기록 삭제", description = "특정 ID의 콧물 기록과 관련 이미지를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "기록을 찾을 수 없음")
    })
    public ResponseEntity<ResponseDTO<String>> deleteSnot(@PathVariable Long recordId) {
        recordService.deleteSnot(recordId);
        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_DELETE_RECORD, "콧물 기록이 성공적으로 삭제되었습니다."));
    }

    @DeleteMapping("/vomiting/{recordId}")
    @Operation(summary = "구토 기록 삭제", description = "특정 ID의 구토 기록과 관련 이미지를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "기록을 찾을 수 없음")
    })
    public ResponseEntity<ResponseDTO<String>> deleteVomiting(@PathVariable Long recordId) {
        recordService.deleteVomiting(recordId);
        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_DELETE_RECORD, "구토 기록이 성공적으로 삭제되었습니다."));
    }

    @DeleteMapping("/walking/{recordId}")
    @Operation(summary = "걷는 모습 기록 삭제", description = "특정 ID의 걷는 모습 기록과 관련 비디오를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "기록을 찾을 수 없음")
    })
    public ResponseEntity<ResponseDTO<String>> deleteWalking(@PathVariable Long recordId) {
        recordService.deleteWalking(recordId);
        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_DELETE_RECORD, "걷는 모습 기록이 성공적으로 삭제되었습니다."));
    }

    @DeleteMapping("/water/{recordId}")
    @Operation(summary = "물 섭취 기록 삭제", description = "특정 ID의 물 섭취 기록을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "기록을 찾을 수 없음")
    })
    public ResponseEntity<ResponseDTO<String>> deleteWater(@PathVariable Long recordId) {
        recordService.deleteWater(recordId);
        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_DELETE_RECORD, "물 섭취 기록이 성공적으로 삭제되었습니다."));
    }

    @DeleteMapping("/skin/{recordId}")
    @Operation(summary = "피부 상태 기록 삭제", description = "특정 ID의 피부 상태 기록과 관련 이미지를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "기록을 찾을 수 없음")
    })
    public ResponseEntity<ResponseDTO<String>> deleteSkin(@PathVariable Long recordId) {
        recordService.deleteSkin(recordId);
        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_DELETE_RECORD, "피부 상태 기록이 성공적으로 삭제되었습니다."));
    }

    @DeleteMapping("/defecation/{recordId}")
    @Operation(summary = "배변 기록 삭제", description = "특정 ID의 배변 기록과 관련 이미지를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "기록을 찾을 수 없음")
    })
    public ResponseEntity<ResponseDTO<String>> deleteDefecation(@PathVariable Long recordId) {
        recordService.deleteDefecation(recordId);
        return ResponseEntity.ok(new ResponseDTO<>(SuccessCode.SUCCESS_DELETE_RECORD, "배변 기록이 성공적으로 삭제되었습니다."));
    }
}