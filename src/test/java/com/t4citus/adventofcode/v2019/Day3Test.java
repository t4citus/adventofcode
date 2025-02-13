package com.t4citus.adventofcode.v2019;

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

    @Value("classpath:/2019/day3.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2019/day3.sample.txt")
    private Resource samplePuzzleInputResource;

    private static Stream<Arguments> pathToMinManhattanDistanceTestCases() {
        return Stream.of(
                Arguments.of(0, 1, 6),
                Arguments.of(2, 3, 159),
                Arguments.of(4, 5, 135)
        );
    }

    private static Stream<Arguments> pathToMinDistanceTestCases() {
        return Stream.of(
                Arguments.of(0, 1, 30),
                Arguments.of(2, 3, 610),
                Arguments.of(4, 5, 410)
        );
    }

    @ParameterizedTest
    @MethodSource("pathToMinManhattanDistanceTestCases")
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected(final int line1, final int line2, final int expectedDistance) {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        int minDistance = minManhattanDistance(lines.get(line1), lines.get(line2));

        // Then
        System.out.println("The minimum distance is " + minDistance);
        Assertions.assertThat(minDistance).isEqualTo(expectedDistance);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        int minDistance = minManhattanDistance(lines.get(0), lines.get(1));

        // Then
        System.out.println("The minimum distance is " + minDistance);
    }

    @ParameterizedTest
    @MethodSource("pathToMinDistanceTestCases")
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected(final int line1, final int line2, final int expectedDistance) {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        int minDistance = minDistance(lines.get(line1), lines.get(line2));

        // Then
        System.out.println("The minimum distance is " + minDistance);
        Assertions.assertThat(minDistance).isEqualTo(expectedDistance);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        int minDistance = minDistance(lines.get(0), lines.get(1));

        // Then
        System.out.println("The minimum distance is " + minDistance);
    }

    record Operation (String dir, int dist) {}
    record Point (int x, int y) {}

    private static List<Operation> parseLine(final String line) {
        return Arrays.stream(line.split(","))
                .map(part -> {
                    String dir = part.substring(0, 1);
                    int dist = Integer.parseInt(part.substring(1));
                    return new Operation(dir, dist);
                })
                .toList();
    }

    private static List<Point> walk(final List<Operation> operations) {
        final List<Point> points = new ArrayList<>();
        Point curr = new Point(0, 0);
        points.add(curr);

        for (Operation operation : operations) {
            final int currX = curr.x();
            final int currY = curr.y();
            switch (operation.dir()) {
                case "U" -> {
                    for (int i = 1; i <= operation.dist(); i++) {
                        curr = new Point(currX, currY + i);
                        points.add(curr);
                    }
                }
                case "D" -> {
                    for (int i = 1; i <= operation.dist(); i++) {
                        curr = new Point(currX, currY - i);
                        points.add(curr);
                    }
                }
                case "L" -> {
                    for (int i = 1; i <= operation.dist(); i++) {
                        curr = new Point(currX - i, currY);
                        points.add(curr);
                    }
                }
                case "R" -> {
                    for (int i = 1; i <= operation.dist(); i++) {
                        curr = new Point(currX + i, currY);
                        points.add(curr);
                    }
                }
            }
        }

        return points;
    }

    private static int manhattanDistance(Point p) {
        return Math.abs(p.x()) + Math.abs(p.y());
    }

    private static int minManhattanDistance(String left, String right) {
        final List<Operation> leftOps = parseLine(left);
        final List<Operation> rightOps = parseLine(right);

        final List<Point> leftPath = walk(leftOps);
        final List<Point> rightPath = walk(rightOps);

        final Set<Point> wireCrosses = new HashSet<>(leftPath);
        wireCrosses.retainAll(rightPath);
        wireCrosses.remove(new Point(0, 0));

        return wireCrosses.stream()
                .mapToInt(Day3Test::manhattanDistance)
                .min()
                .orElseThrow(() -> new IllegalStateException("No minimum distance found."));
    }

    private static int minDistance(String left, String right) {
        final List<Operation> leftOps = parseLine(left);
        final List<Operation> rightOps = parseLine(right);

        final List<Point> leftPath = walk(leftOps);
        final List<Point> rightPath = walk(rightOps);

        final Set<Point> wireCrosses = new HashSet<>(leftPath);
        wireCrosses.retainAll(new HashSet<>(rightPath));
        wireCrosses.remove(new Point(0, 0));

        final List<Integer> distances = new ArrayList<>();

        wireCrosses.forEach(wc -> {
            int leftCount = 0;
            int rightCount = 0;

            for (Point p : leftPath) {
                if (p.equals(wc)) break;
                leftCount++;
            }

            for (Point p : rightPath) {
                if (p.equals(wc)) break;
                rightCount++;
            }

            distances.add(leftCount + rightCount);
        });

        return distances.stream()
                .mapToInt(i -> i)
                .min()
                .orElseThrow(() -> new IllegalStateException("No minimum distance found."));
    }
}
