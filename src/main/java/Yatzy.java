import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;


class DiceHand {
    private final int[] dice;

    public DiceHand(int d1, int d2, int d3, int d4, int d5) {
        this.dice = new int[]{d1, d2, d3, d4, d5};
    }

    public int sumValues(int value) {
        return stream()
                .filter(n -> n == value)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int die(int index) {
        return dice[index];
    }

    public Stream<Integer> stream() {
        return IntStream.of(dice).boxed();
    }

    public List<Integer> getStraightDice(DiceHand diceHand) {
        Map<Integer, Long> counts = diceHand.stream()
                .collect(groupingBy(d -> d, counting()));

        return counts.entrySet().stream()
                .filter(e -> e.getValue() == 1)
                .distinct()
                .map(Entry::getKey)
                .collect(Collectors.toList());
    }
}

public class Yatzy {
    public static int chance(DiceHand diceHand) {
        return diceHand.stream().mapToInt(Integer::intValue).sum();
    }

    public static int yatzy(DiceHand dice) {
        if (dice.stream().distinct().count() == 1) {
            return 50;
        }
        return 0;
    }

    public static int ones(DiceHand diceHand) {
        return diceHand.sumValues(1);
    }

    public static int twos(DiceHand diceHand) {
        return diceHand.sumValues(2);
    }

    public static int threes(DiceHand diceHand) {
        return diceHand.sumValues(3);
    }

    public static int fours(DiceHand diceHand) {
        return diceHand.sumValues(4);
    }

    public static int fives(DiceHand diceHand) {
        return diceHand.sumValues(5);
    }

    public static int sixes(DiceHand diceHand) {
        return diceHand.sumValues(6);
    }

    public static int score_pair(DiceHand diceHand) {
        Map<Integer, Long> counts = diceHand.stream()
                .collect(groupingBy(d -> d, counting()));
        OptionalInt maxDie = counts.entrySet().stream()
                .filter(e -> e.getValue() >= 2)
                .mapToInt(Entry::getKey)
                .max();

        return maxDie.orElse(0) * 2;
    }

    public static int two_pair(DiceHand diceHand) {
        Map<Integer, Long> counts = diceHand.stream()
                .collect(groupingBy(d -> d, counting()));

        List<Integer> diceTwoOrMore = counts.entrySet().stream()
                .filter(e -> e.getValue() >= 2)
                .map(Entry::getKey)
                .collect(Collectors.toList());

        if (diceTwoOrMore.size() != 2) {
            return 0;
        }

        return diceTwoOrMore.stream().mapToInt(Integer::intValue).sum() * 2;

    }

    public static int three_of_a_kind(DiceHand diceHand) {
        Map<Integer, Long> counts = diceHand.stream()
                .collect(groupingBy(d -> d, counting()));
        OptionalInt maxDie = counts.entrySet().stream()
                .filter(e -> e.getValue() >= 3)
                .mapToInt(Entry::getKey)
                .findAny();

        return maxDie.orElse(0) * 3;
    }

    public static int four_of_a_kind(DiceHand diceHand) {
        Map<Integer, Long> counts = diceHand.stream()
                .collect(groupingBy(d -> d, counting()));
        OptionalInt maxDie = counts.entrySet().stream()
                .filter(e -> e.getValue() >= 4)
                .mapToInt(Entry::getKey)
                .findAny();

        return maxDie.orElse(0) * 4;
    }

    public static int smallStraight(DiceHand diceHand) {
        List<Integer> diceTwoOrMore = diceHand.getStraightDice(diceHand);

        if (diceTwoOrMore.size() < 4) {
            return 0;
        }

        return diceTwoOrMore.stream().mapToInt(Integer::intValue).sum() == 15 ? 15 : 0;
    }

    public static int largeStraight(DiceHand diceHand) {
        List<Integer> straightDice = diceHand.getStraightDice(diceHand);

        if (straightDice.size() < 4) {
            return 0;
        }

        return straightDice.stream().mapToInt(Integer::intValue).sum() == 20 ? 20 : 0;
    }


    public static int fullHouse(DiceHand diceHand) {
        Map<Integer, Long> counts = diceHand.stream()
                .collect(groupingBy(d -> d, counting()));

        List<Integer> diceTwoOrMore = counts.entrySet().stream()
                .filter(e -> e.getValue() >= 2)
                .map(Entry::getKey)
                .collect(Collectors.toList());

        if (diceTwoOrMore.size() != 2) {
            return 0;
        }

        return (int) counts.entrySet().stream().mapToLong(mapEntry -> mapEntry.getKey() * mapEntry.getValue()).sum();
    }
}
