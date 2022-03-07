package aio;

import java.io.File;
import java.io.IOException;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.file.FileTypeDirectory;

import jpg_changer.JPGChanger;
import mov_changer.MOVChanger;
import mp4_changer.MP4Changer;

public class AllInOne
{
	// Instance Variables
	private File inputDirectory, outputDirectory;
	private File[] directoryFiles;
	
	private boolean[] sucessList;
	
	private JPGChanger jpgChanger;
	private MOVChanger movChanger;
	private MP4Changer mp4Changer;
	
	// Constructors
	public AllInOne(File inputDirectory, File outputDirectory)
	{
		this.inputDirectory = inputDirectory;
		this.outputDirectory = outputDirectory;
		this.directoryFiles = this.inputDirectory.listFiles();
		
		this.sucessList = new boolean[this.directoryFiles.length];
		
		this.jpgChanger = new JPGChanger();
		this.movChanger = new MOVChanger();
		this.mp4Changer = new MP4Changer();
	}
	
	// Methods
	public void changeFileNames() throws IOException
	{
		System.out.println("[Renaming: " + this.directoryFiles.length + " files]");
		for(int i = 0; i < this.directoryFiles.length; i++)
		{
			try
			{
				Metadata metadata = ImageMetadataReader.readMetadata(directoryFiles[i]);
				
				// Find out what kind of file it is and then change the file name
				if(getDetectedFileType(metadata).equals("jpg") || getDetectedFileType(metadata).equals("jpeg") || getDetectedFileType(metadata).equals("cr2"))
				{
					this.sucessList[i] = this.jpgChanger.changeFileName(this.directoryFiles[i], outputDirectory, metadata);
				}
				else if(getDetectedFileType(metadata).equals("mov"))
				{
					this.sucessList[i] = this.movChanger.changeFileName(this.directoryFiles[i], outputDirectory, metadata);
				}
				else if(getDetectedFileType(metadata).equals("mp4"))
				{
					this.sucessList[i] = this.mp4Changer.changeFileName(this.directoryFiles[i], outputDirectory, metadata);
				}
				else
				{
					System.out.print("[File Not Supported: " + getFileExtension(this.directoryFiles[i]) + "]");
					
					// Move the file to the unsupported folder
					String destinationFilePath = "C:\\Users\\Mayuresh\\Pictures\\new\\FileRename\\errors\\unsupported\\" + directoryFiles[i].getName();
					moveFileTo(directoryFiles[i], destinationFilePath);
					this.sucessList[i] = false;
					
					continue;
				}
			}
			catch(NullPointerException e)
			{
				System.out.print("[Could not find orginal creation date]");
				// Move the file to a could not find creation data folder
				String destinationFilePath = "C:\\Users\\Mayuresh\\Pictures\\new\\FileRename\\errors\\date_not_found\\" + directoryFiles[i].getName();
				moveFileTo(directoryFiles[i], destinationFilePath);
				this.sucessList[i] = false;
			}
			catch(ImageProcessingException e)
			{
				System.out.print("[Cannot Process Image]");
				// Move the file to a could not find creation data folder
				String destinationFilePath = "C:\\Users\\Mayuresh\\Pictures\\new\\FileRename\\errors\\cannot_process\\" + directoryFiles[i].getName();
				moveFileTo(directoryFiles[i], destinationFilePath);
				this.sucessList[i] = false;
				
			}
			catch(Exception e)
			{
				System.out.print("[Something Went Wrong]");
				// Move the file to a could not find creation data folder
				String destinationFilePath = "C:\\Users\\Mayuresh\\Pictures\\new\\FileRename\\errors\\unknown_error\\" + directoryFiles[i].getName();
				moveFileTo(directoryFiles[i], destinationFilePath);
				this.sucessList[i] = false;
			}
		}
		
		System.out.println("\nRenaming Completed: (" + this.countSuccesses() + "/" + this.directoryFiles.length + ") SUCCESS");
	}
	
	private void moveFileTo(File fileToMove, String destinationFilePath)
	{
		System.out.print("[Moving File] -> ");
		boolean changed = fileToMove.renameTo(new File(destinationFilePath));
		String output = changed == true ? "success" : "failed";
		System.out.println("[" + output + "]");
	}
	
	private String getFileExtension(File file)
	{
		String fileName = file.getName();
		
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        {
        	return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        else
        {
        	return "";
        }
	}
	
	private String getDetectedFileType(Metadata metadata)
	{
		Directory d = metadata.getFirstDirectoryOfType(FileTypeDirectory.class);
		return d.getDescription(FileTypeDirectory.TAG_DETECTED_FILE_TYPE_NAME).toLowerCase();
	}
	
	public int countSuccesses()
	{
		int count = 0;
		for(int i = 0; i < this.sucessList.length; i++)
		{
			if(this.sucessList[i])
			{
				count++;
			}
		}
		return count;
	}
}
