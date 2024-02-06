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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day7Test extends AbstractTestBase {

    @Value("classpath:/2015/day7.txt" )
    private Resource puzzleInputResource;

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 339);

        // When
        List<Instruction> all = lines.stream()
                .map(Day7Test::parseInstruction)
                .toList();

        Map<String, Character> resolved = new HashMap<>();

        // Process 'SET'
        for (Instruction instruction : all) {
            if (instruction.command.equals("SET")) {
                if (isInt(instruction.from1)) {
                    resolved.put(instruction.to, (char) Integer.parseInt(instruction.from1));
                    instruction.resolved = true;
                }
            }
        }

        List<Instruction> remaining = all.stream()
                .filter(Instruction::isNotResolved)
                .toList();
        int loop = 0;
        Integer wireA = null;

        while (remaining.size() > 0) {
            for (Instruction instruction : all) {
                if (!instruction.resolved) {
                    if (instruction.command.equals("NOT" )) {
                        if (resolved.containsKey(instruction.from1)) {
                            instruction.from1 = Integer.toString(resolved.get(instruction.from1));
                        }
                        if (isInt(instruction.from1)) {
                            char x = (char) Integer.parseInt(instruction.from1);
                            char not = (char) (~x);
                            resolved.put(instruction.to, not);
                            instruction.resolved = true;
                        }
                    }
                    if (instruction.command.equals("AND" )) {
                        if (resolved.containsKey(instruction.from1)) {
                            instruction.from1 = Integer.toString(resolved.get(instruction.from1));
                        }
                        if (resolved.containsKey(instruction.from2)) {
                            instruction.from2 = Integer.toString(resolved.get(instruction.from2));
                        }
                        if (isInt(instruction.from1) && isInt(instruction.from2)) {
                            char x = (char) Integer.parseInt(instruction.from1);
                            char y = (char) Integer.parseInt(instruction.from2);
                            char and = (char) (x & y);
                            resolved.put(instruction.to, and);
                            instruction.resolved = true;
                        }
                    }
                    if (instruction.command.equals("OR" )) {
                        if (resolved.containsKey(instruction.from1)) {
                            instruction.from1 = Integer.toString(resolved.get(instruction.from1));
                        }
                        if (resolved.containsKey(instruction.from2)) {
                            instruction.from2 = Integer.toString(resolved.get(instruction.from2));
                        }
                        if (isInt(instruction.from1) && isInt(instruction.from2)) {
                            char x = (char) Integer.parseInt(instruction.from1);
                            char y = (char) Integer.parseInt(instruction.from2);
                            char or = (char) (x | y);
                            resolved.put(instruction.to, or);
                            instruction.resolved = true;
                        }
                    }
                    if (instruction.command.equals("LSHIFT" )) {
                        if (resolved.containsKey(instruction.from1)) {
                            instruction.from1 = Integer.toString(resolved.get(instruction.from1));
                        }
                        if (isInt(instruction.from1)) {
                            char x = (char) Integer.parseInt(instruction.from1);
                            char y = (char) Integer.parseInt(instruction.from2);
                            char shift = (char) (x << y);
                            resolved.put(instruction.to, shift);
                            instruction.resolved = true;
                        }
                    }
                    if (instruction.command.equals("RSHIFT" )) {
                        if (resolved.containsKey(instruction.from1)) {
                            instruction.from1 = Integer.toString(resolved.get(instruction.from1));
                        }
                        if (isInt(instruction.from1)) {
                            char x = (char) Integer.parseInt(instruction.from1);
                            char y = (char) Integer.parseInt(instruction.from2);
                            char shift = (char) (x >> y);
                            resolved.put(instruction.to, shift);
                            instruction.resolved = true;
                        }
                    }
                    if (instruction.command.equals("SET")) {
                        if (resolved.containsKey(instruction.from1)) {
                            instruction.from1 = Integer.toString(resolved.get(instruction.from1));
                        }
                        if (isInt(instruction.from1)) {
                            char set = (char) Integer.parseInt(instruction.from1);
                            resolved.put(instruction.to, set);
                            instruction.resolved = true;
                        }
                    }
                }
            }

            if (resolved.containsKey("a")) {
                wireA = (int) resolved.get("a");
                break;
            }

            remaining = remaining.stream()
                    .filter(Instruction::isNotResolved)
                    .toList();

            loop++;

            System.out.println("loop: " + loop);
            System.out.println(resolved);
            System.out.println("remaining: " + remaining.size());
            System.out.println();
        }

        // System.out.println(resolved);
        System.out.println(remaining);

        // Then
        System.out.println("a: " + wireA);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() throws NoSuchAlgorithmException {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 339);

        // When
        List<Instruction> all = lines.stream()
                .map(Day7Test::parseInstruction)
                .toList();

        Map<String, Character> resolved = new HashMap<>();

        // Process 'SET'
        for (Instruction instruction : all) {
            if (instruction.command.equals("SET")) {
                if (isInt(instruction.from1)) {
                    resolved.put(instruction.to, (char) Integer.parseInt(instruction.from1));
                    instruction.resolved = true;
                }
            }
        }

        resolved.put("b", (char) 3176);

        List<Instruction> remaining = all.stream()
                .filter(Instruction::isNotResolved)
                .toList();
        Integer wireA = null;

        while (remaining.size() > 0) {
            for (Instruction instruction : all) {
                if (!instruction.resolved) {
                    if (instruction.command.equals("NOT" )) {
                        if (resolved.containsKey(instruction.from1)) {
                            instruction.from1 = Integer.toString(resolved.get(instruction.from1));
                        }
                        if (isInt(instruction.from1)) {
                            char x = (char) Integer.parseInt(instruction.from1);
                            char not = (char) (~x);
                            resolved.put(instruction.to, not);
                            instruction.resolved = true;
                        }
                    }
                    if (instruction.command.equals("AND" )) {
                        if (resolved.containsKey(instruction.from1)) {
                            instruction.from1 = Integer.toString(resolved.get(instruction.from1));
                        }
                        if (resolved.containsKey(instruction.from2)) {
                            instruction.from2 = Integer.toString(resolved.get(instruction.from2));
                        }
                        if (isInt(instruction.from1) && isInt(instruction.from2)) {
                            char x = (char) Integer.parseInt(instruction.from1);
                            char y = (char) Integer.parseInt(instruction.from2);
                            char and = (char) (x & y);
                            resolved.put(instruction.to, and);
                            instruction.resolved = true;
                        }
                    }
                    if (instruction.command.equals("OR" )) {
                        if (resolved.containsKey(instruction.from1)) {
                            instruction.from1 = Integer.toString(resolved.get(instruction.from1));
                        }
                        if (resolved.containsKey(instruction.from2)) {
                            instruction.from2 = Integer.toString(resolved.get(instruction.from2));
                        }
                        if (isInt(instruction.from1) && isInt(instruction.from2)) {
                            char x = (char) Integer.parseInt(instruction.from1);
                            char y = (char) Integer.parseInt(instruction.from2);
                            char or = (char) (x | y);
                            resolved.put(instruction.to, or);
                            instruction.resolved = true;
                        }
                    }
                    if (instruction.command.equals("LSHIFT" )) {
                        if (resolved.containsKey(instruction.from1)) {
                            instruction.from1 = Integer.toString(resolved.get(instruction.from1));
                        }
                        if (isInt(instruction.from1)) {
                            char x = (char) Integer.parseInt(instruction.from1);
                            char y = (char) Integer.parseInt(instruction.from2);
                            char shift = (char) (x << y);
                            resolved.put(instruction.to, shift);
                            instruction.resolved = true;
                        }
                    }
                    if (instruction.command.equals("RSHIFT" )) {
                        if (resolved.containsKey(instruction.from1)) {
                            instruction.from1 = Integer.toString(resolved.get(instruction.from1));
                        }
                        if (isInt(instruction.from1)) {
                            char x = (char) Integer.parseInt(instruction.from1);
                            char y = (char) Integer.parseInt(instruction.from2);
                            char shift = (char) (x >> y);
                            resolved.put(instruction.to, shift);
                            instruction.resolved = true;
                        }
                    }
                    if (instruction.command.equals("SET")) {
                        if (resolved.containsKey(instruction.from1)) {
                            instruction.from1 = Integer.toString(resolved.get(instruction.from1));
                        }
                        if (isInt(instruction.from1)) {
                            char set = (char) Integer.parseInt(instruction.from1);
                            resolved.put(instruction.to, set);
                            instruction.resolved = true;
                        }
                    }
                }
            }

            if (resolved.containsKey("a")) {
                wireA = (int) resolved.get("a");
                break;
            }

            remaining = remaining.stream()
                    .filter(Instruction::isNotResolved)
                    .toList();
        }

        // Then
        System.out.println("a: " + wireA);
    }

    private static Instruction parseInstruction(String line) {
        String command = null;
        String from1 = null;
        String from2 = null;
        String to = null;
        String[] s = line.split(" ");

        // NOT dq -> dr
        if (line.contains("NOT")) {
            command = s[0];
            from1 = s[1];
            to = s[3];
        }
        // kg OR kf -> kh
        else if (line.contains("OR")) {
            command = s[1];
            from1 = s[0];
            from2 = s[2];
            to = s[4];
        }
        // y AND ae -> ag
        else if (line.contains("AND")) {
            command = s[1];
            from1 = s[0];
            from2 = s[2];
            to = s[4];
        }
        // eo LSHIFT 15 -> es
        else if (line.contains("LSHIFT")) {
            command = s[1];
            from1 = s[0];
            from2 = s[2];
            to = s[4];
        }
        // kk RSHIFT 3 -> km
        else if (line.contains("RSHIFT")) {
            command = s[1];
            from1 = s[0];
            from2 = s[2];
            to = s[4];
        }
        // 44430 -> b
        else {
            command = "SET";
            from1 = s[0];
            to = s[2];
        }

        return Instruction.builder()
                .command(command)
                .from1(from1)
                .from2(from2)
                .to(to)
                .build();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    private static class Instruction {
        private String command;
        private String from1;
        private String from2;
        private String to;
        private Integer shiftBy;
        private boolean resolved;

        public boolean isNotResolved() {
            return !resolved;
        }
    }
}
