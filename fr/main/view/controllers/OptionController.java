package fr.main.view.controllers;

import fr.main.view.MainFrame;
import fr.main.view.views.OptionView;
import fr.main.view.views.View;
import java.awt.event.ActionListener;

/**
 * Option panel
 */
public class OptionController extends Controller {

    public ActionListener quit;

    public OptionController(Controller controller) {
        super();

        quit = e -> MainFrame.setScene(controller);
    }

    public OptionController() { this(new MenuController()); }

    public View makeView() { return new OptionView(this); }
}