package by.samtsov.model;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private Manager manager;
    private List<Employee> employees= new ArrayList<>();


    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }
}
