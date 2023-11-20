/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.XmlVariabile;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrdineDocumentoIn extends CreaModDocumentoInBean {

	@XmlVariabile(nome="ORD_ID_FILE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String idFileNSO;
	@TipoData(tipo=Tipo.DATA)
	@XmlVariabile(nome="ORD_DT_TRASM_SDI_Doc", tipo = TipoVariabile.SEMPLICE)
	private Date dtTrasmNSO;
	@XmlVariabile(nome="ORD_ERR_TRASM_SDI_Doc", tipo = TipoVariabile.SEMPLICE)
	private String errTrasmNSO;
	@XmlVariabile(nome="FLG_FALLITA_CALLBACK_Doc", tipo=TipoVariabile.SEMPLICE)
	private Flag fallitaCallback;
	
	public String getIdFileNSO() {
		return idFileNSO;
	}
	public void setIdFileNSO(String idFileNSO) {
		this.idFileNSO = idFileNSO;
	}
	public Date getDtTrasmNSO() {
		return dtTrasmNSO;
	}
	public void setDtTrasmNSO(Date dtTrasmNSO) {
		this.dtTrasmNSO = dtTrasmNSO;
	}
	public String getErrTrasmNSO() {
		return errTrasmNSO;
	}
	public void setErrTrasmNSO(String errTrasmNSO) {
		this.errTrasmNSO = errTrasmNSO;
	}
	public Flag getFallitaCallback() {
		return fallitaCallback;
	}
	public void setFallitaCallback(Flag fallitaCallback) {
		this.fallitaCallback = fallitaCallback;
	}

}
