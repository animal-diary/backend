package animal.diary.annotation;

import animal.diary.dto.response.ErrorResponseDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Pet ID 관련 공통 Swagger API 응답을 자동으로 추가하는 메타 어노테이션
 * @ValidatePetId와 함께 사용하면 404 Pet Not Found 응답이 자동으로 Swagger에 추가됩니다.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "404",
                description = "반려동물 정보 없음 - 존재하지 않는 Pet ID",
                content = { @io.swagger.v3.oas.annotations.media.Content(
                        mediaType = "application/json",
                        schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDTO.class),
                        examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                name = "Pet Not Found",
                                value = """
                                        {
                                          "status": 404,
                                          "code": "PET_NOT_FOUND",
                                          "message": "반려동물을 찾을 수 없습니다. (ID: 999)",
                                          "data": null
                                        }
                                        """
                        )
                )}
        ),
        @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 - 유효하지 않은 Pet ID 형식",
                content = { @io.swagger.v3.oas.annotations.media.Content(
                        mediaType = "application/json",
                        schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDTO.class),
                        examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                name = "Invalid Pet ID",
                                value = """
                                        {
                                          "status": 400,
                                          "code": "BAD_REQUEST",
                                          "message": "Pet ID는 양수여야 합니다.",
                                          "data": null
                                        }
                                        """
                        )
                )}
        )
})
public @interface PetIdApiResponses {
}