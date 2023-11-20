/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.fileOperation.clientws.ImageFile;

@XmlRootElement
public class InfoFirmaGraficaBean implements Serializable {

	@NumeroColonna(numero = "1")
	private String ruolo;
	@NumeroColonna(numero = "2")
	private String testo;
	@NumeroColonna(numero = "3")
	private Integer areaVerticale;
	@NumeroColonna(numero = "4")
	private Integer areaOrizzontale;
	@NumeroColonna(numero = "5")
	private Integer nroPagina;
	@NumeroColonna(numero = "6")
	private String titolareFirma;
	@NumeroColonna(numero = "7")
	private String codiceActivityFirma;
	@NumeroColonna(numero = "8")
	private String idUtenteLavoroFirma;
	@NumeroColonna(numero = "9")
	private String idUtenteLoggatoFirma;

	private Integer coordinataXRiquadroFirma;
	private Integer coordinataYRiquadroFirma;
	private Integer ampiezzaRiquadroFirma;
	private Integer altezzaRiquadroFirma;
	private Integer pagina;
	private ImageFile immagine;
	private Integer nroPagineFirmeGrafiche;
	// Flag che mi serve per tenere traccia se questa firma è in fase di esecuzione
	private boolean firmaInEsecuzione;
	// Flag che mi serve per tenere traccia se questa firma è già stata eseguita
	private boolean firmaEseguita;

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public Integer getAreaVerticale() {
		return areaVerticale;
	}

	public void setAreaVerticale(Integer areaVerticale) {
		this.areaVerticale = areaVerticale;
	}

	public Integer getAreaOrizzontale() {
		return areaOrizzontale;
	}

	public void setAreaOrizzontale(Integer areaOrizzontale) {
		this.areaOrizzontale = areaOrizzontale;
	}

	public Integer getNroPagina() {
		return nroPagina;
	}

	public void setNroPagina(Integer nroPagina) {
		this.nroPagina = nroPagina;
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

	public Integer getCoordinataXRiquadroFirma() {
		return coordinataXRiquadroFirma;
	}

	public void setCoordinataXRiquadroFirma(Integer coordinataXRiquadroFirma) {
		this.coordinataXRiquadroFirma = coordinataXRiquadroFirma;
	}

	public Integer getCoordinataYRiquadroFirma() {
		return coordinataYRiquadroFirma;
	}

	public void setCoordinataYRiquadroFirma(Integer coordinataYRiquadroFirma) {
		this.coordinataYRiquadroFirma = coordinataYRiquadroFirma;
	}

	public Integer getAmpiezzaRiquadroFirma() {
		return ampiezzaRiquadroFirma;
	}

	public void setAmpiezzaRiquadroFirma(Integer ampiezzaRiquadroFirma) {
		this.ampiezzaRiquadroFirma = ampiezzaRiquadroFirma;
	}

	public Integer getAltezzaRiquadroFirma() {
		return altezzaRiquadroFirma;
	}

	public void setAltezzaRiquadroFirma(Integer altezzaRiquadroFirma) {
		this.altezzaRiquadroFirma = altezzaRiquadroFirma;
	}

	public Integer getPagina() {
		return pagina;
	}

	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}

	public ImageFile getImmagine() {
		return immagine;
	}

	public void setImmagine(ImageFile immagine) {
		this.immagine = immagine;
	}

	public Integer getNroPagineFirmeGrafiche() {
		return nroPagineFirmeGrafiche;
	}

	public void setNroPagineFirmeGrafiche(Integer nroPagineFirmeGrafiche) {
		this.nroPagineFirmeGrafiche = nroPagineFirmeGrafiche;
	}
	
	public boolean isFirmaInEsecuzione() {
		return firmaInEsecuzione;
	}
	
	public void setFirmaInEsecuzione(boolean firmaInEsecuzione) {
		this.firmaInEsecuzione = firmaInEsecuzione;
	}

	public boolean isFirmaEseguita() {
		return firmaEseguita;
	}
	
	public void setFirmaEseguita(boolean firmaEseguita) {
		this.firmaEseguita = firmaEseguita;
	}

}