/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesIuassociazioneudvsprocBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AllegatoProcBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AssociaUdVsProcBean;
import it.eng.client.DmpkProcessesIuassociazioneudvsproc;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@Datasource(id="AssociaUdVsProcDatasource")
public class AssociaUdVsProcDatasource extends AbstractServiceDataSource<AssociaUdVsProcBean, AssociaUdVsProcBean> {

	private static final Logger log = Logger.getLogger(AssociaUdVsProcDatasource.class);

	@Override
	public AssociaUdVsProcBean call(AssociaUdVsProcBean bean) throws Exception {
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		if(bean.getListaAllegatiProc() != null) {
			for(AllegatoProcBean allegato : bean.getListaAllegatiProc()) {
				DmpkProcessesIuassociazioneudvsprocBean input = new DmpkProcessesIuassociazioneudvsprocBean();
				input.setCodidconnectiontokenin(token);
				input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);					
				input.setIdprocessin(new BigDecimal(bean.getIdProcess()));
				input.setIdudin(new BigDecimal(allegato.getIdUd()));
				input.setNroordinevsprocin(null);
				input.setFlgacqprodin("A");
				input.setCodruolodocinprocin("INTIST");						
				input.setCodstatoudinprocin(null); 		
				
				DmpkProcessesIuassociazioneudvsproc store = new DmpkProcessesIuassociazioneudvsproc();
				StoreResultBean<DmpkProcessesIuassociazioneudvsprocBean> output = store.execute(getLocale(), loginBean, input);

				if(StringUtils.isNotBlank(output.getDefaultMessage())) {
					if(output.isInError()) {
						throw new StoreException(output);		
					} else {
						addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
					}
				}
			}
		}

		return bean;
	}

}
