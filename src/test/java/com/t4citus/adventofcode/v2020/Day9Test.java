package com.t4citus.adventofcode.v2020;

import com.t4citus.adventofcode.AbstractTestBase;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;

public class Day9Test extends AbstractTestBase {

    @Value("classpath:/2020/day9.txt" )
    private Resource puzzleInputResource;

    @Value("classpath:/2020/day9_sample.txt" )
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(samplePuzzleInputResource);

        // When
        Xmas xmas = parseLines(lines, 5);
        System.out.println(xmas);

        Long first = findFirst(xmas);

        // Then
        System.out.println("The first number not following the rule is " + first + ".");
        Assertions.assertThat(first).isEqualTo(127);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);

        // When
        Xmas xmas = parseLines(lines, 25);
        Long first = findFirst(xmas);

        // Then
        System.out.println("The first number not following the rule is " + first + ".");
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(samplePuzzleInputResource);

        // When
        Xmas xmas = parseLines(lines, 5);
        Long first = findFirst(xmas);
        Long sum = findSumNumbers(xmas, first);

        // Then
        System.out.println("The sum of the smallest and largest is " + sum + ".");
        Assertions.assertThat(sum).isEqualTo(62);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);

        // When
        Xmas xmas = parseLines(lines, 25);
        Long first = findFirst(xmas);
        Long sum = findSumNumbers(xmas, first);

        // Then
        System.out.println("The sum of the smallest and largest is " + sum + ".");
    }

    @AllArgsConstructor
    @ToString
    private static class Xmas {
        private List<Long> allNumbers;
        private int preambleLength;
    }

    private static Xmas parseLines(List<String> lines, int preambleLength) {
        List<Long> allNumbers = lines.stream()
                .map(Long::parseLong)
                .toList();
        return new Xmas(allNumbers, preambleLength);
    }

    private static Long findFirst(Xmas xmas) {
        List<Long> numbers = xmas.allNumbers;
        int len = xmas.preambleLength;
        boolean found;

        for (int n = xmas.preambleLength; n < numbers.size(); n++) {
            Long num = numbers.get(n);
            List<Long> preamble = numbers.subList(n - len, n);
            // System.out.println("num = " + num + " with preamble " + preamble);
            found = false;
            for (int i = 0; i < len - 1; i++) {
                for (int j = i + 1; j < len; j++) {
                    // System.out.println("Checking " + xmas.preamble.get(i) + " and " + xmas.preamble.get(j) + " to be equal " + num + ".");
                    if (num == preamble.get(i) + preamble.get(j)) {
                        found = true;
                        break;
                    }
                }
                if (found)
                    break;
            }
            if (!found) {
                return num;
            }
        }
        return null;
    }

    private static Long findSumNumbers(Xmas xmas, Long expected) {
        final Long[] allNumbers = xmas.allNumbers.toArray(new Long[0]);
        final int len = allNumbers.length;
        long sum;
        List<Long> sumNumbers;
        for (int i = 0; i < len; i++) {
            sum = 0;
            sumNumbers = new ArrayList<>();
            for (int j = i; j < len; j++) {
                Long num = allNumbers[j];
                sumNumbers.add(num);
                sum += num;
                if (sum == expected) {
                    sumNumbers.sort(Comparator.naturalOrder());
                    Long first = sumNumbers.get(0);
                    Long last = sumNumbers.get(sumNumbers.size() - 1);
                    return first + last;
                }
                if (sum > expected) {
                    break;
                }
            }
        }

        return 0L;
    }
}
