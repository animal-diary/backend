package animal.diary.dto.api;

import animal.diary.code.SuccessCode;
import animal.diary.dto.RecordResponseDTO;
import animal.diary.dto.response.FilterResponseDTO;
import animal.diary.dto.response.ResponseDTO;

public class FilterDataApi extends ResponseDTO<FilterResponseDTO> {
    public FilterDataApi(SuccessCode successCode, FilterResponseDTO data) {
        super(successCode, data);
    }
}
