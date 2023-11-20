/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;


public class XMLAttrAddXEvtDelTipoBean {

	@NumeroColonna(numero = "2")
	private String nome;
	
	@NumeroColonna(numero = "3")
	private String etichetta;
	
	@NumeroColonna(numero = "4")
	private String checkObbligatorio;
	
	@NumeroColonna(numero = "5")
	private String maxNumValori;// visibile solo se ripetibile
	
	@NumeroColonna(numero = "6")
	@TipoData(tipo = Tipo.DATA)
	private Date tsVldDal;
	
	@NumeroColonna(numero = "7")
	@TipoData(tipo = Tipo.DATA)
	private Date tsVldA;
	
	// 10: (flag 1/0) Indica se l'attributo va nel primo TAB della GUI
	@NumeroColonna(numero = "10")
	private String flgTabPrincipale;
	
	// 11: Codice del TAB in cui mostrare l'attributo
	@NumeroColonna(numero = "11")
	private String codice;
	
	// 12: Label del TAB in cui mostrare l'attributo
	@NumeroColonna(numero = "12")
	private String label;
	
	// 13: check-box "gestibile extra-iter workflow" - Tipologie documenti
	@NumeroColonna(numero = "13")
	private String checkExtraIterWf;

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

	public String getCheckObbligatorio() {
		return checkObbligatorio;
	}

	public void setCheckObbligatorio(String checkObbligatorio) {
		this.checkObbligatorio = checkObbligatorio;
	}

	public String getMaxNumValori() {
		return maxNumValori;
	}

	public void setMaxNumValori(String maxNumValori) {
		this.maxNumValori = maxNumValori;
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

	public String getFlgTabPrincipale() {
		return flgTabPrincipale;
	}

	public void setFlgTabPrincipale(String flgTabPrincipale) {
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

	public String getCheckExtraIterWf() {
		return checkExtraIterWf;
	}

	public void setCheckExtraIterWf(String checkExtraIterWf) {
		this.checkExtraIterWf = checkExtraIterWf;
	}

}