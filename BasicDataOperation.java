import java.util.*;

public class BasicDataOperation {
    static final String PATH_TO_DATA_FILE = "list/short.data";

    short shortValueToSearch;
    Short[] shortArray;

    private static final String SEPARATOR = "\n" + "=".repeat(80) + "\n";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Використання: java BasicDataOperation <short-значення>");
            return;
        }

        try {
            short searchValue = Short.parseShort(args[0]);
            BasicDataOperation program = new BasicDataOperation();
            program.runAll(searchValue);
        } catch (NumberFormatException e) {
            System.out.println("Помилка: введіть ціле число типу short.");
        }
    }

    private void runAll(short searchValue) {
        System.out.println(SEPARATOR + "🚀 РОЗПОЧАТО АНАЛІЗ ДАНИХ SHORT 🚀" + SEPARATOR);

        this.shortValueToSearch = searchValue;
        this.shortArray = DataFileHandler.loadArrayFromFile(PATH_TO_DATA_FILE);

        new BasicDataOperationUsingList(shortValueToSearch, shortArray).executeDataOperations();
        System.out.println("\n" + "~".repeat(60) + "\n");
        new BasicDataOperationUsingQueue(shortValueToSearch, shortArray).runDataProcessing();
        System.out.println("\n" + "~".repeat(60) + "\n");
        new BasicDataOperationUsingSet(shortValueToSearch, shortArray).executeDataAnalysis();

        System.out.println(SEPARATOR + "✅ АНАЛІЗ ЗАВЕРШЕНО ✅" + SEPARATOR);
    }
}
