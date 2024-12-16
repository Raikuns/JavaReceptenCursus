package org.example;

import javax.swing.*;
import java.awt.*;

public class OpbouwVanScherm extends JFrame {

    public OpbouwVanScherm() {
        setTitle("Recepten Boek");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        ReceptenBekijkenPaneel receptBekijkenPaneel = new ReceptenBekijkenPaneel();
        ReceptenToevoegenPaneel receptenToevoegenPaneel = new ReceptenToevoegenPaneel();

        JTabbedPane tabBladen = new JTabbedPane();
        tabBladen.addTab("Recepten Toevoegen", receptenToevoegenPaneel.createReceptToevoegenPanel());
        tabBladen.addTab("Recepten Bekijken", receptBekijkenPaneel.createReceptBekijkenPanel());

        // voegt functionaliteit toe aan de tabbladen knop
        tabBladen.addChangeListener(e -> {
            int tabIndex = tabBladen.getSelectedIndex();

            switch (tabIndex) {
                case 0 -> System.out.println("Komt nog");
                // haalt alle recepten op uit de database en refreshed de dropdown
                case 1 -> receptBekijkenPaneel.updateDropdown();
            }
        });

        // Opmaak van het scherm
        add(tabBladen, BorderLayout.CENTER);

        setVisible(true);
    }

}
