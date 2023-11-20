/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.XmlVariabile;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FatturaDocumentoIn extends CreaModDocumentoInBean {

	@XmlVariabile(nome="FATT_ID_SDI_Doc", tipo = TipoVariabile.SEMPLICE)
	private String idSdI;
	@TipoData(tipo=Tipo.DATA)
	@XmlVariabile(nome="FATT_DT_TRASM_SDI_Doc", tipo = TipoVariabile.SEMPLICE)
	private Date dtTrasmSdI;
	@XmlVariabile(nome="FATT_ERR_TRASM_SDI_Doc", tipo = TipoVariabile.SEMPLICE)
	private String errTrasmSdI;
	@XmlVariabile(nome="FLG_FALLITA_CALLBACK_Doc", tipo=TipoVariabile.SEMPLICE)
	private Flag fallitaCallback;
	
	public String getIdSdI() {
		return idSdI;
	}
	public void setIdSdI(String idSdI) {
		this.idSdI = idSdI;
	}
	public Date getDtTrasmSdI() {
		return dtTrasmSdI;
	}
	public void setDtTrasmSdI(Date dtTrasmSdI) {
		this.dtTrasmSdI = dtTrasmSdI;
	}
	public String getErrTrasmSdI() {
		return errTrasmSdI;
	}
	public void setErrTrasmSdI(String errTrasmSdI) {
		this.errTrasmSdI = errTrasmSdI;
	}
	public Flag getFallitaCallback() {
		return fallitaCallback;
	}
	public void setFallitaCallback(Flag fallitaCallback) {
		this.fallitaCallback = fallitaCallback;
	}

}
