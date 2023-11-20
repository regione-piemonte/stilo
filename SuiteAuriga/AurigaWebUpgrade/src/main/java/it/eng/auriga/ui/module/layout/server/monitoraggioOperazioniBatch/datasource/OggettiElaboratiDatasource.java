/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerTrovaoggrichopbatchmassivaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.monitoraggioOperazioniBatch.datasource.bean.FiltriOggettiElaboratiBean;
import it.eng.auriga.ui.module.layout.server.monitoraggioOperazioniBatch.datasource.bean.OggettiElaboratiBean;
import it.eng.client.DmpkBmanagerTrovaoggrichopbatchmassiva;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author Ottavio Passalacqua
 *
 */

@Datasource(id="OggettiElaboratiDatasource")
public class OggettiElaboratiDatasource extends AurigaAbstractFetchDatasource<OggettiElaboratiBean>{

	private static final Logger log = Logger.getLogger(OggettiElaboratiDatasource.class);

	@Override
	public String getNomeEntita() {
		return "monitoraggio_operazioni_batch_lista_oggetti_elab_con_successo";
	};

	@Override
	public OggettiElaboratiBean get(OggettiElaboratiBean bean) throws Exception {

		// TODO Auto-generated method stub
		return bean;
	}	
	
	@Override
	public PaginatorBean<OggettiElaboratiBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		
		// Inizializzo l'INPUT		
		DmpkBmanagerTrovaoggrichopbatchmassivaBean input =  createFetchInput(criteria, token, idUserLavoro);
	/*	
	     col. 1: Tipo di oggetto elaborato
		 col. 2: ID dell'oggetto elaborato
		 col. 3: Data e ora di ultima elaborazione (nel formato dato dal parametro di conf. FMT_STD_TIMESTAMP)
		 col. 4: Esito dell'elaborazione: OK o KO
		 col. 5: Codice di eventuale errore verificatosi nell'elaborazione
		 col. 6: Contesto e messaggio di eventuale errore verificatosi nell'elaborazione
		 col. 7: N° di elaborazioni 
		 col. 8: Estremi dell'oggetto elaborato
         col. 9: Flag 1/0 : se 1 indica che c'è XML con dettagli sull'elaborazioni dell'oggetto
     */													
		
        // Inizializzo l'OUTPUT
		DmpkBmanagerTrovaoggrichopbatchmassiva lservice  = new DmpkBmanagerTrovaoggrichopbatchmassiva();
		StoreResultBean<DmpkBmanagerTrovaoggrichopbatchmassivaBean> output = lservice.execute(getLocale(), loginBean, input);
		
		boolean overflow = false;
		
		String defaultMessage = output.getDefaultMessage();
		if(StringUtils.isNotBlank(defaultMessage)) {
			if(output.isInError()) {
				throw new StoreException(defaultMessage);		
			} else {
				overflow = manageOverflow(defaultMessage);
			}
		}
		
		List<OggettiElaboratiBean> data = new ArrayList<OggettiElaboratiBean>();
		if (output.getResultBean().getNrototrecout() != null){		
			data = XmlListaUtility.recuperaLista(output.getResultBean().getResultout(), OggettiElaboratiBean.class);
		}
		
		PaginatorBean<OggettiElaboratiBean> lPaginatorBean = new PaginatorBean<OggettiElaboratiBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);
		return lPaginatorBean;
	}

   @Override
	public OggettiElaboratiBean update(OggettiElaboratiBean bean, OggettiElaboratiBean oldvalue) throws Exception {
		// TODO Auto-generated method stub		
		return bean;		
	}

	@Override
	public OggettiElaboratiBean add(OggettiElaboratiBean bean) throws Exception {
        // TODO Auto-generated method stub		
		return bean;		
	}

	@Override
	public OggettiElaboratiBean remove(OggettiElaboratiBean bean) throws Exception {
		// TODO Auto-generated method stub
		return bean;
	}
	
	
	/**
	 * @param criteria
	 * @param token
	 * @param idUserLavoro
	 * @return
	 * @throws Exception
	 * @throws JAXBException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	protected DmpkBmanagerTrovaoggrichopbatchmassivaBean createFetchInput(AdvancedCriteria criteria, String token, String idUserLavoro) throws Exception, JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = 1;			// 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;
		
		String idRichiesta = null;
		
        FiltriOggettiElaboratiBean filtri = new FiltriOggettiElaboratiBean();	
		
		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){					

				if("idRichiesta".equals(crit.getFieldName())) {
					idRichiesta = getTextFilterValue(crit);	
				} 
			    else if("tipoOggetti".equals(crit.getFieldName())) {
					filtri.setTipoOggetti(getTextFilterValue(crit));	
				} 			
				else if("esitoElaborazione".equals(crit.getFieldName())) {
					filtri.setEsitoElaborazione(getTextFilterValue(crit));
				} 
				else if("ctxMsgErrore".equals(crit.getFieldName())) {
					filtri.setCtxMsgErrore(getTextFilterValue(crit));
				} 
				else if ("dtFineUltimaElaborazione".equals(crit.getFieldName())) {
					Date[] estremiData = getDateConOraFilterValue(crit);
					if (estremiData[0] != null) {
						filtri.setDtFineUltimaElaborazioneDa(estremiData[0]);
					}
					if (estremiData[1] != null) {
						filtri.setDtFineUltimaElaborazioneA(estremiData[1]);
					}
				}
			}
		}
		
		// Inizializzo l'INPUT		
		DmpkBmanagerTrovaoggrichopbatchmassivaBean input = new DmpkBmanagerTrovaoggrichopbatchmassivaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setColorderbyio(colsOrderBy);		
		input.setFlgdescorderbyio(flgDescOrderBy);		
		input.setFlgsenzapaginazionein((flgSenzaPaginazione == null) ? 0 : flgSenzaPaginazione);
		input.setNropaginaio(numPagina);
		input.setBachsizeio(numRighePagina);
		input.setOverflowlimitin(null);
		input.setFlgsenzatotin(null);
				
		// Setto i filtri all'input del servizio
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		input.setFiltriio(lXmlUtilitySerializer.bindXml(filtri));
		input.setIdrichiestaopmassivain(idRichiesta);
		
		return input;
	}

	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
		// TODO Auto-generated method stub
		AdvancedCriteria criteria = bean.getCriteria();
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Inizializzo l'INPUT		
		DmpkBmanagerTrovaoggrichopbatchmassivaBean input = createFetchInput(criteria, token, idUserLavoro);
		input.setOverflowlimitin(-2);
		
		// Inizializzo l'OUTPUT
		DmpkBmanagerTrovaoggrichopbatchmassiva lservice = new DmpkBmanagerTrovaoggrichopbatchmassiva();
		StoreResultBean<DmpkBmanagerTrovaoggrichopbatchmassivaBean> output = lservice.execute(getLocale(), loginBean, input);
				
		String defaultMessage = output.getDefaultMessage();
		if(StringUtils.isNotBlank(defaultMessage)) {
				if(output.isInError()) {
					throw new StoreException(defaultMessage);		
				} 
		}		
		
		//imposto l'id del job creato
		Integer jobId = output.getResultBean().getBachsizeio();
		bean.setIdAsyncJob(jobId);
		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), OggettiElaboratiBean.class.getName());
		
		updateJob(loginBean, bean, jobId, loginBean.getIdUser());
		
	    if(jobId!=null && jobId > 0){
			String mess = "Schedulata esportazione su file registrata con Nr. " + jobId.toString() + " .Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}
		return bean;
	}

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {
		// TODO Auto-generated method stub
		AdvancedCriteria criteria = bean.getCriteria();
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		// Inizializzo l'INPUT	
		DmpkBmanagerTrovaoggrichopbatchmassivaBean input = createFetchInput(criteria, token, idUserLavoro);
		
		//non voglio overflow
		input.setOverflowlimitin(-1);
		
		//non mi interessano le colonne ritornate, solo il numero dei record
		//input.setColtoreturnin("1");
		
		// Inizializzo l'OUTPUT		
		DmpkBmanagerTrovaoggrichopbatchmassiva lservice = new DmpkBmanagerTrovaoggrichopbatchmassiva();
		StoreResultBean<DmpkBmanagerTrovaoggrichopbatchmassivaBean> output = lservice.execute(getLocale(), loginBean, input);
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
				if(output.isInError()) {
					throw new StoreException(output);		
				} else {
					addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
				}
		}
		bean.setNroRecordTot(output.getResultBean().getNrototrecout());
		return bean;
	}	
}