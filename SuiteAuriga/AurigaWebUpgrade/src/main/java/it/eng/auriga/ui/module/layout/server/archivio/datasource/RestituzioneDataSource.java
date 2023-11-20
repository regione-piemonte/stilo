/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_collaboration.bean.DmpkCollaborationRestituzioneBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.RestituzioneBean;
import it.eng.client.DmpkCollaborationRestituzione;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "RestituzioneDataSource")
public class RestituzioneDataSource extends AbstractDataSource<RestituzioneBean, RestituzioneBean>{	

	@Override
	public RestituzioneBean add(RestituzioneBean bean) throws Exception {		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		HashMap<String, String> errorMessages = null;
		
		for(ArchivioBean udFolder : bean.getListaRecord()) {
			DmpkCollaborationRestituzioneBean input = new DmpkCollaborationRestituzioneBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setFlgtypeobjtoreturnin(udFolder.getFlgUdFolder());
			input.setIdobjtoreturnin(new BigDecimal(udFolder.getIdUdFolder()));
			input.setMessaggiorestituzionein(bean.getMessaggio());	
			input.setAsscopiatoreturntypein(null);
			input.setAsscopiatoreturnidin(null);
			input.setFlgignorewarningin(bean.getFlgIgnoreWarning());		
			
			DmpkCollaborationRestituzione dmpkCollaborationRestituzione = new DmpkCollaborationRestituzione();
			StoreResultBean<DmpkCollaborationRestituzioneBean> output = dmpkCollaborationRestituzione.execute(getLocale(),loginBean, input);
			
			if(StringUtils.isNotBlank(output.getResultBean().getWarningmsgout())) {
				addMessage(output.getResultBean().getWarningmsgout(), "", MessageType.WARNING);
				bean.setFlgIgnoreWarning(new Integer(1));
			} else {
				bean.setFlgIgnoreWarning(new Integer(0));
			}
			
			if(output.getDefaultMessage() != null) {
				if(errorMessages == null) errorMessages = new HashMap<String, String>();
				errorMessages.put(udFolder.getIdUdFolder(), output.getDefaultMessage());
			}
		}
		
		bean.setErrorMessages(errorMessages);
		
		return bean;
	}	
	
	@Override
	public RestituzioneBean get(RestituzioneBean bean) throws Exception {		
		
		return null;
	}
	
	@Override
	public RestituzioneBean remove(RestituzioneBean bean)
	throws Exception {
		
		return null;
	}

	@Override
	public RestituzioneBean update(RestituzioneBean bean,
			RestituzioneBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public PaginatorBean<RestituzioneBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(RestituzioneBean bean)
	throws Exception {
		
		return null;
	}

}
