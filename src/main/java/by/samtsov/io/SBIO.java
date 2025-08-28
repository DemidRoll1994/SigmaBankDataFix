package by.samtsov.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SBIO {// .sb-files input/output


    private static final String CURRENT_DIRECTORY_PATH = ".";
    private static final String EXTENTION = ".sb";

    public static List<File> findFiles() {
        List<File> filesWithSBExtension = new ArrayList<>();
        File[] files = new File(CURRENT_DIRECTORY_PATH).listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(EXTENTION.toLowerCase())) {
                    filesWithSBExtension.add(file);
                }
            }
        }
        return filesWithSBExtension;
    }

    public static List<String> getValuesFromFiles(List<File> files) {
        List<String> employees = new ArrayList<>();
        for (File file : files) {
            employees.addAll(getValuesFromFile(file));
        }
        return employees;
    }


    public static List<String> getValuesFromFile(File file) {
        try (var lineStream = Files.lines(file.toPath())) {
            return lineStream.collect(Collectors.toList());
        } catch (IOException ignored) {
            return new ArrayList();
        }
    }
}
