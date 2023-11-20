/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.Marshaller;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerLoadrigafoglioximportBean;
import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerTrovarighefoglioximportBean;
import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerUpdrigafoglioximportBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.foglioImportato.bean.ContenutoFoglioImportatoBean;
import it.eng.auriga.ui.module.layout.server.foglioImportato.bean.ContenutoFoglioImportatoFilterXmlBean;
import it.eng.auriga.ui.module.layout.server.foglioImportato.bean.ContenutoFoglioImportatoXmlBean;
import it.eng.auriga.ui.module.layout.server.foglioImportato.bean.ContenutoFoglioImportatoXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.foglioImportato.bean.FileAssociatoFoglioBean;
import it.eng.auriga.ui.module.layout.server.foglioImportato.bean.ParametroRigaFoglioXmlBean;
import it.eng.client.DmpkBmanagerLoadrigafoglioximport;
import it.eng.client.DmpkBmanagerTrovarighefoglioximport;
import it.eng.client.DmpkBmanagerUpdrigafoglioximport;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

/**
 * 
 * @author DANCRIST
 *
 */

@Datasource(id = "ContenutoFoglioImportatoDataSource")
public class ContenutoFoglioImportatoDataSource extends AurigaAbstractFetchDatasource<ContenutoFoglioImportatoBean> {

	@Override
	public PaginatorBean<ContenutoFoglioImportatoBean> fetch(AdvancedCriteria criteria, Integer startRow,
			Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		boolean overflow = false;
		
		DmpkBmanagerTrovarighefoglioximportBean input = createTrovarighefoglioximportBean(criteria, loginBean);
		
		DmpkBmanagerTrovarighefoglioximport dmpkBmanagerTrovarighefoglioximport = new DmpkBmanagerTrovarighefoglioximport();
		StoreResultBean<DmpkBmanagerTrovarighefoglioximportBean> output = dmpkBmanagerTrovarighefoglioximport.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				overflow = manageOverflow(output.getDefaultMessage());
				if(!overflow) {
					addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
				}
			}
		}
		
		String xmlOut = output.getResultBean().getRighexmlout();
		List<ContenutoFoglioImportatoXmlBean> storedProcedureResults = XmlListaUtility.recuperaLista(xmlOut, ContenutoFoglioImportatoXmlBean.class);
		List<ContenutoFoglioImportatoBean> returnValue = new ArrayList<ContenutoFoglioImportatoBean>(storedProcedureResults.size());	
		
		getSession().setAttribute(FETCH_SESSION_KEY, storedProcedureResults);
		
		for (ContenutoFoglioImportatoXmlBean contenutoFoglioXmlBean : storedProcedureResults){			
			ContenutoFoglioImportatoBean currentRetrievedBean = new ContenutoFoglioImportatoBean();
			BeanUtilsBean2.getInstance().copyProperties(currentRetrievedBean, contenutoFoglioXmlBean);
			returnValue.add(currentRetrievedBean);
		}
		
		PaginatorBean<ContenutoFoglioImportatoBean> lPaginatorBean = new PaginatorBean<ContenutoFoglioImportatoBean>();
		lPaginatorBean.setData(returnValue);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? returnValue.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(returnValue.size());
		lPaginatorBean.setOverflow(overflow);
		
		return lPaginatorBean;
	}
	
	private DmpkBmanagerTrovarighefoglioximportBean createTrovarighefoglioximportBean(AdvancedCriteria criteria, AurigaLoginBean loginBean) throws Exception {
		
		ContenutoFoglioImportatoFilterXmlBean filterFoglioXmlBean = new ContenutoFoglioImportatoFilterXmlBean();
		
		String nomeFoglio = null;
		String stato = null;
		Date tsUploadDa = null;		
		Date tsUploadA = null;	
		Date tsInizioElabDa = null;		
		Date tsInizioElabA = null;	
		Date tsFineElabDa = null;		
		Date tsFineElabA = null;	
		String nroRigaDa = null;
		String nroRigaA = null;
		String esitoElaborazione = null;
		String messaggioErrore = null;
		String tipiContenuto = null;
		String valoreCampoRiga = null;
		
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("nomeFoglio".equals(crit.getFieldName())) {
					nomeFoglio = getTextFilterValue(crit);
				} else if ("stati".equals(crit.getFieldName())) {
					stato = getTextFilterValue(crit);
				} else if ("tsUpload".equals(crit.getFieldName())) {
					Date[] tsUpload = getDateConOraFilterValue(crit);
					if (tsUploadDa != null) {
						tsUploadDa = tsUploadDa.compareTo(tsUpload[0]) < 0 ? tsUpload[0] : tsUploadDa;
					} else {
						tsUploadDa = tsUpload[0];
					}
					if (tsUploadA != null) {
						tsUploadA = tsUploadA.compareTo(tsUpload[1]) > 0 ? tsUpload[1] : tsUploadA;
					} else {
						tsUploadA = tsUpload[1];
					}
				} else if ("tsInizioElab".equals(crit.getFieldName())) {
					Date[] tsInizioElab = getDateConOraFilterValue(crit);
					if (tsInizioElabDa != null) {
						tsInizioElabDa = tsInizioElabDa.compareTo(tsInizioElab[0]) < 0 ? tsInizioElab[0] : tsInizioElabDa;
					} else {
						tsInizioElabDa = tsInizioElab[0];
					}
					if (tsInizioElabA != null) {
						tsInizioElabA = tsInizioElabA.compareTo(tsInizioElab[1]) > 0 ? tsInizioElab[1] : tsInizioElabA;
					} else {
						tsInizioElabA = tsInizioElab[1];
					}
				}  else if ("tsFineElab".equals(crit.getFieldName())) {
					Date[] tsFineElab = getDateConOraFilterValue(crit);
					if (tsFineElabDa != null) {
						tsFineElabDa = tsFineElabDa.compareTo(tsFineElab[0]) < 0 ? tsFineElab[0] : tsFineElabDa;
					} else {
						tsFineElabDa = tsFineElab[0];
					}
					if (tsFineElabA != null) {
						tsFineElabA = tsFineElabA.compareTo(tsFineElab[1]) > 0 ? tsFineElab[1] : tsFineElabA;
					} else {
						tsFineElabA = tsFineElab[1];
					}
				} else if("nroRiga".equals(crit.getFieldName())) {
					String[] nroRiga = getNumberFilterValue(crit);
					nroRigaDa = nroRiga[0] != null ? nroRiga[0] : null;
					nroRigaA = nroRiga[1] != null ? nroRiga[1] : null;
				} else if("esitoElaborazione".equals(crit.getFieldName())) {
					esitoElaborazione = getTextFilterValue(crit);
				} else if ("messaggioErrore".equals(crit.getFieldName())) {
					messaggioErrore = getTextFilterValue(crit);
				} else if ("tipiContenuto".equals(crit.getFieldName())) {
					tipiContenuto = getTextFilterValue(crit);
				} else if("valoreCampoRiga".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						valoreCampoRiga = (String) map.get("id");
						if(StringUtils.isNotBlank(valoreCampoRiga)) {
							if (crit.getOperator().equals("iStartsWith")) {
			                	valoreCampoRiga = valoreCampoRiga + "%";
			                } else if (crit.getOperator().equals("iEndsWith")) {
			                	valoreCampoRiga = "%" + valoreCampoRiga;
			                } else if (crit.getOperator().equals("iContains")) {
			                	valoreCampoRiga = "%" + valoreCampoRiga + "%";
			                }
						}
					}
				}
			}
		}
		
		if(StringUtils.isNotBlank(nomeFoglio)) {
			filterFoglioXmlBean.setNomeFoglio(nomeFoglio);
		}
		if(StringUtils.isNotBlank(stato)) {
			filterFoglioXmlBean.setStati(stato);
		}
		if(tsUploadDa != null){
			filterFoglioXmlBean.setTsUploadDa(tsUploadDa);
		}
		if(tsUploadA != null){
			filterFoglioXmlBean.setTsUploadA(tsUploadA);
		}
		if(tsInizioElabDa != null){
			filterFoglioXmlBean.setTsInizioElabDa(tsInizioElabDa);
		}
		if(tsInizioElabA != null){
			filterFoglioXmlBean.setTsInizioElabA(tsInizioElabA);
		}
		if(tsFineElabDa != null){
			filterFoglioXmlBean.setTsFineElabDa(tsFineElabDa);
		}
		if(tsFineElabA != null){
			filterFoglioXmlBean.setTsFineElabA(tsFineElabA);
		}
		if(StringUtils.isNotBlank(nroRigaDa)) {
			filterFoglioXmlBean.setNroRigaDa(nroRigaDa);
		}
		if(StringUtils.isNotBlank(nroRigaA)) {
			filterFoglioXmlBean.setNroRigaA(nroRigaA);
		}
		if(StringUtils.isNotBlank(esitoElaborazione)) {
			filterFoglioXmlBean.setEsitoElaborazione(esitoElaborazione);
		}
		if(StringUtils.isNotBlank(messaggioErrore)) {
			filterFoglioXmlBean.setMessaggioErrore(messaggioErrore);
		}
		if(StringUtils.isNotBlank(tipiContenuto)) {
			filterFoglioXmlBean.setTipiContenuto(tipiContenuto);
		}
		if(StringUtils.isNotBlank(valoreCampoRiga)) {
			filterFoglioXmlBean.setValoreCampoRiga(valoreCampoRiga);
		}
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String xmlFiltersFoglio = lXmlUtilitySerializer.bindXml(filterFoglioXmlBean);
		
		DmpkBmanagerTrovarighefoglioximportBean input = new DmpkBmanagerTrovarighefoglioximportBean();
		input.setFiltriio(xmlFiltersFoglio);
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
		input.setFiltriio(xmlFiltersFoglio);
		input.setColorderbyio(null);			
		input.setFlgsenzapaginazionein(new Integer(1));
		
		return input;
	}
	
	
//	public ListaParametriRigaFoglioBean getParametriRiga(ContenutoFoglioImportatoBean bean) throws Exception {
	@Override
	public ContenutoFoglioImportatoBean get(ContenutoFoglioImportatoBean bean) throws Exception {
					
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());			
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
				
		DmpkBmanagerLoadrigafoglioximportBean input = new DmpkBmanagerLoadrigafoglioximportBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdfoglioin(bean.getIdFoglio());
		input.setNrorigain(StringUtils.isNotBlank(bean.getNrRiga()) ? new BigDecimal(bean.getNrRiga()) : null);
		
		DmpkBmanagerLoadrigafoglioximport store = new DmpkBmanagerLoadrigafoglioximport();
        StoreResultBean<DmpkBmanagerLoadrigafoglioximportBean> output = store.execute(getLocale(), loginBean, input);
        
        if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
        
        List<ParametroRigaFoglioXmlBean> parametriRiga = new ArrayList<ParametroRigaFoglioXmlBean>();   
        if(StringUtils.isNotBlank(output.getResultBean().getValcolonneout())) {
        	parametriRiga = XmlListaUtility.recuperaLista(output.getResultBean().getValcolonneout(), ParametroRigaFoglioXmlBean.class);
        }
        
//        listaParametri.setParametriRiga(parametriRiga);
//        listaParametri.setIdFoglio(bean.getIdFoglio());
//        listaParametri.setNumRiga(bean.getNrRiga());
        
		//TODO da cancellare il parsing verrà fatto lato store, per ora per i TEST, decommentare 
        //manageSpaziNome(parametriRiga,"encode");
        
        bean.setParametriRiga(parametriRiga);
        
		return bean;		
	}
	

	@Override
	public ContenutoFoglioImportatoBean update(ContenutoFoglioImportatoBean bean, ContenutoFoglioImportatoBean oldvalue)throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());			
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkBmanagerUpdrigafoglioximportBean input = new DmpkBmanagerUpdrigafoglioximportBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);	
		input.setIdfoglioin(bean.getIdFoglio());
		input.setNrorigain(StringUtils.isNotBlank(bean.getNrRiga()) ? new BigDecimal(bean.getNrRiga()) : null);
		
		//TODO da cancellare il parsing verrà fatto lato store, per ora per i TEST, decommentare 
		//manageSpaziNome(bean.getParametriRiga(), "decode");
		
		Lista listaParametriRiga = new Lista();
        if(bean.getParametriRiga() != null) {
        	  for(int i = 0; i < bean.getParametriRiga().size(); i++) {              		  
        		  Riga riga = new Riga();
        		  Colonna col1 = new Colonna();
        		  col1.setNro(new BigInteger("1"));
        		  col1.setContent(bean.getParametriRiga().get(i).getNome());
        		  riga.getColonna().add(col1);
        		  Colonna col2 = new Colonna();
        		  col2.setNro(new BigInteger("2"));
        		  col2.setContent(bean.getParametriRiga().get(i).getValore());
        		  riga.getColonna().add(col2);
        		  
        		  listaParametriRiga.getRiga().add(riga);
        	  } 
        }
        
        StringWriter sw = new StringWriter();
		Marshaller marshaller = SingletonJAXBContext.getInstance().createMarshaller();
		marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
		marshaller.marshal(listaParametriRiga, sw);
		input.setValcolonnein(sw.toString());
         
		DmpkBmanagerUpdrigafoglioximport store = new DmpkBmanagerUpdrigafoglioximport();
        StoreResultBean<DmpkBmanagerUpdrigafoglioximportBean> output = store.execute(getLocale(), loginBean, input);
        
        if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
        
		return bean;		
	
	}
	
//	private void manageSpaziNome(List<ParametroRigaFoglioXmlBean> parametriRiga, String operazione) {
//		for(ParametroRigaFoglioXmlBean bean : parametriRiga) {
//			String replaceString = "col";
//			if("encode".equalsIgnoreCase(operazione)) {
//				replaceString = bean.getNome().replaceAll(" ", "_");
//			} else if("decode".equalsIgnoreCase(operazione)){
//				replaceString = bean.getNome().replaceAll("_", " ");
//			}
//			
//			bean.setNome(replaceString);
//		}
//	}

	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		AdvancedCriteria criteria = bean.getCriteria();
		
		DmpkBmanagerTrovarighefoglioximportBean input = createTrovarighefoglioximportBean(criteria, loginBean);
		input.setOverflowlimitin(-2);
		
		DmpkBmanagerTrovarighefoglioximport dmpkBmanagerTrovarighefoglioximport = new DmpkBmanagerTrovarighefoglioximport();
		StoreResultBean<DmpkBmanagerTrovarighefoglioximportBean> output = dmpkBmanagerTrovarighefoglioximport.execute(getLocale(), loginBean, input);
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
		}

		// imposto l'id del job creato
		Integer jobId = output.getResultBean().getBachsizeio() != null ? output.getResultBean().getBachsizeio() : 0;
		bean.setIdAsyncJob(jobId);

		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), ContenutoFoglioImportatoXmlBean.class.getName());

		saveRemapInformations(loginBean, jobId, createRemapConditionsMap(), ContenutoFoglioImportatoXmlBeanDeserializationHelper.class);

		updateJob(loginBean, bean, jobId, loginBean.getIdUser());

		if (jobId != null && jobId > 0) {
			String mess = "Richiesta di esportazione su file registrata con Nr. " + jobId.toString()
					+ " .Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}

		return null;
	}
	
	private Map<String, String> createRemapConditionsMap() {

		Map<String, String> retValue = new LinkedHashMap<String, String>();
		return retValue;
	}
	
	public MimeTypeFirmaBean calcolaInfoFile (FileAssociatoFoglioBean bean) throws Exception {
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		
		String uriFile = bean.getUri();	
		String displayFileName = bean.getDisplayFilnename();	
				
		InfoFileUtility lFileUtility = new InfoFileUtility();
		File fileAssociatoFoglio= StorageImplementation.getStorage().extractFile(uriFile);
		lMimeTypeFirmaBean= lFileUtility.getInfoFromFile(fileAssociatoFoglio.toURI().toString(), displayFileName, false, null);
		
		return lMimeTypeFirmaBean;
		
	}

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		AdvancedCriteria criteria = bean.getCriteria();
		
		DmpkBmanagerTrovarighefoglioximportBean input = createTrovarighefoglioximportBean(criteria, loginBean);
		input.setOverflowlimitin(-1);
		
		DmpkBmanagerTrovarighefoglioximport dmpkBmanagerTrovarighefoglioximport = new DmpkBmanagerTrovarighefoglioximport();
		StoreResultBean<DmpkBmanagerTrovarighefoglioximportBean> output = dmpkBmanagerTrovarighefoglioximport.execute(getLocale(), loginBean, input);
		
		String numTotRecOut = output.getResultBean().getNrototrecout() != null ? String.valueOf(output.getResultBean().getNrototrecout()) : "";

		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
		}

		NroRecordTotBean retValue = new NroRecordTotBean();
		retValue.setNroRecordTot(Integer.valueOf(numTotRecOut));
		
		return retValue;
	}
	
	@Override
	public String getNomeEntita() {
		return "contenuto_foglio_importato";
	}

}