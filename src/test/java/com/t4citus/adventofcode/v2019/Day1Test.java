package com.t4citus.adventofcode.v2019;

import com.t4citus.adventofcode.AbstractTestBase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;
import java.util.stream.Stream;

public class Day1Test extends AbstractTestBase {

    @Value("classpath:/2019/day1.txt")
    private Resource puzzleInputResource;

    private static Stream<Arguments> massToFuelTestCases() {
        return Stream.of(
                Arguments.of(12, 2),
                Arguments.of(14, 2),
                Arguments.of(1969, 654),
                Arguments.of(100756, 33583)
        );
    }

    private static Stream<Arguments> massAndFuelMassToFuelTestCases() {
        return Stream.of(
                Arguments.of(14, 2),
                Arguments.of(1969, 966),
                Arguments.of(100756, 50346)
        );
    }

    @ParameterizedTest
    @MethodSource("massToFuelTestCases")
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected(final Integer mass, final Integer expectedFuel) {
        // Given

        // When
        int fuel = massToFuel(mass);

        // Then
        Assertions.assertThat(fuel).isEqualTo(expectedFuel);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        int sum = lines.stream()
                .map(Integer::parseInt)
                .mapToInt(Day1Test::massToFuel)
                .sum();

        // Then
        System.out.println("The total fuel is " + sum);
    }

    @ParameterizedTest
    @MethodSource("massAndFuelMassToFuelTestCases")
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected(final Integer mass, final Integer expectedFuel) {
        // Given

        // When
        int fuel = massAndFuelMassToFuel(mass);

        // Then
        Assertions.assertThat(fuel).isEqualTo(expectedFuel);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        int sum = lines.stream()
                .map(Integer::parseInt)
                .mapToInt(Day1Test::massAndFuelMassToFuel)
                .sum();

        // Then
        System.out.println("The total fuel is " + sum);
    }

    private static int massToFuel(int mass) {
        return (int) Math.round(mass / (double) 3 - 0.5) - 2;
    }

    private static int massAndFuelMassToFuel(int mass) {
        int fuel = 0;
        int delta = massToFuel(mass);

        while (delta > 0) {
            fuel += delta;
            delta = massToFuel(delta);
        }

        return fuel;
    }
}
