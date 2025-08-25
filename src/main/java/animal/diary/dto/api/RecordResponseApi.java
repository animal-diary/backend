package animal.diary.dto.api;

import animal.diary.code.SuccessCode;
import animal.diary.dto.RecordResponseDTO;
import animal.diary.dto.response.ResponseDTO;
import lombok.Getter;

@Getter
public class RecordResponseApi extends ResponseDTO<RecordResponseDTO> {
    public RecordResponseApi(SuccessCode successCode, RecordResponseDTO data) {
        super(successCode, data);
    }

    public static class WeightResponseApi extends ResponseDTO<RecordResponseDTO.WeightResponseDTO> {
        public WeightResponseApi(SuccessCode successCode, RecordResponseDTO.WeightResponseDTO data) {
            super(successCode, data);
        }
    }

    public static class EnergyAndAppetiteResponseApi extends ResponseDTO<RecordResponseDTO.EnergyAndAppetiteResponseDTO> {
        public EnergyAndAppetiteResponseApi(SuccessCode successCode, RecordResponseDTO.EnergyAndAppetiteResponseDTO data) {
            super(successCode, data);
        }
    }

    public static class RRAndHeartRateResponseApi extends ResponseDTO<RecordResponseDTO.RRAndHeartRateResponseDTO> {
        public RRAndHeartRateResponseApi(SuccessCode successCode, RecordResponseDTO.RRAndHeartRateResponseDTO data) {
            super(successCode, data);
        }
    }

    public static class SyncopeResponseApi extends ResponseDTO<RecordResponseDTO.SyncopeResponseDTO> {
        public SyncopeResponseApi(SuccessCode successCode, RecordResponseDTO.SyncopeResponseDTO data) {
            super(successCode, data);
        }
    }

    public static class UrinaryRecordResponseApi extends ResponseDTO<RecordResponseDTO.UrinaryResponseDTO> {
        public UrinaryRecordResponseApi(SuccessCode successCode, RecordResponseDTO.UrinaryResponseDTO data) {
            super(successCode, data);
        }
    }

    public static class SignificantResponseApi extends ResponseDTO<RecordResponseDTO.SignificantResponseDTO> {
        public SignificantResponseApi(SuccessCode successCode, RecordResponseDTO.SignificantResponseDTO data) {
            super(successCode, data);
        }
    }

    public static class ConvulsionResponseApi extends ResponseDTO<RecordResponseDTO.ConvulsionResponseDTO> {
        public ConvulsionResponseApi(SuccessCode successCode, RecordResponseDTO.ConvulsionResponseDTO data) {
            super(successCode, data);
        }
    }

    public static class SoundResponseApi extends ResponseDTO<RecordResponseDTO.SoundResponseDTO> {
        public SoundResponseApi(SuccessCode successCode, RecordResponseDTO.SoundResponseDTO data) {
            super(successCode, data);
        }
    }

    public static class SnotResponseApi extends ResponseDTO<RecordResponseDTO.SnotResponseDTO> {
        public SnotResponseApi(SuccessCode successCode, RecordResponseDTO.SnotResponseDTO data) {
            super(successCode, data);
        }
    }
}
