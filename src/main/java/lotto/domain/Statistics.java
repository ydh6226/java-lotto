package lotto.domain;

import java.util.*;

import static lotto.utils.Validator.checkNotNull;

public class Statistics {

    private static final int MIN_COUNT = 0;
    private static final long DEFAULT_REWARD = 0L;
    
    private final Map<Grade, Long> grades;
    private final Dollars dollars;

    public Statistics(Map<Grade, Long> grades, Dollars dollars) {
        checkArguments(grades, dollars);
        this.grades = grades;
        this.dollars = dollars;
    }

    private void checkArguments(Map<Grade, Long> results, Dollars dollars) {
        checkNotNull(results, dollars);
        checkNonNegativeKey(results);
    }

    private void checkNonNegativeKey(Map<Grade, Long> results) {
        for (Long count : new ArrayList<>(results.values())) {
            checkNonNegative(count);
        }
    }

    private void checkNonNegative(Long count) {
        if (count < MIN_COUNT) {
            throw new IllegalArgumentException("개수가 음수일 수 없습니다.");
        }
    }

    public double yield() {
        return (double) sumReward() / dollars.won();
    }

    private long sumReward() {
        return grades.keySet().stream()
                .map(this::sumGradeReward)
                .reduce(Long::sum)
                .orElse(DEFAULT_REWARD);
    }

    private long sumGradeReward(Grade grade) {
        return grade.getReward() * grades.get(grade);
    }

    public Map<Grade, Long> getReverseOrderedGrades() {
        Map<Grade, Long> reverseOrderedGrades = new TreeMap<>(Collections.reverseOrder());
        reverseOrderedGrades.putAll(grades);
        return Collections.unmodifiableMap(reverseOrderedGrades);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return Objects.equals(grades, that.grades) && Objects.equals(dollars, that.dollars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grades, dollars);
    }
}
