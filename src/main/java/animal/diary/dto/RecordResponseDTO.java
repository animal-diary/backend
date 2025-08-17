package animal.diary.dto;

import animal.diary.entity.record.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RecordResponseDTO {
    private Long petId;
    private Float weight;
    private String state;
    private Integer count;
    private String urineState;
    private String urineAmount;

    // 특이사항
    private String title;
    private String content;

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

    public static RecordResponseDTO appetiteToDTO(Appetite appetite) {
        return RecordResponseDTO.builder()
                .petId(appetite.getPet().getId())
                .state(appetite.getState().name())
                .createdAt(appetite.getCreatedAt())
                .build();
    }

    public static RecordResponseDTO respiratoryRateToDTO(RespiratoryRate respiratoryRate) {
        return RecordResponseDTO.builder()
                .petId(respiratoryRate.getPet().getId())
                .count(respiratoryRate.getCount())
                .createdAt(respiratoryRate.getCreatedAt())
                .build();
    }

    public static RecordResponseDTO heartRateToDTO(HeartRate heartRate) {
        return RecordResponseDTO.builder()
                .petId(heartRate.getPet().getId())
                .count(heartRate.getCount())
                .createdAt(heartRate.getCreatedAt())
                .build();
    }

    public static RecordResponseDTO syncopeToDTO(Syncope syncope) {
        return RecordResponseDTO.builder()
                .petId(syncope.getPet().getId())
                .state(syncope.getState().name())
                .createdAt(syncope.getCreatedAt())
                .build();
    }

    public static RecordResponseDTO urinaryToDTO(Urinary urinary) {
        return RecordResponseDTO.builder()
                .petId(urinary.getPet().getId())
                .urineState(urinary.getState().name())
                .urineAmount(urinary.getOutput().name())
                .createdAt(urinary.getCreatedAt())
                .build();
    }

    public static RecordResponseDTO significantToDTO(Significant significant) {
        return RecordResponseDTO.builder()
                .petId(significant.getPet().getId())
                .title(significant.getTitle())
                .content(significant.getContent())
                .createdAt(significant.getCreatedAt())
                .build();
    }

    public static RecordResponseDTO convulsionToDTO(Convulsion convulsion) {
        return RecordResponseDTO.builder()
                .petId(convulsion.getPet().getId())
                .title(convulsion.getState().name())
                .content(convulsion.getAbnormalState().toString())
                .createdAt(convulsion.getCreatedAt())
                .build();
    }
}
