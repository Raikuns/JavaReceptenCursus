import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MeerdereSchermen extends JFrame {

    public MeerdereSchermen() {
        // Hoofdscherm instellen
        setTitle("Hoofdscherm");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(null);

        // Knop om naar een nieuw scherm te gaan
        JButton openSchermButton = new JButton("Open Nieuw Scherm");
        openSchermButton.setBounds(100, 100, 200, 30);
        openSchermButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Nieuw scherm tonen
                NieuwScherm nieuwScherm = new NieuwScherm();
                nieuwScherm.setVisible(true);
            }
        });

        // Toevoegen aan hoofdscherm
        add(openSchermButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MeerdereSchermen::new);
    }
}

class NieuwScherm extends JFrame {
    public NieuwScherm() {
        // Nieuw scherm instellen
        setTitle("Nieuw Scherm");
        setSize(300, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Alleen dit scherm sluiten

        // Knop om terug te gaan of te sluiten
        JButton sluitButton = new JButton("Sluit");
        sluitButton.setBounds(100, 80, 100, 30);
        sluitButton.addActionListener(e -> dispose()); // Sluit dit scherm
        add(sluitButton);
    }
}
