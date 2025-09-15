package animal.diary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Pet ID 검증과 Swagger API 응답을 모두 포함하는 편리한 조합 어노테이션
 *
 * 사용법:
 * @ValidatePetWithApiResponse
 * public ResponseEntity<...> someMethod(@PathVariable Long petId) { ... }
 *
 * 또는 클래스 레벨에서:
 * @ValidatePetWithApiResponse
 * @RestController
 * public class SomeController { ... }
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ValidatePetId
@PetIdApiResponses
public @interface ValidatePetWithApiResponse {

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