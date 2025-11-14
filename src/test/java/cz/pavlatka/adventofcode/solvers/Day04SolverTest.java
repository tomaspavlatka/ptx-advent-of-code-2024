package cz.pavlatka.adventofcode.solvers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Day04SolverTest {
    @Autowired
    Day04Solver solver;

    @Test
    void testPart1() {
        var result = solver.part1(true);

        assertThat(result).isEqualTo(18);
    }

    @Test
    void solvePart1() {
        var result = solver.part1(false);

        assertThat(result).isEqualTo(2543);
    }

    @Test
    void testPart2() {
        var result = solver.part2(true);

        assertThat(result).isEqualTo(9);
    }

    @Test
    void solvePart2() {
        var result = solver.part2(false);

        assertThat(result).isEqualTo(1930);
    }
}