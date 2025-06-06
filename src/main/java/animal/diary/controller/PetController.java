package animal.diary.controller;

import animal.diary.code.SuccessCode;
import animal.diary.dto.GetMyPetInfoDTO;
import animal.diary.dto.PetRegisterDTO;
import animal.diary.dto.PetRegisterResponseDTO;
import animal.diary.dto.response.ResponseDTO;
import animal.diary.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<PetRegisterResponseDTO>> registerPet(@Valid @RequestBody PetRegisterDTO dto) {
        PetRegisterResponseDTO result = petService.registerPet(dto);

        return ResponseEntity
                .status(SuccessCode.SUCCESS_SAVE_PET.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_SAVE_PET, result));
    }

    // 이후 userId 없앨 것
    @GetMapping("/my/{userId}")
    public ResponseEntity<ResponseDTO<List<GetMyPetInfoDTO>>> getMyPetInfo(@PathVariable Long userId) {
        List<GetMyPetInfoDTO> result = petService.getMyPetInfo(userId);

        return ResponseEntity
                .status(SuccessCode.SUCCESS_GET_PET_LIST.getStatus().value())
                .body(new ResponseDTO<>(SuccessCode.SUCCESS_GET_PET_LIST, result));
    }

}
