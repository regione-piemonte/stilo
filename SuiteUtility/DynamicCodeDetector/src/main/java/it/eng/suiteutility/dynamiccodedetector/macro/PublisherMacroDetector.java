package it.eng.suiteutility.dynamiccodedetector.macro;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.activation.MimeType;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.Entry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetector;
import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetectorException;
import it.eng.suiteutility.dynamiccodedetector.ValidationInfos;

/*
 * TODO: attualmente il detector si limita a verificare la presenza di script VBA all'interno del progetto, occorrerebbe iterare l'analisi anche sugli oggetti
 * in esso riferiti
 */
public class PublisherMacroDetector implements DynamicCodeDetector {

	private static final String MACRO_DIRECTORY_ENTRY = "VBA";
	private static final String MACRO_DEFINITION_ENTRY = "_VBA_PROJECT";

	private boolean recursiveParseDirectoryEntry(DirectoryEntry directoryEntry) throws IOException {
		for (Iterator<Entry> iter = directoryEntry.getEntries(); iter.hasNext();) {
			Entry entry = (Entry) iter.next();
			if (entry instanceof DirectoryEntry) {
				DirectoryEntry newDirectoryEntry = (DirectoryEntry) entry;
				if (recursiveParseDirectoryEntry(newDirectoryEntry))
					return true;
			} else if (entry instanceof DocumentEntry) {
				DocumentEntry documentEntry = (DocumentEntry) entry;
				String entryName = documentEntry.getName();
				if (MACRO_DEFINITION_ENTRY.equals(entryName)) {
					return true;
				}
			}
		}
		return false;
	}

	protected void containsMacro(File openOfficeFile, ValidationInfos validationInfos) throws IOException, DynamicCodeDetectorException {
		InputStream is = new FileInputStream(openOfficeFile);
		POIFSFileSystem poiFS = new POIFSFileSystem(is);
		DirectoryEntry dir = poiFS.getRoot();
		Entry macroDir = dir.getEntry(MACRO_DIRECTORY_ENTRY);
		if (macroDir == null || !(macroDir instanceof DocumentEntry))
			throw new DynamicCodeDetectorException(openOfficeFile + " OLE structure doesn't contain a valid Publisher Document in the root");

		if (recursiveParseDirectoryEntry(dir))
			validationInfos.addError(openOfficeFile + " contains VBA macros");
	}

	public ValidationInfos detect(File file, MimeType mimeType) throws DynamicCodeDetectorException {
		ValidationInfos validationInfos = new ValidationInfos();
		try {
			containsMacro(file, validationInfos);
		} catch (IOException e) {
			throw new DynamicCodeDetectorException(e.getMessage(), e);
		}
		return validationInfos;
	}

}
