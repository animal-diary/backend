package animal.diary.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Data
public class RequestDateDTO {
    private LocalDate date;
    private Long petId;
}
