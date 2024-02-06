package com.t4citus.adventofcode.v2020;

import com.t4citus.adventofcode.AbstractTestBase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day6Test extends AbstractTestBase {

    @Value("classpath:/2020/day6.txt" )
    private Resource puzzleInputResource;

    @Value("classpath:/2020/day6_sample.txt" )
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(samplePuzzleInputResource);

        // When
        List<String> groups = parseGroups(lines, "");
        // groups.forEach(System.out::println);

        Integer sum = groups.stream()
                .map(grp -> Arrays.stream(grp.split("")).collect(Collectors.toSet()))
                .map(Set::size)
                .reduce(0, Integer::sum);

        // Then
        System.out.println("The total sum of 'yes' answers is " + sum + ".");
        Assertions.assertThat(sum).isEqualTo(11);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);

        // When
        List<String> groups = parseGroups(lines, "");
        // groups.forEach(System.out::println);

        Integer sum = groups.stream()
                .map(grp -> Arrays.stream(grp.split("")).collect(Collectors.toSet()))
                .map(Set::size)
                .reduce(0, Integer::sum);

        // Then
        System.out.println("The total sum of 'yes' answers is " + sum + ".");
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(samplePuzzleInputResource);

        // When
        List<String> groups = parseGroups(lines, " ");
        groups.forEach(System.out::println);

        Integer sum = groups.stream()
                .map(grp -> {
                    String[] all = grp.split(" ");
                    Set<String> answers = Arrays.stream(all[0].split("")).collect(Collectors.toSet());
                    if (all.length > 1) {
                        for (int i = 1; i < all.length; i++) {
                            answers.retainAll(Arrays.stream(all[i].split("")).collect(Collectors.toSet()));
                        }
                    }
                    return answers.size();
                })
                .reduce(0, Integer::sum);

        // Then
        System.out.println("The total sum of 'yes' answers is " + sum + ".");
        Assertions.assertThat(sum).isEqualTo(6);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);

        // When
        List<String> groups = parseGroups(lines, " ");
        //groups.forEach(System.out::println);

        Integer sum = groups.stream()
                .map(grp -> {
                    String[] all = grp.split(" ");
                    Set<String> answers = Arrays.stream(all[0].split("")).collect(Collectors.toSet());
                    if (all.length > 1) {
                        for (int i = 1; i < all.length; i++) {
                            answers.retainAll(Arrays.stream(all[i].split("")).collect(Collectors.toSet()));
                        }
                    }
                    return answers.size();
                })
                .reduce(0, Integer::sum);

        // Then
        System.out.println("The total sum of 'yes' answers is " + sum + ".");
    }

    private List<String> parseGroups(List<String> lines, String delimiter) {
        List<String> groups = new ArrayList<>();
        List<String> parts = new ArrayList<>();

        for (String line : lines) {
            if (line.trim().length() == 0) {
                groups.add(parts.stream()
                        .map(String::trim)
                        .collect(Collectors.joining(delimiter )));
                parts = new ArrayList<>();
            } else {
                parts.add(line);
            }
        }
        if (!parts.isEmpty()) {
            groups.add(parts.stream()
                    .map(String::trim)
                    .collect(Collectors.joining(delimiter )));
        }

        return groups;
    }
}
