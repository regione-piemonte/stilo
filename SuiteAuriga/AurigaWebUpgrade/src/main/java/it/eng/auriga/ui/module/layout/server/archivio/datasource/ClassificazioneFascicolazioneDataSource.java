/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.AttributiUdBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.AttributiUdClassFasc;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ClassificazioneFascicolazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ClassificazioneFascicoloBean;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "ClassificazioneFascicolazioneDataSource")
public class ClassificazioneFascicolazioneDataSource extends AbstractDataSource<ClassificazioneFascicolazioneBean, ClassificazioneFascicolazioneBean>{	

	@Override
	public ClassificazioneFascicolazioneBean add(ClassificazioneFascicolazioneBean bean) throws Exception {		
		
		boolean inAppend = getExtraparams().get("inAppend") != null ? new Boolean(getExtraparams().get("inAppend")) : false;		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		HashMap<String, String> errorMessages = null;
		
		for(ArchivioBean ud : bean.getListaRecord()) {
			DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			List<AttributiUdClassFasc> lList = new ArrayList<AttributiUdClassFasc>();
			if (bean.getListaClassFasc() != null) {
	        	for(ClassificazioneFascicoloBean classFascBean: bean.getListaClassFasc()) {     
	        		AttributiUdClassFasc lAttributiUdClassFasc = new AttributiUdClassFasc();
	        		BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lAttributiUdClassFasc, classFascBean);
	        		if(classFascBean.getFlgCapofila() != null && classFascBean.getFlgCapofila()) {
	        			lAttributiUdClassFasc.setTipoRelazione("CPF");
	        		}
	        		lList.add(lAttributiUdClassFasc);
	        	}
			}	
			AttributiUdBean lAttributiUdBean = new AttributiUdBean();
			lAttributiUdBean.setClassFascTitolario(lList);
	        if(inAppend) {
	        	lAttributiUdBean.setAppendClassFascTitolario("1");
	        }				        
	        lAttributiUdBean.setLivRiservatezza(bean.getLivelloRiservatezza() !=null ? new BigDecimal(bean.getLivelloRiservatezza()).intValue() + "" : null);
			
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
	public ClassificazioneFascicolazioneBean get(ClassificazioneFascicolazioneBean bean) throws Exception {		
		
		return null;
	}
	
	@Override
	public ClassificazioneFascicolazioneBean remove(ClassificazioneFascicolazioneBean bean)
	throws Exception {
		
		return null;
	}

	@Override
	public ClassificazioneFascicolazioneBean update(ClassificazioneFascicolazioneBean bean,
			ClassificazioneFascicolazioneBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public PaginatorBean<ClassificazioneFascicolazioneBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(ClassificazioneFascicolazioneBean bean)
	throws Exception {
		
		return null;
	}

}
