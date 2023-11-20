/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.job.aggiungiMarca.bean.HandlerResultBean;
import it.eng.job.aggiungiMarca.exceptions.AggiungiMarcaTemporaleException;

public interface ElaborazioneAggiuntaMarcaManager {

	public HandlerResultBean<Void> elaboraProcessoAggiuntaMarca(  ) throws AggiungiMarcaTemporaleException;
	
}
