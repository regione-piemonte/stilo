/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.module.foutility.beans.OutputOperations;


import it.eng.module.foutility.beans.generated.AbstractInputOperationType;
import it.eng.module.foutility.beans.generated.InputConversionType;
import it.eng.module.foutility.beans.generated.ResponseFormatRecognitionType;
import it.eng.module.foutility.beans.generated.ResponsePdfConvResultType;
import it.eng.module.foutility.beans.generated.VerificationStatusType;
import it.eng.module.foutility.util.CheckPdfEditabili;
import it.eng.module.foutility.util.FileOpMessage;
import it.eng.module.foutility.util.InputFileUtil;
import it.eng.module.foutility.util.StaticizzazioneEsternaUtil;
import it.eng.suiteutility.module.mimedb.DaoAnagraficaFormatiDigitali;
import it.eng.suiteutility.module.mimedb.entity.TAnagFormatiDig;
import it.eng.suiteutility.module.mimedb.entity.TEstensioniFmtDig;
import it.eng.utility.cryptosigner.utils.MessageHelper;
import it.eng.utility.oomanager.OpenOfficeConverter;
import it.eng.utility.pdfUtility.pdfA.PdfAUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.MimeType;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfConvCtrl extends AbstractFileController {

	private static Logger log = LogManager.getLogger(PdfConvCtrl.class);

	public String operationType;

	public static final String CONVERTED_FILE = "CONVERTED_FILE";
	private List<String> convertibleMimetypes;
	private List<String> signConvertibleMimetypes;
	//private static DocumentFormatRegistry registry = new XmlDocumentFormatRegistry();

	@Override
	public boolean execute(InputFileBean input, AbstractInputOperationType customInput, 
			OutputOperations output, String requestKey) {
		boolean ret = false;
		log.debug( requestKey + " - Metodo execute di PdfConvCtrl - input " + input + " output "+ output);

		ResponsePdfConvResultType response = new ResponsePdfConvResultType();
		
		File file = null;
		File fileStaticizzato = output.getPropOfType(FormatRecognitionCtrl.FILE_STATICIZZATO, File.class);
		if (fileStaticizzato == null) {
			log.warn(requestKey + " - file fileStaticizzato non trovato, cerco il file privo di commenti");
			file = output.getPropOfType(FormatRecognitionCtrl.FILE_SENZA_COMMENTI, File.class);
			
			if (file == null) {
				log.warn(requestKey + " - file privo di commenti non trovato, cerco il file estratto");
				file = output.getPropOfType(UnpackCtrl.EXTRACTED_FILE, File.class);
				if (file == null) {
					log.warn(requestKey + " - file sbustato non trovato, uso il file di input");
					file = input.getInputFile();
					log.debug("Uso il file di input per la funzione di conversione");
				} else {
					log.debug("Uso il file sbustato per la funzione di conversione");
				}
			} else {
				log.debug("Uso il file senza commenti per la funzione di conversione");
			}
		} else {
			log.debug("Uso il file staticizzato per la funzione di conversione");
			file = fileStaticizzato;
		}
		log.info( requestKey + " - Elaborazione file " + file);

		// verifica il formato se e' ok per conversione
		int returnFormat = validFormat(customInput, output);
		// il file non è convertibile
		try {
			
			
			if (returnFormat == 0) {
				log.info("Formato non convertibile");
				OutputOperations.addError(response, MessageHelper.getMessage(FileOpMessage.PDF_OP_NO_CONVERTIBLE_FORMAT), VerificationStatusType.ERROR);
				output.addResult(this.getClass().getName(), response);
				return false;
			}

			// il file e' gia'è pdf
			if (returnFormat == 2) {
				log.info("Il file e' gia' pdf, non necessita di conversione");
				response.setVerificationStatus(VerificationStatusType.OK);
				output.addResult(this.getClass().getName(), response);
				output.setFileResult(file);
				return true;
			}

			// il file non e' convertibile per firma
			if (returnFormat == 3) {
				log.info("Formato non convertibile per firma");
				OutputOperations.addError(response, MessageHelper.getMessage(FileOpMessage.PDF_OP_NO_CONVERTIBLE_SIGN_FORMAT), VerificationStatusType.ERROR);
				output.addResult(this.getClass().getName(), response);
				return false;
			}
			
			// il file contiene xform
			if (returnFormat == 4) {
				// verifico se e' attivo il servizio esterno di staticizzazione
				boolean isIntegrazioneServizioStaticizzazioneAttiva = StaticizzazioneEsternaUtil.isAttivaIntegrazioneServizioStaticizzazione();
				log.debug(requestKey + " - Integrazione servizio staticizzazione attiva? " + isIntegrazioneServizioStaticizzazioneAttiva);
				if( isIntegrazioneServizioStaticizzazioneAttiva ){
					//richiamo il servizio
					File fileStatizzatoEsternamente=StaticizzazioneEsternaUtil.richiamaServizio(file);
					
					if( fileStatizzatoEsternamente!=null ){
						log.info("Il file ora è un pdf, non necessita di conversione");
						response.setVerificationStatus(VerificationStatusType.OK);
						output.addResult(this.getClass().getName(), response);
						output.setFileResult(fileStatizzatoEsternamente);
						output.addProperty(FormatRecognitionCtrl.FILE_STATICIZZATO, fileStatizzatoEsternamente );
						
						MimeType mimeTypeStaticizzato = new MimeType();
						mimeTypeStaticizzato.setSubType("pdf");
						mimeTypeStaticizzato.setPrimaryType("application");
							
						log.debug(requestKey + " memorizzo il mime " + mimeTypeStaticizzato + " per i controller successivi");
						// memorizzo il mime rilevato per controller successivi
						output.addProperty(FormatRecognitionCtrl.DETECTED_MIME, mimeTypeStaticizzato);
						output.addProperty(FormatRecognitionCtrl.DETECTED_MIME_STRING, mimeTypeStaticizzato.toString());
							
						return true;
					} else {
						log.info("Formato non convertibile pdf editabile con xform");
						OutputOperations.addError(response, MessageHelper.getMessage(FileOpMessage.PDF_OP_NO_CONVERTIBLE_XFORM), VerificationStatusType.ERROR);
						output.addResult(this.getClass().getName(), response);
						return false;
					}
					
				} else {
					log.info("Formato non convertibile pdf editabile con xform");
					OutputOperations.addError(response, MessageHelper.getMessage(FileOpMessage.PDF_OP_NO_CONVERTIBLE_XFORM), VerificationStatusType.ERROR);
					output.addResult(this.getClass().getName(), response);
					return false;
				}
			}
			
			String fileName = null;
			try {
				fileName = InputFileUtil.getTempFileName(input);
				log.debug(requestKey + " - fileName " + fileName);
			} catch (Exception e1) {
				log.error("", e1);
			}

			File filePdf = null;
			boolean fileTempCreated = false;
			try {
				filePdf = File.createTempFile("conv", ".pdf", input.getTemporaryDir());
				fileTempCreated = true;
			} catch (IOException e1) {
				log.error("Errore nella creazione del file temporaneo");
				OutputOperations.addError(response, FileOpMessage.PDF_OP_ERROR, VerificationStatusType.ERROR, e1.getMessage());
			}
			if (fileTempCreated) {
				// try {
				//DocumentFormat pdfFormat = registry.getFormatByFileExtension("pdf");
				
				String mimeType = "";
				if (!StringUtils.isBlank(fileName) && fileName.contains(".")) {
					String nomeFilePassato = fileName;
					String estensione = nomeFilePassato.substring(nomeFilePassato.lastIndexOf(".") + 1, nomeFilePassato.length());
					
					DaoAnagraficaFormatiDigitali dao = new DaoAnagraficaFormatiDigitali();
					TAnagFormatiDig formato = dao.findFormatByMimeType("text/plain");
					List<String> listaEstensioniTxt = new ArrayList<String>();
					if( formato!=null ){
						Set<TEstensioniFmtDig> estensioniTxt = formato.getTEstensioniFmtDigs();
						Iterator<TEstensioniFmtDig> estensioniTxtItr = estensioniTxt.iterator();
						while( estensioniTxtItr.hasNext() ){
							TEstensioniFmtDig estensioneTxt = estensioniTxtItr.next();
							//log.info("---- " + estensioneTxt.getId().getEstensione());
							listaEstensioniTxt.add( estensioneTxt.getId().getEstensione() );
						}
					}
					
					if( estensione!=null && listaEstensioniTxt.contains(estensione)){
						log.debug("L'estensione ricevuta in input " + estensione + " e' una di quelle censite per il fomato text/plain");
						TAnagFormatiDig formatoEstensione = dao.findFormatByExtPrincipale(estensione);
						mimeType = formatoEstensione.getMimetypePrincipale();
						log.debug("mimeType " + mimeType);
						File newFile = new File(file.getAbsolutePath() + "." + estensione );
						boolean resRen = file.renameTo(newFile);
						if( resRen ){
							file = newFile;
						} else {
							FileUtils.copyFile(file, newFile);
							file = newFile;
						}
						log.debug("Elaboro il file " + file);
					}
				}
				
				if( mimeType.equalsIgnoreCase("")){
					//ResponseFormatRecognitionType responseFormat = (ResponseFormatRecognitionType) output.getResult(FormatRecognitionCtrl.class.getName());
					//mimeType = responseFormat.getMimeType();
					MimeType mimeTypeDetected = output.getPropOfType( FormatRecognitionCtrl.DETECTED_MIME, MimeType.class);
					log.debug("mimeType ricavato per il file" + mimeType);
					mimeType = mimeTypeDetected.getBaseType();
				}
				log.info("mimeType: " + mimeType );
				if (mimeType.equalsIgnoreCase("application/pdf")) {
					filePdf = file;
				} else if (mimeType.equalsIgnoreCase("image/tiff")) {
					// Nei tiff non posso usare OpenOfficeConverter, uso iText
					convertMultipageTiffToPdf(file, filePdf, pdfaRequestes(customInput));
				} else {
					//log.info("ricavo il DocumentFormat dal mime " + mimeType);
					//DocumentFormat inputDocumentFormat = registry.getFormatByMimeType(mimeType);
					log.info("pdfaRequestes(customInput):: " + pdfaRequestes(customInput) );
					log.info(requestKey + " - Chiamo metodo conversione " + filePdf);
					if (pdfaRequestes(customInput)) {
						OpenOfficeConverter.newInstance().convertByOutExt(file, mimeType, filePdf, "pdf"/*buildPdfAFormat()*/);
					} else {
						OpenOfficeConverter.newInstance().convertByOutExt(file, mimeType, filePdf, "pdf");
					}
				}

				if ((customInput != null) && (customInput instanceof InputConversionType) && (((InputConversionType) customInput).isPdfA() != null)
						&& ((InputConversionType) customInput).isPdfA()) {
					log.info("Conversione in formato pdfA/3-U");

					File outputPdf3U = PdfAUtil.convertToPdfA3U(filePdf, input.getTemporaryDir());
					log.info("outputPdf3U " + outputPdf3U);

					output.addProperty(CONVERTED_FILE, outputPdf3U);
					output.setFileResult(outputPdf3U);
				} else {
					log.info(requestKey + " - File convertito " + filePdf);
					output.addProperty(CONVERTED_FILE, filePdf);
					output.setFileResult(filePdf);
				}
				response.setVerificationStatus(VerificationStatusType.OK);

				ret = true;

				// if(filePdf != null) {
				// boolean esito = filePdf.delete();
				// System.out.println("esito " + esito);
				// }
				//
				// log.info("Conversione completata, pdf originale rimosso");

				/*
				 * } catch (OpenOfficeException e) { log.fatal("fatal during conversion",e); OutputOperations.addError(response, FileOpMessage.PDF_OP_ERROR,
				 * VerificationStatusType.KO, e.getMessage() ); }
				 */
			}
			output.addResult(this.getClass().getName(), response);
			return ret;
		} catch (Throwable e) {
			log.error("Errore in fase di conversione " + e.getMessage(), e);
			OutputOperations.addError(response, FileOpMessage.GM_GENERIC_ERROR, VerificationStatusType.KO, e.getMessage());
			return ret;
		}
	}

	// public static void convertWordToPdf(String src, String desc){
	// try{
	// //create file inputstream object to read data from file
	// FileInputStream fs=new FileInputStream(src);
	// //create document object to wrap the file inputstream object
	// XWPFDocument doc=new XWPFDocument(fs);
	// //72 units=1 inch
	// Document pdfdoc=new Document(PageSize.A4,72,72,72,72);
	// //create a pdf writer object to write text to mypdf.pdf file
	// PdfWriter pwriter=PdfWriter.getInstance(pdfdoc, new FileOutputStream(desc));
	// //specify the vertical space between the lines of text
	// pwriter.setInitialLeading(20);
	// //get all paragraphs from word docx
	// List<XWPFParagraph> plist=doc.getParagraphs();
	//
	// //open pdf document for writing
	// pdfdoc.open();
	// for (int i = 0; i < plist.size(); i++) {
	// //read through the list of paragraphs
	// XWPFParagraph pa = plist.get(i);
	// //get all run objects from each paragraph
	// List<XWPFRun> runs = pa.getRuns();
	// //read through the run objects
	// for (int j = 0; j < runs.size(); j++) {
	// XWPFRun run=runs.get(j);
	// //get pictures from the run and add them to the pdf document
	// List<XWPFPicture> piclist=run.getEmbeddedPictures();
	// //traverse through the list and write each image to a file
	// Iterator<XWPFPicture> iterator=piclist.iterator();
	// while(iterator.hasNext()){
	// XWPFPicture pic=iterator.next();
	// XWPFPictureData picdata=pic.getPictureData();
	// byte[] bytepic=picdata.getData();
	// Image imag=Image.getInstance(bytepic);
	// pdfdoc.add(imag);
	//
	// }
	// //get color code
	// int color=getCode( run.get);//int color=getCode("000000");
	// //construct font object
	// Font f=null;
	// if(run.isBold() && run.isItalic())
	// f=FontFactory.getFont(FontFactory.TIMES_ROMAN,run.getFontSize(),Font.BOLDITALIC, new BaseColor(color));
	// else if(run.isBold())
	// f=FontFactory.getFont(FontFactory.TIMES_ROMAN,run.getFontSize(),Font.BOLD, new BaseColor(color));
	// else if(run.isItalic())
	// f=FontFactory.getFont(FontFactory.TIMES_ROMAN,run.getFontSize(),Font.ITALIC, new BaseColor(color));
	// else if(run.isStrike())
	// f=FontFactory.getFont(FontFactory.TIMES_ROMAN,run.getFontSize(),Font.STRIKETHRU, new BaseColor(color));
	// else
	// f=FontFactory.getFont(FontFactory.TIMES_ROMAN,run.getFontSize(),Font.NORMAL, new BaseColor(color));
	// //construct unicode string
	// String text=run.getText(-1);
	// byte[] bs;
	// if (text!=null){
	// bs=text.getBytes();
	// String str=new String(bs,"UTF-8");
	// //add string to the pdf document
	// Chunk chObj1=new Chunk(str,f);
	// pdfdoc.add(chObj1);
	// }
	//
	// }
	// //output new line
	// pdfdoc.add(new Chunk(Chunk.NEWLINE));
	// }
	// //close pdf document
	// pdfdoc.close();
	// }catch(Exception e){e.printStackTrace();}
	// }
	//
	// public static int getCode(String code){
	// int colorCode;
	// if(code!=null)
	// colorCode=Long.decode("0x"+code).intValue();
	// else
	// colorCode=Long.decode("0x000000").intValue();
	// return colorCode;
	// }
	//
	// public static void main(String[] args) {
	// PdfConvCtrl ctr = new PdfConvCtrl();
	// String inputFile = "C:/Users/Anna Tesauro/Desktop/docx1952737290227208808.docx";
	// String outputFile = "C:/Users/Anna Tesauro/Desktop/file.pdf";
	// ctr.convertWordToPdf(inputFile, outputFile);
	// }

	private boolean pdfaRequestes(AbstractInputOperationType customInput) {
		boolean ret = false;
		if (customInput != null && customInput instanceof InputConversionType) {
			Boolean temp = ((InputConversionType) customInput).isPdfA();
			if (temp != null)
				ret = temp;
		}
		return ret;
	}

	/**
	 * valida il mimetype riconosciuto con la conf dei formati ammessi epr la conversione
	 * 
	 * @param output
	 * @return
	 */
	private int validFormat(AbstractInputOperationType customInput, OutputOperations output) {
		int ret = 0;
		String mimeType = null;
		// verifica che il controllo formato e' stato eseguito
		// se il file e' firmato si prende il mime dello sbustato
		/*ResponseFormatRecognitionType responseFormat = (ResponseFormatRecognitionType) output.getResult(FormatRecognitionCtrl.class.getName());
		if (responseFormat != null && responseFormat.getMimeType() != null) {
			mimeType = responseFormat.getMimeType();
		}*/
		String mimeTypeDetected = (String) output.getProperty( FormatRecognitionCtrl.DETECTED_MIME_STRING );
		log.debug("mimeType ricavato per il file " + mimeTypeDetected);
		if( mimeTypeDetected!=null)
			mimeType = mimeTypeDetected;
		
		//0 Formato non convertibile
		//1 Formato convertibile
		//2 Il file e' gia' pdf, non necessita di conversione
		//3 Formato non convertibile per firma
		//4 Formato non convertibile pdf editabile con xform
		
		if ("application/pdf".equalsIgnoreCase(mimeType) ) {
			//se si arriva nella funzione da un'altra che dipende da questa (timbro per esempio) la conversione non è in pdfA per default
			if (customInput != null && customInput instanceof InputConversionType) {
				Boolean pdfA = ((InputConversionType) customInput).isPdfA();
				if( pdfA==null || !pdfA){
					ret = 2;
				} else 
					ret = 1;
			} else {
				ret = 2;
			}
		} else if ("application/pdfe".equalsIgnoreCase(mimeType) ) {
			//se si arriva nella funzione da un'altra che dipende da questa (timbro per esempio) la conversione non è in pdfA per default
			if (customInput != null && customInput instanceof InputConversionType) {
				Boolean pdfA = ((InputConversionType) customInput).isPdfA();
				if( pdfA==null || !pdfA){
					ret = 4;
				} else 
					ret = 4;
			} else {
				ret = 4;
			}
		} else {
			log.debug("verifica convertibilita' del formato " + mimeType);
			if (customInput != null && customInput instanceof InputConversionType) {
				// verifico se e' richiesta una conversione standard o per firma
				Boolean signConversion = ((InputConversionType) customInput).isSignConversion();
				// il default della conversione per firma e false quindi eseguoo il controllo tra i
				// tipi convertibili per firma solo se il parametro è stato specificato ed � pari a true
				if (signConversion != null && signConversion) {
					log.info("E' stata richiesta una conversione per firma ");
					if (signConvertibleMimetypes != null && signConvertibleMimetypes.size() > 0) {
						ret = (signConvertibleMimetypes.contains(mimeType)) ? 1 : 3;
					}
				} else {
					log.info("E' stata richiesta una conversione standard ");
					if (convertibleMimetypes != null && convertibleMimetypes.size() > 0) {
						ret = (convertibleMimetypes.contains(mimeType)) ? 1 : 0;
					}
				}
			} else {
				log.info("E' stata richiesta una conversione standard ");
				if (convertibleMimetypes != null && convertibleMimetypes.size() > 0) {
					ret = (convertibleMimetypes.contains(mimeType)) ? 1 : 0;
				}
			}
		}
		log.debug("validFormat " + ret);
		return ret;
	}

	/**
	 * costruisce il formato PDF/A in modo da richiedere la conversione nel formato specificato
	 * 
	 * @return
	 */
	/*private DocumentFormat buildPdfAFormat() {
		DocumentFormat outputDocumentFormat = new DocumentFormat("Portable Document Format", "application/pdf", "pdf");
		outputDocumentFormat.setExportFilter(DocumentFamily.TEXT, "writer_pdf_Export");
		// outputDocumentFormat.setExportFilter(DocumentFamily.TEXT, "filterName");
		Map<String, Object> pdfFilterData = new HashMap<String, Object>();
		pdfFilterData.put("SelectPdfVersion", 1);
		outputDocumentFormat.setExportOption(DocumentFamily.TEXT, "FilterData", pdfFilterData);

		outputDocumentFormat.setExportFilter(DocumentFamily.SPREADSHEET, "calc_pdf_Export");
		outputDocumentFormat.setExportOption(DocumentFamily.SPREADSHEET, "FilterData", pdfFilterData);

		outputDocumentFormat.setExportFilter(DocumentFamily.DRAWING, "draw_pdf_Export");
		outputDocumentFormat.setExportOption(DocumentFamily.DRAWING, "FilterData", pdfFilterData);

		outputDocumentFormat.setExportFilter(DocumentFamily.PRESENTATION, "impress_pdf_Export");
		outputDocumentFormat.setExportOption(DocumentFamily.PRESENTATION, "FilterData", pdfFilterData);
		return outputDocumentFormat;
	}*/

	// Aggiunto da Federico Cacco in data 08-10-2015
	// Consente la timbratura su file pdf, manca la gestione del formato pdfA (viene prodotto un pdf normale)
	/**
	 * Esegue la conversione din un file da formato TIFF a PDF
	 * 
	 * @param tiffInputFile
	 *            File tiff da convertire
	 * @param pdfOutputFile
	 *            File pdf in cui salvare la conversione
	 * @param convertToPdfa
	 *            Flag che indica se la conversione deve essere fatta nel formato pdfa (ignorato)
	 * @throws IOException
	 * @throws DocumentException
	 */
	private void convertMultipageTiffToPdf(File tiffInputFile, File pdfOutputFile, boolean convertToPdfa) throws IOException, DocumentException {
		// Creo lo stream
		InputStream tiffStream = new FileInputStream(tiffInputFile);
		ImageInputStream is = ImageIO.createImageInputStream(tiffStream);
		// Cerco i plugin supportati
		ImageIO.scanForPlugins();
		// Verifico la presenza di un plugin per la conversione da tiff a pdf
		Iterator<ImageReader> imageReaders = ImageIO.getImageReadersByFormatName("TIFF");
		if (!imageReaders.hasNext()) {
			throw new UnsupportedOperationException("No TIFF Reader found!");
		}
		ImageReader reader = imageReaders.next();
		// Selezioono il plugin per la conversione
		reader.setInput(is);
		Document doc = new Document(PageSize.A4, 0, 0, 0, 0);
		OutputStream outputStream = new FileOutputStream(pdfOutputFile);

		// PdfWriter.getInstance(doc, outputStream);
		// Verifico se devo convertire in pdfa
		if (convertToPdfa) {
			// TODO: Fare la convesione in pdfa, ora genera un pdf normale
			// PdfAWriter writer = PdfAWriter.getInstance(doc, outputStream, PdfAConformanceLevel.PDF_A_3B);
			// writer.setTagged();
			// writer.setLanguage("it");
			// writer.setLinearPageMode();
			// writer.createXmpMetadata();
			PdfWriter.getInstance(doc, outputStream);
		} else {
			PdfWriter.getInstance(doc, outputStream);
		}

		doc.open();
		int pages = reader.getNumImages(true);
		// Inserisco le pagine tiff nel documento pdf
		for (int imageIndex = 0; imageIndex < pages; imageIndex++) {
			BufferedImage bufferedImage = reader.read(imageIndex);
			Image image = Image.getInstance(bufferedImage, null);
			image.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
			doc.add(image);
		}
		// Chiudo doc e stream
		doc.close();
		outputStream.close();
		if( is!=null ){
			is.close();
		}
		if( tiffStream!=null ){
			tiffStream.close();
		}
	}

	public List<String> getConvertibleMimetypes() {
		return convertibleMimetypes;
	}

	public void setConvertibleMimetypes(List<String> convertibleMimetypes) {
		this.convertibleMimetypes = convertibleMimetypes;
	}

	public List<String> getSignConvertibleMimetypes() {
		return signConvertibleMimetypes;
	}

	public void setSignConvertibleMimetypes(List<String> signConvertibleMimetypes) {
		this.signConvertibleMimetypes = signConvertibleMimetypes;
	}

	@Override
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
}
