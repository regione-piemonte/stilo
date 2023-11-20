/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.CentroCostoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.SimpleValueBean;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class AttributiDinamiciOrganigrammaXmlBean  {	
		
	
	@XmlVariabile(nome = "FLG_INIBITA_ASS", tipo=TipoVariabile.SEMPLICE)
	private String flagInibitaAssegnazione;
	
	@XmlVariabile(nome = "FLG_INIBITO_INVIO_CC", tipo=TipoVariabile.SEMPLICE)
	private String flagInibitoInvioCC;
	
	@XmlVariabile(nome = "FLG_PUNTO_PROTOCOLLO", tipo=TipoVariabile.SEMPLICE)
	private String flagPuntoProtocollo;
	
	@XmlVariabile(nome = "FLG_PUNTO_RACCOLTA_EMAIL", tipo=TipoVariabile.SEMPLICE)
	private String flgPuntoRaccoltaEmail;
	
	@XmlVariabile(nome = "FLG_PUNTO_RACCOLTA_DOC", tipo=TipoVariabile.SEMPLICE)
	private String flgPuntoRaccoltaDocumenti;
	
	@XmlVariabile(nome = "FLG_INIBITA_ASS_EMAIL", tipo=TipoVariabile.SEMPLICE)
	private String flgInibitaAssegnazioneEmail;

	@XmlVariabile(nome = "DES_BREVE_UO", tipo=TipoVariabile.SEMPLICE)
	private String denominazioneBreveUO;
		
	@XmlVariabile(nome = "CDC_STRUTTURA", tipo=TipoVariabile.LISTA)
	private List<CentroCostoBean> denominazioneCdCCdR;
	
	@XmlVariabile(nome = "ID_USER_ASSESSORE", tipo=TipoVariabile.LISTA)
	private List<SimpleValueBean>  idAssessoreRif;	
	
	@XmlVariabile(nome = "ID_UFF_LIQUIDATORE", tipo=TipoVariabile.SEMPLICE)
	private String idUfficioLiquidatore;
	
	@XmlVariabile(nome = "TYPENODO_UFF_LIQUIDATORE", tipo=TipoVariabile.SEMPLICE)
	private String typeNodoUfficioLiquidatore;
	
	
	@XmlVariabile(nome = "CODRAPIDO_UFF_LIQUIDATORE", tipo=TipoVariabile.SEMPLICE)
	private String codRapidoUfficioLiquidatore;
	
	@XmlVariabile(nome = "DESC_UFF_LIQUIDATORE", tipo=TipoVariabile.SEMPLICE)
	private String descrizioneUfficioLiquidatore;
	
	
	@XmlVariabile(nome = "ID_UO_PUNTO_PROTOCOLLO_COLL", tipo = TipoVariabile.LISTA)
	private List<PuntoProtocolloCollegatoBean> uoPuntoProtocolloCollegate;
	
	@XmlVariabile(nome = "TIPO_OFFERTA_PV",      tipo = TipoVariabile.LISTA)	
	private List<TipologiaOffertaPuntoVenditaXmlBean> listaTipologieOffertePuntoVendita;

	@XmlVariabile(nome = "PROMO_PV",      tipo = TipoVariabile.LISTA)	
	private List<TipologiaPromozionePuntoVenditaXmlBean> listaTipologiePromozioniPuntoVendita;

	@XmlVariabile(nome = "COD_ISTAT_COMUNE_PV", tipo=TipoVariabile.SEMPLICE)
	private String comuneNascitaIstituzione;
	
	@XmlVariabile(nome = "NOME_COMUNE_PV", tipo=TipoVariabile.SEMPLICE)
	private String nomeComuneNascitaIstituzione;
	
	@XmlVariabile(nome = "LABEL_CHECK_SOCIO_PV", tipo=TipoVariabile.SEMPLICE)
	private String labelSocioCoopDatiClienteForm;
	
	@XmlVariabile(nome = "CATEGORIA_SOCIO_PV", tipo=TipoVariabile.SEMPLICE)
	private String categoriaSocioCoop;
	
	
	@XmlVariabile(nome = "FLG_UO_IN_ATTO", tipo=TipoVariabile.SEMPLICE)
	private String presenteAttoDefOrganigramma;
	
	@XmlVariabile(nome = "FLG_NO_PREIMP_DEST_COME_UO_PROT", tipo=TipoVariabile.SEMPLICE)
	private String inibitaPreimpDestProtEntrata;

	@XmlVariabile(nome = "FLG_ABIL_PROT_E", tipo=TipoVariabile.SEMPLICE)
	private String abilitaUoProtEntrata;

	@XmlVariabile(nome = "FLG_ABIL_PROT_U", tipo=TipoVariabile.SEMPLICE)
	private String abilitaUoProtUscita;
	
	@XmlVariabile(nome = "FLG_ABIL_UO_PROT_USCITA_FULL", tipo=TipoVariabile.SEMPLICE)
	private String abilitaUoProtUscitaFull;

	@XmlVariabile(nome = "FLG_ABIL_ASS_RISERVATEZZA", tipo=TipoVariabile.SEMPLICE)
	private String abilitaUoAssRiservatezza;

	@XmlVariabile(nome = "FLG_GEST_MULTE", tipo=TipoVariabile.SEMPLICE)
	private String abilitaUoGestMulte;

	@XmlVariabile(nome = "FLG_GEST_CONTRATTI", tipo=TipoVariabile.SEMPLICE)
	private String abilitaUoGestContratti;
	
	@XmlVariabile(nome = "FLG_UO_GARE_APPALTI", tipo=TipoVariabile.SEMPLICE)
	private String flgUfficioGareAppalti;
		
	@XmlVariabile(nome = "FLG_ABIL_SCANSIONE_MASSIVA", tipo=TipoVariabile.SEMPLICE)
	private String abilitaUoScansioneMassiva;
	
	// Attributi custom per NESTLE

	@XmlVariabile(nome = "DEST_DISCLAIMER_MSG", tipo=TipoVariabile.SEMPLICE)
	private String destinatarioDisclaimerMessage;

	@XmlVariabile(nome = "MAIL_PEO_MITT", tipo=TipoVariabile.SEMPLICE)
	private String mailMittenteOrdinaria;
	
	@XmlVariabile(nome = "MAIL_PEC_MITT", tipo=TipoVariabile.SEMPLICE)
	private String mailMittentePec;
	
	@XmlVariabile(nome = "GG_ATTESA_1_SOLLECITO", tipo=TipoVariabile.SEMPLICE)
	private String giorniAttesaPrimoSollecito;
	
	@XmlVariabile(nome = "GG_ATTESA_INVIO_POSTA", tipo=TipoVariabile.SEMPLICE)
	private String giorniAttesaInvioPosta;
	
	@XmlVariabile(nome = "INVIO_MAIL_X_UTENTE_WEB", tipo=TipoVariabile.SEMPLICE)
	private String invioMailPerUtenteWeb;
	
	@XmlVariabile(nome = "ATTIVO_EXPORT", tipo=TipoVariabile.SEMPLICE)
	private String flgExport;	
	
	@XmlVariabile(nome = "LOGO_CLUSTER_NESTLE", tipo=TipoVariabile.SEMPLICE)
	private String logoClusterNestle;
	
	
	@XmlVariabile(nome = "COD_TIPO_DOC_CLUSTER_NESTLE", tipo=TipoVariabile.SEMPLICE)
	private String tipoDocClusterNestle;
	
	@XmlVariabile(nome = "LAYOUT_DOC_CLUSTER_NESTLE", tipo=TipoVariabile.SEMPLICE)
	private String layoutDocClusterNestle;
	
	@XmlVariabile(nome = "PREF_LOTTI_CLUSTER_NESTLE", tipo=TipoVariabile.SEMPLICE)
	private String prefLottiClusterNestle;
	
	public String getFlagInibitaAssegnazione() {
		return flagInibitaAssegnazione;
	}

	public void setFlagInibitaAssegnazione(String flagInibitaAssegnazione) {
		this.flagInibitaAssegnazione = flagInibitaAssegnazione;
	}

	public String getFlagInibitoInvioCC() {
		return flagInibitoInvioCC;
	}

	public void setFlagInibitoInvioCC(String flagInibitoInvioCC) {
		this.flagInibitoInvioCC = flagInibitoInvioCC;
	}

	public String getFlagPuntoProtocollo() {
		return flagPuntoProtocollo;
	}

	public void setFlagPuntoProtocollo(String flagPuntoProtocollo) {
		this.flagPuntoProtocollo = flagPuntoProtocollo;
	}

	public List<PuntoProtocolloCollegatoBean> getUoPuntoProtocolloCollegate() {
		return uoPuntoProtocolloCollegate;
	}

	public void setUoPuntoProtocolloCollegate(List<PuntoProtocolloCollegatoBean> uoPuntoProtocolloCollegate) {
		this.uoPuntoProtocolloCollegate = uoPuntoProtocolloCollegate;
	}

	public String getDenominazioneBreveUO() {
		return denominazioneBreveUO;
	}

	public void setDenominazioneBreveUO(String denominazioneBreveUO) {
		this.denominazioneBreveUO = denominazioneBreveUO;
	}

	public List<TipologiaOffertaPuntoVenditaXmlBean> getListaTipologieOffertePuntoVendita() {
		return listaTipologieOffertePuntoVendita;
	}

	public void setListaTipologieOffertePuntoVendita(
			List<TipologiaOffertaPuntoVenditaXmlBean> listaTipologieOffertePuntoVendita) {
		this.listaTipologieOffertePuntoVendita = listaTipologieOffertePuntoVendita;
	}

	public List<TipologiaPromozionePuntoVenditaXmlBean> getListaTipologiePromozioniPuntoVendita() {
		return listaTipologiePromozioniPuntoVendita;
	}

	public void setListaTipologiePromozioniPuntoVendita(
			List<TipologiaPromozionePuntoVenditaXmlBean> listaTipologiePromozioniPuntoVendita) {
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

	public String getInibitaPreimpDestProtEntrata() {
		return inibitaPreimpDestProtEntrata;
	}

	public void setInibitaPreimpDestProtEntrata(String inibitaPreimpDestProtEntrata) {
		this.inibitaPreimpDestProtEntrata = inibitaPreimpDestProtEntrata;
	}

	public String getFlgPuntoRaccoltaEmail() {
		return flgPuntoRaccoltaEmail;
	}

	public void setFlgPuntoRaccoltaEmail(String flgPuntoRaccoltaEmail) {
		this.flgPuntoRaccoltaEmail = flgPuntoRaccoltaEmail;
	}

	public String getFlgInibitaAssegnazioneEmail() {
		return flgInibitaAssegnazioneEmail;
	}

	public void setFlgInibitaAssegnazioneEmail(String flgInibitaAssegnazioneEmail) {
		this.flgInibitaAssegnazioneEmail = flgInibitaAssegnazioneEmail;
	}

	public String getFlgPuntoRaccoltaDocumenti() {
		return flgPuntoRaccoltaDocumenti;
	}

	public void setFlgPuntoRaccoltaDocumenti(String flgPuntoRaccoltaDocumenti) {
		this.flgPuntoRaccoltaDocumenti = flgPuntoRaccoltaDocumenti;
	}

	public List<CentroCostoBean> getDenominazioneCdCCdR() {
		return denominazioneCdCCdR;
	}

	public void setDenominazioneCdCCdR(List<CentroCostoBean> denominazioneCdCCdR) {
		this.denominazioneCdCCdR = denominazioneCdCCdR;
	}

	

	public String getIdUfficioLiquidatore() {
		return idUfficioLiquidatore;
	}

	public void setIdUfficioLiquidatore(String idUfficioLiquidatore) {
		this.idUfficioLiquidatore = idUfficioLiquidatore;
	}

	public String getCodRapidoUfficioLiquidatore() {
		return codRapidoUfficioLiquidatore;
	}

	public void setCodRapidoUfficioLiquidatore(String codRapidoUfficioLiquidatore) {
		this.codRapidoUfficioLiquidatore = codRapidoUfficioLiquidatore;
	}

	public String getDescrizioneUfficioLiquidatore() {
		return descrizioneUfficioLiquidatore;
	}

	public void setDescrizioneUfficioLiquidatore(String descrizioneUfficioLiquidatore) {
		this.descrizioneUfficioLiquidatore = descrizioneUfficioLiquidatore;
	}

	public String getTypeNodoUfficioLiquidatore() {
		return typeNodoUfficioLiquidatore;
	}

	public void setTypeNodoUfficioLiquidatore(String typeNodoUfficioLiquidatore) {
		this.typeNodoUfficioLiquidatore = typeNodoUfficioLiquidatore;
	}

	public List<SimpleValueBean> getIdAssessoreRif() {
		return idAssessoreRif;
	}

	public void setIdAssessoreRif(List<SimpleValueBean> idAssessoreRif) {
		this.idAssessoreRif = idAssessoreRif;
	}



	public String getFlgUfficioGareAppalti() {
		return flgUfficioGareAppalti;
	}

	public void setFlgUfficioGareAppalti(String flgUfficioGareAppalti) {
		this.flgUfficioGareAppalti = flgUfficioGareAppalti;
	}

	public String getAbilitaUoScansioneMassiva() {
		return abilitaUoScansioneMassiva;
	}

	public void setAbilitaUoScansioneMassiva(String abilitaUoScansioneMassiva) {
		this.abilitaUoScansioneMassiva = abilitaUoScansioneMassiva;
	}

	public String getAbilitaUoProtUscitaFull() {
		return abilitaUoProtUscitaFull;
	}

	public void setAbilitaUoProtUscitaFull(String abilitaUoProtUscitaFull) {
		this.abilitaUoProtUscitaFull = abilitaUoProtUscitaFull;
	}

	public String getAbilitaUoProtEntrata() {
		return abilitaUoProtEntrata;
	}

	public void setAbilitaUoProtEntrata(String abilitaUoProtEntrata) {
		this.abilitaUoProtEntrata = abilitaUoProtEntrata;
	}

	public String getAbilitaUoProtUscita() {
		return abilitaUoProtUscita;
	}

	public void setAbilitaUoProtUscita(String abilitaUoProtUscita) {
		this.abilitaUoProtUscita = abilitaUoProtUscita;
	}

	public String getAbilitaUoAssRiservatezza() {
		return abilitaUoAssRiservatezza;
	}

	public void setAbilitaUoAssRiservatezza(String abilitaUoAssRiservatezza) {
		this.abilitaUoAssRiservatezza = abilitaUoAssRiservatezza;
	}

	public String getAbilitaUoGestMulte() {
		return abilitaUoGestMulte;
	}

	public void setAbilitaUoGestMulte(String abilitaUoGestMulte) {
		this.abilitaUoGestMulte = abilitaUoGestMulte;
	}

	public String getAbilitaUoGestContratti() {
		return abilitaUoGestContratti;
	}

	public void setAbilitaUoGestContratti(String abilitaUoGestContratti) {
		this.abilitaUoGestContratti = abilitaUoGestContratti;
	}



}
