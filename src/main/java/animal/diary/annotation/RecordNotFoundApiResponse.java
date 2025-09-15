package animal.diary.annotation;

import animal.diary.dto.response.ErrorResponseDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 기록을 찾을 수 없는 경우의 공통 Swagger API 응답을 자동으로 추가하는 어노테이션
 * 삭제 API에서 "기록을 찾을 수 없음" 404 응답을 자동으로 Swagger에 추가합니다.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "404",
                description = "기록 정보 없음 - 존재하지 않는 기록 ID",
                content = { @io.swagger.v3.oas.annotations.media.Content(
                        mediaType = "application/json",
                        schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDTO.class),
                        examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                name = "Record Not Found",
                                value = """
                                        {
                                          "status": 404,
                                          "error": "NOT_FOUND",
                                          "code": "DIARY_NOT_FOUND",
                                          "message": "해당 일지를 찾을 수 없습니다."
                                        }
                                        """
                        )
                )}
        )
})
public @interface RecordNotFoundApiResponse {
}