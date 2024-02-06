package com.t4citus.adventofcode.v2017;

import com.t4citus.adventofcode.AbstractTestBase;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Day2Test extends AbstractTestBase {

    @Value("classpath:/2017/day2.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2017/day2_sample1.txt")
    private Resource sample1InputResource;

    @Value("classpath:/2017/day2_sample2.txt")
    private Resource sample2InputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(sample1InputResource);

        // When
        Integer sumOfChecksums = lines.stream()
                .map(Day2Test::parseLine)
                .map(Day2Test::checksum)
                .reduce(0, Integer::sum);

        // Then
        System.out.println("The sum of all checksums is " + sumOfChecksums + ".");
        Assertions.assertThat(sumOfChecksums).isEqualTo(18);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);

        // When
        Integer sumOfChecksums = lines.stream()
                .map(line -> line.replaceAll("\\t", " "))
                .map(Day2Test::parseLine)
                .map(Day2Test::checksum)
                .reduce(0, Integer::sum);

        // Then
        System.out.println("The sum of all checksum is " + sumOfChecksums + ".");
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(sample2InputResource);

        // When
        Integer sumOfFractions = lines.stream()
                .map(Day2Test::parseLine)
                .map(Day2Test::fraction)
                .reduce(0, Integer::sum);

        // Then
        System.out.println("The sum of all fractions is " + sumOfFractions + ".");
        Assertions.assertThat(sumOfFractions).isEqualTo(9);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);

        // When
        Integer sumOfFractions = lines.stream()
                .map(line -> line.replaceAll("\\t", " "))
                .map(Day2Test::parseLine)
                .map(Day2Test::fraction)
                .reduce(0, Integer::sum);

        // Then
        System.out.println("The sum of all fractions is " + sumOfFractions + ".");
    }

    private static List<Integer> parseLine(String line) {
        return Arrays.stream(line.split(" "))
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .map(Integer::parseInt)
                .toList();
    }

    private static int checksum(List<Integer> numbers) {
        List<Integer> sorted = numbers.stream().sorted(Comparator.naturalOrder()).toList();
        int min = sorted.get(0);
        int max = sorted.get(sorted.size() - 1);
        return max - min;
    }

    private static int fraction(List<Integer> numbers) {
        int len = numbers.size();
        for (Integer left : numbers) {
            for (Integer right : numbers) {
                if (left.equals(right)) {
                    continue;
                }
                if (left % right == 0) {
                    return left / right;
                }
            }
        }
        return -1;
    }
}
