package com.t4citus.adventofcode.v2017;

import com.t4citus.adventofcode.AbstractTestBase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;
import java.util.stream.Stream;

public class Day3Test extends AbstractTestBase {

    private static final int TOP_RIGHT = 0;
    private static final int TOP_LEFT = 1;
    private static final int BOTTOM_LEFT = 2;
    private static final int BOTTOM_RIGHT = 3;

    @Value("classpath:/2017/day3.txt")
    private Resource puzzleInputResource;

    private static Stream<Arguments> dataDistanceTestCases() {
        return Stream.of(
                Arguments.of(1, 0),
                Arguments.of(12, 3),
                Arguments.of(23, 2),
                Arguments.of(1024, 31)
        );
    }

    @ParameterizedTest
    @MethodSource("dataDistanceTestCases")
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected(Integer data, Integer expectedDistance) {
        // Given

        // When
        int distance = distance(data, true);

        // Then
        System.out.println("The distance for the value '" + data + "' is '" + distance + "'.");
        Assertions.assertThat(distance).isEqualTo(expectedDistance);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        int data = Integer.parseInt(lines.get(0));

        // When
        int distance = distance(data, false);

        // Then
        System.out.println("The distance for the value '" + data + "' is '" + distance + "'.");
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        Map<String, Integer> dataMap = new HashMap<>();
        dataMap.put("0:0", 1);
        dataMap.put("1:0", 1);
        dataMap.put("1:1", 2);
        dataMap.put("0:1", 4);
        dataMap.put("-1:1", 5);
        dataMap.put("-1:0", 10);
        dataMap.put("-1:-1", 11);
        dataMap.put("0:-1", 23);
        dataMap.put("1:-1", 25);

        // When
        printCircles(dataMap);

        // Then
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given

        // When

        // Then
    }

    private static int distance(int data, boolean verbose) {
        int circle = 1;
        int circleMax = 1;
        int distance = 0;

        while (true) {
            circleMax = (int) Math.pow(2 * circle - 1, 2);
            if (circleMax >= data) {
                break;
            }
            circle++;
        }

        int min = (int) Math.pow(2 * (circle - 1) - 1, 2) + 1;
        int max = (int) Math.pow(2 * circle - 1, 2);
        int edgeLength = 2 * circle - 1;
        int edgeHalf = (edgeLength - 1) / 2;
        // top right | top left | bottom left | bottom right
        int[] edges = { max - 3 * edgeLength + 3, max - 2 * edgeLength + 2, max - edgeLength + 1, max };

        if (verbose) {
            System.out.println("circle: " + circle);
            System.out.println("min: " + min);
            System.out.println("max: " + max);
            System.out.println("edgeLength: " + edgeLength);
            System.out.println("edgeHalf: " + edgeHalf);
            System.out.println("edges: " + Arrays.toString(edges));
        }

        if (data >= edges[TOP_RIGHT] && data <= edges[TOP_LEFT]) {
            int center = Math.min(edges[TOP_RIGHT], edges[TOP_LEFT]) + edgeHalf;
            int diff = Math.abs(center - data);
            distance = circle -1 + diff;
        } else if (data >= edges[TOP_LEFT] && data <= edges[BOTTOM_LEFT]) {
            int center = Math.min(edges[TOP_LEFT], edges[BOTTOM_LEFT]) + edgeHalf;
            int diff = Math.abs(center - data);
            distance = circle -1 + diff;
        } else if (data >= edges[BOTTOM_LEFT] && data <= edges[BOTTOM_RIGHT]) {
            int center = Math.min(edges[BOTTOM_LEFT], edges[BOTTOM_RIGHT]) + edgeHalf;
            int diff = Math.abs(center - data);
            distance = circle -1 + diff;
        } else if (data >= min - 1 && data <= edges[TOP_LEFT]) { // the min and the max element of each circle are located at the bottom right
            int center = Math.min(min - 1, edges[TOP_LEFT]) + edgeHalf;
            int diff = Math.abs(center - data);
            distance = circle -1 + diff;
        }

        return distance;
    }

    private static void printCircles(Map<String, Integer> dataMap) {
        List<Integer> xValues = dataMap.keySet().stream()
                .map(key -> Integer.parseInt(key.split(":")[0]))
                .sorted(Comparator.naturalOrder())
                .toList();
        List<Integer> yValues = dataMap.keySet().stream()
                .map(key -> Integer.parseInt(key.split(":")[1]))
                .sorted(Comparator.naturalOrder())
                .toList();
        int minX = xValues.get(0);
        int maxX = xValues.get(xValues.size() - 1);
        int minY = yValues.get(0);
        int maxY = yValues.get(yValues.size() - 1);

        List<List<String>> rows = new ArrayList<>();
        for (int y = maxY; y >= minY; y--) {
            List<String> row = new ArrayList<>();
            for (int x = minX; x <= maxX; x++) {
                String key = x + ":" + y;
                Integer data = dataMap.get(key);
                row.add(data != null ? data.toString() : "_");
            }
            rows.add(row);
        }

        rows.forEach(System.out::println);
    }
}
