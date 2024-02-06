package com.t4citus.adventofcode.v2020;

import com.t4citus.adventofcode.AbstractTestBase;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.List;

public class Day12Test extends AbstractTestBase {

    @Value("classpath:/2020/day12.txt" )
    private Resource puzzleInputResource;

    @Value("classpath:/2020/day12_sample1.txt" )
    private Resource sample1InputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(sample1InputResource);

        // When
        List<Instruction> instructions = parseInstructions(lines);
        int distance = distance(instructions);

        // Then
        System.out.println("The distance is " + distance + ".");
        Assertions.assertThat(distance).isEqualTo(25);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);

        // When
        List<Instruction> instructions = parseInstructions(lines);
        int distance = distance(instructions);

        // Then
        System.out.println("The distance is " + distance + ".");
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(sample1InputResource);

        // When
        List<Instruction> instructions = parseInstructions(lines);
        int distance = distanceTwo(instructions);

        // Then
        System.out.println("The distance is " + distance + ".");
        Assertions.assertThat(distance).isEqualTo(286);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);

        // When
        List<Instruction> instructions = parseInstructions(lines);
        int distance = distanceTwo(instructions);

        // Then
        System.out.println("The distance is " + distance + ".");
    }

    private static List<Instruction> parseInstructions(List<String> lines) {
        return lines.stream()
                .map(line -> {
                    String action = line.substring(0, 1);
                    Integer value = Integer.parseInt(line.substring(1));
                    return new Instruction(action, value);
                })
                .toList();
    }

    private static int distance(List<Instruction> instructions) {
        int x = 0;
        int y = 0;
        String dir = "E";

        for (Instruction instruction : instructions) {
            switch (instruction.action) {
                case "N" -> y += instruction.value;
                case "W" -> x -= instruction.value;
                case "E" -> x += instruction.value;
                case "S" -> y -= instruction.value;
                case "L" -> dir = turnLeft(dir, instruction.value);
                case "R" -> dir = turnRight(dir, instruction.value);
                case "F" -> {
                    switch (dir) {
                        case "N" -> y += instruction.value;
                        case "W" -> x -= instruction.value;
                        case "E" -> x += instruction.value;
                        case "S" -> y -= instruction.value;
                    }
                }
            }
        }

        return Math.abs(x) + Math.abs(y);
    }

    private static int distanceTwo(List<Instruction> instructions) {
        Point ship = new Point(0, 0);
        Point waypoint = new Point(10, 1);

        for (Instruction instruction : instructions) {
            switch (instruction.action) {
                case "N" -> waypoint = new Point(waypoint.x, waypoint.y + instruction.value);
                case "W" -> waypoint = new Point(waypoint.x - instruction.value, waypoint.y);
                case "E" -> waypoint = new Point(waypoint.x + instruction.value, waypoint.y);
                case "S" -> waypoint = new Point(waypoint.x, waypoint.y - instruction.value);
                case "L" -> waypoint = turnLeft(waypoint, instruction.value);
                case "R" -> waypoint = turnRight(waypoint, instruction.value);
                case "F" -> ship = new Point(ship.x + (instruction.value * waypoint.x), ship.y + (instruction.value * waypoint.y));
            }
        }

        return Math.abs(ship.x) + Math.abs(ship.y);
    }

    /**
     * Turn left helper function for part 1.
     */
    private static String turnLeft(String direction, int angle) {
        if (angle > 0) {
            switch (direction) {
                case "N" -> {
                    return turnLeft("W", angle - 90);
                }
                case "W" -> {
                    return turnLeft("S", angle - 90);
                }
                case "E" -> {
                    return turnLeft("N", angle - 90);
                }
                case "S" -> {
                    return turnLeft("E", angle - 90);
                }
            }
        }

        return direction;
    }

    /**
     * Turn right helper function for part 1.
     */
    private static String turnRight(String direction, int angle) {
        if (angle > 0) {
            switch (direction) {
                case "N" -> {
                    return turnRight("E", angle - 90);
                }
                case "W" -> {
                    return turnRight("N", angle - 90);
                }
                case "E" -> {
                    return turnRight("S", angle - 90);
                }
                case "S" -> {
                    return turnRight("W", angle - 90);
                }
            }
        }

        return direction;
    }

    /**
     * Turn left helper function for part 2.
     */
    private static Point turnLeft(Point p, int angle) {
        return angle > 0 ? turnLeft(new Point(-1 * p.y, p.x), angle - 90) : new Point(p.x, p.y);
    }

    /**
     * Turn right helper function for part 2.
     */
    private static Point turnRight(Point p, int angle) {
        return angle > 0 ? turnRight(new Point(p.y, -1 * p.x), angle - 90) : new Point(p.x, p.y);
    }

    @AllArgsConstructor
    @ToString
    private static class Instruction {
        private String action;
        private Integer value;
    }

    @AllArgsConstructor
    @ToString
    private static class Point {
        private int x;
        private int y;
    }
}
