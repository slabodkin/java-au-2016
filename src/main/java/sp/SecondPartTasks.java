package sp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SecondPartTasks {

    private SecondPartTasks() {}

    // Найти строки из переданных файлов, в которых встречается указанная подстрока.
    public static List<String> findQuotes(List<String> paths, CharSequence sequence) {
        return paths
                .stream()
                .flatMap(somePath -> {
                    Stream<String> toFilter = null;
                    try {
                        toFilter = Files.lines(Paths.get(somePath));
                    } catch (IOException e) {
                        toFilter = Stream.empty();
                    }
                    return toFilter;
                })
                .filter(str -> str.contains(sequence))
                .collect(Collectors.toList());
    }


    // В квадрат с длиной стороны 1 вписана мишень.
    // Стрелок атакует мишень и каждый раз попадает в произвольную точку квадрата.
    // Надо промоделировать этот процесс с помощью класса java.util.Random
    // и посчитать, какова вероятность попасть в мишень.
    public static double piDividedBy4() {
        Random r = new Random();
        final int maxIterationNumber = 10000;
        final double zeroPointFive = 0.5;
        return Stream
                .generate(() -> {
            double xCoord = r.nextDouble();
            double yCoord = r.nextDouble();
            return Math.sqrt(((xCoord - zeroPointFive) * (xCoord - zeroPointFive))
                    + ((yCoord - zeroPointFive) * (yCoord - zeroPointFive)));
        })
                .limit(maxIterationNumber)
                .mapToDouble(shootDistance -> {
                    if (shootDistance <= zeroPointFive) {
                        return  1.0;
                    }
                    return 0.0;
                })
                .average()
                .getAsDouble();
    }

    // Дано отображение из имени автора в список с содержанием его произведений.
    // Надо вычислить, чья общая длина произведений наибольшая.
    public static String findPrinter(Map<String, List<String>> compositions) {
        return compositions
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        (Map.Entry<String, List<String>> entry) -> entry
                                .getValue()
                                .stream()
                                .mapToInt(str -> str.length())
                                .sum()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .get();
    }

    // Вы крупный поставщик продуктов. Каждая торговая сеть делает вам заказ в виде Map<Товар, Количество>.
    // Необходимо вычислить, какой товар и в каком количестве надо поставить.
    public static Map<String, Integer> calculateGlobalOrder(List<Map<String, Integer>> orders) {
        return orders
                .stream()
                .flatMap(someOrder -> someOrder
                        .entrySet()
                        .stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                                               Collectors.summingInt(Map.Entry::getValue)));
    }
}
