package fr.main.view.render.terrains.land;

import java.util.LinkedList;

import fr.main.view.render.terrains.TerrainLocation;
import fr.main.view.render.terrains.TerrainRenderer;
import fr.main.view.render.sprites.*;
import fr.main.view.render.animations.*;

public class HillRenderer extends TerrainRenderer.Render {

  public HillRenderer (TerrainLocation.HillLocation location) {
    super(location);

//    LinkedList<ScaleRect> areas = new LinkedList<>();
//    areas.add(new ScaleRect (0, 0, 16, 16, 2));
//    AnimationState idle = new AnimationState(new SpriteList("./assets/terrains/hill.png", areas), 20);
//    anim.put("idle", idle);
//    anim.setState("idle");
  }

}

