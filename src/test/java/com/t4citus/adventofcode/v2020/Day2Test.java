package com.t4citus.adventofcode.v2020;

import com.t4citus.adventofcode.AbstractTestBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.Arrays;
import java.util.List;

public class Day2Test extends AbstractTestBase {

    @Value("classpath:/2020/day2.txt" )
    private Resource puzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = Arrays.asList(
                "1-3 a: abcde",
                "1-3 b: cdefg",
                "2-9 c: ccccccccc"
        );

        // When
        List<Policy> policies = lines.stream()
                .map(this::parsePolicy)
                .filter(this::fitsPolicy)
                .toList();
        policies.forEach(System.out::println);
        int valid = policies.size();

        // Then
        System.out.println(valid + " policies are valid.");
        Assertions.assertThat(valid).isEqualTo(2);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1000);

        // When
        List<Policy> policies = lines.stream()
                .map(this::parsePolicy)
                .filter(this::fitsPolicy)
                .toList();
        int valid = policies.size();

        // Then
        System.out.println(valid + " policies are valid.");
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = Arrays.asList(
                "1-3 a: abcde",
                "1-3 b: cdefg",
                "2-9 c: ccccccccc"
        );

        // When
        List<Policy> policies = lines.stream()
                .map(this::parsePolicy)
                .filter(this::fitsNewPolicy)
                .toList();
        policies.forEach(System.out::println);
        int valid = policies.size();

        // Then
        System.out.println(valid + " policies are valid.");
        Assertions.assertThat(valid).isEqualTo(1);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1000);

        // When
        List<Policy> policies = lines.stream()
                .map(this::parsePolicy)
                .filter(this::fitsNewPolicy)
                .toList();
        int valid = policies.size();

        // Then
        System.out.println(valid + " policies are valid.");
    }

    private Policy parsePolicy(String line) {
        String[] parts = line.split(" ");
        String[] range = parts[0].split("-");
        String letter = parts[1].replaceAll(":", "");

        return Policy.builder()
                .rangeFrom(Integer.parseInt(range[0]))
                .rangeTo(Integer.parseInt(range[1]))
                .letter(letter)
                .password(parts[2])
                .build();
    }

    private boolean fitsPolicy(Policy policy) {
        long times = Arrays.stream(policy.getPassword().split(""))
                .filter(c -> c.equals(policy.getLetter()))
                .count();

        return times >= policy.getRangeFrom() && times <= policy.getRangeTo();
    }

    private boolean fitsNewPolicy(Policy policy) {
        String[] letters = policy.getPassword().split("");
        return letters[policy.getRangeFrom() - 1].equals(policy.getLetter())
                ^ letters[policy.getRangeTo() - 1].equals(policy.getLetter());
    }

    @AllArgsConstructor
    @Builder
    @Getter
    @ToString
    private static class Policy {
        private int rangeFrom;
        private int rangeTo;
        private String letter;
        private String password;
    }
}
