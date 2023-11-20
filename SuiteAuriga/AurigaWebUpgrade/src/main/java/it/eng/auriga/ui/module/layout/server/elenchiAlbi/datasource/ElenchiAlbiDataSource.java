/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.auriga.database.store.dmpk_elenchi_albi.bean.DmpkElenchiAlbiDrecelencoalboBean;
import it.eng.auriga.database.store.dmpk_elenchi_albi.bean.DmpkElenchiAlbiIurecelencoalboBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.function.bean.FindElenchiAlbiObjectBean;
import it.eng.auriga.function.bean.FindElenchiAlbiResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.CriteriPersonalizzati;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.Operator;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DettColonnaAttributoListaBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.client.AurigaService;
import it.eng.client.DmpkElenchiAlbiDrecelencoalbo;
import it.eng.client.DmpkElenchiAlbiIurecelencoalbo;
import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.utility.XmlUtility;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "ElenchiAlbiDataSource")
public class ElenchiAlbiDataSource extends AbstractFetchDataSource<HashMap> {
	
	@Override
	public String getNomeEntita() {
		return "elenchi_albi";
	}
	
	@Override
	public PaginatorBean<HashMap> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
					
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		
		String filtroFullText = null;
		String[] checkAttributes = null;	
		Integer searchAllTerms = null;

		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = new Integer(1); // 1 : Lista non paginata
		Integer numPagina = null;
		Integer numRighePagina = null;
		Integer online = null;
		String finalita = null;
		String attributiDinamici = "ELENCO_ALBO_DENOM_SOGG;ELENCO_ALBO_NRO_AUT;ELENCO_ALBO_DT_RILASCIO_AUT;ELENCO_ALBO_DURATA_AUT;ELENCO_ALBO_TIPO_ATT;ELENCO_ALBO_INDIRIZZO_LUOGO;ELENCO_ALBO_LOCALITA_LUOGO;ELENCO_ALBO_COMUNE_LUOGO;ELENCO_ALBO_TARGA_PROV_LUOGO";
		
		String colsToReturn = "1-17;" + attributiDinamici;
		String denomSogg = null;
		String nroAut = null;
		Date dtRilascioAutDa = null;	
		Date dtRilascioAutA = null;
		
		List<HashMap> data = new ArrayList<HashMap>();

		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){
				if("searchFulltext".equals(crit.getFieldName())) {
					if(crit.getValue() != null) {
						Map map = (Map) crit.getValue();
						filtroFullText = (String) map.get("parole");
						ArrayList<String> lArrayList = (ArrayList<String>) map.get("attributi");
						checkAttributes = lArrayList != null ? lArrayList.toArray(new String[]{}) : null;
						String operator = crit.getOperator();
						if(StringUtils.isNotBlank(operator)) {
							if("allTheWords".equals(operator)) {
								searchAllTerms = new Integer("1");
							} else if ("oneOrMoreWords".equals(operator)) {
								searchAllTerms = new Integer("0");
							} 
						}
					}
				} else if("ELENCO_ALBO_DENOM_SOGG".equals(crit.getFieldName())) {
					denomSogg = getTextFilterValue(crit);
				} else if("ELENCO_ALBO_NRO_AUT".equals(crit.getFieldName())) {
					nroAut = getTextFilterValue(crit);
				} else if("ELENCO_ALBO_DT_RILASCIO_AUT".equals(crit.getFieldName())) {
					Date[] estremiDtRilascioAut = getDateFilterValue(crit);
					dtRilascioAutDa = estremiDtRilascioAut[0];
					dtRilascioAutA = estremiDtRilascioAut[1];
				}
			}			
		}

		if(StringUtils.isNotBlank(filtroFullText) && (checkAttributes == null || checkAttributes.length == 0)) {
			addMessage("Specificare almeno un attributo su cui effettuare la ricerca full-text", "", MessageType.ERROR);			
		} else {

			FindElenchiAlbiObjectBean lFindElenchiAlbiObjectBean = new FindElenchiAlbiObjectBean();
			lFindElenchiAlbiObjectBean.setFiltroFullText(filtroFullText);
			lFindElenchiAlbiObjectBean.setCheckAttributes(checkAttributes);		
			lFindElenchiAlbiObjectBean.setSearchAllTerms(searchAllTerms);		
			lFindElenchiAlbiObjectBean.setColsOrderBy(colsOrderBy);
			lFindElenchiAlbiObjectBean.setFlgDescOrderBy(flgDescOrderBy);
			lFindElenchiAlbiObjectBean.setFlgSenzaPaginazione(flgSenzaPaginazione);
			lFindElenchiAlbiObjectBean.setNumPagina(numPagina);
			lFindElenchiAlbiObjectBean.setNumRighePagina(numRighePagina);
			lFindElenchiAlbiObjectBean.setOnline(online);
			lFindElenchiAlbiObjectBean.setColsToReturn(colsToReturn);
			lFindElenchiAlbiObjectBean.setFinalita(finalita);	
			
			String idDominio = null;			
			if (loginBean.getDominio().split(":").length==2){
				idDominio = loginBean.getDominio().split(":")[1];				
			}
			String[] lValues = { idDominio };
			lFindElenchiAlbiObjectBean.setType("ID_SP_AOO");	
			lFindElenchiAlbiObjectBean.setValues(lValues);	
			lFindElenchiAlbiObjectBean.setUserIdLavoro(loginBean.getIdUserLavoro());
			
			lFindElenchiAlbiObjectBean.setIdTipoElencoAlbo(new BigDecimal(getExtraparams().get("idTipo")));
			
			List<CriteriPersonalizzati> criteriPersonalizzati = new ArrayList<CriteriPersonalizzati>();
			if(StringUtils.isNotBlank(denomSogg)) {
				CriteriPersonalizzati criterio = new CriteriPersonalizzati();
				criterio.setAttrName("ELENCO_ALBO_DENOM_SOGG");
				criterio.setOperator(Operator.ILIKE.getValue());
				criterio.setValue1(denomSogg);
				criteriPersonalizzati.add(criterio);
			}
			if(StringUtils.isNotBlank(nroAut)) {
				CriteriPersonalizzati criterio = new CriteriPersonalizzati();
				criterio.setAttrName("ELENCO_ALBO_NRO_AUT");
				criterio.setOperator(Operator.ILIKE.getValue());
				criterio.setValue1(nroAut);
				criteriPersonalizzati.add(criterio);
			}
			if(dtRilascioAutDa != null || dtRilascioAutA != null) {
				CriteriPersonalizzati criterio = new CriteriPersonalizzati();
				criterio.setAttrName("ELENCO_ALBO_DT_RILASCIO_AUT");
				criterio.setOperator(Operator.BETWEEN.getValue());				
				criterio.setValue1(new SimpleDateFormat(FMT_STD_DATA).format(dtRilascioAutDa));
				criterio.setValue2(new SimpleDateFormat(FMT_STD_DATA).format(dtRilascioAutA));
				criteriPersonalizzati.add(criterio);
			}
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			String criteriPersonalizzatiXml = lXmlUtilitySerializer.bindXmlList(criteriPersonalizzati);
			lFindElenchiAlbiObjectBean.setCriteriPersonalizzati(criteriPersonalizzatiXml);

			FindElenchiAlbiResultBean resFinder = null;
			try {
				resFinder = AurigaService.getFind().findelenchialbi(
					getLocale(), 
					loginBean, 
					lFindElenchiAlbiObjectBean
				);
			} catch(Exception e) {
				throw new StoreException(e.getMessage());
			}

			String xmlResultSet = resFinder.getResult();
			Integer numTotRec = resFinder.getNroTotRec();
			Integer numRecInPag = resFinder.getNroRecInPagina();	
			String errorMessage = resFinder.getErrMsg();			

			if(errorMessage != null && !"".equals(errorMessage)) {
				addMessage(errorMessage, "", MessageType.WARNING);
			}

			// Conversione ListaRisultati ==> EngResultSet 
			if (xmlResultSet != null){
				StringReader sr = new StringReader(xmlResultSet);
				Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
				if(lista != null) {
					for (int i = 0; i < lista.getRiga().size(); i++) 
					{
						// Lista dei record dell'elenco/albo trovati (XML conforme a schema LISTA_STD.xsd)
						// -- Ogni record trovato è un tag Riga che può contenere le seguenti colonne:
						// -- 1:  Identiticativo univoco del record (ID_REC_ELENCO_ALBO)
						// -- 2:  Rowid del record
						// -- 3:  Identificativo del record nell'eventuale sistema di provenienza
						// -- 4:  Descrizione del record (campo DESCRIZIONE di DMT_ELENCHI_ALBI_CONTENTS): può non essere valorizzato
						// -- 5:  Data e ora di creazione del record (nel formato dato dal parametro di config. FMT_STD_TIMESTMP)
						// -- 6:  Id. dell'utente di creazione del record
						// -- 7:  Decodifica dell'utente di creazione del record
						// -- 8:  Data e ora di ultimo aggiornamento del record (nel formato dato dal parametro di config. FMT_STD_TIMESTMP)
						// -- 9:  Id. dell'utente di ultimo aggiornamento del record
						// -- 10:  Decodifica dell'utente di ultimo aggiornamento del record
						// -- 11: Score del record restituito dall'indicizzatore (valori interi da 1 a 5) (valorizzato solo se effettuata ricerca full-text)
						// -- 12: Cod. dell'applicazione che ha creato il record
						// -- 13: Cod. dell'istamza di applicazione che ha creato il record
						// -- 14: Nome dell'applicazione (e sua eventuale istanza) che ha creato il record
						// -- 15: (valoro 1/0) Se 1 il record è valido, se 0 no (è logicamente annullato)
						// -- 16: Motivi dell'annullamento logico del record
						// -- 17: (valori 1/0) Se 1 indica se il record è selezionabile per la finalità indicata in FinalitaIn						
						Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																	
						
//						ElencoAlboBean bean = new ElencoAlboBean();	        				
//						// Setto i valori dell'XML nel bean 
//						bean.setIdElencoAlbo(v.get(0)); //colonna 1 dell'xml
//						bean.setRowId(v.get(1)); //colonna 2 dell'xml			
//						bean.setDenomSogg(v.get(100)); //colonna 101 dell'xml							
//						bean.setNroAut(v.get(101)); //colonna 102 dell'xml
//						bean.setDataRilascioAut(v.get(102) != null ? new SimpleDateFormat(FMT_STD_DATA).parse(v.get(102)) : null); //colonna 103 dell'xml
//						bean.setDurataAut(v.get(103) != null ? new Integer(v.get(103)) : null); //colonna 104 dell'xml
//						bean.setTipoAtt(v.get(104)); //colonna 105 dell'xml
//						bean.setIndirizzoLuogo(v.get(105)); //colonna 106 dell'xml
//						bean.setLocalitaLuogo(v.get(106)); //colonna 107 dell'xml
//						bean.setComuneLuogo(v.get(107)); //colonna 108 dell'xml
//						bean.setTargaProvLuogo(v.get(108)); //colonna 109 dell'xml
//						data.add(bean);
						
						StringSplitterServer st = new StringSplitterServer(attributiDinamici, ";");
						int cont = 0;
						HashMap<String, Object> bean = new HashMap<String, Object>();
						bean.put("idElencoAlbo", v.get(0));
						bean.put("rowId", v.get(1));
						while(st.hasMoreTokens()) {
							String name = st.nextToken();
							String value = v.get(100 + cont);								
							bean.put(name, value);
							cont++;
						}										
						data.add(bean);
					}
				}							
			}	
		}

		PaginatorBean<HashMap> lPaginatorBean = new PaginatorBean<HashMap>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		return lPaginatorBean;		

	}

	@Override
	public HashMap add(HashMap bean) throws Exception {
				
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkElenchiAlbiIurecelencoalboBean input = new DmpkElenchiAlbiIurecelencoalboBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdelencoalbotypein(new BigDecimal(getExtraparams().get("idTipo")));
		input.setFlgignorewarningin(new Integer(1));				
		
		String xmlAttributi = null;
		if(bean.get("values") != null && ((Map) bean.get("values")).size() > 0) {
			SezioneCache scAttributi = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, (Map) bean.get("values"), (Map) bean.get("types"), getSession());
			StringWriter sw = new StringWriter();
			Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
			lMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
			lMarshaller.marshal(scAttributi, sw);
			xmlAttributi = sw.toString();			
		}
		input.setAttributirecxmlin(xmlAttributi);

		DmpkElenchiAlbiIurecelencoalbo iurecelencoalbo = new DmpkElenchiAlbiIurecelencoalbo();
		StoreResultBean<DmpkElenchiAlbiIurecelencoalboBean> output = iurecelencoalbo.execute(getLocale(), loginBean, input);		

		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		bean.put("idElencoalbo", String.valueOf(output.getResultBean().getIdrecelencoalboio()));
		bean.put("rowId", output.getResultBean().getRowidout());

		return bean;
	}

	@Override
	public HashMap update(HashMap bean, HashMap oldvalue) throws Exception {
				
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkElenchiAlbiIurecelencoalboBean input = new DmpkElenchiAlbiIurecelencoalboBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdrecelencoalboio(new BigDecimal((String) bean.get("idElencoAlbo")));
		input.setIdelencoalbotypein(new BigDecimal(getExtraparams().get("idTipo")));
		input.setFlgignorewarningin(new Integer(1));
						
		String xmlAttributi = null;
		if(bean.get("values") != null && ((Map) bean.get("values")).size() > 0) {
			SezioneCache scAttributi = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, (Map) bean.get("values"), (Map) bean.get("types"), getSession());
			StringWriter sw = new StringWriter();
			Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
			lMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
			lMarshaller.marshal(scAttributi, sw);
			xmlAttributi = sw.toString();			
		}
		input.setAttributirecxmlin(xmlAttributi);

		DmpkElenchiAlbiIurecelencoalbo iurecelencoalbo = new DmpkElenchiAlbiIurecelencoalbo();
		StoreResultBean<DmpkElenchiAlbiIurecelencoalboBean> output = iurecelencoalbo.execute(getLocale(), loginBean, input);				

		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		return bean;
	}
	
	@Override
	public HashMap remove(HashMap bean)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
				
		DmpkElenchiAlbiDrecelencoalboBean input = new DmpkElenchiAlbiDrecelencoalboBean();			
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);		
		input.setIdrecelencoalboin(new BigDecimal((String) bean.get("idElencoAlbo")));
		input.setMotiviin(null);
		input.setFlgcancfisicain(null);
		input.setFlgignorewarningin(new Integer(1));
						
		DmpkElenchiAlbiDrecelencoalbo drecelencoalbo = new DmpkElenchiAlbiDrecelencoalbo();
		StoreResultBean<DmpkElenchiAlbiDrecelencoalboBean> output = drecelencoalbo.execute(getLocale(), loginBean, input);
				
		if(StringUtils.isNotBlank(output.getDefaultMessage())) {
			if(output.isInError()) {
				throw new StoreException(output);		
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
				
		return bean;
	}
	
	private List<HashMap<String, String>> recuperaListaValori(Lista lLista, List<DettColonnaAttributoListaBean> dettAttrLista) throws Exception {		
		List<HashMap<String, String>> lList = new ArrayList<HashMap<String, String>>();
		if(lLista != null) {
			HashMap<Integer,String> mappa = new HashMap<Integer, String>();
			for(int i = 0; i < dettAttrLista.size(); i++) {
				mappa.put(new Integer(dettAttrLista.get(i).getNumero()), dettAttrLista.get(i).getNome());
			}			
			List<Riga> righe = lLista.getRiga();			
			for (Riga lRiga : righe){
				HashMap<String, String> lMap = new HashMap<String, String>();				
				for (Colonna lColonna : lRiga.getColonna()){
					lMap.put(mappa.get(lColonna.getNro().intValue()), lColonna.getContent());					
				}	
				lList.add(lMap);
			}													
		}		
		return lList;					
	}
	
	private <T> List<T> recuperaLista(Lista lLista, Class<T> lClass) throws Exception {		
		List<T> lList = new ArrayList<T>();
		if(lLista != null) {				
			List<Riga> righe = lLista.getRiga();			
			Field[] lFieldsLista = retrieveFields(lClass);
			BeanWrapperImpl wrapperObjectLista = BeanPropertyUtility.getBeanWrapper();
			for (Riga lRiga : righe){
				T lObjectLista = lClass.newInstance();
				wrapperObjectLista = BeanPropertyUtility.updateBeanWrapper(wrapperObjectLista, lObjectLista);
				for (Field lFieldLista : lFieldsLista){
					NumeroColonna lNumeroColonna = lFieldLista.getAnnotation(NumeroColonna.class);
					if (lNumeroColonna != null){
						int index = Integer.valueOf(lNumeroColonna.numero());
						for (Colonna lColonna : lRiga.getColonna()){
							if (lColonna.getNro().intValue() == index){
								String value = lColonna.getContent();
								setValueOnBean(lObjectLista, lFieldLista, lFieldLista.getName(), value, wrapperObjectLista);
							}
						}
					}
				}
				lList.add(lObjectLista);
			}													
		}		
		return lList;					
	}
	
	private Field[] retrieveFields(Class lClass) {
		Field[] lFieldsLista = lClass.getDeclaredFields();;
		if (lClass.getSuperclass()!=null && lClass.getSuperclass()!=java.lang.Object.class){
			Field[] inherited = lClass.getSuperclass().getDeclaredFields();
			Field[] original = lFieldsLista;
			lFieldsLista = new Field[inherited.length + original.length];
			int k = 0;
			for (Field lFieldInherited : inherited){
				lFieldsLista[k] = lFieldInherited;
				k++;
			}
			for (Field lFieldOriginal : original){
				lFieldsLista[k] = lFieldOriginal;
				k++;
			}
		}
		return lFieldsLista;
	}	
			
	private void setValueOnBean(Object nested, Field lFieldNest, String propertyNested, String valore, BeanWrapperImpl wrapperNested) throws Exception {
		if (lFieldNest.getType().isEnum()){
			BeanWrapperImpl wrapperObjectEnum = BeanPropertyUtility.getBeanWrapper();
			for (Object lObjectEnum : lFieldNest.getType().getEnumConstants()){
				wrapperObjectEnum = BeanPropertyUtility.updateBeanWrapper(wrapperObjectEnum, lObjectEnum);
				String value = BeanPropertyUtility.getPropertyValueAsString(lObjectEnum, wrapperObjectEnum, "dbValue");
				// String value = BeanUtilsBean2.getInstance().getProperty(lObjectEnum, "dbValue");
				if (value == null){
					if (valore == null){
						BeanPropertyUtility.setPropertyValue(nested, wrapperNested, propertyNested, lObjectEnum);
						// BeanUtilsBean2.getInstance().setProperty(nested, propertyNested, lObjectEnum);
					} else {

					}
				} else if (value.equals(valore)){
					BeanPropertyUtility.setPropertyValue(nested, wrapperNested, propertyNested, lObjectEnum);
					// BeanUtilsBean2.getInstance().setProperty(nested, propertyNested, lObjectEnum);
				}
			}
		} else if (Date.class.isAssignableFrom(lFieldNest.getType())){
			TipoData lTipoData = lFieldNest.getAnnotation(TipoData.class);
			SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(lTipoData.tipo().getPattern());
			if(StringUtils.isNotBlank(valore)) {
				Date lDate = lSimpleDateFormat.parse(valore);
				BeanPropertyUtility.setPropertyValue(nested, wrapperNested, propertyNested, lDate);
				// BeanUtilsBean2.getInstance().setProperty(nested, propertyNested, lDate);
			}
		} else {
			BeanPropertyUtility.setPropertyValue(nested, wrapperNested, propertyNested, valore);
			// BeanUtilsBean2.getInstance().setProperty(nested, propertyNested, valore);
		}
	}
	
}
