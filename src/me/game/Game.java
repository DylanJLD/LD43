package me.game;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1843927388793130474L;
	
	protected BufferStrategy bs;
	protected Graphics g;
	private Thread thread;
	private boolean running = false;
	protected Handler handler;
	
	protected int fps;
	protected int secondsPassed;
	protected boolean fpsToggle, majorTick;
	protected int width, height;
	
	public Game()
	{	
		new Window(640, 480, "Game", this);	
		
		handler = new Handler();
		fpsToggle = false;
		secondsPassed = 0;
	}
	
	public Game(int width, int height, String title)
	{
		new Window(width+6, height+29, title, this);
		
		handler = new Handler();
		fpsToggle = false;
		secondsPassed = 0;
		majorTick = false;
		this.width = width;
		this.height = height;
	}
	
	public synchronized void start()
	{
		//Start buffers
		this.createBufferStrategy(2);
		
		//Start thread
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop()
	{
		try
		{
			thread.join();
			running = false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		//GAME CLOCK
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		init();
		update();
		while(running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1)
			{
				update();
				majorTick = false;
				delta--;
			}
			if(running)
			{
				backendDraw();
			}
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				fps = frames;
				frames = 0;
				secondsPassed++;
				majorTick = true;
			}
		}
		stop();
	}
	
	public void init()
	{
		
	}
	
	public void update()
	{
		
	}
	
	public void draw(Graphics g)
	{
		
	}
	
	public void backendDraw()
	{
		//Buffers and Graphics Startup
		bs = this.getBufferStrategy();
		g = bs.getDrawGraphics();
		((Graphics2D) g).scale(width/378f, height/672f);
		
		//Background
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, 378, 672);
		
		//Game Drawing
		draw(g);
		
		//Draw Frames Per Second
		if(fpsToggle) 
		{
			g.setFont(new Font("Calibri", Font.PLAIN, 22));
			g.setColor(Color.RED);
			g.drawString(Integer.toString(fps), 550, 20);
		}

		//Buffers and Graphics Reset
		g.dispose();
		bs.show();
	}
}
