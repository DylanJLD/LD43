package me.game;

import java.awt.Graphics;

public abstract class GameObject 
{
	protected Handler handler;
	protected int x, y;
	protected Hitbox hitbox;
	protected ID id;
	
	public GameObject()
	{
		x = 0;
		y = 0;
		id = ID.Blank;
	}
	
	public GameObject(int x, int y, ID id)
	{
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public GameObject(int x, int y, int w, int h, ID id)
	{
		this.x = x;
		this.y = y;
		hitbox = new Hitbox(x, y, w, h);
		this.id = id;
	} 
	
	public abstract void update();
	public abstract void draw(Graphics g);
	
	public int getX()
	{
		return x;
	}
	public void setX(int x)
	{
		this.x = x;
	}
	
	public int getY()
	{
		return y;
	}
	public void setY(int y)
	{
		this.y = y;
	}
	
	public ID getID()
	{
		return id;
	}
	public void setID(ID id)
	{
		this.id = id;
	}
	
	public Hitbox getHitbox()
	{
		return hitbox;
	}
}
