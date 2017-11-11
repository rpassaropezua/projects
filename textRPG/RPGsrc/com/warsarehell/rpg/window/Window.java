package com.warsarehell.rpg.window;

import javax.swing.JFrame;

public class Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8440675835996530522L;

	public Window(String title) {
		setTitle(title);
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
