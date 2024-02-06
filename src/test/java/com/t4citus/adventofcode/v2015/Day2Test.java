package com.t4citus.adventofcode.v2015;

import com.t4citus.adventofcode.AbstractTestBase;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.List;

public class Day2Test extends AbstractTestBase {

    @Value("classpath:/2015/day2.txt" )
    private Resource puzzleInputResource;

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1000);

        // When
        int neededPaper = lines.stream()
                .map(this::parseDimensions)
                .map(dim -> dim.surface() + dim.smallestSide())
                .reduce(0, Integer::sum);

        // Then
        System.out.println(neededPaper);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1000);

        // When
        int neededRibbon = lines.stream()
                .map(this::parseDimensions)
                .map(dim -> dim.ribbon() + dim.bow())
                .reduce(0, Integer::sum);

        // Then
        System.out.println(neededRibbon);
    }

    private Dimensions parseDimensions(String line) {
        String[] parts = line.split("x" );
        return new Dimensions(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2]));
    }

    @AllArgsConstructor
    @ToString
    private static class Dimensions {
        private int l;
        private int w;
        private int h;

        int surface() {
            return 2 * l * w + 2 * w * h + 2 * h * l;
        }

        int smallestSide() {
            int s1 = l * w;
            int s2 = w * h;
            int s3 = h * l;

            return Math.min(Math.min(s1, s2), s3);
        }

        int ribbon() {
            int s1 = 2 * l + 2 * w;
            int s2 = 2 * w + 2 * h;
            int s3 = 2 * h + 2 * l;

            return Math.min(Math.min(s1, s2), s3);
        }

        int bow() {
            return l * w * h;
        }
    }
}
