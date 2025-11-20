import java.util.*;
import java.util.Arrays;

public class BasicDataOperationUsingSet {
    private short shortValueToSearch;
    private Short[] shortArray;
    private Set<Short> shortSet;

    BasicDataOperationUsingSet(short shortValueToSearch, Short[] shortArray) {
        this.shortValueToSearch = shortValueToSearch;
        this.shortArray = shortArray;
        this.shortSet = new LinkedHashSet<>(Arrays.asList(shortArray));
    }

    public void executeDataAnalysis() {
        findInSet();
        locateMinMaxInSet();
        analyzeArrayAndSet();

        findInArray();
        locateMinMaxInArray();
        performArraySorting();
        findInArray();
        locateMinMaxInArray();

        DataFileHandler.writeArrayToFile(shortArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
    }

    // 2.4.1 – сортування масиву
    private void performArraySorting() {
        long start = System.nanoTime();
        shortArray = Arrays.stream(shortArray)
                .sorted()
                .toArray(Short[]::new);
        PerformanceTracker.displayOperationTime(start, "упорядкування масиву short");
    }

    // 2.4.2 – пошук у масиві
    private void findInArray() {
        long start = System.nanoTime();
        int pos = Arrays.stream(shortArray)
                .map(Arrays.asList(shortArray)::indexOf)
                .filter(i -> shortValueToSearch == shortArray[i])
                .findFirst()
                .orElse(-1);

        PerformanceTracker.displayOperationTime(start, "пошук елемента в масиві short");
        System.out.println(pos >= 0
                ? "Елемент знайдено у масиві на позиції: " + pos
                : "Елемент відсутній у масиві.");
    }

    // 2.4.3 – min/max у масиві
    private void locateMinMaxInArray() {
        if (shortArray.length == 0) return;
        long start = System.nanoTime();

        Short min = Arrays.stream(shortArray)
                .min(Short::compareTo)
                .orElse(null);
        Short max = Arrays.stream(shortArray)
                .max(Short::compareTo)
                .orElse(null);

        PerformanceTracker.displayOperationTime(start, "визначення min і max у масиві short");
        System.out.println("Min: " + min + " | Max: " + max);
    }

    // 2.5.2 – пошук у Set через Stream API
    private void findInSet() {
        long start = System.nanoTime();
        boolean elementExists = shortSet.stream()
                .anyMatch(value -> value == shortValueToSearch);
        PerformanceTracker.displayOperationTime(start, "пошук елемента в LinkedHashSet short");
        System.out.println(elementExists ? "Елемент знайдено у Set." : "Елемент відсутній у Set.");
    }

    // 2.5.2 – min/max у Set через Stream API
    private void locateMinMaxInSet() {
        if (shortSet.isEmpty()) return;
        long start = System.nanoTime();

        Short minValue = shortSet.stream()
                .min(Short::compareTo)
                .orElse(null);
        Short maxValue = shortSet.stream()
                .max(Short::compareTo)
                .orElse(null);

        PerformanceTracker.displayOperationTime(start, "визначення min і max у LinkedHashSet short");
        System.out.println("Min: " + minValue + " | Max: " + maxValue);
    }

    // 2.5.2 – аналіз масиву і множини через Stream API
    private void analyzeArrayAndSet() {
        System.out.println("Елементів у масиві: " + shortArray.length);
        System.out.println("Елементів у Set: " + shortSet.size());

        boolean allElementsPresent = Arrays.stream(shortArray)
                .allMatch(shortSet::contains);

        System.out.println("Усі елементи масиву присутні в Set: " + allElementsPresent);
    }
}
