/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.AttributiUdBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.AttributiUdFolderCustom;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.OrganizzaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.FolderCustomBean;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "OrganizzaDataSource")
public class OrganizzaDataSource extends AbstractDataSource<OrganizzaBean, OrganizzaBean>{	

	@Override
	public OrganizzaBean add(OrganizzaBean bean) throws Exception {		
		
		boolean inAppend = getExtraparams().get("inAppend") != null ? new Boolean(getExtraparams().get("inAppend")) : false;		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		HashMap<String, String> errorMessages = null;
		
		for(ArchivioBean ud : bean.getListaRecord()) {
			DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			List<AttributiUdFolderCustom> lList = new ArrayList<AttributiUdFolderCustom>();
			if (bean.getListaFolderCustom() != null) {
	        	for(FolderCustomBean folderCustomBean: bean.getListaFolderCustom()) {     
	        		AttributiUdFolderCustom lAttributiUdFolderCustom = new AttributiUdFolderCustom();
	        		lAttributiUdFolderCustom.setId(folderCustomBean.getId());
	        		if(folderCustomBean.getFlgCapofila() != null && folderCustomBean.getFlgCapofila()) {
	        			lAttributiUdFolderCustom.setTipoRelazione("CPF");
	        		}
	        		lList.add(lAttributiUdFolderCustom);
	        	}
			}	
			AttributiUdBean lAttributiUdBean = new AttributiUdBean();
			lAttributiUdBean.setFolderCustom(lList);
	        if(inAppend) {
	        	lAttributiUdBean.setAppendFolderCustom("1");
	        }				        
	        lAttributiUdBean.setLivRiservatezza(bean.getLivelloRiservatezza() != null ? new BigDecimal(bean.getLivelloRiservatezza()).intValue() + "" : null);
			
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			String attributiUdDocXml = lXmlUtilitySerializer.bindXml(lAttributiUdBean);	
	       
	        input.setFlgtipotargetin("D");
			input.setIduddocin(new BigDecimal(ud.getIdDocPrimario()));
			input.setAttributiuddocxmlin(attributiUdDocXml);
			
			DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
			StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(getLocale(),loginBean, input);
			
			if(output.getDefaultMessage() != null) {
				if(errorMessages == null) errorMessages = new HashMap<String, String>();
				errorMessages.put(ud.getIdUdFolder(), output.getDefaultMessage());
			}
		}
		
		bean.setErrorMessages(errorMessages);
		
		return bean;
	}

	@Override
	public OrganizzaBean get(OrganizzaBean bean) throws Exception {		
	
		return null;
	}
	
	@Override
	public OrganizzaBean remove(OrganizzaBean bean)
	throws Exception {
		
		return null;
	}

	@Override
	public OrganizzaBean update(OrganizzaBean bean,
			OrganizzaBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public PaginatorBean<OrganizzaBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(OrganizzaBean bean)
	throws Exception {
		
		return null;
	}

}
