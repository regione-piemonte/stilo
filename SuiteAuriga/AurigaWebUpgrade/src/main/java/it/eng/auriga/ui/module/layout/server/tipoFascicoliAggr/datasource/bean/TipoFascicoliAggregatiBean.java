/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;
import java.util.Map;

import it.eng.auriga.ui.module.layout.server.defattivitaprocedimenti.datasource.bean.AttrAddXEvtDelTipoBean;

/**
 * 
 * @author cristiano
 *
 */

public class TipoFascicoliAggregatiBean {

	/**
	 * -- Identificativo del tipo di folder
	 */
	private String idFolderType;
	/**
	 * -- Nome del tipo di folder
	 */
	private String nome;
	/**
	 * -- Identificativo del tipo di folder più generale in cui ricade
	 */
	private String idFolderTypeGen;
	/**
	 * -- Nome del tipo di folder più generale in cui ricade
	 */
	private String nomeFolderTypeGen;
	/**
	 * -- Identificativo del processo/procedimento associato al tipo folder
	 */
	private String idProcessType;
	/**
	 * -- Nome del processo/procedimento associato al tipo folder
	 */
	private String nomeProcessType;
	/**
	 * -- (valori 1/0) Se 1 indica che è richiesta la scansione dei folder del dato tipo in caso siano originariamente cartacei
	 */
	private Boolean flgDaScansionare;
	/**
	 * -- Periodo di conservazione ("Illimitato" o espresso come n.ro di anni) dei folder del dato tipo
	 */
	private String periodoConserv;
	/**
	 * - (valori 1/0) 1 indica che i folder del dato tipo vanno conservati per tempo illimitato
	 */
	private Boolean flgConservPerm;

	/**
	 * -- Periodo di conservazione espresso in anni ("Illimitato" o espresso come n.ro di anni) dei folder del dato tipo
	 */
	private Integer periodoConservInAnni;

	/**
	 * -- Descrizione del supporto di conservazione previsto per i folder del dato tipo
	 */
	private String descrizione;
	/**
	 * -- Annotazioni sul tipo di folder
	 */
	private String annotazioni;
	/**
	 * -- (valori 1/0): se 1 è richiesta abilitazione esplicita per visualizzare folder del dato tipo
	 */
	private Boolean flgRichAbilXVisualizz;
	/**
	 * -- (valori 1/0): se 1 è richiesta abilitazione esplicita per gestire (modificare, cancellare) folder del dato tipo
	 */
	private Boolean flgRichAbilXGest;
	/**
	 * -- (valori 1/0): se 1 è richiesta abilitazione esplicita per assegnare il dato tipo ad un folder
	 */
	private Boolean flgRichAbilXAssegn;
	/**
	 * -- (valori 1/0) Indicatore di tipo di folder riservato dal sistema e non modificabile da applicativo
	 */
	private Boolean flgSistema;
	/**
	 * -- 18: Timestamp di ultima modifica dei dati del tipo di folder (nel formato dato dal parametro di conf. FMT_STD_TIMESTAMP)
	 */
	private Date tsUltimoAgg;
	/**
	 * -- 19: Descrizione dell'utente di ultima modifica dei dati del tipo di folder
	 */
	private String utenteUltAgg;
	/**
	 * -- 15: Descrizione dell'applicazione (ed eventuale istanza) che si occupa del tipo di folder
	 */
	private String descApplFolderTypes;
	/**
	 * -- Lista con gli attributi addizionali previsti per i documenti del dato tipo (XML conforme a schema LISTA_STD.xsd)
	 */
	private List<AttrAddXEvtDelTipoBean> listaAttributiAddizionali;
	/**
	 * -- Id. della classificazione dei folder del dato tipo. -- Viene considerato solo se almeno uno dei due argomenti successivi è valorizzato
	 */
	private String idClassificazione;
	/**
	 * -- Stringa con i n.ri livello della classificazione dei folder del dato tipo
	 */
	private String livelliClassificazione;
	/**
	 * -- Descrizione della classificazione dei folder del dato tipo
	 */
	private String desClassificazione;
	/**
	 * -- Template secondo cui comporre il nome dei folder del dato tipo (le variabili da sostituire sono tra $)
	 */
	private String templateNome;
	/**
	 * -- Template secondo cui comporre il nome dei folder del dato tipo (le variabili da sostituire sono tra $)
	 */
	private String templateTimbro;
	/**
	 * -- Template secondo cui comporre il codice identificativo (secondario) dei folder del dato tipo (le variabili da sostituire sono tra $)
	 */
	private String templateCode;
	
	private String rowid;
	
	// Attributi dinamici
	private Map<String, Object> valori;
	private Map<String, String> tipiValori;
	
	
	public String getIdFolderType() {
		return idFolderType;
	}
	
	public void setIdFolderType(String idFolderType) {
		this.idFolderType = idFolderType;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getIdFolderTypeGen() {
		return idFolderTypeGen;
	}
	
	public void setIdFolderTypeGen(String idFolderTypeGen) {
		this.idFolderTypeGen = idFolderTypeGen;
	}
	
	public String getNomeFolderTypeGen() {
		return nomeFolderTypeGen;
	}
	
	public void setNomeFolderTypeGen(String nomeFolderTypeGen) {
		this.nomeFolderTypeGen = nomeFolderTypeGen;
	}
	
	public String getIdProcessType() {
		return idProcessType;
	}
	
	public void setIdProcessType(String idProcessType) {
		this.idProcessType = idProcessType;
	}
	
	public String getNomeProcessType() {
		return nomeProcessType;
	}
	
	public void setNomeProcessType(String nomeProcessType) {
		this.nomeProcessType = nomeProcessType;
	}
	
	public Boolean getFlgDaScansionare() {
		return flgDaScansionare;
	}
	
	public void setFlgDaScansionare(Boolean flgDaScansionare) {
		this.flgDaScansionare = flgDaScansionare;
	}
	
	public String getPeriodoConserv() {
		return periodoConserv;
	}
	
	public void setPeriodoConserv(String periodoConserv) {
		this.periodoConserv = periodoConserv;
	}
	
	public Boolean getFlgConservPerm() {
		return flgConservPerm;
	}
	
	public void setFlgConservPerm(Boolean flgConservPerm) {
		this.flgConservPerm = flgConservPerm;
	}
	
	public Integer getPeriodoConservInAnni() {
		return periodoConservInAnni;
	}
	
	public void setPeriodoConservInAnni(Integer periodoConservInAnni) {
		this.periodoConservInAnni = periodoConservInAnni;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public String getAnnotazioni() {
		return annotazioni;
	}
	
	public void setAnnotazioni(String annotazioni) {
		this.annotazioni = annotazioni;
	}
	
	public Boolean getFlgRichAbilXVisualizz() {
		return flgRichAbilXVisualizz;
	}
	
	public void setFlgRichAbilXVisualizz(Boolean flgRichAbilXVisualizz) {
		this.flgRichAbilXVisualizz = flgRichAbilXVisualizz;
	}
	
	public Boolean getFlgRichAbilXGest() {
		return flgRichAbilXGest;
	}
	
	public void setFlgRichAbilXGest(Boolean flgRichAbilXGest) {
		this.flgRichAbilXGest = flgRichAbilXGest;
	}
	
	public Boolean getFlgRichAbilXAssegn() {
		return flgRichAbilXAssegn;
	}
	
	public void setFlgRichAbilXAssegn(Boolean flgRichAbilXAssegn) {
		this.flgRichAbilXAssegn = flgRichAbilXAssegn;
	}
	
	public Boolean getFlgSistema() {
		return flgSistema;
	}
	
	public void setFlgSistema(Boolean flgSistema) {
		this.flgSistema = flgSistema;
	}
	
	public Date getTsUltimoAgg() {
		return tsUltimoAgg;
	}
	
	public void setTsUltimoAgg(Date tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
	}
	
	public String getUtenteUltAgg() {
		return utenteUltAgg;
	}
	
	public void setUtenteUltAgg(String utenteUltAgg) {
		this.utenteUltAgg = utenteUltAgg;
	}
	
	public String getDescApplFolderTypes() {
		return descApplFolderTypes;
	}
	
	public void setDescApplFolderTypes(String descApplFolderTypes) {
		this.descApplFolderTypes = descApplFolderTypes;
	}
	
	public List<AttrAddXEvtDelTipoBean> getListaAttributiAddizionali() {
		return listaAttributiAddizionali;
	}
	
	public void setListaAttributiAddizionali(List<AttrAddXEvtDelTipoBean> listaAttributiAddizionali) {
		this.listaAttributiAddizionali = listaAttributiAddizionali;
	}
	
	public String getIdClassificazione() {
		return idClassificazione;
	}
	
	public void setIdClassificazione(String idClassificazione) {
		this.idClassificazione = idClassificazione;
	}
	
	public String getLivelliClassificazione() {
		return livelliClassificazione;
	}
	
	public void setLivelliClassificazione(String livelliClassificazione) {
		this.livelliClassificazione = livelliClassificazione;
	}
	
	public String getDesClassificazione() {
		return desClassificazione;
	}
	
	public void setDesClassificazione(String desClassificazione) {
		this.desClassificazione = desClassificazione;
	}
	
	public String getTemplateNome() {
		return templateNome;
	}
	
	public void setTemplateNome(String templateNome) {
		this.templateNome = templateNome;
	}
	
	public String getTemplateTimbro() {
		return templateTimbro;
	}
	
	public void setTemplateTimbro(String templateTimbro) {
		this.templateTimbro = templateTimbro;
	}
	
	public String getTemplateCode() {
		return templateCode;
	}
	
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public Map<String, Object> getValori() {
		return valori;
	}

	public void setValori(Map<String, Object> valori) {
		this.valori = valori;
	}

	public Map<String, String> getTipiValori() {
		return tipiValori;
	}

	public void setTipiValori(Map<String, String> tipiValori) {
		this.tipiValori = tipiValori;
	}

}
