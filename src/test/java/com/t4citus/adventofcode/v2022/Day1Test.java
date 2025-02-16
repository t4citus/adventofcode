package com.t4citus.adventofcode.v2022;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;

public class Day1Test extends AbstractTestBase {

    @Value("classpath:/2022/day1.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2022/day1.sample.txt")
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final List<Integer> calories = parseLines(lines);
        final int max = max(calories);

        // Then
        System.out.println("The max calories are " + max);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final List<Integer> calories = parseLines(lines);
        final int max = max(calories);

        // Then
        System.out.println("The max calories are " + max);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final List<Integer> calories = parseLines(lines);
        final int sum = sumOfTop3(calories);

        // Then
        System.out.println("The sum of the three biggest calories is " + sum);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final List<Integer> calories = parseLines(lines);
        final int sum = sumOfTop3(calories);

        // Then
        System.out.println("The sum of the three biggest calories is " + sum);
    }

    private static List<Integer> parseLines(final List<String> lines) {
        final List<Integer> calories = new ArrayList<>();

        int perOne = 0;
        for (String line : lines) {
            if (line.isEmpty()) {
                calories.add(perOne);
                perOne = 0;
            } else {
                perOne += Integer.parseInt(line);
            }
        }
        if (perOne > 0) {
            calories.add(perOne);
        }

        return calories;
    }

    private static int max(final List<Integer> calories) {
        return calories.stream().mapToInt(i -> i).max().orElse(-1);
    }

    private static int sumOfTop3(final List<Integer> calories) {
        return calories.stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .mapToInt(i -> i)
                .sum();
    }
}
