package cz.pavlatka.adventofcode.solvers;

import cz.pavlatka.adventofcode.common.ResourceLineReader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@AllArgsConstructor
public class Day05Solver {
    private final ResourceLineReader reader;

    public Integer part1(boolean sample) {
        var printing = getPrinting(1, sample);

        return printing.pages.stream()
                .filter(pages -> {
                    var printed = new ArrayList<Integer>();
                    for (Integer page : pages) {
                        var violations = printing.rules.getOrDefault(page, List.of())
                                .stream().map(printed::contains).filter(Boolean::booleanValue).toList();
                        if (!violations.isEmpty()) {
                            return false;
                        }

                        printed.add(page);
                    }

                    return true;
                })
                .filter(pages -> !pages.isEmpty())
                .map(pages -> pages.get(pages.size()/2))
                .reduce(0, Integer::sum);
    }

    public Integer part2(boolean sample) {
        return 0;
    }

    private Printing getPrinting(int part, boolean sample) {
        var lines = reader.readLines(5, part, sample);

        var rules = new HashMap<Integer, List<Integer>>();
        var pages = new ArrayList<List<Integer>>();

        var processingPages = false;
        for (String line : lines) {
            if (line.isEmpty()) {
                processingPages = true;
            } else if (processingPages) {
                pages.add(Arrays.stream(line.split(",")).map(Integer::parseInt).toList());
            } else {
                var rule = line.split("\\|");
                var x = Integer.parseInt(rule[0]);
                var y = Integer.parseInt(rule[1]);

                var existing = rules.getOrDefault(x, new ArrayList<>());
                existing.add(y);
                rules.put(x, existing);
            }
        }

        return new Printing(pages, rules);
    }

    private record Printing(List<List<Integer>> pages, Map<Integer, List<Integer>> rules) {}
}