package com.t4citus.adventofcode.v2018;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Day1Test extends AbstractTestBase {

    @Value("classpath:/2018/day1.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2018/day1.sample.txt")
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final int sum = sumOf(lines);

        // Then
        System.out.println("The sum of all lines is " + sum + ".");
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final int sum = sumOf(lines);

        // Then
        System.out.println("The sum of all lines is " + sum + ".");
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final int duplicate = findFirstDuplicate(lines);

        // Then
        System.out.println("The duplicate is " + duplicate + ".");
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final int duplicate = findFirstDuplicate(lines);

        // Then
        System.out.println("The duplicate is " + duplicate + ".");
    }

    private int sumOf(final List<String> lines) {
        return lines.stream()
                .map(Integer::parseInt)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private int findFirstDuplicate(final List<String> lines) {
        final Set<Integer> seen = new HashSet<>();
        int sum = 0;
        while (true) {
            for (String line : lines) {
                sum += Integer.parseInt(line);
                if (seen.contains(sum)) {
                    return sum;
                }
                seen.add(sum);
            }
        }
    }
}
