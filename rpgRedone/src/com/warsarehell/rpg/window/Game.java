package com.warsarehell.rpg.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import com.warsarehell.rpg.framework.MouseInput;
import com.warsarehell.rpg.framework.OBJECTID;
import com.warsarehell.rpg.object.Enemy;
import com.warsarehell.rpg.object.Player;
import com.warsarehell.rpg.object.TileMap;



@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable {
	private boolean running = false;
	private boolean gameOver = false;
	private Thread thread;
	public static int WIDTH,HEIGHT;
	Handler handler;
	//MouseMotion mouseMotion;
	private int fps = 0;
	private int ups = 0;
	private boolean FPS_UPS_Visible=true;
	private MouseInput mouseInput;
	private TileMap map = new TileMap();
	private String baseTileSet = "src/images/tileset1.jpg";
	private String displayTiles = "src/resources/tilemap1";
	private String wipMap = "src/resources/tilemap2";
	
	public void init(){
		WIDTH = getWidth();
		HEIGHT = getHeight();
		map.createTileMap(wipMap, baseTileSet, 81);
		handler = new Handler();
		handler.init();
		handler.addObject(map);
		handler.addObject(new Player(100,100,handler,OBJECTID.Player,this));
		handler.addObject(new Enemy(300,300, handler, OBJECTID.Enemy, this));
		handler.setPlayer();
		mouseInput = new MouseInput(handler, this);
		mouseInput.init();
		//mouseMotion = new MouseMotion(handler, this);
		//mouseMotion.init();
		this.addMouseListener(mouseInput);
		this.addMouseMotionListener(mouseInput);
		System.out.println("Game Initialize");

	}

	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();

	}

	public void run(){
		init();
		long lastTime = System.nanoTime();
		double amountOfTicks = 144.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				this.ups=updates;
				this.fps=frames;
				frames = 0;
				updates = 0;
			}
		}
	}
	private void update(){
		handler.update();
	}
	private void render(){
		BufferStrategy bf = this.getBufferStrategy();
		if(bf==null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics2D g = (Graphics2D) bf.getDrawGraphics();
		super.paint(g);
		g.setColor(Color.magenta);
		if(FPS_UPS_Visible)
			g.drawString("FPS: "+fps+" UPS: "+ups, 0, 50);
		handler.render(g);
		if(gameOver){
			setRunning(false);
		}
		g.dispose();
		bf.show();

	}
	public void setFPS_UPS_Visible(){
		if(FPS_UPS_Visible)
			FPS_UPS_Visible=false;
		else
			FPS_UPS_Visible=true;
	}
	public void setRunning(boolean running){
		this.running=running;
	}
	public boolean isRunning(){
		return this.running;
	}
	public void setgameOver(){
		this.gameOver=true;
		render();
	}
	public static void main(String[]args){
		new Window(1448,719,"RPG",new Game());
	}
}
