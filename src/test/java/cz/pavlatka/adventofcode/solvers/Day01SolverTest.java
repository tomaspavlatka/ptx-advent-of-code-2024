package cz.pavlatka.adventofcode.solvers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Day01SolverTest {
    @Autowired
    private Day01Solver solver;

    @Test
    void testPart01() {
        var result = solver.solve(1, true);

        assertThat(result).isEqualTo("11");
    }

    @Test
    void testPart02() {
        var result = solver.solve(2, false);

        assertThat(result).isEqualTo("1320851");
    }
}