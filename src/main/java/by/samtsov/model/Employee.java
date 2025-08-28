package by.samtsov.model;

public class Employee extends Worker{

    private long managerId;

    public long getManagerId() {
        return managerId;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Employee");
        sb.append(",").append(id);
        sb.append(",").append(name);
        sb.append(",").append(salary);
        sb.append(",").append(managerId);
        return sb.toString();
    }
}
