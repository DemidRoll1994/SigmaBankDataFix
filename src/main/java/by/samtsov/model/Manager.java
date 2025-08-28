package by.samtsov.model;

public class Manager extends Worker{

    private String departmentName;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Manager");
        sb.append(",").append(id);
        sb.append(",").append(name);
        sb.append(",").append(salary);
        return sb.toString();
    }
}
