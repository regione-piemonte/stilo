/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

/**
 * Rappresentazione java dei dati estratti dalla store sottoforma di xml
 * @author ottavio passalacqua
 *
 */
public class DocumentoBiAutoXmlBean implements Serializable {

 
	//private static final long serialVersionUID = 1L;

	@NumeroColonna(numero = "1")
	private String flgUdFolder;
	
	@NumeroColonna(numero = "2")
	private BigDecimal idUd;
	
	@NumeroColonna(numero = "3")
	private String nomeUd;

	@NumeroColonna(numero = "14")
	private String siglaEnumeroDocumento;

	@NumeroColonna(numero = "15")
    @TipoData(tipo=Tipo.DATA)
	private Date dataNumerazione;
	
	@NumeroColonna(numero = "18")
	private String oggetto;

	@NumeroColonna(numero = "20")
	private String flgTipoProv;
		
	@NumeroColonna(numero = "22")
	private String nomeFilePrimario;

	@NumeroColonna(numero = "31")
	private String tipoDocumento;
	
	@NumeroColonna(numero = "32")
	private String descTipoDocumento;
	
	
	@NumeroColonna(numero = "33")
	private BigDecimal idDoc;
	
	@NumeroColonna(numero = "41")
	private String azioniPossibili;

	@NumeroColonna(numero = "53")
	private String descStato;
	
	@NumeroColonna(numero = "54")
	private String descStatoDett;
	
	@NumeroColonna(numero = "88")
	private String nota;
	
	@NumeroColonna(numero = "203")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataDocumento;
	
	@NumeroColonna(numero = "218")
	private String societa;
	
	@NumeroColonna(numero = "230")
	private String uriFilePrimario;
	
	@NumeroColonna(numero = "262")
	private String codApplOwner;
	
	private String siglaDocumento;
	private String numeroDocumento;
	
	// Attributi custom
	@NumeroColonna(numero = "101")
	private String numeroContratto;

	@NumeroColonna(numero = "102")
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataContratto;
	
	@NumeroColonna(numero = "103")
	private String cfPivaSoggetto;

	@NumeroColonna(numero = "104")
	private String ragioneSocialeSoggetto;

	@NumeroColonna(numero = "105")
	private String responsabileVendita;

	@NumeroColonna(numero = "106")
	private String tipoContratto;

	@NumeroColonna(numero = "107")
	private String tipoPagamento;

	@NumeroColonna(numero = "108")
	private String tipoServizioValori;

	@NumeroColonna(numero = "109")
	private String flgPermuta;

	@NumeroColonna(numero = "110")
	private String tipoBrand;

	@NumeroColonna(numero = "111")
	private String targaVeicolo;

	@NumeroColonna(numero = "112")
	private String telaioVeicolo;

	@NumeroColonna(numero = "113")
	private String pivaAzienda;
	
	@NumeroColonna(numero = "114")
	private String numeroOrdine;
	

	private List<String> tipoServizio;
	
	private String descTipoContratto;
	private String descTipoPagamento;
	private String descTipoServizio;
	private String descTipoBrand;
	
	private String abilViewFilePrimario;
	private Boolean flgAbilModifica;
	
	private String codStato;
	private String codStatoDett;
	
	public String getFlgUdFolder() {
		return flgUdFolder;
	}

	public void setFlgUdFolder(String flgUdFolder) {
		this.flgUdFolder = flgUdFolder;
	}

	public BigDecimal getIdUd() {
		return idUd;
	}

	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getAzioniPossibili() {
		return azioniPossibili;
	}

	public void setAzioniPossibili(String azioniPossibili) {
		this.azioniPossibili = azioniPossibili;
	}

	public String getUriFilePrimario() {
		return uriFilePrimario;
	}

	public void setUriFilePrimario(String uriFilePrimario) {
		this.uriFilePrimario = uriFilePrimario;
	}

	public String getAbilViewFilePrimario() {
		return abilViewFilePrimario;
	}

	public void setAbilViewFilePrimario(String abilViewFilePrimario) {
		this.abilViewFilePrimario = abilViewFilePrimario;
	}

	public String getSocieta() {
		return societa;
	}

	public void setSocieta(String societa) {
		this.societa = societa;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getSiglaDocumento() {
		return siglaDocumento;
	}

	public void setSiglaDocumento(String siglaDocumento) {
		this.siglaDocumento = siglaDocumento;
	}

	public String getNomeUd() {
		return nomeUd;
	}

	public void setNomeUd(String nomeUd) {
		this.nomeUd = nomeUd;
	}

	public String getSiglaEnumeroDocumento() {
		return siglaEnumeroDocumento;
	}

	public void setSiglaEnumeroDocumento(String siglaEnumeroDocumento) {
		this.siglaEnumeroDocumento = siglaEnumeroDocumento;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Date getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public String getNumeroContratto() {
		return numeroContratto;
	}

	public void setNumeroContratto(String numeroContratto) {
		this.numeroContratto = numeroContratto;
	}

	public Date getDataContratto() {
		return dataContratto;
	}

	public void setDataContratto(Date dataContratto) {
		this.dataContratto = dataContratto;
	}

	public String getCfPivaSoggetto() {
		return cfPivaSoggetto;
	}

	public void setCfPivaSoggetto(String cfPivaSoggetto) {
		this.cfPivaSoggetto = cfPivaSoggetto;
	}

	public String getRagioneSocialeSoggetto() {
		return ragioneSocialeSoggetto;
	}

	public void setRagioneSocialeSoggetto(String ragioneSocialeSoggetto) {
		this.ragioneSocialeSoggetto = ragioneSocialeSoggetto;
	}

	public String getResponsabileVendita() {
		return responsabileVendita;
	}

	public void setResponsabileVendita(String responsabileVendita) {
		this.responsabileVendita = responsabileVendita;
	}

	public String getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}
	

	public String getTargaVeicolo() {
		return targaVeicolo;
	}

	public void setTargaVeicolo(String targaVeicolo) {
		this.targaVeicolo = targaVeicolo;
	}

	public String getTelaioVeicolo() {
		return telaioVeicolo;
	}

	public void setTelaioVeicolo(String telaioVeicolo) {
		this.telaioVeicolo = telaioVeicolo;
	}

	public String getTipoContratto() {
		return tipoContratto;
	}

	public void setTipoContratto(String tipoContratto) {
		this.tipoContratto = tipoContratto;
	}

	

	public String getPivaAzienda() {
		return pivaAzienda;
	}

	public void setPivaAzienda(String pivaAzienda) {
		this.pivaAzienda = pivaAzienda;
	}

	public String getDescTipoDocumento() {
		return descTipoDocumento;
	}

	public void setDescTipoDocumento(String descTipoDocumento) {
		this.descTipoDocumento = descTipoDocumento;
	}

	public String getNomeFilePrimario() {
		return nomeFilePrimario;
	}

	public void setNomeFilePrimario(String nomeFilePrimario) {
		this.nomeFilePrimario = nomeFilePrimario;
	}

	public String getDescTipoContratto() {
		return descTipoContratto;
	}

	public void setDescTipoContratto(String descTipoContratto) {
		this.descTipoContratto = descTipoContratto;
	}

	public String getDescTipoPagamento() {
		return descTipoPagamento;
	}

	public void setDescTipoPagamento(String descTipoPagamento) {
		this.descTipoPagamento = descTipoPagamento;
	}

	public String getDescTipoServizio() {
		return descTipoServizio;
	}

	public void setDescTipoServizio(String descTipoServizio) {
		this.descTipoServizio = descTipoServizio;
	}

	public BigDecimal getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(BigDecimal idDoc) {
		this.idDoc = idDoc;
	}

	public String getFlgPermuta() {
		return flgPermuta;
	}

	public void setFlgPermuta(String flgPermuta) {
		this.flgPermuta = flgPermuta;
	}

	public String getCodApplOwner() {
		return codApplOwner;
	}

	public void setCodApplOwner(String codApplOwner) {
		this.codApplOwner = codApplOwner;
	}

	public String getFlgTipoProv() {
		return flgTipoProv;
	}

	public void setFlgTipoProv(String flgTipoProv) {
		this.flgTipoProv = flgTipoProv;
	}

	public List<String> getTipoServizio() {
		return tipoServizio;
	}

	public void setTipoServizio(List<String> tipoServizio) {
		this.tipoServizio = tipoServizio;
	}

	public String getTipoServizioValori() {
		return tipoServizioValori;
	}

	public void setTipoServizioValori(String tipoServizioValori) {
		this.tipoServizioValori = tipoServizioValori;
	}

	public Date getDataNumerazione() {
		return dataNumerazione;
	}

	public void setDataNumerazione(Date dataNumerazione) {
		this.dataNumerazione = dataNumerazione;
	}

	public String getNumeroOrdine() {
		return numeroOrdine;
	}

	public void setNumeroOrdine(String numeroOrdine) {
		this.numeroOrdine = numeroOrdine;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getTipoBrand() {
		return tipoBrand;
	}

	public void setTipoBrand(String tipoBrand) {
		this.tipoBrand = tipoBrand;
	}

	public String getDescTipoBrand() {
		return descTipoBrand;
	}

	public void setDescTipoBrand(String descTipoBrand) {
		this.descTipoBrand = descTipoBrand;
	}

	public Boolean getFlgAbilModifica() {
		return flgAbilModifica;
	}

	public void setFlgAbilModifica(Boolean flgAbilModifica) {
		this.flgAbilModifica = flgAbilModifica;
	}

	public String getDescStato() {
		return descStato;
	}

	public void setDescStato(String descStato) {
		this.descStato = descStato;
	}

	public String getDescStatoDett() {
		return descStatoDett;
	}

	public void setDescStatoDett(String descStatoDett) {
		this.descStatoDett = descStatoDett;
	}

	public String getCodStato() {
		return codStato;
	}

	public void setCodStato(String codStato) {
		this.codStato = codStato;
	}

	public String getCodStatoDett() {
		return codStatoDett;
	}

	public void setCodStatoDett(String codStatoDett) {
		this.codStatoDett = codStatoDett;
	}

}