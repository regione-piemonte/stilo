/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.document.NumeroColonna;


public class UoAssociateUtenteXmlBean {

	@NumeroColonna(numero="1")
	private String rowId;
		
	@NumeroColonna(numero="2")
	private String tipoRelazione;
	
	@NumeroColonna(numero="3")
	private String idUo;
	
	@NumeroColonna(numero="6")
	private String dtInizioVld;
	
	@NumeroColonna(numero="7")
	private String dtFineVld;

	@NumeroColonna(numero="8")
	private String idRuolo;

	@NumeroColonna(numero="9")
	private String descrizioneRuolo;
	
    @NumeroColonna(numero="10")
	private String flgRiservatezzaRelUserUo;
	
	@NumeroColonna(numero="13")
	private String denominazioneScrivaniaUtente;
	
	@NumeroColonna(numero="14")
	private String flgIncluseSottoUo;
	
	@NumeroColonna(numero="15")
	private String flgIncluseScrivanie;
	
	@NumeroColonna(numero="16")
	private String flgRecDaEliminare;
	
	@NumeroColonna(numero="17")
	private String listaUOPuntoProtocolloEscluse;
	
	@NumeroColonna(numero="18")
	private String flgTipoDestDoc;
	
	@NumeroColonna(numero="19")
	private String idUODestDocfasc;
		
	@NumeroColonna(numero="20")
	private String flgAccessoDocLimSV;
	
	@NumeroColonna(numero="21")
	private String flgRegistrazioneE;
	
	@NumeroColonna(numero="22")
	private String flgRegistrazioneUI;
	
	@NumeroColonna(numero="23")
	private String flgGestAtti;
	
	@NumeroColonna(numero="24")
	private String flgVisPropAttiInIter;
	
	@NumeroColonna(numero="36")
	private String flgGestAttiTutti;
	
	@NumeroColonna(numero="37")
	private String listaTipiGestAtti;
	
	@NumeroColonna(numero="38")
	private String flgVisPropAttiInIterTutti;
	
	@NumeroColonna(numero="39")
	private String listaTipiVisPropAttiInIter;
	
	
	// FIXME Mettere numero colonna
	private String listaUOPuntoProtocolloEreditarietaAbilitata;

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getTipoRelazione() {
		return tipoRelazione;
	}

	public void setTipoRelazione(String tipoRelazione) {
		this.tipoRelazione = tipoRelazione;
	}

	public String getDtInizioVld() {
		return dtInizioVld;
	}

	public void setDtInizioVld(String dtInizioVld) {
		this.dtInizioVld = dtInizioVld;
	}

	public String getDtFineVld() {
		return dtFineVld;
	}

	public void setDtFineVld(String dtFineVld) {
		this.dtFineVld = dtFineVld;
	}

	public String getIdRuolo() {
		return idRuolo;
	}

	public void setIdRuolo(String idRuolo) {
		this.idRuolo = idRuolo;
	}

	public String getFlgIncluseSottoUo() {
		return flgIncluseSottoUo;
	}

	public void setFlgIncluseSottoUo(String flgIncluseSottoUo) {
		this.flgIncluseSottoUo = flgIncluseSottoUo;
	}

	public String getFlgIncluseScrivanie() {
		return flgIncluseScrivanie;
	}

	public void setFlgIncluseScrivanie(String flgIncluseScrivanie) {
		this.flgIncluseScrivanie = flgIncluseScrivanie;
	}

	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public String getDescrizioneRuolo() {
		return descrizioneRuolo;
	}

	public void setDescrizioneRuolo(String descrizioneRuolo) {
		this.descrizioneRuolo = descrizioneRuolo;
	}

	public String getDenominazioneScrivaniaUtente() {
		return denominazioneScrivaniaUtente;
	}

	public void setDenominazioneScrivaniaUtente(String denominazioneScrivaniaUtente) {
		this.denominazioneScrivaniaUtente = denominazioneScrivaniaUtente;
	}

	public String getListaUOPuntoProtocolloEscluse() {
		return listaUOPuntoProtocolloEscluse;
	}

	public void setListaUOPuntoProtocolloEscluse(
			String listaUOPuntoProtocolloEscluse) {
		this.listaUOPuntoProtocolloEscluse = listaUOPuntoProtocolloEscluse;
	}
	
	public String getFlgTipoDestDoc() {
		return flgTipoDestDoc;
	}

	public void setFlgTipoDestDoc(String flgTipoDestDoc) {
		this.flgTipoDestDoc = flgTipoDestDoc;
	}

	public String getIdUODestDocfasc() {
		return idUODestDocfasc;
	}

	public void setIdUODestDocfasc(String idUODestDocfasc) {
		this.idUODestDocfasc = idUODestDocfasc;
	}
	
	public String getListaUOPuntoProtocolloEreditarietaAbilitata() {
		return listaUOPuntoProtocolloEreditarietaAbilitata;
	}
	
	public void setListaUOPuntoProtocolloEreditarietaAbilitata(String listaUOPuntoProtocolloEreditarietaAbilitata) {
		this.listaUOPuntoProtocolloEreditarietaAbilitata = listaUOPuntoProtocolloEreditarietaAbilitata;
	}

	public String getFlgAccessoDocLimSV() {
		return flgAccessoDocLimSV;
	}

	public void setFlgAccessoDocLimSV(String flgAccessoDocLimSV) {
		this.flgAccessoDocLimSV = flgAccessoDocLimSV;
	}

	public String getFlgRegistrazioneE() {
		return flgRegistrazioneE;
	}

	public void setFlgRegistrazioneE(String flgRegistrazioneE) {
		this.flgRegistrazioneE = flgRegistrazioneE;
	}

	public String getFlgRegistrazioneUI() {
		return flgRegistrazioneUI;
	}

	public void setFlgRegistrazioneUI(String flgRegistrazioneUI) {
		this.flgRegistrazioneUI = flgRegistrazioneUI;
	}

	public String getFlgGestAtti() {
		return flgGestAtti;
	}

	public void setFlgGestAtti(String flgGestAtti) {
		this.flgGestAtti = flgGestAtti;
	}

	public String getFlgVisPropAttiInIter() {
		return flgVisPropAttiInIter;
	}

	public void setFlgVisPropAttiInIter(String flgVisPropAttiInIter) {
		this.flgVisPropAttiInIter = flgVisPropAttiInIter;
	}

	public String getFlgRecDaEliminare() {
		return flgRecDaEliminare;
	}

	public void setFlgRecDaEliminare(String flgRecDaEliminare) {
		this.flgRecDaEliminare = flgRecDaEliminare;
	}

	public String getFlgRiservatezzaRelUserUo() {
		return flgRiservatezzaRelUserUo;
	}

	public void setFlgRiservatezzaRelUserUo(String flgRiservatezzaRelUserUo) {
		this.flgRiservatezzaRelUserUo = flgRiservatezzaRelUserUo;
	}

	public String getFlgGestAttiTutti() {
		return flgGestAttiTutti;
	}

	public void setFlgGestAttiTutti(String flgGestAttiTutti) {
		this.flgGestAttiTutti = flgGestAttiTutti;
	}

	public String getListaTipiGestAtti() {
		return listaTipiGestAtti;
	}

	public void setListaTipiGestAtti(String listaTipiGestAtti) {
		this.listaTipiGestAtti = listaTipiGestAtti;
	}

	public String getFlgVisPropAttiInIterTutti() {
		return flgVisPropAttiInIterTutti;
	}

	public void setFlgVisPropAttiInIterTutti(String flgVisPropAttiInIterTutti) {
		this.flgVisPropAttiInIterTutti = flgVisPropAttiInIterTutti;
	}

	public String getListaTipiVisPropAttiInIter() {
		return listaTipiVisPropAttiInIter;
	}

	public void setListaTipiVisPropAttiInIter(String listaTipiVisPropAttiInIter) {
		this.listaTipiVisPropAttiInIter = listaTipiVisPropAttiInIter;
	}

}
