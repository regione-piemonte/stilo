/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.text.SimpleDateFormat;
import java.util.Map;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.xml.DeserializationHelper;

/**
 * 
 * @author DANCRIST
 *
 */

public class ContenutoFoglioImportatoXmlBeanDeserializationHelper extends DeserializationHelper {

	public ContenutoFoglioImportatoXmlBeanDeserializationHelper(Map<String, String> remapConditions) {
		super(remapConditions);
	}

	@Override
	public void remapValues(Object obj, Riga riga) throws Exception {
		
		ContenutoFoglioImportatoXmlBean bean = (ContenutoFoglioImportatoXmlBean) obj;	
		
		String tsUpload = getColonnaContent(riga, 5);
		bean.setTsUpload((tsUpload != null && !tsUpload.equalsIgnoreCase("")) ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(tsUpload) : null);
		
		String tsInizioElab = getColonnaContent(riga, 10);
		bean.setTsInizioElab((tsInizioElab != null && !tsInizioElab.equalsIgnoreCase("")) ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(tsInizioElab) : null);
		
		String tsFineElab = getColonnaContent(riga, 11);
		bean.setTsFineElab((tsFineElab != null && !tsFineElab.equalsIgnoreCase("")) ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(tsFineElab) : null);
		
		String tsInsRiga = getColonnaContent(riga, 19);
		bean.setTsInsRiga((tsInsRiga != null && !tsInsRiga.equalsIgnoreCase("")) ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(tsInsRiga) : null);
		
		String tsUltimaElabRiga = getColonnaContent(riga, 21);
		bean.setTsUltimaElabRiga((tsUltimaElabRiga != null && !tsUltimaElabRiga.equalsIgnoreCase("")) ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(tsUltimaElabRiga) : null);
		
	}

}