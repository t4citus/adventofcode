package com.t4citus.adventofcode.v2015;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Day8Test extends AbstractTestBase {

    @Value("classpath:/2015/day8.txt" )
    private Resource puzzleInputResource;

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 300);

        // When
        int len = length(lines.get(0));

        // Then
        System.out.println("len: " + len);
    }

    public int len(String line) {
        int orig = line.length();
        String[] s = line.split("");
        int len = 0;
        int offset = 0;

        while (offset < s.length) {
            len++;
            if (s[offset].equals("\\")) {
                String ch = charAt(s, offset + 1);
                if ("\\".equals(ch)) {
                    len--;
                    offset = offset + 1;
                    continue;
                }
                if ("\"".equals(ch)) {
                    len--;
                    offset = offset + 1;
                    continue;
                }
                if ("x".equals(ch)) {
                    String hex = charAt(s, offset + 2) + charAt(s, offset + 3);
                    if (hex.length() == 2 && isHex(hex)) {
                        len -= 4;
                        len += Character.charCount(Integer.parseInt(hex, 16));
                        offset = offset + 3;
                        continue;
                    }
                }
            }
            offset = offset + 1;
        }
        if (line.startsWith("\"")) {
            len--;
        }
        if (line.endsWith("\"")) {
            len--;
        }

        return orig - len;
    }

    private int length(String line) {
        if (line.equals("\"\"")) {
            return 2;
        }
        String rest = line;
        if (rest.startsWith("\"")) {
            rest = rest.substring(1);
        }
        if (rest.endsWith("\"")) {
            rest = rest.substring(0, rest.length() - 1);
        }

        System.out.println(rest);

        return 0;
    }

    private String charAt(String[] arr, int index) {
        if (index >= 0 && index < arr.length) {
            return arr[index];
        }
        return "";
    }

    private boolean isHex(String s) {
        try {
            Integer.parseInt(s, 16);
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() throws NoSuchAlgorithmException {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 300);

        // When

        // Then

    }
}
