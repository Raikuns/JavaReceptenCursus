package Template;

import org.example.Kleuren;

import javax.swing.*;
import java.awt.*;

public class ReceptScherm extends JFrame {

    public ReceptScherm() {
        // Titel van het venster
        setTitle("Recept Scherm");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLayout(new BorderLayout());

        // Kleuren en radius voor de borders
        Color borderColor = new Color(100, 150, 200);
        int borderRadius = 15;

        // Panel voor de titel van het recept
        JPanel titelPanel = new JPanel();
        titelPanel.setLayout(new BorderLayout());
        titelPanel.setBorder(new RoundedBorder(borderRadius, borderColor));
        titelPanel.setBackground(Kleuren.gray);
        titelPanel.setForeground(Color.WHITE);
        JLabel titelLabel = new JLabel("Titel van het Recept:");
        JTextField titelField = new JTextField();
        titelLabel.setForeground(Color.white);
        titelPanel.add(titelLabel, BorderLayout.NORTH);
        titelPanel.add(titelField, BorderLayout.CENTER);

        // Panel voor de korte beschrijving
        JPanel beschrijvingPanel = new JPanel();
        beschrijvingPanel.setLayout(new BorderLayout());
        beschrijvingPanel.setBorder(new RoundedBorder(borderRadius, borderColor));
        JLabel beschrijvingLabel = new JLabel("Korte Beschrijving:");
        JTextArea beschrijvingArea = new JTextArea(3, 30);
        beschrijvingArea.setLineWrap(true);
        beschrijvingArea.setWrapStyleWord(true);
        JScrollPane beschrijvingScroll = new JScrollPane(beschrijvingArea);
        beschrijvingPanel.add(beschrijvingLabel, BorderLayout.NORTH);
        beschrijvingPanel.add(beschrijvingScroll, BorderLayout.CENTER);

        // Panel voor de ingrediëntenlijst
        JPanel ingredientenPanel = new JPanel();
        ingredientenPanel.setLayout(new BorderLayout());
        ingredientenPanel.setBorder(new RoundedBorder(borderRadius, borderColor));
        JLabel ingredientenLabel = new JLabel("Ingrediëntenlijst:");
        JTextArea ingredientenArea = new JTextArea(5, 30);
        ingredientenArea.setLineWrap(true);
        ingredientenArea.setWrapStyleWord(true);
        JScrollPane ingredientenScroll = new JScrollPane(ingredientenArea);
        ingredientenPanel.add(ingredientenLabel, BorderLayout.NORTH);
        ingredientenPanel.add(ingredientenScroll, BorderLayout.CENTER);

        // Panel voor de bereidingswijze
        JPanel bereidingswijzePanel = new JPanel();
        bereidingswijzePanel.setLayout(new BorderLayout());
        bereidingswijzePanel.setBorder(new RoundedBorder(borderRadius, borderColor));
        JLabel bereidingswijzeLabel = new JLabel("Bereidingswijze:");
        JTextArea bereidingswijzeArea = new JTextArea(7, 30);
        bereidingswijzeArea.setLineWrap(true);
        bereidingswijzeArea.setWrapStyleWord(true);
        bereidingswijzeArea.setLayout((LayoutManager) new RoundedBorder(borderRadius,Color.BLUE));
        JScrollPane bereidingswijzeScroll = new JScrollPane(bereidingswijzeArea);
        bereidingswijzePanel.add(bereidingswijzeLabel, BorderLayout.NORTH);
        bereidingswijzePanel.add(bereidingswijzeScroll, BorderLayout.CENTER);

        // Panel voor knoppen
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new RoundedBorder(borderRadius, borderColor));
        JButton opslaanButton = new JButton("Opslaan");
        JButton annulerenButton = new JButton("Annuleren");
        opslaanButton.setBackground(new Color(249, 178, 48));
        opslaanButton.setForeground(Color.white);
        opslaanButton.setBorder(new RoundedBorder(borderRadius, borderColor));
        opslaanButton.setBorderPainted(false);
        opslaanButton.setFocusPainted(false);
        annulerenButton.setBackground(new Color(247, 84, 95));
        annulerenButton.setForeground(Color.white);
        annulerenButton.setBorder(new RoundedBorder(borderRadius, borderColor));
        buttonPanel.add(opslaanButton);
        buttonPanel.add(annulerenButton);

        // Toevoegen van alle componenten aan het frame
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(titelPanel);
        mainPanel.add(beschrijvingPanel);
        mainPanel.add(ingredientenPanel);
        mainPanel.add(bereidingswijzePanel);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        // Start de GUI
        SwingUtilities.invokeLater(ReceptScherm::new);
    }
}
