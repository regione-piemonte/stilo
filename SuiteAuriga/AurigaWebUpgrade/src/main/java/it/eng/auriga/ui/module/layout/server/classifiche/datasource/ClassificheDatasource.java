/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityGrantprivsudefcontestoBean;
import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityRevokeprivsudefcontestoBean;

import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.function.bean.FindTitolarioObjectBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.classifiche.datasource.bean.ClassificheBean;
import it.eng.auriga.ui.module.layout.server.classifiche.datasource.bean.ListaClassificheBean;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.titolario.ClassificaXmlBean;
import it.eng.auriga.ui.module.layout.server.titolario.ClassificaXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.titolario.datasource.bean.TitolarioSezioneCache;
import it.eng.client.AurigaService;
import it.eng.client.DmpkDefSecurityGrantprivsudefcontesto;
import it.eng.client.DmpkDefSecurityRevokeprivsudefcontesto;

import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id="ClassificheDatasource")
public class ClassificheDatasource extends AurigaAbstractFetchDatasource<ClassificheBean>{
	
	@Override
	public PaginatorBean<ClassificheBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String filtroFullText = null;
		String[] checkAttributes = null;	
		
		boolean overflow = false;
		
		String idUo = this.getExtraparams().get("idUo");
		String classAbilita = this.getExtraparams().get("classificheAbilitate");
		String flgTpDestAbil = this.getExtraparams().get("flgTpDestAbil");
		
		List<ClassificheBean> data = new ArrayList<ClassificheBean>();
			
		FindTitolarioObjectBean lFindTitolarioObjectBean = createFindTitolarioObjectBean(criteria, loginBean, filtroFullText, checkAttributes, idUo, classAbilita, flgTpDestAbil);
	
		if(StringUtils.isNotBlank(filtroFullText) && (checkAttributes == null || checkAttributes.length == 0)) {
			addMessage("Specificare almeno un attributo su cui effettuare la ricerca full-text", "", MessageType.ERROR);			
		} else {	
			
				List<Object> resFinder = null;
				try {
						resFinder = AurigaService.getFind().findtitolarioobject(getLocale(), loginBean, lFindTitolarioObjectBean).getList();
				} catch (Exception e) {
					throw new StoreException(e.getMessage());
				}
			
				
				String xmlResultSetOut = (String) resFinder.get(0);
				String numTotRecOut = (String) resFinder.get(1);
				String numRecInPagOut = (String) resFinder.get(2);
				String xmlPercorsiOut = null;
				String dettagliCercaInFolderOut = null;
				String errorMessageOut = null;
				if (resFinder.size() > 3) {
					xmlPercorsiOut = (String) resFinder.get(3);
				}
				if (resFinder.size() > 4) {
					dettagliCercaInFolderOut = (String) resFinder.get(4);
				}
				if (resFinder.size() > 5) {
					errorMessageOut = (String) resFinder.get(5);
				}
				
				if (errorMessageOut != null && !"".equals(errorMessageOut)) {
					addMessage(errorMessageOut, "", MessageType.WARNING);
				}

				overflow = manageOverflow(errorMessageOut);
				
				// Conversione ListaRisultati ==> EngResultSet
				if (xmlResultSetOut != null) {
					StringReader sr = new StringReader(xmlResultSetOut);
					Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
					if (lista != null) {
						for (int i = 0; i < lista.getRiga().size(); i++) {
						
							Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
						
							ClassificheBean node = new ClassificheBean();	        		
							node.setNroLivello(v.get(0));                                                                          // colonna 1 
							node.setIdClassificazione(v.get(1));                                                                   // colonna 2 
							node.setDescrizione(v.get(2));                                                                         // colonna 3 
							node.setTipo(v.get(3));                                                                                // colonna 4 
							node.setDescrizioneEstesa(v.get(4));                                                                   // colonna 5 
							node.setParoleChiave(v.get(5));                                                                        // colonna 6 
							node.setIndice(v.get(6));                                                                              // colonna 7  				        			        	
							node.setTsValidaDal(v.get(7) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(7)) : null);     // colonna 8 
							node.setTsValidaFinoAl(v.get(8) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(8)) : null);  // colonna 9
														
							if(idUo!=null && !idUo.equalsIgnoreCase(""))
							   node.setFlgSelXFinalita(v.get(18) != null && "1".equals(v.get(18)));                                   // colonna 19         		 
							else
								node.setFlgSelXFinalita(true);
							
							node.setIdClassificaSup(v.get(19));                                                                    // colonna 20 
							node.setDesClassificaSup(v.get(20));                                                                   // colonna 21 	
							node.setScore(v.get(23) != null ? new BigDecimal(v.get(23)) : null);                                   // colonna 24 
							node.setIndiceXOrd(v.get(24));                                                                         // colonna 25 		        			        		
							node.setFlgAbilATutti(v.get(25));                                                                      // colonna 26 
							node.setIdUo(idUo);  
							data.add(node);
						}
					}
				}
		}
		
		// salvo i dati in sessione per renderli disponibili l'esportazione
		getSession().setAttribute(FETCH_SESSION_KEY, data);
		
		PaginatorBean<ClassificheBean> lPaginatorBean = new PaginatorBean<ClassificheBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);
		
		return lPaginatorBean;
	}	
	
	public FindTitolarioObjectBean createFindTitolarioObjectBean(AdvancedCriteria criteria, AurigaLoginBean loginBean,
			String filtroFullText, String[] checkAttributes, String idUo, String classAbilita, String flgTpDestAbil) throws Exception {
		
		String idUserLavoro = loginBean.getIdUserLavoro();	
		String[] flgObjectTypes = {"CL"};
		Integer searchAllTerms = null;
		Integer idFolder = null;
		String idPianoClassif = null;
		//in modalità  di esplora il valore di default è 0
		String includiSottoCartelle = "0";
		String tsRif = null;
		String advancedFilters = null;
		String customFilters = null;
		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = new Integer(1);
		Integer numPagina = null;
		Integer numRighePagina = null;
		String flgSenzaTot = "";
		Integer online = null;
		String colsToReturn = "1,2,3,4,5,6,7,8,9,19,20,21,22,24,25,26";
		String finalita = null;
		String flgSoloAttive = null;
		Date tsInizioVldDa = null;
		Date tsInizioVldA = null;
		Date tsFineVldDa = null;
		Date tsFineVldA = null;
		
		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){
				if("idFolder".equals(crit.getFieldName())) {
					if(StringUtils.isNotBlank((String) crit.getValue())) {
						idFolder = new Integer(String.valueOf(crit.getValue()));
					}
				} 
				else if("finalita".equals(crit.getFieldName())) {
					if(StringUtils.isNotBlank((String) crit.getValue())) {
						finalita = String.valueOf(crit.getValue());
					}
				} 
				else if("idPianoClassif".equals(crit.getFieldName())) {
					if(StringUtils.isNotBlank((String) crit.getValue())) {
						idPianoClassif = String.valueOf(crit.getValue());
					}
				} 
				if("tsRif".equals(crit.getFieldName())) {
					if(StringUtils.isNotBlank((String) crit.getValue())) {
						tsRif = String.valueOf(crit.getValue());
					}
				} else if("searchFulltext".equals(crit.getFieldName())) {
					//se sono entrato qui sono in modalitÃ  di ricerca con i filtri quindi imposto il valore di default a 1
					includiSottoCartelle = "1";
					if(crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						filtroFullText = (String) map.get("parole");
						ArrayList<String> lArrayList = (ArrayList<String>) map.get("attributi");
						checkAttributes = lArrayList != null ? lArrayList.toArray(new String[]{}) : null;
						Boolean flgRicorsiva = (Boolean) map.get("flgRicorsiva");
						if(flgRicorsiva != null) {
							includiSottoCartelle = flgRicorsiva ? "1" : "0";
						}
						String operator = crit.getOperator();
						if(StringUtils.isNotBlank(operator)) {
							if("allTheWords".equals(operator)) {
								searchAllTerms = new Integer("1");
							} else if ("oneOrMoreWords".equals(operator)) {
								searchAllTerms = new Integer("0");
							} 
						}
					}
				}
				//SOLO CLASSIFICHE ATTIVE
				else if("flgSoloAttive".equals(crit.getFieldName())) {
					if(crit.getValue() != null) {	
						flgSoloAttive = new Boolean(String.valueOf(crit.getValue())) ? "1" : null;
					}
				}
				// DATA INIZIO VALIDITA'
				else if("tsInizioVld".equals(crit.getFieldName())) {
					Date[] estremiInizioVld = getDateFilterValue(crit);
					if(tsInizioVldDa != null) {
						tsInizioVldDa = tsInizioVldDa.compareTo(estremiInizioVld[0]) < 0 ? estremiInizioVld[0] : tsInizioVldDa;
					} else {
						tsInizioVldDa = estremiInizioVld[0];
					}
					if(tsInizioVldA != null) {
						tsInizioVldA = tsInizioVldA.compareTo(estremiInizioVld[1]) > 0 ? estremiInizioVld[1] : tsInizioVldA;
					} else {
						tsInizioVldA = estremiInizioVld[1];
					}					
				}
				// DATA FINE VALIDITA'
				else if("tsFineVld".equals(crit.getFieldName())) {
					Date[] estremiFineVld = getDateFilterValue(crit);
					if(tsFineVldDa != null) {
						tsFineVldDa = tsFineVldDa.compareTo(estremiFineVld[0]) < 0 ? estremiFineVld[0] : tsFineVldDa;
					} else {
						tsFineVldDa = estremiFineVld[0];
					}
					if(tsFineVldA != null) {
						tsFineVldA = tsFineVldA.compareTo(estremiFineVld[1]) > 0 ? estremiFineVld[1] : tsFineVldA;
					} else {
						tsFineVldA = estremiFineVld[1];
					}					
				}
			}
		}
		
		TitolarioSezioneCache lTitolarioSezioneCache = new TitolarioSezioneCache();
		lTitolarioSezioneCache.setFlgSoloAttive(flgSoloAttive);
		lTitolarioSezioneCache.setTsFineVldA(tsFineVldA);
		lTitolarioSezioneCache.setTsFineVldDa(tsFineVldDa);
		lTitolarioSezioneCache.setTsInizioVldA(tsInizioVldA);
		lTitolarioSezioneCache.setTsInizioVldDa(tsInizioVldDa);
		lTitolarioSezioneCache.setFlgStatoAbil(classAbilita);
		lTitolarioSezioneCache.setFlgTpDestAbil(flgTpDestAbil);
		lTitolarioSezioneCache.setIdDestAbil(idUo);
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String lStrXml = lXmlUtilitySerializer.bindXml(lTitolarioSezioneCache);

		advancedFilters = lStrXml;
	        
		FindTitolarioObjectBean lFindTitolarioObjectBean = new FindTitolarioObjectBean();
		lFindTitolarioObjectBean.setUserIdLavoro(idUserLavoro);
		lFindTitolarioObjectBean.setFiltroFullText(filtroFullText);
		lFindTitolarioObjectBean.setCheckAttributes(checkAttributes);
		lFindTitolarioObjectBean.setFlgObjectTypes(flgObjectTypes);
		lFindTitolarioObjectBean.setSearchAllTerms(searchAllTerms);
		lFindTitolarioObjectBean.setIdClSearchIO(idFolder);
		lFindTitolarioObjectBean.setFlgSubClSearchIn(includiSottoCartelle);
		lFindTitolarioObjectBean.setTsRiferimento(tsRif);
		lFindTitolarioObjectBean.setAdvancedFilters(advancedFilters);
		lFindTitolarioObjectBean.setCustomFilters(customFilters);
		lFindTitolarioObjectBean.setColsOrderBy(colsOrderBy);
		lFindTitolarioObjectBean.setFlgDescOrderBy(flgDescOrderBy);
		lFindTitolarioObjectBean.setFlgSenzaPaginazione(flgSenzaPaginazione);
		lFindTitolarioObjectBean.setNumPagina(numPagina);
		lFindTitolarioObjectBean.setNumRighePagina(numRighePagina);
		lFindTitolarioObjectBean.setFlgSenzaTot(flgSenzaTot);
		lFindTitolarioObjectBean.setOnline(online);
		lFindTitolarioObjectBean.setColsToReturn(colsToReturn);
		lFindTitolarioObjectBean.setFinalita(finalita);

		if (StringUtils.isNotBlank(idPianoClassif)) {
			lFindTitolarioObjectBean.setType("ID_PIANO_CLASSIF");
			String[] lValues = { idPianoClassif };
			lFindTitolarioObjectBean.setValues(lValues);
		} else {
			String idDominio = null;
			if (loginBean.getDominio().split(":").length == 2) {
				idDominio = loginBean.getDominio().split(":")[1];
			}
			String[] lValues = { idDominio };
			lFindTitolarioObjectBean.setType("ID_SP_AOO");
			lFindTitolarioObjectBean.setValues(lValues);
		}
		lFindTitolarioObjectBean.setUserIdLavoro(loginBean.getIdUserLavoro());
		
		return lFindTitolarioObjectBean;
	}

	
	public ListaClassificheBean aggiungiClassificheAUO(ListaClassificheBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityGrantprivsudefcontestoBean input = new DmpkDefSecurityGrantprivsudefcontestoBean();	
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		input.setFlgtpobjprivtoin("UO");
		input.setIdobjprivtoin(new BigDecimal(bean.getIdUo()));
		input.setFlgtpobjtograntin("C");
		
		input.setListaprivilegiin("A;AF");
		
		Lista lista = new Lista();
		for(ClassificheBean funzione : bean.getListaClassifiche()) {			
			Riga riga = new Riga();
			Colonna col1 = new Colonna();
			col1.setNro(new BigInteger("1"));			
			col1.setContent(funzione.getIdClassificazione());			
			riga.getColonna().add(col1);
			
			lista.getRiga().add(riga);
		}
		
		StringWriter sw = new StringWriter();
		SingletonJAXBContext.getInstance().createMarshaller().marshal(lista, sw);				
		input.setListaobjtograntxmlin(sw.toString());
		
		DmpkDefSecurityGrantprivsudefcontesto grantPrivSuDefContesto = new DmpkDefSecurityGrantprivsudefcontesto();
		StoreResultBean<DmpkDefSecurityGrantprivsudefcontestoBean> output = grantPrivSuDefContesto.execute(getLocale(), loginBean, input);
		
		if(output.getDefaultMessage() != null) {
			if(output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		return bean;
	}
	
	public ListaClassificheBean rimuoviClassificheDaUO(ListaClassificheBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkDefSecurityRevokeprivsudefcontestoBean input = new DmpkDefSecurityRevokeprivsudefcontestoBean();	
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		input.setFlgtpobjprivtoin("UO");
		input.setIdobjprivtoin(new BigDecimal(bean.getIdUo()));
		input.setListaprivilegiin("");
		
		Lista lista = new Lista();
		for(ClassificheBean funzione : bean.getListaClassifiche()) {			
			Riga riga = new Riga();
			Colonna col1 = new Colonna();
			col1.setNro(new BigInteger("1"));			
			col1.setContent("C");			
			riga.getColonna().add(col1);	
			Colonna col2 = new Colonna();
			col2.setNro(new BigInteger("2"));			
			col2.setContent(funzione.getIdClassificazione());			
			riga.getColonna().add(col2);			
			lista.getRiga().add(riga);
		}
		
		StringWriter sw = new StringWriter();
		SingletonJAXBContext.getInstance().createMarshaller().marshal(lista, sw);				
		input.setListaobjtorevokexmlin(sw.toString());
		
		DmpkDefSecurityRevokeprivsudefcontesto revokePrivSuDefContesto = new DmpkDefSecurityRevokeprivsudefcontesto();
		StoreResultBean<DmpkDefSecurityRevokeprivsudefcontestoBean> output = revokePrivSuDefContesto.execute(getLocale(), loginBean, input);

		if(output.getDefaultMessage() != null) {
			if(output.isInError()) {
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		return bean;
	}


	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		AdvancedCriteria criteria = bean.getCriteria();
		
		String filtroFullText = null;
		String[] checkAttributes = null;
		
		String idUo = this.getExtraparams().get("idUo");
		String classAbilita = this.getExtraparams().get("classificheAbilitate");
		String flgTpDestAbil = this.getExtraparams().get("flgTpDestAbil");
		
		FindTitolarioObjectBean lFindTitolarioObjectBean = createFindTitolarioObjectBean(criteria, loginBean, filtroFullText, checkAttributes, idUo, classAbilita,flgTpDestAbil);
		lFindTitolarioObjectBean.setOverFlowLimit(-2);

		List<Object> resFinder = null;
		try {
			resFinder = AurigaService.getFind().findtitolarioobject(getLocale(), loginBean, lFindTitolarioObjectBean).getList();
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}

		String errorMessageOut = null;

		if (resFinder.size() > 5) {
			errorMessageOut = (String) resFinder.get(5);
		}

		if (errorMessageOut != null && !"".equals(errorMessageOut)) {
			addMessage(errorMessageOut, "", MessageType.WARNING);
		}
		

		// imposto l'id del job creato
		Integer jobId = Integer.valueOf((String) resFinder.get(6));
		bean.setIdAsyncJob(jobId);

		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), ClassificaXmlBean.class.getName());

		saveRemapInformations(loginBean, jobId, createRemapConditionsMap(), ClassificaXmlBeanDeserializationHelper.class);

		updateJob(loginBean, bean, jobId, loginBean.getIdUser());

		if (jobId != null && jobId > 0) {
			String mess = "Richiesta di esportazione su file registrata con Nr. " + jobId.toString() + " .Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}

		return null;
	}

	private Map<String, String> createRemapConditionsMap() {

		Map<String, String> retValue = new LinkedHashMap<String, String>();
		return retValue;
	}

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		AdvancedCriteria criteria = bean.getCriteria();
		
		String filtroFullText = null;
		String[] checkAttributes = null;
		
		String idUo = this.getExtraparams().get("idUo");
		String classAbilita = this.getExtraparams().get("classificheAbilitate");
		String flgTpDestAbil = this.getExtraparams().get("flgTpDestAbil");
		
		FindTitolarioObjectBean lFindTitolarioObjectBean = createFindTitolarioObjectBean(criteria, loginBean,  filtroFullText, checkAttributes, idUo, classAbilita,flgTpDestAbil);
		lFindTitolarioObjectBean.setOverFlowLimit(-1);
		lFindTitolarioObjectBean.setColsToReturn("1");

		List<Object> resFinder = null;
		try {
			resFinder = AurigaService.getFind().findtitolarioobject(getLocale(), loginBean, lFindTitolarioObjectBean).getList();
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		
		String numTotRecOut = (String) resFinder.get(1);

		String errorMessageOut = null;

		if (resFinder.size() > 5) {
			errorMessageOut = (String) resFinder.get(5);
		}
		
		if (errorMessageOut != null && !"".equals(errorMessageOut)) {
			addMessage(errorMessageOut, "", MessageType.WARNING);
		}

		NroRecordTotBean retValue = new NroRecordTotBean();
		retValue.setNroRecordTot(Integer.valueOf(numTotRecOut));
		
		return retValue;
	}
	
}