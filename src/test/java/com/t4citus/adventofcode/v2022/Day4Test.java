package com.t4citus.adventofcode.v2022;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.List;

public class Day4Test extends AbstractTestBase {

    @Value("classpath:/2022/day4.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2022/day4.sample.txt")
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        long count = lines.stream()
                .map(Day4Test::parseSection)
                .filter(Day4Test::overlap)
                .count();

        // Then
        System.out.println("The number of overlapping pairs is " + count);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        long count = lines.stream()
                .map(Day4Test::parseSection)
                .filter(Day4Test::overlap)
                .count();

        // Then
        System.out.println("The number of overlapping pairs is " + count);
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

    private record Section(int s1, int e1, int s2, int e2) { }

    private static Section parseSection(String line) {
        String[] parts = line.split(",");
        String[] left = parts[0].split("-");
        String[] right = parts[1].split("-");
        return new Section(
                Integer.parseInt(left[0]),
                Integer.parseInt(left[1]),
                Integer.parseInt(right[0]),
                Integer.parseInt(right[1]));
    }

    private static boolean overlap(Section section) {
        if (section.s1() <= section.s2() && section.e1() >= section.e2())
            return true;

        return section.s2() <= section.s1() && section.e2() >= section.e1();
    }
}
