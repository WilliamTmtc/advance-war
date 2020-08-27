package fr.main.view.components;

import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class PlayerButton extends JButton {

    private static Image BACKGROUND;

    static {
        try {
            BACKGROUND = ImageIO.read(new File("./assets/button/border01.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PlayerButton() {
        super("");

        setContentAreaFilled(false);
        setOpaque(false);
        setBounds(0, 0, 50, 50);
        setFocusPainted(false);
        setBorder(null);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(BACKGROUND, 0, 0, getWidth(), getHeight(), null);
    }
}
