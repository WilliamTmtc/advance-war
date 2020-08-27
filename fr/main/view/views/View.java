package fr.main.view.views;

import fr.main.view.controllers.Controller;
import javax.swing.JPanel;

/**
 * Empty view
 */
@SuppressWarnings("serial")
public class View extends JPanel {

    public View(Controller controller) {
        addKeyListener(controller);
        addMouseMotionListener(controller);
        addMouseListener(controller);
        addMouseWheelListener(controller);
    }
}
