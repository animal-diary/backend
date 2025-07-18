package animal.diary.dto;

import animal.diary.entity.record.*;
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
    private Integer count;
    private String urineState;
    private String urineAmount;

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

    public static ResponseDateDTO appetiteToDTO(Appetite appetite){
        return ResponseDateDTO.builder()
                .diaryId(appetite.getId())
                .state(appetite.getState().name())
                .createdTime(appetite.getCreatedAt().toLocalTime())
                .build();
    }

    public static ResponseDateDTO respiratoryRateTODTO(RespiratoryRate respiratoryRate) {
        return ResponseDateDTO.builder()
                .diaryId(respiratoryRate.getId())
                .count(respiratoryRate.getCount())
                .createdTime(respiratoryRate.getCreatedAt().toLocalTime())
                .build();
    }

    // 심박수
    public static ResponseDateDTO heartRateToDTO(HeartRate heartRate) {
        return ResponseDateDTO.builder()
                .diaryId(heartRate.getId())
                .count(heartRate.getCount())
                .createdTime(heartRate.getCreatedAt().toLocalTime())
                .build();
    }

    public static ResponseDateDTO syncopeToDTO(Syncope syncope) {
        return ResponseDateDTO.builder()
                .diaryId(syncope.getId())
                .state(syncope.getState().name())
                .createdTime(syncope.getCreatedAt().toLocalTime())
                .build();
    }

    public static ResponseDateDTO urinaryToDTO(Urinary urinary) {
        return ResponseDateDTO.builder()
                .diaryId(urinary.getId())
                .urineState(urinary.getState().name())
                .urineAmount(urinary.getOutput().name())
                .createdTime(urinary.getCreatedAt().toLocalTime())
                .build();
    }
}
