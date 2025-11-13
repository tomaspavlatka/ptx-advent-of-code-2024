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
public class Day01Solver {
    private final ResourceLineReader reader;

    public Integer part1(boolean sample) {
        var groups = getGroups(sample);

        return IntStream.range(0, groups._1().size())
            .map(i -> Math.abs(groups._1().get(i) - groups._2().get(i)))
            .sum();
    }

    public Integer part2(boolean sample) {
        var groups = getGroups(sample);

        return groups._1().stream().reduce(0, (a, b) ->  {
            var counts = groups._2().stream().filter(c -> c.equals(b)).toList().size();
            a += b * counts;

            return a;
        } );
    }

    private Tuple<List<Integer>, List<Integer>> getGroups(boolean sample) {
        var left = new ArrayList<Integer>();
        var right = new ArrayList<Integer>();

        reader.readLines(1, sample).stream()
                .map(line -> Arrays.stream(line.trim().split("\\s+")).map(Integer::valueOf).toList())
                .forEach(pair -> {
                    left.add(pair.getFirst());
                    right.add(pair.get(1));
                });

        var sortedLeft = left.stream().sorted().toList();
        var sortedRight = right.stream().sorted().toList();

        return new Tuple<>(sortedLeft, sortedRight);

    }


}
