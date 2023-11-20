/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class RigaItemLavorazioneEmail {

	@NumeroColonna(numero = "1")
	private String itemLavTipo;

	@NumeroColonna(numero = "2")
	private String itemLavNrItem;

	@NumeroColonna(numero = "3")
	private String itemLavCommento;

	@NumeroColonna(numero = "4")
	private String itemLavTag;

	@NumeroColonna(numero = "5")
	private String itemLavUriFile;

	@NumeroColonna(numero = "6")
	private String itemLavNomeFile;

	@NumeroColonna(numero = "7")
	private String itemLavDimensioneFile;

	@NumeroColonna(numero = "8")
	private String itemLavMimeTypeFile;

	@NumeroColonna(numero = "9")
	private String itemLavFlgFileFirmato;

	@NumeroColonna(numero = "10")
	private String itemLavImprontaFile;

	@NumeroColonna(numero = "11")
	private String itemLavAlgoritmoImprontaFile;

	@NumeroColonna(numero = "12")
	private String itemLavAlgoritmoEncodingImprontaFile;

	@NumeroColonna(numero = "13")
	private String itemLavFlgConvertibilePdf;

	@NumeroColonna(numero = "14")
	@TipoData(tipo = Tipo.DATA)
	private Date itemLavDataOraCaricamento;

	@NumeroColonna(numero = "15")
	private String itemLavCaricatoDa;

	@NumeroColonna(numero = "16")
	@TipoData(tipo = Tipo.DATA)
	private Date itemLavDataOraModifica;

	@NumeroColonna(numero = "17")
	private String itemLavModificatoDa;

	// (valori 1/0/NULL) Flag di item non modificabile/cancellabile perchè "locked" da qualcuno (da usare per il check "inibisci modifica")
	@NumeroColonna(numero = "18")
	private Boolean flgNonModCancLocked;

	// (valori 1/0) Se 1 indica che l'item non è modificabile/cancellabile
	@NumeroColonna(numero = "19")
	private Integer flgNonModCanc;

	// Motivo per cui l'item non è modificabile cancellabile (con dettaglio dell'utente che lo ha riservato o dell''invio per approvazione che lo ha reso
	// immodificabile)
	@NumeroColonna(numero = "20")
	private String motivoNonModCanc;

	// (valori 1/0) Se 1 significa che per il tag presente è obbligatorio inserire un commento
	@NumeroColonna(numero = "21")
	private Integer flgCommentoObbligatorioTag;

	private Integer itemLavNumProgressivo;

	// ************* FILE ******************
	private Boolean itemLavIsDocPrimarioChanged;
	private BigDecimal itemLavIdDocPrimario;
	private String itemLavIdAttachEmailPrimario;
	private Boolean itemLavRemoteUriFile;
	private Boolean itemLavFlgNoPubblPrimario;
	private String itemLavMimetypeVerPreFirma;
	private String itemLavUriFileVerPreFirma;
	private String itemLavNomeFileVerPreFirma;
	private String itemLavFlgConvertibilePdfVerPreFirma;

	// ************* DATI ITEM LAVORAZIONE ******************

	private String itemLavTipoNota;
	// private String itemLavInfoFile;
	// private String itemLavFileFirmato;

	/**
	 * Indica l'uri originale del file nell'item in lavorazione. Mi server per cancellare il file vecchio se lo modifico
	 */

	private String itemLavUriFileSaved;
	// Il file che ho uplodeto
	@ApiModelProperty(dataType = "string")
	private File itemLavFile;

	private MimeTypeFirmaBean itemLavInfoFile;

	public String getItemLavTipo() {
		return itemLavTipo;
	}

	public void setItemLavTipo(String itemLavTipo) {
		this.itemLavTipo = itemLavTipo;
	}

	public Integer getItemLavNumProgressivo() {
		return itemLavNumProgressivo;
	}

	public void setItemLavNumProgressivo(Integer itemLavNumProgressivo) {
		this.itemLavNumProgressivo = itemLavNumProgressivo;
	}

	public String getItemLavCommento() {
		return itemLavCommento;
	}

	public void setItemLavCommento(String itemLavCommento) {
		this.itemLavCommento = itemLavCommento;
	}

	public String getItemLavTag() {
		return itemLavTag;
	}

	public void setItemLavTag(String itemLavTag) {
		this.itemLavTag = itemLavTag;
	}

	public String getItemLavUriFile() {
		return itemLavUriFile;
	}

	public void setItemLavUriFile(String itemLavUriFile) {
		this.itemLavUriFile = itemLavUriFile;
	}

	public String getItemLavNomeFile() {
		return itemLavNomeFile;
	}

	public void setItemLavNomeFile(String itemLavNomeFile) {
		this.itemLavNomeFile = itemLavNomeFile;
	}

	public String getItemLavDimensioneFile() {
		return itemLavDimensioneFile;
	}

	public void setItemLavDimensioneFile(String itemLavDimensioneFile) {
		this.itemLavDimensioneFile = itemLavDimensioneFile;
	}

	public String getItemLavMimeTypeFile() {
		return itemLavMimeTypeFile;
	}

	public void setItemLavMimeTypeFile(String itemLavMimeTypeFile) {
		this.itemLavMimeTypeFile = itemLavMimeTypeFile;
	}

	public String getItemLavFlgFileFirmato() {
		return itemLavFlgFileFirmato;
	}

	public void setItemLavFlgFileFirmato(String itemLavFlgFileFirmato) {
		this.itemLavFlgFileFirmato = itemLavFlgFileFirmato;
	}

	public String getItemLavImprontaFile() {
		return itemLavImprontaFile;
	}

	public void setItemLavImprontaFile(String itemLavImprontaFile) {
		this.itemLavImprontaFile = itemLavImprontaFile;
	}

	public String getItemLavAlgoritmoImprontaFile() {
		return itemLavAlgoritmoImprontaFile;
	}

	public void setItemLavAlgoritmoImprontaFile(String itemLavAlgoritmoImprontaFile) {
		this.itemLavAlgoritmoImprontaFile = itemLavAlgoritmoImprontaFile;
	}

	public String getItemLavAlgoritmoEncodingImprontaFile() {
		return itemLavAlgoritmoEncodingImprontaFile;
	}

	public void setItemLavAlgoritmoEncodingImprontaFile(String itemLavAlgoritmoEncodingImprontaFile) {
		this.itemLavAlgoritmoEncodingImprontaFile = itemLavAlgoritmoEncodingImprontaFile;
	}

	public String getItemLavFlgConvertibilePdf() {
		return itemLavFlgConvertibilePdf;
	}

	public void setItemLavFlgConvertibilePdf(String itemLavFlgConvertibilePdf) {
		this.itemLavFlgConvertibilePdf = itemLavFlgConvertibilePdf;
	}

	public String getItemLavCaricatoDa() {
		return itemLavCaricatoDa;
	}

	public void setItemLavCaricatoDa(String itemLavCaricatoDa) {
		this.itemLavCaricatoDa = itemLavCaricatoDa;
	}

	public Date getItemLavDataOraCaricamento() {
		return itemLavDataOraCaricamento;
	}

	public void setItemLavDataOraCaricamento(Date itemLavDataOraCaricamento) {
		this.itemLavDataOraCaricamento = itemLavDataOraCaricamento;
	}

	public String getItemLavModificatoDa() {
		return itemLavModificatoDa;
	}

	public void setItemLavModificatoDa(String itemLavModificatoDa) {
		this.itemLavModificatoDa = itemLavModificatoDa;
	}

	public Date getItemLavDataOraModifica() {
		return itemLavDataOraModifica;
	}

	public void setItemLavDataOraModifica(Date itemLavDataOraModifica) {
		this.itemLavDataOraModifica = itemLavDataOraModifica;
	}

	public Boolean getItemLavIsDocPrimarioChanged() {
		return itemLavIsDocPrimarioChanged;
	}

	public void setItemLavIsDocPrimarioChanged(Boolean itemLavIsDocPrimarioChanged) {
		this.itemLavIsDocPrimarioChanged = itemLavIsDocPrimarioChanged;
	}

	public BigDecimal getItemLavIdDocPrimario() {
		return itemLavIdDocPrimario;
	}

	public void setItemLavIdDocPrimario(BigDecimal itemLavIdDocPrimario) {
		this.itemLavIdDocPrimario = itemLavIdDocPrimario;
	}

	public String getItemLavIdAttachEmailPrimario() {
		return itemLavIdAttachEmailPrimario;
	}

	public void setItemLavIdAttachEmailPrimario(String itemLavIdAttachEmailPrimario) {
		this.itemLavIdAttachEmailPrimario = itemLavIdAttachEmailPrimario;
	}

	public Boolean getItemLavRemoteUriFile() {
		return itemLavRemoteUriFile;
	}

	public void setItemLavRemoteUriFile(Boolean itemLavRemoteUriFile) {
		this.itemLavRemoteUriFile = itemLavRemoteUriFile;
	}

	public Boolean getItemLavFlgNoPubblPrimario() {
		return itemLavFlgNoPubblPrimario;
	}

	public void setItemLavFlgNoPubblPrimario(Boolean itemLavFlgNoPubblPrimario) {
		this.itemLavFlgNoPubblPrimario = itemLavFlgNoPubblPrimario;
	}

	public String getItemLavMimetypeVerPreFirma() {
		return itemLavMimetypeVerPreFirma;
	}

	public void setItemLavMimetypeVerPreFirma(String itemLavMimetypeVerPreFirma) {
		this.itemLavMimetypeVerPreFirma = itemLavMimetypeVerPreFirma;
	}

	public String getItemLavUriFileVerPreFirma() {
		return itemLavUriFileVerPreFirma;
	}

	public void setItemLavUriFileVerPreFirma(String itemLavUriFileVerPreFirma) {
		this.itemLavUriFileVerPreFirma = itemLavUriFileVerPreFirma;
	}

	public String getItemLavNomeFileVerPreFirma() {
		return itemLavNomeFileVerPreFirma;
	}

	public void setItemLavNomeFileVerPreFirma(String itemLavNomeFileVerPreFirma) {
		this.itemLavNomeFileVerPreFirma = itemLavNomeFileVerPreFirma;
	}

	public String getItemLavFlgConvertibilePdfVerPreFirma() {
		return itemLavFlgConvertibilePdfVerPreFirma;
	}

	public void setItemLavFlgConvertibilePdfVerPreFirma(String itemLavFlgConvertibilePdfVerPreFirma) {
		this.itemLavFlgConvertibilePdfVerPreFirma = itemLavFlgConvertibilePdfVerPreFirma;
	}

	public String getItemLavTipoNota() {
		return itemLavTipoNota;
	}

	public void setItemLavTipoNota(String itemLavTipoNota) {
		this.itemLavTipoNota = itemLavTipoNota;
	}

	/**
	 * @return the itemLavNrItem
	 */
	public String getItemLavNrItem() {
		return itemLavNrItem;
	}

	/**
	 * @param itemLavNrItem
	 *            the itemLavNrItem to set
	 */
	public void setItemLavNrItem(String itemLavNrItem) {
		this.itemLavNrItem = itemLavNrItem;
	}

	public MimeTypeFirmaBean getItemLavInfoFile() {
		return itemLavInfoFile;
	}

	public void setItemLavInfoFile(MimeTypeFirmaBean itemLavInfoFile) {
		this.itemLavInfoFile = itemLavInfoFile;
	}

	public File getItemLavFile() {
		return itemLavFile;
	}

	public void setItemLavFile(File itemLavFile) {
		this.itemLavFile = itemLavFile;
	}

	public String getItemLavUriFileSaved() {
		return itemLavUriFileSaved;
	}

	public void setItemLavUriFileSaved(String itemLavUriFileSaved) {
		this.itemLavUriFileSaved = itemLavUriFileSaved;
	}

	public Boolean getFlgNonModCancLocked() {
		return flgNonModCancLocked;
	}

	public void setFlgNonModCancLocked(Boolean flgNonModCancLocked) {
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
