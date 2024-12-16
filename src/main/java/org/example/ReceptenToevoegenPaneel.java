package org.example;

import javax.swing.*;
import java.awt.*;

public class ReceptenToevoegenPaneel {
    public JPanel createReceptToevoegenPanel() {
        JPanel veldPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        veldPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Alle velden van recept toevoegen
        JTextField titelField = new JTextField();
        JTextField korteBeschrijvingField = new JTextField();
        JTextField keukenField = new JTextField();
        JTextField bereidingstijdField = new JTextField();
        JComboBox<String> moeilijkheidBox = new JComboBox<>(new String[]{ "-- Maak keuze --", "Makkelijk", "Gemiddeld", "Moeilijk" });
        JComboBox<String> allergieList = new JComboBox<>(new String[]{"niks", "notenvrij", "lactosevrij", "suikervrij", "vegatarisch", "veganistisch", "glutenvrij"});

        JTextArea bereidingswijzeArea = getTextArea(5);
        JTextArea ingredientenArea = getTextArea(10);

        veldPanel.add(new JLabel("Recept Titel:"));
        veldPanel.add(titelField);

        veldPanel.add(new JLabel("Korte Beschrijving:"));
        veldPanel.add(korteBeschrijvingField);

        veldPanel.add(new JLabel("Keuken:"));
        veldPanel.add(keukenField);

        veldPanel.add(new JLabel("Bereidingstijd (min):"));
        veldPanel.add(bereidingstijdField);

        veldPanel.add(new JLabel("Moeilijkheid:"));
        veldPanel.add(moeilijkheidBox);

        veldPanel.add(new JLabel("Bereidingswijze:"));
        veldPanel.add(new JScrollPane(bereidingswijzeArea));

        veldPanel.add(new JLabel("Ingrediënten:"));
        veldPanel.add(new JScrollPane(ingredientenArea));

        veldPanel.add(new JLabel("Allergieën:"));
        veldPanel.add(new JScrollPane(allergieList));

        JButton opslaanKnop = new JButton("Opslaan");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(opslaanKnop, BorderLayout.SOUTH);
        panel.add(veldPanel, BorderLayout.CENTER);

        // Opslaan knop
        opslaanKnop.addActionListener(e -> {
            try {
                // Verzamelen van gegevens
                String titel = titelField.getText();
                String korteBeschrijving = korteBeschrijvingField.getText();
                String keuken = keukenField.getText();
                String bereidingstijd = bereidingstijdField.getText();
                String moeilijkheid = (String) moeilijkheidBox.getSelectedItem();
                String bereidingswijze = bereidingswijzeArea.getText();
                String ingredienten = ingredientenArea.getText();
                String allergieOptie = (String) allergieList.getSelectedItem();

                // Alle velden moeten worden ingevuld anders gooien we een fout
                if (titel.isEmpty()
                        || korteBeschrijving.isEmpty()
                        || keuken.isEmpty()
                        || bereidingstijd.isEmpty()
                        || bereidingswijze.isEmpty()
                        || ingredienten.isEmpty()
                        || "-- Maak keuze --".equals(moeilijkheid)
                ) {
                    throw new Exception("Alle verplichte velden moeten worden ingevuld!");
                }

                // Hier slaan we alles op in de database.
                DatabaseConnection.OpslaanVanRecepten(titel, korteBeschrijving, keuken, bereidingstijd, moeilijkheid, bereidingswijze, ingredienten, allergieOptie);

                JOptionPane.showMessageDialog(panel, "Recept succesvol opgeslagen!", "Succes", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(panel, "Fout bij opslaan van het recept.", "Fout", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private static JTextArea getTextArea(int rows) {
        JTextArea bereidingswijzeArea = new JTextArea(rows, 20);
        bereidingswijzeArea.setLineWrap(true);
        bereidingswijzeArea.setWrapStyleWord(true);

        return bereidingswijzeArea;
    }
}
