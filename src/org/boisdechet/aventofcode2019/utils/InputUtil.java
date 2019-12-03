package org.boisdechet.aventofcode2019.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InputUtil {

    private static final String INPUTS_FOLDER = "inputs";
    private static final String INPUT_FILE_PATTERN = "day%d_%d.txt";

    private static Path inputFolder;

    private static synchronized Path getInputsPath() {
        if (inputFolder == null) {
            inputFolder = Paths.get("", INPUTS_FOLDER).toAbsolutePath();
        }
        return inputFolder;
    }

    public static BufferedReader readInput(int day, boolean firstPart) throws IOException {
        String filename = String.format(INPUT_FILE_PATTERN, day, firstPart ? 1 : 2);
        Path path = Paths.get(getInputsPath().toString(), filename);
        if( !path.toFile().exists() ) {
            throw new FileNotFoundException(String.format("Input file '%s' not found!", path.toString()));
        }
        return new BufferedReader(new FileReader(path.toFile()));
    }

    public static String readInputAsString(int day, boolean firstPart) throws IOException {
        String filename = String.format(INPUT_FILE_PATTERN, day, firstPart ? 1 : 2);
        Path path = Paths.get(getInputsPath().toString(), filename);
        if( !path.toFile().exists() ) {
            throw new FileNotFoundException(String.format("Input file '%s' not found!", path.toString()));
        }
        return Files.readString(path, StandardCharsets.UTF_8);
    }

}
