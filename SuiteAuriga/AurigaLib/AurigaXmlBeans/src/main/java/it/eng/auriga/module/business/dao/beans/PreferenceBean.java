/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;
import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean for capturing grid preference
 * 
 * @author Mattia Zanin
 *
 */

@XmlRootElement
public class PreferenceBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 5900110905504295012L;
	
	// 1) USERID della T_USER_PREFERENCES
	// 2) PREF_NAME della T_USER_PREFERENCES
	// 3) SETTING_TIME della T_USER_PREFERENCES nel formato GG/MM/AAAA HH24:MI:SS
	// 4) ID_UO della T_USER_PREFERENCES
	// 5) FLG_VISIB_SOTTOUO della T_USER_PREFERENCES (valori 1/NULL)
	// 6) FLG_GEST_SOTTOUO della T_USER_PREFERENCES (valori 1/NULL)
	// 7) TIPO_PREF della T_USER_PREFERENCES
	// 10) FLG_ABIL_DEL della T_USER_PREFERENCES (valori 1/NULL)
	// 11) VALUE della T_USER_PREFERENCES
	
	@NumeroColonna(numero = "1")
	private String userid;
	@NumeroColonna(numero = "2")
	private String prefName;
	@NumeroColonna(numero = "3")
	@TipoData(tipo = Tipo.DATA)
	private Date settingTime;
	@NumeroColonna(numero = "4")
	private BigDecimal idUo;
	@NumeroColonna(numero = "5")
	private Boolean flgVisibSottoUo;
	@NumeroColonna(numero = "6")
	private Boolean flgGestSottoUo;
	@NumeroColonna(numero = "7")
	private String tipoPref;
	@NumeroColonna(numero = "10")
	private Boolean flgAbilDel;
	@NumeroColonna(numero = "11")
	private String value;
	
	private String prefKey;
	private String key;
	private String displayValue;	
	private Boolean escludiPrefPublic;	
	
	public void setUserid(String userid) {
		this.userid = userid;
		this.getUpdatedProperties().add("userid");
	}

	public String getUserid() {
		return userid;
	}

	public void setPrefName(String prefName) {
		this.prefName = prefName;
		this.getUpdatedProperties().add("prefName");
	}

	public String getPrefName() {
		return prefName;
	}

	public void setSettingTime(Date settingTime) {
		this.settingTime = settingTime;
		this.getUpdatedProperties().add("settingTime");
	}

	public Date getSettingTime() {
		return settingTime;
	}

	public void setIdUo(BigDecimal idUo) {
		this.idUo = idUo;
		this.getUpdatedProperties().add("idUo");
	}

	public BigDecimal getIdUo() {
		return idUo;
	}

	public void setFlgVisibSottoUo(Boolean flgVisibSottoUo) {
		this.flgVisibSottoUo = flgVisibSottoUo;
		this.getUpdatedProperties().add("flgVisibSottoUo");
	}

	public Boolean getFlgVisibSottoUo() {
		return flgVisibSottoUo;
	}

	public void setFlgGestSottoUo(Boolean flgGestSottoUo) {
		this.flgGestSottoUo = flgGestSottoUo;
		this.getUpdatedProperties().add("flgGestSottoUo");
	}

	public Boolean getFlgGestSottoUo() {
		return flgGestSottoUo;
	}
	
	public void setTipoPref(String tipoPref) {
		this.tipoPref = tipoPref;
		this.getUpdatedProperties().add("tipoPref");
	}

	public String getTipoPref() {
		return tipoPref;
	}
	
	public void setFlgAbilDel(Boolean flgAbilDel) {
		this.flgAbilDel = flgAbilDel;
		this.getUpdatedProperties().add("flgAbilDel");
	}

	public Boolean getFlgAbilDel() {
		return flgAbilDel;
	}

	public void setValue(String value) {
		this.value = value;
		this.getUpdatedProperties().add("value");
	}

	public String getValue() {
		return value;
	}

	public void setPrefKey(String prefKey) {
		this.prefKey = prefKey;
		this.getUpdatedProperties().add("prefKey");
	}

	public String getPrefKey() {
		return prefKey;
	}
	
	public void setKey(String key) {
		this.key = key;
		this.getUpdatedProperties().add("key");
	}

	public String getKey() {
		return key;
	}
	
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
		this.getUpdatedProperties().add("displayValue");
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public Boolean getEscludiPrefPublic() {
		return escludiPrefPublic;
	}

	public void setEscludiPrefPublic(Boolean escludiPrefPublic) {
		this.escludiPrefPublic = escludiPrefPublic;
	}

}
