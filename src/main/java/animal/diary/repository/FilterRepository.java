package animal.diary.repository;

import animal.diary.dto.request.UrinaryFilterRequestDTO;
import animal.diary.entity.record.Urinary;
import animal.diary.entity.record.state.BinaryState;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import static animal.diary.entity.record.QUrinary.urinary;

@Repository
@RequiredArgsConstructor
public class FilterRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Integer> getUrinaryFilteredDates(Long petId, int year, int month, UrinaryFilterRequestDTO filter) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endExclusive = yearMonth.plusMonths(1).atDay(1).atStartOfDay();
        LocalDateTime endInclusive = endExclusive.minusNanos(1);

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(urinary.pet.id.eq(petId));
        builder.and(urinary.createdAt.between(start, endInclusive));

        // 필터 조건 추가 (null이 아닌 경우만)
        if (filter.getOutput() != null) {
            builder.and(urinary.output.eq(filter.getOutput()));
        }
        if (filter.getState() != null) {
            builder.and(urinary.state.eq(filter.getState()));
        }
        if (filter.getBinaryState() != null) {
            builder.and(urinary.binaryState.eq(filter.getBinaryState()));
        }
        // 메모/이미지 유무 필터 (복수 선택 가능, 쉼표로 구분)
        if (filter.getWithImageOrMemo() != null && !filter.getWithImageOrMemo().trim().isEmpty()) {
            String[] states = filter.getWithImageOrMemo().split(",");
            BooleanBuilder imageOrMemoBuilder = new BooleanBuilder();

            for (String stateStr : states) {
                String trimmedState = stateStr.trim();
                if (trimmedState.equals("O")) {
                    // 메모가 있거나 이미지가 있는 경우
                    imageOrMemoBuilder.or(
                        (urinary.memo.isNotNull().and(urinary.memo.ne("")))
                        .or(urinary.imageUrlsRaw.ne("[]").and(urinary.imageUrlsRaw.ne("")).and(urinary.imageUrlsRaw.isNotNull()))
                    );
                } else if (trimmedState.equals("X")) {
                    // 메모도 없고 이미지도 없는 경우
                    imageOrMemoBuilder.or(
                        (urinary.memo.isNull().or(urinary.memo.eq("")))
                        .and(urinary.imageUrlsRaw.eq("[]").or(urinary.imageUrlsRaw.eq("")).or(urinary.imageUrlsRaw.isNull()))
                    );
                }
            }

            builder.and(imageOrMemoBuilder);
        }




        return jpaQueryFactory
                .select(urinary.createdAt.dayOfMonth())
                .from(urinary)
                .where(builder)
                .distinct()
                .orderBy(urinary.createdAt.dayOfMonth().asc())
                .fetch();
    }
}