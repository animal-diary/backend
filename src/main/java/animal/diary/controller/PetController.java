package animal.diary.controller;

import animal.diary.code.SuccessCode;
import animal.diary.dto.PetRegisterDTO;
import animal.diary.dto.PetRegisterResponseDTO;
import animal.diary.dto.response.ResponseDTO;
import animal.diary.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pet")
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

}
