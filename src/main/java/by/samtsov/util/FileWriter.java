package by.samtsov.util;

import by.samtsov.model.Department;
import by.samtsov.model.Employee;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static by.samtsov.Main.LOG;

public class FileWriter {
    public static void writeFiles(List<Department> departments) {
        for (Department department : departments) {
            String departmentFile = generateDepartmentFile(department);
            if (departmentFile != null) {
                writeToFile(departmentFile, department.getManager().getDepartmentName());
            }
        }
    }


    private static void writeToFile(String linesToWrite,
                                    String departmentName) {
        try {
            Files.write(Paths.get(departmentName + ".sb"),
                    linesToWrite.getBytes());
        } catch (IOException e) {
            System.out.println("Can't write a file");
        }
    }


    private static String generateDepartmentFile(Department department) {
        boolean isDeptCorrect = (department.getManager() != null);
        StringBuilder builder = new StringBuilder();
        if (isDeptCorrect) {
            builder.append(department.getManager().toString());
            for (Employee employee : department.getEmployees()) {
                builder.append("\n");
                builder.append(employee.toString());
            }
            return builder.toString();
        } else {
            for (Employee employee : department.getEmployees()) {
                builder.append(employee.toString());
                builder.append("\n");
            }
            LOG.error(builder.toString());
            return null;
        }
    }
}
