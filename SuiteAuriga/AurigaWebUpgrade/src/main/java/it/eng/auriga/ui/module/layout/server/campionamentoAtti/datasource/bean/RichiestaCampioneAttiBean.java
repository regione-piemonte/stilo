/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

/**
 * 
 * @author matzanin
 *
 */

public class RichiestaCampioneAttiBean {
	
	private String idCampione;
	private String nroAttiCampione;
	
	private String idTipoAtto;
	private String codiceAtto;
	private String flgDeterminaAContrarre;
	private String idUoProponente;
	private String rangeImporto;
	private Date dataInizioPeriodoRif;
	private Date dataFinePeriodoRif;
	
	public String getIdCampione() {
		return idCampione;
	}
	public void setIdCampione(String idCampione) {
		this.idCampione = idCampione;
	}
	public String getNroAttiCampione() {
		return nroAttiCampione;
	}
	public void setNroAttiCampione(String nroAttiCampione) {
		this.nroAttiCampione = nroAttiCampione;
	}
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
	public Date getDataInizioPeriodoRif() {
		return dataInizioPeriodoRif;
	}
	public void setDataInizioPeriodoRif(Date dataInizioPeriodoRif) {
		this.dataInizioPeriodoRif = dataInizioPeriodoRif;
	}
	public Date getDataFinePeriodoRif() {
		return dataFinePeriodoRif;
	}
	public void setDataFinePeriodoRif(Date dataFinePeriodoRif) {
		this.dataFinePeriodoRif = dataFinePeriodoRif;
	}
	
}