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
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ModificaLivelloRiservatezzaBean;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.client.DmpkCoreUpdfolder;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.XmlFascicoloIn;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "ModificaLivelloRiservatezzaDataSource")
public class ModificaLivelloRiservatezzaDataSource extends AbstractDataSource<ModificaLivelloRiservatezzaBean, ModificaLivelloRiservatezzaBean>{	

	@Override
	public ModificaLivelloRiservatezzaBean add(ModificaLivelloRiservatezzaBean bean) throws Exception {		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		HashMap<String, String> errorMessages = null;
		
		for(ArchivioBean udFolder : bean.getListaRecord()) {
			if("U".equals(udFolder.getFlgUdFolder())) {
				DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);			
				input.setFlgtipotargetin("D");
				input.setIduddocin(new BigDecimal(udFolder.getIdDocPrimario()));
				
				CreaModDocumentoInBean modDocumentoInBean = new CreaModDocumentoInBean();			
				modDocumentoInBean.setLivelloRiservatezza(bean.getLivelloRiservatezza() != null ? bean.getLivelloRiservatezza() : "");
				
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(modDocumentoInBean));	
					
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
				input.setIdfolderin(new BigDecimal(udFolder.getIdUdFolder()));
				
				XmlFascicoloIn xmlFascicoloIn = new XmlFascicoloIn();
				xmlFascicoloIn.setLivRiservatezza(bean.getLivelloRiservatezza() != null ? bean.getLivelloRiservatezza() : "");
				
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				input.setAttributixmlin(lXmlUtilitySerializer.bindXml(xmlFascicoloIn));
					
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
	public ModificaLivelloRiservatezzaBean get(ModificaLivelloRiservatezzaBean bean) throws Exception {		
		
		return null;
	}
	
	@Override
	public ModificaLivelloRiservatezzaBean remove(ModificaLivelloRiservatezzaBean bean)
	throws Exception {
		
		return null;
	}

	@Override
	public ModificaLivelloRiservatezzaBean update(ModificaLivelloRiservatezzaBean bean,
			ModificaLivelloRiservatezzaBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public PaginatorBean<ModificaLivelloRiservatezzaBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(ModificaLivelloRiservatezzaBean bean)
	throws Exception {
		
		return null;
	}

}
