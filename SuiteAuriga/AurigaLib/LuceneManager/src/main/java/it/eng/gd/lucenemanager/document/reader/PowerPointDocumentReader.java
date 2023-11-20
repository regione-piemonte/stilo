/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.HashSet;

import javax.persistence.Entity;

import org.apache.log4j.Logger;
//import org.apache.poi.hslf.HSLFSlideShow;
//import org.apache.poi.hslf.model.Notes;
//import org.apache.poi.hslf.model.Slide;
//import org.apache.poi.hslf.model.TextRun;
//import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xslf.usermodel.XSLFSlideShow;

import it.eng.gd.lucenemanager.bean.FileVO;
import it.eng.gd.lucenemanager.document.AbstractDocumentReader;

@Entity
public class PowerPointDocumentReader extends AbstractDocumentReader {

	static Logger aLogger = Logger.getLogger(PowerPointDocumentReader.class);

	public PowerPointDocumentReader() {
		super();
	}

	public Reader getContent(File file) throws Exception {
		Writer wr = null;
		BufferedWriter out = null;
		try {
			if (file == null)
				throw new Exception("Il file PowerPoint � nullo");
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
//			HSLFSlideShow ss = new HSLFSlideShow(fs);
//			SlideShow slideShow = new SlideShow(ss);
//			Slide _slides[] = slideShow.getSlides();
//			File fi = getRandomFile();
//			wr = new FileWriter(fi, true);
//			out = new BufferedWriter(wr);
//			for (int i = 0; i < _slides.length; i++) {
//				Slide slide = _slides[i];
//				TextRun[] runs = slide.getTextRuns();
//				for (int j = 0; j < runs.length; j++) {
//					TextRun run = runs[j];
//					if (run != null) {
//						String text = run.getText();
//						out.append(text);
//						if (!text.endsWith("\n")) {
//							out.append("\n");
//						}
//					}
//				}
//			}
			out.append(" ");
			// Not currently using _notes, as that can have the notes of
			// master sheets in. Grab Slide list, then work from there,
			// but ensure no duplicates
			HashSet<Integer> seenNotes = new HashSet<Integer>();
//			for (int i = 0; i < _slides.length; i++) {
//				Notes notes = _slides[i].getNotesSheet();
//				if (notes == null) {
//					continue;
//				}
//				Integer id = new Integer(notes._getSheetNumber());
//				if (seenNotes.contains(id)) {
//					continue;
//				}
//				seenNotes.add(id);
//
//				TextRun[] runs = notes.getTextRuns();
//				if (runs != null && runs.length > 0) {
//					for (int j = 0; j < runs.length; j++) {
//						TextRun run = runs[j];
//						String text = run.getText();
//						out.append(text);
//						if (!text.endsWith("\n")) {
//							out.append("\n");
//						}
//					}
//				}
//			}
			if (out != null)
				try {
					out.close();
				} catch (Exception e) {
				}
			return null;//new FSTemporaryFileReader(fi);
		} catch (OfficeXmlFileException e) {
			// si tratta di un docx e devo convertirlo come tale
			XSLFSlideShow ss = new XSLFSlideShow(file.getAbsolutePath());
			org.apache.poi.xslf.extractor.XSLFPowerPointExtractor ex = new org.apache.poi.xslf.extractor.XSLFPowerPointExtractor(ss);
			String str = ex.getText();
			return new StringReader(str); // FSTemporaryFileReader(new File(filename));
		} catch (Exception e) {
			aLogger.error(e.getMessage(), e);
			throw e;// new DocumentReaderException( "Errore nell'estrazione del testo dal documento Microsoft Excel", e);
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (Exception e) {
				}
			if (wr != null)
				try {
					wr.close();
				} catch (Exception e) {
				}
		}
	}

	@Override
	public FileVO[] getContents(File file, String mimetype) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
