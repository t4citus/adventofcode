package com.t4citus.adventofcode.v2024;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;
import java.util.stream.Collectors;

public class Day6Test extends AbstractTestBase {

    @Value("classpath:/2024/day6.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2024/day6.sample.txt")
    private Resource samplePuzzleInputResource;

    private static final Map<Direction, Direction> NEXT_DIR = Map.of(
            Direction.NORTH, Direction.EAST,
            Direction.EAST, Direction.SOUTH,
            Direction.SOUTH, Direction.WEST,
            Direction.WEST, Direction.NORTH);

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final Map<Point, String> grid = parseGrid(lines);
        final int sum = guardPathBeforeLeaving(grid).size();

        // Then
        System.out.println("The sum of visited positions is " + sum);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final Map<Point, String> grid = parseGrid(lines);
        final int sum = guardPathBeforeLeaving(grid).size();

        // Then
        System.out.println("The sum of visited positions is " + sum);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final Map<Point, String> grid = parseGrid(lines);
        final int totalPositions = findObstaclePositionsInGuardPath(grid).size();

        // Then
        System.out.println("The total of different positions for this obstruction is " + totalPositions);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final Map<Point, String> grid = parseGrid(lines);
        final int totalPositions = findObstaclePositionsInGuardPath(grid).size();

        // Then
        System.out.println("The total of different positions for this obstruction is " + totalPositions);
    }

    private record Point(int x, int y) {
    }

    private enum Direction {NORTH, EAST, SOUTH, WEST}

    private static Map<Point, String> parseGrid(final List<String> lines) {
        final Map<Point, String> grid = new HashMap<>();

        int row = 0, col = 0;
        for (String line : lines) {
            for (char ch : line.toCharArray()) {
                grid.put(new Point(col, row), String.valueOf(ch));
                col++;
            }
            row++;
            col = 0;
        }

        return grid;
    }

    @SuppressWarnings("unused")
    private static void printGrid(final Map<Point, String> grid, final Set<Point> visited) {
        final int xMax = grid.keySet().stream().mapToInt(Point::x).max().orElse(0);
        final int yMax = grid.keySet().stream().mapToInt(Point::y).max().orElse(0);

        for (int row = 0; row <= yMax; row++) {
            for (int col = 0; col <= xMax; col++) {
                Point p = new Point(col, row);
                System.out.print(visited.contains(p) ? "x" : grid.getOrDefault(new Point(col, row), " "));
            }
            System.out.println();
        }
    }

    private static Set<Point> guardPathBeforeLeaving(Map<Point, String> grid) {
        // find all obstacles for easier checks
        final Set<Point> obstacles = findObstacles(grid);

        // find the guards starting position
        Point guard = findGuardStartingPoint(grid)
                .orElseThrow();
        Direction dir = Direction.NORTH;

        final Set<Point> visited = new HashSet<>();
        visited.add(guard);

        while (true) {
            Point next = switch (dir) {
                case NORTH -> new Point(guard.x(), guard.y() - 1);
                case EAST -> new Point(guard.x() + 1, guard.y());
                case WEST -> new Point(guard.x() - 1, guard.y());
                case SOUTH -> new Point(guard.x(), guard.y() + 1);
            };

            // if the guard leaves the grid
            if (!grid.containsKey(next)) {
                break;
            }

            if (obstacles.contains(next)) {
                dir = NEXT_DIR.get(dir);
                continue;
            }

            guard = next;
            visited.add(next);
        }

        return visited;
    }

    private static Optional<Point> findGuardStartingPoint(final Map<Point, String> grid) {
        return grid.entrySet().stream()
                .filter(e -> e.getValue().equals("^"))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    private static Set<Point> findObstacles(final Map<Point, String> grid) {
        return grid.entrySet().stream()
                .filter(e -> e.getValue().equals("#"))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    private static Set<Point> findObstaclePositionsInGuardPath(final Map<Point, String> grid) {
        final Set<Point> guardPath = guardPathBeforeLeaving(grid);
        final Set<Point> obstacles = findObstacles(grid);
        final Point start = findGuardStartingPoint(grid).orElseThrow();
        final Set<Point> obstaclesWithCycles = new HashSet<>();

        for (Point pos : guardPath) {
            //  grid.replace(pos, "#");

            Point guard = new Point(start.x(), start.y());
            Direction dir = Direction.NORTH;

            // Map used for cycle detection
            final Map<Point, Direction> cycle = new HashMap<>();
            cycle.put(guard, dir);

            // Add the additional obstacle to the list (will be removed afterward)
            obstacles.add(pos);

            while (true) {
                Point next = switch (dir) {
                    case NORTH -> new Point(guard.x(), guard.y() - 1);
                    case EAST -> new Point(guard.x() + 1, guard.y());
                    case WEST -> new Point(guard.x() - 1, guard.y());
                    case SOUTH -> new Point(guard.x(), guard.y() + 1);
                };

                // if the guard leaves the grid
                if (!grid.containsKey(next)) {
                    break;
                }

                // if the guard is re-entering an old path
                if (cycle.getOrDefault(next, null) == dir) {
                    obstaclesWithCycles.add(pos);
                    break;
                }

                if (obstacles.contains(next)) {
                    dir = NEXT_DIR.get(dir);
                    continue;
                }

                guard = next;
                cycle.putIfAbsent(next, dir);
            }

            // Remove the additional obstacle to the list
            obstacles.remove(pos);
        }

        return obstaclesWithCycles;
    }
}
