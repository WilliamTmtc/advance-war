package fr.main.view.render.units.land;

import fr.main.model.units.AbstractUnit;
import fr.main.view.render.animations.AnimationState;
import fr.main.view.render.sprites.ScaleRect;
import fr.main.view.render.sprites.SpriteList;
import fr.main.view.render.units.UnitRenderer;
import java.util.LinkedList;

public class MissilesRenderer extends UnitRenderer.Render {

    public MissilesRenderer(AbstractUnit unit) {
        super(unit);

        LinkedList<ScaleRect> areas = new LinkedList<>();
        areas.add(new ScaleRect(437, 893, 14, 16, 2));
        areas.add(new ScaleRect(463, 893, 14, 16, 2));
        AnimationState idle = new AnimationState(
            new SpriteList(getDir() + "sprites.png", areas), 20);

        anim.put("idleRIGHT", idle);
        anim.setState("idleRIGHT");
    }
}