package com.t4citus.adventofcode.v2020;

import com.t4citus.adventofcode.AbstractTestBase;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.Arrays;
import java.util.List;

public class Day3Test extends AbstractTestBase {

    @Value("classpath:/2020/day3.txt" )
    private Resource puzzleInputResource;

    @Value("classpath:/2020/day3_sample.txt" )
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(samplePuzzleInputResource);
        assertLines(lines, 11);

        int right = 3;
        int down = 1;

        // When
        int treeHits = countTreeHits(lines, new Config(right, down));

        // Then
        System.out.println("The slope hit " + treeHits + " trees." );
        Assertions.assertThat(treeHits).isEqualTo(7);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 323);

        int right = 3;
        int down = 1;

        // When
        int treeHits = countTreeHits(lines, new Config(right, down));

        // Then
        System.out.println("The slope hit " + treeHits + " trees." );
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(samplePuzzleInputResource);
        assertLines(lines, 11);

        List<Config> configs = Arrays.asList(
                new Config(1, 1),
                new Config(3, 1),
                new Config(5, 1),
                new Config(7, 1),
                new Config(1, 2)
        );

        // When
        int product = configs.stream()
                .map(config -> countTreeHits(lines, config))
                        .reduce(1, (a, b) -> a * b);

        // Then
        System.out.println("The product of all slope hits is " + product + "." );
        Assertions.assertThat(product).isEqualTo(336);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 323);

        List<Config> configs = Arrays.asList(
                new Config(1, 1),
                new Config(3, 1),
                new Config(5, 1),
                new Config(7, 1),
                new Config(1, 2)
        );

        // When
        List<Integer> treeHits = configs.stream()
                .map(config -> countTreeHits(lines, config))
                .toList();

        long product = 1;
        for (Integer hit : treeHits) {
            product *= hit;
        }

        // Then
        System.out.println("The product of all slope hits is " + product + "." );
    }

    private int countTreeHits(List<String> lines, Config config) {
        int right = config.right;
        int down = config.down;
        int treeHits = 0;
        int pos = 0;

        // When
        for (int i = 0; i < lines.size(); i += down) {
            if (i == 0) {
                continue;
            }
            String line = lines.get(i);
            pos = (pos + right) % line.length();
            if (line.split("" )[pos].equals("#" )) {
                treeHits++;
            }
        }

        return treeHits;
    }

    @AllArgsConstructor
    @ToString
    private static class Config {
        private int right;
        private int down;
    }
}
