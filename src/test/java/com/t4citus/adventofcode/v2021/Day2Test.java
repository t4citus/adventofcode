package com.t4citus.adventofcode.v2021;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Day1Test extends AbstractTestBase {

    @Value("classpath:/2021/day1.txt")
    private Resource puzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<Integer> numbers = Arrays.asList(199, 200, 208, 210, 200, 207, 240, 269, 260, 263);

        // When
        int count = countIncreases(numbers);

        // Then
        System.out.println("The number of increases is " + count);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 2000);

        // When
        List<Integer> numbers = lines.stream()
                .map(Integer::parseInt)
                .toList();
        int count = countIncreases(numbers);

        // Then
        System.out.println("The number of increases is " + count);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<Integer> numbers = Arrays.asList(199, 200, 208, 210, 200, 207, 240, 269, 260, 263);

        // When
        int count = countIncreases(slidingWindow(numbers));

        // Then
        System.out.println("The number of increases is " + count);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 2000);

        // When
        List<Integer> numbers = lines.stream()
                .map(Integer::parseInt)
                .toList();
        int count = countIncreases(slidingWindow(numbers));

        // Then
        System.out.println("The number of increases is " + count);
    }

    private static int countIncreases(List<Integer> numbers) {
        int n = numbers.size();
        int count = 0;

        for (int i = 0; i < n - 1; i++) {
            if (numbers.get(i) < numbers.get(i + 1)) {
                count++;
            }
        }

        return count;
    }

    private static List<Integer> slidingWindow(List<Integer> numbers) {
        int n = numbers.size();
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < n - 2; i++) {
            result.add(numbers.get(i) + numbers.get(i + 1) + numbers.get(i + 2));
        }

        return result;
    }
}
