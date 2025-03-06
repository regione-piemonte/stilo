package it.eng.utility.pdfUtility.services;

import java.io.File;
import java.io.InputStream;

import it.eng.utility.pdfUtility.services.client.XmlSpecificheBean;

public interface StaticizzazionePdfXfaFormService {

	public InputStream staticizzaFile(File fileDaStaticizzare, XmlSpecificheBean xmlSpecifiche);
	
}
