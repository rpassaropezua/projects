package com.warsarehell.rpg.framework;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;



//import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.warsarehell.rpg.object.Armor;
import com.warsarehell.rpg.object.Enemy;
import com.warsarehell.rpg.object.Player;
import com.warsarehell.rpg.object.Weapon;
import com.warsarehell.rpg.window.Window;

public class Board {
	private JFrame window = new Window("Text Adventure");
	private Logic gameLogic = new Logic();
	private Player player;
	private ArrayList<JComponent> components = new ArrayList<>();
	public void init() {
		gameLogic.init();
		JPanel startPanel = new JPanel(new BorderLayout());
		startPanel.setLayout(new FlowLayout());
		startPanel.setVisible(true);
		window.add(startPanel);
		window.setVisible(true);
		JButton startButton = new JButton("Start");
		JButton loadButton = new JButton("Load Game");
		loadButton.setVisible(true);
		startButton.setVisible(true);
		startButton.setSize(10, 10);
		startPanel.add(startButton);
		startPanel.add(loadButton);
		
		components.add(startPanel);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.remove(components.get(0));
				window.repaint();
				start();
			}
		});
		
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.remove(components.get(0));
				window.repaint();
				loadGame();
			}
		});
	}
	
	private void loadGame() {
		System.out.println("made it");
		Player player = new Player();
		int count = 0;
		File saveDirectory = new File("RPGsrc/saves");
		ArrayList<String> fileNames = new ArrayList<>();
		JButton[] buttonGroup;
		for(File file : saveDirectory.listFiles()) {
			if (file.isFile()) {
				fileNames.add(file.getName());
				fileNames.trimToSize();
				count++;
			}
		}
		String[]saves = fileNames.toArray(new String[fileNames.size()]);
		JPanel loadPanel = new JPanel(new GridLayout((int)count/2, 1, 0, 0));
		buttonGroup = buildButtonGroup(count, saves);
		addButtonGroup(loadPanel, buttonGroup);
		for(int index = 0; index < buttonGroup.length; index++) {
			final int loopIndex = index;
			final JPanel loopPanel = loadPanel;
			buttonGroup[loopIndex].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(buttonGroup[loopIndex].getText().equals(saves[loopIndex])){
						File saveFile = new File("RPGsrc/saves/" + saves[loopIndex]);
						System.out.println(saveFile);
						gameLogic.loadFile(saveFile);
						window.remove(loopPanel);
						window.revalidate();
						mainMenu();
					}
				}
			});
		}
		gameLogic.setPlayer(player);
		window.add(loadPanel);
		window.setVisible(true);
		
	}
	
	private void start() {
		player = new Player(100, 0, 0, 1, 0);
		Weapon startWeapon = new Weapon("Iron Sword", 5, 10, 5, 5, 0);
		Armor startArmor = new Armor("Iron Chain", 30);
		player.setCurrentWeapon(startWeapon);
		player.setCurrentArmor(startArmor);
		
		gameLogic.setPlayer(player);
		
		JPanel initPanel = new JPanel(new BorderLayout());
		initPanel.setLayout(new FlowLayout());
		JLabel label = new JLabel("What is your name?");
		JTextField input = new JTextField();
		JButton submit = new JButton("Ok");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!input.getText().isEmpty()) {
					player.setName(input.getText());
					window.remove(initPanel);
					window.repaint();
					mainMenu();
				}
			}
		});
		input.setColumns(10);
		input.setVisible(true);
		
		label.setVisible(true);
		submit.setVisible(true);
		
		initPanel.add(label);
		initPanel.add(input);
		initPanel.add(submit);
		initPanel.setVisible(true);
		
		window.add(initPanel);
		window.setVisible(true);
		
	}
	
	private void mainMenu() {
		JPanel mainMenuPanel = new JPanel(new BorderLayout());
		
		JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 0, 5));
		JPanel labelPanel = new JPanel(new FlowLayout());
		JPanel mapPanel = drawMapPanel();
		String[] buttonNames = {"Move", "Check Map", "Check Inventory", "Check Stats", "Save"};
		JButton[] buttonGroup = buildButtonGroup(5, buttonNames);
		buttonPanel = addButtonGroup(buttonPanel, buttonGroup);
		JLabel playerName = new JLabel("Name: " + player.getName());
		JLabel playerHealth = new JLabel("Health: " + Integer.toString(player.getHealth()));
		
		for(int index = 0; index < buttonGroup.length; index++) {
			final int loopIndex = index;
			final JPanel loopPanel = buttonPanel;
			buttonGroup[loopIndex].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(buttonGroup[loopIndex].getText().equals("Move")){
						mainMenuPanel.remove(loopPanel);
						mainMenuPanel.revalidate();
						moveMenu(mainMenuPanel);
					} else if(buttonGroup[loopIndex].getText().equals("Save")) {
						gameLogic.saveFile();
					}
				}
			});
		}
		


		playerName.setVisible(true);
		playerHealth.setVisible(true);
		

		labelPanel.add(playerName);
		labelPanel.add(playerHealth);
		
		labelPanel.setBackground(Color.cyan);
		mainMenuPanel.setBackground(Color.black);

		

		labelPanel.setVisible(true);
		buttonPanel.setVisible(true);

		mainMenuPanel.add(buttonPanel, BorderLayout.LINE_END);
		mainMenuPanel.add(labelPanel, BorderLayout.PAGE_END);
		mainMenuPanel.add(mapPanel, BorderLayout.CENTER);
		mainMenuPanel.setVisible(true);
		
		window.add(mainMenuPanel);
		window.setVisible(true);
	}
	
	private void moveMenu(JPanel mainMenuPanel) {
		JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 0, 5));
		JPanel mapPanel = drawMapPanel();
		String[] buttonNames = {"North", "East", "South", "West", "Back"};
		JButton[] directionButtons = buildButtonGroup(5, buttonNames);
		
		buttonPanel = addButtonGroup(buttonPanel, directionButtons);
		
		mainMenuPanel.add(mapPanel, BorderLayout.CENTER);
		
		for(int index = 0; index < directionButtons.length; index++) {
			final int loopIndex = index;
			directionButtons[loopIndex].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!directionButtons[loopIndex].getText().equals("Back")) {
						if (directionButtons[loopIndex].getText().equals("North")){
							player.moveX(-1);
						}else if (directionButtons[loopIndex].getText().equals("South")) {
							player.moveX(1);
						}else if (directionButtons[loopIndex].getText().equals("West")) {
							player.moveY(-1);
						}else if (directionButtons[loopIndex].getText().equals("East")) {
							player.moveY(1);
						}
						if(gameLogic.encounterRoll()) {
							window.remove(mainMenuPanel);
							window.repaint();
							combatScene();
						}
						mainMenuPanel.remove(mapPanel);
						mainMenuPanel.add(drawMapPanel(), BorderLayout.CENTER);
						mainMenuPanel.revalidate();
					} else {
						window.remove(mainMenuPanel);
						mainMenu();
					}
				}
			});
		}

		mainMenuPanel.add(buttonPanel, BorderLayout.LINE_END);

		mainMenuPanel.setVisible(true);
		
		window.add(mainMenuPanel);
		window.setVisible(true);
	}
	
	private void combatScene() {
		JPanel combatPanel = new JPanel(new BorderLayout(10, 10));
		JPanel buttonPanel = new JPanel(new GridLayout(3,1));
		JPanel labelPanel = new JPanel(new GridLayout(3,1, 20, 10));
		//ImageIcon playerImage = new ImageIcon("RPGsrc/images/resizedCharacter.png");
		Enemy enemy = gameLogic.createEnemy(player.getLevel());
		String[] buttonNames = {"Attack", "Heal", "Run"};
		JButton[] actionButtons = buildButtonGroup(3, buttonNames);
		
		//JLabel playerHealth = new JLabel("Player Health: " + Integer.toString(player.getHealth()), playerImage, JLabel.CENTER);
		JLabel playerHealth = new JLabel("Player Health: " + Integer.toString(player.getHealth()));
		JLabel enemyHealth = new JLabel("Enemy Health: " + Integer.toString(enemy.getHealth()));
		JLabel combatSteps = new JLabel();
		buttonPanel = addButtonGroup(buttonPanel, actionButtons);
		
		for(int index = 0; index < actionButtons.length; index++) {
			final int loopIndex = index;
			actionButtons[loopIndex].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(enemy.getHealth() > 0) {
						if(actionButtons[loopIndex].getText().equals("Attack")) {
							player.setAttacking(true);
							String result = gameLogic.combatStep();
							combatSteps.setText(result);
						} else if(actionButtons[loopIndex].getText().equals("Heal")) {
							player.setHealing(true);
							String result = gameLogic.combatStep();
							combatSteps.setText(result);
						} else if(actionButtons[loopIndex].getText().equals("Run")) {
							player.setRunning(true);
							if(gameLogic.runRoll(player.getLevel(), enemy.getLevel())) {
								window.remove(combatPanel);
								window.repaint();
								mainMenu();
							}		
						}
						
						enemyHealth.setText("Enemy Health: " + Integer.toString(enemy.getHealth()));
						playerHealth.setText("Player Health: " + Integer.toString(player.getHealth()));	
						
					} else {
						window.remove(combatPanel);
						window.repaint();
						mainMenu();
					}
				}
			});
		}
		

		playerHealth.setVisible(true);
		enemyHealth.setVisible(true);
		
		playerHealth.setOpaque(true);
		enemyHealth.setOpaque(true);
		
		buttonPanel.setMaximumSize(new Dimension(100, 20));
		buttonPanel.setPreferredSize(new Dimension(100, 20));
		buttonPanel.setVisible(true);
		
		labelPanel.setVisible(true);
		
		
		labelPanel.add(playerHealth);
		labelPanel.add(enemyHealth);
		labelPanel.add(combatSteps);
		
		combatPanel.add(labelPanel, BorderLayout.CENTER);
		combatPanel.add(buttonPanel, BorderLayout.LINE_END);
		combatPanel.setVisible(true);
		
		window.add(combatPanel);
		window.setVisible(true);
	}
	
	private JPanel drawMapPanel() {
		JPanel mapPanel = new JPanel(new GridLayout(20, 20, 2, 2));
		for(int col = 0; col < 20; col++) {
			for(int row = 0; row < 20; row++) {
				JPanel icon = new JPanel();
				if(player.getLocationX() == col && player.getLocationY() == row) {
					icon.setBackground(Color.blue);
				} else {
					icon.setBackground(Color.orange);
				}
				mapPanel.add(icon);
			}
		}
		
		mapPanel.setBackground(Color.green);
		mapPanel.setVisible(true);
		
		return mapPanel;
	}
	
	private JButton[] buildButtonGroup(int amount, String[] buttonNames) {
		JButton[] buttonGroup = new JButton[amount];
		for(int index = 0; index < amount; index++){
			JButton button = new JButton();
			button.setVisible(true);
			button.setText(buttonNames[index]);
			buttonGroup[index] = button;
		}
		return buttonGroup;
	}
	
	
	private JPanel addButtonGroup(JPanel panel, JButton[] buttonGroup) {
		for(int index = 0; index < buttonGroup.length; index++) {
			panel.add(buttonGroup[index]);
		}
		return panel;
	}
}
