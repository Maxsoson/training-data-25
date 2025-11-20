import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DataFileHandler {

    public static Short[] loadArrayFromFile(String filePath) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            return fileReader.lines()
                    .map(currentLine -> currentLine.trim().replaceAll("^\\uFEFF", "")) // прибрати BOM
                    .filter(currentLine -> !currentLine.isEmpty())
                    .map(Short::parseShort) // short-варіант: Short.parseShort(currentLine)
                    .toArray(Short[]::new);
        } catch (IOException ioException) {
            throw new RuntimeException("Помилка читання даних з файлу: " + filePath, ioException);
        }
    }

    public static void writeArrayToFile(Short[] array, String filePath) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath))) {
            String content = Arrays.stream(array)
                    .map(String::valueOf)               // short-варіант: String::valueOf
                    .collect(Collectors.joining(System.lineSeparator()));

            fileWriter.write(content);
        } catch (IOException ioException) {
            throw new RuntimeException("Помилка запису даних у файл: " + filePath, ioException);
        }
    }
}
