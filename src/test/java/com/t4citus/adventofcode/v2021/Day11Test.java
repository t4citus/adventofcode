package com.t4citus.adventofcode.v2021;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;

public class Day11Test extends AbstractTestBase {

    @Value("classpath:/2021/day11.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2021/day11.sample.txt")
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final Map<Point, Integer> grid = initGrid(lines);
        final int countFlashes = countFlashes(grid, 10);

        // Then
        System.out.println("The total count of flashes is " + countFlashes);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final Map<Point, Integer> grid = initGrid(lines);
        final int countFlashes = countFlashes(grid, 100);

        // Then
        System.out.println("The total count of flashes is " + countFlashes);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final Map<Point, Integer> grid = initGrid(lines);
        final int stepNo = stepNoWhenSynced(grid);

        // Then
        System.out.println("The flashes are all synced at step " + stepNo);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final Map<Point, Integer> grid = initGrid(lines);
        final int stepNo = stepNoWhenSynced(grid);

        // Then
        System.out.println("The flashes are all synced at step " + stepNo);
    }

    private record Point(int x, int y) {
    }

    private static Map<Point, Integer> initGrid(final List<String> lines) {
        final Map<Point, Integer> grid = new HashMap<>();
        int row = 0;
        int col = 0;

        for (String line : lines) {
            for (String s : line.split("")) {
                grid.put(new Point(col, row), Integer.parseInt(s));
                col++;
            }
            row++;
            col = 0;
        }

        return grid;
    }

    private static void printGrid(final Map<Point, Integer> grid) {
        int maxX = grid.keySet().stream().mapToInt(Point::x).max().orElseThrow(IllegalStateException::new);
        int maxY = grid.keySet().stream().mapToInt(Point::y).max().orElseThrow(IllegalStateException::new);

        for (int row = 0; row < maxY; row++) {
            for (int col = 0; col < maxX; col++) {
                System.out.print(grid.get(new Point(col, row)));
            }
            System.out.println();
        }
        System.out.println();
    }

    private enum MODE { STOP_WHEN_IN_SYNC, STOP_AFTER_STEPS }

    private static int countFlashes(final Map<Point, Integer> grid, final int totalSteps) {
        return performSteps(grid, totalSteps, MODE.STOP_AFTER_STEPS);
    }

    private static int stepNoWhenSynced(final Map<Point, Integer> grid) {
        return performSteps(grid, Integer.MAX_VALUE, MODE.STOP_WHEN_IN_SYNC);
    }

    private static int performSteps(final Map<Point, Integer> grid, final int totalSteps, final MODE mode) {
        int totalFlashes = 0;

        for (int step = 1; step <= totalSteps; step++) {
            final Queue<Point> flashQueue = new LinkedList<>();

            // increase all levels by '1'
            grid.keySet().forEach(p -> {
                int newLevel = grid.getOrDefault(p, 0) + 1;
                grid.put(p, newLevel);
                if (newLevel > 9) {
                    flashQueue.add(p);
                }
            });

            final Set<Point> flashed = new HashSet<>(flashQueue);

            // handle flashes
            while (!flashQueue.isEmpty()) {
                Point curr = flashQueue.poll(); // remove from queue
                List<Point> neighbors = neighborsOf(curr);

                neighbors.stream()
                        .filter(grid::containsKey)
                        .forEach(p -> {
                            int newLevel = grid.getOrDefault(p, 0) + 1;
                            grid.put(p, newLevel);
                            if (newLevel > 9 && !flashed.contains(p)) {
                                flashQueue.add(p);
                                flashed.add(p);
                            }
                        });
            }

            for (Point p : flashed) {
                grid.put(p, 0);
                totalFlashes++;
            }

            if (mode == MODE.STOP_WHEN_IN_SYNC && grid.keySet().size() == flashed.size()) {
                return step;
            }
        }

        return totalFlashes;
    }

    private static List<Point> neighborsOf(final Point centre) {
        Point nw = new Point(centre.x() - 1, centre.y() - 1);
        Point n = new Point(centre.x(), centre.y() - 1);
        Point ne = new Point(centre.x() + 1, centre.y() - 1);
        Point w = new Point(centre.x() - 1, centre.y());
        Point e = new Point(centre.x() + 1, centre.y());
        Point sw = new Point(centre.x() - 1, centre.y() + 1);
        Point s = new Point(centre.x(), centre.y() + 1);
        Point se = new Point(centre.x() + 1, centre.y() + 1);
        return List.of(nw, n, ne, w, e, sw, s, se);
    }
}
