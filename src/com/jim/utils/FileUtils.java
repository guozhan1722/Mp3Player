package com.jim.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.os.Environment;

public class FileUtils
{
	private String SDCardRoot;

	public FileUtils()
	{
		SDCardRoot = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + File.separator;
	}

	// Create file on SDCard
	public File createFileInSDCard(String fileName, String dir)
					throws IOException
	{
		String pathFile = SDCardRoot + dir + fileName;
		System.out.println("create file --> " + pathFile);
		File file = new File(pathFile);
		System.out.println("Open file scriptor ");
		file.createNewFile();
		System.out.println("create new file scriptor ");
		return file;
	}

	public File createSDDir(String dir)
	{

		File createDir = Environment.getExternalStoragePublicDirectory(dir);
		try
		{
			createDir.mkdirs();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return createDir;
	}

	public boolean isFileExist(String fileName, String path)
	{
		File file = new File(SDCardRoot + path + File.separator + fileName);
		return file.exists();
	}

	public File write2SDFromInput(String path, String fileName,
					InputStream input)
	{
		File file = null;
		OutputStream output = null;

		try
		{
			createSDDir(path);
			System.out.println("Dir Created ");
			file = createFileInSDCard(fileName, path);
			System.out.println("File Created ");
			BufferedReader buffer1 = new BufferedReader(new InputStreamReader(
							input));
			System.out.println("get input stream from network "
							+ buffer1.readLine());

			output = new FileOutputStream(file);

			byte[] buffer = new byte[4 * 1024];
			int temp;
			while ((temp = input.read(buffer)) != -1)
			{
				output.write(buffer, 0, temp);
			}
			output.flush();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				output.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return file;
	}

}
