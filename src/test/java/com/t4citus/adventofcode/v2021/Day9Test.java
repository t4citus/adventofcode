package com.t4citus.adventofcode.v2021;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;

public class Day9Test extends AbstractTestBase {

    @Value("classpath:/2021/day9.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2021/day9.sample.txt")
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final Map<Point, Integer> heights = parseHeights(lines);
        final int sum = sumOfRiskLevels(heights);

        // Then
        System.out.println("The sum of the risk levels is " + sum);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final Map<Point, Integer> heights = parseHeights(lines);
        final int sum = sumOfRiskLevels(heights);

        // Then
        System.out.println("The sum of the risk levels is " + sum);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final Map<Point, Integer> heights = parseHeights(lines);
        final int sum = greatestBasinSizes(heights);

        // Then
        System.out.println("The sum of the three greatest basis is " + sum);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final Map<Point, Integer> heights = parseHeights(lines);
        final int sum = greatestBasinSizes(heights);

        // Then
        System.out.println("The sum of the three greatest basis is " + sum);
    }

    private static int sumOfRiskLevels(final Map<Point, Integer> heights) {
        return heights.entrySet().stream()
                .filter(e -> isLow(e.getKey(), heights))
                .mapToInt(e -> e.getValue() + 1)
                .reduce(Integer::sum)
                .orElseThrow(IllegalStateException::new);
    }

    private static int greatestBasinSizes(final Map<Point, Integer> heights) {
        final List<Integer> basinSizes = new ArrayList<>();
        final int MAX_BORDER_HEIGHT = 9;

        final List<Point> lowPoints = heights.keySet().stream()
                .filter(integer -> isLow(integer, heights))
                .toList();

        for (Point lowPoint : lowPoints) {
            final Queue<Point> nextQueue = new LinkedList<>();
            nextQueue.add(lowPoint);
            final Set<Point> visitedPoints = new HashSet<>();
            visitedPoints.add(lowPoint);

            int basinSize = 0;
            while (!nextQueue.isEmpty()) {
                final Point curr = nextQueue.poll();
                basinSize++;

                final List<Point> neighbors = List.of(
                        new Point(curr.x(), curr.y() - 1),
                        new Point(curr.x() - 1, curr.y()),
                        new Point(curr.x() + 1, curr.y()),
                        new Point(curr.x(), curr.y() + 1)
                );

                for (Point neighbor : neighbors) {
                    if (heights.containsKey(neighbor) && !visitedPoints.contains(neighbor) && heights.get(neighbor) < MAX_BORDER_HEIGHT) {
                        nextQueue.add(neighbor);
                        visitedPoints.add(neighbor);
                    }
                }
            }

            basinSizes.add(basinSize);
        }

        basinSizes.sort(Comparator.reverseOrder());
        return basinSizes.stream()
                .limit(3)
                .reduce((a, b) -> a * b)
                .orElse(0);
    }

    private record Point(int x, int y) {
    }

    private static Map<Point, Integer> parseHeights(final List<String> lines) {
        final Map<Point, Integer> heights = new HashMap<>();

        int row = 0;
        int col = 0;
        for (String line : lines) {
            for (String val : line.split("")) {
                heights.put(new Point(col, row), Integer.parseInt(val));
                col += 1;
            }
            col = 0;
            row += 1;
        }

        return heights;
    }

    private static boolean isLow(final Point point, final Map<Point, Integer> heights) {
        final Point top = new Point(point.x(), point.y() - 1);
        final Point left = new Point(point.x() - 1, point.y());
        final Point right = new Point(point.x() + 1, point.y());
        final Point bottom = new Point(point.x(), point.y() + 1);
        final int h = Optional.ofNullable(heights.get(point))
                .orElseThrow(IllegalStateException::new);

        return h < heights.getOrDefault(top, 99)
                && h < heights.getOrDefault(left, 99)
                && h < heights.getOrDefault(right, 99)
                && h < heights.getOrDefault(bottom, 99);
    }
}
