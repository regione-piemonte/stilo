/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfTrovalistalavoroBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.DestInvioNotifica;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.UoCompetente;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.UoProponente;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.attiInLavorazione.bean.CriteriAvanzati;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.attiInLavorazione.bean.CriterioIterNonSvoltoBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.AssegnatarioXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.AttiCompletiBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.AttiCompletiXmlBean;
import it.eng.auriga.ui.module.layout.server.gestioneAtti.bean.AttiCompletiXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.utility.AttributiProcBean;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.utility.AttributiProcCreator;
import it.eng.auriga.ui.module.layout.server.gestioneProcedimenti.utility.AttributiProcCreator.AttributiName;
import it.eng.client.DmpkWfTrovalistalavoro;
import it.eng.document.function.bean.Flag;
import it.eng.utility.ui.html.HtmlNormalizeUtil;
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
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id="AttiCompletiInLavorazioneDataSource")
public class AttiCompletiInLavorazioneDataSource extends AurigaAbstractFetchDatasource<AttiCompletiBean>{

	@Override
	public PaginatorBean<AttiCompletiBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		boolean overflow = false;
		
		DmpkWfTrovalistalavoroBean input = createInputBean(false,criteria,loginBean);
				
		DmpkWfTrovalistalavoro lDmpkWfTrovalistalavoro = new DmpkWfTrovalistalavoro();
		StoreResultBean<DmpkWfTrovalistalavoroBean> lResultBean = lDmpkWfTrovalistalavoro.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);
		
		if (lResultBean.isInError()){
			throw new StoreException(lResultBean);
		} else {
			overflow = manageOverflow(lResultBean.getDefaultMessage());
		}
		
		String xmlOut = lResultBean.getResultBean().getListaxmlout();
		
		List<AttiCompletiXmlBean> storedProcedureResults = XmlListaUtility.recuperaLista(xmlOut, AttiCompletiXmlBean.class);
		List<AttiCompletiBean> returnValue = new ArrayList<AttiCompletiBean>(storedProcedureResults.size());	
		
		getSession().setAttribute(FETCH_SESSION_KEY, storedProcedureResults);
		
		for (AttiCompletiXmlBean attoXmlBean : storedProcedureResults){			
			AttiCompletiBean currentRetrievedBean = new AttiCompletiBean();
			BeanUtilsBean2.getInstance().copyProperties(currentRetrievedBean, attoXmlBean);
			currentRetrievedBean.setOggetto(HtmlNormalizeUtil.getPlainXmlWithCarriageReturn(currentRetrievedBean.getOggetto()));
			if(currentRetrievedBean.getNroInoltriRagioneria() != null) {
				if(currentRetrievedBean.getNroInoltriRagioneria().intValue() == 1) {
					currentRetrievedBean.setStatoRagioneria("N");					
				} else if(currentRetrievedBean.getNroInoltriRagioneria().intValue() > 1) {
					currentRetrievedBean.setStatoRagioneria("R");					
				}
			}
			if(attoXmlBean.getVisionato() != null && !"".equalsIgnoreCase(attoXmlBean.getVisionato())) {
				currentRetrievedBean.setAltFlgVisionato(attoXmlBean.getVisionato());
				currentRetrievedBean.setFlgVisionato("1");
			} else {
				currentRetrievedBean.setFlgVisionato("0");
			}
			if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_NUOVA_PROPOSTA_ATTO_2")) {
			} else {
				currentRetrievedBean.setUriModCopertina(ParametriDBUtil.getParametroDB(getSession(), "URI_MODELLO_COPERTINA_PROPOSTA_ATTO"));
				currentRetrievedBean.setTipoModCopertina(ParametriDBUtil.getParametroDB(getSession(), "TIPO_MODELLO_COPERTINA_PROPOSTA_ATTO"));
			}
			currentRetrievedBean.setFlgPrevistaNumerazione(attoXmlBean.getFlgPrevistaNumerazione());
			returnValue.add(currentRetrievedBean);
		}
		
		PaginatorBean<AttiCompletiBean> lPaginatorBean = new PaginatorBean<AttiCompletiBean>();
		lPaginatorBean.setData(returnValue);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? returnValue.size() - 1 : endRow);
		//TODO PAGINAZIONE START
//		lPaginatorBean.setTotalRows(returnValue.size());
		if(lResultBean.getResultBean().getNropaginaio() != null && lResultBean.getResultBean().getNropaginaio().intValue() > 0) {
			lPaginatorBean.setRowsForPage(input.getBachsizeio());
			lPaginatorBean.setTotalRows(lResultBean.getResultBean().getNrototrecout());
		} else {
			lPaginatorBean.setTotalRows(returnValue.size());			
		}
		//TODO PAGINAZIONE END
		lPaginatorBean.setOverflow(overflow);
		
		return lPaginatorBean;
	}
	
	private DmpkWfTrovalistalavoroBean createInputBean(boolean forNroRecordTot,AdvancedCriteria criteria, AurigaLoginBean loginBean) throws Exception {
		
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		String colOrderBy = null;
		String flgDescOrderBy = null;
		
		String idProcessType = null;
		Date dataDiAvvioDa = null;
		Date dataDiAvvioA = null;
		Boolean avviatiDaMe = null;
		String taskEffettuato = null;
		String taskEseguibile = null;
		String taskDaEffettuare = null;
		String inFase = null;
		String svoltaFase = null;
		String faseDaIniziare = null;
		String faseDaCompletare = null;
		List<DestInvioNotifica> assegnatario = null;
		Date dataInvioVerContDa = null;
		Date dataInvioVerContA = null;
		
		Integer overflowLimit = null;
		Integer bachSize = null;
		Integer nroPagina = null;
		
		List<DestInvioNotifica> uoProponente = null;
		
		List<DestInvioNotifica> uoCompetente = null;
		
		
		CriteriAvanzati scCriteriAvanzati = new CriteriAvanzati();	
		scCriteriAvanzati.setFlgSoloAttiConWF("1");
		scCriteriAvanzati.setFlgSoloEseguibili(null);
		
		if(criteria != null && criteria.getCriteria() != null){		
			for(Criterion crit : criteria.getCriteria()){
				if ("maxRecordVisualizzabili".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						String[] filterMaxRecordVisualizzabili = getNumberFilterValue(crit);
						overflowLimit = filterMaxRecordVisualizzabili[0] != null ? Integer.valueOf(filterMaxRecordVisualizzabili[0]) : null;
					}
				} else if ("nroRecordXPagina".equals(crit.getFieldName())) {
					//TODO PAGINAZIONE
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						String[] filterNroRecordXPagina = getNumberFilterValue(crit);
						bachSize = filterNroRecordXPagina[0] != null ? Integer.valueOf(filterNroRecordXPagina[0]) : null;
					}
				} else if ("nroPagina".equals(crit.getFieldName())) {
					//TODO PAGINAZIONE
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						String[] filterNroPagina = getNumberFilterValue(crit);
						nroPagina = filterNroPagina[0] != null ? Integer.valueOf(filterNroPagina[0]) : null;
					}
				} else if("idProcessType".equals(crit.getFieldName())) {
					idProcessType = getTextFilterValue(crit);					
				} else if("numeroPratica".equals(crit.getFieldName())) {
					String[] estremiNroProposta = getNumberFilterValue(crit);
					scCriteriAvanzati.setNroPropostaDa(estremiNroProposta[0]);
					scCriteriAvanzati.setNroPropostaA(estremiNroProposta[1]);					
				} else if("siglaProposta".equals(crit.getFieldName())) {
					scCriteriAvanzati.setSigleProposta(getTextFilterValue(crit));
				} else if("numeroRepertorio".equals(crit.getFieldName())) {
					String[] estremiNroAtto = getNumberFilterValue(crit);
					scCriteriAvanzati.setNroAttoDa(estremiNroAtto[0]);
					scCriteriAvanzati.setNroAttoA(estremiNroAtto[1]);					
				} else if("siglaRepertorio".equals(crit.getFieldName())) {
					scCriteriAvanzati.setSigleAtto(getTextFilterValue(crit));
				} else if("dataRepertorio".equals(crit.getFieldName())) {
					Date[] estremiDataAtto = getDateFilterValue(crit);
					scCriteriAvanzati.setDataAttoDa(estremiDataAtto[0]);
					scCriteriAvanzati.setDataAttoA(estremiDataAtto[1]);	
				} else if("oggetto".equals(crit.getFieldName())) {
					scCriteriAvanzati.setOggettoProposta(getTextFilterValue(crit));
				} else if("dataDiAvvio".equals(crit.getFieldName())) {
					Date[] estremiDataDiAvvio = getDateFilterValue(crit);
					dataDiAvvioDa = estremiDataDiAvvio[0];
					dataDiAvvioA = estremiDataDiAvvio[1];						
				} else if("avviatiDaMe".equals(crit.getFieldName())) {					
			    	if(crit.getValue() != null && (crit.getValue() instanceof Boolean)) {
			    		avviatiDaMe = (Boolean)crit.getValue();
			    	} else {
			        	String valueStr = (String) crit.getValue();
			        	avviatiDaMe = valueStr != null && ("TRUE".equals(valueStr) || "1".equals(valueStr) || "SI".equals(valueStr));	
			    	}			    	 					
				} else if("uoProponente".equals(crit.getFieldName())) {
					uoProponente = getListaSceltaOrganigrammaFilterValue(crit);
				} else if("uoCompetente".equals(crit.getFieldName())) {
					uoCompetente = getListaSceltaOrganigrammaFilterValue(crit);
				} else if("avviatiDa".equals(crit.getFieldName())) {
					scCriteriAvanzati.setIdUserAvvio(getTextFilterValue(crit));
				} else if("effettuatoTask".equals(crit.getFieldName())) {
					taskEffettuato = getTextFilterValue(crit);
				} else if("eseguibileTask".equals(crit.getFieldName())) {
					taskEseguibile = getTextFilterValue(crit);
				} else if("daEffettuareTask".equals(crit.getFieldName())) {
					taskDaEffettuare = getTextFilterValue(crit);
				} else if("inFase".equals(crit.getFieldName())) {
					inFase = getTextFilterValue(crit);
				} else if("svoltaFase".equals(crit.getFieldName())) {
					svoltaFase = getTextFilterValue(crit);
				} else if("daIniziareFase".equals(crit.getFieldName())) {
					faseDaIniziare = getTextFilterValue(crit);
				} else if("daCompletareFase".equals(crit.getFieldName())) {
					faseDaCompletare = getTextFilterValue(crit);
				} else if("assegnatario".equals(crit.getFieldName())) {
					assegnatario = getListaSceltaOrganigrammaFilterValue(crit);						
				} else if("dataInvioVerCont".equals(crit.getFieldName())) {
					Date[] estremiDataInvioVerCont = getDateConOraFilterValue(crit);
					dataInvioVerContDa = estremiDataInvioVerCont[0];
					dataInvioVerContA = estremiDataInvioVerCont[1];						 
				} else if("numeroEmendamento".equals(crit.getFieldName())) {
					String[] numeroEmendamento = getNumberFilterValue(crit);
					scCriteriAvanzati.setNroEmendamentoDa(numeroEmendamento[0]);
					scCriteriAvanzati.setNroEmendamentoA(numeroEmendamento[1]);
				} else if("parolaChiave".equals(crit.getFieldName())) {
					scCriteriAvanzati.setParoleChiave(getTextFilterValue(crit));
				} else if("stato".equals(crit.getFieldName())) {
					scCriteriAvanzati.setStato(getTextFilterValue(crit));
				} else if("centroDiCosto".equals(crit.getFieldName())) {
					scCriteriAvanzati.setCentroDiCosto(getTextFilterValue(crit));
				} else if("dataScadenza".equals(crit.getFieldName())) {
					Date[] estremiDataScadenza = getDateFilterValue(crit);
					scCriteriAvanzati.setDataScadenzaDa(estremiDataScadenza[0]);
					scCriteriAvanzati.setDataScadenzaA(estremiDataScadenza[1]);
				} else if("flgVisionato".equals(crit.getFieldName())) {
					scCriteriAvanzati.setFlgVisionato(new Boolean(String.valueOf(crit.getValue())) ? "1" : "0");
				} else if("tagApposti".equals(crit.getFieldName())) {
					scCriteriAvanzati.setTagApposti(getTextFilterValue(crit));
				} else if("dataIscrizioneOdG".equals(crit.getFieldName())) {
					Date[] dataIscrizioneOdG = getDateFilterValue(crit);
					scCriteriAvanzati.setIscrittoInOdgSedutaDataDa(dataIscrizioneOdG[0]);
					scCriteriAvanzati.setIscrittoInOdgSedutaDataA(dataIscrizioneOdG[1]);						
				} else if("determinaContrarreConGara".equals(crit.getFieldName())) {
					scCriteriAvanzati.setDeterminaContrarreConGara(getTextFilterValue(crit));
				} else if("tipoAffidamento".equals(crit.getFieldName())) {
					scCriteriAvanzati.setTipoAffidamento(getTextFilterValue(crit));					
				} else if("determinaRimodulazioneSpesaPostAggiudica".equals(crit.getFieldName())) {
						scCriteriAvanzati.setDeterminaRimodulazioneSpesaPostAggiudica(getTextFilterValue(crit));
				} else if("materieTipiAtto".equals(crit.getFieldName())) {
					scCriteriAvanzati.setMaterieTipiAtto(getTextFilterValue(crit));
				} else if("liquidazioneContestualeImpegni".equals(crit.getFieldName())) {
					scCriteriAvanzati.setLiquidazioneContestualeImpegni(getTextFilterValue(crit));
				} else if("liquidazioneContestualeAltriAspettiCont".equals(crit.getFieldName())) {
					scCriteriAvanzati.setLiquidazioneContestualeAltriAspettiCont(getTextFilterValue(crit));
				} else if("determinaAggiudicaGara".equals(crit.getFieldName())) {
					scCriteriAvanzati.setDeterminaAggiudicaGara(getTextFilterValue(crit));
				} else if("inoltroRagioneriaData".equals(crit.getFieldName())) {					
					if (crit.getValue() != null) {					
						Map map = (Map) crit.getValue();					
						idProcessType = (String) map.get("idProcessType");					
						List<DestInvioNotifica> listaAssegnatari = new ArrayList<DestInvioNotifica>();
						if (StringUtils.isNotBlank((String) map.get("assegnatario"))) {
							StringSplitterServer st = new StringSplitterServer((String) map.get("assegnatario"), ";");
							while (st.hasMoreTokens()) {
								DestInvioNotifica destInvioNotifica = new DestInvioNotifica();
								String chiave = st.nextToken();
								destInvioNotifica.setTipo(chiave.substring(0, 2));
								destInvioNotifica.setId(chiave.substring(2));
								listaAssegnatari.add(destInvioNotifica);
							}
						}
						assegnatario = listaAssegnatari;					
						if(StringUtils.isNotBlank((String) map.get("dtInoltroRagioneria"))) {
							dataInvioVerContDa = new SimpleDateFormat(FMT_STD_DATA).parse((String) map.get("dtInoltroRagioneria"));
							dataInvioVerContA = new SimpleDateFormat(FMT_STD_DATA).parse((String) map.get("dtInoltroRagioneria"));
							GregorianCalendar cal = new GregorianCalendar();
							cal.setTime(dataInvioVerContA);
							cal.add(Calendar.DAY_OF_YEAR, Integer.parseInt("1"));
							dataInvioVerContA = cal.getTime();		
						}	
					}					
				} else if("inoltroRagioneriaAssegnatario".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						Map map = (Map) crit.getValue();					
						idProcessType = (String) map.get("idProcessType");													
						List<DestInvioNotifica> listaAssegnatari = new ArrayList<DestInvioNotifica>();
						if (StringUtils.isNotBlank((String) map.get("assegnatario"))) {
							StringSplitterServer st = new StringSplitterServer((String) map.get("assegnatario"), ";");
							while (st.hasMoreTokens()) {
								DestInvioNotifica destInvioNotifica = new DestInvioNotifica();
								String chiave = st.nextToken();
								destInvioNotifica.setTipo(chiave.substring(0, 2));
								destInvioNotifica.setId(chiave.substring(2));
								listaAssegnatari.add(destInvioNotifica);
							}
						}
						assegnatario = listaAssegnatari;					
						if(StringUtils.isNotBlank((String) map.get("dtInoltroRagioneria"))) {
							dataInvioVerContDa = new SimpleDateFormat(FMT_STD_DATA).parse((String) map.get("dtInoltroRagioneria"));
							dataInvioVerContA = new SimpleDateFormat(FMT_STD_DATA).parse((String) map.get("dtInoltroRagioneria"));
							GregorianCalendar cal = new GregorianCalendar();
							cal.setTime(dataInvioVerContA);
							cal.add(Calendar.DAY_OF_YEAR, Integer.parseInt("1"));
							dataInvioVerContA = cal.getTime();		
						}
					}
				} else if("presenzaOpere".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						String presenzaOpereFilter = new Boolean(String.valueOf(crit.getValue())) ? "1" : "0";
						scCriteriAvanzati.setPresenzaOpere(presenzaOpereFilter);
					}
				} else if("opera".equals(crit.getFieldName())) {
					scCriteriAvanzati.setCodOpere(getTextFilterValue(crit));
				} else if("sottoTipologiaAtto".equals(crit.getFieldName())) {
					scCriteriAvanzati.setMaterieTipiAtto(getTextFilterValue(crit));
				} else if ("programmazioneAcquisti".equals(crit.getFieldName())) {
					scCriteriAvanzati.setFlgProgrammazioneAcquisti(getTextFilterValue(crit));
				} else if ("cui".equals(crit.getFieldName())) {
					scCriteriAvanzati.setCui(getTextFilterValue(crit));
				} else if ("cig".equals(crit.getFieldName())) {
					scCriteriAvanzati.setCig(getTextFilterValue(crit));
				} else if ("cup".equals(crit.getFieldName())) {
					scCriteriAvanzati.setCup(getTextFilterValue(crit));
				}
			}
		}
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		if (uoProponente != null) {
			List<UoProponente> listaIdUOProponentiProposta = new ArrayList<UoProponente>();
			for (DestInvioNotifica item : uoProponente) {
				UoProponente lUoProponente = new UoProponente();
				lUoProponente.setIdUo(item.getId());
				lUoProponente.setFlgIncludiSottoUo(item.getFlgIncludiSottoUO() == Flag.SETTED ? Flag.SETTED : Flag.NOT_SETTED);
				listaIdUOProponentiProposta.add(lUoProponente);
			}
			scCriteriAvanzati.setIdUOProponentiProposta(listaIdUOProponentiProposta);
		}
			
		if (uoCompetente != null) {
			List<UoCompetente> listaIdUOCompetentiProposta = new ArrayList<UoCompetente>();
			for (DestInvioNotifica item : uoCompetente) {
				UoCompetente lUoCompetente = new UoCompetente();
				lUoCompetente.setIdUo(item.getId());
				lUoCompetente.setFlgIncludiSottoUo(item.getFlgIncludiSottoUO() == Flag.SETTED ? Flag.SETTED : Flag.NOT_SETTED);
				listaIdUOCompetentiProposta.add(lUoCompetente);
			}
			scCriteriAvanzati.setIdUOCompetentiProposta(listaIdUOCompetentiProposta);
		}
		
		DmpkWfTrovalistalavoroBean input = new DmpkWfTrovalistalavoroBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCriteriavanzatiin(lXmlUtilitySerializer.bindXml(scCriteriAvanzati));
		//TODO PAGINAZIONE START
//		input.setFlgsenzapaginazionein(1);
//		input.setBachsizeio(bachSize);
//		input.setOverflowlimitin(overflowLimit);
		if(bachSize != null && bachSize.intValue() > 0) {
			input.setFlgsenzapaginazionein(null);
			input.setBachsizeio(bachSize);
			input.setNropaginaio(nroPagina != null ? nroPagina : new Integer(1));
		} else {
			input.setFlgsenzapaginazionein(1);
			input.setBachsizeio(overflowLimit);
			if (forNroRecordTot) {
				input.setOverflowlimitin(new Integer(-1));
//				input.setColtoreturnin("1");
			} else {
				input.setOverflowlimitin(overflowLimit);
			}
		}
		//TODO PAGINAZIONE END
		
		// Se in lista è attiva la paginazione o il limite di overflow, allora devo fare l'ordinamento lato server
		if((bachSize != null && bachSize.intValue() > 0) || (overflowLimit != null && overflowLimit.intValue() > 0)) {			
			HashSet<String> setNumColonneOrdinabili = new HashSet<String>(Arrays.asList("3","5","13","16","23","24","26","27","28","29","30","43","72","73","77","78","79","80","81","82","83","90","91","92","93","94","106","107","137","138"));
//			HashSet<String> setNumColonneOrdinabili = new HashSet<String>(Arrays.asList("3","5","13","23","24","26","43","81","93"));	
			String[] colAndFlgDescOrderBy = getColAndFlgDescOrderBy(AttiCompletiXmlBean.class, setNumColonneOrdinabili);
			colOrderBy = colAndFlgDescOrderBy[0];
			flgDescOrderBy = colAndFlgDescOrderBy[1];
			input.setColorderbyio(colOrderBy);
			input.setFlgdescorderbyio(flgDescOrderBy);
		}
				
		SimpleDateFormat dateFormatter = new SimpleDateFormat(FMT_STD_DATA);

		//Tipo procedimento
		input.setIdprocesstypeio(StringUtils.isNotBlank(idProcessType) ? new BigDecimal(idProcessType) : null);
		
		//Data di avvio
		if (dataDiAvvioDa != null){
			input.setDtavviodaio(dateFormatter.format(dataDiAvvioDa));
		}
		
		if (dataDiAvvioA != null){
			input.setDtavvioaio(dateFormatter.format(dataDiAvvioA));
		}
		
		//Avviati da me
		if (avviatiDaMe != null && avviatiDaMe){
			input.setFlgavviouserlavio(new Integer(1));
		}
		
		//In fase
		if(StringUtils.isNotEmpty(inFase)){
			input.setWfnamefasecorrio(inFase);        
		}
		
		//Eseguibile task
		if(StringUtils.isNotEmpty(taskEseguibile)){
			input.setWfnameattesegio(taskEseguibile);        
		}
		
		List<CriterioIterNonSvoltoBean> listaCriterioIterSvolto = new ArrayList<CriterioIterNonSvoltoBean>();
		List<CriterioIterNonSvoltoBean> listaCriterioIterNonSvolto = new ArrayList<CriterioIterNonSvoltoBean>();
		
		//Effettuato task - SVOLTO
		if (StringUtils.isNotEmpty(taskEffettuato)){			
			// i valori della combo tornano già con il ; li prendo a coppie
			String[] stringaSplittata = taskEffettuato.split(";");
			for (int i = 0 ; i < stringaSplittata.length; i++){
				CriterioIterNonSvoltoBean lCriterioIterSvoltoBean = new CriterioIterNonSvoltoBean();
				lCriterioIterSvoltoBean.setTipoFaseEvento("AE");
				lCriterioIterSvoltoBean.setNomeTaskFase(stringaSplittata[i]);
				listaCriterioIterSvolto.add(lCriterioIterSvoltoBean);
			}
		}

		//Task da effettuare - NON SVOLTO
		if (StringUtils.isNotEmpty(taskDaEffettuare)){			
			// i valori della combo tornano già con il ; li prendo a coppie
			String[] stringaSplittata = taskDaEffettuare.split(";");
			for (int i = 0 ; i < stringaSplittata.length; i++  ){
				CriterioIterNonSvoltoBean lCriterioIterNonSvoltoBean = new CriterioIterNonSvoltoBean();
				lCriterioIterNonSvoltoBean.setTipoFaseEvento("AE");
				lCriterioIterNonSvoltoBean.setNomeTaskFase(stringaSplittata[i]);
				listaCriterioIterNonSvolto.add(lCriterioIterNonSvoltoBean);
			}			
		}
		
		//Svolta fase - SVOLTO
		if (StringUtils.isNotEmpty(svoltaFase)){
			String[] stringaSplittata = svoltaFase.split(";");
			for (int i = 0 ; i < stringaSplittata.length; i++ ){
				CriterioIterNonSvoltoBean lCriterioIterSvoltoBean = new CriterioIterNonSvoltoBean();
				lCriterioIterSvoltoBean.setTipoFaseEvento("F");
				lCriterioIterSvoltoBean.setNomeTaskFase(stringaSplittata[i]);				
				listaCriterioIterSvolto.add(lCriterioIterSvoltoBean);
			}
		}
		
		//Fase da iniziare - NON SVOLTO
		if (StringUtils.isNotEmpty(faseDaIniziare)){
			String[] stringaSplittata = faseDaIniziare.split(";");
			for (int i = 0 ; i < stringaSplittata.length; i++ ){
				CriterioIterNonSvoltoBean lCriterioIterNonSvoltoBean = new CriterioIterNonSvoltoBean();
				lCriterioIterNonSvoltoBean.setTipoFaseEvento("F");
				lCriterioIterNonSvoltoBean.setNomeTaskFase(stringaSplittata[i]);
				lCriterioIterNonSvoltoBean.setStatoFaseEvento("1");
				listaCriterioIterNonSvolto.add(lCriterioIterNonSvoltoBean);
			}
		}
		
		//Fase da completare - SVOLTO
		if (StringUtils.isNotEmpty(faseDaCompletare)){
			String[] stringaSplittata = faseDaCompletare.split(";");
			for (int i = 0 ; i < stringaSplittata.length; i++ ){
				CriterioIterNonSvoltoBean lCriterioIterSvoltoBean = new CriterioIterNonSvoltoBean();
				lCriterioIterSvoltoBean.setTipoFaseEvento("F");
				lCriterioIterSvoltoBean.setNomeTaskFase(stringaSplittata[i]);
				lCriterioIterSvoltoBean.setStatoFaseEvento("0");
				listaCriterioIterSvolto.add(lCriterioIterSvoltoBean);
			}
		}
		
		//Invio alla verifica Ragioneria - SVOLTO
		if (dataInvioVerContDa != null || dataInvioVerContA != null){			
			
			CriterioIterNonSvoltoBean lCriterioIterSvoltoBean = new CriterioIterNonSvoltoBean();
			lCriterioIterSvoltoBean.setTipoFaseEvento("F");
			lCriterioIterSvoltoBean.setNomeTaskFase(ParametriDBUtil.getParametroDB(getSession(), "NOME_FASE_CONTABILE_ATTI"));
			lCriterioIterSvoltoBean.setDataInvioVerContDa(dataInvioVerContDa);
			lCriterioIterSvoltoBean.setDataInvioVerContA(dataInvioVerContA);
				
			listaCriterioIterSvolto.add(lCriterioIterSvoltoBean);
		}
		
		String criteriIterSvolto = lXmlUtilitySerializer.bindXmlList(listaCriterioIterSvolto);
		input.setCriteriitersvoltoio(criteriIterSvolto);	
		
		String criteriIterNonSvolto = lXmlUtilitySerializer.bindXmlList(listaCriterioIterNonSvolto);
		input.setCriteriiternonsvoltoio(criteriIterNonSvolto);
		
		//Assegnatario
		if (assegnatario != null){
			List<AssegnatarioXmlBean> listaSoggettiInt = new ArrayList<AssegnatarioXmlBean>();
			for(int i = 0; i < assegnatario.size(); i++) {
				DestInvioNotifica destInvioNotifica = assegnatario.get(i);
				AssegnatarioXmlBean assegnatarioXmlBean = new AssegnatarioXmlBean();
				assegnatarioXmlBean.setRuolo("ASSEGNATARIO_RAGIONERIA");
				assegnatarioXmlBean.setFlgUoSv(destInvioNotifica.getTipo());
				assegnatarioXmlBean.setIdUoSv(destInvioNotifica.getId());
				listaSoggettiInt.add(assegnatarioXmlBean);
			}								
			String soggettiInt = lXmlUtilitySerializer.bindXmlList(listaSoggettiInt);
			input.setSoggettiintio(soggettiInt);					
		}
		
		input.setAttributiprocio("");
		
		return input;
	}

	@Override
	public String getNomeEntita() {
		return "atti_in_lavorazione_completi";
	}
	
	public AttributiProcBean buildNumber(AttributiName pAttributiName, AdvancedCriteria criteria){
		return AttributiProcCreator.buildNumber(pAttributiName, criteria.getCriterionByFieldName(pAttributiName.getGuiValueFilter()));
	}

	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		AdvancedCriteria criteria = bean.getCriteria();
		
		DmpkWfTrovalistalavoroBean input = createInputBean(true,criteria,loginBean);
		input.setOverflowlimitin(-2);
		
		DmpkWfTrovalistalavoro lDmpkWfTrovalistalavoro = new DmpkWfTrovalistalavoro();
		StoreResultBean<DmpkWfTrovalistalavoroBean> output = lDmpkWfTrovalistalavoro.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);
		
		if (output.isInError()){
			throw new StoreException(output);
		}
		
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
		}

		// imposto l'id del job creato
		Integer jobId = output.getResultBean().getBachsizeio() != null ? output.getResultBean().getBachsizeio() : 0;
		bean.setIdAsyncJob(jobId);

		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), AttiCompletiXmlBean.class.getName());

		saveRemapInformations(loginBean, jobId, createRemapConditionsMap(), AttiCompletiXmlBeanDeserializationHelper.class);

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

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		AdvancedCriteria criteria = bean.getCriteria();
		
		DmpkWfTrovalistalavoroBean input = createInputBean(true,criteria,loginBean);
		input.setOverflowlimitin(-1);
		
		DmpkWfTrovalistalavoro lDmpkWfTrovalistalavoro = new DmpkWfTrovalistalavoro();
		StoreResultBean<DmpkWfTrovalistalavoroBean> lResultBean = lDmpkWfTrovalistalavoro.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), input);
		
		if (lResultBean.isInError()){
			throw new StoreException(lResultBean);
		}
		
		String numTotRecOut = lResultBean.getResultBean().getNrototrecout() != null ? String.valueOf(lResultBean.getResultBean().getNrototrecout()) : "";

		if(StringUtils.isNotBlank(lResultBean.getDefaultMessage())) {
			addMessage(lResultBean.getDefaultMessage(), "", MessageType.WARNING);
		}

		NroRecordTotBean retValue = new NroRecordTotBean();
		retValue.setNroRecordTot(Integer.valueOf(numTotRecOut));
		return retValue;
	}

}