/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreaModAttoInBean extends CreaModDocumentoInBean {

	private static final long serialVersionUID = -6609881844566442763L;
		
	@XmlVariabile(nome = "TASK_RESULT_2_RICH_PARERE_REVISORI_Doc", tipo = TipoVariabile.SEMPLICE)
	private Flag flgRichParereRevisori; 
	@XmlVariabile(nome = "TASK_RESULT_2_SCELTA_ITER_Doc", tipo = TipoVariabile.SEMPLICE)
	private String sceltaIter;	
	@XmlVariabile(nome = "SIGLA_ATTO_RIF_SUB_IMPEGNO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String siglaAttoRifSubImpegno;
	@XmlVariabile(nome = "NUMERO_ATTO_RIF_SUB_IMPEGNO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String numeroAttoRifSubImpegno;
	@XmlVariabile(nome = "ANNO_ATTO_RIF_SUB_IMPEGNO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String annoAttoRifSubImpegno;
	@XmlVariabile(nome = "DATA_ATTO_RIF_SUB_IMPEGNO_Doc", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo=Tipo.DATA)
	private Date dataAttoRifSubImpegno;

	public Flag getFlgRichParereRevisori() {
		return flgRichParereRevisori;
	}
	public void setFlgRichParereRevisori(Flag flgRichParereRevisori) {
		this.flgRichParereRevisori = flgRichParereRevisori;
	}
	public String getSceltaIter() {
		return sceltaIter;
	}
	public void setSceltaIter(String sceltaIter) {
		this.sceltaIter = sceltaIter;
	}
	public String getSiglaAttoRifSubImpegno() {
		return siglaAttoRifSubImpegno;
	}
	public void setSiglaAttoRifSubImpegno(String siglaAttoRifSubImpegno) {
		this.siglaAttoRifSubImpegno = siglaAttoRifSubImpegno;
	}
	public String getNumeroAttoRifSubImpegno() {
		return numeroAttoRifSubImpegno;
	}
	public void setNumeroAttoRifSubImpegno(String numeroAttoRifSubImpegno) {
		this.numeroAttoRifSubImpegno = numeroAttoRifSubImpegno;
	}
	public String getAnnoAttoRifSubImpegno() {
		return annoAttoRifSubImpegno;
	}
	public void setAnnoAttoRifSubImpegno(String annoAttoRifSubImpegno) {
		this.annoAttoRifSubImpegno = annoAttoRifSubImpegno;
	}
	public Date getDataAttoRifSubImpegno() {
		return dataAttoRifSubImpegno;
	}
	public void setDataAttoRifSubImpegno(Date dataAttoRifSubImpegno) {
		this.dataAttoRifSubImpegno = dataAttoRifSubImpegno;
	}

}
