/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Rappresenta l'insieme dei filtri per la ricerca sulle mail
 * @author Rametta
 *
 */
@XmlRootElement
public class FilterSearch implements Serializable{

	private static final long serialVersionUID = 7720795343553683017L;
	
	private List<String> terms;
	private LuceneSearchOption flag;
	private List<String> attributi;
	private List<String> caselle;
	private String messageId;
	private Date tsRicezioneDa;
	private Date tsRicezioneA;
	private Date invioDa;
	private Date invioA;
	private List<String> categorie;
	private List<String> statiConsolidamento;
	private List<String> statiSpam;
	private Integer presenzaAllegati;
	private Integer presenzaAllegatiFirmati;
	private Long dimensioneMin;
	private Long dimensioneMax;
	private List<String> contrassegni;
	private List<Integer> livelliPriorita;
	private StatoLettura statoLettura;
	private List<String> statiVsUtente;
	private Integer rispostaInviata;
	private Integer effettuatoInoltro;
	private Integer statoAssegnazione;
	private AssegnatarioBean assegnatario;
	private StatoProtocollaizone statoProtocollazione;
	private EstremiRegistrazioneSearchBean estremiRegistrazione;
	private Date tsRegistrazioneDa;
	private Date tsRegistrazioneA;
	private EstremiRegistrazioneRicSearchBean estremiRegistrazioneRic;
	private String idVoceRubricaDestinatario;
	private List<TipiNofificaInteropRicevute> tipiNotificaInteropRicevute;
	private PresenzaCommenti presenzaCommenti;
	
	public List<String> getTerms() {
		return terms;
	}
	public void setTerms(List<String> terms) {
		this.terms = terms;
	}
	public LuceneSearchOption getFlag() {
		return flag;
	}
	public void setFlag(LuceneSearchOption flag) {
		this.flag = flag;
	}
	public List<String> getAttributi() {
		return attributi;
	}
	public void setAttributi(List<String> attributi) {
		this.attributi = attributi;
	}
	public void setCaselle(List<String> caselle) {
		this.caselle = caselle;
	}
	public List<String> getCaselle() {
		return caselle;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getMessageId() {
		return messageId;
	}
	public Date getTsRicezioneDa() {
		return tsRicezioneDa;
	}
	public void setTsRicezioneDa(Date tsRicezioneDa) {
		this.tsRicezioneDa = tsRicezioneDa;
	}
	public Date getTsRicezioneA() {
		return tsRicezioneA;
	}
	public void setTsRicezioneA(Date tsRicezioneA) {
		this.tsRicezioneA = tsRicezioneA;
	}
	public Date getInvioDa() {
		return invioDa;
	}
	public void setInvioDa(Date invioDa) {
		this.invioDa = invioDa;
	}
	public Date getInvioA() {
		return invioA;
	}
	public void setInvioA(Date invioA) {
		this.invioA = invioA;
	}
	public void setCategorie(List<String> categorie) {
		this.categorie = categorie;
	}
	public List<String> getCategorie() {
		return categorie;
	}
	public void setStatiConsolidamento(List<String> statiConsolidamento) {
		this.statiConsolidamento = statiConsolidamento;
	}
	public List<String> getStatiConsolidamento() {
		return statiConsolidamento;
	}
	public void setStatiSpam(List<String> statiSpam) {
		this.statiSpam = statiSpam;
	}
	public List<String> getStatiSpam() {
		return statiSpam;
	}
	public void setPresenzaAllegati(Integer presenzaAllegati) {
		this.presenzaAllegati = presenzaAllegati;
	}
	public Integer getPresenzaAllegati() {
		return presenzaAllegati;
	}
	public void setPresenzaAllegatiFirmati(Integer presenzaAllegatiFirmati) {
		this.presenzaAllegatiFirmati = presenzaAllegatiFirmati;
	}
	public Integer getPresenzaAllegatiFirmati() {
		return presenzaAllegatiFirmati;
	}
	public void setDimensioneMin(Long dimensioneMin) {
		this.dimensioneMin = dimensioneMin;
	}
	public Long getDimensioneMin() {
		return dimensioneMin;
	}
	public void setDimensioneMax(Long dimensioneMax) {
		this.dimensioneMax = dimensioneMax;
	}
	public Long getDimensioneMax() {
		return dimensioneMax;
	}
	public void setContrassegni(List<String> contrassegni) {
		this.contrassegni = contrassegni;
	}
	public List<String> getContrassegni() {
		return contrassegni;
	}
	public void setLivelliPriorita(List<Integer> livelliPriorita) {
		this.livelliPriorita = livelliPriorita;
	}
	public List<Integer> getLivelliPriorita() {
		return livelliPriorita;
	}
	public void setStatoLettura(StatoLettura statoLettura) {
		this.statoLettura = statoLettura;
	}
	public StatoLettura getStatoLettura() {
		return statoLettura;
	}
	public void setStatiVsUtente(List<String> statiVsUtente) {
		this.statiVsUtente = statiVsUtente;
	}
	public List<String> getStatiVsUtente() {
		return statiVsUtente;
	}
	public void setRispostaInviata(Integer rispostaInviata) {
		this.rispostaInviata = rispostaInviata;
	}
	public Integer getRispostaInviata() {
		return rispostaInviata;
	}
	public void setEffettuatoInoltro(Integer effettuatoInoltro) {
		this.effettuatoInoltro = effettuatoInoltro;
	}
	public Integer getEffettuatoInoltro() {
		return effettuatoInoltro;
	}
	public void setStatoAssegnazione(Integer statoAssegnazione) {
		this.statoAssegnazione = statoAssegnazione;
	}
	public Integer getStatoAssegnazione() {
		return statoAssegnazione;
	}
	public void setAssegnatario(AssegnatarioBean assegnatario) {
		this.assegnatario = assegnatario;
	}
	public AssegnatarioBean getAssegnatario() {
		return assegnatario;
	}
	public void setStatoProtocollazione(StatoProtocollaizone statoProtocollazione) {
		this.statoProtocollazione = statoProtocollazione;
	}
	public StatoProtocollaizone getStatoProtocollazione() {
		return statoProtocollazione;
	}
	public void setEstremiRegistrazione(EstremiRegistrazioneSearchBean estremiRegistrazione) {
		this.estremiRegistrazione = estremiRegistrazione;
	}
	public EstremiRegistrazioneSearchBean getEstremiRegistrazione() {
		return estremiRegistrazione;
	}
	public void setTsRegistrazioneDa(Date tsRegistrazioneDa) {
		this.tsRegistrazioneDa = tsRegistrazioneDa;
	}
	public Date getTsRegistrazioneDa() {
		return tsRegistrazioneDa;
	}
	public void setTsRegistrazioneA(Date tsRegistrazioneA) {
		this.tsRegistrazioneA = tsRegistrazioneA;
	}
	public Date getTsRegistrazioneA() {
		return tsRegistrazioneA;
	}
	public void setEstremiRegistrazioneRic(EstremiRegistrazioneRicSearchBean estremiRegistrazioneRic) {
		this.estremiRegistrazioneRic = estremiRegistrazioneRic;
	}
	public EstremiRegistrazioneRicSearchBean getEstremiRegistrazioneRic() {
		return estremiRegistrazioneRic;
	}
	public void setIdVoceRubricaDestinatario(String idVoceRubricaDestinatario) {
		this.idVoceRubricaDestinatario = idVoceRubricaDestinatario;
	}
	public String getIdVoceRubricaDestinatario() {
		return idVoceRubricaDestinatario;
	}
	public void setTipiNotificaInteropRicevute(
			List<TipiNofificaInteropRicevute> tipiNotificaInteropRicevute) {
		this.tipiNotificaInteropRicevute = tipiNotificaInteropRicevute;
	}
	public List<TipiNofificaInteropRicevute> getTipiNotificaInteropRicevute() {
		return tipiNotificaInteropRicevute;
	}
	public void setPresenzaCommenti(PresenzaCommenti presenzaCommenti) {
		this.presenzaCommenti = presenzaCommenti;
	}
	public PresenzaCommenti getPresenzaCommenti() {
		return presenzaCommenti;
	}

}
