/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

public class ResponseVerificaFirmaBean {

	private String formatoBusta;
	private String dataFirma;

	private String firmatario;
	private String dataDecorrenzaCertificato;
	private String dataScadenzaCertificato;

	private String tsaName;
	private String dataMarca;
	private String tsPolicy;

	private RisultatoVerificaFirmaBean verificaFirmato;
	private RisultatoVerificaFirmaBean verificaGlobale;
	private RisultatoVerificaFirmaBean verificaGlobaleSingolaFirma;
	private RisultatoVerificaFirmaBean verificaFirma;
	private RisultatoVerificaFirmaBean verificaCertificato;
	private RisultatoVerificaFirmaBean verificaCa;
	private RisultatoVerificaFirmaBean verificaCrl;
	private RisultatoVerificaFirmaBean verificaFormato;
	private RisultatoVerificaFirmaBean verificaCodiceEseguibile;
	private List<ResponseVerificaFirmaBean> verificaFirmeList = new ArrayList<ResponseVerificaFirmaBean>();
	private List<RisultatoVerificaMarcaBean> verificaMarcheList = new ArrayList<RisultatoVerificaMarcaBean>();
	private ResponseVerificaFirmaBean firmaInterna;

	private String errorMessage;

	public RisultatoVerificaFirmaBean getVerificaGlobale() {
		return verificaGlobale;
	}

	public void setVerificaGlobale(RisultatoVerificaFirmaBean verificaGlobale) {
		this.verificaGlobale = verificaGlobale;
	}

	public RisultatoVerificaFirmaBean getVerificaGlobaleSingolaFirma() {
		return verificaGlobaleSingolaFirma;
	}

	public void setVerificaGlobaleSingolaFirma(RisultatoVerificaFirmaBean verificaGlobaleSingolaFirma) {
		this.verificaGlobaleSingolaFirma = verificaGlobaleSingolaFirma;
	}

	public RisultatoVerificaFirmaBean getVerificaFirma() {
		return verificaFirma;
	}

	public void setVerificaFirma(RisultatoVerificaFirmaBean verificaFirma) {
		this.verificaFirma = verificaFirma;
	}

	public RisultatoVerificaFirmaBean getVerificaCertificato() {
		return verificaCertificato;
	}

	public void setVerificaCertificato(RisultatoVerificaFirmaBean verificaCertificato) {
		this.verificaCertificato = verificaCertificato;
	}

	public RisultatoVerificaFirmaBean getVerificaCa() {
		return verificaCa;
	}

	public void setVerificaCa(RisultatoVerificaFirmaBean verificaCa) {
		this.verificaCa = verificaCa;
	}

	public RisultatoVerificaFirmaBean getVerificaCrl() {
		return verificaCrl;
	}

	public void setVerificaCrl(RisultatoVerificaFirmaBean verificaCrl) {
		this.verificaCrl = verificaCrl;
	}

	public RisultatoVerificaFirmaBean getVerificaFormato() {
		return verificaFormato;
	}

	public void setVerificaFormato(RisultatoVerificaFirmaBean verificaFormato) {
		this.verificaFormato = verificaFormato;
	}

	public RisultatoVerificaFirmaBean getVerificaCodiceEseguibile() {
		return verificaCodiceEseguibile;
	}

	public void setVerificaCodiceEseguibile(RisultatoVerificaFirmaBean verificaCodiceEseguibile) {
		this.verificaCodiceEseguibile = verificaCodiceEseguibile;
	}

	public List<ResponseVerificaFirmaBean> getVerificaFirmeList() {
		return verificaFirmeList;
	}

	public void setVerificaFirmeList(List<ResponseVerificaFirmaBean> verificaFirmeList) {
		this.verificaFirmeList = verificaFirmeList;
	}

	public String getFormatoBusta() {
		return formatoBusta;
	}

	public void setFormatoBusta(String formatoBusta) {
		this.formatoBusta = formatoBusta;
	}

	public ResponseVerificaFirmaBean() {
		super();
	}

	public ResponseVerificaFirmaBean getFirmaInterna() {
		return firmaInterna;
	}

	public void setFirmaInterna(ResponseVerificaFirmaBean firmaInterna) {
		this.firmaInterna = firmaInterna;
	}

	public String getFirmatario() {
		return firmatario;
	}

	public void setFirmatario(String firmatario) {
		this.firmatario = firmatario;
	}

	public String getDataDecorrenzaCertificato() {
		return dataDecorrenzaCertificato;
	}

	public void setDataDecorrenzaCertificato(String dataDecorrenzaCertificato) {
		this.dataDecorrenzaCertificato = dataDecorrenzaCertificato;
	}

	public String getDataScadenzaCertificato() {
		return dataScadenzaCertificato;
	}

	public void setDataScadenzaCertificato(String dataScadenzaCertificato) {
		this.dataScadenzaCertificato = dataScadenzaCertificato;
	}

	public String getDataFirma() {
		return dataFirma;
	}

	public void setDataFirma(String dataFirma) {
		this.dataFirma = dataFirma;
	}

	public String getTsaName() {
		return tsaName;
	}

	public void setTsaName(String tsaName) {
		this.tsaName = tsaName;
	}

	public String getDataMarca() {
		return dataMarca;
	}

	public void setDataMarca(String dataMarca) {
		this.dataMarca = dataMarca;
	}

	public String getTsPolicy() {
		return tsPolicy;
	}

	public void setTsPolicy(String tsPolicy) {
		this.tsPolicy = tsPolicy;
	}

	public List<RisultatoVerificaMarcaBean> getVerificaMarcheList() {
		return verificaMarcheList;
	}

	public void setVerificaMarcheList(List<RisultatoVerificaMarcaBean> verificaMarcheList) {
		this.verificaMarcheList = verificaMarcheList;
	}

	public RisultatoVerificaFirmaBean getVerificaFirmato() {
		return verificaFirmato;
	}

	public void setVerificaFirmato(RisultatoVerificaFirmaBean verificaFirmato) {
		this.verificaFirmato = verificaFirmato;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
