package com.t4citus.adventofcode.v2021;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2Test extends AbstractTestBase {

    @Value("classpath:/2021/day2.txt")
    private Resource puzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = Arrays.asList(
                "forward 5",
                "down 5",
                "forward 8",
                "up 3",
                "down 8",
                "forward 2"
        );

        // When
        List<Command> commands = lines.stream()
                .map(Day2Test::parseCommand)
                .toList();
        Pos pos = navigate(commands);
        int result = pos.x * pos.depth;

        // Then
        System.out.println("The result of the multiplication is " + result);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1000);

        // When
        List<Command> commands = lines.stream()
                .map(Day2Test::parseCommand)
                .toList();
        Pos pos = navigate(commands);
        int result = pos.x * pos.depth;

        // Then
        System.out.println("The result of the multiplication is " + result);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = Arrays.asList(
                "forward 5",
                "down 5",
                "forward 8",
                "up 3",
                "down 8",
                "forward 2"
        );

        // When
        List<Command> commands = lines.stream()
                .map(Day2Test::parseCommand)
                .toList();
        Pos pos = navigateByManual(commands);
        int result = pos.x * pos.depth;

        // Then
        System.out.println("The result of the multiplication is " + result);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1000);

        // When
        List<Command> commands = lines.stream()
                .map(Day2Test::parseCommand)
                .toList();
        Pos pos = navigateByManual(commands);
        long result = (long) pos.x * (long) pos.depth;

        // Then
        System.out.println("The result of the multiplication is " + result);
    }

    private record Command(String action, Integer steps) {}

    private static Command parseCommand(String line) {
        String[] parts = line.split(" ");
        return new Command(parts[0], Integer.parseInt(parts[1]));
    }

    private record Pos(int x, int depth) {}

    private static Pos navigate(List<Command> commands) {
        int x = 0, depth = 0;

        for (Command command : commands) {
            if ("forward".equals(command.action)) {
                x += command.steps;
            }
            else if ("down".equals(command.action)) {
                depth += command.steps;
            }
            else if ("up".equals(command.action)) {
                depth -= command.steps;
            }
        }

        return new Pos(x, depth);
    }

    private static Pos navigateByManual(List<Command> commands) {
        int x = 0, depth = 0, aim = 0;

        for (Command command : commands) {
            if ("forward".equals(command.action)) {
                x += command.steps;
                depth += aim * command.steps;
            }
            else if ("down".equals(command.action)) {
                aim += command.steps;
            }
            else if ("up".equals(command.action)) {
                aim -= command.steps;
            }
        }

        return new Pos(x, depth);
    }
}
