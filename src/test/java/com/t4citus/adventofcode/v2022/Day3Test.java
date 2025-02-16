package com.t4citus.adventofcode.v2022;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;
import java.util.stream.Collectors;

public class Day3Test extends AbstractTestBase {

    @Value("classpath:/2022/day3.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2022/day3.sample.txt")
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        int sum = lines.stream()
                .map(Day3Test::parseBackpack)
                .map(Day3Test::intersect)
                .mapToInt(Day3Test::priority)
                .sum();

        // Then
        System.out.println("The sum of all priorities is " + sum);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        int sum = lines.stream()
                .map(Day3Test::parseBackpack)
                .map(Day3Test::intersect)
                .mapToInt(Day3Test::priority)
                .sum();

        // Then
        System.out.println("The sum of all priorities is " + sum);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final int n = lines.size();
        int start = 0;
        int sum = 0;

        while (start < n) {
            sum += priority(getBadge(lines.subList(start, start + 3)));
            start += 3;
        }

        // Then
        System.out.println("The sum of all priorities is " + sum);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final int n = lines.size();
        int start = 0;
        int sum = 0;

        while (start < n) {
            sum += priority(getBadge(lines.subList(start, start + 3)));
            start += 3;
        }

        // Then
        System.out.println("The sum of all priorities is " + sum);
    }

    private record Backpack(Set<String> left, Set<String> right) {}

    private static Backpack parseBackpack(String line) {
        final int len = line.length();

        final Set<String> left = Arrays.stream(line.substring(0, len / 2).split(""))
                .collect(Collectors.toSet());

        final Set<String> right = Arrays.stream(line.substring(len / 2).split(""))
                .collect(Collectors.toSet());

        return new Backpack(left, right);
    }

    private static char getBadge(List<String> backpacks) {
        Set<String> chars = null;
        for (String backpack : backpacks) {
            Set<String> join = Arrays.stream(backpack.split("")).collect(Collectors.toSet());
            if (chars == null) {
                chars = join;
            } else {
                chars.retainAll(join);
            }
        }

        if (chars == null) throw new IllegalStateException();
        return chars.iterator().next().charAt(0);
    }

    private static char intersect(Backpack backpack) {
        Set<String> leftCopy = new HashSet<>(backpack.left());
        leftCopy.retainAll(backpack.right());

        String first = leftCopy.iterator().next();
        return first.charAt(0);
    }

    private static int priority(char ch) {
        if (Character.isLowerCase(ch)) {
            return ch - 'a' + 1;
        }
        if (Character.isUpperCase(ch)) {
            return ch - 'A' + 27;
        }
        return 0;
    }
}
