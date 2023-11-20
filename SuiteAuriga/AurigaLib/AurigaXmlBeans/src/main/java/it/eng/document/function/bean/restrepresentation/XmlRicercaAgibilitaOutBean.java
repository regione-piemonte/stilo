/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.List;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.ValueBean;

/**
 * @author Antonio Peluso
 */

public class XmlRicercaAgibilitaOutBean {
	//	ID_UD assegnato alla risposta protocollata
	@XmlVariabile(nome = "IdUDProtocolloRisposta", tipo = TipoVariabile.SEMPLICE)
	private String idUDProtocolloRisposta;
	
	// IdDominio Id. del dominio di lavoro
	@XmlVariabile(nome = "IdDominio", tipo = TipoVariabile.SEMPLICE)
	private String idDominio;
	
	// FlgTpDominio	Indica il tipo di dominio: 2 = Ente; 3 = AOO
	@XmlVariabile(nome = "FlgTpDominio", tipo = TipoVariabile.SEMPLICE)
	private String flgTpDominio;
	
	//	ID_DOC del documento primario del protocollo di risposta
	@XmlVariabile(nome = "IdDocPrimarioProtocolloRisposta", tipo = TipoVariabile.SEMPLICE)
	private String idDocPrimarioProtocolloRisposta;
	
	//	Nome del modello da usare per generare il pdf della risposta
	@XmlVariabile(nome = "NomeModelloRisposta", tipo = TipoVariabile.SEMPLICE)
	private String nomeModelloRisposta;
	
	//	URI del file del modello da usare per generare il pdf della risposta
	@XmlVariabile(nome = "URIModelloRisposta", tipo = TipoVariabile.SEMPLICE)
	private String uriModelloRisposta;
	
	//	Indica il tipo del modello da usare per generare il pdf della risposta
	@XmlVariabile(nome = "TipoModelloRisposta", tipo = TipoVariabile.SEMPLICE)
	private String tipoModelloRisposta;
		
	//	Id del modello da usare per generare il pdf della risposta
	@XmlVariabile(nome = "IdModelloRisposta", tipo = TipoVariabile.SEMPLICE)
	private String idModelloRisposta;
	
	//	Stringa da mettere nel timbro da apporre sul pdf della risposta generata da modello
	@XmlVariabile(nome = "ContenutoTimbroRisposta", tipo = TipoVariabile.SEMPLICE)
	private String contenutoTimbroRisposta;
	
	//	Testo da mettere in chiaro accanto al timbro della risposta generata da modello
	@XmlVariabile(nome = "TestoInChiaroPerTimbroRisposta", tipo = TipoVariabile.SEMPLICE)
	private String testoInChiaroPerTimbroRisposta;
	
	//	n.ro del protocollo in entrata assegnato alla richiesta
	@XmlVariabile(nome = "NroProtocolloRichiesta", tipo = TipoVariabile.SEMPLICE)
	private String nroProtocolloRichiesta;
	
	//	nel formato GG/MM/AAAA HH24:MI:SS
	@XmlVariabile(nome = "DataOraProtocolloRichiesta", tipo = TipoVariabile.SEMPLICE)
	private String dataOraProtocolloRichiesta;
	
	//	indirizzo mail da cui mandare la mail di risposta
	@XmlVariabile(nome = "IndirizzoMittenteEmailRisposta", tipo = TipoVariabile.SEMPLICE)
	private String indirizzoMittenteEmailRisposta;
	
	// 	Id. della casella da cui mandare la mail di risposta
	@XmlVariabile(nome = "IdCasellaMittenteEmailRisposta", tipo = TipoVariabile.SEMPLICE)
	private String idCasellaMittenteEmailRisposta;
	
	//	Indirizzi destinatari principali della mail di risposta (se più di uno separati da ,)
	@XmlVariabile(nome = "DestinatariEmailRisposta", tipo = TipoVariabile.SEMPLICE)
	private String destinatariEmailRisposta;
	
	//	Oggetto della mail di risposta
	@XmlVariabile(nome = "OggettoEmailRisposta", tipo = TipoVariabile.SEMPLICE)
	private String oggettoEmailRisposta;
	
	//	Corpo della mail di risposta (è un html)
	@XmlVariabile(nome = "CorpoEmailRisposta", tipo = TipoVariabile.SEMPLICE)
	private String corpoEmailRisposta;
	
	// Se 1 significa che i file delle agibilità vanno allegati alla mail, se 0 no
	@XmlVariabile(nome = "AgibilitaAllegateEmail", tipo = TipoVariabile.SEMPLICE)
	private String agibilitaAllegateEmail;
	
	//lista con una sola colonna contenete i nri delle agibilità trovate
	@XmlVariabile(nome = "@ListaAgibilita", tipo = TipoVariabile.LISTA)
	private List<ValueBean> listaAgibilita;
	
	//lista formata dalle seguenti colonne
	@XmlVariabile(nome = "@ListaFileAgibilita", tipo = TipoVariabile.LISTA)
	private List<FileAgibilitaOutBean> listaFileAgibilita;
	
	//token da utilizzare per le connessioni alle store
	@XmlVariabile(nome = "ConnToken", tipo = TipoVariabile.SEMPLICE)
	private String connectionToken;

	public String getIdUDProtocolloRisposta() {
		return idUDProtocolloRisposta;
	}

	public void setIdUDProtocolloRisposta(String idUDProtocolloRisposta) {
		this.idUDProtocolloRisposta = idUDProtocolloRisposta;
	}

	public String getIdDocPrimarioProtocolloRisposta() {
		return idDocPrimarioProtocolloRisposta;
	}

	public void setIdDocPrimarioProtocolloRisposta(String idDocPrimarioProtocolloRisposta) {
		this.idDocPrimarioProtocolloRisposta = idDocPrimarioProtocolloRisposta;
	}

	public String getNomeModelloRisposta() {
		return nomeModelloRisposta;
	}

	public void setNomeModelloRisposta(String nomeModelloRisposta) {
		this.nomeModelloRisposta = nomeModelloRisposta;
	}

	public String getUriModelloRisposta() {
		return uriModelloRisposta;
	}

	public void setUriModelloRisposta(String uriModelloRisposta) {
		this.uriModelloRisposta = uriModelloRisposta;
	}

	public String getTipoModelloRisposta() {
		return tipoModelloRisposta;
	}

	public void setTipoModelloRisposta(String tipoModelloRisposta) {
		this.tipoModelloRisposta = tipoModelloRisposta;
	}

	public String getIdModelloRisposta() {
		return idModelloRisposta;
	}

	public void setIdModelloRisposta(String idModelloRisposta) {
		this.idModelloRisposta = idModelloRisposta;
	}

	public String getContenutoTimbroRisposta() {
		return contenutoTimbroRisposta;
	}

	public void setContenutoTimbroRisposta(String contenutoTimbroRisposta) {
		this.contenutoTimbroRisposta = contenutoTimbroRisposta;
	}

	public String getTestoInChiaroPerTimbroRisposta() {
		return testoInChiaroPerTimbroRisposta;
	}

	public void setTestoInChiaroPerTimbroRisposta(String testoInChiaroPerTimbroRisposta) {
		this.testoInChiaroPerTimbroRisposta = testoInChiaroPerTimbroRisposta;
	}

	public String getNroProtocolloRichiesta() {
		return nroProtocolloRichiesta;
	}

	public void setNroProtocolloRichiesta(String nroProtocolloRichiesta) {
		this.nroProtocolloRichiesta = nroProtocolloRichiesta;
	}

	public String getDataOraProtocolloRichiesta() {
		return dataOraProtocolloRichiesta;
	}

	public void setDataOraProtocolloRichiesta(String dataOraProtocolloRichiesta) {
		this.dataOraProtocolloRichiesta = dataOraProtocolloRichiesta;
	}

	public String getIndirizzoMittenteEmailRisposta() {
		return indirizzoMittenteEmailRisposta;
	}

	public void setIndirizzoMittenteEmailRisposta(String indirizzoMittenteEmailRisposta) {
		this.indirizzoMittenteEmailRisposta = indirizzoMittenteEmailRisposta;
	}

	public String getIdCasellaMittenteEmailRisposta() {
		return idCasellaMittenteEmailRisposta;
	}

	public void setIdCasellaMittenteEmailRisposta(String idCasellaMittenteEmailRisposta) {
		this.idCasellaMittenteEmailRisposta = idCasellaMittenteEmailRisposta;
	}

	public String getDestinatariEmailRisposta() {
		return destinatariEmailRisposta;
	}

	public void setDestinatariEmailRisposta(String destinatariEmailRisposta) {
		this.destinatariEmailRisposta = destinatariEmailRisposta;
	}

	public String getOggettoEmailRisposta() {
		return oggettoEmailRisposta;
	}

	public void setOggettoEmailRisposta(String oggettoEmailRisposta) {
		this.oggettoEmailRisposta = oggettoEmailRisposta;
	}

	public String getCorpoEmailRisposta() {
		return corpoEmailRisposta;
	}

	public void setCorpoEmailRisposta(String corpoEmailRisposta) {
		this.corpoEmailRisposta = corpoEmailRisposta;
	}

	public String getAgibilitaAllegateEmail() {
		return agibilitaAllegateEmail;
	}

	public void setAgibilitaAllegateEmail(String agibilitaAllegateEmail) {
		this.agibilitaAllegateEmail = agibilitaAllegateEmail;
	}

	public List<ValueBean> getListaAgibilita() {
		return listaAgibilita;
	}

	public void setListaAgibilita(List<ValueBean> listaAgibilita) {
		this.listaAgibilita = listaAgibilita;
	}

	public List<FileAgibilitaOutBean> getListaFileAgibilita() {
		return listaFileAgibilita;
	}

	public void setListaFileAgibilita(List<FileAgibilitaOutBean> listaFileAgibilita) {
		this.listaFileAgibilita = listaFileAgibilita;
	}

	public String getIdDominio() {
		return idDominio;
	}

	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}

	public String getFlgTpDominio() {
		return flgTpDominio;
	}

	public void setFlgTpDominio(String flgTpDominio) {
		this.flgTpDominio = flgTpDominio;
	}

	public String getConnectionToken() {
		return connectionToken;
	}

	public void setConnectionToken(String connectionToken) {
		this.connectionToken = connectionToken;
	}
		
}


