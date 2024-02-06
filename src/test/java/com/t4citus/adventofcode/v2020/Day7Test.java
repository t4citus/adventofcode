package com.t4citus.adventofcode.v2020;

import com.t4citus.adventofcode.AbstractTestBase;
import lombok.ToString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;

public class Day7Test extends AbstractTestBase {

    @Value("classpath:/2020/day7.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2020/day7_sample1.txt")
    private Resource sample1PuzzleInputResource;

    @Value("classpath:/2020/day7_sample2.txt")
    private Resource sample2PuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(sample1PuzzleInputResource);

        // When
        Map<String, List<Bag>> bags = parseLines(lines);
        Map<String, List<Bag>> flattened = flatten(bags);

        System.out.println("bags:");
        print(bags);
        System.out.println("---");
        System.out.println("flattened:");
        print(flattened);

        long shinyGolds = countInnerBagsWithColor(flattened, "shiny gold");

        // Then
        System.out.println("There are " + shinyGolds + " bags that contain 'shiny gold' bags.");
        Assertions.assertThat(shinyGolds).isEqualTo(4);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);

        // When
        Map<String, List<Bag>> bags = parseLines(lines);
        Map<String, List<Bag>> flattened = flatten(bags);
        long shinyGolds = countInnerBagsWithColor(flattened, "shiny gold");

        // Then
        System.out.println("There are " + shinyGolds + " bags that contain 'shiny gold' bags.");
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(sample2PuzzleInputResource);

        // When
        Map<String, List<Bag>> bags = parseLines(lines);
        Map<String, List<Bag>> flattened = flatten(bags);

        List<Bag> shinyGold = flattened.get("shiny gold");
        Integer total = shinyGold.stream()
                .map(b -> b.amount)
                .reduce(0, Integer::sum);

        // Then
        System.out.println("There are " + total + " bags in total.");
        Assertions.assertThat(total).isEqualTo(126);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);

        // When
        Map<String, List<Bag>> bags = parseLines(lines);
        Map<String, List<Bag>> flattened = flatten(bags);

        List<Bag> shinyGold = flattened.get("shiny gold");
        Integer total = shinyGold.stream()
                .map(b -> b.amount)
                .reduce(0, Integer::sum);

        // Then
        System.out.println("There are " + total + " bags in total.");
    }

    private Map<String, List<Bag>> parseLines(List<String> lines) {
        Map<String, List<Bag>> parsed = new HashMap<>();

        for (String line : lines) {
            String[] split = line.split("contain");

            String mainColor = split[0].trim().replaceAll("\\.", "")
                    .replaceAll("bags", "")
                    .replaceAll("bag", "")
                    .trim();

            List<Bag> containedBags;

            if (split[1].trim().equals("no other bags.")) {
                containedBags = new ArrayList<>();
            } else {
                containedBags = Arrays.stream(split[1].split(","))
                        .map(s -> s.replaceAll("\\.", "")
                                .replaceAll("bags", "")
                                .replaceAll("bag", ""))
                        .map(String::trim)
                        .map(s -> {
                            String[] arr = s.split(" ");
                            String color = (arr[1] + " " + arr[2]).trim();
                            int amount = Integer.parseInt(arr[0]);
                            return new Bag(color, amount);
                        })
                        .toList();
            }

            parsed.put(mainColor, containedBags);
        }

        return parsed;
    }

    private Map<String, List<Bag>> flatten(Map<String, List<Bag>> bags) {
        Map<String, List<Bag>> flattened = new HashMap<>();
        boolean hasChanges = true;

        for (Map.Entry<String, List<Bag>> entry : bags.entrySet()) {
            if (isNullOrEmpty(entry.getValue())) {
                flattened.put(entry.getKey(), new ArrayList<>());
            }
        }

        while (hasChanges) {
            hasChanges = false;
            for (Map.Entry<String, List<Bag>> entry : bags.entrySet()) {
                String outerColor = entry.getKey();
                List<Bag> innerBags = entry.getValue();
                // print(entry);

                if (!flattened.containsKey(outerColor)) {
                    // check if all inner bags are covered by processed bags and add them
                    boolean isMissing = false;
                    for (Bag innerBag : innerBags) {
                        if (!flattened.containsKey(innerBag.color)) {
                            isMissing = true;
                            break;
                        }
                    }
                    if (!isMissing) {
                        List<Bag> newBags = new ArrayList<>(innerBags);
                        for (Bag innerBag : innerBags) {
                            newBags.addAll(flattened.get(innerBag.color).stream()
                                    .map(b -> new Bag(b.color, innerBag.amount * b.amount))
                                    .toList()
                            );
                        }
                        flattened.put(outerColor, newBags);
                        hasChanges = true;
                    }
                }
            }
        }

        return flattened;
    }

    @ToString
    private static class Bag {
        private final String color;
        private final int amount;

        Bag(String color, int amount) {
            this.color = color;
            this.amount = amount;
        }
    }

    private void print(Map<String, List<Bag>> map) {
        map.entrySet().forEach(this::print);
    }

    private void print(Map.Entry<String, List<Bag>> entry) {
        System.out.println(entry.getKey() + ": " + entry.getValue());
    }

    private long countInnerBagsWithColor(Map<String, List<Bag>> allBags, String color) {
        return allBags.values().stream()
                .filter(innerBags -> {
                    for (Bag b : innerBags) {
                        if (b.color.equals(color)) {
                            return true;
                        }
                    }
                    return false;
                })
                .count();
    }
}
