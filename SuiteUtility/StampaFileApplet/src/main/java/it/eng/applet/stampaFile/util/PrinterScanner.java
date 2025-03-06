package it.eng.applet.stampaFile.util;

import java.util.ArrayList;
import java.util.List;

import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterName;

public class PrinterScanner {

	/**
	 * Recupera la lista di tutte le stampanti disponibili
	 * @return Lista delle stampanti
	 */
	public List<String> printerAvailable(){
		LogWriter.writeLog("Cerco le stampanti disponibili");
		List<String> printers = new ArrayList<String>();
		PrintService[] printServices_ = PrintServiceLookup.lookupPrintServices(null, null); 
		printers.add("Seleziona stampante");
		for (PrintService lPrintService : printServices_){
			printers.add(lPrintService.getName());
		}
		LogWriter.writeLog("Stampanti disponibili: " + printers);
		return printers;
	}
	
//	public static void print(File pdfFile, String nomeStampante) throws PrintException, PrinterException,IOException {
//		PrintService lPrintService = getService( nomeStampante );
//		PDDocument lPdDocument = PDDocument.load(pdfFile);
//		PrinterJob printJob = PrinterJob.getPrinterJob();
//		printJob.setPrintService(lPrintService);
//		lPdDocument.silentPrint(printJob);
//		
//	}
	
	public static PrintService getService(String name) throws PrintException {
		PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
		printServiceAttributeSet.add(new PrinterName(name, null));
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, printServiceAttributeSet);
		System.out.println("Cerco la stampante con nome " + name);
		if (printServices.length == 1)
			System.out.println("Trovata");

		else {
			String message = gestisciErroreStampanti(name);
			throw new PrintException(message);
		}
		return printServices[0];
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
	
}
