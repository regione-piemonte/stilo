/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.util.List;

public class AttributiDinamiciSoggettiXmlBean {	

	@XmlVariabile(nome = "BENEFICIARIO_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String BENEFICIARIO_CLIENTE;
	
	@XmlVariabile(nome = "COD_GRUPPO_RIF", tipo=TipoVariabile.SEMPLICE)
	private String COD_GRUPPO_RIF;

	@XmlVariabile(nome = "ISTITUTO_FINANZ_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String ISTITUTO_FINANZ_CLIENTE;

	@XmlVariabile(nome = "IBAN_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String IBAN_CLIENTE;

	@XmlVariabile(nome = "ABI_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String ABI_CLIENTE;

	@XmlVariabile(nome = "CAB_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String CAB_CLIENTE;

	@XmlVariabile(nome = "RS_VENT_CORR", tipo=TipoVariabile.SEMPLICE)
	private String RS_VENT_CORR;

	@XmlVariabile(nome = "RS_PROV_REA", tipo=TipoVariabile.SEMPLICE)
	private String RS_PROV_REA;

	@XmlVariabile(nome = "RS_PRGR_REA", tipo=TipoVariabile.SEMPLICE)
	private String RS_PRGR_REA;

	@XmlVariabile(nome = "RS_NRO_REA", tipo=TipoVariabile.SEMPLICE)
	private String RS_NRO_REA;

	@XmlVariabile(nome = "RS_NOTE", tipo=TipoVariabile.SEMPLICE)
	private String RS_NOTE;

	@XmlVariabile(nome = "RS_ECC_REQ_DIM", tipo=TipoVariabile.SEMPLICE)
	private String RS_ECC_REQ_DIM;

	
	@XmlVariabile(nome = "LISTA_BA_CLIENTE", tipo=TipoVariabile.LISTA)
	private List<BillingAccountBean> LISTA_BA_CLIENTE;
	
	@XmlVariabile(nome = "CID_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String CID_CLIENTE;
	
	@XmlVariabile(nome = "CIG_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String CIG_CLIENTE;
	
	@XmlVariabile(nome = "CUP_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String CUP_CLIENTE;
	
	
	@XmlVariabile(nome = "ODA_NR_CONTRATTO_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String ODA_NR_CONTRATTO_CLIENTE;
	
	@XmlVariabile(nome = "ODA_CIG_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String ODA_CIG_CLIENTE;
	
	@XmlVariabile(nome = "ODA_CUP_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String ODA_CUP_CLIENTE;
	
	@XmlVariabile(nome = "ODA_RIF_AMM_INPS_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String ODA_RIF_AMM_INPS_CLIENTE;
	
	
	@XmlVariabile(nome = "FLG_TIPO_ID_FISCALE_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String FLG_TIPO_ID_FISCALE_CLIENTE;
	
	@XmlVariabile(nome = "POS_FIN_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String POS_FIN_CLIENTE;

	@XmlVariabile(nome = "ANN_FIN_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String ANN_FIN_CLIENTE;

	@XmlVariabile(nome = "CID_APPL_SOCIETA", tipo=TipoVariabile.SEMPLICE)
	private String CID_APPL_SOCIETA;
	
	@XmlVariabile(nome = "ESIGIBILITA_IVA_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String ESIGIBILITA_IVA_CLIENTE;


	@XmlVariabile(nome = "FLG_SEGNO_IMPORTI_CLIENTE", tipo=TipoVariabile.SEMPLICE)
	private String FLG_SEGNO_IMPORTI_CLIENTE;

	@XmlVariabile(nome = "PUNTO_VENDITA_RIF", tipo=TipoVariabile.SEMPLICE)
	private String PUNTO_VENDITA_RIF;

	@XmlVariabile(nome = "PEC", tipo=TipoVariabile.SEMPLICE)
	private String PEC;
	
	public String getCID_CLIENTE() {
		return CID_CLIENTE;
	}

	public void setCID_CLIENTE(String cID_CLIENTE) {
		CID_CLIENTE = cID_CLIENTE;
	}

	
	public String getRS_VENT_CORR() {
		return RS_VENT_CORR;
	}

	public String getRS_PROV_REA() {
		return RS_PROV_REA;
	}

	public String getRS_PRGR_REA() {
		return RS_PRGR_REA;
	}

	public String getRS_NRO_REA() {
		return RS_NRO_REA;
	}

	public String getRS_NOTE() {
		return RS_NOTE;
	}

	public String getRS_ECC_REQ_DIM() {
		return RS_ECC_REQ_DIM;
	}

	public void setRS_VENT_CORR(String rS_VENT_CORR) {
		RS_VENT_CORR = rS_VENT_CORR;
	}

	public void setRS_PROV_REA(String rS_PROV_REA) {
		RS_PROV_REA = rS_PROV_REA;
	}

	public void setRS_PRGR_REA(String rS_PRGR_REA) {
		RS_PRGR_REA = rS_PRGR_REA;
	}

	public void setRS_NRO_REA(String rS_NRO_REA) {
		RS_NRO_REA = rS_NRO_REA;
	}

	public void setRS_NOTE(String rS_NOTE) {
		RS_NOTE = rS_NOTE;
	}

	public void setRS_ECC_REQ_DIM(String rS_ECC_REQ_DIM) {
		RS_ECC_REQ_DIM = rS_ECC_REQ_DIM;
	}

	

	public String getODA_NR_CONTRATTO_CLIENTE() {
		return ODA_NR_CONTRATTO_CLIENTE;
	}

	public String getODA_CIG_CLIENTE() {
		return ODA_CIG_CLIENTE;
	}

	public String getODA_CUP_CLIENTE() {
		return ODA_CUP_CLIENTE;
	}

	public String getODA_RIF_AMM_INPS_CLIENTE() {
		return ODA_RIF_AMM_INPS_CLIENTE;
	}


	public void setODA_NR_CONTRATTO_CLIENTE(String oDA_NR_CONTRATTO_CLIENTE) {
		ODA_NR_CONTRATTO_CLIENTE = oDA_NR_CONTRATTO_CLIENTE;
	}

	public void setODA_CIG_CLIENTE(String oDA_CIG_CLIENTE) {
		ODA_CIG_CLIENTE = oDA_CIG_CLIENTE;
	}

	public void setODA_CUP_CLIENTE(String oDA_CUP_CLIENTE) {
		ODA_CUP_CLIENTE = oDA_CUP_CLIENTE;
	}

	public void setODA_RIF_AMM_INPS_CLIENTE(String oDA_RIF_AMM_INPS_CLIENTE) {
		ODA_RIF_AMM_INPS_CLIENTE = oDA_RIF_AMM_INPS_CLIENTE;
	}

	public String getFLG_TIPO_ID_FISCALE_CLIENTE() {
		return FLG_TIPO_ID_FISCALE_CLIENTE;
	}
	

	public void setFLG_TIPO_ID_FISCALE_CLIENTE(String fLG_TIPO_ID_FISCALE_CLIENTE) {
		FLG_TIPO_ID_FISCALE_CLIENTE = fLG_TIPO_ID_FISCALE_CLIENTE;
	}





	

	public String getPOS_FIN_CLIENTE() {
		return POS_FIN_CLIENTE;
	}

	public void setPOS_FIN_CLIENTE(String pOS_FIN_CLIENTE) {
		POS_FIN_CLIENTE = pOS_FIN_CLIENTE;
	}

	public String getANN_FIN_CLIENTE() {
		return ANN_FIN_CLIENTE;
	}

	public void setANN_FIN_CLIENTE(String aNN_FIN_CLIENTE) {
		ANN_FIN_CLIENTE = aNN_FIN_CLIENTE;
	}

	public String getESIGIBILITA_IVA_CLIENTE() {
		return ESIGIBILITA_IVA_CLIENTE;
	}

	public void setESIGIBILITA_IVA_CLIENTE(String eSIGIBILITA_IVA_CLIENTE) {
		ESIGIBILITA_IVA_CLIENTE = eSIGIBILITA_IVA_CLIENTE;
	}

	public String getFLG_SEGNO_IMPORTI_CLIENTE() {
		return FLG_SEGNO_IMPORTI_CLIENTE;
	}

	public void setFLG_SEGNO_IMPORTI_CLIENTE(String fLG_SEGNO_IMPORTI_CLIENTE) {
		FLG_SEGNO_IMPORTI_CLIENTE = fLG_SEGNO_IMPORTI_CLIENTE;
	}

	public String getBENEFICIARIO_CLIENTE() {
		return BENEFICIARIO_CLIENTE;
	}

	public void setBENEFICIARIO_CLIENTE(String bENEFICIARIO_CLIENTE) {
		BENEFICIARIO_CLIENTE = bENEFICIARIO_CLIENTE;
	}


	public String getIBAN_CLIENTE() {
		return IBAN_CLIENTE;
	}

	public void setIBAN_CLIENTE(String iBAN_CLIENTE) {
		IBAN_CLIENTE = iBAN_CLIENTE;
	}

	public String getABI_CLIENTE() {
		return ABI_CLIENTE;
	}

	public void setABI_CLIENTE(String aBI_CLIENTE) {
		ABI_CLIENTE = aBI_CLIENTE;
	}

	public String getCAB_CLIENTE() {
		return CAB_CLIENTE;
	}

	public void setCAB_CLIENTE(String cAB_CLIENTE) {
		CAB_CLIENTE = cAB_CLIENTE;
	}

	public String getISTITUTO_FINANZ_CLIENTE() {
		return ISTITUTO_FINANZ_CLIENTE;
	}

	public void setISTITUTO_FINANZ_CLIENTE(String iSTITUTO_FINANZ_CLIENTE) {
		ISTITUTO_FINANZ_CLIENTE = iSTITUTO_FINANZ_CLIENTE;
	}

	public String getCID_APPL_SOCIETA() {
		return CID_APPL_SOCIETA;
	}

	public void setCID_APPL_SOCIETA(String cID_APPL_SOCIETA) {
		CID_APPL_SOCIETA = cID_APPL_SOCIETA;
	}

	public List<BillingAccountBean> getLISTA_BA_CLIENTE() {
		return LISTA_BA_CLIENTE;
	}

	public void setLISTA_BA_CLIENTE(List<BillingAccountBean> lISTA_BA_CLIENTE) {
		LISTA_BA_CLIENTE = lISTA_BA_CLIENTE;
	}

	public String getCOD_GRUPPO_RIF() {
		return COD_GRUPPO_RIF;
	}

	public void setCOD_GRUPPO_RIF(String cOD_GRUPPO_RIF) {
		COD_GRUPPO_RIF = cOD_GRUPPO_RIF;
	}

	public String getPUNTO_VENDITA_RIF() {
		return PUNTO_VENDITA_RIF;
	}

	public void setPUNTO_VENDITA_RIF(String pUNTO_VENDITA_RIF) {
		PUNTO_VENDITA_RIF = pUNTO_VENDITA_RIF;
	}

	public String getCIG_CLIENTE() {
		return CIG_CLIENTE;
	}

	public void setCIG_CLIENTE(String cIG_CLIENTE) {
		CIG_CLIENTE = cIG_CLIENTE;
	}

	public String getCUP_CLIENTE() {
		return CUP_CLIENTE;
	}

	public void setCUP_CLIENTE(String cUP_CLIENTE) {
		CUP_CLIENTE = cUP_CLIENTE;
	}

	public String getPEC() {
		return PEC;
	}

	public void setPEC(String pEC) {
		PEC = pEC;
	}

	
}
