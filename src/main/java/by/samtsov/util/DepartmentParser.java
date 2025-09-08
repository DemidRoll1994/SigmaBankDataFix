package by.samtsov.util;

import by.samtsov.model.Department;
import by.samtsov.model.Employee;
import by.samtsov.model.Manager;
import by.samtsov.model.Worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static by.samtsov.Main.LOG;

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
        List<Department> departmentsList = new ArrayList(departments.values());
        for (int i=0;i<departmentsList.size();i++) {
            if (departmentsList.get(i).getManager()==null){
                for (Employee emp: departmentsList.get(i).getEmployees()) {
                    LOG.error(emp.toString());
                }
                departmentsList.remove(i--);
            }
        }
        return departmentsList;
    }


}
