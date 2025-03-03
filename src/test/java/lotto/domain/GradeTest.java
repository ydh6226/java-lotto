package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class GradeTest {

    @DisplayName("일치하는 개수에 맞게 등급을 반환한다.")
    @ParameterizedTest(name = "[{index}] matchCount: {0}, expected: {1}")
    @CsvSource(value = {
            "1000, true, BANG",
            "6, false, FIRST",
            "5, true, BONUS",
            "5, false, SECOND",
            "4, false, THIRD",
            "3, true, FOURTH",
            "2, false, BANG",
            "0, true, BANG",
            "-1, true, BANG"
    })
    void from(int matchCount, boolean matchBonus, Grade expectedGrade) {
        assertThat(Grade.from(matchCount, matchBonus)).isEqualTo(expectedGrade);
    }

    @DisplayName("보너스 등급을 잘 반환하는지")
    @Test
    void from_bonus() {
        assertThat(Grade.from(5, true)).isEqualTo(Grade.BONUS);
    }
    
    @DisplayName("List를 Map으로 잘 변환하는지")
    @Test
    void mapOf() {
        Map<Grade, Long> grades = Grade.mapOf(asList(Grade.BANG, Grade.FIRST, Grade.THIRD, Grade.THIRD, Grade.FIRST));
        assertThat(grades).containsOnly(
                entry(Grade.FIRST, 2L),
                entry(Grade.SECOND, 0L),
                entry(Grade.BONUS, 0L),
                entry(Grade.THIRD, 2L),
                entry(Grade.FOURTH, 0L)
        );
    }

}
