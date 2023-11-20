/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Questa classe contiene le costanti corrispondenti agli indici delle righe del file di import delle liquidazioni AVB (Bari)
 *
 */
public class ConstantsCampiExcelLiquidazioneAVB {
	
	/*********************************************************
	 * 		MACRO AREA 1: OGGETTO DELLA SPESA		         *
	 * *******************************************************/
	
	public static final int OGGETTOSPESA_NUM_DET_IMPEGNO = 2;
	public static final int OGGETTOSPESA_ALTRO_ATTO = 3;
	public static final int OGGETTOSPESA_FLG_ESECUTIVO = 4;
	public static final int OGGETTOSPESA_OGGETTO = 5;
	public static final int OGGETTOSPESA_FLG_CONFORME_REG_CONT = 6;
	public static final int OGGETTOSPESA_FLG_DEBITO_COMMERCIALE = 7;
	public static final int OGGETTOSPESA_FLG_PAGAMENTO_SOGG_ART_22_DLGS_33_2013 = 8;
	public static final int OGGETTOSPESA_FLG_VERIFICA_ANAC = 9;
	public static final int OGGETTOSPESA_FLG_PAGAMENTO_SOGG_ART_1_CC_125_129_L_124_2017 = 10;
	public static final int OGGETTOSPESA_FLG_SOGLIA_SUPERATA = 11;
	public static final int OGGETTOSPESA_FLG_DEBITO_FUORI_BILANCIO = 12;
	
	/**********************************************************
	 * 		MACRO AREA 2: BENEFICIARIO/PAGAMENTO			  *
	 * ********************************************************/
	
	public static final int BENEFICIARIOPAGAMENTO_BENEFICIARIO = 13;
	public static final int BENEFICIARIOPAGAMENTO_SEDE = 14;
	public static final int BENEFICIARIOPAGAMENTO_IVA = 15;
	public static final int BENEFICIARIOPAGAMENTO_COD_FISCALE = 16;
	public static final int BENEFICIARIOPAGAMENTO_FLG_CREDITO_CERTIFICATO = 17;
	public static final int BENEFICIARIOPAGAMENTO_FLG_CREDITO_CEDUTO = 18;
	public static final int BENEFICIARIOPAGAMENTO_SOGGETTO_QUIETANZA = 19;
	public static final int BENEFICIARIOPAGAMENTO_DATI_DOC_DEBITO = 20;
	public static final int BENEFICIARIOPAGAMENTO_NUMERO = 21;
	public static final int BENEFICIARIOPAGAMENTO_DATA = 22;
	public static final int BENEFICIARIOPAGAMENTO_IMPORTO = 23;
	public static final int BENEFICIARIOPAGAMENTO_IMPONIBILE = 24;
	public static final int BENEFICIARIOPAGAMENTO_IVA_IMPORTO = 25;
	public static final int BENEFICIARIOPAGAMENTO_FLG_RITENUTA_FISCALE = 26;
	public static final int BENEFICIARIOPAGAMENTO_IMPORTO_RITENUTA_FISCALE = 27;
	public static final int BENEFICIARIOPAGAMENTO_FLG_REGOLARITA = 28;
	public static final int BENEFICIARIOPAGAMENTO_MODALITA_PAGAMENTO = 29;
	public static final int BENEFICIARIOPAGAMENTO_BANCA = 30;
	public static final int BENEFICIARIOPAGAMENTO_IBAN = 31;
	public static final int BENEFICIARIOPAGAMENTO_RIF_VERBALE_ECONOMO = 32;
	public static final int BENEFICIARIOPAGAMENTO_NOTA_CREDITO = 33;
	public static final int BENEFICIARIOPAGAMENTO_TESTO_NOTA_CREDITO = 34;
	
	/********************************************
	 * 		MACRO AREA 3: REGIME IVA/BOLLO		*
	 * ******************************************/
	
	public static final int REGIMEIVABOLLO_ATTIVITA_IVA = 35;
	public static final int REGIMEIVABOLLO_TIPO_ATTIVITA = 36;
	public static final int REGIMEIVABOLLO_REGIME_IVA = 37;
	public static final int REGIMEIVABOLLO_ALTRO = 38;

	/*******************************
	 * 		MACRO AREA 4: DURC	   *
	 * *****************************/
	
	public static final int DURC_FLG_VERIFICA_REG_CONTRIBUTIVA = 39;
	public static final int DURC_FLG_REGOLARE_DURC = 40;
	public static final int DURC_FLG_ULTERIORE_VERIFICA = 41;
	public static final int DURC_INTERVENTO_SOSTITUTIVO = 42;

	/*****************************************************************
	 * 		MACRO AREA 5: VERIFICHE EX EQUITALIA - ANTIRICICLAGGIO   *
	 * ***************************************************************/
	
	public static final int ANTIRICICLAGGIO_FLG_VERIFICA_NON_INADEMPIENZA = 43;
	public static final int ANTIRICICLAGGIO_FLG_VERIFICA_DISCIPLINA_PRASSI_SETTORE = 44;
	public static final int ANTIRICICLAGGIO_FLG_RISULTATO_NON_INADEMPIENTE = 45;

	/********************************
	 * 		MACRO AREA 6: CIG/CUP   *
	 * ******************************/
	
	public static final int CIGCUP_FLG_APPLICABILITA = 46;
	public static final int CIGCUP_SMART_CIG = 47;
	public static final int CIGCUP_CIG = 48;
	public static final int CIGCUP_FLG_ACQUISITO_PERFEZIONATO = 49;
	public static final int CIGCUP_CUP = 50;
	public static final int CIGCUP_MOTIVI_ESCLUSIONE = 51;
	public static final int CIGCUP_ULT_INF = 52;

	/************************************************************
	 * 		MACRO AREA 7: PROGRESSIVO DI REGISTRAZIONE IN PCC   *
	 * **********************************************************/
	
	public static final int PROGRESSIVO_REG_PCC = 53;
	
	/*********************************************
	 * 		MACRO AREA 8: TERMINI DI PAGAMENTO   *
	 * *******************************************/
	
	public static final int TERMINIPAGAMENTO_GG_SCADENZA = 54;
	public static final int TERMINIPAGAMENTO_DATA_DECORRENZA = 55;
	public static final int TERMINIPAGAMENTO_DATA_SCADENZA = 56;
	public static final int TERMINIPAGAMENTO_MOTIVI_INTERRUZIONE_TERMINI = 57;
	public static final int TERMINIPAGAMENTO_DATA_INIZIO_SOSPENSIONE = 58;
	public static final int TERMINIPAGAMENTO_MOTIVI_ESCLUSIONE_CALCOLO_TMP = 59;
	
	/**********************************************
	 * 		MACRO AREA 9: IMPUTAZIONE CONTABILE   *
	 * ********************************************/
	
	public static final int IMPUTAZIONECONTABILE_CDC = 60;
	public static final int IMPUTAZIONECONTABILE_IMPORTO = 61;
	public static final int IMPUTAZIONECONTABILE_MISSIONE = 62;
	public static final int IMPUTAZIONECONTABILE_PROGRAMMA = 63;
	public static final int IMPUTAZIONECONTABILE_TITOLO = 64;
	public static final int IMPUTAZIONECONTABILE_RIF_BILANCIO = 65;
	public static final int IMPUTAZIONECONTABILE_MACROAGGREGATO = 66;
	public static final int IMPUTAZIONECONTABILE_CAPITOLO = 67;
	public static final int IMPUTAZIONECONTABILE_IMPEGNO = 68;
	public static final int IMPUTAZIONECONTABILE_COD_SIOPE = 69;
	
	
	/*****************************************************
	 * 		MACRO AREA 10: SPESA CORRELATA ALL ENTRATA   *
	 * ***************************************************/
	
	public static final int SPESAENTRATA_FLG_SPESA_CORR_ENTRATA = 70;
	public static final int SPESAENTRATA_FLG_RISP_CRONOPROGRAMMA = 71;
	public static final int SPESAENTRATA_RIF_ENTRATA = 72;
	public static final int SPESAENTRATA_DET_NUM = 73;
	public static final int SPESAENTRATA_DET_ACCERTAMENTO = 74;
	public static final int SPESAENTRATA_CAPITOLO = 75;
	public static final int SPESAENTRATA_REVERSALE = 76;
	public static final int SPESAENTRATA_IMPORTO_SOMMINISTRATO = 77;
	public static final int SPESAENTRATA_IMPORTO_DA_SOMMINISTARE = 78;
	public static final int SPESAENTRATA_MOD_TEMP_OP = 79;
	
	/************************************
	 * 		MACRO AREA 11: DISIMPEGNO   *
	 * **********************************/
	
	public static final int DISIMPEGNO_IMPORTO = 80;
	public static final int DISIMPEGNO_IMPEGNO_ANNO = 81;
	public static final int DISIMPEGNO_SUB = 82;
	public static final int DISIMPEGNO_CAPITOLO = 83;
	
	/*************************************************************************
	 * 		MACRO AREA 12: LIQUIDAZIONI DI COMPENSI LEGATI A PROGETTUALITA   *
	 * ***********************************************************************/
	
	public static final int LIQUIDAZIONICOMPENSIPROGETTUALITA_FLG_LIQ = 84;
	public static final int LIQUIDAZIONICOMPENSIPROGETTUALITA_FLG_VER_ODV = 85;
	
	
	/************************************************************************
	 * 		MACRO AREA 13: LIQUIDAZIONE SPESA SUL TITOLO II° DEL BILANCIO   *
	 * **********************************************************************/
	
	public static final int SPESATITOLOBILANCIO_TIPO_FINANZIAENTO = 86;
	public static final int SPESATITOLOBILANCIO_BUONO_CARICO = 87;
	public static final int SPESATITOLOBILANCIO_RITENUTA = 88;
	public static final int SPESATITOLOBILANCIO_SVINCOLO = 89;
	public static final int SPESATITOLOBILANCIO_FLG_EFF_PATR_IMM_ENTE = 90;
	public static final int SPESATITOLOBILANCIO_CERT_PAG = 91;
	public static final int SPESATITOLOBILANCIO_ALTRO = 92;
}
