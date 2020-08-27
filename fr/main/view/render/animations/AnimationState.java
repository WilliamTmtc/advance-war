package fr.main.view.render.animations;

import fr.main.view.MainFrame;
import fr.main.view.render.sprites.SpriteList;
import java.awt.Graphics;
import java.awt.Image;

public class AnimationState {

    private SpriteList images;
    private int frameRate;

    public AnimationState(SpriteList images, int frameRate) {
        this.images = images;
        this.frameRate = frameRate;
    }

    public void draw(Graphics g, int x, int y) {
        if (MainFrame.getTimer() % frameRate == 0)
            nextFrame();

        Image i = images.element();
        if (i == null)
            System.out.println("WHAT");

        try {
            g.drawImage(i, x, y + MainFrame.UNIT - i.getHeight(null), null);
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.exit(4);
        }
    }

    private void nextFrame() {
        Image tmp = images.poll();
        images.addLast(tmp);
    }
}
