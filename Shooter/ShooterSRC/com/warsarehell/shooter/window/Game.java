package com.warsarehell.shooter.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.warsarehell.shooter.framework.KeyInput;
import com.warsarehell.shooter.framework.OBJECTID;
import com.warsarehell.shooter.object.Player;
//import com.warsarehell.shooter.object.Projectile;

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable {
	//private static final long serialVersionUID = 7837108672426641916L;
	private boolean running = false;
	private boolean gameOver = false;
	private Thread thread;
	private static int WIDTH,HEIGHT;
	private Handler handler;
	private KeyInput keyInput;
	private int fps = 0;
	private int ups = 0;
	private boolean FPS_UPS_Visible=false;

	private void init(){
		setWIDTH(getWidth());
		setHEIGHT(getHeight());
		handler = new Handler();
		handler.createLevel();
		handler.addObject(new Player(100,100,handler,OBJECTID.Player,this));
		handler.setPlayer();
		keyInput = new KeyInput(handler,this);
		keyInput.init();
		this.addKeyListener(keyInput);

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
		double amountOfTicks = 60.0;
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
		Graphics g = bf.getDrawGraphics();
		super.paint(g);
		g.setColor(Color.magenta);
		if(FPS_UPS_Visible)
			g.drawString("FPS: "+fps+" UPS: "+ups, 0, 50);
		handler.render(g);
		if(gameOver){
			//Projectile.endGameStats(g,this);
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
		new Window(1000,500,"Shooter",new Game());
	}

	public static int getWIDTH() {
		return WIDTH;
	}

	public static void setWIDTH(int wIDTH) {
		WIDTH = wIDTH;
	}

	public static int getHEIGHT() {
		return HEIGHT;
	}

	public static void setHEIGHT(int hEIGHT) {
		HEIGHT = hEIGHT;
	}
}
