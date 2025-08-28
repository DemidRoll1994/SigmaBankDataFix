package by.samtsov.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Logger {
    private static StringBuilder builder = new StringBuilder();

    public static void error(String message) {
        builder.append(message).append("\n");
    }


    public void finilize() {
        try {
            Files.writeString(Paths.get("error.log"), builder.toString());
        } catch (IOException e) {
            System.out.println("Can't write error.log");
        }
    }
}
