/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.FileStoreBean;

public class MarcaturaFileStoreBean extends FileStoreBean {

	//FLG_TIMBRO_REG
	//TS_MARCA_TEMPORALE
	
	//@XmlVariabile(nome = "FLG_TIMBRO_REG_Ver", tipo = TipoVariabile.SEMPLICE)
	@XmlVariabile(nome="TS_MARCA_TEMPORALE_Ver", tipo = TipoVariabile.SEMPLICE)
	private String tsMarcaTemporaleVer;
	
	@XmlVariabile(nome="FLG_MARCA_TEMPORALE_APPOSTA_DA_AURIGA_Ver", tipo = TipoVariabile.SEMPLICE)
	private String flgMarcaTemporaleAurigaVer;
	
	@XmlVariabile(nome="TS_VERIFICA_MARCA_TEMP_DOC_EL_Ver", tipo = TipoVariabile.SEMPLICE)
	private String tsVerificaMarcaTemporaleVer;
	
	@XmlVariabile(nome="TIPO_MARCA_TEMPORALE_DOC_EL_Ver", tipo = TipoVariabile.SEMPLICE)
	private String tipoMarcaTemporaleVer;
	
	@XmlVariabile(nome="TIPO_BUSTA_CRITTOGRAFICA_DOC_EL_Ver", tipo = TipoVariabile.SEMPLICE)
	private String tipoBustaCrittograficaVer;
	
	@XmlVariabile(nome="INFO_VERIFICA_MARCA_TEMPORALE_DOC_EL_Ver", tipo = TipoVariabile.SEMPLICE)
	private String infoVerificaMarcaTemporaleVer;

	public String getTsMarcaTemporaleVer() {
		return tsMarcaTemporaleVer;
	}

	public void setTsMarcaTemporaleVer(String tsMarcaTemporaleVer) {
		this.tsMarcaTemporaleVer = tsMarcaTemporaleVer;
	}

	public String getFlgMarcaTemporaleAurigaVer() {
		return flgMarcaTemporaleAurigaVer;
	}

	public void setFlgMarcaTemporaleAurigaVer(String flgMarcaTemporaleAurigaVer) {
		this.flgMarcaTemporaleAurigaVer = flgMarcaTemporaleAurigaVer;
	}

	public String getTsVerificaMarcaTemporaleVer() {
		return tsVerificaMarcaTemporaleVer;
	}

	public void setTsVerificaMarcaTemporaleVer(String tsVerificaMarcaTemporaleVer) {
		this.tsVerificaMarcaTemporaleVer = tsVerificaMarcaTemporaleVer;
	}

	public String getTipoMarcaTemporaleVer() {
		return tipoMarcaTemporaleVer;
	}

	public void setTipoMarcaTemporaleVer(String tipoMarcaTemporaleVer) {
		this.tipoMarcaTemporaleVer = tipoMarcaTemporaleVer;
	}

	public String getTipoBustaCrittograficaVer() {
		return tipoBustaCrittograficaVer;
	}

	public void setTipoBustaCrittograficaVer(String tipoBustaCrittograficaVer) {
		this.tipoBustaCrittograficaVer = tipoBustaCrittograficaVer;
	}

	public String getInfoVerificaMarcaTemporaleVer() {
		return infoVerificaMarcaTemporaleVer;
	}

	public void setInfoVerificaMarcaTemporaleVer(String infoVerificaMarcaTemporaleVer) {
		this.infoVerificaMarcaTemporaleVer = infoVerificaMarcaTemporaleVer;
	}
	
	
	
}
