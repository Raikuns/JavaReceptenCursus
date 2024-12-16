package org.example;

import javax.swing.*;

public class Applicatie {
    public static void main(String[] args) {
        // Start de applicatie
        SwingUtilities.invokeLater(OpbouwVanScherm::new);
    }
}
