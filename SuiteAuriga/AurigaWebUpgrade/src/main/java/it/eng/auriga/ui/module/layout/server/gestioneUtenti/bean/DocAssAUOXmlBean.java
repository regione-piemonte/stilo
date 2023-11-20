/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class DocAssAUOXmlBean {
    
	@NumeroColonna(numero="1")
	private String idUo;

	@NumeroColonna(numero="2")
	private String codRapido;
	
	@NumeroColonna(numero="3")
	private String denominazioneUo;
	
    @NumeroColonna(numero="4")
	private String flgIncluseSottoUo;
	
	@NumeroColonna(numero="5")
	private String flgIncluseScrivanie;

	@NumeroColonna(numero="6")
	private String flgVisDocFascRis;

	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public String getCodRapido() {
		return codRapido;
	}

	public void setCodRapido(String codRapido) {
		this.codRapido = codRapido;
	}

	public String getDenominazioneUo() {
		return denominazioneUo;
	}

	public void setDenominazioneUo(String denominazioneUo) {
		this.denominazioneUo = denominazioneUo;
	}

	public String getFlgIncluseSottoUo() {
		return flgIncluseSottoUo;
	}

	public void setFlgIncluseSottoUo(String flgIncluseSottoUo) {
		this.flgIncluseSottoUo = flgIncluseSottoUo;
	}

	public String getFlgIncluseScrivanie() {
		return flgIncluseScrivanie;
	}

	public void setFlgIncluseScrivanie(String flgIncluseScrivanie) {
		this.flgIncluseScrivanie = flgIncluseScrivanie;
	}

	public String getFlgVisDocFascRis() {
		return flgVisDocFascRis;
	}

	public void setFlgVisDocFascRis(String flgVisDocFascRis) {
		this.flgVisDocFascRis = flgVisDocFascRis;
	}
	
}