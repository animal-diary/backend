package animal.diary.dto;

/**
 * 소변 상태 기록을 위한 validation group
 * 필수 필드: petId, urineState, urineAmount
 * urineState 가능한 값: BLOODY, LIGHT, DARK, NORMAL
 * urineAmount 가능한 값: NONE, LOW, NORMAL, HIGH
 * urineSmallState: O(있음), X(없음)
 */
public interface UrineGroup {
}