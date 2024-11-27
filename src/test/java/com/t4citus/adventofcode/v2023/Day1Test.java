package com.t4citus.adventofcode.v2023;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Day1Test extends AbstractTestBase {

    @Value("classpath:/2023/day1.txt")
    private Resource puzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = Arrays.asList(
                "1abc2",
                "pqr3stu8vwx",
                "a1b2c3d4e5f",
                "treb7uchet"
        );

        // When
        Integer sum = lines.stream()
                .map(this::getSum)
                .reduce(Integer::sum)
                .orElse(0);

        // Then
        System.out.println(sum);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1000);

        // When
        Integer sum = lines.stream()
                .map(this::getSum)
                .reduce(Integer::sum)
                .orElse(0);

        // Then
        System.out.println(sum);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = Arrays.asList(
                "two1nine",
                "eightwothree",
                "abcone2threexyz",
                "xtwone3four",
                "4nineeightseven2",
                "zoneight234",
                "7pqrstsixteen"
        );

        // When
        Integer sum = lines.stream()
                .map(this::getSumWithWords)
                .reduce(Integer::sum)
                .orElse(0);

        // Then
        System.out.println(sum);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1000);

        // When
        Integer sum = lines.stream()
                .map(this::getSumWithWords)
                .reduce(Integer::sum)
                .orElse(0);

        // Then
        System.out.println(sum);
    }

    private int getSum(String line) {
        if (line == null || line.isEmpty()) return 0;

        List<Integer> numbers = new ArrayList<>();
        for (char ch : line.toCharArray()) {
            if (ch >= '1' && ch <= '9') {
                numbers.add(ch - '0');
            }
        }

        return 10 * numbers.get(0) + numbers.get(numbers.size() - 1);
    }

    public static final Map<String, Integer> DIGITS_MAP = Map.of(
            "one", 1,
            "two", 2,
            "three", 3,
            "four", 4,
            "five", 5,
            "six", 6,
            "seven", 7,
            "eight", 8,
            "nine", 9
    );

    private int getSumWithWords(String line) {
        if (line == null || line.isEmpty()) return 0;

        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch >= '1' && ch <= '9') {
                numbers.add(ch - '0');
            } else {
                String rest = line.substring(i);
                for (String key : DIGITS_MAP.keySet()) {
                    if (rest.startsWith(key)) {
                        numbers.add(DIGITS_MAP.get(key));
                    }
                }
            }
        }

        return 10 * numbers.get(0) + numbers.get(numbers.size() - 1);
    }
}
