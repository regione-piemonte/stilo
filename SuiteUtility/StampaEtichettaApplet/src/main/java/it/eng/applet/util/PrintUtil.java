package it.eng.applet.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.RenderedImage;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import it.eng.applet.configuration.ParameterBean;
import it.eng.applet.configuration.bean.PdfPropertiesBean;

public class PrintUtil {

	private static PrintRequestAttributeSet pras;
	private static DocFlavor flavor;

	public static void checkPrinterSelected(ParameterBean parameter) throws PrintException {
		PrintService lPrintService = getService(parameter.getNomeStampante());

	}

	public static String getDefaultPrinter() {
		PrintService printServiceDefault = PrintServiceLookup.lookupDefaultPrintService();
		LogWriter.writeLog("printServiceDefault: " + printServiceDefault.getName());
		return printServiceDefault.getName();
	}

	private static void changeDefaultPrinter(String cmd, String printer) {
		try {
			String cmdline = cmd + " \"" + printer + "\"";
			LogWriter.writeLog("cmdline: " + cmdline);
			Process p = Runtime.getRuntime().exec(cmdline);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * I test dei vari metodi possono essere effettuati con PDFCreator, impostando la dimensione della pagina di stampa uguale a quella effettiva che verrà usata nelle stampanti.
	 * In questo modo si può avere una idea del foglio che verrà inviato alla stampante
	 *  
	 * Ci sono vari metodi di stampa possibili, in quanto si è osservato che non tutti i metodi funzionano con tutte le stampanti
	 * 
	 * Metodi possibili:
	 * - printerJobPDFPageable: Utilizza un PrinterJob per la stampa e un PDFPageable per la paginazione. Su alcuni modelli di stampanti la paginazione non riesce bene
	 * - printerJobPrintable: Utilizza un PrinterJob per la stampa e un Printable per la paginazione. Su alcuni modelli di stampanti l'oggetto Printable non riceve 
	 *                        le dimensioni corrette della pagina di destinazione
	 * - printerJobBook: Utilizza un PrinterJob per la stampa, e un oggetto Book per la paginazione. Sembra essere la modalità con cui il PrinterJob funziona meglio
	 * - docPrintJob: Utilizza un DocPrintJob per la stampa. Non sembra dare problemi di paginazione, ma non tutte le stampanti funzionano con questo metodo di stampa             
	 * 
	 */
	public static void print(File pdfFile, PdfPropertiesBean properties, final ParameterBean parameter) throws PrintException, PrinterException, IOException {
		LogWriter.writeLog("Metodo print");
		PrintService lPrintService = getService(parameter.getNomeStampante());

		PDDocument lPdDocument = PDDocument.load(pdfFile);
		PrinterJob printJob = PrinterJob.getPrinterJob();
		printJob.setPrintService(lPrintService);

		PageFormat pf = printJob.defaultPage();
		Paper paper = new Paper();
		paper.setSize(pf.getWidth(), pf.getHeight());
		paper.setImageableArea(0.0, 0.0, paper.getWidth(), paper.getHeight());
		pf.setPaper(paper);
		
		pf.setOrientation(PageFormat.PORTRAIT);
		
		// Controllo printableResolution
		LogWriter.writeLog("PrintableResolution vale " + properties.getPrintableResolution());
		int printableResolution = 300;
		if (properties.getPrintableResolution() != null) {
			try { 
				printableResolution = Integer.parseInt(properties.getPrintableResolution()); 
		    } catch(Exception e) { 
		    	printableResolution = 300;
		        LogWriter.writeLog("Errore nella conversione di PrintableResolution", e);; 
		    }
		}
		final int printableResolutionFinal = printableResolution;
		
		// Controllo renderImageType
		LogWriter.writeLog("RenderImageType vale " + properties.getRenderImageType());
		final ImageType renderImageType;
		if ("BINARY".equalsIgnoreCase(properties.getRenderImageType())){
			renderImageType = ImageType.BINARY;
		}else if("GRAY".equalsIgnoreCase(properties.getRenderImageType())){
			renderImageType = ImageType.GRAY;
		}else if ("RGB".equalsIgnoreCase(properties.getRenderImageType())){
			renderImageType = ImageType.RGB;
		}else if ("ARGB".equalsIgnoreCase(properties.getRenderImageType())){
			renderImageType = ImageType.ARGB;
		}else{
			renderImageType = ImageType.RGB;
		}
		
		// Controllo antialias
		LogWriter.writeLog("Antialias vale " + properties.getAntialias());
		final Object antialiasingValue;
		if ("on".equalsIgnoreCase(properties.getAntialias())) {	
			antialiasingValue = RenderingHints.VALUE_ANTIALIAS_ON;
		}else if ("off".equalsIgnoreCase(properties.getAntialias())) {	
			antialiasingValue = RenderingHints.VALUE_ANTIALIAS_OFF;
		} else {
			antialiasingValue = RenderingHints.VALUE_ANTIALIAS_DEFAULT;
		}
		
		// Controllo alphaInterpolation
		LogWriter.writeLog("AlphaInterpolation vale " + properties.getAlphaInterpolation());
		final Object alphaInterpolationValue;
		if ("quality".equalsIgnoreCase(properties.getAlphaInterpolation())) {	
			alphaInterpolationValue = RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY;
		}else if ("speed".equalsIgnoreCase(properties.getAlphaInterpolation())) {	
			alphaInterpolationValue = RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED;
		} else {
			alphaInterpolationValue = RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT;
		}
		
		// Controllo colorRender
		LogWriter.writeLog("ColorRender vale " + properties.getColorRender());
		final Object colorRenderValue;
		if ("quality".equalsIgnoreCase(properties.getColorRender())) {	
			colorRenderValue = RenderingHints.VALUE_COLOR_RENDER_QUALITY;
		}else if ("speed".equalsIgnoreCase(properties.getColorRender())) {	
			colorRenderValue = RenderingHints.VALUE_COLOR_RENDER_SPEED;
		} else {
			colorRenderValue = RenderingHints.VALUE_COLOR_RENDER_DEFAULT;
		}
		
		// Controllo dither
		LogWriter.writeLog("Dither vale " + properties.getDither());
		final Object ditherValue;
		if ("disable".equalsIgnoreCase(properties.getDither())) {	
			ditherValue = RenderingHints.VALUE_DITHER_DISABLE;
		}else if ("enable".equalsIgnoreCase(properties.getDither())) {	
			ditherValue = RenderingHints.VALUE_DITHER_ENABLE;
		} else {
			ditherValue = RenderingHints.VALUE_DITHER_DEFAULT;
		}
		
		// Controllo fractionalMetrics
		LogWriter.writeLog("FractionalMetrics vale " + properties.getFractionalMetrics());
		final Object fractionalMetricsValue;
		if ("on".equalsIgnoreCase(properties.getFractionalMetrics())) {	
			fractionalMetricsValue = RenderingHints.VALUE_FRACTIONALMETRICS_ON;
		}else if ("off".equalsIgnoreCase(properties.getFractionalMetrics())) {	
			fractionalMetricsValue = RenderingHints.VALUE_FRACTIONALMETRICS_OFF;
		} else {
			fractionalMetricsValue = RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT;
		}
		
		// Controllo interpolation
		LogWriter.writeLog("Interpolation vale " + properties.getInterpolation());
		final Object interpolationValue;
		if ("bicubic".equalsIgnoreCase(properties.getInterpolation())) {	
			interpolationValue = RenderingHints.VALUE_INTERPOLATION_BICUBIC;
		}else if ("bilinear".equalsIgnoreCase(properties.getInterpolation())) {	
			interpolationValue = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
		} else {
			interpolationValue = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
		}
		
		// Controllo render
		LogWriter.writeLog("Interpolation vale " + properties.getRender());
		final Object renderValue;
		if ("quality".equalsIgnoreCase(properties.getRender())) {	
			renderValue = RenderingHints.VALUE_RENDER_QUALITY;
		}else if ("speed".equalsIgnoreCase(properties.getRender())) {	
			renderValue = RenderingHints.VALUE_RENDER_SPEED;
		} else {
			renderValue = RenderingHints.VALUE_RENDER_DEFAULT;
		}
		
		// Controllo stroke
		LogWriter.writeLog("Stroke vale " + properties.getStroke());
		final Object strokeValue;
		if ("normalize".equalsIgnoreCase(properties.getStroke())) {	
			strokeValue = RenderingHints.VALUE_STROKE_NORMALIZE;
		}else if ("pure".equalsIgnoreCase(properties.getStroke())) {	
			strokeValue = RenderingHints.VALUE_STROKE_PURE;
		} else {
			strokeValue = RenderingHints.VALUE_STROKE_DEFAULT;
		}
		
		// Controllo textAntialias
		LogWriter.writeLog("TextAntialias vale " + properties.getTextAntialias());
		final Object textAntialiasingValue;
		if ("on".equalsIgnoreCase(properties.getTextAntialias())) {	
			textAntialiasingValue = RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
		}else if ("off".equalsIgnoreCase(properties.getTextAntialias())) {	
			textAntialiasingValue = RenderingHints.VALUE_TEXT_ANTIALIAS_OFF;
		}else if ("gasp".equalsIgnoreCase(properties.getTextAntialias())) {	
			textAntialiasingValue = RenderingHints.VALUE_TEXT_ANTIALIAS_GASP;
		}else if ("lcd_hrgb".equalsIgnoreCase(properties.getTextAntialias())) {	
			textAntialiasingValue = RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB;
		}else if ("lcd_hbgr".equalsIgnoreCase(properties.getTextAntialias())) {	
			textAntialiasingValue = RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR;
		}else if ("lcd_vrgb".equalsIgnoreCase(properties.getTextAntialias())) {	
			textAntialiasingValue = RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VRGB;
		}else if ("lcd_vbgr".equalsIgnoreCase(properties.getTextAntialias())) {	
			textAntialiasingValue = RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VBGR;
		} else {
			textAntialiasingValue = RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT; 
		}

		LogWriter.writeLog("Dimensione pagina di default: width=" + pf.getWidth() + " height=" + pf.getHeight());

		final PDFRenderer pdfRenderer = new PDFRenderer(lPdDocument);
		
		Printable printable = new Printable() {

			public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

				try {

					/*
					 * Soltanto nel caso in cui il numero di pagine analizzate � uguale al numero di copie da fare allora si pu� uscire da questo metodo e
					 * procedere alla stampa
					 */
					if (pageIndex == parameter.getNumeroCopie()) {
						return NO_SUCH_PAGE;
					} else {
						/*
						 * Ritorno la pagina del pdf indicata con indice pageIndex ad una risoluzione di 300 dpi Questa istruzione � stata portata all'interno
						 * del metodo paint perch� altrimenti si otteneva sempre e solo una etichetta ripetuta in 5 pagine
						 */
						final RenderedImage image = pdfRenderer.renderImageWithDPI(pageIndex, printableResolutionFinal, renderImageType);

						Graphics2D g2 = (Graphics2D) graphics;
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antialiasingValue);
						g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, alphaInterpolationValue);
						g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, colorRenderValue);
						g2.setRenderingHint(RenderingHints.KEY_DITHERING, ditherValue);
						g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, fractionalMetricsValue);
						g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, interpolationValue);
						g2.setRenderingHint(RenderingHints.KEY_RENDERING, renderValue);
						g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, strokeValue);
						g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, textAntialiasingValue);
						
						final double xScale = 1;
						final double xTranslate = 0;// -55;
						final double yScale = 1;
						final double yTranslate = 0;

						LogWriter.writeLog("Dimensione pageFormat: Width= " + pageFormat.getWidth() + " Height= " + pageFormat.getHeight());
						LogWriter.writeLog("Dimensione image: Width: " + image.getWidth() + " Height: " + image.getHeight());

						double widthScale = 1;// ( image.getHeight() / pageFormat.getWidth()) * xScale;
						double heightScale = 1;// (image.getWidth() / pageFormat.getHeight()) * yScale;

						if (image.getWidth() >= pageFormat.getWidth()) {
							widthScale = pageFormat.getWidth() / image.getWidth();
						}
						if (image.getHeight() >= pageFormat.getHeight()) {
							heightScale = pageFormat.getHeight() / image.getHeight();
						}

						if (widthScale > heightScale)
							widthScale = heightScale;
						else
							heightScale = widthScale;

						LogWriter.writeLog("Valori di scale: WidthScale= " + widthScale + " heightScale= " + heightScale);
						final AffineTransform at = AffineTransform.getScaleInstance(widthScale, heightScale);
						LogWriter.writeLog("Valori di translate: xTraslate= " + xTranslate + " yTraslate= " + yTranslate);
						at.translate(xTranslate, yTranslate);

						g2.drawRenderedImage(image, at);
						return PAGE_EXISTS;
					}
				} catch (IOException e) {
					LogWriter.writeLog("Errore nell'ottenimento della pagina (in renderImageWithDPI)");
				}

				return NO_SUCH_PAGE;
			}
		};
		
		// Controllo metodoStampaEtichette
		LogWriter.writeLog("MetodoStampaEtichette vale " + properties.getMetodoStampaEtichette());
		if ("printerJobBookPrintable".equalsIgnoreCase(properties.getMetodoStampaEtichette())) {
			LogWriter.writeLog("Utilizzo metodo di stampa printerJobBookPrintable");
			Book book = new Book();
			book.append(printable, pf, lPdDocument.getNumberOfPages());
			printJob.setPageable(book);
			printJob.print();
		} else if ("printerJobBookPDFPrintable".equalsIgnoreCase(properties.getMetodoStampaEtichette())) {
			LogWriter.writeLog("Utilizzo metodo di stampa printerJobBookPDFPrintable");
			Book book = new Book();
			book.append(new PDFPrintable(lPdDocument), pf, lPdDocument.getNumberOfPages());
			printJob.setPageable(book);
			printJob.print();
		} else if ("docPrintJob".equalsIgnoreCase(properties.getMetodoStampaEtichette())) {
			LogWriter.writeLog("Utilizzo metodo di stampa docPrintJob");
			DocPrintJob docPrintJob = lPrintService.createPrintJob();
			JobCompleteMonitor monitor = new JobCompleteMonitor();
			docPrintJob.addPrintJobListener( monitor );
			if( flavor==null )
				flavor = DocFlavor.INPUT_STREAM.AUTOSENSE; 
			if( pras== null )
				pras = new HashPrintRequestAttributeSet();
			try {
				monitor.setCompleted(false);
				FileInputStream fis = new FileInputStream(pdfFile);
				Doc mydoc = new SimpleDoc(fis, flavor, null);
				docPrintJob.print(mydoc, pras);
				monitor.waitForJobCompletion();
				fis.close();
			} catch (PrintException e1) {
				LogWriter.writeLog(e1.getMessage(), e1);
			} catch (FileNotFoundException e1) {
				LogWriter.writeLog(e1.getMessage(), e1);
			} catch (IOException e1) {
				LogWriter.writeLog(e1.getMessage(), e1);
			}
		}else if ("printerJobPageable".equalsIgnoreCase(properties.getMetodoStampaEtichette())) {
			LogWriter.writeLog("Utilizzo metodo di stampa printerJobPageable");
			try {
				printJob.setPageable(new PDFPageable(lPdDocument));
				printJob.print();
			} catch (PrinterException e) {
				LogWriter.writeLog(e.getMessage(), e);
			}  
		}else if ("printerJobPDFPrintable".equalsIgnoreCase(properties.getMetodoStampaEtichette())) {
			LogWriter.writeLog("Utilizzo metodo di stampa printerJobPDFPrintable");
			printJob.setPrintable(new PDFPrintable(lPdDocument), pf);
			printJob.print();
		} else {
			LogWriter.writeLog("Utilizzo metodo di stampa printerJobPrintable di default");
			printJob.setPrintable(printable, pf);
			printJob.print();
		}

	}

	public static PrintService mostraProprietaStampante(String nomeStampante) {
		flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		pras = new HashPrintRequestAttributeSet();

		PrintService printServices[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
		LogWriter.writeLog("Print Service:" + printServices);

		PrintService defaultService = null;
		for (PrintService printService : printServices) {
			if (printService.getName().equalsIgnoreCase(nomeStampante)) {
				defaultService = printService;
			}
		}

		PrintService service = ServiceUI.printDialog(null, 200, 200, printServices, defaultService, flavor, pras);
		return service;
	}

	public static PrintService getService(String name) throws PrintException {

		flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		pras = new HashPrintRequestAttributeSet();
		PrintService printServices[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
		LogWriter.writeLog("Print Services:" + printServices);

		LogWriter.writeLog("Cerco la stampante con nome " + name);

		PrintService defaultService = null;
		for (PrintService printService : printServices) {
			if (printService.getName().equalsIgnoreCase(name)) {
				defaultService = printService;
			}
		}

		if (defaultService != null) {
			LogWriter.writeLog("Trovata");
		} else {
			String message = gestisciErroreStampanti(name);
			throw new PrintException(message);
		}
		return defaultService;
	}

	private static String gestisciErroreStampanti(String name) {
		PrintService[] printServices_ = PrintServiceLookup.lookupPrintServices(null, null);
		StringBuffer lStringBuffer = new StringBuffer("Attenzione! Seleziona una stampante collegata tra quelle disponibili: ");
		boolean first = true;
		for (PrintService lPrintService : printServices_) {
			if (first)
				first = false;
			else
				lStringBuffer.append(" - ");
			lStringBuffer.append(lPrintService.getName());
		}
		return lStringBuffer.toString();
	}

	private static class JobCompleteMonitor extends PrintJobAdapter {

		private boolean completed = false;

		@Override
		public void printJobCanceled(PrintJobEvent pje) {
			LogWriter.writeLog("JobCanceled");
			signalCompletion();
		}

		@Override
		public void printJobCompleted(PrintJobEvent pje) {
			LogWriter.writeLog("JobCompleted");
			signalCompletion();
		}

		@Override
		public void printJobFailed(PrintJobEvent pje) {
			LogWriter.writeLog("JobFailed");
			signalCompletion();
		}

		@Override
		public void printJobNoMoreEvents(PrintJobEvent pje) {
			LogWriter.writeLog("JobNoMoreEvents");
			signalCompletion();
		}

		private void signalCompletion() {

			synchronized (JobCompleteMonitor.this) {
				completed = true;
				JobCompleteMonitor.this.notify();
			}
		}

		public synchronized void waitForJobCompletion() {
			try {
				while (!completed) {
					wait();
					Thread.sleep(5000);
				}
			} catch (InterruptedException e) {
			}
		}

		public boolean isCompleted() {
			return completed;
		}

		public void setCompleted(boolean completed) {
			this.completed = completed;
		}

	}

}
