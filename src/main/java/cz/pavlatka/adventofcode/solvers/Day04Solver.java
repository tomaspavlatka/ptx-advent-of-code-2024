package cz.pavlatka.adventofcode.solvers;

import cz.pavlatka.adventofcode.common.ResourceLineReader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class Day04Solver {
    private final ResourceLineReader reader;

    public Integer part1(boolean sample) {
        var maze = buildMaze(1, sample);
        return maze.plan.entrySet().stream().map((entry) -> {
            if (!entry.getValue().equals("X")) {
                return 0;
            }

            return Stream.of("right", "left", "down", "up", "right-up", "right-down", "left-up", "left-down")
                    .map(dir -> checkMaze(maze, entry.getKey(), dir))
                    .map(bol -> bol ? 1 : 0)
                    .reduce(0, Integer::sum);
        }).reduce(0, Integer::sum);
    }

    private String getLetter(Maze maze, int x, int y) {
        return maze.plan.get(new Coordinates(x, y));
    }

    private boolean checkMaze(Maze maze, Coordinates coordinates, String direction) {
        try {
            var x = coordinates.x;
            var y = coordinates.y;

            return switch (direction) {
                case "right" -> getLetter(maze, x + 1, y).equals("M")
                        && getLetter(maze, x + 2, y).equals("A")
                        && getLetter(maze, x + 3, y).equals("S");
                case "left" -> getLetter(maze, x - 1, y).equals("M")
                        && getLetter(maze, x - 2, y).equals("A")
                        && getLetter(maze, x - 3, y).equals("S");
                case "down" -> getLetter(maze, x, y + 1).equals("M")
                        && getLetter(maze, x, y + 2).equals("A")
                        && getLetter(maze, x, y + 3).equals("S");
                case "up" -> getLetter(maze, x, y - 1).equals("M")
                        && getLetter(maze, x, y - 2).equals("A")
                        && getLetter(maze, x, y - 3).equals("S");
                case "right-up" -> getLetter(maze, x + 1, y - 1).equals("M")
                        && getLetter(maze, x + 2, y - 2).equals("A")
                        && getLetter(maze, x + 3, y - 3).equals("S");
                case "right-down" -> getLetter(maze, x + 1, y + 1).equals("M")
                        && getLetter(maze, x + 2, y + 2).equals("A")
                        && getLetter(maze, x + 3, y + 3).equals("S");
                case "left-up" -> getLetter(maze, x - 1, y - 1).equals("M")
                        && getLetter(maze, x - 2, y - 2).equals("A")
                        && getLetter(maze, x - 3, y - 3).equals("S");
                case "left-down" -> getLetter(maze, x - 1, y + 1).equals("M")
                        && getLetter(maze, x - 2, y + 2).equals("A")
                        && getLetter(maze, x - 3, y + 3).equals("S");
                default -> false;
            };
        } catch (Exception _ex) {
            return false;
        }
    }

    public Integer part2(boolean sample) {
        return 0;
    }

    private Maze buildMaze(int part, boolean sample) {
        var plan = new HashMap<Coordinates, String>();

        var lines = reader.readLines(4, part, sample);
        for (int i = 0; i < lines.size(); i++) {
            var letters = Arrays.asList(lines.get(i).split(""));
            for (int j =0; j < letters.size(); j++) {
                var coordinates = new Coordinates(i, j);
                plan.put(coordinates, letters.get(j));
            }
        }

        return new Maze(plan);
    }

    record Maze(Map<Coordinates, String> plan) {}

    record Coordinates(int x, int y) {}
}