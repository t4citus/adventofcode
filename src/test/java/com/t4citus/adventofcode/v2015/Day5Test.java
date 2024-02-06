package com.t4citus.adventofcode.v2015;

import com.t4citus.adventofcode.AbstractTestBase;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day5Test extends AbstractTestBase {

    @Value("classpath:/2015/day5.txt" )
    private Resource puzzleInputResource;

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() throws NoSuchAlgorithmException {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1000);

        // When
        final Set<String> vowels = Arrays.stream("aeiou".split("")).collect(Collectors.toSet());
        final Set<String> excluded = new HashSet<>(Arrays.asList("ab", "cd", "pq", "xy" ));

        int niceCount = lines.stream()
                .filter(line -> isNice(line, vowels, excluded))
                .toList()
                .size();

        // Then
        System.out.println(niceCount);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() throws NoSuchAlgorithmException {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1000);

        // When
        int niceCount = lines.stream()
                .filter(this::isNice)
                .toList()
                .size();

        // Then
        System.out.println(niceCount);
    }

    private boolean isNice(String line, Set<String> vowels, Set<String> excluded) {
        String[] split = line.split("");

        // check excluded strings
        for (String s : excluded) {
            if (line.contains(s)) {
                return false;
            }
        }

        // count vowels
        int vowelCount = 0;
        for (String c : split) {
            if (vowels.contains(c)) {
                vowelCount++;
            }
        }
        if (vowelCount < 3) {
            return false;
        }

        // check twice in a row
        boolean twice = false;
        for (int i = 0; i < split.length - 1; i++) {
            if (split[i].equals(split[i + 1])) {
                twice = true;
                break;
            }
        }
        if (!twice) {
            return false;
        }

        return true;
    }

    private boolean isNice(String line) {
        String[] s = line.split("");
        int len = s.length;

        // check with letter between
        boolean letterBetween = false;
        for (int i = 0; i < len - 2; i++) {
            if (s[i].equals(s[i + 2])) {
                letterBetween = true;
                break;
            }
        }
        if (!letterBetween) {
            return false;
        }

        // check repetitions of two letter sequences
        for (int i = 0; i < len - 2; i++) {
            for (int j = i + 2; j < len - 1; j++) {
                if (s[i].equals(s[j]) && s[i + 1].equals(s[j + 1])) {
                    return true;
                }
            }
        }

        return false;
    }
}
