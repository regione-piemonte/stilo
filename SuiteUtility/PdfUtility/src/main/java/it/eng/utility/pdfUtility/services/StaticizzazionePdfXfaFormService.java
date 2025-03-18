/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.InputStream;

import it.eng.utility.pdfUtility.services.client.XmlSpecificheBean;

public interface StaticizzazionePdfXfaFormService {

	public InputStream staticizzaFile(File fileDaStaticizzare, XmlSpecificheBean xmlSpecifiche);
	
}
