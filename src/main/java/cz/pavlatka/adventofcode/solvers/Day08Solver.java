package cz.pavlatka.adventofcode.solvers;

import cz.pavlatka.adventofcode.common.ResourceLineReader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.Tuple;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class Day08Solver {
    private final ResourceLineReader reader;

    public Integer part1(boolean sample) {
        var maze = getMaze(1, sample);

        return maze.antennas.entrySet().stream().map(entry -> {
            if (entry.getKey().equals(".")) {
                return new HashSet<Coordinates>();
            }

            var coordinates = entry.getValue().stream().toList();
            return IntStream.range(0, coordinates.size())
                .boxed()
                .map(idx -> {
                    var anti = new HashSet<Coordinates>();
                    var antenna = coordinates.get(idx);

                    coordinates.forEach(coord -> {
                        var rowDiff = coord.row - antenna.row;
                        var colDiff = coord.col - antenna.col;

                        if (rowDiff == 0) { // same line
                            if (colDiff > 0) {  // we process from left to right, so rest we ignore
                                anti.add(new Coordinates(antenna.row, antenna.col - colDiff));
                                anti.add(new Coordinates(antenna.row, coord.col + colDiff));
                            }
                        } else if (rowDiff > 0) { // we process from top to bottom
                            anti.add(new Coordinates(antenna.row - rowDiff, antenna.col - colDiff));
                            anti.add(new Coordinates(coord.row + rowDiff, coord.col + colDiff));
                        }
                    });

                    return anti;
                })
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        })
        .flatMap(Set::stream)
        .collect(Collectors.toSet())
        .stream()
        .filter(coord -> insideMaze(coord, maze))
        .peek(System.out::println)
        .collect(Collectors.toSet())
        .size();
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