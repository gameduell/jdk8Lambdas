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

        DoubleUnaryOperator tax = price -> price * 19 / 100 + price;
        DoubleUnaryOperator voucher = price -> price - 10;

        System.out.println(calculateGrossPrice(60,
                tax,
                voucher
        ));

        System.out.println(calculateGrossPriceWrongly(60,
                tax,
                voucher
        ));

        System.out.println(calculateGrossSingleOp(60,
                tax.andThen(voucher)

        ));

        System.out.println(calculateGrossSingleOp(60,
                tax.compose(voucher)

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

    private static double calculateGrossSingleOp(double price, DoubleUnaryOperator op) {

        return op.applyAsDouble(price);

    }
}
