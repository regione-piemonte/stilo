/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

@XmlRootElement
public class CreaModRegistroKimberlyInBean extends CreaModDocumentoInBean {

	private static final long serialVersionUID = -6609881844566442763L;

	@XmlVariabile(nome = "REGISTRO_ANNO_Doc", tipo = TipoVariabile.SEMPLICE)
	private int anno;

	@XmlVariabile(nome = "REGISTRO_MESE_Doc", tipo = TipoVariabile.SEMPLICE)
	private int mese;

	@XmlVariabile(nome = "REGISTRO_TIPO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String tipoRegistro;

	@XmlVariabile(nome = "REGISTRO_SOTTOTIPO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String sottoTipoRegistro;

	@XmlVariabile(nome = "NRO_PAGINE_Ud", tipo = TipoVariabile.SEMPLICE)
	private int nroPagine;

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public int getMese() {
		return mese;
	}

	public void setMese(int mese) {
		this.mese = mese;
	}

	public String getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	public String getSottoTipoRegistro() {
		return sottoTipoRegistro;
	}

	public void setSottoTipoRegistro(String sottoTipoRegistro) {
		this.sottoTipoRegistro = sottoTipoRegistro;
	}

	public int getNroPagine() {
		return nroPagine;
	}

	public void setNroPagine(int nroPagine) {
		this.nroPagine = nroPagine;
	}
	
}
