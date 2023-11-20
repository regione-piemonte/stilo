/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

@XmlRootElement
public class TFolderCaselleBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -7341833444762147789L;

	private String classificazione;
	private boolean flgChiuso;
	private String idCasella;
	private String idFolderCasella;
	private String idUteIns;
	private String idUteUltimoAgg;
	private String nomeFolder;
	private String idFolderSup;

	@Override
	public String toString() {
		return "TFolderCaselleBean [classificazione=" + classificazione + ", idCasella=" + idCasella + ", idFolderCasella=" + idFolderCasella + ", nomeFolder="
				+ nomeFolder + "]";
	}

	private Date tsIns;
	private Date tsUltimoAgg;

	public String getClassificazione() {
		return classificazione;
	}

	public void setClassificazione(String classificazione) {
		this.classificazione = classificazione;
	}

	public boolean getFlgChiuso() {
		return flgChiuso;
	}

	public void setFlgChiuso(boolean flgChiuso) {
		this.flgChiuso = flgChiuso;
	}

	public String getIdCasella() {
		return idCasella;
	}

	public void setIdCasella(String idCasella) {
		this.idCasella = idCasella;
	}

	public String getIdFolderCasella() {
		return idFolderCasella;
	}

	public void setIdFolderCasella(String idFolderCasella) {
		this.idFolderCasella = idFolderCasella;
	}

	public String getIdUteIns() {
		return idUteIns;
	}

	public void setIdUteIns(String idUteIns) {
		this.idUteIns = idUteIns;
	}

	public String getIdUteUltimoAgg() {
		return idUteUltimoAgg;
	}

	public void setIdUteUltimoAgg(String idUteUltimoAgg) {
		this.idUteUltimoAgg = idUteUltimoAgg;
	}

	public String getNomeFolder() {
		return nomeFolder;
	}

	public void setNomeFolder(String nomeFolder) {
		this.nomeFolder = nomeFolder;
	}

	public Date getTsIns() {
		return tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	public Date getTsUltimoAgg() {
		return tsUltimoAgg;
	}

	public void setTsUltimoAgg(Date tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
	}

	public String getIdFolderSup() {
		return idFolderSup;
	}

	public void setIdFolderSup(String idFolderSup) {
		this.idFolderSup = idFolderSup;
	}

}