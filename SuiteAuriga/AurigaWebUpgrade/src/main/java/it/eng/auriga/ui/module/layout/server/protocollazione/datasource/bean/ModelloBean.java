/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

import java.math.BigDecimal;
import java.util.Date;

public class ModelloBean {

	// 1) USERID della T_USER_PREFERENCES
	// 2) PREF_NAME della T_USER_PREFERENCES
	// 3) SETTING_TIME della T_USER_PREFERENCES nel formato GG/MM/AAAA HH24:MI:SS
	// 4) ID_UO della T_USER_PREFERENCES
	// 5) FLG_VISIB_SOTTOUO della T_USER_PREFERENCES (valori 1/NULL)
	// 6) FLG_GEST_SOTTOUO della T_USER_PREFERENCES (valori 1/NULL)
	// 7) Tipo di preferenza: PR=Privata, UO = di UO, PB=Pubblica
	// 8) Livelli della UO
	// 9) Denominazione della UO
	// 10) Flag 1/0 che indica se preferenza/modello eliminabile dall'utente
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

	@NumeroColonna(numero = "8")
	private String codRapidoUo;

	@NumeroColonna(numero = "9")
	private String denominazioneUo;

	@NumeroColonna(numero = "10")
	private Boolean flgAbilDel;

	@NumeroColonna(numero = "11")
	private String value;

	private String prefKey;

	private String key;

	private String displayValue;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPrefName() {
		return prefName;
	}

	public void setPrefName(String prefName) {
		this.prefName = prefName;
	}

	public Date getSettingTime() {
		return settingTime;
	}

	public void setSettingTime(Date settingTime) {
		this.settingTime = settingTime;
	}

	public BigDecimal getIdUo() {
		return idUo;
	}

	public void setIdUo(BigDecimal idUo) {
		this.idUo = idUo;
	}

	public Boolean getFlgVisibSottoUo() {
		return flgVisibSottoUo;
	}

	public void setFlgVisibSottoUo(Boolean flgVisibSottoUo) {
		this.flgVisibSottoUo = flgVisibSottoUo;
	}

	public Boolean getFlgGestSottoUo() {
		return flgGestSottoUo;
	}

	public void setFlgGestSottoUo(Boolean flgGestSottoUo) {
		this.flgGestSottoUo = flgGestSottoUo;
	}

	public String getTipoPref() {
		return tipoPref;
	}

	public void setTipoPref(String tipoPref) {
		this.tipoPref = tipoPref;
	}

	public String getCodRapidoUo() {
		return codRapidoUo;
	}

	public void setCodRapidoUo(String codRapidoUo) {
		this.codRapidoUo = codRapidoUo;
	}

	public String getDenominazioneUo() {
		return denominazioneUo;
	}

	public void setDenominazioneUo(String denominazioneUo) {
		this.denominazioneUo = denominazioneUo;
	}

	public Boolean getFlgAbilDel() {
		return flgAbilDel;
	}

	public void setFlgAbilDel(Boolean flgAbilDel) {
		this.flgAbilDel = flgAbilDel;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPrefKey() {
		return prefKey;
	}

	public void setPrefKey(String prefKey) {
		this.prefKey = prefKey;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

}
