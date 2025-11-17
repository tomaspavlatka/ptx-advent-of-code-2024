package cz.pavlatka.adventofcode.solvers;

import cz.pavlatka.adventofcode.common.ResourceLineReader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.Tuple;

import java.util.*;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class Day08Solver {
    private final ResourceLineReader reader;

    public Integer part1(boolean sample) {
        var maze = getMaze(1, sample);
        Set<Coordinates> antinodes = new HashSet<>();

        for (var entry: maze.antennas.entrySet()) {
            if (entry.getKey().equals(".")) continue;

            var list = new ArrayList<>(entry.getValue());
            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    var a = list.get(i);
                    var b = list.get(j);

                    int dr = b.row - a.row;
                    int dc = b.col - a.col;

                    antinodes.add(new Coordinates(a.row - dr, a.col - dc));
                    antinodes.add(new Coordinates(b.row + dr, b.col + dc));
                }
            }
        }

        return (int) antinodes.stream().filter(c -> insideMaze(c, maze)).count();
    }

    private boolean insideMaze(Coordinates coordinates, Maze maze) {
        return coordinates.row < maze.rows() && coordinates.col < maze.cols() && coordinates.row >= 0 && coordinates.col >= 0;
    }
    public Integer part2(boolean sample) {
        return 0;
    }

    private Maze getMaze(int part, boolean sample) {
        var lines = reader.readLines(8, part, sample);
        var cols = lines.getFirst().length();

        var data = IntStream.range(0, lines.size())
            .boxed()
            .map(row -> {
                var line = lines.get(row);
                return IntStream.range(0, line.length()).boxed().map(col -> {
                    var letter = String.valueOf(line.charAt(col));
                    return new Tuple<>(letter, new Coordinates(row, col));
                }).toList();
            }).flatMap(List::stream)
            .toList();

        var antennas = new HashMap<String, Set<Coordinates>>();
        data.forEach(antenna -> antennas.computeIfAbsent(antenna._1(), k -> new HashSet<>()).add(antenna._2()));

        return new Maze(lines.size(), cols, antennas);
    }

    private record Maze(int rows, int cols, Map<String, Set<Coordinates>> antennas) {}

    private record Coordinates(int row, int col){}
}