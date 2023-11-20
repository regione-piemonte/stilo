/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class DocAssAUOBean {
    
	@NumeroColonna(numero="1")
	private String idUo;
	
	@NumeroColonna(numero="2")
	private String codRapido;
	
	@NumeroColonna(numero="3")
	private String denominazioneUo;
	
	@NumeroColonna(numero="4")
	private Boolean flgIncluseSottoUo;
	
	@NumeroColonna(numero="5")
	private Boolean flgIncluseScrivanie;
	
	@NumeroColonna(numero="6")
	private Boolean flgVisDocFascRis;
	

	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public String getDenominazioneUo() {
		return denominazioneUo;
	}

	public void setDenominazioneUo(String denominazioneUo) {
		this.denominazioneUo = denominazioneUo;
	}

	public Boolean getFlgIncluseSottoUo() {
		return flgIncluseSottoUo;
	}

	public void setFlgIncluseSottoUo(Boolean flgIncluseSottoUo) {
		this.flgIncluseSottoUo = flgIncluseSottoUo;
	}

	public Boolean getFlgIncluseScrivanie() {
		return flgIncluseScrivanie;
	}

	public void setFlgIncluseScrivanie(Boolean flgIncluseScrivanie) {
		this.flgIncluseScrivanie = flgIncluseScrivanie;
	}

	public Boolean getFlgVisDocFascRis() {
		return flgVisDocFascRis;
	}

	public void setFlgVisDocFascRis(Boolean flgVisDocFascRis) {
		this.flgVisDocFascRis = flgVisDocFascRis;
	}

	public String getCodRapido() {
		return codRapido;
	}

	public void setCodRapido(String codRapido) {
		this.codRapido = codRapido;
	}

}