package cz.pavlatka.adventofcode.solvers;

import cz.pavlatka.adventofcode.common.ResourceLineReader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class Day02Solver {
    private final ResourceLineReader reader;

    public Integer part1(boolean sample) {
        return getReports(sample)
                .stream()
                .filter(report -> {
                    if (exceedStep(report.getFirst(), report.get(1))) {
                        return false;
                    }

                    // Direction
                    var direction = this.direction(report.getFirst(), report.get(1));
                    for (int i = 1; i < report.size() - 1; i++) {
                        if (exceedStep(report.get(i), report.get(i+1))) {
                            return false;
                        }

                        var dir = direction(report.get(i), report.get(i+1));
                        if (!dir.equals(direction)) {
                            return false;
                        }
                    }

                    return true;
                }).toList().size();

    }

    private boolean exceedStep(int a, int b) {
        return Math.abs(a - b) > 3;
    }

    private String direction(int a, int b) {
        return a == b ? "match" : a > b ? "down" : "up";
    }

    public Integer part2(boolean sample) {
        return 0;
    }

    private List<List<Integer>> getReports(boolean sample) {
        return reader.readLines(2, sample).stream()
                .map(line -> Arrays.stream(line.trim().split("\\s+")).map(Integer::valueOf).toList())
                .toList();
    }
}