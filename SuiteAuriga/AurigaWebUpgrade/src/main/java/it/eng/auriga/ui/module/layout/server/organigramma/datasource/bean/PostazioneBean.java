/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;

public class PostazioneBean {

	private String sostituzioneSV;

	private String codiceRapido;
	private String intestazione;

	// Utente corrente
	private String idUoSvUt;
	private String ciRelUserUo;
	private String idScrivania;
	private String idUo;
	private String idUser;
	private String tipoRelUtenteUo;
	private Date dataDal;
	private Date dataAl;
	private String ruolo;
	private boolean flgInclSottoUo;
	private boolean flgInclScrivVirt;
	
	private boolean flgAccessoDocLimSV;
	private boolean flgRegistrazioneE;
	private boolean flgRegistrazioneUI;
	private boolean flgGestAtti;
	private boolean flgVisPropAttiInIter;
	private boolean flgRiservatezzaRelUserUo;
	private boolean flgGestAttiTutti;
	private String listaTipiGestAttiSelezionati;
	private boolean flgVisPropAttiInIterTutti;
	private String listaTipiVisPropAttiInIterSelezionati;
	
	
	
	private String nomePostazione;

	// Nuovo utente da sostituire a quello corrente
	private String idUserNew;
	private String username;
	private String descrizioneUser;
	private String tipoRelUtenteUoNew;
	private Date dataDalNew;
	private Date dataAlNew;
	private String ruoloNew;
	private boolean flgInclSottoUoNew;
	private boolean flgInclScrivVirtNew;
	private boolean flgRegistrazioneENew;
	private boolean flgRegistrazioneUINew;
	private boolean flgGestAttiNew;
	private boolean flgVisPropAttiInIterNew;
	private boolean flgRiservatezzaRelUserUoNew;
	private boolean flgGestAttiTuttiNew;
	private String listaTipiGestAttiSelezionatiNew;
	private boolean flgVisPropAttiInIterTuttiNew;
	private String listaTipiVisPropAttiInIterSelezionatiNew;
	
	
	
	private Boolean flgSpostamento;
	private Boolean flgDuplicazione;
	private boolean flgUoPuntoProtocollo;
	private String listaUOPuntoProtocolloEscluse;
	private String listaUOPuntoProtocolloEretidarietaAbilitata;
	private String denominazioneUo;
	private String nriLivelliUo;
	private boolean skipFlgUoPuntoProtocollo;
	
	
	// UO che ha in carico i documenti/fascicoli
	private Integer flgPresentiDocFasc;
	private String flgTipoDestDoc;
	private BigDecimal idUODestDocfasc;	
	private String livelliUODestDocFasc;
	private String desUODestDocFasc;	
			
	// UO al quale spostare i documenti/fascicoli
	private BigDecimal idUoSpostaDocFasc;
	private String descrizioneSpostaDocFasc;
	private String organigrammaSpostaDocFasc;
	private String typeNodoSpostaDocFasc;
	
	
	// Resoconto sulla situazione documentazione e mail in competenza alla UO
	// Situazione dei documenti/fascicoli assegnati
	private BigDecimal nrDocAssegnati;
	private Date dataConteggioDocAssegnati;
			
	private BigDecimal nrFascAssegnati;
	private Date dataConteggioFascAssegnati;
			
	private String descUoSpostamentoDocFasc;
	private String statoSpostamentoDocFasc;
	private Date dataInizioSpostamentoDocFasc;
	private Date dataFineSpostamentoDocFasc;
			
		
		

	public String getIdUoSvUt() {
		return idUoSvUt;
	}

	public void setIdUoSvUt(String idUoSvUt) {
		this.idUoSvUt = idUoSvUt;
	}

	public String getCodiceRapido() {
		return codiceRapido;
	}

	public void setCodiceRapido(String codiceRapido) {
		this.codiceRapido = codiceRapido;
	}

	public boolean isFlgInclScrivVirt() {
		return flgInclScrivVirt;
	}

	public void setFlgInclScrivVirt(boolean flgInclScrivVirt) {
		this.flgInclScrivVirt = flgInclScrivVirt;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getDescrizioneUser() {
		return descrizioneUser;
	}
	
	public void setDescrizioneUser(String descrizioneUser) {
		this.descrizioneUser = descrizioneUser;
	}

	public String getTipoRelUtenteUo() {
		return tipoRelUtenteUo;
	}

	public void setTipoRelUtenteUo(String tipoRelUtenteUo) {
		this.tipoRelUtenteUo = tipoRelUtenteUo;
	}

	public String getIntestazione() {
		return intestazione;
	}

	public void setIntestazione(String intestazione) {
		this.intestazione = intestazione;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getSostituzioneSV() {
		return sostituzioneSV;
	}

	public void setSostituzioneSV(String sostituzioneSV) {
		this.sostituzioneSV = sostituzioneSV;
	}

	public boolean isFlgInclSottoUoNew() {
		return flgInclSottoUoNew;
	}

	public void setFlgInclSottoUoNew(boolean flgInclSottoUoNew) {
		this.flgInclSottoUoNew = flgInclSottoUoNew;
	}

	public boolean isFlgInclScrivVirtNew() {
		return flgInclScrivVirtNew;
	}

	public void setFlgInclScrivVirtNew(boolean flgInclScrivVirtNew) {
		this.flgInclScrivVirtNew = flgInclScrivVirtNew;
	}

	public String getIdUserNew() {
		return idUserNew;
	}

	public void setIdUserNew(String idUserNew) {
		this.idUserNew = idUserNew;
	}

	public String getTipoRelUtenteUoNew() {
		return tipoRelUtenteUoNew;
	}

	public void setTipoRelUtenteUoNew(String tipoRelUtenteUoNew) {
		this.tipoRelUtenteUoNew = tipoRelUtenteUoNew;
	}

	public String getRuoloNew() {
		return ruoloNew;
	}

	public void setRuoloNew(String ruoloNew) {
		this.ruoloNew = ruoloNew;
	}

	public Date getDataDal() {
		return dataDal;
	}

	public void setDataDal(Date dataDal) {
		this.dataDal = dataDal;
	}

	public Date getDataAl() {
		return dataAl;
	}

	public void setDataAl(Date dataAl) {
		this.dataAl = dataAl;
	}

	public Date getDataDalNew() {
		return dataDalNew;
	}

	public void setDataDalNew(Date dataDalNew) {
		this.dataDalNew = dataDalNew;
	}

	public Date getDataAlNew() {
		return dataAlNew;
	}

	public void setDataAlNew(Date dataAlNew) {
		this.dataAlNew = dataAlNew;
	}

	public boolean isFlgInclSottoUo() {
		return flgInclSottoUo;
	}

	public void setFlgInclSottoUo(boolean flgInclSottoUo) {
		this.flgInclSottoUo = flgInclSottoUo;
	}

	public String getIdScrivania() {
		return idScrivania;
	}

	public void setIdScrivania(String idScrivania) {
		this.idScrivania = idScrivania;
	}

	public String getCiRelUserUo() {
		return ciRelUserUo;
	}

	public void setCiRelUserUo(String ciRelUserUo) {
		this.ciRelUserUo = ciRelUserUo;
	}

	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public String getNomePostazione() {
		return nomePostazione;
	}

	public void setNomePostazione(String nomePostazione) {
		this.nomePostazione = nomePostazione;
	}

	public Boolean getFlgSpostamento() {
		return flgSpostamento;
	}

	public void setFlgSpostamento(Boolean flgSpostamento) {
		this.flgSpostamento = flgSpostamento;
	}

	public Boolean getFlgDuplicazione() {
		return flgDuplicazione;
	}

	public void setFlgDuplicazione(Boolean flgDuplicazione) {
		this.flgDuplicazione = flgDuplicazione;
	}

	public boolean isFlgUoPuntoProtocollo() {
		return flgUoPuntoProtocollo;
	}

	public void setFlgUoPuntoProtocollo(boolean flgUoPuntoProtocollo) {
		this.flgUoPuntoProtocollo = flgUoPuntoProtocollo;
	}

	public String getListaUOPuntoProtocolloEscluse() {
		return listaUOPuntoProtocolloEscluse;
	}

	public void setListaUOPuntoProtocolloEscluse(String listaUOPuntoProtocolloEscluse) {
		this.listaUOPuntoProtocolloEscluse = listaUOPuntoProtocolloEscluse;
	}
	
	public String getListaUOPuntoProtocolloEretidarietaAbilitata() {
		return listaUOPuntoProtocolloEretidarietaAbilitata;
	}

	public void setListaUOPuntoProtocolloEretidarietaAbilitata(String listaUOPuntoProtocolloEretidarietaAbilitata) {
		this.listaUOPuntoProtocolloEretidarietaAbilitata = listaUOPuntoProtocolloEretidarietaAbilitata;
	}

	public String getDenominazioneUo() {
		return denominazioneUo;
	}

	public void setDenominazioneUo(String denominazioneUo) {
		this.denominazioneUo = denominazioneUo;
	}


	public String getNriLivelliUo() {
		return nriLivelliUo;
	}

	public void setNriLivelliUo(String nriLivelliUo) {
		this.nriLivelliUo = nriLivelliUo;
	}

	public boolean isSkipFlgUoPuntoProtocollo() {
		return skipFlgUoPuntoProtocollo;
	}

	public void setSkipFlgUoPuntoProtocollo(boolean skipFlgUoPuntoProtocollo) {
		this.skipFlgUoPuntoProtocollo = skipFlgUoPuntoProtocollo;
	}

	public Integer getFlgPresentiDocFasc() {
		return flgPresentiDocFasc;
	}

	public void setFlgPresentiDocFasc(Integer flgPresentiDocFasc) {
		this.flgPresentiDocFasc = flgPresentiDocFasc;
	}

	
	

	public String getLivelliUODestDocFasc() {
		return livelliUODestDocFasc;
	}

	public void setLivelliUODestDocFasc(String livelliUODestDocFasc) {
		this.livelliUODestDocFasc = livelliUODestDocFasc;
	}

	public String getDesUODestDocFasc() {
		return desUODestDocFasc;
	}

	public void setDesUODestDocFasc(String desUODestDocFasc) {
		this.desUODestDocFasc = desUODestDocFasc;
	}





	public BigDecimal getNrDocAssegnati() {
		return nrDocAssegnati;
	}

	public void setNrDocAssegnati(BigDecimal nrDocAssegnati) {
		this.nrDocAssegnati = nrDocAssegnati;
	}

	public Date getDataConteggioDocAssegnati() {
		return dataConteggioDocAssegnati;
	}

	public void setDataConteggioDocAssegnati(Date dataConteggioDocAssegnati) {
		this.dataConteggioDocAssegnati = dataConteggioDocAssegnati;
	}

	public BigDecimal getNrFascAssegnati() {
		return nrFascAssegnati;
	}

	public void setNrFascAssegnati(BigDecimal nrFascAssegnati) {
		this.nrFascAssegnati = nrFascAssegnati;
	}

	public Date getDataConteggioFascAssegnati() {
		return dataConteggioFascAssegnati;
	}

	public void setDataConteggioFascAssegnati(Date dataConteggioFascAssegnati) {
		this.dataConteggioFascAssegnati = dataConteggioFascAssegnati;
	}

	public String getDescUoSpostamentoDocFasc() {
		return descUoSpostamentoDocFasc;
	}

	public void setDescUoSpostamentoDocFasc(String descUoSpostamentoDocFasc) {
		this.descUoSpostamentoDocFasc = descUoSpostamentoDocFasc;
	}

	public String getStatoSpostamentoDocFasc() {
		return statoSpostamentoDocFasc;
	}

	public void setStatoSpostamentoDocFasc(String statoSpostamentoDocFasc) {
		this.statoSpostamentoDocFasc = statoSpostamentoDocFasc;
	}

	public Date getDataInizioSpostamentoDocFasc() {
		return dataInizioSpostamentoDocFasc;
	}

	public void setDataInizioSpostamentoDocFasc(Date dataInizioSpostamentoDocFasc) {
		this.dataInizioSpostamentoDocFasc = dataInizioSpostamentoDocFasc;
	}

	public Date getDataFineSpostamentoDocFasc() {
		return dataFineSpostamentoDocFasc;
	}

	public void setDataFineSpostamentoDocFasc(Date dataFineSpostamentoDocFasc) {
		this.dataFineSpostamentoDocFasc = dataFineSpostamentoDocFasc;
	}

	

	public BigDecimal getIdUoSpostaDocFasc() {
		return idUoSpostaDocFasc;
	}

	public void setIdUoSpostaDocFasc(BigDecimal idUoSpostaDocFasc) {
		this.idUoSpostaDocFasc = idUoSpostaDocFasc;
	}

	public String getDescrizioneSpostaDocFasc() {
		return descrizioneSpostaDocFasc;
	}

	public void setDescrizioneSpostaDocFasc(String descrizioneSpostaDocFasc) {
		this.descrizioneSpostaDocFasc = descrizioneSpostaDocFasc;
	}

	public String getOrganigrammaSpostaDocFasc() {
		return organigrammaSpostaDocFasc;
	}

	public void setOrganigrammaSpostaDocFasc(String organigrammaSpostaDocFasc) {
		this.organigrammaSpostaDocFasc = organigrammaSpostaDocFasc;
	}

	public String getTypeNodoSpostaDocFasc() {
		return typeNodoSpostaDocFasc;
	}

	public void setTypeNodoSpostaDocFasc(String typeNodoSpostaDocFasc) {
		this.typeNodoSpostaDocFasc = typeNodoSpostaDocFasc;
	}

	public BigDecimal getIdUODestDocfasc() {
		return idUODestDocfasc;
	}

	public void setIdUODestDocfasc(BigDecimal idUODestDocfasc) {
		this.idUODestDocfasc = idUODestDocfasc;
	}

	public String getFlgTipoDestDoc() {
		return flgTipoDestDoc;
	}

	public void setFlgTipoDestDoc(String flgTipoDestDoc) {
		this.flgTipoDestDoc = flgTipoDestDoc;
	}

	public boolean isFlgAccessoDocLimSV() {
		return flgAccessoDocLimSV;
	}

	public void setFlgAccessoDocLimSV(boolean flgAccessoDocLimSV) {
		this.flgAccessoDocLimSV = flgAccessoDocLimSV;
	}

	public boolean isFlgRegistrazioneE() {
		return flgRegistrazioneE;
	}

	public void setFlgRegistrazioneE(boolean flgRegistrazioneE) {
		this.flgRegistrazioneE = flgRegistrazioneE;
	}

	public boolean isFlgRegistrazioneUI() {
		return flgRegistrazioneUI;
	}

	public void setFlgRegistrazioneUI(boolean flgRegistrazioneUI) {
		this.flgRegistrazioneUI = flgRegistrazioneUI;
	}

	public boolean isFlgGestAtti() {
		return flgGestAtti;
	}

	public void setFlgGestAtti(boolean flgGestAtti) {
		this.flgGestAtti = flgGestAtti;
	}

	public boolean isFlgVisPropAttiInIter() {
		return flgVisPropAttiInIter;
	}

	public void setFlgVisPropAttiInIter(boolean flgVisPropAttiInIter) {
		this.flgVisPropAttiInIter = flgVisPropAttiInIter;
	}

	public boolean isFlgRegistrazioneENew() {
		return flgRegistrazioneENew;
	}

	public void setFlgRegistrazioneENew(boolean flgRegistrazioneENew) {
		this.flgRegistrazioneENew = flgRegistrazioneENew;
	}

	public boolean isFlgRegistrazioneUINew() {
		return flgRegistrazioneUINew;
	}

	public void setFlgRegistrazioneUINew(boolean flgRegistrazioneUINew) {
		this.flgRegistrazioneUINew = flgRegistrazioneUINew;
	}

	public boolean isFlgGestAttiNew() {
		return flgGestAttiNew;
	}

	public void setFlgGestAttiNew(boolean flgGestAttiNew) {
		this.flgGestAttiNew = flgGestAttiNew;
	}

	public boolean isFlgVisPropAttiInIterNew() {
		return flgVisPropAttiInIterNew;
	}

	public void setFlgVisPropAttiInIterNew(boolean flgVisPropAttiInIterNew) {
		this.flgVisPropAttiInIterNew = flgVisPropAttiInIterNew;
	}

	public boolean isFlgRiservatezzaRelUserUo() {
		return flgRiservatezzaRelUserUo;
	}

	public void setFlgRiservatezzaRelUserUo(boolean flgRiservatezzaRelUserUo) {
		this.flgRiservatezzaRelUserUo = flgRiservatezzaRelUserUo;
	}

	public boolean isFlgRiservatezzaRelUserUoNew() {
		return flgRiservatezzaRelUserUoNew;
	}

	public void setFlgRiservatezzaRelUserUoNew(boolean flgRiservatezzaRelUserUoNew) {
		this.flgRiservatezzaRelUserUoNew = flgRiservatezzaRelUserUoNew;
	}

	public boolean isFlgGestAttiTutti() {
		return flgGestAttiTutti;
	}

	public void setFlgGestAttiTutti(boolean flgGestAttiTutti) {
		this.flgGestAttiTutti = flgGestAttiTutti;
	}

	public String getListaTipiGestAttiSelezionati() {
		return listaTipiGestAttiSelezionati;
	}

	public void setListaTipiGestAttiSelezionati(String listaTipiGestAttiSelezionati) {
		this.listaTipiGestAttiSelezionati = listaTipiGestAttiSelezionati;
	}

	public boolean isFlgVisPropAttiInIterTutti() {
		return flgVisPropAttiInIterTutti;
	}

	public void setFlgVisPropAttiInIterTutti(boolean flgVisPropAttiInIterTutti) {
		this.flgVisPropAttiInIterTutti = flgVisPropAttiInIterTutti;
	}

	public String getListaTipiVisPropAttiInIterSelezionati() {
		return listaTipiVisPropAttiInIterSelezionati;
	}

	public void setListaTipiVisPropAttiInIterSelezionati(String listaTipiVisPropAttiInIterSelezionati) {
		this.listaTipiVisPropAttiInIterSelezionati = listaTipiVisPropAttiInIterSelezionati;
	}

	public boolean isFlgGestAttiTuttiNew() {
		return flgGestAttiTuttiNew;
	}

	public void setFlgGestAttiTuttiNew(boolean flgGestAttiTuttiNew) {
		this.flgGestAttiTuttiNew = flgGestAttiTuttiNew;
	}

	public String getListaTipiGestAttiSelezionatiNew() {
		return listaTipiGestAttiSelezionatiNew;
	}

	public void setListaTipiGestAttiSelezionatiNew(String listaTipiGestAttiSelezionatiNew) {
		this.listaTipiGestAttiSelezionatiNew = listaTipiGestAttiSelezionatiNew;
	}

	public boolean isFlgVisPropAttiInIterTuttiNew() {
		return flgVisPropAttiInIterTuttiNew;
	}

	public void setFlgVisPropAttiInIterTuttiNew(boolean flgVisPropAttiInIterTuttiNew) {
		this.flgVisPropAttiInIterTuttiNew = flgVisPropAttiInIterTuttiNew;
	}

	public String getListaTipiVisPropAttiInIterSelezionatiNew() {
		return listaTipiVisPropAttiInIterSelezionatiNew;
	}

	public void setListaTipiVisPropAttiInIterSelezionatiNew(String listaTipiVisPropAttiInIterSelezionatiNew) {
		this.listaTipiVisPropAttiInIterSelezionatiNew = listaTipiVisPropAttiInIterSelezionatiNew;
	}

}