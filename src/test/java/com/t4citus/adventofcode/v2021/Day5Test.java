package com.t4citus.adventofcode.v2021;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;

public class Day5Test extends AbstractTestBase {

    @Value("classpath:/2021/day5.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2021/day5.sample.txt")
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final List<Path> paths = parsePaths(lines);
        final Diagram diagram = Diagram.withHorizontalAndVerticalPaths(paths);
        final long count = diagram.countOverlapping();

        // Then
        System.out.println("The number of overlapping paths is " + count);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final List<Path> paths = parsePaths(lines);
        final Diagram diagram = Diagram.withHorizontalAndVerticalPaths(paths);
        final long count = diagram.countOverlapping();

        // Then
        System.out.println("The number of overlapping paths is " + count);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final List<Path> paths = parsePaths(lines);
        final Diagram diagram = Diagram.withAllPaths(paths);
        final long count = diagram.countOverlapping();

        // Then
        System.out.println("The number of overlapping paths is " + count);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final List<Path> paths = parsePaths(lines);
        final Diagram diagram = Diagram.withAllPaths(paths);
        final long count = diagram.countOverlapping();

        // Then
        System.out.println("The number of overlapping paths is " + count);
    }

    private record Path(int x1, int y1, int x2, int y2) {}

    private static List<Path> parsePaths(final List<String> lines) {
        return lines.stream()
                .map(line -> {
                    String[] parts = line.split(" ");
                    String[] start = parts[0].split(",");
                    String[] end = parts[2].split(",");
                    return new Path(
                            Integer.parseInt(start[0].trim()),
                            Integer.parseInt(start[1].trim()),
                            Integer.parseInt(end[0].trim()),
                            Integer.parseInt(end[1].trim()));
                })
                .toList();
    }

    private static String key(int x, int y) {
        return x + ":" + y;
    }

    private static class Diagram {
        private final Map<String, Integer> diagram;

        private Diagram(final Map<String, Integer> diagram) {
            this.diagram = diagram;
        }

        public static Diagram withHorizontalAndVerticalPaths(final List<Path> paths) {
            final Map<String, Integer> diagram = new HashMap<>();
            for (Path path : paths) {
                if (path.x1() == path.x2()) {
                    final int min = Math.min(path.y1(), path.y2());
                    final int max = Math.max(path.y1(), path.y2());

                    for (int i = min; i <= max; i++) {
                        final String key = key(path.x1(), i);
                        final Integer count = diagram.getOrDefault(key, 0);
                        diagram.put(key, count + 1);
                    }
                }
                else if (path.y1() == path.y2()) {
                    final int min = Math.min(path.x1(), path.x2());
                    final int max = Math.max(path.x1(), path.x2());

                    for (int i = min; i <= max; i++) {
                        final String key = key(i, path.y1());
                        final Integer count = diagram.getOrDefault(key, 0);
                        diagram.put(key, count + 1);
                    }
                }
            }
            return new Diagram(diagram);
        }

        public static Diagram withAllPaths(final List<Path> paths) {
            final Map<String, Integer> diagram = new HashMap<>();
            for (Path path : paths) {
                if (path.x1() == path.x2()) {
                    final int min = Math.min(path.y1(), path.y2());
                    final int max = Math.max(path.y1(), path.y2());

                    for (int i = min; i <= max; i++) {
                        final String key = key(path.x1(), i);
                        final Integer count = diagram.getOrDefault(key, 0);
                        diagram.put(key, count + 1);
                    }
                }
                else if (path.y1() == path.y2()) {
                    final int min = Math.min(path.x1(), path.x2());
                    final int max = Math.max(path.x1(), path.x2());

                    for (int i = min; i <= max; i++) {
                        final String key = key(i, path.y1());
                        final Integer count = diagram.getOrDefault(key, 0);
                        diagram.put(key, count + 1);
                    }
                }
                else if (Math.abs(path.x1() - path.x2()) == Math.abs(path.y1() - path.y2())) {
                    if (path.x1() < path.x2() && path.y1() < path.y2()) {
                        path = new Path(path.x2(), path.y2(), path.x1(), path.y1());
                    }
                    if (path.x1() > path.x2() && path.y1() > path.y2()) {
                        final int max = Math.abs(path.x1() - path.x2());
                        for (int i = 0; i <= max; i++) {
                            final String key = key(path.x1() - i, path.y1() - i);
                            final Integer count = diagram.getOrDefault(key, 0);
                            diagram.put(key, count + 1);
                        }
                    }
                    if (path.x1() > path.x2() && path.y1() < path.y2()) {
                        path = new Path(path.x2(), path.y2(), path.x1(), path.y1());
                    }
                    if (path.x1() < path.x2() && path.y1() > path.y2()) {
                        final int max = Math.abs(path.x1() - path.x2());
                        for (int i = 0; i <= max; i++) {
                            final String key = key(path.x1() + i, path.y1() - i);
                            final Integer count = diagram.getOrDefault(key, 0);
                            diagram.put(key, count + 1);
                        }
                    }
                }
            }
            return new Diagram(diagram);
        }

        public long countOverlapping() {
            return diagram.values()
                    .stream()
                    .filter(v -> v >= 2)
                    .count();
        }

        @Override
        public String toString() {
            return this.diagram.toString();
        }
    }
}
