/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AltriRiferimentiBean implements Serializable {

	private static final long serialVersionUID = 5611310655086324291L;

	@NumeroColonna(numero = "1")
	private String registroTipoRif;

	@NumeroColonna(numero = "2")
	private String numero;

	@NumeroColonna(numero = "3")
	private String anno;

	@NumeroColonna(numero = "4")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date data;

	@NumeroColonna(numero = "5")
	private String note;

	public String getRegistroTipoRif() {
		return registroTipoRif;
	}

	public void setRegistroTipoRif(String registroTipoRif) {
		this.registroTipoRif = registroTipoRif;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
