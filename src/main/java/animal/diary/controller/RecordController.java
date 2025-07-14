package animal.diary.controller;

import animal.diary.code.SuccessCode;
import animal.diary.dto.*;
import animal.diary.dto.response.ResponseDTO;
import animal.diary.service.RecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/record")
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;

    @PostMapping("/weight")
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordWeight(@Validated(WeightGroup.class) @RequestBody RecordNumberDTO dto) {
        RecordResponseDTO result = recordService.recordWeight(dto);

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_WEIGHT.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_WEIGHT, result));
    }

    @PostMapping("/weight/date")
    public ResponseEntity<ResponseDTO<ResponseDateListDTO>> getWeightsByDate(@Valid @RequestBody RequestDateDTO dto) {
        ResponseDateListDTO result = recordService.getWeightsByDate(dto);

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_WEIGHT_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_WEIGHT_BY_DATE, result));
    }

    
    // 기력 상태
    @PostMapping("/energy")
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordEnergy(@Validated(StateGroup.class)@RequestBody RecordNumberDTO dto) {
        RecordResponseDTO result = recordService.recordEnergyAndAppetite(dto, "energy");

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_ENERGY.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_ENERGY, result));
    }

    @PostMapping("/energy/date")
    public ResponseEntity<ResponseDTO<ResponseDateListDTO>> getEnergyByDate(@Valid @RequestBody RequestDateDTO dto) {
        ResponseDateListDTO result = recordService.getEnergyOrAppetiteByDate(dto, "energy");

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_ENERGY_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_ENERGY_BY_DATE, result));
    }

    // 식욕 상태
    @PostMapping("/appetite")
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordAppetite(@Validated(StateGroup.class) @RequestBody RecordNumberDTO dto) {
        RecordResponseDTO result = recordService.recordEnergyAndAppetite(dto, "appetite");

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_APPETITE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_APPETITE, result));
    }

    @PostMapping("/appetite/date")
    public ResponseEntity<ResponseDTO<ResponseDateListDTO>> getAppetiteByDate(@Valid @RequestBody RequestDateDTO dto) {
        ResponseDateListDTO result = recordService.getEnergyOrAppetiteByDate(dto, "appetite");

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_APPETITE_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_APPETITE_BY_DATE, result));
    }


    // 호흡 수
    @PostMapping("/RR")
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordRR(@Validated(CountGroup.class) @RequestBody RecordNumberDTO dto) {
        RecordResponseDTO result = recordService.recordRRAndHeartRate(dto, "RR");

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_RR.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_RR, result));
    }

    @PostMapping("/RR/date")
    public ResponseEntity<ResponseDTO<ResponseDateListDTO>> getRRByDate(@Valid @RequestBody RequestDateDTO dto) {
        ResponseDateListDTO result = recordService.getRRByDate(dto);

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_RR_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RR_BY_DATE, result));
    }

    // 심박수
    @PostMapping("/heart-rate")
    public ResponseEntity<ResponseDTO<RecordResponseDTO>> recordHeartRate(@Validated(CountGroup.class) @RequestBody RecordNumberDTO dto) {
        RecordResponseDTO result = recordService.recordRRAndHeartRate(dto, "heart-rate");

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_RR.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_RR, result));
    }

    @PostMapping("/heart-rate/date")
    public ResponseEntity<ResponseDTO<ResponseDateListDTO>> getHeartRateByDate(@Valid @RequestBody RequestDateDTO dto) {
        ResponseDateListDTO result = recordService.getRRByDate(dto);

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_RR_BY_DATE.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_RR_BY_DATE, result));
    }

}
