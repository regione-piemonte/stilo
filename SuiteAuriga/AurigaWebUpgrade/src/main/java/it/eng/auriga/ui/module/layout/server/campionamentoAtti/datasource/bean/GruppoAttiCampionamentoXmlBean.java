/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class GruppoAttiCampionamentoXmlBean {

	@NumeroColonna(numero = "1")
	private String idTipoAtto;
	
	@NumeroColonna(numero = "3")
	private String codiceAtto;
	
	@NumeroColonna(numero = "5")
	private String flgDeterminaAContrarre;
	
	@NumeroColonna(numero = "6")
	private String idUoProponente;
	
	@NumeroColonna(numero = "9")
	private String rangeImporto;
	
	@NumeroColonna(numero = "11")
	private String idRegola;
	
	@NumeroColonna(numero = "12")
	private String flgMantieniCessaRegola;

	@NumeroColonna(numero = "13")
	private String flgCodiceAttoParticolare;

	public String getIdTipoAtto() {
		return idTipoAtto;
	}

	public void setIdTipoAtto(String idTipoAtto) {
		this.idTipoAtto = idTipoAtto;
	}

	public String getCodiceAtto() {
		return codiceAtto;
	}

	public void setCodiceAtto(String codiceAtto) {
		this.codiceAtto = codiceAtto;
	}

	public String getFlgDeterminaAContrarre() {
		return flgDeterminaAContrarre;
	}

	public void setFlgDeterminaAContrarre(String flgDeterminaAContrarre) {
		this.flgDeterminaAContrarre = flgDeterminaAContrarre;
	}

	public String getIdUoProponente() {
		return idUoProponente;
	}

	public void setIdUoProponente(String idUoProponente) {
		this.idUoProponente = idUoProponente;
	}

	public String getRangeImporto() {
		return rangeImporto;
	}

	public void setRangeImporto(String rangeImporto) {
		this.rangeImporto = rangeImporto;
	}

	public String getIdRegola() {
		return idRegola;
	}

	public void setIdRegola(String idRegola) {
		this.idRegola = idRegola;
	}

	public String getFlgMantieniCessaRegola() {
		return flgMantieniCessaRegola;
	}

	public void setFlgMantieniCessaRegola(String flgMantieniCessaRegola) {
		this.flgMantieniCessaRegola = flgMantieniCessaRegola;
	}

	public String getFlgCodiceAttoParticolare() {
		return flgCodiceAttoParticolare;
	}

	public void setFlgCodiceAttoParticolare(String flgCodiceAttoParticolare) {
		this.flgCodiceAttoParticolare = flgCodiceAttoParticolare;
	}
	
}
