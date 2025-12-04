import java.util.Comparator;

public class HorseComparator implements Comparator<Horse> {

    private static final Comparator<Horse> CMP =
            Comparator.comparing(Horse::nickname, Comparator.reverseOrder())
                      .thenComparing(Horse::birthday, Comparator.reverseOrder());

    @Override
    public int compare(Horse h1, Horse h2) {
        return CMP.compare(h1, h2);
    }
}
