/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.concurrent.RecursiveTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import it.eng.job.aggiungiMarca.bean.ConfigurazioniProcessoAggiuntaMarca;
import it.eng.job.aggiungiMarca.bean.FileDaMarcareBean;
import it.eng.job.aggiungiMarca.bean.HandlerResultBean;
import it.eng.job.aggiungiMarca.constants.AggiuntaMarcaConstants;
import it.eng.job.aggiungiMarca.constants.ErrorInfoEnum;
import it.eng.job.aggiungiMarca.constants.PostManageActionEnum;
import it.eng.job.aggiungiMarca.constants.StatoElaborazione;
import it.eng.job.aggiungiMarca.dao.DaoCodaFileDaMarcare;
import it.eng.job.aggiungiMarca.dao.DaoFileDaMarcare;
import it.eng.job.aggiungiMarca.entity.CodaFileDaMarcare;
import it.eng.job.aggiungiMarca.entity.FileDaMarcare;
import it.eng.job.aggiungiMarca.exceptions.AggiungiMarcaTemporaleException;


public class OperazioniFinaliMarcaFileTask extends RecursiveTask<HandlerResultBean<Void>> {

	static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private static final long serialVersionUID = 504923471932769829L;
	private final String idProcesso;
	private final boolean esitoElaborazione;
	private final FileDaMarcareBean fileDaMarcare;
	private final ConfigurazioniProcessoAggiuntaMarca configurazioniProcessoAggiuntaMarca;

	public OperazioniFinaliMarcaFileTask(String idProcesso, boolean esitoElaborazione, FileDaMarcareBean fileDaMarcare,
			ConfigurazioniProcessoAggiuntaMarca configurazioniProcessoAggiuntaMarca) {
		super();
		this.idProcesso = idProcesso;
		this.esitoElaborazione = esitoElaborazione;
		this.fileDaMarcare = fileDaMarcare;
		this.configurazioniProcessoAggiuntaMarca = configurazioniProcessoAggiuntaMarca;
	}

	@Override
	protected HandlerResultBean<Void> compute() {
		logger.debug("Task operazioni finali ");
		ThreadContext.put(AggiuntaMarcaConstants.ROUTINGKEY,
				AggiuntaMarcaConstants.ELABORAZIONE + idProcesso);
		try {

			HandlerResultBean<Void> result = elaboraFile(fileDaMarcare);

			return result;
		} catch (AggiungiMarcaTemporaleException e) {
			logger.error("Si è verificato un errore nelle operazioni finali sulla sulla marca del file con idUd ",
					fileDaMarcare.getIdUd() + " e idDoc " + fileDaMarcare.getIdDoc() , e);
			HandlerResultBean<Void> result = new HandlerResultBean<>();
			result.setSuccessful(false);
			return result;
		}
	}

	private HandlerResultBean<Void> elaboraFile(final FileDaMarcareBean fileDaMarcare)
			throws AggiungiMarcaTemporaleException {

		HandlerResultBean<Void> result = new HandlerResultBean<>();

		if (!esitoElaborazione)
			result = operazioniFinaliElaborazioneFile(fileDaMarcare, StatoElaborazione.ELABORATO_CON_ERRORI);
		else
			result = operazioniFinaliElaborazioneFile(fileDaMarcare, StatoElaborazione.ELABORATO_SENZA_ERRORI);

		return result;

	}

	/**
	 * Azioni di fine lavorazione per il file in input
	 * 
	 * @param statoElaborazione
	 * @throws ImportDocIndexException
	 */

	protected HandlerResultBean<Void> operazioniFinaliElaborazioneFile(final FileDaMarcareBean fileDaMarcare,
			final StatoElaborazione statoElaborazione) throws AggiungiMarcaTemporaleException {

		HandlerResultBean<Void> result = new HandlerResultBean<>();
		result.setSuccessful(false);

		try {

			final PostManageActionEnum azioneFinale = configurazioniProcessoAggiuntaMarca.getAzioneFinale();
			logger.info("azioneFinale " + azioneFinale);
				
			if (azioneFinale.equals(PostManageActionEnum.DELETE)) {
				if( statoElaborazione.equals(StatoElaborazione.ELABORATO_CON_ERRORI )){
					logger.info("record elaborato con errori, non cancello");
				 } else if( statoElaborazione.equals(StatoElaborazione.ELABORATO_SENZA_ERRORI)) {
					 logger.info("record elaborato con successo, lo cancello");
					 DaoCodaFileDaMarcare dao = new DaoCodaFileDaMarcare();
					 CodaFileDaMarcare bean = new CodaFileDaMarcare();
					 bean.setIdUd( fileDaMarcare.getIdUd());
					 bean.setIdDoc( fileDaMarcare.getIdDoc());
					 bean.setTipoBustaCritt( fileDaMarcare.getTipoFile());
					 bean.setTsFirma( fileDaMarcare.getTsFirma() );	
					 dao.delete(bean);
					 logger.info("dopo cancellazione");
				 }
			
			 }
				//else if (azioneFinale.equals(PostManageActionEnum.UPDATE)) {
			// logger.info("Aggiorno lo scarto", idScarto );
			// if(
			// statoElaborazione.equals(StatoElaborazione.ELABORATO_CON_ERRORI
			// )){

			// } else if(
			// statoElaborazione.equals(StatoElaborazione.ELABORATO_SENZA_ERRORI)
			// ) {
			// ScartiUtils.scartato(idScarto);
			// }
			// }

			result.setSuccessful(true);
		}

		catch (Exception e) {
			logger.error(ErrorInfoEnum.ERRORE_OPERAZIONI_FINALI.getDescription(), e);
			throw new AggiungiMarcaTemporaleException(e, ErrorInfoEnum.ERRORE_OPERAZIONI_FINALI);
		}

		return result;
	}

}