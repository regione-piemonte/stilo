package it.eng.hybrid.module.wordOpener.fileProvider.impl;

import it.eng.hybrid.module.wordOpener.fileProvider.FileProvider;

import java.io.File;

import javax.swing.JApplet;

import org.apache.log4j.Logger;

public class FileSystemFileProvider implements FileProvider {

	private static Logger mLogger = Logger.getLogger(FileSystemFileProvider.class);
	
	@Override
	public File getFile() {
		mLogger.debug("Recupero un file locale");
		return new File("D:\\Open.doc");
	}

	@Override
	public void saveFileToServer(File pFile) {
		mLogger.debug("Salvo il file sul server");
	}

	@Override
	public String getArgFunction() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
