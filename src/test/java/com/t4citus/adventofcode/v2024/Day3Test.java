package com.t4citus.adventofcode.v2024;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3Test extends AbstractTestBase {

    @Value("classpath:/2024/day3.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2024/day3.sample.txt")
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final int sum = sumWithoutInstructions(lines.get(0));

        // Then
        System.out.println("The sum of all multiplications is " + sum);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final int sum = sumWithoutInstructions(toLine(lines));

        // Then
        System.out.println("The sum of all multiplications is " + sum);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final List<String> filtered = Collections.singletonList(lines.get(1));
        final int sum = sumWithInstructions(toLine(filtered));

        // Then
        System.out.println("The sum of all multiplications is " + sum);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final int sum = sumWithInstructions(toLine(lines));

        // Then
        System.out.println("The sum of all multiplications is " + sum);
    }

    private static Map<Integer, String> parseEquations(final String line) {
        final Map<Integer, String> equations = new HashMap<>();
        final Pattern pattern = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)");
        final Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            int startIndex = matcher.start();
            String functionString = matcher.group();
            equations.put(startIndex, functionString);
        }

        return equations;
    }

    private static List<Integer> parseIndexes(final String line, final String regex) {
        final List<Integer> indexes = new ArrayList<>();
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            int startIndex = matcher.start();
            indexes.add(startIndex);
        }

        return indexes;
    }

    private static List<Integer> parseDoIndexes(final String line) {
        return parseIndexes(line, "do\\(\\)");
    }

    private static List<Integer> parseDoNotIndexes(final String line) {
        return parseIndexes(line, "don't\\(\\)");
    }

    private static int parseAndCalculate(final String functionString) {
        final String numbersPart = functionString.substring("mul(".length(), functionString.length() - ")".length());
        final String[] split = numbersPart.split(",");
        return Integer.parseInt(split[0]) * Integer.parseInt(split[1]);
    }

    private static int sumWithoutInstructions(final String line) {
        return parseEquations(line)
                .values()
                .stream()
                .mapToInt(Day3Test::parseAndCalculate)
                .sum();
    }

    private static int sumWithInstructions(final String line) {
        final Map<Integer, String> equations = parseEquations(line);
        final List<Integer> doIndexes = parseDoIndexes(line);
        final List<Integer> doNotIndexes = parseDoNotIndexes(line);

        // collect all indexes for iteration
        final List<Integer> indexes = new ArrayList<>();
        indexes.addAll(equations.keySet());
        indexes.addAll(doIndexes);
        indexes.addAll(doNotIndexes);
        indexes.sort(Comparator.naturalOrder());

        boolean allowNext = true; // At the beginning of the program, mul instructions are enabled.
        int sum = 0;
        for (Integer index : indexes) {
            if (doIndexes.contains(index)) allowNext = true;
            if (doNotIndexes.contains(index)) allowNext = false;

            if (equations.containsKey(index) && allowNext) {
                sum += parseAndCalculate(equations.get(index));
            }
        }

        return sum;
    }

    private static String toLine(final List<String> lines) {
        final StringBuilder sb = new StringBuilder();
        lines.forEach(sb::append);
        return sb.toString();
    }
}
