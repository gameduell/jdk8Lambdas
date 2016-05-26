import java.math.BigDecimal;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Class that shows some function combinations examples
 *
 * @author jpra
 * copyright (c) 2003-2016 GameDuell GmbH, All Rights Reserved
 */
public class CombinatorsBigDecimals {

    public static void main(String[] args) {

        System.out.println(calculateGrossPriceFunctions(new BigDecimal("60"),
                price -> price.multiply(new BigDecimal("19")).divide(new BigDecimal("100")).add(price),
                price -> price.subtract(new BigDecimal("10"))
        ));

        System.out.println(calculateGrossPriceWrongly(new BigDecimal("60"),
                price -> price.multiply(new BigDecimal("19")).divide(new BigDecimal("100")).add(price),
                price -> price.subtract(new BigDecimal("10"))
        ));
    }


    private static BigDecimal calculateGrossPrice(BigDecimal price, UnaryOperator<BigDecimal>... ops) {

//        return Stream.of(ops).reduce(UnaryOperator.identity(), UnaryOperator::andThen).apply(price);
//        return Stream.of(ops).reduce(UnaryOperator.identity(), (a, b) -> a.andThen(b)).apply(price);
//        return Stream.of(ops).reduce(UnaryOperator.identity(), (UnaryOperator<BigDecimal> a , UnaryOperator<BigDecimal> b) -> a.andThen(b)).apply(price);
          return BigDecimal.ZERO;
    }

    private static BigDecimal calculateGrossPriceFunctions(BigDecimal price, Function<BigDecimal, BigDecimal>... ops) {

        //andThen applies the functions in the order they appear. f.andThen(g) --> g(f(x))
        return Stream.of(ops).reduce(Function.identity(), Function::andThen).apply(price);
    }

    private static BigDecimal calculateGrossPriceWrongly(BigDecimal price, Function<BigDecimal, BigDecimal>... ops) {

        //compose applies the functions in reverse order. f.compose(g) --> f(g(x))
        return Stream.of(ops).reduce(Function.identity(), Function::compose).apply(price);
    }
}
