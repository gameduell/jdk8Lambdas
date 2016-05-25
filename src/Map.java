import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class that shows some Map operations
 *
 * @author jpra
 * copyright (c) 2003-2016 GameDuell GmbH, All Rights Reserved
 */
public class Map {

    public static void main(String[] args) {
        getBase64OfStrings();
        toNameOnly();
    }

    private static void getBase64OfStrings() {
        final int[] i = {0};
        final Stream<String> stream = Stream.generate(() -> "foo" + (i[0]++));
        final List<String> base64s = stream
                .map(str -> Base64.getEncoder().encodeToString(str.getBytes()))
                .limit(10) // note that if this is not present, we will hang forever!
                .collect(Collectors.toList());
        base64s.forEach(System.out::println);
    }

    private static void toNameOnly() {
        Employee.gimmeEmployees().stream()
                .map(Employee::getName)  //note that the stream here is of type Stream<String>
                .forEach(System.out::println);
    }
}
