package animal.diary.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Schema(description = "특정 날짜에 기록된 펫의 기록된 날짜 리스트 DTO")
public class ResponseDateListDTO<T> {
    @Schema(description = "날짜", example = "2025-08-05")
    private LocalDate date;
    @Schema(description = "펫 타입", example = "DOG")
    private String type; // 강아지, 고양이
    @Schema(description = "기록된 날짜 리스트")
    private List<T> dateDTOS;
}
