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
                .filter(pages -> !pages.isEmpty())
                .filter(pages -> hasCorrectOrder(printing.rules, pages))
                .map(pages -> pages.get(pages.size()/2))
                .reduce(0, Integer::sum);
    }

    public Integer part2(boolean sample) {
        var printing = getPrinting(1, sample);

        return printing.pages.stream()
                .filter(pages -> !pages.isEmpty())
                .filter(pages -> !hasCorrectOrder(printing.rules, pages))
                .map(pages -> fix(printing.rules, pages))
                .map(pages -> pages.get(pages.size()/2))
                .reduce(0, Integer::sum);
    }

    private List<Integer> fix(Map<Integer, List<Integer>> rules, List<Integer> pages) {
        var ordered = new ArrayList<>(pages);

        boolean changed;
        do {
            changed = false;
            var printed = new HashSet<Integer>();

            for (int i = 0; i< ordered.size(); i++) {
                int page = ordered.get(i);

                for (int blocked: rules.getOrDefault(page, List.of())) {
                    if (printed.contains(blocked)) {
                        int j = ordered.indexOf(blocked);
                        Collections.swap(ordered, i, j);
                        changed = true;
                        break;
                    }
                }

                printed.add(page);
                if (changed) {
                    break;
                }
            }
        } while (changed);

        return ordered;
    }

    private Boolean hasCorrectOrder(Map<Integer, List<Integer>> rules, List<Integer> pages) {
        var printed = new ArrayList<Integer>();

        for (int page : pages) {
             if (rules.getOrDefault(page, List.of()).stream().anyMatch(printed::contains)) {
                return false;
            }

            printed.add(page);
        }

        return true;
    }

    private Printing getPrinting(int part, boolean sample) {
        var lines = reader.readLines(5, part, sample);

        var rules = new HashMap<Integer, List<Integer>>();
        var pages = new ArrayList<List<Integer>>();

        var processingPages = false;
        for (String line : lines) {
            if (line.isEmpty()) {
                processingPages = true;
                continue;
            }

            if (processingPages) {
                pages.add(Arrays.stream(line.split(","))
                        .map(Integer::parseInt)
                        .toList());
            } else {
                var parts = line.split("\\|");
                var a = Integer.parseInt(parts[0]);
                var b = Integer.parseInt(parts[1]);

                rules.computeIfAbsent(a, k -> new ArrayList<>()).add(b);
            }
        }

        return new Printing(pages, rules);
    }

    private record Printing(List<List<Integer>> pages, Map<Integer, List<Integer>> rules) {}
}