package animal.diary.controller;

import animal.diary.code.SuccessCode;
import animal.diary.dto.RecordNumberDTO;
import animal.diary.dto.RecordResponseDTO;
import animal.diary.dto.response.ResponseDTO;
import animal.diary.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
