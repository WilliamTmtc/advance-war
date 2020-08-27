package fr.main.view.render.units.naval;

import fr.main.model.units.AbstractUnit;
import fr.main.view.render.animations.AnimationState;
import fr.main.view.render.sprites.ScaleRect;
import fr.main.view.render.sprites.SpriteList;
import fr.main.view.render.units.UnitRenderer;
import java.util.LinkedList;

public class BlackBoatRenderer extends UnitRenderer.Render {

    public BlackBoatRenderer(AbstractUnit unit) {
        super(unit);

        LinkedList<ScaleRect> areas = new LinkedList<>();
        areas.add(new ScaleRect(48, 0, 16, 16, 2));
        AnimationState idle = new AnimationState(
            new SpriteList(getDir() + "missing.png", areas), 20);

        anim.put("idleRIGHT", idle);
        anim.setState("idleRIGHT");
    }
}