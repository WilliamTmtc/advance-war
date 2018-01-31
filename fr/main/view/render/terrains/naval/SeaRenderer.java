package fr.main.view.render.terrains.naval;

import java.awt.Graphics;
import java.awt.Color;

import fr.main.view.MainFrame;
import fr.main.view.render.Renderer;
import fr.main.model.terrains.naval.Sea;

public class SeaRenderer extends Sea implements Renderer {

  public void draw (Graphics g, int x, int y) {
    g.setColor (Color.cyan);
    g.fillRect (x, y, MainFrame.UNIT, MainFrame.UNIT);
  }

}

