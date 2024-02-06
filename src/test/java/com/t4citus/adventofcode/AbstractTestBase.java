package com.t4citus.adventofcode;

import org.assertj.core.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class AbstractTestBase {

    public List<String> readLines(Resource resource) {
        try {
            return Files.readAllLines(Path.of(resource.getURI()));
        } catch (Exception ignored) {
        }

        return Collections.emptyList();
    }

    public void assertLines(List<String> lines, int expectedSize) {
        Assertions.assertThat(lines).isNotNull();
        Assertions.assertThat(lines.size()).isEqualTo(expectedSize);
    }

    public static boolean isInt(String s) {
        if (s == null) {
            return false;
        }
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNullOrEmpty(List<?> l) {
        return l == null || l.isEmpty();
    }

    public static <T> List<T> list(List<T> l) {
        return l != null ? l : new ArrayList<>();
    }
}
