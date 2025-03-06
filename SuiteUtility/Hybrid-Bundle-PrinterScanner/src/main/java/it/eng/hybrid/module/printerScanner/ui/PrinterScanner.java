package it.eng.hybrid.module.printerScanner.ui;

import java.util.ArrayList;
import java.util.List;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

public class PrinterScanner {

	/**
	 * Recupera la lista di tutte le stampanti disponibili
	 * @return Lista delle stampanti
	 */
	public List<String> printerAvailable(){
		List<String> printers = new ArrayList<String>();
		PrintService[] printServices_ = PrintServiceLookup.lookupPrintServices(null, null); 
		for (PrintService lPrintService : printServices_){
			printers.add(lPrintService.getName());
		}
		return printers;
	}
}
