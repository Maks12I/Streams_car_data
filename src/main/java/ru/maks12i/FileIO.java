package ru.maks12i;

import lombok.Data;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FileIO {

    public void writeFile(Iterable<?> iterable, String filename) throws IOException, URISyntaxException {

        Path path = Paths.get("")
                .toAbsolutePath()
                .resolve("src")
                .resolve("main")
                .resolve("resources")
                .resolve(filename);
        if (Files.notExists(path))
            Files.createFile(path);

        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (Object elem : iterable) {
                bw.write(elem.toString());
                bw.newLine();
            }
        }
    }

    public List<String> readFile() throws URISyntaxException, IOException {

        Path path = Paths.get("").toAbsolutePath()
                    .resolve("src")
                    .resolve("main")
                    .resolve("resources")
                    .resolve("CAR_DATA.csv");

        List<String> rf =
            Files.lines(path, StandardCharsets.UTF_8)
            .map(str -> str.replaceAll("(,)\\1+", ",Not stated,"))
            .map(str -> str.replaceAll("\\s+", " "))
            .map(String::trim)
            .flatMap(line -> Arrays.stream(line.split("\\n+")))
            .collect(Collectors.toList());

        return rf;
    }

}
