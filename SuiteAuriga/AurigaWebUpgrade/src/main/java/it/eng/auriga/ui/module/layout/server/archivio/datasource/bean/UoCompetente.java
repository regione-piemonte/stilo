/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.function.bean.Flag;

import java.io.Serializable;


public class UoCompetente implements Serializable {
	
	@NumeroColonna(numero = "1")
	private String idUo;

	@NumeroColonna(numero = "2")
	private Flag flgIncludiSottoUo;
	
	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}
	
	public Flag getFlgIncludiSottoUo() {
		return flgIncludiSottoUo;
	}

	public void setFlgIncludiSottoUo(Flag flgIncludiSottoUo) {
		this.flgIncludiSottoUo = flgIncludiSottoUo;
	}
	
}
