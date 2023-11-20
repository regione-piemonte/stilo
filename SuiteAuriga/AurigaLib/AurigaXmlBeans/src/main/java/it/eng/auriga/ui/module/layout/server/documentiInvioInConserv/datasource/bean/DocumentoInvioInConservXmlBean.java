/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

/**
 * 
 * @author DANCRIST
 *
 */

public class DocumentoInvioInConservXmlBean {
	
	@NumeroColonna(numero = "2")
	private BigDecimal idUd;
	
	@NumeroColonna(numero = "3")
	private String estremiDocumento;
	
	@NumeroColonna(numero = "22")
	private String nomeFile;

	@NumeroColonna(numero = "32")
	private String nomeTipoDocumento;
	
	@NumeroColonna(numero = "33")
	private String idDoc;

	@NumeroColonna(numero = "53")
	private String statoDocumento;
	
	@NumeroColonna(numero = "101")
	@TipoData(tipo = Tipo.DATA)
	private Date dataMessaADisp;
	
	@NumeroColonna(numero = "102")
	private String idLotto;
	
	@NumeroColonna(numero = "230")
	private String uriFile;

	@NumeroColonna(numero = "238")
	private String idDocOriginale;
	
	@NumeroColonna(numero = "239")
	private String nomeClasseDocumentale;	
	
	@NumeroColonna(numero = "240")
	private String nomeSottoclasseDocumentale;
	
	@NumeroColonna(numero = "241")
	private String idUltimoPdV;
	
	@NumeroColonna(numero = "242")
	@TipoData(tipo = Tipo.DATA)
	private Date dataInvio;
	
	@NumeroColonna(numero = "243")
	@TipoData(tipo = Tipo.DATA)
	private Date dataAccettazione;
	
	@NumeroColonna(numero = "244")
	@TipoData(tipo = Tipo.DATA)
	private Date dataInizioConservazione;
	
	@NumeroColonna(numero = "245")
	private String idDocSdC;
	
	@NumeroColonna(numero = "246")
	@TipoData(tipo = Tipo.DATA)
	private Date dataTermineConservazione;
	
	@NumeroColonna(numero = "247")
	@TipoData(tipo = Tipo.DATA)
	private Date dataScarto;
	
	@NumeroColonna(numero = "248")
	private Boolean flgProlungamentoTermini;
	
	@NumeroColonna(numero = "249")
	private Boolean flgRichEsib;

	
	public String getEstremiDocumento() {
		return estremiDocumento;
	}

	
	public void setEstremiDocumento(String estremiDocumento) {
		this.estremiDocumento = estremiDocumento;
	}

	
	public String getNomeFile() {
		return nomeFile;
	}

	
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	
	public String getNomeTipoDocumento() {
		return nomeTipoDocumento;
	}

	
	public void setNomeTipoDocumento(String nomeTipoDocumento) {
		this.nomeTipoDocumento = nomeTipoDocumento;
	}

	
	public String getIdDoc() {
		return idDoc;
	}

	
	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}

	
	public String getStatoDocumento() {
		return statoDocumento;
	}

	
	public void setStatoDocumento(String statoDocumento) {
		this.statoDocumento = statoDocumento;
	}

	
	public Date getDataMessaADisp() {
		return dataMessaADisp;
	}

	
	public void setDataMessaADisp(Date dataMessaADisp) {
		this.dataMessaADisp = dataMessaADisp;
	}

	
	public String getIdLotto() {
		return idLotto;
	}

	
	public void setIdLotto(String idLotto) {
		this.idLotto = idLotto;
	}

	
	public String getUriFile() {
		return uriFile;
	}


	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}


	public String getIdDocOriginale() {
		return idDocOriginale;
	}

	
	public void setIdDocOriginale(String idDocOriginale) {
		this.idDocOriginale = idDocOriginale;
	}

	
	public String getNomeClasseDocumentale() {
		return nomeClasseDocumentale;
	}

	
	public void setNomeClasseDocumentale(String nomeClasseDocumentale) {
		this.nomeClasseDocumentale = nomeClasseDocumentale;
	}

	
	public String getNomeSottoclasseDocumentale() {
		return nomeSottoclasseDocumentale;
	}

	
	public void setNomeSottoclasseDocumentale(String nomeSottoclasseDocumentale) {
		this.nomeSottoclasseDocumentale = nomeSottoclasseDocumentale;
	}

	
	public String getIdUltimoPdV() {
		return idUltimoPdV;
	}

	
	public void setIdUltimoPdV(String idUltimoPdV) {
		this.idUltimoPdV = idUltimoPdV;
	}

	
	public Date getDataInvio() {
		return dataInvio;
	}

	
	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	
	public Date getDataAccettazione() {
		return dataAccettazione;
	}

	
	public void setDataAccettazione(Date dataAccettazione) {
		this.dataAccettazione = dataAccettazione;
	}

	
	public Date getDataInizioConservazione() {
		return dataInizioConservazione;
	}

	
	public void setDataInizioConservazione(Date dataInizioConservazione) {
		this.dataInizioConservazione = dataInizioConservazione;
	}

	
	public String getIdDocSdC() {
		return idDocSdC;
	}

	
	public void setIdDocSdC(String idDocSdC) {
		this.idDocSdC = idDocSdC;
	}

	
	public Date getDataTermineConservazione() {
		return dataTermineConservazione;
	}

	
	public void setDataTermineConservazione(Date dataTermineConservazione) {
		this.dataTermineConservazione = dataTermineConservazione;
	}

	
	public Date getDataScarto() {
		return dataScarto;
	}

	
	public void setDataScarto(Date dataScarto) {
		this.dataScarto = dataScarto;
	}

	
	public Boolean getFlgProlungamentoTermini() {
		return flgProlungamentoTermini;
	}

	
	public void setFlgProlungamentoTermini(Boolean flgProlungamentoTermini) {
		this.flgProlungamentoTermini = flgProlungamentoTermini;
	}

	
	public Boolean getFlgRichEsib() {
		return flgRichEsib;
	}

	
	public void setFlgRichEsib(Boolean flgRichEsib) {
		this.flgRichEsib = flgRichEsib;
	}


	public BigDecimal getIdUd() {
		return idUd;
	}


	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}
	
}
