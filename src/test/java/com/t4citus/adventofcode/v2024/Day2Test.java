package com.t4citus.adventofcode.v2024;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2Test extends AbstractTestBase {

    @Value("classpath:/2024/day2.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2024/day2.sample.txt")
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final List<Report> reports = parseReports(lines);
        final long safeCount = countSafeReports(reports, false);

        // Then
        System.out.println("The number of safe reports is " + safeCount);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final List<Report> reports = parseReports(lines);
        final long safeCount = countSafeReports(reports, false);

        // Then
        System.out.println("The number of safe reports is " + safeCount);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final List<Report> reports = parseReports(lines);
        final long safeCount = countSafeReports(reports, true);

        // Then
        System.out.println("The number of safe reports is " + safeCount);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final List<Report> reports = parseReports(lines);
        final long safeCount = countSafeReports(reports, true);

        // Then
        System.out.println("The number of safe reports is " + safeCount);
    }

    private record Report(List<Integer> levels) {
    }

    private static List<Report> parseReports(final List<String> lines) {
        return lines.stream()
                .map(line -> Arrays.stream(line.split(" ")).map(Integer::parseInt).toList())
                .map(Report::new)
                .toList();
    }

    private static boolean isSafe(final Report report) {
        int negInvalid = 0; // x < -3
        int negValid = 0; // -3 <= x < 0
        int zero = 0; // x = 0
        int posValid = 0; // 0 < x <= -3
        int posInvalid = 0; // x > 3

        for (int i = 1; i < report.levels().size(); i++) {
            int delta = report.levels().get(i) - report.levels().get(i - 1);
            if (delta == 0) zero++;
            else if (delta < -3) negInvalid++;
            else if (delta < 0) negValid++;
            else if (delta > 3) posInvalid++;
            else posValid++;
        }

        if (negInvalid > 0 || posInvalid > 0 || zero > 0)
            return false;

        return (negValid > 0 && posValid == 0) || (negValid == 0 && posValid > 0);
    }

    private static boolean isSafeWithProblemDampener(final Report report) {
        boolean isSafe = isSafe(report);
        if (isSafe) return true;

        final int n = report.levels().size();
        for (int i = 0; i < n; i++) {
            List<Integer> levels = new ArrayList<>(report.levels());
            levels.remove(i);
            isSafe = isSafe(new Report(levels));
            if (isSafe) return true;
        }

        return false;
    }

    private static long countSafeReports(final List<Report> reports, final boolean withProblemDampener) {
        return withProblemDampener
                ? reports.stream().filter(Day2Test::isSafeWithProblemDampener).count()
                : reports.stream().filter(Day2Test::isSafe).count();
    }
}
