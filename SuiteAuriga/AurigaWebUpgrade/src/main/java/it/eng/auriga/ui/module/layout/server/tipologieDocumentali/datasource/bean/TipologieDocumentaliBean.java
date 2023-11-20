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

public class TipologieDocumentaliBean {

	/**
	 * -- 1: Id.
	 */
	private String idTipoDoc;
	/**
	 * -- 2: Nome
	 */
	private String nome;
	/**
	 * -- 3: Descrizione
	 */
	private String descrizione;

	/**
	 * -- Identificativo del tipo di documento in cui ricade
	 */
	private String idDocTypeGen;
	/**
	 * -- 4: Sotto-tipologia di
	 */
	private String nomeDocTypeGen;
	/**
	 * -- Identificativo del processo/procedimento associato al tipo documento
	 */
	private String idProcessType;
	/**
	 * -- Nome del processo/procedimento associato al tipo documento
	 */
	private String nomeProcessType;
	/**
	 * -- 14: Periodo di conservazione ("Illimitato" o espresso come n.ro di anni) dei documenti del dato tipo
	 */
	private String periodoConserv;
	/**
	 * -- 15: Descrizione del supporto di conservazione previsto per i documenti del dato tipo
	 */
	private String descrSuppConserv;
	/**
	 * -- 16: Specifiche di accessibilità
	 */
	private String specAccess;
	/**
	 * -- 17: Specifiche di riproducibilità
	 */
	private String specRiprod;
	/**
	 * -- 20: Annotazioni
	 */
	private String annotazioni;
	/**
	 * -- 21: Valido (la stored dà flag di annullamento, quindi se 0 è valido, se 1 è non valido)
	 */
	private Boolean valido;
	/**
	 * -- 24: Creata il
	 */
	private Date creataIl;
	/**
	 * -- 25: Creata da
	 */
	private String creataDa;
	/**
	 * -- 26: Ultimo aggiornamento il
	 */
	private Date ultAggiorn;
	/**
	 * -- 27: Ultimo aggiornamento effettuato da
	 */
	private String ultAggiornEffeDa;
	/**
	 * -- 28: (valori 1/0) Indicatore di tipologia riservata dal sistema e non modificabile/cancellabile da applicativo
	 */
	private Boolean flgSistema;
	/**
	 * -- 29: (valori 1/0): Rich. abil. per visualizzzione
	 */
	private Boolean flgRichAbilVis;
	/**
	 * -- 30: (valori 1/0): Ruch. abil. per lavorazione
	 */
	private Boolean flgAbilLavor;
	/**
	 * -- (valori 1/0/NULL): se 1 è richiesta abilitazione esplicita per gestire (modificare, versionare, cancellare) documenti del dato tipo
	 */
	private Boolean flgRichAbilXGestIn;
	/**
	 * -- (valori 1/0/NULL): se 1 è richiesta abilitazione esplicita per assegnare il dato tipo ad un documento
	 */
	private Boolean flgRichAbilXAssegnIn;
	/**
	 * -- 31: (valori 1/0): Rich. abil. per utizzo
	 */
	private Boolean flgAbilUtilizzo;
	/**
	 * -- 32: (valori 1/0): Rich. abil. per firma
	 */
	private Boolean flgAbilFirma;
	/**
	 * -- 33: (valori 1/0): tipologia associata ad un iter workflow
	 */
	private Boolean flgIsAssociataIterWf;
	/**
	 * -- 34: (valori P/V/NULL): Richiesta firma digitale per i file della tipologia
	 */
	private String flgRichFirmaDigitale;
	/**
	 * Escludi annullati (check-box): FlgInclAnnullatiIO. Se è ceccato passo NULL o 0, se no passo 1
	 */
	private Boolean flgInclAnn;
	/**
	 * -- (valori 1/0/NULL) 1 indica che le unità documentarie del dato tipo vanno conservate per tempo illimitato
	 */
	private Boolean flgConservPermIn;
	/**
	 * -- Periodo minimo di conservazione (espresso in anni) delle unità documentarie del dato tipo.
	 */
	private Integer periodoConservAnni;
	/**
	 * -- Lista con gli attributi addizionali previsti per i documenti del dato tipo (XML conforme a schema LISTA_STD.xsd)
	 */
	private List<AttrAddXEvtDelTipoBean> listaAttributiAddizionali;
	
	/**
	 * -- Indica le UO e i gruppi di privilegi abilitati alla pubblicazione dei documenti della data tipologia
	 */
	private List<UoGpPrivAbilitatiPubblicazioneTipologieDocumentaliXmlBean> listaUoGpPrivAbilitatiPubblicazione;
	
	
	/**
	 * -- Template secondo cui comporre il timbro da apporre sui documenti del dato tipo (le variabili da sostituire sono tra $)
	 */
	private String templateTimbroUD;
	/**
	 * -- Template secondo cui comporre il codice identificativo (secondario) dei folder del dato tipo (le variabili da sostituire sono tra $)
	 */
	private String templateNomeUD;
	
	/**
	 * -- Indica se in genere i documenti del dato tipo sono in entrata (E), interni (I) o in uscita (U) dal soggetto produttore / AOO (valori E/I/U) 
	 */
	private String flgTipoProv;
	
	/**
	 * -- (valori 1/2/0) Indica se i documenti del dato tipo possono (1) o devono (2) essere degli allegati o possono essere solo primari (0) 
	 */
	private String flgAllegato;
	
	
	private String rowid;
	
	// Attributi dinamici
	private Map<String, Object> valori;
	private Map<String, String> tipiValori;
	public String getIdTipoDoc() {
		return idTipoDoc;
	}
	public void setIdTipoDoc(String idTipoDoc) {
		this.idTipoDoc = idTipoDoc;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getIdDocTypeGen() {
		return idDocTypeGen;
	}
	public void setIdDocTypeGen(String idDocTypeGen) {
		this.idDocTypeGen = idDocTypeGen;
	}
	public String getNomeDocTypeGen() {
		return nomeDocTypeGen;
	}
	public void setNomeDocTypeGen(String nomeDocTypeGen) {
		this.nomeDocTypeGen = nomeDocTypeGen;
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
	public String getPeriodoConserv() {
		return periodoConserv;
	}
	public void setPeriodoConserv(String periodoConserv) {
		this.periodoConserv = periodoConserv;
	}
	public String getDescrSuppConserv() {
		return descrSuppConserv;
	}
	public void setDescrSuppConserv(String descrSuppConserv) {
		this.descrSuppConserv = descrSuppConserv;
	}
	public String getSpecAccess() {
		return specAccess;
	}
	public void setSpecAccess(String specAccess) {
		this.specAccess = specAccess;
	}
	public String getSpecRiprod() {
		return specRiprod;
	}
	public void setSpecRiprod(String specRiprod) {
		this.specRiprod = specRiprod;
	}
	public String getAnnotazioni() {
		return annotazioni;
	}
	public void setAnnotazioni(String annotazioni) {
		this.annotazioni = annotazioni;
	}
	public Boolean getValido() {
		return valido;
	}
	public void setValido(Boolean valido) {
		this.valido = valido;
	}
	public Date getCreataIl() {
		return creataIl;
	}
	public void setCreataIl(Date creataIl) {
		this.creataIl = creataIl;
	}
	public String getCreataDa() {
		return creataDa;
	}
	public void setCreataDa(String creataDa) {
		this.creataDa = creataDa;
	}
	public Date getUltAggiorn() {
		return ultAggiorn;
	}
	public void setUltAggiorn(Date ultAggiorn) {
		this.ultAggiorn = ultAggiorn;
	}
	public String getUltAggiornEffeDa() {
		return ultAggiornEffeDa;
	}
	public void setUltAggiornEffeDa(String ultAggiornEffeDa) {
		this.ultAggiornEffeDa = ultAggiornEffeDa;
	}
	public Boolean getFlgSistema() {
		return flgSistema;
	}
	public void setFlgSistema(Boolean flgSistema) {
		this.flgSistema = flgSistema;
	}
	public Boolean getFlgRichAbilVis() {
		return flgRichAbilVis;
	}
	public void setFlgRichAbilVis(Boolean flgRichAbilVis) {
		this.flgRichAbilVis = flgRichAbilVis;
	}
	public Boolean getFlgAbilLavor() {
		return flgAbilLavor;
	}
	public void setFlgAbilLavor(Boolean flgAbilLavor) {
		this.flgAbilLavor = flgAbilLavor;
	}
	public Boolean getFlgRichAbilXGestIn() {
		return flgRichAbilXGestIn;
	}
	public void setFlgRichAbilXGestIn(Boolean flgRichAbilXGestIn) {
		this.flgRichAbilXGestIn = flgRichAbilXGestIn;
	}
	public Boolean getFlgRichAbilXAssegnIn() {
		return flgRichAbilXAssegnIn;
	}
	public void setFlgRichAbilXAssegnIn(Boolean flgRichAbilXAssegnIn) {
		this.flgRichAbilXAssegnIn = flgRichAbilXAssegnIn;
	}
	public Boolean getFlgAbilUtilizzo() {
		return flgAbilUtilizzo;
	}
	public void setFlgAbilUtilizzo(Boolean flgAbilUtilizzo) {
		this.flgAbilUtilizzo = flgAbilUtilizzo;
	}
	public Boolean getFlgAbilFirma() {
		return flgAbilFirma;
	}
	public void setFlgAbilFirma(Boolean flgAbilFirma) {
		this.flgAbilFirma = flgAbilFirma;
	}
	public Boolean getFlgIsAssociataIterWf() {
		return flgIsAssociataIterWf;
	}
	public void setFlgIsAssociataIterWf(Boolean flgIsAssociataIterWf) {
		this.flgIsAssociataIterWf = flgIsAssociataIterWf;
	}
	public String getFlgRichFirmaDigitale() {
		return flgRichFirmaDigitale;
	}
	public void setFlgRichFirmaDigitale(String flgRichFirmaDigitale) {
		this.flgRichFirmaDigitale = flgRichFirmaDigitale;
	}
	public Boolean getFlgInclAnn() {
		return flgInclAnn;
	}
	public void setFlgInclAnn(Boolean flgInclAnn) {
		this.flgInclAnn = flgInclAnn;
	}
	public Boolean getFlgConservPermIn() {
		return flgConservPermIn;
	}
	public void setFlgConservPermIn(Boolean flgConservPermIn) {
		this.flgConservPermIn = flgConservPermIn;
	}
	public Integer getPeriodoConservAnni() {
		return periodoConservAnni;
	}
	public void setPeriodoConservAnni(Integer periodoConservAnni) {
		this.periodoConservAnni = periodoConservAnni;
	}
	public List<AttrAddXEvtDelTipoBean> getListaAttributiAddizionali() {
		return listaAttributiAddizionali;
	}
	public void setListaAttributiAddizionali(List<AttrAddXEvtDelTipoBean> listaAttributiAddizionali) {
		this.listaAttributiAddizionali = listaAttributiAddizionali;
	}
	public List<UoGpPrivAbilitatiPubblicazioneTipologieDocumentaliXmlBean> getListaUoGpPrivAbilitatiPubblicazione() {
		return listaUoGpPrivAbilitatiPubblicazione;
	}
	public void setListaUoGpPrivAbilitatiPubblicazione(
			List<UoGpPrivAbilitatiPubblicazioneTipologieDocumentaliXmlBean> listaUoGpPrivAbilitatiPubblicazione) {
		this.listaUoGpPrivAbilitatiPubblicazione = listaUoGpPrivAbilitatiPubblicazione;
	}
	public String getTemplateTimbroUD() {
		return templateTimbroUD;
	}
	public void setTemplateTimbroUD(String templateTimbroUD) {
		this.templateTimbroUD = templateTimbroUD;
	}
	public String getTemplateNomeUD() {
		return templateNomeUD;
	}
	public void setTemplateNomeUD(String templateNomeUD) {
		this.templateNomeUD = templateNomeUD;
	}
	public String getFlgTipoProv() {
		return flgTipoProv;
	}
	public void setFlgTipoProv(String flgTipoProv) {
		this.flgTipoProv = flgTipoProv;
	}
	public String getFlgAllegato() {
		return flgAllegato;
	}
	public void setFlgAllegato(String flgAllegato) {
		this.flgAllegato = flgAllegato;
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