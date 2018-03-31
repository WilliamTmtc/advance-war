package fr.main.view.render.units.air;

import java.awt.*;
import java.util.LinkedList;

import fr.main.model.players.Player;
import fr.main.model.units.AbstractUnit;
import fr.main.model.Direction;
import fr.main.view.MainFrame;
import fr.main.view.render.units.UnitRenderer;
import fr.main.view.render.sprites.*;
import fr.main.view.render.animations.*;

public class StealthRenderer extends UnitRenderer.Render {

  public StealthRenderer (AbstractUnit unit) {
    super (unit);

    LinkedList<ScaleRect> areas = new LinkedList<>();
    areas.add(new ScaleRect (1, 67, 64, 63, 0.5));
    AnimationState idle = new AnimationState(new SpriteList(getDir() + "missing.png", areas), 20);

    anim.put("idleRIGHT", idle);
    anim.setState("idleRIGHT");
  }

}