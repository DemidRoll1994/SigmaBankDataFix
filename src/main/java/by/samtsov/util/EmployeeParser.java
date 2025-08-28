package by.samtsov.util;

import by.samtsov.model.Employee;
import by.samtsov.model.Manager;
import by.samtsov.model.Worker;

import static by.samtsov.Main.LOG;


public class EmployeeParser {

    public static Worker getWorkerFromString(String line) {
        String[] values = line.split(",");
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i].trim();
        }
        if ("manager".equalsIgnoreCase(values[0])) {
            return getManagerFromString(values,line);
        } else if ("employee".equalsIgnoreCase(values[0])) {
            return getEmployeeFromString(values,line);
        } else {
            LOG.error(line);
            return null;
        }
    }

    private static Manager getManagerFromString(String[] values, String line) {
        try {
            if (Long.parseLong(values[1]) > 0
                    && !values[2].isEmpty()
                    && Double.parseDouble(values[3]) > 0
                    && !values[4].isEmpty()) {
                Manager manager = new Manager();
                manager.setId(Long.parseLong(values[1]));
                manager.setName(values[2]);
                manager.setSalary(Double.parseDouble(values[3]));
                manager.setDepartmentName(values[4]);
                return manager;
            } else {
                LOG.error(line);
                return null;
            }
        } catch (Exception e) {
            LOG.error(line);
            return null;
        }
    }

    private static Employee getEmployeeFromString(String[] values, String line) {
        try {
            if (Long.parseLong(values[1]) > 0
                    && !values[2].isEmpty()
                    && Double.parseDouble(values[3]) > 0
                    && Long.parseLong(values[4]) > 0) {
                Employee employee = new Employee();
                employee.setId(Long.parseLong(values[1]));
                employee.setName(values[2]);
                employee.setSalary(Double.parseDouble(values[3]));
                employee.setManagerId(Long.parseLong(values[4]));
                return employee;
            } else {
                LOG.error(line);
                return null;
            }
        } catch (Exception e) {
            LOG.error(line);
            return null;
        }
    }
}
