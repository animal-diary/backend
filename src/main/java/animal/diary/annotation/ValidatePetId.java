package animal.diary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Pet ID 검증을 위한 커스텀 어노테이션
 * 메서드 파라미터에서 petId를 자동으로 검증하고 존재하지 않을 경우 PetNotFoundException을 발생시킵니다.
 * Swagger 응답에도 자동으로 404 Pet Not Found 응답을 추가합니다.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatePetId {

    /**
     * Pet ID 파라미터의 이름을 지정합니다.
     * 기본값은 "petId"입니다.
     */
    String paramName() default "petId";

    /**
     * 에러 메시지를 커스터마이징할 수 있습니다.
     * 기본값은 "반려동물을 찾을 수 없습니다."입니다.
     */
    String message() default "반려동물을 찾을 수 없습니다.";
}