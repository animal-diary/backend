package animal.diary.controller;

import animal.diary.annotation.ValidatePetId;
import animal.diary.annotation.ValidatePetWithApiResponse;
import animal.diary.code.SuccessCode;
import animal.diary.dto.response.FlatStatisticsResponseDTO;
import animal.diary.dto.response.FrequencyMonthlyResponseDTO;
import animal.diary.dto.response.ResponseDTO;
import animal.diary.service.FlatStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Frequency-monthly Controller", description = "각 필드별 독립적인 통계 API")
@RestController
@RequestMapping("/api/v1/frequency-monthly")
@RequiredArgsConstructor
@ValidatePetWithApiResponse
@Slf4j
public class FrequencyMonthlyController {

    private final FlatStatisticsService flatStatisticsService;

    // ==============================================
    // 소변 통계 - 4개 필드 독립 통계
    // ==============================================

    @Operation(
        summary = "소변 관련 필드별 독립 통계 조회",
        description = """
            소변 관련 모든 필드들의 독립적인 통계를 조회합니다.
            - 같은 날 하나의 값으로 기록된 경우 dates 에는 한 번만 포함됩니다.

            반환되는 필드들:
            - output: 소변량 (NONE, LOW, NORMAL, HIGH)
            - state: 소변 상태 (NORMAL, BLOODY, LIGHT, DARK, ETC)
            - binaryState: 악취 유무 (O, X)
            - withImageOrMemo: 이미지 및 메모 유무 (O, X)

            각 필드별로 독립적인 카운트와 날짜 정보를 제공합니다.
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "소변 통계 조회 성공",
            content = { @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = """
                        {
                          "status": 200,
                          "code": "SUCCESS_GET_RECORD_LIST",
                          "message": "기록 리스트를 성공적으로 불러왔습니다.",
                          "data": {
                            "output": {
                              "NONE": {
                                "count": 2,
                                "dates": [5, 12]
                              },
                              "LOW": {
                                "count": 3,
                                "dates": [7, 14, 21]
                              },
                              "NORMAL": {
                                "count": 5,
                                "dates": [1, 8, 15, 22, 29]
                              },
                              "HIGH": {
                                "count": 1,
                                "dates": [19]
                              }
                            },
                            "state": {
                              "NORMAL": {
                                "count": 6,
                                "dates": [1, 5, 8, 12, 15, 22]
                              },
                              "BLOODY": {
                                "count": 2,
                                "dates": [7, 19]
                              },
                              "LIGHT": {
                                "count": 2,
                                "dates": [14, 21]
                              },
                              "DARK": {
                                "count": 1,
                                "dates": [29]
                              },
                              "ETC": {
                                "count": 0,
                                "dates": []
                              }
                            },
                            "binaryState": {
                              "O": {
                                "count": 4,
                                "dates": [7, 14, 19, 21]
                              },
                              "X": {
                                "count": 7,
                                "dates": [1, 5, 8, 12, 15, 22, 29]
                              }
                            },
                            "withImageOrMemo": {
                              "O": {
                                "count": 3,
                                "dates": [5, 19, 29]
                              },
                              "X": {
                                "count": 8,
                                "dates": [1, 7, 8, 12, 14, 15, 21, 22]
                              }
                            }
                          }
                        }
                        """
                )
            )}
        ),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터")
    })
    @GetMapping("/urinary/{petId}/{year}/{month}")
    public ResponseEntity<ResponseDTO<FlatStatisticsResponseDTO>> getUrinaryFlatStatistics(
            @Parameter(description = "반려동물 ID", required = true, example = "1")
            @PathVariable Long petId,
            @Parameter(description = "조회 연도 (YYYY)", required = true, example = "2025")
            @PathVariable int year,
            @Parameter(description = "조회 월 (1-12, mm)", required = true, example = "9")
            @PathVariable int month) {

        FlatStatisticsResponseDTO response = flatStatisticsService.getUrinaryFlatStatistics(petId, year, month);
        return ResponseEntity.ok(
                new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, response)
        );
    }

    // ==============================================
    // 배변 통계 - 3개 필드 독립 통계
    // ==============================================

    @Operation(
        summary = "배변 관련 필드별 독립 통계 조회",
        description = """
            배변 관련 모든 필드들의 독립적인 통계를 조회합니다.
            - 같은 날 하나의 값으로 기록된 경우 dates 에는 한 번만 포함됩니다.

            반환되는 필드들:
            - level: 배변량 (NONE, LOW, NORMAL, HIGH)
            - state: 배변 상태 (NORMAL, DIARRHEA, BLOODY, ETC)
            - withImageOrMemo: 이미지 및 메모 유무 (O, X)

            각 필드별로 독립적인 카운트와 날짜 정보를 제공합니다.
            """
    )
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "배변 통계 조회 성공",
                    content = {@io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "status": 200,
                                              "code": "SUCCESS_GET_RECORD_LIST",
                                              "message": "기록 리스트를 성공적으로 불러왔습니다.",
                                              "data": {
                                                "level": {
                                                  "NONE": {
                                                    "count": 1,
                                                    "dates": [10]
                                                  },
                                                  "LOW": {
                                                    "count": 4,
                                                    "dates": [5, 12, 19, 26]
                                                  },
                                                  "NORMAL": {
                                                    "count": 6,
                                                    "dates": [1, 8, 15, 22, 29, 30]
                                                  },
                                                  "HIGH": {
                                                    "count": 2,
                                                    "dates": [7, 14]
                                                  }
                                                },
                                                "state": {
                                                  "NORMAL": {
                                                    "count": 7,
                                                    "dates": [1, 5, 8, 12, 15, 22, 29]
                                                  },
                                                  "DIARRHEA": {
                                                    "count": 3,
                                                    "dates": [7, 14, 19]
                                                  },
                                                  "BLOODY": {
                                                    "count": 2,
                                                    "dates": [26, 30]
                                                  },
                                                  "ETC": {
                                                    "count": 1,
                                                    "dates": [10]
                                                  }
                                                },
                                                "withImageOrMemo": {
                                                  "O": {
                                                    "count": 4,
                                                    "dates": [5, 19, 26, 30]
                                                  },
                                                  "X": {
                                                    "count": 8,
                                                    "dates": [1, 7, 8, 10, 12, 14, 15, 22, 29]
                                                  }
                                                }
                                              }
                                            }
                                            """
                            )
                    )}
            ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터")}
    )
    @GetMapping("/defecation/{petId}/{year}/{month}")
    public ResponseEntity<ResponseDTO<FlatStatisticsResponseDTO>> getDefecationFlatStatistics(
            @Parameter(description = "반려동물 ID", required = true, example = "1")
            @PathVariable Long petId,
            @Parameter(description = "조회 연도(YYYY)", required = true, example = "2025")
            @PathVariable int year,
            @Parameter(description = "조회 월(1-12, mm)", required = true, example = "9")
            @PathVariable int month) {

        FlatStatisticsResponseDTO response = flatStatisticsService.getDefecationFlatStatistics(petId, year, month);
        return ResponseEntity.ok(
                new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, response)
        );
    }

    // ==============================================
    // 피부 통계 - 2개 필드 독립 통계
    // ==============================================

    @Operation(
        summary = "피부 관련 필드별 독립 통계 조회",
        description = """
            피부 관련 모든 필드들의 독립적인 통계를 조회합니다.
            - 같은 날 하나의 값으로 기록된 경우 dates 에는 한 번만 포함됩니다.

            반환되는 필드들:
            - state: 피부 상태 (ZERO, ONE, TWO, THREE)
            - withImageOrMemo: 이미지 및 메모 유무 (O, X)

            각 필드별로 독립적인 카운트와 날짜 정보를 제공합니다.
            """
    )
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "피부 통계 조회 성공",
                    content = {@io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "status": 200,
                                              "code": "SUCCESS_GET_RECORD_LIST",
                                              "message": "기록 리스트를 성공적으로 불러왔습니다.",
                                              "data": {
                                                "state": {
                                                  "ZERO": {
                                                    "count": 5,
                                                    "dates": [1, 5, 8, 12, 15]
                                                  },
                                                  "ONE": {
                                                    "count": 3,
                                                    "dates": [7, 14, 19]
                                                  },
                                                  "TWO": {
                                                    "count": 2,
                                                    "dates": [22, 29]
                                                  },
                                                  "THREE": {
                                                    "count": 0,
                                                    "dates": []
                                                  }
                                                },
                                                "withImageOrMemo": {
                                                  "O": {
                                                    "count": 4,
                                                    "dates": [5, 14, 19, 29]
                                                  },
                                                  "X": {
                                                    "count": 6,
                                                    "dates": [1, 7, 8, 12, 15, 22]
                                                  }
                                                }
                                              }
                                            }
                                            """
                            )
                    )}
            ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터")}
    )
    @GetMapping("/skin/{petId}/{year}/{month}")
    public ResponseEntity<ResponseDTO<FlatStatisticsResponseDTO>> getSkinFlatStatistics(
            @Parameter(description = "반려동물 ID", required = true, example = "1")
            @PathVariable Long petId,
            @Parameter(description = "조회 연도(YYYY)", required = true, example = "2025")
            @PathVariable int year,
            @Parameter(description = "조회 월(1-12, mm)", required = true, example = "9")
            @PathVariable int month) {

        FlatStatisticsResponseDTO response = flatStatisticsService.getSkinFlatStatistics(petId, year, month);
        return ResponseEntity.ok(
                new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, response)
        );
    }

    // ==============================================
    // 경련 통계 - 3개 필드 독립 통계
    // ==============================================

    @Operation(
        summary = "경련 관련 필드별 독립 통계 조회",
        description = """
            경련 관련 모든 필드들의 독립적인 통계를 조회합니다.
            - 같은 날 하나의 값으로 기록된 경우 dates 에는 한 번만 포함됩니다.

            반환되는 필드들:
            - state: 경련 여부 (O, X)
            - abnormalState: 이상 증상 (INCONTINENCE, DROOLING, UNCONSCIOUS, NORMAL 등)
            - withVideo: 비디오 유무 (O, X)

            각 필드별로 독립적인 카운트와 날짜 정보를 제공합니다.
            """
    )
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "경련 통계 조회 성공",
                    content = {@io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "status": 200,
                                              "code": "SUCCESS_GET_RECORD_LIST",
                                              "message": "기록 리스트를 성공적으로 불러왔습니다.",
                                              "data": {
                                                "state": {
                                                  "O": {
                                                    "count": 3,
                                                    "dates": [7, 14, 19]
                                                  },
                                                  "X": {
                                                    "count": 6,
                                                    "dates": [1, 5, 8, 12, 15, 22]
                                                  }
                                                },
                                                "abnormalState": {
                                                  "INCONTINENCE": {
                                                    "count": 1,
                                                    "dates": [7]
                                                  },
                                                  "DROOLING": {
                                                    "count": 1,
                                                    "dates": [14]
                                                  },
                                                  "UNCONSCIOUS": {
                                                    "count": 1,
                                                    "dates": [19]
                                                  },
                                                  "NORMAL": {
                                                    "count": 6,
                                                    "dates": [1, 5, 8, 12, 15, 22]
                                                  }
                                                },
                                                "withVideo": {
                                                  "O": {
                                                    "count": 2,
                                                    "dates": [14, 19]
                                                  },
                                                  "X": {
                                                    "count": 7,
                                                    "dates": [1, 5, 7, 8, 12, 15, 22]
                                                  }
                                                }
                                              }
                                            }
                                            """
                            )
                    )}
            ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터")}
    )
    @GetMapping("/convulsion/{petId}/{year}/{month}")
    public ResponseEntity<ResponseDTO<FlatStatisticsResponseDTO>> getConvulsionFlatStatistics(
            @Parameter(description = "반려동물 ID", required = true, example = "1")
            @PathVariable Long petId,
            @Parameter(description = "조회 연도(YYYY)", required = true, example = "2025")
            @PathVariable int year,
            @Parameter(description = "조회 월(1-12, mm)", required = true, example = "9")
            @PathVariable int month) {

        FlatStatisticsResponseDTO response = flatStatisticsService.getConvulsionFlatStatistics(petId, year, month);
        return ResponseEntity.ok(
                new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, response)
        );
    }

    // ==============================================
    // 콧물 통계 - 2개 필드 독립 통계
    // ==============================================

    @Operation(
        summary = "콧물 관련 필드별 독립 통계 조회",
        description = """
            콧물 관련 모든 필드들의 독립적인 통계를 조회합니다.
            - 같은 날 하나의 값으로 기록된 경우 dates 에는 한 번만 포함됩니다.

            반환되는 필드들:
            - state: 콧물 상태 (CLEAR, MUCUS, BLOODY)
            - withImageOrMemo: 이미지 및 메모 유무 (O, X)

            각 필드별로 독립적인 카운트와 날짜 정보를 제공합니다.
            """
    )
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "콧물 통계 조회 성공",
                    content = {@io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "status": 200,
                                              "code": "SUCCESS_GET_RECORD_LIST",
                                              "message": "기록 리스트를 성공적으로 불러왔습니다.",
                                              "data": {
                                                "state": {
                                                  "CLEAR": {
                                                    "count": 4,
                                                    "dates": [1, 5, 8, 12]
                                                  },
                                                  "MUCUS": {
                                                    "count": 3,
                                                    "dates": [7, 14, 19]
                                                  },
                                                  "BLOODY": {
                                                    "count": 2,
                                                    "dates": [15, 22]
                                                  }
                                                },
                                                "withImageOrMemo": {
                                                  "O": {
                                                    "count": 3,
                                                    "dates": [5, 15, 22]
                                                  },
                                                  "X": {
                                                    "count": 6,
                                                    "dates": [1, 7, 8, 12, 14, 19]
                                                  }
                                                }
                                              }
                                            }
                                            """
                            )
                    )}
            ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터")}
    )
    @GetMapping("/snot/{petId}/{year}/{month}")
    public ResponseEntity<ResponseDTO<FlatStatisticsResponseDTO>> getSnotFlatStatistics(
            @Parameter(description = "반려동물 ID", required = true, example = "1")
            @PathVariable Long petId,
            @Parameter(description = "조회 연도(YYYY)", required = true, example = "2025")
            @PathVariable int year,
            @Parameter(description = "조회 월(1-12, mm)", required = true, example = "9")
            @PathVariable int month) {

        FlatStatisticsResponseDTO response = flatStatisticsService.getSnotFlatStatistics(petId, year, month);
        return ResponseEntity.ok(
                new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, response)
        );
    }

    // ==============================================
    // 구토 통계 - 2개 필드 독립 통계
    // ==============================================

    @Operation(
        summary = "구토 관련 필드별 독립 통계 조회",
        description = """
            구토 관련 모든 필드들의 독립적인 통계를 조회합니다.
            - 같은 날 하나의 값으로 기록된 경우 dates 에는 한 번만 포함됩니다.

            반환되는 필드들:
            - state: 구토 여부 (O, X)
            - withImages: 이미지 유무 (O, X)

            각 필드별로 독립적인 카운트와 날짜 정보를 제공합니다.
            """
    )
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "구토 통계 조회 성공",
                    content = {@io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "status": 200,
                                              "code": "SUCCESS_GET_RECORD_LIST",
                                              "message": "기록 리스트를 성공적으로 불러왔습니다.",
                                              "data": {
                                                "state": {
                                                  "O": {
                                                    "count": 3,
                                                    "dates": [7, 14, 19]
                                                  },
                                                  "X": {
                                                    "count": 6,
                                                    "dates": [1, 5, 8, 12, 15, 22]
                                                  }
                                                },
                                                "withImages": {
                                                  "O": {
                                                    "count": 2,
                                                    "dates": [14, 19]
                                                  },
                                                  "X": {
                                                    "count": 7,
                                                    "dates": [1, 5, 7, 8, 12, 15, 22]
                                                  }
                                                }
                                              }
                                            }
                                            """
                            )
                    )}
            ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터")}
    )
    @GetMapping("/vomiting/{petId}/{year}/{month}")
    public ResponseEntity<ResponseDTO<FlatStatisticsResponseDTO>> getVomitingFlatStatistics(
            @Parameter(description = "반려동물 ID", required = true, example = "1")
            @PathVariable Long petId,
            @Parameter(description = "조회 연도(YYYY)", required = true, example = "2025")
            @PathVariable int year,
            @Parameter(description = "조회 월(1-12, mm)", required = true, example = "9")
            @PathVariable int month) {

        FlatStatisticsResponseDTO response = flatStatisticsService.getVomitingFlatStatistics(petId, year, month);
        return ResponseEntity.ok(
                new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, response)
        );
    }

    // ==============================================
    // 단순 상태만 있는 엔티티들 - 1개 필드
    // ==============================================

    @Operation(
        summary = "식욕 상태 통계 조회",
        description = """
            식욕 상태의 독립적인 통계를 조회합니다.
            - 같은 날 하나의 값으로 기록된 경우 dates 에는 한 번만 포함됩니다.

            반환되는 필드들:
            - state: 식욕 상태 (LOW, NORMAL, HIGH)
            """
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "식욕 통계 조회 성공",
            content = { @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = """
                        {
                          "status": 200,
                          "code": "SUCCESS_GET_RECORD_LIST",
                          "message": "기록 리스트를 성공적으로 불러왔습니다.",
                          "data": {
                            "state": {
                              "LOW": {
                                "count": 3,
                                "dates": [7, 14, 19]
                              },
                              "NORMAL": {
                                "count": 5,
                                "dates": [1, 5, 8, 12, 15]
                              },
                              "HIGH": {
                                "count": 1,
                                "dates": [22]
                              }
                            }
                          }
                        }
                        """
                )
            )}
        ),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터")
    })
    @GetMapping("/appetite/{petId}/{year}/{month}")
    public ResponseEntity<ResponseDTO<FlatStatisticsResponseDTO>> getAppetiteFlatStatistics(
            @Parameter(description = "반려동물 ID", required = true, example = "1")
            @PathVariable Long petId,
            @Parameter(description = "조회 연도(YYYY)", required = true, example = "2025")
            @PathVariable int year,
            @Parameter(description = "조회 월(1-12, mm)", required = true, example = "9")
            @PathVariable int month) {

        FlatStatisticsResponseDTO response = flatStatisticsService.getAppetiteFlatStatistics(petId, year, month);
        return ResponseEntity.ok(
                new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, response)
        );
    }

    @Operation(
        summary = "활력 상태 통계 조회",
        description = """
            활력 상태의 독립적인 통계를 조회합니다.
            - 같은 날 하나의 값으로 기록된 경우 dates 에는 한 번만 포함됩니다.

            반환되는 필드들:
            - state: 활력 상태 (LOW, NORMAL, HIGH)
            """
    )
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "활력 통계 조회 성공",
                    content = {@io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "status": 200,
                                              "code": "SUCCESS_GET_RECORD_LIST",
                                              "message": "기록 리스트를 성공적으로 불러왔습니다.",
                                              "data": {
                                                "state": {
                                                  "LOW": {
                                                    "count": 2,
                                                    "dates": [7, 14]
                                                  },
                                                  "NORMAL": {
                                                    "count": 6,
                                                    "dates": [1, 5, 8, 12, 15, 19]
                                                  },
                                                  "HIGH": {
                                                    "count": 1,
                                                    "dates": [22]
                                                  }
                                                }
                                              }
                                            }
                                            """
                            )
                    )}
            ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터")}
    )
    @GetMapping("/energy/{petId}/{year}/{month}")
    public ResponseEntity<ResponseDTO<FlatStatisticsResponseDTO>> getEnergyFlatStatistics(
            @Parameter(description = "반려동물 ID", required = true, example = "1")
            @PathVariable Long petId,
            @Parameter(description = "조회 연도(YYYY)", required = true, example = "2025")
            @PathVariable int year,
            @Parameter(description = "조회 월(1-12, mm)", required = true, example = "9")
            @PathVariable int month) {

        FlatStatisticsResponseDTO response = flatStatisticsService.getEnergyFlatStatistics(petId, year, month);
        return ResponseEntity.ok(
                new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, response)
        );
    }

    @Operation(
        summary = "실신 여부 통계 조회",
        description = """
            실신 여부의 독립적인 통계를 조회합니다.
            - 같은 날 하나의 값으로 기록된 경우 dates 에는 한 번만 포함됩니다.

            반환되는 필드들:
            - state: 실신 여부 (O, X)
            """
    )
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "실신 통계 조회 성공",
                    content = {@io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "status": 200,
                                              "code": "SUCCESS_GET_RECORD_LIST",
                                              "message": "기록 리스트를 성공적으로 불러왔습니다.",
                                              "data": {
                                                "state": {
                                                  "O": {
                                                    "count": 3,
                                                    "dates": [7, 14, 19]
                                                  },
                                                  "X": {
                                                    "count": 6,
                                                    "dates": [1, 5, 8, 12, 15, 22]
                                                  }
                                                }
                                              }
                                            }
                                            """
                            )
                    )}
            ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터")}
    )
    @GetMapping("/syncope/{petId}/{year}/{month}")
    public ResponseEntity<ResponseDTO<FlatStatisticsResponseDTO>> getSyncopeFlatStatistics(
            @Parameter(description = "반려동물 ID", required = true, example = "1")
            @PathVariable Long petId,
            @Parameter(description = "조회 연도(YYYY)", required = true, example = "2025")
            @PathVariable int year,
            @Parameter(description = "조회 월(1-12, mm)", required = true, example = "9")
            @PathVariable int month) {

        FlatStatisticsResponseDTO response = flatStatisticsService.getSyncopeFlatStatistics(petId, year, month);
        return ResponseEntity.ok(
                new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, response)
        );
    }

    @Operation(
        summary = "물 섭취량 상태 통계 조회",
        description = """
            물 섭취량 상태의 독립적인 통계를 조회합니다.
            - 같은 날 하나의 값으로 기록된 경우 dates 에는 한 번만 포함됩니다.

            반환되는 필드들:
            - state: 물 섭취량 상태 (LOW, NORMAL, HIGH)
            """
    )
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "물 섭취량 통계 조회 성공",
                    content = {@io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                              "status": 200,
                                              "code": "SUCCESS_GET_RECORD_LIST",
                                              "message": "기록 리스트를 성공적으로 불러왔습니다.",
                                              "data": {
                                                "state": {
                                                  "LOW": {
                                                    "count": 2,
                                                    "dates": [7, 14]
                                                  },
                                                  "NORMAL": {
                                                    "count": 6,
                                                    "dates": [1, 5, 8, 12, 15, 19]
                                                  },
                                                  "HIGH": {
                                                    "count": 1,
                                                    "dates": [22]
                                                  }
                                                }
                                              }
                                            }
                                            """
                            )
                    )}
            ),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터")}
    )
    @GetMapping("/water/{petId}/{year}/{month}")
    public ResponseEntity<ResponseDTO<FlatStatisticsResponseDTO>> getWaterFlatStatistics(
            @Parameter(description = "반려동물 ID", required = true, example = "1")
            @PathVariable Long petId,
            @Parameter(description = "조회 연도(YYYY)", required = true, example = "2025")
            @PathVariable int year,
            @Parameter(description = "조회 월(1-12, mm)", required = true, example = "9")
            @PathVariable int month) {

        FlatStatisticsResponseDTO response = flatStatisticsService.getWaterFlatStatistics(petId, year, month);
        return ResponseEntity.ok(
                new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, response)
        );
    }


    @Operation(summary = "월별 호흡수 통계 조회", description = """
            특정 반려동물의 월별 호흡수 통계를 조회합니다.
            - 주의 날짜: 기준 초과 호흡수
            - 안정 날짜: 기준 이하 호흡수
            """)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "호흡수 통계 조회 성공",
            content = { @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = """
                        {
                          "status": 200,
                          "code": "SUCCESS_GET_RECORD_LIST",
                          "message": "기록 리스트를 성공적으로 불러왔습니다.",
                          "data": {
                            "cautionDates": [5, 12, 19],
                            "stableDates": [1, 8, 15, 22, 29]
                          }
                        }
                        """
                )
            )}
        ),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터")
    })
    @GetMapping("/respiratory-rate/{petId}/{year}/{month}")
    public ResponseEntity<ResponseDTO<FrequencyMonthlyResponseDTO>> getRespiratoryRate(
            @Parameter(description = "반려동물 ID", required = true, example = "1") @PathVariable Long petId,
            @Parameter(description = "조회 연도(YYYY)", required = true, example = "2025") @PathVariable int year,
            @Parameter(description = "조회 월(1-12, mm)", required = true, example = "9") @PathVariable int month) {

        FrequencyMonthlyResponseDTO response = flatStatisticsService.getRespiratoryRate(petId, year, month);
        return ResponseEntity.ok(
                new ResponseDTO<>(SuccessCode.SUCCESS_GET_RECORD_LIST, response)
        );
    }
}