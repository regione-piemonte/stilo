package it.eng.hybrid.module.stampaEtichette.util;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeDatamatrix;
import com.itextpdf.text.pdf.BarcodePDF417;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfReader;

import it.eng.hybrid.module.stampaEtichette.bean.ParameterBean;
import it.eng.hybrid.module.stampaEtichette.bean.PdfPropertiesBean;
import it.eng.hybrid.module.stampaEtichette.bean.PdfRigaBean;
import it.eng.hybrid.module.stampaEtichette.bean.TestoBarcodeBean;
import it.eng.hybrid.module.stampaEtichette.config.ManagerConfiguration;

public class PdfWriter {

	public final static Logger logger = Logger.getLogger(PdfWriter.class);

	private static String pdfFileName = "out.pdf";
	
	public static String writePdf(PdfPropertiesBean pdfPropertiesBean, ParameterBean pParameterBean) throws Exception {
		
		// Creo i nuovi file ed eliminando quelli precedenti
		File[] lFiles = new File(ManagerConfiguration.userPrefDirPath + File.separator).listFiles();
		for (File lFile : lFiles) {
			if (!lFile.getName().equals(pdfFileName)) {
				lFile.delete();
			}
		}
					
		File pdffile = new File(ManagerConfiguration.userPrefDirPath + File.separator + pdfFileName);
		return writePdf(pdfPropertiesBean, pParameterBean, pdffile);
	}

	public static String writePdf(PdfPropertiesBean pdfPropertiesBean, ParameterBean pParameterBean, File pdffile) throws Exception {
		logger.debug("Entro in writePdf");
		try {
			if (!pdffile.exists()) {
				pdffile.createNewFile();
			}

			// Controllo pageWidth
			String pageWidthString = "4.0cm";
			logger.debug("PageWidth vale " + pdfPropertiesBean.getPageWidth());
			if (pdfPropertiesBean.getPageWidth() != null) {
				pageWidthString = pdfPropertiesBean.getPageWidth();
			}
			float pdfW = CommonUtils.convertLengthMeasurementToMm(pageWidthString);
			logger.debug("pdfW mm: " + pdfW);
			pdfW = Utilities.millimetersToPoints(pdfW);
			logger.debug("pdfW: " + pdfW);
			
			// Controllo pageHeight
			String pageHeightString = "2.0cm";
			logger.debug("PageHeight vale " + pdfPropertiesBean.getPageHeight());
			if (pdfPropertiesBean.getPageHeight() != null) {
				pageHeightString = pdfPropertiesBean.getPageHeight();
			}						
			float pdfH = CommonUtils.convertLengthMeasurementToMm(pageHeightString);
			logger.debug("pdfH mm: " + pdfH);
			pdfH = Utilities.millimetersToPoints(pdfH);
			logger.debug("pdfH: " + pdfH);

			Document document = new Document();
			
			// Azzero i margini del documento, per facilitare la gestione delgi spazi tra paragrafi
			// Controllo margin
			String marginString = "0.0cm";
			logger.debug("Margin vale " + pdfPropertiesBean.getMargin());
			if (pdfPropertiesBean.getMargin() != null) {
				marginString = pdfPropertiesBean.getMargin();
			}
			float margin = CommonUtils.convertLengthMeasurementToMm(marginString);
			logger.debug("margin mm: " + margin);
			margin = Utilities.millimetersToPoints(margin);
			logger.debug("margin: " + margin);
			document.setMargins(margin, margin, margin, margin);

			FileOutputStream fos = new FileOutputStream(pdffile);
			com.itextpdf.text.pdf.PdfWriter writer = com.itextpdf.text.pdf.PdfWriter.getInstance(document, fos);
			DirectionEvent directionEvent = new DirectionEvent();
			writer.setPageEvent(directionEvent);
			
			directionEvent.setOrientation(PdfPage.PORTRAIT);
			directionEvent.setRotation(PdfPage.PORTRAIT);

			List<TestoBarcodeBean> listaTesti = pParameterBean.getTesto();
			
			// Tengo traccia delle pagine da stampare in verticale
			HashMap<Integer, Boolean> pageInVertical = new HashMap<Integer, Boolean>();
			// Numero di label inserite
			int numOfLabel = 0;
			
			// Ciclo sulle etichette da stampare
			for (TestoBarcodeBean bean : listaTesti) {
				logger.debug("************** Inizio elaborazione riga **************");
				logger.debug("Testo: " + bean.getTesto());
				logger.debug("Code: " + bean.getBarcode());
				logger.debug("TestoBarcode: " + bean.getTestoBarcode());
				logger.debug("TestoRepertorio: " + bean.getTestoRepertorio());
				logger.debug("LabelWithoutCodeVertical vale: " + pdfPropertiesBean.getLabelWithoutCodeVertical());
				logger.debug("LabelWithCodeAndTextVertical vale: " + pdfPropertiesBean.getLabelWithCodeAndTextVertical());
				logger.debug("BarcodeEncoding vale: " + pParameterBean.getBarcodeEncoding());
				logger.debug("QrCodeVertical vale: " + pdfPropertiesBean.getQrCodeVertical());
				logger.debug("PrintCodeInSecondLabel vale: " + pParameterBean.getPrintCodeInSecondLabel());
				logger.debug("TestoPresenteInTestoBarcodeBean vale: " + testoPresenteInTestoBarcodeBean(bean));
				logger.debug("BarCodePresenteInTestoBarcodeBean vale: " + barcodePresenteInTestoBarcodeBean(bean));
				logger.debug("TestoRepertorioPresenteInTestoBarcodeBean vale: " + testoPresenteInTestoBarcodeBean(bean));

				// Verifico se è presente il testo da mettere nella prima etichetta
				if (testoPresenteInTestoBarcodeBean(bean)) {
					// Dimensione della prima pagina, se devo stampare l'etichetta in verticale devo prima ruotare il foglio 
					// in quanto i paragrafi vengono scritti in orizzontale
					Rectangle pagesize1;
					boolean page1ToRotate = false;
					if ((!barcodePresenteInTestoBarcodeBean(bean) && pdfPropertiesBean.getLabelWithoutCodeVertical() != null && "true".equalsIgnoreCase(pdfPropertiesBean.getLabelWithoutCodeVertical())) ||
						(barcodePresenteInTestoBarcodeBean(bean) &&  pdfPropertiesBean.getLabelWithoutCodeVertical() != null && "true".equalsIgnoreCase(pdfPropertiesBean.getLabelWithoutCodeVertical()) && pParameterBean.getPrintCodeInSecondLabel() != null && "true".equalsIgnoreCase(pParameterBean.getPrintCodeInSecondLabel())) ||
						(barcodePresenteInTestoBarcodeBean(bean) &&  pdfPropertiesBean.getLabelWithCodeAndTextVertical() != null && "true".equalsIgnoreCase(pdfPropertiesBean.getLabelWithCodeAndTextVertical()))){
						// E' una etichetta senza barcode che va stampata verticalmente, oppure l'etichetta non è contenuta nella prima pagina, oppure ha il barcode ma è forzata in verticale
						logger.debug("Etichetta senza barcode che va stampata verticalmente, o con barcode ma forzata in verticale");
						// Inverto altezza e larghezza, in questo modo è come se scrivessi in verticale. Poi in fixRotation rioriento il foglio in modo corretto
						pagesize1 = new Rectangle(pdfH, pdfW);
						page1ToRotate = true;
					} else if (barcodePresenteInTestoBarcodeBean(bean) && "QRCODE".equalsIgnoreCase(pParameterBean.getBarcodeEncoding()) &&
							pdfPropertiesBean.getQrCodeVertical() != null && "true".equalsIgnoreCase(pdfPropertiesBean.getQrCodeVertical()) &&
							!(pParameterBean.getPrintCodeInSecondLabel() != null && "true".equalsIgnoreCase(pParameterBean.getPrintCodeInSecondLabel()))) {
						// Il qrcode è presente, è stampato nella prima etichetta e la devo stampare in verticale
						logger.debug("Etichetta con QRCode nella prima pagina e che va stampata verticalmente");
						// Inverto altezza e larghezza, in questo modo è come se scrivessi in verticale. Poi in fixRotation rioriento il foglio in modo corretto
						pagesize1 = new Rectangle(pdfH, pdfW);
						page1ToRotate = true;
					} else {
						logger.debug("Etichetta stampata normalmente");
						pagesize1 = new Rectangle(pdfW, pdfH);
					}
					// Creo una nuova pagina
					numOfLabel = createNewPage(document, pagesize1, numOfLabel);
					// Tengo traccia delle pagine da stamoare in verticale
					pageInVertical.put(numOfLabel, page1ToRotate);
					// Splitto il testo
					String[] elencoTesti = PrintUtil.splittaTesto(bean.getTesto());
					// Aggiungo il testo da stampare nella prima pagina
					addParagraph(pdfPropertiesBean, document, elencoTesti, TipoLabelEtichetta.PRIMA_LABEL);
				} 
				
				// Verifico se è presente un codice barcode da stampare
				if (barcodePresenteInTestoBarcodeBean(bean)) {
					logger.debug("Code presente, aggiungo l'immagine");
					//Genero il barcode sono se è presente
					Image img = null;
					// Controllo barcodeEncoding
					logger.debug("barcodeEncoding vale " + pParameterBean.getBarcodeEncoding());
					if ((pParameterBean.getBarcodeEncoding() != null) && ("PDF417".equalsIgnoreCase(pParameterBean.getBarcodeEncoding()))){
						img = addBarcodeCodePDF147(bean.getBarcode(), writer, pdfPropertiesBean);
					}else if ((pParameterBean.getBarcodeEncoding() != null) && ("DATAMATRIX".equalsIgnoreCase(pParameterBean.getBarcodeEncoding()))){
						img = addBarcodeCodeBarcodeDatamatrix(bean.getBarcode(), writer, pdfPropertiesBean);
					}else if ((pParameterBean.getBarcodeEncoding() != null) && ("QRCODE".equalsIgnoreCase(pParameterBean.getBarcodeEncoding()))){
						img = addBarcodeCodeQRCode(bean.getBarcode(), writer, pdfPropertiesBean);
					}else {
						img = addBarcode128(bean.getBarcode(), writer, pdfPropertiesBean);
					}
					// Controllo printCodeInSecondLabel (Se valorizzato stampo il barcode su una etichetta a parte)
					// Se non ho il testo della prima pagina, il barcode è in una nuova pagina
					if ((!testoPresenteInTestoBarcodeBean(bean)) || pParameterBean.getPrintCodeInSecondLabel() != null && "true".equalsIgnoreCase(pParameterBean.getPrintCodeInSecondLabel())) {
						// Il barcode va aggiunto in una pagina a parte, oppure la prima pagina non è ancora stata creata
						logger.debug("Il barcode va aggiunto nella seconda pagina");
						boolean page2ToRotate = false;
						// Controllo qrCodeVerticalInSecondLabel (Se valorizzato e se l'encoding è QRCODE stampo la seconda etichetta in verticale)
						Rectangle pagesize2;
						if ("QRCODE".equalsIgnoreCase(pParameterBean.getBarcodeEncoding()) &&
								pdfPropertiesBean.getQrCodeVertical() != null && "true".equalsIgnoreCase(pdfPropertiesBean.getQrCodeVertical())) {
							// Il QRCode va stampato in verticale
							logger.debug("Il QRCode va stampato in verticale");
							// Inverto altezza e larghezza, in questo modo è come se scrivessi in verticale. Poi in fixRotation rioriento il foglio in modo corretto
							pagesize2 = new Rectangle(pdfH, pdfW);
							page2ToRotate = true;
						} else {
							pagesize2 = new Rectangle(pdfW, pdfH);
						}
						// Creo una nuova pagina
						numOfLabel = createNewPage(document, pagesize2, numOfLabel);
						// Tengo traccia delle pagine da stamoare in verticale
						pageInVertical.put(numOfLabel, page2ToRotate);
						// Splitto il testo
						String[] elencoTestiBarcode = PrintUtil.splittaTesto(bean.getTestoBarcode());
						// Aggiungo il testo da mettere sopra il code nella seconda etichetta
						addParagraph(pdfPropertiesBean, document, elencoTestiBarcode, TipoLabelEtichetta.SECONDA_LABEL);
						document.add(img);	
					} else {
						// Il barcode va aggiunto nella prima pagina
						logger.debug("Il barcode va aggiunto nella prima pagina");
						document.add(img);
					}
				} 
				
				// Stampo l'etichetta col testo repertorio
				if (testoRepertorioPresenteInTestoBarcodeBean(bean)) {
					// Ricavo la rotazione della pagina repertorio (sarà sicuramente senza barcode)
					Rectangle pagesizeRepertorio;
					boolean pageRepertorioToRotate = false;
					if (pdfPropertiesBean.getLabelWithoutCodeVertical() != null && "true".equalsIgnoreCase(pdfPropertiesBean.getLabelWithoutCodeVertical())){
						// Il repertorio va stampato verticalmente
						logger.debug("Il repertorio va stampato verticalmente");
						// Inverto altezza e larghezza, in questo modo è come se scrivessi in verticale. Poi in fixRotation rioriento il foglio in modo corretto
						pagesizeRepertorio = new Rectangle(pdfH, pdfW);
						pageRepertorioToRotate = true;
					} else {
						logger.debug("Repertorio va stampato verticalmente");
						pagesizeRepertorio = new Rectangle(pdfW, pdfH);
					}
					// Creo una nuova pagina
					numOfLabel = createNewPage(document, pagesizeRepertorio, numOfLabel);
					// Tengo traccia delle pagine da stamoare in verticale
					pageInVertical.put(numOfLabel, pageRepertorioToRotate);
					// Splitto il testo
					String[] elencoTestiRepertorio = PrintUtil.splittaTesto(bean.getTestoRepertorio());
					// Aggiungo il testo da stampare nella prima repertorio
					addParagraph(pdfPropertiesBean, document, elencoTestiRepertorio, TipoLabelEtichetta.REPERTORIO_LABEL);
				}
				
				// Stampo l'etichetta per il faldone di cittadella
				if (testoFaldoneCittadellaPresenteInTestoBarcodeBean(bean) || (barcodeFaldoneCittadellaPresenteInTestoBarcodeBean(bean))) {
					Rectangle pagesizeFaldone = new Rectangle(pdfW, pdfH);
					// Creo una nuova pagina
					numOfLabel = createNewPage(document, pagesizeFaldone, numOfLabel);
					// Tengo traccia delle pagine da stamoare in verticale
					pageInVertical.put(numOfLabel, false);
					// Splitto il testo
					String[] elencoTesti = PrintUtil.splittaTesto(bean.getTestoFaldone());
					// Aggiungo il testo da stampare nella prima pagina
					addParagraph(pdfPropertiesBean, document, elencoTesti, TipoLabelEtichetta.FALDONE_LABEL);
					Image imgFaldone = addBarcodeCodeQRCodePerFaldone(bean.getBarcodeFaldone(), writer, pdfPropertiesBean);
					document.add(imgFaldone);	
				}
			}
			
			document.close();
			writer.close();
			fos.close();
			
			fixRotation(pdffile.getPath(), pdfPropertiesBean, pParameterBean, pageInVertical);
			return pdffile.getPath() + "_fix.pdf";
			
		} catch (Exception exception) {
			throw exception;
		} finally {

		}
	}
	
	private static int createNewPage(Document document, Rectangle pagesize, int currentNumOfLabel) {
		document.setPageSize(pagesize);
		if (!document.isOpen()) {
			document.open();
		} else {
			document.newPage();
		} 
		return currentNumOfLabel + 1;
	}

	private static void addParagraph(PdfPropertiesBean pdfPropertiesBean, Document document, String[] elencoTesti, TipoLabelEtichetta labelDaStampare) throws DocumentException {
		
		// Aggiungo un paragrago vuoto di dimensione 0, mi serve poer avere un riferimento per il margine superiore della prima riga
		Paragraph emptyParagraph = new Paragraph(" ");
		emptyParagraph.setSpacingBefore(0);
		emptyParagraph.setSpacingAfter(0);
		// Devo togliere le interlinee
		emptyParagraph.setLeading(0, 1);
		emptyParagraph.setFont(FontFactory.getFont(pdfPropertiesBean.getFontFamily(), 0, Font.BOLD));
		document.add(emptyParagraph);
		logger.debug("Devo inserire " + elencoTesti.length + " righe nell'etichetta");
		for (int numRiga = 0; numRiga < elencoTesti.length; numRiga++) {
			logger.debug("Aggiungo la riga " + numRiga);
			
			// Leggo il layout della riga
			PdfRigaBean lPdfRigaBean = null;
			if (labelDaStampare == TipoLabelEtichetta.PRIMA_LABEL) {
				lPdfRigaBean = RigaBarcodeUtil.getPdfRigaBean(numRiga + 1, pdfPropertiesBean) != null ? RigaBarcodeUtil.getPdfRigaBean(numRiga + 1, pdfPropertiesBean) : new PdfRigaBean();
			} else if (labelDaStampare == TipoLabelEtichetta.SECONDA_LABEL){
				lPdfRigaBean = RigaBarcodeUtil.getPdfSecondLabelRigaBean(numRiga + 1, pdfPropertiesBean) != null ? RigaBarcodeUtil.getPdfSecondLabelRigaBean(numRiga + 1, pdfPropertiesBean) : new PdfRigaBean();
			} else if (labelDaStampare == TipoLabelEtichetta.REPERTORIO_LABEL){
				lPdfRigaBean = RigaBarcodeUtil.getPdfRepertorioLabelRigaBean(numRiga + 1, pdfPropertiesBean) != null ? RigaBarcodeUtil.getPdfRepertorioLabelRigaBean(numRiga + 1, pdfPropertiesBean) : new PdfRigaBean();	
			} else if (labelDaStampare == TipoLabelEtichetta.FALDONE_LABEL){
				lPdfRigaBean = RigaBarcodeUtil.getPdfFaldoneLabelRigaBean(numRiga + 1, pdfPropertiesBean) != null ? RigaBarcodeUtil.getPdfFaldoneLabelRigaBean(numRiga + 1, pdfPropertiesBean) : new PdfRigaBean();
			}	

			// Setto lo stile
			logger.debug("FontWeight vale " + lPdfRigaBean.getFontWeight());
			logger.debug("Il font da settare vale " + pdfPropertiesBean.getFontFamily());
			Font font = FontFactory.getFont(pdfPropertiesBean.getFontFamily(), 9, Font.BOLD);// BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			font.setSize(9);
			FontFactory.registerDirectories();
			if ((lPdfRigaBean.getFontWeight() != null) && (lPdfRigaBean.getFontWeight().toLowerCase().indexOf("bold") != -1)
					&& (lPdfRigaBean.getFontWeight().toLowerCase().indexOf("italic") != -1)) {
				font = FontFactory.getFont(pdfPropertiesBean.getFontFamily(), 9, Font.BOLDITALIC);
			} else if ((lPdfRigaBean.getFontWeight() != null) && (lPdfRigaBean.getFontWeight().toLowerCase().indexOf("bold") != -1)) {
				font = FontFactory.getFont(pdfPropertiesBean.getFontFamily(), 9, Font.BOLD);
			} else if ((lPdfRigaBean.getFontWeight() != null) && (lPdfRigaBean.getFontWeight().toLowerCase().indexOf("italic") != -1)) {
				font = FontFactory.getFont(pdfPropertiesBean.getFontFamily(), 9, Font.ITALIC);
			} else {
				font = FontFactory.getFont(pdfPropertiesBean.getFontFamily(), 9, Font.NORMAL);
			}
			if ((lPdfRigaBean.getFontWeight() != null) && (lPdfRigaBean.getFontWeight().toLowerCase().indexOf("under") != -1)) {
				font.setStyle(Font.UNDERLINE);
			}

			// Setto il font size
			logger.debug("FontSize vale " + lPdfRigaBean.getFontSize());
			if ((lPdfRigaBean.getFontSize() != null) && (!"".equalsIgnoreCase(lPdfRigaBean.getFontSize()))) {
				font.setSize(Integer.parseInt(CommonUtils.removeUnitOfMeasure(lPdfRigaBean.getFontSize(), "pt")));
			}

			// Creo un nuovo paragrafo
			String testoDellaRiga = elencoTesti[numRiga];
			Paragraph paragraph = new Paragraph(testoDellaRiga, font);

			// Setto il textAlign
			logger.debug("TextAlign vale " + lPdfRigaBean.getTextAlign());
			if ("center".equalsIgnoreCase(lPdfRigaBean.getTextAlign())) {
				paragraph.setAlignment(Element.ALIGN_CENTER);
			} else if ("left".equalsIgnoreCase(lPdfRigaBean.getTextAlign())) {
				paragraph.setAlignment(Element.ALIGN_LEFT);
			}
			if ("right".equalsIgnoreCase(lPdfRigaBean.getTextAlign())) {
				paragraph.setAlignment(Element.ALIGN_RIGHT);
			}

			// Con questo setti il marginTop
			logger.debug("MarginTop vale " + lPdfRigaBean.getMarginTop());
			if ((lPdfRigaBean.getMarginTop() != null) && (!"".equalsIgnoreCase(lPdfRigaBean.getMarginTop()))) {
				paragraph.setSpacingBefore(Utilities.millimetersToPoints(CommonUtils.convertLengthMeasurementToMm(lPdfRigaBean.getMarginTop())));
			}
			
			// Setto il leading
			logger.debug("Leading vale " + lPdfRigaBean.getLeading());
			float leading = 0f;
			if ((lPdfRigaBean.getLeading() != null) && (!"".equalsIgnoreCase(lPdfRigaBean.getLeading()))) {
				leading = (Utilities.millimetersToPoints(CommonUtils.convertLengthMeasurementToMm(lPdfRigaBean.getLeading())));
			}
			paragraph.setLeading(new Float((font.getSize() * 0.5) + leading), 0.2f);

			// Setto il marginBottom
			logger.debug("MarginBottom vale " + lPdfRigaBean.getMarginBottom());
			if ((lPdfRigaBean.getMarginBottom() != null) && (!"".equalsIgnoreCase(lPdfRigaBean.getMarginBottom()))) {
				paragraph.setSpacingAfter(Utilities.millimetersToPoints(CommonUtils.convertLengthMeasurementToMm(lPdfRigaBean.getMarginBottom())));
			}

			document.add(paragraph);
		}
	}

	private static Image addBarcode128(String testo, com.itextpdf.text.pdf.PdfWriter writer, PdfPropertiesBean pdfPropertiesBean)
			throws BadElementException {
		logger.debug("Aggiungo il barcode");
		Barcode barcode = new Barcode128();

		barcode.setCode(testo);
		barcode.setCodeType(Barcode128.CODE128);

		// Controllo barCodeHeight
		logger.debug("BarCodeHeight vale " + pdfPropertiesBean.getBarCodeHeight());
		if ((pdfPropertiesBean.getBarCodeHeight() != null) && (!"".equalsIgnoreCase(pdfPropertiesBean.getBarCodeHeight()))) {
			String barCodeHeightString = pdfPropertiesBean.getBarCodeHeight();
			barcode.setBarHeight(Utilities.millimetersToPoints(CommonUtils.convertLengthMeasurementToMm(barCodeHeightString)));
		} else {
			barcode.setBarHeight(Utilities.millimetersToPoints(10));
		}

		// Controllo barCodeHumanReadablePlacement
		logger.debug("BarCodeHumanReadablePlacement vale " + pdfPropertiesBean.getBarCodeHumanReadablePlacement());
		if (pdfPropertiesBean.getBarCodeHumanReadablePlacement() == null || "none".equalsIgnoreCase(pdfPropertiesBean.getBarCodeHumanReadablePlacement())) {
			barcode.setFont(null);
		} else {
			barcode.setBaseline(10);
		}

		// Controllo barcodeModuleWidth
		float barcodeModuleWidth = 0f;
		logger.debug("BarcodeModuleWidth vale " + pdfPropertiesBean.getBarcodeModuleWidth());
		if (pdfPropertiesBean.getBarcodeModuleWidth() != null && !"".equalsIgnoreCase(pdfPropertiesBean.getBarcodeModuleWidth())) {
			barcodeModuleWidth = Utilities.millimetersToPoints(CommonUtils.convertLengthMeasurementToMm(pdfPropertiesBean.getBarcodeModuleWidth()));
			barcode.setX(barcodeModuleWidth);
		}else {
			barcodeModuleWidth = Utilities.millimetersToPoints(CommonUtils.convertLengthMeasurementToMm("0.2"));
			barcode.setX(barcodeModuleWidth);
		}

		// Controllo barCodeWideFactor
		logger.debug("BarCodeWideFactor vale " + pdfPropertiesBean.getBarCodeWideFactor());
		if (pdfPropertiesBean.getBarCodeWideFactor() != null && !"".equalsIgnoreCase(pdfPropertiesBean.getBarCodeWideFactor())) {
			barcode.setN(Float.parseFloat(CommonUtils.removeUnitOfMeasure(pdfPropertiesBean.getBarCodeWideFactor(), "mw")));
		}

		// Ho settato il barcode. Lo converto in immagine
		Image img = barcode.createImageWithBarcode(writer.getDirectContent(), null, null);

		// Controllo barCodeOrientation
		logger.debug("BarCodeOrientation vale " + pdfPropertiesBean.getBarCodeOrientation());
		if (pdfPropertiesBean.getBarCodeOrientation() != null) {
			if ("0".equalsIgnoreCase(pdfPropertiesBean.getBarCodeOrientation())) {
				img.setRotationDegrees(0);
			} else if ("90".equalsIgnoreCase(pdfPropertiesBean.getBarCodeOrientation())) {
				img.setRotationDegrees(90);
			} else if ("180".equalsIgnoreCase(pdfPropertiesBean.getBarCodeOrientation())) {
				img.setRotationDegrees(180);
			} else if ("270".equalsIgnoreCase(pdfPropertiesBean.getBarCodeOrientation())) {
				img.setRotationDegrees(270);
			}
		}

		// Controllo barCodeAlign
		logger.debug("BarCodeAlign vale " + pdfPropertiesBean.getBarCodeAlign());
		img.setAlignment(Element.ALIGN_CENTER);
		if ("center".equalsIgnoreCase(pdfPropertiesBean.getBarCodeAlign())) {
			img.setAlignment(Image.ALIGN_CENTER);
		}
		if ("right".equalsIgnoreCase(pdfPropertiesBean.getBarCodeAlign())) {
			img.setAlignment(Image.ALIGN_RIGHT);
		}
		if ("left".equalsIgnoreCase(pdfPropertiesBean.getBarCodeAlign())) {
			img.setAlignment(Image.ALIGN_LEFT);
		}

		logger.debug("Genero l'immagine per il timbro con codifica BarcodeCode128  " + img);

		// Controllo barCodeQuietZoneEnabled
		float larghezzaBarcode = img.getWidth();
		float larghezzaQuitezone = 0;
		logger.debug("BarCodeQuietZoneEnabled vale " + pdfPropertiesBean.getBarCodeQuietZoneEnabled());
		if ("true".equalsIgnoreCase(pdfPropertiesBean.getBarCodeQuietZoneEnabled())) {
			// Controllo BarCodeQuietZoneValue
			if ((pdfPropertiesBean.getBarCodeQuietZoneValue() != null) && (!"".equalsIgnoreCase(pdfPropertiesBean.getBarCodeQuietZoneValue()))) {
				float mwQuitezone = Float.valueOf(CommonUtils.removeUnitOfMeasure(pdfPropertiesBean.getBarCodeQuietZoneValue(), "mw"));
				larghezzaQuitezone = mwQuitezone * barcodeModuleWidth;
			}
			// Settare lo spacing in base al valore di barCodeQuietZoneValue
		}
		
		return img;
	}
	
	private static Image addBarcodeCodePDF147(String testo, com.itextpdf.text.pdf.PdfWriter writer, PdfPropertiesBean pdfPropertiesBean)
			throws BadElementException {
		logger.debug("Aggiungo il barcode");
		BarcodePDF417 barcodePDF417 = new BarcodePDF417();
		barcodePDF417.setText(testo);
		barcodePDF417.setOptions(BarcodePDF417.PDF417_USE_ASPECT_RATIO);
		barcodePDF417.setOptions(BarcodePDF417.PDF417_FIXED_COLUMNS);
		barcodePDF417.setOptions(BarcodePDF417.PDF417_FIXED_COLUMNS);
		
		barcodePDF417.setCodeColumns(0);
		barcodePDF417.setCodeColumns(0);
		
		// Controllo pdf147YHeight
		logger.debug("Pdf147YHeight vale " + pdfPropertiesBean.getPdf147YHeight());
		barcodePDF417.setYHeight(3);
		if ( pdfPropertiesBean.getPdf147YHeight() != null && !"".equalsIgnoreCase(pdfPropertiesBean.getPdf147YHeight())) {
			barcodePDF417.setYHeight(Float.valueOf(pdfPropertiesBean.getPdf147YHeight()));
		}
		
		// Controllo pdf147AspectRatio
		logger.debug("Pdf147AspectRatio vale " + pdfPropertiesBean.getPdf147AspectRatio());
		barcodePDF417.setAspectRatio(1);
		if ( pdfPropertiesBean.getPdf147AspectRatio() != null && !"".equalsIgnoreCase(pdfPropertiesBean.getPdf147AspectRatio())) {
			barcodePDF417.setAspectRatio(Float.valueOf(pdfPropertiesBean.getPdf147AspectRatio()));
		}
		
		// Controllo pdf147ScalePercentX
		logger.debug("pdf147ScalePercentX vale " + pdfPropertiesBean.getPdf147ScalePercentX());
		int pdf147ScalePercentX = 100;
		if ( pdfPropertiesBean.getPdf147ScalePercentX() != null && !"".equalsIgnoreCase(pdfPropertiesBean.getPdf147ScalePercentX())) {
			pdf147ScalePercentX = Integer.valueOf(pdfPropertiesBean.getPdf147ScalePercentX());
		}
		
		// Controllo pdf147ScalePercentY
		logger.debug("pdf147ScalePercentY vale " + pdfPropertiesBean.getPdf147ScalePercentY());
		int pdf147ScalePercentY = 100;
		if ( pdfPropertiesBean.getPdf147ScalePercentY() != null && !"".equalsIgnoreCase(pdfPropertiesBean.getPdf147ScalePercentY())) {
			pdf147ScalePercentY = Integer.valueOf(pdfPropertiesBean.getPdf147ScalePercentY());
		}
				
		// Controllo pdf147AspectRatio
		logger.debug("Pdf147AspectRatio vale " + pdfPropertiesBean.getPdf147AspectRatio());
		barcodePDF417.setAspectRatio(1);
		if ( pdfPropertiesBean.getPdf147AspectRatio() != null && !"".equalsIgnoreCase(pdfPropertiesBean.getPdf147AspectRatio())) {
			barcodePDF417.setAspectRatio(Float.valueOf(pdfPropertiesBean.getPdf147AspectRatio()));
		}

		// Ho settato il barcode. Lo converto in immagine
		Image img = barcodePDF417.getImage();
		img.scalePercent(pdf147ScalePercentX, pdf147ScalePercentY);

		// Controllo barCodeOrientation
		logger.debug("BarCodeOrientation vale " + pdfPropertiesBean.getBarCodeOrientation());
		if (pdfPropertiesBean.getBarCodeOrientation() != null) {
			if ("0".equalsIgnoreCase(pdfPropertiesBean.getBarCodeOrientation())) {
				img.setRotationDegrees(0);
			} else if ("90".equalsIgnoreCase(pdfPropertiesBean.getBarCodeOrientation())) {
				img.setRotationDegrees(90);
			} else if ("180".equalsIgnoreCase(pdfPropertiesBean.getBarCodeOrientation())) {
				img.setRotationDegrees(180);
			} else if ("270".equalsIgnoreCase(pdfPropertiesBean.getBarCodeOrientation())) {
				img.setRotationDegrees(270);
			}
		}

		// Controllo barCodeAlign
		logger.debug("BarCodeAlign vale " + pdfPropertiesBean.getBarCodeAlign());
		img.setAlignment(Element.ALIGN_CENTER);
		if ("center".equalsIgnoreCase(pdfPropertiesBean.getBarCodeAlign())) {
			img.setAlignment(Image.ALIGN_CENTER);
		}
		if ("right".equalsIgnoreCase(pdfPropertiesBean.getBarCodeAlign())) {
			img.setAlignment(Image.ALIGN_RIGHT);
		}
		if ("left".equalsIgnoreCase(pdfPropertiesBean.getBarCodeAlign())) {
			img.setAlignment(Image.ALIGN_LEFT);
		}

		logger.debug("Genero l'immagine per il timbro con codifica BarcodePDF417 " + img);

		return img;
	}
	
	private static Image addBarcodeCodeBarcodeDatamatrix(String testo, com.itextpdf.text.pdf.PdfWriter writer, PdfPropertiesBean pdfPropertiesBean)
			throws BadElementException, UnsupportedEncodingException {
		logger.debug("Aggiungo il barcode");
		BarcodeDatamatrix barcodeDatamatrix = new BarcodeDatamatrix();
		
		// Controllo datamatrixHeight
		logger.debug("DatamatrixHeight vale " + pdfPropertiesBean.getDatamatrixHeight());
		barcodeDatamatrix.setHeight(32);
		if ( pdfPropertiesBean.getDatamatrixHeight() != null && !"".equalsIgnoreCase(pdfPropertiesBean.getDatamatrixHeight())) {
			barcodeDatamatrix.setHeight(Integer.valueOf(pdfPropertiesBean.getDatamatrixHeight()));
		}
		
		// Controllo datamatrixWidth
		logger.debug("DatamatrixWidth vale " + pdfPropertiesBean.getDatamatrixWidth());
		barcodeDatamatrix.setWidth(32);
		if ( pdfPropertiesBean.getDatamatrixWidth() != null && !"".equalsIgnoreCase(pdfPropertiesBean.getDatamatrixWidth())) {
			barcodeDatamatrix.setWidth(Integer.valueOf(pdfPropertiesBean.getDatamatrixWidth()));
		}
				
		// Controllo datamatrixWhitespace
		logger.debug("DatamatrixWhitespace vale " + pdfPropertiesBean.getDatamatrixWhitespace());
		barcodeDatamatrix.setWs(0);
		if ( pdfPropertiesBean.getDatamatrixWhitespace() != null && !"".equalsIgnoreCase(pdfPropertiesBean.getDatamatrixWhitespace())) {
			barcodeDatamatrix.setWs(Integer.valueOf(pdfPropertiesBean.getDatamatrixWhitespace()));
		}
		
		barcodeDatamatrix.generate(testo);
		
		// Ho settato il barcode. Lo converto in immagine
		Image img = barcodeDatamatrix.createImage();

		// Controllo barCodeOrientation
		logger.debug("BarCodeOrientation vale " + pdfPropertiesBean.getBarCodeOrientation());
		if (pdfPropertiesBean.getBarCodeOrientation() != null) {
			if ("0".equalsIgnoreCase(pdfPropertiesBean.getBarCodeOrientation())) {
				img.setRotationDegrees(0);
			} else if ("90".equalsIgnoreCase(pdfPropertiesBean.getBarCodeOrientation())) {
				img.setRotationDegrees(90);
			} else if ("180".equalsIgnoreCase(pdfPropertiesBean.getBarCodeOrientation())) {
				img.setRotationDegrees(180);
			} else if ("270".equalsIgnoreCase(pdfPropertiesBean.getBarCodeOrientation())) {
				img.setRotationDegrees(270);
			}
		}

		// Controllo barCodeAlign
		logger.debug("BarCodeAlign vale " + pdfPropertiesBean.getBarCodeAlign());
		img.setAlignment(Element.ALIGN_CENTER);
		if ("center".equalsIgnoreCase(pdfPropertiesBean.getBarCodeAlign())) {
			img.setAlignment(Image.ALIGN_CENTER);
		}
		if ("right".equalsIgnoreCase(pdfPropertiesBean.getBarCodeAlign())) {
			img.setAlignment(Image.ALIGN_RIGHT);
		}
		if ("left".equalsIgnoreCase(pdfPropertiesBean.getBarCodeAlign())) {
			img.setAlignment(Image.ALIGN_LEFT);
		}

		logger.debug("Genero l'immagine per il timbro con codifica BarcodeDataMatrix " + img);

		return img;
	}
	
	private static Image addBarcodeCodeQRCode(String testo, com.itextpdf.text.pdf.PdfWriter writer, PdfPropertiesBean pdfPropertiesBean)
			throws BadElementException, UnsupportedEncodingException {
		logger.debug("Aggiungo il barcode");
		
		// Controllo QRCodeHeight
		logger.debug("QRCodeHeight vale " + pdfPropertiesBean.getQrCodeHeight());
		int height = 1;
		if ( pdfPropertiesBean.getQrCodeHeight() != null && !"".equalsIgnoreCase(pdfPropertiesBean.getQrCodeHeight())) {
			height = Integer.valueOf(pdfPropertiesBean.getQrCodeHeight());
		}
		
		// Controllo qrCodeWidth
		logger.debug("QRCodeWidth vale " + pdfPropertiesBean.getQrCodeWidth());
		int width = 1;
		if ( pdfPropertiesBean.getQrCodeWidth() != null && !"".equalsIgnoreCase(pdfPropertiesBean.getQrCodeWidth())) {
			width = Integer.valueOf(pdfPropertiesBean.getQrCodeWidth());
		}
		
		BarcodeQRCode barcodeQRCode = new BarcodeQRCode(testo, width, height, null);
					
		// Ho settato il barcode. Lo converto in immagine
		Image img = barcodeQRCode.getImage();
		barcodeQRCode.createAwtImage(Color.BLACK, Color.WHITE);

		// Controllo barCodeOrientation
		logger.debug("BarCodeOrientation vale " + pdfPropertiesBean.getBarCodeOrientation());
		if (pdfPropertiesBean.getBarCodeOrientation() != null) {
			if ("0".equalsIgnoreCase(pdfPropertiesBean.getBarCodeOrientation())) {
				img.setRotationDegrees(0);
			} else if ("90".equalsIgnoreCase(pdfPropertiesBean.getBarCodeOrientation())) {
				img.setRotationDegrees(90);
			} else if ("180".equalsIgnoreCase(pdfPropertiesBean.getBarCodeOrientation())) {
				img.setRotationDegrees(180);
			} else if ("270".equalsIgnoreCase(pdfPropertiesBean.getBarCodeOrientation())) {
				img.setRotationDegrees(270);
			}
		}

		// Controllo barCodeAlign
		logger.debug("BarCodeAlign vale " + pdfPropertiesBean.getBarCodeAlign());
		img.setAlignment(Element.ALIGN_CENTER);
		if ("center".equalsIgnoreCase(pdfPropertiesBean.getBarCodeAlign())) {
			img.setAlignment(Image.ALIGN_CENTER);
		}
		if ("right".equalsIgnoreCase(pdfPropertiesBean.getBarCodeAlign())) {
			img.setAlignment(Image.ALIGN_RIGHT);
		}
		if ("left".equalsIgnoreCase(pdfPropertiesBean.getBarCodeAlign())) {
			img.setAlignment(Image.ALIGN_LEFT);
		}

		logger.debug("Genero l'immagine per il timbro con codifica QRCode " + img);

		return img;
	}
	
	private static Image addBarcodeCodeQRCodePerFaldone(String testo, com.itextpdf.text.pdf.PdfWriter writer, PdfPropertiesBean pdfPropertiesBean)
			throws BadElementException, UnsupportedEncodingException {
		logger.debug("Aggiungo il barcode del faldone");
		
		// Controllo qrCodeFaldoneHeight
		logger.debug("QrCodeFaldoneHeight vale " + pdfPropertiesBean.getQrCodeHeight());
		int height = 1;
		if ( pdfPropertiesBean.getQrCodeFaldoneHeight() != null && !"".equalsIgnoreCase(pdfPropertiesBean.getQrCodeFaldoneHeight())) {
			height = Integer.valueOf(pdfPropertiesBean.getQrCodeFaldoneHeight());
		}
		
		// Controllo qrCodeFaldoneWidth
		logger.debug("QrCodeFaldoneWidth vale " + pdfPropertiesBean.getQrCodeFaldoneWidth());
		int width = 1;
		if ( pdfPropertiesBean.getQrCodeFaldoneWidth() != null && !"".equalsIgnoreCase(pdfPropertiesBean.getQrCodeFaldoneWidth())) {
			width = Integer.valueOf(pdfPropertiesBean.getQrCodeFaldoneWidth());
		}
		
		BarcodeQRCode barcodeQRCode = new BarcodeQRCode(testo, width, height, null);
					
		// Ho settato il barcode. Lo converto in immagine
		Image img = barcodeQRCode.getImage();
		barcodeQRCode.createAwtImage(Color.BLACK, Color.WHITE);

		// Controllo barCodeOrientation
		img.setRotationDegrees(0);
	
		// Controllo barCodeAlign
		img.setAlignment(Element.ALIGN_CENTER);

		logger.debug("Genero l'immagine per il faldone con codifica QRCode " + img);

		return img;
	}

	private static void fixRotation(String pdfPath, PdfPropertiesBean pdfPropertiesBean, ParameterBean pParameterBean, HashMap<Integer, Boolean> pageInVertical) throws IOException, DocumentException {
		logger.debug("Metodo fixRotation");
		Document documentFix = new Document();

		com.itextpdf.text.pdf.PdfWriter writerFix = com.itextpdf.text.pdf.PdfWriter.getInstance(documentFix, new FileOutputStream(pdfPath + "_fix.pdf"));

		documentFix.open();

		PdfContentByte cb = writerFix.getDirectContent();

		// Collego il reader al file
		PdfReader reader = new PdfReader(pdfPath);
		// Scorro le pagine da copiare
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			
			PdfImportedPage page = writerFix.getImportedPage(reader, i);
			Rectangle psize = reader.getPageSizeWithRotation(i);
			Rectangle rect = new Rectangle(psize.getWidth(), psize.getHeight());
			documentFix.setPageSize(rect);
			
			int rotation = 0;
			
			// Verifico se la pagina è verticale
			if (pageInVertical.containsKey(i) && pageInVertical.get(i)){
				// La pagina è verticale
				rotation += 270;
			}
			logger.debug("Pagina " + i + ": rotazione: " + rotation);
			
			// Effettuo la rotazione
			if (270 <= rotation) {
				// Ruoto di 270 gradi
				documentFix.setPageSize(rect.rotate());
				documentFix.newPage();
				cb.addTemplate(page, 0, 1f, -1f, 0, psize.getHeight(), 0);
			}else if (180 <= rotation) {
				// Ruoto di 180 gradi
				documentFix.setPageSize(rect);
				documentFix.newPage();
				cb.addTemplate(page, -1f, 0, 0, -1f, psize.getWidth(), psize.getHeight());
			} else if (90 <= rotation) {
				// Ruoto di 90 gradi 
				documentFix.setPageSize(rect.rotate());
				documentFix.newPage();
				cb.addTemplate(page, 0, -1f, 1f, 0, 0, psize.getWidth());
			} else {
				// nessuna rotazione
				documentFix.setPageSize(rect);
				documentFix.newPage();
				cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
			} 
		}

		documentFix.close();
		try {
			reader.close();

		} catch (Exception e) {

		}
	}
	
	private static boolean testoPresenteInTestoBarcodeBean(TestoBarcodeBean bean) {
		return bean.getTesto() != null && !"".equalsIgnoreCase(bean.getTesto().trim());
	}
	
	private static boolean barcodePresenteInTestoBarcodeBean(TestoBarcodeBean bean) {
		return bean.getBarcode() != null && !"".equalsIgnoreCase(bean.getBarcode().trim());
	}
	
	private static boolean testoRepertorioPresenteInTestoBarcodeBean(TestoBarcodeBean bean) {
		return bean.getTestoRepertorio() != null && !"".equalsIgnoreCase(bean.getTestoRepertorio().trim());
	}
	
	private static boolean testoFaldoneCittadellaPresenteInTestoBarcodeBean(TestoBarcodeBean bean) {
		return bean.getTestoFaldone() != null && !"".equalsIgnoreCase(bean.getTestoFaldone().trim());
	}
	
	private static boolean barcodeFaldoneCittadellaPresenteInTestoBarcodeBean(TestoBarcodeBean bean) {
		return bean.getBarcodeFaldone() != null && !"".equalsIgnoreCase(bean.getBarcodeFaldone().trim());
	}
	
//	public enum TipoLabelEtichetta {
//
//		PRIMA_LABEL("pdf"), SECONDA_LABEL("prn"), REPERTORIO_LABEL("prn"), FALDONE_LABEL("prn");
//
//		private String value;
//
//		private TipoLabelEtichetta(String pString) {
//			value = pString;
//		}
//
//		public String getValue() {
//			return value;
//		}
//	}
	
	public enum TipoLabelEtichetta {
		
		PRIMA_LABEL, SECONDA_LABEL, REPERTORIO_LABEL, FALDONE_LABEL;
	
	}
}
