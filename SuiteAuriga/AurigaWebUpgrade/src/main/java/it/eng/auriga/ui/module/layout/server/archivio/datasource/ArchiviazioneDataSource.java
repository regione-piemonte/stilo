/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpdfolderBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchiviazioneBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.InteresseCessatoSezioneCacheBean;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.client.DmpkCoreUpdfolder;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "ArchiviazioneDataSource")
public class ArchiviazioneDataSource extends AbstractDataSource<ArchiviazioneBean, ArchiviazioneBean>{	

	@Override
	public ArchiviazioneBean add(ArchiviazioneBean bean) throws Exception {		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		HashMap<String, String> errorMessages = null;
		
		for(ArchivioBean udFolder : bean.getListaRecord()) {
			if("U".equals(udFolder.getFlgUdFolder())) {
				DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				InteresseCessatoSezioneCacheBean lInteresseCessatoSezioneCacheBean = new InteresseCessatoSezioneCacheBean();
				lInteresseCessatoSezioneCacheBean.setFlgInteresseCessato(bean.getFlgInteresseCessato());
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				String lStrXml = lXmlUtilitySerializer.bindXml(lInteresseCessatoSezioneCacheBean);
				
		        String attributiUdDocXml = lStrXml;	
		       
		        input.setFlgtipotargetin("U");
				input.setIduddocin(new BigDecimal(udFolder.getIdUdFolder()));
				input.setAttributiuddocxmlin(attributiUdDocXml);
				
				DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
				StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(getLocale(),loginBean, input);
				
				if(output.getDefaultMessage() != null) {
					if(errorMessages == null) errorMessages = new HashMap<String, String>();
					errorMessages.put(udFolder.getIdUdFolder(), output.getDefaultMessage());
				}
			} else if("F".equals(udFolder.getFlgUdFolder())) {
				DmpkCoreUpdfolderBean input = new DmpkCoreUpdfolderBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				InteresseCessatoSezioneCacheBean lInteresseCessatoSezioneCacheBean = new InteresseCessatoSezioneCacheBean();
				lInteresseCessatoSezioneCacheBean.setFlgInteresseCessato(bean.getFlgInteresseCessato());
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				String lStrXml = lXmlUtilitySerializer.bindXml(lInteresseCessatoSezioneCacheBean);
				
		        String attributiXml = lStrXml;	
		       
		        input.setIdfolderin(new BigDecimal(udFolder.getIdUdFolder()));
				input.setAttributixmlin(attributiXml);
				
				DmpkCoreUpdfolder dmpkCoreUpdfolder = new DmpkCoreUpdfolder();
				StoreResultBean<DmpkCoreUpdfolderBean> output = dmpkCoreUpdfolder.execute(getLocale(),loginBean, input);
				
				if(output.getDefaultMessage() != null) {
					if(errorMessages == null) errorMessages = new HashMap<String, String>();
					errorMessages.put(udFolder.getIdUdFolder(), output.getDefaultMessage());
				}
			}
		}
		
		bean.setErrorMessages(errorMessages);
		
		return bean;
	}

	@Override
	public ArchiviazioneBean get(ArchiviazioneBean bean) throws Exception {		
		
		return null;
	}
	
	@Override
	public ArchiviazioneBean remove(ArchiviazioneBean bean)
	throws Exception {
		
		return null;
	}

	@Override
	public ArchiviazioneBean update(ArchiviazioneBean bean,
			ArchiviazioneBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public PaginatorBean<ArchiviazioneBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(ArchiviazioneBean bean)
	throws Exception {
		
		return null;
	}
	
}
