package com.t4citus.adventofcode.v2015;

import com.t4citus.adventofcode.AbstractTestBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

public class Day6Test extends AbstractTestBase {

    @Value("classpath:/2015/day6.txt" )
    private Resource puzzleInputResource;

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() throws NoSuchAlgorithmException {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 300);

        // When
        List<Instruction> instructions = lines.stream()
                .map(Day6Test::parseInstruction)
                .toList();

        Set<String> lit = new HashSet<>();
        Point p = null;
        for (Instruction instruction : instructions) {
            int from_x = instruction.leftTop.x;
            int to_x = instruction.rightBottom.x;
            int from_y = instruction.leftTop.y;
            int to_y = instruction.rightBottom.y;

            for (int y = from_y; y <= to_y; y++) {
                for (int x = from_x; x <= to_x; x++) {
                    p = new Point(x, y);

                    if (instruction.action.equals("turn on")) {
                        lit.add(p.toString());
                    }
                    if (instruction.action.equals("turn off")) {
                        lit.remove(p.toString());
                    }
                    if (instruction.action.equals("toggle")) {
                        if (lit.contains(p.toString())) {
                            lit.remove(p.toString());
                        } else {
                            lit.add(p.toString());
                        }
                    }
                }
            }
        }

        // Then
        System.out.println("lit: " + lit.size());
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() throws NoSuchAlgorithmException {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 300);

        // When
        List<Instruction> instructions = lines.stream()
                .map(Day6Test::parseInstruction)
                .toList();

        Map<String, Integer> brightness = new HashMap<>();
        Point p = null;
        for (Instruction instruction : instructions) {
            int from_x = instruction.leftTop.x;
            int to_x = instruction.rightBottom.x;
            int from_y = instruction.leftTop.y;
            int to_y = instruction.rightBottom.y;

            for (int y = from_y; y <= to_y; y++) {
                for (int x = from_x; x <= to_x; x++) {
                    p = new Point(x, y);

                    if (instruction.action.equals("turn on")) {
                        if (brightness.containsKey(p.toString())) {
                            Integer val = brightness.get(p.toString());
                            brightness.put(p.toString(), val + 1);
                        } else {
                            brightness.put(p.toString(), 1);
                        }
                    }
                    if (instruction.action.equals("turn off")) {
                        if (brightness.containsKey(p.toString())) {
                            Integer val = brightness.get(p.toString());
                            if (val <= 1) {
                                brightness.remove(p.toString());
                            } else {
                                brightness.put(p.toString(), val - 1);
                            }
                        }
                    }
                    if (instruction.action.equals("toggle")) {
                        if (brightness.containsKey(p.toString())) {
                            Integer val = brightness.get(p.toString());
                            brightness.put(p.toString(), val + 2);
                        } else {
                            brightness.put(p.toString(), 2);
                        }
                    }
                }
            }
        }

        Integer totalBrightness = brightness.values().stream()
                .reduce(0, Integer::sum);

        // Then
        System.out.println("totalBrightness: " + totalBrightness);
    }

    private static Instruction parseInstruction(String line) {
        String rest = line;
        String action = null;

        if (line.startsWith("turn on ")) {
            action = "turn on";
            rest = line.substring("turn on ".length());
        }
        else if (line.startsWith("turn off ")) {
            action = "turn off";
            rest = line.substring("turn off ".length());
        }
        else if (line.startsWith("toggle ")) {
            action = "toggle";
            rest = line.substring("toggle ".length());
        }

        String[] fromTo = rest.split(" through ");
        Point from = parsePoint(fromTo[0]);
        Point to = parsePoint(fromTo[1]);

        return Instruction.builder()
                .action(action)
                .leftTop(from)
                .rightBottom(to)
                .build();
    }

    private static Point parsePoint(String s) {
        String[] fromTo = s.trim().split(",");
        return new Point(
                Integer.parseInt(fromTo[0]),
                Integer.parseInt(fromTo[1]));
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    private static class Instruction {
        private String action;
        private Point leftTop;
        private Point rightBottom;
    }

    private static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return x + ":" + y;
        }
    }
}
