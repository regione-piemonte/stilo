/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreaModFatturaInBean extends CreaModDocumentoInBean {

	private static final long serialVersionUID = -6609881844566442763L;

	@XmlVariabile(nome = "FATT_NUMERO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String fattNumeroDoc;
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	@XmlVariabile(nome = "FATT_DATA_Doc", tipo = TipoVariabile.SEMPLICE)
	private Date fattDataDoc;
	@XmlVariabile(nome = "FATT_COD_DEST_Doc", tipo = TipoVariabile.SEMPLICE)
	private String fattCodDestDoc;
	@XmlVariabile(nome = "FATT_DENOM_DEST_Doc", tipo = TipoVariabile.SEMPLICE)
	private String fattDenomDestDoc;
	@XmlVariabile(nome = "FATT_ID_FISC_DEST_Doc", tipo = TipoVariabile.SEMPLICE)
	private String fattIdFiscDestDoc;
	@XmlVariabile(nome = "FATT_NRO_ODA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String fattNroOdaDoc;
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	@XmlVariabile(nome = "FATT_DT_ODA_Doc", tipo = TipoVariabile.SEMPLICE)
	private Date fattDtOdaDoc;
	@XmlVariabile(nome = "FATT_CUP_ODA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String fattCupOdaDoc;
	@XmlVariabile(nome = "FATT_CIG_ODA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String fattCigOdaDoc;
	@XmlVariabile(nome = "FATT_IMPORTO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String fattImportoDoc;
	@XmlVariabile(nome = "FATT_DIVISA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String fattDivisaDoc;
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	@XmlVariabile(nome = "FATT_SCAD_PAG_Doc", tipo = TipoVariabile.SEMPLICE)
	private Date fattScadPagDoc;
	@XmlVariabile(nome = "FATT_ID_TRASM_SDI_Doc", tipo = TipoVariabile.SEMPLICE)
	private String fattIdTrasmSdiDoc;
	@XmlVariabile(nome = "NOME_FILE_ORIG_Ud", tipo = TipoVariabile.SEMPLICE)
	private String nomeFileOrigUd;
	@XmlVariabile(nome = "FATT_DENOM_MITT_Doc", tipo = TipoVariabile.SEMPLICE)
	private String fattDenomMittDoc;
	@XmlVariabile(nome = "FATT_COD_FISC_MITT_Doc", tipo = TipoVariabile.SEMPLICE)
	private String fattCodFiscMittDoc;
	@XmlVariabile(nome = "FATT_PIVA_MITT_Doc", tipo = TipoVariabile.SEMPLICE)
	private String fattPIvaMittDoc;
	@XmlVariabile(nome = "ID_TRASM_FATT_Doc", tipo = TipoVariabile.SEMPLICE)
	private String idTrasmFattDoc;
	@XmlVariabile(nome = "FATT_ID_SDI_Doc", tipo = TipoVariabile.SEMPLICE)
	private String fattIdSdiDoc;
	@XmlVariabile(nome = "FLG_RECAPITATA_Ud", tipo = TipoVariabile.SEMPLICE)
	private String flgRecapitataDoc;
	@XmlVariabile(nome = "FATT_LOTTO_Doc", tipo = TipoVariabile.LISTA)
	private List<FatturaLotto> listaFattureLotto;
	@XmlVariabile(nome = "FATT_LOTTO_ALTRE_NUM_Doc", tipo = TipoVariabile.LISTA)
	private List<FatturaLottoAltreNum> listaFattureLottoAltreNum;
	@XmlVariabile(nome = "FATT_IVA_REVERSE_CHARGE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String fattIvaReverseChargeDoc;
	@XmlVariabile(nome = "FATT_ALIQ_IVA_REVERSE_CHARGE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String fattAliqIvaReverseChargeDoc;
	@XmlVariabile(nome = "CUP_Doc", tipo = TipoVariabile.SEMPLICE)
	private String cupDoc;
	@XmlVariabile(nome = "CIG_Doc", tipo = TipoVariabile.SEMPLICE)
	private String cigDoc;
	@XmlVariabile(nome = "FATT_RILEVANZA_IVA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String fattRilevanzaIvaDoc;
	@XmlVariabile(nome = "FATT_DESCRIZIONE_LINEA_Doc", tipo = TipoVariabile.LISTA)
	private List<FatturazioneDescrizioneLineaBean> fattDescrizioneLineaDoc;
	@XmlVariabile(nome = "FATT_TIPO_ID_FISC_MITT_Doc", tipo = TipoVariabile.SEMPLICE)
	private String fattTipoIdFiscMittDoc;
	@XmlVariabile(nome = "FATT_CID_ODA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String fattCidOdaDoc;
	@XmlVariabile(nome = "FATT_ESIGIBILITA_IVA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String fattEsigibilitaIvaDoc;
	@XmlVariabile(nome = "NUM_PROT_REG_EXT_Doc", tipo = TipoVariabile.SEMPLICE)
	private String numeroProtocolloRegistrazioneExt;
	@XmlVariabile(nome = "DATA_PROT_REG_EXT_Doc", tipo = TipoVariabile.SEMPLICE)
	private Date dataProtocolloRegistrazioneExt;
	@XmlVariabile(nome = "STATO_REG_EXT_Doc", tipo = TipoVariabile.SEMPLICE)
	private String statoRegistrazioneExt;
	@XmlVariabile(nome = "FATT_CAUSALE_PA_Doc", tipo = TipoVariabile.LISTA)
	private List<FatturaCausale> causali;
	@XmlVariabile(nome = "RIF_AMM_CED_PREST_Doc", tipo = TipoVariabile.SEMPLICE)
	private String rifAmmCedPrest;


	public String getFattNumeroDoc() {
		return fattNumeroDoc;
	}

	public void setFattNumeroDoc(String fattNumeroDoc) {
		this.fattNumeroDoc = fattNumeroDoc;
	}

	public Date getFattDataDoc() {
		return fattDataDoc;
	}

	public void setFattDataDoc(Date fattDataDoc) {
		this.fattDataDoc = fattDataDoc;
	}

	public String getFattCodDestDoc() {
		return fattCodDestDoc;
	}

	public void setFattCodDestDoc(String fattCodDestDoc) {
		this.fattCodDestDoc = fattCodDestDoc;
	}

	public String getFattDenomDestDoc() {
		return fattDenomDestDoc;
	}

	public void setFattDenomDestDoc(String fattDenomDestDoc) {
		this.fattDenomDestDoc = fattDenomDestDoc;
	}

	public String getFattIdFiscDestDoc() {
		return fattIdFiscDestDoc;
	}

	public void setFattIdFiscDestDoc(String fattIdFiscDestDoc) {
		this.fattIdFiscDestDoc = fattIdFiscDestDoc;
	}

	public String getFattNroOdaDoc() {
		return fattNroOdaDoc;
	}

	public void setFattNroOdaDoc(String fattNroOdaDoc) {
		this.fattNroOdaDoc = fattNroOdaDoc;
	}

	public Date getFattDtOdaDoc() {
		return fattDtOdaDoc;
	}

	public void setFattDtOdaDoc(Date fattDtOdaDoc) {
		this.fattDtOdaDoc = fattDtOdaDoc;
	}

	public String getFattCupOdaDoc() {
		return fattCupOdaDoc;
	}

	public void setFattCupOdaDoc(String fattCupOdaDoc) {
		this.fattCupOdaDoc = fattCupOdaDoc;
	}

	public String getFattCigOdaDoc() {
		return fattCigOdaDoc;
	}

	public void setFattCigOdaDoc(String fattCigOdaDoc) {
		this.fattCigOdaDoc = fattCigOdaDoc;
	}

	public String getFattImportoDoc() {
		return fattImportoDoc;
	}

	public void setFattImportoDoc(String fattImportoDoc) {
		this.fattImportoDoc = fattImportoDoc;
	}

	public String getFattDivisaDoc() {
		return fattDivisaDoc;
	}

	public void setFattDivisaDoc(String fattDivisaDoc) {
		this.fattDivisaDoc = fattDivisaDoc;
	}

	public Date getFattScadPagDoc() {
		return fattScadPagDoc;
	}

	public void setFattScadPagDoc(Date fattScadPagDoc) {
		this.fattScadPagDoc = fattScadPagDoc;
	}

	public String getFattIdTrasmSdiDoc() {
		return fattIdTrasmSdiDoc;
	}

	public void setFattIdTrasmSdiDoc(String fattIdTrasmSdiDoc) {
		this.fattIdTrasmSdiDoc = fattIdTrasmSdiDoc;
	}

	public String getNomeFileOrigUd() {
		return nomeFileOrigUd;
	}

	public void setNomeFileOrigUd(String nomeFileOrigUd) {
		this.nomeFileOrigUd = nomeFileOrigUd;
	}

	public String getFattDenomMittDoc() {
		return fattDenomMittDoc;
	}

	public void setFattDenomMittDoc(String fattDenomMittDoc) {
		this.fattDenomMittDoc = fattDenomMittDoc;
	}

	public String getFattCodFiscMittDoc() {
		return fattCodFiscMittDoc;
	}

	public void setFattCodFiscMittDoc(String fattCodFiscMittDoc) {
		this.fattCodFiscMittDoc = fattCodFiscMittDoc;
	}

	public String getFattPIvaMittDoc() {
		return fattPIvaMittDoc;
	}

	public void setFattPIvaMittDoc(String fattPIvaMittDoc) {
		this.fattPIvaMittDoc = fattPIvaMittDoc;
	}

	public String getIdTrasmFattDoc() {
		return idTrasmFattDoc;
	}

	public void setIdTrasmFattDoc(String idTrasmFattDoc) {
		this.idTrasmFattDoc = idTrasmFattDoc;
	}

	public String getFattIdSdiDoc() {
		return fattIdSdiDoc;
	}

	public void setFattIdSdiDoc(String fattIdSdiDoc) {
		this.fattIdSdiDoc = fattIdSdiDoc;
	}

	public String getFlgRecapitataDoc() {
		return flgRecapitataDoc;
	}

	public void setFlgRecapitataDoc(String flgRecapitataDoc) {
		this.flgRecapitataDoc = flgRecapitataDoc;
	}

	public List<FatturaLotto> getListaFattureLotto() {
		return listaFattureLotto;
	}

	public void setListaFattureLotto(List<FatturaLotto> listaFattureLotto) {
		this.listaFattureLotto = listaFattureLotto;
	}

	public List<FatturaLottoAltreNum> getListaFattureLottoAltreNum() {
		return listaFattureLottoAltreNum;
	}

	public void setListaFattureLottoAltreNum(List<FatturaLottoAltreNum> listaFattureLottoAltreNum) {
		this.listaFattureLottoAltreNum = listaFattureLottoAltreNum;
	}

	public String getFattIvaReverseChargeDoc() {
		return fattIvaReverseChargeDoc;
	}

	public void setFattIvaReverseChargeDoc(String fattIvaReverseChargeDoc) {
		this.fattIvaReverseChargeDoc = fattIvaReverseChargeDoc;
	}

	public String getFattAliqIvaReverseChargeDoc() {
		return fattAliqIvaReverseChargeDoc;
	}

	public void setFattAliqIvaReverseChargeDoc(String fattAliqIvaReverseChargeDoc) {
		this.fattAliqIvaReverseChargeDoc = fattAliqIvaReverseChargeDoc;
	}

	public String getCupDoc() {
		return cupDoc;
	}

	public void setCupDoc(String cupDoc) {
		this.cupDoc = cupDoc;
	}

	public String getCigDoc() {
		return cigDoc;
	}

	public void setCigDoc(String cigDoc) {
		this.cigDoc = cigDoc;
	}

	public String getFattRilevanzaIvaDoc() {
		return fattRilevanzaIvaDoc;
	}

	public void setFattRilevanzaIvaDoc(String fattRilevanzaIvaDoc) {
		this.fattRilevanzaIvaDoc = fattRilevanzaIvaDoc;
	}

	public String getFattTipoIdFiscMittDoc() {
		return fattTipoIdFiscMittDoc;
	}

	public void setFattTipoIdFiscMittDoc(String fattTipoIdFiscMittDoc) {
		this.fattTipoIdFiscMittDoc = fattTipoIdFiscMittDoc;
	}

	public List<FatturazioneDescrizioneLineaBean> getFattDescrizioneLineaDoc() {
		return fattDescrizioneLineaDoc;
	}

	public void setFattDescrizioneLineaDoc(List<FatturazioneDescrizioneLineaBean> fattDescrizioneLineaDoc) {
		this.fattDescrizioneLineaDoc = fattDescrizioneLineaDoc;
	}

	public String getFattCidOdaDoc() {
		return fattCidOdaDoc;
	}

	public void setFattCidOdaDoc(String fattCidOdaDoc) {
		this.fattCidOdaDoc = fattCidOdaDoc;
	}

	public String getFattEsigibilitaIvaDoc() {
		return fattEsigibilitaIvaDoc;
	}

	public void setFattEsigibilitaIvaDoc(String fattEsigibilitaIvaDoc) {
		this.fattEsigibilitaIvaDoc = fattEsigibilitaIvaDoc;
	}

	public String getNumeroProtocolloRegistrazioneExt() {
		return numeroProtocolloRegistrazioneExt;
	}

	public void setNumeroProtocolloRegistrazioneExt(String numeroProtocolloRegistrazioneExt) {
		this.numeroProtocolloRegistrazioneExt = numeroProtocolloRegistrazioneExt;
	}

	public Date getDataProtocolloRegistrazioneExt() {
		return dataProtocolloRegistrazioneExt;
	}

	public void setDataProtocolloRegistrazioneExt(Date dataProtocolloRegistrazioneExt) {
		this.dataProtocolloRegistrazioneExt = dataProtocolloRegistrazioneExt;
	}

	public String getStatoRegistrazioneExt() {
		return statoRegistrazioneExt;
	}

	public void setStatoRegistrazioneExt(String statoRegistrazioneExt) {
		this.statoRegistrazioneExt = statoRegistrazioneExt;
	}

	public List<FatturaCausale> getCausali() {
		return causali;
	}

	public void setCausali(List<FatturaCausale> causali) {
		this.causali = causali;
	}

	public String getRifAmmCedPrest() {
		return rifAmmCedPrest;
	}

	public void setRifAmmCedPrest(String rifAmmCedPrest) {
		this.rifAmmCedPrest = rifAmmCedPrest;
	}

}
