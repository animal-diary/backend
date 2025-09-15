package animal.diary.controller;

import animal.diary.code.SuccessCode;
import animal.diary.dto.GetMyPetInfoDTO;
import animal.diary.dto.PetRegisterDTO;
import animal.diary.dto.PetRegisterResponseDTO;
import animal.diary.dto.response.ErrorResponseDTO;
import animal.diary.dto.response.ResponseDTO;
import animal.diary.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/pets")
@RequiredArgsConstructor
@Tag(name = "Pet Controller", description = "반려동물 관련 API")
public class PetController {
    private final PetService petService;

    /**
     * 반려동물 등록 API
     * @param dto 반려동물 등록 정보
     * @return 등록된 반려동물 정보
     */
    @Operation(summary = "반려동물 등록 API", description = "반려동물을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "반려동물 등록 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PetRegisterResponseDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = {
                    @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                    @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDTO.class)
                    )
            })
    })
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<PetRegisterResponseDTO>> registerPet(@Valid @RequestBody PetRegisterDTO dto) {
        log.info("Received pet registration request for user ID: {}", dto.getUserId());
        PetRegisterResponseDTO result = petService.registerPet(dto);
        log.info("Pet registration completed successfully for user ID: {}", dto.getUserId());

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_PET.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_PET, result));
    }

    // 이후 userId 없앨 것
    /**
     * 특정 사용자의 반려동물 정보 조회 API
     * @param userId 사용자 ID
     * @return 사용자의 반려동물 정보 리스트
     */
    @Operation(summary = "사용자 반려동물 정보 조회 API", description = "특정 사용자의 반려동물 정보를 조회합니다.")
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "반려동물 정보 조회 성공", content = {
                    @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = GetMyPetInfoDTO.class)
                    )
            }),
                    @ApiResponse(responseCode = "404", description = "사용자 또는 반려동물 정보 없음", content = {
                            @io.swagger.v3.oas.annotations.media.Content(
                                    mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDTO.class)
                            )
                    }),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = {
                            @io.swagger.v3.oas.annotations.media.Content(
                                    mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponseDTO.class)
                            )
                    })}
    )
    @GetMapping("/my/{userId}")
    public ResponseEntity<ResponseDTO<List<GetMyPetInfoDTO>>> getMyPetInfo(@PathVariable Long userId) {
        log.info("Received request to get pet info for user ID: {}", userId);
        List<GetMyPetInfoDTO> result = petService.getMyPetInfo(userId);
        log.info("Successfully retrieved pet info for user ID: {}", userId);

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_PET_LIST.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_PET_LIST, result));
    }

}
