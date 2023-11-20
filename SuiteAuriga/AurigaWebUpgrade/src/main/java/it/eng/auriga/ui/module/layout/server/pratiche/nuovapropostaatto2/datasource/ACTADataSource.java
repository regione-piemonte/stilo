/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ACTABean;
import it.eng.client.EsportazioneDocActaImpl;
import it.eng.core.performance.PerformanceLogger;
import it.eng.document.function.bean.acta.ActaInputGetClassificazioneEstesa;
import it.eng.document.function.bean.acta.ActaInputGetDestinatariSmistamento;
import it.eng.document.function.bean.acta.ActaInputGetFascicoloDossierInVoceTitolario;
import it.eng.document.function.bean.acta.ActaNodoSmistamento;
import it.eng.document.function.bean.acta.ActaOutputGetClassificazioneEstesa;
import it.eng.document.function.bean.acta.ActaOutputGetDestinatariSmistamento;
import it.eng.document.function.bean.acta.ActaOutputGetFascicoloDossierInVoceTitolario;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;

@Datasource(id = "ACTADataSource")
public class ACTADataSource extends AbstractFetchDataSource<ActaNodoSmistamento> {

	private static final Logger logger = Logger.getLogger(ACTADataSource.class);
	
	@Override
	public PaginatorBean<ActaNodoSmistamento> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {
		EsportazioneDocActaImpl lEsportazioneDocActaImpl = new EsportazioneDocActaImpl();
		List<ActaNodoSmistamento> data = new ArrayList<ActaNodoSmistamento>();		
		ActaInputGetDestinatariSmistamento input = new ActaInputGetDestinatariSmistamento();		
		input.setAooCode(getExtraparams().get("aooCode"));
		input.setStructurCode(getExtraparams().get("structurCode"));
		ActaOutputGetDestinatariSmistamento output = null;
		try {
			PerformanceLogger lPerformanceLogger = new PerformanceLogger("ACTAGetDestinatariSmistamento (AooCode: " + input.getAooCode() + ", StructureCode: " + input.getStructurCode() + ")");
			lPerformanceLogger.start();		
			output = lEsportazioneDocActaImpl.getdestinatarismistamento(getLocale(), input);
			lPerformanceLogger.end();		
		} catch(Exception e) {
			String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di ACTA (GetDestinatariSmistamento)";
			logger.error(errorMessage + ": " + e.getMessage(), e);
			throw new StoreException(errorMessage);
		}
		if (output.getEsitoChiamata() != null && output.getEsitoChiamata().isOk()) {
			if(output.getNodoSmistamento() != null) {
				data = output.getNodoSmistamento();		
			}
		} else {
			String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di ACTA (GetDestinatariSmistamento)";
			logger.error(errorMessage + ": " + output.getEsitoChiamata().getMessaggio());
			throw new StoreException(errorMessage);
		}		
		PaginatorBean<ActaNodoSmistamento> paginatorBean = new PaginatorBean<ActaNodoSmistamento>();
		paginatorBean.setData(data);
		paginatorBean.setStartRow(0);
		paginatorBean.setEndRow(data.size());
		paginatorBean.setTotalRows(data.size());
		return paginatorBean;
	}

	public ACTABean getClassificazioneEstesa(ACTABean bean) throws Exception {
		EsportazioneDocActaImpl lEsportazioneDocActaImpl = new EsportazioneDocActaImpl();
		ActaInputGetClassificazioneEstesa input = new ActaInputGetClassificazioneEstesa();	
		input.setAooCode(getExtraparams().get("aooCode"));
		input.setStructurCode(getExtraparams().get("structurCode"));
		input.setIndiceClassificazioneEstesa(bean.getIndiceClassificazioneEstesa());
		ActaOutputGetClassificazioneEstesa output = null;
		try {
			PerformanceLogger lPerformanceLogger = new PerformanceLogger("ACTAGetClassificazioneEstesa (AooCode: " + input.getAooCode() + ", StructureCode: " + input.getStructurCode() + ", IndiceClassificazioneEstesa: " + bean.getIndiceClassificazioneEstesa() + ")");
			lPerformanceLogger.start();		
			output = lEsportazioneDocActaImpl.getclassificazioneestesa(getLocale(), input);
			lPerformanceLogger.end();		
		} catch(Exception e) {
			String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di ACTA (GetClassificazioneEstesa)";
			logger.error(errorMessage + ": " + e.getMessage(), e);
			throw new StoreException(errorMessage);
		}
		if (output.getEsitoChiamata() != null && output.getEsitoChiamata().isOk()) {
			if(output.getPresenzaIndiceClassificazione() != null && output.getPresenzaIndiceClassificazione()) {
				bean.setPresenzaIndiceClassificazione(true);						
			} else {
				throw new StoreException("Nessuna corrispondenza trovata in ACTA");	
			}
		} else {
			String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di ACTA (GetClassificazioneEstesa)";
			logger.error(errorMessage + ": " + output.getEsitoChiamata().getMessaggio());
			throw new StoreException(errorMessage);
		}
		return bean;
	}
	
	public ACTABean getFascicoloDossierInVoceTitolario(ACTABean bean) throws Exception {
		EsportazioneDocActaImpl lEsportazioneDocActaImpl = new EsportazioneDocActaImpl();
		ActaInputGetFascicoloDossierInVoceTitolario input = new ActaInputGetFascicoloDossierInVoceTitolario();
		input.setAooCode(getExtraparams().get("aooCode"));
		input.setStructurCode(getExtraparams().get("structurCode"));
		input.setDescrizioneVoceTitolario(bean.getDescrizioneVoceTitolario());
		input.setCodiceFascicoloDossier(bean.getCodiceFascicoloDossier());
		input.setSuffissoCodiceFascicoloDossier(bean.getSuffissoCodiceFascicoloDossier());
		input.setNumeroSottofascicolo(bean.getCodiceSottofascicoloDossier());
		ActaOutputGetFascicoloDossierInVoceTitolario output = null;
		try {
			PerformanceLogger lPerformanceLogger = new PerformanceLogger("ACTAGetFascicoloDossierInVoceTitolario (AooCode: " + input.getAooCode() + ", StructureCode: " + input.getStructurCode() + ", DescrizioneVoceTitolario: " + bean.getDescrizioneVoceTitolario() + ", CodiceFascicoloDossier: " + bean.getCodiceFascicoloDossier() +  ", SuffissoCodiceFascicoloDossier: " + bean.getSuffissoCodiceFascicoloDossier() +  ", CodiceSottofascicoloDossier: " + bean.getCodiceSottofascicoloDossier() + ")");
			lPerformanceLogger.start();		
			output = lEsportazioneDocActaImpl.getfascicolodossierinvocetitolario(getLocale(), input);
			lPerformanceLogger.end();		
		} catch(Exception e) {
			String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di ACTA (GetFascicoloDossierInVoceTitolario)";
			logger.error(errorMessage + ": " + e.getMessage(), e);
			throw new StoreException(errorMessage);
		}
		if (output.getEsitoChiamata() != null && output.getEsitoChiamata().isOk()) {
			if(StringUtils.isNotBlank(output.getIdFascicoloDossier())) {
				bean.setIdFascicoloDossier(output.getIdFascicoloDossier());						
			} else {
				throw new StoreException("Nessuna corrispondenza trovata in ACTA");	
			}		
		} else {
			String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di ACTA (GetFascicoloDossierInVoceTitolario)";
			logger.error(errorMessage + ": " + output.getEsitoChiamata().getMessaggio());
			throw new StoreException(errorMessage);
		}
		return bean;
	}	
	
}
