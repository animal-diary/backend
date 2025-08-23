package animal.diary.dto;

import animal.diary.entity.record.*;
import animal.diary.entity.record.state.AbnormalState;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class RecordResponseDTO {
    @Builder
    @Getter
    public static class WeightResponseDTO {
        private Long petId;
        private Float weight;
        private LocalDateTime createdAt;

        public static WeightResponseDTO weightToDTO(Weight weight) {
            return WeightResponseDTO.builder()
                    .petId(weight.getPet().getId())
                    .weight(weight.getWeight())
                    .createdAt(weight.getCreatedAt())
                    .build();
        }
    }
    @Builder
    @Getter
    public static class EnergyAndAppetiteResponseDTO {
        private Long petId;
        private String state;
        private LocalDateTime createdAt;

        public static EnergyAndAppetiteResponseDTO energyToDTO(Energy energy) {
            return EnergyAndAppetiteResponseDTO.builder()
                    .petId(energy.getPet().getId())
                    .state(energy.getState().name())
                    .createdAt(energy.getCreatedAt())
                    .build();
        }

        public static EnergyAndAppetiteResponseDTO appetiteToDTO(Appetite appetite) {
            return EnergyAndAppetiteResponseDTO.builder()
                    .petId(appetite.getPet().getId())
                    .state(appetite.getState().name())
                    .createdAt(appetite.getCreatedAt())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class RRAndHeartRateResponseDTO {
        private Long petId;
        private Integer count;
        private LocalDateTime createdAt;

        public static RRAndHeartRateResponseDTO respiratoryRateToDTO(RespiratoryRate respiratoryRate) {
            return RRAndHeartRateResponseDTO.builder()
                    .petId(respiratoryRate.getPet().getId())
                    .count(respiratoryRate.getCount())
                    .createdAt(respiratoryRate.getCreatedAt())
                    .build();
        }

        public static RRAndHeartRateResponseDTO heartRateToDTO(HeartRate heartRate) {
            return RRAndHeartRateResponseDTO.builder()
                    .petId(heartRate.getPet().getId())
                    .count(heartRate.getCount())
                    .createdAt(heartRate.getCreatedAt())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class SyncopeResponseDTO {
        private Long petId;
        private String state;
        private LocalDateTime createdAt;

        public static SyncopeResponseDTO syncopeToDTO(Syncope syncope) {
            return SyncopeResponseDTO.builder()
                    .petId(syncope.getPet().getId())
                    .state(syncope.getState().name())
                    .createdAt(syncope.getCreatedAt())
                    .build();
        }
    }
    @Builder
    @Getter
    public static class UrinaryResponseDTO {
        private Long petId;
        private String binaryState;
        private String urineState;
        private String urineAmount;
        private LocalDateTime createdAt;

        public static UrinaryResponseDTO urinaryToDTO(Urinary urinary) {
            return UrinaryResponseDTO.builder()
                    .petId(urinary.getPet().getId())
                    .binaryState(urinary.getBinaryState().name())
                    .urineState(urinary.getState().name())
                    .urineAmount(urinary.getOutput().name())
                    .createdAt(urinary.getCreatedAt())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class SignificantResponseDTO {
        private Long petId;
        private String title;
        private String content;
        private LocalDateTime createdAt;

        public static SignificantResponseDTO significantToDTO(Significant significant) {
            return SignificantResponseDTO.builder()
                    .petId(significant.getPet().getId())
                    .title(significant.getTitle())
                    .content(significant.getContent())
                    .createdAt(significant.getCreatedAt())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class ConvulsionResponseDTO {
        private Long petId;
        private String state;
        private List<String> abnormalState;
        private LocalDateTime createdAt;

        public static ConvulsionResponseDTO convulsionToDTO(Convulsion convulsion) {
            return ConvulsionResponseDTO.builder()
                    .petId(convulsion.getPet().getId())
                    .state(convulsion.getState().name())
                    .abnormalState(convulsion.getAbnormalState().stream()
                            .map(AbnormalState::name)
                            .toList())
                    .createdAt(convulsion.getCreatedAt())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class SoundResponseDTO {
        private Long petId;
        private String title;
        private LocalDateTime createdAt;

        public static SoundResponseDTO soundToDTO(Sound sound) {
            return SoundResponseDTO.builder()
                    .petId(sound.getPet().getId())
                    .title(sound.getTitle())
                    .createdAt(sound.getCreatedAt())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class SnotResponseDTO {
        private Long petId;
        private String state;
        private LocalDateTime createdAt;

        public static SnotResponseDTO snotToDTO(Snot snot) {
            return SnotResponseDTO.builder()
                    .petId(snot.getPet().getId())
                    .state(snot.getState().name())
                    .createdAt(snot.getCreatedAt())
                    .build();
        }
    }
}
