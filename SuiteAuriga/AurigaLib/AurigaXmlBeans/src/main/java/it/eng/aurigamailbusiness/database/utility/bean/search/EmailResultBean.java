/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.aurigamailbusiness.bean.TRegProtVsEmailBean;

/**
 * Bean che rappresenta un messaggio. Utilizzato come ritorno dalla ricerca
 * 
 * @author Rametta
 *
 */
@XmlRootElement
public class EmailResultBean implements Serializable {

	private static final long serialVersionUID = 3879923391518795334L;
	// Tutti i campi semplici di TEmailMgo
	private String idEmail;
	private String messageId;
	private String idCasella;
	private String flgIo;
	private String categoria;
	private long dimensione;
	private String uriEmail;
	private boolean flgSpam;
	private String flgStatoSpam;
	private String statoConsolidamento;
	private short nroAllegati;
	private short nroAllegatiFirmati;
	private boolean flgEmailFirmata;
	private boolean flgNoAssocAuto;
	private String accountMittente;
	private String oggetto;
	private String corpo;
	private Date tsLock;
	private boolean flgRichConferma;
	private Date tsAssegn;
	private boolean flgInviataRisposta;
	private boolean flgInoltrata;
	private String flgStatoProt;
	private boolean flgNotifInteropEcc;
	private boolean flgNotifInteropConf;
	private boolean flgNotifInteropAgg;
	private boolean flgNotifInteropAnn;
	private Long livPriorita;
	private Date tsLettura;
	private Date tsIns;
	private String idUteIns;
	private Date tsUltimoAgg;
	private String idUteUltimoAgg;
	private Date tsRicezione;
	private Date tsInvio;
	private Date tsInvioClient;
	private String flgRicevutaCbs;
	// Tutte le FK
	private String TUtentiModPecByIdUtenteLockId;
	private String TAnagFruitoriCaselleId;
	private String TValDizionarioByIdRecDizOperLockId;
	private String TValDizionarioByIdRecDizContrassegnoId;
	private String TUtentiModPecByIdUtenteAssegnId;
	// Decodifiche dei campi con FK
	private String TUtentiModPecByIdUtenteLockDecodifica;
	private String TAnagFruitoriCaselleDecodifica;
	private String TValDizionarioByIdRecDizOperLockDecodifica;
	private String TValDizionarioByIdRecDizContrassegnoDecodifica;
	private String TUtentiModPecByIdUtenteAssegnDecodifica;
	// Estremi protocolli associati
	private List<TRegProtVsEmailBean> regProtAssociati;
	// Flag che mi dice se sono presenti commenti
	private boolean flgPresenzaCommenti;
	private String avvertimenti;
	private List<String> destinatari;
	private List<String> destinatariCC;
	private List<String> destinatariCCN;

	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public String getTUtentiModPecByIdUtenteLockId() {
		return TUtentiModPecByIdUtenteLockId;
	}

	public void setTUtentiModPecByIdUtenteLockId(String tUtentiModPecByIdUtenteLockId) {
		TUtentiModPecByIdUtenteLockId = tUtentiModPecByIdUtenteLockId;
	}

	public String getTAnagFruitoriCaselleId() {
		return TAnagFruitoriCaselleId;
	}

	public void setTAnagFruitoriCaselleId(String tAnagFruitoriCaselleId) {
		TAnagFruitoriCaselleId = tAnagFruitoriCaselleId;
	}

	public String getTValDizionarioByIdRecDizOperLockId() {
		return TValDizionarioByIdRecDizOperLockId;
	}

	public void setTValDizionarioByIdRecDizOperLockId(String tValDizionarioByIdRecDizOperLockId) {
		TValDizionarioByIdRecDizOperLockId = tValDizionarioByIdRecDizOperLockId;
	}

	public String getTValDizionarioByIdRecDizContrassegnoId() {
		return TValDizionarioByIdRecDizContrassegnoId;
	}

	public void setTValDizionarioByIdRecDizContrassegnoId(String tValDizionarioByIdRecDizContrassegnoId) {
		TValDizionarioByIdRecDizContrassegnoId = tValDizionarioByIdRecDizContrassegnoId;
	}

	public String getTUtentiModPecByIdUtenteAssegnId() {
		return TUtentiModPecByIdUtenteAssegnId;
	}

	public void setTUtentiModPecByIdUtenteAssegnId(String tUtentiModPecByIdUtenteAssegnId) {
		TUtentiModPecByIdUtenteAssegnId = tUtentiModPecByIdUtenteAssegnId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getIdCasella() {
		return idCasella;
	}

	public void setIdCasella(String idCasella) {
		this.idCasella = idCasella;
	}

	public String getFlgIo() {
		return flgIo;
	}

	public void setFlgIo(String flgIo) {
		this.flgIo = flgIo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public long getDimensione() {
		return dimensione;
	}

	public void setDimensione(long dimensione) {
		this.dimensione = dimensione;
	}

	public String getUriEmail() {
		return uriEmail;
	}

	public void setUriEmail(String uriEmail) {
		this.uriEmail = uriEmail;
	}

	public boolean isFlgSpam() {
		return flgSpam;
	}

	public void setFlgSpam(boolean flgSpam) {
		this.flgSpam = flgSpam;
	}

	public String getFlgStatoSpam() {
		return flgStatoSpam;
	}

	public void setFlgStatoSpam(String flgStatoSpam) {
		this.flgStatoSpam = flgStatoSpam;
	}

	public String getStatoConsolidamento() {
		return statoConsolidamento;
	}

	public void setStatoConsolidamento(String statoConsolidamento) {
		this.statoConsolidamento = statoConsolidamento;
	}

	public short getNroAllegati() {
		return nroAllegati;
	}

	public void setNroAllegati(short nroAllegati) {
		this.nroAllegati = nroAllegati;
	}

	public short getNroAllegatiFirmati() {
		return nroAllegatiFirmati;
	}

	public void setNroAllegatiFirmati(short nroAllegatiFirmati) {
		this.nroAllegatiFirmati = nroAllegatiFirmati;
	}

	public boolean isFlgEmailFirmata() {
		return flgEmailFirmata;
	}

	public void setFlgEmailFirmata(boolean flgEmailFirmata) {
		this.flgEmailFirmata = flgEmailFirmata;
	}

	public boolean isFlgNoAssocAuto() {
		return flgNoAssocAuto;
	}

	public void setFlgNoAssocAuto(boolean flgNoAssocAuto) {
		this.flgNoAssocAuto = flgNoAssocAuto;
	}

	public String getAccountMittente() {
		return accountMittente;
	}

	public void setAccountMittente(String accountMittente) {
		this.accountMittente = accountMittente;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getCorpo() {
		return corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

	public Date getTsLock() {
		return tsLock;
	}

	public void setTsLock(Date tsLock) {
		this.tsLock = tsLock;
	}

	public boolean isFlgRichConferma() {
		return flgRichConferma;
	}

	public void setFlgRichConferma(boolean flgRichConferma) {
		this.flgRichConferma = flgRichConferma;
	}

	public Date getTsAssegn() {
		return tsAssegn;
	}

	public void setTsAssegn(Date tsAssegn) {
		this.tsAssegn = tsAssegn;
	}

	public boolean isFlgInviataRisposta() {
		return flgInviataRisposta;
	}

	public void setFlgInviataRisposta(boolean flgInviataRisposta) {
		this.flgInviataRisposta = flgInviataRisposta;
	}

	public boolean isFlgInoltrata() {
		return flgInoltrata;
	}

	public void setFlgInoltrata(boolean flgInoltrata) {
		this.flgInoltrata = flgInoltrata;
	}

	public String getFlgStatoProt() {
		return flgStatoProt;
	}

	public void setFlgStatoProt(String flgStatoProt) {
		this.flgStatoProt = flgStatoProt;
	}

	public boolean isFlgNotifInteropEcc() {
		return flgNotifInteropEcc;
	}

	public void setFlgNotifInteropEcc(boolean flgNotifInteropEcc) {
		this.flgNotifInteropEcc = flgNotifInteropEcc;
	}

	public boolean isFlgNotifInteropConf() {
		return flgNotifInteropConf;
	}

	public void setFlgNotifInteropConf(boolean flgNotifInteropConf) {
		this.flgNotifInteropConf = flgNotifInteropConf;
	}

	public boolean isFlgNotifInteropAgg() {
		return flgNotifInteropAgg;
	}

	public void setFlgNotifInteropAgg(boolean flgNotifInteropAgg) {
		this.flgNotifInteropAgg = flgNotifInteropAgg;
	}

	public boolean isFlgNotifInteropAnn() {
		return flgNotifInteropAnn;
	}

	public void setFlgNotifInteropAnn(boolean flgNotifInteropAnn) {
		this.flgNotifInteropAnn = flgNotifInteropAnn;
	}

	public Long getLivPriorita() {
		return livPriorita;
	}

	public void setLivPriorita(Long livPriorita) {
		this.livPriorita = livPriorita;
	}

	public Date getTsLettura() {
		return tsLettura;
	}

	public void setTsLettura(Date tsLettura) {
		this.tsLettura = tsLettura;
	}

	public Date getTsIns() {
		return tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	public String getIdUteIns() {
		return idUteIns;
	}

	public void setIdUteIns(String idUteIns) {
		this.idUteIns = idUteIns;
	}

	public Date getTsUltimoAgg() {
		return tsUltimoAgg;
	}

	public void setTsUltimoAgg(Date tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
	}

	public String getIdUteUltimoAgg() {
		return idUteUltimoAgg;
	}

	public void setIdUteUltimoAgg(String idUteUltimoAgg) {
		this.idUteUltimoAgg = idUteUltimoAgg;
	}

	public Date getTsRicezione() {
		return tsRicezione;
	}

	public void setTsRicezione(Date tsRicezione) {
		this.tsRicezione = tsRicezione;
	}

	public Date getTsInvio() {
		return tsInvio;
	}

	public void setTsInvio(Date tsInvio) {
		this.tsInvio = tsInvio;
	}

	public Date getTsInvioClient() {
		return tsInvioClient;
	}

	public void setTsInvioClient(Date tsInvioClient) {
		this.tsInvioClient = tsInvioClient;
	}

	public String getFlgRicevutaCbs() {
		return flgRicevutaCbs;
	}

	public void setFlgRicevutaCbs(String flgRicevutaCbs) {
		this.flgRicevutaCbs = flgRicevutaCbs;
	}

	public String getTUtentiModPecByIdUtenteLockDecodifica() {
		return TUtentiModPecByIdUtenteLockDecodifica;
	}

	public void setTUtentiModPecByIdUtenteLockDecodifica(String tUtentiModPecByIdUtenteLockDecodifica) {
		TUtentiModPecByIdUtenteLockDecodifica = tUtentiModPecByIdUtenteLockDecodifica;
	}

	public String getTAnagFruitoriCaselleDecodifica() {
		return TAnagFruitoriCaselleDecodifica;
	}

	public void setTAnagFruitoriCaselleDecodifica(String tAnagFruitoriCaselleDecodifica) {
		TAnagFruitoriCaselleDecodifica = tAnagFruitoriCaselleDecodifica;
	}

	public String getTValDizionarioByIdRecDizOperLockDecodifica() {
		return TValDizionarioByIdRecDizOperLockDecodifica;
	}

	public void setTValDizionarioByIdRecDizOperLockDecodifica(String tValDizionarioByIdRecDizOperLockDecodifica) {
		TValDizionarioByIdRecDizOperLockDecodifica = tValDizionarioByIdRecDizOperLockDecodifica;
	}

	public String getTValDizionarioByIdRecDizContrassegnoDecodifica() {
		return TValDizionarioByIdRecDizContrassegnoDecodifica;
	}

	public void setTValDizionarioByIdRecDizContrassegnoDecodifica(String tValDizionarioByIdRecDizContrassegnoDecodifica) {
		TValDizionarioByIdRecDizContrassegnoDecodifica = tValDizionarioByIdRecDizContrassegnoDecodifica;
	}

	public String getTUtentiModPecByIdUtenteAssegnDecodifica() {
		return TUtentiModPecByIdUtenteAssegnDecodifica;
	}

	public void setTUtentiModPecByIdUtenteAssegnDecodifica(String tUtentiModPecByIdUtenteAssegnDecodifica) {
		TUtentiModPecByIdUtenteAssegnDecodifica = tUtentiModPecByIdUtenteAssegnDecodifica;
	}

	public void setFlgPresenzaCommenti(boolean flgPresenzaCommenti) {
		this.flgPresenzaCommenti = flgPresenzaCommenti;
	}

	public boolean isFlgPresenzaCommenti() {
		return flgPresenzaCommenti;
	}

	public List<String> getDestinatari() {
		return destinatari;
	}

	public void setDestinatari(List<String> destinatari) {
		this.destinatari = destinatari;
	}

	public List<String> getDestinatariCC() {
		return destinatariCC;
	}

	public void setDestinatariCC(List<String> destinatariCC) {
		this.destinatariCC = destinatariCC;
	}

	public List<String> getDestinatariCCN() {
		return destinatariCCN;
	}

	public void setDestinatariCCN(List<String> destinatariCCN) {
		this.destinatariCCN = destinatariCCN;
	}

	public String getAvvertimenti() {
		return avvertimenti;
	}

	public void setAvvertimenti(String avvertimenti) {
		this.avvertimenti = avvertimenti;
	}

	public List<TRegProtVsEmailBean> getRegProtAssociati() {
		return regProtAssociati;
	}

	public void setRegProtAssociati(List<TRegProtVsEmailBean> regProtAssociati) {
		this.regProtAssociati = regProtAssociati;
	}

}
