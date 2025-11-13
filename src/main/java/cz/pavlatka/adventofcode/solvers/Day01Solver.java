package cz.pavlatka.adventofcode.solvers;

import cz.pavlatka.adventofcode.common.ResourceLineReader;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class Day01Solver {
    private final ResourceLineReader reader;

    @SneakyThrows
    public String solve(Integer part) {
        var left = new ArrayList<Integer>();
        var right = new ArrayList<Integer>();

        reader.readLines(1, part).stream()
            .map(line -> Arrays.stream(line.trim().split("\\s+")).map(Integer::valueOf).toList())
            .forEach(pair -> {
                left.add(pair.getFirst());
                right.add(pair.get(1));
            });

        var sortedLeft = left.stream().sorted().toList();
        var sortedRight = right.stream().sorted().toList();

        return String.valueOf(
            IntStream.range(0, sortedLeft.size())
            .map(i -> Math.abs(sortedLeft.get(i) - sortedRight.get(i)))
            .sum()
        );
    }
}
