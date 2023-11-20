/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class FileDaFirmareBean extends IdFileBean {
	
	private Boolean firmaEseguita;
	private String uriVerPreFirma;
	private String nomeFileVerPreFirma;
	private MimeTypeFirmaBean infoFileVerPreFirma;

	// Tengo l'uri dell'odt originale del file generato da modello e un flag per sapere se si tratta del dispositivo di NuovaPropostaAtto2Completa
	private String uriFileOdtGenerato;
	private Boolean isDispositivoNuovaPropostaAtto2Completa = false;
	
	// numero progressivo dell'eventuale allegato
	private Integer nroProgAllegato;
	
	private File file;
	
	private String tipoFileUnione;	
	private String nroPagDispositivoUnione;

	// tipo firma da effettuare, passata dai moduli di firma per aggiornare il mimetype dopo la firma senza invocare fileop
	private String tipoFirmaDaEffettuare;
	// flag di file marcato, passata dai moduli di firma per verificare se possibile aggiornare il mimetype dopo la firma senza invocare fileop
	private String marcaDaApporre;
	// json mappa attributi di firma, passata dai moduli di firma per aggiornare il mimetype dopo la firma senza invocare fileop
	private String jsonMappaAttributiFirma;
	// flag che indica se si sta firmando il file principale di un atto
	private Boolean isFilePrincipaleAtto;
	
	public Boolean getFirmaEseguita() {
		return firmaEseguita;
	}	
	public void setFirmaEseguita(Boolean firmaEseguita) {
		this.firmaEseguita = firmaEseguita;
	}	
	public String getUriVerPreFirma() {
		return uriVerPreFirma;
	}
	public void setUriVerPreFirma(String uriVerPreFirma) {
		this.uriVerPreFirma = uriVerPreFirma;
	}
	public String getNomeFileVerPreFirma() {
		return nomeFileVerPreFirma;
	}
	public void setNomeFileVerPreFirma(String nomeFileVerPreFirma) {
		this.nomeFileVerPreFirma = nomeFileVerPreFirma;
	}
	public MimeTypeFirmaBean getInfoFileVerPreFirma() {
		return infoFileVerPreFirma;
	}
	public void setInfoFileVerPreFirma(MimeTypeFirmaBean infoFileVerPreFirma) {
		this.infoFileVerPreFirma = infoFileVerPreFirma;
	}
	public String getUriFileOdtGenerato() {
		return uriFileOdtGenerato;
	}
	public void setUriFileOdtGenerato(String uriFileOdtGenerato) {
		this.uriFileOdtGenerato = uriFileOdtGenerato;
	}
	public Boolean getIsDispositivoNuovaPropostaAtto2Completa() {
		return isDispositivoNuovaPropostaAtto2Completa;
	}
	public void setIsDispositivoNuovaPropostaAtto2Completa(Boolean isDispositivoNuovaPropostaAtto2Completa) {
		this.isDispositivoNuovaPropostaAtto2Completa = isDispositivoNuovaPropostaAtto2Completa;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public Integer getNroProgAllegato() {
		return nroProgAllegato;
	}
	public void setNroProgAllegato(Integer nroProgAllegato) {
		this.nroProgAllegato = nroProgAllegato;
	}
	public String getNroPagDispositivoUnione() {
		return nroPagDispositivoUnione;
	}
	public void setNroPagDispositivoUnione(String nroPagDispositivoUnione) {
		this.nroPagDispositivoUnione = nroPagDispositivoUnione;
	}
	public String getTipoFileUnione() {
		return tipoFileUnione;
	}
	public void setTipoFileUnione(String tipoFileUnione) {
		this.tipoFileUnione = tipoFileUnione;
	}
	public String getTipoFirmaDaEffettuare() {
		return tipoFirmaDaEffettuare;
	}
	public void setTipoFirmaDaEffettuare(String tipoFirmaDaEffettuare) {
		this.tipoFirmaDaEffettuare = tipoFirmaDaEffettuare;
	}
	public String getMarcaDaApporre() {
		return marcaDaApporre;
	}
	public void setMarcaDaApporre(String marcaDaApporre) {
		this.marcaDaApporre = marcaDaApporre;
	}
	public String getJsonMappaAttributiFirma() {
		return jsonMappaAttributiFirma;
	}
	public void setJsonMappaAttributiFirma(String jsonMappaAttributiFirma) {
		this.jsonMappaAttributiFirma = jsonMappaAttributiFirma;
	}
	public Boolean getIsFilePrincipaleAtto() {
		return isFilePrincipaleAtto;
	}
	public void setIsFilePrincipaleAtto(Boolean isFilePrincipaleAtto) {
		this.isFilePrincipaleAtto = isFilePrincipaleAtto;
	}
}
