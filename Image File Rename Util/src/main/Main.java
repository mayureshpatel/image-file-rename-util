package main;

import aio.AllInOne;
import java.io.File;
import java.io.IOException;

public class Main
{
	public static void main(String[] args)
	{
		if(args[0].equals("-t") || args[0].equals("--test"))
		{
			File[] files = setFileObjects();
			AllInOne aio = new AllInOne(files[0], files[1]);
			
			try
			{
				aio.changeFileNames();
			}
			catch(IOException e)
			{
				System.out.println("Error: " + e.getMessage());
			}
		}
		else if(args[0].equals("-h") || args[0].equals("--h"))
		{
			System.out.println("Image File Name Standardization\n\nUsage");
			System.out.println("-t, --test\t\tRuns a test using images provided in the program folder");
			System.out.println("-h, --help\t\tPrints out available options for the program. java Main <inputFile> <outputFile> or java Main [option]");
		}
		else if(args.length == 2)
		{
			File[] files = setFileObjects(args[0], args[1]);
			AllInOne aio = new AllInOne(files[0], files[1]);
			
			try
			{
				aio.changeFileNames();
			}
			catch(IOException e)
			{
				System.out.println("Error: " + e.getMessage());
			}
		}
		else
		{
			System.out.println("Incorrect arguments. Try again or type \"java Main -h\"");
		}
	}
	
	public static File[] setFileObjects()
	{
		File inputDirectory = new File("C:\\Users\\mayur\\Documents\\Programming\\Java\\Workspaces\\FileRename\\Rename Pictures\\testFolder\\input");
		File outputDirectory = new File("C:\\Users\\mayur\\Documents\\Programming\\Java\\Workspaces\\FileRename\\Rename Pictures\\testFolder\\output");
		File[] files = {inputDirectory, outputDirectory};
		return files;
	}
	
	public static File[] setFileObjects(String inputFile, String outputFile)
	{
		File[] files = {new File(inputFile), new File(outputFile)};
		return files;
	}
}
