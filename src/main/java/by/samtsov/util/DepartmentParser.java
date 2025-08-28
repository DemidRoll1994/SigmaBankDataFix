package by.samtsov.util;

import by.samtsov.model.Department;
import by.samtsov.model.Employee;
import by.samtsov.model.Manager;
import by.samtsov.model.Worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DepartmentParser {
    public static List<Department> separateToDepartments(List<String> lines) {
        HashMap<Long, Department> departments = new HashMap<>();
        for (String line : lines) {
            Worker worker = EmployeeParser.getWorkerFromString(line);
            if (worker instanceof Manager) {
                Manager manager = (Manager) worker;
                departments.putIfAbsent(manager.getId(), new Department());
                departments.get(manager.getId()).setManager(manager);
            } else if (worker instanceof Employee) {
                Employee employee = (Employee) worker;
                departments.putIfAbsent(employee.getManagerId(),new Department());
                departments.get(employee.getManagerId()).addEmployee(employee);
            }
        }
        return new ArrayList(departments.values());
    }


}
