package aio;

import java.io.File;
import java.io.IOException;

public class Main
{
	public static void main(String[] args)
	{
		File inputDirectory = new File("C:\\Users\\Mayuresh\\Pictures\\new\\FileRename\\input");
		File outputDirectory = new File("C:\\Users\\Mayuresh\\Pictures\\new\\FileRename\\output");
		
		AllInOne aio = new AllInOne(inputDirectory, outputDirectory);
		
		try 
		{
			aio.changeFileNames();
		}
		catch(IOException e)
		{
			
		}
	}
}
