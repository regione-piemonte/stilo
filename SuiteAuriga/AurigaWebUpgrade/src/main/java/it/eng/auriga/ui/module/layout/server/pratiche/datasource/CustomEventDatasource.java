/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesIueventocustomBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DettColonnaAttributoListaBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.DettaglioEventoItemBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.EventoCustomBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.EventoCustomXmlBean;
import it.eng.auriga.ui.module.layout.server.pratiche.util.EventoCustomCreator;
import it.eng.client.DmpkProcessesIueventocustom;
import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id="CustomEventDatasource")
public class CustomEventDatasource extends AbstractServiceDataSource<EventoCustomBean, EventoCustomBean>{

	@Override
	public EventoCustomBean call(EventoCustomBean pInBean) throws Exception {
		
		String flgIgnoreObblig = getExtraparams().get("flgIgnoreObblig");
		String skipSuccessMsg = getExtraparams().get("skipSuccessMsg");
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkProcessesIueventocustomBean lBean = new DmpkProcessesIueventocustomBean();
		lBean.setCodidconnectiontokenin(token);
		lBean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);		
		
		if (StringUtils.isNotBlank(pInBean.getIdProcess()))
			lBean.setIdprocessin(new BigDecimal(pInBean.getIdProcess()));
		if (StringUtils.isNotBlank(pInBean.getIdEvento()))
			lBean.setIdeventoio(new BigDecimal(pInBean.getIdEvento()));
		if (StringUtils.isNotBlank(pInBean.getIdTipoEvento()))
			lBean.setIdtipoeventoin(new BigDecimal(pInBean.getIdTipoEvento()));
		if (StringUtils.isNotBlank(pInBean.getIdUd()))
			lBean.setIdudassociatain(new BigDecimal(pInBean.getIdUd()));
		
		lBean.setDeseventoin(pInBean.getDesEvento());
		lBean.setMessaggioin(pInBean.getMessaggio());
		
		if(StringUtils.isNotBlank(flgIgnoreObblig)) {
			lBean.setFlgignoraobbligin(new Integer(flgIgnoreObblig));
		}

		if(pInBean.getValori() != null && pInBean.getTipiValori() != null) {
			SezioneCache lSezioneCache = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(pInBean.getIdProcess(), pInBean.getValori(), pInBean.getTipiValori(), getSession());
			
//			SezioneCache lSezioneCache = new SezioneCache();
//			for (String lString : pInBean.getValori().keySet()){
//				Variabile lVariabile = new Variabile();
//				lVariabile.setNome(lString);
//				Object lValue = pInBean.getValori().get(lString);
//				if(isContainedIn(lString, pInBean.getCheckboxes())){
//					boolean lBoolean = (Boolean) lValue;
//					if (lBoolean){
//						lVariabile.setValoreSemplice("1");
//					} else {
//						lVariabile.setValoreSemplice("0");
//					}
//				} else if(isContainedIn(lString, pInBean.getDates())) {
//					lVariabile.setValoreSemplice(new SimpleDateFormat(FMT_STD_DATA).format(new SimpleDateFormat(DATE_ATTR_FORMAT).parse(lValue.toString())));
//				} else if(isContainedIn(lString, pInBean.getDatetimes())) {
//					lVariabile.setValoreSemplice(new SimpleDateFormat(FMT_STD_TIMESTAMP).format(new SimpleDateFormat(DATETIME_ATTR_FORMAT).parse(lValue.toString())));							
//				} else {
//					lVariabile.setValoreSemplice(lValue.toString());
//				}
//				lSezioneCache.getVariabile().add(lVariabile);
//			}
			
			StringWriter lStringWriter = new StringWriter();
			Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
			lMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
			lMarshaller.marshal(lSezioneCache, lStringWriter);
			lBean.setAttributiaddin(lStringWriter.toString());
		}
		
		DmpkProcessesIueventocustom store = new DmpkProcessesIueventocustom();
		StoreResultBean<DmpkProcessesIueventocustomBean> result = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lBean);
		if (result.isInError()){
			throw new StoreException(result);
		}
		if(skipSuccessMsg == null || !"true".equalsIgnoreCase(skipSuccessMsg)) {
			addMessage("Salvataggio effettuato con successo", "", MessageType.INFO);
		}
		pInBean.setIdEvento(String.valueOf(result.getResultBean().getIdeventoio()));
		return pInBean;
	}
	
	public EventoCustomBean loadDati(EventoCustomBean pInBean) throws Exception {
		if(StringUtils.isNotBlank(pInBean.getIdEvento())) {
			List<DettaglioEventoItemBean> lListResult = EventoCustomCreator.buildCustomForLoad(pInBean, getLocale(), AurigaUserUtil.getLoginInfo(getSession()));
			if (lListResult == null) return new EventoCustomBean();
			Field[] lFields = EventoCustomXmlBean.class.getDeclaredFields();
			if (!EventoCustomXmlBean.class.getSuperclass().equals(Object.class)){
				Field[] lFieldsSuperClass = EventoCustomXmlBean.class.getSuperclass().getDeclaredFields();
				lFields = ArrayUtils.addAll(lFields, lFieldsSuperClass);
			}
			HashMap<String,Object> valori = new HashMap<String,Object>();
			for (DettaglioEventoItemBean lDettaglioEventoFieldBean : lListResult){
				if(lDettaglioEventoFieldBean.getTipo().equals("LISTA")) {
					valori.put(lDettaglioEventoFieldBean.getNome(), null);
				} if(lDettaglioEventoFieldBean.getTipo().equals("CHECK")) {
					valori.put(lDettaglioEventoFieldBean.getNome(), (lDettaglioEventoFieldBean.getValue() != null && "1".equals(lDettaglioEventoFieldBean)));
				} else {
					valori.put(lDettaglioEventoFieldBean.getNome(), lDettaglioEventoFieldBean.getValue());
				}
			}			
			pInBean.setValori(valori);			
		} 
		return pInBean;
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
			BeanWrapperImpl wrappedObjectLista = BeanPropertyUtility.getBeanWrapper();
			for (Riga lRiga : righe){
				T lObjectLista = lClass.newInstance();
				wrappedObjectLista = BeanPropertyUtility.updateBeanWrapper(wrappedObjectLista, lObjectLista);
				for (Field lFieldLista : lFieldsLista){
					NumeroColonna lNumeroColonna = lFieldLista.getAnnotation(NumeroColonna.class);
					if (lNumeroColonna != null){
						int index = Integer.valueOf(lNumeroColonna.numero());
						for (Colonna lColonna : lRiga.getColonna()){
							if (lColonna.getNro().intValue() == index){
								String value = lColonna.getContent();
								setValueOnBean(lObjectLista, lFieldLista, lFieldLista.getName(), value, wrappedObjectLista);
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

	private void setValueOnBean(Object nested, Field lFieldNest, String propertyNested, String valore, BeanWrapperImpl wrappedNested) throws Exception {
		if (lFieldNest.getType().isEnum()){
			BeanWrapperImpl wrappedObjectEnum = BeanPropertyUtility.getBeanWrapper();
			for (Object lObjectEnum : lFieldNest.getType().getEnumConstants()){
				wrappedObjectEnum = BeanPropertyUtility.updateBeanWrapper(wrappedObjectEnum, lObjectEnum);
				String value = BeanPropertyUtility.getPropertyValueAsString(lObjectEnum, wrappedObjectEnum, "dbValue");
				// String value = BeanUtilsBean2.getInstance().getProperty(lObjectEnum, "dbValue");
				if (value == null){
					if (valore == null){
						BeanPropertyUtility.setPropertyValue(nested, wrappedNested, propertyNested, lObjectEnum);
						// BeanUtilsBean2.getInstance().setProperty(nested, propertyNested, lObjectEnum);
					} else {

					}
				} else if (value.equals(valore)){
					BeanPropertyUtility.setPropertyValue(nested, wrappedNested, propertyNested, lObjectEnum);
					// BeanUtilsBean2.getInstance().setProperty(nested, propertyNested, lObjectEnum);
				}
			}
		} else if (Date.class.isAssignableFrom(lFieldNest.getType())){
			TipoData lTipoData = lFieldNest.getAnnotation(TipoData.class);
			SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(lTipoData.tipo().getPattern());
			if(StringUtils.isNotBlank(valore)) {
				Date lDate = lSimpleDateFormat.parse(valore);
				BeanPropertyUtility.setPropertyValue(nested, wrappedNested, propertyNested, lDate);
				// BeanUtilsBean2.getInstance().setProperty(nested, propertyNested, lDate);
			}
		} else {
			BeanPropertyUtility.setPropertyValue(nested, wrappedNested, propertyNested, valore);
			// BeanUtilsBean2.getInstance().setProperty(nested, propertyNested, valore);
		}
	}

//	private boolean isContainedIn(String lString, List<String> lList) {
//		for (String lStringContained : lList){
//			if (lStringContained.equals(lString)) {
//				return true;
//			}
//		}
//		return false;
//	}

}
