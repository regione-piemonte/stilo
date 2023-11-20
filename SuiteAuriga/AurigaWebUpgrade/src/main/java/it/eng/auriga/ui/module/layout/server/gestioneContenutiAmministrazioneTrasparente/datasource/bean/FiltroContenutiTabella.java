/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class FiltroContenutiTabella {
	
	@NumeroColonna(numero="1")
	private String nomeFiltro;
	
	@NumeroColonna(numero="2")
	private String valoreFiltro;

	public String getNomeFiltro() {
		return nomeFiltro;
	}

	public void setNomeFiltro(String nomeFiltro) {
		this.nomeFiltro = nomeFiltro;
	}

	public String getValoreFiltro() {
		return valoreFiltro;
	}

	public void setValoreFiltro(String valoreFiltro) {
		this.valoreFiltro = valoreFiltro;
	}

}
