package fr.main.view.render.units.air;

import java.awt.*;
import java.util.LinkedList;

import fr.main.model.Player;
import fr.main.model.units.AbstractUnit;
import fr.main.model.Direction;
import fr.main.view.MainFrame;
import fr.main.view.render.units.UnitRenderer;
import fr.main.view.render.sprites.*;
import fr.main.view.render.animations.*;

public class BomberRenderer extends UnitRenderer.Render {

  public BomberRenderer (AbstractUnit unit) {
    super (unit);

    LinkedList<ScaleRect> areas = new LinkedList<>();
    areas.add(new ScaleRect (94, 100, 16, 16, 2));
    areas.add(new ScaleRect (117, 100, 16, 16, 2));
    AnimationState idle = new AnimationState(new SpriteList(getDir() + "air.png", areas), 20);

    anim.put("idleRIGHT", idle);
    anim.setState("idleRIGHT");
  }

}