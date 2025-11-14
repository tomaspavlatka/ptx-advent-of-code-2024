package cz.pavlatka.adventofcode.solvers;

import cz.pavlatka.adventofcode.common.ResourceLineReader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component
@AllArgsConstructor
public class Day03Solver {
    private final ResourceLineReader reader;

    public Integer part1(boolean sample) {
        return getMemory(1, sample)
                .stream().map(this::memorySum).reduce(0, Integer::sum);
    }

    public Integer part2(boolean sample) {
        var memory = String.join("", getMemory(2, sample));

        var pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)|(do)\\(\\)|(don't)\\(\\)");
        var matcher = pattern.matcher(memory);

        var sum = 0;
        var enabled = true;

        while(matcher.find()) {
            if (matcher.group(4) != null) {
                enabled = false;
            } else if (matcher.group(3) != null) {
                enabled = true;
            } else if (enabled) {
                sum += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
            }
        }

        return sum;
    }

    private Integer memorySum(String memory) {
        var pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        var matcher = pattern.matcher(memory);

        var sum = 0;
        while (matcher.find()) {
            sum += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
        }

        return sum;
    }

    private List<String> getMemory(int part, boolean sample) {
        return reader.readLines(3, part, sample);
    }
}