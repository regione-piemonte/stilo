/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class DatiLiquidazioneAVBXmlBean implements Cloneable {

	@NumeroColonna(numero = "1")
	private String uriExcel;
	
	@NumeroColonna(numero = "2")
	private String oggettoSpesaNumDetImpegno;
	
	@NumeroColonna(numero = "3")
	private String oggettoSpesaAltroAtto;
	
	@NumeroColonna(numero = "4")
	private String oggettoSpesaFlgEsecutivo;
	
	@NumeroColonna(numero = "5")
	private String oggettoSpesaOggetto;
	
	@NumeroColonna(numero = "6")
	private String oggettoSpesaFlgConformeRegCont;
	
	@NumeroColonna(numero = "7")
	private String oggettoSpesaFlgDebitoCommerciale;
	
	@NumeroColonna(numero = "8")
	private String oggettoSpesaFlgPagamentoSoggArt22Dlgs33_2013;
	
	@NumeroColonna(numero = "9")
	private String oggettoSpesaFlgVerificaAnac;
	
	@NumeroColonna(numero = "10")
	private String oggettoSpesaFlgPagamentoSoggArt1Cc125_129L124_2017;
	
	@NumeroColonna(numero = "11")
	private String oggettoSpesaFlhSogliaSuperata;
	
	@NumeroColonna(numero = "12")
	private String oggettoSpesaFlgDebitoFuoriBilancio;
	
	@NumeroColonna(numero = "13")
	private String beneficiarioPagamentoBeneficiario;
	
	@NumeroColonna(numero = "14")
	private String beneficiarioPagamentoSede;
	
	@NumeroColonna(numero = "15")
	private String beneficiarioPagamentoIva;
	
	@NumeroColonna(numero = "16")
	private String beneficiarioPagamentoCodFiscale;
	
	@NumeroColonna(numero = "17")
	private String beneficiarioPagamentoFlgCreditoCertificato;
	
	@NumeroColonna(numero = "18")
	private String beneficiarioPagamentoFlgCreditoCeduto;
	
	@NumeroColonna(numero = "19")
	private String beneficiarioPagamentoSoggettoQuietanza;
	
	@NumeroColonna(numero = "20")
	private String beneficiarioPagamentoDatiDocDebito;
	
	@NumeroColonna(numero = "21")
	private String beneficiarioPagamentoNumero;
	
	@NumeroColonna(numero = "22")
	private String beneficiarioPagamentoData;
	
	@NumeroColonna(numero = "23")
	private String beneficiarioPagamentoImporto;
	
	@NumeroColonna(numero = "24")
	private String beneficiarioPagamentoImponibile;
	
	@NumeroColonna(numero = "25")
	private String beneficiarioPagamentoIvaImporto;
	
	@NumeroColonna(numero = "26")
	private String beneficiarioPagamentoFlgRitenutaFiscale;
	
	@NumeroColonna(numero = "27")
	private String beneficiarioPagamentoImportoRitenutaFiscale;
	
	@NumeroColonna(numero = "28")
	private String beneficiarioPagamentoFlgRegolarita;
	
	@NumeroColonna(numero = "29")
	private String beneficiarioPagamentoModalitaPagamento;
	
	@NumeroColonna(numero = "30")
	private String beneficiarioPagamentoBanca;
	
	@NumeroColonna(numero = "31")
	private String beneficiarioPagamentoIban;
	
	@NumeroColonna(numero = "32")
	private String beneficiarioPagamentoRifVerbaleEconomo;
	
	@NumeroColonna(numero = "33")
	private String beneficiarioPagamentoPresenteNotaCredito;
	
	@NumeroColonna(numero = "34")
	private String beneficiarioPagamentoTestoNotaCredito;
	
	@NumeroColonna(numero = "35")
	private String regimeIvaBolloAttivitaIva;
	
	@NumeroColonna(numero = "36")
	private String regimeIvaBolloTipoAttivita;
	
	@NumeroColonna(numero = "37")
	private String regimeIvaBolloRegimeIva;
	
	@NumeroColonna(numero = "38")
	private String regimeIvaBolloAltro;
	
	@NumeroColonna(numero = "39")
	private String durcFlgVerificaRegContributiva;
	
	@NumeroColonna(numero = "40")
	private String durcFlgRegolareDurc;
	
	@NumeroColonna(numero = "41")
	private String durcFlgUlterioreVerifica;
	
	@NumeroColonna(numero = "42")
	private String durcInterventoSostitutivo;
	
	@NumeroColonna(numero = "43")
	private String antiriciclaggioFlgVerificaNonInadempienza;
	
	@NumeroColonna(numero = "44")
	private String antiriciclaggioFlgVerificaDisciplinaPrassiSettore;
	
	@NumeroColonna(numero = "45")
	private String antiriciclaggioFlgRisultatoNonInadempiente;
	
	@NumeroColonna(numero = "46")
	private String cigCupFlgApplicabillita;
	
	@NumeroColonna(numero = "47")
	private String cigCupSmartCig;
	
	@NumeroColonna(numero = "48")
	private String cigCupCig;
	
	@NumeroColonna(numero = "49")
	private String cigCupFlgAcquisitoPerfezionato;
	
	@NumeroColonna(numero = "50")
	private String cigCupCup;
	
	@NumeroColonna(numero = "51")
	private String cigCupMotiviEsclusione;
	
	@NumeroColonna(numero = "52")
	private String cigCupUltInf;
	
	@NumeroColonna(numero = "53")
	private String progressivoRegPcc;
	
	@NumeroColonna(numero = "54")
	private String terminiPagamentoGgScadenza;
	
	@NumeroColonna(numero = "55")
	private String terminiPagamentoDataDecorrenza;
	
	@NumeroColonna(numero = "56")
	private String terminiPagamentoDataScadenza;
	
	@NumeroColonna(numero = "57")
	private String terminiPagamentoMotiviInterruzioneTermini;
	
	@NumeroColonna(numero = "58")
	private String terminiPagamentoDataInizioSospensione;
	
	@NumeroColonna(numero = "59")
	private String terminiPagamentoMotiviEsclusioneCalcoloTmp;
	
	@NumeroColonna(numero = "60")
	private String imputazioneContabileCdc;
	
	@NumeroColonna(numero = "61")
	private String imputazioneContabileImporto;
	
	@NumeroColonna(numero = "62")
	private String imputazioneContabileMissione;
	
	@NumeroColonna(numero = "63")
	private String imputazioneContabileProgramma;
	
	@NumeroColonna(numero = "64")
	private String imputazioneContabileTitolo;
	
	@NumeroColonna(numero = "65")
	private String imputazioneContabileRifBilancio;
	
	@NumeroColonna(numero = "66")
	private String imputazioneContabileMacroaggregato;
	
	@NumeroColonna(numero = "67")
	private String imputazioneContabileCapitolo;
	
	@NumeroColonna(numero = "68")
	private String imputazioneContabileImpegno;
	
	@NumeroColonna(numero = "69")
	private String imputazioneContabileCodSiope;
	
	@NumeroColonna(numero = "70")
	private String spesaEntrataFlgSpesaCorrEntrata;
	
	@NumeroColonna(numero = "71")
	private String spesaEntrataFlgRispCronoprogramma;
	
	@NumeroColonna(numero = "72")
	private String spesaEntrataRifEntrata;
	
	@NumeroColonna(numero = "73")
	private String spesaEntrataDetNum;
	
	@NumeroColonna(numero = "74")
	private String spesaEntrataDetAccertamento;
	
	@NumeroColonna(numero = "75")
	private String spesaEntrataCapitolo;
	
	@NumeroColonna(numero = "76")
	private String spesaEntrataReversale;
	
	@NumeroColonna(numero = "77")
	private String spesaEntrataImportoSomministrato;
	
	@NumeroColonna(numero = "78")
	private String spesaEntrataImportDaSomministrare;
	
	@NumeroColonna(numero = "79")
	private String spesaEntrataModTempOp;
	
	@NumeroColonna(numero = "80")
	private String disimpegnoImporto;
	
	@NumeroColonna(numero = "81")
	private String disimpegnoImpegnoAnno;
	
	@NumeroColonna(numero = "82")
	private String disimpegnoSub;
	
	@NumeroColonna(numero = "83")
	private String disimpegnoCapitolo;
	
	@NumeroColonna(numero = "84")
	private String liquidazioneCompensiProgettualitaFlgLiq;
	
	@NumeroColonna(numero = "85")
	private String liquidazioneCompensiProgettualitaFlgVerOdv;
	
	@NumeroColonna(numero = "86")
	private String spesaTitoloBilancioTipoFinanziamento;
	
	@NumeroColonna(numero = "87")
	private String spesaTitoloBilancioBuonoCarico;
	
	@NumeroColonna(numero = "88")
	private String spesaTitoloBilancioRitenuta;
	
	@NumeroColonna(numero = "89")
	private String spesaTitoloBilancioSvincolo;
	
	@NumeroColonna(numero = "90")
	private String spesaTitoloBilancioFlgEffPatrImmEnte;
	
	@NumeroColonna(numero = "91")
	private String spesaTitoloBilancioCertPag;
	
	@NumeroColonna(numero = "92")
	private String spesaTitoloBilancioAltro;
	
	public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    } 

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

	public String getOggettoSpesaAltroAtto() {
		return oggettoSpesaAltroAtto;
	}

	public void setOggettoSpesaAltroAtto(String oggettoSpesaAltroAtto) {
		this.oggettoSpesaAltroAtto = oggettoSpesaAltroAtto;
	}

	public String getOggettoSpesaFlgEsecutivo() {
		return oggettoSpesaFlgEsecutivo;
	}

	public void setOggettoSpesaFlgEsecutivo(String oggettoSpesaFlgEsecutivo) {
		this.oggettoSpesaFlgEsecutivo = oggettoSpesaFlgEsecutivo;
	}

	public String getOggettoSpesaOggetto() {
		return oggettoSpesaOggetto;
	}

	public void setOggettoSpesaOggetto(String oggettoSpesaOggetto) {
		this.oggettoSpesaOggetto = oggettoSpesaOggetto;
	}

	public String getOggettoSpesaFlgConformeRegCont() {
		return oggettoSpesaFlgConformeRegCont;
	}

	public void setOggettoSpesaFlgConformeRegCont(String oggettoSpesaFlgConformeRegCont) {
		this.oggettoSpesaFlgConformeRegCont = oggettoSpesaFlgConformeRegCont;
	}

	public String getOggettoSpesaFlgDebitoCommerciale() {
		return oggettoSpesaFlgDebitoCommerciale;
	}

	public void setOggettoSpesaFlgDebitoCommerciale(String oggettoSpesaFlgDebitoCommerciale) {
		this.oggettoSpesaFlgDebitoCommerciale = oggettoSpesaFlgDebitoCommerciale;
	}

	public String getOggettoSpesaFlgPagamentoSoggArt22Dlgs33_2013() {
		return oggettoSpesaFlgPagamentoSoggArt22Dlgs33_2013;
	}

	public void setOggettoSpesaFlgPagamentoSoggArt22Dlgs33_2013(String oggettoSpesaFlgPagamentoSoggArt22Dlgs33_2013) {
		this.oggettoSpesaFlgPagamentoSoggArt22Dlgs33_2013 = oggettoSpesaFlgPagamentoSoggArt22Dlgs33_2013;
	}

	public String getOggettoSpesaFlgVerificaAnac() {
		return oggettoSpesaFlgVerificaAnac;
	}

	public void setOggettoSpesaFlgVerificaAnac(String oggettoSpesaFlgVerificaAnac) {
		this.oggettoSpesaFlgVerificaAnac = oggettoSpesaFlgVerificaAnac;
	}

	public String getOggettoSpesaFlgPagamentoSoggArt1Cc125_129L124_2017() {
		return oggettoSpesaFlgPagamentoSoggArt1Cc125_129L124_2017;
	}

	public void setOggettoSpesaFlgPagamentoSoggArt1Cc125_129L124_2017(
			String oggettoSpesaFlgPagamentoSoggArt1Cc125_129L124_2017) {
		this.oggettoSpesaFlgPagamentoSoggArt1Cc125_129L124_2017 = oggettoSpesaFlgPagamentoSoggArt1Cc125_129L124_2017;
	}

	public String getOggettoSpesaFlhSogliaSuperata() {
		return oggettoSpesaFlhSogliaSuperata;
	}

	public void setOggettoSpesaFlhSogliaSuperata(String oggettoSpesaFlhSogliaSuperata) {
		this.oggettoSpesaFlhSogliaSuperata = oggettoSpesaFlhSogliaSuperata;
	}

	public String getOggettoSpesaFlgDebitoFuoriBilancio() {
		return oggettoSpesaFlgDebitoFuoriBilancio;
	}

	public void setOggettoSpesaFlgDebitoFuoriBilancio(String oggettoSpesaFlgDebitoFuoriBilancio) {
		this.oggettoSpesaFlgDebitoFuoriBilancio = oggettoSpesaFlgDebitoFuoriBilancio;
	}

	public String getBeneficiarioPagamentoBeneficiario() {
		return beneficiarioPagamentoBeneficiario;
	}

	public void setBeneficiarioPagamentoBeneficiario(String beneficiarioPagamentoBeneficiario) {
		this.beneficiarioPagamentoBeneficiario = beneficiarioPagamentoBeneficiario;
	}

	public String getBeneficiarioPagamentoSede() {
		return beneficiarioPagamentoSede;
	}

	public void setBeneficiarioPagamentoSede(String beneficiarioPagamentoSede) {
		this.beneficiarioPagamentoSede = beneficiarioPagamentoSede;
	}

	public String getBeneficiarioPagamentoIva() {
		return beneficiarioPagamentoIva;
	}

	public void setBeneficiarioPagamentoIva(String beneficiarioPagamentoIva) {
		this.beneficiarioPagamentoIva = beneficiarioPagamentoIva;
	}

	public String getBeneficiarioPagamentoCodFiscale() {
		return beneficiarioPagamentoCodFiscale;
	}

	public void setBeneficiarioPagamentoCodFiscale(String beneficiarioPagamentoCodFiscale) {
		this.beneficiarioPagamentoCodFiscale = beneficiarioPagamentoCodFiscale;
	}

	public String getBeneficiarioPagamentoFlgCreditoCertificato() {
		return beneficiarioPagamentoFlgCreditoCertificato;
	}

	public void setBeneficiarioPagamentoFlgCreditoCertificato(String beneficiarioPagamentoFlgCreditoCertificato) {
		this.beneficiarioPagamentoFlgCreditoCertificato = beneficiarioPagamentoFlgCreditoCertificato;
	}

	public String getBeneficiarioPagamentoFlgCreditoCeduto() {
		return beneficiarioPagamentoFlgCreditoCeduto;
	}

	public void setBeneficiarioPagamentoFlgCreditoCeduto(String beneficiarioPagamentoFlgCreditoCeduto) {
		this.beneficiarioPagamentoFlgCreditoCeduto = beneficiarioPagamentoFlgCreditoCeduto;
	}

	public String getBeneficiarioPagamentoSoggettoQuietanza() {
		return beneficiarioPagamentoSoggettoQuietanza;
	}

	public void setBeneficiarioPagamentoSoggettoQuietanza(String beneficiarioPagamentoSoggettoQuietanza) {
		this.beneficiarioPagamentoSoggettoQuietanza = beneficiarioPagamentoSoggettoQuietanza;
	}

	public String getBeneficiarioPagamentoDatiDocDebito() {
		return beneficiarioPagamentoDatiDocDebito;
	}

	public void setBeneficiarioPagamentoDatiDocDebito(String beneficiarioPagamentoDatiDocDebito) {
		this.beneficiarioPagamentoDatiDocDebito = beneficiarioPagamentoDatiDocDebito;
	}

	public String getBeneficiarioPagamentoNumero() {
		return beneficiarioPagamentoNumero;
	}

	public void setBeneficiarioPagamentoNumero(String beneficiarioPagamentoNumero) {
		this.beneficiarioPagamentoNumero = beneficiarioPagamentoNumero;
	}

	public String getBeneficiarioPagamentoData() {
		return beneficiarioPagamentoData;
	}

	public void setBeneficiarioPagamentoData(String beneficiarioPagamentoData) {
		this.beneficiarioPagamentoData = beneficiarioPagamentoData;
	}

	public String getBeneficiarioPagamentoImporto() {
		return beneficiarioPagamentoImporto;
	}

	public void setBeneficiarioPagamentoImporto(String beneficiarioPagamentoImporto) {
		this.beneficiarioPagamentoImporto = beneficiarioPagamentoImporto;
	}

	public String getBeneficiarioPagamentoImponibile() {
		return beneficiarioPagamentoImponibile;
	}

	public void setBeneficiarioPagamentoImponibile(String beneficiarioPagamentoImponibile) {
		this.beneficiarioPagamentoImponibile = beneficiarioPagamentoImponibile;
	}

	public String getBeneficiarioPagamentoIvaImporto() {
		return beneficiarioPagamentoIvaImporto;
	}

	public void setBeneficiarioPagamentoIvaImporto(String beneficiarioPagamentoIvaImporto) {
		this.beneficiarioPagamentoIvaImporto = beneficiarioPagamentoIvaImporto;
	}

	public String getBeneficiarioPagamentoFlgRitenutaFiscale() {
		return beneficiarioPagamentoFlgRitenutaFiscale;
	}

	public void setBeneficiarioPagamentoFlgRitenutaFiscale(String beneficiarioPagamentoFlgRitenutaFiscale) {
		this.beneficiarioPagamentoFlgRitenutaFiscale = beneficiarioPagamentoFlgRitenutaFiscale;
	}

	public String getBeneficiarioPagamentoImportoRitenutaFiscale() {
		return beneficiarioPagamentoImportoRitenutaFiscale;
	}

	public void setBeneficiarioPagamentoImportoRitenutaFiscale(String beneficiarioPagamentoImportoRitenutaFiscale) {
		this.beneficiarioPagamentoImportoRitenutaFiscale = beneficiarioPagamentoImportoRitenutaFiscale;
	}

	public String getBeneficiarioPagamentoFlgRegolarita() {
		return beneficiarioPagamentoFlgRegolarita;
	}

	public void setBeneficiarioPagamentoFlgRegolarita(String beneficiarioPagamentoFlgRegolarita) {
		this.beneficiarioPagamentoFlgRegolarita = beneficiarioPagamentoFlgRegolarita;
	}

	public String getBeneficiarioPagamentoModalitaPagamento() {
		return beneficiarioPagamentoModalitaPagamento;
	}

	public void setBeneficiarioPagamentoModalitaPagamento(String beneficiarioPagamentoModalitaPagamento) {
		this.beneficiarioPagamentoModalitaPagamento = beneficiarioPagamentoModalitaPagamento;
	}

	public String getBeneficiarioPagamentoBanca() {
		return beneficiarioPagamentoBanca;
	}

	public void setBeneficiarioPagamentoBanca(String beneficiarioPagamentoBanca) {
		this.beneficiarioPagamentoBanca = beneficiarioPagamentoBanca;
	}

	public String getBeneficiarioPagamentoIban() {
		return beneficiarioPagamentoIban;
	}

	public void setBeneficiarioPagamentoIban(String beneficiarioPagamentoIban) {
		this.beneficiarioPagamentoIban = beneficiarioPagamentoIban;
	}

	public String getBeneficiarioPagamentoRifVerbaleEconomo() {
		return beneficiarioPagamentoRifVerbaleEconomo;
	}

	public void setBeneficiarioPagamentoRifVerbaleEconomo(String beneficiarioPagamentoRifVerbaleEconomo) {
		this.beneficiarioPagamentoRifVerbaleEconomo = beneficiarioPagamentoRifVerbaleEconomo;
	}

	public String getBeneficiarioPagamentoPresenteNotaCredito() {
		return beneficiarioPagamentoPresenteNotaCredito;
	}

	public void setBeneficiarioPagamentoPresenteNotaCredito(String beneficiarioPagamentoPresenteNotaCredito) {
		this.beneficiarioPagamentoPresenteNotaCredito = beneficiarioPagamentoPresenteNotaCredito;
	}

	public String getBeneficiarioPagamentoTestoNotaCredito() {
		return beneficiarioPagamentoTestoNotaCredito;
	}

	public void setBeneficiarioPagamentoTestoNotaCredito(String beneficiarioPagamentoTestoNotaCredito) {
		this.beneficiarioPagamentoTestoNotaCredito = beneficiarioPagamentoTestoNotaCredito;
	}

	public String getRegimeIvaBolloAttivitaIva() {
		return regimeIvaBolloAttivitaIva;
	}

	public void setRegimeIvaBolloAttivitaIva(String regimeIvaBolloAttivitaIva) {
		this.regimeIvaBolloAttivitaIva = regimeIvaBolloAttivitaIva;
	}

	public String getRegimeIvaBolloTipoAttivita() {
		return regimeIvaBolloTipoAttivita;
	}

	public void setRegimeIvaBolloTipoAttivita(String regimeIvaBolloTipoAttivita) {
		this.regimeIvaBolloTipoAttivita = regimeIvaBolloTipoAttivita;
	}

	public String getRegimeIvaBolloRegimeIva() {
		return regimeIvaBolloRegimeIva;
	}

	public void setRegimeIvaBolloRegimeIva(String regimeIvaBolloRegimeIva) {
		this.regimeIvaBolloRegimeIva = regimeIvaBolloRegimeIva;
	}

	public String getRegimeIvaBolloAltro() {
		return regimeIvaBolloAltro;
	}

	public void setRegimeIvaBolloAltro(String regimeIvaBolloAltro) {
		this.regimeIvaBolloAltro = regimeIvaBolloAltro;
	}

	public String getDurcFlgVerificaRegContributiva() {
		return durcFlgVerificaRegContributiva;
	}

	public void setDurcFlgVerificaRegContributiva(String durcFlgVerificaRegContributiva) {
		this.durcFlgVerificaRegContributiva = durcFlgVerificaRegContributiva;
	}

	public String getDurcFlgRegolareDurc() {
		return durcFlgRegolareDurc;
	}

	public void setDurcFlgRegolareDurc(String durcFlgRegolareDurc) {
		this.durcFlgRegolareDurc = durcFlgRegolareDurc;
	}

	public String getDurcFlgUlterioreVerifica() {
		return durcFlgUlterioreVerifica;
	}

	public void setDurcFlgUlterioreVerifica(String durcFlgUlterioreVerifica) {
		this.durcFlgUlterioreVerifica = durcFlgUlterioreVerifica;
	}

	public String getDurcInterventoSostitutivo() {
		return durcInterventoSostitutivo;
	}

	public void setDurcInterventoSostitutivo(String durcInterventoSostitutivo) {
		this.durcInterventoSostitutivo = durcInterventoSostitutivo;
	}

	public String getAntiriciclaggioFlgVerificaNonInadempienza() {
		return antiriciclaggioFlgVerificaNonInadempienza;
	}

	public void setAntiriciclaggioFlgVerificaNonInadempienza(String antiriciclaggioFlgVerificaNonInadempienza) {
		this.antiriciclaggioFlgVerificaNonInadempienza = antiriciclaggioFlgVerificaNonInadempienza;
	}

	public String getAntiriciclaggioFlgVerificaDisciplinaPrassiSettore() {
		return antiriciclaggioFlgVerificaDisciplinaPrassiSettore;
	}

	public void setAntiriciclaggioFlgVerificaDisciplinaPrassiSettore(
			String antiriciclaggioFlgVerificaDisciplinaPrassiSettore) {
		this.antiriciclaggioFlgVerificaDisciplinaPrassiSettore = antiriciclaggioFlgVerificaDisciplinaPrassiSettore;
	}

	public String getAntiriciclaggioFlgRisultatoNonInadempiente() {
		return antiriciclaggioFlgRisultatoNonInadempiente;
	}

	public void setAntiriciclaggioFlgRisultatoNonInadempiente(String antiriciclaggioFlgRisultatoNonInadempiente) {
		this.antiriciclaggioFlgRisultatoNonInadempiente = antiriciclaggioFlgRisultatoNonInadempiente;
	}

	public String getCigCupFlgApplicabillita() {
		return cigCupFlgApplicabillita;
	}

	public void setCigCupFlgApplicabillita(String cigCupFlgApplicabillita) {
		this.cigCupFlgApplicabillita = cigCupFlgApplicabillita;
	}

	public String getCigCupSmartCig() {
		return cigCupSmartCig;
	}

	public void setCigCupSmartCig(String cigCupSmartCig) {
		this.cigCupSmartCig = cigCupSmartCig;
	}

	public String getCigCupCig() {
		return cigCupCig;
	}

	public void setCigCupCig(String cigCupCig) {
		this.cigCupCig = cigCupCig;
	}

	public String getCigCupFlgAcquisitoPerfezionato() {
		return cigCupFlgAcquisitoPerfezionato;
	}

	public void setCigCupFlgAcquisitoPerfezionato(String cigCupFlgAcquisitoPerfezionato) {
		this.cigCupFlgAcquisitoPerfezionato = cigCupFlgAcquisitoPerfezionato;
	}

	public String getCigCupCup() {
		return cigCupCup;
	}

	public void setCigCupCup(String cigCupCup) {
		this.cigCupCup = cigCupCup;
	}

	public String getCigCupMotiviEsclusione() {
		return cigCupMotiviEsclusione;
	}

	public void setCigCupMotiviEsclusione(String cigCupMotiviEsclusione) {
		this.cigCupMotiviEsclusione = cigCupMotiviEsclusione;
	}

	public String getCigCupUltInf() {
		return cigCupUltInf;
	}

	public void setCigCupUltInf(String cigCupUltInf) {
		this.cigCupUltInf = cigCupUltInf;
	}

	public String getProgressivoRegPcc() {
		return progressivoRegPcc;
	}

	public void setProgressivoRegPcc(String progressivoRegPcc) {
		this.progressivoRegPcc = progressivoRegPcc;
	}

	public String getTerminiPagamentoGgScadenza() {
		return terminiPagamentoGgScadenza;
	}

	public void setTerminiPagamentoGgScadenza(String terminiPagamentoGgScadenza) {
		this.terminiPagamentoGgScadenza = terminiPagamentoGgScadenza;
	}

	public String getTerminiPagamentoDataDecorrenza() {
		return terminiPagamentoDataDecorrenza;
	}

	public void setTerminiPagamentoDataDecorrenza(String terminiPagamentoDataDecorrenza) {
		this.terminiPagamentoDataDecorrenza = terminiPagamentoDataDecorrenza;
	}

	public String getTerminiPagamentoDataScadenza() {
		return terminiPagamentoDataScadenza;
	}

	public void setTerminiPagamentoDataScadenza(String terminiPagamentoDataScadenza) {
		this.terminiPagamentoDataScadenza = terminiPagamentoDataScadenza;
	}

	public String getTerminiPagamentoMotiviInterruzioneTermini() {
		return terminiPagamentoMotiviInterruzioneTermini;
	}

	public void setTerminiPagamentoMotiviInterruzioneTermini(String terminiPagamentoMotiviInterruzioneTermini) {
		this.terminiPagamentoMotiviInterruzioneTermini = terminiPagamentoMotiviInterruzioneTermini;
	}

	public String getTerminiPagamentoDataInizioSospensione() {
		return terminiPagamentoDataInizioSospensione;
	}

	public void setTerminiPagamentoDataInizioSospensione(String terminiPagamentoDataInizioSospensione) {
		this.terminiPagamentoDataInizioSospensione = terminiPagamentoDataInizioSospensione;
	}

	public String getTerminiPagamentoMotiviEsclusioneCalcoloTmp() {
		return terminiPagamentoMotiviEsclusioneCalcoloTmp;
	}

	public void setTerminiPagamentoMotiviEsclusioneCalcoloTmp(String terminiPagamentoMotiviEsclusioneCalcoloTmp) {
		this.terminiPagamentoMotiviEsclusioneCalcoloTmp = terminiPagamentoMotiviEsclusioneCalcoloTmp;
	}

	public String getImputazioneContabileCdc() {
		return imputazioneContabileCdc;
	}

	public void setImputazioneContabileCdc(String imputazioneContabileCdc) {
		this.imputazioneContabileCdc = imputazioneContabileCdc;
	}

	public String getImputazioneContabileImporto() {
		return imputazioneContabileImporto;
	}

	public void setImputazioneContabileImporto(String imputazioneContabileImporto) {
		this.imputazioneContabileImporto = imputazioneContabileImporto;
	}

	public String getImputazioneContabileMissione() {
		return imputazioneContabileMissione;
	}

	public void setImputazioneContabileMissione(String imputazioneContabileMissione) {
		this.imputazioneContabileMissione = imputazioneContabileMissione;
	}

	public String getImputazioneContabileProgramma() {
		return imputazioneContabileProgramma;
	}

	public void setImputazioneContabileProgramma(String imputazioneContabileProgramma) {
		this.imputazioneContabileProgramma = imputazioneContabileProgramma;
	}

	public String getImputazioneContabileTitolo() {
		return imputazioneContabileTitolo;
	}

	public void setImputazioneContabileTitolo(String imputazioneContabileTitolo) {
		this.imputazioneContabileTitolo = imputazioneContabileTitolo;
	}

	public String getImputazioneContabileRifBilancio() {
		return imputazioneContabileRifBilancio;
	}

	public void setImputazioneContabileRifBilancio(String imputazioneContabileRifBilancio) {
		this.imputazioneContabileRifBilancio = imputazioneContabileRifBilancio;
	}

	public String getImputazioneContabileMacroaggregato() {
		return imputazioneContabileMacroaggregato;
	}

	public void setImputazioneContabileMacroaggregato(String imputazioneContabileMacroaggregato) {
		this.imputazioneContabileMacroaggregato = imputazioneContabileMacroaggregato;
	}

	public String getImputazioneContabileCapitolo() {
		return imputazioneContabileCapitolo;
	}

	public void setImputazioneContabileCapitolo(String imputazioneContabileCapitolo) {
		this.imputazioneContabileCapitolo = imputazioneContabileCapitolo;
	}

	public String getImputazioneContabileImpegno() {
		return imputazioneContabileImpegno;
	}

	public void setImputazioneContabileImpegno(String imputazioneContabileImpegno) {
		this.imputazioneContabileImpegno = imputazioneContabileImpegno;
	}

	public String getImputazioneContabileCodSiope() {
		return imputazioneContabileCodSiope;
	}

	public void setImputazioneContabileCodSiope(String imputazioneContabileCodSiope) {
		this.imputazioneContabileCodSiope = imputazioneContabileCodSiope;
	}

	public String getSpesaEntrataFlgSpesaCorrEntrata() {
		return spesaEntrataFlgSpesaCorrEntrata;
	}

	public void setSpesaEntrataFlgSpesaCorrEntrata(String spesaEntrataFlgSpesaCorrEntrata) {
		this.spesaEntrataFlgSpesaCorrEntrata = spesaEntrataFlgSpesaCorrEntrata;
	}

	public String getSpesaEntrataFlgRispCronoprogramma() {
		return spesaEntrataFlgRispCronoprogramma;
	}

	public void setSpesaEntrataFlgRispCronoprogramma(String spesaEntrataFlgRispCronoprogramma) {
		this.spesaEntrataFlgRispCronoprogramma = spesaEntrataFlgRispCronoprogramma;
	}

	public String getSpesaEntrataRifEntrata() {
		return spesaEntrataRifEntrata;
	}

	public void setSpesaEntrataRifEntrata(String spesaEntrataRifEntrata) {
		this.spesaEntrataRifEntrata = spesaEntrataRifEntrata;
	}

	public String getSpesaEntrataDetNum() {
		return spesaEntrataDetNum;
	}

	public void setSpesaEntrataDetNum(String spesaEntrataDetNum) {
		this.spesaEntrataDetNum = spesaEntrataDetNum;
	}

	public String getSpesaEntrataDetAccertamento() {
		return spesaEntrataDetAccertamento;
	}

	public void setSpesaEntrataDetAccertamento(String spesaEntrataDetAccertamento) {
		this.spesaEntrataDetAccertamento = spesaEntrataDetAccertamento;
	}

	public String getSpesaEntrataCapitolo() {
		return spesaEntrataCapitolo;
	}

	public void setSpesaEntrataCapitolo(String spesaEntrataCapitolo) {
		this.spesaEntrataCapitolo = spesaEntrataCapitolo;
	}

	public String getSpesaEntrataReversale() {
		return spesaEntrataReversale;
	}

	public void setSpesaEntrataReversale(String spesaEntrataReversale) {
		this.spesaEntrataReversale = spesaEntrataReversale;
	}

	public String getSpesaEntrataImportoSomministrato() {
		return spesaEntrataImportoSomministrato;
	}

	public void setSpesaEntrataImportoSomministrato(String spesaEntrataImportoSomministrato) {
		this.spesaEntrataImportoSomministrato = spesaEntrataImportoSomministrato;
	}

	public String getSpesaEntrataImportDaSomministrare() {
		return spesaEntrataImportDaSomministrare;
	}

	public void setSpesaEntrataImportDaSomministrare(String spesaEntrataImportDaSomministrare) {
		this.spesaEntrataImportDaSomministrare = spesaEntrataImportDaSomministrare;
	}

	public String getSpesaEntrataModTempOp() {
		return spesaEntrataModTempOp;
	}

	public void setSpesaEntrataModTempOp(String spesaEntrataModTempOp) {
		this.spesaEntrataModTempOp = spesaEntrataModTempOp;
	}

	public String getDisimpegnoImporto() {
		return disimpegnoImporto;
	}

	public void setDisimpegnoImporto(String disimpegnoImporto) {
		this.disimpegnoImporto = disimpegnoImporto;
	}

	public String getDisimpegnoImpegnoAnno() {
		return disimpegnoImpegnoAnno;
	}

	public void setDisimpegnoImpegnoAnno(String disimpegnoImpegnoAnno) {
		this.disimpegnoImpegnoAnno = disimpegnoImpegnoAnno;
	}

	public String getDisimpegnoSub() {
		return disimpegnoSub;
	}

	public void setDisimpegnoSub(String disimpegnoSub) {
		this.disimpegnoSub = disimpegnoSub;
	}

	public String getDisimpegnoCapitolo() {
		return disimpegnoCapitolo;
	}

	public void setDisimpegnoCapitolo(String disimpegnoCapitolo) {
		this.disimpegnoCapitolo = disimpegnoCapitolo;
	}

	public String getLiquidazioneCompensiProgettualitaFlgLiq() {
		return liquidazioneCompensiProgettualitaFlgLiq;
	}

	public void setLiquidazioneCompensiProgettualitaFlgLiq(String liquidazioneCompensiProgettualitaFlgLiq) {
		this.liquidazioneCompensiProgettualitaFlgLiq = liquidazioneCompensiProgettualitaFlgLiq;
	}

	public String getLiquidazioneCompensiProgettualitaFlgVerOdv() {
		return liquidazioneCompensiProgettualitaFlgVerOdv;
	}

	public void setLiquidazioneCompensiProgettualitaFlgVerOdv(String liquidazioneCompensiProgettualitaFlgVerOdv) {
		this.liquidazioneCompensiProgettualitaFlgVerOdv = liquidazioneCompensiProgettualitaFlgVerOdv;
	}

	public String getSpesaTitoloBilancioTipoFinanziamento() {
		return spesaTitoloBilancioTipoFinanziamento;
	}

	public void setSpesaTitoloBilancioTipoFinanziamento(String spesaTitoloBilancioTipoFinanziamento) {
		this.spesaTitoloBilancioTipoFinanziamento = spesaTitoloBilancioTipoFinanziamento;
	}

	public String getSpesaTitoloBilancioBuonoCarico() {
		return spesaTitoloBilancioBuonoCarico;
	}

	public void setSpesaTitoloBilancioBuonoCarico(String spesaTitoloBilancioBuonoCarico) {
		this.spesaTitoloBilancioBuonoCarico = spesaTitoloBilancioBuonoCarico;
	}

	public String getSpesaTitoloBilancioRitenuta() {
		return spesaTitoloBilancioRitenuta;
	}

	public void setSpesaTitoloBilancioRitenuta(String spesaTitoloBilancioRitenuta) {
		this.spesaTitoloBilancioRitenuta = spesaTitoloBilancioRitenuta;
	}

	public String getSpesaTitoloBilancioSvincolo() {
		return spesaTitoloBilancioSvincolo;
	}

	public void setSpesaTitoloBilancioSvincolo(String spesaTitoloBilancioSvincolo) {
		this.spesaTitoloBilancioSvincolo = spesaTitoloBilancioSvincolo;
	}

	public String getSpesaTitoloBilancioFlgEffPatrImmEnte() {
		return spesaTitoloBilancioFlgEffPatrImmEnte;
	}

	public void setSpesaTitoloBilancioFlgEffPatrImmEnte(String spesaTitoloBilancioFlgEffPatrImmEnte) {
		this.spesaTitoloBilancioFlgEffPatrImmEnte = spesaTitoloBilancioFlgEffPatrImmEnte;
	}

	public String getSpesaTitoloBilancioCertPag() {
		return spesaTitoloBilancioCertPag;
	}

	public void setSpesaTitoloBilancioCertPag(String spesaTitoloBilancioCertPag) {
		this.spesaTitoloBilancioCertPag = spesaTitoloBilancioCertPag;
	}

	public String getSpesaTitoloBilancioAltro() {
		return spesaTitoloBilancioAltro;
	}

	public void setSpesaTitoloBilancioAltro(String spesaTitoloBilancioAltro) {
		this.spesaTitoloBilancioAltro = spesaTitoloBilancioAltro;
	}
}
