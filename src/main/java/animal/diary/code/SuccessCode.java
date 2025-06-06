package animal.diary.code;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum SuccessCode {
    /**
     * User
     */
    SUCCESS_REISSUE(HttpStatus.OK, "토큰 재발급을 성공했습니다. 헤더 토큰을 확인하세요."),
    SUCCESS_REGISTER(HttpStatus.OK, "회원가입을 성공했습니다."),
    SUCCESS_LOGIN(HttpStatus.OK, "로그인을 성공했습니다. 헤더 토큰을 확인하세요."),
    SUCCESS_LOGOUT(HttpStatus.OK, "성공적으로 로그아웃했습니다."),

    /**
     * Pet
     */
    SUCCESS_SAVE_PET(HttpStatus.CREATED, "반려동물 등록이 완료되었습니다."),
    SUCCESS_GET_PET_LIST(HttpStatus.OK, "나의 반려동물 정보를 성공적으로 불러왔습니다."),
    SUCCESS_SAVE_MESSAGE(HttpStatus.OK, "메시지가 저장되었습니다."),
    SUCCESS_SAVE_SELECT_STRATEGY(HttpStatus.OK, "전략을 선택하였습니다."),
    SUCCESS_SAVE_STRATEGY_EFFECT(HttpStatus.OK, "전략에 대한 효과를 저장했습니다"),
    SUCCESS_SAVE_RECOMMEND_STRATEGY(HttpStatus.OK, "추천한 전략을 저장했습니다."),

    /**
     * record
     * */
    SUCCESS_SAVE_WEIGHT(HttpStatus.CREATED, "몸무게를 성공적으로 등록했습니다."),


    ;
    private final HttpStatus status;
    private final String message;
}