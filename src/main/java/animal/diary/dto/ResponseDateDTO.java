package animal.diary.dto;

import animal.diary.entity.record.*;
import animal.diary.entity.record.state.AbnormalState;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ResponseDateDTO {

    @Builder
    @Getter
    public static class WeightResponse implements DiaryDateResponse {
        private Long diaryId;
        private Float weight;
        private LocalTime createdTime;

        public static WeightResponse weightToDTO(Weight weight) {
            return WeightResponse.builder()
                    .diaryId(weight.getId())
                    .weight(weight.getWeight())
                    .createdTime(weight.getCreatedAt().toLocalTime())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class StateResponse implements DiaryDateResponse {
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
    public static class CountResponse implements DiaryDateResponse{
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
    public static class UrinaryResponse implements DiaryDateResponse {
        private Long diaryId;
        private String urineState;
        private String urineAmount;
        private String memo;
        private List<String> imageUrls;
        private LocalTime createdTime;

        public static UrinaryResponse urinaryToDTO(Urinary urinary, List<String> imageUrls) {
            return UrinaryResponse.builder()
                    .diaryId(urinary.getId())
                    .urineState(urinary.getState() != null ? urinary.getState().name() : null)
                    .urineAmount(urinary.getOutput().name())
                    .memo(urinary.getMemo())
                    .imageUrls(imageUrls)
                    .createdTime(urinary.getCreatedAt().toLocalTime())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class SignificantResponse implements DiaryDateResponse{
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
    public static class ConvulsionResponse implements DiaryDateResponse{
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
    public static class SoundResponse implements DiaryDateResponse{
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
    public static class SnotResponse implements DiaryDateResponse{
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