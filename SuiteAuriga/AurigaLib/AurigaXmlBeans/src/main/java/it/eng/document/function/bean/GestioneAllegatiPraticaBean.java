/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GestioneAllegatiPraticaBean implements Serializable {

	private static final long serialVersionUID = 2995576840494796487L;

	private String idProcess;
	private String idTipoAllegato;
	private List<BigDecimal> idUdToRemove;
	private List<AllegatoPraticaBean> fileAllegati;
	private String codRuoloDocInProd;
	private String flgAcqProd;
	private String codStatoUdInProc;
	
	public String getIdProcess() {
		return idProcess;
	}
	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}
	public String getIdTipoAllegato() {
		return idTipoAllegato;
	}
	public void setIdTipoAllegato(String idTipoAllegato) {
		this.idTipoAllegato = idTipoAllegato;
	}
	public List<BigDecimal> getIdUdToRemove() {
		return idUdToRemove;
	}
	public void setIdUdToRemove(List<BigDecimal> idUdToRemove) {
		this.idUdToRemove = idUdToRemove;
	}
	public List<AllegatoPraticaBean> getFileAllegati() {
		return fileAllegati;
	}
	public void setFileAllegati(List<AllegatoPraticaBean> fileAllegati) {
		this.fileAllegati = fileAllegati;
	}
	public String getCodRuoloDocInProd() {
		return codRuoloDocInProd;
	}
	public void setCodRuoloDocInProd(String codRuoloDocInProd) {
		this.codRuoloDocInProd = codRuoloDocInProd;
	}
	public String getFlgAcqProd() {
		return flgAcqProd;
	}
	public void setFlgAcqProd(String flgAcqProd) {
		this.flgAcqProd = flgAcqProd;
	}
	public String getCodStatoUdInProc() {
		return codStatoUdInProc;
	}
	public void setCodStatoUdInProc(String codStatoUdInProc) {
		this.codStatoUdInProc = codStatoUdInProc;
	}
	
		
}
