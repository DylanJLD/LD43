package me.game;

import java.awt.Graphics;

public class GameFramework extends Game
{
	private static final long serialVersionUID = 2338590104281194985L;
	
	public GameFramework(int width, int height, String title)
	{
		super(width, height, title);
	}
	
	@Override
	public void init()
	{			
		//Initial Settings
		fpsToggle = true;
		
	}
	
	@Override
	public void update()
	{
		//Game Updating
		
	}
	
	@Override
	public void draw(Graphics g)
	{
		//Game Drawing
		
	}
}
