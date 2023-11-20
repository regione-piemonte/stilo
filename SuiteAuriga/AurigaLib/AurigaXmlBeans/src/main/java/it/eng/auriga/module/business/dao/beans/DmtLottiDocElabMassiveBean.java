/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;

import it.eng.core.business.beans.AbstractBean;

public class DmtLottiDocElabMassiveBean extends AbstractBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6612823334135431637L;
	private BigDecimal idJob;
	private String idLotto;
	private String useridApplicazione;
	private BigDecimal idSpAoo;
	private Date tsMessaDisposizione;
	private String formato;
	private String tipologiaDoc;
	private String stato;
	private String xmlRequest;
	private Date tsInizioValidazione;
	private Date tsFineValidazione;
	private Date tsInizioElaborazione;
	private Date tsFineElaborazione;
	private int nroDocDichiarati;
	private int nroFileDichiarati;
	private int nroDocRilevati;
	private int nroFileRilevati;
	private int nroDocCorretti;
	private int nroDocInErrValidazione;
	private int nroDocInErrOperazioni;
	private String codErroreGlobale;
	private String msgErroreGlobale;
	private String workPath;
	private BigDecimal dimensione;
	private String fileEsito;
	private Date tsGenFileEsito;
	private Date tsConsegnaFileEsito;
	private Date tsEsitoCompleto;
	private Date tsIns;
	private Date tsLastUpd;

	public DmtLottiDocElabMassiveBean() {
	}

	public DmtLottiDocElabMassiveBean(BigDecimal idJob, String idLotto, String useridApplicazione, BigDecimal idSpAoo, Date tsMessaDisposizione, String formato,
			String tipologiaDoc, String stato, String xmlRequest, int nroDocDichiarati, int nroFileDichiarati, int nroDocRilevati, int nroFileRilevati,
			int nroDocCorretti, int nroDocInErrValidazione, int nroDocInErrOperazioni, BigDecimal dimensione, Date tsIns, Date tsLastUpd) {
		this.idJob = idJob;
		this.idLotto = idLotto;
		this.useridApplicazione = useridApplicazione;
		this.idSpAoo = idSpAoo;
		this.tsMessaDisposizione = tsMessaDisposizione;
		this.formato = formato;
		this.tipologiaDoc = tipologiaDoc;
		this.stato = stato;
		this.xmlRequest = xmlRequest;
		this.nroDocDichiarati = nroDocDichiarati;
		this.nroFileDichiarati = nroFileDichiarati;
		this.nroDocRilevati = nroDocRilevati;
		this.nroFileRilevati = nroFileRilevati;
		this.nroDocCorretti = nroDocCorretti;
		this.nroDocInErrValidazione = nroDocInErrValidazione;
		this.nroDocInErrOperazioni = nroDocInErrOperazioni;
		this.dimensione = dimensione;
		this.tsIns = tsIns;
		this.tsLastUpd = tsLastUpd;
	}

	public BigDecimal getIdJob() {
		return idJob;
	}

	public void setIdJob(BigDecimal idJob) {
		this.idJob = idJob;
	}

	public String getIdLotto() {
		return idLotto;
	}

	public void setIdLotto(String idLotto) {
		this.idLotto = idLotto;
	}

	public String getUseridApplicazione() {
		return useridApplicazione;
	}

	public void setUseridApplicazione(String useridApplicazione) {
		this.useridApplicazione = useridApplicazione;
	}

	public BigDecimal getIdSpAoo() {
		return idSpAoo;
	}

	public void setIdSpAoo(BigDecimal idSpAoo) {
		this.idSpAoo = idSpAoo;
	}

	public Date getTsMessaDisposizione() {
		return tsMessaDisposizione;
	}

	public void setTsMessaDisposizione(Date tsMessaDisposizione) {
		this.tsMessaDisposizione = tsMessaDisposizione;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getTipologiaDoc() {
		return tipologiaDoc;
	}

	public void setTipologiaDoc(String tipologiaDoc) {
		this.tipologiaDoc = tipologiaDoc;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getXmlRequest() {
		return xmlRequest;
	}

	public void setXmlRequest(String xmlRequest) {
		this.xmlRequest = xmlRequest;
	}

	public Date getTsInizioValidazione() {
		return tsInizioValidazione;
	}

	public void setTsInizioValidazione(Date tsInizioValidazione) {
		this.tsInizioValidazione = tsInizioValidazione;
	}

	public Date getTsFineValidazione() {
		return tsFineValidazione;
	}

	public void setTsFineValidazione(Date tsFineValidazione) {
		this.tsFineValidazione = tsFineValidazione;
	}

	public Date getTsInizioElaborazione() {
		return tsInizioElaborazione;
	}

	public void setTsInizioElaborazione(Date tsInizioElaborazione) {
		this.tsInizioElaborazione = tsInizioElaborazione;
	}

	public Date getTsFineElaborazione() {
		return tsFineElaborazione;
	}

	public void setTsFineElaborazione(Date tsFineElaborazione) {
		this.tsFineElaborazione = tsFineElaborazione;
	}

	public int getNroDocDichiarati() {
		return nroDocDichiarati;
	}

	public void setNroDocDichiarati(int nroDocDichiarati) {
		this.nroDocDichiarati = nroDocDichiarati;
	}

	public int getNroFileDichiarati() {
		return nroFileDichiarati;
	}

	public void setNroFileDichiarati(int nroFileDichiarati) {
		this.nroFileDichiarati = nroFileDichiarati;
	}

	public int getNroDocRilevati() {
		return nroDocRilevati;
	}

	public void setNroDocRilevati(int nroDocRilevati) {
		this.nroDocRilevati = nroDocRilevati;
	}

	public int getNroFileRilevati() {
		return nroFileRilevati;
	}

	public void setNroFileRilevati(int nroFileRilevati) {
		this.nroFileRilevati = nroFileRilevati;
	}

	public int getNroDocCorretti() {
		return nroDocCorretti;
	}

	public void setNroDocCorretti(int nroDocCorretti) {
		this.nroDocCorretti = nroDocCorretti;
	}

	public int getNroDocInErrValidazione() {
		return nroDocInErrValidazione;
	}

	public void setNroDocInErrValidazione(int nroDocInErrValidazione) {
		this.nroDocInErrValidazione = nroDocInErrValidazione;
	}

	public int getNroDocInErrOperazioni() {
		return nroDocInErrOperazioni;
	}

	public void setNroDocInErrOperazioni(int nroDocInErrOperazioni) {
		this.nroDocInErrOperazioni = nroDocInErrOperazioni;
	}

	public String getCodErroreGlobale() {
		return codErroreGlobale;
	}

	public void setCodErroreGlobale(String codErroreGlobale) {
		this.codErroreGlobale = codErroreGlobale;
	}

	public String getMsgErroreGlobale() {
		return msgErroreGlobale;
	}

	public void setMsgErroreGlobale(String msgErroreGlobale) {
		this.msgErroreGlobale = msgErroreGlobale;
	}

	public String getWorkPath() {
		return workPath;
	}

	public void setWorkPath(String workPath) {
		this.workPath = workPath;
	}

	public BigDecimal getDimensione() {
		return dimensione;
	}

	public void setDimensione(BigDecimal dimensione) {
		this.dimensione = dimensione;
	}

	public String getFileEsito() {
		return fileEsito;
	}

	public void setFileEsito(String fileEsito) {
		this.fileEsito = fileEsito;
	}

	public Date getTsGenFileEsito() {
		return tsGenFileEsito;
	}

	public void setTsGenFileEsito(Date tsGenFileEsito) {
		this.tsGenFileEsito = tsGenFileEsito;
	}

	public Date getTsConsegnaFileEsito() {
		return tsConsegnaFileEsito;
	}

	public void setTsConsegnaFileEsito(Date tsConsegnaFileEsito) {
		this.tsConsegnaFileEsito = tsConsegnaFileEsito;
	}

	public Date getTsEsitoCompleto() {
		return tsEsitoCompleto;
	}

	public void setTsEsitoCompleto(Date tsEsitoCompleto) {
		this.tsEsitoCompleto = tsEsitoCompleto;
	}

	public Date getTsIns() {
		return tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	public Date getTsLastUpd() {
		return tsLastUpd;
	}

	public void setTsLastUpd(Date tsLastUpd) {
		this.tsLastUpd = tsLastUpd;
	}

}
