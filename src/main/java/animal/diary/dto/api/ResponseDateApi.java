package animal.diary.dto.api;

import animal.diary.code.SuccessCode;
import animal.diary.dto.RecordResponseDTO;
import animal.diary.dto.ResponseDateDTO;
import animal.diary.dto.ResponseDateListDTO;
import animal.diary.dto.response.ResponseDTO;

public class ResponseDateApi extends ResponseDTO<ResponseDateListDTO<?>> {

    public ResponseDateApi(SuccessCode successCode, ResponseDateListDTO<?> data) {
        super(successCode, data);
    }

    public static class WeightDateResponseApi extends ResponseDTO<ResponseDateListDTO<ResponseDateDTO.WeightResponse>> {
        public WeightDateResponseApi(SuccessCode successCode, ResponseDateListDTO<ResponseDateDTO.WeightResponse> data) {
            super(successCode, data);
        }
    }

    public static class EnergyAndAppetiteAndSyncopeDateResponseApi extends ResponseDTO<ResponseDateListDTO<ResponseDateDTO.StateResponse>> {
        public EnergyAndAppetiteAndSyncopeDateResponseApi(SuccessCode successCode, ResponseDateListDTO<ResponseDateDTO.StateResponse> data) {
            super(successCode, data);
        }
    }

    public static class RRAndHeartRateDateResponseApi extends ResponseDTO<ResponseDateListDTO<ResponseDateDTO.CountResponse>> {
        public RRAndHeartRateDateResponseApi(SuccessCode successCode, ResponseDateListDTO<ResponseDateDTO.CountResponse> data) {
            super(successCode, data);
        }
    }

    public static class UrinaryDateResponseApi extends ResponseDTO<ResponseDateListDTO<ResponseDateDTO.UrinaryResponse>> {
        public UrinaryDateResponseApi(SuccessCode successCode, ResponseDateListDTO<ResponseDateDTO.UrinaryResponse> data) {
            super(successCode, data);
        }
    }

    public static class SignificantDateResponseApi extends ResponseDTO<ResponseDateListDTO<ResponseDateDTO.SignificantResponse>> {
        public SignificantDateResponseApi(SuccessCode successCode, ResponseDateListDTO<ResponseDateDTO.SignificantResponse> data) {
            super(successCode, data);
        }
    }

    public static class ConvulsionDateResponseApi extends ResponseDTO<ResponseDateListDTO<ResponseDateDTO.ConvulsionResponse>> {
        public ConvulsionDateResponseApi(SuccessCode successCode, ResponseDateListDTO<ResponseDateDTO.ConvulsionResponse> data) {
            super(successCode, data);
        }
    }

    public static class SoundDateResponseApi extends ResponseDTO<ResponseDateListDTO<ResponseDateDTO.SoundResponse>> {
        public SoundDateResponseApi(SuccessCode successCode, ResponseDateListDTO<ResponseDateDTO.SoundResponse> data) {
            super(successCode, data);
        }
    }

    public static class SnotDateResponseApi extends ResponseDTO<ResponseDateListDTO<ResponseDateDTO.SnotResponse>> {
        public SnotDateResponseApi(SuccessCode successCode, ResponseDateListDTO<ResponseDateDTO.SnotResponse> data) {
            super(successCode, data);
        }
    }

    public static class VomitingDateResponseApi extends ResponseDTO<ResponseDateListDTO<ResponseDateDTO.VomitingResponse>> {
        public VomitingDateResponseApi(SuccessCode successCode, ResponseDateListDTO<ResponseDateDTO.VomitingResponse> data) {
            super(successCode, data);
        }
    }

    public class WalkingDateResponseApi extends ResponseDTO<ResponseDateListDTO<RecordResponseDTO.WalkingResponseDTO>> {
        public WalkingDateResponseApi(SuccessCode successCode, ResponseDateListDTO<RecordResponseDTO.WalkingResponseDTO> data) {
            super(successCode, data);
        }
    }
}
