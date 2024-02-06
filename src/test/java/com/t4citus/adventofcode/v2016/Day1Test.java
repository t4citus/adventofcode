package com.t4citus.adventofcode.v2016;

import com.t4citus.adventofcode.AbstractTestBase;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;

public class Day1Test extends AbstractTestBase {

    @Value("classpath:/2016/day1.txt")
    private Resource puzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        String line = "R5, L5, R5, R3";
        List<Instruction> instructions = parseInstructions(line);
        System.out.println(instructions);

        // When
        int totalSteps = walk1(instructions);

        // Then
        System.out.println("totalSteps: " + totalSteps);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1);

        // When
        List<Instruction> instructions = parseInstructions(lines.get(0));
        System.out.println(instructions);

        int totalSteps = walk1(instructions);

        // Then
        System.out.println("totalSteps: " + totalSteps);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        String line = "R8, R4, R4, R8";
        List<Instruction> instructions = parseInstructions(line);
        System.out.println(instructions);

        // When
        int totalSteps = walk2(instructions);

        // Then
        System.out.println("totalSteps: " + totalSteps);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1);

        // When
        List<Instruction> instructions = parseInstructions(lines.get(0));
        System.out.println(instructions);

        int totalSteps = walk2(instructions);

        // Then
        System.out.println("totalSteps: " + totalSteps);
    }

    private List<Instruction> parseInstructions(String line) {
        return Arrays.stream(line.split(", "))
                .map(s -> {
                    String dir = s.startsWith("L") ? "L" : "R";
                    int steps = Integer.parseInt(s.substring(1));
                    return new Instruction(dir, steps);
                })
                .toList();
    }

    private int walk1(List<Instruction> instructions) {
        int x = 0;
        int y = 0;
        String currDir = "N";
        for (Instruction instruction : instructions) {
            if ("L".equals(instruction.dir)) {
                if ("N".equals(currDir)) {
                    x = x - instruction.steps;
                    currDir = "W";
                    continue;
                }
                if ("W".equals(currDir)) {
                    y = y - instruction.steps;
                    currDir = "S";
                    continue;
                }
                if ("S".equals(currDir)) {
                    x = x + instruction.steps;
                    currDir = "E";
                    continue;
                }
                if ("E".equals(currDir)) {
                    y = y + instruction.steps;
                    currDir = "N";
                    continue;
                }
            }
            if ("R".equals(instruction.dir)) {
                if ("N".equals(currDir)) {
                    x = x + instruction.steps;
                    currDir = "E";
                    continue;
                }
                if ("W".equals(currDir)) {
                    y = y + instruction.steps;
                    currDir = "N";
                    continue;
                }
                if ("S".equals(currDir)) {
                    x = x - instruction.steps;
                    currDir = "W";
                    continue;
                }
                if ("E".equals(currDir)) {
                    y = y - instruction.steps;
                    currDir = "S";
                    continue;
                }
            }
        }

        return x + y;
    }

    private int walk2(List<Instruction> instructions) {
        int x = 0;
        int y = 0;
        String currDir = "N";
        Set<String> visited = new HashSet<>();
        store(visited, x, y);
        int foundX = 0;
        int foundY = 0;

        for (Instruction instruction : instructions) {
            System.out.println(instruction);
            if ("L".equals(instruction.dir)) {
                if ("N".equals(currDir)) {
                    for (int i = 1; i <= instruction.steps; i++) {
                        x = x - 1;
                        if (store(visited, x, y)) {
                            foundX = x;
                            foundY = y;
                            break;
                        }
                    }
                    // x = x - instruction.steps;
                    currDir = "W";
                }
                else if ("W".equals(currDir)) {
                    for (int i = 1; i <= instruction.steps; i++) {
                        y = y - 1;
                        if (store(visited, x, y)) {
                            foundX = x;
                            foundY = y;
                            break;
                        }
                    }
                    // y = y - instruction.steps;
                    currDir = "S";
                }
                else if ("S".equals(currDir)) {
                    for (int i = 1; i <= instruction.steps; i++) {
                        x = x + 1;
                        if (store(visited, x, y)) {
                            foundX = x;
                            foundY = y;
                            break;
                        }
                    }
                    // x = x + instruction.steps;
                    currDir = "E";
                }
                else if ("E".equals(currDir)) {
                    for (int i = 1; i <= instruction.steps; i++) {
                        y = y + 1;
                        if (store(visited, x, y)) {
                            foundX = x;
                            foundY = y;
                            break;
                        }
                    }
                    // y = y + instruction.steps;
                    currDir = "N";
                }
            }
            else if ("R".equals(instruction.dir)) {
                if ("N".equals(currDir)) {
                    for (int i = 1; i <= instruction.steps; i++) {
                        x = x + 1;
                        if (store(visited, x, y)) {
                            foundX = x;
                            foundY = y;
                            break;
                        }
                    }
                    // /x = x + instruction.steps;
                    currDir = "E";
                }
                else if ("W".equals(currDir)) {
                    for (int i = 1; i <= instruction.steps; i++) {
                        y = y + 1;
                        if (store(visited, x, y)) {
                            foundX = x;
                            foundY = y;
                            break;
                        }
                    }
                    // y = y + instruction.steps;
                    currDir = "N";
                }
                else if ("S".equals(currDir)) {
                    for (int i = 1; i <= instruction.steps; i++) {
                        x = x - 1;
                        if (store(visited, x, y)) {
                            foundX = x;
                            foundY = y;
                            break;
                        }
                    }
                    // x = x - instruction.steps;
                    currDir = "W";
                }
                else if ("E".equals(currDir)) {
                    for (int i = 1; i <= instruction.steps; i++) {
                        y = y - 1;
                        if (store(visited, x, y)) {
                            foundX = x;
                            foundY = y;
                            break;
                        }
                    }
                    // y = y - instruction.steps;
                    currDir = "S";
                }
            }
            System.out.println(visited);
        }

        System.out.println("foundX: " + foundX + ", foundY: " + foundY);
        return foundX + foundY;
    }

    @AllArgsConstructor
    @ToString
    private static class Instruction {
        private String dir;
        private int steps;
    }

    private static boolean store(Set<String> store, int x, int y) {
        String key = x + ":" + y;

        if (store.contains(key)) {
            return true;
        }

        store.add(key);
        return false;
    }
}
