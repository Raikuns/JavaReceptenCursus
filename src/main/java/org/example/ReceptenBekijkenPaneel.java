package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.DatabaseConnection.ZoekRecepten;
import static org.example.DatabaseConnection.connectToDatabase;

public class ReceptenBekijkenPaneel {
    // Dropdown en zoekknop
    private JComboBox<String> dropbox = new JComboBox<>();

    public void updateDropdown() {
        // We updaten de lijst van de dropdown menu zodat hij alle recepten ophaalt die bestaan in de database
        try {
            List<String> recepten = ZoekRecepten();
            dropbox.removeAllItems();
            // We hebben hier een Arraylist van recepten en die voegen we toe aan de lijst van de dropdown menu
            for (int i = 0; i < recepten.size(); i++) {
                dropbox.addItem(recepten.get(i));
            }
        } catch (SQLException e) {
            System.out.println("er gaat iets fout: " + e.getMessage());
        }
    }

    public JPanel createReceptBekijkenPanel() {
        // Create text areas for recipe details
        JTextArea titelBeschrijvingpaneel = new JTextArea("Titel & korte beschrijving");
        titelBeschrijvingpaneel.setLineWrap(true);
        titelBeschrijvingpaneel.setWrapStyleWord(true);
        titelBeschrijvingpaneel.setEditable(false);

        JTextArea extraInformatiePaneel = new JTextArea("Bereidingstijd, Moeilijkheid, allergieOptie");
        extraInformatiePaneel.setLineWrap(true);
        extraInformatiePaneel.setWrapStyleWord(true);
        extraInformatiePaneel.setEditable(false);

        JTextArea ingredientenpaneel = new JTextArea("ingredienten");
        ingredientenpaneel.setLineWrap(true);
        ingredientenpaneel.setWrapStyleWord(true);
        ingredientenpaneel.setEditable(false);

        JTextArea bereidingswijzepaneel = new JTextArea("recept bereidingswijze");
        bereidingswijzepaneel.setLineWrap(true);
        bereidingswijzepaneel.setWrapStyleWord(true);
        bereidingswijzepaneel.setEditable(false);

        JPanel detailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        // Add receptIngredients (top-left)
        gbc.gridx = 0; // Column 0
        gbc.gridy = 0; // Row 0
        gbc.gridwidth = 2; // Spans two columns
        gbc.weighty = 0.5;
        detailsPanel.add(new JScrollPane(titelBeschrijvingpaneel), gbc);

        // Add receptDescription (top-right)
        gbc.gridx = 0; // Column 0
        gbc.gridy = 1; // Row 1
        gbc.gridwidth = 2; // Spans two columns
        gbc.weighty = 0.2;
        detailsPanel.add(new JScrollPane(extraInformatiePaneel), gbc);

        // Add extraDetails1 (bottom-left)
        gbc.gridx = 0; // Column 0
        gbc.gridy = 2; // Row 2
        gbc.gridwidth = 1; // Single column
        gbc.weighty = 2;
        detailsPanel.add(new JScrollPane(ingredientenpaneel), gbc);

        // Add extraDetails2 (bottom-right)
        gbc.gridx = 1; // Column 1
        gbc.gridy = 2; // Row 2
        gbc.gridwidth = 1; // Single column
        gbc.weighty = 2;
        detailsPanel.add(new JScrollPane(bereidingswijzepaneel), gbc);

        // Zoek actie
        JButton zoekKnop = new JButton("Recept ophalen");
        zoekKnop.addActionListener(e -> {
            int geselecteerdIndex = dropbox.getSelectedIndex();
            if (geselecteerdIndex >= 0) {
                try {
                    Recept receptG = haalAlleReceptGegevens();
                    updateTextVelden(receptG, titelBeschrijvingpaneel, extraInformatiePaneel, ingredientenpaneel, bereidingswijzepaneel);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                titelBeschrijvingpaneel.setText("Geen recept geselecteerd.");
            }
        });

        // Create the search panel
        JPanel zoekPanel = new JPanel(new FlowLayout());
        zoekPanel.add(dropbox);
        zoekPanel.add(zoekKnop);

        // Combine everything into the main panel
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.add(zoekPanel, BorderLayout.NORTH);
        panel.add(detailsPanel, BorderLayout.CENTER);

        return panel;
    }

    private void updateTextVelden(Recept receptGegevens, JTextArea titel, JTextArea moeilijk, JTextArea ingredientenLijst, JTextArea bereidingswijze) {

        String allergie = "";
        if (receptGegevens.getAllergieOptie() != null) {
            allergie = receptGegevens.getAllergieOptie();
        }

        titel.setText(receptGegevens.getReceptNaam() + "\n\n" + receptGegevens.getKorteBeschrijving());
        moeilijk.setText(receptGegevens.getBereidingstijd() + " Minuten  " + receptGegevens.getMoeilijkheid() + "  " + allergie);
        String ingredientenLijstjeOpbouw = "";
        for (Ingredient ingredient : receptGegevens.getIngredienten()) {
            ingredientenLijstjeOpbouw += ingredient.getAantal() + " " + ingredient.getGewichtseenheid() + " " + ingredient.getNaam() + "\n";
        }
        ingredientenLijst.setText(ingredientenLijstjeOpbouw);
        bereidingswijze.setText(receptGegevens.getBereidingswijze());

    }

    public Recept haalAlleReceptGegevens() throws SQLException {
        int currentReceptId = 0;
        currentReceptId = dropbox.getSelectedIndex();
        Connection connection = connectToDatabase();
        Recept recept = null;

        // Hier halen we de recept gegevens op
        String sqlQuery = """
                 SELECT recept_naam,
                       recept_beschrijving,
                       recept_bereidingtijd,
                       recept_moeilijkheid,
                       recept_keuken,
                       recept_bereidingswijze,
                       allergieoptie_naam,
                       ingredient_naam,
                       i.gewichtseenheid,
                       i.hoeveelheid
                FROM recepten t
                         Left JOIN allergieaanrecept a on t.recept_id = a.recept_id
                         Left JOIN allergienopties b on a.allergieoptie_id = b.allergieoptie_id
                         LEFT JOIN ingredientaanrecept i on t.recept_id = i.recept_id
                         LEFT JOIN ingredienten j on i.ingredient_id = j.ingredient_id
                WHERE t.recept_id = ?""";

        try (PreparedStatement haalTitelEnBeschrijvingOp = connection.prepareStatement(sqlQuery)) {
            haalTitelEnBeschrijvingOp.setInt(1, currentReceptId);
            ResultSet resultaat = haalTitelEnBeschrijvingOp.executeQuery();

            ArrayList<Ingredient> lijstIngredienten = new ArrayList<>();
            while (resultaat.next()) {
                if (recept == null) {
                    String naam = resultaat.getString("recept_naam");
                    String korteBeschrijving = resultaat.getString("recept_beschrijving");
                    String bereidingstijd = resultaat.getString("recept_bereidingtijd");
                    String moeilijkheid = resultaat.getString("recept_moeilijkheid");
                    String keuken = resultaat.getString("recept_keuken");
                    String bereidingswijze = resultaat.getString("recept_bereidingswijze");
                    String allergieoptie = resultaat.getString("allergieoptie_naam");
                    recept = new Recept(naam, korteBeschrijving, bereidingstijd, moeilijkheid, keuken, allergieoptie, bereidingswijze);
                }
                String ingredientNaam = resultaat.getString("ingredient_naam");
                String gewichteenheid = resultaat.getString("gewichtseenheid");
                double hoeveelheid = resultaat.getDouble("hoeveelheid");

                // Add each ingredient to the recipe
                Ingredient ingredient = new Ingredient(hoeveelheid, gewichteenheid, ingredientNaam);
                lijstIngredienten.add(ingredient);
            }
            recept.setIngredienten(lijstIngredienten.toArray(new Ingredient[0]));
            return recept;
        }
    }
}
