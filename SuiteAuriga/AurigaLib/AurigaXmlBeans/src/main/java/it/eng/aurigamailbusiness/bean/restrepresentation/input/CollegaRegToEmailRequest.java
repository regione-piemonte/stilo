/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.aurigamailbusiness.bean.restrepresentation.row.ImprontaAllegato;

@XmlRootElement(name="richiestaCollegaRegToEmail")
public class CollegaRegToEmailRequest extends MutualInput {
	
	@XmlElement(name="idEmail", required=true)
	private String idEmail;
	
	@XmlElement(name="idUd")
	private int idUd;
	
	@XmlElement(name="categoriaReg")
	private String categoriaReg;
	
	@XmlElement(name="siglaRegistro")
	private String siglaRegistro;
	
	@XmlElement(name="annoReg")
	private int annoReg;
	
	@XmlElement(name="numeroReg")
	private int numeroReg;

	@XmlElement(name="impronteAllegati")
	private List<ImprontaAllegato> impronteAllegati = new ArrayList<>();

	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public int getIdUd() {
		return idUd;
	}

	public void setIdUd(int idUd) {
		this.idUd = idUd;
	}

	public String getCategoriaReg() {
		return categoriaReg;
	}

	public void setCategoriaReg(String categoriaReg) {
		this.categoriaReg = categoriaReg;
	}

	public String getSiglaRegistro() {
		return siglaRegistro;
	}

	public void setSiglaRegistro(String siglaRegistro) {
		this.siglaRegistro = siglaRegistro;
	}

	public int getAnnoReg() {
		return annoReg;
	}

	public void setAnnoReg(int annoReg) {
		this.annoReg = annoReg;
	}

	public int getNumeroReg() {
		return numeroReg;
	}

	public void setNumeroReg(int numeroReg) {
		this.numeroReg = numeroReg;
	}

	public List<ImprontaAllegato> getImpronteAllegati() {
		return impronteAllegati;
	}

	public void setImpronteAllegati(List<ImprontaAllegato> impronteAllegati) {
		this.impronteAllegati = impronteAllegati;
	}
	
}
