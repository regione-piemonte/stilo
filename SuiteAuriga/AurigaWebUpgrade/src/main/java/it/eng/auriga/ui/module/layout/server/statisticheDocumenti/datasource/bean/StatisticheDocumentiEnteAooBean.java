/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;


public class StatisticheDocumentiEnteAooBean {

		
	@NumeroColonna(numero="1")
	private String idSoggRubricaEnteAoo;

	@NumeroColonna(numero="3")
	private String descrizioneRubricaEnteAoo;
	
	@NumeroColonna(numero="4")
	private String descrizioneEnteAoo;

	@NumeroColonna(numero="5")
	private String idEnteAoo;
	
	@NumeroColonna(numero="6")
	private String dtNascitaEnteAoo;

	
	public String getIdEnteAoo() {
		return idEnteAoo;
	}

	public void setIdEnteAoo(String idEnteAoo) {
		this.idEnteAoo = idEnteAoo;
	}

	public String getDescrizioneEnteAoo() {
		return descrizioneEnteAoo;
	}

	public void setDescrizioneEnteAoo(String descrizioneEnteAoo) {
		this.descrizioneEnteAoo = descrizioneEnteAoo;
	}

	public String getIdSoggRubricaEnteAoo() {
		return idSoggRubricaEnteAoo;
	}

	public void setIdSoggRubricaEnteAoo(String idSoggRubricaEnteAoo) {
		this.idSoggRubricaEnteAoo = idSoggRubricaEnteAoo;
	}

	public String getDescrizioneRubricaEnteAoo() {
		return descrizioneRubricaEnteAoo;
	}

	public void setDescrizioneRubricaEnteAoo(String descrizioneRubricaEnteAoo) {
		this.descrizioneRubricaEnteAoo = descrizioneRubricaEnteAoo;
	}

	public String getDtNascitaEnteAoo() {
		return dtNascitaEnteAoo;
	}

	public void setDtNascitaEnteAoo(String dtNascitaEnteAoo) {
		this.dtNascitaEnteAoo = dtNascitaEnteAoo;
	}
	
	
}
