package com.t4citus.adventofcode.v2024;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day4Test extends AbstractTestBase {

    @Value("classpath:/2024/day4.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2024/day4.sample.txt")
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final Map<Point, String> points = parsePoints(lines);
        final List<Point> locationsOfX = locationsOf(points, "X");
        final int sum = locationsOfX.stream()
                .mapToInt(x -> countXmas(points, x))
                .sum();

        // Then
        System.out.println("The sum of XMAS strings is " + sum);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final Map<Point, String> points = parsePoints(lines);
        final List<Point> locationsOfX = locationsOf(points, "X");
        final int sum = locationsOfX.stream()
                .mapToInt(x -> countXmas(points, x))
                .sum();

        // Then
        System.out.println("The sum of XMAS strings is " + sum);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final Map<Point, String> points = parsePoints(lines);
        final List<Point> locationsOfA = locationsOf(points, "A");
        final long count = locationsOfA.stream()
                .filter(a -> isMasAsX(points, a))
                .count();

        // Then
        System.out.println("The count of MAS in X-form is " + count);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final Map<Point, String> points = parsePoints(lines);
        final List<Point> locationsOfA = locationsOf(points, "A");
        final long count = locationsOfA.stream()
                .filter(a -> isMasAsX(points, a))
                .count();

        // Then
        System.out.println("The count of MAS in X-form is " + count);
    }

    private record Point(int x, int y) {
    }

    private static Map<Point, String> parsePoints(final List<String> lines) {
        Map<Point, String> points = new HashMap<>();

        int row = 0, col = 0;
        for (String line : lines) {
            for (char ch : line.toCharArray()) {
                points.put(new Point(col, row), String.valueOf(ch));
                col += 1;
            }
            row += 1;
            col = 0;
        }

        return points;
    }

    private static List<Point> locationsOf(final Map<Point, String> points, final String letter) {
        return points.entrySet().stream()
                .filter(e -> e.getValue().equals(letter))
                .map(Map.Entry::getKey)
                .toList();
    }

    private static int countXmas(final Map<Point, String> points, final Point x) {
        int c = 0;

        // n
        if (checkLetters(points, x, new Point(x.x(), x.y() - 1), new Point(x.x(), x.y() - 2), new Point(x.x(), x.y() - 3)))
            c++;

        // w
        if (checkLetters(points, x, new Point(x.x() - 1, x.y()), new Point(x.x() - 2, x.y()), new Point(x.x() - 3, x.y())))
            c++;

        // e
        if (checkLetters(points, x, new Point(x.x() + 1, x.y()), new Point(x.x() + 2, x.y()), new Point(x.x() + 3, x.y())))
            c++;

        // s
        if (checkLetters(points, x, new Point(x.x(), x.y() + 1), new Point(x.x(), x.y() + 2), new Point(x.x(), x.y() + 3)))
            c++;

        // nw
        if (checkLetters(points, x, new Point(x.x() - 1, x.y() - 1), new Point(x.x() - 2, x.y() - 2), new Point(x.x() - 3, x.y() - 3)))
            c++;

        // ne
        if (checkLetters(points, x, new Point(x.x() + 1, x.y() - 1), new Point(x.x() + 2, x.y() - 2), new Point(x.x() + 3, x.y() - 3)))
            c++;

        // sw
        if (checkLetters(points, x, new Point(x.x() - 1, x.y() + 1), new Point(x.x() - 2, x.y() + 2), new Point(x.x() - 3, x.y() + 3)))
            c++;

        // se
        if (checkLetters(points, x, new Point(x.x() + 1, x.y() + 1), new Point(x.x() + 2, x.y() + 2), new Point(x.x() + 3, x.y() + 3)))
            c++;

        return c;
    }

    private static boolean isMasAsX(final Map<Point, String> points, final Point a) {
        int c = 0;

        // nw
        if (checkLetters(points, new Point(a.x() - 1, a.y() - 1), a, new Point(a.x() + 1, a.y() + 1)))
            c++;

        // ne
        if (checkLetters(points, new Point(a.x() + 1, a.y() - 1), a, new Point(a.x() - 1, a.y() + 1)))
            c++;

        // sw
        if (checkLetters(points, new Point(a.x() - 1, a.y() + 1), a, new Point(a.x() + 1, a.y() - 1)))
            c++;

        // se
        if (checkLetters(points, new Point(a.x() + 1, a.y() + 1), a, new Point(a.x() - 1, a.y() - 1)))
            c++;

        // At least 2 diagonals are needed to form an 'X'
        return c >= 2;
    }

    private static boolean checkLetters(final Map<Point, String> points, final Point x, final Point m, final Point a, final Point s) {
        return "X".equals(points.getOrDefault(x, ""))
                && "M".equals(points.getOrDefault(m, ""))
                && "A".equals(points.getOrDefault(a, ""))
                && "S".equals(points.getOrDefault(s, ""));
    }

    private static boolean checkLetters(final Map<Point, String> points, final Point m, final Point a, final Point s) {
        return "M".equals(points.getOrDefault(m, ""))
                && "A".equals(points.getOrDefault(a, ""))
                && "S".equals(points.getOrDefault(s, ""));
    }
}
