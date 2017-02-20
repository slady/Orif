//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cz.slady.oriflame;

import cz.slady.oriflame.DataSource;
import cz.slady.oriflame.Item;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class Oriflame implements ActionListener {
    DataSource dataSource = new DataSource();
    JTextArea textArea;
    JTextField textField;
    JButton button;

    public Oriflame() throws InvalidFormatException, IOException {
        JFrame frame = new JFrame("Oriflame");
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        this.textArea = new JTextArea(15, 100);
        JScrollPane scrollPane = new JScrollPane(this.textArea, 20, 30);
        contentPane.add(scrollPane, "Center");
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Oriflame kód:"), "West");
        this.textField = new JTextField(10);
        panel.add(this.textField, "Center");
        this.button = new JButton("Hledej");
        panel.add(this.button, "East");
        contentPane.add(panel, "North");
        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(3);
        this.button.addActionListener(this);
        this.textField.addActionListener(this);
        frame.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                Oriflame.this.textField.requestFocus();
            }
        });
    }

    public void actionPerformed(ActionEvent actionEvent) {
        String code = this.textField.getText();
        StringBuilder text = new StringBuilder();
        Iterator var5 = this.dataSource.getItemsById(code).iterator();

        while(var5.hasNext()) {
            Item item = (Item)var5.next();
            text.append(item.toString());
            text.append('\n');
        }

        this.textArea.setText(text.toString());
    }

    public static void main(String[] args) throws InvalidFormatException, IOException {
        new Oriflame();
    }
}
