package Template;

import javax.swing.*;
import java.awt.*;

public class CardLayoutDemo extends JFrame {

    public CardLayoutDemo() {
        // Instellingen
        setTitle("CardLayout Voorbeeld");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // CardLayout instellen
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        // Eerste scherm
        JPanel scherm1 = new JPanel();
        scherm1.setBackground(Color.CYAN);
        JButton naarScherm2Button = new JButton("Ga naar Scherm 2");
        scherm1.add(naarScherm2Button);

        // Tweede scherm
        JPanel scherm2 = new JPanel();
        scherm2.setBackground(Color.ORANGE);
        JButton naarScherm1Button = new JButton("Ga terug naar Scherm 1");
        scherm2.add(naarScherm1Button);

        // Voeg schermen toe aan het CardPanel
        cardPanel.add(scherm1, "Scherm1");
        cardPanel.add(scherm2, "Scherm2");

        // Actieknoppen
        naarScherm2Button.addActionListener(e -> cardLayout.show(cardPanel, "Scherm2"));
        naarScherm1Button.addActionListener(e -> cardLayout.show(cardPanel, "Scherm1"));

        // Layout toevoegen
        add(cardPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CardLayoutDemo::new);
    }
}
