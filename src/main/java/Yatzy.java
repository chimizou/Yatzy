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


class DiceHand implements Iterable<Integer> {
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

    @Override
    public Iterator<Integer> iterator() {
        return stream().iterator();
    }

    public Stream<Integer> stream() {
        return IntStream.of(dice).boxed();
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

    public static int smallStraight(int d1, int d2, int d3, int d4, int d5) {
        int[] tallies;
        tallies = new int[6];
        tallies[d1 - 1] += 1;
        tallies[d2 - 1] += 1;
        tallies[d3 - 1] += 1;
        tallies[d4 - 1] += 1;
        tallies[d5 - 1] += 1;
        if (tallies[0] == 1 &&
                tallies[1] == 1 &&
                tallies[2] == 1 &&
                tallies[3] == 1 &&
                tallies[4] == 1)
            return 15;
        return 0;
    }

    public static int largeStraight(int d1, int d2, int d3, int d4, int d5) {
        int[] tallies;
        tallies = new int[6];
        tallies[d1 - 1] += 1;
        tallies[d2 - 1] += 1;
        tallies[d3 - 1] += 1;
        tallies[d4 - 1] += 1;
        tallies[d5 - 1] += 1;
        if (tallies[1] == 1 &&
                tallies[2] == 1 &&
                tallies[3] == 1 &&
                tallies[4] == 1
                && tallies[5] == 1)
            return 20;
        return 0;
    }

    public static int fullHouse(int d1, int d2, int d3, int d4, int d5) {
        int[] tallies;
        boolean _2 = false;
        int i;
        int _2_at = 0;
        boolean _3 = false;
        int _3_at = 0;


        tallies = new int[6];
        tallies[d1 - 1] += 1;
        tallies[d2 - 1] += 1;
        tallies[d3 - 1] += 1;
        tallies[d4 - 1] += 1;
        tallies[d5 - 1] += 1;

        for (i = 0; i != 6; i += 1)
            if (tallies[i] == 2) {
                _2 = true;
                _2_at = i + 1;
            }

        for (i = 0; i != 6; i += 1)
            if (tallies[i] == 3) {
                _3 = true;
                _3_at = i + 1;
            }

        if (_2 && _3)
            return _2_at * 2 + _3_at * 3;
        else
            return 0;
    }
}
