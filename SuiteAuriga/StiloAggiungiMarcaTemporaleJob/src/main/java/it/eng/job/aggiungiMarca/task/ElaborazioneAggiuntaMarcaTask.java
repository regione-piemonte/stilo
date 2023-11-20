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

public class ElaborazioneAggiuntaMarcaTask extends RecursiveTask<HandlerResultBean<Void>> {

	static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private static final long serialVersionUID = 4272278979801291534L;

	private final String idProcessoAggiuntaMarca;
	private final ConfigurazioniProcessoAggiuntaMarca configurazioniProcessoAggiuntaMarca;
	private final AurigaLoginBean aurigaLoginBean;
	private final Locale locale;
	private final StorageService storageService;
	

	public ElaborazioneAggiuntaMarcaTask(ConfigurazioniProcessoAggiuntaMarca confProcessoAggiuntaMarca, AurigaLoginBean aurigaLoginBean, Locale locale)
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

		logger.debug("Task elaborazione aggiunta marca id " + idProcessoAggiuntaMarca);
		ThreadContext.put(AggiuntaMarcaConstants.ROUTINGKEY,
				AggiuntaMarcaConstants.ELABORAZIONE + idProcessoAggiuntaMarca);

		HandlerResultBean<Void> result = new HandlerResultBean<>();
		result.setSuccessful(false);
		
		List<FileDaMarcareBean> listaFilesDaMarcare = null;
		String errore = "";
		try {
			List<RecursiveTask<HandlerResultBean<Void>>> forkRicercaFileFirmati = new LinkedList<>();

			RicercaFileFirmatiTask taskRicercaFileFirmati = new RicercaFileFirmatiTask(idProcessoAggiuntaMarca, configurazioniProcessoAggiuntaMarca.getNumTentativiMarca());
			forkRicercaFileFirmati.add(taskRicercaFileFirmati);

			Collection<RecursiveTask<HandlerResultBean<Void>>> resultRicercaFileFirmati = ForkJoinTask
					.invokeAll(forkRicercaFileFirmati);
			

			Collection<RecursiveTask<HandlerResultBean<Void>>> globalResultElaborazioneSingoloFileFork = new ArrayList<RecursiveTask<HandlerResultBean<Void>>>();
			if (!resultRicercaFileFirmati.isEmpty()) {

				for (RecursiveTask<HandlerResultBean<Void>> taskRicerca : resultRicercaFileFirmati) {

					// recupero dell'esito del risultato del task
					HandlerResultBean<Void> ricercaFileFirmatiResult = taskRicerca.get();
					logger.debug("ricercaFileFirmatiResult " + ricercaFileFirmatiResult.isSuccessful());

					if (ricercaFileFirmatiResult.isSuccessful()) {

						listaFilesDaMarcare = (List<FileDaMarcareBean>) ricercaFileFirmatiResult
								.getAdditionalInformation(AggiuntaMarcaConstants.RESULT_RICERCA);

						if (listaFilesDaMarcare == null || listaFilesDaMarcare.size() == 0) {
							logger.debug("Nessun file da marcare ");
							result = ricercaFileFirmatiResult;
						} else {
							logger.debug("files da marcare " + listaFilesDaMarcare.size());
							for (Iterator<FileDaMarcareBean> iterator = listaFilesDaMarcare.iterator(); iterator.hasNext();) {
								List<RecursiveTask<HandlerResultBean<Void>>> forkElaborazioneFileFirmati = new LinkedList<>();
								FileDaMarcareBean fileDaMarcare = iterator.next();

								logger.debug("Elaborazione singola");
								ElaborazioneSingoloFileTask taskElab = new ElaborazioneSingoloFileTask(
										idProcessoAggiuntaMarca, fileDaMarcare, aurigaLoginBean, locale, storageService);
								forkElaborazioneFileFirmati.add(taskElab);
								
								Collection<RecursiveTask<HandlerResultBean<Void>>> resultElaborazioneSingoloFileFork = ForkJoinTask
										.invokeAll(forkElaborazioneFileFirmati);
								logger.debug("dopo elaborazione singola");
								
								globalResultElaborazioneSingoloFileFork.addAll(resultElaborazioneSingoloFileFork);
								logger.debug("globalResultElaborazioneSingoloFileFork " + globalResultElaborazioneSingoloFileFork);
							}
							
							
							List<FileDaMarcareBean> fileMarcatiOk = new ArrayList<FileDaMarcareBean>();
							List<FileDaMarcareBean> fileMarcatiKO = new ArrayList<FileDaMarcareBean>();
							if (!globalResultElaborazioneSingoloFileFork.isEmpty()) {
								for (RecursiveTask<HandlerResultBean<Void>> taskElaborazioneFile : globalResultElaborazioneSingoloFileFork) {
									// recupero dell'esito del risultato del  task
									HandlerResultBean<Void> elaborazioneFileResult = taskElaborazioneFile.get();
									
									FileDaMarcareBean filesDaMarcare = (FileDaMarcareBean) elaborazioneFileResult
											.getAdditionalInformation(AggiuntaMarcaConstants.RESULT_ELAB);
									logger.debug("filesDaMarcare " + filesDaMarcare);
									
									if( elaborazioneFileResult.isSuccessful() ){
										fileMarcatiOk.add(filesDaMarcare);
									} else {
										fileMarcatiKO.add(filesDaMarcare);
									}

								}
							}
							
							List<RecursiveTask<HandlerResultBean<Void>>> forkOperazioniFinali = new LinkedList<>();

							for (FileDaMarcareBean fileMarcatoOK : fileMarcatiOk) {
								OperazioniFinaliMarcaFileTask taskOperazioniFinale = new OperazioniFinaliMarcaFileTask(
										idProcessoAggiuntaMarca, true, fileMarcatoOK, configurazioniProcessoAggiuntaMarca);
								forkOperazioniFinali.add(taskOperazioniFinale);
							}
							for (FileDaMarcareBean fileMarcatoKO : fileMarcatiKO) {
								OperazioniFinaliMarcaFileTask taskOperazioniFinale = new OperazioniFinaliMarcaFileTask(
										idProcessoAggiuntaMarca, false, fileMarcatoKO, configurazioniProcessoAggiuntaMarca);
								forkOperazioniFinali.add(taskOperazioniFinale);
							}
							Collection<RecursiveTask<HandlerResultBean<Void>>> resultOperazioniFinaliFork = ForkJoinTask
									.invokeAll(forkOperazioniFinali);
							logger.debug("resultOperazioniFinaliFork " + resultOperazioniFinaliFork);
						}
					} else {
						result = ricercaFileFirmatiResult;
					}

					
				}
			}

		} catch (AggiungiMarcaTemporaleException | InterruptedException | ExecutionException e) {
			errore = "Si è verificato un errore nel task di " + e.getMessage();
			logger.error("Si è verificato un errore nel task di ", e);
			result = new HandlerResultBean<>();
			result.setSuccessful(false);
		}

		return result;

	}

}
