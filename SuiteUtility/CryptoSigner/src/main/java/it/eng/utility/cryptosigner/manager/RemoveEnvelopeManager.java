package it.eng.utility.cryptosigner.manager;

import it.eng.utility.cryptosigner.controller.ISignerController;
import it.eng.utility.cryptosigner.controller.MasterSignerController;
import it.eng.utility.cryptosigner.controller.bean.InputSignerBean;
import it.eng.utility.cryptosigner.controller.bean.OutputSignerBean;
import it.eng.utility.cryptosigner.controller.exception.ExceptionController;
import it.eng.utility.cryptosigner.controller.impl.signature.RecursiveContentExtractionController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;


public class RemoveEnvelopeManager {
	private static final Logger log = Logger.getLogger(RemoveEnvelopeManager.class);
	
	public File estractFile(File file){
		File f=null;
		OutputSignerBean o=execute(file);
		if(o!=null && o.getProperty(RecursiveContentExtractionController.EXTRACTED_FILE)!=null){
			f=(File)o.getProperty(RecursiveContentExtractionController.EXTRACTED_FILE);
		}
		return f;
	}
	
	public OutputSignerBean execute(File file){
		 
		MasterSignerController sc= new MasterSignerController();
		List<ISignerController> ctrls= new ArrayList<ISignerController>();
		//ContentExtraction ce = new ContentExtraction();
		RecursiveContentExtractionController ce = new RecursiveContentExtractionController();

		ctrls.add(ce);
		sc.setControllers(ctrls);
		InputSignerBean isb = new InputSignerBean();
		 
		//File file = new File("C:/spazilavoro/aurigaserv/CryptoSigner/src/test/resources/examples/M7M/1Firma_CAScaduta.pdf.m7m");
		 
		try {
			isb.setEnvelopeStream(FileUtils.openInputStream(file));
		} catch (IOException e) {
			log.fatal("file inesistente",e);
		}
		OutputSignerBean osb=null;
		try {
			  osb=sc.executeControll(isb, true);
		} catch (ExceptionController e) {
			log.fatal("fatal executing controller",e);
		}
		return osb;
	}
}
