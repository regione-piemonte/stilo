/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class RiepilogoDatiLiquidazioneAVB {

	@NumeroColonna(numero = "1")
	private String uriExcel;
	
	@NumeroColonna(numero = "2")
	private String oggettoSpesaNumDetImpegno;
		
	@NumeroColonna(numero = "3")
	private String oggettoSpesaOggetto;
	
	@NumeroColonna(numero = "4")
	private String beneficiarioPagamentoBeneficiario;
	
	@NumeroColonna(numero = "5")
	private String beneficiarioPagamentoCodFiscale;	
	
	@NumeroColonna(numero = "6")
	private String beneficiarioPagamentoIva;
	
	@NumeroColonna(numero = "7")
	private String documentoDiDebito;
	
	@NumeroColonna(numero = "8")
	private String beneficiarioPagamentoImporto;

	public String getUriExcel() {
		return uriExcel;
	}

	public void setUriExcel(String uriExcel) {
		this.uriExcel = uriExcel;
	}

	public String getOggettoSpesaNumDetImpegno() {
		return oggettoSpesaNumDetImpegno;
	}

	public void setOggettoSpesaNumDetImpegno(String oggettoSpesaNumDetImpegno) {
		this.oggettoSpesaNumDetImpegno = oggettoSpesaNumDetImpegno;
	}

	public String getOggettoSpesaOggetto() {
		return oggettoSpesaOggetto;
	}

	public void setOggettoSpesaOggetto(String oggettoSpesaOggetto) {
		this.oggettoSpesaOggetto = oggettoSpesaOggetto;
	}

	public String getBeneficiarioPagamentoBeneficiario() {
		return beneficiarioPagamentoBeneficiario;
	}

	public void setBeneficiarioPagamentoBeneficiario(String beneficiarioPagamentoBeneficiario) {
		this.beneficiarioPagamentoBeneficiario = beneficiarioPagamentoBeneficiario;
	}

	public String getBeneficiarioPagamentoCodFiscale() {
		return beneficiarioPagamentoCodFiscale;
	}

	public void setBeneficiarioPagamentoCodFiscale(String beneficiarioPagamentoCodFiscale) {
		this.beneficiarioPagamentoCodFiscale = beneficiarioPagamentoCodFiscale;
	}

	public String getBeneficiarioPagamentoIva() {
		return beneficiarioPagamentoIva;
	}

	public void setBeneficiarioPagamentoIva(String beneficiarioPagamentoIva) {
		this.beneficiarioPagamentoIva = beneficiarioPagamentoIva;
	}

	public String getDocumentoDiDebito() {
		return documentoDiDebito;
	}

	public void setDocumentoDiDebito(String documentoDiDebito) {
		this.documentoDiDebito = documentoDiDebito;
	}

	public String getBeneficiarioPagamentoImporto() {
		return beneficiarioPagamentoImporto;
	}

	public void setBeneficiarioPagamentoImporto(String beneficiarioPagamentoImporto) {
		this.beneficiarioPagamentoImporto = beneficiarioPagamentoImporto;
	}

}
