package it.eng.common;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FilenameUtils;

public class DllFilter extends FileFilter{

	@Override
	public boolean accept(File f) {
		return FilenameUtils.isExtension(f.getName().toUpperCase(), new String[]{"DLL"});
	}

	@Override
	public String getDescription() {
		return "Driver PKCS11 DLL";
	}

}
