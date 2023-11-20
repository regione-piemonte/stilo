/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

@XmlRootElement
public class TUserPreferencesBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -7341833444762147789L;

	private String prefKey;
	private String prefName;
	private String userid;
	private Date settingTime;
	private String value;
	private BigDecimal idUo;
	private Boolean flgVisibSottouo;
	private Boolean flgGestSottouo;

	public String getPrefKey() {
		return prefKey;
	}

	public void setPrefKey(String prefKey) {
		this.prefKey = prefKey;
	}

	public String getPrefName() {
		return prefName;
	}

	public void setPrefName(String prefName) {
		this.prefName = prefName;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getSettingTime() {
		return settingTime;
	}

	public void setSettingTime(Date settingTime) {
		this.settingTime = settingTime;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getFlgVisibSottouo() {
		return flgVisibSottouo;
	}

	public void setFlgVisibSottouo(Boolean flgVisibSottouo) {
		this.flgVisibSottouo = flgVisibSottouo;
	}

	public BigDecimal getIdUo() {
		return idUo;
	}

	public void setIdUo(BigDecimal idUo) {
		this.idUo = idUo;
	}

	public Boolean getFlgGestSottouo() {
		return flgGestSottouo;
	}

	public void setFlgGestSottouo(Boolean flgGestSottouo) {
		this.flgGestSottouo = flgGestSottouo;
	}

}