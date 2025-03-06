/**  
 * Copyright @ 2009 Quan Nguyen  
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");  
 * you may not use this file except in compliance with the License.  
 * You may obtain a copy of the License at  
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0  
 *  
 * Unless required by applicable law or agreed to in writing, software  
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  
 * See the License for the specific language governing permissions and  
 * limitations under the License.  
 */ 
package net.sourceforge.vietocr;  

import it.eng.utility.FileUtil;

import java.io.ByteArrayOutputStream; 
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter; 
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList; 
import java.util.Arrays; 
import java.util.Comparator; 
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;  


//import net.sf.ghost4j.Ghostscript; 
//import net.sf.ghost4j.GhostscriptException;  
//import org.ghost4j.Ghostscript;
//import org.ghost4j.GhostscriptException;

/**
 * 
 * @author mzanin
 *
 */
public class PdfUtilities { 
	
	//public static final String GS_INSTALL = "\nPlease download, install GPL Ghostscript from http://sourceforge.net/projects/ghostscript/files\nand/or set the appropriate environment variable.";          
	
	/**          
	 * Convert PDF to TIFF format.          
	 *            
	 * @param inputPdfFile
	 * @return a multi-page TIFF image          
	 */         
//	public static File convertPdf2Tiff(final File inputPdfFile) throws IOException {                 
//		File[] pngFiles = null;          		
//		try {                         
//			pngFiles = convertPdf2Png(inputPdfFile);                         
//			final File tiffFile = File.createTempFile("multipage", ".tif", inputPdfFile.getParentFile());  			
//			// put PNG images into a single multi-page TIFF image for return                         
//			ImageIOHelper.mergeTiff(pngFiles, tiffFile);                         
//			return tiffFile;                 
//		} catch (final UnsatisfiedLinkError ule) {                         
//			throw new RuntimeException(getMessage(ule.getMessage()));                 
//		} catch (final NoClassDefFoundError ncdfe) {                         
//			throw new RuntimeException(getMessage(ncdfe.getMessage()));                 
//		} finally {                         
//			if (pngFiles != null) {                                 
//				// delete temporary PNG images                                 
//				for (final File tempFile : pngFiles) {                                         
//					FileUtil.deleteFile(tempFile);                                 
//				}                         
//			}                 		         
//		} 		
//	}
//	
//	/**          
//	 * Convert PDF to PNG format.          
//	 *            
//	 * @param inputPdfFile
//	 * @return an array of PNG images          
//	 */         
//	public static File[] convertPdf2Png(final File inputPdfFile) {     
//		
//		File imageDir = inputPdfFile.getParentFile();  
//		
//		if (imageDir == null) {                         
//			final String userDir = System.getProperty("user.dir");                         
//			imageDir = new File(userDir);                 
//		}
//		                  
//		// get Ghostscript instance                 
//		final Ghostscript gs = Ghostscript.getInstance(); 
//		
//		// prepare Ghostscript interpreter parameters                 
//		// refer to Ghostscript documentation for parameter usage                 
//		final List<String> gsArgs = new ArrayList<String>();                 
//		gsArgs.add("-gs");                 
//		gsArgs.add("-dNOPAUSE");                 
//		gsArgs.add("-dBATCH");                 
//		gsArgs.add("-dSAFER");                 
//		gsArgs.add("-sDEVICE=pnggray");                 
//		gsArgs.add("-r150");                 
//		gsArgs.add("-dGraphicsAlphaBits=4");                 
//		gsArgs.add("-dTextAlphaBits=4");                 
//		gsArgs.add("-sOutputFile=" + imageDir.getPath() + "/workingimage%03d.png");                 
//		gsArgs.add(inputPdfFile.getPath());    
//		
//		// execute and exit interpreter                 
//		try {                         
//			gs.initialize(gsArgs.toArray(new String[0]));                         
//			gs.exit();			
//		} catch (final GhostscriptException e) {                         
//			System.err.println("ERROR: " + e.getMessage());                 
//		} finally {
//			try { Ghostscript.deleteInstance(); } catch(Exception e) {}
//			System.gc();
//		}
//			
//		// find working files                 
//		final File[] workingFiles = imageDir.listFiles(new FilenameFilter() {                          
//			public boolean accept(final File dir, final String name) {                                 
//				return name.toLowerCase().matches("workingimage\\d{3}\\.png$");                         
//			}                 
//		});                  
//		
//		Arrays.sort(workingFiles, new Comparator<File>() {                          
//			public int compare(final File f1, final File f2) {                                 
//				return f1.getName().compareTo(f2.getName());                         
//			}                 
//		});                  
//			
//		return workingFiles;         		
//	}
//	
//    /**          
//     * Split PDF.          
//     *           
//     * @param inputPdfFile
//     * @param outputPdfFile
//     * @param firstPage
//     * @param lastPage
//     */
	
	
	public static File rewriteDocument(File filePdf, int pageNumber, File outputFile) throws Exception {
		
		OutputStream output = new FileOutputStream(outputFile);
		Document document = new Document();
		
		
		PdfWriter writer = PdfWriter.getInstance(document, output);
		writer.setTagged();
		writer.setLanguage("it");
		writer.setLinearPageMode();
		writer.createXmpMetadata();
        
		document.open();

		PdfContentByte cb = writer.getDirectContent();

		// Collego il reader al file
		PdfReader reader = new PdfReader(filePdf.getAbsolutePath());
		// Scorro le pagine da copiare
		rewritePage(filePdf, document, writer, cb, reader, pageNumber+1);
		
		output.flush();
		document.close();
		output.close();
		
		return outputFile;
	}
	
	private static void rewritePage(File filePdf, Document document, PdfWriter writer, PdfContentByte cb,
			PdfReader reader, int pageNumber) throws Exception {
		
		PdfImportedPage page = writer.getImportedPage(reader, pageNumber);

		Rectangle psize = reader.getPageSizeWithRotation(pageNumber);

		// Imposto il document in ladscape o portrait, a seconda della pagina
		if (psize.getWidth() > psize.getHeight()) {
			document.setPageSize(psize);
		} else {
			document.setPageSize(psize);
		}

		// Creo una nuova pagina nel document in cui copiare la pagina corrente
			document.newPage();
			switch (psize.getRotation()) {
			case 0:
				cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
				break;
			case 90:
				cb.addTemplate(page, 0, -1f, 1f, 0, 0, psize.getHeight());
				break;
			case 180:
				cb.addTemplate(page, -1f, 0, 0, -1f, psize.getWidth(), psize.getHeight());
				break;
			case 270:
				cb.addTemplate(page, 0, 1f, -1f, 0, psize.getWidth(), 0);
				break;
			default:
				break;
			}
		
	}
	
	
	public static List<File> splitPdf(final File pdfFile) throws Exception {
		List<File> files = new ArrayList<File>();
		
		 File tempPath = pdfFile.getParentFile();
	      PdfReader reader = new PdfReader(pdfFile.getAbsolutePath());
		
        for(int i=0; i<reader.getNumberOfPages();i++){
        	File outFile = new File(tempPath.getAbsolutePath() +"/"+String.format("%03d", i) + ".pdf");
        	File pageFile = rewriteDocument(pdfFile, i, outFile);
        	files.add(pageFile);
        }
		
            	
		return files;
	}
//		// get Ghostscript instance                 
//		final Ghostscript gs = Ghostscript.getInstance();        				
//		
//		// prepare Ghostscript interpreter parameters                 
//		// refer to Ghostscript documentation for parameter usage                 
//		// gs -sDEVICE=pdfwrite -dNOPAUSE -dQUIET -dBATCH -dFirstPage=m                 
//		// -dLastPage=n -sOutputFile=out.pdf in.pdf                 
//		final List<String> gsArgs = new ArrayList<String>();                 
//		gsArgs.add("-gs");                 
//		gsArgs.add("-dNOPAUSE");                 
//		gsArgs.add("-dQUIET");                 
//		gsArgs.add("-dBATCH");                 
//		gsArgs.add("-sDEVICE=pdfwrite");                  
//		
//		if (!firstPage.trim().isEmpty()) {                         
//			gsArgs.add("-dFirstPage=" + firstPage);                 
//		}                  
//		if (!lastPage.trim().isEmpty()) {                         
//			gsArgs.add("-dLastPage=" + lastPage);                 
//		}    
//		
//		gsArgs.add("-sOutputFile=" + outputPdfFile);                 
//		gsArgs.add(inputPdfFile);                  
//		
//		// execute and exit interpreter                 
//		try {                         
//			gs.initialize(gsArgs.toArray(new String[0]));                         
//			gs.exit();                 
//		} catch (final GhostscriptException e) {                         
//			System.err.println("ERROR: " + e.getMessage());                         
//			throw new RuntimeException(e.getMessage());                 
//		} catch (final UnsatisfiedLinkError ule) {                         
//			throw new RuntimeException(getMessage(ule.getMessage()));                 
//		} catch (final NoClassDefFoundError ncdfe) {                         
//			throw new RuntimeException(getMessage(ncdfe.getMessage()));                 
//		} finally {
//			try { Ghostscript.deleteInstance(); } catch(Exception e) {}
//			System.gc();
//		}    
//	}		
//		
//	/**          
//	 * Get PDF Page Count.          
//	 *           
//	 * @param inputPdfFile          
//	 * @return number of pages          
//	 */         
//	public static int getPdfPageCount(final String inputPdfFile) {                 
//		// get Ghostscript instance                 
//		final Ghostscript gs = Ghostscript.getInstance();        
//		
//		// prepare Ghostscript interpreter parameters                 
//		// refer to Ghostscript documentation for parameter usage                 
//		// gs -q -sPDFname=test.pdf pdfpagecount.ps                 
//		final List<String> gsArgs = new ArrayList<String>();                 
//		gsArgs.add("-gs");                 
//		gsArgs.add("-dNOPAUSE");                 
//		gsArgs.add("-dQUIET");                 
//		gsArgs.add("-dBATCH");                 
//		gsArgs.add("-sPDFname=" + inputPdfFile);                 
//		gsArgs.add("lib/pdfpagecount.ps");    
//		
//		int pageCount = 0;                 
//		ByteArrayOutputStream os = null;     
//		
//		// execute and exit interpreter                 
//		try {                         
//			// output                         
//			os = new ByteArrayOutputStream();                         
//			gs.setStdOut(os);                         
//			gs.initialize(gsArgs.toArray(new String[0]));                         
//			pageCount = Integer.parseInt(os.toString().replace("%%Pages: ", ""));                         
//			os.close();                 
//		} catch (final GhostscriptException e) {                         
//			System.err.println("ERROR: " + e.getMessage());                 
//		} catch (final Exception e) {                         
//			System.err.println("ERROR: " + e.getMessage());                 
//		} finally {
//			try { Ghostscript.deleteInstance(); } catch(Exception e) {}
//			System.gc();
//		}     
//		
//		return pageCount;         
//	}          	
//		
//	/**          
//	 * Merge PDF files.          
//	 *           
//	 * @param inputPdfFiles          
//	 * @param outputPdfFile          
//	 */         
//	public static void mergePdf(final File[] inputPdfFiles, final File outputPdfFile) {                 
//		// get Ghostscript instance                 
//		final Ghostscript gs = Ghostscript.getInstance();                  
//		
//		// prepare Ghostscript interpreter parameters                 
//		// refer to Ghostscript documentation for parameter usage                 
//		// gs -sDEVICE=pdfwrite -dNOPAUSE -dQUIET -dBATCH -sOutputFile=out.pdf                 
//		// in1.pdf in2.pdf in3.pdf                 
//		final List<String> gsArgs = new ArrayList<String>();                 
//		gsArgs.add("-gs");                 
//		gsArgs.add("-dNOPAUSE");                 
//		gsArgs.add("-dQUIET");                 
//		gsArgs.add("-dBATCH");                 
//		gsArgs.add("-sDEVICE=pdfwrite");                 
//		gsArgs.add("-sOutputFile=" + outputPdfFile.getPath()); 
//		
//		for (final File inputPdfFile : inputPdfFiles) {                         
//			gsArgs.add(inputPdfFile.getPath());                 
//		}                  
//		
//		// execute and exit interpreter                 
//		try {                         
//			gs.initialize(gsArgs.toArray(new String[0]));                         
//			gs.exit();                 
//		} catch (final GhostscriptException e) {                         
//			System.err.println("ERROR: " + e.getMessage());                         
//			throw new RuntimeException(e.getMessage());                 
//		} catch (final UnsatisfiedLinkError ule) {                         
//			throw new RuntimeException(getMessage(ule.getMessage()));                 
//		} catch (final NoClassDefFoundError ncdfe) {                         
//			throw new RuntimeException(getMessage(ncdfe.getMessage()));                 
//		} finally {
//			try { Ghostscript.deleteInstance(); } catch(Exception e) {}
//			System.gc();
//		}
//	}	
//
//	static String getMessage(final String message) {                 
//		if (message.contains("library 'gs") || message.contains("ghost4j")) {                         
//			return message + GS_INSTALL;                 
//		}                 
//		return message;         
//	} 
}