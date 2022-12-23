import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        InputArgs Args = Utils.parseCmdArgs(args);
        if (!Args.getAreFilesCorrect()) {
            System.err.println("Ошибка: введенные данные не корректны.");
            System.exit(0);
        }
        List<List<Integer>> lists = Utils.getListsFromFile(Args.getInputFile());
        List<Integer> newList = Utils.createNewList(lists.get(0), lists.get(1));
        Utils.writeListToFile(Args.getOutputFile(), newList);
    }
}