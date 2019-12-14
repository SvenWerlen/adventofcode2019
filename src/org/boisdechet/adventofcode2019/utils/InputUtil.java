package org.boisdechet.adventofcode2019.utils;

import org.boisdechet.adventofcode2019.coord.Point;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class InputUtil {

    private static final String INPUTS_FOLDER = "inputs";
    private static final String INPUT_FILE_PATTERN = "day%d_%d.txt";
    private static final String INPUT_SAMPLE_PATTERN = "day%d_s%d.txt";

    private static Path inputFolder;

    private static synchronized Path getInputsPath() {
        if (inputFolder == null) {
            inputFolder = Paths.get("", INPUTS_FOLDER).toAbsolutePath();
        }
        return inputFolder;
    }

    public static BufferedReader readInputSample(int day, int sampleId) throws IOException {
        String filename = String.format(INPUT_SAMPLE_PATTERN, day, sampleId);
        Path path = Paths.get(getInputsPath().toString(), filename);
        if( !path.toFile().exists() ) {
            throw new FileNotFoundException(String.format("Input sample file '%s' not found!", path.toString()));
        }
        return new BufferedReader(new FileReader(path.toFile()));
    }

    public static String readInputSampleAsString(int day, int sampleId) throws IOException {
        String filename = String.format(INPUT_SAMPLE_PATTERN, day, sampleId);
        Path path = Paths.get(getInputsPath().toString(), filename);
        if( !path.toFile().exists() ) {
            throw new FileNotFoundException(String.format("Input file '%s' not found!", path.toString()));
        }
        return Files.readString(path, StandardCharsets.UTF_8);
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

    public static int[] convertToIntArray(String value) {
        String[] instr = value.split(",");
        int[] result = new int[instr.length];
        for(int i=0; i<instr.length; i++) {
            result[i]=Integer.parseInt(instr[i]);
        }
        return result;
    }

    public static long[] convertToLongArray(String value) {
        String[] instr = value.split(",");
        long[] result = new long[instr.length];
        for(int i=0; i<instr.length; i++) {
            result[i]=Long.parseLong(instr[i]);
        }
        return result;
    }

    public static int[] convertToIntArrayNoSep(String value) {
        return Stream.of(value.split(""))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public static String convertToString(int[] value) {
        StringBuffer buf = new StringBuffer();
        for(int v : value) {
            buf.append(v);
        }
        return buf.toString();
    }

    public static Integer[] convertToIntegerArray(int[] intArray) {
        Integer[] result = new Integer[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            result[i] = Integer.valueOf(intArray[i]);
        }
        return result;
    }

    public static int[] convertToDigitArray(String value) {
        int[] result = new int[value.length()];
        for(int i=0; i<value.length(); i++) {
            result[i] = Character.getNumericValue(value.charAt(i));
        }
        return result;
    }

    /**
     * Reads input (string) and converts it to a list of coordinates
     * @param text input string
     * @param character character representing a point
     * @return list of points (coordinates)
     */
    public static List<Point> convertInputAsCoordinates(String text, char character) {
        List<Point> list = new ArrayList<>();
        int y = 0;
        for(String line : text.split("\n")) {
            int x = 0;
            for(char c : line.toCharArray()) {
                if(c == character) {
                    list.add(new Point(x,y));
                }
                x++;
            }
            y++;
        }
        return list;
    }
}