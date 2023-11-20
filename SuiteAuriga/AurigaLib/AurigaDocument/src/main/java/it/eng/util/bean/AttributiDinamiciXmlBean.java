/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlAttributiCustom;
import it.eng.jaxb.variabili.SezioneCache;

public class AttributiDinamiciXmlBean  {	
		
	@XmlAttributiCustom
	private SezioneCache sezioneCacheAttributiDinamici;
	
	public SezioneCache getSezioneCacheAttributiDinamici() {
		return sezioneCacheAttributiDinamici;
	}
	public void setSezioneCacheAttributiDinamici(SezioneCache sezioneCacheAttributiDinamici) {
		this.sezioneCacheAttributiDinamici = sezioneCacheAttributiDinamici;
	}
}
