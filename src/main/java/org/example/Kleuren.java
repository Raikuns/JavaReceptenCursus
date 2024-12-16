package org.example;

import javax.swing.*;
import java.awt.*;

public class Kleuren {
    public static Color gray = new Color(80, 80, 80);
    public static Color white = new Color(255, 255, 255);
    public static Color black = new Color(0, 0, 0);
    public static Color orange = new Color(255, 120, 70);


    public static void KleurButton(JButton naarToevoegenKnop, Color orange, Color white) {
        naarToevoegenKnop.setBackground(orange);
        naarToevoegenKnop.setForeground(white);
    }
}
