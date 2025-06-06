package animal.diary.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Data
public class RequestWeightDateDTO {
    private LocalDate date;
    private Long petId;
}
