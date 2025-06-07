package animal.diary.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseDateListDTO {
    private LocalDate date;
    private String type; // 강아지, 고양이
    private List<ResponseDateDTO> dateDTOS;
}
