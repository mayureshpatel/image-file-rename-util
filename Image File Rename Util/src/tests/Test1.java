package tests;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.file.FileTypeDirectory;

public class Test1
{
	public static void main(String[] args) throws ImageProcessingException, IOException
	{
		// The directory to work in
		File picDirectory = new File("C://Users//Mayuresh//Pictures//Personal//All(nonrenamed)");
		
		// Have a list of all the files in the directory
		File[] directoryFiles = picDirectory.listFiles();
		
		// Iterate through all of the files in the directory
		for(int i = 0; i < directoryFiles.length; i++)
		{
			try
			{
				Metadata metadata = ImageMetadataReader.readMetadata(directoryFiles[i]);
				
				for (Directory directory : metadata.getDirectories()) {
					for (Tag tag : directory.getTags()) {
						System.out.println(tag);
					}
				}
				System.out.println();
			}
			catch(ImageProcessingException e)
			{
				System.out.println("Cannot Process Image: " + directoryFiles[i].getName() + "\n");
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				e.getMessage();
			}
		}
	}
	
	private static String formatDate(Date date)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_hhmms");
		return formatter.format(date);
	}
	
	private static String getFormattedDate(ExifSubIFDDirectory d)
	{
		Date date = d.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmssSS");
		return formatter.format(date);
	}
	
	private static String getFileExtension(File file)
	{
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
}
