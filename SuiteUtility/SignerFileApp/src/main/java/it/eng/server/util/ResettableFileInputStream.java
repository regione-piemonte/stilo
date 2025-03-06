package it.eng.server.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
 

public class ResettableFileInputStream extends InputStream
{
	RandomAccessFile raFile;
	long mark;
	public ResettableFileInputStream(File file) throws IOException 
	{
		this.raFile = new RandomAccessFile(file, "r");
	}
	public int read() throws IOException 
	{
		return raFile.read();
	}
	public synchronized void mark(int readlimit) 
	{
		try 
		{
			mark = readlimit;
		} 
		catch (Exception e) 
		{
				throw new RuntimeException(e);
		}
	}
	public synchronized void mark() 
	{
		try 
		{
			mark = raFile.getFilePointer();
		} 
		catch (Exception e) 
		{
				throw new RuntimeException(e);
		}
	}
	public boolean markSupported() 
	{
		return true;
	}
	public int read(byte[] b, int off, int len) throws IOException 
	{
		return raFile.read(b, off, len);
	}
	public int read(byte b[]) throws IOException
	{
		return raFile.read(b);
	}
	public synchronized void reset() throws IOException 
	{
		raFile.seek(this.mark);
	}
	public int available() throws IOException 
	{
		return (int) (raFile.length() - raFile.getFilePointer());
	}
	public void close() throws IOException 
	{
		raFile.close();
	}
	public long skip(long n) throws IOException 
	{
		long s = raFile.getFilePointer() + n;
		System.out.println("ResettableFileInputStream:: skip filePointer = " + s);
		if (raFile.getFilePointer() + n > raFile.length())
			n = raFile.length() - raFile.getFilePointer();
		raFile.seek(raFile.getFilePointer() + n);
		return n;
	}
}