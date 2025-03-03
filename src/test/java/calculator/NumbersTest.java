package calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NumbersTest {

    @DisplayName("문자열로 생성")
    @Test
    void from() {
        //given
        String expression = "1,2:3";

        //when
        Numbers numbers = Numbers.from(expression, Calculator.DEFAULT_DELIMITER_PATTERN);

        //then
        Numbers expected = new Numbers(asList(new Number(1), new Number(2), new Number(3)));
        assertThat(numbers).isEqualTo(expected);
    }

    @Test
    void from_illegalExpression_thrownException() {
        //given
        String invalidExpression = ":";
        assertThatThrownBy(() -> Numbers.from(invalidExpression, Calculator.DEFAULT_DELIMITER_PATTERN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("하나 이상의 Number가 필요합니다.");
    }


    @ParameterizedTest(name = "[{index}] {0} = {1}")
    @CsvSource(delimiter = '=', value = {
            "22:2,3 = 27",
            "1:2:100 = 103",
            "1000,100,10:1 = 1111"
    })
    void sum(String expression, int expected) {
        //given
        Numbers numbers = Numbers.from(expression, Calculator.DEFAULT_DELIMITER_PATTERN);

        //when
        int sum = numbers.sum();

        //then
        assertThat(sum).isEqualTo(expected);
    }

}
