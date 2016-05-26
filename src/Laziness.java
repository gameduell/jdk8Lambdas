import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * This class demonstrates the laziness nature of Streams
 *
 * @author jpra
 * copyright (c) 2003-2016 GameDuell GmbH, All Rights Reserved
 */
public class Laziness {

    private static final List<String> ACTION_GAMES = new ArrayList<>();
    static {
        ACTION_GAMES.add("mbs");
        ACTION_GAMES.add("mjj");
        ACTION_GAMES.add("bux");
        ACTION_GAMES.add("jwl");
    }
    private static final List<String> CARD_GAMES = new ArrayList<>();
    static {
        CARD_GAMES.add("soh");
        CARD_GAMES.add("spi");
        CARD_GAMES.add("spp");
        CARD_GAMES.add("spl");
    }

    private static final List<String> LOGIC_GAMES = new ArrayList<>();
    static {
        LOGIC_GAMES.add("wos");
        LOGIC_GAMES.add("wob");
        LOGIC_GAMES.add("hid");
        LOGIC_GAMES.add("sdk");
    }

    public static void main(String[] args) {
        System.out.println("With functions!");
        System.out.println(decideIfGameDuellGame("World of Warcraft"));
        System.out.println(decideIfGameDuellGame("mbs"));
        System.out.println(decideIfGameDuellGame("spp"));

        System.out.println("\n\n\n\nWith lambdas!");
        System.out.println(decideIfGameDuellGameDirectLambdas("World of Warcraft"));
        System.out.println(decideIfGameDuellGameDirectLambdas("mbs"));
        System.out.println(decideIfGameDuellGameDirectLambdas("spp"));

        System.out.println("\n\n\n\nUsing closures!");
        System.out.println(decideIfGameDuellGameDirectLambdasAndClousure("World of Warcraft"));
        System.out.println(decideIfGameDuellGameDirectLambdasAndClousure("mbs"));
        System.out.println(decideIfGameDuellGameDirectLambdasAndClousure("spp"));
    }

    private static String decideIfGameDuellGame(String game) {
        return Stream.<Predicate<String>>of(
                Laziness::isActionGame,
                Laziness::isLogicGame,
                Laziness::isCardGame)
                .map(f -> f.test(game)) // we execute the predicate
                .filter(b -> b) // we check if the predicate returned true
                .findFirst() // we want the first one
                .map(b -> game + " is a GameDuell Game")
                .orElse(game + " is not a GameDuell Game");
    }

    private static boolean isActionGame(String gameType) {
        System.out.println("Checking Action Games");
        return ACTION_GAMES.contains(gameType);
    }

    private static boolean isCardGame(String gameType) {
        System.out.println("Checking Card Games");
        return CARD_GAMES.contains(gameType);
    }

    private static boolean isLogicGame(String gameType) {
        System.out.println("Checking Logic Games");
        return LOGIC_GAMES.contains(gameType);
    }

    private static String decideIfGameDuellGameDirectLambdas(String game) {
        Predicate<String> isActionGame = aGame -> {
            System.out.println("Checking Action Games");
            return ACTION_GAMES.contains(aGame);
        };
        Predicate<String> isLogicGame = aGame -> {
            System.out.println("Checking Logic Games");
            return LOGIC_GAMES.contains(aGame);
        };
        Predicate<String> isCardGame = aGame -> {
            System.out.println("Checking Card Games");
            return CARD_GAMES.contains(aGame);
        };

        return Stream.of(
                isActionGame,
                isLogicGame,
                isCardGame)
                .map(f -> f.test(game)) // we execute the predicate
                .filter(b -> b) // we check if the predicate returned true
                .findFirst() // we want the first one
                .map(b -> game + " is a GameDuell Game")
                .orElse(game + " is not a GameDuell Game");
    }

    private static String decideIfGameDuellGameDirectLambdasAndClousure(String game) {
        Supplier<Boolean> isActionGame = () -> {
            System.out.println("Checking Action Games");
            return ACTION_GAMES.contains(game);
        };
        Supplier<Boolean> isLogicGame = () -> {
            System.out.println("Checking Logic Games");
            return LOGIC_GAMES.contains(game);
        };
        Supplier<Boolean> isCardGame = () -> {
            System.out.println("Checking Card Games");
            return CARD_GAMES.contains(game);
        };

        return Stream.of(
                isActionGame,
                isLogicGame,
                isCardGame)
                .map(Supplier::get) // we execute the predicate
                .filter(b -> b) // we check if the predicate returned true
                .findFirst() // we want the first one
                .map(b -> game + " is a GameDuell Game")
                .orElse(game + " is not a GameDuell Game");
    }

}
