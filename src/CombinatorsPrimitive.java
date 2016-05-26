import java.util.function.DoubleUnaryOperator;
import java.util.stream.Stream;

/**
 * Class that shows some function combinations examples
 *
 * @author jpra
 * copyright (c) 2003-2016 GameDuell GmbH, All Rights Reserved
 */
public class CombinatorsPrimitive {

    public static void main(String[] args) {

        System.out.println(calculateGrossPrice(60,
                price -> price * 19 / 100 + price,
                price -> price - 10
        ));

        System.out.println(calculateGrossPriceWrongly(60,
                price -> price * 19 / 100 + price,
                price -> price - 10
        ));
    }


    private static double calculateGrossPrice(double price, DoubleUnaryOperator... ops) {

        //andThen applies the functions in the order they appear. f.andThen(g) --> g(f(x))
        return Stream.of(ops).reduce(DoubleUnaryOperator.identity(), DoubleUnaryOperator::andThen).applyAsDouble(price);
    }

    private static double calculateGrossPriceWrongly(double price, DoubleUnaryOperator... ops) {

        //compose applies the functions in reverse order. f.compose(g) --> f(g(x))
        return Stream.of(ops).reduce(DoubleUnaryOperator.identity(), DoubleUnaryOperator::compose).applyAsDouble(price);
    }
}
