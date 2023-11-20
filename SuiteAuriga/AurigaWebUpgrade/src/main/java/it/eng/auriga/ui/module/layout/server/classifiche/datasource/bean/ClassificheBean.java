/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.titolario.datasource.bean.TitolarioBean;

public class ClassificheBean extends TitolarioBean
{
	private String  idUo;
	private String  classificheAbilitate;
	private String  denominazioneEstesaUO;
	private String  flgAbilATutti;
	

	public String getClassificheAbilitate() {
		return classificheAbilitate;
	}

	public void setClassificheAbilitate(String classificheAbilitate) {
		this.classificheAbilitate = classificheAbilitate;
	}

	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public String getDenominazioneEstesaUO() {
		return denominazioneEstesaUO;
	}

	public void setDenominazioneEstesaUO(String denominazioneEstesaUO) {
		this.denominazioneEstesaUO = denominazioneEstesaUO;
	}

	public String getFlgAbilATutti() {
		return flgAbilATutti;
	}

	public void setFlgAbilATutti(String flgAbilATutti) {
		this.flgAbilATutti = flgAbilATutti;
	}
	

}
