/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EmailProvBean implements Serializable{

	@XmlVariabile(nome="Id", tipo=TipoVariabile.SEMPLICE)
	private String id;
	@XmlVariabile(nome="Indirizzo", tipo=TipoVariabile.SEMPLICE)
	private String indirizzo;
	@XmlVariabile(nome="FlgPEC", tipo=TipoVariabile.SEMPLICE)
	private Flag flgPEC;
	@XmlVariabile(nome="FlgCasellaIstituz", tipo=TipoVariabile.SEMPLICE)
	private Flag flgCasellaIstituzionale;
	@XmlVariabile(nome="FlgDichIPA", tipo=TipoVariabile.SEMPLICE)
	private Flag flgDichIPA;
	@XmlVariabile(nome="GestorePEC", tipo=TipoVariabile.SEMPLICE)
	private String gestorePEC;
	
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public Flag getFlgPEC() {
		return flgPEC;
	}
	public void setFlgPEC(Flag flgPEC) {
		this.flgPEC = flgPEC;
	}
	public Flag getFlgCasellaIstituzionale() {
		return flgCasellaIstituzionale;
	}
	public void setFlgCasellaIstituzionale(Flag flgCasellaIstituzionale) {
		this.flgCasellaIstituzionale = flgCasellaIstituzionale;
	}
	public Flag getFlgDichIPA() {
		return flgDichIPA;
	}
	public void setFlgDichIPA(Flag flgDichIPA) {
		this.flgDichIPA = flgDichIPA;
	}
	public String getGestorePEC() {
		return gestorePEC;
	}
	public void setGestorePEC(String gestorePEC) {
		this.gestorePEC = gestorePEC;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
