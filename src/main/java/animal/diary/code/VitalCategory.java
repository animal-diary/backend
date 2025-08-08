package animal.diary.code;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "바이탈 사인 카테고리")
public enum VitalCategory {
    @Schema(description = "호흡수")
    RR,
    
    @Schema(description = "심박수") 
    HEART_RATE;

    /**
     * 문자열에서 VitalCategory로 변환하는 메서드
     * @param value 변환할 문자열 값
     * @return 해당하는 VitalCategory enum 값
     * @throws IllegalArgumentException 알 수 없는 카테고리인 경우
     */
    public static VitalCategory from(String value) {
        return switch (value.toLowerCase()) {
            case "rr" -> RR;
            case "heart-rate" -> HEART_RATE;
            default -> throw new IllegalArgumentException("Unknown category: " + value);
        };
    }
}