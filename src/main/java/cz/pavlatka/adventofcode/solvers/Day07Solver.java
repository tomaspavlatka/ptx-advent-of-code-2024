package cz.pavlatka.adventofcode.solvers;

import cz.pavlatka.adventofcode.common.NumberParser;
import cz.pavlatka.adventofcode.common.ResourceLineReader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class Day07Solver {
    private final ResourceLineReader reader;
    private final NumberParser numberParser;

    public Long part1(boolean sample) {
        return reader.readLines(7, 1, sample)
                .stream()
                .map(this::toEquation)
                .filter(equation -> isValid(equation, List.of("+", "*")))
                .map(eq -> eq.result)
                .reduce(0L, Long::sum);
    }

    private Boolean isValid(Equation equation, List<String> operations) {
        var configurations = configurations(operations, equation.numbers.size());

        var data = configurations.stream().map(configuration -> {
            long sum = -1;
            for (int i = 0; i < equation.numbers.size(); i++) {
                var number = equation.numbers.get(i);
                if (sum == -1) {
                    sum = number;
                } else {
                    var operation = configuration.get(i - 1);

                    if (operation.equals("+")) {
                        sum += number;
                    } else if (operation.equals("*")) {
                        sum *= number;
                    } else {
                        var value = sum + String.valueOf(number);
                        sum = Long.parseLong(value);
                    }
                }

                if (sum > equation.result) {
                    return -1;
                }
            }

            return sum;
        })
        .filter(sum -> sum.equals(equation.result))
        .toList();

        return !data.isEmpty();
    }

    public static List<List<String>> configurations(List<String> operations, int n) {
        if (n == 0) {
            return List.of(new ArrayList<>());
        }

        List<List<String>> result = new ArrayList<>();

        for (String operation : operations) {
            var tail = configurations(operations, n - 1).stream().map(conf -> {
                var c = new ArrayList<String>();
                c.add(operation);
                c.addAll(conf);
                return c;
            }).toList();
            result.addAll(tail);
        }

        return result;
    }

    private Equation toEquation(String line) {
        var numbers = numberParser.getLongs(line);
        var result = numbers.getFirst();
        numbers.removeFirst();

        return new Equation(result, numbers);
    }

    public Long part2(boolean sample) {
        return reader.readLines(7, 2, sample)
                .stream()
                .map(this::toEquation)
                .filter(equation -> isValid(equation, List.of("+", "*", "||")))
                .map(eq -> eq.result)
                .reduce(0L, Long::sum);
    }

    private record Equation(Long result, List<Long> numbers) {}
}