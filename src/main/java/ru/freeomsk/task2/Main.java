package ru.freeomsk.task2;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Utils.printFormatted('*', 57);

        // 1. Реализовать удаление из листа всех дубликатов.
        List<Integer> list = Arrays.asList(1, 2, 2, 3, 4, 4, 5);

        List<Integer> distinctList = list.stream()
                .distinct().toList();

        System.out.println(distinctList);

        Utils.printFormatted('-', 57);

        // 2. Найти в списке целых чисел 3-е наибольшее число (пример: 5 2 10 9 4 3 10 1 13 => 10)
        List<Integer> numbers = Arrays.asList(5, 2, 10, 9, 4, 3, 10, 1, 13);

        Integer thirdLargest = numbers.stream()
                .sorted(Comparator.reverseOrder()) // Сортировка по убыванию
//                .sorted((a, b) -> b - a) // Альтернативный способ сортировки по убыванию
                .toList() // Сборка в список
                .get(2); // Получение третьего элемента (индексация с 0)

        System.out.println(thirdLargest);

        Utils.printFormatted('-', 57);

        // 3. Найти в списке целых чисел 3-е наибольшее «уникальное» число (пример: 5 2 10 9 4 3 10 1 13 => 9.
        // В отличие от прошлой задачи здесь разные 10 считаются за одно число).

        Integer thirdLargestUnique = numbers.stream()
                .distinct() // Удаление дубликатов
                .sorted(Comparator.reverseOrder()) // Сортировка по убыванию
                .toList() // Сборка в список
                .get(2); // Получение третьего элемента (индексация с 0)

        System.out.println(thirdLargestUnique);

        Utils.printFormatted('-', 57);

        // 4. Имеется список объектов типа Сотрудник (имя, возраст, должность).
        // Необходимо получить список имен 3-х самых старших сотрудников с должностью «Инженер» в порядке убывания возраста.
        List<Employee> employees = Arrays.asList(
                new Employee("Иван", 43, "Инженер"),
                new Employee("Петр", 54, "Инженер"),
                new Employee("Алексей", 21, "Менеджер"),
                new Employee("Александр", 33, "Менеджер"),
                new Employee("Сергей", 42, "Инженер"),
                new Employee("Владимир", 48, "Инженер"),
                new Employee("Николай", 38, "Менеджер"),
                new Employee("Екатерина", 27, "Менеджер"),
                new Employee("Мария", 30, "Инженер"),
                new Employee("Елена", 52, "Инженер")
        );

        List<String> top3OldestEngineersNames = employees.stream()
                .filter(e -> "Инженер".equals(e.position())) // Фильтрация по должности «Инженер»
                .sorted((e1, e2) -> Integer.compare(e2.age(), e1.age())) // Сортировка по убыванию возраста
                .limit(3) // Ограничение первыми тремя элементами
                .map(Employee::name) // Преобразование в список имен
                .toList(); // Сборка в список

        System.out.println(top3OldestEngineersNames);

        Utils.printFormatted('-', 57);

        // 5. Имеется список объектов типа Сотрудник (имя, возраст, должность).
        // Посчитаеь средний возраст сотрудников с должностью «Инженер».

        OptionalDouble averageAge = employees.stream()
                .filter(e -> "Инженер".equals(e.position())) // Фильтрация по должности «Инженер»
                .mapToInt(Employee::age) // Преобразование в поток возрастов
                .average(); // Расчет среднего значения

        if (averageAge.isPresent()) {
            System.out.println("Средний возраст сотрудников с должностью «Инженер»: " +
                    String.format("%.2f", averageAge.getAsDouble()));
        } else {
            System.out.println("Сотрудников с должностью «Инженер» не найдено.");
        }

        Utils.printFormatted('-', 57);

        // 6. Найти в списке слов самое длинное.

        List<String> words = Arrays.asList("Java", "Python", "C", "JavaScript", "Kotlin");

        Optional<String> longestWord = words.stream()
                .max(Comparator.comparingInt(String::length));

        longestWord.ifPresent(word -> System.out.println("Самое длинное слово: " + word));

        Utils.printFormatted('-', 57);

        // 7. Имеется строка с набором слов в нижнем регистре, разделенных пробелом.
        // Построить хеш-мапу, в которой будут хранится пары: слово - сколько раз оно встречается во входной строке.

        String input = "java python java c kotlin java javascript python kotlin java kotlin";

        // Разделение строки на слова
        String[] setWords = input.split(" ");

        // Создание хеш-мапы для хранения слов и их количества
        Map<String, Integer> wordCount = new HashMap<>();

        // Подсчет количества каждого слова
        for (String word : setWords) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }

        // Вывод результата
        wordCount.forEach((key, value) -> System.out.println(key + " - " + value));

        Utils.printFormatted('-', 57);

        // 8. Вывести в консоль строки из списка в порядке увеличения длины слова.
        // Если слова имеют одинаковую длину, то должен быть сохранен алфавитный порядок.

        List<String> listWords = Arrays.asList("Lada", "BMW", "Honda", "Mercedes-Benz", "Toyota", "Suzuki", "KIA", "Lexus");

        listWords.stream()
                .sorted(Comparator.comparingInt(String::length) // Сортировка по длине
                        .thenComparing(Comparator.naturalOrder())) // Затем по алфавиту
                .forEach(System.out::println); // Вывод результатов

        Utils.printFormatted('-', 57);

        // 9. Имеется массив строк, в каждой из которых лежит набор из 5 слов, разделенных пробелом.
        // Найти среди всех слов самое длинное. Если таких слов несколько, получить любое из них.

        String[] arrayOfStrings = {
                "яблоко банан вишня финик баклажан",
                "инжир смородина дыня киви лимон",
                "манго нектарин апельсин папайя айва",
                "малина клубника мандарин ежевика грейпфрут",
                "арбуз картофель слива огурец ирга"
        };

        Optional<String> anyLongestWord = Arrays.stream(arrayOfStrings)
                .flatMap(str -> Arrays.stream(str.split(" "))) // Преобразование в поток слов
                .max(Comparator.comparingInt(String::length)); // Поиск самого длинного слова

        anyLongestWord.ifPresent(word -> System.out.println("Самое длинное слово: " + word));

        Utils.printFormatted('*', 57);

    }
}