package cz.pavlatka.adventofcode.solvers;

import cz.pavlatka.adventofcode.common.ResourceLineReader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
public class Day06Solver {
    private final ResourceLineReader reader;

    public Integer part1(boolean sample) {
        var maze = buildMaze(1, sample);

        var visited = new HashSet<Coordinates>();
        visited.add(maze.guard.coordinates);
        var nextMove = nextMove(maze.guard, maze.obstacles);
        while (
            nextMove.coordinates.x >= 0
            && nextMove.coordinates.y >= 0
            && nextMove.coordinates.x < maze.cols
            && nextMove.coordinates.y < maze.rows
        ) {
            visited.add(nextMove.coordinates);
            nextMove = nextMove(nextMove, maze.obstacles);
        }

        return visited.size();
    }

    private String nextDirection(String direction) {
        return switch (direction) {
            case "up" -> "right";
            case "right" -> "down";
            case "down" -> "left";
            default -> "up";
        };
    }

    private Guard nextMove(Guard guard, Set<Coordinates> obstacles) {
        var dir = guard.dir;
        var next = nextPlannedMove(guard, dir);
        while (obstacles.contains(next.coordinates)) {
            dir = nextDirection(dir);
            next = nextPlannedMove(guard, dir);
        }

        return next;
    }

    private Guard nextPlannedMove(Guard guard, String dir) {
        return switch (dir) {
            case "up" -> new Guard("up", new Coordinates(guard.coordinates.x, guard.coordinates.y - 1));
            case "right" -> new Guard("right", new Coordinates(guard.coordinates.x + 1,guard.coordinates.y));
            case "down" -> new Guard("down", new Coordinates(guard.coordinates.x, guard.coordinates.y + 1));
            default -> new Guard("left", new Coordinates(guard.coordinates.x - 1, guard.coordinates.y));
        };
    }

    public Integer part2(boolean sample) {
        return 0;
    }

    private Maze buildMaze(int part, boolean sample) {
        var lines = reader.readLines(6, part, sample);

        Guard guard = null;
        var obstacles = new HashSet<Coordinates>();
        for (int row = 0; row < lines.size(); row++) {
            var line = lines.get(row);
            for (int col = 0; col < line.length(); col++) {
                var symbol = String.valueOf(line.charAt(col));
                if (symbol.equals("^")) {
                    guard = new Guard("up", new Coordinates(col, row));
                } else if (symbol.equals("#")) {
                    obstacles.add(new Coordinates(col, row));
                }
            }
        }

        return new Maze(guard, obstacles, lines.size(), lines.getFirst().length());
    }

    private record Guard(String dir, Coordinates coordinates) {}
    private record Maze(Guard guard, Set<Coordinates> obstacles, int cols, int rows) {}

    private record Coordinates(int x, int y) {}
}