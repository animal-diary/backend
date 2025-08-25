package animal.diary.dto.response;

import animal.diary.code.SuccessCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "HTTP 응답 DTO")
public class ResponseDTO<T> {
    /**
     * HTTP 응답 상태 코드
     */
    @Schema(description = "HTTP 응답 상태 코드", example = "200 또는 201")
    private Integer status;
    @Schema(description = "응답 코드", example = "SUCCESS_REGISTER")
    private String code;
    @Schema(description = "응답 메시지", example = "성공 메시지")
    private String message;
    @Schema(description = "응답 데이터")
    private T data;

    public ResponseDTO(SuccessCode successCode, T data) {
        this.status = successCode.getStatus().value();
        this.code = successCode.name();
        this.message = successCode.getMessage();
        this.data = data;
    }
}