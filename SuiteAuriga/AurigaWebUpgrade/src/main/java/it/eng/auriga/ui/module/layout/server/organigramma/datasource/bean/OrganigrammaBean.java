/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.CentroCostoBean;
import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.ContattoSoggettoBean;

public class OrganigrammaBean {

	private String tipo;
	private String descrTipo;
	private String idUoSvUt;
	private String nome;
	private String denominazioneEstesa;
	private Date tsInizio;
	private Date tsFine;
	private String codRapidoUo;
	private String codRapidoUoXOrd;
	private String codRapidoSvUt;
	private String descrUoSvUt;
	private String tipoRelUtenteUo;
	private Boolean flgSelXFinalita;
	private String idUoSup;
	private String denomUoSup;
	private BigDecimal score;
	private String codFiscale;
	private String idRubrica;
	private String ruolo;
	private String username;
	private String titolo;
	private String qualifica;
	private String competenzefunzioni;
	private Date dtPubblicazioneIpa;
	private String flgPubblicataSuIpa;
	private String indirizzo;
	private String telefono;
	private String email;
	private String fax;
	private Boolean flgProtocollista;
	private String profilo;
	private String subProfili;
	private String collegataAPuntoProt;
	private List<CentroCostoBean> listaCentriCosto;
	private List<String> idAssessoreRif;
	private String uoUfficioLiquidatore;
	private String idUoUfficioLiquidatore;	
	private String typeNodoUfficioLiquidatore;
	private String descrizioneUfficioLiquidatore;
	private String codRapidoUfficioLiquidatore;
	
	
	// Indica il percorso per giungere all'utente. gli Id della geranchia divisi da /
	private String parentId;

	private List<ContattoSoggettoBean> listaContatti;
	private String nroLivello;
	private String livello;
	private String livelloCorrente;
	private Date dataIstituita;
	private Date dataSoppressa;
	private Boolean checkDichiarataIndicePA;
	private Date dataDichIndicePA;
	private Boolean storicizzaDatiVariati;
	private Date variazioneDatiIn;
	private String motiviVariazioneIn;

	// Valori per load SV
	private String idUo;
	private String idUser;
	private String dataInizio;
	private String idRuolo;
	private String ciRelUserUo;

	private Boolean flgInibitaAssegnazione;
	private Boolean flgInibitoInvioCC;
	private Boolean flgPuntoRaccoltaEmail;
	private Boolean flgPuntoRaccoltaDocumenti;
	private Boolean flgInibitaAssegnazioneEmail;

	private Integer flgIgnoreWarning;
	private String  warningMsgOut;

	// Valori per punto protocollo
	private Boolean flgPuntoProtocollo;
	private List<PuntoProtocolloCollegatoBean> uoPuntoProtocolloCollegate;
	private String denominazioneBreve;
	private String presenteAttoDefOrganigramma;
	private Boolean inibitaPreimpDestProtEntrata;
	private List<TipologiaOffertaPuntoVenditaBean>    listaTipologieOffertePuntoVendita;
	private List<TipologiaPromozionePuntoVenditaBean> listaTipologiePromozioniPuntoVendita;
	private String comuneNascitaIstituzione;
	private String nomeComuneNascitaIstituzione;
	private String labelSocioCoopDatiClienteForm;
	private String categoriaSocioCoop;
	private String nomePostazioneInPrecedenza;
	private Boolean flgDestinatarioNeiPreferiti;
	private Boolean abilitaUoProtEntrata;
	private Boolean abilitaUoProtUscita;
	private Boolean abilitaUoProtUscitaFull;
	private Boolean abilitaUoAssRiservatezza;
	private Boolean abilitaUoGestMulte;
	private Boolean abilitaUoGestContratti;
	private Boolean flgUfficioGareAppalti;
	private Boolean abilitaUoScansioneMassiva;
	
	// Uo derivata
	private String uoDerivataPer;
	private List<SelezionaUOBean> listaUODerivataDa;
	
	// UO dato luogo a
	private String uoDatoLuogoAPer;
	private List<SelezionaUOBean> listaUODatoLuogoA;

	private String flgSelXAssegnazione;
	
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
	
	// UO che ha in carico le mail
	private Integer flgPresentiMail;	
	private BigDecimal idUODestMail;
	private String livelliUODestMail;
	private String desUODestMail;
	
	// UO al quale spostare le mail
	private BigDecimal idUoSpostaMail;
	private String descrizioneSpostaMail;
	private String organigrammaSpostaMail;
	private String typeNodoSpostaMail;

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
	
    // Situazione delle mail assegnate
	private BigDecimal nrMailAssegnati;
	private Date dataConteggioMailAssegnati;
	
	private String descUoSpostamentoMail;
	private String statoSpostamentoMail;
	private Date dataInizioSpostamentoMail;
	private Date dataFineSpostamentoMail;
	
	// Attributi custom per NESTLE
	private String destinatarioDisclaimerMessage;
	private String mailMittenteOrdinaria;
	private String mailMittentePec;
	private String giorniAttesaPrimoSollecito;
	private String giorniAttesaInvioPosta;
	private String invioMailPerUtenteWeb;
	private String flgExport;
	private String logoClusterNestle;
	private String tipoDocClusterNestle;
	private String layoutDocClusterNestle;
	private String prefLottiClusterNestle;
	private String competenze;
	private String ciProvUO;
	
	private Integer flgAssInvioUP;
	private Boolean flgPropagaAssInvioUP;
	private Integer flgEreditaAssInvioUP;
	
	private String tsStrutturaDaCessareDal;
	
	private String origineCreazione;
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getIdUoSvUt() {
		return idUoSvUt;
	}

	public void setIdUoSvUt(String idUoSvUt) {
		this.idUoSvUt = idUoSvUt;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescrTipo() {
		return descrTipo;
	}

	public void setDescrTipo(String descrTipo) {
		this.descrTipo = descrTipo;
	}

	public Boolean getFlgSelXFinalita() {
		return flgSelXFinalita;
	}

	public void setFlgSelXFinalita(Boolean flgSelXFinalita) {
		this.flgSelXFinalita = flgSelXFinalita;
	}

	public String getIdUoSup() {
		return idUoSup;
	}

	public void setIdUoSup(String idUoSup) {
		this.idUoSup = idUoSup;
	}

	public String getDenominazioneEstesa() {
		return denominazioneEstesa;
	}

	public void setDenominazioneEstesa(String denominazioneEstesa) {
		this.denominazioneEstesa = denominazioneEstesa;
	}

	public Date getTsInizio() {
		return tsInizio;
	}

	public void setTsInizio(Date tsInizio) {
		this.tsInizio = tsInizio;
	}

	public Date getTsFine() {
		return tsFine;
	}

	public void setTsFine(Date tsFine) {
		this.tsFine = tsFine;
	}

	public String getCodRapidoUo() {
		return codRapidoUo;
	}

	public void setCodRapidoUo(String codRapidoUo) {
		this.codRapidoUo = codRapidoUo;
	}

	public String getTipoRelUtenteUo() {
		return tipoRelUtenteUo;
	}

	public void setTipoRelUtenteUo(String tipoRelUtenteUo) {
		this.tipoRelUtenteUo = tipoRelUtenteUo;
	}

	public String getDenomUoSup() {
		return denomUoSup;
	}

	public void setDenomUoSup(String denomUoSup) {
		this.denomUoSup = denomUoSup;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public String getDescrUoSvUt() {
		return descrUoSvUt;
	}

	public void setDescrUoSvUt(String descrUoSvUt) {
		this.descrUoSvUt = descrUoSvUt;
	}

	public String getCodRapidoSvUt() {
		return codRapidoSvUt;
	}

	public void setCodRapidoSvUt(String codRapidoSvUt) {
		this.codRapidoSvUt = codRapidoSvUt;
	}

	public String getCodRapidoUoXOrd() {
		return codRapidoUoXOrd;
	}

	public void setCodRapidoUoXOrd(String codRapidoUoXOrd) {
		this.codRapidoUoXOrd = codRapidoUoXOrd;
	}

	public String getCodFiscale() {
		return codFiscale;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	public String getIdRubrica() {
		return idRubrica;
	}

	public void setIdRubrica(String idRubrica) {
		this.idRubrica = idRubrica;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getQualifica() {
		return qualifica;
	}

	public void setQualifica(String qualifica) {
		this.qualifica = qualifica;
	}

	public String getCompetenzefunzioni() {
		return competenzefunzioni;
	}

	public void setCompetenzefunzioni(String competenzefunzioni) {
		this.competenzefunzioni = competenzefunzioni;
	}

	public String getFlgPubblicataSuIpa() {
		return flgPubblicataSuIpa;
	}

	public void setFlgPubblicataSuIpa(String flgPubblicataSuIpa) {
		this.flgPubblicataSuIpa = flgPubblicataSuIpa;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public Boolean getFlgProtocollista() {
		return flgProtocollista;
	}
	
	public void setFlgProtocollista(Boolean flgProtocollista) {
		this.flgProtocollista = flgProtocollista;
	}

	public Date getDtPubblicazioneIpa() {
		return dtPubblicazioneIpa;
	}

	public void setDtPubblicazioneIpa(Date dtPubblicazioneIpa) {
		this.dtPubblicazioneIpa = dtPubblicazioneIpa;
	}

	public Date getDataIstituita() {
		return dataIstituita;
	}

	public void setDataIstituita(Date dataIstituita) {
		this.dataIstituita = dataIstituita;
	}

	public Date getDataSoppressa() {
		return dataSoppressa;
	}

	public void setDataSoppressa(Date dataSoppressa) {
		this.dataSoppressa = dataSoppressa;
	}

	public Boolean getCheckDichiarataIndicePA() {
		return checkDichiarataIndicePA;
	}

	public void setCheckDichiarataIndicePA(Boolean checkDichiarataIndicePA) {
		this.checkDichiarataIndicePA = checkDichiarataIndicePA;
	}

	public Date getDataDichIndicePA() {
		return dataDichIndicePA;
	}

	public void setDataDichIndicePA(Date dataDichIndicePA) {
		this.dataDichIndicePA = dataDichIndicePA;
	}

	public List<ContattoSoggettoBean> getListaContatti() {
		return listaContatti;
	}

	public void setListaContatti(List<ContattoSoggettoBean> listaContatti) {
		this.listaContatti = listaContatti;
	}

	public String getLivello() {
		return livello;
	}

	public void setLivello(String livello) {
		this.livello = livello;
	}

	public String getLivelloCorrente() {
		return livelloCorrente;
	}

	public void setLivelloCorrente(String livelloCorrente) {
		this.livelloCorrente = livelloCorrente;
	}

	public String getNroLivello() {
		return nroLivello;
	}

	public void setNroLivello(String nroLivello) {
		this.nroLivello = nroLivello;
	}

	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(String dataInizio) {
		this.dataInizio = dataInizio;
	}

	public String getIdRuolo() {
		return idRuolo;
	}

	public void setIdRuolo(String idRuolo) {
		this.idRuolo = idRuolo;
	}

	public Boolean getStoricizzaDatiVariati() {
		return storicizzaDatiVariati;
	}

	public void setStoricizzaDatiVariati(Boolean storicizzaDatiVariati) {
		this.storicizzaDatiVariati = storicizzaDatiVariati;
	}

	public Date getVariazioneDatiIn() {
		return variazioneDatiIn;
	}

	public void setVariazioneDatiIn(Date variazioneDatiIn) {
		this.variazioneDatiIn = variazioneDatiIn;
	}

	public String getMotiviVariazioneIn() {
		return motiviVariazioneIn;
	}

	public void setMotiviVariazioneIn(String motiviVariazioneIn) {
		this.motiviVariazioneIn = motiviVariazioneIn;
	}

	public Boolean getFlgInibitaAssegnazione() {
		return flgInibitaAssegnazione;
	}

	public Boolean getFlgInibitoInvioCC() {
		return flgInibitoInvioCC;
	}

	public void setFlgInibitaAssegnazione(Boolean flgInibitaAssegnazione) {
		this.flgInibitaAssegnazione = flgInibitaAssegnazione;
	}

	public void setFlgInibitoInvioCC(Boolean flgInibitoInvioCC) {
		this.flgInibitoInvioCC = flgInibitoInvioCC;
	}

	public String getProfilo() {
		return profilo;
	}

	public void setProfilo(String profilo) {
		this.profilo = profilo;
	}
	
	public String getCollegataAPuntoProt() {
		return collegataAPuntoProt;
	}

	public void setCollegataAPuntoProt(String collegataAPuntoProt) {
		this.collegataAPuntoProt = collegataAPuntoProt;
	}	

	public String getCiRelUserUo() {
		return ciRelUserUo;
	}

	public void setCiRelUserUo(String ciRelUserUo) {
		this.ciRelUserUo = ciRelUserUo;
	}

	public Integer getFlgIgnoreWarning() {
		return flgIgnoreWarning;
	}

	public void setFlgIgnoreWarning(Integer flgIgnoreWarning) {
		this.flgIgnoreWarning = flgIgnoreWarning;
	}

	public String getWarningMsgOut() {
		return warningMsgOut;
	}

	public void setWarningMsgOut(String warningMsgOut) {
		this.warningMsgOut = warningMsgOut;
	}

	public Boolean getFlgPuntoProtocollo() {
		return flgPuntoProtocollo;
	}

	public void setFlgPuntoProtocollo(Boolean flgPuntoProtocollo) {
		this.flgPuntoProtocollo = flgPuntoProtocollo;
	}

	public List<PuntoProtocolloCollegatoBean> getUoPuntoProtocolloCollegate() {
		return uoPuntoProtocolloCollegate;
	}

	public void setUoPuntoProtocolloCollegate(List<PuntoProtocolloCollegatoBean> uoPuntoProtocolloCollegate) {
		this.uoPuntoProtocolloCollegate = uoPuntoProtocolloCollegate;
	}
	
	public String getDenominazioneBreve() {
		return denominazioneBreve;
	}
	
	public void setDenominazioneBreve(String denominazioneBreve) {
		this.denominazioneBreve = denominazioneBreve;
	}

	public List<TipologiaOffertaPuntoVenditaBean> getListaTipologieOffertePuntoVendita() {
		return listaTipologieOffertePuntoVendita;
	}

	public void setListaTipologieOffertePuntoVendita(
			List<TipologiaOffertaPuntoVenditaBean> listaTipologieOffertePuntoVendita) {
		this.listaTipologieOffertePuntoVendita = listaTipologieOffertePuntoVendita;
	}

	public List<TipologiaPromozionePuntoVenditaBean> getListaTipologiePromozioniPuntoVendita() {
		return listaTipologiePromozioniPuntoVendita;
	}

	public void setListaTipologiePromozioniPuntoVendita(
			List<TipologiaPromozionePuntoVenditaBean> listaTipologiePromozioniPuntoVendita) {
		this.listaTipologiePromozioniPuntoVendita = listaTipologiePromozioniPuntoVendita;
	}

	public String getLabelSocioCoopDatiClienteForm() {
		return labelSocioCoopDatiClienteForm;
	}

	public void setLabelSocioCoopDatiClienteForm(
			String labelSocioCoopDatiClienteForm) {
		this.labelSocioCoopDatiClienteForm = labelSocioCoopDatiClienteForm;
	}

	public String getComuneNascitaIstituzione() {
		return comuneNascitaIstituzione;
	}

	public void setComuneNascitaIstituzione(String comuneNascitaIstituzione) {
		this.comuneNascitaIstituzione = comuneNascitaIstituzione;
	}

	public String getNomeComuneNascitaIstituzione() {
		return nomeComuneNascitaIstituzione;
	}

	public void setNomeComuneNascitaIstituzione(String nomeComuneNascitaIstituzione) {
		this.nomeComuneNascitaIstituzione = nomeComuneNascitaIstituzione;
	}

	public String getNomePostazioneInPrecedenza() {
		return nomePostazioneInPrecedenza;
	}

	public void setNomePostazioneInPrecedenza(String nomePostazioneInPrecedenza) {
		this.nomePostazioneInPrecedenza = nomePostazioneInPrecedenza;
	}

	public Boolean getFlgDestinatarioNeiPreferiti() {
		return flgDestinatarioNeiPreferiti;
	}

	public void setFlgDestinatarioNeiPreferiti(Boolean flgDestinatarioNeiPreferiti) {
		this.flgDestinatarioNeiPreferiti = flgDestinatarioNeiPreferiti;
	}

	public String getUoDerivataPer() {
		return uoDerivataPer;
	}

	public void setUoDerivataPer(String uoDerivataPer) {
		this.uoDerivataPer = uoDerivataPer;
	}

	public List<SelezionaUOBean> getListaUODerivataDa() {
		return listaUODerivataDa;
	}

	public void setListaUODerivataDa(List<SelezionaUOBean> listaUODerivataDa) {
		this.listaUODerivataDa = listaUODerivataDa;
	}
	
	public String getUoDatoLuogoAPer() {
		return uoDatoLuogoAPer;
	}
	
	public void setUoDatoLuogoAPer(String uoDatoLuogoAPer) {
		this.uoDatoLuogoAPer = uoDatoLuogoAPer;
	}

	public List<SelezionaUOBean> getListaUODatoLuogoA() {
		return listaUODatoLuogoA;
	}
	
	public void setListaUODatoLuogoA(List<SelezionaUOBean> listaUODatoLuogoA) {
		this.listaUODatoLuogoA = listaUODatoLuogoA;
	}
	
	public String getFlgSelXAssegnazione() {
		return flgSelXAssegnazione;
	}
	public void setFlgSelXAssegnazione(String flgSelXAssegnazione) {
		this.flgSelXAssegnazione = flgSelXAssegnazione;
	}

	public String getPresenteAttoDefOrganigramma() {
		return presenteAttoDefOrganigramma;
	}

	public void setPresenteAttoDefOrganigramma(String presenteAttoDefOrganigramma) {
		this.presenteAttoDefOrganigramma = presenteAttoDefOrganigramma;
	}

	public String getMailMittenteOrdinaria() {
		return mailMittenteOrdinaria;
	}

	public void setMailMittenteOrdinaria(String mailMittenteOrdinaria) {
		this.mailMittenteOrdinaria = mailMittenteOrdinaria;
	}

	public String getMailMittentePec() {
		return mailMittentePec;
	}

	public void setMailMittentePec(String mailMittentePec) {
		this.mailMittentePec = mailMittentePec;
	}

	public String getGiorniAttesaPrimoSollecito() {
		return giorniAttesaPrimoSollecito;
	}

	public void setGiorniAttesaPrimoSollecito(String giorniAttesaPrimoSollecito) {
		this.giorniAttesaPrimoSollecito = giorniAttesaPrimoSollecito;
	}

	public String getGiorniAttesaInvioPosta() {
		return giorniAttesaInvioPosta;
	}

	public void setGiorniAttesaInvioPosta(String giorniAttesaInvioPosta) {
		this.giorniAttesaInvioPosta = giorniAttesaInvioPosta;
	}

	public String getInvioMailPerUtenteWeb() {
		return invioMailPerUtenteWeb;
	}

	public void setInvioMailPerUtenteWeb(String invioMailPerUtenteWeb) {
		this.invioMailPerUtenteWeb = invioMailPerUtenteWeb;
	}

	public String getFlgExport() {
		return flgExport;
	}

	public void setFlgExport(String flgExport) {
		this.flgExport = flgExport;
	}

	public String getLogoClusterNestle() {
		return logoClusterNestle;
	}

	public void setLogoClusterNestle(String logoClusterNestle) {
		this.logoClusterNestle = logoClusterNestle;
	}

	public String getTipoDocClusterNestle() {
		return tipoDocClusterNestle;
	}

	public void setTipoDocClusterNestle(String tipoDocClusterNestle) {
		this.tipoDocClusterNestle = tipoDocClusterNestle;
	}

	public String getLayoutDocClusterNestle() {
		return layoutDocClusterNestle;
	}

	public void setLayoutDocClusterNestle(String layoutDocClusterNestle) {
		this.layoutDocClusterNestle = layoutDocClusterNestle;
	}

	public String getPrefLottiClusterNestle() {
		return prefLottiClusterNestle;
	}

	public void setPrefLottiClusterNestle(String prefLottiClusterNestle) {
		this.prefLottiClusterNestle = prefLottiClusterNestle;
	}

	public String getCategoriaSocioCoop() {
		return categoriaSocioCoop;
	}

	public void setCategoriaSocioCoop(String categoriaSocioCoop) {
		this.categoriaSocioCoop = categoriaSocioCoop;
	}

	public String getDestinatarioDisclaimerMessage() {
		return destinatarioDisclaimerMessage;
	}

	public void setDestinatarioDisclaimerMessage(
			String destinatarioDisclaimerMessage) {
		this.destinatarioDisclaimerMessage = destinatarioDisclaimerMessage;
	}

	public Integer getFlgPresentiDocFasc() {
		return flgPresentiDocFasc;
	}

	public void setFlgPresentiDocFasc(Integer flgPresentiDocFasc) {
		this.flgPresentiDocFasc = flgPresentiDocFasc;
	}

	public Integer getFlgPresentiMail() {
		return flgPresentiMail;
	}

	public void setFlgPresentiMail(Integer flgPresentiMail) {
		this.flgPresentiMail = flgPresentiMail;
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

	public BigDecimal getIdUODestMail() {
		return idUODestMail;
	}

	public void setIdUODestMail(BigDecimal idUODestMail) {
		this.idUODestMail = idUODestMail;
	}

	public String getLivelliUODestMail() {
		return livelliUODestMail;
	}

	public void setLivelliUODestMail(String livelliUODestMail) {
		this.livelliUODestMail = livelliUODestMail;
	}

	public String getDesUODestMail() {
		return desUODestMail;
	}

	public void setDesUODestMail(String desUODestMail) {
		this.desUODestMail = desUODestMail;
	}

	public BigDecimal getIdUODestDocfasc() {
		return idUODestDocfasc;
	}

	public void setIdUODestDocfasc(BigDecimal idUODestDocfasc) {
		this.idUODestDocfasc = idUODestDocfasc;
	}

	
	public Date getDataConteggioDocAssegnati() {
		return dataConteggioDocAssegnati;
	}

	public void setDataConteggioDocAssegnati(Date dataConteggioDocAssegnati) {
		this.dataConteggioDocAssegnati = dataConteggioDocAssegnati;
	}
	
	public Date getDataConteggioFascAssegnati() {
		return dataConteggioFascAssegnati;
	}

	public void setDataConteggioFascAssegnati(Date dataConteggioFascAssegnati) {
		this.dataConteggioFascAssegnati = dataConteggioFascAssegnati;
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

	public Date getDataConteggioMailAssegnati() {
		return dataConteggioMailAssegnati;
	}

	public void setDataConteggioMailAssegnati(Date dataConteggioMailAssegnati) {
		this.dataConteggioMailAssegnati = dataConteggioMailAssegnati;
	}


	public String getStatoSpostamentoMail() {
		return statoSpostamentoMail;
	}

	public void setStatoSpostamentoMail(String statoSpostamentoMail) {
		this.statoSpostamentoMail = statoSpostamentoMail;
	}

	public Date getDataInizioSpostamentoMail() {
		return dataInizioSpostamentoMail;
	}

	public void setDataInizioSpostamentoMail(Date dataInizioSpostamentoMail) {
		this.dataInizioSpostamentoMail = dataInizioSpostamentoMail;
	}

	public Date getDataFineSpostamentoMail() {
		return dataFineSpostamentoMail;
	}

	public void setDataFineSpostamentoMail(Date dataFineSpostamentoMail) {
		this.dataFineSpostamentoMail = dataFineSpostamentoMail;
	}

	public String getDescUoSpostamentoDocFasc() {
		return descUoSpostamentoDocFasc;
	}

	public void setDescUoSpostamentoDocFasc(String descUoSpostamentoDocFasc) {
		this.descUoSpostamentoDocFasc = descUoSpostamentoDocFasc;
	}

	public String getDescUoSpostamentoMail() {
		return descUoSpostamentoMail;
	}

	public void setDescUoSpostamentoMail(String descUoSpostamentoMail) {
		this.descUoSpostamentoMail = descUoSpostamentoMail;
	}

	public BigDecimal getNrDocAssegnati() {
		return nrDocAssegnati;
	}

	public void setNrDocAssegnati(BigDecimal nrDocAssegnati) {
		this.nrDocAssegnati = nrDocAssegnati;
	}

	public BigDecimal getNrFascAssegnati() {
		return nrFascAssegnati;
	}

	public void setNrFascAssegnati(BigDecimal nrFascAssegnati) {
		this.nrFascAssegnati = nrFascAssegnati;
	}

	public BigDecimal getNrMailAssegnati() {
		return nrMailAssegnati;
	}

	public void setNrMailAssegnati(BigDecimal nrMailAssegnati) {
		this.nrMailAssegnati = nrMailAssegnati;
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

	public BigDecimal getIdUoSpostaMail() {
		return idUoSpostaMail;
	}

	public void setIdUoSpostaMail(BigDecimal idUoSpostaMail) {
		this.idUoSpostaMail = idUoSpostaMail;
	}

	public String getDescrizioneSpostaMail() {
		return descrizioneSpostaMail;
	}

	public void setDescrizioneSpostaMail(String descrizioneSpostaMail) {
		this.descrizioneSpostaMail = descrizioneSpostaMail;
	}

	public String getOrganigrammaSpostaMail() {
		return organigrammaSpostaMail;
	}

	public void setOrganigrammaSpostaMail(String organigrammaSpostaMail) {
		this.organigrammaSpostaMail = organigrammaSpostaMail;
	}

	public String getTypeNodoSpostaMail() {
		return typeNodoSpostaMail;
	}

	public void setTypeNodoSpostaMail(String typeNodoSpostaMail) {
		this.typeNodoSpostaMail = typeNodoSpostaMail;
	}

	public String getFlgTipoDestDoc() {
		return flgTipoDestDoc;
	}

	public void setFlgTipoDestDoc(String flgTipoDestDoc) {
		this.flgTipoDestDoc = flgTipoDestDoc;
	}

	public Boolean getInibitaPreimpDestProtEntrata() {
		return inibitaPreimpDestProtEntrata;
	}

	public void setInibitaPreimpDestProtEntrata(Boolean inibitaPreimpDestProtEntrata) {
		this.inibitaPreimpDestProtEntrata = inibitaPreimpDestProtEntrata;
	}

	public Boolean getFlgPuntoRaccoltaEmail() {
		return flgPuntoRaccoltaEmail;
	}

	public void setFlgPuntoRaccoltaEmail(Boolean flgPuntoRaccoltaEmail) {
		this.flgPuntoRaccoltaEmail = flgPuntoRaccoltaEmail;
	}

	public Boolean getFlgInibitaAssegnazioneEmail() {
		return flgInibitaAssegnazioneEmail;
	}

	public void setFlgInibitaAssegnazioneEmail(Boolean flgInibitaAssegnazioneEmail) {
		this.flgInibitaAssegnazioneEmail = flgInibitaAssegnazioneEmail;
	}

	public String getCompetenze() {
		return competenze;
	}

	public void setCompetenze(String competenze) {
		this.competenze = competenze;
	}

	public Boolean getFlgPuntoRaccoltaDocumenti() {
		return flgPuntoRaccoltaDocumenti;
	}

	public void setFlgPuntoRaccoltaDocumenti(Boolean flgPuntoRaccoltaDocumenti) {
		this.flgPuntoRaccoltaDocumenti = flgPuntoRaccoltaDocumenti;
	}

	public List<CentroCostoBean> getListaCentriCosto() {
		return listaCentriCosto;
	}

	public void setListaCentriCosto(List<CentroCostoBean> listaCentriCosto) {
		this.listaCentriCosto = listaCentriCosto;
	}
	
	public String getUoUfficioLiquidatore() {
		return uoUfficioLiquidatore;
	}

	public void setUoUfficioLiquidatore(String uoUfficioLiquidatore) {
		this.uoUfficioLiquidatore = uoUfficioLiquidatore;
	}

	public String getTypeNodoUfficioLiquidatore() {
		return typeNodoUfficioLiquidatore;
	}

	public void setTypeNodoUfficioLiquidatore(String typeNodoUfficioLiquidatore) {
		this.typeNodoUfficioLiquidatore = typeNodoUfficioLiquidatore;
	}

	public String getDescrizioneUfficioLiquidatore() {
		return descrizioneUfficioLiquidatore;
	}

	public void setDescrizioneUfficioLiquidatore(String descrizioneUfficioLiquidatore) {
		this.descrizioneUfficioLiquidatore = descrizioneUfficioLiquidatore;
	}

	public String getCodRapidoUfficioLiquidatore() {
		return codRapidoUfficioLiquidatore;
	}

	public void setCodRapidoUfficioLiquidatore(String codRapidoUfficioLiquidatore) {
		this.codRapidoUfficioLiquidatore = codRapidoUfficioLiquidatore;
	}

	public String getIdUoUfficioLiquidatore() {
		return idUoUfficioLiquidatore;
	}

	public void setIdUoUfficioLiquidatore(String idUoUfficioLiquidatore) {
		this.idUoUfficioLiquidatore = idUoUfficioLiquidatore;
	}

	public List<String> getIdAssessoreRif() {
		return idAssessoreRif;
	}

	public void setIdAssessoreRif(List<String> idAssessoreRif) {
		this.idAssessoreRif = idAssessoreRif;
	}

	public String getCiProvUO() {
		return ciProvUO;
	}

	public void setCiProvUO(String ciProvUO) {
		this.ciProvUO = ciProvUO;
	}

	public Integer getFlgAssInvioUP() {
		return flgAssInvioUP;
	}

	public void setFlgAssInvioUP(Integer flgAssInvioUP) {
		this.flgAssInvioUP = flgAssInvioUP;
	}


	public Integer getFlgEreditaAssInvioUP() {
		return flgEreditaAssInvioUP;
	}

	public void setFlgEreditaAssInvioUP(Integer flgEreditaAssInvioUP) {
		this.flgEreditaAssInvioUP = flgEreditaAssInvioUP;
	}

	public Boolean getFlgPropagaAssInvioUP() {
		return flgPropagaAssInvioUP;
	}

	public void setFlgPropagaAssInvioUP(Boolean flgPropagaAssInvioUP) {
		this.flgPropagaAssInvioUP = flgPropagaAssInvioUP;
	}

	

	public Boolean getFlgUfficioGareAppalti() {
		return flgUfficioGareAppalti;
	}

	public void setFlgUfficioGareAppalti(Boolean flgUfficioGareAppalti) {
		this.flgUfficioGareAppalti = flgUfficioGareAppalti;
	}

	public Boolean getAbilitaUoScansioneMassiva() {
		return abilitaUoScansioneMassiva;
	}

	public void setAbilitaUoScansioneMassiva(Boolean abilitaUoScansioneMassiva) {
		this.abilitaUoScansioneMassiva = abilitaUoScansioneMassiva;
	}

	public Boolean getAbilitaUoProtUscitaFull() {
		return abilitaUoProtUscitaFull;
	}

	public void setAbilitaUoProtUscitaFull(Boolean abilitaUoProtUscitaFull) {
		this.abilitaUoProtUscitaFull = abilitaUoProtUscitaFull;
	}

	public Boolean getAbilitaUoProtEntrata() {
		return abilitaUoProtEntrata;
	}

	public void setAbilitaUoProtEntrata(Boolean abilitaUoProtEntrata) {
		this.abilitaUoProtEntrata = abilitaUoProtEntrata;
	}

	public Boolean getAbilitaUoProtUscita() {
		return abilitaUoProtUscita;
	}

	public void setAbilitaUoProtUscita(Boolean abilitaUoProtUscita) {
		this.abilitaUoProtUscita = abilitaUoProtUscita;
	}

	public Boolean getAbilitaUoAssRiservatezza() {
		return abilitaUoAssRiservatezza;
	}

	public void setAbilitaUoAssRiservatezza(Boolean abilitaUoAssRiservatezza) {
		this.abilitaUoAssRiservatezza = abilitaUoAssRiservatezza;
	}

	public Boolean getAbilitaUoGestMulte() {
		return abilitaUoGestMulte;
	}

	public void setAbilitaUoGestMulte(Boolean abilitaUoGestMulte) {
		this.abilitaUoGestMulte = abilitaUoGestMulte;
	}

	public Boolean getAbilitaUoGestContratti() {
		return abilitaUoGestContratti;
	}

	public void setAbilitaUoGestContratti(Boolean abilitaUoGestContratti) {
		this.abilitaUoGestContratti = abilitaUoGestContratti;
	}

	public String getSubProfili() {
		return subProfili;
	}

	public void setSubProfili(String subProfili) {
		this.subProfili = subProfili;
	}

	public String getTsStrutturaDaCessareDal() {
		return tsStrutturaDaCessareDal;
	}

	public void setTsStrutturaDaCessareDal(String tsStrutturaDaCessareDal) {
		this.tsStrutturaDaCessareDal = tsStrutturaDaCessareDal;
	}

	public String getOrigineCreazione() {
		return origineCreazione;
	}

	public void setOrigineCreazione(String origineCreazione) {
		this.origineCreazione = origineCreazione;
	}

	
}