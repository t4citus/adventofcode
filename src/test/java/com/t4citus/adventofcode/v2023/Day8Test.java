package com.t4citus.adventofcode.v2023;

import com.t4citus.adventofcode.AbstractTestBase;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;

public class Day8Test extends AbstractTestBase {

    @Value("classpath:/2023/day8.txt" )
    private Resource puzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = samplePuzzleInput();

        // When
        Navigation navigation = parseNavigation(lines);
        System.out.println(navigation);

        boolean found = false;
        String nodeKey = "AAA";
        int steps = 0;

        while (!found) {
            for (String direction : navigation.instructions) {
                if (direction.equals("L" )) {
                    nodeKey = navigation.nodes.get(nodeKey).left;
                    steps++;
                }
                if (direction.equals("R" )) {
                    nodeKey = navigation.nodes.get(nodeKey).right;
                    steps++;
                }
                if (nodeKey.equals("ZZZ" )) {
                    found = true;
                    break;
                }
            }
        }

        // Then
        System.out.println("steps: " + steps);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 740);

        // When
        Navigation navigation = parseNavigation(lines);
        System.out.println(navigation);

        boolean found = false;
        String nodeKey = "AAA";
        int steps = 0;

        while (!found) {
            for (String direction : navigation.instructions) {
                if (direction.equals("L" )) {
                    nodeKey = navigation.nodes.get(nodeKey).left;
                    steps++;
                }
                if (direction.equals("R" )) {
                    nodeKey = navigation.nodes.get(nodeKey).right;
                    steps++;
                }
                if (nodeKey.equals("ZZZ" )) {
                    found = true;
                    break;
                }
            }
        }

        // Then
        System.out.println("steps: " + steps);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = samplePuzzleInput2();

        // When
        Navigation navigation = parseNavigation(lines);
        System.out.println(navigation);

        boolean found = false;
        List<String> nodeKeys = navigation.nodes.keySet().stream()
                .filter(n -> n.endsWith("A"))
                .toList();
        long steps = 0;

        while (!found) {
            for (String direction : navigation.instructions) {
                List<String> newNodeKeys = new ArrayList<>();
                if (direction.equals("L" )) {
                    for (String nodeKey : nodeKeys) {
                        newNodeKeys.add(navigation.nodes.get(nodeKey).left);
                    }
                    steps++;
                }
                if (direction.equals("R" )) {
                    for (String nodeKey : nodeKeys) {
                        newNodeKeys.add(navigation.nodes.get(nodeKey).right);
                    }
                    steps++;
                }
                found = true;
                for (String nodeKey : newNodeKeys) {
                    if (!nodeKey.endsWith("Z")) {
                        found = false;
                    }
                }
                if (found) {
                    break;
                }
                nodeKeys = newNodeKeys;
            }
        }

        // Then
        System.out.println("steps: " + steps);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 740);

        // When
        Navigation navigation = parseNavigation(lines);
        System.out.println(navigation);

        boolean found = false;
        List<String> nodeKeys = navigation.nodes.keySet().stream()
                .filter(n -> n.endsWith("A"))
                .toList();
        System.out.println(nodeKeys);

        List<String> endKeys = navigation.nodes.keySet().stream()
                .filter(n -> n.endsWith("Z"))
                .toList();
        System.out.println(endKeys);
        long steps = 0;

        while (!found) {
            for (String direction : navigation.instructions) {
                List<String> newNodeKeys = new ArrayList<>();
                if (direction.equals("L" )) {
                    for (String nodeKey : nodeKeys) {
                        newNodeKeys.add(navigation.nodes.get(nodeKey).left);
                    }
                    steps++;
                }
                if (direction.equals("R" )) {
                    for (String nodeKey : nodeKeys) {
                        newNodeKeys.add(navigation.nodes.get(nodeKey).right);
                    }
                    steps++;
                }
                found = true;
                for (String nodeKey : newNodeKeys) {
                    if (!nodeKey.endsWith("Z")) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    break;
                }
                nodeKeys = newNodeKeys;
            }
        }

        // Then
        System.out.println("steps: " + steps);
    }

    @Test
    public void givenPuzzleInput_whenSolve2v2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 740);

        // When
        Navigation navigation = parseNavigation(lines);
        System.out.println(navigation);

        List<String> nodeKeys = navigation.nodes.keySet().stream()
                .filter(n -> n.endsWith("A"))
                .toList();

        Map<String, List<Step>> allWalks = new HashMap<>();

        // Find steps to first end node
        for (String nodeKey : nodeKeys) {
            boolean found = false;
            int foundCount = 0;
            int steps = 0;
            List<Step> walk = new ArrayList<>();
            while (!found) {
                for (String direction : navigation.instructions) {
                    if (direction.equals("L" )) {
                        nodeKey = navigation.nodes.get(nodeKey).left;
                        steps++;
                    }
                    if (direction.equals("R" )) {
                        nodeKey = navigation.nodes.get(nodeKey).right;
                        steps++;
                    }
                    if (nodeKey.endsWith("Z" )) {
                        foundCount++;
                        walk.add(new Step(nodeKey, steps));
                        steps = 0;

                        if (foundCount == 3) {
                            found = true;
                            break;
                        }
                    }
                }
            }
            allWalks.put(nodeKey, walk);
            walk.forEach(System.out::println);
        }




        // Then
    }


    private List<String> samplePuzzleInput() {
        return Arrays.asList(
                "RL",
                "",
                "AAA = (BBB, CCC)",
                "BBB = (DDD, EEE)",
                "CCC = (ZZZ, GGG)",
                "DDD = (DDD, DDD)",
                "EEE = (EEE, EEE)",
                "GGG = (GGG, GGG)",
                "ZZZ = (ZZZ, ZZZ)"
        );
    }

    private List<String> samplePuzzleInput2() {
        return Arrays.asList(
                "LR",
                "",
                "11A = (11B, XXX)",
                "11B = (XXX, 11Z)",
                "11Z = (11B, XXX)",
                "22A = (22B, XXX)",
                "22B = (22C, 22C)",
                "22C = (22Z, 22Z)",
                "22Z = (22B, 22B)",
                "XXX = (XXX, XXX)"
        );
    }

    private static Navigation parseNavigation(List<String> lines) {
        List<String> instructions = Arrays.stream(lines.get(0).split("" )).toList();
        Map<String, Node> nodes = new HashMap<>();

        for (int i = 2; i < lines.size(); i++) {
            Node node = parseNode(lines.get(i));
            nodes.put(node.key, node);
        }

        return new Navigation(instructions, nodes);
    }

    private static Node parseNode(String line) {
        String[] keyValue = line.split("=" );
        String key = keyValue[0].trim();
        String value = keyValue[1].trim().replaceAll("\\(", "" ).replaceAll("\\)", "" );
        String[] leftRight = value.split("," );
        String left = leftRight[0].trim();
        String right = leftRight[1].trim();
        return new Node(key, left, right);
    }

    @AllArgsConstructor
    @ToString
    private static class Navigation {
        private List<String> instructions;
        private Map<String, Node> nodes;
    }

    @AllArgsConstructor
    @ToString
    private static class Node {
        private String key;
        private String left;
        private String right;
    }

    @AllArgsConstructor
    @ToString
    private static class Step {
        private String nodeValue;
        private int steps;
    }
}
