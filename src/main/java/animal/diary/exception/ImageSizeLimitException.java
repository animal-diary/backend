package animal.diary.exception;

import animal.diary.code.ErrorCode;
import lombok.Getter;

@Getter
public class ImageSizeLimitException extends RuntimeException {
    private final ErrorCode errorCode;

    public ImageSizeLimitException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
