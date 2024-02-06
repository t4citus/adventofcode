package com.t4citus.adventofcode.v2017;

import com.t4citus.adventofcode.AbstractTestBase;
import lombok.AllArgsConstructor;
import lombok.ToString;
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

    @Value("classpath:/2017/day1.txt")
    private Resource puzzleInputResource;

    private static Stream<Arguments> captchaSumOneTestCases() {
        return Stream.of(
                Arguments.of("1122", "3"),
                Arguments.of("1111", "4"),
                Arguments.of("1234", "0"),
                Arguments.of("91212129", "9")
        );
    }

    private static Stream<Arguments> captchaSumTwoTestCases() {
        return Stream.of(
                Arguments.of("1212", "6"),
                Arguments.of("1221", "0"),
                Arguments.of("123425", "4"),
                Arguments.of("123123", "12"),
                Arguments.of("12131415", "4")
        );
    }

    @ParameterizedTest
    @MethodSource("captchaSumOneTestCases")
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected(String line, int expectedSum) {
        // Given

        // When
        Integer[] captcha = parseCaptcha(line);
        int sum = sumOne(captcha);

        // Then
        Assertions.assertThat(sum).isEqualTo(expectedSum);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);

        // When
        int sum = sumOne(parseCaptcha(lines.get(0)));

        // Then
        System.out.println("The sum is " + sum + ".");
    }

    @ParameterizedTest
    @MethodSource("captchaSumTwoTestCases")
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected(String line, int expectedSum) {
        // Given

        // When
        Integer[] captcha = parseCaptcha(line);
        int sum = sumTwo(captcha);

        // Then
        Assertions.assertThat(sum).isEqualTo(expectedSum);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);

        // When
        int sum = sumTwo(parseCaptcha(lines.get(0)));

        // Then
        System.out.println("The sum is " + sum + ".");
    }

    private static Integer[] parseCaptcha(String line) {
        return Arrays.stream(line.split("")).map(Integer::parseInt).toArray(Integer[]::new);
    }

    private static int sumOne(Integer[] captcha) {
        int sum = 0, len = captcha.length;
        for (int i = 0; i < len - 1; i++) {
            if (Objects.equals(captcha[i], captcha[i + 1])) {
                sum += captcha[i];
            }
        }
        if (Objects.equals(captcha[len - 1], captcha[0])) {
            sum += captcha[len - 1];
        }
        return sum;
    }

    private static int sumTwo(Integer[] captcha) {
        int sum = 0;
        int len = captcha.length;
        int half = len / 2;
        for (int i = 0; i < half; i++) {
            if (captcha[i].equals(captcha[i + half])) {
                sum += captcha[i] + captcha[i + half];
            }
        }
        return sum;
    }
}
