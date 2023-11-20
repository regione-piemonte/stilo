/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.function.bean.Flag;

//@XmlRootElement(name="destInvioNotifica")
public class RigaAssegnatarioEmail {
	
	@NumeroColonna(numero = "1")
	private String tipo;
	
	@NumeroColonna(numero = "2")
	private String id;
	
	@NumeroColonna(numero = "3")
	private Flag flgIncludiSottoUO;
	
	@NumeroColonna(numero = "4")
	private Flag flgIncludiScrivanie;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Flag getFlgIncludiSottoUO() {
		return flgIncludiSottoUO;
	}
	public void setFlgIncludiSottoUO(Flag flgIncludiSottoUO) {
		this.flgIncludiSottoUO = flgIncludiSottoUO;
	}
	public Flag getFlgIncludiScrivanie() {
		return flgIncludiScrivanie;
	}
	public void setFlgIncludiScrivanie(Flag flgIncludiScrivanie) {
		this.flgIncludiScrivanie = flgIncludiScrivanie;
	}
	
	
}
