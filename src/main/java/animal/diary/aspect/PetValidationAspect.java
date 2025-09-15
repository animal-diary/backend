package animal.diary.aspect;

import animal.diary.annotation.ValidatePetId;
import animal.diary.exception.PetNotFoundException;
import animal.diary.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class PetValidationAspect {

    private final PetRepository petRepository;

    @Before("@annotation(validatePetId)")
    public void validatePetId(JoinPoint joinPoint, ValidatePetId validatePetId) {
        log.debug("Pet ID validation started for method: {}", joinPoint.getSignature().getName());

        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();

        String paramName = validatePetId.paramName();
        String errorMessage = validatePetId.message();

        Long petId = null;

        // 파라미터에서 petId 찾기
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];

            // @PathVariable이나 파라미터 이름으로 petId 찾기
            if (parameter.getName().equals(paramName) ||
                (parameter.isAnnotationPresent(org.springframework.web.bind.annotation.PathVariable.class) &&
                 parameter.getName().equals(paramName))) {

                if (args[i] instanceof Long) {
                    petId = (Long) args[i];
                    break;
                }
            }
        }

        if (petId == null) {
            log.warn("Pet ID parameter '{}' not found in method parameters", paramName);
            return;
        }

        // Pet 존재 여부 확인
        if (!petRepository.existsById(petId)) {
            log.warn("Pet not found with ID: {}", petId);
            throw new PetNotFoundException(errorMessage + " (ID: " + petId + ")");
        }

        log.debug("Pet ID validation passed for pet: {}", petId);
    }

    @Before("@within(validatePetId)")
    public void validatePetIdForClass(JoinPoint joinPoint, ValidatePetId validatePetId) {
        // 클래스 레벨 어노테이션 처리
        validatePetId(joinPoint, validatePetId);
    }
}