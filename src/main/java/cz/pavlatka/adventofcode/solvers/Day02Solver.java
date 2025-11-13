package cz.pavlatka.adventofcode.solvers;

import cz.pavlatka.adventofcode.common.ResourceLineReader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.Tuple;
import tools.jackson.core.PrettyPrinter;

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
                .map(this::isSafe)
                .filter(Boolean::booleanValue).toList().size();

    }

    public Integer part2(boolean sample) {
        return getReports(sample)
                .stream()
                .map(report -> isSafe(report) || canSafelyRemoveOne(report))
                .filter(Boolean::booleanValue)
                .toList()
                .size();
    }

    private Boolean canSafelyRemoveOne(List<Integer> report) {
        for (int i = 0; i < report.size(); i++) {
            var modified = new ArrayList<>(report);
            modified.remove(i);
            if (isSafe(modified)) {
                return true;
            }
        }

        return false;

    }

    private boolean exceedStep(int a, int b) {
        return Math.abs(a - b) > 3;
    }

    private String direction(int a, int b) {
        return a == b ? "match" : a > b ? "down" : "up";
    }


    private Boolean isSafe(List<Integer> report) {
        if(report.size() < 2) {
            return false;
        }

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
            if (dir.equals("match") || !dir.equals(direction)) {
                return false;
            }
        }

        return true;
    }

    private List<List<Integer>> getReports(boolean sample) {
        return reader.readLines(2, sample).stream()
                .map(line -> Arrays.stream(line.trim().split("\\s+")).map(Integer::valueOf).toList())
                .toList();
    }
}