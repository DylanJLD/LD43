package me.hotairbaloon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Random;

import me.game.GameObject;
import me.game.ID;

public class Hotbox extends GameObject {

	HashMap<String, BufferedImage> images;
	int w, h, cooldown;
	ID item;
	
	public Hotbox(int x, int y, int w, int h, HashMap<String, BufferedImage> images, ID id)
	{
		super(x, y, w, h, id);
		this.images = images;
		this.w = w;
		this.h = h;
		cooldown = 100;
		item = ID.Blank;
	}
	
	@Override
	public void update() {
		
		if(item == ID.Blank) {
			item = ID.Cooldown;
			cooldown = 100;
		}
		
		if(item == ID.Cooldown)
		{
			cooldown--;
		}
		
		if(cooldown == 0)
		{
			item = newItem();
			cooldown = 100;
		}

	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(images.get("hotbox"), x, y, w, h, null);

		g.drawImage(idToImage(item), x+6, y+6, 46, 46, null);
		
		//Cooldown
		if(item == ID.Cooldown)
		{
			g.setColor(Color.GRAY);
			g.fillRect(x+12, y+25, 36, 10);
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x+14, y+27, (int)(cooldown / 2.8), 6);
		}
	}
	
	private BufferedImage idToImage(ID id)
	{
		switch(id)
		{
		case BigRock:
			return images.get("bigrock");
		
		case Fuel:
			return images.get("fuel");
			
		case SmallRock:
			return images.get("smallrock");
			
		case Feather:
			return images.get("feather");
			
		case Anchor:
			return images.get("anchor");
			
		default:
			return images.get("blank");
		}
	}
	
	public ID getItem()
	{
		return item;
	}
	public void setItem(ID id)
	{
		this.item = id;
	}
	
	private ID newItem()
	{
		Random r = new Random();
		int number = r.nextInt(20);
		if(number <= 6)
		{
			return ID.Feather;
		}
		else if(number > 6 && number <= 11)
		{
			return ID.SmallRock;
		}
		else if(number > 11 && number <= 16)
		{
			return ID.BigRock;
		}
		else if(number > 16 && number <= 20)
		{
			return ID.Fuel;
		}
		return ID.Blank;
	}

}
