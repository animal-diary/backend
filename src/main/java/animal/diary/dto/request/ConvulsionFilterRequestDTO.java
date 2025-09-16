package animal.diary.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "경련 기록 필터링 요청 DTO")
public class ConvulsionFilterRequestDTO {
    // binaryState (경련 유무)
    @Schema(description = "경련 유무 (O, X)", example = "O", nullable = true)
    private String binaryState;

    // abnormalState (이상 행동 선택 (복수 선택 가능, 리스트 X 쉼표 구분)
    //    INCONTINENCE("배변실수"),
    //    DROOLING("침흘림"),
    //    UNCONSCIOUS("의식없음"),
    //    NORMAL("추가 증상 없음");
    @Schema(description = "이상 행동 선택 (INCONTINENCE, DROOLING, UNCONSCIOUS, NORMAL) (복수 선택 가능, 쉼표로 구분)", example = "INCONTINENCE,DROOLING", nullable = true)
    private String abnormalState;


    // withVideo (영상 유무)
    @Schema(description = "영상 유무 (O, X)", example = "O", nullable = true)
    private String withVideo;

}
