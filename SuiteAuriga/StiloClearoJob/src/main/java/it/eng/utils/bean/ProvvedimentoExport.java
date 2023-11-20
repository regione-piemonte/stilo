/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Provvedimento")
@XmlAccessorType (XmlAccessType.FIELD)
public class ProvvedimentoExport {
	
	@XmlElement(name = "Anno")
	private String anno;
	@XmlElement(name = "Oggetto")
	private String oggetto;
	@XmlElement(name = "Semestre")
	private String semestre;
	@XmlElement(name = "Num_Provvedimento")
	private String num_Provvedimento;
	@XmlElement(name = "Data_Provvedimento")
	private String data_Provvedimento;
	@XmlElement(name = "Tipo_Provvedimento")
	private String tipo_Provvedimento;
	
	public String getAnno() {
		return anno;
	}
	
	public void setAnno(String anno) {
		this.anno = anno;
	}
	
	public String getOggetto() {
		return oggetto;
	}
	
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	
	public String getSemestre() {
		return semestre;
	}
	
	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}
	
	public String getNum_Provvedimento() {
		return num_Provvedimento;
	}
	
	public void setNum_Provvedimento(String num_Provvedimento) {
		this.num_Provvedimento = num_Provvedimento;
	}
	
	public String getData_Provvedimento() {
		return data_Provvedimento;
	}
	
	public void setData_Provvedimento(String data_Provvedimento) {
		this.data_Provvedimento = data_Provvedimento;
	}
	
	public String getTipo_Provvedimento() {
		return tipo_Provvedimento;
	}
	
	public void setTipo_Provvedimento(String tipo_Provvedimento) {
		this.tipo_Provvedimento = tipo_Provvedimento;
	}
	
}
