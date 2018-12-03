package me.game;

public class Hitbox 
{
	public int x, y, w, h;
	
	public Hitbox(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public Boolean intersects(int x, int y)
	{
		if(x >= this.x && x <= this.x+w)//In x boundaries
		{
			if(y >= this.y && y <= this.y+h)//In y boundaries
			{
				return true;
			}
		}
		return false;
	}
	
	public Boolean intersects(Hitbox other)
	{
		if(other.x + other.w > x && other.x < x+w)//In x boundaries
		{
			if(other.y + other.h > y && other.y < y+h)//In y boundaries
			{
				return true;
			}
		}
		return false;
	}
	
	public void updatePos(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}
