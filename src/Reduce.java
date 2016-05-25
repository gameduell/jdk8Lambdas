import java.util.List;
import java.util.stream.IntStream;

/**
 * Class that shows some Reduce operations
 *
 * @author jpra
 * copyright (c) 2003-2016 GameDuell GmbH, All Rights Reserved
 */
public class Reduce {

    public static void main(String[] args) {
        callingClosedStreams();
        intStreams();
        fromLeftToRight();
        sumOfSalaries();
        sumOfSalariesOfDept();
    }

    /**
     * Reduce on streams go from left to right
     */
    private static void fromLeftToRight() {
        IntStream stream = IntStream.range(0, 10);
        final String reduced = stream.mapToObj(Integer::toString).reduce("", (x, y) -> x + "-" + y);
        System.out.println(reduced);
    }

    /**
     * It is illegal to called an already closed stream
     */
    private static void callingClosedStreams() {
        IntStream stream = IntStream.range(0, 100);
        System.out.println(stream.sum());
        try {
            System.out.println(stream.average());
        } catch (IllegalStateException e) {
            System.out.println("You can't call a closed stream!");
        }
    }

    /**
     * IntStreams have interesting methods like sum, average, or summaryStatistics
     */
    private static void intStreams() {
        IntStream stream = IntStream.range(0, 100);
        System.out.println(stream.summaryStatistics());
    }

    /**
     * Typical example of reduce
     */
    private static void sumOfSalaries() {
        final List<Employee> employees = Employee.gimmeEmployees();
        final Integer salaries = employees.stream()
                .reduce(0, (accumulated, emp) -> accumulated + emp.getSalary(), Integer::sum);
        System.out.println(salaries);
    }

    /**
     * Typical example of reduce with some more complexity, for example filtering by department
     */
    private static void sumOfSalariesOfDept() {
        final List<Employee> employees = Employee.gimmeEmployees();
        final Integer salaries = employees.stream()
                .filter(p -> p.getDepartment().endsWith("0"))
                .reduce(0, (accumulated, emp) -> accumulated + emp.getSalary(), Integer::sum);
        System.out.println(salaries);
    }

}
