/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreRemovefromsezarealavBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.EliminazioneDaAreaLavoroBean;
import it.eng.client.DmpkCoreRemovefromsezarealav;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "EliminazioneDaAreaLavoroDataSource")
public class EliminazioneDaAreaLavoroDataSource extends AbstractDataSource<EliminazioneDaAreaLavoroBean, EliminazioneDaAreaLavoroBean>{	

	@Override
	public EliminazioneDaAreaLavoroBean add(EliminazioneDaAreaLavoroBean bean) throws Exception {		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		HashMap<String, String> errorMessages = null;
		
		for(ArchivioBean udFolder : bean.getListaRecord()) {
			DmpkCoreRemovefromsezarealavBean input = new DmpkCoreRemovefromsezarealavBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setCisezionearealavin(bean.getSezioneAreaLav());
			input.setFlgtipoobjin(udFolder.getFlgUdFolder());
			input.setIdobjin(new BigDecimal(udFolder.getIdUdFolder()));
			input.setMotiviin(bean.getMotivo());	
			
			DmpkCoreRemovefromsezarealav dmpkCoreRemovefromsezarealav = new DmpkCoreRemovefromsezarealav();
			StoreResultBean<DmpkCoreRemovefromsezarealavBean> output = dmpkCoreRemovefromsezarealav.execute(getLocale(),loginBean, input);
			
			if(output.getDefaultMessage() != null) {
				if(errorMessages == null) errorMessages = new HashMap<String, String>();
				errorMessages.put(udFolder.getIdUdFolder(), output.getDefaultMessage());
			}
		}
		
		bean.setErrorMessages(errorMessages);
		
		return bean;
	}	
	
	@Override
	public EliminazioneDaAreaLavoroBean get(EliminazioneDaAreaLavoroBean bean) throws Exception {		
		return null;
	}
	
	@Override
	public EliminazioneDaAreaLavoroBean remove(EliminazioneDaAreaLavoroBean bean)
	throws Exception {
		return null;
	}

	@Override
	public EliminazioneDaAreaLavoroBean update(EliminazioneDaAreaLavoroBean bean,
			EliminazioneDaAreaLavoroBean oldvalue) throws Exception {
		return bean;
	}

	@Override
	public PaginatorBean<EliminazioneDaAreaLavoroBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(EliminazioneDaAreaLavoroBean bean)
	throws Exception {
		return null;
	}

}
