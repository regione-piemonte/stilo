/**
* ===========================================
* Java Pdf Extraction Decoding Access Library
* ===========================================
*
* Project Info:  http://www.jpedal.org
* (C) Copyright 1997-2008, IDRsolutions and Contributors.
*
* 	This file is part of JPedal
*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA


*
* ---------------
* Printer.java Original Version
* ---------------
*/
//package org.jpedal.examples.viewer.utils;

//import java.awt.*;
//import java.awt.color.ColorSpace;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.image.BufferedImage;
//import java.awt.print.PageFormat;
//import java.awt.print.Paper;
//import java.awt.print.PrinterException;
//import java.awt.print.PrinterJob;
//
//import javax.print.*;
//import javax.print.attribute.*;
//import javax.print.attribute.standard.Chromaticity;
//import javax.print.attribute.standard.Copies;
//import javax.print.attribute.standard.JobName;
//import javax.print.attribute.standard.PageRanges;
//
//import javax.print.attribute.standard.PrinterResolution;
//import javax.print.event.PrintJobEvent;
//import javax.print.event.PrintJobListener;
//import javax.swing.JOptionPane;
//import javax.swing.ProgressMonitor;
//import javax.swing.Timer;
//
//import org.jpedal.PdfDecoder;
//
//import org.jpedal.color.PdfPaint;
//import org.jpedal.examples.viewer.gui.popups.PrintPanel;
//import org.jpedal.exception.PdfException;
//import org.jpedal.external.ColorHandler;
//import org.jpedal.external.Options;
//import org.jpedal.gui.GUIFactory;
//import org.jpedal.objects.PrinterOptions;
//import org.jpedal.utils.LogWriter;
//import org.jpedal.utils.Messages;
//
//public class Printer {
//
//	public static boolean isPrinting() {
//		
//		return false;
//	}
//	/**/
//
//
//}



/**
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 *
 * Project Info:  http://www.jpedal.org
 * (C) Copyright 1997-2008, IDRsolutions and Contributors.
 *
 *  This file is part of JPedal
 *
    This source code is copyright IDRSolutions 2012,
 *
 * ---------------
 * Printer.java
 * ---------------
 */
package it.eng.hybrid.module.jpedal.util;

import it.eng.hybrid.module.jpedal.color.PdfPaint;
import it.eng.hybrid.module.jpedal.exception.PdfException;
import it.eng.hybrid.module.jpedal.external.ColorHandler;
import it.eng.hybrid.module.jpedal.messages.Messages;
import it.eng.hybrid.module.jpedal.objects.PrinterOptions;
import it.eng.hybrid.module.jpedal.pdf.PdfDecoder;
import it.eng.hybrid.module.jpedal.ui.SwingGUI;
import it.eng.hybrid.module.jpedal.ui.popup.PrintPanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.SetOfIntegerSyntax;
import javax.print.attribute.standard.Chromaticity;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.PageRanges;
import javax.print.attribute.standard.PrinterResolution;
import javax.print.event.PrintJobEvent;
import javax.print.event.PrintJobListener;
import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;
import javax.swing.Timer;

import org.apache.log4j.Logger;

/**
 * Implementazione della funzionalita' di stampa per JPedal LGPL.
 * <br>
 * Attualmente esistono due modalita' di stampa:
 * <br>
 * <br>
 * <ol>
 * <li>Una base, che utilizza il dialog di default del sistema operativo per consentire all'utente 
 *     di scegliere la modalita' di stampa desiderata;</li>
 * <li>Una JPedal, che utilizza un dialog Java Swing fornito dagli sviluppatori di JPedal per consentire all'utente 
 *     di scegliere la modalita' di stampa desiderata.</li>
 * </ol>
 * @author upescato
 *
 */ 

//Attualmente il codice e' implementato in modo da offrire la versione di stampa base poich� quella JPedalStyle ha qualche problemino
//relativo al dialog Swing, upescato 03.07.2012

/**{@see org.jpedal.examples.simpleviewer.Commands#printFile()}*/

public class Printer {

	public final static Logger logger = Logger.getLogger(Printer.class);
	
	//<start-gpl>
	/**flag to stop mutliple prints*/
	private static int printingThreads = 0;

	/**page range to print*/
	int rangeStart = 1, rangeEnd = 1;

	/**type of printing - all, odd, even*/
	int subset = PrinterOptions.ALL_PAGES;

	/**Check to see if Printing cancelled*/
	boolean wasCancelled = false;

	/**Allow Printing Cancelled to appear once*/
	boolean messageShown = false;

	boolean pagesReversed = false;

	/**provide user with visual clue to print progress*/
	Timer updatePrinterProgress = null;

	private ProgressMonitor status = null;

	/** needs to be global for the printer selection to work */
	private DocPrintJob printJob = null;

	// for use with fest testing
	public static boolean showOptionPane = true;
	
	//Nome del print job da usare in caso di assenza del nomefile
	private static final String NOME_STAMPA_DEFAULT = "Stampa_JPedal";

	// public static int count;
	
	/**
	 * Versione base del metodo di stampa.
	 * <br>
	 * Prende in input il nome del file che si intende stampare e il pdf attualmente contenuto in memoria.
	 * A partire da questi dati effettua la stampa successivamente all'apertura del popup di default per gestire le propriet� di stampa.
	 * <br><br>
	 * <i>� multithreaded</i>.
	 */
	public static void printPDFBasic(final PdfDecoder decode_pdf, final SwingGUI currentGUI, final String filename) throws PdfException, PrinterException  {

		//Flag atomico sulla stampa cos� non usciamo fino a quando non sono finite le stampe
		printingThreads++;

		Thread worker = new Thread() {

			public void run() {
				try {
					//Numero totale delle pagine nel PDF
					int pageCount = decode_pdf.getPageCount();  
					//Intervallo di pagine totale
					decode_pdf.setPagePrintRange(1, pageCount);  
					//Auto-rotate e scale flag  
					decode_pdf.setPrintAutoRotateAndCenter(false);  
					//Specifichiamo che non stiamo stampando solo l'area corrente.
					decode_pdf.setPrintCurrentView(false);  
					//set mode - see org.jpedal.objects.constants.PrinterOptions  
					//Il PDF � gi� nel formato richiesto, quindi non effettuiamo scaling 
					decode_pdf.setPrintPageScalingMode(PrinterOptions.PAGE_SCALING_NONE);  
					//di default lo scaling centra anche la pagina.
					decode_pdf.setCenterOnScaling(false);  
					//flag settato a true se usiamo la dimensione della carta o del PDF.  
					//Utilizzare formato PDF come avente gi� il formato carta desiderato  
					decode_pdf.setUsePDFPaperSize(true);  
					//Setup del job di stampa e degli oggetti  
					PrinterJob printJob = PrinterJob.getPrinterJob();
					PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
					printJob.setPrintService(defaultPrintService);
					if(StringUtils.isNotBlank(filename)) {
						printJob.setJobName(filename);
					}
					else
						printJob.setJobName(NOME_STAMPA_DEFAULT);
					//Setup del Java Print Service (JPS) per usarlo in JPedal  
					printJob.setPageable(decode_pdf);
					//Dopo aver chiuso il dialog di stampa selezionando l'operazione di stampa viene effettuata l'operazione
					//con la stampante selezionata
					if (printJob.printDialog()) {
						printJob.print();
					}

					printingThreads--;

				} catch (Exception ex) {
					logger.info("Exception " + ex + " printing");
					ex.printStackTrace();
					currentGUI.showMessageDialog("Si � verificato un errore durante le operazioni di stampa: " + ex);
				}
			} //chiude il run() del thread
		}; //chiude il new Thread

		//Esegue il thread di stampa in background
		worker.start();

	}  

	/**
	 * Metodo di stampa che mostra un dialog di JPedal,
	 * � una versione basata sugli esempi di JPedal, visualizza un popup Swing proprio di JPedal 
	 */
	public void printPDFJPedalStyle(final PdfDecoder decode_pdf, final SwingGUI currentGUI, final String blacklist, final String defaultPrinter, final boolean debugPrinting) {

		//provides atomic flag on printing so we don't exit until all done
		printingThreads++;

		/**
		 * printing in thread to improve background printing -
		 * comment out if not required
		 */
		Thread worker = new Thread() {

			public void run() {

				boolean printFile = false;
				try {

					PageFormat pf = PrinterJob.getPrinterJob().defaultPage();

					/**
					 * default page size
					 */
					Paper paper = new Paper();
					paper.setSize(595, 842);
					paper.setImageableArea(43, 43, 509, 756);

					pf.setPaper(paper);

					/**
					 * workaround to improve performance on PCL printing 
					 * by printing using drawString or Java's glyph if font
					 * available in Java
					 */
					//decode_pdf.setTextPrint(PdfDecoder.NOTEXTPRINT); //normal mode - only needed to reset
					//decode_pdf.setTextPrint(PdfDecoder.TEXTGLYPHPRINT); //intermediate mode - let Java create Glyphs if font matches
					//decode_pdf.setTextPrint(PdfDecoder.TEXTSTRINGPRINT); //try and get Java to do all the work

					//wrap in Doc as we can then add a listeners
					Doc doc = new SimpleDoc(decode_pdf, DocFlavor.SERVICE_FORMATTED.PAGEABLE, null);

					//setup default values to padd into JPS
					PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
					aset.add(new PageRanges(1, decode_pdf.getPageCount()));

					// useful debugging code to show supported values and values returned by printer
					//Attribute[] settings = aset.toArray();

					//Class[] attribs=printJob.getPrintService().getSupportedAttributeCategories();
					//for(int i=0;i<attribs.length;i++)
					//System.out.println(i+" "+attribs[i]);

					//for(int i=0;i<settings.length;i++) //show values set by printer
					//  System.out.println(i+" "+settings[i].toString()+" "+settings[i].getName());

					/**
					 * custom dialog so we can copy Acrobat PDF settings
					 * (removed from OS versions)
					 */
					/*
                    /**/
					PrintPanel printPanel = (PrintPanel) currentGUI.printDialog(getAvailablePrinters(blacklist), defaultPrinter, decode_pdf);

					printFile = printPanel.okClicked();

					//ensure PDF display reappears
					decode_pdf.repaint();

					// set values in JPS

					// choose the printer, testing if printer in list
					setPrinter(printPanel.getPrinter());

					//range of pages
					int printMode = 0;
					subset = PrinterOptions.ALL_PAGES;
					if (printPanel.isOddPagesOnly()) {
						printMode = PrinterOptions.ODD_PAGES_ONLY;
						subset = PrinterOptions.ODD_PAGES_ONLY;
					} else if (printPanel.isEvenPagesOnly()) {
						printMode = PrinterOptions.EVEN_PAGES_ONLY;
						subset = PrinterOptions.EVEN_PAGES_ONLY;
					}

					//flag to show reversed
					pagesReversed = printPanel.isPagesReversed();
					if (pagesReversed) {
						printMode = printMode + PrinterOptions.PRINT_PAGES_REVERSED;
					}

					decode_pdf.setPrintPageMode(printMode);

					//can also take values such as  new PageRanges("3,5,7-9,15");
					SetOfIntegerSyntax range = printPanel.getPrintRange();

					//store color handler in case it needs to be replaced for grayscale printing
					Object storedColorHandler = decode_pdf.getExternalHandler(Options.ColorHandler);

					if (range == null) {
						currentGUI.showMessageDialog("No pages to print");
					} else {
						decode_pdf.setPagePrintRange(range);

						// workout values for progress monitor
						rangeStart = range.next(0); // find first

						// find last
						int i = rangeStart;
						rangeEnd = rangeStart;
						if (range.contains(2147483647)) //allow for all returning largest int
						{
							rangeEnd = decode_pdf.getPageCount();
						} else {
							while (range.next(i) != -1) {
								i++;
							}
							rangeEnd = i;
						}

						//pass through number of copies
						aset.add(new Copies(printPanel.getCopies()));

						//Auto-rotate and scale flag
						decode_pdf.setPrintAutoRotateAndCenter(printPanel.isAutoRotateAndCenter());

						// Are we printing the current area only
						decode_pdf.setPrintCurrentView(printPanel.isPrintingCurrentView());

						//set mode - see org.jpedal.objects.contstants.PrinterOptions for all values
						decode_pdf.setPrintPageScalingMode(printPanel.getPageScaling());

						//Set whether to print in monochrome or full color
						if (printPanel.isMonochrome()) {
							aset.remove(Chromaticity.COLOR);
							aset.add(Chromaticity.MONOCHROME);
							decode_pdf.addExternalHandler(new ColorHandler() {
								public void setPaint(Graphics2D g2, PdfPaint textFillCol, int pageNumber, boolean isPrinting) {
									//converts to grayscale for printing
									if(isPrinting && textFillCol!=null){ //only on printout

										int rgb=textFillCol.getRGB();

										//get the value
										float[] val=new float[3];
										val[0]=((rgb>>16) & 255)/255f;
										val[1]=((rgb>>8) & 255)/255f;
										val[2]=(rgb & 255)/255f;

										//to gray
										ColorSpace cs=ColorSpace.getInstance(ColorSpace.CS_GRAY);
										float[] grayVal=cs.fromRGB(val);

										Color colGray= new Color(cs,grayVal,1f);
										g2.setPaint(colGray);

									}else
										g2.setPaint(textFillCol);
								}

								public BufferedImage processImage(BufferedImage image, int pageNumber, boolean isPrinting) {

									if(isPrinting && image != null){ //only on printout

										//grayscale conversion
										BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
										Graphics2D newG2=newImage.createGraphics();
										newG2.setPaint(Color.WHITE);
										newG2.fillRect(0,0,image.getWidth(), image.getHeight());
										newG2.drawImage(image,0,0,null);

										return newImage;
									}
									return image;
								}
							}, Options.ColorHandler);
						} else {
							aset.remove(Chromaticity.MONOCHROME);
							aset.add(Chromaticity.COLOR);
						}

						//set paper size
						if (printPanel.getSelectedPaper() != null) {
							pf.setPaper(printPanel.getSelectedPaper());
						}
						decode_pdf.setPageFormat(pf);

						// flag if we use paper size or PDF size
						decode_pdf.setUsePDFPaperSize(printPanel.isPaperSourceByPDFSize());

						//Set print resolution
						PrinterResolution res = printPanel.getResolution();
						if (res != null) {
							aset.add(res);
						}

					}

					/**/

					/**
					 * popup to show user progress
					 */
					if (showOptionPane) {
						status = new ProgressMonitor(currentGUI.getFrame(), "", "", 1, 100);

						/** used to track user stopping movement and call refresh every 2 seconds*/
						updatePrinterProgress = new Timer(1000, new ActionListener() {

							public void actionPerformed(ActionEvent event) {

								int currentPage = decode_pdf.getCurrentPrintPage();

								if (currentPage > 0) {
									updatePrinterProgess(decode_pdf, currentPage);
								}

								//make sure turned off
								if (currentPage == -1) {
									updatePrinterProgress.stop();
									status.close();
								}
							}
						});
						updatePrinterProgress.setRepeats(true);
						updatePrinterProgress.start();
					}

					//Name the print job the same as the Pdf file.
					String name=decode_pdf.getFileName();
					if(name==null){ //can be null if we pass in PDF as byte[] array
						name=NOME_STAMPA_DEFAULT;
					}else{
						String[] jobString = decode_pdf.getFileName().split("/");
						JobName jobName = new JobName(jobString[jobString.length-1], null);
						if(printJob.getPrintService().isAttributeValueSupported(jobName, DocFlavor.SERVICE_FORMATTED.PAGEABLE, aset)) {
							aset.add(jobName);
						} 
					}

					/**
					 * actual print call
					 */
					if (printFile) {
						//used to track print activity
						printJob.addPrintJobListener(new PDFPrintJobListener());

						//Print PDF document
						printJob.print(doc, aset);
					}

					//Restore color handler in case grayscale printing was used
					decode_pdf.addExternalHandler(storedColorHandler, Options.ColorHandler);

				} catch (PrinterException ee) {
					ee.printStackTrace();
					logger.info("Exception " + ee + " printing");
					currentGUI.showMessageDialog(ee.getMessage() + ' ' + ee + ' ' + ' ' + ee.getCause());
				} catch (Exception e) {
					logger.info("Exception " + e + " printing");
					e.printStackTrace();
					currentGUI.showMessageDialog("Exception " + e);
				} catch (Error err) {
					err.printStackTrace();
					logger.info("Error " + err + " printing");
					currentGUI.showMessageDialog("Error " + err);
				}

				/**
				 * visual print update progress box
				 */
				if (updatePrinterProgress != null) {
					updatePrinterProgress.stop();
					status.close();
				}
				/**report any or our errors 
				 * (we do it this way rather than via PrinterException as MAC OS X has a nasty bug in PrinterException)
				 */
				if (!printFile && !decode_pdf.isPageSuccessful()) {
					String errorMessage = Messages.getMessage("PdfViewerError.ProblemsEncountered") + decode_pdf.getPageFailureMessage() + '\n';

					if (decode_pdf.getPageFailureMessage().toLowerCase().contains("memory")) {
						errorMessage += Messages.getMessage("PdfViewerError.RerunJava")
						+ Messages.getMessage("PdfViewerError.RerunJava1")
						+ Messages.getMessage("PdfViewerError.RerunJava2");
					}

					currentGUI.showMessageDialog(errorMessage);
				}

				printingThreads--;

				//redraw to clean up
				decode_pdf.resetCurrentPrintPage();
				decode_pdf.invalidate();
				decode_pdf.updateUI();
				decode_pdf.repaint();



				if ((printFile && !wasCancelled)) {

					if (showOptionPane) {
						currentGUI.showMessageDialog(Messages.getMessage("PdfViewerPrintingFinished"));
					}


				}
			}
		};

		//start printing in background (comment out if not required)
		worker.start();

	}


	public static String[] getAvailablePrinters(String blacklist) {
		PrintService[] service=PrinterJob.lookupPrintServices();

		int noOfPrinters = service.length;
		String[] serviceNames = new String[noOfPrinters];

		//check blacklist
		if (blacklist != null) {
			String[] bl = blacklist.split(",");

			int count = 0;
			//loop through printservices
			for (PrintService aService : service) {
				boolean pass = true;
				String name = aService.getName();

				//loop through blacklist items
				for (String aBl : bl) {

					//check for wildcard
					if (aBl.contains("*")) {
						String term = aBl.replace("*", "").trim();
						if (name.contains(term))
							pass = false;

					} else if (name.toLowerCase().equals(aBl.toLowerCase()))
						pass = false;
				}

				//Add to array
				if (pass) {
					serviceNames[count] = name;
					count++;
				}
			}

			//Trim array
			String[] temp = serviceNames;
			serviceNames = new String[count];
			System.arraycopy(temp,0,serviceNames,0,count);
		} else {
			for(int i=0;i<noOfPrinters;i++)
				serviceNames[i] = service[i].getName();
		}

		return serviceNames;
	}


	/**visual print indicator*/
	private String dots=".";

	private void updatePrinterProgess(PdfDecoder decode_pdf,int currentPage) {
		//Calculate no of pages printing
		int noOfPagesPrinting=(rangeEnd-rangeStart+1);

		//Calculate which page we are currently printing
		int currentPrintingPage=(currentPage-rangeStart);

		int actualCount=noOfPagesPrinting;
		int actualPage=currentPrintingPage;
		int actualPercentage= (int) (((float)actualPage/(float)actualCount)*100); 


		if(status.isCanceled()){
			decode_pdf.stopPrinting();
			updatePrinterProgress.stop();
			status.close();
			wasCancelled=true;
			printingThreads--;

			if(!messageShown){
				JOptionPane.showMessageDialog(null,Messages.getMessage("PdfViewerPrint.PrintingCanceled"));
				messageShown=true;
			}
			return;

		}

		//update visual clue
		dots=dots+ '.';
		if(dots.length()>8)
			dots=".";

		//allow for backwards
		boolean isBackwards=((currentPrintingPage<=0));

		if(rangeStart==rangeEnd)
			isBackwards=false;

		if((isBackwards))
			noOfPagesPrinting=(rangeStart-rangeEnd+1);

		int percentage = (int) (((float)currentPrintingPage / (float)noOfPagesPrinting) * 100);

		if((!isBackwards)&&(percentage<1))
			percentage=1;


		//invert percentage so percentage works correctly
		if(isBackwards){
			percentage=-percentage;
			currentPrintingPage=-currentPrintingPage;
		}

		if(pagesReversed)
			percentage=100-percentage;

		status.setProgress(percentage);
		String message="";

		if(subset==PrinterOptions.ODD_PAGES_ONLY){
			actualCount=((actualCount/2)+1);
			actualPage=actualPage/2;

		}else if(subset==PrinterOptions.EVEN_PAGES_ONLY){
			actualCount=((actualCount/2)+1);
			actualPage=actualPage/2;

		}

		/*
		 * allow for printing 1 page 
		 * Set to page 1 of 1 like Adobe
		 */
		if (actualCount==1){
			actualPercentage=50;
			actualPage=1;
			status.setProgress(actualPercentage);
		}

		message=actualPage + " "+Messages.getMessage("PdfViewerPrint.Of")+ ' ' +
		actualCount + ": " + actualPercentage + '%' + ' ' +dots;

		if(pagesReversed){
			message=(actualCount-actualPage) + " "+Messages.getMessage("PdfViewerPrint.Of")+ ' ' +
			actualCount + ": " + percentage + '%' + ' ' +dots;

			status.setNote(Messages.getMessage("PdfViewerPrint.ReversedPrinting")+ ' ' + message);

		}else if(isBackwards)
			status.setNote(Messages.getMessage("PdfViewerPrint.ReversedPrinting")+ ' ' + message);
		else
			status.setNote(Messages.getMessage("PdfViewerPrint.Printing")+ ' ' + message);

	}

	public static boolean isPrinting() {  
		return printingThreads>0;
	}


	private void setPrinter(String chosenPrinter) throws PrinterException, PdfException {

		PrintService[] service=PrinterJob.lookupPrintServices(); //list of printers

		int count=service.length;
		boolean matchFound=false;

		for(int i=0;i<count;i++){
			if(service[i].getName().contains(chosenPrinter)){
				printJob= service[i].createPrintJob();
				i=count;
				matchFound=true;
			}
		}
		if(!matchFound)
			throw new PdfException("Unknown printer "+chosenPrinter);
	}

	/**
	 * listener code - just an example
	 */
	private static class PDFPrintJobListener implements PrintJobListener {

		static final private boolean showMessages=false;

		public void printDataTransferCompleted(PrintJobEvent printJobEvent) {
			if(showMessages)
				System.out.println("printDataTransferCompleted="+printJobEvent.toString());
		}

		public void printJobCompleted(PrintJobEvent printJobEvent) {
			if(showMessages)
				System.out.println("printJobCompleted="+printJobEvent.toString());
		}

		public void printJobFailed(PrintJobEvent printJobEvent) {
			if(showMessages)
				System.out.println("printJobEvent="+printJobEvent.toString());
		}

		public void printJobCanceled(PrintJobEvent printJobEvent) {
			if(showMessages)
				System.out.println("printJobFailed="+printJobEvent.toString());
		}

		public void printJobNoMoreEvents(PrintJobEvent printJobEvent) {
			if(showMessages)
				System.out.println("printJobNoMoreEvents="+printJobEvent.toString());
		}

		public void printJobRequiresAttention(PrintJobEvent printJobEvent) {
			if(showMessages)
				System.out.println("printJobRequiresAttention="+printJobEvent.toString());
		}
	}
	/**
    //<end-gpl>
  public static boolean isPrinting() {

    return false;
  }
  /**/


	public void dispose(){
		if( updatePrinterProgress!=null )
			updatePrinterProgress.stop();
		updatePrinterProgress = null;

//		if(status!=null)
//			status.
		status = null;

		printJob = null;

	}
}
