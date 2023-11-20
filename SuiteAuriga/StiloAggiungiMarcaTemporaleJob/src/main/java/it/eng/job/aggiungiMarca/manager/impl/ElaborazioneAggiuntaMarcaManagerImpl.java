/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;


import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.job.aggiungiMarca.bean.ConfigurazioniProcessoAggiuntaMarca;
import it.eng.job.aggiungiMarca.bean.HandlerResultBean;
import it.eng.job.aggiungiMarca.constants.AggiuntaMarcaConstants;
import it.eng.job.aggiungiMarca.exceptions.AggiungiMarcaTemporaleException;
import it.eng.job.aggiungiMarca.manager.ElaborazioneAggiuntaMarcaManager;
import it.eng.job.aggiungiMarca.manager.Manager;
import it.eng.job.aggiungiMarca.task.ElaborazioneAggiuntaMarcaTask;
import it.eng.job.aggiungiMarca.task.ElaborazioneVuotaTask;


public class ElaborazioneAggiuntaMarcaManagerImpl extends Manager implements ElaborazioneAggiuntaMarcaManager, Callable<HandlerResultBean<Void>> {

	static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private final ForkJoinPool forkJoinPool;
	private final AurigaLoginBean aurigaLoginBean;
	private final Locale locale;

	public ElaborazioneAggiuntaMarcaManagerImpl( ForkJoinPool forkJoinPool, ConfigurazioniProcessoAggiuntaMarca configurazioneProcessoAggiuntaMarca, 
			AurigaLoginBean aurigaLoginBean, Locale locale) throws AggiungiMarcaTemporaleException {

		super(configurazioneProcessoAggiuntaMarca);

		// Pool dei thread da utilizzare 
		this.forkJoinPool = forkJoinPool;

		this.aurigaLoginBean = aurigaLoginBean;
		
		this.locale = locale;
	}
	
	@Override
	public HandlerResultBean<Void> call() throws Exception {

		final String id = configurazioniProcessoAggiuntaMarca.getId();
		ThreadContext.put(AggiuntaMarcaConstants.ROUTINGKEY, AggiuntaMarcaConstants.ELABORAZIONE + id);
		Thread.currentThread().setName(AggiuntaMarcaConstants.ELABORAZIONE + id);
		logger.debug("METODO CALL ElaborazioneAggiuntaMarcaManagerImpl: " + AggiuntaMarcaConstants.ELABORAZIONE + id);
		
		HandlerResultBean<Void> globalRes = new HandlerResultBean<Void>();
		globalRes.setSuccessful(true);
		
		HandlerResultBean<Void> res = elaboraProcessoAggiuntaMarca();
		if( !res.isSuccessful()){
			globalRes.setSuccessful(false);
			if( res.getMessage()!=null )
			globalRes.setMessage( res.getMessage() );
		}
		
		return globalRes;
	}

	@Override
	public HandlerResultBean<Void> elaboraProcessoAggiuntaMarca() throws AggiungiMarcaTemporaleException {
		logger.debug("ESECUZIONE INVOKE FORK");
		return forkJoinPool.invoke(new ElaborazioneAggiuntaMarcaTask(configurazioniProcessoAggiuntaMarca, aurigaLoginBean, locale));
		//return forkJoinPool.invoke(new ElaborazioneVuotaTask(configurazioniProcessoAggiuntaMarca, aurigaLoginBean, locale));
	}
	
}
