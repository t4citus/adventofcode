package com.t4citus.adventofcode.v2024;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;

public class Day1Test extends AbstractTestBase {

    @Value("classpath:/2024/day1.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2024/day1.sample.txt")
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final Lists lists = parseLists(lines);
        final int sum = sumDistances(lists);

        // Then
        System.out.println("The sum of the distances is " + sum);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final Lists lists = parseLists(lines);
        final int sum = sumDistances(lists);

        // Then
        System.out.println("The sum of the distances is " + sum);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final Lists lists = parseLists(lines);
        final int score = similarityScore(lists);

        // Then
        System.out.println("The similarity score is " + score);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final Lists lists = parseLists(lines);
        final int score = similarityScore(lists);

        // Then
        System.out.println("The similarity score is " + score);
    }

    private record Lists(List<Integer> left, List<Integer> right) {}

    private static Lists parseLists(final List<String> lines) {
        final List<Integer> left = new ArrayList<>();
        final List<Integer> right = new ArrayList<>();

        lines.forEach(line -> {
            String[] split = line.split(" ");
            left.add(Integer.parseInt(split[0]));
            right.add(Integer.parseInt(split[split.length - 1]));
        });

        left.sort(Comparator.naturalOrder());
        right.sort(Comparator.naturalOrder());

        return new Lists(left, right);
    }

    private static int sumDistances(final Lists lists) {
        final int n = lists.left().size();

        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += Math.abs(lists.left().get(i) - lists.right().get(i));
        }

        return sum;
    }

    private static int similarityScore(final Lists lists) {
        final Map<Integer, Integer> occurrences = new HashMap<>();

        lists.right().forEach(r -> {
            int c = occurrences.getOrDefault(r, 0);
            occurrences.put(r, c + 1);
        });

        int score = 0;
        for (Integer l : lists.left()) {
            int r = occurrences.getOrDefault(l, 0);
            score += l * r;
        }

        return score;
    }
}
