/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.invoke.MethodHandles;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.job.aggiungiMarca.bean.ConfigurazioniProcessoAggiuntaMarca;
import it.eng.job.aggiungiMarca.exceptions.AggiungiMarcaTemporaleException;


/**
 * Classe astratta comune con metodi e proprietà comuni ai manager 
 * 
 *
 */

public abstract class Manager {

	static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	protected final ConfigurazioniProcessoAggiuntaMarca configurazioniProcessoAggiuntaMarca;

	public Manager(ConfigurazioniProcessoAggiuntaMarca configurazioniProcessoAggiuntaMarca) throws AggiungiMarcaTemporaleException {

		super();

		this.configurazioniProcessoAggiuntaMarca = configurazioniProcessoAggiuntaMarca;
		
	}

	
	
	

	
}
