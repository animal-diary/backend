package animal.diary.dto;

import animal.diary.entity.record.Weight;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Getter
public class ResponseWeightDTO {
    private Long weightId;
    private Float weight;
    private LocalTime createdTime;

    public static ResponseWeightDTO weightToDTO(Weight weight) {
        return ResponseWeightDTO.builder()
                .weightId(weight.getId())
                .weight(weight.getWeight())
                .createdTime(weight.getCreatedAt().toLocalTime())
                .build();
    }
}
