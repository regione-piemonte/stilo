/**
 * Copyright @ 2010 Quan Nguyen
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
package net.sourceforge.tess4j;

import it.eng.utility.FileUtil;
import it.eng.utility.conversion.PdfToImageUtil;
import it.eng.utility.ocr.OCRApi;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.IIOImage;

import net.sourceforge.vietocr.ImageIOHelper;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.lf5.util.StreamUtils;

/**
 * An object layer on top of <code>TessDllAPI</code>, provides character recognition support for common image formats, and multi-page TIFF
 * images beyond the uncompressed, binary TIFF format supported by Tesseract OCR engine. The extended capabilities are provided by the
 * <code>Java Advanced Imaging Image I/O Tools</code>. <br />
 * <br />
 * Support for PDF documents is available through <code>Ghost4J</code>, a <code>JNA</code> wrapper for <code>GPL Ghostscript</code>, which
 * should be installed and included in system path. <br />
 * <br />
 * Any program that uses the library will need to ensure that the required libraries (the <code>.jar</code> files for <code>jna</code>,
 * <code>jai-imageio</code>, and <code>ghost4j</code>) are in its compile and run-time <code>classpath</code>.
 */
public class Tesseract {

	private static Logger aLogger = Logger.getLogger(OCRApi.class.getName());

	private static Tesseract instance;

	private final static Rectangle EMPTY_RECTANGLE = new Rectangle();

	private String language = "ita";

	static Logger log = Logger.getLogger(Tesseract.class);

	/**
	 * Private constructor.
	 */
	private Tesseract() {
	}

	/**
	 * Gets an instance of the class library.
	 * 
	 * @return instance
	 */
	public static synchronized Tesseract getInstance() {
		if (instance == null) {
			instance = new Tesseract();
		}

		return instance;
	}

	/**
	 * Sets language for OCR.
	 * 
	 * @param language
	 *            the language code, which follows ISO 639-3 standard.
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	private String executableTesseract(File imageFile) throws TesseractException {
		List<File> files;
		List<File> tiffFromPdf = new ArrayList<File>();
		try {
			aLogger.debug("Inizio executableTesseract del file " + imageFile.getAbsolutePath() + "");

			try {
				if (imageFile.getName().toLowerCase().endsWith(".pdf")) {
					log.debug("il file è un pdf: lo converto in TIFF e faccio l'OCR");
					// convert PDF to TIFF
					// tiffFromPdf = PdfUtilities.convertPdf2Tiff(imageFile);
					tiffFromPdf = PdfToImageUtil.convertToTiff(imageFile.getAbsolutePath());
					aLogger.debug("Pages converted from pdf to tiff: " + tiffFromPdf.size());
					files = tiffFromPdf;
				} else {
					log.debug("creo una lista di TIFF da un file immagine");
					files = ImageIOHelper.createTiffFiles(imageFile, -1);
				}
				aLogger.debug("Created tiff files: ");
				for (File tiff : files) {
					aLogger.debug(tiff.getAbsolutePath() + (tiff.exists() ? "" : " (non esiste)"));
				}
			} catch (Throwable e) {
				throw e;
			}
			// NON SERVE CANCELLARLI PERCHE' LI CANCELLO ALLA FINE DELL'ELABORAZIONE UNO A UNO
			// } finally {
			// // cancello tutti i file temporanei creati come operazione finale
			// for (File tiffFile : tiffFromPdf) {
			// FileUtil.deleteFile(tiffFile);
			// }
			// }

			StringBuffer buffer = new StringBuffer();
			for (File tiff : files) {
				// Se sono windows a 64 bit effettuo la chiamata dalla command line
				File txt = null;
				try {
					// Effettuo la chimata a runtime
					String command = "tesseract \"" + tiff.getCanonicalPath() + "\" \"" + tiff.getCanonicalPath() + "\"";
					if (this.language != null) {
						command += " -l " + this.language;
					}
					log.debug("#% command da lanciare: " + command);
					String tmpcommand = "cmd /c " + command;
					log.debug("#% tmpcommand da lanciare: " + tmpcommand);
					Process proc = Runtime.getRuntime().exec(tmpcommand);
					aLogger.debug(tmpcommand);
					int ret = proc.waitFor();
					if (ret != 0) {
						// aLogger.debug(new String(StreamUtils.getBytes(proc.getErrorStream())));
						// aLogger.debug(decodeResultOfProcessWaitFor(ret));
						proc.destroy();
						// Provo a lanciare il processo senza il cmd /c
						Process proc2 = Runtime.getRuntime().exec(command);
						aLogger.debug(command);
						ret = proc2.waitFor();
						if (ret != 0) {
							aLogger.debug(new String(StreamUtils.getBytes(proc2.getErrorStream())));
							aLogger.debug(decodeResultOfProcessWaitFor(ret));
							proc2.destroy();
							// Provo a lanciare il processo con la variabile d'ambiente
							tmpcommand = System.getenv("TESSDATA_PREFIX") + command;
							log.debug("#% Lancio il comando con la variabile d'ambiente: " + tmpcommand);
							Process proc3 = Runtime.getRuntime().exec(tmpcommand);
							aLogger.debug(tmpcommand);
							ret = proc3.waitFor();
							if (ret != 0) {
								aLogger.debug(new String(StreamUtils.getBytes(proc3.getErrorStream())));
								aLogger.debug(decodeResultOfProcessWaitFor(ret));
								proc3.destroy();
								// Provo a lanciare il processo con la variabile d'ambiente
								tmpcommand = "cmd /c " + System.getenv("TESSDATA_PREFIX") + command;
								Process proc4 = Runtime.getRuntime().exec(tmpcommand);
								aLogger.debug(tmpcommand);
								ret = proc4.waitFor();
								if (ret != 0) {
									aLogger.debug(new String(StreamUtils.getBytes(proc4.getErrorStream())));
									aLogger.debug(decodeResultOfProcessWaitFor(ret));
									proc4.destroy();
									throw new Exception("Processo tesseract terminato erroneamente!");
								}
							}
						}
					}

					// Recupero il risultato
					txt = new File(tiff.getAbsolutePath() + ".txt");
					aLogger.debug("Created file " + txt.getAbsolutePath());
					if (txt.exists()) {
						buffer.append(FileUtils.readFileToString(txt));
					} else {
						throw new Exception("Il file di elaborazione di tesseract non è stato creato!");
					}

				} catch (Throwable e) {
					throw e;
				} finally {
					FileUtil.deleteFile(tiff);
					FileUtil.deleteFile(txt);
				}
			}
			aLogger.debug("Tutto OK");
			return buffer.toString();
		} catch (Throwable e) {
			aLogger.error("executableTesseract: " + e.getMessage(), e);
			throw new TesseractException(e);
		} finally {
			aLogger.debug("Fine executableTesseract");
		}
	}

	public String executableTesseract4Unix(File imageFile) throws TesseractException {
		List<File> files;
		List<File> tiffFromPdf = new ArrayList<File>();
		try {
			aLogger.debug("Inizio executableTesseract4Unix del file " + imageFile.getAbsolutePath() + "");

			try {
				if (imageFile.getName().toLowerCase().endsWith(".pdf")) {
					// convert PDF to TIFF
					// tiffFromPdf = PdfUtilities.convertPdf2Tiff(imageFile);
					tiffFromPdf = PdfToImageUtil.convertToTiff(imageFile.getAbsolutePath());
					aLogger.debug("Pages converted from pdf to tiff: " + tiffFromPdf.size());
					files = tiffFromPdf;
				} else {
					files = ImageIOHelper.createTiffFiles(imageFile, -1);
				}
				aLogger.debug("Created tiff files: ");

				for (File tiff : files) {
					aLogger.debug(tiff.getAbsolutePath() + (tiff.exists() ? "" : " (non esiste)"));
				}
			} catch (Throwable e) {
				throw e;
			} finally {
				// cancello tutti i file temporanei creati come operazione finale
				for (File tiffFile : tiffFromPdf) {
					FileUtil.deleteFile(tiffFile);
				}
			}

			StringBuffer buffer = new StringBuffer();
			List<String> cmd = new ArrayList<String>();
			cmd.add("tesseract");
			cmd.add(""); // placeholder for inputfile
			cmd.add(""); // placeholder for outputfile
			if (this.language != null) {
				cmd.add("-l");
				cmd.add(this.language);
			}
			for (File tiff : files) {
				File txt = null;
				ProcessBuilder pb = null;
				Process proc = null;
				try {
					pb = new ProcessBuilder();
					cmd.set(1, tiff.getCanonicalPath());
					cmd.set(2, tiff.getCanonicalPath());
					pb.command(cmd);
					pb.redirectErrorStream(true);
					proc = pb.start();
					Runtime.getRuntime().exec(cmd.toArray(new String[0]));
					int ret = proc.waitFor();
					if (ret != 0) {
						aLogger.debug(new String(StreamUtils.getBytes(proc.getErrorStream())));
						aLogger.debug(decodeResultOfProcessWaitFor(ret));
						proc.destroy();
						throw new Exception("Processo tesseract terminato erroneamente!");
					}
					// Recupero il risultato
					txt = new File(tiff.getAbsolutePath() + ".txt");
					if (txt.exists()) {
						buffer.append(FileUtils.readFileToString(txt));
					} else {
						throw new Exception("Il file di elaborazione di tesseract non è stato creato!");
					}
				} catch (Throwable e) {
					throw e;
				} finally {
					proc.destroy();
					FileUtil.deleteFile(tiff);
					FileUtil.deleteFile(txt);
				}
			}
			aLogger.debug("Tutto OK");
			return buffer.toString();
		} catch (Throwable e) {
			aLogger.error("executableTesseract4Unix: " + e.getMessage(), e);
			throw new TesseractException(e);
		} finally {
			aLogger.debug("Fine executableTesseract4Unix");
		}
	}

	private String decodeResultOfProcessWaitFor(int ret) {
		switch (ret) {
		case 1:
			return "Errors accessing files. There may be spaces in your image's filepath";
		case 29:
			return "Cannot recognize the image or its selected region.";
		case 31:
			return "Unsupported image format.";
		default:
			return "Errors occurred.";
		}
	}

	/**
	 * Performs OCR operation.
	 * 
	 * @param imageFile
	 *            an image file
	 * @return the recognized text
	 * @throws TesseractException
	 */
	public String doOCR(File imageFile) throws TesseractException {
		// Controllo il sistema operativo e i bit del processore
		if (getOS() == OperationSystem.UNIX) {
			return this.executableTesseract4Unix(imageFile);
		} else {
			try {
				return doOCR(imageFile, null);
			} catch (Throwable e) {
				aLogger.error(e.getMessage());
				return this.executableTesseract(imageFile);
			}
		}
	}

	/**
	 * Performs OCR operation.
	 * 
	 * @param imageFile
	 *            an image file
	 * @param rect
	 *            the bounding rectangle defines the region of the image to be recognized. A rectangle of zero dimension or
	 *            <code>null</code> indicates the whole image.
	 * @return the recognized text
	 * @throws TesseractException
	 */
	private String doOCR(File imageFile, Rectangle rect) throws TesseractException {
		try {
			return doOCR(ImageIOHelper.getIIOImageList(imageFile), rect);
		} catch (IOException ioe) {
			throw new TesseractException(ioe);
		}
	}

	/**
	 * Performs OCR operation.
	 * 
	 * @param bi
	 *            a buffered image
	 * @return the recognized text
	 * @throws TesseractException
	 */
	private String doOCR(BufferedImage bi) throws TesseractException {
		return doOCR(bi, null);
	}

	/**
	 * Performs OCR operation.
	 * 
	 * @param bi
	 *            a buffered image
	 * @param rect
	 *            the bounding rectangle defines the region of the image to be recognized. A rectangle of zero dimension or
	 *            <code>null</code> indicates the whole image.
	 * @return the recognized text
	 * @throws TesseractException
	 */
	private String doOCR(BufferedImage bi, Rectangle rect) throws TesseractException {
		IIOImage oimage = new IIOImage(bi, null, null);
		List<IIOImage> imageList = new ArrayList<IIOImage>();
		imageList.add(oimage);
		return doOCR(imageList, rect);
	}

	/**
	 * Performs OCR operation.
	 * 
	 * @param imageList
	 *            a list of <code>IIOImage</code> objects
	 * @param rect
	 *            the bounding rectangle defines the region of the image to be recognized. A rectangle of zero dimension or
	 *            <code>null</code> indicates the whole image.
	 * @return the recognized text
	 * @throws TesseractException
	 */
	private String doOCR(List<IIOImage> imageList, Rectangle rect) throws TesseractException {
		StringBuilder sb = new StringBuilder();
		for (IIOImage oimage : imageList) {
			try {
				ByteBuffer buf = ImageIOHelper.getImageByteBuffer(oimage);
				RenderedImage ri = oimage.getRenderedImage();
				String pageText = doOCR(ri.getWidth(), ri.getHeight(), buf, rect, ri.getColorModel().getPixelSize());
				sb.append(pageText);
			} catch (IOException ioe) {
				// skip the problematic image
				System.err.println(ioe.getMessage());
			}
		}

		return sb.toString();
	}

	/**
	 * Performs OCR operation.
	 * 
	 * @param xsize
	 *            width of image
	 * @param ysize
	 *            height of image
	 * @param buf
	 *            pixel data
	 * @param rect
	 *            the bounding rectangle defines the region of the image to be recognized. A rectangle of zero dimension or
	 *            <code>null</code> indicates the whole image.
	 * @param bpp
	 *            bits per pixel, represents the bit depth of the image, with 1 for binary bitmap, 8 for gray, and 24 for color RGB.
	 * @return the recognized text
	 * @throws TesseractException
	 */
	private String doOCR(int xsize, int ysize, ByteBuffer buf, Rectangle rect, int bpp) throws TesseractException {

		TessDllAPI api = TessDllAPI.INSTANCE;

		// System.setProperty("jna.library.path", "c:\\Windows\\System32");
		// TessDllAPI api = (TessDllAPI)Native.loadLibrary("tessdll_32", TessDllAPI.class);

		int resultRead = api.TessDllBeginPageUprightBPP(xsize, ysize, buf, language, (byte) bpp);

		if (resultRead == 0) {
			return null; // can't read image
		}

		ETEXT_DESC output;

		if (rect == null || rect.equals(EMPTY_RECTANGLE)) {
			// api.TessDllRelease();
			output = api.TessDllRecognize_all_Words();
		} else {
			// (left, right, top, bottom) specifies a region enclosing the text
			output = api.TessDllRecognize_a_Block(rect.x, rect.x + rect.width, rect.y, rect.y + rect.height);
		}

		final short count = output.count;

		EANYCODE_CHAR[] text = (EANYCODE_CHAR[]) output.text[0].toArray(count);

		List<Byte> unistr = new ArrayList<Byte>();

		int j = 0;

		for (int i = 0; i < count; i = j) {
			final EANYCODE_CHAR ch = text[i];

			for (int b = 0; b < ch.blanks; ++b) {
				unistr.add((byte) ' ');
			}

			for (j = i; j < count; j++) {
				final EANYCODE_CHAR unich = text[j];

				if (ch.left != unich.left || ch.right != unich.right || ch.top != unich.top || ch.bottom != unich.bottom) {
					break; // bytes making up the Unicode character have the same coordinates
				}
				unistr.add(unich.char_code); // aggregate all the utf-8 bytes of a character
			}

			if ((ch.formatting & 64) == 64) {
				// new line
				unistr.add((byte) '\n');
			} else if ((ch.formatting & 128) == 128) {
				// new paragraph
				unistr.add((byte) '\n');
				unistr.add((byte) '\n');
			}
		}
		try {
			return new String(wrapperListToByteArray(unistr), "utf8");
		} catch (UnsupportedEncodingException uee) {
			throw new TesseractException(uee);
		}

	}

	/**
	 * A utility method to convert a generic Byte list to a byte array.
	 * 
	 * @param list
	 *            a List<Byte>
	 * @return an array of bytes
	 */
	public static byte[] wrapperListToByteArray(List<Byte> list) {
		int size = list.size();
		byte[] byteArray = new byte[size];
		for (int i = 0; i < size; i++) {
			byteArray[i] = list.get(i);
		}
		return byteArray;
	}

	public static OperationSystem getOS() {
		OperationSystem sysop = OperationSystem.UNKNOWN;
		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("win") >= 0) {
			sysop = OperationSystem.WINDOWS;
		} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
			sysop = OperationSystem.UNIX;
		} else if (os.indexOf("mac") >= 0) {
			sysop = OperationSystem.MAC;
		}
		return sysop;
	}

	public static ProcessorBit getBitProcessor() {
		ProcessorBit bit = null;
		String probit = System.getProperty("sun.arch.data.model");
		if (probit.equals("64")) {
			bit = ProcessorBit._64;
		} else if (probit.equals("32")) {
			bit = ProcessorBit._32;
		}
		return bit;
	}

}