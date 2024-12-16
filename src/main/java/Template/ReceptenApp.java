package Template;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ReceptenApp extends JFrame {

    // Enum voor allergie-opties
    public enum AllergieOptie {
        GLUTENVRIJ, NOTENVRIJ, LACTOSEVRIJ, SUIKERVRIJ, VEGETARISCH, VEGANISTISCH
    }

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private ArrayList<String> recepten; // Simpele lijst om recepten op te slaan

    public ReceptenApp() {
        setTitle("Recepten App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        recepten = new ArrayList<>();

        // CardLayout voor schermen
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Voeg beide schermen toe
        cardPanel.add(createReceptToevoegenPanel(), "ReceptToevoegen");
        cardPanel.add(createReceptBekijkenPanel(), "ReceptBekijken");

        // Menu bovenaan
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton naarToevoegenKnop = new JButton("Recept Toevoegen");
        JButton naarBekijkenKnop = new JButton("Recepten Bekijken");

        // Acties voor knoppen
        naarToevoegenKnop.addActionListener(e -> cardLayout.show(cardPanel, "ReceptToevoegen"));
        naarBekijkenKnop.addActionListener(e -> cardLayout.show(cardPanel, "ReceptBekijken"));

        menuPanel.add(naarToevoegenKnop);
        menuPanel.add(naarBekijkenKnop);

        add(menuPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Scherm 1: Recept Toevoegen
    private JPanel createReceptToevoegenPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel veldPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        veldPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField titelField = new JTextField();
        JTextField korteBeschrijvingField = new JTextField();
        JTextField keukenField = new JTextField();
        JTextField bereidingstijdField = new JTextField();
        JComboBox<String> moeilijkheidBox = new JComboBox<>(new String[]{"Makkelijk", "Gemiddeld", "Moeilijk"});
        JTextArea bereidingswijzeArea = new JTextArea(5, 20);
        JTextArea ingredientenArea = new JTextArea(5, 20);
        JList<AllergieOptie> allergieList = new JList<>(AllergieOptie.values());

        bereidingswijzeArea.setLineWrap(true);
        bereidingswijzeArea.setWrapStyleWord(true);
        ingredientenArea.setLineWrap(true);
        ingredientenArea.setWrapStyleWord(true);
        allergieList.setVisibleRowCount(4);
        allergieList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

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

        veldPanel.add(new JLabel("Allergieën (meerdere selecties mogelijk):"));
        veldPanel.add(new JScrollPane(allergieList));

        panel.add(veldPanel, BorderLayout.CENTER);

        // Opslaan knop
        JButton opslaanKnop = new JButton("Opslaan");
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

                if (titel.isEmpty() || korteBeschrijving.isEmpty() || keuken.isEmpty() || bereidingstijd.isEmpty()
                        || bereidingswijze.isEmpty() || ingredienten.isEmpty()) {
                    throw new Exception("Alle verplichte velden moeten worden ingevuld!");
                }

                Object[] geselecteerdeAllergieen = allergieList.getSelectedValuesList().toArray();

                // Sla recept op in de lijst
                recepten.add("Titel: " + titel + "\nBeschrijving: " + korteBeschrijving + "\nKeuken: " + keuken +
                        "\nTijd: " + bereidingstijd + " min\nMoeilijkheid: " + moeilijkheid +
                        "\nBereidingswijze: " + bereidingswijze + "\nIngrediënten: " + ingredienten +
                        "\nAllergieën: " + (geselecteerdeAllergieen.length > 0 ? geselecteerdeAllergieen.toString() : "Geen"));

                JOptionPane.showMessageDialog(panel, "Recept succesvol opgeslagen!", "Succes", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Fout bij opslaan: " + ex.getMessage(), "Fout", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(opslaanKnop, BorderLayout.SOUTH);
        return panel;
    }

    // Scherm 2: Recepten Bekijken
    private JPanel createReceptBekijkenPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea receptenArea = new JTextArea();
        receptenArea.setEditable(false);

        // Update recepten bij het openen van dit scherm
        JButton verversKnop = new JButton("Ververs");
        verversKnop.addActionListener(e -> {
            receptenArea.setText(""); // Maak leeg
            if (recepten.isEmpty()) {
                receptenArea.setText("Geen recepten beschikbaar.");
            } else {
                for (String recept : recepten) {
                    receptenArea.append(recept + "\n\n---\n\n");
                }
            }
        });

        panel.add(new JScrollPane(receptenArea), BorderLayout.CENTER);
        panel.add(verversKnop, BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReceptenApp::new);
    }
}
