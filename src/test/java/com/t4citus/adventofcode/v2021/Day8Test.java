package com.t4citus.adventofcode.v2021;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day7Test extends AbstractTestBase {

    @Value("classpath:/2021/day7.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2021/day7.sample.txt")
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final List<Integer> positions = parsePositions(lines.get(0));
        int fuelConsumption = cheapestPossibleFuelConsumption(positions, n -> n);

        // Then
        System.out.println("The cheapest possible fuel consumption is " + fuelConsumption);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final List<Integer> positions = parsePositions(lines.get(0));
        int fuelConsumption = cheapestPossibleFuelConsumption(positions, n -> n);

        // Then
        System.out.println("The cheapest possible fuel consumption is " + fuelConsumption);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final List<Integer> positions = parsePositions(lines.get(0));
        int fuelConsumption = cheapestPossibleFuelConsumption(positions, n -> n * (n + 1) / 2);

        // Then
        System.out.println("The cheapest possible fuel consumption is " + fuelConsumption);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final List<Integer> positions = parsePositions(lines.get(0));
        int fuelConsumption = cheapestPossibleFuelConsumption(positions, n -> n * (n + 1) / 2);

        // Then
        System.out.println("The cheapest possible fuel consumption is " + fuelConsumption);
    }

    private static List<Integer> parsePositions(final String line) {
        return Arrays.stream(line.split(","))
                .map(Integer::parseInt)
                .toList();
    }

    private static int cheapestPossibleFuelConsumption(final List<Integer> positions, FuelConsumption fuelConsumption) {
        final Map<Integer, Integer> map = new HashMap<>();
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        // find min/max
        for (Integer pos : positions) {
            final Integer val = map.getOrDefault(pos, 0);
            map.put(pos, val + 1);

            min = Math.min(min, pos);
            max = Math.max(max, pos);
        }

        // loop from min to max
        int minDelta = Integer.MAX_VALUE;
        for (int i = min; i <= max; i++) {
            int totalDelta = 0;
            for (Map.Entry<Integer, Integer> pos : map.entrySet()) {
                totalDelta += fuelConsumption.calculate(Math.abs(i - pos.getKey())) * pos.getValue();
            }
            minDelta = Math.min(minDelta, totalDelta);
        }

        return minDelta;
    }

    @FunctionalInterface
    interface FuelConsumption {
        int calculate(int n);
    }
}
