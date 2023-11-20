/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreaModContrattoInBean extends CreaModDocumentoInBean {

	private static final long serialVersionUID = -6609881844566442763L;
		
	@XmlVariabile(nome = "DATA_STIPULA_Doc", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataStipulaDoc;
	
	@XmlVariabile(nome = "NRO_CONTRATTO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String nroContrattoDoc;
	
	public Date getDataStipulaDoc() {
		return dataStipulaDoc;
	}

	public void setDataStipulaDoc(Date dataStipulaDoc) {
		this.dataStipulaDoc = dataStipulaDoc;
	}

	public String getNroContrattoDoc() {
		return nroContrattoDoc;
	}

	public void setNroContrattoDoc(String nroContrattoDoc) {
		this.nroContrattoDoc = nroContrattoDoc;
	}

}
