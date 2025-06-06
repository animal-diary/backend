package animal.diary.dto;

import animal.diary.entity.record.Energy;
import animal.diary.entity.record.Weight;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ResponseDateDTO {
    private Long diaryId;
    private String state;
    private Float weight;
    private LocalTime createdTime;

    public static ResponseDateDTO weightToDTO(Weight weight) {
        return ResponseDateDTO.builder()
                .diaryId(weight.getId())
                .weight(weight.getWeight())
                .createdTime(weight.getCreatedAt().toLocalTime())
                .build();
    }

    public static ResponseDateDTO energyToDTO(Energy energy){
        return ResponseDateDTO.builder()
                .diaryId(energy.getId())
                .state(energy.getState().name())
                .createdTime(energy.getCreatedAt().toLocalTime())
                .build();
    }
}
