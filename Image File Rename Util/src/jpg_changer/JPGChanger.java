package jpg_changer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

/**
 * A class that changes the name of a file with the .jpg extension.
 * 
 * This class currently supports renaming to another folder. I will change it so that it renames into the same folder
 * 
 * @author Mayuresh
 *
 */
public class JPGChanger
{
	// Instance Variables
	
	// Constructors
	
	// Methods
	public boolean changeFileName(File inputFile, File outputFile, Metadata metadata)
	{
		System.out.println("Renaming: [" + inputFile.getName() + "]");
		
		ExifSubIFDDirectory exifDirectory = getExifDirectory(metadata);
		
		// Extract the date the picture was taken and the file extension
		String formattedDate = getFormattedDate(exifDirectory);
		
		// Change the file name to the new name
		boolean success = changeFileName(inputFile, outputFile, formattedDate);
		
		// Output Result
		System.out.println("...Completed: " + (success ? "[SUCCESSFUL]" : "[FAILED]"));
		return success;
	}
	
	private ExifSubIFDDirectory getExifDirectory(Metadata m)
	{
		return m.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
	}
	
	private String getFormattedDate(ExifSubIFDDirectory d)
	{
		Date date = d.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmssSS");
		return formatter.format(date);
	}
	
	private boolean changeFileName(File file, File newFile, String newFileName)
	{
		// We are working with only JPegs here so the file extension is .jpg
		String filePath = newFile.getAbsolutePath() + "/" + newFileName + ".jpg";
		
		File destinationFile = new File(filePath);
		System.out.print(" -> [" + destinationFile.getName() + "]");
		
		boolean changed = file.renameTo(destinationFile);
		if(!changed)
		{
			System.out.print("\n\t[File Exists] -> [Placing in Dups Folder: ");
			
			filePath = "C:\\Users\\Mayuresh\\Pictures\\Rename\\duplicates" + "/" + newFileName + ".jpg";
			File duplicateFile = new File(filePath);
			
			boolean dupChanged = file.renameTo(duplicateFile);
			System.out.println(dupChanged ? "SUCCESS]" : "FAILED]");
			
			// Delete from dups folder
			duplicateFile.delete();
		}
		return changed;
	}
}
