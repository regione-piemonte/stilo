/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

@XmlRootElement
public class Firmatario implements Serializable {

	private static final long serialVersionUID = -1915478160718344009L;
	
	@NumeroColonna(numero = "1")
	private String commonName;
	@NumeroColonna(numero = "2")
	@TipoData(tipo = Tipo.DATA)
	private Date dataOraFirma;
	@NumeroColonna(numero = "3")
	private Flag firmatoDaAuriga;
	@NumeroColonna(numero = "4")
	@TipoData(tipo = Tipo.DATA)
	private Date dataOraVerificaFirma;
	@NumeroColonna(numero = "5")
	private Flag firmaNonValida;
	@NumeroColonna(numero = "6")
	private String infoFirma;
	@NumeroColonna(numero = "7")
	private String tipoFirma;
	@NumeroColonna(numero = "8")
	private Flag bustaEsterna;
	@NumeroColonna(numero = "9")
	@TipoData(tipo = Tipo.DATA)
	private Date dataOraEmissioneCertificatoFirma;
	@NumeroColonna(numero = "10")
	@TipoData(tipo = Tipo.DATA)
	private Date dataOraScadenzaCertificatoFirma;
	@NumeroColonna(numero = "11")
	private String tipoFirmaQA;
	@NumeroColonna(numero = "12")
	private String titolareFirma;
	@NumeroColonna(numero = "13")
	private String codiceActivityFirma;
	@NumeroColonna(numero = "14")
	private String idUtenteLavoroFirma;
	@NumeroColonna(numero = "15")
	private String idUtenteLoggatoFirma;
	
	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public Date getDataOraFirma() {
		return dataOraFirma;
	}

	public void setDataOraFirma(Date dataOraFirma) {
		this.dataOraFirma = dataOraFirma;
	}

	public Flag getFirmatoDaAuriga() {
		return firmatoDaAuriga;
	}

	public void setFirmatoDaAuriga(Flag firmatoDaAuriga) {
		this.firmatoDaAuriga = firmatoDaAuriga;
	}

	public Date getDataOraVerificaFirma() {
		return dataOraVerificaFirma;
	}

	public void setDataOraVerificaFirma(Date dataOraVerificaFirma) {
		this.dataOraVerificaFirma = dataOraVerificaFirma;
	}

	public Flag getFirmaNonValida() {
		return firmaNonValida;
	}

	public void setFirmaNonValida(Flag firmaNonValida) {
		this.firmaNonValida = firmaNonValida;
	}

	public String getInfoFirma() {
		return infoFirma;
	}

	public void setInfoFirma(String infoFirma) {
		this.infoFirma = infoFirma;
	}

	public String getTipoFirma() {
		return tipoFirma;
	}

	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

	public Flag getBustaEsterna() {
		return bustaEsterna;
	}

	public void setBustaEsterna(Flag bustaEsterna) {
		this.bustaEsterna = bustaEsterna;
	}
	
	public Date getDataOraEmissioneCertificatoFirma() {
		return dataOraEmissioneCertificatoFirma;
	}

	public void setDataOraEmissioneCertificatoFirma(Date dataOraEmissioneCertificatoFirma) {
		this.dataOraEmissioneCertificatoFirma = dataOraEmissioneCertificatoFirma;
	}

	public Date getDataOraScadenzaCertificatoFirma() {
		return dataOraScadenzaCertificatoFirma;
	}
	
	public void setDataOraScadenzaCertificatoFirma(Date dataOraScadenzaCertificatoFirma) {
		this.dataOraScadenzaCertificatoFirma = dataOraScadenzaCertificatoFirma;
	}

	public String getTipoFirmaQA() {
		return tipoFirmaQA;
	}

	public void setTipoFirmaQA(String tipoFirmaQA) {
		this.tipoFirmaQA = tipoFirmaQA;
	}

	public String getTitolareFirma() {
		return titolareFirma;
	}

	public void setTitolareFirma(String titolareFirma) {
		this.titolareFirma = titolareFirma;
	}
	
	public String getCodiceActivityFirma() {
		return codiceActivityFirma;
	}
	
	public void setCodiceActivityFirma(String codiceActivityFirma) {
		this.codiceActivityFirma = codiceActivityFirma;
	}

	public String getIdUtenteLavoroFirma() {
		return idUtenteLavoroFirma;
	}

	public void setIdUtenteLavoroFirma(String idUtenteLavoroFirma) {
		this.idUtenteLavoroFirma = idUtenteLavoroFirma;
	}

	public String getIdUtenteLoggatoFirma() {
		return idUtenteLoggatoFirma;
	}

	public void setIdUtenteLoggatoFirma(String idUtenteLoggatoFirma) {
		this.idUtenteLoggatoFirma = idUtenteLoggatoFirma;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}