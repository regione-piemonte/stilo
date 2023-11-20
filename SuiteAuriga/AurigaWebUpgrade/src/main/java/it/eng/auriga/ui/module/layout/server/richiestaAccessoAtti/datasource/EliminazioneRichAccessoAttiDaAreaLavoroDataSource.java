/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreRemovefromsezarealavBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioBean;
import it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean.EliminazioneRichAccessoAttiDaAreaLavoroBean;
import it.eng.auriga.ui.module.layout.server.richiestaAccessoAtti.datasource.bean.RichiestaAccessoAttiBean;
import it.eng.client.DmpkCoreRemovefromsezarealav;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "EliminazioneRichAccessoAttiDaAreaLavoroDataSource")
public class EliminazioneRichAccessoAttiDaAreaLavoroDataSource extends AbstractDataSource<EliminazioneRichAccessoAttiDaAreaLavoroBean, EliminazioneRichAccessoAttiDaAreaLavoroBean>{	

	@Override
	public EliminazioneRichAccessoAttiDaAreaLavoroBean add(EliminazioneRichAccessoAttiDaAreaLavoroBean bean) throws Exception {		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		HashMap<String, String> errorMessages = null;
		
		for(RichiestaAccessoAttiBean richAccessoAtti : bean.getListaRecord()) {
			DmpkCoreRemovefromsezarealavBean input = new DmpkCoreRemovefromsezarealavBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setCisezionearealavin(bean.getSezioneAreaLav());
			input.setFlgtipoobjin("U");
			input.setIdobjin(new BigDecimal(richAccessoAtti.getIdUd()));
			input.setMotiviin(bean.getMotivo());	
			
			DmpkCoreRemovefromsezarealav dmpkCoreRemovefromsezarealav = new DmpkCoreRemovefromsezarealav();
			StoreResultBean<DmpkCoreRemovefromsezarealavBean> output = dmpkCoreRemovefromsezarealav.execute(getLocale(),loginBean, input);
			
			if(output.getDefaultMessage() != null) {
				if(errorMessages == null) errorMessages = new HashMap<String, String>();
				errorMessages.put(richAccessoAtti.getIdUd(), output.getDefaultMessage());
			}
		}
		
		bean.setErrorMessages(errorMessages);
		
		return bean;
	}	
	
	@Override
	public EliminazioneRichAccessoAttiDaAreaLavoroBean get(EliminazioneRichAccessoAttiDaAreaLavoroBean bean) throws Exception {		
		return null;
	}
	
	@Override
	public EliminazioneRichAccessoAttiDaAreaLavoroBean remove(EliminazioneRichAccessoAttiDaAreaLavoroBean bean)
	throws Exception {
		return null;
	}

	@Override
	public EliminazioneRichAccessoAttiDaAreaLavoroBean update(EliminazioneRichAccessoAttiDaAreaLavoroBean bean,
			EliminazioneRichAccessoAttiDaAreaLavoroBean oldvalue) throws Exception {
		return bean;
	}

	@Override
	public PaginatorBean<EliminazioneRichAccessoAttiDaAreaLavoroBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(EliminazioneRichAccessoAttiDaAreaLavoroBean bean)
	throws Exception {
		return null;
	}

}
