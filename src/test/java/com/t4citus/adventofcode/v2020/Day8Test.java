package com.t4citus.adventofcode.v2020;

import com.t4citus.adventofcode.AbstractTestBase;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day8Test extends AbstractTestBase {

    @Value("classpath:/2020/day8.txt" )
    private Resource puzzleInputResource;

    @Value("classpath:/2020/day8_sample.txt" )
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(samplePuzzleInputResource);

        // When
        List<Op> operations = parseLines(lines);
        operations.forEach(System.out::println);

        int acc = compute(operations).acc;

        // Then
        System.out.println("The accumulator is " + acc + ".");
        Assertions.assertThat(acc).isEqualTo(5);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);

        // When
        List<Op> operations = parseLines(lines);
        int acc = compute(operations).acc;

        // Then
        System.out.println("The accumulator is " + acc + ".");
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(samplePuzzleInputResource);

        // When
        List<Op> operations = parseLines(lines);
        int acc = computeWithReplace(operations);

        // Then
        System.out.println("The accumulator is " + acc + ".");
        Assertions.assertThat(acc).isEqualTo(8);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);

        // When
        List<Op> operations = parseLines(lines);
        int acc = computeWithReplace(operations);

        // Then
        System.out.println("The accumulator is " + acc + ".");
    }

    private List<Op> parseLines(List<String> lines) {
        return lines.stream()
                .map(this::parseLine)
                .toList();
    }

    private Op parseLine(String line) {
        String[] split = line.split(" ");
        String action = split[0];
        String prefix = split[1].substring(0, 1);
        Integer value = Integer.parseInt(split[1].substring(1));
        return new Op(action, prefix, value);
    }

    @AllArgsConstructor
    @ToString
    private static class Op {
        private String action;
        private String prefix;
        private Integer value;
    }

    @AllArgsConstructor
    @ToString
    private static class Result {
        private Integer acc;
        private boolean success;
    }

    private Result compute(List<Op> operations) {
        int index = 0;
        int acc = 0;
        Set<Integer> processed = new HashSet<>();
        boolean success = false;

        while (true) {
            if (index >= operations.size()) {
                success = true;
                break;
            }
            Op op = operations.get(index);
            if (processed.contains(index)) {
                break;
            }
            processed.add(index);
            switch (op.action) {
                case "nop" -> index++;
                case "acc" -> {
                    if (op.prefix.equals("+")) {
                        acc += op.value;
                    } else {
                        acc -= op.value;
                    }
                    index++;
                }
                case "jmp" -> {
                    if (op.prefix.equals("+")) {
                        index += op.value;
                    } else {
                        index -= op.value;
                    }
                }
            }
        }
        return new Result(acc, success);
    }

    private Integer computeWithReplace(List<Op> operations) {
        int len = operations.size();
        Op op;
        List<Op> replaced;
        Result res;
        Integer acc = 0;

        for (int i = 0; i < len; i++) {
            op = operations.get(i);
            if (op.action.equals("nop")) {
                replaced = clone(operations);
                replaced.get(i).action = "jmp";
                res = compute(replaced);
                if (res.success) {
                    acc = res.acc;
                    System.out.println("found2: " + op + " at index " + i);
                }
            }
            if (op.action.equals("jmp")) {
                replaced = clone(operations);
                replaced.get(i).action = "nop";
                res = compute(replaced);
                if (res.success) {
                    operations.forEach(System.out::println);
                    acc = res.acc;
                    System.out.println("found2: " + op + " at index " + i);
                }
            }
        }

        return acc;
    }

    private List<Op> clone(List<Op> source) {
        return source.stream()
                .map(op -> new Op(op.action, op.prefix, op.value))
                .toList();
    }
}
