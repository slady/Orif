package cz.slady.oriflame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.*;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

public class Oriflame implements ActionListener {

    private DataSource dataSource = new DataSource();
    private JTextArea textArea;
    private JTextField textField;
    private JButton button;

    public Oriflame() throws InvalidFormatException, IOException {
        final JFrame frame = new JFrame("Oriflame");
        final Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        textArea = new JTextArea(15, 100);
        final JScrollPane scrollPane = new JScrollPane(textArea, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Oriflame kód:"), BorderLayout.WEST);

        textField = new JTextField(10);
        panel.add(textField, BorderLayout.CENTER);

        button = new JButton("Hledej");
        panel.add(button, BorderLayout.EAST);

        contentPane.add(panel, BorderLayout.NORTH);

        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button.addActionListener(this);
        textField.addActionListener(this);

        frame.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                Oriflame.this.textField.requestFocus();
            }
        });
    }

    public void actionPerformed(final ActionEvent actionEvent) {
        final String code = textField.getText();
        final StringBuilder text = new StringBuilder();

        for (final Item item : dataSource.getItemsById(code)) {
            text.append(item.toString());
            text.append('\n');
        }

        textArea.setText(text.toString());
    }

    public static void main(String[] args) throws InvalidFormatException, IOException {
        new Oriflame();
    }

}
