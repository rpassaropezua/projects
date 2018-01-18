package com.ray.TileMap.framework;
/**
 * Animation class, controls image frames for current animation.
 * @author Raymundo Passaro
 */
import java.awt.image.*;

public class Animation {
	private BufferedImage[] frames;
	private int currentFrame;
	
	private long startTime;
	private long delay;
	
	
	/**
	 * Sets the current frame for the current animation,
	 * if it would go over it resets back to 0.
	 * @param images
	 */
	public void setFrames(BufferedImage[] images) {
		frames = images;
		if(currentFrame >= frames.length) {
			currentFrame = 0;
		}
	}
	
	/**
	 * Sets the delay for cycling through the image frames.
	 * @param d
	 */
	public void setDelay(long d) {
		delay = d;
	}
	
	/**
	 * Update method, checks if its time to switch frames.
	 */
	public void update() {
		if(delay == -1) {
			return;
		}
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if(elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}
		if(currentFrame == frames.length) {
			currentFrame = 0;
		}
	}
	
	/**
	 * Returns the current image frame.
	 * @return currenFrame
	 */
	public BufferedImage getImage() {
		return frames[currentFrame];
	}
}
