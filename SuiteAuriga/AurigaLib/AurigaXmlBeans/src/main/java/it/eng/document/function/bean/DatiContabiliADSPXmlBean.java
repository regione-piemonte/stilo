/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.List;

import it.eng.document.NumeroColonna;

public class DatiContabiliADSPXmlBean {

	@NumeroColonna(numero = "1")
	private String annoEsercizio;
	
	@NumeroColonna(numero = "2")
	private String capitolo;
	
	@NumeroColonna(numero = "3")
	private String conto;

	@NumeroColonna(numero = "4")
	private String codiceCIG;

	@NumeroColonna(numero = "5")
	private String codiceCUP;

	@NumeroColonna(numero = "6")
	private String importo;

	@NumeroColonna(numero = "7")
	private String opera;

	@NumeroColonna(numero = "8")
	private String flgEntrataUscita;

	@NumeroColonna(numero = "9")
	private String statoSistemaContabile;

	@NumeroColonna(numero = "10")
	private String operazioneSistemaContabile;

	@NumeroColonna(numero = "11")
	private String erroreSistemaContabile;
	
	@NumeroColonna(numero = "12")
	private String id;
	
	@NumeroColonna(numero = "13")
	private String keyCapitolo;
	
	@NumeroColonna(numero = "14")
	private String desOpera;
	
	@NumeroColonna(numero = "15")
	private String note;
	
	@NumeroColonna(numero = "16")
	private String imponibile;
	
	private String disponibilitaImporto;
	
	private String ultimoImportoAllineato;
	
	private String ultimoKeyCapitoloAllineato;

	private String flgDisattivaIntegrazioneSistemaContabile;
	
	private HashMap<String, String> mappaKeyCapitoli;
	
	private List<ProposteConcorrenti> listaProposteConcorrenti; 

	public String getAnnoEsercizio() {
		return annoEsercizio;
	}

	public void setAnnoEsercizio(String annoEsercizio) {
		this.annoEsercizio = annoEsercizio;
	}
	
	public String getCapitolo() {
		return capitolo;
	}

	public void setCapitolo(String capitolo) {
		this.capitolo = capitolo;
	}

	public String getConto() {
		return conto;
	}

	public void setConto(String conto) {
		this.conto = conto;
	}

	public String getCodiceCIG() {
		return codiceCIG;
	}

	public void setCodiceCIG(String codiceCIG) {
		this.codiceCIG = codiceCIG;
	}

	public String getCodiceCUP() {
		return codiceCUP;
	}

	public void setCodiceCUP(String codiceCUP) {
		this.codiceCUP = codiceCUP;
	}

	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}

	public String getOpera() {
		return opera;
	}

	public void setOpera(String opera) {
		this.opera = opera;
	}

	public String getFlgEntrataUscita() {
		return flgEntrataUscita;
	}

	public void setFlgEntrataUscita(String flgEntrataUscita) {
		this.flgEntrataUscita = flgEntrataUscita;
	}

	public String getStatoSistemaContabile() {
		return statoSistemaContabile;
	}

	public void setStatoSistemaContabile(String statoSistemaContabile) {
		this.statoSistemaContabile = statoSistemaContabile;
	}

	public String getOperazioneSistemaContabile() {
		return operazioneSistemaContabile;
	}

	public void setOperazioneSistemaContabile(String operazioneSistemaContabile) {
		this.operazioneSistemaContabile = operazioneSistemaContabile;
	}

	public String getErroreSistemaContabile() {
		return erroreSistemaContabile;
	}

	public void setErroreSistemaContabile(String erroreSistemaContabile) {
		this.erroreSistemaContabile = erroreSistemaContabile;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeyCapitolo() {
		return keyCapitolo;
	}

	public void setKeyCapitolo(String keyCapitolo) {
		this.keyCapitolo = keyCapitolo;
	}

	public String getDesOpera() {
		return desOpera;
	}

	public void setDesOpera(String desOpera) {
		this.desOpera = desOpera;
	}

	public String getUltimoImportoAllineato() {
		return ultimoImportoAllineato;
	}

	public String getUltimoKeyCapitoloAllineato() {
		return ultimoKeyCapitoloAllineato;
	}

	public void setUltimoKeyCapitoloAllineato(String ultimoKeyCapitoloAllineato) {
		this.ultimoKeyCapitoloAllineato = ultimoKeyCapitoloAllineato;
	}

	public void setUltimoImportoAllineato(String ultimoImportoAllineato) {
		this.ultimoImportoAllineato = ultimoImportoAllineato;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getImponibile() {
		return imponibile;
	}

	public void setImponibile(String imponibile) {
		this.imponibile = imponibile;
	}

	public String getFlgDisattivaIntegrazioneSistemaContabile() {
		return flgDisattivaIntegrazioneSistemaContabile;
	}

	public void setFlgDisattivaIntegrazioneSistemaContabile(String flgDisattivaIntegrazioneSistemaContabile) {
		this.flgDisattivaIntegrazioneSistemaContabile = flgDisattivaIntegrazioneSistemaContabile;
	}

	public HashMap<String, String> getMappaKeyCapitoli() {
		return mappaKeyCapitoli;
	}

	public void setMappaKeyCapitoli(HashMap<String, String> mappaKeyCapitoli) {
		this.mappaKeyCapitoli = mappaKeyCapitoli;
	}

	public List<ProposteConcorrenti> getListaProposteConcorrenti() {
		return listaProposteConcorrenti;
	}

	public void setListaProposteConcorrenti(List<ProposteConcorrenti> listaProposteConcorrenti) {
		this.listaProposteConcorrenti = listaProposteConcorrenti;
	}

	public String getDisponibilitaImporto() {
		return disponibilitaImporto;
	}

	public void setDisponibilitaImporto(String disponibilitaImporto) {
		this.disponibilitaImporto = disponibilitaImporto;
	}
		
}
