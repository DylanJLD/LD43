package me.game;

public class GameMain 
{

	public static void main(String[] args) 
	{
		int width = 640;
		int height = 480;
		String title = "My Game";
		
		GameFramework myGame = new GameFramework(width, height, title);
		
		myGame.start();
	}

}
