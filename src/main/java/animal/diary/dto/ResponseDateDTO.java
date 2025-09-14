package animal.diary.dto;

import animal.diary.entity.record.*;
import animal.diary.entity.record.state.AbnormalState;
import animal.diary.entity.record.state.BinaryState;
import animal.diary.entity.record.state.LevelState;
import com.fasterxml.jackson.annotation.JsonFormat;
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
        @Schema(description = "일기 ID", example = "1")
        private Long diaryId;
        @Schema(description = "제목", example = "5.0kg")
        private String title;
        @Schema(description = "몸무게", example = "5.0")
        private String weight;
        @Schema(type = "string", example = "14:30", description = "기록 시간 (HH:mm)")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        private LocalTime createdTime;

        public static WeightResponse weightToDTO(Weight weight) {
            String title;
            Float weightValue = weight.getWeight();
            
            String formattedWeight;
            // 정수인 경우 소수점 한자리(.0), 아니면 원래 값 그대로
            if (weightValue.floatValue() == weightValue.intValue()) {
                title = String.format("%.1f", weightValue) + "kg";
                formattedWeight = String.format("%.1f", weightValue);
            } else {
                title = weightValue + "kg";
                formattedWeight = weightValue.toString();
            }
            
            return WeightResponse.builder()
                    .diaryId(weight.getId())
                    .title(title)
                    .weight(formattedWeight)
                    .createdTime(weight.getCreatedAt().toLocalTime())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class StateResponse {
        @Schema(description = "일기 ID", example = "1")
        private Long diaryId;
        @Schema(description = "상태", example = "HIGH")
        private String state;
        @Schema(type = "string", example = "14:30", description = "기록 시간 (HH:mm)")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
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
        @Schema(description = "일기 ID", example = "1")
        private Long diaryId;
        @Schema(description = "제목", example = "20회/60초")
        private String title;
        @Schema(description = "호흡수/심박수", example = "20")
        private Integer count;
        @Schema(type = "string", example = "14:30", description = "기록 시간 (HH:mm)")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        private LocalTime createdTime;

        public static CountResponse respiratoryRateToDTO(RespiratoryRate respiratoryRate) {
            return CountResponse.builder()
                    .diaryId(respiratoryRate.getId())
                    .title(respiratoryRate.getCount() + "회/60초")
                    .count(respiratoryRate.getCount())
                    .createdTime(respiratoryRate.getCreatedAt().toLocalTime())
                    .build();
        }

        public static CountResponse heartRateToDTO(HeartRate heartRate) {
            return CountResponse.builder()
                    .diaryId(heartRate.getId())
                    .title(heartRate.getCount() + "회/60초")
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
        @Schema(type = "string", example = "14:30", description = "기록 시간 (HH:mm)")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
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
        @Schema(description = "일기 ID", example = "1")
        private Long diaryId;
        @Schema(description = "제목", example = "산책 중 강아지를 만남")
        private String title;
        @Schema(description = "내용", example = "오늘 산책 중에 다른 강아지를 만났어요. 매우 즐거워했답니다.")
        private String content;
        @Schema(description = "이미지 URL 목록", example = "[\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"]")
        private List<String> imageUrls;
        @Schema(description = "비디오 URL", example = "https://example.com/video.mp4")
        private String videoUrl;
        @Schema(type = "string", example = "14:30", description = "기록 시간 (HH:mm)")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
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
        @Schema(description = "일기 ID", example = "1")
        private Long diaryId;
        @Schema(description = "제목", example = "경련 O")
        private String title;
        @Schema(description = "경련 상태 (O, X)", example = "O")
        private String state;
        // 배변실수, 침흘림, 의식없음, 추가 증상 없음
        @Schema(description = "이상 행동 상태 목록", example = "[\"침흘림\", \"배변실수\", \"의식없음\", \"추가 증상 없음\"]")
        private List<String> abnormalStates;
        @Schema(description = "비디오 URL", example = "https://example.com/video.mp4")
        private String videoUrl;
        @Schema(type = "string", example = "14:30", description = "기록 시간 (HH:mm)")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        private LocalTime createdTime;

        public static ConvulsionResponse convulsionToDTO(Convulsion convulsion, String videoUrl) {
            return ConvulsionResponse.builder()
                    .diaryId(convulsion.getId())
                    .title(convulsion.getState() == BinaryState.O ? "경련 O" : "경련 X")
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
        @Schema(description = "일기 ID", example = "1")
        private Long diaryId;
        @Schema(description = "제목", example = "제목1")
        private String title;
        @Schema(description = "소리 파일 URL", example = "https://example.com/sound.mp4")
        private String soundUrl;
        @Schema(type = "string", example = "14:30", description = "기록 시간 (HH:mm)")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
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
        @Schema(description = "일기 ID", example = "1")
        private Long diaryId;
        // 맑음, 탁함, 피섞임
        @Schema(description = "제목", example = "탁함")
        private String title;
        @Schema(description = "콧물 상태 (CLEAR, CLOUDY, BLOODY)", example = "CLOUDY")
        private String state;
        @Schema(description = "메모", example = "아침부터 콧물이 심해요")
        private String memo;
        @Schema(description = "이미지 URL 목록", example = "[\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"]")
        private List<String> imageUrls;
        @Schema(type = "string", example = "14:30", description = "기록 시간 (HH:mm)")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        private LocalTime createdTime;

        public static SnotResponse snotToDTO(Snot snot, List<String> imageUrls) {
            return SnotResponse.builder()
                    .diaryId(snot.getId())
                    .title(snot.getState().name())
                    .state(snot.getState().name())
                    .memo(snot.getMemo())
                    .imageUrls(imageUrls != null ? imageUrls : List.of())
                    .createdTime(snot.getCreatedAt().toLocalTime())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class VomitingResponse {
        @Schema(description = "일기 ID", example = "1")
        private Long diaryId;
        @Schema(description = "제목", example = "구토 O")
        private String title;
        @Schema(description = "구토 상태 (O, X)", example = "O")
        private String state;
        @Schema(description = "이미지 URL 목록", example = "[\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"]")
        private List<String> imageUrls;
        @Schema(type = "string", example = "14:30", description = "기록 시간 (HH:mm)")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        private LocalTime createdTime;

        public static VomitingResponse vomitingToDTO(Vomiting vomiting, List<String> imageUrls) {
            return VomitingResponse.builder()
                    .diaryId(vomiting.getId())
                    .title(vomiting.getState() == BinaryState.O ? "구토 O" : "구토 X")
                    .state(vomiting.getState().name())
                    .imageUrls(imageUrls != null ? imageUrls : List.of())
                    .createdTime(vomiting.getCreatedAt().toLocalTime())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class WalkingResponse {
        @Schema(description = "일기 ID", example = "1")
        private Long diaryId;
        @Schema(description = "제목", example = "제목1")
        private String title;
        @Schema(description = "걷는 영상 URL", example = "https://example.com/walking.mp4")
        private String videoUrl;
        @Schema(type = "string", example = "14:30", description = "기록 시간 (HH:mm)")
        private LocalTime createdTime;

        public static WalkingResponse walkingToDTO(Walking walking, String videoUrl) {
            return WalkingResponse.builder()
                    .diaryId(walking.getId())
                    .title(walking.getTitle())
                    .videoUrl(videoUrl)
                    .createdTime(walking.getCreatedAt().toLocalTime())
                    .build();
        }

        // Walking to DTO

    }

    @Builder
    @Getter
    public static class WaterResponse {
        @Schema(description = "일기 ID", example = "1")
        private Long diaryId;
        // LOW(적음), NORMAL(보통), HIGH(많음)
        @Schema(description = "제목", example = "적음")
        private String title;
        @Schema(description = "물 섭취량 상태 (LOW, NORMAL, HIGH)", example = "NORMAL")
        private String state;
        @Schema(type = "string", example = "14:30", description = "기록 시간 (HH:mm)")
        private LocalTime createdTime;

        public static WaterResponse waterToDTO(Water water) {
            // LOW(적음), NORMAL(보통), HIGH(많음)
            String title = switch (water.getState()) {
                case LOW -> "적음";
                case NORMAL -> "보통";
                case HIGH -> "많음";
                default -> "";
            };

            return WaterResponse.builder()
                    .diaryId(water.getId())
                    .title(title)
                    .state(water.getState().name())
                    .createdTime(water.getCreatedAt().toLocalTime())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class SkinResponse {
        @Schema(description = "일기 ID", example = "1")
        private Long diaryId;
        // 0단계, 1단계, 2단계, 3단계
        @Schema(description = "제목", example = "1단계")
        private String title;
        @Schema(description = "피부 상태 (0,1,2,3)", example = "1")
        private String state;
        @Schema(description = "메모", example = "털이 많이 빠지고 있음")
        private String memo;
        @Schema(description = "이미지 URL 목록", example = "[\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"]")
        private List<String> imageUrls;
        @Schema(type = "string", example = "14:30", description = "기록 시간 (HH:mm)")
        private LocalTime createdTime;

        public static SkinResponse skinToDTO(Skin skin, List<String> imageUrls) {
            String title = skin.getState() + "단계";

            return SkinResponse.builder()
                    .diaryId(skin.getId())
                    .title(title)
                    .state(skin.getState().name())
                    .memo(skin.getMemo() != null ? skin.getMemo() : null)
                    .imageUrls(imageUrls != null ? imageUrls : List.of())
                    .createdTime(skin.getCreatedAt().toLocalTime())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class DefecationResponse {
        @Schema(description = "일기 ID", example = "1")
        private Long diaryId;
        @Schema(description = "제목", example = "보통")
        private String title;
        @Schema(description = "배변량 상태 (NONE, LOW, NORMAL, HIGH)", example = "NORMAL")
        private String level;
        @Schema(description = "대변 상태 (NORMAL, DIARRHEA, BLACK, BLOODY, ETC)", example = "NORMAL")
        private String state;
        @Schema(description = "메모", example = "평소보다 단단한 편")
        private String memo;
        @Schema(description = "이미지 URL 목록", example = "[\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"]")
        private List<String> imageUrls;
        @Schema(type = "string", example = "14:30", description = "기록 시간 (HH:mm)")
        private LocalTime createdTime;

        public static DefecationResponse defecationToDTO(Defecation defecation, List<String> imageUrls) {
            // 배변량에 따른 제목 설정
            String title = switch (defecation.getLevel()) {
                case NONE -> "없음";
                case LOW -> "적음";
                case NORMAL -> "보통";
                case HIGH -> "많음";
            };

            return DefecationResponse.builder()
                    .diaryId(defecation.getId())
                    .title(title)
                    .level(defecation.getLevel().name())
                    .state(defecation.getState() != null ? defecation.getState().name() : null)
                    .memo(defecation.getMemo())
                    .imageUrls(imageUrls != null ? imageUrls : List.of())
                    .createdTime(defecation.getCreatedAt().toLocalTime())
                    .build();
        }
    }
}