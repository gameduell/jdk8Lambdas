import java.util.ArrayList;
import java.util.List;

/**
 * Employee dummy class
 *
 * @author jpra
 * copyright (c) 2003-2016 GameDuell GmbH, All Rights Reserved
 */
class Employee {

    private final String name;
    private final int salary;
    private final String department;

    Employee(String _name, String _department, int _salary) {
        name = _name;
        salary = _salary;
        department = _department;
    }

    String getName() {
        return name;
    }

    int getSalary() {
        return salary;
    }

    String getDepartment() {
        return department;
    }


    /**
     * tiny helper method that gives me employees
     * @return a list of employees
     */
    static List<Employee> gimmeEmployees() {
        List<Employee> employeeList = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            employeeList.add(new Employee("name" + i, "department"+(i%3), 35000 + (i * 1000)));
        }
        return employeeList;
    }
}
