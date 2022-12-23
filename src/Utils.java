import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static Integer getElementByIndex(List<Integer> list, int id) {
        int i = 0;
        for (Integer value : list) {
            if (i == id) return value;
            i++;
        }
        return 0;
    }

    public static List<List<Integer>> getListsFromFile(String filename) throws FileNotFoundException {
        List<List<Integer>> lists = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            while (line != null) {
                List<Integer> list = new ArrayList<>();
                String[] nums = line.split(" ");
                for (String n : nums) {
                    list.add(Integer.parseInt(n));
                }
                lists.add(list);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lists;
    }

    public static boolean writeListToFile(String fileName, List<Integer> list) throws IOException {
        if (list.size() == 0) return false;
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (Integer el : list) writer.write(el + " ");
        writer.close();
        return true;
    }

    public static List<Integer> createNewList( // реализация нашей 1 функции
            List<Integer> list1, List<Integer> list2) {
        List<Integer> resList = new ArrayList<>();
        for (int el : list1) {
            if (indexOf(resList, el) != -1 || el == 0)
                continue;
            if (hasDivider(list2, el))
                resList.add(el);
        }
        return resList;
    }

    public static boolean hasDivider(List<Integer> list, int el) {
        for (int x : list) {
            if (Math.abs(x) == 1 || x == 0 || Math.abs(x) == el)
                continue;
            if (el % x == 0)
                return true;
        }
        return false;
    }

    public static int indexOf(List<Integer> list, int num) { // реализация нашей 2 функции
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == num) return i;
        }
        return -1;
    }

    public static InputArgs parseCmdArgs(String[] args) {
        InputArgs Args = new InputArgs();
        try {
            String inputFile = args[0];
            String outputFile = args[1];
            if (!checkIfFileExists(inputFile)) {
                throw new Exception();
            }
            assert !checkIfFileExists(inputFile);
            Args.setInputFile(inputFile);
            Args.setOutputFile(outputFile);
        } catch (Exception ex) {
            Args.setAreFilesCorrect(false);
        }
        return Args;
    }

    public static boolean checkIfFileExists(String fileName) {
        File f = new File(fileName);
        return f.exists();
    }
}
