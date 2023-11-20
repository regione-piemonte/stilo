/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAddtorecentBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreExtractverdocBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AddToRecentBean;
import it.eng.client.DmpkCoreAddtorecent;
import it.eng.client.DmpkCoreExtractverdoc;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;

@Datasource(id = "AddToRecentDataSource")
public class AddToRecentDataSource extends AbstractDataSource<AddToRecentBean, AddToRecentBean>{	

	@Override
	public AddToRecentBean add(AddToRecentBean bean) throws Exception {		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		// Se ATTIVA_GDPR_COMPLIANCE = true chiamo la Dmpk_core.ExtractVerDoc
		if (ParametriDBUtil.getParametroDBAsBoolean(getSession(), "ATTIVA_GDPR_COMPLIANCE") && bean.getIdDoc()!=null ) {
			DmpkCoreExtractverdocBean input = new DmpkCoreExtractverdocBean();
			input.setCodidconnectiontokenin(loginBean.getToken());
			input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()) : null);
			input.setIddocin(new BigDecimal(bean.getIdDoc()));
			if (StringUtils.isNotBlank(bean.getNroProgrVer())) {
				input.setNroprogrverio(new BigDecimal(bean.getNroProgrVer()));
			}
			DmpkCoreExtractverdoc lDmpkCoreExtractverdoc = new DmpkCoreExtractverdoc();
			lDmpkCoreExtractverdoc.execute(getLocale(), loginBean, input);					
		} else {
			// Altrimento chiamo la DmpkCore.Addtorecent 
			DmpkCoreAddtorecentBean input = new DmpkCoreAddtorecentBean();
			input.setFlgtpdominioautin(loginBean.getSpecializzazioneBean().getTipoDominio());
			input.setIddominioautin(loginBean.getSpecializzazioneBean().getIdDominio());
			if(StringUtils.isNotBlank(loginBean.getIdUserLavoro())) {
				input.setIduserin(new BigDecimal(loginBean.getIdUserLavoro()));
			} else {
				input.setIduserin(loginBean.getIdUser());
			}
			input.setFlgtipoobjin("U");
			input.setIdobjin(new BigDecimal(bean.getIdUd()));
			input.setIddocdownloadedin(new BigDecimal(bean.getIdDoc()));
			input.setFlgvlin("D");
			input.setMotiviin(null);
					
			SchemaBean lSchemaBean = new SchemaBean();
			lSchemaBean.setSchema(loginBean.getSchema());
			DmpkCoreAddtorecent dmpkCoreAddtorecent = new DmpkCoreAddtorecent();
			dmpkCoreAddtorecent.execute(getLocale(), lSchemaBean, input);	
		}
		
		return bean;
	}	
	
	@Override
	public AddToRecentBean get(AddToRecentBean bean) throws Exception {		
		return null;
	}
	
	@Override
	public AddToRecentBean remove(AddToRecentBean bean)
	throws Exception {	
		return null;
	}

	@Override
	public AddToRecentBean update(AddToRecentBean bean,
			AddToRecentBean oldvalue) throws Exception {	
		return bean;
	}

	@Override
	public PaginatorBean<AddToRecentBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {	
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(AddToRecentBean bean)
	throws Exception {
		return null;
	}

}