package com.ray.TileMap.window;
import javax.swing.JFrame;

import com.ray.TileMap.framework.GamePanel;

public class Game {
	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Choices");
		GamePanel panel = new GamePanel();
		window.setContentPane(panel);
		window.pack();
		window.setVisible(true);
		
	}
}
