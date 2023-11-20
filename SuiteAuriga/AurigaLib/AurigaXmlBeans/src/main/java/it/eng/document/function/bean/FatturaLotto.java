/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FatturaLotto implements Serializable{

	@NumeroColonna(numero = "1")
	private String nroFattura;
	@NumeroColonna(numero = "2")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataFattura;
	@NumeroColonna(numero = "3")
	private String tipoDocumento;
	@NumeroColonna(numero = "4")
	private String nroOda;
	@NumeroColonna(numero = "5")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataOda;	
	@NumeroColonna(numero = "6")
	private String cup;
	@NumeroColonna(numero = "7")
	private String cig;
	@NumeroColonna(numero = "8")
	private String importo;
	@NumeroColonna(numero = "9")
	private String divisa;
	@NumeroColonna(numero = "10")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataScadPagamento;
	
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
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
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
	public String getCup() {
		return cup;
	}
	public void setCup(String cup) {
		this.cup = cup;
	}
	public String getCig() {
		return cig;
	}
	public void setCig(String cig) {
		this.cig = cig;
	}
	public String getImporto() {
		return importo;
	}
	public void setImporto(String importo) {
		this.importo = importo;
	}
	public String getDivisa() {
		return divisa;
	}
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	public Date getDataScadPagamento() {
		return dataScadPagamento;
	}
	public void setDataScadPagamento(Date dataScadPagamento) {
		this.dataScadPagamento = dataScadPagamento;
	}
	
	
}
