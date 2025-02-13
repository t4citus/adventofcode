package com.t4citus.adventofcode.v2019;

import com.t4citus.adventofcode.AbstractTestBase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Day4Test extends AbstractTestBase {

    private static Stream<Arguments> passwordTestCases() {
        return Stream.of(
                Arguments.of(122345, true),
                Arguments.of(111123, true),
                Arguments.of(135679, false),
                Arguments.of(111111, true),
                Arguments.of(223450, false),
                Arguments.of(123789, false),
                Arguments.of(1234567, false),
                Arguments.of(12345, false),
                Arguments.of(1234, false),
                Arguments.of(123, false),
                Arguments.of(12, false),
                Arguments.of(1, false)
        );
    }

    private static Stream<Arguments> passwordWithGroupsTestCases() {
        return Stream.of(
                Arguments.of(112233, true),
                Arguments.of(123444, false),
                Arguments.of(111122, true),
                Arguments.of(133344, true),
                Arguments.of(134444, false)
        );
    }

    @ParameterizedTest
    @MethodSource("passwordTestCases")
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected(final int password, final boolean expectedValid) {
        // Given

        // When
        boolean valid = isValid(password);

        // Then
        System.out.println("The password is " + (valid ? "valid" : "invalid"));
        Assertions.assertThat(valid).isEqualTo(expectedValid);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final int from = 231832;
        final int to = 767346;

        // When
        int validCount = 0;
        for (int password = from; password <= to; password++) {
            if (isValid(password))
                validCount++;
        }

        // Then
        System.out.println("The number of valid passwords is " + validCount);
    }

    @ParameterizedTest
    @MethodSource("passwordWithGroupsTestCases")
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected(final int password, final boolean expectedValid) {
        // Given

        // When
        boolean valid = isValidWithGroups(password);

        // Then
        System.out.println("The password is " + (valid ? "valid" : "invalid"));
        Assertions.assertThat(valid).isEqualTo(expectedValid);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final int from = 231832;
        final int to = 767346;

        // When
        int validCount = 0;
        for (int password = from; password <= to; password++) {
            if (isValidWithGroups(password)) {
                validCount++;
            }
        }

        // Then
        System.out.println("The number of valid passwords is " + validCount);
    }

    private static boolean isValid(final int password, boolean checkGroups) {
        if (password < 100000) return false;
        if (password > 999999) return false;

        final int[] digits = new int[6];
        int tmpPassword = password;
        for (int i = 5; i >= 0; i--) {
            digits[i] = tmpPassword % 10;
            tmpPassword /= 10;
        }

        boolean hasDouble = false;
        boolean onlyIncreasing = true;
        final Map<Integer, Integer> doubleCounter = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            if (digits[i] == digits[i + 1]) hasDouble = true;
            if (digits[i] > digits[i + 1]) onlyIncreasing = false;
        }

        for (int d : digits) {
            doubleCounter.put(d, doubleCounter.getOrDefault(d, 0) + 1);
        }

        final boolean hasExactlyDouble = doubleCounter.entrySet().stream().anyMatch(entry -> entry.getValue() == 2);

        if (checkGroups)
            return hasDouble && onlyIncreasing && hasExactlyDouble;
        else
            return hasDouble && onlyIncreasing;
    }

    private static boolean isValid(final int password) {
        return isValid(password, false);
    }

    private static boolean isValidWithGroups(final int password) {
        return isValid(password, true);
    }
}
