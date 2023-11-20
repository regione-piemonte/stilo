/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocTrovaregemergenzaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.registrazioneEmergenza.datasource.bean.RegistrazioneEmergenzaXmlBean;
import it.eng.auriga.ui.module.layout.server.registrazioneEmergenza.datasource.bean.RegistrazioneEmergenzaXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.registrazioniEmergenza.datasource.bean.FiltriXML;
import it.eng.auriga.ui.module.layout.server.registrazioniEmergenza.datasource.bean.RegistrazioneEmergenzaBean;
import it.eng.auriga.ui.module.layout.server.registrazioniEmergenza.datasource.bean.SiglaRegistro;
import it.eng.client.DmpkRegistrazionedocTrovaregemergenza;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id="RegistrazioniEmergenzaDatasource")
public class RegistrazioniEmergenzaDatasource extends AurigaAbstractFetchDatasource<RegistrazioneEmergenzaBean>{
	
	@Override
	public PaginatorBean<RegistrazioneEmergenzaBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
				
		List<RegistrazioneEmergenzaBean> data = new ArrayList<RegistrazioneEmergenzaBean>();
		
		boolean overflow = false;
		
		DmpkRegistrazionedocTrovaregemergenzaBean input = createDmpkRegistrazionedocTrovaregemergenzaInput(criteria,loginBean);
		
		DmpkRegistrazionedocTrovaregemergenza dmpkRegistrazionedocTrovaregemergenza = new DmpkRegistrazionedocTrovaregemergenza();
		
		StoreResultBean<DmpkRegistrazionedocTrovaregemergenzaBean> output = dmpkRegistrazionedocTrovaregemergenza.execute(getLocale(), loginBean, input);
		
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			
			if (output.isInError()) {
				throw new StoreException(output);
			} else {
				overflow = manageOverflow(output.getDefaultMessage());
			}
		}
		
		if(output.getResultBean().getResultout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getResultout());			
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
			if(lista != null) {
				
				for (int i = 0; i < lista.getRiga().size(); i++) {		
					
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));		
					RegistrazioneEmergenzaBean registrazioneEmergenza = new RegistrazioneEmergenzaBean();	
					
					registrazioneEmergenza.setIdRegEmergenza(v.get(0)); //colonna 1
					registrazioneEmergenza.setRegistro(v.get(2)); //colonna 3
					registrazioneEmergenza.setNumero(v.get(6)); //colonna 7
					registrazioneEmergenza.setEffettuataIl(v.get(5) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(5)) : null); //colonna 6
					registrazioneEmergenza.setTipoProt(v.get(3)); //colonna 4
					registrazioneEmergenza.setEffettuataDa(v.get(8)); //colonna 9
					registrazioneEmergenza.setDesUoProt(v.get(7)); //colonna 8
					registrazioneEmergenza.setOggetto(v.get(9)); //colonna 10
					registrazioneEmergenza.setMittente(v.get(10)); //colonna 11
					registrazioneEmergenza.setDestinatari(v.get(13)); //colonna 14
					registrazioneEmergenza.setProtRicevuto(v.get(14)); //colonna 15
					registrazioneEmergenza.setDataOraArrivo(v.get(11) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(11)) : null);  //colonna 12
					registrazioneEmergenza.setRiversataIl(v.get(12) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(12)) : null);  //colonna 13
					registrazioneEmergenza.setFlgCreataDaMe(StringUtils.isNotBlank(v.get(15)) ? new Integer(v.get(15)) : new Integer(0)); //colonna 16
					
					data.add(registrazioneEmergenza);
					
				}
			}
		}			
		
		getSession().setAttribute(FETCH_SESSION_KEY, data);
					
		PaginatorBean<RegistrazioneEmergenzaBean> lPaginatorBean = new PaginatorBean<RegistrazioneEmergenzaBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);
		return lPaginatorBean;
	}
	
	protected DmpkRegistrazionedocTrovaregemergenzaBean createDmpkRegistrazionedocTrovaregemergenzaInput(AdvancedCriteria criteria,AurigaLoginBean loginBean) throws Exception {
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		String registriEmergenza = null;
		Date dataRegistrazioneDa = null;
		Date dataRegistrazioneA = null;								
		String nroRegistrazioneDa = null;
		String nroRegistrazioneA  = null;
		String flgCreataDaMe = null;
		String mittente = null;
		String destinatario = null;	
		String oggetto = null;	
		
		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){
				if("registriEmergenza".equals(crit.getFieldName())) {
					registriEmergenza = getTextFilterValue(crit);
				} else if("dataRegistrazione".equals(crit.getFieldName())) {
					Date[] estremiDataRegistrazione = getDateFilterValue(crit);
					dataRegistrazioneDa = estremiDataRegistrazione[0];
					dataRegistrazioneA = estremiDataRegistrazione[1];								
				} else if("nroRegistrazione".equals(crit.getFieldName())) {
					String[] estremiNroRegistrazione = getNumberFilterValue(crit);
					nroRegistrazioneDa = estremiNroRegistrazione[0];
					nroRegistrazioneA  = estremiNroRegistrazione[1];
				} else if("flgCreataDaMe".equals(crit.getFieldName())) {
					flgCreataDaMe = new Boolean(String.valueOf(crit.getValue())) ? "1" : null;
				} else if("mittente".equals(crit.getFieldName())) {
					mittente = getTextFilterValue(crit);	
				} else if("destinatario".equals(crit.getFieldName())) {
					destinatario = getTextFilterValue(crit);	
				} else if("oggetto".equals(crit.getFieldName())) {
					oggetto = getTextFilterValue(crit);	
				} 				
			}
		}                	
		
		FiltriXML scFiltriXML = new FiltriXML();
		if(StringUtils.isNotBlank(registriEmergenza)) {
	    	List<SiglaRegistro> sigleRegistri = new ArrayList<SiglaRegistro>();
	    	StringSplitterServer st = new StringSplitterServer(registriEmergenza, ";");
	    	while(st.hasMoreTokens()) {	    		
	    		SiglaRegistro siglaRegistro = new SiglaRegistro();
	    		siglaRegistro.setSigla(st.nextToken());
	    		sigleRegistri.add(siglaRegistro);
	    	}	    	
	    	scFiltriXML.setSigleRegistri(sigleRegistri);
		}
		if(dataRegistrazioneDa != null) {
			scFiltriXML.setDataRegEmergenzaDal(dataRegistrazioneDa);
        }
		if(dataRegistrazioneA != null) {
			scFiltriXML.setDataRegEmergenzaAl(dataRegistrazioneA);
        }
		if(StringUtils.isNotBlank(nroRegistrazioneDa)) {
			scFiltriXML.setNroRegEmergenzaDa(nroRegistrazioneDa);
        }
		if(StringUtils.isNotBlank(nroRegistrazioneA)) {
			scFiltriXML.setNroRegEmergenzaA(nroRegistrazioneA);
        }
		if(StringUtils.isNotBlank(flgCreataDaMe)) {
			scFiltriXML.setRegistratiDaMe(flgCreataDaMe);
	    }	
		if(StringUtils.isNotBlank(mittente)) {
			scFiltriXML.setMittente(mittente);
	    }
		if(StringUtils.isNotBlank(destinatario)) {
			scFiltriXML.setDestinatario(destinatario);
	    }
		if(StringUtils.isNotBlank(oggetto)) {
			scFiltriXML.setOggetto(oggetto);
	    }
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
	    String filtriXml = lXmlUtilitySerializer.bindXml(scFiltriXML);  
	       
		DmpkRegistrazionedocTrovaregemergenzaBean input = new DmpkRegistrazionedocTrovaregemergenzaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFiltrixmlin(filtriXml);		
		input.setColorderbyio(null);
		input.setFlgdescorderbyio(null);
		input.setFlgsenzapaginazionein(new Integer(1));
		input.setBachsizeio(null);
		input.setOverflowlimitin(null);
		input.setFlgsenzatotin(null);
		input.setFlgbatchsearchin(null);
		
		return input;
	}

	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
		
		AdvancedCriteria criteria = bean.getCriteria();
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		DmpkRegistrazionedocTrovaregemergenzaBean input = createDmpkRegistrazionedocTrovaregemergenzaInput(criteria,loginBean);
		input.setOverflowlimitin(-2);
		
		DmpkRegistrazionedocTrovaregemergenza dmpkRegistrazionedocTrovaregemergenza = new DmpkRegistrazionedocTrovaregemergenza();
		
		StoreResultBean<DmpkRegistrazionedocTrovaregemergenzaBean> output = dmpkRegistrazionedocTrovaregemergenza.execute(getLocale(), loginBean, input);
			
		if(output.getDefaultMessage() != null) {
			throw new StoreException(output);
		}
		
		String errorMessageOut = null;

		// imposto l'id del job creato
		Integer jobId = output.getResultBean().getBachsizeio();
		bean.setIdAsyncJob(jobId);

		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), RegistrazioneEmergenzaXmlBean.class.getName());
		
		saveRemapInformations(loginBean, jobId, createRemapConditionsMap(), RegistrazioneEmergenzaXmlBeanDeserializationHelper.class);

		updateJob(loginBean, bean, jobId, loginBean.getIdUser());

		if (jobId != null && jobId > 0) {
			String mess = "Schedulata esportazione su file registrata con Nr. " + jobId.toString()
					+ " .Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}

		return bean;
	}
	
	private Map<String, String> createRemapConditionsMap() {

		Map<String, String> retValue = new LinkedHashMap<String, String>();
		return retValue;
	}
	

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		AdvancedCriteria criteria = bean.getCriteria();
		
		DmpkRegistrazionedocTrovaregemergenzaBean input = createDmpkRegistrazionedocTrovaregemergenzaInput(criteria,loginBean);
		// non voglio overflow
		input.setOverflowlimitin(-1);
		
		DmpkRegistrazionedocTrovaregemergenza dmpkRegistrazionedocTrovaregemergenza = new DmpkRegistrazionedocTrovaregemergenza();
		StoreResultBean<DmpkRegistrazionedocTrovaregemergenzaBean> output = dmpkRegistrazionedocTrovaregemergenza.execute(getLocale(), loginBean, input);
		
		if(output.getDefaultMessage() != null) {
			throw new StoreException(output);
		}
		
		bean.setNroRecordTot(output.getResultBean().getNrototrecout());

		return bean;
	}	
	
}