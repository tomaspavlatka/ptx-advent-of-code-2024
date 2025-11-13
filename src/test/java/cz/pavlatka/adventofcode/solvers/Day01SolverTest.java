package cz.pavlatka.adventofcode.solvers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Day01SolverTest {
    @Autowired
    Day01Solver solver;

    @Test
    void testPart1() {
        var result = solver.part1(true);

        assertThat(result).isEqualTo(11);
    }

    @Test
    void solvePart1() {
        var result = solver.part1(false);

        assertThat(result).isEqualTo(1320851);
    }

    @Test
    void testPart2() {
        var result = solver.part2(true);

        assertThat(result).isEqualTo(31);
    }

    @Test
    void solvePart2() {
        var result = solver.part2(false);

        assertThat(result).isEqualTo(26859182);
    }
}