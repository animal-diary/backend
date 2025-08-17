package animal.diary.exception;

import animal.diary.code.ErrorCode;
import animal.diary.code.SuccessCode;
import animal.diary.dto.response.ErrorResponseDTO;
import animal.diary.dto.response.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 입력값 검증
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.warn("Validation exception occurred: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity
                .status(ErrorCode.BAD_REQUEST.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.BAD_REQUEST, errors));
    }

    @ExceptionHandler(DiseaseInvalidException.class)
    protected ResponseEntity<ErrorResponseDTO> handleDiseaseInvalid(final DiseaseInvalidException e) {
        log.warn("Invalid disease exception: {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.DISEASE_INVALID.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.DISEASE_INVALID));
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ErrorResponseDTO> handleUserNotFound(final UserNotFoundException e) {
        log.warn("User not found exception: {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.USER_NOT_FOUND.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.USER_NOT_FOUND));
    }

    @ExceptionHandler(InvalidNeuteredException.class)
    protected ResponseEntity<ErrorResponseDTO> handleInvalidNeutered(final InvalidNeuteredException e) {
        return ResponseEntity
                .status(ErrorCode.NEUTERED_INVALID.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.NEUTERED_INVALID));
    }
    @ExceptionHandler(InvalidGenderException.class)
    protected ResponseEntity<ErrorResponseDTO> handleInvalidGender(final InvalidGenderException e) {
        return ResponseEntity
                .status(ErrorCode.GENDER_INVALID.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.GENDER_INVALID));
    }
    @ExceptionHandler(InvalidTypeException.class)
    protected ResponseEntity<ErrorResponseDTO> handleInvalidType(final InvalidTypeException e) {
        return ResponseEntity
                .status(ErrorCode.TYPE_INVALID.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.TYPE_INVALID));
    }
    @ExceptionHandler(InvalidHealthException.class)
    protected ResponseEntity<ErrorResponseDTO> handleInvalidHealth(final InvalidHealthException e) {
        return ResponseEntity
                .status(ErrorCode.HEALTH_INVALID.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.HEALTH_INVALID));
    }

    @ExceptionHandler(PetNotFoundException.class)
    protected ResponseEntity<ErrorResponseDTO> handlePetNotFound(final PetNotFoundException e) {
        log.warn("Pet not found exception: {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.PET_NOT_FOUND.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.PET_NOT_FOUND));
    }

    @ExceptionHandler(InvalidDateException.class)
    protected ResponseEntity<ErrorResponseDTO> handleInvalidDate(final InvalidDateException e) {
        log.warn("Invalid date exception: {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.DATE_INVALID.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.DATE_INVALID));
    }

    @ExceptionHandler(InvalidStateException.class)
    protected ResponseEntity<ErrorResponseDTO> handleInvalidState(final InvalidStateException e) {
        return ResponseEntity
                .status(ErrorCode.STATE_INVALID.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.STATE_INVALID));
    }

    @ExceptionHandler(EmptyListException.class)
    protected ResponseEntity<ResponseDTO<List<Object>>> handleEmptyList(final EmptyListException e) {
        log.info("Empty list result: {}", e.getMessage());
        return ResponseEntity
                .status(SuccessCode.SUCCESS_BUT_EMPTY.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_BUT_EMPTY, Collections.emptyList()));
    }
}
