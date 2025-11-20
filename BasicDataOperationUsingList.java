import java.util.*;
import java.util.stream.Collectors; // Додай цей імпорт для використання Collectors

public class BasicDataOperationUsingList {
    private short shortValueToSearch;
    private Short[] shortArray;
    private List<Short> shortList;

    BasicDataOperationUsingList(short shortValueToSearch, Short[] shortArray) {
        this.shortValueToSearch = shortValueToSearch;
        this.shortArray = shortArray;
        this.shortList = new ArrayList<>(Arrays.asList(shortArray));
    }

    public void executeDataOperations() {
        findInList();
        locateMinMaxInList();

        sortList();
        findInList();
        locateMinMaxInList();

        findInArray();
        locateMinMaxInArray();

        performArraySorting();
        findInArray();
        locateMinMaxInArray();

        DataFileHandler.writeArrayToFile(shortArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
    }

    // 2.4.1 – сортування масиву через Stream API
    void performArraySorting() {
        long start = System.nanoTime();
        shortArray = Arrays.stream(shortArray)
                .sorted()
                .toArray(Short[]::new);
        PerformanceTracker.displayOperationTime(start, "упорядкування масиву short");
    }

    // 2.4.2 – пошук у масиві через Stream API
    void findInArray() {
        long start = System.nanoTime();
        int pos = Arrays.stream(shortArray)
                .map(Arrays.asList(shortArray)::indexOf)
                .filter(i -> shortValueToSearch == shortArray[i])
                .findFirst()
                .orElse(-1);

        PerformanceTracker.displayOperationTime(start, "пошук елемента в масиві short");
        if (pos >= 0)
            System.out.println("Елемент '" + shortValueToSearch + "' знайдено на позиції: " + pos);
        else
            System.out.println("Елемент '" + shortValueToSearch + "' відсутній у масиві.");
    }

    // 2.4.3 – min/max у масиві через Stream API
    void locateMinMaxInArray() {
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

    // 2.5.1 – пошук у списку через Stream API
    void findInList() {
        long start = System.nanoTime();
        int pos = shortList.stream()
                .map(shortList::indexOf)
                .filter(i -> shortValueToSearch == shortList.get(i))
                .findFirst()
                .orElse(-1);

        PerformanceTracker.displayOperationTime(start, "пошук елемента в ArrayList short");
        if (pos >= 0)
            System.out.println("Елемент '" + shortValueToSearch + "' знайдено у списку на позиції: " + pos);
        else
            System.out.println("Елемент '" + shortValueToSearch + "' відсутній у списку.");
    }

    void locateMinMaxInList() {
        if (shortList.isEmpty()) return;
        long start = System.nanoTime();
        short min = Collections.min(shortList);
        short max = Collections.max(shortList);
        PerformanceTracker.displayOperationTime(start, "визначення min і max у ArrayList short");
        System.out.println("Min: " + min + " | Max: " + max);
    }

    // 2.5.1 – сортування списку через Stream API
    void sortList() {
        long start = System.nanoTime();
        shortList = shortList.stream()
              .sorted()
             .collect(Collectors.toList());  // замість toList() використовуємо collect(Collectors.toList())
        PerformanceTracker.displayOperationTime(start, "упорядкування ArrayList short");
    }

}
