/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANCRIST
 *
 */

public class UOAssegnatarieMailStoricizzateXmlBean {
	
	@NumeroColonna(numero = "1")
	private String idUo;
	
	@NumeroColonna(numero = "2")
	private boolean flgIncSottoUO;

	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public boolean getFlgIncSottoUO() {
		return flgIncSottoUO;
	}

	public void setFlgIncSottoUO(boolean flgIncSottoUO) {
		this.flgIncSottoUO = flgIncSottoUO;
	}

}