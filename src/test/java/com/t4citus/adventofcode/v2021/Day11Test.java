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
        printGrid(grid, 0);

        for (int step = 1; step <= 2; step++) {
            Queue<Point> queue = new LinkedList<>();
            Set<Point> flashed = new HashSet<>();

            // increase all levels by '1'
            for (Point p : grid.keySet()) {
                int newLevel = grid.getOrDefault(p, 0) + 1;
                grid.put(p, newLevel);

                if (newLevel > 9) {
                    queue.add(p);
                }
            }

            // handle flashes
            while (!queue.isEmpty()) {
                Point curr = queue.poll(); // remove from queue
                int level = grid.getOrDefault(curr, 0);

                if (flashed.contains(curr))
                    break;

                if (level > 9) {
                    // flash
                    grid.put(curr, 0);
                    flashed.add(curr);

                    // add neighbors to queue
                    queue.add(new Point(curr.x() - 1, curr.y() - 1));
                    queue.add(new Point(curr.x(), curr.y() - 1));
                    queue.add(new Point(curr.x() + 1, curr.y() - 1));

                    queue.add(new Point(curr.x() - 1, curr.y()));
                    queue.add(new Point(curr.x() + 1, curr.y()));

                    queue.add(new Point(curr.x() - 1, curr.y() + 1));
                    queue.add(new Point(curr.x(), curr.y() + 1));
                    queue.add(new Point(curr.x() + 1, curr.y() + 1));
                }
                else {
                    int newLevel = grid.getOrDefault(curr, 0) + 1;
                    grid.put(curr, newLevel);
                    if (newLevel > 9) {
                        queue.add(curr);
                    }
                }
            }

            printGrid(grid, step);
        }

        // Then
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When

        // Then
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When

        // Then
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When

        // Then
    }

    private record Point(int x, int y) {}

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

    private static void printGrid(final Map<Point, Integer> grid, int step) {
        System.out.println("Step: " + step);
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                System.out.print(grid.get(new Point(col, row)));
            }
            System.out.println();
        }
        System.out.println();
    }
}
