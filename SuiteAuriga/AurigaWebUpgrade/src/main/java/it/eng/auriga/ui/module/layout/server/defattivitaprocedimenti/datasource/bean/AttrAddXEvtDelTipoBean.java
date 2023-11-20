/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

/**
 * 
 * @author Antonio Magnocavallo
 *
 */

public class AttrAddXEvtDelTipoBean {

	private String nome;
	private String etichetta;
	private Boolean checkObbligatorio;
	private Boolean checkRipetibile;
	private String maxNumValori; // visibile solo se ripetibile
	private Boolean flgTabPrincipale;
	private String codice;
	private String label;
	private Date tsVldDal;
	private Date tsVldA;
	private Boolean checkExtraIterWf;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEtichetta() {
		return etichetta;
	}

	public void setEtichetta(String etichetta) {
		this.etichetta = etichetta;
	}

	public Boolean getCheckRipetibile() {
		return checkRipetibile;
	}

	public void setCheckRipetibile(Boolean checkRipetibile) {
		this.checkRipetibile = checkRipetibile;
	}

	public Boolean getCheckObbligatorio() {
		return checkObbligatorio;
	}

	public void setCheckObbligatorio(Boolean checkObbligatorio) {
		this.checkObbligatorio = checkObbligatorio;
	}

	public String getMaxNumValori() {
		return maxNumValori;
	}

	public void setMaxNumValori(String maxNumValori) {
		this.maxNumValori = maxNumValori;
	}

	public Boolean getFlgTabPrincipale() {
		return flgTabPrincipale;
	}

	public void setFlgTabPrincipale(Boolean flgTabPrincipale) {
		this.flgTabPrincipale = flgTabPrincipale;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Date getTsVldDal() {
		return tsVldDal;
	}

	public void setTsVldDal(Date tsVldDal) {
		this.tsVldDal = tsVldDal;
	}

	public Date getTsVldA() {
		return tsVldA;
	}

	public void setTsVldA(Date tsVldA) {
		this.tsVldA = tsVldA;
	}

	public Boolean getCheckExtraIterWf() {
		return checkExtraIterWf;
	}

	public void setCheckExtraIterWf(Boolean checkExtraIterWf) {
		this.checkExtraIterWf = checkExtraIterWf;
	}

}