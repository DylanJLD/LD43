package me.game;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseState extends GameObject implements MouseListener, MouseMotionListener {
	
	boolean clicked, clickedPrevious;
	boolean pressed, pressedPrevious;
	boolean released, releasedPrevious;
	
	public MouseState()
	{
		super(0, 0, ID.MouseState);
		clicked = false;
		clickedPrevious = false;
		pressed = false;
		pressedPrevious = false;
		released = false;
		releasedPrevious = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		clicked = true;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressed = true;

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		released = true;

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();

	}

	@Override
	public void update() {
		if(clickedPrevious)
		{
			clicked = false;
		}
		
		if(pressedPrevious)
		{
			pressed = false;
		}
		
		if(releasedPrevious)
		{
			released = false;
		}
		
		clickedPrevious = clicked;
		pressedPrevious = pressed;
		releasedPrevious = released;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean getClicked()
	{
		return clicked;
	}
	
	public boolean getPressed()
	{
		return pressed;
	}
	
	public boolean getReleased()
	{
		return released;
	}

}
