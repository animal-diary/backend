package animal.diary.dto;

import animal.diary.entity.record.Weight;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
public class RecordResponseDTO {
    private Long petId;
    private Float weight;
    private LocalDateTime createdAt;

    public static RecordResponseDTO weightToDTO(Weight weight) {
        return RecordResponseDTO.builder()
                .petId(weight.getPet().getId())
                .weight(weight.getWeight())
                .createdAt(weight.getCreatedAt())
                .build();
    }
}
