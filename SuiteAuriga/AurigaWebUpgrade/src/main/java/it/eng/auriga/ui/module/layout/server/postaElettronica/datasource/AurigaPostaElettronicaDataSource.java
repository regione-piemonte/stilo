/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailTrovaemailBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.DestInvioNotifica;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaXmlBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.XmlFilterPostaElettronicaBean;
import it.eng.client.DmpkIntMgoEmailTrovaemail;
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
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "AurigaPostaElettronicaDataSource")
public class AurigaPostaElettronicaDataSource extends AurigaAbstractFetchDatasource<PostaElettronicaBean> {

	private static Logger mLogger = Logger.getLogger(AurigaPostaElettronicaDataSource.class);

	@Override
	public PaginatorBean<PostaElettronicaBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		List<PostaElettronicaBean> data = new ArrayList<PostaElettronicaBean>();
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DmpkIntMgoEmailTrovaemailBean input = createInputBean(false, criteria, orderby);
		DmpkIntMgoEmailTrovaemail dmpkIntMgoEmailTrovaemail = new DmpkIntMgoEmailTrovaemail();
		StoreResultBean<DmpkIntMgoEmailTrovaemailBean> output = dmpkIntMgoEmailTrovaemail.execute(getLocale(), loginBean, input);

		boolean overflow = false;
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				overflow = manageOverflow(output.getDefaultMessage());
			}
		}

		// SETTO L'OUTPUT
		if (output.getResultBean().getResultout() != null) {
			String xml = output.getResultBean().getResultout();
			data = XmlListaUtility.recuperaLista(xml, PostaElettronicaBean.class, new PostaElettronicaBeanDeserializationHelper(createRemapConditions()));
		}

		// salvo i dati in sessione per renderli disponibili l'esportazione
		getSession().setAttribute(FETCH_SESSION_KEY, data);

		PaginatorBean<PostaElettronicaBean> lPaginatorBean = new PaginatorBean<PostaElettronicaBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		//TODO PAGINAZIONE START
//		lPaginatorBean.setTotalRows(returnValue.size());
		if(output.getResultBean().getNropaginaio() != null && output.getResultBean().getNropaginaio().intValue() > 0) {
			lPaginatorBean.setRowsForPage(input.getBachsizeio());
			lPaginatorBean.setTotalRows(output.getResultBean().getNrototrecout());
		} else {
			lPaginatorBean.setTotalRows(data.size());
		}
		//TODO PAGINAZIONE END
		lPaginatorBean.setOverflow(overflow);
				
		return lPaginatorBean;
	}

	private Map<String, String> createRemapConditions() {
		return new HashMap<String, String>();
	}

	/**
	 * Crea il bean necessario per il recupero dei dati
	 * 
	 * @param xmlPostaElettronicaBean
	 * @param forNroRecordTot
	 * @param criteria
	 * @param token
	 * @param colsToReturn
	 * @return
	 * @throws Exception
	 * @throws NumberFormatException
	 * @throws JAXBException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	protected DmpkIntMgoEmailTrovaemailBean createInputBean(boolean forNroRecordTot, AdvancedCriteria criteria, List<OrderByBean> orderby) throws Exception, NumberFormatException, JAXBException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		String colsOrderBy = null;
		String flgDescOrderBy = null;
		
		String colsToReturn = null;
		String idUserLavoro = AurigaUserUtil.getLoginInfo(getSession()).getIdUserLavoro();
		String token = AurigaUserUtil.getLoginInfo(getSession()).getToken();	
		String criteriAvanzati = null;
		
		Integer overflowLimit = null;
		Integer bachSize = null;
		Integer nroPagina = null;
		
		XmlFilterPostaElettronicaBean xmlPostaElettronicaBean = new XmlFilterPostaElettronicaBean();
		
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("criteriAvanzati".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						criteriAvanzati = String.valueOf(crit.getValue());
					}
				}
			}
		}
		
		if (criteriAvanzati != null) {
			XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
			xmlPostaElettronicaBean = lXmlUtilityDeserializer.unbindXml(criteriAvanzati, XmlFilterPostaElettronicaBean.class);
		}
		
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {				
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
				} else if ("idNode".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						xmlPostaElettronicaBean.setIdNode((String) crit.getValue());						
					}
				} else if ("tsSalvataggioEmail".equals(crit.getFieldName())) {
					Date[] tsSalvataggioEmail = getDateConOraFilterValue(crit);
					if (tsSalvataggioEmail[0] != null) {
						xmlPostaElettronicaBean.setInseritaDal(new SimpleDateFormat(FMT_STD_TIMESTAMP).format(tsSalvataggioEmail[0]));
					}
					if (tsSalvataggioEmail[1] != null) {
						xmlPostaElettronicaBean.setInseritaAl(new SimpleDateFormat(FMT_STD_TIMESTAMP).format(tsSalvataggioEmail[1]));
					}
				} else if ("dataRicezione".equals(crit.getFieldName())) {
					Date[] dataRicezione = getDateConOraFilterValue(crit);
					if (dataRicezione[0] != null) {
						xmlPostaElettronicaBean.setRicezioneDal(new SimpleDateFormat(FMT_STD_TIMESTAMP).format(dataRicezione[0]));
					}
					if (dataRicezione[1] != null) {
						xmlPostaElettronicaBean.setRicezioneAl(new SimpleDateFormat(FMT_STD_TIMESTAMP).format(dataRicezione[1]));
					}
				} else if ("dataInvio".equals(crit.getFieldName())) {
					Date[] dataInvio = getDateConOraFilterValue(crit);
					if (dataInvio[0] != null) {
						xmlPostaElettronicaBean.setInvioDal(new SimpleDateFormat(FMT_STD_TIMESTAMP).format(dataInvio[0]));
					}
					if (dataInvio[1] != null) {
						xmlPostaElettronicaBean.setInvioAl(new SimpleDateFormat(FMT_STD_TIMESTAMP).format(dataInvio[1]));
					}
				} else if ("dataDiMovimentazione".equals(crit.getFieldName())) {
					Date[] dataDiMovimentazione = getDateConOraFilterValue(crit);
					if (dataDiMovimentazione[0] != null) {
						xmlPostaElettronicaBean.setDataDiMovimentazioneDal(new SimpleDateFormat(FMT_STD_TIMESTAMP).format(dataDiMovimentazione[0]));
					}
					if (dataDiMovimentazione[1] != null) {
						xmlPostaElettronicaBean.setDataDiMovimentazioneAl(new SimpleDateFormat(FMT_STD_TIMESTAMP).format(dataDiMovimentazione[1]));
					}
				} else if ("dataDiLavorazione".equals(crit.getFieldName())) {
					Date[] dataDiLavorazione = getDateConOraFilterValue(crit);
					if (dataDiLavorazione[0] != null) {
						xmlPostaElettronicaBean.setLavoratiDal(new SimpleDateFormat(FMT_STD_TIMESTAMP).format(dataDiLavorazione[0]));
					}
					if (dataDiLavorazione[1] != null) {
						xmlPostaElettronicaBean.setLavoratiAl(new SimpleDateFormat(FMT_STD_TIMESTAMP).format(dataDiLavorazione[1]));
					}
				} else if ("mittente".equals(crit.getFieldName())) {
					xmlPostaElettronicaBean.setIndirizzoMittente(getValueStringaFullTextMista(crit));
					xmlPostaElettronicaBean.setOperMittente(getOperatorStringaFullTextMista(crit));
				} else if ("destinatario".equals(crit.getFieldName())) {
					xmlPostaElettronicaBean.setIndirizzoDestinatario(getValueStringaFullTextMista(crit));
					xmlPostaElettronicaBean.setOperDestinatario(getOperatorStringaFullTextMista(crit));
				} else if ("oggetto".equals(crit.getFieldName())) {
					xmlPostaElettronicaBean.setOggetto(getValueStringaFullTextMista(crit));
					xmlPostaElettronicaBean.setOperOggetto(getOperatorStringaFullTextMista(crit));
				} else if ("testoMessaggio".equals(crit.getFieldName())) {
					xmlPostaElettronicaBean.setCorpo(getTextFilterValue(crit));
				} else if ("flgAssegnazioneEffettuata".equals(crit.getFieldName())) {
					String value = (String) crit.getValue();
					if (value != null && value.equals("SI")) {
						xmlPostaElettronicaBean.setAssegnazioneEffettuata("true"); // se SI
					} else {
						xmlPostaElettronicaBean.setAssegnazioneEffettuata("false"); // se NO
					}
				} else if ("inviataRisposta".equals(crit.getFieldName())) {
					String value = (String) crit.getValue();
					if (value != null) {
						xmlPostaElettronicaBean.setInviataRisposta(value); // il value può essere già true o false
					}
				} else if ("inoltrata".equals(crit.getFieldName())) {
					String value = (String) crit.getValue();
					if (value != null) {
						xmlPostaElettronicaBean.setInoltrata(value); // il value può essere già true o false
					}
				} else if ("flgIncludiAssegnateAdAltri".equals(crit.getFieldName())) {
					// Il filtro è diventato Escludi assegnate ad altri quindi passa false e non più true
					Boolean flgIncludiValue = (Boolean) crit.getValue();
					if (flgIncludiValue != null && flgIncludiValue)
						xmlPostaElettronicaBean.setIncludiAssegnateAdAltri("false");
					else {
						xmlPostaElettronicaBean.setIncludiAssegnateAdAltri(null);
					}
				} else if ("casellaRicezione".equals(crit.getFieldName())) {
					String value = getTextFilterValue(crit);
					if (value != null) {
						xmlPostaElettronicaBean.setIdCasella(value);
					}
				} else if ("statoLavorazioneUrgenza".equals(crit.getFieldName())) {
					String value = getTextFilterValue(crit);
					if (value != null) {
						xmlPostaElettronicaBean.setStatoLavorazioneUrgenza(value);
					}
				} else if ("azioneDaFare".equals(crit.getFieldName())) {
					String value = getTextFilterValue(crit);
					if (value != null) {
						xmlPostaElettronicaBean.setCodAzioneDaFare(value);
					}
				} else if ("progressivo".equals(crit.getFieldName())) {
					if (crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						String numero = (String) map.get("numero");
						String anno = (String) map.get("anno");
						String tipo = (String) map.get("tipo");
						if (crit.getOperator().equals("equals")) {
							if (numero != null && !"".equals(numero)) {
								xmlPostaElettronicaBean.setProgrEmailDa(numero);
								xmlPostaElettronicaBean.setProgrEmailA(numero);
							}
							if (anno != null && !"".equals(anno)) {
								xmlPostaElettronicaBean.setAnnoEmailDa(anno);
								xmlPostaElettronicaBean.setAnnoEmailA(anno);
							}
						} else if (crit.getOperator().equals("lessOrEqual")) {
							if (numero != null && !"".equals(numero)) {
								xmlPostaElettronicaBean.setProgrEmailA(numero);
							}
							if (anno != null && !"".equals(anno)) {
								xmlPostaElettronicaBean.setAnnoEmailA(anno);
							}
						} else if (crit.getOperator().equals("greaterOrEqual")) {
							if (numero != null && !"".equals(numero)) {
								xmlPostaElettronicaBean.setProgrEmailDa(numero);
							}
							if (anno != null && !"".equals(anno)) {
								xmlPostaElettronicaBean.setAnnoEmailDa(anno);
							}
						} else if (crit.getOperator().equals("lessThan")) {
							if (numero != null && !"".equals(numero)) {
								xmlPostaElettronicaBean.setProgrEmailA(Integer.toString(Integer.valueOf(numero) - 1));
							}
							if (anno != null && !"".equals(anno)) {
								xmlPostaElettronicaBean.setAnnoEmailA(anno);
							}
						} else if (crit.getOperator().equals("greaterThan")) {
							if (numero != null && !"".equals(numero)) {
								xmlPostaElettronicaBean.setProgrEmailDa(Integer.toString(Integer.valueOf(numero) + 1));
							}
							if (anno != null && !"".equals(anno)) {
								xmlPostaElettronicaBean.setAnnoEmailDa(anno);
							}
						}
						if (tipo != null && !"".equals(tipo)) {
							xmlPostaElettronicaBean.setTipoProgrEmail(tipo);
						}
					} else if (crit.getStart() != null || crit.getEnd() != null) {
						if (crit.getOperator().equals("betweenInclusive")) {
							if (crit.getStart() != null) {
								Map mapStart = (Map) crit.getStart();
								String numeroStart = (String) mapStart.get("numero");
								String annoStart = (String) mapStart.get("anno");
								if (numeroStart != null && !"".equals(numeroStart)) {
									xmlPostaElettronicaBean.setProgrEmailDa(numeroStart);
								}
								if (annoStart != null && !"".equals(annoStart)) {
									xmlPostaElettronicaBean.setAnnoEmailDa(annoStart);
								}
							}
							if (crit.getEnd() != null) {
								Map mapEnd = (Map) crit.getEnd();
								String numeroEnd = (String) mapEnd.get("numero");
								String annoEnd = (String) mapEnd.get("anno");
								String tipo = (String) mapEnd.get("tipo");
								if (numeroEnd != null && !"".equals(numeroEnd)) {
									xmlPostaElettronicaBean.setProgrEmailA(numeroEnd);
								}
								if (annoEnd != null && !"".equals(annoEnd)) {
									xmlPostaElettronicaBean.setAnnoEmailA(annoEnd);
								}
								if (tipo != null && !"".equals(tipo)) {
									xmlPostaElettronicaBean.setTipoProgrEmail(tipo);
								}
							}
						}
					}
				} else if ("tipoEmail".equals(crit.getFieldName())) {
					String value = getTextFilterValue(crit);
					xmlPostaElettronicaBean.setTipoEmail(value);
				} else if ("dimensione".equals(crit.getFieldName())) {
					String[] dimensione = getNumberFilterValue(crit);
					String dimensioneDa = dimensione[0] != null ? dimensione[0] : null;
					String dimensioneA = dimensione[1] != null ? dimensione[1] : null;
					xmlPostaElettronicaBean.setDimensioneDa(dimensioneDa);
					xmlPostaElettronicaBean.setDimensioneA(dimensioneA);
				} else if ("statoLavorazioneAperto".equals(crit.getFieldName())) {
					String[] statoLavAperto = getNumberFilterValue(crit);
					String nroGiorniStatoLavorazioneApertoDa = statoLavAperto[0] != null ? statoLavAperto[0] : null;
					String nroGiorniStatoLavorazioneApertoAl = statoLavAperto[1] != null ? statoLavAperto[1] : null;
					xmlPostaElettronicaBean.setNroGiorniStatoLavorazioneApertoDa(nroGiorniStatoLavorazioneApertoDa);
					xmlPostaElettronicaBean.setNroGiorniStatoLavorazioneApertoAl(nroGiorniStatoLavorazioneApertoAl);
				} else if ("StatoIAC".equals(crit.getFieldName())) {
					String value = getTextFilterValue(crit);
					xmlPostaElettronicaBean.setStatoIAC(value);
				} else if ("assegnatoA".equals(crit.getFieldName())) {
					List<DestInvioNotifica> lista = getListaSceltaOrganigrammaFilterValue(crit);				
					xmlPostaElettronicaBean.setListaUOAssegnatari(lista);
				} else if ("inCaricoA".equals(crit.getFieldName())) {
					String value = getTextFilterValue(crit);
					xmlPostaElettronicaBean.setInCaricoA(value);
				} else if ("movimentatoDa".equals(crit.getFieldName())) {
					String value = getTextFilterValue(crit);
					xmlPostaElettronicaBean.setMovimentatoDa(value);
				} else if ("lavoratoDa".equals(crit.getFieldName())) {
					String value = getTextFilterValue(crit);
					xmlPostaElettronicaBean.setLavoratoDa(value);
				} else if ("restringiA".equals(crit.getFieldName())) {
					String value = getTextFilterValue(crit);
					value = value.replace("UO", "");
					xmlPostaElettronicaBean.setIdUOLavoro(value);
				} else if ("noteApposte".equals(crit.getFieldName())) {
					xmlPostaElettronicaBean.setNoteApposte(getTextFilterValue(crit));
				} else if ("tagApposto".equals(crit.getFieldName())) {
					xmlPostaElettronicaBean.setTagApposto(getTextFilterValue(crit));
				} else if ("nomeFileAssociato".equals(crit.getFieldName())) {
					xmlPostaElettronicaBean.setNomeFileAssociato(getValueStringaFullTextMista(crit));
					xmlPostaElettronicaBean.setOperFileAssociato(getOperatorStringaFullTextMista(crit));
				} else if ("classifica".equals(crit.getFieldName())) {
					xmlPostaElettronicaBean.setClassificaFolder(String.valueOf(crit.getValue()));
					if (xmlPostaElettronicaBean.getClassificaFolder().startsWith("standard.arrivo")
							|| xmlPostaElettronicaBean.getClassificaFolder().startsWith("standard.archiviata.arrivo")) {
						colsToReturn = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,19,20,21,22,23,24,25,26,27,28,29,31,32,33,34,35,40,46,47,49,55,56,57,58,59,60,61,62,64,67,68,69,70,71,72,73,74,76,77,78,79,80,81,82,84,85,86";
					} else if (xmlPostaElettronicaBean.getClassificaFolder().startsWith("standard.invio")
							|| xmlPostaElettronicaBean.getClassificaFolder().startsWith("standard.uscita")
							|| xmlPostaElettronicaBean.getClassificaFolder().startsWith("standard.archiviata.invio")) {
						colsToReturn = "1,3,4,5,6,8,10,11,15,22,23,24,25,26,27,29,30,36,37,38,39,40,46,47,48,55,56,59,60,61,62,64,65,66,68,69,70,71,72,73,74,76,77,78,79,80,81,82,84,85,86";
					} else if (xmlPostaElettronicaBean.getClassificaFolder().startsWith("standard.eliminata")
							|| xmlPostaElettronicaBean.getClassificaFolder().startsWith("standard.bozze")
							|| xmlPostaElettronicaBean.getClassificaFolder().startsWith("custom.")) {
						colsToReturn = "1,3,4,5,6,8,10,11,22,23,24,25,26,27,29,40,64,68,69,70,71,72,73,74,76,77,78,79,80,81,82,84,85,86";
					}
				} else if ("idMessaggio".equals(crit.getFieldName())) {
					xmlPostaElettronicaBean.setIdMessaggio(getTextFilterValue(crit));
				} else if ("dataChiusura".equals(crit.getFieldName())) {
					Date[] dataChiusura = getDateConOraFilterValue(crit);
					if (dataChiusura[0] != null) {
						xmlPostaElettronicaBean.setChiusuraDal(new SimpleDateFormat(FMT_STD_TIMESTAMP).format(dataChiusura[0]));
					}
					if (dataChiusura[1] != null) {
						xmlPostaElettronicaBean.setChiusuraAl(new SimpleDateFormat(FMT_STD_TIMESTAMP).format(dataChiusura[1]));
					}				
				} else if ("nomeAttach".equals(crit.getFieldName())) {
					xmlPostaElettronicaBean.setNomeAttach(getValueStringaFullTextMista(crit));
					xmlPostaElettronicaBean.setOperNomeAttach(getOperatorStringaFullTextMista(crit));
				} else if ("chiusuraEffettuataDa".equals(crit.getFieldName())) {
					String value = getTextFilterValue(crit);
					xmlPostaElettronicaBean.setChiusuraEffettuataDa(value);
				} else if ("mittentePECPEO".equals(crit.getFieldName())) {
					xmlPostaElettronicaBean.setMittentePECPEO(getTextFilterValue(crit));
				} else if ("categoria".equals(crit.getFieldName())) {
					xmlPostaElettronicaBean.setCategoria(getTextFilterValue(crit));
				} else if ("idRichMassivaInvioDaXls".equals(crit.getFieldName())) {
					String value = getTextFilterValue(crit);
					if (value != null) {
						xmlPostaElettronicaBean.setIdRichMassivaInvioDaXls(value);
					}
				} else if ("codCategoriaReg".equals(crit.getFieldName())) {
					String value = getTextFilterValue(crit);
					xmlPostaElettronicaBean.setCodCategoriaReg(value);
				} else if ("registroReg".equals(crit.getFieldName())) {
					String value = getTextFilterValue(crit);
					xmlPostaElettronicaBean.setRegistroReg(value);
				} else if("annoReg".equals(crit.getFieldName())) {
					String[] estremiAnnoRegistrazione = getNumberFilterValue(crit);
					if (estremiAnnoRegistrazione[0] != null) {
						xmlPostaElettronicaBean.setAnnoRegDa(estremiAnnoRegistrazione[0]);
					}
					if (estremiAnnoRegistrazione[1] != null) {
						xmlPostaElettronicaBean.setAnnoRegA(estremiAnnoRegistrazione[1]);
					}
				} else if("nroRegistrazione".equals(crit.getFieldName())) {
					String[] estremiNroRegistrazione = getNumberFilterValue(crit);
					
					if (estremiNroRegistrazione[0] != null) {
						xmlPostaElettronicaBean.setNroRegDa(estremiNroRegistrazione[0]);
					}
					if (estremiNroRegistrazione[1] != null) {
						xmlPostaElettronicaBean.setNroRegA(estremiNroRegistrazione[1]);
					}
				} else if("dataRegistrazione".equals(crit.getFieldName())) {
					Date[] estremiDataRegistrazione = getDateFilterValue(crit);
					if (estremiDataRegistrazione[0] != null) {
						xmlPostaElettronicaBean.setDtRegDal(new SimpleDateFormat(FMT_STD_DATA).format(estremiDataRegistrazione[0]));
					}
					if (estremiDataRegistrazione[1] != null) {
						xmlPostaElettronicaBean.setDtRegAl(new SimpleDateFormat(FMT_STD_DATA).format(estremiDataRegistrazione[1]));
					}
				} else if ("statoProtocollazione".equals(crit.getFieldName())) {
					xmlPostaElettronicaBean.setStatoProtocollazione(getTextFilterValue(crit));
				} else if ("ricercaMailArchiviate".equals(crit.getFieldName())) {
					xmlPostaElettronicaBean.setRicercaMailArchiviate(getTextFilterValue(crit));
				}				
			}
		}
		
		boolean isCruscottoMail = getExtraparams().get("isCruscottoMail") != null ? new Boolean(getExtraparams().get("isCruscottoMail")) : false;
		if (isCruscottoMail) {
			xmlPostaElettronicaBean.setClassificaFolder(null);
			xmlPostaElettronicaBean.setCruscottoMail("1");
			colsToReturn = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,19,20,21,22,23,24,25,26,27,28,29,31,32,33,34,35,40,46,47,49,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,84,85,86,87,88";
		}

		// CREO L'INPUT PER LA STORE
		DmpkIntMgoEmailTrovaemailBean input = new DmpkIntMgoEmailTrovaemailBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		//TODO PAGINAZIONE START
		input.setColtoreturnin(colsToReturn);
		if(bachSize != null && bachSize.intValue() > 0) {
			input.setFlgsenzapaginazionein(null);
			input.setBachsizeio(bachSize);
			input.setNropaginaio(nroPagina != null ? nroPagina : new Integer(1));
		} else {
			input.setFlgsenzapaginazionein(1);
			input.setBachsizeio(overflowLimit);
			if (forNroRecordTot) {
				input.setOverflowlimitin(new Integer(-1));
				input.setColtoreturnin("1");
			} else {
				input.setOverflowlimitin(overflowLimit);
			}
		}
		//TODO PAGINAZIONE END
		
		// Se in lista è attivo il limite di overflow o la paginazione, allora devo fare l'ordinamento lato server
		if((bachSize != null && bachSize.intValue() > 0) || (overflowLimit != null && overflowLimit.intValue() > 0)) {
			HashSet<String> setNumColonneOrdinabili = new HashSet<String>(Arrays.asList("1","2","3","4","5","6","7","8","9","10","21","22","23","36","40","71","72","73","74","75","76","87"));
			String[] colAndFlgDescOrderBy = getColAndFlgDescOrderBy(PostaElettronicaBean.class, setNumColonneOrdinabili);
			colsOrderBy = colAndFlgDescOrderBy[0];
			flgDescOrderBy = colAndFlgDescOrderBy[1];
			input.setColorderbyio(colsOrderBy);
			input.setFlgdescorderbyio(flgDescOrderBy);
		}
		
		// xml di aurigapostaele
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String filter = lXmlUtilitySerializer.bindXml(xmlPostaElettronicaBean);

		input.setFiltriio(filter);
		return input;
	}

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {

		DmpkIntMgoEmailTrovaemailBean input = createInputBean(true, bean.getCriteria(), null);
		input.setOverflowlimitin(-1);

		// mi interessa solamente ritornare il numero di record, qundi imposto la prima colonna solo per avere tutti i record con un campo valorizzato
		input.setColtoreturnin("1");

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		DmpkIntMgoEmailTrovaemail dmpkIntMgoEmailTrovaemail = new DmpkIntMgoEmailTrovaemail();
		StoreResultBean<DmpkIntMgoEmailTrovaemailBean> output = dmpkIntMgoEmailTrovaemail.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		// SETTO L'OUTPUT
		if (output.getResultBean().getResultout() != null) {
			bean.setNroRecordTot(output.getResultBean().getNrototrecout());
		}

		return bean;
	}

	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		AdvancedCriteria criteria = bean.getCriteria();

		// IN BASE AI CRITERIA MI POPOLO L'OGGETTO CACHE XMLPOSTAELETTRONICA DA PASSARE COME INPUT
		DmpkIntMgoEmailTrovaemailBean input = createInputBean(false, criteria, null);

		// richiesta di schedulazione del job asincrono di generazione del documento di esportazione della lista
		input.setOverflowlimitin(-2);

		DmpkIntMgoEmailTrovaemail dmpkIntMgoEmailTrovaemail = new DmpkIntMgoEmailTrovaemail();
		StoreResultBean<DmpkIntMgoEmailTrovaemailBean> result = dmpkIntMgoEmailTrovaemail.execute(getLocale(), loginBean, input);

		// if (result.isInError() && StringUtils.isNotBlank(result.getDefaultMessage())) {
		// throw new StoreException(result.getDefaultMessage());
		// }
		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + result.getDefaultMessage());
				throw new StoreException(result);
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		Integer jobId = result.getResultBean().getBachsizeio();
		bean.setIdAsyncJob(jobId);

		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), PostaElettronicaXmlBean.class.getName());

		// salvo il deserializzatore di base perchè interessa l'esportazione dei soli campi originali
		saveRemapInformations(loginBean, jobId, createRemapConditions(), PostaElettronicaXmlBeanDeserializationHelper.class);

		updateJob(loginBean, bean, jobId, loginBean.getIdUser());

		if (jobId != null && jobId > 0) {
			String mess = "Schedulata esportazione su file registrata con Nr. " + jobId.toString()
					+ " .Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}

		return bean;
	}
	
}