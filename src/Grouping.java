import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class that shows some grouping operations
 *
 * @author jpra
 * copyright (c) 2003-2016 GameDuell GmbH, All Rights Reserved
 */
public class Grouping {

    public static void main(String[] args) {
        final Map<String, List<Employee>> employeeByDept = organizeThem(Employee.gimmeEmployees());
        for (Map.Entry<String, List<Employee>> entry : employeeByDept.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            entry.getValue().forEach(System.out::println);
        }
    }

    private static Map<String, List<Employee>> organizeThem(List<Employee> employeeList) {
        return employeeList.stream().collect(Collectors.groupingBy(Employee::getDepartment));
    }
}
