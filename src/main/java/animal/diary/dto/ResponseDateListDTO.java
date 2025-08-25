package animal.diary.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class ResponseDateListDTO {
    private LocalDate date;
    private String type; // 강아지, 고양이
    private List<DiaryDateResponse> dateDTOS;
}
