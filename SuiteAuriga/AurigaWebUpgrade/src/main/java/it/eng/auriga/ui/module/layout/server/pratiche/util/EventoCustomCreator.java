/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesIueventocustomBean;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesLoaddetteventocustomBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.DettaglioEventoItemBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.EventoCustomBean;
import it.eng.client.DmpkProcessesLoaddetteventocustom;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;

public class EventoCustomCreator {

	public static DmpkProcessesIueventocustomBean buildCustomForSave(EventoCustomBean pInBean, AurigaLoginBean aurigaLoginBean) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{

		String token = aurigaLoginBean.getToken();
		String idUserLavoro = aurigaLoginBean.getIdUserLavoro();
		
		DmpkProcessesIueventocustomBean lBean = new DmpkProcessesIueventocustomBean();
		lBean.setCodidconnectiontokenin(token);
		lBean.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);		
		
		if (StringUtils.isNotEmpty(pInBean.getIdProcess()))
			lBean.setIdprocessin(new BigDecimal(pInBean.getIdProcess()));
		if (StringUtils.isNotEmpty(pInBean.getIdEvento()))
			lBean.setIdeventoio(new BigDecimal(pInBean.getIdEvento()));
		if (StringUtils.isNotEmpty(pInBean.getIdTipoEvento()))
			lBean.setIdtipoeventoin(new BigDecimal(pInBean.getIdTipoEvento()));

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String xmlIn = lXmlUtilitySerializer.bindXml(pInBean);
		lBean.setAttributiaddin(xmlIn);
		
		return lBean;
	}

	public static List<DettaglioEventoItemBean> buildCustomForLoad(EventoCustomBean pInBean, Locale locale, AurigaLoginBean aurigaLoginBean) throws Exception{
		
		DmpkProcessesLoaddetteventocustomBean lBean = new DmpkProcessesLoaddetteventocustomBean();
		
		if (StringUtils.isNotEmpty(pInBean.getIdEvento())){
			lBean.setIdeventoio(new BigDecimal(pInBean.getIdEvento()));//3 colonna
		} else {
			return null;
		}
		
		DmpkProcessesLoaddetteventocustom store = new DmpkProcessesLoaddetteventocustom();
		
		StoreResultBean<DmpkProcessesLoaddetteventocustomBean> result = store.execute(locale, aurigaLoginBean, lBean);
		
		if (result.isInError()){
			throw new StoreException(result);
		} else {
			List<DettaglioEventoItemBean> lList = XmlListaUtility.recuperaLista(result.getResultBean().getAttributiaddout(), DettaglioEventoItemBean.class); 
			return lList;
		}
	}
}
