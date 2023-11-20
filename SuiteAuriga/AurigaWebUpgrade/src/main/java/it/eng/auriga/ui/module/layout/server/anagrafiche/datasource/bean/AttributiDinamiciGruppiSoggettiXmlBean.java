/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.common.datasource.bean.AttributiDinamiciXmlBean;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class AttributiDinamiciGruppiSoggettiXmlBean  extends AttributiDinamiciXmlBean {	

	@XmlVariabile(nome = "FLG_INIBITA_ASS", tipo=TipoVariabile.SEMPLICE)
	private String flgInibitaAss;

	public String getFlgInibitaAss() {
		return flgInibitaAss;
	}

	public void setFlgInibitaAss(String flgInibitaAss) {
		this.flgInibitaAss = flgInibitaAss;
	}
}