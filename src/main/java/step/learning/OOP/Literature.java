package step.learning.OOP;
import java.util.List;
import java.util.stream.Collectors;
public abstract class Literature {
    @Required
    private String title ;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public abstract String getCard() ;

    public static List<Literature> getPrintable(List<Literature> literatures) {
        return literatures.stream()
                .filter(literature -> literature instanceof Printable)
                .collect(Collectors.toList());
    }

    public static List<Literature> getNonPrintable(List<Literature> literatures) {
        return literatures.stream()
                .filter(literature -> !(literature instanceof Printable))
                .collect(Collectors.toList());
    }

    public static List<Literature> getMultiple(List<Literature> literatures) {
        return literatures.stream()
                .filter(literature -> literature instanceof Multiple)
                .collect(Collectors.toList());
    }

    public static List<Literature> getSingle(List<Literature> literatures) {
        return literatures.stream()
                .filter(literature -> !(literature instanceof Multiple))
                .collect(Collectors.toList());
    }
}