package animal.diary.repository;

import animal.diary.dto.request.ConvulsionFilterRequestDTO;
import animal.diary.dto.request.UrinaryFilterRequestDTO;
import animal.diary.dto.request.DefecationFilterRequestDTO;
import animal.diary.dto.request.SkinFilterRequestDTO;
import animal.diary.dto.request.SnotFilterRequestDTO;
import animal.diary.dto.request.VomitingFilterRequestDTO;
import animal.diary.dto.request.WaterFilterRequestDTO;
import animal.diary.dto.request.AppetiteFilterRequestDTO;
import animal.diary.dto.request.EnergyFilterRequestDTO;
import animal.diary.dto.request.SyncopeFilterRequestDTO;
import animal.diary.entity.record.state.AbnormalState;
import animal.diary.entity.record.state.BinaryState;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import static animal.diary.entity.record.QUrinary.urinary;
import static animal.diary.entity.record.QDefecation.defecation;
import static animal.diary.entity.record.QConvulsion.convulsion;
import static animal.diary.entity.record.QSkin.skin;
import static animal.diary.entity.record.QSnot.snot;
import static animal.diary.entity.record.QVomiting.vomiting;
import static animal.diary.entity.record.QWater.water;
import static animal.diary.entity.record.QAppetite.appetite;
import static animal.diary.entity.record.QEnergy.energy;
import static animal.diary.entity.record.QSyncope.syncope;

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

    public List<Integer> getDefecationFilteredDates(Long petId, int year, int month, DefecationFilterRequestDTO filter) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endExclusive = yearMonth.plusMonths(1).atDay(1).atStartOfDay();
        LocalDateTime endInclusive = endExclusive.minusNanos(1);

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(defecation.pet.id.eq(petId));
        builder.and(defecation.createdAt.between(start, endInclusive));

        // 필터 조건 추가 (null이 아닌 경우만)
        if (filter.getLevel() != null) {
            builder.and(defecation.level.eq(filter.getLevel()));
        }
        if (filter.getState() != null) {
            builder.and(defecation.state.eq(filter.getState()));
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
                            (defecation.memo.isNotNull().and(defecation.memo.ne("")))
                                    .or(defecation.imageUrlsRaw.ne("[]").and(defecation.imageUrlsRaw.ne("")).and(defecation.imageUrlsRaw.isNotNull()))
                    );
                } else if (trimmedState.equals("X")) {
                    // 메모도 없고 이미지도 없는 경우
                    imageOrMemoBuilder.or(
                            (defecation.memo.isNull().or(defecation.memo.eq("")))
                                    .and(defecation.imageUrlsRaw.eq("[]").or(defecation.imageUrlsRaw.eq("")).or(defecation.imageUrlsRaw.isNull()))
                    );
                }
            }

            builder.and(imageOrMemoBuilder);
        }

        return jpaQueryFactory
                .select(defecation.createdAt.dayOfMonth())
                .from(defecation)
                .where(builder)
                .distinct()
                .orderBy(defecation.createdAt.dayOfMonth().asc())
                .fetch();
    }

    // ===================================== 경련 필터링
    public List<Integer> getConvulsionFilteredDates(Long petId, int year, int month, ConvulsionFilterRequestDTO filter) {
        // 경련에 메모는 존재하지 않음.
        // 사진들이 아니라 비디오 한 개 파일임

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endExclusive = yearMonth.plusMonths(1).atDay(1).atStartOfDay();
        LocalDateTime endInclusive = endExclusive.minusNanos(1);

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(convulsion.pet.id.eq(petId));
        builder.and(convulsion.createdAt.between(start, endInclusive));
        // 필터 조건 추가 (null이 아닌 경우만)
        if (filter.getBinaryState() != null) {
            builder.and(convulsion.state.eq(BinaryState.valueOf(filter.getBinaryState())));
        }
        // abnormalState 필터 추가 (여러 개 선택 가능
        if (filter.getAbnormalState() != null && !filter.getAbnormalState().trim().isEmpty()) {
            String[] states = filter.getAbnormalState().split(",");
            BooleanBuilder abnormalStateBuilder = new BooleanBuilder();

            for (String stateStr : states) {
                String trimmedState = stateStr.trim();
                abnormalStateBuilder.or(convulsion.abnormalState.contains(AbnormalState.valueOf(trimmedState)));
            }

            builder.and(abnormalStateBuilder);
        }

        // 메모 없음, video 유무 필터 (복수 선택 가능, 쉼표로 구분)
        if (filter.getWithVideo() != null && !filter.getWithVideo().trim().isEmpty()) {
            String[] states = filter.getWithVideo().split(",");
            BooleanBuilder imageOrMemoBuilder = new BooleanBuilder();

            for (String stateStr : states) {
                String trimmedState = stateStr.trim();
                if (trimmedState.equals("O")) {
                    // 비디오가 있는 경우
                    imageOrMemoBuilder.or(
                            convulsion.videoUrl.isNotNull().and(convulsion.videoUrl.ne(""))
                    );
                } else if (trimmedState.equals("X")) {
                    // 비디오가 없는 경우
                    imageOrMemoBuilder.or(
                            convulsion.videoUrl.isNull().or(convulsion.videoUrl.eq(""))
                    );
                }
            }

            builder.and(imageOrMemoBuilder);
        }

        return jpaQueryFactory
                .select(convulsion.createdAt.dayOfMonth())
                .from(convulsion)
                .where(builder)
                .distinct()
                .orderBy(convulsion.createdAt.dayOfMonth().asc())
                .fetch();

    }

    // ===================================== 피부 필터링
    public List<Integer> getSkinFilteredDates(Long petId, int year, int month, SkinFilterRequestDTO filter) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endExclusive = yearMonth.plusMonths(1).atDay(1).atStartOfDay();
        LocalDateTime endInclusive = endExclusive.minusNanos(1);

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(skin.pet.id.eq(petId));
        builder.and(skin.createdAt.between(start, endInclusive));

        // 필터 조건 추가 (null이 아닌 경우만)
        if (filter.getState() != null) {
            builder.and(skin.state.eq(filter.getState()));
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
                            (skin.memo.isNotNull().and(skin.memo.ne("")))
                                    .or(skin.imageUrlsRaw.ne("[]").and(skin.imageUrlsRaw.ne("")).and(skin.imageUrlsRaw.isNotNull()))
                    );
                } else if (trimmedState.equals("X")) {
                    // 메모도 없고 이미지도 없는 경우
                    imageOrMemoBuilder.or(
                            (skin.memo.isNull().or(skin.memo.eq("")))
                                    .and(skin.imageUrlsRaw.eq("[]").or(skin.imageUrlsRaw.eq("")).or(skin.imageUrlsRaw.isNull()))
                    );
                }
            }

            builder.and(imageOrMemoBuilder);
        }

        return jpaQueryFactory
                .select(skin.createdAt.dayOfMonth())
                .from(skin)
                .where(builder)
                .distinct()
                .orderBy(skin.createdAt.dayOfMonth().asc())
                .fetch();
    }

    // ===================================== 콧물 필터링
    public List<Integer> getSnotFilteredDates(Long petId, int year, int month, SnotFilterRequestDTO filter) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endExclusive = yearMonth.plusMonths(1).atDay(1).atStartOfDay();
        LocalDateTime endInclusive = endExclusive.minusNanos(1);

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(snot.pet.id.eq(petId));
        builder.and(snot.createdAt.between(start, endInclusive));

        // 필터 조건 추가 (null이 아닌 경우만)
        if (filter.getState() != null) {
            builder.and(snot.state.eq(filter.getState()));
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
                            (snot.memo.isNotNull().and(snot.memo.ne("")))
                                    .or(snot.imageUrlsRaw.ne("[]").and(snot.imageUrlsRaw.ne("")).and(snot.imageUrlsRaw.isNotNull()))
                    );
                } else if (trimmedState.equals("X")) {
                    // 메모도 없고 이미지도 없는 경우
                    imageOrMemoBuilder.or(
                            (snot.memo.isNull().or(snot.memo.eq("")))
                                    .and(snot.imageUrlsRaw.eq("[]").or(snot.imageUrlsRaw.eq("")).or(snot.imageUrlsRaw.isNull()))
                    );
                }
            }

            builder.and(imageOrMemoBuilder);
        }

        return jpaQueryFactory
                .select(snot.createdAt.dayOfMonth())
                .from(snot)
                .where(builder)
                .distinct()
                .orderBy(snot.createdAt.dayOfMonth().asc())
                .fetch();
    }

    // ===================================== 구토 필터링
    public List<Integer> getVomitingFilteredDates(Long petId, int year, int month, VomitingFilterRequestDTO filter) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endExclusive = yearMonth.plusMonths(1).atDay(1).atStartOfDay();
        LocalDateTime endInclusive = endExclusive.minusNanos(1);

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(vomiting.pet.id.eq(petId));
        builder.and(vomiting.createdAt.between(start, endInclusive));

        // 필터 조건 추가 (null이 아닌 경우만)
        if (filter.getState() != null) {
            builder.and(vomiting.state.eq(filter.getState()));
        }

        // 이미지 유무 필터 (복수 선택 가능, 쉼표로 구분)
        if (filter.getWithImage() != null && !filter.getWithImage().trim().isEmpty()) {
            String[] states = filter.getWithImage().split(",");
            BooleanBuilder imageBuilder = new BooleanBuilder();

            for (String stateStr : states) {
                String trimmedState = stateStr.trim();
                if (trimmedState.equals("O")) {
                    // 이미지가 있는 경우
                    imageBuilder.or(
                            vomiting.imageUrlsRaw.ne("[]").and(vomiting.imageUrlsRaw.ne("")).and(vomiting.imageUrlsRaw.isNotNull())
                    );
                } else if (trimmedState.equals("X")) {
                    // 이미지가 없는 경우
                    imageBuilder.or(
                            vomiting.imageUrlsRaw.eq("[]").or(vomiting.imageUrlsRaw.eq("")).or(vomiting.imageUrlsRaw.isNull())
                    );
                }
            }

            builder.and(imageBuilder);
        }

        return jpaQueryFactory
                .select(vomiting.createdAt.dayOfMonth())
                .from(vomiting)
                .where(builder)
                .distinct()
                .orderBy(vomiting.createdAt.dayOfMonth().asc())
                .fetch();
    }

    // ===================================== 음수량 필터링
    public List<Integer> getWaterFilteredDates(Long petId, int year, int month, WaterFilterRequestDTO filter) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endExclusive = yearMonth.plusMonths(1).atDay(1).atStartOfDay();
        LocalDateTime endInclusive = endExclusive.minusNanos(1);

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(water.pet.id.eq(petId));
        builder.and(water.createdAt.between(start, endInclusive));

        // 필터 조건 추가 (null이 아닌 경우만)
        if (filter.getState() != null) {
            builder.and(water.state.eq(filter.getState()));
        }

        return jpaQueryFactory
                .select(water.createdAt.dayOfMonth())
                .from(water)
                .where(builder)
                .distinct()
                .orderBy(water.createdAt.dayOfMonth().asc())
                .fetch();
    }

    // ===================================== 식욕 필터링
    public List<Integer> getAppetiteFilteredDates(Long petId, int year, int month, AppetiteFilterRequestDTO filter) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endExclusive = yearMonth.plusMonths(1).atDay(1).atStartOfDay();
        LocalDateTime endInclusive = endExclusive.minusNanos(1);

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(appetite.pet.id.eq(petId));
        builder.and(appetite.createdAt.between(start, endInclusive));

        if (filter.getState() != null) {
            builder.and(appetite.state.eq(filter.getState()));
        }

        return jpaQueryFactory
                .select(appetite.createdAt.dayOfMonth())
                .from(appetite)
                .where(builder)
                .distinct()
                .orderBy(appetite.createdAt.dayOfMonth().asc())
                .fetch();
    }

    // ===================================== 활력도 필터링
    public List<Integer> getEnergyFilteredDates(Long petId, int year, int month, EnergyFilterRequestDTO filter) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endExclusive = yearMonth.plusMonths(1).atDay(1).atStartOfDay();
        LocalDateTime endInclusive = endExclusive.minusNanos(1);

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(energy.pet.id.eq(petId));
        builder.and(energy.createdAt.between(start, endInclusive));

        if (filter.getState() != null) {
            builder.and(energy.state.eq(filter.getState()));
        }

        return jpaQueryFactory
                .select(energy.createdAt.dayOfMonth())
                .from(energy)
                .where(builder)
                .distinct()
                .orderBy(energy.createdAt.dayOfMonth().asc())
                .fetch();
    }

    // ===================================== 실신 필터링
    public List<Integer> getSyncopeFilteredDates(Long petId, int year, int month, SyncopeFilterRequestDTO filter) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endExclusive = yearMonth.plusMonths(1).atDay(1).atStartOfDay();
        LocalDateTime endInclusive = endExclusive.minusNanos(1);

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(syncope.pet.id.eq(petId));
        builder.and(syncope.createdAt.between(start, endInclusive));

        if (filter.getState() != null) {
            builder.and(syncope.state.eq(filter.getState()));
        }

        return jpaQueryFactory
                .select(syncope.createdAt.dayOfMonth())
                .from(syncope)
                .where(builder)
                .distinct()
                .orderBy(syncope.createdAt.dayOfMonth().asc())
                .fetch();
    }
}