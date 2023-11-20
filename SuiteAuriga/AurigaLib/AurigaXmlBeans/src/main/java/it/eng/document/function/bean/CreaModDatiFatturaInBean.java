/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

@XmlRootElement
public class CreaModDatiFatturaInBean extends CreaModFatturaInBean{

	private static final long serialVersionUID = -939872847060861013L;


	@XmlVariabile(nome = "DF_DATA_TRASM_SDI_Doc", tipo = TipoVariabile.SEMPLICE)
	private Date dfDataFattTrasmSDI;

	@XmlVariabile(nome = "DF_TIPO_FATT_Doc", tipo = TipoVariabile.SEMPLICE)
	private String dfTipoFattSDI;
	
	@XmlVariabile(nome = "DF_FATT_DAL_Doc", tipo = TipoVariabile.SEMPLICE)
	private Date dfFattDal;
	
	@XmlVariabile(nome = "DF_FATT_AL_Doc", tipo = TipoVariabile.SEMPLICE)
	private Date dfFattAl;
	
	@XmlVariabile(nome = "DF_NUMERO_FATTURE_Doc", tipo = TipoVariabile.SEMPLICE)
	private Integer dfNumeroFatture;
	
	@XmlVariabile(nome = "DF_MSG_ERR_TRASM_SDI_Doc", tipo = TipoVariabile.LISTA)
	private List<String> dfMsgErrTrasmSDI;
	
	@XmlVariabile(nome = "DF_FATT_ID_SDI_Doc", tipo = TipoVariabile.SEMPLICE)
	private String dfFattIdSDI;

	public Date getDfDataFattTrasmSDI() {
		return dfDataFattTrasmSDI;
	}

	public void setDfDataFattTrasmSDI(Date dfDataFattTrasmSDI) {
		this.dfDataFattTrasmSDI = dfDataFattTrasmSDI;
	}

	public String getDfTipoFattSDI() {
		return dfTipoFattSDI;
	}

	public void setDfTipoFattSDI(String dfTipoFattSDI) {
		this.dfTipoFattSDI = dfTipoFattSDI;
	}

	public Date getDfFattDal() {
		return dfFattDal;
	}

	public void setDfFattDal(Date dfFattDal) {
		this.dfFattDal = dfFattDal;
	}

	public Date getDfFattAl() {
		return dfFattAl;
	}

	public void setDfFattAl(Date dfFattAl) {
		this.dfFattAl = dfFattAl;
	}

	public Integer getDfNumeroFatture() {
		return dfNumeroFatture;
	}

	public void setDfNumeroFatture(Integer dfNumeroFatture) {
		this.dfNumeroFatture = dfNumeroFatture;
	}

	public List<String> getDfMsgErrTrasmSDI() {
		return dfMsgErrTrasmSDI;
	}

	public void setDfMsgErrTrasmSDI(List<String> dfMsgErrTrasmSDI) {
		this.dfMsgErrTrasmSDI = dfMsgErrTrasmSDI;
	}

	public String getDfFattIdSDI() {
		return dfFattIdSDI;
	}

	public void setDfFattIdSDI(String dfFattIdSDI) {
		this.dfFattIdSDI = dfFattIdSDI;
	}
	
}
