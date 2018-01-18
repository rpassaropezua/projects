package com.ray.TileMap.framework;
/**
 * Main framework class, handles the scenes, user input, and also contains the game loop.
 * @author Raymundo Passaro
 */

import java.awt.BorderLayout;

import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ray.TileMap.object.Door;
import com.ray.TileMap.object.Enemy;
import com.ray.TileMap.object.MapObject;
import com.ray.TileMap.object.Player;
import com.ray.TileMap.object.TileMap;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;
	
	private Thread thread;
	private boolean running;
	private boolean interrupted;
	
	private BufferedImage image;
	private Graphics2D g;
	private int FPS = 60;
	private int targetTime = 1000/FPS;
	
	private ArrayList<TileMap> easyLevels;
	private ArrayList<TileMap> hardLevels;
	private ArrayList<MapObject> objects;
	private int level;
	private int prestige = 0;
	private final String[] easyMaps = {"/Resources/StartMap.txt", "/Resources/level_1.txt", "/Resources/level_2.txt", "/Resources/level_3.txt", "/Resources/level_4.txt"};
	private final String[] hardMaps = {"/Resources/hard1.txt", "/Resources/hard2.txt", "/Resources/hard3.txt", "/Resources/hard4.txt", "/Resources/hard5.txt"};
	private final String scenarioFile = "/Resources/scenarios.txt";
	private String[][] scenarios = new String[10][3];
	private Player player;
	
	/**
	 * No argument constructor.
	 */
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
	}
	
	
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		addKeyListener(this);
	}
	
	public void run() {
		init();
		long startTime;
		long urdTime;
		long waitTime;
		
		while(running) {
			checkInterrupt();
			if(!interrupted) {
				if(player.getLives() > 0 && player.getPoints() < 5) {
					startTime = System.nanoTime();
					
					update();
					render();
					draw();
					urdTime = (System.nanoTime() - startTime) / 1000000;
					
					waitTime = targetTime - urdTime;
					
					try {
						Thread.sleep(waitTime);
					} catch (Exception e){}
				} else {
					break;
					
				}
			}
		}
		
		//endScene();
		
	}
	
	private void checkInterrupt() {
		synchronized(this) {
			while(interrupted) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void resume() {
		synchronized(this) {
			if(!interrupted) {
				notifyAll();
			}
		}
	}
	
	private synchronized void init() {
		super.setLayout(new BorderLayout());
		super.setBackground(Color.black);
		
		JPanel startPanel = new JPanel(new MigLayout("", "300[]-145[]", "200[]50[]"));
		startPanel.setBackground(Color.black);
		
		
		JButton startButton = new JButton("Start");
		startButton.setBorderPainted(false);
		startButton.setFont(new Font("Monospaced", Font.PLAIN, 13));
		startButton.setBackground(new Color(0, 255, 153));
		startButton.setFocusPainted(false);
		startButton.setVisible(true);
		
		JLabel title = new JLabel("Choices");
		title.setFont(new Font("Monospaced", Font.PLAIN, 50));
		title.setForeground(new Color(255, 255, 255));
		title.setVisible(true);
		
		startPanel.add(title, "cell 0 0");
		startPanel.add(startButton, "cell 1 1");
		startPanel.setVisible(true);
		
		super.add(startPanel, BorderLayout.CENTER);
		super.validate();
		super.setVisible(true);
		setInterrupted(true);
		
		running = true;
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		easyLevels = new ArrayList<TileMap>();
		hardLevels = new ArrayList<TileMap>();
		objects = new ArrayList<MapObject>();
		level = 0;
		TileMap.setEasyLevel(0);
		TileMap.setHardLevel(0);
		loadScenarios();
		
		for(int index = 0; index < easyMaps.length; index++) {
			TileMap tempMap = new TileMap(easyMaps[index], 81, this);
			easyLevels.add(tempMap);
		}
		
		for(int index = 0; index < hardMaps.length; index++) {
			TileMap tempMap = new TileMap(hardMaps[index], 81, this);
			hardLevels.add(tempMap);
		}
		
		player = new Player(easyLevels.get(level), this, ObjectID.Player);
		objects.add(player);
		
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GamePanel.super.remove(startPanel);
				howToPlay();
			}
			
		});
	}
	
	private void howToPlay() {
		super.removeAll();
		JPanel howToPlay = new JPanel(new MigLayout("", "50[]", "[]20[]"));
		howToPlay.setBackground(Color.black);
		
		JLabel info = new JLabel();
		info.setFont(new Font("Monospaced", Font.PLAIN, 25));
		info.setText("<html><h1 style='color:red;text-decoration:underline;'>How to Play</h1><p>You move your character using the WASD keys."
				+ " When jumping, you can press shift to grip to a wall. You can only do it a maximum of three times,"
				+ " this limit resets when you touch ground.</p>"
				+ "<br />"
				+ "<h1 style='color:red;text-decoration:underline;'>Goal of the game</h1>"
				+ "<p>The goal is to get five points. You get points by getting to the end of each level."
				+ " Each level consists of two paths, one's the easy path"
				+ " the other is the hard path. If you take the easy path you have a 50/50 split of either getting points, or losing a life."
				+ " With the hard path, you have a 50/50 chance of either getting a point or nothing happening."
				+ " However, the more hard paths you take, your chance to get points increases.</p>"
				+ "</html>");
		info.setForeground(Color.white);
		info.setVisible(true);
		
		JButton startButton = new JButton("Continue");
		startButton.setBorderPainted(false);
		startButton.setFont(new Font("Monospaced", Font.PLAIN, 20));
		startButton.setBackground(new Color(0, 255, 153));
		startButton.setBounds(20, 100, 20, 100);
		startButton.setFocusPainted(false);
		startButton.setVisible(true);
		
		howToPlay.add(info, "cell 0 0");
		howToPlay.add(startButton, "cell 0 1");
		howToPlay.setVisible(true);
		
		super.add(howToPlay);
		super.validate();
		
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GamePanel.super.remove(howToPlay);
				scenarioIntro();
			}
			
		});

	}
	
	public void scenarioIntro() {
		interrupted = true;

		JPanel scenario = new JPanel(new MigLayout("", "20[]20[]", "200[]20[]"));
		scenario.setBackground(Color.black);
		
		
		JLabel scenarioText = new JLabel();
		scenarioText.setText(scenarios[level][0]);
		scenarioText.setFont(new Font("Monospaced", Font.PLAIN, 20));
		scenarioText.setForeground(Color.white);
		scenarioText.setVisible(true);
		
		JButton easyChoice = new JButton();
		
		easyChoice.setFont(new Font("Monospaced", Font.PLAIN, 20));
		easyChoice.setForeground(Color.black);
		easyChoice.setBackground(new Color(100, 255, 0));
		easyChoice.setBorderPainted(false);
		easyChoice.setFocusPainted(false);
		easyChoice.setVisible(true);
		
		JButton hardChoice = new JButton();
		
		hardChoice.setFont(new Font("Monospaced", Font.PLAIN, 20));
		hardChoice.setForeground(Color.black);
		hardChoice.setBackground(new Color(100, 255, 0));
		hardChoice.setBorderPainted(false);
		hardChoice.setFocusPainted(false);
		hardChoice.setVisible(true);
		
		easyChoice.setText(scenarios[level][1]);
		hardChoice.setText(scenarios[level][2]);
		
		scenario.add(scenarioText, "cell 0 0 2 1");
		
		if((int)(Math.random() * 10) % 2 == 0) {
			scenario.add(easyChoice, "cell 0 1");
			scenario.add(hardChoice, "cell 1 1");
		} else {
			scenario.add(easyChoice, "cell 1 1");
			scenario.add(hardChoice, "cell 0 1");
		}
		
		scenario.setVisible(true);
		
		super.add(scenario);
		super.revalidate();
		
		easyChoice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				synchronized(this) {
					player.setMap(easyLevels.get(level));
					GamePanel.super.remove(scenario);
					interrupted = false;
					resume();
				}
				
			}
			
		});
		
		hardChoice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				synchronized(this) {
					player.setMap(hardLevels.get(level));
					GamePanel.super.remove(scenario);
					interrupted = false;
					resume();
				}
				
			}
			
		});
		
		
	}
	
	public void nextLevel() {
		if(level < easyLevels.size()-1) {
			level++;
			scenarioIntro();
		} else {
			level = 0;
			prestige += easyLevels.size();
			scenarioIntro();
		}
	}
	
	private void update(){
		if(!player.getMap().isHard()) {
			easyLevels.get(level).update();
		} else {
			hardLevels.get(level).update();
		}
		for(int index = 0; index < objects.size(); index++) {
			if(objects.get(index).getId() == ObjectID.Enemy) {
				Enemy tempEnemy = (Enemy)objects.get(index);
				if(tempEnemy.getLevel() == level) {
					tempEnemy.update(objects);
				}
			} else {
				objects.get(index).update(objects);
			}
		}

	}
	
	private void render(){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		if(!player.getMap().isHard()) {
			easyLevels.get(level).draw(g);
		} else {
			hardLevels.get(level).draw(g);
		}
		for(int index = 0; index < objects.size(); index++) {
			if(objects.get(index).getId() == ObjectID.Enemy) {
				Enemy tempEnemy = (Enemy)objects.get(index);
				if(tempEnemy.getLevel() == level) {
					tempEnemy.draw(g);
				}
			} else if(objects.get(index).getId() == ObjectID.Door) {
				Door tempDoor = (Door)objects.get(index);
				if(tempDoor.getLevel() == level) {
					if(player.getMap().isHard()) {
						if(tempDoor.isDifficult()) {
							tempDoor.draw(g);
						}
					}else if(!player.getMap().isHard()) {
						if(!tempDoor.isDifficult()) {
							tempDoor.draw(g);
						}
					}
				}
			} else {
				objects.get(index).draw(g);
			}
		}
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.BOLD, 25));
		g.drawString("Level " + (level+1+(prestige)), 100, 100);
		g.drawString("Lives", 100, 150);
		g.drawString("Points " + (player.getPoints()), 100, 200);
		g.setColor(Color.RED);
		if(player.getLives() > 0) {
			for(int i = 0; i < player.getLives(); i++) {
				g.fillRect(200 + (40 * i), 130, player.getWidth(), player.getWidth());
			}
		}
	}
	
	private void draw(){
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	
	public void createEnemy(TileMap map, double x, double y, int level) {
		Enemy enemy = new Enemy(map, x, y, ObjectID.Enemy, level);
		objects.add(enemy);
	}
	
	public void createDoor(TileMap map, double x, double y, int level, boolean difficulty) {
		Door door = new Door(map, x, y, ObjectID.Door, level, difficulty);
		objects.add(door);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {

		int code = arg0.getKeyCode();
		if(code == KeyEvent.VK_A) {
			player.setLeft(true);
		}
		if(code == KeyEvent.VK_D) {
			player.setRight(true);
		}
		if(code == KeyEvent.VK_W ) {
			player.setJumping(true);
		}
		if(code == KeyEvent.VK_SHIFT) {
			player.setGripping(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int code = arg0.getKeyCode();
		if(code == KeyEvent.VK_A) {
			player.setLeft(false);
		}
		if(code == KeyEvent.VK_D) {
			player.setRight(false);
		}
		if(code == KeyEvent.VK_SHIFT) {
			player.setGripping(false);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
		
	}
	
	public void setInterrupted(boolean interrupted) {
		this.interrupted = interrupted;
	}
	
	private void loadScenarios() {
		try {
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(scenarioFile)));
			for(int index = 0; index < scenarios.length; index++) {
							
				for(int innerIndex = 0; innerIndex < scenarios[index].length; innerIndex++) {
					
					scenarios[index][innerIndex] = reader.readLine();			
				}
				
			}
			
			reader.close();
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
	}
	
	public void endScene() {
		super.removeAll();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT + 100);
		super.setBackground(Color.black);
		JPanel endPanel = new JPanel(new MigLayout("", "50[]", "[]20[]"));
		endPanel.setBackground(Color.black);
		
		JLabel info = new JLabel();
		info.setFont(new Font("Monospaced", Font.PLAIN, 30));
		info.setText("<html><h1 style='color:red;text-decoration:underline;'>Thanks for playing</h1>"
				+ "<p>I made this game to model depression. Choosing the hard path doesn't mean immediate happiness or success"
				+ " and might even feel useless,"
				+ " and the easy path might seem just as good.</p><br />"
				+ "<p>While choosing the hard path doesn't mean immediate happiness though, as you make more and more of these choices"
				+ " you better yourself and make it more likely that you'll succeed and achieve happiness. While choosing the easy path"
				+ " has the same initial chance of success for relatively low effort, it will wear you down until you lose </p>"
				+ "<br />"
				+ "<br />"
				+ "</html>");
		info.setForeground(Color.white);
		info.setVisible(true);
		
		JButton exitButton = new JButton("Game Over");
		exitButton.setBorderPainted(false);
		exitButton.setFont(new Font("Monospaced", Font.PLAIN, 20));
		exitButton.setBackground(new Color(0, 255, 153));
		exitButton.setBounds(20, 100, 20, 100);
		exitButton.setFocusPainted(false);
		exitButton.setVisible(true);
		
		endPanel.add(info, "cell 0 0");
		endPanel.add(exitButton, "cell 0 1");
		endPanel.setVisible(true);
		
		super.add(endPanel);
		super.validate();
		
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
			
		});
	}
}
