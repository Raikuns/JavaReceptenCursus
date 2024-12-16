import javax.swing.*;

public class DialogVoorbeeld extends JFrame {

    public DialogVoorbeeld() {
        // Hoofdscherm instellen
        setTitle("Hoofdscherm");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(null);

        // Knop om een dialoog te openen
        JButton openDialogButton = new JButton("Open Dialoog");
        openDialogButton.setBounds(100, 100, 200, 30);
        openDialogButton.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Dialoog", true); // Modaal
            dialog.setSize(300, 200);
            dialog.setLayout(null);

            JButton sluitDialogButton = new JButton("Sluit");
            sluitDialogButton.setBounds(100, 100, 100, 30);
            sluitDialogButton.addActionListener(ev -> dialog.dispose());

            dialog.add(sluitDialogButton);
            dialog.setVisible(true);
        });

        add(openDialogButton);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DialogVoorbeeld::new);
    }
}
