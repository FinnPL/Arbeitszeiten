import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class GUI implements ActionListener {
    JFormattedTextField ftf;
    String inputTime;
    MaskFormatter formatter;
    JFrame frame;

    public GUI() {
        frame = new JFrame("Arbeitszeiten");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
        frame.setResizable(false);
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(panel);
        JLabel label = new JLabel("Lade CSV Datei");
        panel.add(label);
        CSVEditor csv = new CSVEditor();
        try {
            csv.editRecord(this);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame,
                    "Error: " + e.getMessage(),
                    "Fatal Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }

    public void GUImaker(String info) throws ParseException {
        frame = new JFrame();
        JLabel label = new JLabel(info);
        JPanel panel = new JPanel();
        JButton button = new JButton("Enter");
        formatter = new MaskFormatter("##:##");
        formatter.setAllowsInvalid(false);
        formatter.setValidCharacters("0123456789:");
        formatter.setPlaceholderCharacter('0');

        ftf = new JFormattedTextField(formatter);

        button.addActionListener(this);

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new java.awt.GridLayout(0, 1));
        panel.add(label);
        panel.add(ftf);
        panel.add(button);

        frame.add(panel, java.awt.BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Arbeitszeiten");
        frame.setSize(500, 200);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        inputTime = ftf.getText();
        frame.setVisible(false);
        frame.dispose();
    }
}