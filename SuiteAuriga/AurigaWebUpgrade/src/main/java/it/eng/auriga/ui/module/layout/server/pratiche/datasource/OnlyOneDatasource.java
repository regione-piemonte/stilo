/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocSavefileasclobBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AttProcBean;
import it.eng.aurigamailbusiness.bean.ResultBean;
import it.eng.client.DmpkRegistrazionedocSavefileasclob;
import it.eng.client.OnlyOneClient;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "OnlyOneDatasource")
public class OnlyOneDatasource extends AbstractServiceDataSource<AttProcBean, AttProcBean> {

	private static final Logger log = Logger.getLogger(OnlyOneDatasource.class);

	@Override
	public AttProcBean call(AttProcBean bean) throws Exception {
		
		try {
			
			OnlyOneClient lOnlyOneClient = new OnlyOneClient();
			ResultBean<String> lResultBean = lOnlyOneClient.interrogaDatiPratica(bean.getNroProtocolloIstanza() + "/" + bean.getAnnoProtocolloIstanza());
			
			if(lResultBean.isInError()) {
				throw new StoreException(lResultBean.getDefaultMessage());
			}
			
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
			String token = loginBean.getToken();
			String idUserLavoro = loginBean.getIdUserLavoro();
			
			DmpkRegistrazionedocSavefileasclobBean input = new DmpkRegistrazionedocSavefileasclobBean();
			input.setCodidconnectiontokenin(token);
			input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
			input.setIddocin(new BigDecimal(bean.getIdDocIstanza()));
			input.setFilecontentin(lResultBean.getResultBean());
			
			DmpkRegistrazionedocSavefileasclob store = new DmpkRegistrazionedocSavefileasclob();
			StoreResultBean<DmpkRegistrazionedocSavefileasclobBean> output = store.execute(getLocale(), loginBean, input);
		
			if (output.isInError()) {
				throw new StoreException(output);
			}									
			
		} catch(StoreException se) {			
			throw new StoreException("Non è stato possibile recuperare i dati della pratica da WF: " + se.getMessage());
		} catch(Exception e) {			
			throw new StoreException("Non è stato possibile recuperare i dati della pratica da WF");
		}
		return bean;
	}

}