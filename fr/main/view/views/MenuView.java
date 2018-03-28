package fr.main.view.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import fr.main.view.sound.MusicEngine;
import fr.main.view.components.*;
import fr.main.view.controllers.MenuController;

public class MenuView extends View {

	private ImageIcon bg;
	private JLabel label;
	private JButton play,
                  select,
                  load,
                  edit,
                  exit,
                  option,
                  sound;

	static MusicEngine bm = new MusicEngine("./assets/sound/bc.wav");
	static boolean listen = true;

  protected MenuController controller;
	
	public MenuView (MenuController controller) {
    super(controller);
		// TODO Auto-generated constructor stub
    try {
		bg = new ImageIcon("./assets/bd.png");
		label = new JLabel(bg);
		
		play =new MenuButton("PLAY","./assets/button/b02.png",350, 150);
		select = new MenuButton("SELECT","./assets/button/b02.png",350, 290);
		load = new MenuButton("LOAD","./assets/button/b02.png",350, 430);
		edit = new MenuButton("EDIT","./assets/button/b02.png",350, 570);
		
		exit = new MenuButton("EXIT","./assets/button/b03.png",830, 0,20);
		option = new MenuButton("OPTION","./assets/button/b03.png",-5, 0,20);
		sound = new RButton("×");
		sound.setBounds(40, 600, 60, 60);
    } catch (IOException e) {
      System.err.println(e);
    }
		
		label.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());
		label.setIcon(bg);
		label.setHorizontalAlignment(0);
		
		label.add(play);
		label.add(select);
		label.add(load);
		label.add(edit);
		label.add(play);
		label.add(exit);
		label.add(option);
		label.add(sound);
		
    add(label);

    play.addActionListener(controller.play);
    sound.addActionListener(controller.sound);
    exit.addActionListener(controller.exit);
	}

}