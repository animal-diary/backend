package animal.diary.exception;

public class InvalidGenderException extends RuntimeException{
    public InvalidGenderException(String message) {
        super(message);
    }
}
