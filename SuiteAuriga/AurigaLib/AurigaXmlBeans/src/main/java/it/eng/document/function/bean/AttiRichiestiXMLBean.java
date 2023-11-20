/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

@XmlRootElement
public class AttiRichiestiXMLBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@NumeroColonna(numero = "1")
	private String tipoProtocollo;

	@NumeroColonna(numero = "2")
	private String numeroProtocollo;

	@NumeroColonna(numero = "3")
	private String regProtocolloDiSettore;

	@NumeroColonna(numero = "4")
	private String annoProtocollo;

	@NumeroColonna(numero = "5")
	private String subProtocolloDiSettore;

	@NumeroColonna(numero = "6")
	private String statoScansione;

	@NumeroColonna(numero = "7")
	private String idFolder;

	@NumeroColonna(numero = "8")
	private String stato;

	@NumeroColonna(numero = "9")
	private String udc;

	@NumeroColonna(numero = "10")
	private String denUffPrelievo;

	@NumeroColonna(numero = "11")
	private String codUffPrelievo;

	@NumeroColonna(numero = "12")
	private String idUoPrelievo;

	@NumeroColonna(numero = "13")
	private String cognomeRespPrelievo;

	@NumeroColonna(numero = "14")
	private String nomeRespPrelievo;

	@NumeroColonna(numero = "15")
	private String usernameRespPrelievo;

	@NumeroColonna(numero = "16")
	private String idUserRespPrelievo;

	@NumeroColonna(numero = "17")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataPrelievo;

	@NumeroColonna(numero = "18")
	private String noteUffRichiedente;

	@NumeroColonna(numero = "19")
	private String noteCittadella;
	
	@NumeroColonna(numero = "20")
	private String classifica;
	
	@NumeroColonna(numero = "21")
	private String statoAttoDaSincronizzare;
	
	@NumeroColonna(numero = "22")
	private String competenzaDiUrbanistica;
	
	@NumeroColonna(numero = "23")
	private String cartaceoReperibile;
	
	@NumeroColonna(numero = "24")
	private String inArchivio;
	
	@NumeroColonna(numero = "25")
	private String flgRichiestaVisioneCartaceo;
	
	@NumeroColonna(numero = "26")
	private String tipoFascicolo;
	
	@NumeroColonna(numero = "27")
	private String annoProtEdiliziaPrivata;
	
	@NumeroColonna(numero = "28")
	private String numeroProtEdiliziaPrivata;
	
	@NumeroColonna(numero = "29")
	private String annoWorkflow;
	
	@NumeroColonna(numero = "30")
	private String numeroWorkflow;
	
	@NumeroColonna(numero = "31")
	private String numeroDeposito;
	
	@NumeroColonna(numero = "32")
	private String tipoComunicazione;
	
	@NumeroColonna(numero = "33")
	private String noteSportello;
	
	@NumeroColonna(numero = "34")
	private String visureCollegate;
	
	@NumeroColonna(numero = "35")
	private String desInArchivio;
	
	@NumeroColonna(numero = "36")
	private String desTipoFascicolo;
	
	@NumeroColonna(numero = "37")
	private String desTipoComunicazione;

	public String getTipoProtocollo() {
		return tipoProtocollo;
	}

	public void setTipoProtocollo(String tipoProtocollo) {
		this.tipoProtocollo = tipoProtocollo;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getRegProtocolloDiSettore() {
		return regProtocolloDiSettore;
	}

	public void setRegProtocolloDiSettore(String regProtocolloDiSettore) {
		this.regProtocolloDiSettore = regProtocolloDiSettore;
	}

	public String getAnnoProtocollo() {
		return annoProtocollo;
	}

	public void setAnnoProtocollo(String annoProtocollo) {
		this.annoProtocollo = annoProtocollo;
	}

	public String getSubProtocolloDiSettore() {
		return subProtocolloDiSettore;
	}

	public void setSubProtocolloDiSettore(String subProtocolloDiSettore) {
		this.subProtocolloDiSettore = subProtocolloDiSettore;
	}

	public String getStatoScansione() {
		return statoScansione;
	}

	public void setStatoScansione(String statoScansione) {
		this.statoScansione = statoScansione;
	}

	public String getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getUdc() {
		return udc;
	}

	public void setUdc(String udc) {
		this.udc = udc;
	}

	public String getDenUffPrelievo() {
		return denUffPrelievo;
	}

	public void setDenUffPrelievo(String denUffPrelievo) {
		this.denUffPrelievo = denUffPrelievo;
	}

	public String getCodUffPrelievo() {
		return codUffPrelievo;
	}

	public void setCodUffPrelievo(String codUffPrelievo) {
		this.codUffPrelievo = codUffPrelievo;
	}

	public String getIdUoPrelievo() {
		return idUoPrelievo;
	}

	public void setIdUoPrelievo(String idUoPrelievo) {
		this.idUoPrelievo = idUoPrelievo;
	}

	public String getCognomeRespPrelievo() {
		return cognomeRespPrelievo;
	}

	public void setCognomeRespPrelievo(String cognomeRespPrelievo) {
		this.cognomeRespPrelievo = cognomeRespPrelievo;
	}

	public String getNomeRespPrelievo() {
		return nomeRespPrelievo;
	}

	public void setNomeRespPrelievo(String nomeRespPrelievo) {
		this.nomeRespPrelievo = nomeRespPrelievo;
	}

	public String getUsernameRespPrelievo() {
		return usernameRespPrelievo;
	}

	public void setUsernameRespPrelievo(String usernameRespPrelievo) {
		this.usernameRespPrelievo = usernameRespPrelievo;
	}

	public String getIdUserRespPrelievo() {
		return idUserRespPrelievo;
	}

	public void setIdUserRespPrelievo(String idUserRespPrelievo) {
		this.idUserRespPrelievo = idUserRespPrelievo;
	}

	public Date getDataPrelievo() {
		return dataPrelievo;
	}

	public void setDataPrelievo(Date dataPrelievo) {
		this.dataPrelievo = dataPrelievo;
	}

	public String getNoteUffRichiedente() {
		return noteUffRichiedente;
	}

	public void setNoteUffRichiedente(String noteUffRichiedente) {
		this.noteUffRichiedente = noteUffRichiedente;
	}

	public String getNoteCittadella() {
		return noteCittadella;
	}

	public void setNoteCittadella(String noteCittadella) {
		this.noteCittadella = noteCittadella;
	}
	
	public String getClassifica() {
		return classifica;
	}

	public void setClassifica(String classifica) {
		this.classifica = classifica;
	}

	public String getStatoAttoDaSincronizzare() {
		return statoAttoDaSincronizzare;
	}
	
	public void setStatoAttoDaSincronizzare(String statoAttoDaSincronizzare) {
		this.statoAttoDaSincronizzare = statoAttoDaSincronizzare;
	}
	
	public String getCompetenzaDiUrbanistica() {
		return competenzaDiUrbanistica;
	}
	
	public void setCompetenzaDiUrbanistica(String competenzaDiUrbanistica) {
		this.competenzaDiUrbanistica = competenzaDiUrbanistica;
	}
	
	public String getCartaceoReperibile() {
		return cartaceoReperibile;
	}

	public void setCartaceoReperibile(String cartaceoReperibile) {
		this.cartaceoReperibile = cartaceoReperibile;
	}
	
	public String getInArchivio() {
		return inArchivio;
	}

	public void setInArchivio(String inArchivio) {
		this.inArchivio = inArchivio;
	}

	public String getFlgRichiestaVisioneCartaceo() {
		return flgRichiestaVisioneCartaceo;
	}

	public void setFlgRichiestaVisioneCartaceo(String flgRichiestaVisioneCartaceo) {
		this.flgRichiestaVisioneCartaceo = flgRichiestaVisioneCartaceo;
	}
	
	public String getTipoFascicolo() {
		return tipoFascicolo;
	}
	
	public void setTipoFascicolo(String tipoFascicolo) {
		this.tipoFascicolo = tipoFascicolo;
	}
	
	public String getAnnoProtEdiliziaPrivata() {
		return annoProtEdiliziaPrivata;
	}
	
	public void setAnnoProtEdiliziaPrivata(String annoProtEdiliziaPrivata) {
		this.annoProtEdiliziaPrivata = annoProtEdiliziaPrivata;
	}
	
	public String getNumeroProtEdiliziaPrivata() {
		return numeroProtEdiliziaPrivata;
	}
	
	public void setNumeroProtEdiliziaPrivata(String numeroProtEdiliziaPrivata) {
		this.numeroProtEdiliziaPrivata = numeroProtEdiliziaPrivata;
	}
	
	public String getAnnoWorkflow() {
		return annoWorkflow;
	}
	
	public void setAnnoWorkflow(String annoWorkflow) {
		this.annoWorkflow = annoWorkflow;
	}
	
	public String getNumeroWorkflow() {
		return numeroWorkflow;
	}

	public void setNumeroWorkflow(String numeroWorkflow) {
		this.numeroWorkflow = numeroWorkflow;
	}
	
	public String getNumeroDeposito() {
		return numeroDeposito;
	}

	public void setNumeroDeposito(String numeroDeposito) {
		this.numeroDeposito = numeroDeposito;
	}
	
	public String getTipoComunicazione() {
		return tipoComunicazione;
	}
	
	public void setTipoComunicazione(String tipoComunicazione) {
		this.tipoComunicazione = tipoComunicazione;
	}
	
	public String getNoteSportello() {
		return noteSportello;
	}
	
	public void setNoteSportello(String noteSportello) {
		this.noteSportello = noteSportello;
	}
	
	public String getVisureCollegate() {
		return visureCollegate;
	}

	public void setVisureCollegate(String visureCollegate) {
		this.visureCollegate = visureCollegate;
	}

	public String getDesInArchivio() {
		return desInArchivio;
	}

	public void setDesInArchivio(String desInArchivio) {
		this.desInArchivio = desInArchivio;
	}

	public String getDesTipoFascicolo() {
		return desTipoFascicolo;
	}

	public void setDesTipoFascicolo(String desTipoFascicolo) {
		this.desTipoFascicolo = desTipoFascicolo;
	}

	public String getDesTipoComunicazione() {
		return desTipoComunicazione;
	}

	public void setDesTipoComunicazione(String desTipoComunicazione) {
		this.desTipoComunicazione = desTipoComunicazione;
	}
	
}