/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FatturaLottoAltreNum implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@NumeroColonna(numero = "1")
	private String nroFattura;
	@NumeroColonna(numero = "2")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataFattura;
	@NumeroColonna(numero = "3")
	private String siglaFattura;
	@NumeroColonna(numero = "4")
	private String nroOda;
	@NumeroColonna(numero = "5")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataOda;	
	@NumeroColonna(numero = "6")
	private String siglaOda;
	@NumeroColonna(numero = "7")
	private String numeroProtocollo;
	@NumeroColonna(numero = "8")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataProtocollo;
	
	
	public String getNroFattura() {
		return nroFattura;
	}
	public void setNroFattura(String nroFattura) {
		this.nroFattura = nroFattura;
	}
	public Date getDataFattura() {
		return dataFattura;
	}
	public void setDataFattura(Date dataFattura) {
		this.dataFattura = dataFattura;
	}
	public String getSiglaFattura() {
		return siglaFattura;
	}
	public void setSiglaFattura(String siglaFattura) {
		this.siglaFattura = siglaFattura;
	}
	public String getNroOda() {
		return nroOda;
	}
	public void setNroOda(String nroOda) {
		this.nroOda = nroOda;
	}
	public Date getDataOda() {
		return dataOda;
	}
	public void setDataOda(Date dataOda) {
		this.dataOda = dataOda;
	}
	public String getSiglaOda() {
		return siglaOda;
	}
	public void setSiglaOda(String siglaOda) {
		this.siglaOda = siglaOda;
	}
	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}
	public Date getDataProtocollo() {
		return dataProtocollo;
	}
	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}
	
}
