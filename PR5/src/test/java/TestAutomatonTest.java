import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestAutomatonTest {

    @ParameterizedTest(name = "{index}: рядок [{0}] => стан {1}")
    @MethodSource("normalCases")
    void recognizesAllBasicStates(String input, String expectedState) {
        assertEquals(expectedState, TestAutomaton.recognize(input));
    }

    static Stream<Arguments> normalCases() {
        return Stream.of(
                Arguments.of("", "S"),
                Arguments.of("abc", "S"),
                Arguments.of("test", "S"), // Регістр важливий.
                Arguments.of("T", "1"),
                Arguments.of("TE", "2"),
                Arguments.of("TES", "3"),
                Arguments.of("TEST", "F"),
                Arguments.of("abcTESTabc", "F"),
                Arguments.of("abcTES", "3"),
                Arguments.of("TESTabc", "F"),
                Arguments.of("abcTEST", "F"),
                Arguments.of("TESTTEST", "F"),
                Arguments.of("XXT", "1"),
                Arguments.of("XXTE", "2"),
                Arguments.of("XXTES", "3"),
                Arguments.of("TEA", "S"),
                Arguments.of("TE!T", "1"),
                Arguments.of("\u0422EST", "1") // Кирилична Т — інший символ; остання латинська T дає стан 1.
        );
    }

    @ParameterizedTest(name = "{index}: виправлення перекриття [{0}]")
    @MethodSource("overlapCases")
    void findsTestWhenMatchesOverlap(String input) {
        assertNotEquals("F", OriginalAutomaton.recognize(input),
                "Початкова схема помилково не знаходить TEST");
        assertEquals("F", TestAutomaton.recognize(input),
                "Виправлений автомат має знайти TEST");
    }

    static Stream<Arguments> overlapCases() {
        return Stream.of(
                Arguments.of("TTEST"),
                Arguments.of("XTTEST"),
                Arguments.of("TETEST"),
                Arguments.of("TETESTabc"),
                Arguments.of("TTESTT")
        );
    }

    @ParameterizedTest(name = "{index}: правильний проміжний стан [{0}] => {1}")
    @MethodSource("intermediateOverlapCases")
    void keepsPartialMatchesWhenNewTAppears(String input, String expectedState) {
        assertEquals(expectedState, TestAutomaton.recognize(input));
    }

    static Stream<Arguments> intermediateOverlapCases() {
        return Stream.of(
                Arguments.of("TT", "1"),
                Arguments.of("TTE", "2"),
                Arguments.of("TTES", "3"),
                Arguments.of("TET", "1"),
                Arguments.of("TETE", "2"),
                Arguments.of("TETES", "3")
        );
    }
}
