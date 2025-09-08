package by.samtsov.service;

import by.samtsov.io.SBIO;
import by.samtsov.model.Department;
import by.samtsov.model.Employee;
import by.samtsov.util.DepartmentParser;
import by.samtsov.util.FileWriter;

import java.util.Comparator;
import java.util.List;

import static by.samtsov.Main.LOG;

public class MainService {
    public void process(String[] args) {
        List<String> lines = SBIO.getValuesFromFiles(SBIO.findFiles());
        List<Department> departments = DepartmentParser.separateToDepartments(lines);
        try {
            doActionFromArgs(args, departments);
        } catch (Exception e) {
            LOG.error("can't sort or generate statistic");
        }
        FileWriter.writeFiles(departments);
    }

    private void doActionFromArgs(String[] args, List<Department> departments) {
        String sortField = null;
        Boolean ascOrder = null;
        boolean stat = false;
        Boolean statOutputToFile = null;
        String path = null;

        try {
            for (String arg : args) {
                boolean correctArg = false;
                if (arg.startsWith("--sort=") || arg.startsWith("-s=")) {
                    sortField = arg.substring(arg.indexOf("=") + 1);
                    correctArg = true;
                }
                if (arg.startsWith("--order=")) {
                    if ("asc".equalsIgnoreCase(arg.substring(arg.indexOf("=") + 1))) {
                        ascOrder = true;
                        correctArg = true;
                    } else if ("desc".equalsIgnoreCase(arg.substring(arg.indexOf("=") + 1))) {
                        ascOrder = false;
                        correctArg = true;
                    }
                }
                if (arg.equals("--stat")) {
                    stat = true;
                    correctArg = true;
                }
                if (arg.startsWith("--output=") || arg.startsWith("-o=")) {
                    if ("console".equalsIgnoreCase(arg.substring(arg.indexOf("=") + 1))) {
                        statOutputToFile = false;
                        correctArg = true;
                    } else if ("file".equalsIgnoreCase(arg.substring(arg.indexOf("=") + 1))) {
                        statOutputToFile = true;
                        correctArg = true;
                    }
                }
                if (arg.startsWith("--path=")) {
                    path = arg.substring(arg.indexOf("=") + 1);
                    correctArg = true;
                }
                if (!correctArg) LOG.error("Invalid argument: " + arg);
            }
        } catch (Exception e) {
            LOG.error("Can't parse arguments");
        }
        try {
            sortDepartments(departments, sortField, ascOrder);
        } catch (Exception e) {
            LOG.error("Can't sort Departments");
        }
        try {
            generateStatistic(departments, stat, statOutputToFile, path);
        } catch (Exception e) {
            LOG.error("Can't generate statistic");
        }
    }

    private void sortDepartments(List<Department> departments, String sortField, Boolean ascOrder) {
        if (sortField != null) {
            Comparator comparator = null;
            if ("name".equalsIgnoreCase(sortField)) {
                comparator = Comparator.comparing(Employee::getName);
            } else if ("salary".equalsIgnoreCase(sortField)) {
                comparator = Comparator.comparing(Employee::getSalary);
            }
            if (comparator != null) {
                if (Boolean.FALSE.equals(ascOrder))
                    comparator = comparator.reversed();
                for (Department department : departments) {
                    if (department.getEmployees() != null)
                        department.getEmployees().sort(comparator);
                }
            } else {
                LOG.error("Can't sort departments. Invalid field: " + sortField);
            }
        }
    }

    private void generateStatistic(List<Department> departments,
                                   boolean stat, Boolean statOutputToFile, String path) {
        if (checkForCorrectStatParams(stat, statOutputToFile, path)) {
            var result = new StringBuilder("department, min, max, mid\n");
            departments.stream()
                    .sorted(new DepartmentComparator())
                    .forEach(department -> result.append(calculateStatisticForDept(department)));
            if (Boolean.TRUE.equals(statOutputToFile)) {
                FileWriter.writeToFile(result.toString(), path);
            } else {
                System.out.println(result);
            }
        }

    }

    private String calculateStatisticForDept(Department department) {
        double min = 0;
        double max = 0;
        double mid = 0;
        if (department.getEmployees() != null && department.getEmployees().size() > 0) {
            min = max = mid = department.getEmployees().get(0).getSalary();
            for (int i = 1; i < department.getEmployees().size(); i++) {
                Employee emp = department.getEmployees().get(i);
                if (emp.getSalary() < min) min = emp.getSalary();
                if (max < emp.getSalary()) max = emp.getSalary();
                mid += emp.getSalary();
            }
            mid /= department.getEmployees().size();
        }
        return String.format("%s, %.2f, %.2f, %.2f %n",
                department.getManager().getDepartmentName(), min, max, mid);
    }

    private boolean checkForCorrectStatParams(boolean stat,
                                              Boolean statOutputToFile, String path) {
        if (!stat && statOutputToFile != null)
            LOG.error("Output parameter without stat parameter");
        if (!stat && path != null)
            LOG.error("Path parameter without stat parameter");
        if (stat && !Boolean.TRUE.equals(statOutputToFile) && path != null)
            LOG.error("Path parameter without output parameter");
        if (stat && Boolean.TRUE.equals(statOutputToFile) && path == null)
            LOG.error("Output parameter without path parameter");
        return stat && (Boolean.TRUE.equals(statOutputToFile) == (path != null));
    }

    class DepartmentComparator implements Comparator<Department> {
        public int compare(Department a, Department b) {
            return a.getManager().getDepartmentName().toUpperCase().compareTo(
                    b.getManager().getDepartmentName().toUpperCase());
        }
    }
}
