package com.t4citus.adventofcode.v2021;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

public class Day6Test extends AbstractTestBase {

    @Value("classpath:/2021/day6.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2021/day6.sample.txt")
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final List<Integer> initialCounts = parseInitialCounts(lines);
        final long totalCount = populationCountAfterDays(initialCounts, 80);

        // Then
        System.out.println("The total population count is " + totalCount);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final List<Integer> initialCounts = parseInitialCounts(lines);
        final long totalCount = populationCountAfterDays(initialCounts, 80);

        // Then
        System.out.println("The total population count is " + totalCount);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final List<Integer> initialCounts = parseInitialCounts(lines);
        final long totalCount = populationCountAfterDays(initialCounts, 256);

        // Then
        System.out.println("The total population count is " + totalCount);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final List<Integer> initialCounts = parseInitialCounts(lines);
        final long totalCount = populationCountAfterDays(initialCounts, 256);

        // Then
        System.out.println("The total population count is " + totalCount);
    }

    private static List<Integer> parseInitialCounts(final List<String> lines) {
        return Arrays.stream(lines.get(0).split(","))
                .map(Integer::parseInt)
                .toList();
    }

    private static long populationCountAfterDays(final List<Integer> initialCounts, final int days) {
        if (days < 1) throw new IllegalArgumentException("The days parameter cannot be lower than 1.");
        final long[] counts = new long[9];

        // init population counts
        for (Integer c : initialCounts) {
            counts[c]++;
        }

        // loop days
        for (int day = 1; day <= days; day++) {
            long zeros = counts[0]; // preserve before shifting
            for (int i = 1; i < counts.length; i++) {
                counts[i - 1] = counts[i];
                counts[i] = 0;
            }
            if (zeros > 0) {
                counts[6] += zeros;
                counts[8] += zeros;
            }
        }

        return Arrays.stream(counts).reduce(Long::sum).orElse(0);
    }
}
