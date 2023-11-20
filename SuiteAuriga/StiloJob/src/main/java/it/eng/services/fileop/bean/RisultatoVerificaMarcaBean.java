/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.fileOperation.clientws.VerificationStatusType;

public class RisultatoVerificaMarcaBean {

	private VerificationStatusType esitoVerifica;
	private String messaggioErrore;
	private String dataMarca;
	private String formatoMarca;
	private String tsaName;
	
	public VerificationStatusType getEsitoVerifica() {
		return esitoVerifica;
	}
	public void setEsitoVerifica(VerificationStatusType esitoVerifica) {
		this.esitoVerifica = esitoVerifica;
	}
	
	public String getDataMarca() {
		return dataMarca;
	}
	public void setDataMarca(String dataMarca) {
		this.dataMarca = dataMarca;
	}
	public String getFormatoMarca() {
		return formatoMarca;
	}
	public void setFormatoMarca(String formatoMarca) {
		this.formatoMarca = formatoMarca;
	}
	public String getTsaName() {
		return tsaName;
	}
	public void setTsaName(String tsaName) {
		this.tsaName = tsaName;
	}
	public RisultatoVerificaMarcaBean() {
		super();
	}
	public String getMessaggioErrore() {
		return messaggioErrore;
	}
	public void setMessaggioErrore(String messaggioErrore) {
		this.messaggioErrore = messaggioErrore;
	}
	
	
	
}
