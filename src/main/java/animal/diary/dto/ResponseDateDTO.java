package animal.diary.dto;

import animal.diary.entity.record.*;
import animal.diary.entity.record.state.AbnormalState;
import animal.diary.entity.record.state.BinaryState;
import animal.diary.entity.record.state.LevelState;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Builder
@Getter
public class ResponseDateDTO {

    @Builder
    @Getter
    public static class WeightResponse {
        private Long diaryId;
        private String title;
        private Float weight;
        private LocalTime createdTime;

        public static WeightResponse weightToDTO(Weight weight) {
            String title;
            Float weightValue = weight.getWeight();
            
            // 정수인 경우 소수점 한자리(.0), 아니면 원래 값 그대로
            if (weightValue.floatValue() == weightValue.intValue()) {
                title = String.format("%.1f", weightValue) + "kg";
            } else {
                title = weightValue + "kg";
            }
            
            return WeightResponse.builder()
                    .diaryId(weight.getId())
                    .title(title)
                    .weight(weight.getWeight())
                    .createdTime(weight.getCreatedAt().toLocalTime())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class StateResponse {
        private Long diaryId;
        private String state;
        private LocalTime createdTime;

        public static StateResponse energyToDTO(Energy energy) {
            return StateResponse.builder()
                    .diaryId(energy.getId())
                    .state(energy.getState().name())
                    .createdTime(energy.getCreatedAt().toLocalTime())
                    .build();
        }

        public static StateResponse appetiteToDTO(Appetite appetite) {
            return StateResponse.builder()
                    .diaryId(appetite.getId())
                    .state(appetite.getState().name())
                    .createdTime(appetite.getCreatedAt().toLocalTime())
                    .build();
        }

        public static StateResponse syncopeToDTO(Syncope syncope) {
            return StateResponse.builder()
                    .diaryId(syncope.getId())
                    .state(syncope.getState().name())
                    .createdTime(syncope.getCreatedAt().toLocalTime())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class CountResponse {
        private Long diaryId;
        private Integer count;
        private LocalTime createdTime;

        public static CountResponse respiratoryRateToDTO(RespiratoryRate respiratoryRate) {
            return CountResponse.builder()
                    .diaryId(respiratoryRate.getId())
                    .count(respiratoryRate.getCount())
                    .createdTime(respiratoryRate.getCreatedAt().toLocalTime())
                    .build();
        }

        public static CountResponse heartRateToDTO(HeartRate heartRate) {
            return CountResponse.builder()
                    .diaryId(heartRate.getId())
                    .count(heartRate.getCount())
                    .createdTime(heartRate.getCreatedAt().toLocalTime())
                    .build();
        }
    }

    @Builder
    @Getter
    @Schema(description = "소변 기록 응답 DTO")
    public static class UrinaryResponse {
        @Schema(description = "일기 ID", example = "1")
        private Long diaryId;
        @Schema(description = "제목", example = "보통·보통·악취 없음")
        private String title;
        @Schema(description = "소변 상태 (BLOODY, LIGHT, DARK, NORMAL, ETC)", example = "NORMAL")
        private String urineState;
        @Schema(description = "소변량 (NONE, LOW, NORMAL, HIGH)", example = "NORMAL")
        private String urineAmount;
        @Schema(description = "메모", example = "소변이 자주 보임")
        private String memo;
        @Schema(description = "이미지 URL 목록", example = "[\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"]")
        private List<String> imageUrls;
        @Schema(description = "기록 시간", example = "14:30:00")
        private LocalTime createdTime;

        public static UrinaryResponse urinaryToDTO(Urinary urinary, List<String> imageUrls) {
            return UrinaryResponse.builder()
                    .diaryId(urinary.getId())
                    // 제목 1. 소변량이 NONE이라면 "무뇨"만 내보냄
                    // 제목 2. 소변량이 NONE이 아니라면 "{소변상태}·악취 있음(없음)·{소변량}" 형태로 내보냄ㅉ
                    .title(urinary.getOutput() == LevelState.NONE ? "무뇨" :
                            urinary.getState().getDescription()  + "·" +
                                    LevelState.toUrineString(urinary.getOutput()) + "·" +
                                    (urinary.getBinaryState() == BinaryState.O ? "악취 있음" : "악취 없음"))
                    .urineState(urinary.getState() != null ? urinary.getState().name() : null)
                    .urineAmount(urinary.getOutput() != null ? urinary.getOutput().name() : null)
                    .memo(urinary.getMemo() != null ? urinary.getMemo() : null)
                    .imageUrls(imageUrls != null ? imageUrls : List.of())
                    .createdTime(urinary.getCreatedAt().toLocalTime())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class SignificantResponse {
        private Long diaryId;
        private String title;
        private String content;
        private List<String> imageUrls;
        private String videoUrl;
        private LocalTime createdTime;

        public static SignificantResponse significantToDTO(Significant significant, List<String> imageCloudFrontUrls, String videoUrl) {
            return SignificantResponse.builder()
                    .diaryId(significant.getId())
                    .title(significant.getTitle())
                    .content(significant.getContent())
                    .imageUrls(imageCloudFrontUrls != null ? imageCloudFrontUrls : List.of())
                    .videoUrl(videoUrl)
                    .createdTime(significant.getCreatedAt().toLocalTime())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class ConvulsionResponse {
        private Long diaryId;
        private String title;
        private String state;
        private List<String> abnormalStates;
        private String videoUrl;
        private LocalTime createdTime;

        public static ConvulsionResponse convulsionToDTO(Convulsion convulsion, String videoUrl) {
            return ConvulsionResponse.builder()
                    .diaryId(convulsion.getId())
                    .title("경련 " + convulsion.getState().name())
                    .state(convulsion.getState().name())
                    .abnormalStates(convulsion.getAbnormalState() != null ? 
                            convulsion.getAbnormalState().stream()
                                    .map(AbnormalState::getDescription)
                                    .toList() : List.of())
                    .videoUrl(videoUrl)
                    .createdTime(convulsion.getCreatedAt().toLocalTime())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class SoundResponse{
        private Long diaryId;
        private String title;
        private String soundUrl;
        private LocalTime createdTime;

        public static SoundResponse soundToDTO(Sound sound, String soundUrl) {
            return SoundResponse.builder()
                    .diaryId(sound.getId())
                    .title(sound.getTitle())
                    .soundUrl(soundUrl)
                    .createdTime(sound.getCreatedAt().toLocalTime())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class SnotResponse {
        private Long diaryId;
        private String state;
        private String memo;
        private List<String> imageUrls;
        private LocalTime createdTime;

        public static SnotResponse snotToDTO(Snot snot, List<String> imageUrls) {
            return SnotResponse.builder()
                    .diaryId(snot.getId())
                    .state(snot.getState().name())
                    .memo(snot.getMemo())
                    .imageUrls(imageUrls != null ? imageUrls : List.of())
                    .createdTime(snot.getCreatedAt().toLocalTime())
                    .build();
        }
    }
}