package by.samtsov;

import by.samtsov.io.SBIO;
import by.samtsov.model.Department;
import by.samtsov.util.DepartmentParser;
import by.samtsov.util.FileWriter;
import by.samtsov.util.Logger;

import java.util.List;

public class Main {
    public static final Logger LOG = new Logger();

    public static void main(String[] args) {
        try {
            List<String> lines = SBIO.getValuesFromFiles(SBIO.findFiles());
            List<Department> departments = DepartmentParser.separateToDepartments(lines);
            FileWriter.writeFiles(departments);
            LOG.finilize();
        }catch (Exception e) {
            System.out.println("Unexpected error");
        }
    }
}