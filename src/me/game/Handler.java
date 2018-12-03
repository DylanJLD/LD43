package me.game;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler 
{
	public LinkedList<GameObject> objects = new LinkedList<GameObject>();
	
	public void update()
	{
		for(GameObject obj : objects)
		{
			obj.update();
		}
	}
	
	public void draw(Graphics g)
	{
		for(GameObject obj : objects)
		{
			obj.draw(g);
		}
	}
	
	public void addObject(GameObject obj)
	{
		this.objects.add(obj);
	}
	
	public void removeObject(GameObject obj)
	{
		this.objects.remove(obj);
	}
	
	public GameObject getObject(ID id)
	{
		for(GameObject obj : objects)
		{
			if(obj.getID() == id)
			{
				return obj;
			}
		}
		return objects.get(0);
	}
}
