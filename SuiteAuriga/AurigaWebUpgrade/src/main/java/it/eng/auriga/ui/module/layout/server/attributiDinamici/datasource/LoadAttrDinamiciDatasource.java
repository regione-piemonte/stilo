/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.auriga.database.store.dmpk_attributi_dinamici.bean.DmpkAttributiDinamiciLoadattrdinamiciBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributoBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.LoadAttrDinamiciInputBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.LoadAttrDinamiciOutputBean;
import it.eng.client.DmpkAttributiDinamiciLoadattrdinamici;
import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id="LoadAttrDinamiciDatasource")
public class LoadAttrDinamiciDatasource extends AbstractServiceDataSource<LoadAttrDinamiciInputBean, LoadAttrDinamiciOutputBean> {
	
	public LoadAttrDinamiciOutputBean call(LoadAttrDinamiciInputBean lInput) throws Exception {
		
		AurigaLoginBean lLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		DmpkAttributiDinamiciLoadattrdinamiciBean lLoadattrdinamiciBean = new DmpkAttributiDinamiciLoadattrdinamiciBean();
		lLoadattrdinamiciBean.setCodidconnectiontokenin(lLoginBean.getToken());
		lLoadattrdinamiciBean.setIduserlavoroin(StringUtils.isNotBlank(lLoginBean.getIdUserLavoro()) ? new BigDecimal(lLoginBean.getIdUserLavoro()) : null);
		lLoadattrdinamiciBean.setNometabellain(lInput.getNomeTabella());
		lLoadattrdinamiciBean.setRowidin(lInput.getRowId());
		lLoadattrdinamiciBean.setCitipoentitain(lInput.getTipoEntita());
		lLoadattrdinamiciBean.setListatipiregin(lInput.getListaTipiReg());
		lLoadattrdinamiciBean.setAttrvaluesxmlin(lInput.getAttrValuesXml());
		lLoadattrdinamiciBean.setFlgmostratuttiin(lInput.getFlgMostraTutti());
		lLoadattrdinamiciBean.setFlgnomeattrconsuffin(lInput.getFlgNomeAttrConSuff());
		
		DmpkAttributiDinamiciLoadattrdinamici lLoadattrdinamici = new DmpkAttributiDinamiciLoadattrdinamici();
		StoreResultBean<DmpkAttributiDinamiciLoadattrdinamiciBean> lStoreResultBean = lLoadattrdinamici.execute(getLocale(), lLoginBean, lLoadattrdinamiciBean);
		
		LoadAttrDinamiciOutputBean lOutput = new LoadAttrDinamiciOutputBean();
		lOutput.setFlgMostraAltriAttr(lStoreResultBean.getResultBean().getFlgmostraaltriattrout());
		
		if(StringUtils.isNotBlank(lStoreResultBean.getResultBean().getAttributiaddout())) {
			StringReader sr = new StringReader(lStoreResultBean.getResultBean().getAttributiaddout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			lOutput.setAttributiAdd(recuperaLista(lista, AttributoBean.class));
		}
		
		return lOutput;
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
