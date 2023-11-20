/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.job.aggiungiMarca.bean.ConfigurazioniProcessoAggiuntaMarca;
import it.eng.job.aggiungiMarca.bean.FileDaMarcareBean;
import it.eng.job.aggiungiMarca.bean.HandlerResultBean;
import it.eng.job.aggiungiMarca.constants.AggiuntaMarcaConstants;
import it.eng.job.aggiungiMarca.entity.CodaFileDaMarcare;
import it.eng.job.aggiungiMarca.exceptions.AggiungiMarcaTemporaleException;
import it.eng.utility.storageutil.StorageService;

public class ElaborazioneVuotaTask extends RecursiveTask<HandlerResultBean<Void>> {

	static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private static final long serialVersionUID = 4272278979801291534L;

	private final String idProcessoAggiuntaMarca;
	private final ConfigurazioniProcessoAggiuntaMarca configurazioniProcessoAggiuntaMarca;
	private final AurigaLoginBean aurigaLoginBean;
	private final Locale locale;
	private final StorageService storageService;
	

	public ElaborazioneVuotaTask(ConfigurazioniProcessoAggiuntaMarca confProcessoAggiuntaMarca, AurigaLoginBean aurigaLoginBean, Locale locale)
			throws AggiungiMarcaTemporaleException {
		super();
		this.idProcessoAggiuntaMarca = confProcessoAggiuntaMarca.getId();
		this.configurazioniProcessoAggiuntaMarca = confProcessoAggiuntaMarca;
		this.aurigaLoginBean = aurigaLoginBean;
		this.locale = locale;
		this.storageService = confProcessoAggiuntaMarca.getStorageUtilImpl();
	}

	@Override
	protected HandlerResultBean<Void> compute() {

		logger.debug("Task elaborazione vuota id " + idProcessoAggiuntaMarca);
		ThreadContext.put(AggiuntaMarcaConstants.ROUTINGKEY,
				AggiuntaMarcaConstants.ELABORAZIONE + idProcessoAggiuntaMarca);

		logger.debug("ESECUZIONE TASK!!!"); 
		
		HandlerResultBean<Void> result = new HandlerResultBean<>();
		result.setSuccessful(true);
		
		

		return result;

	}

}
