package animal.diary.controller;

import animal.diary.code.SuccessCode;
import animal.diary.dto.RecordNumberDTO;
import animal.diary.dto.RecordResponseDTO;
import animal.diary.dto.RequestDateDTO;
import animal.diary.dto.ResponseDateListDTO;
import animal.diary.dto.response.ResponseDTO;
import animal.diary.service.RecordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/record")
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;

    @PostMapping("/weight")
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordWeight(@RequestBody RecordNumberDTO dto) {
        RecordResponseDTO result = recordService.recordWeight(dto);

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_WEIGHT.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_WEIGHT, result));
    }

    @PostMapping("/weight/date")
    public ResponseEntity<ResponseDTO<ResponseDateListDTO>> getWeightsByDate(@RequestBody RequestDateDTO dto) {
        ResponseDateListDTO result = recordService.getWeightsByDate(dto);

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_WEIGHT_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_WEIGHT_BY_DATE, result));
    }

    
    // 기력 상태
    @PostMapping("/energy")
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordEnergy(@RequestBody RecordNumberDTO dto) {
        RecordResponseDTO result = recordService.recordEnergyAndAppetite(dto, "energy");

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_ENERGY.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_ENERGY, result));
    }

    @PostMapping("/energy/date")
    public ResponseEntity<ResponseDTO<ResponseDateListDTO>> getEnergyByDate(@RequestBody RequestDateDTO dto) {
        ResponseDateListDTO result = recordService.getEnergyOrAppetiteByDate(dto, "energy");

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_ENERGY_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_ENERGY_BY_DATE, result));
    }

    // 식욕 상태
    @PostMapping("/appetite")
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordAppetite(@RequestBody RecordNumberDTO dto) {
        RecordResponseDTO result = recordService.recordEnergyAndAppetite(dto, "appetite");

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_APPETITE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_APPETITE, result));
    }

    @PostMapping("/appetite/date")
    public ResponseEntity<ResponseDTO<ResponseDateListDTO>> getAppetiteByDate(@RequestBody RequestDateDTO dto) {
        ResponseDateListDTO result = recordService.getEnergyOrAppetiteByDate(dto, "appetite");

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_APPETITE_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_APPETITE_BY_DATE, result));
    }

    // 특이사항
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordSignificant(
            @Valid @RequestPart("dto") RecordNumberDTO dto) {
        //,@RequestPart(value = "files", required = false) List<MultipartFile> files) throws IOException {

        RecordResponseDTO result = recordService.recordSignificant(dto);//, files);

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_SIGNIFICANT.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_SIGNIFICANT, result));
    }
}
