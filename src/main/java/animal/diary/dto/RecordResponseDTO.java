package animal.diary.dto;

import animal.diary.entity.record.*;
import animal.diary.entity.record.state.AbnormalState;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
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
        @Schema(description = "반려동물 ID", example = "1")
        private Long petId;
        @Schema(description = "몸무게", example = "5.5")
        private Float weight;
        @Schema(description = "몸무게 기록 생성 시간", example = "2023-10-01T12:00:00")
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
        @Schema(description = "반려동물 ID", example = "1")
        private Long petId;
        @Schema(description = "에너지/식욕 상태", example = "HIGH")
        private String state;
        @Schema(description = "에너지/식욕 기록 생성 시간", example = "2023-10-01T12:00:00")
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
        @Schema(description = "반려동물 ID", example = "1")
        private Long petId;
        @Schema(description = "호흡수/심박수", example = "20")
        private Integer count;
        @Schema(description = "호흡수/심박수 기록 생성 시간", example = "2023-10-01T12:00:00")
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
        @Schema(description = "반려동물 ID", example = "1")
        private Long petId;
        @Schema(description = "실신 상태", example = "O")
        private String state;
        @Schema(description = "실신 기록 생성 시간", example = "2023-10-01T12:00:00")
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
        @Schema(description = "반려동물 ID", example = "1")
        private Long petId;
        @Schema(description = "소변 상태 - 배뇨 여부", example = "O")
        private String binaryState;
        @Schema(description = "소변 상태 - 소변 상태", example = "NORMAL")
        private String urineState;
        @Schema(description = "소변 상태 - 소변 양", example = "LOW")
        private String urineAmount;
        @Schema(description = "소변 기록 생성 시간", example = "2023-10-01T12:00:00")
        private LocalDateTime createdAt;

        public static UrinaryResponseDTO urinaryToDTO(Urinary urinary) {
            return UrinaryResponseDTO.builder()
                    .petId(urinary.getPet().getId())
                    .binaryState(urinary.getBinaryState() != null ? urinary.getBinaryState().name() : null)
                    .urineState(urinary.getState() != null ? urinary.getState().name() : null)
                    .urineAmount(urinary.getOutput() != null ? urinary.getOutput().name() : null)
                    .createdAt(urinary.getCreatedAt())
                    .build();
        }
    }

    @Builder
    @Getter
    @Schema(description = "특이사항 응답 DTO")
    public static class SignificantResponseDTO {
        @Schema(description = "반려동물 ID", example = "1")
        private Long petId;
        @Schema(description = "특이사항 제목", example = "구토 증상 발생")
        private String title;
        @Schema(description = "특이사항 내용", example = "반려동물이 구토를 했습니다. 수의사 방문 필요.")
        private String content;
        @Schema(description = "특이사항 동영상 URL", example = "https://example.com/video.mp4")
        private String videoUrl;
        @Schema(description = "특이사항 이미지 URL 목록", example = "[\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"]")
        private List<String> imageUrls;
        @Schema(description = "특이사항 생성 시간", example = "2023-10-01T12:00:00")
        private LocalDateTime createdAt;

        public static SignificantResponseDTO significantToDTO(Significant significant) {
            return SignificantResponseDTO.builder()
                    .petId(significant.getPet().getId())
                    .title(significant.getTitle())
                    .content(significant.getContent())
                    .videoUrl(significant.getVideoUrl())
                    .imageUrls(significant.getImageUrls())
                    .createdAt(significant.getCreatedAt())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class ConvulsionResponseDTO {
        @Schema(description = "반려동물 ID", example = "1")
        private Long petId;
        @Schema(description = "경련 상태 O, X", example = "O")
        private String state;
        //    INCONTINENCE("배변실수"),
        //    DROOLING("침흘림"),
        //    UNCONSCIOUS("의식없음"),
        //    NORMAL("추가 증상 없음");
        @Schema(description = "경련 시 이상 상태 목록 INCONTINENCE(\"배변실수\"), DROOLING(\"침흘림\"), UNCONSCIOUS(\"의식없음\"), NORMAL(\"추가 증상 없음\")", example = "[\"INCONTINENCE\", \"DROOLING\"]")
        private List<String> abnormalState;
        @Schema(description = "경련 기록 생성 시간", example = "2023-10-01T12:00:00")
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
        @Schema(description = "반려동물 ID", example = "1")
        private Long petId;
        @Schema(description = "소리 제목", example = "기침 소리")
        private String title;
        @Schema(description = "소리 기록 생성 시간", example = "2023-10-01T12:00:00")
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
        @Schema(description = "반려동물 ID", example = "1")
        private Long petId;
        //CLEAR("맑음"), MUCUS("탁함"), BLOODY("피섞임");
        @Schema(description = "콧물 상태 CLEAR(\"맑음\"), MUCUS(\"탁함\"), BLOODY(\"피섞임\")", example = "CLEAR")
        private String state;
        @Schema(description = "콧물 기록 생성 시간", example = "2023-10-01T12:00:00")
        private LocalDateTime createdAt;

        public static SnotResponseDTO snotToDTO(Snot snot) {
            return SnotResponseDTO.builder()
                    .petId(snot.getPet().getId())
                    .state(snot.getState().name())
                    .createdAt(snot.getCreatedAt())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class VomitingResponseDTO {
        @Schema(description = "반려동물 ID", example = "1")
        private Long petId;
        @Schema(description = "구토 상태 O, X", example = "O")
        private String state;
        @Schema(description = "구토 이미지 URL 목록", example = "[\"https://example.com/vomiting1.jpg\", \"https://example.com/vomiting2.jpg\"]")
        private List<String> imageUrls;
        @Schema(description = "구토 기록 생성 시간", example = "2023-10-01T12:00:00")
        private LocalDateTime createdAt;

        public static VomitingResponseDTO vomitingToDTO(Vomiting vomiting) {
            return VomitingResponseDTO.builder()
                    .petId(vomiting.getPet().getId())
                    .state(vomiting.getState().name())
                    .imageUrls(vomiting.getImageUrls())
                    .createdAt(vomiting.getCreatedAt())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class WalkingResponseDTO {
        @Schema(description = "반려동물 ID", example = "1")
        private Long petId;
        @Schema(description = "걷는 모습 제목", example = "첫 번째 산책")
        private String title;
        @Schema(description = "걷는 모습 동영상 URL", example = "https://example.com/walking_video.mp4")
        private String videoUrl;
        @Schema(description = "걷는 모습 기록 생성 시간", example = "2023-10-01T12:00:00")
        private LocalDateTime createdAt;

        public static WalkingResponseDTO walkingToDTO(Walking walking) {
            return WalkingResponseDTO.builder()
                    .petId(walking.getPet().getId())
                    .title(walking.getTitle())
                    .videoUrl(walking.getImageUrl())
                    .createdAt(walking.getCreatedAt())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class WaterResponseDTO {
        @Schema(description = "반려동물 ID", example = "1")
        private Long petId;
        // NORMAL = 보통, LOW = 적음, HIGH = 많음
        @Schema(description = "물 섭취량 제목", example = "적음")
        private String title;
        @Schema(description = "물 섭취량 상태", example = "NORMAL")
        private String state;
        @Schema(description = "물 섭취량 기록 생성 시간", example = "2023-10-01T12:00:00")
        private LocalDateTime createdAt;

        public static WaterResponseDTO waterToDTO(Water water) {
            // NORMAL = 보통, LOW = 적음, HIGH = 많음 -> title
            // NONE은 존재하지 않음

            String title = switch (water.getState()) {
                case NORMAL -> "보통";
                case LOW -> "적음";
                case HIGH -> "많음";
                default -> null;
            };

            return WaterResponseDTO.builder()
                    .petId(water.getPet().getId())
                    .title(title)
                    .state(water.getState().name())
                    .createdAt(water.getCreatedAt())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class SkinResponseDTO {
        @Schema(description = "반려동물 ID", example = "1")
        private Long petId;
        // numberState
        @Schema(description = "피부 상태", example = "0")
        private String state;
        @Schema(description = "피부 상태 기록 생성 시간", example = "2023-10-01T12:00:00")
        private LocalDateTime createdAt;

        public static SkinResponseDTO skinToDTO(Skin skin) {
            return SkinResponseDTO.builder()
                    .petId(skin.getPet().getId())
                    .state(skin.getState().name())
                    .createdAt(skin.getCreatedAt())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class DefecationResponseDTO {
        @Schema(description = "반려동물 ID", example = "1")
        private Long petId;
        @Schema(description = "배변량 상태", example = "NORMAL")
        private String level;
        @Schema(description = "대변 상태", example = "NORMAL")
        private String state;
        @Schema(description = "메모", example = "평소보다 단단한 편")
        private String memo;
        @Schema(description = "이미지 URL 목록", example = "[\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"]")
        private List<String> imageUrls;
        @Schema(description = "배변 기록 생성 시간", example = "2023-10-01T12:00:00")
        private LocalDateTime createdAt;

        public static DefecationResponseDTO defecationToDTO(Defecation defecation) {
            return DefecationResponseDTO.builder()
                    .petId(defecation.getPet().getId())
                    .level(defecation.getLevel().name())
                    .state(defecation.getState() != null ? defecation.getState().name() : null)
                    .memo(defecation.getMemo())
                    .imageUrls(defecation.getImageUrls())
                    .createdAt(defecation.getCreatedAt())
                    .build();
        }
    }
}
