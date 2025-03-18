/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.client.sib.Esito;

public class OutputCreaProposta extends Esito {

	private Long idPropostaDetermina;

	public Long getIdPropostaDetermina() {
		return idPropostaDetermina;
	}

	public void setIdPropostaDetermina(Long idPropostaDetermina) {
		this.idPropostaDetermina = idPropostaDetermina;
	}

}
