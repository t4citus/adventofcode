package com.t4citus.adventofcode.v2020;

import com.t4citus.adventofcode.AbstractTestBase;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Day10Test extends AbstractTestBase {

    @Value("classpath:/2020/day10.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2020/day10_sample1.txt")
    private Resource sample1InputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(sample1InputResource);

        // When
        List<Integer> parsed = parseLines(lines);
        Result deltas = findDeltas(parsed);

        // Then
        System.out.println("deltas: " + deltas);
        Assertions.assertThat(deltas).isNotNull();
        Assertions.assertThat(deltas.jolt1).isEqualTo(7);
        Assertions.assertThat(deltas.jolt2).isEqualTo(0);
        Assertions.assertThat(deltas.jolt3).isEqualTo(5);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);

        // When
        List<Integer> parsed = parseLines(lines);
        Result deltas = findDeltas(parsed);
        int mult = deltas.jolt1 * deltas.jolt3;

        // Then
        System.out.println("deltas: " + deltas);
        System.out.println("mult: " + mult);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(sample1InputResource);

        // When
        List<Integer> parsed = parseLines(lines);
        long permutations = findPermutations(parsed);

        // Then
        System.out.println("There are " + permutations + " permutations.");
        Assertions.assertThat(permutations).isEqualTo(8);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);

        // When
        List<Integer> parsed = parseLines(lines);
        long permutations = findPermutations(parsed);

        // Then
        // permutations.forEach(System.out::println);
        System.out.println("There are " + permutations + " permutations.");
    }

    @Test
    public void givenSamplePuzzleInput_whenFindDifferences_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(sample1InputResource);

        // When
        List<Integer> parsed = parseLines(lines);
        List<Integer> differences = differences(parsed);

        // Then
        System.out.println(differences);
    }

    private static List<Integer> parseLines(List<String> lines) {
        return lines.stream()
                .map(Integer::parseInt)
                .toList();
    }

    private static Result findDeltas(List<Integer> parsed) {
        List<Integer> adapters = createAdapters(parsed);

        int jolt1 = 0;
        int jolt2 = 0;
        int jolt3 = 0;

        for (int i = 0; i < adapters.size() - 1; i++) {
            int diff = adapters.get(i + 1) - adapters.get(i);
            // System.out.println("from: " + sorted.get(i) + ", to: " + sorted.get(i + 1) + ", diff: " + diff);
            switch (diff) {
                case 1:
                    jolt1++;
                    break;
                case 2:
                    jolt2++;
                    break;
                case 3:
                    jolt3++;
                    break;
                default:
                    throw new IllegalStateException("No available adapter to support [" + adapters.get(i) + "-" + adapters.get(i + 1) + "].");
            }
        }

        return new Result(jolt1, jolt2, jolt3);
    }

    private static Long findPermutations(List<Integer> adapters) {
        List<Integer> differences = differences(adapters);
        int max = differences.stream().max(Integer::compareTo).orElseThrow(() -> new IllegalStateException("No max value found."));
        Map<Integer, Long> tri = tribonacci(max);

        Long sum = differences.stream()
                .map(tri::get)
                .reduce(1L, (a, b) -> a * b);

        return sum;
    }

    private static List<Integer> createAdapters(List<Integer> parsed) {
        List<Integer> adapters = new ArrayList<>(parsed);
        adapters.add(0); // add root (0)
        Integer max = adapters.stream().max(Comparator.naturalOrder()).orElseThrow(() -> new IllegalStateException("No max adapter found."));
        adapters.add(max + 3); // add built-in adapter (max + 3)
        adapters.sort(Comparator.naturalOrder());
        return adapters;
    }

    private static Map<Integer, Long> tribonacci(Integer toValue) {
        // stores key=n, value=permutations
        Map<Integer, Long> cache = new HashMap<>();
        cache.put(0, 1L);
        cache.put(1, 1L);
        cache.put(2, 2L);

        if (toValue < 3) {
            return cache;
        }

        long permutations;
        for (int i = 3; i <= toValue; i++) {
            permutations = cache.get(i - 1) + cache.get(i - 2) + cache.get(i - 3);
            cache.put(i, permutations);
        }
        return cache;
    }

    private static List<Integer> differences(List<Integer> parsed) {
        List<Integer> adapters = createAdapters(parsed);
        List<Integer> blocks = new ArrayList<>();
        boolean isBlock = false;
        int blockSize = 0;
        for (int i = 0; i < adapters.size() - 1; i++) {
            int diff = adapters.get(i + 1) - adapters.get(i);
            if (diff == 1) {
                if (!isBlock) {
                    isBlock = true;
                }
                blockSize++;
            } else {
              if (isBlock) {
                  if (blockSize > 0) {
                      blocks.add(blockSize);
                  }
                  isBlock = false;
                  blockSize = 0;
              }
            }
        }

        return blocks;
    }

    @AllArgsConstructor
    @ToString
    private static class Result {
        private int jolt1;
        private int jolt2;
        private int jolt3;
    }
}
