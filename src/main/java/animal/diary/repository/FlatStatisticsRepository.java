package animal.diary.repository;

import animal.diary.dto.response.SymptomStateCountResponseDTO;
import animal.diary.dto.response.FlatStatisticsResponseDTO;
import animal.diary.entity.record.state.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FlatStatisticsRepository {

    private final EntityManager entityManager;

    // ==============================================
    // URINARY - 4개 필드 독립 통계
    // ==============================================
    public FlatStatisticsResponseDTO getUrinaryFlatStatistics(Long petId, int year, int month) {
        Map<String, List<SymptomStateCountResponseDTO>> fieldStats = new LinkedHashMap<>();

        // 1. 소변량 (output)
        fieldStats.put("output", getUrinaryOutputStatistics(petId, year, month));

        // 2. 소변 상태 (state)
        fieldStats.put("state", getUrinaryStateStatistics(petId, year, month));

        // 3. 악취 유무 (binaryState)
        fieldStats.put("binaryState", getUrinaryBinaryStateStatistics(petId, year, month));

        // 4. 이미지 및 메모 유무 (imageUrls or memo)
        fieldStats.put("withImageOrMemo", getUrinaryImageMemoStatistics(petId, year, month));

        return new FlatStatisticsResponseDTO(fieldStats);
    }

    private List<SymptomStateCountResponseDTO> getUrinaryOutputStatistics(Long petId, int year, int month) {
        String sql = """
            SELECT u.output,
                   COUNT(*) as count,
                   GROUP_CONCAT(EXTRACT(DAY FROM u.created_at) ORDER BY u.created_at SEPARATOR ',') as dates
            FROM urinary u
            WHERE u.pet_id = :petId
              AND YEAR(u.created_at) = :year
              AND MONTH(u.created_at) = :month
            GROUP BY u.output
            ORDER BY u.output
            """;

        List<SymptomStateCountResponseDTO> results = executeStatisticsQueryWithStateConversion(sql, petId, year, month, "LEVEL");
        return ensureAllLevelStates(results);
    }

    private List<SymptomStateCountResponseDTO> getUrinaryStateStatistics(Long petId, int year, int month) {
        String sql = """
            SELECT u.state,
                   COUNT(*) as count,
                   GROUP_CONCAT(EXTRACT(DAY FROM u.created_at) ORDER BY u.created_at SEPARATOR ',') as dates
            FROM urinary u
            WHERE u.pet_id = :petId
              AND YEAR(u.created_at) = :year
              AND MONTH(u.created_at) = :month
            GROUP BY u.state
            ORDER BY u.state
            """;

        List<SymptomStateCountResponseDTO> results = executeStatisticsQueryWithStateConversion(sql, petId, year, month, "URINE");
        return ensureAllUrineStates(results);
    }

    private List<SymptomStateCountResponseDTO> getUrinaryBinaryStateStatistics(Long petId, int year, int month) {
        String sql = """
            SELECT u.binary_state,
                   COUNT(*) as count,
                   GROUP_CONCAT(EXTRACT(DAY FROM u.created_at) ORDER BY u.created_at SEPARATOR ',') as dates
            FROM urinary u
            WHERE u.pet_id = :petId
              AND YEAR(u.created_at) = :year
              AND MONTH(u.created_at) = :month
            GROUP BY u.binary_state
            ORDER BY u.binary_state
            """;

        List<SymptomStateCountResponseDTO> results = executeStatisticsQueryWithStateConversion(sql, petId, year, month, "BINARY");
        return ensureAllBinaryStates(results);
    }

    private List<SymptomStateCountResponseDTO> getUrinaryImageMemoStatistics(Long petId, int year, int month) {
        String sql = """
            SELECT CASE
                WHEN (u.memo IS NOT NULL AND u.memo != '') OR (u.image_urls IS NOT NULL AND u.image_urls != '' AND u.image_urls != '[]')
                THEN 'O'
                ELSE 'X'
            END as has_image_or_memo,
            COUNT(*) as count,
            GROUP_CONCAT(EXTRACT(DAY FROM u.created_at) ORDER BY u.created_at SEPARATOR ',') as dates
            FROM urinary u
            WHERE u.pet_id = :petId
              AND YEAR(u.created_at) = :year
              AND MONTH(u.created_at) = :month
            GROUP BY has_image_or_memo
            ORDER BY has_image_or_memo
            """;

        List<SymptomStateCountResponseDTO> results = executeStatisticsQuery(sql, petId, year, month);
        return ensureOXStates(results);
    }

    // ==============================================
    // DEFECATION - 4개 필드 독립 통계
    // ==============================================
    public FlatStatisticsResponseDTO getDefecationFlatStatistics(Long petId, int year, int month) {
        Map<String, List<SymptomStateCountResponseDTO>> fieldStats = new LinkedHashMap<>();

        // 1. 배변량 (level)
        fieldStats.put("level", getDefecationLevelStatistics(petId, year, month));

        // 2. 배변 상태 (state)
        fieldStats.put("state", getDefecationStateStatistics(petId, year, month));

        // 3. 이미지 및 메모 유무 (imageUrls or memo)
        fieldStats.put("withImageOrMemo", getDefecationImageMemoStatistics(petId, year, month));

        return new FlatStatisticsResponseDTO(fieldStats);
    }

    private List<SymptomStateCountResponseDTO> getDefecationLevelStatistics(Long petId, int year, int month) {
        String sql = """
            SELECT d.level,
                   COUNT(*) as count,
                   GROUP_CONCAT(EXTRACT(DAY FROM d.created_at) ORDER BY d.created_at SEPARATOR ',') as dates
            FROM defecation d
            WHERE d.pet_id = :petId
              AND YEAR(d.created_at) = :year
              AND MONTH(d.created_at) = :month
            GROUP BY d.level
            ORDER BY d.level
            """;

        List<SymptomStateCountResponseDTO> results = executeStatisticsQueryWithStateConversion(sql, petId, year, month, "LEVEL");
        return ensureAllLevelStates(results);
    }

    private List<SymptomStateCountResponseDTO> getDefecationStateStatistics(Long petId, int year, int month) {
        String sql = """
            SELECT d.state,
                   COUNT(*) as count,
                   GROUP_CONCAT(EXTRACT(DAY FROM d.created_at) ORDER BY d.created_at SEPARATOR ',') as dates
            FROM defecation d
            WHERE d.pet_id = :petId
              AND YEAR(d.created_at) = :year
              AND MONTH(d.created_at) = :month
            GROUP BY d.state
            ORDER BY d.state
            """;

        List<SymptomStateCountResponseDTO> results = executeStatisticsQueryWithStateConversion(sql, petId, year, month, "STOOL");
        return ensureAllStoolStates(results);
    }

    private List<SymptomStateCountResponseDTO> getDefecationImageMemoStatistics(Long petId, int year, int month) {
        String sql = """
            SELECT CASE
                WHEN (d.memo IS NOT NULL AND d.memo != '') OR (d.image_urls IS NOT NULL AND d.image_urls != '' AND d.image_urls != '[]')
                THEN 'O'
                ELSE 'X'
            END as has_image_or_memo,
            COUNT(*) as count,
            GROUP_CONCAT(EXTRACT(DAY FROM d.created_at) ORDER BY d.created_at SEPARATOR ',') as dates
            FROM defecation d
            WHERE d.pet_id = :petId
              AND YEAR(d.created_at) = :year
              AND MONTH(d.created_at) = :month
            GROUP BY has_image_or_memo
            ORDER BY has_image_or_memo
            """;

        List<SymptomStateCountResponseDTO> results = executeStatisticsQuery(sql, petId, year, month);
        return ensureOXStates(results);
    }

    // ==============================================
    // SKIN - 3개 필드 독립 통계
    // ==============================================
    public FlatStatisticsResponseDTO getSkinFlatStatistics(Long petId, int year, int month) {
        Map<String, List<SymptomStateCountResponseDTO>> fieldStats = new LinkedHashMap<>();

        // 1. 피부 상태 (state)
        fieldStats.put("state", getSkinStateStatistics(petId, year, month));

        // 2. 이미지 및 메모 유무 (imageUrls or memo)
        fieldStats.put("withImageOrMemo", getSkinImageMemoStatistics(petId, year, month));

        return new FlatStatisticsResponseDTO(fieldStats);
    }

    private List<SymptomStateCountResponseDTO> getSkinStateStatistics(Long petId, int year, int month) {
        String sql = """
            SELECT s.state as state,
                   COUNT(*) as count,
                   GROUP_CONCAT(EXTRACT(DAY FROM s.created_at) ORDER BY s.created_at SEPARATOR ',') as dates
            FROM skin s
            WHERE s.pet_id = :petId
              AND YEAR(s.created_at) = :year
              AND MONTH(s.created_at) = :month
            GROUP BY s.state
            ORDER BY s.state
            """;

        List<SymptomStateCountResponseDTO> result = executeStatisticsQueryWithStateConversion(sql, petId, year, month, "NUMBER");
        return ensureAllNumberStates(result); // 🔥 누락된 NumberState 보정
    }

    private List<SymptomStateCountResponseDTO> getSkinImageMemoStatistics(Long petId, int year, int month) {
        String sql = """
            SELECT CASE
                WHEN (s.memo IS NOT NULL AND s.memo != '') OR (s.image_urls IS NOT NULL AND s.image_urls != '' AND s.image_urls != '[]')
                THEN 'O'
                ELSE 'X'
            END as has_image_or_memo,
            COUNT(*) as count,
            GROUP_CONCAT(EXTRACT(DAY FROM s.created_at) ORDER BY s.created_at SEPARATOR ',') as dates
            FROM skin s
            WHERE s.pet_id = :petId
              AND YEAR(s.created_at) = :year
              AND MONTH(s.created_at) = :month
            GROUP BY has_image_or_memo
            ORDER BY has_image_or_memo
            """;

        List<SymptomStateCountResponseDTO> result = executeStatisticsQuery(sql, petId, year, month);
        return ensureOXStates(result); // 🔥 O/X 빠진 경우 보정
    }

    // ==============================================
    // CONVULSION - 3개 필드 독립 통계
    // ==============================================
    public FlatStatisticsResponseDTO getConvulsionFlatStatistics(Long petId, int year, int month) {
        Map<String, List<SymptomStateCountResponseDTO>> fieldStats = new LinkedHashMap<>();

        // 1. 경련 여부 (state)
        fieldStats.put("state", getConvulsionStateStatistics(petId, year, month));

        // 2. 이상 증상 (abnormalState) - 복수개 가능
        fieldStats.put("abnormalState", getConvulsionAbnormalStateStatistics(petId, year, month));

        // 3. 비디오 유무 (videoUrl)
        fieldStats.put("withVideo", getConvulsionVideoStatistics(petId, year, month));

        return new FlatStatisticsResponseDTO(fieldStats);
    }

    private List<SymptomStateCountResponseDTO> getConvulsionStateStatistics(Long petId, int year, int month) {
        String sql = """
            SELECT c.state,
                   COUNT(*) as count,
                   GROUP_CONCAT(EXTRACT(DAY FROM c.created_at) ORDER BY c.created_at SEPARATOR ',') as dates
            FROM convulsion c
            WHERE c.pet_id = :petId
              AND YEAR(c.created_at) = :year
              AND MONTH(c.created_at) = :month
            GROUP BY c.state
            ORDER BY c.state
            """;

         List<SymptomStateCountResponseDTO> result =executeStatisticsQueryWithStateConversion(sql, petId, year, month, "BINARY");
         return ensureAllBinaryStates(result);
    }

    private List<SymptomStateCountResponseDTO> getConvulsionAbnormalStateStatistics(Long petId, int year, int month) {
        String sql = """
            SELECT c.abnormal_state,
                   COUNT(*) as count,
                   GROUP_CONCAT(EXTRACT(DAY FROM c.created_at) ORDER BY c.created_at SEPARATOR ',') as dates
            FROM convulsion c
            WHERE c.pet_id = :petId
              AND YEAR(c.created_at) = :year
              AND MONTH(c.created_at) = :month
            GROUP BY c.abnormal_state
            ORDER BY c.abnormal_state
            """;

        List<SymptomStateCountResponseDTO> result = executeStatisticsQuery(sql, petId, year, month);
        return ensureAllConvulsionStates(result);
    }

    private List<SymptomStateCountResponseDTO> getConvulsionVideoStatistics(Long petId, int year, int month) {
        String sql = """
            SELECT CASE
                WHEN c.video_url IS NOT NULL AND c.video_url != ''
                THEN 'O'
                ELSE 'X'
            END as has_video,
            COUNT(*) as count,
            GROUP_CONCAT(EXTRACT(DAY FROM c.created_at) ORDER BY c.created_at SEPARATOR ',') as dates
            FROM convulsion c
            WHERE c.pet_id = :petId
              AND YEAR(c.created_at) = :year
              AND MONTH(c.created_at) = :month
            GROUP BY has_video
            ORDER BY has_video
            """;

        List<SymptomStateCountResponseDTO> result = executeStatisticsQuery(sql, petId, year, month);
        return ensureOXStates(result);
    }

    // ==============================================
    // SNOT - 3개 필드 독립 통계
    // ==============================================
    public FlatStatisticsResponseDTO getSnotFlatStatistics(Long petId, int year, int month) {
        Map<String, List<SymptomStateCountResponseDTO>> fieldStats = new LinkedHashMap<>();

        // 1. 콧물 상태 (state)
        fieldStats.put("state", getSnotStateStatistics(petId, year, month));

        // 2. 이미지 및 메모 유무 (imageUrls or memo)
        fieldStats.put("withImageOrMemo", getSnotImageMemoStatistics(petId, year, month));

        return new FlatStatisticsResponseDTO(fieldStats);
    }

    private List<SymptomStateCountResponseDTO> getSnotStateStatistics(Long petId, int year, int month) {
        String sql = """
        SELECT s.state,
               COUNT(*) as count,
               GROUP_CONCAT(EXTRACT(DAY FROM s.created_at) ORDER BY s.created_at SEPARATOR ',') as dates
        FROM snot s
        WHERE s.pet_id = :petId
          AND YEAR(s.created_at) = :year
          AND MONTH(s.created_at) = :month
        GROUP BY s.state
        ORDER BY s.state
        """;

        List<SymptomStateCountResponseDTO> results =
                executeStatisticsQueryWithStateConversion(sql, petId, year, month, "SNOT");

        // ✅ 모든 SnotState 값 보장
        return ensureAllSnotStates(results);
    }

    private List<SymptomStateCountResponseDTO> getSnotImageMemoStatistics(Long petId, int year, int month) {
        String sql = """
        SELECT CASE
            WHEN (s.memo IS NOT NULL AND s.memo != '') OR (s.image_urls IS NOT NULL AND s.image_urls != '' AND s.image_urls != '[]')
            THEN 'O'
            ELSE 'X'
        END as has_image_or_memo,
        COUNT(*) as count,
        GROUP_CONCAT(EXTRACT(DAY FROM s.created_at) ORDER BY s.created_at SEPARATOR ',') as dates
        FROM snot s
        WHERE s.pet_id = :petId
          AND YEAR(s.created_at) = :year
          AND MONTH(s.created_at) = :month
        GROUP BY has_image_or_memo
        ORDER BY has_image_or_memo
        """;

        List<SymptomStateCountResponseDTO> results = executeStatisticsQuery(sql, petId, year, month);

        // ✅ 반드시 O, X 두 가지 상태 모두 포함되도록 보정
        return ensureOXStates(results);
    }


    // ==============================================
    // VOMITING - 2개 필드 독립 통계
    // ==============================================
    public FlatStatisticsResponseDTO getVomitingFlatStatistics(Long petId, int year, int month) {
        Map<String, List<SymptomStateCountResponseDTO>> fieldStats = new LinkedHashMap<>();

        // 1. 구토 여부 (state)
        fieldStats.put("state", getVomitingStateStatistics(petId, year, month));

        // 2. 이미지 유무 (imageUrls)
        fieldStats.put("withImages", getVomitingImageStatistics(petId, year, month));

        return new FlatStatisticsResponseDTO(fieldStats);
    }

    private List<SymptomStateCountResponseDTO> getVomitingStateStatistics(Long petId, int year, int month) {
        String sql = """
            SELECT v.state,
                   COUNT(*) as count,
                   GROUP_CONCAT(EXTRACT(DAY FROM v.created_at) ORDER BY v.created_at SEPARATOR ',') as dates
            FROM vomiting v
            WHERE v.pet_id = :petId
              AND YEAR(v.created_at) = :year
              AND MONTH(v.created_at) = :month
            GROUP BY v.state
            ORDER BY v.state
            """;

        List<SymptomStateCountResponseDTO> results = executeStatisticsQueryWithStateConversion(sql, petId, year, month, "BINARY");
        return ensureAllBinaryStates(results);
    }

    private List<SymptomStateCountResponseDTO> getVomitingImageStatistics(Long petId, int year, int month) {
        String sql = """
            SELECT CASE
                WHEN v.image_urls IS NOT NULL AND v.image_urls != '' AND v.image_urls != '[]'
                THEN 'O'
                ELSE 'X'
            END as has_images,
            COUNT(*) as count,
            GROUP_CONCAT(EXTRACT(DAY FROM v.created_at) ORDER BY v.created_at SEPARATOR ',') as dates
            FROM vomiting v
            WHERE v.pet_id = :petId
              AND YEAR(v.created_at) = :year
              AND MONTH(v.created_at) = :month
            GROUP BY has_images
            ORDER BY has_images
            """;

        List<SymptomStateCountResponseDTO> results = executeStatisticsQuery(sql, petId, year, month);
        return ensureOXStates(results);
    }

    // ==============================================
    // 단순 상태만 있는 엔티티들 - 1개 필드
    // ==============================================

    // APPETITE - 식욕 상태만
    public FlatStatisticsResponseDTO getAppetiteFlatStatistics(Long petId, int year, int month) {
        Map<String, List<SymptomStateCountResponseDTO>> fieldStats = new LinkedHashMap<>();
        fieldStats.put("state", getAppetiteStateStatistics(petId, year, month));
        return new FlatStatisticsResponseDTO(fieldStats);
    }

    private List<SymptomStateCountResponseDTO> getAppetiteStateStatistics(Long petId, int year, int month) {
        String sql = """
            SELECT a.state,
                   COUNT(*) as count,
                   GROUP_CONCAT(EXTRACT(DAY FROM a.created_at) ORDER BY a.created_at SEPARATOR ',') as dates
            FROM appetite a
            WHERE a.pet_id = :petId
              AND YEAR(a.created_at) = :year
              AND MONTH(a.created_at) = :month
            GROUP BY a.state
            ORDER BY a.state
            """;

        List<SymptomStateCountResponseDTO> results = executeStatisticsQueryWithStateConversion(sql, petId, year, month, "LEVEL");
        return ensureAllLevelStatesExcludeNone(results);
    }

    // ENERGY - 활력 상태만
    public FlatStatisticsResponseDTO getEnergyFlatStatistics(Long petId, int year, int month) {
        Map<String, List<SymptomStateCountResponseDTO>> fieldStats = new LinkedHashMap<>();
        fieldStats.put("state", getEnergyStateStatistics(petId, year, month));
        return new FlatStatisticsResponseDTO(fieldStats);
    }

    private List<SymptomStateCountResponseDTO> getEnergyStateStatistics(Long petId, int year, int month) {
        String sql = """
            SELECT e.state,
                   COUNT(*) as count,
                   GROUP_CONCAT(EXTRACT(DAY FROM e.created_at) ORDER BY e.created_at SEPARATOR ',') as dates
            FROM energy e
            WHERE e.pet_id = :petId
              AND YEAR(e.created_at) = :year
              AND MONTH(e.created_at) = :month
            GROUP BY e.state
            ORDER BY e.state
            """;

        List<SymptomStateCountResponseDTO> results = executeStatisticsQueryWithStateConversion(sql, petId, year, month, "LEVEL");
        return ensureAllLevelStatesExcludeNone(results);
    }

    // SYNCOPE - 실신 여부만
    public FlatStatisticsResponseDTO getSyncopeFlatStatistics(Long petId, int year, int month) {
        Map<String, List<SymptomStateCountResponseDTO>> fieldStats = new LinkedHashMap<>();
        fieldStats.put("state", getSyncopeStateStatistics(petId, year, month));
        return new FlatStatisticsResponseDTO(fieldStats);
    }

    private List<SymptomStateCountResponseDTO> getSyncopeStateStatistics(Long petId, int year, int month) {
        String sql = """
            SELECT s.state,
                   COUNT(*) as count,
                   GROUP_CONCAT(EXTRACT(DAY FROM s.created_at) ORDER BY s.created_at SEPARATOR ',') as dates
            FROM syncope s
            WHERE s.pet_id = :petId
              AND YEAR(s.created_at) = :year
              AND MONTH(s.created_at) = :month
            GROUP BY s.state
            ORDER BY s.state
            """;

        return executeStatisticsQueryWithStateConversion(sql, petId, year, month, "BINARY");
    }

    // WATER - 물 섭취량 상태만
    public FlatStatisticsResponseDTO getWaterFlatStatistics(Long petId, int year, int month) {
        Map<String, List<SymptomStateCountResponseDTO>> fieldStats = new LinkedHashMap<>();
        fieldStats.put("state", getWaterStateStatistics(petId, year, month));
        return new FlatStatisticsResponseDTO(fieldStats);
    }

    private List<SymptomStateCountResponseDTO> getWaterStateStatistics(Long petId, int year, int month) {
        String sql = """
            SELECT w.state,
                   COUNT(*) as count,
                   GROUP_CONCAT(EXTRACT(DAY FROM w.created_at) ORDER BY w.created_at SEPARATOR ',') as dates
            FROM water w
            WHERE w.pet_id = :petId
              AND YEAR(w.created_at) = :year
              AND MONTH(w.created_at) = :month
            GROUP BY w.state
            ORDER BY w.state
            """;

        List<SymptomStateCountResponseDTO> results = executeStatisticsQueryWithStateConversion(sql, petId, year, month, "LEVEL");
        return ensureAllLevelStatesExcludeNone(results);
    }

    // ==============================================
    // Helper Methods
    // ==============================================

    private List<SymptomStateCountResponseDTO> executeStatisticsQueryWithStateConversion(String sql, Long petId, int year, int month, String stateType) {
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("petId", petId);
        query.setParameter("year", year);
        query.setParameter("month", month);

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        List<SymptomStateCountResponseDTO> statistics = new ArrayList<>();
        for (Object[] result : results) {
            String rawState = String.valueOf(result[0]);
            String convertedState = convertStateValue(rawState, stateType);
            Number countNumber = (Number) result[1];
            int count = countNumber.intValue();
            String datesStr = (String) result[2];

            Set<Integer> dateSet = new TreeSet<>();
            if (datesStr != null && !datesStr.isEmpty()) {
                String[] dateArray = datesStr.split(",");
                for (String dateStr : dateArray) {
                    try {
                        dateSet.add(Integer.parseInt(dateStr.trim()));
                    } catch (NumberFormatException e) {
                        log.warn("Failed to parse date: {}", dateStr, e);
                    }
                }
            }
            List<Integer> dates = new ArrayList<>(dateSet);

            statistics.add(new SymptomStateCountResponseDTO(convertedState, count, dates));
        }

        return statistics;
    }

    private String convertStateValue(String rawState, String stateType) {
        log.debug("Converting state: rawState={}, stateType={}", rawState, stateType);

        try {
            int stateIndex = Integer.parseInt(rawState);

            switch (stateType) {
                case "LEVEL":
                    LevelState[] levelValues = LevelState.values();
                    if (stateIndex >= 0 && stateIndex < levelValues.length) {
                        String result = levelValues[stateIndex].name();
                        log.debug("LEVEL conversion: {} -> {}", stateIndex, result);
                        return result;
                    }
                    log.warn("LEVEL state index {} out of bounds (0-{})", stateIndex, levelValues.length - 1);
                    break;

                case "BINARY":
                    BinaryState[] binaryValues = BinaryState.values();
                    if (stateIndex >= 0 && stateIndex < binaryValues.length) {
                        String result = binaryValues[stateIndex].name();
                        log.debug("BINARY conversion: {} -> {}", stateIndex, result);
                        return result;
                    }
                    log.warn("BINARY state index {} out of bounds (0-{})", stateIndex, binaryValues.length - 1);
                    break;

                case "NUMBER":
                    NumberState[] numberValues = NumberState.values();
                    if (stateIndex >= 0 && stateIndex < numberValues.length) {
                        String result = numberValues[stateIndex].name();
                        log.debug("NUMBER conversion: {} -> {}", stateIndex, result);
                        return result;
                    }
                    log.warn("NUMBER state index {} out of bounds (0-{})", stateIndex, numberValues.length - 1);
                    break;

                case "STOOL":
                    StoolState[] stoolValues = StoolState.values();
                    if (stateIndex >= 0 && stateIndex < stoolValues.length) {
                        String result = stoolValues[stateIndex].name();
                        log.debug("STOOL conversion: {} -> {}", stateIndex, result);
                        return result;
                    }
                    log.warn("STOOL state index {} out of bounds (0-{})", stateIndex, stoolValues.length - 1);
                    break;

                case "SNOT":
                    SnotState[] snotValues = SnotState.values();
                    if (stateIndex >= 0 && stateIndex < snotValues.length) {
                        String result = snotValues[stateIndex].name();
                        log.debug("SNOT conversion: {} -> {}", stateIndex, result);
                        return result;
                    }
                    log.warn("SNOT state index {} out of bounds (0-{})", stateIndex, snotValues.length - 1);
                    break;

                case "URINE":
                    UrineState[] urineValues = UrineState.values();
                    if (stateIndex >= 0 && stateIndex < urineValues.length) {
                        String result = urineValues[stateIndex].name();
                        log.debug("URINE conversion: {} -> {}", stateIndex, result);
                        return result;
                    }
                    log.warn("URINE state index {} out of bounds (0-{})", stateIndex, urineValues.length - 1);
                    break;

                default:
                    log.warn("Unknown state type: {}", stateType);
                    return rawState;
            }
        } catch (NumberFormatException e) {
            log.warn("Failed to parse state as integer: {}", rawState);
            return rawState;
        }

        log.warn("State conversion failed: rawState={}, stateType={}", rawState, stateType);
        return rawState; // fallback to original value
    }

    private List<SymptomStateCountResponseDTO> executeStatisticsQuery(String sql, Long petId, int year, int month) {
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("petId", petId);
        query.setParameter("year", year);
        query.setParameter("month", month);

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        List<SymptomStateCountResponseDTO> statistics = new ArrayList<>();
        for (Object[] result : results) {
            String state = String.valueOf(result[0]);
            Number countNumber = (Number) result[1];
            int count = countNumber.intValue();
            String datesStr = (String) result[2];

            Set<Integer> dateSet = new TreeSet<>();
            if (datesStr != null && !datesStr.isEmpty()) {
                String[] dateArray = datesStr.split(",");
                for (String dateStr : dateArray) {
                    try {
                        dateSet.add(Integer.parseInt(dateStr.trim()));
                    } catch (NumberFormatException e) {
                        log.warn("Failed to parse date: {}", dateStr, e);
                    }
                }
            }
            List<Integer> dates = new ArrayList<>(dateSet);

            statistics.add(new SymptomStateCountResponseDTO(state, count, dates));
        }

        return statistics;
    }

    // ==============================================
    // Ensure Methods - 모든 가능한 enum 값들을 보장 (count=0이어도 포함)
    // ==============================================

    private List<SymptomStateCountResponseDTO> ensureAllLevelStates(List<SymptomStateCountResponseDTO> existing) {
        Map<String, SymptomStateCountResponseDTO> stateMap = new LinkedHashMap<>();

        // 모든 LevelState 값들을 0으로 초기화
        for (LevelState state : LevelState.values()) {
            stateMap.put(state.name(), new SymptomStateCountResponseDTO(state.name(), 0, new ArrayList<>()));
        }

        // 실제 데이터로 덮어쓰기
        for (SymptomStateCountResponseDTO dto : existing) {
            stateMap.put(dto.getState(), dto);
        }

        return new ArrayList<>(stateMap.values());
    }

    private List<SymptomStateCountResponseDTO> ensureAllUrineStates(List<SymptomStateCountResponseDTO> existing) {
        Map<String, SymptomStateCountResponseDTO> stateMap = new LinkedHashMap<>();

        // 모든 UrineState 값들을 0으로 초기화
        for (UrineState state : UrineState.values()) {
            stateMap.put(state.name(), new SymptomStateCountResponseDTO(state.name(), 0, new ArrayList<>()));
        }

        // 실제 데이터로 덮어쓰기
        for (SymptomStateCountResponseDTO dto : existing) {
            stateMap.put(dto.getState(), dto);
        }

        return new ArrayList<>(stateMap.values());
    }

    private List<SymptomStateCountResponseDTO> ensureAllBinaryStates(List<SymptomStateCountResponseDTO> existing) {
        Map<String, SymptomStateCountResponseDTO> stateMap = new LinkedHashMap<>();

        // 모든 BinaryState 값들을 0으로 초기화
        for (BinaryState state : BinaryState.values()) {
            stateMap.put(state.name(), new SymptomStateCountResponseDTO(state.name(), 0, new ArrayList<>()));
        }

        // 실제 데이터로 덮어쓰기
        for (SymptomStateCountResponseDTO dto : existing) {
            stateMap.put(dto.getState(), dto);
        }

        return new ArrayList<>(stateMap.values());
    }

    private List<SymptomStateCountResponseDTO> ensureAllNumberStates(List<SymptomStateCountResponseDTO> existing) {
        Map<String, SymptomStateCountResponseDTO> stateMap = new LinkedHashMap<>();

        // 모든 NumberState 값들을 0으로 초기화
        for (NumberState state : NumberState.values()) {
            stateMap.put(state.name(), new SymptomStateCountResponseDTO(state.name(), 0, new ArrayList<>()));
        }

        // 실제 데이터로 덮어쓰기
        for (SymptomStateCountResponseDTO dto : existing) {
            stateMap.put(dto.getState(), dto);
        }

        return new ArrayList<>(stateMap.values());
    }

    private List<SymptomStateCountResponseDTO> ensureAllStoolStates(List<SymptomStateCountResponseDTO> existing) {
        Map<String, SymptomStateCountResponseDTO> stateMap = new LinkedHashMap<>();

        // 모든 StoolState 값들을 0으로 초기화
        for (StoolState state : StoolState.values()) {
            stateMap.put(state.name(), new SymptomStateCountResponseDTO(state.name(), 0, new ArrayList<>()));
        }

        // 실제 데이터로 덮어쓰기
        for (SymptomStateCountResponseDTO dto : existing) {
            stateMap.put(dto.getState(), dto);
        }

        return new ArrayList<>(stateMap.values());
    }

    private List<SymptomStateCountResponseDTO> ensureAllSnotStates(List<SymptomStateCountResponseDTO> existing) {
        Map<String, SymptomStateCountResponseDTO> stateMap = new LinkedHashMap<>();

        // 모든 SnotState 값들을 0으로 초기화
        for (SnotState state : SnotState.values()) {
            stateMap.put(state.name(), new SymptomStateCountResponseDTO(state.name(), 0, new ArrayList<>()));
        }

        // 실제 데이터로 덮어쓰기
        for (SymptomStateCountResponseDTO dto : existing) {
            stateMap.put(dto.getState(), dto);
        }

        return new ArrayList<>(stateMap.values());
    }

    private List<SymptomStateCountResponseDTO> ensureOXStates(List<SymptomStateCountResponseDTO> existing) {
        Map<String, SymptomStateCountResponseDTO> stateMap = new LinkedHashMap<>();

        // O, X 값들을 0으로 초기화
        stateMap.put("O", new SymptomStateCountResponseDTO("O", 0, new ArrayList<>()));
        stateMap.put("X", new SymptomStateCountResponseDTO("X", 0, new ArrayList<>()));

        // 실제 데이터로 덮어쓰기
        for (SymptomStateCountResponseDTO dto : existing) {
            stateMap.put(dto.getState(), dto);
        }

        return new ArrayList<>(stateMap.values());
    }

    private List<SymptomStateCountResponseDTO> ensureAllLevelStatesExcludeNone(List<SymptomStateCountResponseDTO> existing) {
        Map<String, SymptomStateCountResponseDTO> stateMap = new LinkedHashMap<>();

        // NONE을 제외한 LevelState 값들을 0으로 초기화 (물, 식욕, 기력용)
        for (LevelState state : LevelState.values()) {
            if (state != LevelState.NONE) {
                stateMap.put(state.name(), new SymptomStateCountResponseDTO(state.name(), 0, new ArrayList<>()));
            }
        }

        // 실제 데이터로 덮어쓰기
        for (SymptomStateCountResponseDTO dto : existing) {
            stateMap.put(dto.getState(), dto);
        }

        return new ArrayList<>(stateMap.values());
    }

    private List<SymptomStateCountResponseDTO> ensureAllConvulsionStates(List<SymptomStateCountResponseDTO> existing) {
        Map<String, SymptomStateCountResponseDTO> stateMap = new LinkedHashMap<>();

        // 모든 AbnormalState 값들을 0으로 초기화
        for (AbnormalState state : AbnormalState.values()) {
            stateMap.put(state.name(), new SymptomStateCountResponseDTO(state.name(), 0, new ArrayList<>()));
        }

        // 실제 데이터 덮어쓰기
        for (SymptomStateCountResponseDTO dto : existing) {
            stateMap.put(dto.getState(), dto);
        }

        return new ArrayList<>(stateMap.values());
    }


}