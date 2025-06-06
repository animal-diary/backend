package animal.diary.controller;

import animal.diary.code.SuccessCode;
import animal.diary.dto.RecordNumberDTO;
import animal.diary.dto.RecordResponseDTO;
import animal.diary.dto.RequestWeightDateDTO;
import animal.diary.dto.ResponseWeightDateDTO;
import animal.diary.dto.response.ResponseDTO;
import animal.diary.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/record")
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;

    @PostMapping("/weight")
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordWeight(@RequestBody RecordNumberDTO dto) {
        RecordResponseDTO result = recordService.record(dto, "weight");

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_WEIGHT.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_WEIGHT, result));
    }

    @PostMapping("/weights/date")
    public ResponseEntity<ResponseDTO<ResponseWeightDateDTO>> getWeightsByDate(@RequestBody RequestWeightDateDTO dto) {
        ResponseWeightDateDTO result = recordService.getWeightsByDate(dto);

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_WEIGHT_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_WEIGHT_BY_DATE, result));
    }



}
