package com.t4citus.adventofcode.v2023;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;
import java.util.stream.Collectors;

public class Day3Test extends AbstractTestBase {

    @Value("classpath:/2023/day3.txt")
    private Resource puzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = Arrays.asList(
                "467..114..",
                "...*......",
                "..35..633.",
                "......#...",
                "617*......",
                ".....+.58.",
                "..592.....",
                "......755.",
                "...$.*....",
                ".664.598.."
        );

        // When
        int sum = findNumbersWithAdjacents(lines, findSymbols(lines, null)).stream()
                .reduce(Integer::sum)
                .orElse(0);

        // Then
        System.out.println(sum);
    }

    public record Coord(int x, int y) {
        List<Coord> adjacents() {
            return List.of(
                    new Coord(x - 1, y - 1),
                    new Coord(x, y - 1),
                    new Coord(x + 1, y - 1),
                    new Coord(x - 1, y),
                    new Coord(x + 1, y),
                    new Coord(x - 1, y + 1),
                    new Coord(x, y + 1),
                    new Coord(x + 1, y + 1)
            );
        }
    }

    public boolean isSymbol(char ch) {
        return ch != '.' && !Character.isDigit(ch);
    }

    public Set<Coord> findSymbols(List<String> lines, Character onlyThis) {
        Set<Coord> symbols = new HashSet<>();
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char ch = line.charAt(x);
                if (isSymbol(ch)) {
                    if (onlyThis == null || onlyThis == ch)
                        symbols.add(new Coord(x, y));
                }
            }
        }
        return symbols;
    }

    public Set<Coord> findDigits(List<String> lines) {
        Set<Coord> digits = new HashSet<>();
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char ch = line.charAt(x);
                if (Character.isDigit(ch)) {
                    digits.add(new Coord(x, y));
                }
            }
        }
        return digits;
    }

    public List<Integer> findNumbersWithAdjacents (List<String> lines, Set<Coord> symbols) {
        List<Integer> res = new ArrayList<>();

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            StringBuilder number = null;
            boolean hasAdjacent = false;
            for (int x = 0; x < line.length(); x++) {
                char ch = line.charAt(x);
                Coord coord = new Coord(x, y);

                if (Character.isDigit(ch)) {
                    if (number == null) {
                        number = new StringBuilder();
                    }
                    number.append(ch);

                    Coord first = coord.adjacents().stream()
                            .filter(symbols::contains)
                            .findFirst()
                            .orElse(null);

                    if (first != null && !hasAdjacent) {
                        hasAdjacent = true;
                    }
                } else {
                    if (number != null) {
                        if (hasAdjacent) res.add(Integer.parseInt(number.toString()));
                        number = null;
                        hasAdjacent = false;
                    }
                }
            }

            if (number != null && hasAdjacent) {
                res.add(Integer.parseInt(number.toString()));
            }
        }

        return res;
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 140);

        // When
        int sum = findNumbersWithAdjacents(lines, findSymbols(lines, null)).stream()
                .reduce(Integer::sum)
                .orElse(0);

        // Then
        System.out.println(sum);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = Arrays.asList(
                "467..114..",
                "...*......",
                "..35..633.",
                "......#...",
                "617*......",
                ".....+.58.",
                "..592.....",
                "......755.",
                "...$.*....",
                ".664.598.."
        );

        // When
        Set<Coord> digits = findDigits(lines);
        Set<Coord> gears = findSymbols(lines, '*').stream()
                .filter(gear -> {
                    int cnt = 0;
                    Coord top = new Coord(gear.x(), gear.y() - 1);
                    Coord bottom = new Coord(gear.x(), gear.y() + 1);
                    Coord left = new Coord(gear.x() - 1, gear.y());
                    Coord right = new Coord(gear.x() + 1, gear.y());
                    Coord topLeft = new Coord(gear.x() - 1, gear.y() - 1);
                    Coord topRight = new Coord(gear.x() + 1, gear.y() - 1);
                    Coord bottomLeft = new Coord(gear.x() - 1, gear.y() + 1);
                    Coord bottomRight = new Coord(gear.x() + 1, gear.y() + 1);

                    if (digits.contains(left)) cnt++;
                    if (digits.contains(right)) cnt++;
                    if (digits.contains(top)) {
                        cnt++;
                    } else {
                        if (digits.contains(topLeft)) cnt++;
                        if (digits.contains(topRight)) cnt++;
                    }
                    if (digits.contains(bottom)) {
                        cnt++;
                    } else {
                        if (digits.contains(bottomLeft)) cnt++;
                        if (digits.contains(bottomRight)) cnt++;
                    }

                    return cnt == 2;
                })
                .collect(Collectors.toSet());

        gears.forEach(System.out::println);

        // Then
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 100);

        // When

        // Then
    }
}
