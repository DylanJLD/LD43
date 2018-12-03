package me.hotairbaloon;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.game.*;

public class Baloon extends GameObject {

	BufferedImage img;
	int w, h;
	
	public Baloon(int x, int y, int w, int h, BufferedImage img, ID id)
	{
		super(x, y, w, h, id);
		this.img = img;
		this.w = w;
		this.h = h;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y, w, h, null);
		
	}

}
