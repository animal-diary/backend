package animal.diary.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MonthlyRecordRepository {

    private final EntityManager entityManager;

    public List<Integer> findRecordDatesByPetIdAndMonth(Long petId, LocalDateTime startDate, LocalDateTime endDate) {
        String sql = """
            SELECT DISTINCT EXTRACT(DAY FROM created_at) as record_date
            FROM (
                SELECT created_at FROM weight WHERE pet_id = :petId AND created_at BETWEEN :startDate AND :endDate
                UNION ALL
                SELECT created_at FROM energy WHERE pet_id = :petId AND created_at BETWEEN :startDate AND :endDate
                UNION ALL
                SELECT created_at FROM appetite WHERE pet_id = :petId AND created_at BETWEEN :startDate AND :endDate
                UNION ALL
                SELECT created_at FROM respiratory_rate WHERE pet_id = :petId AND created_at BETWEEN :startDate AND :endDate
                UNION ALL
                SELECT created_at FROM heart_rate WHERE pet_id = :petId AND created_at BETWEEN :startDate AND :endDate
                UNION ALL
                SELECT created_at FROM syncope WHERE pet_id = :petId AND created_at BETWEEN :startDate AND :endDate
                UNION ALL
                SELECT created_at FROM urinary WHERE pet_id = :petId AND created_at BETWEEN :startDate AND :endDate
                UNION ALL
                SELECT created_at FROM significant WHERE pet_id = :petId AND created_at BETWEEN :startDate AND :endDate
                UNION ALL
                SELECT created_at FROM convulsion WHERE pet_id = :petId AND created_at BETWEEN :startDate AND :endDate
                UNION ALL
                SELECT created_at FROM sound WHERE pet_id = :petId AND created_at BETWEEN :startDate AND :endDate
                UNION ALL
                SELECT created_at FROM snot WHERE pet_id = :petId AND created_at BETWEEN :startDate AND :endDate
                UNION ALL
                SELECT created_at FROM vomiting WHERE pet_id = :petId AND created_at BETWEEN :startDate AND :endDate
                UNION ALL
                SELECT created_at FROM walking WHERE pet_id = :petId AND created_at BETWEEN :startDate AND :endDate
                UNION ALL
                SELECT created_at FROM water WHERE pet_id = :petId AND created_at BETWEEN :startDate AND :endDate
                UNION ALL
                SELECT created_at FROM skin WHERE pet_id = :petId AND created_at BETWEEN :startDate AND :endDate
                UNION ALL
                SELECT created_at FROM defecation WHERE pet_id = :petId AND created_at BETWEEN :startDate AND :endDate
            ) all_records
            ORDER BY record_date
            """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("petId", petId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        @SuppressWarnings("unchecked")
        List<Integer> result = query.getResultList();
        return result;
    }
}