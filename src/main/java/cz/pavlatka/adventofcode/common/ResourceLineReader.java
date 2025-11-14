package cz.pavlatka.adventofcode.common;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class ResourceLineReader {
    public List<String> readLines(int day, int part, boolean sample) {
        var input = "inputs/day" + day;
        if (sample) {
            input += ".sample" + part;
        }

        return readLines(input);
    }

    @SneakyThrows
    private List<String> readLines(String path) {
        var lines = new ArrayList<String>();

        try (InputStream inputStream = ResourceLineReader.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + path);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }
        }

        return lines;
    }
}
