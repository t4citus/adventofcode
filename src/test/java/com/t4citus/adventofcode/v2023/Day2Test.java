package com.t4citus.adventofcode.v2023;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;

public class Day2Test extends AbstractTestBase {

    @Value("classpath:/2023/day2.txt")
    private Resource puzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = Arrays.asList(
                "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
                "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
                "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
                "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
                "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
        );

        // When
        Integer idSum = lines.stream()
                .map(Game::fromString)
                .filter(game -> game.maxRed() <= 12 && game.maxGreen() <= 13 && game.maxBlue() <= 14)
                .map(Game::id)
                .reduce(Integer::sum)
                .orElse(0);

        // Then
        System.out.println(idSum);
    }

    public record Round(int red, int green, int blue) {
        static Round fromString(String s) {
            int r = 0, g = 0, b = 0;
            for (String outer : s.split(",")) {
                String[] inner = outer.trim().split(" ");
                int amount = Integer.parseInt(inner[0].trim());
                String color = inner[1].trim();
                switch (color) {
                    case "red" -> r = amount;
                    case "green" -> g = amount;
                    case "blue" -> b = amount;
                    default -> throw new IllegalStateException("Unsupported color.");
                }
            }
            return new Round(r, g, b);
        }
    }

    public record Game(int id, List<Round> rounds) {
        static Game fromString(String s) {
            String[] outer = s.split(":");
            String left = outer[0].trim();
            String right = outer[1].trim();
            int id = Integer.parseInt(left.split(" ")[1]);
            List<Round> rounds = Arrays.stream(right.split(";")).map(Round::fromString).toList();
            return new Game(id, rounds);
        }

        public int maxRed() {
            return maxByColor(Round::red);
        }

        public int maxGreen() {
            return maxByColor(Round::green);
        }

        public int maxBlue() {
            return maxByColor(Round::blue);
        }

        public int maxByColor(ToIntFunction<Round> condition) {
            return rounds.stream().mapToInt(condition).max().orElse(0);
        }
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 100);

        // When
        Integer idSum = lines.stream()
                .map(Game::fromString)
                .filter(game -> game.maxRed() <= 12 && game.maxGreen() <= 13 && game.maxBlue() <= 14)
                .map(Game::id)
                .reduce(Integer::sum)
                .orElse(0);

        // Then
        System.out.println(idSum);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = Arrays.asList(
                "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
                "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
                "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
                "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
                "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
        );

        // When
        Integer powSum = lines.stream()
                .map(Game::fromString)
                .map(game -> game.maxRed() * game.maxGreen() * game.maxBlue())
                .reduce(Integer::sum)
                .orElse(0);

        // Then
        System.out.println(powSum);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 100);

        // When
        Integer powSum = lines.stream()
                .map(Game::fromString)
                .map(game -> game.maxRed() * game.maxGreen() * game.maxBlue())
                .reduce(Integer::sum)
                .orElse(0);

        // Then
        System.out.println(powSum);
    }
}
