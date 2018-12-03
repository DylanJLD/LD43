package me.hotairbaloon;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.game.GameObject;
import me.game.ID;

public class Bird extends GameObject {

	BufferedImage imgLeft, imgRight;
	int w, h;
	boolean dir;
	double rollingX;
	
	public Bird(int x, int y, int w, int h, boolean dir, BufferedImage imgLeft, BufferedImage imgRight, ID id)
	{
		super(x, y, w, h, id);
		this.imgLeft = imgLeft;
		this.imgRight = imgRight;
		this.w = w;
		this.h = h;
		this.dir = dir;
		
		if(dir)//Left
		{
			this.x = 380;
		}
		else//Right
		{
			this.x = -5 - w;
		}
		rollingX = this.x;
	}
	
	@Override
	public void update() {
		
		if(dir)//Left
		{
			rollingX -= 0.5;
			x = (int)rollingX;
		}
		else//Right
		{
			rollingX += 0.5;
			x = (int)rollingX;
		}

		hitbox.updatePos(x, y);
	}

	@Override
	public void draw(Graphics g) {
		if(dir)//Left
		{
			g.drawImage(imgLeft, x, y, w, h, null);
		}
		else//Right
		{
			g.drawImage(imgRight, x, y, w, h, null);
		}
		
	}

}
