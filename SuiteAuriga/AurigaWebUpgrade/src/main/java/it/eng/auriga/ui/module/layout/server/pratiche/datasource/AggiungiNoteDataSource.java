/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesIueventocustomBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AggiungiNoteBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.DettaglioEventoItemBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.EventoCustomXmlBean;
import it.eng.auriga.ui.module.layout.server.pratiche.util.EventoCustomCreator;
import it.eng.client.DmpkProcessesIueventocustom;
import it.eng.document.XmlVariabile;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;


@Datasource(id = "AggiungiNoteDataSource")
public class AggiungiNoteDataSource extends AbstractServiceDataSource<AggiungiNoteBean,AggiungiNoteBean>{
	
	private static Logger mLogger = Logger.getLogger(AggiungiNoteDataSource.class);

	@Override
	public AggiungiNoteBean call(AggiungiNoteBean pInBean)
			throws Exception {
		mLogger.debug("Start call");
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());		
		DmpkProcessesIueventocustomBean lBean = EventoCustomCreator.buildCustomForSave(convertBeanToXmlBean(pInBean), loginBean);				
		DmpkProcessesIueventocustom store = new DmpkProcessesIueventocustom();		
		StoreResultBean<DmpkProcessesIueventocustomBean> result = store.execute(getLocale(), AurigaUserUtil.getLoginInfo(getSession()), lBean);
		if (result.isInError()){
			throw new StoreException(result);
		}
		addMessage("Salvataggio effettuato con successo", "", MessageType.INFO);
		return pInBean;
	}
	
	public AggiungiNoteBean loadDati(AggiungiNoteBean pInBean) throws Exception {
		mLogger.debug("Start loadDati");
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		List<DettaglioEventoItemBean> lListResult = EventoCustomCreator.buildCustomForLoad(convertBeanToXmlBean(pInBean), getLocale(), loginBean);
		if (lListResult == null) return new AggiungiNoteBean();
		Field[] lFields = EventoCustomXmlBean.class.getDeclaredFields();
		if (!EventoCustomXmlBean.class.getSuperclass().equals(Object.class)){
			Field[] lFieldsSuperClass = EventoCustomXmlBean.class.getSuperclass().getDeclaredFields();
			lFields = ArrayUtils.addAll(lFields, lFieldsSuperClass);
		}
		EventoCustomXmlBean lEventoCustomXmlBean = new EventoCustomXmlBean();
		BeanWrapperImpl wrapperEventoCustomXmlBean = BeanPropertyUtility.getBeanWrapper(lEventoCustomXmlBean);
		for (Field lField : lFields){
			XmlVariabile lXmlVariabile = lField.getAnnotation(XmlVariabile.class);
			if (lXmlVariabile!=null){
				String nome = lXmlVariabile.nome();
				for (DettaglioEventoItemBean lDettaglioEventoFieldBean : lListResult){
					if (lDettaglioEventoFieldBean.getNome().equals(nome)){
						String value = lDettaglioEventoFieldBean.getValue();
						BeanPropertyUtility.setPropertyValue(lEventoCustomXmlBean, wrapperEventoCustomXmlBean, lField.getName(), value);
						// BeanUtilsBean2.getInstance().setProperty(lEventoCustomXmlBean, lField.getName(), value);
					}
				}
			}
		}			
		return convertXmlBeanToBean(lEventoCustomXmlBean);
	}
	
	public EventoCustomXmlBean convertBeanToXmlBean(AggiungiNoteBean pInBean) {
		EventoCustomXmlBean pInBeanXml = new EventoCustomXmlBean();
		pInBeanXml.setAggiungiNote(pInBean.getAggiungiNote());
		pInBeanXml.setIdProcess(pInBean.getIdProcess());
		pInBeanXml.setIdEvento(pInBean.getIdEvento());
		pInBeanXml.setIdTipoEvento(pInBean.getIdTipoEvento());		
		return pInBeanXml;
	}
	
	public AggiungiNoteBean convertXmlBeanToBean(EventoCustomXmlBean pInBeanXml) {
		AggiungiNoteBean pInBean = new AggiungiNoteBean();
		pInBean.setAggiungiNote(pInBeanXml.getAggiungiNote());
		pInBean.setIdProcess(pInBeanXml.getIdProcess());
		pInBean.setIdEvento(pInBeanXml.getIdEvento());
		pInBean.setIdTipoEvento(pInBeanXml.getIdTipoEvento());		
		return pInBean;
	}

}
