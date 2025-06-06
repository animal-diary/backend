package animal.diary.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class ResponseWeightDateDTO {
    private LocalDate date;
    private List<ResponseWeightDTO> weightDTOS;
}
