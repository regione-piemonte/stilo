/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import it.eng.job.AurigaSpringContext;
import it.eng.job.SpringHelper;
import it.eng.job.aggiungiMarca.bean.FileDaMarcareBean;
import it.eng.job.aggiungiMarca.bean.HandlerResultBean;
import it.eng.job.aggiungiMarca.bean.MarcaConfigBean;
import it.eng.job.aggiungiMarca.constants.AggiuntaMarcaConstants;
import it.eng.job.aggiungiMarca.constants.ErrorInfoEnum;
import it.eng.job.aggiungiMarca.dao.DaoCodaFileDaMarcare;
import it.eng.job.aggiungiMarca.dao.DaoFileDaMarcare;
import it.eng.job.aggiungiMarca.entity.CodaFileDaMarcare;
import it.eng.job.aggiungiMarca.entity.FileDaMarcare;
import it.eng.job.aggiungiMarca.exceptions.AggiungiMarcaTemporaleException;



public class RicercaFileFirmatiTask extends RecursiveTask<HandlerResultBean<Void>> {

	static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private static final long serialVersionUID = 4272278979801291534L;
	
	private final String idProcessoAggiuntaMarca;
	private final String numTentativiMarca;
	
	
	public RicercaFileFirmatiTask(String idProcessoAggiuntaMarca, String numTentativiMarca) throws AggiungiMarcaTemporaleException {
		super();
		this.idProcessoAggiuntaMarca = idProcessoAggiuntaMarca;
		this.numTentativiMarca = numTentativiMarca;
	}

	@Override
	protected HandlerResultBean<Void> compute() {

		logger.debug("Task ricerca file firmati");
		ThreadContext.put(AggiuntaMarcaConstants.ROUTINGKEY, AggiuntaMarcaConstants.ELABORAZIONE + idProcessoAggiuntaMarca);
		try {
			return effettuaRicerca();
		} catch (AggiungiMarcaTemporaleException e) {
			logger.error("Si è verificato un errore nella ricerca dei files firmati");
			HandlerResultBean<Void> result = new HandlerResultBean<>();
			result.setSuccessful(false);
			result.setErrorInfo( ErrorInfoEnum.ERRORE_RICERCA_FILES );
			result.setMessage( ErrorInfoEnum.ERRORE_RICERCA_FILES.getDescription() + " " + e.getMessage() );
			return result; 
		}

	}
	
	private HandlerResultBean<Void> effettuaRicerca()
			throws AggiungiMarcaTemporaleException {

		HandlerResultBean<Void> result = new HandlerResultBean<>();
		result.setSuccessful(false);
		
		List<FileDaMarcareBean> listaFilesDaMarcare = new ArrayList<FileDaMarcareBean>();
		try {	
			// valorizzo la lista idUdFilesDaMarcare facendo la query
			
			logger.debug("Effettuo la ricerca ");
			
			//DaoFileDaMarcare dao = new DaoFileDaMarcare();
			DaoCodaFileDaMarcare dao = new DaoCodaFileDaMarcare();
			
			int numTentativiMarcaInt = 0;
			try {
				numTentativiMarcaInt = Integer.parseInt(numTentativiMarca);
			} catch(Exception e){
				logger.error("Errore nel determinare il numero massimo di tentativi di marca da effettuare");
			}
			
			List<CodaFileDaMarcare> records = dao.getRecordsDaMarcare(false, numTentativiMarcaInt);
			logger.info("records: " + records);
			
			for(CodaFileDaMarcare record : records){
				FileDaMarcareBean file = new FileDaMarcareBean();		
				file.setIdUd( record.getIdUd() );
				file.setIdDoc( record.getIdDoc() );
				file.setTipoFile( record.getTipoBustaCritt() );
				file.setTsFirma( record.getTsFirma() );
				file.setNumTry( record.getNumTry() );
				listaFilesDaMarcare.add(file);
			}
			
			logger.debug("Risultato della ricerca " +listaFilesDaMarcare);
			
			
			if( listaFilesDaMarcare!=null &&  listaFilesDaMarcare.size()>0){
				result.addAdditionalInformation(AggiuntaMarcaConstants.RESULT_RICERCA, listaFilesDaMarcare);
				result.setSuccessful(true);
			} else {
				logger.debug("Nessun files da elaborare");
				result.setSuccessful(true);
				result.setMessage("Nessun files da elaborare");
			}
		} catch(Exception e){
//			result.setSuccessful(false);
//			result.setMessage(e.getMessage());
//			result.setErrorInfo( ErrorInfoEnum.ERRORE_RICERCA_Riapertura);
			e.printStackTrace();
			throw new AggiungiMarcaTemporaleException(e.getMessage());
		}
		
		return result;
	}


}
