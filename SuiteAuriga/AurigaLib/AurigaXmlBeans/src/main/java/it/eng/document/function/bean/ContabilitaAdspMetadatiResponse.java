/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

public class ContabilitaAdspMetadatiResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String progressivo;
	private String numeroDocumento;
	private Date dataRegistrazione;
	private String codnrSettiva;
	private Date dataDocumento;
	private Integer tipoDocumento;
	private String ragsocDestinatario;
	private String codfisDestinatario;
	private String partivaDestinatario;
	private String oggettoFornitura;
	private Integer progInterno;
	private Integer annoInterno;
	private Double importo;
	private Integer statoConsegnaSdi;
	private Integer fatturaRicevuta;
	private String uuid;
	public String getProgressivo() {
		return progressivo;
	}
	public void setProgressivo(String progressivo) {
		this.progressivo = progressivo;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public Date getDataRegistrazione() {
		return dataRegistrazione;
	}
	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}
	public String getCodnrSettiva() {
		return codnrSettiva;
	}
	public void setCodnrSettiva(String codnrSettiva) {
		this.codnrSettiva = codnrSettiva;
	}
	public Date getDataDocumento() {
		return dataDocumento;
	}
	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}
	public Integer getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(Integer tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getRagsocDestinatario() {
		return ragsocDestinatario;
	}
	public void setRagsocDestinatario(String ragsocDestinatario) {
		this.ragsocDestinatario = ragsocDestinatario;
	}
	public String getCodfisDestinatario() {
		return codfisDestinatario;
	}
	public void setCodfisDestinatario(String codfisDestinatario) {
		this.codfisDestinatario = codfisDestinatario;
	}
	public String getPartivaDestinatario() {
		return partivaDestinatario;
	}
	public void setPartivaDestinatario(String partivaDestinatario) {
		this.partivaDestinatario = partivaDestinatario;
	}
	public String getOggettoFornitura() {
		return oggettoFornitura;
	}
	public void setOggettoFornitura(String oggettoFornitura) {
		this.oggettoFornitura = oggettoFornitura;
	}
	public Integer getProgInterno() {
		return progInterno;
	}
	public void setProgInterno(Integer progInterno) {
		this.progInterno = progInterno;
	}
	public Integer getAnnoInterno() {
		return annoInterno;
	}
	public void setAnnoInterno(Integer annoInterno) {
		this.annoInterno = annoInterno;
	}
	public Double getImporto() {
		return importo;
	}
	public void setImporto(Double importo) {
		this.importo = importo;
	}
	public Integer getStatoConsegnaSdi() {
		return statoConsegnaSdi;
	}
	public void setStatoConsegnaSdi(Integer statoConsegnaSdi) {
		this.statoConsegnaSdi = statoConsegnaSdi;
	}
	public Integer getFatturaRicevuta() {
		return fatturaRicevuta;
	}
	public void setFatturaRicevuta(Integer fatturaRicevuta) {
		this.fatturaRicevuta = fatturaRicevuta;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Override
	public String toString() {
		return "ContabilitaAdspMetadatiResponse [progressivo=" + progressivo + ", numeroDocumento=" + numeroDocumento
				+ ", dataRegistrazione=" + dataRegistrazione + ", codnrSettiva=" + codnrSettiva + ", dataDocumento="
				+ dataDocumento + ", tipoDocumento=" + tipoDocumento + ", ragsocDestinatario=" + ragsocDestinatario
				+ ", codfisDestinatario=" + codfisDestinatario + ", partivaDestinatario=" + partivaDestinatario
				+ ", oggettoFornitura=" + oggettoFornitura + ", progInterno=" + progInterno + ", annoInterno="
				+ annoInterno + ", importo=" + importo + ", statoConsegnaSdi=" + statoConsegnaSdi + ", fatturaRicevuta="
				+ fatturaRicevuta + ", uuid=" + uuid + "]";
	}
	
}
