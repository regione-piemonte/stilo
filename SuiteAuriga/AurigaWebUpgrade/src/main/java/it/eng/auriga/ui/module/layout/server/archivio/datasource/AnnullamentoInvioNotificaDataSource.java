/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_collaboration.bean.DmpkCollaborationAnnullamentoinvioBean;
import it.eng.auriga.database.store.dmpk_collaboration.bean.DmpkCollaborationAnnullamentonotificaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.AnnullamentoInvioNotificaBean;
import it.eng.client.DmpkCollaborationAnnullamentoinvio;
import it.eng.client.DmpkCollaborationAnnullamentonotifica;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

@Datasource(id = "AnnullamentoInvioNotificaDataSource")
public class AnnullamentoInvioNotificaDataSource extends AbstractDataSource<AnnullamentoInvioNotificaBean, AnnullamentoInvioNotificaBean>{	

	@Override
	public AnnullamentoInvioNotificaBean add(AnnullamentoInvioNotificaBean bean) throws Exception {		
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		if("I".equals(bean.getFlgInvioNotifica())) {
				DmpkCollaborationAnnullamentoinvioBean input = new DmpkCollaborationAnnullamentoinvioBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				input.setIdinviomovimentoin(StringUtils.isNotBlank(bean.getIdInvioNotifica()) ? new BigDecimal(bean.getIdInvioNotifica()) : null);
				input.setFlgtypeobjsentin(bean.getFlgUdFolder());
				input.setIdobjsentin(new BigDecimal(bean.getIdUdFolder()));	
				input.setMotivoannin(bean.getMotivo());
				
				DmpkCollaborationAnnullamentoinvio dmpkCollaborationAnnullamentoinvio = new DmpkCollaborationAnnullamentoinvio();
				StoreResultBean<DmpkCollaborationAnnullamentoinvioBean> output = dmpkCollaborationAnnullamentoinvio.execute(getLocale(),loginBean, input);
				
				if(output.getDefaultMessage() != null) {
					throw new StoreException(output);
				}
		} else if("N".equals(bean.getFlgInvioNotifica())) {
				DmpkCollaborationAnnullamentonotificaBean input = new DmpkCollaborationAnnullamentonotificaBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				input.setIdnotificain(StringUtils.isNotBlank(bean.getIdInvioNotifica()) ? new BigDecimal(bean.getIdInvioNotifica()) : null);
				input.setFlgtypeobjnotifiedin(bean.getFlgUdFolder());
				input.setIdobjnotifiedin(new BigDecimal(bean.getIdUdFolder()));				
				input.setMotivoannin(bean.getMotivo());
				
				DmpkCollaborationAnnullamentonotifica dmpkCollaborationAnnullamentonotifica = new DmpkCollaborationAnnullamentonotifica();
				StoreResultBean<DmpkCollaborationAnnullamentonotificaBean> output = dmpkCollaborationAnnullamentonotifica.execute(getLocale(),loginBean, input);
				
				if(output.getDefaultMessage() != null) {
					throw new StoreException(output);
				}
		}					
				
		return bean;
	}	
	
	@Override
	public AnnullamentoInvioNotificaBean get(AnnullamentoInvioNotificaBean bean) throws Exception {		
		
		return null;
	}
	
	@Override
	public AnnullamentoInvioNotificaBean remove(AnnullamentoInvioNotificaBean bean)
	throws Exception {
		
		return null;
	}

	@Override
	public AnnullamentoInvioNotificaBean update(AnnullamentoInvioNotificaBean bean,
			AnnullamentoInvioNotificaBean oldvalue) throws Exception {
		
		return bean;
	}

	@Override
	public PaginatorBean<AnnullamentoInvioNotificaBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(AnnullamentoInvioNotificaBean bean)
	throws Exception {
		
		return null;
	}

}
