/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class AssPreselMittBean extends VisualBean{

	@NumeroColonna(numero="1")
	private String flgUoSv;
	@NumeroColonna(numero="2")
	private String idUoSv;
	@NumeroColonna(numero="3")
	private String descrizione;
	
	public String getFlgUoSv() {
		return flgUoSv;
	}
	public void setFlgUoSv(String flgUoSv) {
		this.flgUoSv = flgUoSv;
	}
	public String getIdUoSv() {
		return idUoSv;
	}
	public void setIdUoSv(String idUoSv) {
		this.idUoSv = idUoSv;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}
