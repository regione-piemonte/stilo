/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;
import it.eng.document.NumeroColonna;

/**
 * 
 * @author Cristiano Daniele
 *
 */

@XmlRootElement
public class ItemLavorazioneMailXmlBean extends AbstractBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3914608492768874753L;

	/**
	 * (obblig.) Tipo di item. Valori possibili: F = File, AT = Commento e/o tag
	 */
	@NumeroColonna(numero = "1")
	private String sceltaTipo;

	/**
	 * Nro progressivo dell'item (da lasciare vuoto se è un nuovo item)
	 */
	@NumeroColonna(numero = "2")
	private String numProgressivo;

	/**
	 * Commento
	 */
	@NumeroColonna(numero = "3")
	private String nota;

	/**
	 * Tag
	 */
	@NumeroColonna(numero = "4")
	private String tag;

	/**
	 * URI del file
	 */
	@NumeroColonna(numero = "5")
	private String uriFile;

	/**
	 * Nome del file
	 */
	@NumeroColonna(numero = "6")
	private String nomeFile;

	/**
	 * Dimensione del file (in bytes)
	 */
	@NumeroColonna(numero = "7")
	private String dimensioneFile;

	/**
	 * Mimetype del file
	 */
	@NumeroColonna(numero = "8")
	private String mimeType;

	/**
	 * (valori 1/0) Se 1 indica se file firmato digitalmente
	 */
	@NumeroColonna(numero = "9")
	private Integer flgFileFirmato;

	/**
	 * Impronta del file
	 */
	@NumeroColonna(numero = "10")
	private String impronta;

	/**
	 * Algoritmo di calcolo dell'impronta del file
	 */
	@NumeroColonna(numero = "11")
	private String agoritmoImpronta;

	/**
	 * Encoding dell'impronta del file (hex o base64)
	 */
	@NumeroColonna(numero = "12")
	private String algoritmoEncoding;

	/**
	 * (valori 1/0) Se 1 indica se è pdf o convertibile in pdf
	 */

	@NumeroColonna(numero = "13")
	private Integer flgConvertibilePdf;

	/**
	 * Data e ora di inserimento
	 */

	@NumeroColonna(numero = "14")
	private Date dataOraCaricamento;

	/**
	 * Utente di inserimento
	 */

	@NumeroColonna(numero = "15")
	private String caricatoDa;

	/**
	 * Data e ora di ultima modifica
	 */

	@NumeroColonna(numero = "16")
	private Date dataOraModifica;

	/**
	 * Utente di ultima modifica
	 */

	@NumeroColonna(numero = "17")
	private String modificatoDa;

	/**
	 * (valori 1/0) Flag di item non modificabile/cancellabile perchè "locked" da qualcuno (da usare per il check "inibisci modifica")
	 */

	@NumeroColonna(numero = "18")
	private Integer flgNonModCancLocked;

	/**
	 * (valori 1/0) Se 1 indica che l'item non è modificabile/cancellabile
	 */
	@NumeroColonna(numero = "19")
	private Integer flgNonModCanc;

	/**
	 * Motivo per cui l'item non è modificabile cancellabile (con dettaglio dell'utente che lo ha riservato o dell''invio per approvazione che lo ha reso
	 * immodificabile)
	 */

	@NumeroColonna(numero = "20")
	private String motivoNonModCanc;

	/**
	 * (valori 1/0) Se 1 significa che per il tag presente è obbligatorio inserire un commento
	 */

	@NumeroColonna(numero = "21")
	private Integer flgCommentoObbligatorioTag;

	/**
	 * Uri file originale salvato in archivio. Mi serve per poter cancellare il file originale nel caso di aggiornamento
	 */
	private String uriFileSaved;

	/**
	 * Posizione del file nella lista di attach, vedi {@link InvioMailXmlBean#listaItemLavorazione}
	 */
	private Integer posFileInLista;

	/**
	 * @return the sceltaTipo
	 */
	public String getSceltaTipo() {
		return sceltaTipo;
	}

	/**
	 * @param sceltaTipo
	 *            the sceltaTipo to set
	 */
	public void setSceltaTipo(String sceltaTipo) {
		this.sceltaTipo = sceltaTipo;
	}

	/**
	 * @return the numProgressivo
	 */
	public String getNumProgressivo() {
		return numProgressivo;
	}

	/**
	 * @param numProgressivo
	 *            the numProgressivo to set
	 */
	public void setNumProgressivo(String numProgressivo) {
		this.numProgressivo = numProgressivo;
	}

	/**
	 * @return the nota
	 */
	public String getNota() {
		return nota;
	}

	/**
	 * @param nota
	 *            the nota to set
	 */
	public void setNota(String nota) {
		this.nota = nota;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag
	 *            the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @return the uriFile
	 */
	public String getUriFile() {
		return uriFile;
	}

	/**
	 * @param uriFile
	 *            the uriFile to set
	 */
	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}

	/**
	 * @return the nomeFile
	 */
	public String getNomeFile() {
		return nomeFile;
	}

	/**
	 * @param nomeFile
	 *            the nomeFile to set
	 */
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	/**
	 * @return the dimensioneFile
	 */
	public String getDimensioneFile() {
		return dimensioneFile;
	}

	/**
	 * @param dimensioneFile
	 *            the dimensioneFile to set
	 */
	public void setDimensioneFile(String dimensioneFile) {
		this.dimensioneFile = dimensioneFile;
	}

	/**
	 * @return the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * @param mimeType
	 *            the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * @return the flgFileFirmato
	 */
	public Integer getFlgFileFirmato() {
		return flgFileFirmato;
	}

	/**
	 * @param flgFileFirmato
	 *            the flgFileFirmato to set
	 */
	public void setFlgFileFirmato(Integer flgFileFirmato) {
		this.flgFileFirmato = flgFileFirmato;
	}

	/**
	 * @return the impronta
	 */
	public String getImpronta() {
		return impronta;
	}

	/**
	 * @param impronta
	 *            the impronta to set
	 */
	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}

	/**
	 * @return the agoritmoImpronta
	 */
	public String getAgoritmoImpronta() {
		return agoritmoImpronta;
	}

	/**
	 * @param agoritmoImpronta
	 *            the agoritmoImpronta to set
	 */
	public void setAgoritmoImpronta(String agoritmoImpronta) {
		this.agoritmoImpronta = agoritmoImpronta;
	}

	/**
	 * @return the algoritmoEncoding
	 */
	public String getAlgoritmoEncoding() {
		return algoritmoEncoding;
	}

	/**
	 * @param algoritmoEncoding
	 *            the algoritmoEncoding to set
	 */
	public void setAlgoritmoEncoding(String algoritmoEncoding) {
		this.algoritmoEncoding = algoritmoEncoding;
	}

	/**
	 * @return the flgConvertibilePdf
	 */
	public Integer getFlgConvertibilePdf() {
		return flgConvertibilePdf;
	}

	/**
	 * @param flgConvertibilePdf
	 *            the flgConvertibilePdf to set
	 */
	public void setFlgConvertibilePdf(Integer flgConvertibilePdf) {
		this.flgConvertibilePdf = flgConvertibilePdf;
	}

	/**
	 * @return the caricatoDa
	 */
	public String getCaricatoDa() {
		return caricatoDa;
	}

	/**
	 * @param caricatoDa
	 *            the caricatoDa to set
	 */
	public void setCaricatoDa(String caricatoDa) {
		this.caricatoDa = caricatoDa;
	}

	/**
	 * @return the dataOraCaricamento
	 */
	public Date getDataOraCaricamento() {
		return dataOraCaricamento;
	}

	/**
	 * @param dataOraCaricamento
	 *            the dataOraCaricamento to set
	 */
	public void setDataOraCaricamento(Date dataOraCaricamento) {
		this.dataOraCaricamento = dataOraCaricamento;
	}

	/**
	 * @return the modificatoDa
	 */
	public String getModificatoDa() {
		return modificatoDa;
	}

	/**
	 * @param modificatoDa
	 *            the modificatoDa to set
	 */
	public void setModificatoDa(String modificatoDa) {
		this.modificatoDa = modificatoDa;
	}

	/**
	 * @return the dataOraModifica
	 */
	public Date getDataOraModifica() {
		return dataOraModifica;
	}

	/**
	 * @param dataOraModifica
	 *            the dataOraModifica to set
	 */
	public void setDataOraModifica(Date dataOraModifica) {
		this.dataOraModifica = dataOraModifica;
	}

	public String getUriFileSaved() {
		return uriFileSaved;
	}

	public void setUriFileSaved(String uriFileSaved) {
		this.uriFileSaved = uriFileSaved;
	}

	public Integer getPosFileInLista() {
		return posFileInLista;
	}

	public void setPosFileInLista(Integer posFileInLista) {
		this.posFileInLista = posFileInLista;
	}

	public Integer getFlgNonModCancLocked() {
		return flgNonModCancLocked;
	}

	public void setFlgNonModCancLocked(Integer flgNonModCancLocked) {
		this.flgNonModCancLocked = flgNonModCancLocked;
	}

	public Integer getFlgNonModCanc() {
		return flgNonModCanc;
	}

	public void setFlgNonModCanc(Integer flgNonModCanc) {
		this.flgNonModCanc = flgNonModCanc;
	}

	public String getMotivoNonModCanc() {
		return motivoNonModCanc;
	}

	public void setMotivoNonModCanc(String motivoNonModCanc) {
		this.motivoNonModCanc = motivoNonModCanc;
	}

	public Integer getFlgCommentoObbligatorioTag() {
		return flgCommentoObbligatorioTag;
	}

	public void setFlgCommentoObbligatorioTag(Integer flgCommentoObbligatorioTag) {
		this.flgCommentoObbligatorioTag = flgCommentoObbligatorioTag;
	}

}
