package animal.diary.dto;

import animal.diary.entity.record.Energy;
import animal.diary.entity.record.Weight;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RecordResponseDTO {
    private Long petId;
    private Float weight;
    private String state;
    private LocalDateTime createdAt;

    public static RecordResponseDTO weightToDTO(Weight weight) {
        return RecordResponseDTO.builder()
                .petId(weight.getPet().getId())
                .weight(weight.getWeight())
                .createdAt(weight.getCreatedAt())
                .build();
    }

    public static RecordResponseDTO energyToDTO(Energy energy) {
        return RecordResponseDTO.builder()
                .petId(energy.getPet().getId())
                .state(energy.getState().name())
                .createdAt(energy.getCreatedAt())
                .build();
    }

}
