package cz.pavlatka.adventofcode.common;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class NumberParser {
    public List<Long> getLongs(String line) {
        var pattern = Pattern.compile("\\d+");
        var matcher = pattern.matcher(line);

        var numbers = new ArrayList<Long>();
        while (matcher.find()) {
            numbers.add(Long.valueOf(matcher.group()));
        }

        return numbers;
    }
}
