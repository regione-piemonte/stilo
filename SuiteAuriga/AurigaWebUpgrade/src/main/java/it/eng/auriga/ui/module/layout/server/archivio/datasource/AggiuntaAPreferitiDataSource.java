/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAddtofavouritesBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.OperazioneMassivaArchivioBean;
import it.eng.client.DmpkCoreAddtofavourites;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "AggiuntaAPreferitiDataSource")
public class AggiuntaAPreferitiDataSource extends AbstractDataSource<OperazioneMassivaArchivioBean, OperazioneMassivaArchivioBean>{	

	@Override
	public OperazioneMassivaArchivioBean add(OperazioneMassivaArchivioBean bean) throws Exception {		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		HashMap<String, String> errorMessages = null;
		
		for(ArchivioBean udFolder : bean.getListaRecord()) {
			DmpkCoreAddtofavouritesBean input = new DmpkCoreAddtofavouritesBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setFlgtipoobjin(udFolder.getFlgUdFolder());
			input.setIdobjin(new BigDecimal(udFolder.getIdUdFolder()));
			input.setMotiviin(null);
			
			DmpkCoreAddtofavourites dmpkCoreAddtofavourites = new DmpkCoreAddtofavourites();
			StoreResultBean<DmpkCoreAddtofavouritesBean> output = dmpkCoreAddtofavourites.execute(getLocale(),loginBean, input);
			
			if(output.getDefaultMessage() != null) {
				if(errorMessages == null) errorMessages = new HashMap<String, String>();
				errorMessages.put(udFolder.getIdUdFolder(), output.getDefaultMessage());
			}
		}
		
		bean.setErrorMessages(errorMessages);
		
		return bean;
	}	
	
	@Override
	public OperazioneMassivaArchivioBean get(OperazioneMassivaArchivioBean bean) throws Exception {		
		
		return null;
	}
	
	@Override
	public OperazioneMassivaArchivioBean remove(OperazioneMassivaArchivioBean bean)
	throws Exception {
		
		return null;
	}

	@Override
	public OperazioneMassivaArchivioBean update(OperazioneMassivaArchivioBean bean,
			OperazioneMassivaArchivioBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public PaginatorBean<OperazioneMassivaArchivioBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(OperazioneMassivaArchivioBean bean)
	throws Exception {
		
		return null;
	}

}
