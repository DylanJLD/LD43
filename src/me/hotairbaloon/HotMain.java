package me.hotairbaloon;

public class HotMain 
{

	public static void main(String[] args) 
	{
		int width = 378;
		int height = 672;
		String title = "My Game";
		
		HotFramework myGame = new HotFramework(width, height, title);
		
		myGame.start();
	}

}
