package animal.diary.dto;

import java.time.LocalTime;

public interface DiaryDateResponse {
    Long getDiaryId();
    LocalTime getCreatedTime();

}
