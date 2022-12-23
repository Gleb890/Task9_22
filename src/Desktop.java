import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Desktop {
    private static JFrame frame;
    private JPanel panelMain;
    private JTable list1;
    private JButton addColumnButton1;
    private JButton loadFromFileButton;
    private JButton loadToFileButton;
    private JButton deleteColumnButton1;
    private JTable resultList;
    private JButton resultButton;
    private JButton writeResultToFile;
    private JTable list2;
    private JButton addColumnButton2;
    private JButton deleteColumnButton2;

    public Desktop() {
        DefaultTableModel model = (DefaultTableModel) list1.getModel();
        model.setRowCount(1);
        model.setColumnCount(1);
        DefaultTableModel model1 = (DefaultTableModel) list2.getModel();
        model1.setRowCount(1);
        model1.setColumnCount(1);
        list1.setSize(250, 16);
        list2.setSize(250, 16);

        addColumnButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) list1.getModel();
                model.addColumn("");
            }
        });
        deleteColumnButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list1.getColumnCount() == 0) return;
                DefaultTableModel model = (DefaultTableModel) list1.getModel();
                model.setColumnCount(list1.getColumnCount() - 1);
            }
        });
        addColumnButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) list2.getModel();
                model.addColumn("");
            }
        });
        deleteColumnButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list2.getColumnCount() == 0) return;
                DefaultTableModel model = (DefaultTableModel) list2.getModel();
                model.setColumnCount(list2.getColumnCount() - 1);
            }
        });
        loadFromFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filename = getFileName();
                if (filename.equals(""))
                    return;
                List<List<Integer>> lists = null;
                try {
                    lists = Utils.getListsFromFile(filename);
                    writeArrayToTable(list1, lists.get(0));
                    writeArrayToTable(list2, lists.get(1));
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        resultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> l1 = getListFromField(list1);
                if (l1.size() == 0)
                    return;
                List<Integer> l2 = getListFromField(list2);
                if (l2.size() == 0)
                    return;
                List<Integer> resList = Utils.createNewList(l1, l2);
                writeArrayToTable(resultList, resList);
            }
        });

        writeResultToFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filename = getFileName();
                if (filename.equals("")) return;
                List<Integer> list = getListFromField(resultList);
                if (list.size() == 0) return;
                try {
                    boolean res = Utils.writeListToFile(filename, list);
                    if (res) JOptionPane.showMessageDialog(frame,
                            "Данные успешно записаны в файл.");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    private String getFileName() {
        JFileChooser fileChooser = new JFileChooser();
        int response = fileChooser.showOpenDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsoluteFile().getAbsolutePath();
        }
        return "";
    }

    private void writeArrayToTable(JTable table, List<Integer> list) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(1);
        model.setColumnCount(list.size());
        for (int i = 0; i < list.size(); i++) {
            model.setValueAt(Integer.toString(Utils.getElementByIndex(list, i)), 0, i);
        }
    }

    private List<Integer> getListFromField(JTable table) {
        List<Integer> list = new ArrayList<Integer>();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = 0; i < model.getColumnCount(); i++) {
            var value = model.getValueAt(0, i);
            int res = value == "" || value == null ? 0 : Integer.parseInt((String) value);
            list.add(res);
        }
        return list;
    }

    public static void main(String[] args) {
        frame = new JFrame("Desktop");
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setContentPane(new Desktop().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
