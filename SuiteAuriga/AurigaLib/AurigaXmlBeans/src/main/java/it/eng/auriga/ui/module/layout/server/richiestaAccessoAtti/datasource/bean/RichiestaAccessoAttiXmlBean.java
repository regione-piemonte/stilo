/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

public class RichiestaAccessoAttiXmlBean {

	@NumeroColonna(numero = "2")
	private String idUd;
	
	@NumeroColonna(numero = "4")
	private String protocolloONumRichiesta;

	@NumeroColonna(numero = "8")
	private String protocollo;

	@NumeroColonna(numero = "9")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataProtocollo;

	@NumeroColonna(numero = "14")
	private String numUffRichiedente;
	
	@NumeroColonna(numero = "62")
	private String datiNotifica;

	@NumeroColonna(numero = "91")
	private String richEsterno;

	@NumeroColonna(numero = "101")
	private String indirizzo;

	@NumeroColonna(numero = "102")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInvioApprovazione;

	@NumeroColonna(numero = "103")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataApprovazione;

	@NumeroColonna(numero = "104")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataEsitoCittadella;

	@NumeroColonna(numero = "105")
	@TipoData(tipo = Tipo.DATA)
	private Date dataAppuntamento;

	@NumeroColonna(numero = "106")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataPrelievo;

	@NumeroColonna(numero = "107")
	private String statoPrelievo;

	@NumeroColonna(numero = "108")
	private String richApprovataDa;
	
	@NumeroColonna(numero = "201")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataProtocolloORichiesta;

	@NumeroColonna(numero = "214")
	private String richRegistrataDa;

	@NumeroColonna(numero = "237")
	private String udc;

	@NumeroColonna(numero = "250")
	private String elencoIdFolderAttiRich;

	@NumeroColonna(numero = "251")
	private String elencoProtocolliAttiRich;
	
	@NumeroColonna(numero = "252")
	private String attiNonPresentiInCittadella;

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
		
	public String getProtocolloONumRichiesta() {
		return protocolloONumRichiesta;
	}

	public void setProtocolloONumRichiesta(String protocolloONumRichiesta) {
		this.protocolloONumRichiesta = protocolloONumRichiesta;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public Date getDataProtocollo() {
		return dataProtocollo;
	}

	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}

	public String getNumUffRichiedente() {
		return numUffRichiedente;
	}

	public void setNumUffRichiedente(String numUffRichiedente) {
		this.numUffRichiedente = numUffRichiedente;
	}

	public String getDatiNotifica() {
		return datiNotifica;
	}

	public void setDatiNotifica(String datiNotifica) {
		this.datiNotifica = datiNotifica;
	}

	public String getRichEsterno() {
		return richEsterno;
	}

	public void setRichEsterno(String richEsterno) {
		this.richEsterno = richEsterno;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public Date getDataInvioApprovazione() {
		return dataInvioApprovazione;
	}

	public void setDataInvioApprovazione(Date dataInvioApprovazione) {
		this.dataInvioApprovazione = dataInvioApprovazione;
	}

	public Date getDataApprovazione() {
		return dataApprovazione;
	}

	public void setDataApprovazione(Date dataApprovazione) {
		this.dataApprovazione = dataApprovazione;
	}

	public Date getDataEsitoCittadella() {
		return dataEsitoCittadella;
	}

	public void setDataEsitoCittadella(Date dataEsitoCittadella) {
		this.dataEsitoCittadella = dataEsitoCittadella;
	}

	public Date getDataAppuntamento() {
		return dataAppuntamento;
	}

	public void setDataAppuntamento(Date dataAppuntamento) {
		this.dataAppuntamento = dataAppuntamento;
	}

	public Date getDataPrelievo() {
		return dataPrelievo;
	}

	public void setDataPrelievo(Date dataPrelievo) {
		this.dataPrelievo = dataPrelievo;
	}

	public String getStatoPrelievo() {
		return statoPrelievo;
	}

	public void setStatoPrelievo(String statoPrelievo) {
		this.statoPrelievo = statoPrelievo;
	}

	public String getRichApprovataDa() {
		return richApprovataDa;
	}

	public void setRichApprovataDa(String richApprovataDa) {
		this.richApprovataDa = richApprovataDa;
	}
	
	public Date getDataProtocolloORichiesta() {
		return dataProtocolloORichiesta;
	}

	public void setDataProtocolloORichiesta(Date dataProtocolloORichiesta) {
		this.dataProtocolloORichiesta = dataProtocolloORichiesta;
	}

	public String getRichRegistrataDa() {
		return richRegistrataDa;
	}

	public void setRichRegistrataDa(String richRegistrataDa) {
		this.richRegistrataDa = richRegistrataDa;
	}

	public String getUdc() {
		return udc;
	}

	public void setUdc(String udc) {
		this.udc = udc;
	}

	public String getElencoIdFolderAttiRich() {
		return elencoIdFolderAttiRich;
	}

	public void setElencoIdFolderAttiRich(String elencoIdFolderAttiRich) {
		this.elencoIdFolderAttiRich = elencoIdFolderAttiRich;
	}

	public String getElencoProtocolliAttiRich() {
		return elencoProtocolliAttiRich;
	}

	public void setElencoProtocolliAttiRich(String elencoProtocolliAttiRich) {
		this.elencoProtocolliAttiRich = elencoProtocolliAttiRich;
	}

	public String getAttiNonPresentiInCittadella() {
		return attiNonPresentiInCittadella;
	}
	
	public void setAttiNonPresentiInCittadella(String attiNonPresentiInCittadella) {
		this.attiNonPresentiInCittadella = attiNonPresentiInCittadella;
	}

}
