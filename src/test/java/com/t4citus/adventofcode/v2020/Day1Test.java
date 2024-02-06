package com.t4citus.adventofcode.v2020;

import com.t4citus.adventofcode.AbstractTestBase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.Arrays;
import java.util.List;

public class Day1Test extends AbstractTestBase {

    @Value("classpath:/2020/day1.txt")
    private Resource puzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = Arrays.asList("1721", "979", "366", "299", "675", "1456");

        // When
        List<Integer> numbers = lines.stream().map(Integer::parseInt).toList();
        Integer mult = null;

        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size(); j++) {
                if (numbers.get(i) + numbers.get(j) == 2020) {
                    mult = numbers.get(i) * numbers.get(j);
                    break;
                }
            }
            if (mult != null) {
                break;
            }
        }

        // Then
        Assertions.assertThat(mult).isEqualTo(514579);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 200);

        // When
        List<Integer> numbers = lines.stream().map(Integer::parseInt).toList();
        Integer mult = null;

        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size(); j++) {
                if (numbers.get(i) + numbers.get(j) == 2020) {
                    mult = numbers.get(i) * numbers.get(j);
                    break;
                }
            }
            if (mult != null) {
                break;
            }
        }

        // Then
        System.out.println("mult = " + mult);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = Arrays.asList("1721", "979", "366", "299", "675", "1456");

        // When
        List<Long> numbers = lines.stream().map(Long::parseLong).toList();
        Long mult = null;

        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size(); j++) {
                for (int k = j + 1; k < numbers.size(); k++) {
                    if (numbers.get(i) + numbers.get(j) + numbers.get(k) == 2020) {
                        mult = numbers.get(i) * numbers.get(j) * numbers.get(k);
                        break;
                    }
                }
                if (mult != null) {
                    break;
                }
            }
            if (mult != null) {
                break;
            }
        }

        // Then
        Assertions.assertThat(mult).isEqualTo(241861950);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 200);

        // When
        List<Long> numbers = lines.stream().map(Long::parseLong).toList();
        Long mult = null;

        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size(); j++) {
                for (int k = j + 1; k < numbers.size(); k++) {
                    if (numbers.get(i) + numbers.get(j) + numbers.get(k) == 2020) {
                        mult = numbers.get(i) * numbers.get(j) * numbers.get(k);
                        break;
                    }
                }
                if (mult != null) {
                    break;
                }
            }
            if (mult != null) {
                break;
            }
        }

        // Then
        System.out.println("mult = " + mult);
    }
}
