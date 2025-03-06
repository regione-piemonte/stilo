package it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors;

import it.eng.suiteutility.mimedetector.implementations.mimeutils.detectors.util.CharsetToolkit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;

import org.apache.commons.lang3.StringUtils;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.detector.MimeDetector;

public class TextPlainDetector extends MimeDetector {
		
	public String getDescription() {
		return "TextPlainDetector";
	}

	protected Collection getMimeTypesByteArray(byte[] arg0)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	protected Collection getMimeTypesFile(File file)throws UnsupportedOperationException {
		Collection coll = new ArrayList();
		try {
			FileReader fr = new FileReader(file);
			BufferedReader read = new BufferedReader(fr);
			//modifica: ora tutti i formati xml, html, htm ecc. sono trattati come plain text
			String line = read.readLine();
			
			boolean isRtf = false;
			if (line!=null && line.startsWith("{\\rtf") && line.endsWith("}")) {
				RTFEditorKit kit = new RTFEditorKit();
				Document doc = kit.createDefaultDocument();
				FileInputStream is = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				kit.read(br, doc, 0);
				String str = doc.getText(0, Math.min(10,doc.getLength()));
				if (str!=null && !"".equals(str)) {
					isRtf=true;
				}
			}
			
			if (isRtf) {
				MimeType mime = new MimeType("text/richtext");
				coll.add(mime);
			}
			else {
				Charset charset = CharsetToolkit.guessEncoding(file, 1024);
				String name = charset.displayName();
				if (StringUtils.contains(name, "UTF") || StringUtils.contains(name, "ASCII")  || StringUtils.contains(name,"windows-1252")) {
					MimeType mime = new MimeType("text/plain");
					coll.add(mime);
				}
			}
			read.close();
			fr.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return coll;
	}

	protected Collection getMimeTypesFileName(String arg0)
			throws UnsupportedOperationException {
		File file= new File(arg0);
		return getMimeTypesFile(file);
	}

	protected Collection getMimeTypesInputStream(InputStream arg0)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	protected Collection getMimeTypesURL(URL arg0)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

}
