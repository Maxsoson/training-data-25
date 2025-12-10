import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class BasicDataOperationUsingMap {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // елементи для пошуку та додавання
    private final Horse KEY_TO_SEARCH_AND_DELETE =
            new Horse("Вітер", LocalDate.parse("27/12/2022", FMT));

    private final Horse KEY_TO_ADD =
            new Horse("Гнідий", LocalDate.parse("15/06/2024", FMT));

    private final String VALUE_TO_SEARCH_AND_DELETE = "Орина";
    private final String VALUE_TO_ADD = "Степан";

    private LinkedHashMap<Horse, String> linkedHashMap;
    private TreeMap<Horse, String> treeMap;

    public BasicDataOperationUsingMap(LinkedHashMap<Horse, String> linkedHashMap,
                                      TreeMap<Horse, String> treeMap) {
        this.linkedHashMap = linkedHashMap;
        this.treeMap = treeMap;
    }

    public void executeDataOperations() {

        System.out.println("========= Операції з LinkedHashMap =========");

        findByKey(linkedHashMap);
        findByValue(linkedHashMap);

        printMap(linkedHashMap, "LinkedHashMap до сортування");
        sortLinkedHashMap();
        printMap(linkedHashMap, "LinkedHashMap після сортування");

        addEntry(linkedHashMap);
        removeByKey(linkedHashMap);
        removeByValue(linkedHashMap);

        System.out.println("\n========= Операції з TreeMap =========");

        findByKey(treeMap);
        findByValue(treeMap);

        printMap(treeMap, "TreeMap (вже відсортована)");

        addEntry(treeMap);
        removeByKey(treeMap);
        removeByValue(treeMap);

        comparePerformance();
    }

    private void printMap(Map<Horse, String> map, String title) {
        System.out.println("\n=== " + title + " ===");
        map.forEach((k, v) -> System.out.println(k + " -> " + v));
    }

    private void sortLinkedHashMap() {
        linkedHashMap = linkedHashMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(new HorseComparator()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldV, newV) -> oldV,
                        LinkedHashMap::new
                ));
    }

    private void findByKey(Map<Horse, String> map) {
        long start = System.nanoTime();
        boolean found = map.containsKey(KEY_TO_SEARCH_AND_DELETE);
        long end = System.nanoTime();

        System.out.println("Пошук за ключем: " + (end - start) + " нс");
        System.out.println(found ? "Знайдено" : "Не знайдено");
    }

    private void findByValue(Map<Horse, String> map) {
        long start = System.nanoTime();
        boolean found = map.containsValue(VALUE_TO_SEARCH_AND_DELETE);
        long end = System.nanoTime();

        System.out.println("Пошук за значенням: " + (end - start) + " нс");
        System.out.println(found ? "Знайдено" : "Не знайдено");
    }

    private void addEntry(Map<Horse, String> map) {
        long start = System.nanoTime();
        map.put(KEY_TO_ADD, VALUE_TO_ADD);
        long end = System.nanoTime();

        System.out.println(map.getClass().getSimpleName() + " - Додавання пари (put): " + (end - start) + " нс");
    }

    private void removeByKey(Map<Horse, String> map) {
        long start = System.nanoTime();
        Object removed = map.remove(KEY_TO_SEARCH_AND_DELETE);
        long end = System.nanoTime();

        System.out.println(map.getClass().getSimpleName() + " - Видалення за ключем (remove): " + (end - start) + " нс");
        System.out.println(removed != null ? "Видалено за ключем" : "Ключ не знайдено");
    }

    private void removeByValue(Map<Horse, String> map) {
        int before = map.size();
        long start = System.nanoTime();
        boolean anyRemoved = map.values().removeIf(v -> v.equals(VALUE_TO_SEARCH_AND_DELETE));
        long end = System.nanoTime();
        int after = map.size();

        System.out.println(map.getClass().getSimpleName() + " - Видалення за значенням (removeIf): " + (end - start) + " нс");
        System.out.println("Кількість видалених елементів: " + (before - after) + (anyRemoved ? "" : " (0)"));
    }

    private void comparePerformance() {

        System.out.println("\n=== Порівняння продуктивності LinkedHashMap і TreeMap ===");

        long start1 = System.nanoTime();
        linkedHashMap.containsKey(KEY_TO_ADD);
        long end1 = System.nanoTime();

        long start2 = System.nanoTime();
        treeMap.containsKey(KEY_TO_ADD);
        long end2 = System.nanoTime();

        System.out.println("LinkedHashMap.containsKey(): " + (end1 - start1) + " нс");
        System.out.println("TreeMap.containsKey(): " + (end2 - start2) + " нс");

        System.out.println("\nLinkedHashMap швидший у пошуку ключа (O(1))");
        System.out.println("TreeMap повільніший, але забезпечує автоматичне сортування (O(log n))");
    }

    public static void main(String[] args) {

        LinkedHashMap<Horse, String> linked = new LinkedHashMap<>();

        linked.put(new Horse("Буян", LocalDate.parse("03/12/2020", FMT)), "Єва");
        linked.put(new Horse("Вітер", LocalDate.parse("27/12/2022", FMT)), "Зінаїда");
        linked.put(new Horse("Зірка", LocalDate.parse("10/01/2019", FMT)), "Матвій");
        linked.put(new Horse("Стріла", LocalDate.parse("23/01/2021", FMT)), "Орина");
        linked.put(new Horse("Гром", LocalDate.parse("04/05/2025", FMT)), "Андрій");
        linked.put(new Horse("Луна", LocalDate.parse("13/07/2017", FMT)), "Андрій");
        linked.put(new Horse("Барсик", LocalDate.parse("17/11/2021", FMT)), "Ярослав");
        linked.put(new Horse("Араб", LocalDate.parse("09/09/2019", FMT)), "Орина");
        linked.put(new Horse("Луна", LocalDate.parse("31/08/2021", FMT)), "Стефанія");
        linked.put(new Horse("Барсик", LocalDate.parse("07/03/2023", FMT)), "Тимофій");

        TreeMap<Horse, String> tree = new TreeMap<>(new HorseComparator());
        tree.putAll(linked);

        new BasicDataOperationUsingMap(linked, tree).executeDataOperations();
    }
}
