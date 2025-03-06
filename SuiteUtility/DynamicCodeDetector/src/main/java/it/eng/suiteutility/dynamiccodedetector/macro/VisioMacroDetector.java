package it.eng.suiteutility.dynamiccodedetector.macro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.activation.MimeType;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.apache.poi.poifs.filesystem.Entry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetector;
import it.eng.suiteutility.dynamiccodedetector.DynamicCodeDetectorException;
import it.eng.suiteutility.dynamiccodedetector.ValidationInfos;

public class VisioMacroDetector implements DynamicCodeDetector {

	private static final String VISIO_DOCUMENT_ENTRY = "VisioDocument";
	private static final String VBA_OBJECT = "VBA";

	protected void containsMacro(File openOfficeFile, ValidationInfos validationInfos) throws IOException, DynamicCodeDetectorException {
		InputStream is = new FileInputStream(openOfficeFile);
		POIFSFileSystem poiFS = new POIFSFileSystem(is);
		DirectoryEntry dir = poiFS.getRoot();
		Entry entry = dir.getEntry(VISIO_DOCUMENT_ENTRY);
		if (entry == null || !(entry instanceof DocumentEntry))
			throw new DynamicCodeDetectorException(openOfficeFile + " OLE structure doesn't contain a valid Visio Document in the root");
		DocumentEntry visioDocument = (DocumentEntry) entry;
		DocumentInputStream documentIS = new DocumentInputStream(visioDocument);
		BufferedReader in = new BufferedReader(new InputStreamReader(documentIS));
		String line = null;
		while ((line = in.readLine()) != null) {
			if (line.contains(VBA_OBJECT)) {
				validationInfos.addWarning(openOfficeFile + " potentially contain macros");
				return;
			}
		}
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
