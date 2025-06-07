package animal.diary.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Data
public class RequestDateDTO {

    @NotNull(message = "검색 일자는 필수입니다.")
    private LocalDate date;

    @NotNull(message = "pet ID는 필수입니다.")
    private Long petId;
}
