import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class that shows some usage of flatMap operation
 *
 * @author jpra
 * copyright (c) 2003-2016 GameDuell GmbH, All Rights Reserved
 */
public class Flatten {

    public static void main(String[] args) {
        System.out.println(flattenAllTheThings(IntStream.range(0, 10)));
    }

    private static IntStream expand(int times) {
        return IntStream.generate(() -> times).limit(times);
    }

    private static List<Integer> flattenAllTheThings(IntStream stream) {
        return stream
                .flatMap(Flatten::expand)
                .boxed()
                .collect(Collectors.toList());
    }
}
