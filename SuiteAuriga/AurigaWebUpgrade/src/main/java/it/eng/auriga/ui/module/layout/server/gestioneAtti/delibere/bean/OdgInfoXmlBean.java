/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

/**
 * 
 * @author DANCRIST
 *
 */

public class OdgInfoXmlBean {
	
	@XmlVariabile(nome="IdDoc", tipo=TipoVariabile.SEMPLICE)
	private String idDoc;	
	
	@XmlVariabile(nome="IdFolder", tipo=TipoVariabile.SEMPLICE)
	private String idFascicolo;
	
	@XmlVariabile(nome="URI", tipo=TipoVariabile.SEMPLICE)
	private String uri;
	
	@XmlVariabile(nome="Mimetype", tipo=TipoVariabile.SEMPLICE)
	private String mimetype;
	
	@XmlVariabile(nome="FlgPdfizzabile", tipo=TipoVariabile.SEMPLICE)
	private Boolean flgPdfizzabile = true;
	
	@XmlVariabile(nome="Dimensione", tipo=TipoVariabile.SEMPLICE)
	private Long dimensione;
	
	@XmlVariabile(nome="Impronta", tipo=TipoVariabile.SEMPLICE)
	private String impronta;
	
	@XmlVariabile(nome="EncodingImpronta", tipo=TipoVariabile.SEMPLICE)
	private String encoding;
	
	@XmlVariabile(nome="AlgoritmoImpronta", tipo=TipoVariabile.SEMPLICE)
	private String algoritmo;
	
	@XmlVariabile(nome="FlgFirmato", tipo=TipoVariabile.SEMPLICE)
	private Boolean flgFirmato;
	
	@XmlVariabile(nome="TipoFirma", tipo=TipoVariabile.SEMPLICE)
	private String tipoFirma;
	
	@XmlVariabile(nome="@Firmatari", tipo=TipoVariabile.LISTA)
	private List<FirmatarioXmlBean> listaFirmatari;
	
	@XmlVariabile(nome="FlgDaFirmare", tipo=TipoVariabile.SEMPLICE)
	private Boolean flgDaFirmare;
	
	@XmlVariabile(nome="DisplayFilename", tipo=TipoVariabile.SEMPLICE)
	private String displayFilename;
	
	@XmlVariabile(nome="NomeModello", tipo=TipoVariabile.SEMPLICE)
	private String nomeModello;
	
	@XmlVariabile(nome="IdModello", tipo=TipoVariabile.SEMPLICE)
	private String idModello;
	
	@XmlVariabile(nome="URIModello", tipo=TipoVariabile.SEMPLICE)
	private String uriModello;
	
	@XmlVariabile(nome="TipoModello", tipo=TipoVariabile.SEMPLICE)
	private String tipoModello;
	
	@XmlVariabile(nome="CodCircoscrizione", tipo=TipoVariabile.SEMPLICE)
	private String codCircoscrizione;
	
	/**
	 *  valori:  consolidamento - chiusura - invio_email 
	 */
	@XmlVariabile(nome="AzioneDaFare", tipo=TipoVariabile.SEMPLICE)
	private String azioneDaFare;
	
	@XmlVariabile(nome="TipoOdGConsolidato", tipo=TipoVariabile.SEMPLICE)
	private String tipoOdGConsolidato;
	
	@XmlVariabile(nome="InvioOdg_EmailTo", tipo=TipoVariabile.SEMPLICE)
	private String invioOdgEmailTo;
	
	@XmlVariabile(nome="InvioOdg_EmailCC", tipo=TipoVariabile.SEMPLICE)
	private String invioOdgEmailCC;
	
	@XmlVariabile(nome="Oggetto_Email", tipo=TipoVariabile.SEMPLICE)
	private String oggettoEmail;

	@XmlVariabile(nome="IdEmail", tipo=TipoVariabile.SEMPLICE)
	private String idEmail;	
	
	@XmlVariabile(nome="@OdGConsolidati", tipo=TipoVariabile.LISTA)
	private List<OdGConsolidatiXmlBean> listaOdGConsolidati;
	
	@XmlVariabile(nome="FlgRichPubblicazione", tipo=TipoVariabile.SEMPLICE)
	private Boolean flgRichPubblicazione;
	
	@XmlVariabile(nome="AbilitazioneAnnullamentoSeduta", tipo=TipoVariabile.SEMPLICE)
	private Boolean flgAbilAnnullaSeduta;
	
	@XmlVariabile(nome="AbilitazioneArchSedutaInStorico", tipo=TipoVariabile.SEMPLICE)
	private Boolean flgAbilArchiviaSeduta;
	
	@XmlVariabile(nome="ShowVistoSegretario", tipo=TipoVariabile.SEMPLICE)
	private Boolean flgShowVistoSegretario;
			
	@XmlVariabile(nome="@Commissioni", tipo=TipoVariabile.LISTA)
	private List<CommissioniXmlBean> listaCommissioniConvocate;
		
	@XmlVariabile(nome="ModalitaOdG", tipo=TipoVariabile.SEMPLICE)
	private String modalitaOdG;	
	
	@XmlVariabile(nome="DisattivatoRiordinoAutomatico", tipo=TipoVariabile.SEMPLICE)
	private Boolean disattivatoRiordinoAutomatico;
		
	/**
	 *  INFORMAZIONI PER INVIO EMAIL 
	 */
	private String destinatari;

	public String getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}
	
	public String getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(String idFascicolo) {
		this.idFascicolo = idFascicolo;
	}	

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public Boolean getFlgPdfizzabile() {
		return flgPdfizzabile;
	}

	public void setFlgPdfizzabile(Boolean flgPdfizzabile) {
		this.flgPdfizzabile = flgPdfizzabile;
	}

	public Long getDimensione() {
		return dimensione;
	}

	public void setDimensione(Long dimensione) {
		this.dimensione = dimensione;
	}

	public String getImpronta() {
		return impronta;
	}

	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}

	public Boolean getFlgFirmato() {
		return flgFirmato;
	}

	public void setFlgFirmato(Boolean flgFirmato) {
		this.flgFirmato = flgFirmato;
	}

	public String getTipoFirma() {
		return tipoFirma;
	}

	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

	public List<FirmatarioXmlBean> getListaFirmatari() {
		return listaFirmatari;
	}

	public void setListaFirmatari(List<FirmatarioXmlBean> listaFirmatari) {
		this.listaFirmatari = listaFirmatari;
	}

	public Boolean getFlgDaFirmare() {
		return flgDaFirmare;
	}

	public void setFlgDaFirmare(Boolean flgDaFirmare) {
		this.flgDaFirmare = flgDaFirmare;
	}

	public String getDisplayFilename() {
		return displayFilename;
	}

	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}

	public String getNomeModello() {
		return nomeModello;
	}

	public void setNomeModello(String nomeModello) {
		this.nomeModello = nomeModello;
	}

	public String getIdModello() {
		return idModello;
	}

	public void setIdModello(String idModello) {
		this.idModello = idModello;
	}

	public String getUriModello() {
		return uriModello;
	}

	public void setUriModello(String uriModello) {
		this.uriModello = uriModello;
	}

	public String getTipoModello() {
		return tipoModello;
	}

	public void setTipoModello(String tipoModello) {
		this.tipoModello = tipoModello;
	}

	public String getCodCircoscrizione() {
		return codCircoscrizione;
	}

	public void setCodCircoscrizione(String codCircoscrizione) {
		this.codCircoscrizione = codCircoscrizione;
	}

	public String getAzioneDaFare() {
		return azioneDaFare;
	}

	public void setAzioneDaFare(String azioneDaFare) {
		this.azioneDaFare = azioneDaFare;
	}

	public String getTipoOdGConsolidato() {
		return tipoOdGConsolidato;
	}

	public void setTipoOdGConsolidato(String tipoOdGConsolidato) {
		this.tipoOdGConsolidato = tipoOdGConsolidato;
	}

	public String getInvioOdgEmailTo() {
		return invioOdgEmailTo;
	}

	public void setInvioOdgEmailTo(String invioOdgEmailTo) {
		this.invioOdgEmailTo = invioOdgEmailTo;
	}

	public String getInvioOdgEmailCC() {
		return invioOdgEmailCC;
	}

	public void setInvioOdgEmailCC(String invioOdgEmailCC) {
		this.invioOdgEmailCC = invioOdgEmailCC;
	}

	public String getOggettoEmail() {
		return oggettoEmail;
	}

	public void setOggettoEmail(String oggettoEmail) {
		this.oggettoEmail = oggettoEmail;
	}

	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public List<OdGConsolidatiXmlBean> getListaOdGConsolidati() {
		return listaOdGConsolidati;
	}

	public void setListaOdGConsolidati(List<OdGConsolidatiXmlBean> listaOdGConsolidati) {
		this.listaOdGConsolidati = listaOdGConsolidati;
	}

	public Boolean getFlgRichPubblicazione() {
		return flgRichPubblicazione;
	}

	public void setFlgRichPubblicazione(Boolean flgRichPubblicazione) {
		this.flgRichPubblicazione = flgRichPubblicazione;
	}
	
	public Boolean getFlgAbilAnnullaSeduta() {
		return flgAbilAnnullaSeduta;
	}

	public void setFlgAbilAnnullaSeduta(Boolean flgAbilAnnullaSeduta) {
		this.flgAbilAnnullaSeduta = flgAbilAnnullaSeduta;
	}

	public Boolean getFlgAbilArchiviaSeduta() {
		return flgAbilArchiviaSeduta;
	}

	public void setFlgAbilArchiviaSeduta(Boolean flgAbilArchiviaSeduta) {
		this.flgAbilArchiviaSeduta = flgAbilArchiviaSeduta;
	}
	
	public Boolean getFlgShowVistoSegretario() {
		return flgShowVistoSegretario;
	}

	public void setFlgShowVistoSegretario(Boolean flgShowVistoSegretario) {
		this.flgShowVistoSegretario = flgShowVistoSegretario;
	}

	public String getDestinatari() {
		return destinatari;
	}

	public void setDestinatari(String destinatari) {
		this.destinatari = destinatari;
	}

	public List<CommissioniXmlBean> getListaCommissioniConvocate() {
		return listaCommissioniConvocate;
	}

	public void setListaCommissioniConvocate(List<CommissioniXmlBean> listaCommissioniConvocate) {
		this.listaCommissioniConvocate = listaCommissioniConvocate;
	}

	public String getModalitaOdG() {
		return modalitaOdG;
	}

	public void setModalitaOdG(String modalitaOdG) {
		this.modalitaOdG = modalitaOdG;
	}

	public Boolean getDisattivatoRiordinoAutomatico() {
		return disattivatoRiordinoAutomatico;
	}

	public void setDisattivatoRiordinoAutomatico(Boolean disattivatoRiordinoAutomatico) {
		this.disattivatoRiordinoAutomatico = disattivatoRiordinoAutomatico;
	}

}