package me.hotairbaloon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

import me.game.Game;
import me.game.GameObject;
import me.game.GameState;
import me.game.ID;
import me.game.MouseState;

public class HotFramework extends Game
{
	private static final long serialVersionUID = 2338590104281194985L;
	
	HashMap<String, BufferedImage> images;
	
	MouseState mouse;
	
	GameState gameState;
	
	Hotbox hb1, hb2, hb3;
	
	ID hand, contact;
	
	ArrayList<GameObject> objects;
	
	int lives;
	
	double rollingY, height, velocity, acceleration, divisor;
	double baloonY;
	double fuel;
	int power, downforce;
	
	boolean newInteractive, start;
	
	Font centerFont, sideFont, fuelFont, endFont;
	FontRenderContext centerCtx, endCtx;
	
	DecimalFormat dpZero, dpOne;
	
	public HotFramework(int width, int height, String title)
	{
		super(width, height, title);
	}
	
	@Override
	public void init()
	{			
		//Initial Settings
		fpsToggle = true;
		
		//Game State
		gameState = GameState.Start;
		
		//Load Images
		loadImages();
		
		//Mouse State
		mouse = new MouseState();
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		
		//Hotboxes
		hb1 = new Hotbox(91, 520, 60, 60, images, ID.Hotbox);
		hb2 = new Hotbox(156, 520, 60, 60, images, ID.Hotbox);
		hb3 = new Hotbox(221, 520, 60, 60, images, ID.Hotbox);
		
		hb2.setItem(ID.Anchor);
		
		//Hand and Contact
		hand = ID.Blank;
		contact = ID.Blank;
		
		//Interactives
		objects = new ArrayList<GameObject>();
		objects.add(new Baloon(140, 220, 100, 160, images.get("baloon"), ID.Baloon));
		
		//Other
		lives = 3;
		fuel = 100.0;
		rollingY = 0.0;
		height = 0;
		velocity = 0;
		acceleration = 0;
		divisor = 30.0;
		baloonY = 200.0;
		power = 0;
		downforce = 0;
		newInteractive = true;
		start = false;
		
		dpZero = new DecimalFormat("#");
		dpOne = new DecimalFormat("#.#");
		
		centerFont = new Font("Arial Rounded MT Bold", Font.PLAIN, 30);
		sideFont = new Font("Arial Rounded MT Bold", Font.PLAIN, 22);	
		fuelFont = new Font("Arial Rounded MT Bold", Font.PLAIN, 16);
		endFont = new Font("Arial Rounded MT Bold", Font.PLAIN, 44);
	}
	
	@Override
	public void update()
	{
		//Mouse
		mouse.update();
		
		//Updates
		switch(gameState)
		{
		case Start:
			updateStart();
			break;
		
		case Game:
			updateGame();
			break;
			
		case End:
			updateEnd();
			break;
			
		default:
			break;
		}
		
	}
	
	private void updateStart()
	{
		if(mouse.getClicked())
		{
			gameState = GameState.Game;
		}
	}
	
	private void updateGame()
	{
		//Physics
		if(start)
		{
			acceleration -= 0.003;
		}
		else
		{
			height = 0;
		}
		
		if(velocity <= -2)
		{
			baloonY -= velocity;
			objects.get(0).setY((int)baloonY);
		}
		else
		{
			velocity += acceleration/divisor;
			rollingY += velocity;
			height += velocity/divisor;
		}
		
		//Sky
		if(rollingY > 671 || rollingY < -671)
		{
			rollingY = 0.0;
		}
		
		//Fuel Update
		if(acceleration != 0)
		{
			fuel -= 0.06;
		}
		
		//Click Updates
		clicksUpdate();
		handAndContact();
				
		//Interactive Update
		if(start)
		{
			newInteractiveUpdate();
		}
		
		for(GameObject obj : objects)
		{
			obj.update();
		}
		
		baloonHitUpdate();
				
		//Hotbox Updates
		if(start)
		{
			hb1.update();
			hb2.update();
			hb3.update();
		}
		
		//End Game Updates
		if(fuel <= 0 || lives == 0)
		{
			velocity = -2;
		}
		
		if(baloonY >= 680)
		{
			startAgain();
			gameState = GameState.End;
		}
	}
	
	private void updateEnd()
	{
		if(mouse.getClicked())
		{
			gameState = GameState.Game;
		}
	}
	
	@Override
	public void draw(Graphics g)
	{
		//Game Drawing
		centerCtx = g.getFontMetrics(centerFont).getFontRenderContext();
		endCtx = g.getFontMetrics(endFont).getFontRenderContext();
		
		//Sky
		if(height*30 > 672)
		{
			g.drawImage(images.get("sky"), 0, (int)rollingY, 378, 672, null);
		}
		else
		{
			g.drawImage(images.get("ground"), 0, (int)rollingY, 378, 672, null);
		}
		g.drawImage(images.get("sky"), 0, (int)rollingY - 672, 378, 672, null);
		g.drawImage(images.get("sky"), 0, (int)rollingY + 672, 378, 672, null);
		
		//Interactives
		for(GameObject obj : objects)
		{
			obj.draw(g);
		}
		
		switch(gameState)
		{
		case Start:
			drawStart();
			break;
		
		case Game:
			drawGame();
			break;
			
		case End:
			drawEnd();
			break;
			
		default:
			break;
		}
		
	}
	
	private void drawStart()
	{
		g.setColor(Color.BLACK);
		g.setFont(endFont);
		
		//You Go Up,
		g.drawString("You Go Up,", (int)(378-endFont.getStringBounds("You Go Up,", endCtx).getWidth())/2, 100);
		
		//Things Go Down!
		g.drawString("Things Go Down!", (int)(378-endFont.getStringBounds("Things Go Down!", endCtx).getWidth())/2, 150);
		
		//Click to Start
		g.setFont(centerFont);
		g.drawString("Click anywhere", (int)(378-centerFont.getStringBounds("Click anywhere", centerCtx).getWidth())/2, 500);
		g.drawString("to start!", (int)(378-centerFont.getStringBounds("to start!", centerCtx).getWidth())/2, 535);
	}
	
	private void drawGame()
	{
		//Hotboxes
		hb1.draw(g);
		hb2.draw(g);
		hb3.draw(g);
		
		//Game UI Text
		g.setColor(Color.BLACK);
		g.setFont(centerFont);
		g.drawString(dpZero.format(height) + "m", 155, 30);
		g.setFont(sideFont);
		g.drawString(dpOne.format(velocity) + "m/s", 300, 25);
		
		//Lives
		for(int i = 0; i < lives; i++)
		{
			g.drawImage(images.get("heart"), 10 + (i * 25), 10, 20, 20, null);
		}
		
		//Fuel
		g.setFont(fuelFont);
		g.setColor(Color.BLACK);
		g.drawString("Fuel", 28, 55);
		g.setColor(Color.GRAY);
		g.fillRect(10, 60, 70, 10);
		g.setColor(Color.ORANGE);
		g.fillRect(12, 62, (int)(fuel / 1.52), 6);
		
		//Hand
		g.drawImage(idToImage(hand), mouse.getX() - 23, mouse.getY() - 23, 46, 46, null);
	}
	
	private void drawEnd()
	{
		g.setColor(Color.BLACK);
		
		//You Went
		g.setFont(centerFont);
		g.drawString("You went", (int)(378-centerFont.getStringBounds("You went", centerCtx).getWidth())/2, 100);
		
		//Height
		g.setFont(endFont);
		g.drawString(dpZero.format(height) + "m", (int)(378-endFont.getStringBounds(dpZero.format(height) + "m", endCtx).getWidth())/2, 150);
		
		//High!
		g.setFont(centerFont);
		g.drawString("high!", (int)(378-centerFont.getStringBounds("high!", centerCtx).getWidth())/2, 190);
		
		//Continue
		g.drawString("Click anywhere", (int)(378-centerFont.getStringBounds("Click anywhere", centerCtx).getWidth())/2, 500);
		g.drawString("to start again!", (int)(378-centerFont.getStringBounds("to start again!", centerCtx).getWidth())/2, 535);
	}
	
	private void loadImages()
	{
		images = new HashMap<String, BufferedImage>();
		try {
			images.put("sky", ImageIO.read(getClass().getResource("/sky.png")));
			images.put("baloon", ImageIO.read(getClass().getResource("/baloon.png")));
			images.put("hotbox", ImageIO.read(getClass().getResource("/hotBox.png")));
			images.put("bigrock", ImageIO.read(getClass().getResource("/bigrock.png")));
			images.put("blank", ImageIO.read(getClass().getResource("/blank.png")));
			images.put("fuel", ImageIO.read(getClass().getResource("/fuel.png")));
			images.put("heart", ImageIO.read(getClass().getResource("/heart.png")));
			images.put("smallrock", ImageIO.read(getClass().getResource("/smalrock.png")));
			images.put("birdLeft", ImageIO.read(getClass().getResource("/bird.png")));
			images.put("birdRight", ImageIO.read(getClass().getResource("/birdRight.png")));
			images.put("feather", ImageIO.read(getClass().getResource("/feather.png")));
			images.put("anchor", ImageIO.read(getClass().getResource("/anchor.png")));
			images.put("ground", ImageIO.read(getClass().getResource("/ground.png")));
			images.put("planeLeft", ImageIO.read(getClass().getResource("/plane.png")));
			images.put("planeRight", ImageIO.read(getClass().getResource("/planeRight.png")));
		} catch (IOException e) {
			e.printStackTrace();
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
	
	private void clicksUpdate()
	{
		boolean interactiveClicked = false;
		
		if(mouse.getClicked())
		{
			//Hotbox 1
			if(hb1.getHitbox().intersects(mouse.getX(), mouse.getY()))
			{
				hand = hb1.getItem();
				hb1.setItem(ID.Blank);
			}
			else if(hb2.getHitbox().intersects(mouse.getX(), mouse.getY()))
			{
				hand = hb2.getItem();
				hb2.setItem(ID.Blank);
			}
			else if(hb3.getHitbox().intersects(mouse.getX(), mouse.getY()))
			{
				hand = hb3.getItem();
				hb3.setItem(ID.Blank);
			}
			else
			{
				for(GameObject obj : objects)
				{
					if(obj.getHitbox().intersects(mouse.getX(), mouse.getY()))
					{
						contact = obj.getID();
						interactiveClicked = true;
					}
				}
				
				if(!interactiveClicked)
				{
					contact = ID.Dropped;
				}
			}
		}
	}
	
	private void handAndContact()
	{
		if(contact == ID.Dropped)
		{
			if(hand == ID.Anchor)
			{
				start = true;
			}
			
			if(acceleration <= 0)
			{
				acceleration = 0;
			}
			
			acceleration += idToWeight(hand);
			hand = ID.Blank;
		}
		else if(hand == ID.Fuel && contact == ID.Baloon)
		{
			fuel = 100.0;
			acceleration += 0.14;
			hand = ID.Blank;
		}
		else if((hand == ID.SmallRock || hand == ID.BigRock) && contact == ID.Bird)
		{
			acceleration += 0.1;
			removeInteractive(ID.Bird);
			newInteractive = true;
			hand = ID.Blank;
		}
		else if(hand == ID.BigRock && contact == ID.Plane)
		{
			acceleration += 0.2;
			removeInteractive(ID.Plane);
			newInteractive = true;
			hand = ID.Blank;
		}
		
		contact = ID.Blank;
	}
	
	private double idToWeight(ID id)
	{
		switch(id)
		{
		case Blank:
			return 0.0;
			
		case BigRock:
			return 0.6;
			
		case Fuel:
			return 0.8;
			
		case SmallRock:
			return 0.3;
			
		case Feather:
			return 0;
			
		case Anchor:
			return 0.9;
			
		default:
			return 0.0;
		}
	}
	
	private void newInteractiveUpdate()
	{
		Random r = new Random(); 
		int selection;
		if(newInteractive && r.nextInt(500) == 1)
		{
			selection = r.nextInt(2);
			if(selection == 0 && height >= 100)
			{
				objects.add(new Bird(0, 220 + r.nextInt(90), 40, 40, r.nextBoolean(), images.get("birdLeft"), images.get("birdRight"), ID.Bird));
				newInteractive = false;
			}
			else if(selection == 1 && height >= 500)
			{
				objects.add(new Bird(0, 220 + r.nextInt(90), 40, 40, r.nextBoolean(), images.get("planeLeft"), images.get("planeRight"), ID.Plane));
				newInteractive = false;
			}
		}
	}
	
	private void baloonHitUpdate()
	{
		GameObject toRemove = null;
		for(GameObject obj : objects)
		{
			if(obj.getID() != ID.Baloon)
			{
				if(obj.getHitbox().intersects(objects.get(0).getHitbox()))
				{
					toRemove = obj;
					lives--;
					newInteractive = true;
				}
			}
		}
		objects.remove(toRemove);
	}
	
	private void removeInteractive(ID id)
	{
		GameObject toRemove = null;
		for(GameObject obj : objects)
		{
			if(obj.getID() == id)
			{
				toRemove = obj;
			}
		}
		objects.remove(toRemove);
	}
	
	public void startAgain()
	{			
		//Hotboxes
		hb1 = new Hotbox(91, 520, 60, 60, images, ID.Hotbox);
		hb2 = new Hotbox(156, 520, 60, 60, images, ID.Hotbox);
		hb3 = new Hotbox(221, 520, 60, 60, images, ID.Hotbox);
		
		hb2.setItem(ID.Anchor);
		
		//Hand and Contact
		hand = ID.Blank;
		contact = ID.Blank;
		
		//Interactives
		objects = new ArrayList<GameObject>();
		objects.add(new Baloon(140, 220, 100, 160, images.get("baloon"), ID.Baloon));
		
		//Other
		lives = 3;
		fuel = 100.0;
		rollingY = 0.0;
		velocity = 0;
		acceleration = 0;
		divisor = 30.0;
		baloonY = 200.0;
		power = 0;
		downforce = 0;
		newInteractive = true;
		start = false;
	}
}
