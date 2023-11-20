/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

/**
 * 
 * @author matzanin
 *
 */

public class GruppoAttiCampionamentoBean {
	
	private String idTipoAtto;
	private String tipologiaAtto;
//	private String codiceAtto;
	private String flgCodiceAttoParticolare;
	private String flgDeterminaAContrarre;
//	private String idUoProponente;
//	private String strutturaProponente;
	private String rangeImporto;
	private String percentualeCampionamento;
	private String idRegola;
	private Date dataDecorrenzaRegola;
	private String colonneRegola;
	
	private Boolean flgCessaRegolaPreEsistente;
	private Boolean flgMantieniRegolaPreEsistente;
	
	public String getIdTipoAtto() {
		return idTipoAtto;
	}
	public void setIdTipoAtto(String idTipoAtto) {
		this.idTipoAtto = idTipoAtto;
	}
	public String getTipologiaAtto() {
		return tipologiaAtto;
	}
	public void setTipologiaAtto(String tipologiaAtto) {
		this.tipologiaAtto = tipologiaAtto;
	}
//	public String getCodiceAtto() {
//		return codiceAtto;
//	}
//	public void setCodiceAtto(String codiceAtto) {
//		this.codiceAtto = codiceAtto;
//	}
	public String getFlgCodiceAttoParticolare() {
		return flgCodiceAttoParticolare;
	}
	public void setFlgCodiceAttoParticolare(String flgCodiceAttoParticolare) {
		this.flgCodiceAttoParticolare = flgCodiceAttoParticolare;
	}
	public String getFlgDeterminaAContrarre() {
		return flgDeterminaAContrarre;
	}
	public void setFlgDeterminaAContrarre(String flgDeterminaAContrarre) {
		this.flgDeterminaAContrarre = flgDeterminaAContrarre;
	}
//	public String getIdUoProponente() {
//		return idUoProponente;
//	}
//	public void setIdUoProponente(String idUoProponente) {
//		this.idUoProponente = idUoProponente;
//	}
//	public String getStrutturaProponente() {
//		return strutturaProponente;
//	}
//	public void setStrutturaProponente(String strutturaProponente) {
//		this.strutturaProponente = strutturaProponente;
//	}
	public String getRangeImporto() {
		return rangeImporto;
	}
	public void setRangeImporto(String rangeImporto) {
		this.rangeImporto = rangeImporto;
	}
	public String getPercentualeCampionamento() {
		return percentualeCampionamento;
	}
	public void setPercentualeCampionamento(String percentualeCampionamento) {
		this.percentualeCampionamento = percentualeCampionamento;
	}
	public String getIdRegola() {
		return idRegola;
	}
	public void setIdRegola(String idRegola) {
		this.idRegola = idRegola;
	}
	public Date getDataDecorrenzaRegola() {
		return dataDecorrenzaRegola;
	}
	public void setDataDecorrenzaRegola(Date dataDecorrenzaRegola) {
		this.dataDecorrenzaRegola = dataDecorrenzaRegola;
	}
	public String getColonneRegola() {
		return colonneRegola;
	}
	public void setColonneRegola(String colonneRegola) {
		this.colonneRegola = colonneRegola;
	}
	public Boolean getFlgCessaRegolaPreEsistente() {
		return flgCessaRegolaPreEsistente;
	}
	public void setFlgCessaRegolaPreEsistente(Boolean flgCessaRegolaPreEsistente) {
		this.flgCessaRegolaPreEsistente = flgCessaRegolaPreEsistente;
	}
	public Boolean getFlgMantieniRegolaPreEsistente() {
		return flgMantieniRegolaPreEsistente;
	}
	public void setFlgMantieniRegolaPreEsistente(Boolean flgMantieniRegolaPreEsistente) {
		this.flgMantieniRegolaPreEsistente = flgMantieniRegolaPreEsistente;
	}
	
}