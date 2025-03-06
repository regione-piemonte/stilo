package it.eng.client.applet.operation.detector;

  

import java.io.File;
import java.io.IOException;
import java.io.PipedOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class CopyThread extends Thread{

	private File file = null;
	private PipedOutputStream out;
	
	public CopyThread(File file,PipedOutputStream out) {
		this.file = file;
		this.out = out;
	}
	
	
	@Override
	public void run() {
		try {
			IOUtils.copyLarge(FileUtils.openInputStream(file), out);
		} catch (Exception e) {	
			e.printStackTrace();
		} 
		 finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			FileUtils.deleteQuietly(file);			
		}
	}

}
