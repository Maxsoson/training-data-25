import java.util.*;
import java.util.Arrays;

public class BasicDataOperationUsingQueue {
    private short shortValueToSearch;
    private Short[] shortArray;
    private Queue<Short> shortQueue;

    BasicDataOperationUsingQueue(short shortValueToSearch, Short[] shortArray) {
        this.shortValueToSearch = shortValueToSearch;
        this.shortArray = shortArray;
        this.shortQueue = new PriorityQueue<>();
        Collections.addAll(shortQueue, shortArray);
    }

    public void runDataProcessing() {
        findInQueue();
        locateMinMaxInQueue();
        performQueueOperations();

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
                ? "Елемент знайдено у масиві: " + pos
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

    // 2.5.3 – пошук у Queue
    private void findInQueue() {
        long start = System.nanoTime();
        boolean elementExists = shortQueue.stream()
                .anyMatch(value -> value == shortValueToSearch);
        PerformanceTracker.displayOperationTime(start, "пошук елемента в Queue short");
        System.out.println(elementExists ? "Елемент знайдено у Queue" : "Елемент відсутній у Queue");
    }

    // 2.5.3 – min/max у Queue через Stream API
    private void locateMinMaxInQueue() {
        if (shortQueue.isEmpty()) return;
        long start = System.nanoTime();

        Short minValue = shortQueue.stream()
                .min(Short::compareTo)
                .orElse(null);
        Short maxValue = shortQueue.stream()
                .max(Short::compareTo)
                .orElse(null);

        PerformanceTracker.displayOperationTime(start, "визначення min і max у Queue short");
        System.out.println("Min: " + minValue + " | Max: " + maxValue);
    }

    private void performQueueOperations() {
        if (shortQueue.isEmpty()) return;
        short peek = shortQueue.peek();
        System.out.println("Перший елемент (peek): " + peek);
        shortQueue.poll();
        System.out.println("Після poll новий перший: " + shortQueue.peek());
    }
}
