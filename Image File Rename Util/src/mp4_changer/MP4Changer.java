package mp4_changer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.drew.metadata.Metadata;
import com.drew.metadata.mp4.Mp4Directory;

public class MP4Changer
{
	// Instance Variables

	// Constructors
	
	// Methods
	public boolean changeFileName(File inputFile, File outputFile, Metadata metadata)
	{
		System.out.print("Renaming [" + inputFile.getName() + "]");
		
		Mp4Directory quickTimeDirectory = getMP4Directory(metadata);

		// Extract the date the picture was taken and the file extension
		String formattedDate = getFormattedDate(quickTimeDirectory);

		// Change the file name to the new name
		boolean success = changeFileName(inputFile, outputFile, formattedDate);
		
		// Output Result
		System.out.println("Completed: " + (success ? "[SUCCESSFUL]" : "[FAILED]"));
		return success;
	}

	private Mp4Directory getMP4Directory(Metadata m)
	{
		return m.getFirstDirectoryOfType(Mp4Directory.class);
	}

	private String getFormattedDate(Mp4Directory d)
	{
		Date date = d.getDate(Mp4Directory.TAG_CREATION_TIME);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmssSS");
		return formatter.format(date);
	}

	private boolean changeFileName(File file, File newFile, String newFileName)
	{
		// We are working with only MP4 files here so the file extension is .mp4
		String filePath = newFile.getAbsolutePath() + "/" + newFileName + ".mp4";

		File destinationFile = new File(filePath);
		System.out.print(" -> [" + destinationFile.getName() + "]");
		
		boolean changed = file.renameTo(destinationFile);
		if(!changed)
		{
			System.out.print("[File Exists] -> [Placing in Dups Folder: ");
			
			filePath = "C:\\Users\\Mayuresh\\Pictures\\Rename\\duplicates" + "/" + newFileName + ".jpg";
			File duplicateFile = new File(filePath);
			
			boolean dupChanged = file.renameTo(duplicateFile);
			System.out.print(dupChanged ? "SUCCESS]" : "FAILED]");
			
			// Delete from dups folder
			duplicateFile.delete();
		}
		return changed;
	}
}
